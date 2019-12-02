package com.leimingtech.core.util;

import java.io.File;
import java.io.IOException;
import java.util.Scanner;

/*import org.artofsolving.jodconverter.OfficeDocumentConverter;
import org.artofsolving.jodconverter.office.DefaultOfficeManagerConfiguration;
import org.artofsolving.jodconverter.office.OfficeManager;*/

import com.jacob.activeX.ActiveXComponent;
import com.jacob.com.ComThread;
import com.jacob.com.Dispatch;
import com.jacob.com.Variant;
import org.junit.Test;

public class OpenOfficePDFConverter  {
	
	/*private static  OfficeManager officeManager;*/
	private static String OFFICE_HOME = "D:/OpenOffice";
	private static int port[] = {8100};

	

	/*public  void convert2PDF(String inputFile, String pdfFile) {
	 
		 
		startService();
		System.out.println("进行文档转换转换:" + inputFile + " --> " + pdfFile);
        OfficeDocumentConverter converter = new OfficeDocumentConverter(officeManager);
        converter.convert(new File(inputFile),new File(pdfFile));
		stopService();
 
	}

	

	public void convert2PDF(String inputFile) {
		String pdfFile = FileUtil.getFilePrefix(inputFile)+".pdf";
		convert2PDF(inputFile,pdfFile);
		
	}
	
	public static void startService(){
		DefaultOfficeManagerConfiguration configuration = new DefaultOfficeManagerConfiguration();
        try {
          System.out.println("准备启动服务....");
            configuration.setOfficeHome(OFFICE_HOME);//设置OpenOffice.org安装目录
            configuration.setPortNumbers(port); //设置转换端口，默认为8100
            configuration.setTaskExecutionTimeout(1000 * 60 * 60L);//设置任务执行超时为5分钟
            configuration.setTaskQueueTimeout(1000 * 60 * 60 * 24L);//设置任务队列超时为24小时
         
            officeManager = configuration.buildOfficeManager();
            officeManager.start();	//启动服务
            System.out.println("office转换服务启动成功!");
        } catch (Exception ce) {
            System.out.println("office转换服务启动失败!详细信息:" + ce);
        }
	}
	
	public static void stopService(){
	      System.out.println("关闭office转换服务....");
	        if (officeManager != null) {
	            officeManager.stop();
	        }
	        System.out.println("关闭office转换成功!");
	}*/
	
	/*public static void main(String[] args) throws IOException{
		//OpenOfficePDFConverter pdfConverter= new OpenOfficePDFConverter(); 
		//String docFile = "D:/ppt/神经系统变性疾病.ppt";
		//pdfConverter.convert2PDF(docFile); 
		 
//		String commend ="D:/Program Files (x86)/Print2Flash3/print2flash.exe D:/pdf/1.ppt d:/pdf/12.swf";
//		Runtime.getRuntime().exec(commend);
		
		//word2PDF("D:/ppt/神经系统变性疾病.ppt", "D:/ppt/神经系统变性疾病.ppt");
	//	ppt2pdf("D:/ppt/神经系统变性疾病.ppt", "D:/ppt/神经系统变性疾病.pdf");
		File dir = new File("D:/work/projects/book/数据导入1/测试数据/ISBN 978-7-117-17059-8_预防医学/课件");
		File[] files = dir.listFiles();
		
		for(File file:files){
			if(file.isDirectory()) continue;
			String path  =file.getPath();
			String pdf  = FileUtil.getFilePrefix(path)+".pdf";
			OfficeConverter  converter1 = new OfficeConverter(path, pdf);
			converter1.start();
		}
		 
//		OfficeConverter  converter1 = new OfficeConverter("D:/ppt/神经系统变性疾病.ppt", "D:/ppt/神经系统变性疾病.pdf");
//		OfficeConverter  converter2 = new OfficeConverter("D:/ppt/01-预防医学绪论.ppt", "D:/ppt/01-预防医学绪论.pdf");
//		
//		converter1.start();
//		System.out.println("1");
//		converter2.start();
//		System.out.println("2");
		
	}*/
	
	public static void word2PDF(String inputFile,String pdfFile){
        //打开word应用程序
        ActiveXComponent app = new ActiveXComponent("Word.Application");
        //设置word不可见
        app.setProperty("Visible", new Variant(false));
        //获得word中所有打开的文档,返回Documents对象
        Dispatch docs = app.getProperty("Documents").toDispatch();
        //调用Documents对象中Open方法打开文档，并返回打开的文档对象Document
        Dispatch doc = Dispatch.call(docs,
                                    "Open",
                                    inputFile,
                                    false,
                                    true
                                    ).toDispatch();
        //调用Document对象的SaveAs方法，将文档保存为pdf格式
        /*
        Dispatch.call(doc,
                    "SaveAs",
                    pdfFile,
                    wdFormatPDF        //word保存为pdf格式宏，值为17
                    );
                    */
        
        
        Dispatch.call(doc,
                "ExportAsFixedFormat",
                pdfFile,
                17        //word保存为pdf格式宏，值为17
                );
        //关闭文档
        Dispatch.call(doc, "Close",false);
        //关闭word应用程序
        app.invoke("Quit", new Variant[] {});
        
    }
	
	public static boolean ppt2pdf(String srcFilePath, String pdfFilePath) {  
        ActiveXComponent app = null;  
        Dispatch ppt = null;  
            try {  
                ComThread.InitSTA();  
                app = new ActiveXComponent("PowerPoint.Application");  
                Dispatch ppts = app.getProperty("Presentations").toDispatch();  
  
                // 因POWER.EXE的发布规则为同步，所以设置为同步发布  
                ppt = Dispatch.call(ppts, "Open", srcFilePath, true,// ReadOnly  
                        true,// Untitled指定文件是否有标题  
                        false// WithWindow指定文件是否可见  
                        ).toDispatch();  
  
                Dispatch.call(ppt, "SaveAs", pdfFilePath, 32); //ppSaveAsPDF为特定值32  
  
                return true; // set flag true;  
            }   catch (Exception e) {  
                return false;  
            } finally {  
                if (ppt != null) {  
                    Dispatch.call(ppt, "Close");  
                }  
                if (app != null) {  
                    app.invoke("Quit");  
                }  
                ComThread.Release();  
            }  
    }  
	
	    public static void changeDoc(String filename, String htmlFilename) {
//	    	htmlFilename=htmlFilename+".htm";
	       ActiveXComponent xl = new ActiveXComponent("Word.Application");
	//打开一个word，不显示窗口      
	       try {
	           Dispatch.put(xl, "Visible", new Variant(false));
	           Object workbooks = xl.getProperty("Documents").toDispatch();
	           Object workbook = Dispatch.call((Dispatch) workbooks, "Open",
	                  filename).toDispatch();
	           Dispatch.invoke((Dispatch) workbook, "SaveAs", Dispatch.Method,
	                  new Object[] { htmlFilename, new Variant(8) }, new int[1]);
	           Variant f = new Variant(false);
	           //Close关闭文件，不关闭窗口
	           Dispatch.call((Dispatch) workbooks, "Close", f);
	       } catch (Exception e) {
	           e.printStackTrace();
	       } finally {
	           // 调用office关闭方法，关闭窗口和word进程         
	           xl.invoke("Quit", new Variant[] {});                 
	           xl = null;
	       }
	    }
	   
        public static void changeExcel(String filename, String htmlFilename) {
           ActiveXComponent xl = new ActiveXComponent("Excel.Application");
           try {
               Dispatch.put(xl, "Visible", new Variant(false));
    //打开一个Excel，不显示窗口
               Object workbooks = xl.getProperty("workbooks").toDispatch();
                            Object workbook = Dispatch.call((Dispatch) workbooks, "Open",
                      filename).toDispatch();
               Dispatch.invoke((Dispatch) workbook, "SaveAs", Dispatch.Method,
                      new Object[] { htmlFilename, new Variant(44) }, new int[1]);
               Dispatch.call((Dispatch) workbooks, "Close");
           } catch (Exception e) {
               e.printStackTrace();
           } finally {
               xl.invoke("Quit", new Variant[] {});
               xl = null;
               Process process;
               int pid = 0;
               try {
                  process = Runtime.getRuntime().exec("tasklist");
                  Scanner in = new Scanner(process.getInputStream());
                  while (in.hasNextLine()) {
                      String p = in.nextLine();
                      // 打印所有进程
//                      System.out.println(p);
                      if (p.contains("EXCEL.EXE")) {
                         StringBuffer buf = new StringBuffer();
                         for (int i = 0; i < p.length(); i++) {
                             char ch = p.charAt(i);
                             if (ch != ' ') {
                                buf.append(ch);
                             }
                         }
                         // 打印pid，根据pid关闭进程
//                         System.out.println(buf.toString().split("Console")[0]
//                                .substring("EXCEL.EXE".length()));
                         pid = Integer.parseInt(buf.toString().split("Console")[0]
                                .substring("EXCEL.EXE".length()));
//                         Runtime.getRuntime().exec("tskill"+" "+pid);
                      }
                  }
               } catch (IOException e) {
                  e.printStackTrace();
               }
           }
        }
       
        public static void changePpt(String filename, String htmlFilename) {
            ActiveXComponent xl = new ActiveXComponent("Powerpoint.Application");
            try {
                Dispatch.put(xl, "Visible", new Variant(true));
     //打开一个PPT，显示窗口，PPT的不显示就会报错，狂晕！
                Object workbooks = xl.getProperty("Presentations").toDispatch();
                Object workbook = Dispatch.call((Dispatch) workbooks, "Open",
                       filename).toDispatch();
                Dispatch.invoke((Dispatch) workbook, "SaveAs", Dispatch.Method,
                       new Object[] { htmlFilename, new Variant(20) }, new int[1]);
                //Variant f = new Variant(false);
                //Dispatch.call((Dispatch) workbooks, "Close",f);
                //PPT的加这两行会报错，干脆注释上，反正在下面也关闭进程
            } catch (Exception e) {
                e.printStackTrace();
            } finally {
                xl.invoke("Quit", new Variant[] {});
                xl = null;
                Process process;
                int pid = 0;
                try {
                   process = Runtime.getRuntime().exec("tasklist");
                   Scanner in = new Scanner(process.getInputStream());
                   while (in.hasNextLine()) {
                       String p = in.nextLine();
                       // 打印所有进程
//                       System.out.println(p);
                       if (p.contains("POWERPNT.EXE")) {
                          StringBuffer buf = new StringBuffer();
                          for (int i = 0; i < p.length(); i++) {
                              char ch = p.charAt(i);
                              if (ch != ' ') {
                                 buf.append(ch);
                              }
                          }
                          // 打印pid，根据pid关闭进程
//                          System.out.println(buf.toString().split("Console")[0]
//                                 .substring("POWERPNT.EXE".length()));
                          pid = Integer
                                 .parseInt(buf.toString().split("Console")[0]
                                        .substring("POWERPNT.EXE".length()));
//                          Runtime.getRuntime().exec("tskill" + " " + pid);
                       }
                   }
                } catch (IOException e) {
                   e.printStackTrace();
                }
            }
         }
        
        /*public static void changePpt2flash(String filename, String htmlFilename){
        	String commend =EopSetting.ISPRING_PATH+"/iSpringSDK.exe s "+filename+" "+htmlFilename;
	    	Runtime rt = Runtime.getRuntime();
	    	try {
				rt.exec(commend);
			} catch (IOException e) {
				e.printStackTrace();
			}
        }*/

    @Test
    public void test() {
//	    	String exePath = "/PPT2Flash/iSpringSDK.exe s ";
	       OpenOfficePDFConverter.changeDoc("d:/123.doc", "d:/ab.htm");
//	       OpenOfficePDFConverter.changeExcel("d:/abcd.xls", "d:/abcd.htm");
//	       OpenOfficePDFConverter.changePpt("d:/abc.ppt", "d:/abc.mht");
//	    	OpenOfficePDFConverter.changePpt2flash("d:/abc.ppt", "d:/abc.swf");
	    }
	 
}
