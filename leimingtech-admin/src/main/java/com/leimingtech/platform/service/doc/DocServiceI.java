package com.leimingtech.platform.service.doc;

import java.util.List;
import java.util.Map;

import com.leimingtech.platform.entity.doc.DocEnRefEntity;
import com.leimingtech.platform.entity.doc.DocEntity;

/**
 * 文档管理接口
 * 
 * @author liuzhen 2014年6月11日 16:52:19
 * 
 */
public interface DocServiceI {
	
	
	/**
	 * 按属性查找对象列表.
	 */
	public  List<DocEnRefEntity> findByProperty(String id);
	
	/**
	 * 通过id获取预览下的文档
	 * 
	 * @param <T>
	 * @param cq
	 * @return
	 */
	public  List<DocEntity> getListRedDoc(String id); 	
	
	
	
	
	
	/**
	 * 根据id查看预览api文档
	 * 
	 * @param reqeust
	 * @return
	 */
	public  List<DocEntity> getListRedDocApi(String id); 
	
	
	/**
	 * 通过id获取预览下的api文档列表
	 * 
	 * @param <T>
	 * @param cq
	 * @return
	 */
	public  List<DocEntity> getListRedDocApiList(String id); 	
	
	
	
	/**
	 * 通过id获取预览下的标签文档
	 * 
	 * @param <T>
	 * @param cq
	 * @return
	 */
	public  List<DocEntity> getListRedDocTag(String id); 	
	

	/**
	 * 删除文档（级联删除子集）
	 * 
	 * @param doc
	 */
	void delete(DocEntity doc);

	/**
	 * 获取文档列表(标签调用)
	 * 
	 * @param params
	 * @return
	 */
	Object getDocListByTag(Map params);
	
	
	/**
	 * 获取分页后的站点数据集
	 * 
	 * @param DocEntity
	 * @param param
	 *            字段结尾含有"_begin"或"_end"的查询条件，无此类型时传参null
	 * @param pageSize
	 *            每页获取数量
	 * @param pageNo
	 *            当前页码
	 * @return docList栏目数据集 pageCount总页数
	 */
	Map<String, Object> getPageList(String name, String typeid, String pid,String platform ,int pageSize,
			int pageNo);
	/**
	 * 更新文档
	 * 
	 * @param doc
	 */
	void saveOrUpdate(DocEntity doc);

	/**
	 * 保存文档
	 * 
	 * @param doc
	 * @return
	 */
	String save(DocEntity doc);
	/**
	 * 根据id获取文档
	 * 
	 * @param id
	 * @return
	 */
	DocEntity getDoc(String id);
}
