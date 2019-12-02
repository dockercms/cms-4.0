package com.leimingtech.core.extend.hqlsearch;

import java.beans.PropertyDescriptor;
import java.lang.reflect.Field;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.persistence.Transient;

import org.apache.commons.beanutils.PropertyUtils;
import org.apache.commons.lang.StringUtils;

import com.leimingtech.core.annotation.query.QueryTimeFormat;
import com.leimingtech.core.extend.hqlsearch.parse.IHqlParse;
import com.leimingtech.core.extend.hqlsearch.parse.impl.BigDecimalParseImpl;
import com.leimingtech.core.extend.hqlsearch.parse.impl.BooleanParseImpl;
import com.leimingtech.core.extend.hqlsearch.parse.impl.DoubleParseImpl;
import com.leimingtech.core.extend.hqlsearch.parse.impl.FloatParseImpl;
import com.leimingtech.core.extend.hqlsearch.parse.impl.IntegerParseImpl;
import com.leimingtech.core.extend.hqlsearch.parse.impl.LongParseImpl;
import com.leimingtech.core.extend.hqlsearch.parse.impl.ShortParseImpl;
import com.leimingtech.core.extend.hqlsearch.parse.impl.StringParseImpl;
import com.leimingtech.core.hibernate.qbc.CriteriaQuery;
import com.leimingtech.core.util.StringUtil;


/**
 * 
 * @author  
 *
 */
public class HqlGenerateUtil {

	/** 时间查询符号 */
	private static final String END = "_end";
	private static final String BEGIN = "_begin";

	private static Map<String, IHqlParse> map = new HashMap<String, IHqlParse>();

	private static final SimpleDateFormat time = new SimpleDateFormat(
			"yyyy-MM-dd hh:mm:ss");
	private static final SimpleDateFormat day = new SimpleDateFormat(
			"yyyy-MM-dd");

	static {
		map.put("class java.lang.Integer", new IntegerParseImpl());
		map.put("class java.lang.Boolean", new BooleanParseImpl());
		map.put("class java.lang.Short", new ShortParseImpl());
		map.put("class java.lang.String", new StringParseImpl());
		map.put("class java.lang.Long", new LongParseImpl());
		map.put("class java.lang.Double", new DoubleParseImpl());
		map.put("class java.lang.Float", new FloatParseImpl());
		map.put("class java.math.BigDecimal", new BigDecimalParseImpl());
	}

	/**
	 * 自动生成查询条件HQL 模糊查询 不带有日期组合
	 * 
	 * @param hql
	 * @param values
	 * @param searchObj
	 * @throws Exception
	 */
	public static void installHql(CriteriaQuery cq, Object searchObj) {
		installHqlJoinAlias(cq, searchObj,null, "");
		cq.add();
	}

	/**
	 * 自动生成查询条件HQL（扩展区间查询功能）
	 * 
	 * @param cq
	 * @param searchObj
	 * @param param_lock
	 *            request参数集合，封装了所有查询信息
	 */
	public static void installHql(CriteriaQuery cq, Object searchObj,Map<String, String[]> parameterMap) {
		Map<String , String []> param_noLock = new HashMap<String , String []>();
		param_noLock.putAll(parameterMap);
		installHqlJoinAlias(cq, searchObj, param_noLock, "");
		cq.add();
	}
	/**
	 * 添加Alias别名的查询
	 *@date   2014年1月19日
	 *@param cq
	 *@param searchObj
	 *@param parameterMap
	 *@param alias
	 */
	private static void installHqlJoinAlias(CriteriaQuery cq, Object searchObj,
			Map<String, String[]> parameterMap, String alias)  {
		PropertyDescriptor origDescriptors[] = PropertyUtils
				.getPropertyDescriptors(searchObj.getClass());
		String aliasName, name, type;
		Class<?> cls=searchObj.getClass();
		Field fields [] =cls.getDeclaredFields();
		List<String> fieldList=new ArrayList<String>();
		for(Field f : fields) {
			fieldList.add(f.getName());
		}
		for (int i = 0; i < origDescriptors.length; i++) {
			aliasName = (alias.equals("") ? "" : alias + ".")
					+ origDescriptors[i].getName();
			name = origDescriptors[i].getDisplayName();
			type = origDescriptors[i].getPropertyType().toString();
			try {
				if (judgedIsUselessField(name)
						|| !PropertyUtils.isReadable(searchObj, name)) {
					continue;
				}
				// 添加 判断是否有区间值
				String beginValue = null;
				String endValue = null;
				if (parameterMap!=null&&parameterMap.containsKey(name + BEGIN)) {
					beginValue = parameterMap.get(name + BEGIN)[0].trim();
				}
				if (parameterMap!=null&&parameterMap.containsKey(name + END)) {
					endValue = parameterMap.get(name + END)[0].trim();
				}

				Object value = PropertyUtils.getSimpleProperty(searchObj, name);
				// 根据类型分类处理
				if (type.contains("class java.lang")
						|| type.contains("class java.math")) {
					Transient transientField= origDescriptors[i].getReadMethod().getAnnotation(Transient.class);
					if(transientField!=null){//标识为瞬时的属性不参与数据库操作
						continue ;
					}
					if(parameterMap==null && fieldList.contains(name)){
						map.get(type).addCriteria(cq, aliasName, value);
					}else if(fieldList.contains(name)) {
						map.get(type).addCriteria(cq , aliasName , value , beginValue , endValue);
					}
				} else if ("class java.util.Date".equals(type)) {
					Transient transientField=  origDescriptors[i].getReadMethod().getAnnotation(Transient.class);
					if(transientField!=null){//标识为瞬时的属性不参与数据库操作
						continue ;
					}
					QueryTimeFormat format = origDescriptors[i].getReadMethod()
							.getAnnotation(QueryTimeFormat.class);
					SimpleDateFormat userDefined = null;
					if (format != null) {
						userDefined = new SimpleDateFormat(format.format());
					}
					if (StringUtils.isNotBlank(beginValue)) {
						if (userDefined != null) {
							cq.ge(aliasName, new Timestamp(time.parse(beginValue).getTime()));
						} else if (beginValue.length() == 19) {
							cq.ge(aliasName, new Timestamp(time.parse(beginValue).getTime()));
						} else if (beginValue.length() == 10) {
							cq.ge(aliasName, new Timestamp(time.parse(beginValue+" 00:00:00").getTime()));
						}
						parameterMap.remove(name + BEGIN);//移除范围值,范围值暂不支持递归时继续作为条件
					}
					if (StringUtils.isNotBlank(endValue)) {
						if (userDefined != null) {
							cq.ge(aliasName,new Timestamp(time.parse(beginValue).getTime()));
						} else if (endValue.length() == 19) {
							cq.le(aliasName, new Timestamp(time.parse(endValue).getTime()));
						} else if (endValue.length() == 10) {
							// 对于"yyyy-MM-dd"格式日期，因时间默认为0，故此添加" 23:23:59"并使用time解析，以方便查询日期时间数据
							cq.le(aliasName, new Timestamp(time.parse(endValue + " 23:23:59").getTime()));
						}
						parameterMap.remove(name + END);//移除范围值,范围值暂不支持递归时继续作为条件
					}
					if (isNotEmpty(value)) {
						cq.eq(aliasName, value);
					}
				} else if (!StringUtil.isJavaClass(origDescriptors[i]
						.getPropertyType())) {
					Object param = PropertyUtils.getSimpleProperty(searchObj,
							name);
					if (isNotEmpty(param) && itIsNotAllEmpty(param)) {
						// 如果是实体类,创建别名,继续创建查询条件
						cq.createAlias(aliasName, aliasName);
						
						installHqlJoinAlias(cq, param,parameterMap,aliasName);
					}
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}


	private static boolean judgedIsUselessField(String name) {
		return "class".equals(name) || "ids".equals(name)
				|| "page".equals(name) || "rows".equals(name)
				|| "sort".equals(name) || "order".equals(name);
	}

	/**
	 * 判断是不是空
	 */
	public static boolean isNotEmpty(Object value) {
		return value != null && !"".equals(value);
	}

	/**
	 * 判断这个类是不是所以属性都为空
	 * 
	 * @param param
	 * @return
	 */
	private static boolean itIsNotAllEmpty(Object param) {
		boolean isNotEmpty = false;
		try {
			PropertyDescriptor origDescriptors[] = PropertyUtils
					.getPropertyDescriptors(param);
			String name;
			for (int i = 0; i < origDescriptors.length; i++) {
				name = origDescriptors[i].getName();
				if ("class".equals(name)
						|| !PropertyUtils.isReadable(param, name)) {
					continue;
				}
				if (Map.class.isAssignableFrom(origDescriptors[i]
						.getPropertyType())) {
					Map<?, ?> map = (Map<?, ?>) PropertyUtils
							.getSimpleProperty(param, name);
					if (map != null && map.size() > 0) {
						isNotEmpty = true;
						break;
					}
				} else if (Collection.class.isAssignableFrom(origDescriptors[i]
						.getPropertyType())) {
					Collection<?> c = (Collection<?>) PropertyUtils
							.getSimpleProperty(param, name);
					if (c != null && c.size() > 0) {
						isNotEmpty = true;
						break;
					}
				} else if (StringUtil.isNotEmpty(PropertyUtils
						.getSimpleProperty(param, name))) {
					isNotEmpty = true;
					break;
				}
			}
		} catch (Exception e) {

		}
		return isNotEmpty;
	}

}
