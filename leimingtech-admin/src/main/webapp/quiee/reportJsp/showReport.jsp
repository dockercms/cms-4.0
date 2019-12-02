<%@page import="com.leimingtech.platform.web.system.pojo.base.Helper"%>
<%@ page contentType="text/html;charset=UTF-8"%>
<%@ page import="com.runqian.report4.usermodel.*"%>
<%@ page import="com.runqian.report4.model.*"%>
<%@ page import="com.runqian.report4.util.ReportUtils"%>
<%@ page import="com.runqian.report4.view.html.*"%> 
<%@ page import="com.runqian.report4.view.applet.*"%>
<%@ page import="java.sql.*"%>
<%@ page import="java.io.*"%>
<%@ taglib uri="/WEB-INF/quieeReport/runqianReport4.tld" prefix="report"%>
<html>
<head>
	<title>中国交通设备物资采购管理信息平台</title>
</head>
<body topmargin=0 leftmargin=0 rightmargin=0 bottomMargin=0>
<%
	request.setCharacterEncoding("UTF-8");
	
	String report = null;
	if (request.getAttribute("raq") == null) {
		report = request.getParameter("raq");
	} else {
		report = request.getAttribute("raq").toString();
	}
	
	String reportFileHome = request.getRealPath("/quiee/reportFiles/" + report);
	FileInputStream fis = new FileInputStream(reportFileHome);
	ReportDefine rd = (ReportDefine) ReportUtils.read(fis);
	Connection conn = new Helper().requestConnection();
	StringBuffer param = new StringBuffer();
	Context context = new Context();
	context.setDefDataSourceName("dataSource");
	context.setConnection("dataSource", conn); 
	String defDsName = context.getDefDataSourceName ();
	
	//保证报表名称的完整性
	int iTmp = report.lastIndexOf(".raq");
	if (iTmp <= 0) {
		report = report + ".raq";
		iTmp = 0;
	}

	ParamMetaData pmd = rd.getParamMetaData();
	if (pmd != null) {
		for (int k = 0, pmdNum = pmd.getParamCount(); k < pmdNum; k++) {
			Param kParam = pmd.getParam(k);
			String paramName = kParam.getParamName();
			String paramValue = (String) request.getParameter(paramName);
			if (paramValue == null)
				paramValue = kParam.getValue();
			context.setParamValue(paramName, paramValue);
		}
	}

	MacroMetaData mmd = rd.getMacroMetaData();
	if (mmd != null) {
		int mmdNum = mmd.getMacroCount();
		for (int k = 0; k < mmdNum; k++) {
			Macro kMacro = mmd.getMacro(k);
			String macroName = kMacro.getMacroName();
			String macroValue = (String) request.getParameter(macroName);
			if (macroValue == null)
				macroValue = kMacro.getMacroValue();
			context.setMacroValue(macroName, macroValue);
		}
	}

	//以下代码是检测这个报表是否有相应的参数模板
	String paramFile = report.substring(0, iTmp) + "_arg.raq";
	File file = new File(application.getRealPath(reportFileHome + File.separator + paramFile));

	String rptName = "RPT_" + Double.toString(Math.random());
	request.setAttribute(rptName, rd);
	request.setAttribute("myContext", context);
%>
  <table id=rpt align=center><tr><td>
<%
	//如果参数模板存在，则显示参数模板
	if (file.exists()) {
%>
    <table id="param_tbl">
	  <tr>
		<td>
		  <report:param name="form1" paramFileName="<%=paramFile%>" needSubmit="yes" params="<%=param.toString()%>" />
		</td>
	  </tr>
    </table>
<%
	}
%>
    <table align="center">
	  <tr>
	    <td align="right">
		  <report:html name="report1" beanName="<%=rptName%>" srcType="defineBean" contextName="myContext" width="-1" height="-1" 
		               needSaveAsExcel="no" excelPageStyle="0" needSaveAsPdf="no" needSaveAsText="no" needPrint="no" needPageMark="no"/>
		</td>
	  </tr>
	</table>
  </td></tr></table>
</body>
</html>