<%@ page contentType="text/html; charset=GB2312" %>
<%@ page import="com.runqian.report4.view.applet.*"%>
<%  //ȡ��List
	String report = request.getParameter("raq");
	report = "itemandproject.raq";
%>
<html>
<head>
<title>��Ŀ��ѯ</title>
<script language="javascript"src="/webpage/quiee/reportJsp/gap-mainframe.js"></script>
<script language="javascript" type="text/javascript">

//��ѯ����
function query_onClick(){
	var strCondtion = buildQueryCondition();
	document.getElementById("queryCondition").value=strCondtion;
	form.action="/webpage/quiee/reportJsp/showReport.jsp?raq=<%=report%>";
	form.target="reportFrame";
	form.submit();
	return false;
}
//���õķ���
function reset_back(){
	
	document.getElementById("itemname").value="";
}
//��ӡ����
function toPrint_OnClick() {
	alert(222);
	try{
		//window.print();
		window.frames['reportFrame'].report1_print();
		//document.report1_printIFrame.location = "http://192.168.1.113:8080/second/reportServlet?action=2&name=report1&reportFileName=itemandproject.raq&srcType=file&savePrintSetup=no&appletJarName=runqianReport4Applet.jar&serverPagedPrint=no";
	}catch(e){
	}
}
//��������
function toExcel_OnClick() {
	try{
		window.frames['reportFrame'].report1_saveAsExcel();
	}catch(e){
	}
}
//��������Iframe�߶�
function initFrameHeight(){
	var iHeight = document.body.offsetHeight;
	var frameObj = document.getElementById("reportFrame");
	frameObj.height = iHeight-135;
}
//�궨������
function buildQueryCondition() {  //�����򵥲�ѯ
	var queryCondition = "";  //������Ϻ�Ĳ�ѯ�������ַ�������
	var qca = new Array();  //�����ѯ�������������,ÿһ�����ܵĲ�ѯ�����ᱻѹ���������
			
	pushCondition(qca, "itemname", "like '", "%'", "a.itemname");
	for(var i=0; i<qca.length; i++) {  //�ӵ�2����ʼѭ����ѯ����
		queryCondition += " and " + qca[i] + " ";  //��װ��2���Ժ�Ĳ�ѯ����
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
			<img src="webpage/quiee/images/07-0.gif" class="div_control_image" >&nbsp;��������ѯ
		</td>
	</tr>
</table>
</div>

<div id="ccChild0"> 
<table class="table_div_content">
<tr><td>
	<table class="table_noFrame" width="100%">
		<tr>
		    <td align="right">��Ŀ����</td>
            <td align="left">
                <input name="itemname" id="itemname" valueName="returnValue" value=""/> <!-- ���λ���� -->
				<input type="hidden" name="queryCondition" id="queryCondition"/>
            </td>
            <td align="right"></td>	
            <td align="left"></td>
            <td align="right"></td>
            <td align="left"></td>
			<td align="left">
				<input type="button" value="��ѯ" onclick="query_onClick()"/>
				<input type="button" value="����" onclick="reset_back()"/>
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
				    <img src="/webpage/quiee/images/print.gif"/><input type="button" value="��ӡ" onclick="javascript:toPrint_OnClick();return false;" title="��ӡ"/>
				    </td>
					<td>
					<img src="/webpage/quiee/images/excel.gif"/><input type="button" value="����Excel" onclick="javascript:toExcel_OnClick();return false;" title="����Excel"/>
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
