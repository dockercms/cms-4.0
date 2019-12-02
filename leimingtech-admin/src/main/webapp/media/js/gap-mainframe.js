//document.onkeydown=keyDown;

function keyDown() {
	/*	
	//屏蔽鼠标右键、Ctrl+n、shift+F10、F5刷新、退格键
	if((window.event.altKey)&&
	  ((window.event.keyCode==37)|| //屏蔽 Alt+ 方向键 ←
	  (window.event.keyCode==39))){ //屏蔽 Alt+ 方向键 →
	  alert("不准你使用ALT+方向键前进或后退网页！");
	  event.returnValue=false;
	  }
	  
	if ((event.ctrlKey)&&(event.keyCode==78)) //屏蔽 Ctrl+n
	    {alert("禁止新建窗口！");
	    event.returnValue=false;}
	if ((event.shiftKey)&&(event.keyCode==121)) //屏蔽 shift+F10
	    {alert("禁止使用该菜单！");
	    event.returnValue=false;}
	 if (window.event.srcElement.tagName == "A" && window.event.shiftKey)
	window.event.returnValue = false; //屏蔽 shift 加鼠标左键新开一网页
	*/
	if ((event.ctrlKey)&&(event.keyCode==81)) {  //CTRL+Q
		//alert(event.keyCode);
		//event.returnValue=false;
		hideshowAll();
	}

}

//打开日期参照
function getYearMonthDay(textDate,path){
	
	var returnAry;
	if(path == undefined) {
		path = "../../";
	}
	var defaultTime = jQuery("#"+textDate).val();
	var event = window.event?window.event:getYearMonthDay.caller.arguments[0];//i18n.date_reference
	var returnAry = window.showModalDialog(path + "js/calendar/calendar.jsp",defaultTime,"status:1;dialogHeight:230px;dialogWidth:300px;dialogTop: "+event.screenY+"px; dialogLeft: "+event.screenX+"px");
   
    if (!returnAry) {
       jQuery("#"+textDate).val(defaultTime);
	} else if (returnAry != null) {
        jQuery("#"+textDate).val(returnAry);
        jQuery("#"+textDate).trigger("change");
	}
}

function getYearMonth(textMonth, path) {
	if(path == undefined) {
		path = "../../";
	}
	path = path + "js/month/selectMonth.jsp";
	var strFeatures = "dialogWidth=150px;dialogHeight=126px;center=yes;location=0;resizable=0;titlebar=0;scrollbars=0;help=no";
	var defaultMonth = textMonth.value;
	var returnMonth = showModalDialog(path,defaultMonth,strFeatures);
	if(returnMonth == null) { 
		returnMonth="";
	}
	textMonth.value = returnMonth;
}

function getHourMinuteSecond(textMonth,path) {
	if(path == undefined) {
		path = "../../";
	}
	path = path + "js/time/hourMinuteSecond.jsp";
	strFeatures = "dialogWidth=224px;dialogHeight=126px;center=yes;location=0;resizable=0;titlebar=0;scrollbars=0;help=no";
	var defaultTime = jQuery("#"+textMonth).val();
	var returnMonth = showModalDialog(path,defaultTime,strFeatures);
	if(returnMonth != null) { 
		textMonth.value = returnMonth;
	}
}

function getYearMonthDayHourMinuteSecond(textMonth,path) {
	if(path == undefined) {
		path = "../../";
	}
	path = path + "js/time/yearMonthDayHourMinuteSecond.jsp";
	strFeatures = "dialogWidth=306px;dialogHeight=285px;center=yes;location=0;resizable=0;titlebar=0;scrollbars=0;help=no";
	var defaultTime = jQuery("#"+textMonth).val();
	var returnMonth = showModalDialog(path,defaultTime,strFeatures);
	if (!returnMonth) {
       jQuery("#"+textMonth).val(defaultTime);
    } else if (returnMonth != null) {
        jQuery("#"+textMonth).val(returnMonth);
    }
}

function getAddressList(textName, textValue, path){
	if(path == undefined) {
		path = "../../";
	}
	
	var retVal = window.showModalDialog(path + 'include/addresslist/addressListTree.htm','','dialogHeight=600px;dialogWidth=320px;');
	if(retVal != undefined && retVal!=null){
		document.all[textName].value=retVal;
	}
	else{
		document.all[textName].value="";
	}
}

function getAddressListValue(path){
	if(path == undefined) {
		path = "../../";
	}
	
	var retVal = window.showModalDialog(path + 'include/addresslist/addressListTree.htm','','dialogHeight=600px;dialogWidth=320px;');
	if(retVal == undefined || retVal == null) {
		retVal = "";	
	}
	return retVal;
}

function getInfoDialog(urlPath) {
	var width = 700;
	var height = 500;
	var xposition = 0; 
	var yposition = 0;
	if ((parseInt(navigator.appVersion) >= 4 )) {
		xposition = (screen.width - width) / 2;
		yposition = (screen.height - height) / 2;
	}
	var windowProperty = "width=" + width + "," 
		+ "height=" + height + ","
		+ "location=0," 
		+ "menubar=0,"
		+ "resizable=1,"
		+ "scrollbars=1,"
		+ "status=1," 
		+ "titlebar=0,"
		+ "toolbar=0,"
		+ "hotkeys=0,"
		+ "screenx=" + xposition + "," //Netscape
		+ "screeny=" + yposition + "," //Netscape
		+ "left=" + xposition + "," //IE
		+ "top=" + yposition; //IE
	getDialog(urlPath, "1", "", width, height, windowProperty);
}

function getDialog(urlPath, isModelessDialog, windowName, width, height, windowProperty){
	if(urlPath == undefined) {
		return null;	
	}
	if(isModelessDialog == undefined) {
		isModelessDialog = "0";	
	}
	if(windowName == undefined) {
		windowName = "myAnonymousWindow";	
	}
	if(width == undefined) {
		width = "400";	
	}
	if(height == undefined) {
		height = "300";	
	}
	var xposition = 0; 
	var yposition = 0;
	if ((parseInt(navigator.appVersion) >= 4 )) {
		xposition = (screen.width - width) / 2;
		yposition = (screen.height - height) / 2;
	}
	if(windowProperty == undefined) {
		windowProperty= "width=" + width + "," 
		+ "height=" + height + "," 
		+ "location=0," 
		+ "menubar=0,"
		+ "resizable=0,"
		+ "scrollbars=1,"
		+ "status=0," 
		+ "titlebar=0,"
		+ "toolbar=0,"
		+ "hotkeys=0,"
		+ "screenx=" + xposition + "," //Netscape
		+ "screeny=" + yposition + "," //Netscape
		+ "left=" + xposition + "," //IE
		+ "top=" + yposition; //IE 	
	}
	if(isModelessDialog == "1") {
		window.open( urlPath,windowName,windowProperty );
		return new Object();
	} else {
		var returnObj = window.showModalDialog(urlPath,windowName,windowProperty);
		if(returnObj != undefined) {
			return returnObj;	
		} else {
			return new Object();	
		}
	}
}

/**
 * 参照选择界面
 * @param inputArray input框名字
 * @param path 项目根地址
 * @param urlPath 参照的地址
 * @param inputType 选择类型，是多选还是单选 默认radio
 * @param width
 * @param height
 * @param refName 参照中文名字
 * @param isPublic 参照返回值方式，true时默认返回， false时根据选择情况自己书写返回方法 方法名为 toDoWriteReferencePrivate
 */
function getReference(inputArray, path, urlPath, inputType, width, height, refName, isPublic){
	var thisObj = event.srcElement;
	if(path == undefined) {
		path = "../../";
	}
	if(width == undefined) {
		width = 800;
	}
	if(height == undefined) {
		height = 600;
	}
	if(inputType == undefined){
		inputType = "radio";
	}
	if(inputType==""){
		inputType = "radio";
	}
	if(isPublic == undefined){
		isPublic = true;
	}
	var myObject = new Object();
	myObject['urlPath'] = urlPath + "&isInit=1&inputType=" + inputType;
	myObject['inputType'] = inputType;
	myObject['inputArray'] = inputArray;
	if(refName!=undefined){
		myObject['title'] = refName;
	}
	var rtObj = window.showModalDialog(path + 'jsp/include/globalReference.jsp', myObject,'dialogHeight=' + height + 'px;dialogWidth=' + width + 'px;');
	if(rtObj!=null){
		if(isPublic){
			toDoWriteReference(inputArray, rtObj, thisObj);
		}else{
			toDoWriteReferencePrivate(inputArray, rtObj);
		}	
	}
}

function toDoWriteReference(inputArray, rtObj, thisObj) {
	var obj = document.getElementsByName(inputArray[0]);
	var objArray = new Array();
	if(obj!=undefined && obj.length>1){
		var thisTrObj = null;
		while(thisObj.parentElement.tagName.toLowerCase() != "table"){
			thisObj = thisObj.parentElement;
			if(thisObj.tagName.toLowerCase() == "tr"){
				thisTrObj = thisObj;
			}
		}
		thisObj = thisObj.parentElement;
		if(thisTrObj!=null){ //判断是那个对象
			var rowNo = thisTrObj.rowIndex;
			for(var k=0; k<thisObj.rows.length; k++){
				if(thisObj.rows[k].cells[0].tagName.toLowerCase()=="th"){
					rowNo--
				}else{
					break;
				}
			}
			for(var k=0; k<inputArray.length; k++){
				objArray[k] = document.getElementsByName(inputArray[k])[rowNo];
			}
		}
	}else{
		for(var k=0; k<inputArray.length; k++){
			objArray[k] = document.getElementById(inputArray[k]);
		}
	}
	
	if(objArray[0].valueName==undefined){
		var newTd = document.createElement("td");
		newTd.innerHTML = rtObj[0];
		var inputObj = newTd.firstChild;
		objArray[0].value = inputObj['value'];
		objArray[1].value = inputObj['displayName'];
		if(objArray.length>=3){
			objArray[2].value = inputObj['code'];
		}
		for(var j=1; j<rtObj.length; j++) {
			newTd = document.createElement("td");
			newTd.innerHTML = rtObj[j];
			inputObj = newTd.firstChild;
			objArray[0].value += "," + inputObj['value'];
			objArray[1].value += "," + inputObj['displayName'];
			if(objArray.length>=3){
				objArray[2].value += "," + inputObj['code'];						
			}
		}
	}else{
		var newTd = document.createElement("td");
		newTd.innerHTML = rtObj[0];
		var inputObj = newTd.firstChild;
		for(var i=0; i<objArray.length; i++){
			objArray[i].value = inputObj[objArray[i].valueName];
		}
		for(var j=1; j<rtObj.length; j++) {
			newTd = document.createElement("td");
			newTd.innerHTML = rtObj[j];
			inputObj = newTd.firstChild;
			for(var i=0; i<objArray.length; i++){
				objArray[i].value += "," + inputObj[objArray[i].valueName];
			}
		}
	}
}

function getUploadWindow(inputArray, path, width, height){
	if(path == undefined) {
		path = "../../";
	}
	if(width == undefined) {
		width = 800;
	}
	if(height == undefined) {
		height = 600;
	}
	var myObject = new Object();
	try {
		if(inputArray.length >= 2) {
			if(inputArray[0].value != null && inputArray[0].value != "" && inputArray[1].value != null && inputArray[1].value != "") {
				var oldData = new Array();
				var aSaveName = inputArray[0].value.split(",");
				var aOldName = inputArray[1].value.split(",");
				var aIsImage = inputArray.length >= 3 ? inputArray[2].value.split(",") : null;
				var aAbbreviatoryName = inputArray.length >= 4 ? inputArray[3].value.split(",") : null;
				var aOldWidthHeight = inputArray.length >= 5 ? inputArray[4].value.split(",") : null;
				var aAbbreviatoryWidthHeight = inputArray.length >= 6 ? inputArray[5].value.split(",") : null;
				var aUploadRemark = inputArray.length >= 7 ? inputArray[6].value.split(",") : null;

				for(var i=0; i<aOldName.length; i++) {
					var tempArray = new Array();
					tempArray[0] = aSaveName[i];
					tempArray[1] = aOldName[i];
					tempArray[2] = (aIsImage == null) ? "" : aIsImage[i];
					tempArray[3] = aAbbreviatoryName == null ? "" : aAbbreviatoryName[i];
					tempArray[4] = aOldWidthHeight == null ? "" : aOldWidthHeight[i];
					tempArray[5] = aAbbreviatoryWidthHeight == null ? "" : aAbbreviatoryWidthHeight[i];
					tempArray[6] = aUploadRemark == null ? "" : aUploadRemark[i];
					oldData[oldData.length] = tempArray;
				}
				myObject.oldData = oldData;
			}
		}
	} catch(e) {
		//alert("a  0:" + e.message);
	}
	var rtObj = window.showModalDialog(path + 'jsp/include/upload/globalUpload.jsp', myObject,'dialogHeight=' + height + 'px;dialogWidth=' + width + 'px;');
	toDoWriteUpload(inputArray, rtObj);
}

function toDoWriteUpload(inputArray, rtObj) {
	var textValue = inputArray[0];
	var textName = inputArray[1];
	var textIsImage = (inputArray.length >= 3) ? inputArray[2] : null;
	var textAbbreviatoryName = inputArray.length >= 4 ? inputArray[3] : null;
	var textSaveNameWidthHeight = inputArray.length >= 5 ? inputArray[4] : null;
	var textAbbreviatoryNameWidthHeight = inputArray.length >= 6 ? inputArray[5] : null;
	var textUploadRemark = inputArray.length >= 7 ? inputArray[6] : null;
	
	var value1 = "";
	var name1 = "";
	var isImage1 = "";
	var abbreviatoryName = "";
	var saveNameWidthHeight = "";
	var abbreviatoryNameWidthHeight = "";
	var uploadRemark = "";
	
	if(rtObj != undefined && rtObj!=null){
		for(var i=0; i<rtObj.length-1; i++) {
			value1 += rtObj[i][0] + ",";
			name1 += rtObj[i][1] + ",";
			isImage1 += rtObj[i][2] + ",";
			abbreviatoryName += rtObj[i][3] + ",";
			saveNameWidthHeight += rtObj[i][4] + ",";
			abbreviatoryNameWidthHeight += rtObj[i][5] + ",";
			uploadRemark += rtObj[i][6] + ",";
		}
		value1 += rtObj[rtObj.length-1][0];
		name1 += rtObj[rtObj.length-1][1];
		isImage1 += rtObj[rtObj.length-1][2];
		abbreviatoryName += rtObj[rtObj.length-1][3];
		saveNameWidthHeight += rtObj[rtObj.length-1][4];
		abbreviatoryNameWidthHeight += rtObj[rtObj.length-1][5];
		uploadRemark += rtObj[rtObj.length-1][6];

		textValue.value=value1;
		textName.value=name1;
		if(textIsImage != null) {
			textIsImage.value = isImage1;
		}
		if(textAbbreviatoryName != null) {
			textAbbreviatoryName.value = abbreviatoryName;
		}
		if(textSaveNameWidthHeight != null) {
			textSaveNameWidthHeight.value = saveNameWidthHeight;
		}
		if(textAbbreviatoryNameWidthHeight != null) {
			textAbbreviatoryNameWidthHeight.value = abbreviatoryNameWidthHeight;
		}
		if(textUploadRemark != null) {
			textUploadRemark.value = uploadRemark;
		}
	}
}

function getUploadWindowWithObj(inputObj, path, width, height){
	if(path == undefined) {
		path = "../../";
	}
	if(width == undefined) {
		width = 700;
	}
	if(height == undefined) {
		height = 500;
	}
	var myObject = new Object();
	myObject.oldData = inputObj;
	var rtObj = window.showModalDialog(path + 'jsp/include/upload/globalUpload.jsp', myObject,'dialogHeight=' + height + 'px;dialogWidth=' + width + 'px;');
	return rtObj;
}

function getChannelTree(inputArray, path, width, height, deeptreePath){
	if(path == undefined) {
		path = "../../";
	}
	if(width == undefined) {
		width = 700;
	}
	if(height == undefined) {
		height = 500;
	}
	if(deeptreePath == undefined) {
		deeptreePath = path + 'jsp/include/deeptree/deeptree_channel.jsp?inputType=radio&rootXmlSource=/oa/jsp/oa/channel/buildChannelTree.jsp';
	}
	var myObject = new Object();
	var rtObj = window.showModalDialog(deeptreePath, myObject,'dialogHeight=' + height + 'px;dialogWidth=' + width + 'px;');
	toDoWriteChannelTree(inputArray, rtObj);
}

function toDoWriteChannelTree(inputArray, rtObj) {
	var textValue = inputArray[0];
	var textName = inputArray[1];
	if(rtObj != undefined && rtObj.length > 0){
		var allTextValue = "";
		var allTextName = "";
		for(var i=0; i<rtObj.length-1; i++) {
			allTextValue += rtObj[i]['returnValue'] + ",";
			allTextName += rtObj[i]['childName'] + ",";
		}
		allTextValue += rtObj[rtObj.length-1]['returnValue'];
		allTextName += rtObj[rtObj.length-1]['childName'];
		textValue.value = allTextValue;
		textName.value = allTextName;
	} else {
	}
}

function getPartyWindow(inputArray, path, urlPath, width, height)
{
	if(path == undefined) {
		path = "../../";
	}
	if(width == undefined) {
		width = 700;
	}
	if(height == undefined) {
		height = 500;
	}

	var myObject = new Object();
	myObject.oldData = inputArray;

	var myObject = new Object();
	var rtObj = window.showModalDialog(urlPath, myObject, 'dialogHeight=' + height + 'px;dialogWidth=' + width + 'px;');
	toDoWritePartyWindow(inputArray, rtObj);
}

function toDoWritePartyWindow(inputArray, rtObj) {
	var textValue = inputArray[0];
	var textName = inputArray[1];
	if(rtObj != undefined){
		textValue.value = rtObj[0];
		textName.value = rtObj[1];
	} else {
	}
	//alert(textValue.name + "=" + textValue.value + "\n" + textName.name + "=" + textName.value);
}

function getRandomString() {
	return "uniqueRandomNumber=" + Math.random();
}

function getConfirm(msg) {  //确认选择框
  	if(msg == undefined || msg == null || msg=="") {
		msg = i18n.confirm_to_do_this;
	}
  	if(confirm(msg)) {
  		return true;
  	} else {
  		return false;
  	}
}

//写表的顶部
function writeTableTop_new_zyjc(pageTitle, path) {
	if(pageTitle == undefined) {
		pageTitle = i18n.demo_page;
	}
	if(path == undefined) {
		path = "../../";
	}
	document.write("<table width='99%'  border='0' align='center' cellpadding='0' cellspacing='0' class='title1'>");
	document.write("  <tr>");
	document.write("		<td width='10'><img src='" + path + "images/con_top_01.jpg' width='10' height='31'></td>");
	document.write("		<td background='" + path + "images/con_top_bg.jpg'>" + pageTitle + "</td>");
	document.write("		<td width='10'><img src='" + path + "images/con_top_02.jpg' width='10' height='31'></td>");
	document.write("  </tr>");
	document.write("  <tr>");
	document.write("    <td background='" + path + "images/con_left_01.jpg'>&nbsp;</td>");
	document.write("    <td valign='top' bgcolor='#FFFFFF'>");
}

//写表的底部
function writeTableBottom_new_zyjc(path) {
	if(path == undefined) {
		path = "../../";
	}
	document.write("	</td><td background='" + path + "images/con_rig_01.jpg'>&nbsp;</td>");
	document.write("  </tr>");
	document.write("  <tr>");
	document.write("    <td><img src='" + path + "images/con_dif_01.jpg' width='10' height='14'></td>");
	document.write("    <td background='" + path + "images/con_dif_03.jpg'>&nbsp;</td>");
	document.write("    <td><img src='" + path + "images/con_dif_02.jpg' width='10' height='14'></td>");
	document.write("  </tr>");
	document.write("</table>");
}

//写表的顶部
function writeTableTop_wangtong(pageTitle, path) {
	if(pageTitle == undefined) {
		pageTitle = i18n.demo_page;
	}
	if(path == undefined) {
		path = "../../";
	}
	document.write("<table width='100%' height='100%' border='0' cellpadding='0' cellspacing='0'>                                                                 				");																																																																																																																																																												
	document.write("   <tr>                                                                                                                                    			");																																																																																																																																																													
	document.write("     <td width='14' valign='top' height='29'><img src='" + path + "images/con_t_left.jpg' width='14' height='29'></td>                                                    			");																																																																																																																																																													
	document.write("    <td background='" + path + "images/con_t_tabg.jpg' valign='top'><table  border='0' cellpadding='0' cellspacing='0' background='" + path + "images/con_t_bg.jpg'>			");																																																																																																																																																													
	document.write("      <tr>                                                                                                                                 			");																																																																																																																																																													
	document.write("        <td valign='top'><img src='" + path + "images/con_t_m.jpg' width='13' height='29'></td>                                                               			");																																																																																																																																																													
	document.write("        <td class='2bt'>" + pageTitle + "</td>                                                                                                         			");																																																																																																																																																													
	document.write("        <td width='41' valign='top'><img src='" + path + "images/con_t_c.jpg' width='41' height='29'></td>                                                    			");																																																																																																																																																													
	document.write("      </tr>                                                                                                                                			");																																																																																																																																																													
	document.write("    </table></td>                                                                                                                          			");																																																																																																																																																													
	document.write("    <td width='15' valign='top'><img src='" + path + "images/con_t_rig.jpg' width='15' height='29'></td>                                                      			");																																																																																																																																																													
	document.write("  </tr>                                                                                                                                    			");																																																																																																																																																													
	document.write("   <tr>                                                                                                                                    			");																																																																																																																																																													
	document.write("    <td background='" + path + "images/con_l_bg.jpg'>&nbsp;</td>                                                                                 			");																																																																																																																																																													
	document.write("    <td bgcolor='#EDF4FD'>                                                                                                                 			");																																																																																																																																																																									
	document.write("<table width='100%' height='100%'  border='0' cellpadding='6' cellspacing='1'>											");																																																																																																																											
	document.write("       <tr>                                                                   											");																																																																																																																											
	document.write("         <td valign='top'> ");	
}

function writeTableTop(pageTitle, path) {
	if(pageTitle == undefined) {
		pageTitle = i18n.demo_page;
	}
	if(path == undefined) {
		path = "../../";
	}
	document.write("<table width='100%' height='100%' border='0' cellpadding='0' cellspacing='0'>                                                                 				");																																																																																																																																																												
	document.write("   <tr>                                                                                                                                    			");																																																																																																																																																													
	document.write("     <td background='" + path + "images/templatestyle/main0_04.jpg' valign='top' height='45'><table  border='0' width='100%' cellpadding='0' cellspacing='0' background='" + path + "images/templatestyle/main0_04.jpg'>			");																																																																																																																																																													
	document.write("      <tr>                                                                                                                                 			");																																																																																																																																																													
	document.write("         <td width='420' valign='top' class='maintopbg01'><table border='0' cellspacing='0' cellpadding='0'>  ");																																																																																																																																																													
	document.write("      <tr>                                                                                                                                 			");																																																																																																																																																													
	document.write("         <td height='8' colspan='4'></td>  ");																																																																																																																																																													
	document.write("      </tr>                                                                                                                                    			");																																																																																																																																																													
	document.write("      <tr>                                                                                                                                 			");																																																																																																																																																													
	document.write("        <td width='32'>&nbsp;</td>                                                                                                       			");																																																																																																																																																													
	document.write("        <td width='11'><img src='" + path + "images/templatestyle/maintop_title_01.gif' width='11' height='34' /></td>                                                    			");																																																																																																																																																													
	document.write("        <td width='118'  background='" + path + "images/templatestyle/maintop_title_03.gif'><table width='118' height='34' border='0' cellpadding='0' cellspacing='0' class='maintop_title_bg'>                                                    			");																																																																																																																																																													
	document.write("           <tr>                                                                                                                                 			");																																																																																																																																																													
	document.write("           <td width='22'><img src='" + path + "images/templatestyle/001.gif' width='23' height='22'></td>             ");																																																																																																																																																													
	document.write("           <td nowrap='nowrap' class='maintop_title_text'>" + pageTitle + "</td>             ");																																																																																																																																																													
	document.write("           </tr>                                                                                                                                    			");																																																																																																																																																													
	document.write("           </table></td>                                                                                                                          			");																																																																																																																																																													
	document.write("        <td width='13' align='right'><img src='" + path + "images/templatestyle/maintop_title_04.gif' width='13' height='34' /></td>                                                    			");																																																																																																																																																													
	document.write("      </tr>                                                                                                                                			");																																																																																																																																																													
	document.write("    </table></td>                                                                                                                          			");																																																																																																																																																													
	document.write("    <td><img src='" + path + "images/templatestyle/main0_04.jpg' width='2' height='45' /></td>                                                    			");																																																																																																																																																													
	document.write("    <td width='403' align='right' class='maintopbg02'>&nbsp;</td>                                                    			");																																																																																																																																																													
	document.write("    </tr>                                                                                                                                			");																																																																																																																																																													
	document.write("    </table></td>                                                                                                                          			");																																																																																																																																																													
	document.write("  </tr>                                                                                                                                    			");																																																																																																																																																													
	document.write("   <tr>                                                                                                                                    			");																																																																																																																																																													
	document.write("    <td bgcolor=''>                                                                                                                 			");																																																																																																																																																																									
	document.write("     <table width='96%' height='100%'  border='0' cellpadding='0' cellspacing='1' align='center'>											");																																																																																																																											
	document.write("       <tr>                                                                   											");																																																																																																																											
	document.write("         <td valign='top'> ");	
}

function writeTableBottom(path) {
	if(path == undefined) {
		path = "../../";
	}
	document.write("        </td>");
	document.write("      </tr>");
	document.write("    </table>");
	document.write("</td>");
	document.write("   </tr>");
	document.write("   <tr>");
	document.write("     <td valign='bottom'  height='11'><table width='100%' border='0' cellpadding='0' cellspacing='0'>");
	document.write("        <tr>");
	document.write("          <td width='11' valign='bottom'><img src='" + path + "/images/templatestyle/main0_15.gif' width='11' height='11'></td>    ");
	document.write("          <td>&nbsp;&nbsp;</td>    ");
	document.write("          <td width='11' align='right' valign='bottom'><img src='" + path + "/images/templatestyle/main0_18.gif' width='11' height='11'></td>    ");
	document.write("         </tr>");
	document.write("       </table></td>");	
	document.write("   </tr>");
	document.write(" </table>");	
}

//写表的底部
function writeTableBottom_wangtong(path) {
	if(path == undefined) {
		path = "../../";
	}
	document.write("</td>");
	document.write("</tr>");
	document.write("</table>");
	document.write("</td>");
	document.write("     <td background='" + path + "images/con_t_rg.jpg'>&nbsp;</td>");
	document.write("   </tr>");
	document.write("   <tr>");
	document.write("     <td valign='bottom' background='" + path + "/images/con_l_bg.jpg'><img src='" + path + "images/con_d_left.jpg' width='14' height='15'></td>");
	document.write("     <td height='15' background='" + path + "images/con_d_ng.jpg'>&nbsp;</td>");
	document.write("     <td valign='bottom' background='" + path + "/images/con_t_rg.jpg'><img src='" + path + "images/con_d_rig.jpg' width='15' height='15'></td>");
	document.write("   </tr>");
	document.write(" </table>");	
}

//写位置页面的导航位置信息
function writePlaceInfo(palceInfoValue) {
	if(parent == undefined || parent == null || parent.placeFrame == undefined || parent.placeFrame == null)
		return false;
	var thisObj = parent.placeFrame.document.getElementById("placeInfo");
	if(thisObj != undefined && thisObj != null) {
		thisObj.innerHTML = palceInfoValue;	
	}
}

function writeInfoToId(fontId, info) {
	var remainPromptFont;
	if(document.getElementById)
		remainPromptFont = document.getElementById(fontId);
	if(remainPromptFont != null)
		remainPromptFont.innerHTML = info;
}

//把某个div合起来或展开
function hideshow(which,img,path,isClosed) {
	if(path == undefined) {
		path = "../../";
	}
	if (!document.getElementById|document.all) {
		return;
	}
	else {
		if (document.getElementById) {
			oWhich = eval ("document.getElementById('" + which + "')")
		} else {
			oWhich = eval ("document.all." + which)
		}
	}

	window.focus()
	if(oWhich != null) {
		if (isClosed || oWhich.style.display=="none") {
			oWhich.style.display="";
			if(img!=undefined)
				img.src=path + "/images/icon/07-0.gif";
		}
		else {
			oWhich.style.display="none";
			if(img!=undefined)img.src=path + "/images/icon/07.gif";
		}		
	}
}

//把所有div合起来或展开
function hideshowAll()
{
	var div_s = document.getElementsByTagName("DIV"); 
	var flag;
	flag = true;
	var isClosed; //是否是关闭的
	for(i=0;i<div_s.length;i++)
	{
		var sId = div_s[i].id;
		if(sId.indexOf("ccParent") != -1 )
		{
			var sChildId = "ccChild" + sId.substr("ccParent".length);
			//alert(sChildId);
			var imgs = div_s[i].getElementsByTagName("IMG"); 
			if(imgs.length !=1) continue;
			var img_src = imgs[0].src;
			if(flag) //检查第一个的状态 
			{
				isClosed = (img_src.indexOf("07-0.gif") == -1);
				flag = !flag;
			}
			hideshow(sChildId,imgs[0],isClosed);
		}
	}
}

//初始化展开或合上
function initccExpandible() {
	//hideshow('ccChild0')
	hideshow('ccChild1')
	hideshow('ccChild2')
	hideshow('ccChild3')
	//hideshow('ccChild4')
	//hideshow('ccChild5')
}


function getObjectByNameRecursive(thisObj, thisName) {  //从thisObj递归的取出name是thisName的对象
		var rtHiddenInput = null;  //定义返回的变量
		for(var i=0; i<thisObj.childNodes.length; i++) {  //循环thisObj的子节点
			var tempObj = thisObj.childNodes[i];  //定义当前临时节点
			if(jQuery(tempObj).attr("signName") == thisName) {  //如果当前临时节点的name等于thisName
				rtHiddenInput = tempObj;  //当前临时节点就是返回的对象
				break;  //完成了目标对象的查询,退出循环
			} else {
				rtHiddenInput = getObjectByNameRecursive(tempObj, thisName);  //递归的找自己的子节点
				if(rtHiddenInput != null) {  //如果返回值不为空
					break;  //完成了目标对象的查询,退出循环
				}
			}
		}
		return rtHiddenInput;
	}

//系统导航条
function MM_preloadImages() { //v3.0
  var d=document; if(d.images){ if(!d.MM_p) d.MM_p=new Array();
    var i,j=d.MM_p.length,a=MM_preloadImages.arguments; for(i=0; i<a.length; i++)
    if (a[i].indexOf("#")!=0){ d.MM_p[j]=new Image; d.MM_p[j++].src=a[i];}}
}

function MM_findObj(n, d) { //v4.01
  var p,i,x;  if(!d) d=document; if((p=n.indexOf("?"))>0&&parent.frames.length) {
    d=parent.frames[n.substring(p+1)].document; n=n.substring(0,p);}
  if(!(x=d[n])&&d.all) x=d.all[n]; for (i=0;!x&&i<d.forms.length;i++) x=d.forms[i][n];
  for(i=0;!x&&d.layers&&i<d.layers.length;i++) x=MM_findObj(n,d.layers[i].document);
  if(!x && d.getElementById) x=d.getElementById(n); return x;
}

function MM_nbGroup(event, grpName) { //v6.0
  var i,img,nbArr,args=MM_nbGroup.arguments;
  if (event == "init" && args.length > 2) {
    if ((img = MM_findObj(args[2])) != null && !img.MM_init) {
      img.MM_init = true; img.MM_up = args[3]; img.MM_dn = img.src;
      if ((nbArr = document[grpName]) == null) nbArr = document[grpName] = new Array();
      nbArr[nbArr.length] = img;
      for (i=4; i < args.length-1; i+=2) if ((img = MM_findObj(args[i])) != null) {
        if (!img.MM_up) img.MM_up = img.src;
        img.src = img.MM_dn = args[i+1];
        nbArr[nbArr.length] = img;
    } }
  } else if (event == "over") {
    document.MM_nbOver = nbArr = new Array();
    for (i=1; i < args.length-1; i+=3) if ((img = MM_findObj(args[i])) != null) {
      if (!img.MM_up) img.MM_up = img.src;
      img.src = (img.MM_dn && args[i+2]) ? args[i+2] : ((args[i+1])? args[i+1] : img.MM_up);
      nbArr[nbArr.length] = img;
    }
  } else if (event == "out" ) {
    for (i=0; i < document.MM_nbOver.length; i++) {
      img = document.MM_nbOver[i]; img.src = (img.MM_dn) ? img.MM_dn : img.MM_up; }
  } else if (event == "down") {
    nbArr = document[grpName];
    if (nbArr)
      for (i=0; i < nbArr.length; i++) { img=nbArr[i]; img.src = img.MM_up; img.MM_dn = 0; }
    document[grpName] = nbArr = new Array();
    for (i=2; i < args.length-1; i+=2) if ((img = MM_findObj(args[i])) != null) {
      if (!img.MM_up) img.MM_up = img.src;
      img.src = img.MM_dn = (args[i+1])? args[i+1] : img.MM_up;
      nbArr[nbArr.length] = img;
  } }
}


function MM_swapImgRestore() { //v3.0
  var i,x,a=document.MM_sr; for(i=0;a&&i<a.length&&(x=a[i])&&x.oSrc;i++) x.src=x.oSrc;
}

function MM_swapImage() { //v3.0
  var i,j=0,x,a=MM_swapImage.arguments; document.MM_sr=new Array; for(i=0;i<(a.length-2);i+=3)
   if ((x=MM_findObj(a[i]))!=null){document.MM_sr[j++]=x; if(!x.oSrc) x.oSrc=x.src; x.src=a[i+2];}
}



//以下是识别不同浏览器
// work around bug in xpcdom Mozilla 0.9.1
//window.saveNavigator = window.navigator;

// Handy functions
function noop() {}
function noerror() { return true; }

function defaultOnError(msg, url, line)
{
	// customize this for your site
	if (top.location.href.indexOf('_files/errors/') == -1)
		top.location = '/evangelism/xbProjects/_files/errors/index.html?msg=' + escape(msg) + '&url=' + escape(url) + '&line=' + escape(line);
}

// Display Error page...  
// more work to be done here
//
function reportError(message)
{
	// customize this for your site
	if (top.location.href.indexOf('_files/errors/') == -1)
		top.location = '/evangelism/xbProjects/_files/errors/index.html?msg=' + escape(message);
}

function pageRequires(cond, msg, redirectTo)
{
	if (!cond)
	{
		msg = 'This page requires ' + msg;
		top.location = redirectTo + '?msg=' + escape(msg);
	}
	// return cond so can use in <A> onclick handlers to exclude browsers
	// from pages they do not support.
	return cond;
}

function detectBrowser()
{
	var oldOnError = window.onerror;
	var element = null;
	
	window.onerror = defaultOnError;

	navigator.OS		= '';
	navigator.version	= 0;
	navigator.org		= '';
	navigator.family	= '';

	var platform;
	if (typeof(window.navigator.platform) != 'undefined')
	{
		platform = window.navigator.platform.toLowerCase();
		if (platform.indexOf('win') != -1)
			navigator.OS = 'win';
		else if (platform.indexOf('mac') != -1)
			navigator.OS = 'mac';
		else if (platform.indexOf('unix') != -1 || platform.indexOf('linux') != -1 || platform.indexOf('sun') != -1)
			navigator.OS = 'nix';
	}

	var i = 0;
	var ua = window.navigator.userAgent.toLowerCase();
	
	if (ua.indexOf('opera') != -1)
	{
		i = ua.indexOf('opera');
		navigator.family	= 'opera';
		navigator.org		= 'opera';
		navigator.version	= parseFloat('0' + ua.substr(i+6), 10);
	}
	else if ((i = ua.indexOf('msie')) != -1)
	{
		navigator.org		= 'microsoft';
		navigator.version	= parseFloat('0' + ua.substr(i+5), 10);
		
		if (navigator.version < 4)
			navigator.family = 'ie3';
		else
			navigator.family = 'ie4'
	}
	else if (typeof(window.controllers) != 'undefined' && typeof(window.locationbar) != 'undefined')
	{
		i = ua.lastIndexOf('/')
		navigator.version = parseFloat('0' + ua.substr(i+1), 10);
		navigator.family = 'gecko';

		if (ua.indexOf('netscape') != -1)
			navigator.org = 'netscape';
		else if (ua.indexOf('compuserve') != -1)
			navigator.org = 'compuserve';
		else
			navigator.org = 'mozilla';
	}
	else if ((ua.indexOf('mozilla') !=-1) && (ua.indexOf('spoofer')==-1) && (ua.indexOf('compatible') == -1) && (ua.indexOf('opera')==-1)&& (ua.indexOf('webtv')==-1) && (ua.indexOf('hotjava')==-1))
	{
	    var is_major = parseFloat(navigator.appVersion);
    
		if (is_major < 4)
			navigator.version = is_major;
		else
		{
			i = ua.lastIndexOf('/')
			navigator.version = parseFloat('0' + ua.substr(i+1), 10);
		}
		navigator.org = 'netscape';
		navigator.family = 'nn' + parseInt(navigator.appVersion);
	}
	else if ((i = ua.indexOf('aol')) != -1 )
	{
		// aol
		navigator.family	= 'aol';
		navigator.org		= 'aol';
		navigator.version	= parseFloat('0' + ua.substr(i+4), 10);
	}

	navigator.DOMCORE1	= (typeof(document.getElementsByTagName) != 'undefined' && typeof(document.createElement) != 'undefined');
	navigator.DOMCORE2	= (navigator.DOMCORE1 && typeof(document.getElementById) != 'undefined' && typeof(document.createElementNS) != 'undefined');
	navigator.DOMHTML	= (navigator.DOMCORE1 && typeof(document.getElementById) != 'undefined');
	navigator.DOMCSS1	= ( (navigator.family == 'gecko') || (navigator.family == 'ie4') );

	navigator.DOMCSS2   = false;
	if (navigator.DOMCORE1)
	{
		element = document.createElement('p');
		navigator.DOMCSS2 = (typeof(element.style) == 'object');
	}

	navigator.DOMEVENTS	= (typeof(document.createEvent) != 'undefined');

	window.onerror = oldOnError;
}

//移动选择框中的列表项（option），要求：列表项的value值从0开始，依次增加
//参数selectName:选择框的名称，moveType：移动类型——top置首，up向上，down向下，bottom置底
function moveSelect_onClick(selectObj,moveType) {
	var sel = selectObj;
	var val = parseInt(sel.value,10);
	
	if(moveType=="top") {
		if(val==0) {
			alert(i18n.already_first);
			return false;
		}
		var temp = sel[val].text;
		for(var i=val;i>0;i--) {
			sel[i].text = sel[i-1].text;
		}
		sel[0].text = temp;
		sel.selectedIndex = 0;
	}
	
	if(moveType=="up") {
		if(val==0) {
			alert(i18n.already_first);
			return false;
		}
		var temp = sel[val-1].text;
		sel[val-1].text = sel[val].text;
		sel[val].text = temp;
		sel.selectedIndex = val-1;
	}
	
	if(moveType=="down") {
		if(val>=sel.size-1) {
			alert(i18n.already_last);
			return false;
		}
		var temp = sel[val+1].text;
		sel[val+1].text = sel[val].text;
		sel[val].text = temp;
		sel.selectedIndex = val+1;
	}
	
	if(moveType=="bottom") {
		if(val>=sel.size-1) {
			alert(i18n.already_last);
			return false;
		}
		var temp = sel[val].text;
		for(var i=val;i<sel.size-1;i++) {
			sel[i].text = sel[i+1].text;
		}
		sel[sel.size-1].text = temp;
		sel.selectedIndex = sel.size-1;
	}
}

detectBrowser();

//回写表单
function writeBackValue(inputName) {
	if(form.elements[inputName] == undefined) {
		return false;
	}
	if(form.elements[inputName].value != undefined) {  //如果有value属性，直接赋值
		form.elements[inputName].value = mForm[inputName];	
	} 
	if(form.elements[inputName].length != undefined ) {  //没有value属性
		var thisValue = mForm[inputName];
		if(mForm[inputName][0] == undefined) {
			thisValue = new Array();
			thisValue[thisValue.length] = mForm[inputName];		
		}
		if(form.elements[inputName].length != null) {  //length不为空
			var tempLength = form.elements[inputName].length;
			for(var j=0; j<tempLength; j++) {
				var thisObj = form.elements[inputName][j];
				for(var k=0; k<thisValue.length; k++) {
					if(thisObj.value == thisValue[k]) {  //如有选中，继续循环
						if( thisObj.checked != undefined) {
							thisObj.checked = true;	
							break;									
						} else if( thisObj.selected != undefined) {
							thisObj.selected = true;								
							break;
						}
					} else {                             //如没有选中，察看下一个
						if( thisObj.checked != undefined) {
							thisObj.checked = false;	
						} else if( thisObj.selected != undefined) {
							thisObj.selected = false;								
							}	
						}
					}
				}
			} 	
						
		}
}
	  
function getFormValue(inputObjString) {  //获取表单value
  	var inputObj = form.elements[inputObjString];
  	if(inputObj == undefined) {
  		//alert(inputObjString + '不是有效的输入表单');
  		return false;
  	}
  	if(inputObj.value != undefined) {
  		return inputObj.value;
  	} else if(inputObj.length != undefined){
  		if(inputObj.length != null) {
  			var rtValue = "";
  			for(var i=0; i<inputObj.length; i++) {
  				if(inputObj[i].checked) {
  					if(rtValue == "") {
	  					rtValue += inputObj[i].value;  					
  					} else {
  						rtValue += "," + inputObj[i].value; 
  					}

  				}
  			}
  			return rtValue;
  		} else {
  			return "";
  		}
  	}
}
  
function moveList_onClick(from,to) {
  	var fromList = from;
	var fromLen = fromList.options.length;
	
	var toList = to;
	var toLen = toList.options.length;
	
	var current = fromList.selectedIndex;
	while(current > -1) {
		var o = fromList.options[current];
		var t = o.text;
		var v = o.value;
		var optionName = new Option(t,v,false,false);
		toList.options[toLen] = optionName;
		toLen ++;
		fromList.options[current] = null;
		current = fromList.selectedIndex;
	}
}

function arrayHasString(tempArray, myString) {
	var returnMyValue = false;
	for(var i=0; i<tempArray.length; i++) {
		if(tempArray[i] == myString) {
			returnMyValue = true;
		}
	}
	return returnMyValue;
}

function pushCondition(myArray, pageRealValue, operate1, operate2, thisField) {  //压入查询条件
	if(operate1 == undefined) {
		operate1 = " like '%";
		operate2 = "%' ";
	} else if(operate2 == undefined) {
		operate2 = "";
	}
	if(thisField == undefined) {
		thisField = pageRealValue;
	}
	if(pageRealValue == null || pageRealValue == "" || getFormValue(pageRealValue) == "") {
		return false;
	}
	myArray.push(" " + thisField + " " + operate1 + getFormValue(pageRealValue) + operate2);
}
function returnBack() { //兼容IE，FF，解决使用iframe执行history.go(-1)函数无法返回上级页面的问题
    if(-[1,]){  //非IE浏览器
        var referUrl = document.referrer;
        window.location.href = referUrl;  
        return false;  
    }else{  
        history.go(-1);  
    }     
}

/**
 * 打开一个模式对话窗口
 */
function openDialogWindow(strUrl,iWidth,iHeight,strTitle){
	var objWindow = new Object();
	objWindow.url = strUrl;
	objWindow.title = strTitle;
	var windowFeatures = "dialogWidth:"+ iWidth +";dialogHeight:"+ iHeight +";center:1;help:0;resizable:0;scroll:no;status:0;";
	var returnObj = window.showModalDialog(dir_base +"/jsp/business/pub/openWindowUI.jsp",objWindow,windowFeatures);
	return returnObj;
}

/**
 * 打印一个模式对话窗口，传递objWindow对象，可以传递除url以外的其它属性，加强了窗口间的交换
 */
function openDialogWindowByObj(objWindow,iWidth,iHeight){
	var windowFeatures = "dialogWidth:"+ iWidth +";dialogHeight:"+ iHeight +";center:1;help:0;resizable:0;scroll:no;status:0;";
	var returnObj = window.showModalDialog(dir_base + "/jsp/business/pub/openWindowUI.jsp",objWindow,windowFeatures);
	return returnObj;
}

function commafy(num, isAmt) 
{ 
	var value = num.toString();
	value = value.replace(/(\d)(?=(\d{3})+(?!\d))/g, "$1,");
	var strArr = value.split(".");
	if(value.indexOf(".")==-1){
		if(isAmt){
			return value+".00";
		}else{
			return value+".000";
		}
	}else{
		if(isAmt){
			if(strArr[1].length==1){
				return value+"0";
			}
			if(strArr[1].length==2){
				return value;
			}
		}else{
			if(strArr[1].length==1){
				return value+"00";
			}
			if(strArr[1].length==2){
				return value+"0";
			}
			if(strArr[1].length==3){
				return value;
			}
		}
		
	}
} 

function formatQuantity(myvalue, isDeftZero){
	if(myvalue==""){
		if(isDeftZero){
			return "0.000";
		}else{
			return "";
		}
	}else{
		var value = new BigDecimal(myvalue);
		return commafy(value, false);
	}
}


function formatAmt(myvalue, isDeftZero){
	if(myvalue==""){
		if(isDeftZero){
			return "0.00";
		}else{
			return "";
		}
	}else{
		var value = new BigDecimal(myvalue);
		return commafy(value, true);
	}
}

function formatInteger(myvalue, isDeftZero){
	if(myvalue==""){
		if(isDeftZero){
			return "0";
		}else{
			return "";
		}
	}else{
		var strValue = parseFloat(myvalue).toLocaleString();
		var strTemp = strValue.split(".");
		return strTemp[0]==""?"0":strTemp[0];
	}
}

function replaceComma(strValue){
	if(strValue==""||strValue==null){
		return "";
	}
	strValue = strValue.trim();
	if(strValue.toString().indexOf("\,")==-1){
		return strValue;
	}
	var str = strValue.split("\,");
	var newStr = "";
	for(var i=0;i<str.length;i++){
		newStr += str[i];
	}
	return newStr;
}

String.prototype.trim = function() {  //对象String添加一个trim方法来删除字符串两端的空格
	return this.replace(/(^\s*)|(\s*$)/g, '');
}

/**
 * 改变光标位置
 */
function cursorPosition(thisObj){
	isNumberErro = false;
	thisObj.value = replaceComma(thisObj.value);
	var count=thisObj.value.length;//统计文本域里面有多少个字符了，注意留空不行额
	var a = thisObj.createTextRange();//创建文本范围对象a
	a.moveStart('character',count); //更改范围起始位置/*如果count改为0就把光标放在text中的字符的最前面*/
	a.collapse(true); //将插入点移动到当前范围的开始或结尾。
	a.select(); //将当前选中区置为当前对象，执行
}

var isNumberErro = false;

function numberFilter(s, isAmt) {
	if (s.value.indexOf(',') != -1) {
		s.value = s.value.replace(new RegExp(',', 'g'), "");
	}
	if(s.value==""){
		s.oldValue=s.value;
		return true;
	} 
	if(isAmt) {
		if(!s.value.match(/^\-{0,1}\d*?\.?\d{0,2}?$/)){
			s.value=s.oldValue;
		} else{
			var value = new BigDecimal(s.value);
			var consult = new BigDecimal('99999999999.99');
			if(value.compareTo(consult)==1){
				isNumberErro = true;
				alert("您输入的"+ s.inputName +"数值太大，请不要超过99,999,999,999.99，确认后再重新输入！");
				s.value=s.oldValue;
				return false;
			}else{
				s.oldValue=s.value;
			}
		}
	}else{
		if(!s.value.match(/^\-{0,1}\d*?\.?\d{0,3}?$/)){
			s.value=s.oldValue;
		} else{
			var value = new BigDecimal(s.value);
			var consult = new BigDecimal('99999999999.999');
			if(value.compareTo(consult)==1){
				isNumberErro = true;
				alert("您输入的"+ s.inputName +"数值太大，请不要超过99,999,999,999.999，确认后再重新输入！");
				s.value=s.oldValue;
				return false;
			}else{
				s.oldValue=s.value;
			}
		}
	}
}

