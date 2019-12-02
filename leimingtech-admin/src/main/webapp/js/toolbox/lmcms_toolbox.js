// JavaScript Document
(function(){
	if (!window.lmcms_toolbox) {
		// initialization lmcms_toolbox
		var lmcms_toolbox = window.lmcms_toolbox = {};
	
		// return client width
		lmcms_toolbox.getWidth	= window.innerWidth | document.body.clientWidth;
	
		// return client height
		lmcms_toolbox.getHeight = window.innerHeight | document.body.clientHeight;
	
		lmcms_toolbox.isMove=true;
		
		// return a div document object based on params
		lmcms_toolbox.divMaker	= function(attribute, style, parentObj) {
			var obj = document.createElement('div');
			for (var key in attribute) {
				if (key == 'class') {
					obj.setAttribute('class', attribute[key]);
					obj.setAttribute('className', attribute[key]);	// compatible
																	// IE
				}else{
					obj.setAttribute(key, attribute[key]);
				}
			}
			for (var key in style) {
				obj.style[key] = style[key];
			}
			if (!parentObj) {
				parentObj = document.body;
			}
			parentObj.appendChild(obj);
			return obj;
		};
	
		// event bind
		lmcms_toolbox.bind = function bind(obj, action, func) {
			if (window.addEventListener) {
				obj.addEventListener( action, function(event) {
					func(obj, event);
				}, false);
			} else if (window.attachEvent) { // compatible IE
				obj.attachEvent('on' +action, function(event) {
					func(obj, event);
				});
			}
		};
	
		// event unbind
		lmcms_toolbox.unbind = function(obj, action, func) {
			if (window.removeEventListener) {
				obj.removeEventListener(action, func , false);
			} else if (window.detachEvent) { // compatible IE
				obj.detachEvent(action, func);
			}
		};
	
		// a dragger lib class
		lmcms_toolbox.drag = function(dragObj, moveObj) {
			var isDrag = false;
			var x = 0, y = 0;
			dragObj.style.cursor = 'move';
			// drag mouse
			var _mousemove = function(obj, event) {
				if(!lmcms_toolbox.isMove){
					return;
				}
				if (!isDrag) {
					return
				}
				moveObj.style.left	= x +  event.clientX + 'px';
				moveObj.style.top	= y +  event.clientY + 'px';
				parseInt(moveObj.style.top) < 0 && (moveObj.style.top = '0');
				lmcms_toolbox.bind(document.body, 'mouseup', _mouseup);
				return false;
			};
			// release mouse
			var _mouseup = function() {
				if(!lmcms_toolbox.isMove){
					return;
				}
				if (!isDrag) {
					return
				}
				lmcms_toolbox.unbind(document.body, 'mousemove', _mousemove);
				lmcms_toolbox.unbind(document.body, 'mouseup', _mouseup);
				isDrag = false;
				return false;
			};
			var _mousedown = function(obj, event) {
				if(!lmcms_toolbox.isMove){
					return;
				}
				if (isDrag) {
					return;
				}
				isDrag = true;
				x	= parseInt(moveObj.style.left) - event.clientX;
				y	= parseInt(moveObj.style.top)  - event.clientY;
				lmcms_toolbox.bind(document.body, 'mousemove', _mousemove);
				lmcms_toolbox.bind(document.body, 'mouseup', _mouseup);
			};
			// mouse down
			lmcms_toolbox.bind(dragObj, 'mousedown', _mousedown);
		};
	
		// get client width
		lmcms_toolbox.getWidth	= function() {
			var width	= window.innerWidth;
			if (width == undefined) { // compatible IE
				width	= document.documentElement.clientWidth;
			}
			return width;
		};
	
		// get client height
		lmcms_toolbox.getHeight = function() {
			var height	= window.innerHeight;
			if (height == undefined) { // compatible IE
				height	= document.documentElement.clientHeight;
				height	= ((window.screen.height - 100) < height) ? window.screen.height - 100 : height;
			}
			return height;
		};
	
		lmcms_toolbox.getClientHeight=function() {
			var clientHeight = 0;
			if (document.body.clientHeight && document.documentElement.clientHeight) {
				var clientHeight = (document.body.clientHeight < document.documentElement.clientHeight) ? document.body.clientHeight: document.documentElement.clientHeight;
			} else {
				var clientHeight = (document.body.clientHeight > document.documentElement.clientHeight) ? document.body.clientHeight: document.documentElement.clientHeight;
			}
			return clientHeight;
		};
		// tools window
		lmcms_toolbox.toolWin	= function() {
			var self = this;
			self.isopen = false;
			var lmcmsToolbar		=  null;
			var lmcmsToolbarBody	= null;
			var openStatus			= true;
			var cmdButton	= [
				{'class':'ico1', 'title':'\u8f6c\u8f7d', 'event':'reproduce','condition':'true'},
				{'class':'ico2', 'title':'\u7f16\u8f91', 'event':'edit', 'condition':'lmcms_toolbox.isMySite && typeof(contentid) !="undefined"'},
				{'class':'ico4', 'title':'\u5220\u9664', 'event':'delete', 'condition':'lmcms_toolbox.isMySite && typeof(contentid) !="undefined"'},
				{'class':'ico7', 'title':'\u7f16\u8f91', 'event':'visualedit', 'condition':'lmcms_toolbox.isMySite && typeof(pageid) !="undefined"'},
				{'class':'ico5', 'title':'\u7ba1\u7406', 'event':'admin','condition':'true'},
				{'class':'ico6', 'title':'\u9000\u51fa', 'event':'logout','condition':'true'}
			];
			self.open = function() {
				if (self.isopen) {
					return false;
				}
				self.isopen = true;
				var a, btn, logo, width;
				// build UI
				width	= lmcms_toolbox.getWidth();
				lmcmsToolbar	= lmcms_toolbox.divMaker({"class":"lmcms-toolbar"}, {'top':'20px', 'left':(width-120+'px')});
				logo = lmcms_toolbox.divMaker({"class":"lmcms-toolbar-logo"}, {}, lmcmsToolbar);
				lmcmsToolbarBody = lmcms_toolbox.divMaker({"class":"lmcms-toolbar-body"}, {}, lmcmsToolbar);
				var lmcmsToolbarFoot = lmcms_toolbox.divMaker({"class":"lmcms-toolbar-foot"}, {}, lmcmsToolbar);
				lmcms_toolbox.divMaker({"class":"lmcms-toolbar-bg"}, {}, lmcmsToolbarBody);
				lmcmsToolbarFoot.innerHTML	= '<a id="lmcms_openstatus" class="lmcms-toolbar-size-switch-drop lmcms-toolbar-open-status" href="javascript:void(0);" onclick="lmcms_toolbox.toolWin.sizeToggle()" target="_self"></a>';
				lmcmsToolbarFoot.innerHTML += '<a class="lmcms-toolbar-size-switch-close" href="javascript:void(0);" onclick="lmcms_toolbox.toolWin.close();" target="_self"></a>';
				lmcmsToolbarFoot.innerHTML += '<div class="lmcms-toolbar-shadow-radius"></div>';
				// prevent default dragging
				logo.ondragstart=function (){return false;};
				// build button
				for (var i in cmdButton) {
					btn	= cmdButton[i];
					if (!eval(btn['condition'])) {
						continue;
					}
					a = document.createElement('a');
					a.setAttribute('href'		, "javascript:void((function(){lmcms_toolbox_domain_admin='"+lmcms_toolbox.adminUrl+"';lmcms_toolbox_ver=2;lmcms_toolbox_cmd='"+btn['event']+"';if(typeof(lmcms_toolbox)!='undefined'){lmcms_toolbox.ready(lmcms_toolbox_cmd);return}var%20e=document.createElement('script');e.setAttribute('src',lmcms_toolbox_domain_admin+'js/lmcms.toolbox.js');e.setAttribute('charset','utf-8');document.body.appendChild(e)})())");
					a.setAttribute('class'		, 'lmcms-toolbar-btn lmcms-toolbar-' + btn['class']);
					a.setAttribute('className'	, 'lmcms-toolbar-btn lmcms-toolbar-' + btn['class']);
					a.setAttribute('title'		, btn['title']);
					a.setAttribute('id'			, 'lmcms_toolbox_menu_' + btn['event']);
					a.setAttribute('onclick'	, 'lmcms_toolbox.ready("' + btn['event'] + '");return false;');
					a.setAttribute('target'		, '_self');
					a.innerHTML = '<div style="display:none;">'+btn['title']+'</div>';
					lmcmsToolbarBody.appendChild(a);
				}
				lmcms_toolbox.drag(logo, lmcmsToolbar);
			};
			self.sizeToggle = function() {
				var displayValue, btns = lmcmsToolbarBody.getElementsByTagName('a');
				if (openStatus) {
					document.getElementById('lmcms_openstatus').setAttribute('class', 'lmcms-toolbar-size-switch-drop lmcms-toolbar-min-status');
					document.getElementById('lmcms_openstatus').setAttribute('className', 'lmcms-toolbar-size-switch-drop lmcms-toolbar-min-status');
					displayValue = 'none';
					openStatus = false;
				} else {
					document.getElementById('lmcms_openstatus').setAttribute('class', 'lmcms-toolbar-size-switch-drop lmcms-toolbar-open-status');
					document.getElementById('lmcms_openstatus').setAttribute('className', 'lmcms-toolbar-size-switch-drop lmcms-toolbar-open-status');
					displayValue = 'block';
					openStatus = true;
				}
				for (var a in btns) {
					if (typeof (btns[a]) == 'object') {
						 btns[a].style.display = displayValue;
					}
				}
			};
			self.close = function() {
				document.body.removeChild(lmcmsToolbar);
				self.isopen = false;
			};
		};
		lmcms_toolbox.getElementsByClassName=function(className) {
			var all = document.all ? document.all: document.getElementsByTagName('*');
			var elements = new Array();
			for (var e = 0; e < all.length; e++) {
				if (all[e].className == className) {
					elements[elements.length] = all[e];
					break;
				}
			}
			return elements;
		};
		// main window
		lmcms_toolbox.mainWin	= function() {
			var self = this;
			self.isopen = false;
			self.miniwin = false;
			self.isFullScreen=false;
			self.originalWidth=850;
			self.originalHeight=400;
			var getId=function(){
				this.getIntegerBits = function(val, start, end) {
					var base16 = this.returnBase(val, 16);
					var quadArray = new Array();
					var quadString = '';
					var i = 0;
					for (i = 0; i < base16.length; i++) {
						quadArray.push(base16.substring(i, i + 1));
					}
					for (i = Math.floor(start / 4); i <= Math.floor(end / 4); i++) {
						if (!quadArray[i] || quadArray[i] == '') quadString += '0';
						else quadString += quadArray[i];
					}
					return quadString;
				};
				this.returnBase = function(number, base) {
					return (number).toString(base).toUpperCase();
				};
				this.rand = function(max) {
					return Math.floor(Math.random() * (max + 1));
				};
				var dg = new Date(1582, 10, 15, 0, 0, 0, 0);
				var dc = new Date();
				var t = dc.getTime() - dg.getTime();
				var tl = this.getIntegerBits(t, 0, 31);
				var tm = this.getIntegerBits(t, 32, 47);
				var thv = this.getIntegerBits(t, 48, 59) + '1';
				var csar = this.getIntegerBits(this.rand(4095), 0, 7);
				var csl = this.getIntegerBits(this.rand(4095), 0, 7);
				var n = this.getIntegerBits(this.rand(8191), 0, 7) + this.getIntegerBits(this.rand(8191), 8, 15) + this.getIntegerBits(this.rand(8191), 0, 7) + this.getIntegerBits(this.rand(8191), 8, 15) + this.getIntegerBits(this.rand(8191), 0, 15);
				return tl + tm + thv + csar + csl + n;
			};
			self.originalId=getId();
			var messageboxContainer = null;
			var messageboxHd	= null;
			var messageboxBd	= null;
			var messageboxFt	= null;
			var messageMainWin	= null;
			var closeRefresh	= false;
			var option	= {};
			self.open = function(o) {
				if (self.isopen) {
					return false;
				}
				self.isopen = true;
				var headContent,headTitle,sizeControl,sizeControlItem,a,ifm,left;
				option = o || {};
				option.width	= option.width || 850;
				option.height	= option.height || 400;
				self.originalWidth=option.width;
				self.originalHeight=option.height;
				option.title	= option.title || '';
				if (option.refresh) closeRefresh = true;
				left = (lmcms_toolbox.getWidth() - option.width) / 2;
				if (left < 120) {
					left = 0;
				} else {
					left += 'px';
				}
				messageboxContainer = lmcms_toolbox.divMaker({'class':'lmcms-messagebox','id':self.originalId}, {'width':(option.width+12)+'px','top':0, 'left':left});
				messageboxHd	= lmcms_toolbox.divMaker({'class':'lmcms-messagebox-head'}, {}, messageboxContainer);
				messageboxBd	= lmcms_toolbox.divMaker({'class':'lmcms-messagebox-body'}, {}, messageboxContainer);
				messageboxFt	= lmcms_toolbox.divMaker({'class':'lmcms-messagebox-foot'}, {}, messageboxContainer);
				headContent		= lmcms_toolbox.divMaker({'class':'lmcms-messagebox-head-content'}, {}, messageboxHd);
				headTitle		= lmcms_toolbox.divMaker({'class':'lmcms-messagebox-head-title'}, {}, headContent);
				headTitle.innerHTML	+= '<div class="lmcms-messagebox-head-ico"></div>';
				headTitle.innerHTML	+= '<h2>' + option.title + '</h2>';
				sizeControl		= lmcms_toolbox.divMaker({'class':'lmcms-messagebox-size-control'}, {}, messageboxHd);
				sizeControlItem	= ['minsize','maxsize','closepanel'];
				for (var i=0; i < sizeControlItem.length; i++) {
					a = document.createElement('a');
					a.setAttribute('class', 'lmcms-messagebox-head-' + sizeControlItem[i]);
					a.setAttribute('className', 'lmcms-messagebox-head-' + sizeControlItem[i]);
					a.setAttribute('href', 'javascript:;');
					a.setAttribute('target', '_self');
					sizeControl.appendChild(a);
					lmcms_toolbox.bind(a, 'click' , lmcms_toolbox.messageBox[sizeControlItem[i]]);
					lmcms_toolbox.bind(a, 'mousedown', function(){return false;});
					a.ondragstart = function() {return false;};
					a.cancelBubble = true;
					a = undefined;
				}
				lmcms_toolbox.divMaker({'class':'lmcms-messagebox-head-left'}, {}, messageboxHd);
				lmcms_toolbox.divMaker({'class':'lmcms-messagebox-head-right'}, {}, messageboxHd);
				messageMainWin	= lmcms_toolbox.divMaker({'class':'lmcms-messagebox-body-content'}, {'width':(option.width)+'px', 'height':(option.height-40+'px')}, messageboxBd);
				if (option.url) {
					var ifm	= document.createElement('iframe');
					ifm.src	= option.url;
					ifm.style.width		= '100%';
					ifm.style.height	= '100%';
					ifm.frameBorder		= 0;
					var refreshCount = 0;
					ifm.onload = function() {
						refreshCount += 1;
						if (refreshCount == 7) {
							lmcms_toolbox.messageBox.closepanel();
						}
					};
					messageMainWin.appendChild(ifm);
				}
				if (typeof (option.content) == 'object') {
					messageMainWin.appendChild(option.content);
				}
				if (typeof (option.content) == 'string') {
					messageMainWin.innerHTML = option.content;
				}				
				lmcms_toolbox.divMaker({'class':'lmcms-messagebox-foot-center'},{}, messageboxFt);
				lmcms_toolbox.divMaker({'class':'lmcms-messagebox-foot-left'},{}, messageboxFt);
				lmcms_toolbox.divMaker({'class':'lmcms-messagebox-foot-right'},{}, messageboxFt);
				lmcms_toolbox.drag(messageboxHd, messageboxContainer);
				setInterval(function(){_isClose();}, 1000);
			};
			self.resize=function(width,height){
				var mainWin=document.getElementById(self.originalId);
				if(mainWin){
					mainWin.style.width=width+"px";
					mainWin.style.height=height+"px";
					var left = (lmcms_toolbox.getWidth() - width) / 2;
					mainWin.style.left=left+"px";
					mainWin.style.top=0+"px";
				}
				var bodyContent=lmcms_toolbox.getElementsByClassName("lmcms-messagebox-body-content");
				if(bodyContent&&bodyContent.length>0){
					bodyContent[0].style.width=(width-12)+"px";
					bodyContent[0].style.height=height+"px";
				}
			};
			self.messageBoxHeight=0;
			self.minsize = function() {
				if (!self.miniwin) {
					if(self.messageBoxHeight==0){
						self.messageBoxHeight=messageboxBd.style.height.toString().split("px")[0];
					}
					try{
						jQuery(messageboxBd).slideUp(150);
					}catch(e){
						messageboxBd.style.display	= 'none';
					}
					self.miniwin = true;
				} else {
					try{
						jQuery(messageboxBd).slideDown(150);
					}catch(e){
						messageboxBd.style.display	= 'block';
					}
					self.miniwin = false;
				}
				return false;
			};
			self.maxsize = function() {
				if(self.isFullScreen){
					self.exitFullScreen();
				}else{
					self.fullScreen();
				}
			};
			var bodysArrays=new Array();
			self.fullScreen=function(){
				// 全屏
				var el = document.documentElement; 
				var rfs = el.requestFullScreen || el.webkitRequestFullScreen || el.mozRequestFullScreen || el.msRequestFullScreen; 
				if (typeof rfs != "undefined" && rfs) { 
					rfs.call(el); 
					self.isFullScreen=true;
				} else if (typeof window.ActiveXObject != "undefined") { 
					// for Internet Explorer
					var wscript = new ActiveXObject("WScript.Shell"); 
					if (wscript != null) { 
						wscript.SendKeys("{F11}");
						self.isFullScreen=true;
					} 
				}
				if(self.isFullScreen){
					var timer=setInterval(function(){
						var width=lmcms_toolbox.getWidth()+15;
						self.resize(width, lmcms_toolbox.getHeight()-40);
						var bodys=document.getElementsByTagName("body");
						for ( var i = 0; i < bodys.length; i++) {
							bodys[i].style.overflow="hidden";
						}
						clearInterval(timer);
					},100);
					var lmcmsBoxHead=lmcms_toolbox.getElementsByClassName("lmcms-messagebox-head-content");
					if(lmcmsBoxHead&&lmcmsBoxHead.length>0){
						lmcmsBoxHead[0].style.cursor='auto';
					}
					lmcms_toolbox.isMove=false;
				}
			};
			self.exitFullScreen=function () {
			    var el = document,  cfs = el.cancelFullScreen || el.webkitCancelFullScreen || el.mozCancelFullScreen || el.exitFullScreen,
			        wscript;
			    if (typeof cfs != "undefined" && cfs) {
			      cfs.call(el);
			      self.isFullScreen=false;
			    }else if (typeof window.ActiveXObject != "undefined") {
			        wscript = new ActiveXObject("WScript.Shell");
			        if (wscript != null) {
			            wscript.SendKeys("{F11}");
			            self.isFullScreen=false;
			        }
			    }
				if(!self.isFullScreen){
					self.resize(self.originalWidth, self.originalHeight-15);
					var bodys=document.getElementsByTagName("body");
					for ( var i = 0; i < bodys.length; i++) {
						bodys[i].style.overflow="auto";
					}
					var lmcmsBoxHead=lmcms_toolbox.getElementsByClassName("lmcms-messagebox-head-content");
					if(lmcmsBoxHead&&lmcmsBoxHead.length>0){
						lmcmsBoxHead[0].style.cursor='move';
					}
					var lmcmsMessageBoxFootLeft=lmcms_toolbox.getElementsByClassName("lmcms-messagebox-foot-left");
					if(lmcmsMessageBoxFootLeft&&lmcmsMessageBoxFootLeft.length>0){
						lmcmsMessageBoxFootLeft[0].style.bottom='-40px';
					}
					var lmcmsMessageBoxFootRight=lmcms_toolbox.getElementsByClassName("lmcms-messagebox-foot-right");
					if(lmcmsMessageBoxFootRight&&lmcmsMessageBoxFootRight.length>0){
						lmcmsMessageBoxFootRight[0].style.bottom='-40px';
					}
					lmcms_toolbox.isMove=true;
				}
			};
			self.closepanel = function() {
				document.body.removeChild(messageboxContainer);
				self.isopen = false;
				closeRefresh && location.reload();
				if(self.isFullScreen){
					self.exitFullScreen();
				}
				return false;
			};
			var _isClose = function() {
				if (location.hash == "#close") {
					self.closepanel();
					location.hash = '';
				}
			};
		};
	
		// load css
		var _loadCSS = function() {
			var cssUrl	= lmcms_toolbox.adminUrl + 'js/toolbox/css/lmcms_toolbox.css';
			try {
				window.document.head.innerHTML += '<link type="text/css" rel="stylesheet" href="' + cssUrl + '" />';
			} catch (e) {	// compatible IE
				window.document.createStyleSheet(cssUrl);
			}
		};



		// get meta:keywords
		var _getTags = function() {
			var metas = document.getElementsByTagName('meta'),l;
			if (!metas || !(l = metas.length)) {
				return '';
			}
			for(var i=0, l=metas.length, meta; i<l, meta=metas[i]; i++) {
				if (meta.name.toLowerCase() == 'keywords') {
					var tags = meta.content.split(/[,|\s]/);
					for (var i=0,l=tags.length,item;i<l,item=tags[i];i++) {
						if (Math.ceil(item.replace(/[^\x00-\xff]/gm, '__').length) > 16) {
							delete(tags[i]);
						}
					}
					return tags.join(' ');
				}
			}
			return '';
		};

		// action a click
		var _jump = function(url) {
			var a = document.createElement('a');
			a.setAttribute('href', url);
			a.setAttribute('target', '_blank');
			document.body.appendChild(a);
			try {
				a.click();
			} catch (e) {
				try {
					var e = document.createEvent('MouseEvents');
					e.initEvent( 'click', true, true );
					a.dispatchEvent(e);
				} catch (e) {
					location.href = url;
				}
			}
		};

		// initialization
		lmcms_toolbox.ready = function(cmd) {
			if (typeof (lmcms_toolbox_ver) == 'undefined' || lmcms_toolbox_ver != 2) {
				alert('\u60a8\u7684\u7f51\u7f16\u5de5\u5177\u680f\u8fc7\u65e7,\n\u8bf7\u91cd\u65b0\u4e0b\u8f7d');
				return;
			}
			var start = function() {

				lmcms_toolbox.adminUrl	= lmcms_toolbox_domain_admin;
				var temp_arr = lmcms_toolbox.adminUrl.split('.');
				temp_arr.shift();
				lmcms_toolbox.domain	= temp_arr.join('.').replace(/\/+/, '');
				lmcms_toolbox_domain_admin = undefined;
				lmcms_toolbox.isMySite = (function(){var d = /([^:]*)/.exec(lmcms_toolbox.domain)[0],r = new RegExp(d);return r.test(location.host);})();
				_loadCSS();
				try {
					lmcms_toolbox.toolWin = new lmcms_toolbox.toolWin();
				}
				catch (e) {}
				if (!lmcms_toolbox.toolWin.isopen) {
					lmcms_toolbox.toolWin.open();
				}
			};
			var reproduce = function() {
				try {
					lmcms_toolbox.messageBox = new lmcms_toolbox.mainWin();
				}
				catch (e) {}
				if (!lmcms_toolbox.messageBox.isopen) {
					var url = lmcms_toolbox.adminUrl;

					url += '/articleController.do?networkActicleEdit&contentCatId=402881914e0a8fed014e0ad3812b0130&modelsId=1';
					url += '&source=' + encodeURIComponent(window.location.href);
					url += '&sourcetitle=' + encodeURIComponent(window.document.title);
					url += '&tags=' + encodeURIComponent(_getTags().replace(/,/g, ' '));
					url += '&netCatchManage='+true;
					var height = lmcms_toolbox.getHeight() || 400;
					height -= 32;
					lmcms_toolbox.messageBox.open({
						'width'	:1024,
						'height': height,
						'title'	: '雷铭CMS(一键转载)',
						'url'	: url
					});
				}
			};
			var edit = function() {
				try {
					lmcms_toolbox.messageBox = new lmcms_toolbox.mainWin();
				}
				catch (e) {}
				if (!lmcms_toolbox.messageBox.isopen) {
					var url	 = lmcms_toolbox.adminUrl;
					url		+= '?app=system&controller=content&action=miniedit';
					url		+= '&contentid=' + (contentid || '') + '&url=' + location.href;
					var height = lmcms_toolbox.getHeight() || 400;
					height -= 32;
					lmcms_toolbox.messageBox.open({
						'width'	: 900,
						'height': height,
						'title'	: '\u7f16\u8f91\u5185\u5bb9',
						'url'	: url,
						'refresh': true
					});
				}
			};
			var del = function() {
				try {
					lmcms_toolbox.messageBox = new lmcms_toolbox.mainWin();
				}
				catch (e) {}
				if (!lmcms_toolbox.messageBox.isopen) {
					var contentIfm, statusBar, okBtn, canelBtn;
					contentIfm	= document.createElement('div');
					contentIfm.style.textAlign	= 'center';
					contentIfm.innerHTML	= '<p style="padding: 12px 0; font-size: 16px;">\u786e\u5b9a\u8981\u5220\u9664\u8fd9\u7bc7\u6587\u7ae0\u4e48?</p>';
					statusBar	= lmcms_toolbox.divMaker({'class':'lmcms-messagebox-body-statusbar'}, {}, contentIfm);
					canelBtn	= document.createElement('a');
					canelBtn.setAttribute('class', 'lmcms-messagebox-body-statusbar-cancel');
					canelBtn.setAttribute('className', 'lmcms-messagebox-body-statusbar-cancel');
					canelBtn.href	= 'javascript:;';
					canelBtn.innerHTML = '\u53d6\u6d88';
					statusBar.appendChild(canelBtn);
					okBtn	= document.createElement('input');
					okBtn.setAttribute('class', 'lmcms-messagebox-body-statusbar-ok');
					okBtn.setAttribute('className', 'lmcms-messagebox-body-statusbar-ok');
					okBtn.type	= 'button';
					okBtn.value	= '\u786e\u5b9a';
					okBtn.style.cursor	= 'pointer';
					statusBar.appendChild(okBtn);
					lmcms_toolbox.bind(okBtn, 'click', function() {
						var ifm = document.createElement('iframe');
						ifm.src	= lmcms_toolbox.adminUrl + '?app=system&controller=content&action=delete&contentid='+contentid;
						ifm.style.display	= 'none';
						document.body.appendChild(ifm);
						lmcms_toolbox.bind(ifm, 'load', function() {
							location.href='http://'+location.host;
						});
					});
					lmcms_toolbox.bind(canelBtn, 'click', function() {
						lmcms_toolbox.messageBox.closepanel();
					});
					lmcms_toolbox.messageBox.open({
						'width'	: 240,
						'height': 120,
						'title'	: '\u662f\u5426\u5220\u9664?',
						'content': contentIfm
					});
				}
			};
			var visualedit = function() {
				_jump(lmcms_toolbox.adminUrl+'?app=page&controller=page&action=view&pageid='+pageid);
			};
			var admin = function() {
				_jump(lmcms_toolbox.adminUrl);
			};
			var logout = function() {
				var ifm = document.createElement('iframe');
				ifm.src	= lmcms_toolbox.adminUrl + 'loginAction.do?logout';
				ifm.style.display	= 'none';
				document.body.appendChild(ifm);
				lmcms_toolbox.bind(ifm, 'load', function() {
					document.location.reload();
				});
			};
			switch (cmd) {
				case 'start':
					start();
					if (lmcms_toolbox.isMySite) {
						if (window.ENV) {
							alert("window.ENV="+window.ENV);
							_jump(lmcms_toolbox.adminUrl+'?app=special&controller=online&action=design&contentid='+ENV.contentid+'&pageid='+ENV.pageid);
						} else if (window.contentid) {
							alert("edit");
							edit();
						} else if (window.pageid) {
							alert("window.pageid="+window.pageid);
							_jump(lmcms_toolbox.adminUrl+'?app=page&controller=page&action=view&pageid='+pageid);
						}
					} else {
						reproduce();
					}
					break;
				case 'reproduce':
					reproduce();
					break;
				case 'edit':
					edit();
					break;
				case 'delete':
					del();
					break;
				case 'visualedit': 
					visualedit();
					break;
				case 'admin':
					admin();
					break;
				case 'logout':
					logout();
					break;
			}
		};
	}
})();
window.lmcms_toolbox.ready(lmcms_toolbox_cmd);