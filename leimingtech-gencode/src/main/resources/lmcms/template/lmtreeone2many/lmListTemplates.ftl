<div class="row-fluid">
	<div class="span4">
		<div class="portlet box blue">
			<div class="portlet-title">
				<div class="caption"><i class="icon-sitemap"></i>${ftl_description}管理</div>
			</div>
			<div class="portlet-body fuelux" id="${entityName?uncap_first}_tree"></div>
		</div>
	</div>
	<div class="span8">
		<div class="portlet box blue" id="divBody">
			<${'#'}include "/${webRootPackage?substring(webRootPackage?last_index_of('/')+1)}/${entityPackage}/${entityName?uncap_first}Show.html">
		</div>
	</div>
</div>
<div id="editModel" class="modal fade" tabindex="-1" data-backdrop="static" data-focus-on="input:first" data-width="600"></div>
<script>
$(function (){
	$("#${entityName?uncap_first}_tree").jstree({"types" : {"default" : { "icon" : "fa fa-folder icon-success icon-lg" }},
	    "plugins": ["types"],"core" : {"themes" : { "responsive": false},  "check_callback" : true,'data': ${'$'}{jstreeData}}});
	$("#${entityName?uncap_first}_tree").coffee({click:{'.jstree-anchor':function (){
		var id=$(this).parent().attr("id");
		changeDivBody('${entityName?uncap_first}Controller.do?show&id=' + id, 'divBody');
		}}
	});
});
function formSubmit${entityName}(urlStr, formName){
	if(!$('#' + formName).valid()){
		return false;
  	}
	$.ajax({
		type:'post',
		url:urlStr,
		data:$('#' + formName).serialize(),
		success:function(msg){
			var obj = JSON.parse(eval(msg));
			if(obj.isSuccess){
				$('#editModel').empty();
				$('#editModel').modal('hide');
				lmAlert(obj.msg);
				lazyChangePage(obj.toUrl);
			}else{
				lmAlert(obj.msg);
			}
		},
		error:function(){
			lmAlert("提交通讯失败!");
		}
	});
}
</script>