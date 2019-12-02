<div class="modal-body">
	<div class="row-fluid">
		<div class="span12">
			<div class="portlet">
				<div class="portlet-title">
					<div class="caption"><i class="icon-globe"></i>${ftl_description}</div>
					<div class="actions">
						<button onclick="toEditModelSub('${parentEntityName?uncap_first}Controller.do?add${entityName}&${foreignKeys[1]?uncap_first}=${'$'}{${foreignKeys[1]?uncap_first}?if_exists}')" class="btn yellow-stripe"><i class="fa fa-plus"></i> 新增 </button>
						<button  class="btn yellow-stripe"  data-dismiss="modal" aria-hidden="true"> 关闭 </button>
					</div>
				</div>
		
				<div class="portlet-body">
					<div class="span12 booking-search">
						<form id="searchSub_form">
						<input type="hidden" name="${foreignKeys[1]?uncap_first}" value="${'$'}{${foreignKeys[1]?uncap_first}${'?if_exists'}}"/>
							<div class="clearfix">
								<#list columns as po>
									 <#if po_index lt 2>
								<div class="span4">
									<label style="margin-bottom:0px;">
										${po.filedComment}：
										<input name="${po.fieldName}" class="form-control span7" type="text" value="${'$'}{searchMap["${po.fieldName}"]${'?'}if_exists[0]}" style="margin-bottom:0px;">
									</label>
								</div>
									<#else>
										<#break>
									</#if>
								</#list>
								<div class="pull-right">
									<button type="button" class="btn yellow btn-sm" onclick="toSearchSub('${parentEntityName?uncap_first}Controller.do?${entityName?uncap_first}', 'searchSub_form')">搜索  <i class="icon-search m-icon-white"></i></button>
									<button type="button" class="btn yellow btn-sm" onclick="resetForm('searchSub_form');">重置<i class="icon-repeat m-icon-white"></i></button>
								</div>
							</div>
						</form>
					</div>
				
					<table class="table table-striped table-bordered table-hover" id="sample_1">
						<thead>
							<tr>
								<th width="40" style="text-align:center;">序号</th>
							<#list columns as po>
							<#if po.fieldName!=foreignKeys[1]?uncap_first>
								<th style="">${po.filedComment}</th>
							</#if>
							</#list>
								<th width="45" style="text-align:center;">操作</th>
							</tr>
						</thead>
						<tbody>
						<${'#'}if ${entityName?uncap_first}List?exists>
						  <${'#'}list ${entityName?uncap_first}List as ${entityName?uncap_first}>
							<tr class="odd gradeX">
								<td style="text-align:center;">${'$'}{(pageNo-1)*pageSize+${entityName?uncap_first}_index?if_exists+1}</td>
							<#list columns as po>
							<#if po.fieldName!=foreignKeys[1]?uncap_first>
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
									<input type='button' class="editbtn" title='修改' onclick="toEditModelSub('${parentEntityName?uncap_first}Controller.do?edit${entityName}&id=${'$'}{${entityName?uncap_first}.id?if_exists}&${foreignKeys[1]?uncap_first}=${'$'}{${foreignKeys[1]?uncap_first}?if_exists}')" />
									<input type='button' class="delbtn" title='删除' onclick="delSub('${parentEntityName?uncap_first}Controller.do?del${entityName}', '${'$'}{${entityName?uncap_first}.id?if_exists}')" />
								</td>
							</tr>
						  </${'#'}list> 
						</${'#'}if>
						</tbody>
						
					</table>
					<${'#'}include "/lmPage/common/page.html">
				</div>
			</div>
		</div>
	</div>
</div>
<script>
function toEditModelSub(urlStr){
	$.ajax({
		type:'post',
		url:urlStr,
		data:'data=0',
		dataType:'text',
		success:function(msg){
			$('#toEditModelSub').empty();
			$('#toEditModelSub').append(msg);
			$('#toEditModelSub').modal('show');
		},
		error:function(){
			lmAlert("load page error, page url is " + urlStr);
		}
	})
}
</script>