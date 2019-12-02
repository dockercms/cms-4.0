/*--------------------------------------------------|

| MODEL | API.UCoolMe.Com                           |

|---------------------------------------------------|

| Copyright (c) 2008  jiaobailong    ?              |

| :ð QQ:676355970,Email:UCoolMe@foxmail.com|

| This script can be used freely as long as all     |

| copyright messages are intact.                    |

|                                                   |

| Updated: 08.5.2008                                |

|--------------------------------------------------*/
//ſAPIӿ
var UCoolMe={
     Version:'1.0.0',
	 Copyright:'ſ Ȩ',
	 Config:{
		 ResourceURL:'/flvjs/'
	 },
	 PlayFlv:function(Title,URI,Width,Height,Logo,IsAuto){
		 var FlashUrl=UCoolMe.Config.ResourceURL+'PlayFlv.swf';
		 var JsHtml='';
		 JsHtml=JsHtml+'<object classid="clsid:d27cdb6e-ae6d-11cf-96b8-444553540000" codebase="http://fpdownload.macromedia.com/pub/shockwave/cabs/flash/swflash.cab#version=6,0,0,0" width="'+Width+'" height="'+Height+'">';
		 JsHtml=JsHtml+'<param name="movie" value="'+FlashUrl+'"><param name="quality" value="high">';
		 JsHtml=JsHtml+'<param name="menu" value="false"><param name="allowFullScreen" value="true" /><param value="transparent" name="wmode">';
		 JsHtml=JsHtml+'<param name="FlashVars" value="vcastr_file='+URI+'&vcastr_title='+Title+'&LogoText='+Logo+'&IsAutoPlay='+IsAuto+'">';
		 JsHtml=JsHtml+'<embed src="'+FlashUrl+'" allowFullScreen="true" wmode="transparent" FlashVars="vcastr_file='+URI+'&vcastr_title='+Title+'" menu="false" quality="high" width="'+Width+'" height="'+Height+'" type="application/x-shockwave-flash" pluginspage="http://www.macromedia.com/go/getflashplayer" />';
		 JsHtml=JsHtml+'</object>';
		 document.writeln(JsHtml);
	 },
	 Copy:function(Text,Message){
		 window.clipboardData.setData("Text",Text);
		 alert(Message);
	 },
	 AddFavorite:function(URL,Text){
		 window.external.addFavorite(URL,Text);
	 },
	 Guid:function(Check){
		 var _Guid;
		 if(!Check){
			 _Date=new Date();
			 _Time=_Date.getTime();
			 _Guid=_Time;
			return _Guid;
		 }else{
			 for(var i = 1; i <= 32; i++){
				var n = Math.floor(Math.random() * 16.0).toString(16);
				_Guid += n;
				if ((i == 8) || (i == 12) || (i == 16) || (i == 20))
				_Guid += '-';
			}
			_Guid += '';
			return _Guid.toUpperCase();
		 }
	 },
	ReadCookie:function(name){
			var cookieValue="";
			var search=name+"=";
			if(document.cookie.length>0){
				offset=document.cookie.indexOf(search);
				if(offset!=-1){
					offset+=search.length;
					end=document.cookie.indexOf(";",offset);
					if(end==-1)end=document.cookie.length;
					cookieValue=unescape(document.cookie.substring(offset,end))
				}
			}
			return cookieValue;
		},
	WriteCookie:function(name,value,minutes){
		var expire="";
		if(minutes!=null){
			expire=new Date((new Date()).getTime()+minutes*360000);
			expire=";expires="+expire.toGMTString();
		}
		document.cookie=name+"="+escape(value)+expire;
	},
	MD5:function(T,byte){
				var hexcase=0;
				var b64pad="";
				var chrsz=8;
				function md5(s,byte){
					return binl2hex(core_md5(str2binl(s),s.length*chrsz,byte));
				}
				function b64_md5(s){
					return binl2b64(core_md5(str2binl(s),s.length*chrsz));
				}
				function str_md5(s){
					return binl2str(core_md5(str2binl(s),s.length*chrsz));
				}
				function hex_hmac_md5(key,data){
					return binl2hex(core_hmac_md5(key,data));
				}
				function b64_hmac_md5(key,data){
					return binl2b64(core_hmac_md5(key,data));
				}
				function str_hmac_md5(key,data){
					return binl2str(core_hmac_md5(key,data));
				}
				function md5_vm_test(){
					return hex_md5("abc")=="900150983cd24fb0d6963f7d28e17f72";
				}
				function core_md5(x,len,byte){
					x[len>>5]|=0x80<<((len)%32);
					x[(((len+64)>>>9)<<4)+14]=len;
					var a=1732584193;
					var b=-271733879;
					var c=-1732584194;
					var d=271733878;
					for(var i=0;i<x.length;i+=16){
						var olda=a;
						var oldb=b;
						var oldc=c;
						var oldd=d;
						a=md5_ff(a,b,c,d,x[i+0],7,-680876936);
						d=md5_ff(d,a,b,c,x[i+1],12,-389564586);
						c=md5_ff(c,d,a,b,x[i+2],17,606105819);
						b=md5_ff(b,c,d,a,x[i+3],22,-1044525330);
						a=md5_ff(a,b,c,d,x[i+4],7,-176418897);
						d=md5_ff(d,a,b,c,x[i+5],12,1200080426);
						c=md5_ff(c,d,a,b,x[i+6],17,-1473231341);
						b=md5_ff(b,c,d,a,x[i+7],22,-45705983);
						a=md5_ff(a,b,c,d,x[i+8],7,1770035416);
						d=md5_ff(d,a,b,c,x[i+9],12,-1958414417);
						c=md5_ff(c,d,a,b,x[i+10],17,-42063);
						b=md5_ff(b,c,d,a,x[i+11],22,-1990404162);
						a=md5_ff(a,b,c,d,x[i+12],7,1804603682);
						d=md5_ff(d,a,b,c,x[i+13],12,-40341101);
						c=md5_ff(c,d,a,b,x[i+14],17,-1502002290);
						b=md5_ff(b,c,d,a,x[i+15],22,1236535329);
						a=md5_gg(a,b,c,d,x[i+1],5,-165796510);
						d=md5_gg(d,a,b,c,x[i+6],9,-1069501632);
						c=md5_gg(c,d,a,b,x[i+11],14,643717713);
						b=md5_gg(b,c,d,a,x[i+0],20,-373897302);
						a=md5_gg(a,b,c,d,x[i+5],5,-701558691);
						d=md5_gg(d,a,b,c,x[i+10],9,38016083);
						c=md5_gg(c,d,a,b,x[i+15],14,-660478335);
						b=md5_gg(b,c,d,a,x[i+4],20,-405537848);
						a=md5_gg(a,b,c,d,x[i+9],5,568446438);
						d=md5_gg(d,a,b,c,x[i+14],9,-1019803690);
						c=md5_gg(c,d,a,b,x[i+3],14,-187363961);
						b=md5_gg(b,c,d,a,x[i+8],20,1163531501);
						a=md5_gg(a,b,c,d,x[i+13],5,-1444681467);
						d=md5_gg(d,a,b,c,x[i+2],9,-51403784);
						c=md5_gg(c,d,a,b,x[i+7],14,1735328473);
						b=md5_gg(b,c,d,a,x[i+12],20,-1926607734);
						a=md5_hh(a,b,c,d,x[i+5],4,-378558);
						d=md5_hh(d,a,b,c,x[i+8],11,-2022574463);
						c=md5_hh(c,d,a,b,x[i+11],16,1839030562);
						b=md5_hh(b,c,d,a,x[i+14],23,-35309556);
						a=md5_hh(a,b,c,d,x[i+1],4,-1530992060);
						d=md5_hh(d,a,b,c,x[i+4],11,1272893353);
						c=md5_hh(c,d,a,b,x[i+7],16,-155497632);
						b=md5_hh(b,c,d,a,x[i+10],23,-1094730640);
						a=md5_hh(a,b,c,d,x[i+13],4,681279174);
						d=md5_hh(d,a,b,c,x[i+0],11,-358537222);
						c=md5_hh(c,d,a,b,x[i+3],16,-722521979);
						b=md5_hh(b,c,d,a,x[i+6],23,76029189);
						a=md5_hh(a,b,c,d,x[i+9],4,-640364487);
						d=md5_hh(d,a,b,c,x[i+12],11,-421815835);
						c=md5_hh(c,d,a,b,x[i+15],16,530742520);
						b=md5_hh(b,c,d,a,x[i+2],23,-995338651);
						a=md5_ii(a,b,c,d,x[i+0],6,-198630844);
						d=md5_ii(d,a,b,c,x[i+7],10,1126891415);
						c=md5_ii(c,d,a,b,x[i+14],15,-1416354905);
						b=md5_ii(b,c,d,a,x[i+5],21,-57434055);
						a=md5_ii(a,b,c,d,x[i+12],6,1700485571);
						d=md5_ii(d,a,b,c,x[i+3],10,-1894986606);
						c=md5_ii(c,d,a,b,x[i+10],15,-1051523);
						b=md5_ii(b,c,d,a,x[i+1],21,-2054922799);
						a=md5_ii(a,b,c,d,x[i+8],6,1873313359);
						d=md5_ii(d,a,b,c,x[i+15],10,-30611744);
						c=md5_ii(c,d,a,b,x[i+6],15,-1560198380);
						b=md5_ii(b,c,d,a,x[i+13],21,1309151649);
						a=md5_ii(a,b,c,d,x[i+4],6,-145523070);
						d=md5_ii(d,a,b,c,x[i+11],10,-1120210379);
						c=md5_ii(c,d,a,b,x[i+2],15,718787259);
						b=md5_ii(b,c,d,a,x[i+9],21,-343485551);
						a=safe_add(a,olda);
						b=safe_add(b,oldb);
						c=safe_add(c,oldc);
						d=safe_add(d,oldd);
					}
					if(byte==32){
						return Array(a,b,c,d);
					}
					else if(byte==16){
						return Array(b,c);
					}
					else {
						return false;
					}
				}
				function md5_cmn(q,a,b,x,s,t){
					return safe_add(bit_rol(safe_add(safe_add(a,q),safe_add(x,t)),s),b);
				}
				function md5_ff(a,b,c,d,x,s,t){
					return md5_cmn((b&c)|((~b)&d),a,b,x,s,t);
				}
				function md5_gg(a,b,c,d,x,s,t){
					return md5_cmn((b&d)|(c&(~d)),a,b,x,s,t);
				}
				function md5_hh(a,b,c,d,x,s,t){
					return md5_cmn(b^c^d,a,b,x,s,t);
				}
				function md5_ii(a,b,c,d,x,s,t){
					return md5_cmn(c^(b|(~d)),a,b,x,s,t);
				}
				function core_hmac_md5(key,data){
					var bkey=str2binl(key);
					if(bkey.length>16)bkey=core_md5(bkey,key.length*chrsz);
					var ipad=Array(16),opad=Array(16);
					for(var i=0;i<16;i++){
						ipad[i]=bkey[i]^0x36363636;
						opad[i]=bkey[i]^0x5C5C5C5C;
					}
					var hash=core_md5(ipad.concat(str2binl(data)),512+data.length*chrsz);
					return core_md5(opad.concat(hash),512+128);
				}
				function safe_add(x,y){
					var lsw=(x&0xFFFF)+(y&0xFFFF);
					var msw=(x>>16)+(y>>16)+(lsw>>16);
					return (msw<<16)|(lsw&0xFFFF);
				}
				function bit_rol(num,cnt){
					return (num<<cnt)|(num>>>(32-cnt));
				}
				function str2binl(str){
					var bin=Array();
					var mask=(1<<chrsz)-1;
					for(var i=0;i<str.length*chrsz;i+=chrsz)bin[i>>5]|=(str.charCodeAt(i/chrsz)&mask)<<(i%32);return bin;
				}
				function binl2str(bin){
					var str="";
					var mask=(1<<chrsz)-1;
					for(var i=0;i<bin.length*32;i+=chrsz)str+=String.fromCharCode((bin[i>>5]>>>(i%32))&mask);return str;
				}
				function binl2hex(binarray){
					var hex_tab=hexcase?"0123456789ABCDEF":"0123456789abcdef";
					var str="";
					for(var i=0;i<binarray.length*4;i++){
						str+=hex_tab.charAt((binarray[i>>2]>>((i%4)*8+4))&0xF)+hex_tab.charAt((binarray[i>>2]>>((i%4)*8))&0xF);
					}
					return str;
				}
				function binl2b64(binarray){
					var tab="ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789+/";
					var str="";
					for(var i=0;i<binarray.length*4;i+=3){
						var triplet=(((binarray[i>>2]>>8*(i%4))&0xFF)<<16)|(((binarray[i+1>>2]>>8*((i+1)%4))&0xFF)<<8)|((binarray[i+2>>2]>>8*((i+2)%4))&0xFF);
						for(var j=0;j<4;j++){
							if(i*8+j*6>binarray.length*32)str+=b64pad;
							else str+=tab.charAt((triplet>>6*(3-j))&0x3F);
						}
					}
					return str;
				}
					return md5(T,byte);
					alert('f');
	 },
	base64EncodeChars:"ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789+/",
	base64DecodeChars:new Array(
	-1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1,
	-1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1,
	-1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, 62, -1, -1, -1, 63,
	52, 53, 54, 55, 56, 57, 58, 59, 60, 61, -1, -1, -1, -1, -1, -1,
	-1,  0,  1,  2,  3,  4,  5,  6,  7,  8,  9, 10, 11, 12, 13, 14,
	15, 16, 17, 18, 19, 20, 21, 22, 23, 24, 25, -1, -1, -1, -1, -1,
	-1, 26, 27, 28, 29, 30, 31, 32, 33, 34, 35, 36, 37, 38, 39, 40,
	41, 42, 43, 44, 45, 46, 47, 48, 49, 50, 51, -1, -1, -1, -1, -1),
	Base64Encode:function(str){
		var out, i, len;
		var c1, c2, c3;
		len = str.length;
		i = 0;
		out = "";
		while(i < len) {
			c1 = str.charCodeAt(i++) & 0xff;
			if(i == len)
			{
				out += this.base64EncodeChars.charAt(c1 >> 2);
				out += this.base64EncodeChars.charAt((c1 & 0x3) << 4);
				out += "==";
				break;
			}
			c2 = str.charCodeAt(i++);
			if(i == len)
			{
				out += this.base64EncodeChars.charAt(c1 >> 2);
				out += this.base64EncodeChars.charAt(((c1 & 0x3)<< 4) | ((c2 & 0xF0) >> 4));
				out += this.base64EncodeChars.charAt((c2 & 0xF) << 2);
				out += "=";
				break;
			}
			c3 = str.charCodeAt(i++);
			out += this.base64EncodeChars.charAt(c1 >> 2);
			out += this.base64EncodeChars.charAt(((c1 & 0x3)<< 4) | ((c2 & 0xF0) >> 4));
			out += this.base64EncodeChars.charAt(((c2 & 0xF) << 2) | ((c3 & 0xC0) >>6));
			out += this.base64EncodeChars.charAt(c3 & 0x3F);
		}
		return out;
	},
	Base64Decode:function(str){
		var c1, c2, c3, c4;
		var i, len, out;
		len = str.length;
		i = 0;
		out = "";
		while(i < len) {
			do {
				c1 = this.base64DecodeChars[str.charCodeAt(i++) & 0xff];
			} while(i < len && c1 == -1);
			if(c1 == -1)
			break;
			do {
				c2 = this.base64DecodeChars[str.charCodeAt(i++) & 0xff];
			} while(i < len && c2 == -1);
			if(c2 == -1)
			break;
			out += String.fromCharCode((c1 << 2) | ((c2 & 0x30) >> 4));
			do {
				c3 = str.charCodeAt(i++) & 0xff;
				if(c3 == 61)
				return out;
				c3 = this.base64DecodeChars[c3];
			} while(i < len && c3 == -1);
			if(c3 == -1)
			break;
			out += String.fromCharCode(((c2 & 0XF) << 4) | ((c3 & 0x3C) >> 2));
			do {
				c4 = str.charCodeAt(i++) & 0xff;
				if(c4 == 61)
				return out;
				c4 = this.base64DecodeChars[c4];
			} while(i < len && c4 == -1);
			if(c4 == -1)
			break;
			out += String.fromCharCode(((c3 & 0x03) << 6) | c4);
		}
		return out;
	},
	FlashRotation:function(ImgUrl,ImgLink,ImgText,Width,HeightI,HeightT,Style){
		var focus_width=Width;
		var focus_height=HeightI;
		var text_height=HeightT;
		var swf_height=HeightI+HeightT;
		var pics=ImgUrl;
		var links=ImgLink;
		var texts=ImgText;
		var flashSource;
		if(Style==1){
			flashSource=UCoolMe.Config.ResourceURL+"FlashImg1.swf";
		}
		else if(Style==0){
			flashSource=UCoolMe.Config.ResourceURL+"FlashImg0.swf";
		}
		document.writeln('<object classid="clsid:d27cdb6e-ae6d-11cf-96b8-444553540000" codebase="http://fpdownload.macromedia.com/pub/shockwave/cabs/flash/swflash.cab#version=6,0,0,0" width="'+focus_width+'" height="'+swf_height+'">');
		document.writeln('<param name="allowScriptAccess" value="sameDomain"><param name="movie" value="'+flashSource+'"><param name="quality" value="high"><param name="bgcolor" value=#F7F3F7>');
		document.writeln('<param name="menu" value="false"><param name=wmode value="opaque">');
		document.writeln('<param name="FlashVars" value="pics='+pics+'&links='+links+'&texts='+texts+'&borderwidth='+focus_width+'&borderheight='+focus_height+'&textheight='+text_height+'">');
		document.writeln('<embed src="'+flashSource+'" wmode="opaque" FlashVars="pics='+pics+'&links='+links+'&texts='+texts+'&borderwidth='+focus_width+'&borderheight='+focus_height+'&textheight='+text_height+'" menu="false" bgcolor=#ffffff quality="high" width="'+focus_width+'" height="'+swf_height+'" allowScriptAccess="sameDomain" type="application/x-shockwave-flash" pluginspage="http://www.macromedia.com/go/getflashplayer" />');
		document.writeln('</object>');
	}
}
/*--------------------------------------------------|

| MODEL | API.UCoolMe.Com                           |

|---------------------------------------------------|

| Copyright (c) 2008  jiaobailong    ?              |

| :ð QQ:676355970,Email:UCoolMe@foxmail.com|

| This script can be used freely as long as all     |

| copyright messages are intact.                    |

|                                                   |

| Updated: 08.5.2008                                |

|--------------------------------------------------*/