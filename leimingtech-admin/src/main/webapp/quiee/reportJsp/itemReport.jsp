<%@ page contentType="text/html; charset=GB2312" %>
<%@ page import="com.runqian.report4.view.applet.*"%>
<%  //取出List
	String report = request.getParameter("raq");
	report = "itemandproject.raq";
%>
<html>
<head>
<title>项目查询</title>
<script language="javascript"src="/webpage/quiee/reportJsp/gap-mainframe.js"></script>
<script language="javascript" type="text/javascript">

//查询方法
function query_onClick(){
	var strCondtion = buildQueryCondition();
	document.getElementById("queryCondition").value=strCondtion;
	form.action="/webpage/quiee/reportJsp/showReport.jsp?raq=<%=report%>";
	form.target="reportFrame";
	form.submit();
	return false;
}
//重置的方法
function reset_back(){
	
	document.getElementById("itemname").value="";
}
//打印报表
function toPrint_OnClick() {
	alert(222);
	try{
		//window.print();
		window.frames['reportFrame'].report1_print();
		//document.report1_printIFrame.location = "http://192.168.1.113:8080/second/reportServlet?action=2&name=report1&reportFileName=itemandproject.raq&srcType=file&savePrintSetup=no&appletJarName=runqianReport4Applet.jar&serverPagedPrint=no";
	}catch(e){
	}
}
//导出报表
function toExcel_OnClick() {
	try{
		window.frames['reportFrame'].report1_saveAsExcel();
	}catch(e){
	}
}
//调整报表Iframe高度
function initFrameHeight(){
	var iHeight = document.body.offsetHeight;
	var frameObj = document.getElementById("reportFrame");
	frameObj.height = iHeight-135;
}
//宏定义条件
function buildQueryCondition() {  //构建简单查询
	var queryCondition = "";  //定义组合后的查询条件的字符串变量
	var qca = new Array();  //定义查询条件的数组变量,每一个可能的查询条件会被压入这个数组
			
	pushCondition(qca, "itemname", "like '", "%'", "a.itemname");
	for(var i=0; i<qca.length; i++) {  //从第2个开始循环查询条件
		queryCondition += " and " + qca[i] + " ";  //组装第2个以后的查询条件
	}
	return queryCondition;
}
</script>
</head>
<body onload="javascript:initFrameHeight();">
<form name="form" method="post" action="">
<div id="ccParent0"> 
<table class="table_div_control">
	<tr height="25px"> 
		<td>
			<img src="webpage/quiee/images/07-0.gif" class="div_control_image" >&nbsp;按条件查询
		</td>
	</tr>
</table>
</div>

<div id="ccChild0"> 
<table class="table_div_content">
<tr><td>
	<table class="table_noFrame" width="100%">
		<tr>
		    <td align="right">项目名称</td>
            <td align="left">
                <input name="itemname" id="itemname" valueName="returnValue" value=""/> <!-- 填报单位主键 -->
				<input type="hidden" name="queryCondition" id="queryCondition"/>
            </td>
            <td align="right"></td>	
            <td align="left"></td>
            <td align="right"></td>
            <td align="left"></td>
			<td align="left">
				<input type="button" value="查询" onclick="query_onClick()"/>
				<input type="button" value="重置" onclick="reset_back()"/>
			</td>			
            <td align="right"></td>
            <td align="left"></td>
		</tr>
	</table>

</td></tr>
</table>
</div>
<div id="ccParent1"> 
<table style="border-bottom: none;" align="right">
	<tr> 
		<td> 
			<table align="left">
                <tr>
				    <td>
				    <img src="/webpage/quiee/images/print.gif"/><input type="button" value="打印" onclick="javascript:toPrint_OnClick();return false;" title="打印"/>
				    </td>
					<td>
					<img src="/webpage/quiee/images/excel.gif"/><input type="button" value="导出Excel" onclick="javascript:toExcel_OnClick();return false;" title="导出Excel"/>
					</td>
				</tr>
            </table>
		</td>
	</tr>
</table>
</div>
<div id="ccChild1"> 
  <table style="width:100%">
    <tr>
      <td>
        <iframe id="reportFrame" name="reportFrame" scrolling="auto" width="100%" height="100%" frameborder="0" src="/webpage/quiee/reportJsp/showReport.jsp?raq=<%=report%>"></iframe>
      </td>
    </tr> 
  </table>
</div>
</form>

</body>
</html>
