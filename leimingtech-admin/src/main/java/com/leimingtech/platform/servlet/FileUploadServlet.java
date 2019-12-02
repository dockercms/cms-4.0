package com.leimingtech.platform.servlet;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.FileItemFactory;
import org.apache.commons.fileupload.FileUploadException;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;
import org.apache.commons.lang.StringUtils;

import com.leimingtech.core.base.ClientManager;
import com.leimingtech.core.base.ContextHolderUtils;
import com.leimingtech.core.entity.Client;
import com.leimingtech.core.entity.SiteEntity;
import com.leimingtech.core.util.SystemPath;
import com.leimingtech.core.util.image.IThumbnailCreator;
import com.leimingtech.core.util.image.ThumbnailCreatorFactory;

public class FileUploadServlet extends HttpServlet {
	
	
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		this.doPost(request, response);
	}
	
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		request.setCharacterEncoding("UTF-8");
		String uploadType = request.getParameter("uploadType");
		if(StringUtils.isNotEmpty(uploadType)&&uploadType.equals("judge")){
			judgeUpload(request, response);
		}else{
			StandardUpload(request, response);
		}
		
	}
	
	
	private void StandardUpload(HttpServletRequest request,
			HttpServletResponse response) throws IOException {
		HttpSession session = ContextHolderUtils.getSession();
		String sessionId = request.getParameter("PHPSESSID");
    	Client client = ClientManager.getInstance().getClient(sessionId);
    	SiteEntity site = client.getSite();
		SimpleDateFormat t = new SimpleDateFormat("yyyyMMdd");
		String syspath = SystemPath.getSysPath();
		String path = request.getParameter("filePath");
		String flag = request.getParameter("flag");
//		//文件路径按作业/ID/date/文件名加时间戳   保存
//		String customPath = "upload/myfile/"+ t.format(new Date())+ "/";
//		if(StringUtils.isNotEmpty(path)){
//			//自定义文件路径   File.separatorChar
//			customPath = "upload/"+path+"/"+ t.format(new Date())+ "/";
//		}
		String customPath = "wwwroot/"+site.getSitePath()+"/imgfile/"+t.format(new Date())+"/";
		if(StringUtils.isNotEmpty(path)){
			//自定义文件路径   File.separatorChar
			customPath = "wwwroot/"+site.getSitePath()+"/"+path+"/";
		}
		String savePath = syspath+customPath;
		savePath =savePath.replace("/", "\\");
		String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+"/";
		FileItemFactory fileFactory = new DiskFileItemFactory();
		ServletFileUpload fu = new ServletFileUpload(fileFactory);
		List<FileItem> fileItems =new ArrayList<FileItem>();
		try {
			fileItems = fu.parseRequest(request);
		} catch (FileUploadException e1) {
			e1.printStackTrace();
			System.out.println("上传文件失败");
			response.getWriter().write("0|0|0|0");
			return;
		}
		fu.setHeaderEncoding("UTF-8");
		Iterator iter = fileItems.iterator();
		String oldFileName ="";
		long currTime = System.currentTimeMillis();
		while (iter.hasNext()){
			FileItem item = (FileItem) iter.next();
			if (!item.isFormField()){
				oldFileName = item.getName();
				long size = item.getSize();
				if((oldFileName==null||oldFileName.equals("")) && size==0){
					continue;
				}
				String typeName = "";
				if(oldFileName.contains(".")){
					 typeName = oldFileName.substring(oldFileName.lastIndexOf("."));
				}
				File file = new File(savePath+currTime+typeName);
				if(!file.getParentFile().exists()){
					file.getParentFile().mkdirs();
				}try {
					item.write(file);
					if(typeName.toLowerCase().lastIndexOf("png")>-1 || 
						 typeName.toLowerCase().lastIndexOf("jpeg")>-1 ||
						 typeName.toLowerCase().lastIndexOf("gif")>-1 ||
						 typeName.toLowerCase().lastIndexOf("jpg")>-1){
						 // 系统缩略图，自动生成
						 thumbThread(savePath+currTime+typeName);
					}
				} catch (Exception e) {
					e.printStackTrace();
					System.out.println("上传文件失败");
					response.getWriter().write("0|0|0|0|0");
					return;
				}
			}
		}
		System.out.println("上传成功");
		String backValue="1|"+basePath+customPath+"|"+savePath+"|"+currTime+"|0";
		if(StringUtils.isNotEmpty(flag)){
			backValue="1|"+basePath+customPath+"|"+savePath+"|"+currTime+"|"+flag;
		}
		response.getWriter().write(backValue);
	}

	private void thumbThread(final String path) {
		System.out.println("生成小图开始");
		Runnable th = new Runnable() {
			@Override
			public void run() {
				 IThumbnailCreator thumbnailCreator = ThumbnailCreatorFactory.getCreator();
		 		 thumbnailCreator.scaleRateImageFile(path , path+".thumb.jpg", 160, 160);
			}
		};
		new Thread(th).start();
	}
	
	
	private void judgeUpload(HttpServletRequest request,HttpServletResponse response) throws IOException {
		SimpleDateFormat t = new SimpleDateFormat("yyyyMMdd");
		String syspath = SystemPath.getSysPath();
		String judgeID = request.getParameter("judgeID");
		String userID = request.getParameter("userID");
		//文件路径按作业/ID/date/文件名加时间戳   保存
		String customPath = "/upload/task/"+ t.format(new Date())+ "/";
		String savePath = syspath+customPath;
		String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+"/";
		FileItemFactory fileFactory = new DiskFileItemFactory();
		ServletFileUpload fu = new ServletFileUpload(fileFactory);
		List fileItems =new ArrayList();
		try {
			fileItems = fu.parseRequest(request);
		} catch (FileUploadException e1) {
			e1.printStackTrace();
		}
		fu.setHeaderEncoding("UTF-8");
		Iterator iter = fileItems.iterator();
		String oldFileName ="";
		long currTime = System.currentTimeMillis();
		while (iter.hasNext()){
			FileItem item = (FileItem) iter.next();
			if (!item.isFormField()){
				oldFileName = item.getName();
				long size = item.getSize();
				if((oldFileName==null||oldFileName.equals("")) && size==0){
					continue;
				}
				String typeName ="";
				if(oldFileName.contains(".")){
					 typeName = oldFileName.substring(oldFileName.lastIndexOf("."));
				}
				File file= new File(savePath+currTime+typeName);
				if(!file.getParentFile().exists()){
					file.getParentFile().mkdirs();
				}try {
					item.write(file);
					System.out.println("id="+judgeID+"----path==="+customPath+currTime+typeName);
					String value=customPath+currTime+typeName;
//					insert(judgeID,value,userID);
//					String insertSql = "INSERT INTO TASK_JudgeAttach (relaid,img1) VALUES(?,?)";
//					judgeAttachService.executeSql(insertSql, judgeID,"'"+value+"'");
				} catch (Exception e) {
					e.printStackTrace();
					System.out.println("上传文件失败");
					response.getWriter().write("0|0|0|0");
					return;
				}
			}
		}
		System.out.println("judge上传成功");
		String backValue="1|"+basePath+customPath+"|"+savePath+"|"+currTime;
		response.getWriter().write(backValue);
	}
}
