package com.leimingtech.core.common;

/**
 * 内容文章状态以及工作流常量类
 * @author zhangxiaoqiang
 *
 */
public class ContentStatus {

	/**草稿*/
	public static final String CONTENT_DRAFT = "10";
	/**待审*/
	public static final String CONTENT_PENDING_TRIAL = "20";
	/**退稿*/
	public static final String CONTENT_BACK_MANUSCRIPT = "30";
	/**待发*/
	public static final String CONTENT_COMMITTED = "40";
	/**已发*/
	public static final String CONTENT_PUBLISHED = "50";
	/**已撤*/
	public static final String CONTENT_RECALL = "60";
	/**回收站*/
	public static final String CONTENT_RECYCLE = "70";
	
	/**工作流状态*/
	/**待审*/
	public static final String WORKFLOW_PENDING_TRIAL = "20";
	/**已审*/
	public static final String WORKFLOW_YET_AUDIT = "30";
	/**拒审*/
	public static final String WORKFLOW_REFUSE = "40";
}
