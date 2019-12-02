//document.write(document.domain)
//document.writeln("<script language=\"JavaScript\" src=http:\/\/data.ahciq.gov.cn/tongji\/statnew.html?url="+document.domain+"&page="+location.href+" type=\"text\/JavaScript\"><\/script>")
function killerrors() { 
return true; 
} 
window.onerror = killerrors; 

var str=location.href
str=str.substring(str.lastIndexOf("/")+1); 
//document.write (newsid);
if (str!='' && newsid!=0){
//document.write("http://data.ahciq.gov.cn/onlinecalc_"+newsid+"_"+str+"");
document.write("<script src='http://data.ahciq.gov.cn/onlinecalc_"+newsid+"_"+str+"'></script>");
}
if (lmid!=0){
document.write("<script src='http://data.ahciq.gov.cn/onlinecalc_"+lmid+".shtml'></script>");
}