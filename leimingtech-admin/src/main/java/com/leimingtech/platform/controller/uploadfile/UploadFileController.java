package com.leimingtech.platform.controller.uploadfile;

import java.io.File;
import java.io.FileOutputStream;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import net.sf.json.JSONObject;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.util.FileCopyUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import com.leimingtech.core.base.ClientManager;
import com.leimingtech.core.base.ContextHolderUtils;
import com.leimingtech.core.entity.AttachPictureEntity;
import com.leimingtech.core.entity.Client;
import com.leimingtech.core.entity.PfConfigEntity;
import com.leimingtech.core.entity.SiteEntity;
import com.leimingtech.core.entity.VideoSourcesEntity;
import com.leimingtech.core.entity.WaterMarkEntity;
import com.leimingtech.core.service.AttachPictureServiceI;
import com.leimingtech.core.service.SystemService;
import com.leimingtech.core.service.VideoSourcesServiceI;
import com.leimingtech.core.util.FileUtil;
import com.leimingtech.core.util.OpenOfficePDFConverter;
import com.leimingtech.core.util.ResourceUtil;
import com.leimingtech.core.util.SystemPath;
import com.leimingtech.core.util.VideoUtil;
import com.leimingtech.core.util.image.IThumbnailCreator;
import com.leimingtech.core.util.image.ThumbnailCreatorFactory;
import com.leimingtech.platform.constants.CmsConstants;

/**
 * @author :linjm linjianmao@gmail.com
 * @version :2014-4-25上午09:39:37 description :
 **/
@Controller
@RequestMapping("/uploadFileController")
public class UploadFileController {

	// private List<UploadFileMeta> files = new ArrayList<UploadFileMeta>();

	// private UploadFileMeta fileMeta = null;

	@Autowired
	private AttachPictureServiceI attachPictureService;

	@Autowired
	private VideoSourcesServiceI videoSourcesService;

	@Autowired
	private SystemService systemService;

	private SimpleDateFormat t = new SimpleDateFormat("yyyyMMdd");

	/***************************************************
	 * 
	 * 上传方法 URL: controller.do?imageUpload upload(): receives files
	 * 
	 * @param request
	 *            : MultipartHttpServletRequest auto passed
	 * @param response
	 *            : HttpServletResponse auto passed
	 * @return json
	 ****************************************************/
	@RequestMapping(params = "imageUpload")
	@ResponseBody
	public JSONObject upload(MultipartHttpServletRequest request, HttpServletResponse response) {
		HttpSession session = ContextHolderUtils.getSession();
		String sessionId = request.getParameter("PHPSESSID");
		Client client = ClientManager.getInstance().getClient(sessionId);
		SiteEntity site = client.getSite();
		JSONObject json = new JSONObject();

		String syspath = SystemPath.getSysPath();
		String path = request.getParameter("filePath");
		String flag = request.getParameter("flag");
		String customPath = "/upload/imgfile/" + t.format(new Date()) + "/";
		if (StringUtils.isNotEmpty(path)) {
			// 自定义文件路径 File.separatorChar
			customPath = "/upload/" + path + "/";
		}

		/*
		 * 默认去sysConfig.properties中lmcms.site.staticfiles.path指定配置路径 没有配置的话就读取工程
		 * WebRoot/wwwroot目录
		 */
		String savePath = "";
		String staticFilesPath = ResourceUtil.getCMSStaticFilesPath();// cms站点静态资源存放路径
		if (StringUtils.isNotEmpty(staticFilesPath)) {
			savePath = staticFilesPath + "/" + site.getSitePath() + customPath;// 站点资源路径
		} else {
			savePath = syspath + CmsConstants.SITE_STORAGE_PATH + "/" + site.getSitePath() + customPath;// 站点模板资源路径
		}

		savePath = savePath.replace("/", "\\");
		File file = new File(savePath);
		if (!file.exists()) {
			file.mkdirs();
		}
		// 1. build an iterator
		Iterator<String> itr = request.getFileNames();
		MultipartFile mpf = null;

		// 2. get each file
		while (itr.hasNext()) {
			// 2.1 get next MultipartFile
			mpf = request.getFile(itr.next());
			// 2.2 create new fileMeta
			json.put("fileName", mpf.getOriginalFilename());
			json.put("fileSize", FileUtil.FormetFileSize(mpf.getSize()));
			json.put("fileType", mpf.getContentType());

			String filename = mpf.getOriginalFilename();
			try {
				filename = System.currentTimeMillis() + filename.substring(filename.lastIndexOf("."));
				json.put("reName", filename);
				json.put("localPath",  customPath + filename);
				// json.put("", mpf.getBytes());

				// copy file to local disk (make sure the path
				// "e.g. D:/temp/files" exists)
				FileCopyUtils.copy(mpf.getBytes(), new FileOutputStream(savePath + filename));
				System.out.println(mpf.getOriginalFilename() + " uploaded! " + savePath + filename);

				// 系统缩略图，自动生成
				IThumbnailCreator thumbnailCreator = ThumbnailCreatorFactory.getCreator();
				thumbnailCreator.scaleRateImageFile(savePath + filename, savePath + filename + ".thumb.jpg", 160, 160);
				// 加水印
				WaterMark(syspath, savePath, filename, thumbnailCreator);
				json.put("thumbnailpath", customPath + filename + ".thumb.jpg");
			} catch (Exception e) {
				e.printStackTrace();
			}
			// 2.3 do databases op
			AttachPictureEntity attachPictureEntity = new AttachPictureEntity();
			attachPictureEntity.setLocalname(json.getString("fileName"));
			attachPictureEntity.setLocalpath(json.getString("localPath"));
			attachPictureEntity.setThumbnailpath(json.getString("thumbnailpath"));
			attachPictureEntity.setRealname(json.getString("reName"));
			attachPictureEntity.setCreatedate(new Date());
			attachPictureEntity.setSiteid(site.getId());
			attachPictureService.save(attachPictureEntity);
			// json.put("id", attachPictureEntity.getId());//图片id
		}
		return json;
	}

	private void WaterMark(final String syspath, final String savePath, final String filename, final IThumbnailCreator thumbnailCreator) {
		Map<String, String> mCache = PfConfigEntity.pfconfigCache;
		String watermark = mCache.get("watermark");
		if (StringUtils.isNotEmpty(watermark) && "1".equals(watermark)) {
			List<WaterMarkEntity> waterMarks = attachPictureService.getList(WaterMarkEntity.class);
			if (waterMarks.size() > 0) {
				final WaterMarkEntity waterMark = waterMarks.get(0);
				new Thread(new Runnable() {
					@Override
					public void run() {
						thumbnailCreator.pressImage(savePath + filename, syspath + waterMark.getImagepath(),
								Integer.valueOf(waterMark.getPosition()));
					}
				}).start();
			}
		}
	}

	/**
	 * 视频上传
	 * 
	 * @param request
	 * @param response
	 * @return
	 */
	@RequestMapping(params = "videoUpload")
	@ResponseBody
	public JSONObject VideoUpload(MultipartHttpServletRequest request, HttpServletResponse response) {
		HttpSession session = ContextHolderUtils.getSession();
		String sessionId = request.getParameter("PHPSESSID");
		Client client = ClientManager.getInstance().getClient(sessionId);
		SiteEntity site = client.getSite();
		JSONObject json = new JSONObject();
		String syspath = SystemPath.getSysPath();
		String path = request.getParameter("filePath");
		String flag = request.getParameter("flag");
		// 文件路径
		String customPath = "/upload/vodeofile/" + t.format(new Date()) + "/";
		if (StringUtils.isNotEmpty(path)) {
			// 自定义文件路径 File.separatorChar
			customPath = "/upload/" + path + "/";
		}

		/*
		 * 默认去sysConfig.properties中lmcms.site.staticfiles.path指定配置路径 没有配置的话就读取工程
		 * WebRoot/wwwroot目录
		 */
		String savePath = "";
		String staticFilesPath = ResourceUtil.getCMSStaticFilesPath();// cms站点静态资源存放路径
		if (StringUtils.isNotEmpty(staticFilesPath)) {
			savePath = staticFilesPath + "/" + site.getSitePath() + customPath;// 站点资源路径
		} else {
			savePath = syspath + CmsConstants.SITE_STORAGE_PATH + "/" + site.getSitePath() + customPath;// 站点模板资源路径
		}
		
		savePath = savePath.replace("/", "\\");
		File file = new File(savePath);
		if (!file.exists()) {
			file.mkdirs();
		}
		// 1. build an iterator
		Iterator<String> itr = request.getFileNames();
		MultipartFile mpf = null;

		// 2. get each file
		while (itr.hasNext()) {
			// 2.1 get next MultipartFile
			mpf = request.getFile(itr.next());
			// 2.2 create new fileMeta
			json.put("fileName", mpf.getOriginalFilename());
			json.put("fileSize", FileUtil.FormetFileSize(mpf.getSize()));
			json.put("fileType", mpf.getContentType());

			try {
				String filename = mpf.getOriginalFilename();
				filename = System.currentTimeMillis() + filename.substring(filename.lastIndexOf("."));
				json.put("reName", filename);
				json.put("localPath", customPath + filename);
				json.put("realPath", savePath + filename);

				// copy file to local disk (make sure the path
				// "e.g. D:/temp/files" exists)
				FileCopyUtils.copy(mpf.getBytes(), new FileOutputStream(savePath + filename));
				System.out.println(mpf.getOriginalFilename() + " uploaded! " + savePath + filename);

				// 生成默认图
				boolean ff = VideoUtil.captureDefaultImage(savePath + filename, savePath + filename + ".jpg");
				System.out.println(ff);
				json.put("defaultImage", customPath + filename + ".jpg");
			} catch (Exception e) {
				e.printStackTrace();
			}
			// 2.3 do databases op
			VideoSourcesEntity v = new VideoSourcesEntity();
			v.setLocalpath(json.getString("localPath"));
			v.setRealname(json.getString("reName"));
			// v.setTranspath(json.getString("transpath"));
			v.setDefaultimage(json.getString("defaultImage"));
			v.setVideoname(mpf.getOriginalFilename());
			v.setVideosize(mpf.getSize());
			// v.setTime(VideoUtil.getDuration(json.getString("realPath")));
			v.setCreatedate(new Date());
			v.setSiteid(site.getId());
			videoSourcesService.save(v);
			// json.put("id", attachPictureEntity.getId());//图片id
		}
		return json;
	}

	@RequestMapping(params = "wordUpload")
	@ResponseBody
	public JSONObject wordUpload(MultipartHttpServletRequest request, HttpServletResponse response) {
		JSONObject json = new JSONObject();
		String syspath = SystemPath.getSysPath();
		String path = request.getParameter("filePath");
		String flag = request.getParameter("flag");
		
		HttpSession session = ContextHolderUtils.getSession();
		Client client = ClientManager.getInstance().getClient(session.getId());
		SiteEntity site = client.getSite();
		
		// 文件路径按作业/ID/date/
		String customPath = "/upload/wordfile/";
		if (StringUtils.isNotEmpty(path)) {
			// 自定义文件路径 File.separatorChar
			customPath = "/upload/" + path + "/";
		}

		/*
		 * 默认去sysConfig.properties中lmcms.site.staticfiles.path指定配置路径 没有配置的话就读取工程
		 * WebRoot/wwwroot目录
		 */
		String savePath = "";
		String staticFilesPath = ResourceUtil.getCMSStaticFilesPath();// cms站点静态资源存放路径
		if (StringUtils.isNotEmpty(staticFilesPath)) {
			savePath = staticFilesPath + "/" + site.getSitePath() + customPath;// 站点资源路径
		} else {
			savePath = syspath + CmsConstants.SITE_STORAGE_PATH + "/" + site.getSitePath() + customPath;// 站点模板资源路径
		}
		
		savePath = savePath.replace("/", "\\");
		File file = new File(savePath);
		if (!file.exists()) {
			file.mkdirs();
		}
		// 1. build an iterator
		Iterator<String> itr = request.getFileNames();
		MultipartFile mpf = null;

		// 2. get each file
		while (itr.hasNext()) {
			// 2.1 get next MultipartFile
			mpf = request.getFile(itr.next());
			// 2.2 create new fileMeta
			json.put("fileName", mpf.getOriginalFilename());
			json.put("fileSize", FileUtil.FormetFileSize(mpf.getSize()));
			json.put("fileType", mpf.getContentType());

			String filename = mpf.getOriginalFilename();
			try {
				filename = System.currentTimeMillis() + filename.substring(filename.lastIndexOf("."));
				json.put("reName", filename);
				json.put("localPath", customPath + filename);
				// json.put("", mpf.getBytes());

				// copy file to local disk (make sure the path
				// "e.g. D:/temp/files" exists)
				FileCopyUtils.copy(mpf.getBytes(), new FileOutputStream(savePath + filename));
				System.out.println(mpf.getOriginalFilename() + " uploaded! " + savePath + filename);

				// 系统缩略图，自动生成
				OpenOfficePDFConverter
						.changeDoc(savePath + filename, savePath + filename.substring(0, filename.lastIndexOf(".")) + ".html");
				String wordname = savePath + filename.substring(0, filename.lastIndexOf(".")) + ".html";
				json.put("content", FileUtil.readText(wordname).replaceAll("src=\"", "src=\"/" + customPath));
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		return json;
	}

	/***************************************************
	 * URL: /controller.do?getView get(): get file as an attachment
	 * 
	 * @param response
	 *            : passed by the server
	 * @param value
	 *            : value from the URL
	 * @return void
	 ****************************************************/
	// @RequestMapping(params = "getView", method =
	// {RequestMethod.GET,RequestMethod.POST})
	// public void get(HttpServletResponse response,String value){
	// UploadFileMeta getFile = files.get(Integer.parseInt(value));
	// try {
	// response.setContentType(getFile.getFileType());
	// response.setHeader("Content-disposition",
	// "attachment; filename=\""+getFile.getFileName()+"\"");
	// FileCopyUtils.copy(getFile.getBytes(), response.getOutputStream());
	// }catch (IOException e) {
	// e.printStackTrace();
	// }
	// }
}
