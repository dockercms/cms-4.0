<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml" >
<head>
    <meta content="text/html; charset=UTF-8" http-equiv="content-type" />
    <title>网站标题</title>
    <meta name="keywords" content="keyword ..." />
    <meta name="Description" content="description ..." />
    <!--<link href="favicon.ico" rel="shortcut icon" />-->
    <link href="member/css/global.css" rel="stylesheet" type="text/css" />
    <link href="member/css/reginfo.css" rel="stylesheet" type="text/css" />
</head>
<body>
<div id="alipay_content">
<div class="title"><b>会员组列表</b></div>
<div class="body">
 <p class="p">您申请过的职位，国家人才网自动帮您保存60天的申请职位信息，方便您对职位进行跟踪。</p>
 <p class="p">申请记录查询：起始时间：<input name="content" style="height:28px;" type="text"onFocus="this.value=''" onBlur="if(!value){value=defaultValue;}" />结束时间:<input name="content" style="height:28px;"  type="text"onFocus="this.value=''" onBlur="if(!value){value=defaultValue;}" /></p>
 <div class="tablediv">
     <button type="button" onclick="test();" class="btn">新增</button>
   <table class="table" cellpadding="0" cellspacing="0">
     <thead>
       <tr>
         <th class="t0">&nbsp;</th>
         <th class="t1">id</th>
         <th class="t1">会员组名称</th>
         <th class="t2">个数</th>
         <th class="t3">状态</th>
         <th class="t4">备注</th>
         <th class="t5">操作</th>
       </tr>
     </thead>
     <tbody>
     	<#list memberGroupsList as memberGroups>
	        <tr>
	          <td class="t0"><input type="checkbox" class="checkbox" /></td>
	          <td class="t1">${memberGroups.id}</td>
	          <td class="t1">${memberGroups.usergroupsname}</td>
	          <td class="t2">${memberGroups.number}</td>
	          <td class="t3">${memberGroups.state}</td>
	          <td class="t4">${memberGroups.remark}</td>
	          <td class="t5"><a href="javascript:;" onclick="modify(${memberGroups.id})">修改</a><a href="javascript:;" onclick="del(${memberGroups.id})">删除</a></td>
	        </tr>
        </#list>
     </tbody>
   </table>
    <div class="page"><a href="javaScript:void(0)" onclick="nextpage('1')">首页</a><a href="javaScript:void(0)" onclick="nextpage('${pageIndex-1}')">上一页</a>
   <#list 1..pageNumber as i>
			<td style="color:red;"><span><a href="javaScript:void(0)" onclick="nextpage('${i}')">${i}</a></span></td>
    </#list>
    <a href="javaScript:void(0)" onclick="nextpage('${pageIndex+1}','${pageNumber}')">下一页</a><a href="javaScript:void(0)" onclick="nextpage('${pageNumber}')">尾页</a>&nbsp;
     
 </div>
 </div>
</div>
</body>
</html>
<script>
	function add(){
		$.ajax({
			type:'post',
			url:'memberGroupsFrontController.do?add',
			data:'t='+new Date().getTime(),
			dataType:'text',
			success:function(msg){
				$('#rightcontent').empty();
				$('#rightcontent').append(msg);
			},
			error:function(){
				alert("load page error, page url is " + urlStr);
			}
		});
	}
	
	function modify(id){
		$.ajax({
			type:'post',
			url:'memberGroupsFrontController.do?modify',
			data:'id='+id,
			dataType:'text',
			success:function(msg){
				$('#rightcontent').empty();
				$('#rightcontent').append(msg);
			},
			error:function(){
				alert("load page error, page url is " + urlStr);
			}
		});
	}
	
	function del(id){
		$.ajax({
			type:'post',
			url:'memberGroupsFrontController.do?del',
			data:'id='+id,
			dataType:'text',
			success:function(msg){
				$('#rightcontent').empty();
				$('#rightcontent').append(msg);
			},
			error:function(){
				alert("load page error, page url is " + urlStr);
			}
		});
	}
	
		/**
		分页
	*/
	function nextpage(pageIndex,count){
	if(pageIndex<1){
		alert("已经到首页");
		return;
	}
	if(pageIndex>count){
		alert("已经是最后一页");
		return;
	}
		$.ajax({
			type:'post',
			url:'memberGroupsFrontController.do?memberGroups',
			data:'pageIndex='+pageIndex,
			dataType:'text',
			success:function(msg){
				$('#rightcontent').empty();
				$('#rightcontent').append(msg);
			},
			error:function(){
				alert("load page error, page url is " + urlStr);
			}
		});
	}
</script>
<style>
.btn {
    border: 1px solid #F28427;
    cursor: pointer;
    padding: 0px 15px;
    height: 27px;
    background:#ff8b00;
    color: #FFF;
    font-size: 14px;
}

</style>