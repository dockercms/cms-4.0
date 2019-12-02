package com.leimingtech.core.common;

/**
 * 标签常量
 * 
 * @author liuzhen 2014年4月28日 11:33:26
 * 
 */
public class TagConstants {

	// level 获取层级
	/** catalog站点下所有栏目，如果指定了name则此属性无效 article 所有栏目的新闻 */
	public static final String LEVEL_ROOT = "root";
	/** catalog当前栏目的同级栏目列表 article 当前栏目下的新闻 */
	public static final String LEVEL_CURRENT = "current";
	/** catalog当前栏目下的子栏目，默认属性 article当前栏目下子栏目的所有新闻，不包含当前栏目 */
	public static final String LEVEL_CHILD = "child";
	/** 当前 栏目/文章 对象 */
	public static final String LEVEL_SELF = "self";

	/** 文章专用 获取当前栏目下所有子栏目的新闻，含本栏目，默认值 */
	public static final String LEVEL_All = "all";

	// 文章排序 默认数据库id倒叙
	/** 配合新闻使用，按点击率排序 */
	public static final String ORDER_PV = "pv";
	/** 配合新闻使用，最新新闻，按发布时间排序 */
	public static final String ORDER_RECENT = "recent";
	/** 头条新闻 */
	public static final String ORDER_TOP = "top";
	/**置顶*/
	public static final String ORDER_ISTOP="is_top";
}
