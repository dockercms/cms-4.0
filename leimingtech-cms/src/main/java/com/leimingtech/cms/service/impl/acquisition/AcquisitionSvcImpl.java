package com.leimingtech.cms.service.impl.acquisition;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.URI;
import java.text.DateFormat;
import java.text.NumberFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.commons.io.IOUtils;
import org.apache.commons.lang.RandomStringUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.StatusLine;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.HttpResponseException;
import org.apache.http.client.ResponseHandler;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.util.EntityUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.leimingtech.cms.core.util.Num62;
import com.leimingtech.cms.entity.acquisition.AcquisitionContentEntity;
import com.leimingtech.cms.entity.acquisition.AcquisitionEntity;
import com.leimingtech.cms.entity.acquisition.AcquisitionHistoryEntity;
import com.leimingtech.cms.entity.acquisition.AcquisitionTempEntity;
import com.leimingtech.cms.entity.acquisition.AcquisitionreplaceEntity;
import com.leimingtech.cms.service.acquisition.AcquisitionContentServiceI;
import com.leimingtech.cms.service.acquisition.AcquisitionHistoryServiceI;
import com.leimingtech.cms.service.acquisition.AcquisitionServiceI;
import com.leimingtech.cms.service.acquisition.AcquisitionSvcI;
import com.leimingtech.cms.service.acquisition.AcquisitionTempServiceI;
import com.leimingtech.cms.service.acquisition.AcquisitionreplaceServiceI;
import com.leimingtech.cms.service.impl.acquisition.AcquisitionServiceImpl.AcquisitionResultType;

/**
 * 数据采集数据交换实现
 * @author Administrator
 *
 */
@Service("AcquisitionSvcService")
@Transactional
public class AcquisitionSvcImpl implements AcquisitionSvcI {
	
	@Autowired
	private AcquisitionHistoryServiceI acqHistoryMng;// 采集历史管理
	@Autowired
	private AcquisitionServiceI acqMng;// 采集管理
	@Autowired
	private AcquisitionTempServiceI acqTempMng;// 采集进度管理
	@Autowired
	private AcquisitionContentServiceI acqContentMng;// 采集结果管理
	/**数据采集关联 内容替换*/
	@Autowired
	private AcquisitionreplaceServiceI acquisitionreplaceService;

	private Logger log = LoggerFactory.getLogger(AcquisitionSvcImpl.class);

	/**
	 * 开始采集
	 * 
	 * @param id
	 *            采集管理中的ID
	 * @return 是否真正启动采集
	 */
	@Override
	public boolean start(String id) {
		AcquisitionEntity acqu = acqMng.getEntity(AcquisitionEntity.class, id);
		if (acqu == null || acqu.getStatus() == AcquisitionServiceImpl.START) {
			return false;
		}
		Thread thread = new AcquisitionThread(acqu);
		thread.start();
		return true;
	}

	private void end(AcquisitionEntity acqu) {
		acqMng.end(acqu.getId());
		AcquisitionEntity acquisition = acqMng.popAcquFromQueue();
		if (acquisition != null) {
			String id = acquisition.getId();
			start(id);
		}
	}
	
	private class AcquisitionThread extends Thread {
		private AcquisitionEntity acqu;// 采集管理类

		public AcquisitionThread(AcquisitionEntity acqu) {
			super(acqu.getClass().getName() + "#" + acqu.getId());
			this.acqu = acqu;
		}

		@Override
		public void run() {
			if (acqu == null) {
				return;
			}
			acqu = acqMng.startAcq(acqu.getId());
			String[] plans = acqMng.getAllPlans(acqu);
			HttpClient client = new DefaultHttpClient();
			CharsetHandler handler = new CharsetHandler(acqu.getPageEncoding());
			List<String> contentList;
			String url;
			int currNum = acqu.getCurrNum();
			int currItem = acqu.getCurrItem();
			String acquId = acqu.getId();
			for (int i = plans.length - currNum; i >= 0; i--) {
				url = plans[i];
				contentList = getContentList(client, handler, url, acqu);
				String link;
				for (int j = contentList.size() - currItem; j >= 0; j--) {
					if (acqMng.isNeedBreak(acqu.getId(), plans.length - i, contentList.size() - j, contentList.size())) {
						client.getConnectionManager().shutdown();
						log.info("Acquisition#{} breaked", acqu.getId());
						return;
					}
					if (acqu.getPauseTime() > 0) {
						try {
							Thread.sleep(acqu.getPauseTime());
						} catch (InterruptedException e) {
							log.warn(null, e);
						}
					}
					link = contentList.get(j);
					float curr = contentList.size() - j;
					float total = contentList.size();
					AcquisitionTempEntity temp = newTemp(url, link, contentList.size() - j, curr, total, null);
					AcquisitionHistoryEntity history = newHistory(url, link, acqu);

					String contextPath = "/";

					saveContent(client, handler, contextPath, "upload/acquisition", acquId, link, temp, history);
				}
				currItem = 1;
			}
			client.getConnectionManager().shutdown();
			AcquisitionSvcImpl.this.end(acqu);
			log.info("Acquisition#{} complete", acqu.getId());
		}

		private List<String> getContentList(HttpClient client, CharsetHandler handler, String url, AcquisitionEntity acqu) {
			String linksetStart = acqu.getLinksetStart();
			String linksetEnd = acqu.getLinksetEnd();
			String linkStart = acqu.getLinkStart();
			String linkEnd = acqu.getLinkEnd();
			List<String> list = new ArrayList<String>();
			try {
				HttpGet httpget = new HttpGet(new URI(url));
				String base = url.substring(0, url.indexOf("/", url.indexOf("//") + 2));
				String html = client.execute(httpget, handler);
				int start = html.indexOf(linksetStart);
				if (start == -1) {
					return list;
				}
				start += linksetStart.length();
				int end = html.indexOf(linksetEnd, start);
				if (end == -1) {
					return list;
				}
				String s = html.substring(start, end);
				start = 0;
				String link;
				log.info(s);
				while ((start = s.indexOf(linkStart, start)) != -1) {
					start += linkStart.length();
					end = s.indexOf(linkEnd, start);
					if (end == -1) {
						return list;
					} else {
						link = s.substring(start, end);
						// 内容地址前缀
						if (StringUtils.isNotBlank(acqu.getContentPrefix())) {
							link = acqu.getContentPrefix() + link;
						}
						if (!link.startsWith("http")) {
							link = base + link;
						}
						log.debug("content link: {}", link);
						list.add(link);
						start = end + linkEnd.length();
					}
				}
			} catch (Exception e) {
				log.warn(null, e);
			}
			return list;
		}

		private Boolean saveContent(HttpClient client, CharsetHandler handler, String contextPath, String uploadPath, String acquId,
				String url, AcquisitionTempEntity temp, AcquisitionHistoryEntity history) {
			AcquisitionEntity acqu = acqMng.getEntity(AcquisitionEntity.class, acquId);
			String titleStart = acqu.getTitleStart();
			String titleEnd = acqu.getTitleEnd();
			String contentStart = acqu.getContentStart();
			String contentEnd = acqu.getContentEnd();
			String viewStart = acqu.getViewStart();
			String viewEnd = acqu.getViewEnd();
			String viewIdStart = acqu.getViewIdStart();
			String viewIdEnd = acqu.getViewIdEnd();
			String viewLink = acqu.getViewLink();
			String authorStart = acqu.getAuthorStart();
			String authorEnd = acqu.getAuthorEnd();
			String originStart = acqu.getOriginStart();
			String originEnd = acqu.getOriginEnd();
			String releaseTimeStart = acqu.getReleasetimeStart();
			String releaseTimeEnd = acqu.getReleasetimeEnd();
			String descriptionStart = acqu.getDescriptionStart();
			String descriptionEnd = acqu.getDescriptionEnd();
			acqu.setCreatedtime(new Date());
			history.setAcquisition(acqu);
			try {
				int start, end;
				HttpGet httpget = new HttpGet(new URI(url));
				String html = client.execute(httpget, handler);
				
				List<AcquisitionreplaceEntity> repList = acquisitionreplaceService.getListByAcq(acquId + "");
				if(repList!=null && repList.size()>0){
					for(int i=0;i<repList.size();i++){
						AcquisitionreplaceEntity rep=repList.get(i);
						html=html.replaceAll(rep.getReplaceOld(), rep.getReplaceNew());
					}
				}
				
				start = html.indexOf(titleStart);
				if (start == -1) {
					return handerResult(temp, history, null, AcquisitionResultType.TITLESTARTNOTFOUND);
				}
				start += titleStart.length();
				end = html.indexOf(titleEnd, start);
				if (end == -1) {
					return handerResult(temp, history, null, AcquisitionResultType.TITLEENDNOTFOUND);
				}
				String title = html.substring(start, end);
				if (acqHistoryMng.checkExistByProperties(true, title)) {
					return handerResult(temp, history, title, AcquisitionResultType.TITLEEXIST, true);
				}
				start = html.indexOf(contentStart);
				if (start == -1) {
					return handerResult(temp, history, title, AcquisitionResultType.CONTENTSTARTNOTFOUND);
				}
				start += contentStart.length();
				end = html.indexOf(contentEnd, start);
				if (end == -1) {
					return handerResult(temp, history, title, AcquisitionResultType.CONTENTENDNOTFOUND);
				}
				String txt = html.substring(start, end);

				if (acqMng.getImgAcqu(acqu)) {
					List<String> imgUrls = getImageSrc(txt);
					for (String img : imgUrls) {
						if (StringUtils.isNotBlank(acqu.getImgPrefix())) {
							img = acqu.getImgPrefix() + img;
						}
						txt = txt.replace(img, saveImg(img, contextPath, uploadPath));
					}
				}

				String author = null;
				if (StringUtils.isNotBlank(authorStart)) {
					start = html.indexOf(authorStart);
					if (start == -1) {
						return handerResult(temp, history, null, AcquisitionResultType.AUTHORSTARTNOTFOUND);
					}
					start += authorStart.length();
					end = html.indexOf(authorEnd, start);
					if (end == -1) {
						return handerResult(temp, history, null, AcquisitionResultType.AUTHORENDNOTFOUND);
					}
					author = html.substring(start, end);
				}

				String origin = null;
				if (StringUtils.isNotBlank(originStart)) {
					start = html.indexOf(originStart);
					if (start == -1) {
						return handerResult(temp, history, null, AcquisitionResultType.ORIGINSTARTNOTFOUND);
					}
					start += originStart.length();
					end = html.indexOf(originEnd, start);
					if (end == -1) {
						return handerResult(temp, history, null, AcquisitionResultType.ORIGINENDNOTFOUND);
					}
					origin = html.substring(start, end).trim();
				}

				String description = null;
				if (StringUtils.isNotBlank(descriptionStart)) {
					start = html.indexOf(descriptionStart);
					if (start == -1) {
						return handerResult(temp, history, null, AcquisitionResultType.DESCRISTARTNOTFOUND);
					}
					start += descriptionStart.length();
					end = html.indexOf(descriptionEnd, start);
					if (end == -1) {
						return handerResult(temp, history, null, AcquisitionResultType.DESCRIENDNOTFOUND);
					}
					description = html.substring(start, end).trim();
				}

				Date releaseTime = null;
				if (StringUtils.isNotBlank(releaseTimeStart)) {
					start = html.indexOf(releaseTimeStart);
					if (start == -1) {
						return handerResult(temp, history, null, AcquisitionResultType.RELEASESTARTNOTFOUND);
					}
					start += releaseTimeStart.length();
					end = html.indexOf(releaseTimeEnd, start);
					if (end == -1) {
						return handerResult(temp, history, null, AcquisitionResultType.RELEASEENDNOTFOUND);
					}
					String releaseDate = html.substring(start, end).trim();
					SimpleDateFormat df = new SimpleDateFormat(acqu.getReleasetimeFormat());
					releaseTime = df.parse(releaseDate);
				}else{
					releaseTime=new Date();
				}

				String view = null;
				if (StringUtils.isNotBlank(viewLink)) {
					start = html.indexOf(viewIdStart);
					if (start == -1) {
						return handerResult(temp, history, null, AcquisitionResultType.VIEWIDSTARTNOTFOUND);
					}
					start += viewIdStart.length();
					end = html.indexOf(viewIdEnd, start);
					if (end == -1) {
						return handerResult(temp, history, null, AcquisitionResultType.VIEWIDENDNOTFOUND);
					}
					viewLink += html.substring(start, end).trim();
					HttpGet viewHttpGet = new HttpGet(new URI(viewLink));
					html = client.execute(viewHttpGet, handler);
				}
				if (StringUtils.isNotBlank(viewStart)) {
					start = html.indexOf(viewStart);
					if (start == -1) {
						return handerResult(temp, history, null, AcquisitionResultType.VIEWSTARTNOTFOUND);
					}
					start += viewStart.length();
					end = html.indexOf(viewEnd, start);
					if (end == -1) {
						return handerResult(temp, history, null, AcquisitionResultType.VIEWENDNOTFOUND);
					}
					view = html.substring(start, end).trim();
				}

				saveContent(title, txt, origin, author, description, releaseTime, acquId, AcquisitionResultType.SUCCESS, temp, history);
				// if(StringUtils.isNotBlank(view)){
				// ContentCount count=content.getContentCount();
				// int c=Integer.parseInt(view);
				// //采集访问一次需减一
				// if(StringUtils.isNotBlank(viewLink)){
				// c=c-1;
				// }
				// count.setViews(c);
				// contentCountMng.update(count);
				// }
				temp.setCreatedtime(new Date());
				acqTempMng.save(temp);
				history.setCreatedtime(new Date());//创建时间
				acqHistoryMng.save(history);
				return true;
			} catch (Exception e) {
				e.printStackTrace();
				log.warn(null, e);
				return handerResult(temp, history, null, AcquisitionResultType.UNKNOW);
			}
		}
		
		public void saveContent(String title, String txt, String origin, String author, String description, Date releaseDate,
				String acquId, AcquisitionResultType resultType, AcquisitionTempEntity temp, AcquisitionHistoryEntity history) {
			AcquisitionContentEntity content = new AcquisitionContentEntity();
			content.setTitle(title);
			content.setTxt(txt);
			content.setAuthor(author);
			content.setReleaseDate(releaseDate);
			content.setDescription(description);
			content.setOriginUrl(origin);
			content.setLink(history.getContentUrl());
			content.setCreatedtime(new Date());//创建时间
			acqContentMng.save(content);

			temp.setTitle(title);
			temp.setDescription(resultType.name());
			temp.setCreatedtime(new Date());
			history.setTitle(title);
			history.setContent(content);
			history.setDescription(resultType.name());
		}

		private List<String> getImageSrc(String htmlCode) {
			List<String> imageSrcList = new ArrayList<String>();
			String regular = "<img(.*?)src=\"(.*?)\"";
			String img_pre = "(?i)<img(.*?)src=\"";
			String img_sub = "\"";
			Pattern p = Pattern.compile(regular, Pattern.CASE_INSENSITIVE);
			Matcher m = p.matcher(htmlCode);
			String src = null;
			while (m.find()) {
				src = m.group();
				src = src.replaceAll(img_pre, "").replaceAll(img_sub, "").trim();
				imageSrcList.add(src);
			}
			return imageSrcList;
		}

		private String saveImg(String imgUrl, String contextPath, String uploadPath) {
			HttpClient client = new DefaultHttpClient();
			String outFileName = "";
			OutputStream os = null;
			try {
				HttpGet httpget = new HttpGet(new URI(imgUrl));
				HttpResponse response = client.execute(httpget);
				InputStream is = null;
				HttpEntity entity = null;
				entity = response.getEntity();
				is = entity.getContent();

				int splitIndex = imgUrl.lastIndexOf(".");
				String imgSuffixal = imgUrl.substring(splitIndex + 1);
				DateFormat MONTH_FORMAT = new SimpleDateFormat("/yyyyMM/ddHHmmss");
				outFileName = uploadPath + MONTH_FORMAT.format(new Date()) + RandomStringUtils.random(4, Num62.N36_CHARS) + "." + imgSuffixal;
				String filePath=acqMng.getPath(outFileName);
				File file=new File(filePath);
				if(!file.getParentFile().exists()){
					file.getParentFile().mkdirs();
				}
				os = new FileOutputStream(file);
				IOUtils.copy(is, os);
			} catch (Exception e) {
				e.printStackTrace();
				return imgUrl;
			}
			return contextPath + outFileName;
		}

		private Boolean handerResult(AcquisitionTempEntity temp, AcquisitionHistoryEntity history, String title,
				AcquisitionResultType errorType) {
			return handerResult(temp, history, title, errorType, false);
		}

		private Boolean handerResult(AcquisitionTempEntity temp, AcquisitionHistoryEntity history, String title,
				AcquisitionResultType errorType, Boolean repeat) {
			temp.setDescription(errorType.name());
			temp.setTitle(title);
			temp.setCreatedtime(new Date());//创建时间
			acqTempMng.save(temp);
			if (!repeat) {
				history.setTitle(title);
				history.setDescription(errorType.name());
				history.setCreatedtime(new Date());//创建时间
				acqHistoryMng.save(history);
			}
			return false;
		}
	}

	private AcquisitionHistoryEntity newHistory(String channelUrl, String contentUrl, AcquisitionEntity acqu) {
		AcquisitionHistoryEntity history = new AcquisitionHistoryEntity();
		history.setChannelUrl(channelUrl);
		history.setContentUrl(contentUrl);
		history.setAcquisition(acqu);
		history.setCreatedtime(new Date());
		return history;
	}

	private AcquisitionTempEntity newTemp(String channelUrl, String contentUrl, Integer id, Float curr, Float total, Object site) {
		AcquisitionTempEntity temp = new AcquisitionTempEntity();
		temp.setChannelUrl(channelUrl);
		temp.setContentUrl(contentUrl);
		temp.setSeq(id);
		NumberFormat nf = NumberFormat.getPercentInstance();
		String percent = nf.format(curr / total);
		temp.setPercents(Integer.parseInt(percent.substring(0, percent.length() - 1)));
		temp.setCreatedtime(new Date());
		// temp.setSite(site);
		return temp;
	}
	
	private class CharsetHandler implements ResponseHandler<String> {
		private String charset;

		public CharsetHandler(String charset) {
			this.charset = charset;
		}

		public String handleResponse(HttpResponse response) throws ClientProtocolException, IOException {
			StatusLine statusLine = response.getStatusLine();
			if (statusLine.getStatusCode() >= 300) {
				throw new HttpResponseException(statusLine.getStatusCode(), statusLine.getReasonPhrase());
			}
			HttpEntity entity = response.getEntity();
			if (entity != null) {
				if (!StringUtils.isBlank(charset)) {
					return EntityUtils.toString(entity, charset);
				} else {
					return EntityUtils.toString(entity);
				}
			} else {
				return null;
			}
		}
	}


}
