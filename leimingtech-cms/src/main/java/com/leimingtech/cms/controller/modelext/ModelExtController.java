package com.leimingtech.cms.controller.modelext;
import java.io.UnsupportedEncodingException;
import java.util.Date;

import javax.servlet.http.HttpServletRequest;

import net.sf.json.JSONObject;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.leimingtech.core.common.Globals;
import com.leimingtech.core.controller.BaseController;
import com.leimingtech.core.entity.ModelExtEntity;
import com.leimingtech.core.service.SystemService;
import com.leimingtech.core.service.modelext.ModelExtServiceI;


/**   
 * @Title: Controller
 * @Description: 选项内容
 * @author zhangdaihao modify by linjm 20140402
 * @date 2014-04-16 09:57:58
 * @version V1.0   
 *
 */
@Controller
@RequestMapping("/modelExtController")
public class ModelExtController extends BaseController {

	@Autowired
	private ModelExtServiceI modelExtService;
	@Autowired
	private SystemService systemService;
	private String message;
	
	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	/**
	 * 选项内容保存
	 * 
	 * @return
	 * @throws UnsupportedEncodingException 
	 */
	@RequestMapping(params = "save")
	@ResponseBody
	public String saveOrUpdate(HttpServletRequest request) throws UnsupportedEncodingException {
		String contentsId=request.getParameter("contentsId");
		String[] modelListId=request.getParameterValues("extid");
		JSONObject j = new JSONObject();
		boolean isSuccess=false;
		if (modelListId!=null) {
			String[] attrvalueList=request.getParameterValues("attrvalue");
			String[] dataTypeList=request.getParameterValues("dataType");
			String[] attrNameList=request.getParameterValues("attrName");
			String[] displayList=request.getParameterValues("display");
			String[] modelidList=request.getParameterValues("modelid");
			String[] modelItemIdList=request.getParameterValues("modelItemId");		
			for(int i=0;i<modelItemIdList.length;i++){
				if(attrvalueList[i]==null){
					attrvalueList[i]="";
				}
			}
			try {
				for(int i=0;i<modelListId.length;i++){
					if(!modelListId[i].equals("-1")){
				        ModelExtEntity modelExt = modelExtService.getEntity(modelListId[i]);
				        modelExt.setAttrValue(new String(attrvalueList[i].getBytes("ISO-8859-1"),"UTF-8"));
				        modelExtService.saveOrUpdate(modelExt);
					}else{
						ModelExtEntity modelExt =new ModelExtEntity();
						modelExt.setAttrValue(new String(attrvalueList[i].getBytes("ISO-8859-1"),"UTF-8"));
						modelExt.setDataType(Integer.parseInt(dataTypeList[i]));
						modelExt.setAttrName(new String(attrNameList[i].getBytes("ISO-8859-1"),"UTF-8"));
						modelExt.setModelItemId(modelItemIdList[i]);
						modelExt.setCreatedtime(new Date());//创建时间
						modelExt.setContentId(contentsId);
						modelExt.setAttrToken(displayList[i]);
						modelExt.setModelId(modelidList[i]);
						modelExtService.save(modelExt);
					}
				
				}
				message = "选项内容修改成功";
				systemService.addLog(message, Globals.Log_Leavel_INFO, Globals.Log_Type_UPDATE);
				isSuccess=true;
			} catch (Exception e) {
				e.printStackTrace();
			}
		}else{
			String[] attrvalueList=request.getParameterValues("attrvalue");
			String[] dataTypeList=request.getParameterValues("dataType");
			String[] attrNameList=request.getParameterValues("attrName");
			String[] displayList=request.getParameterValues("display");
			String[] modelItemIdList=request.getParameterValues("modelItemId");			
			//替换掉空值
			if(modelItemIdList!=null){
				for(int i=0;i<modelItemIdList.length;i++){
					if(attrvalueList[i]==null){
						attrvalueList[i]="";
					}
				}
				
				for(int i=0;i<modelItemIdList.length;i++){
					ModelExtEntity modelExts=new ModelExtEntity();
					modelExts.setAttrValue(new String(attrvalueList[i].getBytes("ISO-8859-1"),"UTF-8"));
					modelExts.setDataType(Integer.parseInt(dataTypeList[i]));
					modelExts.setAttrName(new String(attrNameList[i].getBytes("ISO-8859-1"),"UTF-8"));
					modelExts.setModelItemId(modelItemIdList[i]);
					modelExts.setCreatedtime(new Date());//创建时间
					modelExts.setContentId(contentsId);
					modelExts.setAttrToken(displayList[i]);
					modelExtService.save(modelExts);
					message = "选项内容【"+modelExts.getAttrName()+"】添加成功";
					systemService.addLog(message, Globals.Log_Leavel_INFO, Globals.Log_Type_INSERT);
				}
			}
			
			isSuccess=true;
		}
		j.accumulate("isSuccess", isSuccess);
		String str = j.toString();
		return str;
	}
	
}
