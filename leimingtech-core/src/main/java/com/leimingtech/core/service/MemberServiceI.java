package com.leimingtech.core.service;

import com.leimingtech.core.entity.member.MemberEntity;
import com.leimingtech.core.entity.member.MemberSimpleVOEntity;
import com.leimingtech.core.hibernate.qbc.PageList;
import net.sf.json.JSONObject;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

/**
 * 会员管理接口
 * 
 * @author liuzhen 2014年5月20日 16:34:18
 * 
 */
public interface MemberServiceI extends CommonService {
	public void delMain(MemberEntity member);

	/**
	 * 验证用户是否存在
	 */
	public MemberEntity checkUserExits(MemberEntity member);

	/**
	 * 验证用户是否存在
	 */
	public Boolean checkUserExits(String username);

	/**
	 * 获取session中的会员（默认session中的会员只有会员id和会员名）
	 * 
	 * @return
	 */
	public MemberEntity getSessionMember();

	/**
	 * 获取站点路径
	 * 
	 * @return
	 */
	public String getSitePath();
	
	/**
	 * 移动客户端修改编辑头像
	 * 
	 * @return
	 */
	public JSONObject editData(String all,MultipartFile portrait,String userId);
	/**
	 * 移动客户端修改编辑用户名
	 * @param all
	 * @param userId
	 * @param userName
	 * @return
	 */
	public JSONObject editUserName(String all,String userId,String userName);
	/**
	 * 移动客户端修改编辑密码
	 * @param all
	 * @param userId
	 * @param password
	 * @return
	 */
	public JSONObject editPwd(String all,String userId,String password);
	/**
	 * 验证用户email是否已绑定
	 * @param email
	 * @return
	 */
	public Boolean checkUserEmail(String email);
	
	/**
	 * 验证用户名与邮箱是否为同一个用户的
	 * @param username
	 * @param email
	 * @return
	 */
	public Boolean checkNameAndEmail(String username, String email);
	
	/**
	 * 根据用户名获取用户
	 * @param username
	 * @return
	 */
	public MemberEntity getMemberByUsername(String username);
	
	/**
	 * 统计会员年sql
	 * @param value
	 * @param month
	 * @param staticsType
	 * @return
	 */
	public String yearSqlMember(int value,String month,String staticsType,String dbType);
	/**
	 * 统计会员月sql
	 * @param year
	 * @param month
	 * @param staticsType
	 * @param i
	 * @return
	 */
	public String monthSqlMember(String year,String month,String staticsType,int i,String dbType);
	/**
	 * 统计会员天sql
	 * @param year
	 * @param month
	 * @param staticsType
	 * @param i
	 * @return
	 */
	public String daySqlMember(String year,String month,String staticsType,int i,String dbType);
	/**
	 * APP上传图片进行处理
	 * @param portrait MultipartFile
	 * @return  图片路径
	 */
	public String getPath(MultipartFile portrait);
	/**
	 * APP上传多张图片进行处理
	 * @param portrait List<MultipartFile>
	 * @return  图片路径
	 */
	public String getPathAll(List<MultipartFile> files);
	/**
	 * 根据用户id获取用户
	 * @param userId
	 * @return
	 */
	public MemberEntity getMember(String userId);
	
	/**
	 * 生成会员卡图
	 * @param member
	 * @throws IOException
	 */
	public void createMemberCard(MemberEntity member) throws IOException;
	
	/**
	 * 删除会员
	 * @param member
	 */
	public void delete(MemberEntity member);

	/**
	 * 获取指定id集合的数据
	 * 
	 * @param memberIds
	 * @return 只有id和username列的集合
	 */
	public List<MemberSimpleVOEntity> findIdUsernameListByIds(String[] memberIds);

	/**
	 * 获取分页的会员
	 * @param pageNo
	 * @param pageSize
	 * @return  只有id和username列的分页集合
	 */
	public PageList getIdUsernamePageList(int pageNo, int pageSize);

	/**
	 * 根据username获取分页的会员
	 * @param pageNo
	 * @param pageSize
	 * @param username like 模糊匹配查询
	 * @return 只有id和username列的分页集合
	 */
	public PageList getIdUsernamePageList(int pageNo, int pageSize,
			String username);
}
