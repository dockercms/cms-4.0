package com.leimingtech.platform.servlet;

import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.DataInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.PrintWriter;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import javax.imageio.ImageIO;
import javax.servlet.ServletException;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.FileUploadException;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;
import org.imgscalr.Scalr;

import com.leimingtech.core.util.FileUtils;
import com.leimingtech.core.util.SystemPath;
/**
 * 上传图片
 * 
 * @author linjm linjianmao@gmail.com <br/>
 *         2014-3-31 下午11:00:00
 * 
 */
public class UploadServlet extends HttpServlet {

	private static final long serialVersionUID = 1L;

	private File fileUploadPath;
    
    private String url = null;

//    @Override
//    public void init(ServletConfig config) {
////        fileUploadPath = new File(config.getInitParameter("upload_path"));
//    	SimpleDateFormat t = new SimpleDateFormat("yyyyMMdd");
//    	url = "upload/img/"+ t.format(new Date())+ "/";
//    	File file = new File(SystemPath.getSysPath()+ url);
//    	if(!file.exists()){
//    		file.mkdirs();
//    	}
//        fileUploadPath = file;
//    }
        
    /**
    * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
    * 
    */
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

    	fileUploadPath = initFile();
        if (request.getParameter("getfile") != null 
                && !request.getParameter("getfile").isEmpty()) {
            File file = new File(fileUploadPath.toString(),
                    request.getParameter("getfile"));
            if (file.exists()) {
                int bytes = 0;
                ServletOutputStream op = response.getOutputStream();

                response.setContentType(FileUtils.getMimeType(file));
                response.setContentLength((int) file.length());
                response.setHeader( "Content-Disposition", "inline; filename=\"" + file.getName() + "\"" );

                byte[] bbuf = new byte[1024];
                DataInputStream in = new DataInputStream(new FileInputStream(file));

                while ((in != null) && ((bytes = in.read(bbuf)) != -1)) {
                    op.write(bbuf, 0, bytes);
                }

                in.close();
                op.flush();
                op.close();
            }
        } else if (request.getParameter("delfile") != null && !request.getParameter("delfile").isEmpty()) {
            File file = new File(fileUploadPath.toString(), request.getParameter("delfile"));
            if (file.exists()) {
                file.delete(); 
            } 
        } else if (request.getParameter("getthumb") != null && !request.getParameter("getthumb").isEmpty()) {
            File file = new File(SystemPath.getSysPath()+request.getParameter("getthumb"));
                if (file.exists()) {
                    String mimetype = FileUtils.getMimeType(file);
                    if (mimetype.endsWith("png") || mimetype.endsWith("jpeg") || mimetype.endsWith("gif")) {
                        BufferedImage im = ImageIO.read(file);
                        if (im != null) {
                            BufferedImage thumb = Scalr.resize(im, 75); 
                            ByteArrayOutputStream os = new ByteArrayOutputStream();
                            if (mimetype.endsWith("png")) {
                                ImageIO.write(thumb, "PNG" , os);
                                response.setContentType("image/png");
                            } else if (mimetype.endsWith("jpeg")) {
                                ImageIO.write(thumb, "jpg" , os);
                                response.setContentType("image/jpeg");
                            } else {
                                ImageIO.write(thumb, "GIF" , os);
                                response.setContentType("image/gif");
                            }
                            ServletOutputStream srvos = response.getOutputStream();
                            response.setContentLength(os.size());
                            response.setHeader( "Content-Disposition", "inline; filename=\"" + file.getName() + "\"" );
                            os.writeTo(srvos);
                            srvos.flush();
                            srvos.close();
                        }
                    }
            } 
        } else {
            PrintWriter writer = response.getWriter();
            writer.write("call POST with multipart form data");
        }
    }
    
    /**
    * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
    * 
    */
    @SuppressWarnings("unchecked")
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
    	fileUploadPath = initFile();//初始化文件
        if (!ServletFileUpload.isMultipartContent(request)) {
            throw new IllegalArgumentException("Request is not multipart, please 'multipart/form-data' enctype for your form.");
        }

        ServletFileUpload uploadHandler = new ServletFileUpload(new DiskFileItemFactory());
        PrintWriter writer = response.getWriter();
        //response.setContentType("application/json");//这个类型IE10以下会之直接下载
        response.setContentType("text/html");//解决IE10以下直接下载JSON对象问题
        JSONArray json = new JSONArray();
        try {
            List<FileItem> items = uploadHandler.parseRequest(request);
            for (FileItem item : items) {
            	//没重命名文件
//                if (!item.isFormField()) {
//                        File file = new File(fileUploadPath, item.getName());
//                        item.write(file);
//                        JSONObject jsono = new JSONObject();
//                        jsono.put("name", item.getName());
//                        jsono.put("size", item.getSize());
//                        jsono.put("url", "uploadimg?getfile=" + item.getName());
//                        jsono.put("thumbnailUrl", "uploadimg?getthumb=" + item.getName());
//                        jsono.put("deleteTrl", "uploadimg?delfile=" + item.getName());
//                        jsono.put("deleteType", "GET");
//                        json.add(jsono);
//                }
            	//重名文件方法
            	if (!item.isFormField()) {
//            		String suffix = item.getName().substring(item.getName().lastIndexOf("."));
             		long currTime = System.currentTimeMillis();
             		String fileName = currTime+"."+FileUtils.getSuffix(item.getName());//文件重命名
             		File file = new File(fileUploadPath, fileName);
                    item.write(file);//输出文件
                    JSONObject jsono = new JSONObject();
                    jsono.put("name", item.getName());
                    jsono.put("size", item.getSize());
//                    jsono.put("url", "uploadimg?getfile=/" + fileName);
                    jsono.put("url", url + fileName);
                    jsono.put("thumbnailUrl", "uploadimg?getthumb=/" + url + fileName);
                    jsono.put("deleteUrl", "uploadimg?delfile=/" + fileName);
                    jsono.put("deleteType", "GET");
                    json.add(jsono);
            	}
            }
        } catch (FileUploadException e) {
                throw new RuntimeException(e);
        } catch (Exception e) {
                throw new RuntimeException(e);
        } finally {
            writer.write("{\"files\":"+json.toString()+"}");
            writer.close();
        }

    }
    
    private File initFile(){
    	SimpleDateFormat t = new SimpleDateFormat("yyyyMMdd");
    	url = "upload/myfile/"+ t.format(new Date())+ "/";
    	File file = new File(SystemPath.getSysPath()+ url);
    	if(!file.exists()){
    		file.mkdirs();
    	}
    	return file;
    }
    
}