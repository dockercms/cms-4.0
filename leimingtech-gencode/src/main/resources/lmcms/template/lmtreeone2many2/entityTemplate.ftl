package ${bussiPackage}.entity.${entityPackage};

import java.util.ArrayList;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
<#if lmcms_primary_key_policy != 'uuid'>
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
</#if>
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;
<#if lmcms_primary_key_policy == 'sequence'>
import javax.persistence.SequenceGenerator;
</#if>
<#if lmcms_primary_key_policy == 'uuid'>

import com.leimingtech.core.entity.IdEntity;
</#if>

/**   
 * @Title: Entity
 * @Description: ${ftl_description}
 * @author 
 * @date ${ftl_create_time}
 * @version V1.0   
 *
 */
@Entity
@Table(name = "${tableName}", schema = "")
@DynamicUpdate(true)
@DynamicInsert(true)
<#if lmcms_primary_key_policy == 'uuid'>
public class ${entityName}Entity extends IdEntity implements java.io.Serializable {
<#else>
public class ${entityName}Entity implements java.io.Serializable {
</#if>

	private static final long serialVersionUID = ${serialVersionUID}L;
	<#list originalColumns as po>
	<#if po.fieldName == lmcms_table_id && lmcms_primary_key_policy == 'uuid'>
	<#else>
		<#if po.fieldName != "parentid">
	/** ${po.filedComment} */
	private ${po.fieldType} ${po.fieldName};
		</#if>
	</#if>
	</#list>
	private ${entityName}Entity ${entityName?uncap_first} ;
	private List<${entityName}Entity> ${entityName?uncap_first}s = new ArrayList<${entityName}Entity>();
	
	@ManyToOne(fetch = FetchType.LAZY)
	<#list originalColumns as po>
		<#if po.fieldName == "parentid">
	@JoinColumn(name = "${po.fieldName}")
		</#if>
	</#list>
	public ${entityName}Entity get${entityName}() {
		return this.${entityName?uncap_first};
	}

	public void set${entityName}(${entityName}Entity ${entityName?uncap_first}) {
		this.${entityName?uncap_first} = ${entityName?uncap_first};
	}
	
	@OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY, mappedBy = "${entityName?uncap_first}")
	public List<${entityName}Entity> get${entityName}s() {
		return ${entityName?uncap_first}s;
	}
	public void set${entityName}s(List<${entityName}Entity> ${entityName?uncap_first}s) {
		this.${entityName?uncap_first}s = ${entityName?uncap_first}s;
	}
	<#list originalColumns as po>
	<#if po.fieldName != "parentid">
	<#if po.fieldName != lmcms_table_id>
	
	/**
	 * 方法: 取得${po.fieldType}
	 *
	 * @return: ${po.fieldType} ${po.filedComment}
	 */
	@Column(name = "${po.fieldDbName}", nullable = <#if po.nullable == 'Y'>true<#else>false</#if><#if po.precision != ''>, precision = ${po.precision}</#if><#if po.scale != ''>, scale = ${po.scale}</#if><#if po.charmaxLength != ''>, length = ${po.charmaxLength}</#if>)
	public ${po.fieldType} get${po.fieldName?cap_first}() {
		return this.${po.fieldName};
	}

	/**
	 * 方法: 设置${po.fieldType}
	 *
	 * @param: ${po.fieldType} ${po.filedComment}
	 */
	public void set${po.fieldName?cap_first}(${po.fieldType} ${po.fieldName}) {
		this.${po.fieldName} = ${po.fieldName};
	}
	<#elseif lmcms_primary_key_policy != 'uuid'>
	
	/**
	 * 方法: 取得${po.fieldType}
	 *
	 * @return: ${po.fieldType} ${po.filedComment}
	 */
	<#if lmcms_primary_key_policy == 'identity'>
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	</#if>
	<#if lmcms_primary_key_policy == 'sequence'>
	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequence")
	@SequenceGenerator(name = "sequence", sequenceName = "${lmcms_sequence_code}", allocationSize = 1)
	</#if>
	@Column(name = "${po.fieldDbName}", nullable = <#if po.nullable == 'Y'>true<#else>false</#if><#if po.precision != ''>, precision = ${po.precision}</#if><#if po.scale != ''>, scale = ${po.scale}</#if><#if po.charmaxLength != ''>, length = ${po.charmaxLength}</#if>)
	public ${po.fieldType} get${po.fieldName?cap_first}() {
		return this.${po.fieldName};
	}

	/**
	 * 方法: 设置${po.fieldType}
	 *
	 * @param: ${po.fieldType} ${po.filedComment}
	 */
	public void set${po.fieldName?cap_first}(${po.fieldType} ${po.fieldName}) {
		this.${po.fieldName} = ${po.fieldName};
	}
	</#if>
	</#if>
	</#list>
}
