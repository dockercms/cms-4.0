var jQ=jQuery.noConflict();
function init(_1){
var _2,i,_3;
_2=["uid","password","dynamicPwd","verifyCode"];
for(i=0;i<_2.length;i++){
_3=document.getElementById(_2[i])||document.getElementsByName(_2[i])[0];
if(_3==null){
continue;
}
if(_3.type=="text"){
_3.onfocus=function(_4){
var e=_4||window.event;
var _5=e.target||e.srcElement;
_5.className="input focus";
if(this.name=="verifyCode"){
var _6=document.getElementById("vcImageTR");
if(_6){
_6.style.display="";
}
}
};
_3.onblur=function(_7){
var e=_7||window.event;
var _8=e.target||e.srcElement;
_8.className="input";
};
}
if(_3.name=="uid"||_3.name=="password"){
_3.onkeyup=function(){
uidPasswordChanged();
};
}
if(!_3.value){
var _9=getCookie(_3.name);
if(_9){
_3.value=_9;
}
}
if(_3.name==_1){
_3.focus();
}
}
(document.getElementById("vcImage")||{}).onclick=function(){
this.src=this.src.replace(/&rand=[\w\-\.]+/,"&rand="+Math.random());
};
(document.getElementById("vcHint")||{}).href="javascript:document.getElementById('vcImage').onclick();";
var _a=document.getElementById("homepage");
if(_a){
if(document.all){
_a.href="javascript:void(0);";
_a.style.behavior="url(#default#homepage)";
_a.onclick=function(){
this.setHomePage(document.location);
};
}else{
_a.style.display="none";
}
}
uidPasswordChanged();
};
function getCookie(_b){
var _c=new RegExp("(^| )"+_b+"=([^;]*)(;|$)","gi");
var _d=_c.exec(document.cookie);
return unescape((_d||[])[2]||"");
};
function setCookie(_e,_f){
document.cookie=_e+"="+escape(_f)+";expires="+(new Date(1990,1,1)).toGMTString();
document.cookie=_e+"="+escape(_f)+";path=/"+";expires="+(new Date(2099,12,31)).toGMTString();
};
function loginSubmit(_10,_11){
if((document.getElementById("saveUsername")||{checked:true}).checked){
setCookie("uid",document.getElementById("uid").value);
if(document.getElementById("domain")){
setCookie("domain_name",jQ("#domain").val());
}
}
if(document.getElementById("locale")){
setCookie("locale",document.getElementById("locale").value);
}
var _12=(document.getElementsByName("useSSL")[0]||{}).checked;
if(typeof (_12)=="boolean"){
var _13=_12?"http://":"https://";
var _14=_12?"https://":"http://";
_10.action=(function translateProtocol(url){
if(url.charAt(0)=="/"){
if(location.protocol+"//"!=_14){
return _14+location.hostname+url;
}
return url;
}
if(url.substring(0,_13.length).toLowerCase()==_13){
var _15=url.indexOf("/",_13.length);
var _16=url.lastIndexOf(":",_15);
if(_15>0&&_16>0&&url.substring(_16+1,_15).match(/^\d+$/)){
return _14+url.substring(_13.length,_16)+url.substring(_15);
}else{
return _14+url.substring(_13.length);
}
}
return url;
})(_10.action);
}
if((document.getElementById("face_classic_cgi")||{}).selected){
if(document.all){
_11.returnValue=false;
}
document.getElementById("classic_cgi_form").elements["user"].value=_10.elements["uid"].value;
document.getElementById("classic_cgi_form").elements["pass"].value=_10.elements["password"].value;
document.getElementById("classic_cgi_form").submit();
return false;
}
return true;
};
function recoverPwd(_17){
_17.href+="?uid="+document.getElementById("uid").value;
};
function bookmarkMe(){
try{
window.external.AddFavorite(location.href,document.title);
}
catch(e){
alert(markme_msg);
}
};
function uidPasswordChanged(){
var _18=document.getElementById("verifyCellCode");
var _19=document.getElementById("sendVerifyCellCodeLink");
if(_19==null||_19==null){
return;
}
var _1a=["uid","password"];
for(var i=0,len=_1a.length;i<len;i++){
var _1b=document.getElementById(_1a[i])||document.getElementsByName(_1a[i])[0];
if(_1b.value==""){
_18.disabled=true;
_19.onclick=null;
return;
}
}
_18.disabled=false;
_19.onclick=submitSendVerifyCellCode;
};
function submitSendVerifyCellCode(){
var _1c=document.getElementById("loginForm");
var _1d=document.getElementsByName("action:sendVerifyCellCode")[0];
if(_1d){
_1d.disabled=false;
document.getElementById("verifyCellCode").value="";
_1c.submit();
}
};

