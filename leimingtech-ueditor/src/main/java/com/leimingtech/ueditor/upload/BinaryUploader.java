package com.leimingtech.ueditor.upload;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.apache.commons.collections.MapUtils;
import org.apache.commons.fileupload.FileItemIterator;
import org.apache.commons.fileupload.FileItemStream;
import org.apache.commons.fileupload.FileUploadException;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;

import com.leimingtech.core.base.ContextHolderUtils;
import com.leimingtech.core.common.CmsConstants;
import com.leimingtech.core.common.FileType;
import com.leimingtech.core.entity.AttachPictureEntity;
import com.leimingtech.core.entity.VideoSourcesEntity;
import com.leimingtech.core.service.AttachPictureServiceI;
import com.leimingtech.core.service.VideoSourcesServiceI;
import com.leimingtech.core.util.PathFormat;
import com.leimingtech.core.util.PlatFormUtil;
import com.leimingtech.core.util.VideoUtil;
import com.leimingtech.core.util.image.IThumbnailCreator;
import com.leimingtech.core.util.image.ThumbnailCreatorFactory;
import com.leimingtech.ueditor.define.ActionMap;
import com.leimingtech.ueditor.define.AppInfo;
import com.leimingtech.ueditor.define.BaseState;
import com.leimingtech.ueditor.define.State;

public class BinaryUploader {
	
	public static final State save(HttpServletRequest request,
			Map<String, Object> conf) {
		
		boolean save=MapUtils.getBooleanValue(conf, "save", true);
		
		FileItemStream fileStream = null;
		boolean isAjaxUpload = request.getHeader( "X_Requested_With" ) != null;

		if (!ServletFileUpload.isMultipartContent(request)) {
			return new BaseState(false, AppInfo.NOT_MULTIPART_CONTENT);
		}

		ServletFileUpload upload = new ServletFileUpload(
				new DiskFileItemFactory());

        if ( isAjaxUpload ) {
            upload.setHeaderEncoding( "UTF-8" );
        }

		try {
			FileItemIterator iterator = upload.getItemIterator(request);

			while (iterator.hasNext()) {
				fileStream = iterator.next();

				if (!fileStream.isFormField())
					break;
				fileStream = null;
			}

			if (fileStream == null) {
				return new BaseState(false, AppInfo.NOTFOUND_UPLOAD_DATA);
			}

			String savePath = (String) conf.get("savePath");
			String originFileName = fileStream.getName();
			String suffix = FileType.getSuffixByFilename(originFileName);

			originFileName = originFileName.substring(0,
					originFileName.length() - suffix.length());
			
			String mp4path = savePath + ".mp4";
			savePath = savePath + suffix;

			long maxSize = ((Long) conf.get("maxSize")).longValue();

			if (!validType(suffix, (String[]) conf.get("allowFiles"))) {
				return new BaseState(false, AppInfo.NOT_ALLOW_FILE_TYPE);
			}

			savePath = PathFormat.parse(savePath, originFileName);
			mp4path = PathFormat.parse(mp4path, originFileName);

			String uploadFilesPath=CmsConstants.getUploadFilesPath(PlatFormUtil.getSessionSite());
			String physicalPath = uploadFilesPath + savePath;
			String physicalmp4Path = uploadFilesPath + mp4path;

			InputStream is = fileStream.openStream();
			State storageState = StorageManager.saveFileByInputStream(is,
					physicalPath, maxSize);
			is.close();
			
			if (storageState.isSuccess()) {
				storageState.putInfo("url", PathFormat.format(savePath));
				storageState.putInfo("type", suffix);
				storageState.putInfo("original", originFileName + suffix);
			}
			
			if (Integer.valueOf(conf.get("upload_file_type").toString()) == ActionMap.UPLOAD_VIDEO && !".mp4".equalsIgnoreCase(suffix) && !".flv".equalsIgnoreCase(suffix)) {
				if (VideoUtil.convert2Mp4(physicalPath, physicalmp4Path)) {
					storageState.putInfo("url", PathFormat.format(mp4path));//PlatFormUtil.getCurrentSitedomain() + 
					storageState.putInfo("type", ".mp4");
					storageState.putInfo("original", originFileName + ".mp4");
					uploaderAfter(save,Integer.valueOf(conf.get("upload_file_type").toString()), physicalmp4Path, originFileName + suffix,
							PathFormat.format(mp4path), storageState);
				}
			} else {
				uploaderAfter(save,Integer.valueOf(conf.get("upload_file_type").toString()), physicalPath, fileStream.getName(),
						PathFormat.format(savePath), storageState);
			}
			return storageState;
		} catch (FileUploadException e) {
			return new BaseState(false, AppInfo.PARSE_REQUEST_ERROR);
		} catch (IOException e) {
		}
		return new BaseState(false, AppInfo.IO_ERROR);
	}

	private static boolean validType(String type, String[] allowTypes) {
		List<String> list = Arrays.asList(allowTypes);

		return list.contains(type);
	}
	
	/**
	 * 上传之后
	 * @param actionCode 有多种类型  现在只拦截 image 和 video
	 * @param filepath  上传后的资源路径
	 * @param localname 文件名称
	 * @param saveurl 保存的地址 以/开头
	 * @param storageState 上传状态
	 */
	private static void uploaderAfter(boolean issave ,int actionCode,String filepath,String localname,String saveurl,State storageState) {
		
		AttachPictureServiceI attachPictureService=(AttachPictureServiceI)PlatFormUtil.getInterface("attachPictureService");
		VideoSourcesServiceI videoSourcesService=(VideoSourcesServiceI)PlatFormUtil.getInterface("videoSourcesService");
		String finalUrl = saveurl;
		
		String siteid = PlatFormUtil.getSessionSiteId();
		Date date = new Date();
		switch (actionCode) {
		case ActionMap.UPLOAD_IMAGE:
			thumbThread(filepath, ContextHolderUtils.getSession(), 450, 450, ".450_450.jpg");
			thumbThread(filepath, ContextHolderUtils.getSession(), 160, 160, ".thumb.jpg");
			WaterMark(filepath, ContextHolderUtils.getSession());

				AttachPictureEntity attachPictureEntity = new AttachPictureEntity();
				attachPictureEntity.setLocalname(localname);
				attachPictureEntity.setLocalpath(finalUrl);
				attachPictureEntity.setThumbnailpath(finalUrl + ".thumb.jpg");
				attachPictureEntity.setRealname(new File(filepath).getName());
				
				attachPictureEntity.setCreatedate(date);
				attachPictureEntity.setSiteid(siteid);
				
			if(issave){
				attachPictureService.save(attachPictureEntity);
			}
			storageState.putInfo("defaultimage", finalUrl + ".thumb.jpg");
			storageState.putInfo("createdate", new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(date));
			break;
		case ActionMap.UPLOAD_VIDEO:
			if (videoCaptureDefaultImage(filepath)) {
				VideoSourcesEntity v = new VideoSourcesEntity();
				if (new File(filepath + "1.jpg").exists()) {
					v.setDefaultimage(finalUrl + "1.jpg");
					storageState.putInfo("defaultimage", finalUrl + "1.jpg");
				}
				v.setLocalpath(finalUrl);
				v.setRealname(new File(filepath).getName());
				v.setVideoname(localname);
				v.setTranspath(saveurl);
				v.setVideosize(new File(filepath).length());
				v.setCreatedate(date);
				int timesize = VideoUtil.getDuration(filepath);
				v.setTimesize(timesize);
				v.setSiteid(siteid);

				if(issave){
					videoSourcesService.save(v);
				}
				storageState.putInfo("timesize", timesize);
				storageState.putInfo("createdate", new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(date));
			}
			break;
		}
	}
	
	/**
	 * 线程生成缩略图160*160
	 * 
	 * @param path 资源路径
	 * @param session 
	 * @param width 
	 * @param height
	 * @param thumbsuffix 生成缩略图后缀
	 * 
	 */
	private static void thumbThread(final String path, final HttpSession session, final int width, final int height,final String thumbsuffix) {
		Runnable th = new Runnable() {
			@Override
			public void run() {
				IThumbnailCreator thumbnailCreator = ThumbnailCreatorFactory.getCreator();
				thumbnailCreator.scaleRateImageFile(path, path + thumbsuffix, width, height);
				WaterMark(path + thumbsuffix, session);
			}
		};
		new Thread(th).start();
	}

	/**
	 * 打水印
	 * 
	 * @param file
	 *            要打水印的图片路径
	 * @param session
	 *            ContextHolderUtils.getSession()
	 * @param thumbnailCreator
	 */
	private static void WaterMark(final String file, final HttpSession session) {
		final Map<String, Object> m = PlatFormUtil.findWatermarkImageUrl(session);
		if (MapUtils.isNotEmpty(m)) {
			new Thread(new Runnable() {
				@Override
				public void run() {
					IThumbnailCreator thumbnailCreator = ThumbnailCreatorFactory.getCreator();
					thumbnailCreator.pressImage(file, m.get("imagePath").toString(), Integer.valueOf(m.get("position").toString()));
				}
			}).start();
		}
	}

	/**
	 * 生成视频预览图
	 * 
	 * @param videofilepath
	 * @return
	 */
	private static Boolean videoCaptureDefaultImage(String videofilepath) {
		try {
			VideoUtil.captureRandomImage(videofilepath);
			return true;
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
	}
	
}
