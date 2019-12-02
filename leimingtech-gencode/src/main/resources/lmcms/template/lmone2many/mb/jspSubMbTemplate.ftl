<div class="modal-header">
	<button type="button" class="close" data-dismiss="modal" aria-hidden="true"></button>
	<h4 class="modal-title">${ftl_description}</h4>
</div>
<div class="modal-body">
	<!-- BEGIN FORM-->
	<form id="${entityName?uncap_first}_form" class="form-horizontal">
		<input type="hidden" id="id" name="id" value="${'$'}{${entityName?uncap_first}.id${'?if_exists'}}"/>
	<#list columns as po>
		<#if po.fieldName=='id'>
		<#elseif po.fieldName==foreignKeys[1]?uncap_first>
		<div class="control-group">
			<label class="control-label">${po.filedComment}</label>
			<div class="controls">
				<select id="${foreignKeys[1]?uncap_first}" name="${foreignKeys[1]?uncap_first}" class="span3">
				<${'#'}list ${parentEntityName?uncap_first}List as ${parentEntityName?uncap_first}>
					<${'#'}if ${parentEntityName?uncap_first}?exists>
		        	<option value="${'$'}{${parentEntityName?uncap_first}.id}" <${'#'}if ${entityName?uncap_first}.${foreignKeys[1]?uncap_first}?if_exists && ${entityName?uncap_first}.${foreignKeys[1]?uncap_first}=${parentEntityName?uncap_first}.id>selected="selected"</${'#'}if>>${'$'}{${parentEntityName?uncap_first}.name}</option> 
			        </${'#'}if>
			    </${'#'}list>      
		        </select>
			</div>
		</div>
		<#else>
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
				<input type="text" id="${po.fieldName}" name="${po.fieldName}" class="span3 form-control" <#if po_index==0||po_index==1 >data-rule-required="true" </#if>value="${'$'}{${entityName?uncap_first}.${po.fieldName}${'?if_exists'}}"/>
			</div>
		</div>
		</#if>
		</#if>
	</#list>
	</form>
</div>
<div class="modal-footer">
	<input type="button" onclick="formSubmitModel('${entityName?uncap_first}Controller.do?save', '${entityName?uncap_first}_form');" class="btn btn-primary" value="提交">
	<input type="button" class="btn" value="关闭" data-dismiss="modal">
</div>
<script>
//校验
jQuery(document).ready(function() {   
   App.init();
   $("#${entityName?uncap_first}_form").myValidate();
});
</script>