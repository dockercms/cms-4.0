var itz=itz||{};itz.register={},itz.register.init=function(regData){this.submit(regData)},itz.register.submit=function(regData){$("#vcode").find(".vcodeWrapper,.icon-refresh").click(function(){$(this).parent().find("img")[0].src="/lmcms/randCodeImage?a=" + new Date().getTime()});var enableSubmit=1;$("#regForm").validate({errorPlacement:function(error,element){var $p=element.parents("li").find(".rcf-alert"),$hint=$p.find(".rcf-alert-hint");$hint.length&&$hint.hide(),$p.find(".rcf-alert-error").html(error).show()},focusCleanup:!0,focusInvalid:!1,onfocusin:function(element){this.lastActive=element,this.settings.focusCleanup&&!this.blockFocusCleanup&&(this.settings.unhighlight&&this.settings.unhighlight.call(this,element,this.settings.errorClass,this.settings.validClass),this.addWrapper(this.errorsFor(element)).hide());var $ele=$(element),$p=$ele.parents("li");return"password"==$ele.attr("name")?($.trim($ele.val())?itz.pwd.renderStrenth($p.find("#pwdStr"),$ele):$p.find(".rcf-alert-hint").show(),void 0):($p.find(".success").length||$p.find(".rcf-alert-hint").show(),void 0)},onkeyup:function(ele){var $ele=$(ele);if("password"==$ele.attr("name")){var $pwdStr=$("#pwdStr"),$p=$ele.parent().find(".rcf-alert-hint");$.trim($ele.val())?(itz.pwd.renderStrenth($pwdStr,$ele),$p.is(":hidden")||$p.hide(),$pwdStr.is(":hidden")&&$pwdStr.show()):($pwdStr.hide(),$p.show())}},onfocusout:function(element){this.checkable(element)||!(element.name in this.submitted)&&this.optional(element)||this.element(element);var $ele=$(element),$p=$ele.parent();$.trim($ele.val())||$ele.valid(),$p.find(".rcf-alert-hint").hide(),"password"==$ele.attr("name")&&($ele.valid()?$p.find(".error").hide():$p.find("#pwdStr").hide())},success:function(ele){var $input=ele.parents("li").find("input");return"password"==$input.attr("name")?(itz.pwd.renderStrenth($("#pwdStr"),$input),ele.remove(),void 0):"reg_agreement"==$input.attr("name")?(ele.remove(),void 0):"username"==$input.attr("name")?(ele.html("一经注册不能修改").attr("class","success success2"),void 0):"valicode"==$input.attr("name")?(ele.remove(),void 0):(ele.attr("class","success"),void 0)},submitHandler:function(form){if(enableSubmit){enableSubmit=0;var $submitBtn=$("#regSubBtn"),$form=$(form),params=$form.serialize(),$regError=$("#regError"),$vcode=$("#vcode");$regError.is(":hidden")||$regError.hide(),$submitBtn.val("提交中..."),itz.form.disable($form),$.ajax({url:regData.subAjaxUrl,type:"post",dataType:"json",data:params,timeout:2e4,success:function(msg){ var data = JSON.parse(msg); if(0==data.code){alert(data.info);location.href=data.url;}else $vcode.find("input").val(""),$vcode.find("img")[0].src="/lmcms/randCodeImage?a=" + new Date().getTime(),$vcode.find(".success").hide(),$regError.text(data.info).show(),itz.form.enable($(form)),$submitBtn.val("提交"),enableSubmit=1},error:function(jqXHR,textStatus){return $vcode.find("input").val(""),$vcode.find("img")[0].src="/lmcms/randCodeImage?a=" + new Date().getTime(),$vcode.find(".success").hide(),itz.form.enable($(form)),$submitBtn.val("提交"),enableSubmit=1,"timeout"==textStatus?($regError.text("请求超时，请重试").show(),void 0):($regError.text("爱亲，网络不畅，请重试").show(),void 0)}})}},rules:{email:{required:!0,email:!0,rangelength:[5,50],remote:regData.checkEmailAjaxUrl+"&type=nojson"},username:{required:!0,isUserName:!0,sensitiveWord:!0,rangelength:[2,15],remote:regData.checkUsernameAjaxUrl+"&type=nojson"},password:{required:!0,isPwd:!0,rangelength:[6,16]},confirm_password:{required:!0,equalTo:"#password"},valicode:{required:!0},reg_agreement:{required:!0}},messages:{email:{required:"邮箱不能为空",email:"邮箱格式错误，请重新输入",rangelength:"邮箱长度请控制在5-50位",remote:function(v){return'该邮箱已被注册，请重新输入或用此邮箱 <a href="/newuser/index/login?username='+encodeURIComponent(v)+'">登录</a>'}},username:{required:"用户名不能为空",isUserName:"用户名只能由中文，字母，数字及下划线组成",sensitiveWord:"该昵称不允许注册",rangelength:"长度为2-15个字符",remote:function(v){return'用户名已被注册，重新输入或用此用户名 <a href="loginAct.do?login&username='+encodeURIComponent(v)+'">登录</a>'}},password:{required:"密码不能为空",isPwd:"密码存在非法字符",rangelength:"6-16位字符，包括英文字母、数字或符号"},confirm_password:{required:"确认密码不能为空",equalTo:"确认密码不一致"},valicode:{required:"验证码不能为空"},reg_agreement:{required:"请同意协议和条款"}}})},itz.form={disable:function($f){$f.addClass("form-disabled").find("input,select").attr("disabled",!0)},enable:function($f){$f.removeClass("form-disabled").find("input,select").removeAttr("disabled")}},itz.pwd={renderStrenth:function($w,$p){var $items=$w.find("span"),val=($items.length,$.trim($p.val()));if(val){var level,vLen=val.length,incNum=/[0-9]+/.test(val),incABC=/[a-z]+/.test(val),incAbc=/[A-Z]+/.test(val),incChar=/[_+=-@#~,.\[\]()!%^*$]+/.test(val),incType=(incNum?1:0)+(incABC?1:0)+(incAbc?1:0)+(incChar?1:0);6===vLen?level=1==incType?1:2:vLen>=7&&12>=vLen?level=2>=incType?2:3:vLen>=13&&16>=vLen?level=3:6>vLen&&(level=1),$items.each(function(i,ele){var $ele=$(ele);switch(level){case 1:$ele.hasClass("rcf-password-1")||($ele.attr("class","rcf-password-1"),0==i?$ele.html("弱"):$ele.html("&nbsp;"));break;case 2:$ele.hasClass("rcf-password-2")||($ele.attr("class","rcf-password-2"),1==i?$ele.html("中"):$ele.html("&nbsp;"));break;case 3:$ele.hasClass("rcf-password-3")||($ele.attr("class","rcf-password-3"),2==i?$ele.html("强"):$ele.html("&nbsp;"))}}),$w.is(":hidden")&&$w.show()}}};