<div class="portlet-title"></div>
<div class="portlet-body form">
	<!-- BEGIN FORM-->
	<form class="form-horizontal" role="form">
		<div class="form-body">
			<h2 class="margin-bottom-20"> ${'$'}{${entityName?uncap_first}.name?if_exists} </h2>
		</div>
		<div class="form-actions fluid " style="padding-left:50px;">
			<div class="row">
				<div class="col-md-6">
					<div class="col-md-offset-3 col-md-9">
						<${'#'}if ${entityName?uncap_first}.id != "">
						<input type="button" class="btn green btn-sm" value="修改" onclick="toEditModel('${entityName?uncap_first}Controller.do?edit&id=${'$'}{${entityName?uncap_first}.id?if_exists}')"/>
						<input type="button" class="btn green btn-sm" value="删除" onclick="del('${entityName?uncap_first}Controller.do?del', '${'$'}{${entityName?uncap_first}.id?if_exists}')"/>
						</${'#'}if>
						<input type="button" class="btn green btn-sm" value="创建子节点" onclick="toEditModel('${entityName?uncap_first}Controller.do?add&pid=${'$'}{${entityName?uncap_first}.id}')"/>
					</div>
				</div>
			</div>
		</div>
	<${'#'}if (${entityName?uncap_first}List?exists && ${entityName?uncap_first}List?size > 0)>
		<div class="form-body margin-top-20">
			<table class="table table-hover" id="sample_1">
				<thead>
					<tr>
						<th width="40" style="text-align:center;">序号</th>
					<#list columns as po>
					<#if po.fieldName?index_of("parent")==-1 && po.fieldName?index_of("level")==-1 && po.fieldName?index_of("pathids")==-1>
						<th style="">${po.filedComment}</th>
					</#if>
					</#list>
						<th width="40" style="text-align:center;">操作</th>
					</tr>
				</thead>
				<tbody>
				  <${'#'}if ${entityName?uncap_first}List?exists>
					  <${'#'}list ${entityName?uncap_first}List as ${entityName?uncap_first}>
					<tr class="odd gradeX">
						<td style="text-align:center;">${'$'}{${entityName?uncap_first}_index?if_exists+1}</td>
					<#list columns as po>
					<#if po.fieldName?index_of("parent")==-1 && po.fieldName?index_of("level")==-1 && po.fieldName?index_of("pathids")==-1>
						<td>
						<#if po.fieldType?index_of("time")!=-1 >
							${'$'}{${entityName?uncap_first}.${po.fieldName}${'?'}string("yyyy-MM-dd")}
						<#elseif po.fieldType?index_of("date")!=-1>
							${'$'}{${entityName?uncap_first}.${po.fieldName}${'?'}string("yyyy-MM-dd")}
						<#else>
							${'$'}{${entityName?uncap_first}.${po.fieldName}}
						</#if>
						</td>
					</#if>
					</#list>
						<td style="text-align:center;">
							<input type='button' class="editbtn" title='修改' onclick="toEditModel('${entityName?uncap_first}Controller.do?edit&id=${'$'}{${entityName?uncap_first}.id?if_exists}')" />
							<input type='button' class="delbtn" title='删除' onclick="del('${entityName?uncap_first}Controller.do?del', '${'$'}{${entityName?uncap_first}.id?if_exists}')" />
						</td>
					</tr>
					  </${'#'}list> 
					</${'#'}if>
				</tbody>
			</table>
		</div>
	</${'#'}if>
	</form>
	<!-- END FORM-->
</div>