<div class="portlet-title">
	<div class="caption">
		<i class="fa fa-shopping-cart"></i>${ftl_description}信息
	</div>
	<${'#'}if ${entityName?uncap_first}.id != "">
	<div class="actions btn-set">
		<button class="btn yellow btn-sm" onclick="toEditModel('${entityName?uncap_first}Controller.do?add&pid=${'$'}{${entityName?uncap_first}.id?if_exists}')"><i class="fa icon-plus"></i> 创建子节点 </button>
		<button class="btn yellow btn-sm" onclick="formSubmit${entityName}('${entityName?uncap_first}Controller.do?save', '${entityName?uncap_first}_form1')"><i class="fa fa-check-circle"></i> 保存 </button>
		<button class="btn yellow btn-sm" onclick="del('${entityName?uncap_first}Controller.do?del', '${'$'}{${entityName?uncap_first}.id}')"><i class="fa icon-remove"></i> 删除 </button>
	</div>
	</${'#'}if>
</div>
<div class="portlet-body">
<${'#'}if ${entityName?uncap_first}.id == "">
	<button class="btn yellow btn-sm" onclick="toEditModel('${entityName?uncap_first}Controller.do?add')"><i class="icon-plus icon-white"></i> 创建子节点 </button>
<${'#'}else>
	<form id="${entityName?uncap_first}_form1" class="form-horizontal">
		<input type="hidden" id="id" name="id" value="${'$'}{${entityName?uncap_first}.id${'?if_exists'}}"/>
	<#list columns as po>
		<#if po.fieldName?index_of("parent")==-1&& po.fieldName?index_of("level")==-1&& po.fieldName?index_of("pathids")==-1>
			<#if po.fieldType?index_of("time")!=-1 >
		<div class="control-group">
			<label class="control-label">${po.filedComment}</label>
			<div class="controls">
				<input onfocus="WdatePicker({dateFmt:'yyyy-MM-dd',readOnly:true})" class="Wdate" type="text" id="${po.fieldName}" name="${po.fieldName}" value="${'$'}{${entityName?uncap_first}.${po.fieldName}${'?if_exists'}}"/>
			</div>
		</div>
			<#elseif po.fieldType?index_of("date")!=-1>
		<div class="control-group">
			<label class="control-label">${po.filedComment}</label>
			<div class="controls">
				<input onfocus="WdatePicker({dateFmt:'yyyy-MM-dd',readOnly:true})" class="Wdate" type="text" id="${po.fieldName}" name="${po.fieldName}" value="${'$'}{${entityName?uncap_first}.${po.fieldName}${'?if_exists'}}"/>
			</div>
		</div>
			<#else>
		<div class="control-group">
			<label class="control-label">${po.filedComment}</label>
			<div class="controls">
				<#if po.fieldName?index_of("sort")!=-1 || po.fieldName?index_of("Sort")!=-1>
				<input type="text" id="${po.fieldName}" name="${po.fieldName}" class="span2 form-control"  data-rule-digits="true" <#if po_index==0||po_index==1 >data-rule-required="true" </#if>value="${'$'}{${entityName?uncap_first}.${po.fieldName}!'0'}"/>
				<span class="help-inline">(数字越到越靠前)</span>	
				<#else>
				<input type="text" id="${po.fieldName}" name="${po.fieldName}" class="span6 form-control" <#if po_index==0||po_index==1 >data-rule-required="true" </#if>value="${'$'}{${entityName?uncap_first}?if_exists.${po.fieldName}}"/>
				</#if>
			</div>
		</div>
			</#if>
		</#if>
	</#list>
	</form>
</${'#'}if>
</div>
<script>
//校验
jQuery(document).ready(function() {   
   App.init();
   $("#${entityName?uncap_first}_form1").myValidate();
});
</script>