package com.leimingtech.cms.controller.special;

import com.leimingtech.cms.controller.contents.ContentsbaseController;
import com.leimingtech.core.entity.ContentCatEntity;
import com.leimingtech.core.entity.ContentsEntity;
import com.leimingtech.core.entity.ModelItemEntity;
import com.leimingtech.core.entity.SpecialEntity;
import com.leimingtech.core.service.ContentCatServiceI;
import com.leimingtech.core.service.ModelItemServiceI;
import com.leimingtech.core.util.PlatFormUtil;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**   
 * @Title: Controller
 * @Description: 专题
 * @author zhangdaihao modify by linjm 20140402
 * @date 2014-05-07 12:20:15
 * @version V1.0   
 *
 */
@Controller
@RequestMapping("/specialController")
public class SpecialController extends ContentsbaseController {

	@Autowired
	private ModelItemServiceI modelItemService;
	 /**
     * 栏目接口
     */
    @Autowired
    private ContentCatServiceI contentCatService;
	private String message;
	
	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}


    /**
     * 专题添加
     *
     * @return
     */
    @RequestMapping(params = "addPageModel")
    public ModelAndView addPageModel(String contentCatId, String modelsId, Model model) {
        //区分添加/编辑页面
        String flag = "add";
        ContentCatEntity contentCat = contentCatService.getEntity(contentCatId);

        if (!"-1".equals(contentCat.getJsonid())) {
            List<ModelItemEntity> modelItemList = modelItemService.findByModelId(contentCat.getJsonid());
            model.addAttribute("modelItemList", modelItemList);
        }
        //当前人
        String markpeople = "";
        if (PlatFormUtil.getSessionUser() != null) {
            markpeople = PlatFormUtil.getSessionUser().getUserName();
            if (StringUtils.isEmpty(markpeople)) {
                markpeople = PlatFormUtil.getSessionUser().getRealName();
            }
        }
        ContentsEntity contents = new ContentsEntity();
        // 设置文章分类(1文章2组图3链接4视频5活动6投票7访谈8调查9专题)
        contents.setClassify(com.leimingtech.cms.controller.contents.ContentClassify.CONTENT_SPECIAL);

        Map<String, Object> m = new HashMap<String, Object>();
        m.put("special", new SpecialEntity());
        m.put("contents", contents);
        m.put("contentCat", contentCat);
        m.put("modelsId", modelsId);
        m.put("flag", flag);
        m.put("markpeople", markpeople);
        m.put("memberinfo", PlatFormUtil.getSessionUser());
        return new ModelAndView("cms/special/special_open", m);
    }
}
