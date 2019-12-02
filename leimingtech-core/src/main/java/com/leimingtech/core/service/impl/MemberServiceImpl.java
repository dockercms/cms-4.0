package com.leimingtech.core.service.impl;

import com.leimingtech.core.base.ClientManager;
import com.leimingtech.core.base.ContextHolderUtils;
import com.leimingtech.core.base.SortDirection;
import com.leimingtech.core.common.CmsConstants;
import com.leimingtech.core.entity.BeanApi;
import com.leimingtech.core.entity.Client;
import com.leimingtech.core.entity.SiteEntity;
import com.leimingtech.core.entity.member.MemberEntity;
import com.leimingtech.core.entity.member.MemberSimpleVOEntity;
import com.leimingtech.core.hibernate.qbc.CriteriaQuery;
import com.leimingtech.core.hibernate.qbc.PageList;
import com.leimingtech.core.service.CommonService;
import com.leimingtech.core.service.MemberDepartServiceI;
import com.leimingtech.core.service.MemberServiceI;
import com.leimingtech.core.service.SiteServiceI;
import com.leimingtech.core.util.*;
import com.leimingtech.core.util.image.ImageJDKUtil;
import net.sf.json.JSONObject;
import net.sf.json.JsonConfig;
import net.sf.json.util.PropertyFilter;
import org.apache.commons.lang.RandomStringUtils;
import org.apache.commons.lang.StringUtils;
import org.hibernate.Query;
import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.MatchMode;
import org.hibernate.criterion.ProjectionList;
import org.hibernate.criterion.Projections;
import org.hibernate.transform.Transformers;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URLDecoder;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

/**
 * 会员管理接口实现
 * 
 * @author liuzhen 2014年5月20日 16:42:32
 * 
 */

@Service("memberService")
@Transactional
public class MemberServiceImpl extends CommonServiceImpl implements MemberServiceI {
	
	/**站点管理接口*/
	@Autowired
	private SiteServiceI siteService;
	/**会员与部门关联接口*/
	@Autowired
	private MemberDepartServiceI memberDepartService;
	@Autowired
	private CommonService commonService;
	
	public void delMain(MemberEntity member) {
		// 删除字表信息
		this.delete(member);
	}

	/**
	 * 验证用户是否存在
	 */
	@Override
	public MemberEntity checkUserExits(MemberEntity member) {
		MemberEntity newM = null;
		String query = "from MemberEntity u where u.username = :username and u.password=:password";
		Query queryObject = getSession().createQuery(query);
		queryObject.setParameter("username", member.getUsername());
		queryObject.setParameter("password", PasswordUtil.encrypt(member.getUsername(), member.getPassword(), PasswordUtil.getStaticSalt()));
		List<MemberEntity> users = queryObject.list();
		if (users != null && users.size() > 0) {
			newM = users.get(0);
		}
		return newM;
	}

	/**
	 * 验证用户是否存在
	 */
	@Override
	public Boolean checkUserExits(String username) {
		List<MemberEntity> memberlist = findByProperty(MemberEntity.class, "username", username);
		if (memberlist != null && memberlist.size() > 0) {
			return true;
		}
		return false;
	}

	/**
	 * 获取session中的会员（默认session中的会员只有会员id和会员名）
	 * 
	 * @return
	 */
	@Override
	public MemberEntity getSessionMember() {
		HttpSession session = ContextHolderUtils.getSession();
		ClientManager clientMng = ClientManager.getInstance();
		Client client = clientMng.getClient("front" + session.getId());
		MemberEntity member = null;
		if (client != null) {
			member = clientMng.getClient("front" + session.getId()).getMember();
		}
		if (member != null) {
			return getEntity(MemberEntity.class, member.getId());
		}
		return null;
	}

	/**
	 * 获取站点路径
	 * 
	 * @return
	 */
	@Override
	public String getSitePath() {
		HttpServletRequest reqeust = ContextHolderUtils.getRequest();
		String siteid = reqeust.getParameter("siteid");
		SiteEntity site = null;

		HttpSession session = ContextHolderUtils.getSession();
		ClientManager clientMng = ClientManager.getInstance();
		Client client = clientMng.getClient(session.getId());

		if (StringUtils.isNotEmpty(siteid) && !StringUtils.isNumeric(siteid)) {
			site = getEntity(SiteEntity.class, String.valueOf(siteid));
			if (site == null) {
				site = (SiteEntity) getList(SiteEntity.class).get(0);
			}

			if (client == null) {
				client = new Client();
			}
			client.setSite(site);
			clientMng.addClinet(session.getId(), client);
		} else {
			if (client != null && client.getSite() != null) {
				site = client.getSite();
			} else {
				site = (SiteEntity) getList(SiteEntity.class).get(0);
				if (client == null) {
					client = new Client();
				}
				client.setSite(site);
				clientMng.addClinet(session.getId(), client);
			}
		}
		return site.getSitePath();
	}

	/**
	 * 移动客户端修改编辑头像
	 */
	@Override
	public JSONObject editData(String all, MultipartFile portrait,String userId) {
		JSONObject json = new JSONObject();
		BeanApi beanApi = new BeanApi();
		
		try {
			if (StringUtil.isNotEmpty(all)) {
				beanApi.setResultCode("1");
				beanApi.setResultMsg("修改成功");
				beanApi.setUserId("1");
				beanApi.setUserName("mac");
				beanApi.setFaceImg("/upload/xxx.png");
				beanApi.setItems(null);
				json = json.fromObject(beanApi,getJsonConfig());
			} else {
				if(StringUtil.isNotEmpty(userId)){
					MemberEntity member = get(MemberEntity.class, String.valueOf(userId));
					if (member != null) {
						//修改头像
						if(StringUtil.isNotEmpty(portrait)){
							member.setFaceImg(getPath(portrait));
							saveOrUpdate(member);
							beanApi.setResultMsg("上传成功");
							beanApi.setResultCode("1");
							beanApi.setUserId(String.valueOf(member.getId()));
							beanApi.setUserName(member.getRealname());
							beanApi.setFaceImg(member.getFaceImg());
							beanApi.setItems(null);
							json = json.fromObject(beanApi,getJsonConfig());
						}
					} else {
						beanApi.setResultMsg("找不到用户，请重新登录后编辑");
						beanApi.setResultCode("0");
						beanApi.setItems(null);
						json = json.fromObject(beanApi,getJsonConfig());
					}
				}else{
					beanApi.setResultMsg("未登录，请登录后编辑");
					beanApi.setResultCode("0");
					beanApi.setItems(null);
					json = json.fromObject(beanApi,getJsonConfig());
				}
			}
		} catch (Exception e1) {
			e1.printStackTrace();
			beanApi.setResultMsg("服务器繁忙，稍后重试！");
			beanApi.setResultCode("0");
			beanApi.setItems(null);
			json = json.fromObject(beanApi,getJsonConfig());
		}
		return json;
	}
	/**
	 * APP上传图片进行处理
	 * @param portrait MultipartFile
	 * @return  图片路径
	 */
	public String getPath(MultipartFile portrait){
		SimpleDateFormat t = new SimpleDateFormat("yyyyMMdd");
		HttpServletRequest request = ContextHolderUtils.getRequest();
		SiteEntity site = get(SiteEntity.class, 1);
        String sitepath =CmsConstants.getSitePath(site);//wwwwroot/www
        String path = "/upload/image/"+t.format(new Date());//数据库保存的路径
		String customPath = sitepath+path;
//		String path = request.getSession().getServletContext().getRealPath(customPath);
//		FileInputStream file = new FileInputStream(portrait);
		FileUtils.createFolder(customPath);
		String facePath = "";
		try {
			// 得到上传的文件的文件名
			String filename = portrait.getOriginalFilename();
			InputStream inputStream = portrait.getInputStream();
			byte[] b = new byte[1048576];
			int length = 0;
			// 文件流写到服务器端
			FileOutputStream outputStream = new FileOutputStream(customPath +"/"+filename+".jpg");//固定后缀是jpg
			while((length=inputStream.read(b))!=-1){
				outputStream.write(b, 0, length);
				outputStream.flush();
			 }
			inputStream.close();
			outputStream.close();
			facePath = path+"/"+filename+".jpg";
		} catch (Exception e) {
			e.printStackTrace();
			System.out.println(e.getMessage());
		}
		return facePath;
	}
	/**
	 * 移动客户端修改编辑用户名
	 */
	public JSONObject editUserName(String all,String userId,String userName){
		JSONObject json = new JSONObject();
		BeanApi beanApi = new BeanApi();
		try {
			if (StringUtil.isNotEmpty(all)) {
				beanApi.setResultCode("1");
				beanApi.setResultMsg("修改成功");
				beanApi.setUserId("1");
				beanApi.setUserName("mac");
				beanApi.setFaceImg("/upload/xxx.png");
				beanApi.setItems(null);
				json = json.fromObject(beanApi,getJsonConfig());
			} else {
				if(StringUtil.isNotEmpty(userId)){
					MemberEntity member = get(MemberEntity.class, String.valueOf(userId));
					if (member != null) {
						//修改用户名RealName
						if(StringUtil.isNotEmpty(userName)){
							userName = URLDecoder.decode(userName,"utf-8");
							member.setRealname(userName);
							saveOrUpdate(member);
							beanApi.setResultMsg("编辑成功");
							beanApi.setResultCode("1");
							beanApi.setUserId(String.valueOf(member.getId()));
							beanApi.setUserName(member.getRealname());
							beanApi.setFaceImg(member.getFaceImg());
							beanApi.setItems(null);
							json = json.fromObject(beanApi,getJsonConfig());
						}
					} else {
						beanApi.setResultMsg("找不到用户，请重新登录后编辑");
						beanApi.setResultCode("0");
						beanApi.setItems(null);
						json = json.fromObject(beanApi,getJsonConfig());
					}
				}else{
					beanApi.setResultMsg("未登录，请登录后编辑");
					beanApi.setResultCode("0");
					beanApi.setItems(null);
					json = json.fromObject(beanApi,getJsonConfig());
				}
			}
		} catch (Exception e1) {
			e1.printStackTrace();
			beanApi.setResultMsg("服务器繁忙，稍后重试！");
			beanApi.setResultCode("0");
			beanApi.setItems(null);
			json = json.fromObject(beanApi,getJsonConfig());
		}
		return json;
	}

	/**
	 * 移动客户端修改编辑用户名
	 */
	public JSONObject editPwd(String all,String userId,String password){
		JSONObject json = new JSONObject();
		BeanApi beanApi = new BeanApi();
		try {
			if (StringUtil.isNotEmpty(all)) {
				beanApi.setResultCode("1");
				beanApi.setResultMsg("修改成功");
				beanApi.setUserId("1");
				beanApi.setUserName("mac");
				beanApi.setFaceImg("/upload/xxx.png");
				beanApi.setItems(null);
				json = json.fromObject(beanApi,getJsonConfig());
			} else {
				if(StringUtil.isNotEmpty(userId)){
					MemberEntity member = get(MemberEntity.class, String.valueOf(userId));
					if (member != null) {
						//修改密码
						if(StringUtil.isNotEmpty(password)){
							String pwd = PasswordUtil.encrypt(member.getUsername(), password, PasswordUtil.getStaticSalt());
							member.setPassword(pwd);
							saveOrUpdate(member);
							beanApi.setResultMsg("修改成功");
							beanApi.setResultCode("1");
							beanApi.setUserId(String.valueOf(member.getId()));
							beanApi.setUserName(member.getRealname());
							beanApi.setFaceImg(member.getFaceImg());
							beanApi.setItems(null);
							json = json.fromObject(beanApi,getJsonConfig());
						}
					} else {
						beanApi.setResultMsg("找不到用户，请重新登录后编辑");
						beanApi.setResultCode("0");
						beanApi.setItems(null);
						json = json.fromObject(beanApi,getJsonConfig());
					}
				}else{
					beanApi.setResultMsg("未登录，请登录后编辑");
					beanApi.setResultCode("0");
					beanApi.setItems(null);
					json = json.fromObject(beanApi,getJsonConfig());
				}
			}
		} catch (Exception e1) {
			e1.printStackTrace();
			beanApi.setResultMsg("服务器繁忙，稍后重试！");
			beanApi.setResultCode("0");
			beanApi.setItems(null);
			json = json.fromObject(beanApi,getJsonConfig());
		}
		return json;
	}
	/**
	 * 设置过滤值为空的属性，使得生成的 json 字符串只包含非空的值
	 * @return
	 */
	public JsonConfig getJsonConfig(){
		JsonConfig jsonConfig = new JsonConfig();
		jsonConfig.setJsonPropertyFilter(new PropertyFilter(){
			@Override
            public boolean apply(Object source, String name, Object value) {
                return value == null;
            }
		});
		return jsonConfig;
	}
	/**
	 * 验证用户email是否已绑定
	 * 
	 * @param email
	 * @return
	 */
	@Override
	public Boolean checkUserEmail(String email) {
		List<MemberEntity> memberlist = findByProperty(MemberEntity.class, "email", email);
		if (memberlist != null && memberlist.size() > 0) {
			return true;
		}
		return false;
	}
	
	/**
	 * 验证用户名与邮箱是否为同一个用户的
	 * @param username
	 * @param email
	 * @return
	 */
	public Boolean checkNameAndEmail(String username, String email) {
		List<MemberEntity> memberlist = findByProperty(MemberEntity.class, "username", username);
		if(memberlist != null && memberlist.size() > 0){
			MemberEntity member = memberlist.get(0);
			String memberEmail = member.getEmail();
			if(memberEmail.equals(email)){
				return true;
			}else{
				return false;
			}
		}
		return false;
	}
	
	/**
	 * 根据用户名获取用户
	 * @param username
	 * @return
	 */
	public MemberEntity getMemberByUsername(String username) {
		List<MemberEntity> memberlist = findByProperty(MemberEntity.class, "username", username);
		if(memberlist != null && memberlist.size() > 0){
			MemberEntity member = memberlist.get(0);
			return member;
		}
		return null;
	}

	@Override
	public String yearSqlMember(int value, String month, String staticsType,String dbType) {
		String sql = "select count(id) count from cms_member ";
		if("0".equals(month)){
			if("mysql".equals(dbType)){
				sql += "where date_format(createtime,'%Y')='"+value+"'";
			}
			if("sqlserver".equals(dbType)){
				sql += "where year(createtime)='"+value+"'";
			}
			if("oracle".equals(dbType)){
				sql += "where to_char(createtime,'yyyy')='"+value+"'";
			}
		}else{
			if("mysql".equals(dbType)){
				sql += "where date_format(createtime,'%Y-%m')= '"+value+"-"+month+"' ";
			}
			if("sqlserver".equals(dbType)){
				sql += "where year(createtime)='"+value+"' and month(createtime)='"+month+"'";
			}
			if("oracle".equals(dbType)){
				sql += "where to_char(createtime,'yyyy-MM')='"+value+"-"+month+"' ";
			}
		}
		return sql;
	}

	@Override
	public String monthSqlMember(String year, String month, String staticsType,int i, String dbType) {
		String sql = "select count(id) count from cms_member ";
		if("sqlserver".equals(dbType)){
			sql += "where year(createtime)='"+year+"' and month(createtime)='"+i+"' ";
		}
		if(i<10){
			if("mysql".equals(dbType)){
				sql += "where date_format(createtime,'%Y-%m')='"+year+"-0"+i+"' ";
			}
			if("oracle".equals(dbType)){
				sql += "where to_char(createtime,'yyyy-MM')='"+year+"-0"+i+"' ";
			}
		}else{
			if("mysql".equals(dbType)){
				sql += "where date_format(createtime,'%Y-%m')='"+year+"-"+i+"' ";
			}
			if("oracle".equals(dbType)){
				sql += "where to_char(createtime,'yyyy-MM')='"+year+"-"+i+"' ";
			}
		}
		return sql;
	}

	@Override
	public String daySqlMember(String year, String month, String staticsType,int i, String dbType) {
		String sql = "select count(id) count from cms_member ";
		if("sqlserver".equals(dbType)){
			sql += "where CONVERT(varchar(100), createtime, 23)='"+year+"-"+month+"-"+i+"' ";
		}
		if(i<10){
			if("mysql".equals(dbType)){
				sql += "where date_format(createtime,'%Y-%m-%d')= '"+year+"-"+month+"-0"+i+"' ";
			}
			if("oracle".equals(dbType)){
				sql += "where to_char(createtime,'yyyy-MM-dd')='"+year+"-"+month+"-0"+i+"' ";
			}
		}else{
			if("mysql".equals(dbType)){
				sql += "where date_format(createtime,'%Y-%m-%d')= '"+year+"-"+month+"-"+i+"' ";
			}
			if("oracle".equals(dbType)){
				sql += "where to_char(createtime,'yyyy-MM-dd')='"+year+"-"+month+"-"+i+"' ";
			}
		}
		return sql;
	}

	@Override
	public MemberEntity getMember(String userId) {
		MemberEntity member = get(MemberEntity.class, userId);
		return member;
	}
	
	/**
	 * 生成会员卡图
	 * 
	 * @param member
	 * @throws IOException
	 */
	@Override
	public void createMemberCard(MemberEntity member) throws IOException {
		SiteEntity siteEntity = siteService.getSite("1");// FIXME 2015-06-15 16:02:51 获取站点需要修改
		String sitepath = CmsConstants.getSitePath(siteEntity);

		SimpleDateFormat sdf = new SimpleDateFormat("yyyy/MM/dd");
		Date registerTime = member.getCreatetime();
		// 会员卡图片打印
		Calendar cal = Calendar.getInstance();
		String memberCardId = null;
		if (org.apache.commons.lang3.StringUtils.isNotEmpty(member.getCardId())) {
			memberCardId = member.getCardId();
		} else {
			String y, s;
			y = String.valueOf(cal.get(Calendar.YEAR));
			s = y.substring(y.length() - 2, y.length());
			memberCardId = "LM" + s;
			String ran = RandomStringUtils.randomNumeric(8);
			memberCardId += ran;
			
			MemberEntity memberlist = findUniqueByProperty(MemberEntity.class, "cardId", memberCardId);
			if (memberlist != null) {
				ran = RandomStringUtils.randomNumeric(8);
				memberCardId += ran;
			}
		}
		String showPath =null;
		if (org.apache.commons.lang3.StringUtils.isNotEmpty(member.getCardPath())) {
			showPath = member.getCardPath();
		}else{
			showPath = "/upload/memberCard/" + sdf.format(registerTime) + "/" + memberCardId + ".jpg";
		}
		HttpSession session = ContextHolderUtils.getSession();
		Client client = ClientManager.getInstance().getClient(session.getId());
		SiteEntity siteId = client.getSite();
		String path=CmsConstants.getUploadFilesPath(siteId.getId())+showPath;
		String sname = sitepath + "/images/memberCard.jpg";

		File templateImgFile = new File(sname);
		if (templateImgFile.exists()) {
			String fileDir = StringUtils.substringBeforeLast(path, "/");
			org.apache.commons.io.FileUtils.forceMkdir(new File(fileDir + File.separator));
			FileUtil.copy(sname, path);
			String one,two,three;
			one=memberCardId.substring(0, 4);
			two=memberCardId.substring(4, 8);
			three=memberCardId.substring(8, 12);
			String newMem=one+" "+two+" "+three;
			// ImageMagickUtil.pressText(path, memberCardId, 0, 12, 167, 150);
			ImageJDKUtil.pressText(path,newMem, "幼圆", 0, 60, 30, 355, 255);
			LogUtil.info(member.getUsername() + "会员卡生成！ 路径：" + path);
		} else {
			String Site = SystemPath.getRootPath();
			String startname = Site + "/member/memberCard/memberCard.jpg";
			String fileDir = StringUtils.substringBeforeLast(path, File.separator);
			org.apache.commons.io.FileUtils.forceMkdir(new File(fileDir + File.separator));
			FileUtil.copy(startname, path);
			String one,two,three;
			one=memberCardId.substring(0, 4);
			two=memberCardId.substring(4, 8);
			three=memberCardId.substring(8, 12);
			String newMem=one+" "+two+" "+three;
			ImageJDKUtil.pressText(path, newMem, "幼圆", 0, 60, 30, 355, 255);
			LogUtil.info(member.getUsername() + "会员卡生成！ 路径：" + path);
		}
		member.setCardId(memberCardId);
		member.setCardPath(showPath);
		member.setCreatetime(new Date());// 创建时间
		save(member);
	}
	
	@Override
	public String getPathAll(List<MultipartFile> files) {
		String facePath = "";
		for (int i =0; i< files.size(); ++i) {
            MultipartFile file = files.get(i);
            SimpleDateFormat t = new SimpleDateFormat("yyyyMMdd");
            SiteEntity site = get(SiteEntity.class,"1");
            String sitepath =CmsConstants.getSitePath(site);//wwwwroot/www
            
        	HttpServletRequest request = ContextHolderUtils.getRequest();
        	
        	String customPath = "/upload/image/"+t.format(new Date());
        	String path = sitepath+customPath;
//        		FileInputStream file = new FileInputStream(portrait);
        	FileUtils.createFolder(path);
        	try {
        		// 得到上传的文件的文件名
        		String filename = file.getOriginalFilename();
        		InputStream inputStream = file.getInputStream();
        		byte[] b = new byte[1048576];
        		int length = 0;
        		// 文件流写到服务器端
        		FileOutputStream outputStream = new FileOutputStream(path +"/"+filename+".jpg");
        		while((length=inputStream.read(b))!=-1){
        			outputStream.write(b, 0, length);
        			outputStream.flush();
        		 }
        		inputStream.close();
        		outputStream.close();
        		facePath += customPath+"/"+filename+".jpg"+",";
        	} catch (Exception e) {
        		e.printStackTrace();
        		System.out.println(e.getMessage());
        	}
        }
		return facePath;
	}

	/**
	 * 删除会员
	 */
	@Override
	public void delete(MemberEntity member) {
		if(member==null){
			return;
		}
		memberDepartService.deleteByMember(member.getId());//通过会员删除与部门之间的关联
		
		super.delete(member);
	}

	/**
	 * 获取指定id集合的数据
	 * 
	 * @param memberIds
	 * @return 只有id和username列的集合
	 */
	@Override
	public List<MemberSimpleVOEntity> findIdUsernameListByIds(String[] memberIds) {
		
		CriteriaQuery cq = new CriteriaQuery(MemberEntity.class);
		
		//指定要返回的列
		DetachedCriteria dc =cq.getDetachedCriteria();
		ProjectionList pList = Projections.projectionList();
		pList.add(Projections.property("id").as("id"));
		pList.add(Projections.property("username").as("username"));  
		dc.setProjection(pList);  
		dc.setResultTransformer(Transformers.aliasToBean(MemberSimpleVOEntity.class));
		
		cq.in("id", memberIds);
		cq.add();
		
		return commonService.getListByCriteriaQuery(cq, false);
	}

	/**
	 * 获取分页的会员
	 * @param pageNo
	 * @param pageSize
	 * @return  只有id和username列的分页集合
	 */
	@Override
	public PageList getIdUsernamePageList(int pageNo, int pageSize) {
		PageList pageList = getIdUsernamePageList(pageNo, pageSize, null);
		return pageList;
	}

	/**
	 * 根据username获取分页的会员
	 * @param pageNo
	 * @param pageSize
	 * @param username like 模糊匹配查询
	 * @return 只有id和username列的分页集合
	 */
	@Override
	public PageList getIdUsernamePageList(int pageNo, int pageSize,
			String username) {
		CriteriaQuery cq = new CriteriaQuery(MemberEntity.class,pageSize,pageNo,"","");
		
		//指定要返回的列
		DetachedCriteria dc =cq.getDetachedCriteria();
		ProjectionList pList = Projections.projectionList(); 
		pList.add(Projections.property("id").as("id"));  
		pList.add(Projections.property("username").as("username"));  
		dc.setProjection(pList);  
		dc.setResultTransformer(Transformers.aliasToBean(MemberEntity.class)); 
	
		if(StringUtils.isNotBlank(username)){
			cq.like("username", username, MatchMode.ANYWHERE);
		}
		
		cq.addOrder("createtime", SortDirection.desc);
		cq.add();
	
		PageList pageList=commonService.getPageList(cq, true);
		return pageList;
	}
}