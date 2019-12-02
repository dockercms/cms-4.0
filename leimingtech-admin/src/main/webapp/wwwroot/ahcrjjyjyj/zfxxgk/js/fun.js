String.prototype.trim=function(reg){if(reg)return this.replace(reg,"");else return this.replace(/^[\s| ]*|[\s| ]*$/g,"");}
String.prototype.leftTrim=function(reg){if(reg)return this.replace(reg,"");else return this.replace(/^\s*$/g,"");}
String.prototype.rightTrim=function(reg){if(reg)return this.replace(reg,"");else return this.replace(/\s*$/g,"");}
function $(id,IsFrameContent)
{
   if(IsFrameContent)
   {
     if(document.all)return frames[id].document;
     else  return document.getElementById(id).contentWindow.document;
   }
   return document.getElementById(id);
}
function $s(obj,TagName){return obj.getElementsByTagName(TagName);}