<div class="modal-header">
	<button type="button" class="close" data-dismiss="modal" aria-hidden="true"></button>
	<h4 class="modal-title">${ftl_description}</h4>
</div>
<div class="modal-body">
	<!-- BEGIN FORM-->
	<form id="${entityName?uncap_first}_form" class="form-horizontal">
		<input type="hidden" id="id" name="id" value="${'$'}{${entityName?uncap_first}?if_exists.id}"/>
		<input type="hidden" id="id" name="${entityName?uncap_first}.id" value="${'$'}{${entityName?uncap_first}?if_exists.${entityName?uncap_first}?if_exists.id}"/>
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
				<input type="text" id="${po.fieldName}" name="${po.fieldName}" class="span1 form-control"  data-rule-digits="true" <#if po_index==0||po_index==1 >data-rule-required="true" </#if>value="${'$'}{${entityName?uncap_first}.${po.fieldName}!'0'}"/>
				<span class="help-inline">(数字越到越靠前)</span>	
				<#else>
				<input type="text" id="${po.fieldName}" name="${po.fieldName}" class="span3 form-control" <#if po_index==0||po_index==1 >data-rule-required="true" </#if>value="${'$'}{${entityName?uncap_first}?if_exists.${po.fieldName}}"/>
				</#if>
			</div>
		</div>
			</#if>
		</#if>
	</#list>
	</form>
	<!-- END FORM-->
</div>
<div class="modal-footer">
	<input type="button" onclick="formSubmit${entityName}('${entityName?uncap_first}Controller.do?save', '${entityName?uncap_first}_form');" class="btn btn-primary" value="提交">
	<input type="button" class="btn" value="关闭" data-dismiss="modal">
</div>
<script>
//校验
jQuery(document).ready(function() {   
   App.init();
   $("#${entityName?uncap_first}_form").myValidate();
});
</script>