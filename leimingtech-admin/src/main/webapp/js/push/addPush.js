$('.tips3').hide();


var dimensionTag = ["版本","渠道","标签","地域","语言","机型","用户活跃度"],options='<option value="" selected>请选择维度</option>';
for(var i=0;i<=6;i++){
	if(i==2||i==5||i==1){
		continue;
	}
   options += '<option value='+i+' >'+dimensionTag[i]+'</option>';
 }
var pre_province = "",app_channel = "" ,app_tag =  "",app_version =  "",device_model =  "",language = "",language_option="",alias_types="",selected_alias="",
 component='<div class="formItem" style="margin-left:15px;"><select class="dimension">'
              +options
              +'</select>'
              + '<select class="op">'
              +  '  <option value="contains" selected>包含</option>'
              + ' <option value="notcontains">不包含</option>'
              + '</select>'
              + '<select class="opversion" style="display:none">'
              + '<option value="contains" selected>包含</option>'
              + '<option value="notcontains">不包含</option>'
              + '<option value="ge">>=</option>'
              + '<option value="le"><=</option>'
              + '</select>'
              + '<select class="tagRelation" style="display: none;">'
              +' <option value="or" selected>OR</option>'
              +' <option value="and">AND</option>'
              +'</select>'
              + '<select class="activeselect" style="display:none">'
              + '<option value="0">不活跃</option>'
              + '<option value="1">活跃</option>'
              +'</select>'
              +'<input type="text" class="launch" style="display: none;" maxlength="2" placeholder="不大于30的正整数"><span class="myspan"  style="display:none;">天内</span>'
              + '<select  multiple="true" style="display:none;" class="chzn-select multiple" data-placeholder="Please choose an option..."></select>'
              + '<select style="display:none;" class="chzn-select single" data-placeholder="Please choose an option..."></select>'
              + '<div class="iconDel"><span class="deleteGroupIcon"></span></div>';

// pre_province = ['北京', '上海', '广东', '安徽', '澳门', '重庆', '福建', '甘肃', '广西', '贵州',
// '海南', '河北', '河南', '黑龙江', '湖北', '湖南', '吉林', '江苏', '江西', '辽宁', '内蒙', '宁夏',
// '青海', '山东', '山西', '陕西', '四川', '台湾', '天津', '西藏', '香港', '新疆', '云南', '浙江'];
// language =
// ['中文（简体）','中文（繁体）','英语','西班牙语','葡萄牙语','德语','法语','意大利语','阿拉伯语','土耳其语','印度语','韩语','俄语','日语','泰国语','波斯语','荷兰语'];
 language_option=['zh,zh-Hans,cn','zh-Hant','en,en-GB,en-AU,en_us,us','es,es-MX','pt','de','fr','it','ar','tr','in','ko','ru','ja','th','fa','nl'];


var diff = 7 * 24 * 60 *60 *1000;
var dimensions = ["app_version","channel","tag","province","language","device_model",""];

var dimensionData=[],segments={},myparams={},segmentContent={};
var dimensionName = {}
dimensionName["app_version"] = 0;
dimensionName["channel"] = 1;
dimensionName["tag"] = 2;
dimensionName["province"] = 3;
dimensionName["language"] = 4;
dimensionName["device_model"] = 5;
dimensionName["active"]=6;



var separator = "!";

function htmlEscape(str) {
    return String(str).replace(/&/g, '&amp;').replace(/"/g, '&quot;').replace(/'/g, '&#39;').replace(/</g, '&lt;').replace(/>/g, '&gt;');
}

function utf8strlen(str) {
    var realLength = 0, len = str.length, charCode = -1, i = 0;
    for (; i < len; i++) {
        charCode = str.charCodeAt(i);
        if (charCode >= 0 && charCode <= 128) {
            realLength += 1;
        } else {
            // 如果是中文则长度加3
            realLength += 3;
        }
    }
    return realLength;
}


function inputNumber(el) {
    el.value = el.value.replace(/\D/g, '').replace(/^[0]+/, '');
}
function validateInputNumber(el) {
    el.value = el.value.replace(/\D/g, '');
    if(el.value.length>2){
     el.value=el.value.substring(0,2);
    }
}



function slideBlock(el, func) {
    el.on("click", function(evt) {
        var target = $(evt.target), block = $(".green", $(this));

        if (!target.hasClass("green")) {
            block.toggleClass("right");
            func.call(null, block.hasClass("right"));
        }
    });
}

function isContains(datas,target){
  if(target.indexOf(",")>=0){
    var strs = target.split(",");
    for(key in strs){
      if(datas.indexOf(strs[key])>=0){
       return true;
      }
    }
  }else{
    if(datas.indexOf(target)>=0){
      return true;
    }
  }
  return false;
}
$(function() {
    var appkey = APPKEY, url,fromtest=false;
    try {
    	var umengUrl="";// 'http://message.umeng.com';
        var url_params = location.href.split("?")[1].split("&");
        var mid = url_params[0].split("=")[1];
        if(url_params.length==2 && url_params[1]=="fromtest"){
          fromtest = true;
        }
        if(!IS_TEST){
            url = umengUrl+'/msg/' + appkey + '/getPush?msgId=' + mid + '&callback=?';
        } else {
            url = umengUrl+'/msg/' + appkey + '/getPush?msgId=' + mid + '&testMode=1&callback=?';
        }
    } catch(e) {
        if(!IS_TEST){
            url = umengUrl+'/msg/' + appkey + '/getPush?callback=?';
        } else {
            url = umengUrl+'/msg/' + appkey + '/getPush?testMode=1&callback=?';
        }
    };

    function init(platform) {
        if(platform != "iOS"){
            slideBlock($("#pushtype_slider"), function(flag) {
                if (flag) {
                    $("#messageInputArea").hide();
                    $("#customContentArea").show();
                    $("#push_type").val("1");
                     $("#customicon").hide();
                     $("#customsound").hide();
                     $('#alertStyle').parent().hide();
                     $('#messageExtra').show();
                } else {
                    $("#messageInputArea").show();
                    $("#customContentArea").hide();
                    $("#push_type").val("0");
                    $("#customicon").show();
                    $("#customsound").show();
                    $('#alertStyle').parent().show();
                    $('#messageExtra').hide();

                }
            });
        }
        if ($("#expireTime").val() == "") {
             var now = new Date().getTime(), tdl = new Date(now + 3600 * 24 * 1000);
             $("#expireTime").val(tdl.getFullYear() + "-" + ((tdl.getMonth() + 1) > 9 ? (tdl.getMonth() + 1) : ("0" + (tdl.getMonth() + 1))) + "-" + (tdl.getDate() > 9 ? tdl.getDate() : "0" + tdl.getDate()) + " " + (tdl.getHours() > 9 ? tdl.getHours() : ("0" + tdl.getHours())) + ":" + (tdl.getMinutes() > 9 ? tdl.getMinutes() : "0" + tdl.getMinutes()) + ":" + (tdl.getSeconds() > 9 ? tdl.getSeconds() : "0" + tdl.getSeconds()));
               }
        $('.return-btn').click(function() {
            if(IS_TEST){
                location.href = "/" + appkey + "/testMsg";
            } else {
                location.href = "/" + appkey + "/dashboard";
            }
        });
        $("#useGender").attr("checked",false);
        var genderBox = $(".gendercheckbox");
        for(var t=0;t<genderBox.length;t++){
           genderBox[t].checked=false;
         }
        $(".formItem").each(function() {
            $(this).find(".tabshow:first").show().siblings(".tabshow").hide();
            $(this).find(".radioSel:first").click();
        });

        $(".datetime,#deviceId").next().hide();
        checkWord();
        
    }
$(document).on("click",".templateId",function(){
   if($(this).attr("checked")!=undefined){
         $(".templateId").attr("checked",false);
        $(this).attr("checked",true);
       var index = $(this).val();
        if(segmentContent[index]){
         completeSegment(segmentContent[index]);
        }else{
          $.get('/'+appkey+'/getFilter',{"segment_id":index}, function(d) {
              completeSegment(d);
             segmentContent[index] = d;
           });
        }
      }else{
       $(".dimension").parent().remove();
      var genderBox = $(".gendercheckbox");
      for(var t=0;t<genderBox.length;t++){
          genderBox[t].checked=false;
       }
       $("#useGender").attr("checked",false);
        $("#addCondition").before(component+'<span class="tips3" style="display: none;">筛选条件>重复，请修改</span></div>');
         var dimensionSelect = $(".dimension").last();
         var myselect = dimensionSelect.siblings("select.multiple");
         dimensionSelect.siblings("select.single").hide();
         myselect.chosen();
      }
   });
function completeSegment(d){
  $(".dimension").parent().remove();
  var genderBox = $(".gendercheckbox");
  for(var t=0;t<genderBox.length;t++){
    genderBox[t].checked=false;
  }
  $("#useGender").attr("checked",false);
  var contains = {};
  if(d.contains){
     var g = d.contains.split(';');
     for (i in g) {
        if (g[i]) {
          var gi = g[i].split(':');
          if (gi[1]) {

             if(gi[0]=="device_model"){
               if (gi[1].split(separator).length > 0) {
                     contains[gi[0]] = gi[1].split(separator);
                  }
              }else{
                  if (gi[1].split(",").length > 0) {
                        contains[gi[0]] = gi[1].split(",");
                     }
               }
           }
       }

     }
  }

  var notContains = {};
  if(d.notContains){
    var g = d.notContains.split(';');
    for (i in g) {
       if (g[i]) {
          var gi = g[i].split(':');
          if (gi[1]) {
            if(gi[0]=="device_model"){
               if(gi[1].split(separator).length > 0){
                  notContains[gi[0]] = gi[1].split(separator);
                }
            }else if (gi[1].split(",").length > 0) {
                notContains[gi[0]] = gi[1].split(",");
             }
          }
    }

  }
 }
 var tagrelation=0;
 if(d.relation){
     tagrelation = 1;
 }
  for (var k in contains){
    if(k=="gender"){
         $("#useGender").attr("checked",true);
         $('input[name="gender"]').removeAttr("disabled");
         $('input[name="gender"]').each(function() {
         if (contains[k].join(",").indexOf($(this).val())>=0) {
             $(this).attr("checked",true);
             }
           });
           continue;
      }
     var selection= dimensionName[k];
     var datas = dimensionData[selection];
     $("#addCondition").before(component+'<span class="tips3" style="display: none;">筛选条件>重复，请修改</span></div>');

     var dimensionSelect = $(".dimension").last();
     var myselect = dimensionSelect.siblings("select.multiple");
     var singleselect = dimensionSelect.siblings("select.single");
     singleselect.chosen();
     singleselect.next().hide();
     dimensionSelect.get(0).selectedIndex=selection+1;
     if(selection == 2 ){
        dimensionSelect.siblings("select.tagRelation").show();
       if( tagrelation=="and"){
             dimensionSelect.siblings("select.tagRelation").get(0).selectedIndex=1;
          }
     }else{
        dimensionSelect.siblings("select.tagRelation").hide();
        if(selection == 0){
          // if(digitalVersion()){
             dimensionSelect.siblings("select.op").hide();
             dimensionSelect.siblings("select.opversion").show();
         // }
        }
     }
     if(selection == 4){
     datas = language_option;
     $.each(datas,function(i,e){
       if(isContains(contains[k],e)){
          myselect.append("<option value='"+e+"' selected='selected'>"+language[i]+"</option>");
        }else {
          myselect.append("<option value='"+e+"'>"+language[i]+"</option>");
        }
     });
     myselect.chosen();
     myselect.trigger("liszt:updated");
    }else{

      var useOperation = false;
      if(k =="app_version"){
        if(contains[k][0].indexOf("ge_|")>=0){
            dimensionSelect.siblings("select.opversion").get(0).selectedIndex=2;
            useOperation = true;
        }else if(contains[k][0].indexOf("le_|")>=0){
            dimensionSelect.siblings("select.opversion").get(0).selectedIndex=3;
            useOperation = true;
        }
      }
      if(useOperation){
         $.each(datas,function(i,e){
           if(contains[k][0].indexOf(e)>=0){
               singleselect.append("<option value='"+e+"' selected='selected'>"+htmlEscape(e)+"</option>");
            }else {
               singleselect.append("<option value='"+e+"'>"+htmlEscape(e)+"</option>");
             }
           });
         myselect.chosen();
         myselect.next().hide();
         singleselect.chosen();
         singleselect.next().fadeIn();
         singleselect.trigger("liszt:updated");
      }else{
          $.each(datas,function(i,e){
           if(contains[k].indexOf(e)>=0){
                   myselect.append("<option value='"+e+"' selected='selected'>"+htmlEscape(e)+"</option>");
             }else {
                   myselect.append("<option value='"+e+"'>"+htmlEscape(e)+"</option>");
              }
        });
        if(selection <6){
              myselect.chosen();
              myselect.trigger("liszt:updated");
            }
      }
   }


 }
  for (var k in notContains){
    var selection= dimensionName[k];
    var datas = dimensionData[selection];
    var vals = component;
    vals += '<span class="tips3" style="display: none;">筛选条件>重复，请修改</span></div>';
    $('#addCondition').before(vals);
    var dimensionSelect = $(".dimension").last();
    var myselect = dimensionSelect.siblings("select.multiple");
    var singleselect = dimensionSelect.siblings("select.single");
    singleselect.chosen();
    singleselect.next().hide();
    dimensionSelect.get(0).selectedIndex=selection+1;
    dimensionSelect.siblings("select.op").get(0).selectedIndex=1;
    if(selection == 2){
      dimensionSelect.siblings("select.tagRelation").show();
      if(tagrelation == 1){
         dimensionSelect.siblings("select.tagRelation").get(0).selectedIndex=1;
      }
    }else{
      dimensionSelect.siblings("select.tagRelation").hide();
      if(selection == 0){
       // if(digitalVersion()){
            dimensionSelect.siblings("select.op").hide();
            dimensionSelect.siblings("select.opversion").show();
            dimensionSelect.siblings("select.opversion").get(0).selectedIndex=1;
       // }
      }
    }
   if(selection == 4){
     datas = language_option;
     $.each(datas,function(i,e){
         if(isContains(notContains[k],e)){
            myselect.append("<option value='"+e+"' selected='selected'>"+language[i]+"</option>");
         }else {
            myselect.append("<option value='"+e+"'>"+language[i]+"</option>");
         }
     });
   }else{
     $.each(datas,function(i,e){

      if(notContains[k].indexOf(e)>=0){
          myselect.append("<option selected='selected'>"+htmlEscape(e)+"</option>");
      }else {
          myselect.append("<option value='"+e+"'>"+htmlEscape(e)+"</option>");
      }
    });
  }
  if(selection <6){
      myselect.chosen();
      myselect.trigger("liszt:updated");
     }

  }
  if(d.not_launch_in || d.launch_in){
     var options="",vals=component;
     vals += '<span class="tips3" style="display: none;">筛选条件>重复，请修改</span></div>';
     $('#addCondition').before(vals);
     var selection = 6;
     var dimensionSelect = $(".dimension").last();
     dimensionSelect.get(0).selectedIndex=selection+1;
     dimensionSelect.siblings("select.op").hide();
     dimensionSelect.siblings("input.launch").show();
     dimensionSelect.siblings(".myspan").show();
     dimensionSelect.siblings("select.activeselect").show();
     if(d.launch_in){
         dimensionSelect.siblings("select.activeselect").get(0).selectedIndex=1;
         dimensionSelect.siblings("input.launch").val(d.launch_in);
     }else{
      dimensionSelect.siblings("input.launch").val(d.not_launch_in);
     }

     $(".launch").bind('input',function(e){
         inputNumber(this);
     });
  }
}
pre_province = ['北京', '上海', '广东', '安徽', '澳门', '重庆', '福建', '甘肃', '广西', '贵州', '海南', '河北', '河南', '黑龙江', '湖北', '湖南', '吉林', '江苏', '江西', '辽宁', '内蒙', '宁夏', '青海', '山东', '山西', '陕西', '四川', '台湾', '天津', '西藏', '香港', '新疆', '云南', '浙江'];
language = ['中文（简体）','中文（繁体）','英语','西班牙语','葡萄牙语','德语','法语','意大利语','阿拉伯语','土耳其语','印度语','韩语','俄语','日语','泰国语','波斯语','荷兰语'];
dimensionData[0]=['1.0','2.0','3.0','4.0','5.0','6.0','7.0','8.0','9.0','10.0'];
dimensionData[3] = pre_province;
dimensionData[4] = language;
$(document) .on( 'change', '.dimension', function() {
	var obj = $(this);
	var index = obj.val();
	var datas = "";
	var label = obj.siblings(".myspan"); 
	var op = obj.siblings("select.op");
	var opversion = obj.siblings("select.opversion");
	var optxt = obj .siblings("input.launch"); 
	var myselect = obj .siblings("select.multiple");
	var tagrelation = obj .siblings("select.tagRelation"); 
	var activeSelect = obj .siblings("select.activeselect");
	myselect.empty();
	opversion.hide();
	op.show();
	var singleselect = obj.siblings("select.single");
	singleselect.chosen();
	singleselect.next().hide();
	datas = dimensionData[index];
	if (index == "") {
		myselect.chosen();
		myselect.trigger("liszt:updated");
		optxt.hide();
		label.hide();
		tagrelation.hide();
		activeSelect.hide();
		myselect.next().fadeIn();
		myselect.prop('disabled', false).trigger("liszt:updated");
		op.show();
	} else if (index == 6) {
		op.hide();
		myselect.next().hide();
		tagrelation.hide();
		optxt.show();
		label.show();
		activeSelect.show();
		optxt.bind('input', function(e) {
			inputNumber(this);
		});
	} else {
		if(datas==undefined&&index!=3){
			alert("暂不支持"+$(".dimension :selected").text()+"维度!");
			return;
		}
		
		activeSelect.hide();
		if (index == 4) {
			$.each(datas, function(i, e) {
				myselect.append("<option value=" + language_option[i] + ">" + e + "</option>");
			});
		} else {
			$.each(datas, function(i, e) {
				myselect.append("<option value='" + e + "'>" + htmlEscape(e) + "</option>");
			});
		}
		if (index == 2) {
			tagrelation.show();
		} else {
			tagrelation.hide();
			if (index == 0) {
				op.hide();
				opversion.show();
			}
		}
		myselect.chosen();
		myselect.trigger("liszt:updated");
		optxt.hide();
		label.hide();
		myselect.next().fadeIn();
		myselect.prop('disabled', false).trigger( "liszt:updated");
	}
});

  $(document).on('change', '.opversion', function(){
      var obj = $(this),selected=obj.val(),myselect = obj.siblings("select.multiple"),singleselect = obj.siblings("select.single");
      if(selected !="contains" && selected != "notcontains"){
        myselect.next().hide();
        singleselect.empty();
       $.each(dimensionData[0],function(i,e){
          singleselect.append("<option value='"+e+"'>"+htmlEscape(e)+"</option>");
         });
        singleselect.chosen();
        singleselect.next().fadeIn();
        singleselect.prop('disabled', false).trigger("liszt:updated");
      }else{
        singleselect.next().hide();
        myselect.empty();
        $.each(dimensionData[0],function(i,e){
             myselect.append("<option value='"+e+"'>"+htmlEscape(e)+"</option>");
        });
        myselect.chosen();
        myselect.next().fadeIn();
        myselect.prop('disabled', false).trigger("liszt:updated");
      }
    });

    function digitalVersion(){
     var data = dimensionData[0];
     if(!data.join(",")){return false;}
     for(var i = 0;i<data.length;i++){
      var str = data[i].replace(/\./g,'').replace(/\d+/g,'');
        if(str!=""){
          return false;
        }
     }
     return true;
    }

    $("input[value='sendnow']").click();
    $("input[name='vibraPrompt']").attr("checked", "true");
    $("input[name='soundPrompt']").attr("checked", "true");
    $("input[name='lightPrompt']").attr("checked", "true");

    $.getJSON(url, function(d) {
        if (d.success == 1) {
            // $('.left-sidebar h2').text(d.name).attr('title', d.name);
            $("#pushDes").val(d.description);
            $("#pushTitle").val(d.msg_title);
            $("#pushText").val(d.msg_text);
            if(!is_expired(d.start_time)){
              $("#sendTime").val(d.start_time);
              }
            $("#notLaunchIn").val(d.not_launch_in);
                
            if(d.platform == "iOS"){
                $('iosArea').show();
               // $("#ios_sound").val(d.ios_sound);

                $("#ios_badge").val(d.ios_badge);
                $("#category").val(d.category);

                // d.ios_custom

                if(d.ios_custom){
                    $('.customkeyvalue').remove();
                    for(var k in d.ios_custom){
                        addIOSKyeValue(k, d.ios_custom[k]);
                    }
                }

                $("#expireTimeArea").show();
                if(!is_expired(d.end_time)){
                  $("#expireTime").val(d.end_time);
                }

            } else {
                $('#push_type_area, #androidArea, #expireTimeArea').show();
                
                // 推送类型 android下使用
                $("#push_type").val(d.push_type);

                if (d.push_type == 1) {
                    $("#custom_content").val(d.custom_content);
                    $("#pushtype_slider .green").addClass("right");
                    $("#messageInputArea").hide();
                    $("#customContentArea").show();
                    $("#customicon").hide();
                    $("#customsound").hide();
                    $('#messageExtra').show();
                    if (d.custom_content) {
                        $("#customContentTip").hide();
                    }
                }
                if(!is_expired(d.end_time)){
                  $("#expireTime").val(d.end_time);
                 }
                $("#openUrl_val").val(d.url);
                $("#openActivity_val").val(d.activity);
                $("#openCustom_val").val(d.custom);
                if (d.play_vibrate == "false") {
                    $("input[name='vibraPrompt']").removeAttr("checked");
                }
                if (d.play_sound == "false") {
                    $("input[name='soundPrompt']").removeAttr("checked");
                }
                if (d.play_lights == "false") {
                    $("input[name='lightPrompt']").removeAttr("checked");
                }

            }

            if (d.type == "groupcast") {
                var contains = {};
                if(d.contains){
                 var g = d.contains.split(';');
                 for (i in g) {
                    if (g[i]) {
                        var gi = g[i].split(':');
                        if (gi[1]) {

                            if(gi[0]=="device_model"){
                             if (gi[1].split(separator).length > 0) {
                                  contains[gi[0]] = gi[1].split(separator);
                                     }
                            }else{
                             if (gi[1].split(",").length > 0) {
                                    contains[gi[0]] = gi[1].split(",");
                               }
                            }
                        }
                    }

                 }
                }

                var notContains = {};
                if(d.notContains){
                 var g = d.notContains.split(';');
                 for (i in g) {
                    if (g[i]) {
                       var gi = g[i].split(':');
                       if (gi[1]) {
                          if(gi[0]=="device_model"){
                           if(gi[1].split(separator).length > 0){
                              notContains[gi[0]] = gi[1].split(separator);
                            }
                          }else if (gi[1].split(",").length > 0) {
                               notContains[gi[0]] = gi[1].split(",");
                                      }
                                  }
                              }

                           }
               }

            }
            language_option=['zh,zh-Hans,cn','zh-Hant','en,en-GB,en-AU,en_us,us','es,es-MX','pt','de','fr','it','ar','tr','in','ko','ru','ja','th','fa','nl'];
            app_channel =  d.predefined_app_channel.split(",");
            app_tag =  d.predefined_app_tag.split(",");
            app_version =  d.predefined_app_version.split(",");
            device_model =  d.predefined_device_model.split(separator);
            alias_types = d.predefined_alias_type.split(",");
            // language = d.predefined_language.split(",");
            dimensionData[0] = app_version;
            dimensionData[1] = app_channel;
            dimensionData[2] = app_tag;
            dimensionData[5] = device_model;

            segments = d.segments;
            var str = "";
            if(segments){
             var num = 1;
             for(var key in segments){
                str += "<div class='iconDelDiv'><input type='checkbox' name='templates' value='"+key+"' class='templateId' id='"+key+"'><label>"+segments[key]+"</label><span class='deleteSegment'></span></div>";
                num++;
             }
             if(num ==1){
               str='暂无可用模板';
             }
               $("#templateList").html(str);
            }
            init(d.platform);
            if(d.platform=="iOS"){
              if(d.ios_sound){
                  if(d.ios_sound=="default"){
                     $('input[name="sound_ios"]:eq(0)').attr('checked', true);
                     $('input[name="ios_sound"]').hide();
                  }else{

                     $('input[name="sound_ios"]:eq(2)').attr('checked', true);
                     $('input[name="ios_sound"]').show();
                     $("#ios_sound").val(d.ios_sound);
                  }

               }else{
                     $('input[name="sound_ios"]:eq(1)').attr('checked', true);
                     $('input[name="ios_sound"]').hide();
              }
            }

            if (d.type == 'unicast') {
                $("#deviceId").val(d.target);
                $("input[value='sendin']").closest('label').hide();
            }else if(d.type=='groupcast'){
               var tagrelation=0;
               if(d.relation){
                  tagrelation = 1;
               }
               for (var k in contains){
                 if(k=="gender"){
                  $("#useGender").attr("checked",true);
                  $('input[name="gender"]').removeAttr("disabled");
                  $('input[name="gender"]').each(function() {
                        if (contains[k].join(",").indexOf($(this).val())>=0) {
                         $(this).attr("checked",true);
                     }
                  });
                  continue;
                 }
                 var selection= dimensionName[k];
                 var datas = dimensionData[selection];
                 $("#addCondition").before(component+'<span class="tips3" style="display: none;">筛选条件>重复，请修改</span></div>');

                 var dimensionSelect = $(".dimension").last();
                 var myselect = dimensionSelect.siblings("select.multiple");
                 var singleselect = dimensionSelect.siblings("select.single");
                 singleselect.chosen();
                 singleselect.next().hide();
                 dimensionSelect.get(0).selectedIndex=selection+1;
                 if(selection == 2 ){
                    dimensionSelect.siblings("select.tagRelation").show();
                    if( tagrelation == 1){
                     dimensionSelect.siblings("select.tagRelation").get(0).selectedIndex=1;
                    }
                 }else{
                   dimensionSelect.siblings("select.tagRelation").hide();
                   if(selection == 0){
                   // if(digitalVersion()){
                       dimensionSelect.siblings("select.op").hide();
                       dimensionSelect.siblings("select.opversion").show();
                    // }
                   }
                 }
                 if(selection == 4){
                   datas = language_option;
                    $.each(datas,function(i,e){
                       if(isContains(contains[k],e)){
                              myselect.append("<option value='"+e+"' selected='selected'>"+language[i]+"</option>");
                        }else {
                              myselect.append("<option value='"+e+"'>"+language[i]+"</option>");
                           }
                      });
                 }else{
                  $.each(datas,function(i,e){

                       if(contains[k].indexOf(e)>=0){
                         myselect.append("<option value='"+e+"'selected='selected' >"+htmlEscape(e)+"</option>");
                       }else {
                         myselect.append("<option value='"+e+"'>"+htmlEscape(e)+"</option>");
                         }
                           });
                   }
                   if(selection <6){
                     myselect.chosen();
                     myselect.trigger("liszt:updated");
                     }

               }

               for (var k in notContains){
                    var selection= dimensionName[k];
                    var datas = dimensionData[selection];
                    var vals = component;
                     vals += '<span class="tips3" style="display: none;">筛选条件>重复，请修改</span></div>';
                    $('#addCondition').before(vals);
                    var dimensionSelect = $(".dimension").last();
                    var myselect = dimensionSelect.siblings("select.multiple");
                    var singleselect = dimensionSelect.siblings("select.single");
                    singleselect.chosen();
                    singleselect.next().hide();
                    dimensionSelect.get(0).selectedIndex=selection+1;
                    dimensionSelect.siblings("select.op").get(0).selectedIndex=1;
                    if(selection == 2){
                         dimensionSelect.siblings("select.tagRelation").show();
                         if(tagrelation == 1){
                         dimensionSelect.siblings("select.tagRelation").get(0).selectedIndex=1;
                         }
                      }else{
                       dimensionSelect.siblings("select.tagRelation").hide();
                       if(selection == 0){
                          // if(digitalVersion()){
                            dimensionSelect.siblings("select.op").hide();
                            dimensionSelect.siblings("select.opversion").show();
                            dimensionSelect.siblings("select.opversion").get(0).selectedIndex=1;
                             // }
                        }
                      }
                    if(selection == 4){
                       datas = language_option;
                       $.each(datas,function(i,e){
                         if(isContains(notContains[k],e)){
                            myselect.append("<option value='"+e+"' selected='selected'>"+language[i]+"</option>");
                           }else {
                            myselect.append("<option value='"+e+"'>"+language[i]+"</option>");
                             }
                        });
                    }else{
                     $.each(datas,function(i,e){

                       if(notContains[k].indexOf(e)>=0){
                            myselect.append("<option value='"+e+"' selected='selected'>"+htmlEscape(e)+"</option>");
                        }else {
                            myselect.append("<option value='"+e+"'>"+htmlEscape(e)+"</option>");
                        }
                      });
                      }
                    if(selection <6){
                      myselect.chosen();
                      myselect.trigger("liszt:updated");
                      }

               }
               if(d.not_launch_in || d.launch_in){
                 var options="",vals=component;
                 vals += '<span class="tips3" style="display: none;">筛选条件>重复，请修改</span></div>';
                    $('#addCondition').before(vals);
                    var selection = 6;
                    var dimensionSelect = $(".dimension").last();
                    dimensionSelect.get(0).selectedIndex=selection+1;
                    dimensionSelect.siblings("select.op").hide();
                    dimensionSelect.siblings("input.launch").show();
                    dimensionSelect.siblings(".myspan").show();
                    dimensionSelect.siblings("select.activeselect").show();
                    if(d.launch_in){
                     dimensionSelect.siblings("select.activeselect").get(0).selectedIndex=1;
                     dimensionSelect.siblings("input.launch").val(d.launch_in);
                    }else{
                     dimensionSelect.siblings("input.launch").val(d.not_launch_in);
                    }

                    $(".launch").bind('input',function(e){
                          inputNumber(this);
                             });
               }
            }
            try {
                $('input[value="' + d.type + '"]').click();
                if (d.start_time) {
                    $("input[value='sendin']").click();
                }
                if (d.after_open) {
                    var ao = d.after_open.replace("go_", "").split("");
                    ao[0] = ao[0].toUpperCase();
                    ao = "open" + ao.join("");
                    $('input[value="' + ao + '"]').click();
                }
                if (d.target) {
                    $(".mutiSelTip").hide();
                }

                if (d.type == 'unicast') {
                    $("input[value='sendin']").closest('label').hide();
                }

                if(d.type == 'customizedcast'){
                    $('#customizedcast_val').val(d.alias);
                    var aliasTypeSelect =  $("#alias_type_select");
                    aliasTypeSelect.empty();
                    selected_alias = d.alias_type;
                    if(selected_alias){
                       $.each(alias_types,function(i,e){
                        if(selected_alias == e){
                            aliasTypeSelect.append("<option selected>"+htmlEscape(e)+"</option>");
                            }else{
                            aliasTypeSelect.append("<option>"+htmlEscape(e)+"</option>");
                         }
                      });
                      aliasTypeSelect.chosen();
                      aliasTypeSelect.trigger("liszt:updated");
                    }
                    $('input[name="sendTime"]').eq(0).click();
                    $('input[name="sendTime"]').eq(1).parent().hide();
                }

            } catch(e) {
                console.log("error");
            };
             var accumulate = 1;
             var prime=[2,3,5,7,11,17,19];
            // add extra key and value
            if((d.push_type==0)&&(d.extra)){
                $('#extrakeyvalue').attr('checked', true);
                $('#addExtrakeyvalue').removeAttr('disabled').show();
                for(var k in d.extra){
                    var value = htmlEscape(d.extra[k]);
                    k = htmlEscape(k);
                    $('#addExtrakeyvalue').before('<div class="customrow"><label>Key:<input type="text" class="input" name="key" value="' + k + '"/></label><label>Value:<input type="text" class="input" name="value" value="' + value + '"/></label><span class="deleteIcon"></span><span class="tips3" style="display: none;">请完整填写自定义参数</span></div>');
                }
            }else if((d.push_type==1)&&(d.extra)){
               $('#extrakv').attr('checked', true);
               $('#extrakv').parent().siblings(".alert").show();
               $('#addMessageExtra').removeAttr('disabled').show();
               for(var k in d.extra){
                   var value = htmlEscape(d.extra[k]);
                   k = htmlEscape(k);
                   $('#addMessageExtra').before('<div class="customkv"><label>Key:<input type="text" class="input" name="key" value="' + k + '"/></label><label>Value:<input type="text" class="input" name="value" value="' + value + '"/></label><span class="deleteIcon"></span><span class="tips3" style="display: none;">请完整填写自定义参数</span></div>');
                  }
                 }


            if(d.icon_type){
                $('#customiconckb').attr('checked', true);
                $('#customIconShow').show();
                if(d.icon_type == 1){
                    $('input[name="icon_type"]:eq(0)').attr('checked', true);
                    $('#customIconShow input[name="largeIcon"]').val(d.largeIcon);
                    $('#customIconShow input[name="icon"]').val(d.icon);
                } else {
                    $('input[name="icon_type"]:eq(1)').attr('checked', true);
                    $('#uploadiconarea input[name="largeIcon"]').val(d.img);
                    $('#uploadiconarea input[name="icon"]').val(d.icon);
                    $('#blah').attr('src', d.img).attr('width', 128).attr('height', 128).show();

                    $('#appiconarea').hide();
                    $('#uploadiconarea').show();

                    // 显示重新上传按钮
                }

            }

            if(d.sound){
                $('#customsoundckb').attr('checked', true);
                $('#customsoundrow input[name="sound"]').val(d.sound);
                $('#customsoundrow').show();
            }

            if(d.max_send_num > 0){
                $('#ristrictSpeech').attr('checked', true);
                $('#max_send_num').val(d.max_send_num);
                $('#max_send_num').removeAttr('disabled');
            }

            if(d.icon_type || d.sound || d.max_send_num){
                $('.advance').click();
            }

            if(d.buildid){
                $('#alertStyle').val(d.buildid);
            }
        }
    });

    function checkWord() {
        // $(".push-preview .preTitle").text($("#pushTitle").val());
        var total = 0;
        if(APPPLATFORM != 'iOS'){
            $(".push-preview .preTitle").text($("#pushTitle").val());
            $(".push-preview .preText").text($("#pushText").val());
            total = 180;
        } else {
            $(".push-iospreview .preText").text($("#pushText").val());
            total=1900;
        }
        var res = true;

        var textLength = total - utf8strlen($("#pushText").val().trim());
        // - $("#pushTitle").val().length;
        if (textLength == total || $(".push-preview .pushDes").val() == "") {
            $("#pushWordCheck").html('还可以输入<b class="purple">' + textLength + '</b>个字符');
            res = false;
        }
        if (textLength >= 0) {
            $("#pushWordCheck").html('还可以输入<b class="purple">' + textLength + '</b>个字符');
        } else {
            $("#pushWordCheck").html('已经超过<b class="red">' + Math.abs(textLength) + '</b>个字符');
            res = false;
        }
        if($("#custom_content").val()){
        textLength = 800 - utf8strlen($("#custom_content").val().trim());
                // - $("#pushTitle").val().length;
        if (textLength == 800 ) {
              $("#customContentCheck").html('还可以输入<b class="purple">' + textLength + '</b>个字符');
                  // res = false;
               }
             if (textLength >= 0) {
                    $("#customContentCheck").html('还可以输入<b class="purple">' + textLength + '</b>个字符');
                } else {
                    $("#customContentCheck").html('已经超过<b class="red">' + Math.abs(textLength) + '</b>个字符');
                   // res = false;
                }
        }
        return res;
    }

    function ckword(el, infoEl, maxlen) {

        var textLength = maxlen - utf8strlen(el.val());
        if (textLength < 0) {
            infoEl.html('已经超过<b class="red">' + Math.abs(textLength) + '</b>个字符');
            return false;
        } else {
            infoEl.html('还可以输入<b class="purple">' + textLength + '</b>个字符');
            return true;
        }
    }
    function is_expired(time){
       if(time){
       var date_and_time = time.split(" ");
       var date_part = date_and_time[0].split("-");
       var time_part = date_and_time[1].split(":");
       var the = new Date(date_part[0],date_part[1]-1,date_part[2],time_part[0],time_part[1],time_part[2]), now = new Date();
       if(the - now > 0){
            return false;
          }else{
            return true;
          }
       }
        }
   function checkDateTime(str){
	    var reg = /^(\d{1,4})(-|\/)(\d{1,2})\2(\d{1,2}) (\d{1,2}):(\d{1,2}):(\d{1,2})$/;
	    var r = str.match(reg);
	    if(r==null)return false;
	    var d= new Date(r[1], r[3]-1,r[4],r[5],r[6],r[7]);
	    return (d.getFullYear()==r[1]&&(d.getMonth()+1)==r[3]&&d.getDate()==r[4]&&d.getHours()==r[5]&&d.getMinutes()==r[6]&&d.getSeconds()==r[7]);
   }
    function ckTextInputLength(el, maxlen, minLen) {
        var len = utf8strlen(el.val());
        minLen = minLen || 0;
        if (len >= minLen && len <= maxlen) {
            return 1;
        }
        if(len > maxlen){
            return -1;
        }
        if(len < minLen){
            return -2;
        }
        return 1;
    }

    function ckOpenurl() {
        var el = $("#openUrl_val"), v = el.val().trim();
        if (v.indexOf("http://") == 0 || v.indexOf("https://") == 0) {
            $("#urlTip").hide();
            return true;
        } else {
            $("#urlTip").show();
            return false;
        }
    }
    $("#openUrl_val").on("blur", ckOpenurl);
    $("#openUrl_val").on("input", ckOpenurl);

    $('#ios_badge').on('input', function(evt){
        inputNumber(this);
    });
    $("#templateName").on("blur",ckTemplateName);
    $("#templateName").on("input",ckTemplateName);
    function ckTemplateName(){
      var el = $('#templateName'), v = el.val().trim();
      if(utf8strlen(v)>20){
       $("#destnewtip").text("长度不能超过20字符").show();
       return false;
      }else{
        $("#destnewtip").hide();
      }
      return true;
    }
    function ckActivity() {
        var el = $('#openActivity_val'), v = el.val().trim();
        if (v) {
            $('#activityTip').hide();
        } else {
            $('#activityTip').show();
        }
    }


    $("#openActivity_val").on("blur", ckActivity);
    $("#openActivity_val").on("input", ckActivity);

    function ckCustomcontent() {
        var el = $("#custom_content"), tip = $("#customContentTip");
        el.val() ? tip.hide() : tip.show();
        ckword(el, $("#customContentCheck"), 800);
    }


    $("#custom_content").on("keyup", ckCustomcontent);
    $("#custom_content").on("input paste", ckCustomcontent);

    function ckOpenCustom() {
        var el = $('#openCustom_val'), tip = $("#customTextTip");
        el.val() ? tip.hide() : tip.show();
        ckword(el, $("#customNumCheck"), 50 * 3);

    }

    $("#openCustom_val").on("keyup", ckOpenCustom);
    $("#openCustom_val").on('input', ckOpenCustom);
    $(document).on('keyup', '.launch', function(){
      if($(this).val()>30){
        $(this).siblings("span.tips3").text("天数不能超过30").show();
        }else{
          $(this).siblings("span.tips3").hide();
        }
    });

    function ckPushDes() {

        checkWord();
        var el = $('#pushDes'), v = el.val().trim(), len = utf8strlen(v);
        if (v && len <= 25 * 3) {
            el.next("span").hide();
            return true;
        } else if (len > 25 * 3) {
            el.next("span").html("长度不能超过75字符").show();
        } else {
            el.next("span").html("消息描述不能为空").show();
        }
        return false;
    }


    $("#pushDes").on("keyup", ckPushDes);
    $("#openCustom_val").on('input', ckPushDes);

    function ckPushTitle() {
         checkWord();
        var el = $("#pushTitle"), v = el.val(), len = utf8strlen(v);
        if (v) {
            if (len > 15 * 3) {
                el.next("span").text("不能超过15个字符").show();
                return false;
            } else {
                el.next("span").hide();
                return true;
            }
        } else {
            el.next("span").text("标题不能为空").show();
            return false;
        }
    }
  function ckSound() {
          var el = $("#sound"), v = el.val().trim(), len = utf8strlen(v);
          if (v) {
              if (len > 30) {
                  el.next("span").text("不能超过30个字符").show();
                  return false;
              } else {
                  el.next("span").hide();
                  return true;
              }
          } else {
              el.next("span").text("声音不能为空").show();
              return false;
          }
      }
   function ckLargeIcon() {
           var el = $("#largeIcon"), v = el.val().trim(), len = utf8strlen(v);
           if (v) {
               if (len > 30) {
                   el.next("span").text("不能超过30个字符").show();
                   return false;
               } else {
                   el.next("span").hide();
                   return true;
               }
           }else{
               return true;
           }
       }

       function ckIcon() {
                  var el = $("#icon1"), v = el.val().trim(), len = utf8strlen(v);
                  if (v) {
                      if (len > 30) {
                          el.next("span").text("不能超过30个字符").show();
                          return false;
                      } else {
                          el.next("span").hide();
                          return true;
                      }
                  }
                  return true;
              }
              function ckIcon2() {
                                var el = $("#icon2"), v = el.val().trim(), len = utf8strlen(v);
                                if (v) {
                                    if (len > 30) {
                                        el.next("span").text("不能超过30个字符").show();
                                        return false;
                                    } else {
                                        el.next("span").hide();
                                        return true;
                                    }
                                }
                                return true;
                            }

    $("#pushTitle").on("keyup", ckPushTitle);
    $("#largeIcon").on("keyup",ckLargeIcon);
    $("#icon1").on("keyup",ckIcon);
    $("#icon2").on("keyup",ckIcon2);
    $("#sound").on("keyup",ckSound);


    function ckPushText() {
        checkWord();

        if ($("#pushText").val().trim() != "") {
            $("#pushTextTip").hide();
            return true;
        } else {
            $("#pushTextTip").show();
            return false;
        }

    }


    $("#pushText").on("keyup", ckPushText);



    $(".radioSel").click(function() {
        $(this).closest(".formItem").find("." + $(this).val() + "_show").show().siblings(".tabshow").hide();
        if ($(this).val() == 'unicast') {
            $('.radioSel').each(function() {
                if ($(this).val() == 'sendnow') {
                    $(this).click();
                }
                if ($(this).val() == 'sendin') {
                    $(this).closest('label').hide();
                }
            });
        } else if ($(this).val() != 'sendnow') {
            $('.radioSel').each(function() {
                if ($(this).val() == 'sendin') {
                    $(this).closest('label').show();
                }
            });
        }
        if($(this).val()=='groupcast'){
          if($(".dimension").length == 0){
                  $("#addCondition").before(component+'<span class="tips3" style="display: none;">筛选条件>重复，请修改</span></div>');
                  var dimensionSelect = $(".dimension").last();
                  var myselect = dimensionSelect.siblings("select.multiple");
                  dimensionSelect.siblings("select.single").hide();
                  myselect.chosen();

          }
        }

         if($(this).val()=='customizedcast'){
          var aliasTypeSelect =  $("#alias_type_select");
          aliasTypeSelect.empty();
           $.each(alias_types,function(i,e){
             aliasTypeSelect.append("<option>"+htmlEscape(e)+"</option>");
         });


             aliasTypeSelect.chosen();
             aliasTypeSelect.trigger("liszt:updated");

        }

        if ($(this).val() == 'openUrl') {
            if (!$("#openUrl_val").val()) {
                $("#urlTip").hide();
            }
        }

        if ($(this).val() == 'openActivity') {
            if (!$("#openActivity_val").val()) {
                $("#activityTip").hide();
            }
        }

        if ($(this).val() == 'openCustom') {
            if (!$("#openCustom_val").val()) {
                $("#customTextTip").hide();
            }
        }
    });
    
    $('input[name="sendTo"]').on('click', function(){
        var self = $(this);
        if(self.val() == "customizedcast" || self.val() == 'unicast'){
            $('input[name="sendTime"]').eq(0).click();
            $('input[name="sendTime"]').eq(1).parent().hide();
        } else {
            $('input[name="sendTime"]').eq(1).parent().show();
        }

    });
    $('input[name="gender"]').attr("disabled", true);
    $("#useGender").click(function(){
      if ($(this).attr("checked")){
        $('input[name="gender"]').removeAttr("disabled");
      }else{
        $('input[name="gender"]').attr("disabled", true);
      }
    });

     $('input[name="sound_ios"]').on('click', function(){
            var self = $(this);
            if(self.val() == "define"){
                $('input[name="ios_sound"]').show();
            } else {
               $('input[name="ios_sound"]').hide();
            }

        });

    $("#sendTime").datetimepicker({
         showSecond : true,
         timeFormat : 'hh:mm:ss',
         minDate : new Date(),
         maxDate: '7',
         onSelect:function(ui, event) {
           var date_and_time = ui.split(" ");
           var date_part = date_and_time[0].split("-");
           var time_part = date_and_time[1].split(":");
           var tmp = new Date(date_part[0],date_part[1]-1,date_part[2],time_part[0],time_part[1],time_part[2]);
           var till = new Date(date_part[0],date_part[1]-1,date_part[2],time_part[0],time_part[1],time_part[2]);
           till.setDate(till.getDate()+7);
           $('#expireTime').datetimepicker("option","showSecond",true);
           $('#expireTime').datetimepicker("option","minDate",ui);
           $('#expireTime').datepicker("option","maxDate",till);
           var now = tmp.getTime(), tdl = new Date(now + 3600 * 24 * 1000);
           $("#expireTime").val(tdl.getFullYear() + "-" + ((tdl.getMonth() + 1) > 9 ? (tdl.getMonth() + 1) : ("0" + (tdl.getMonth() + 1))) + "-" + (tdl.getDate() > 9 ? tdl.getDate() : "0" + tdl.getDate()) + " " + (tdl.getHours() > 9 ? tdl.getHours() : ("0" + tdl.getHours())) + ":" + (tdl.getMinutes() > 9 ? tdl.getMinutes() : "0" + tdl.getMinutes()) + ":" + (tdl.getSeconds() > 9 ? tdl.getSeconds() : "0" + tdl.getSeconds()));
        }
    });
     $(".send").change(function(){
       var sendType = $("input[name='sendTime']:checked").val();
       if(sendType == "sendnow"){
        $('#expireTime').datepicker("option","minDate",new Date());
        $('#expireTime').datepicker("option","maxDate","7");
       }
     });
     $("#expireTime").datetimepicker(
         {
           showSecond : true,
           timeFormat : 'hh:mm:ss',
           minDate : new Date(),
           maxDate: '7'
             }
        );

    $(".datetime,#deviceId").change(function() {
        if ($(this).val().trim() == "") {
            $(this).next().show();
        } else {
            $(this).next().hide();
        }
    });

     $("#showfilter").click(function(){
           var params = {
                      "start_time" : '',
                      "not_launch_in" : '',
                      "contains":'',
                      "notContains":'',
                      "launch_in":''
                      // "push_type" : $("#push_type").val() - 0
                  }
               getFilterParams(params,false);
               var error = 0;
              if(params.contains=="" && params.notContains=="" && params.not_launch_in == "" && params.launch_in== ""){
                  error++;
                  $(".groupcast_show").children(".tips3").show();
                  }else{
                   $(".groupcast_show").children(".tips3").hide();
                 }
               if(!error){
	                $.post("contentsMobileController.do?generateFilter", params, function(resultJson){
	                	var d=JSON.parse(resultJson).result;
		                $("#preshow").dialog({
		                           title:"过滤Json串生成成功",
		                           width:700/*
											 * , close: function(){ location.href =
											 * location.href; }
											 */
		                         });
		                $("#filter").val(JSON.stringify(d, null, 4));
		                 $("#preshow").html("<div>filter:"+JSON.stringify(d)+"</div><br><div class='header'><input type='button' class='filter_up' id='displayFilter'></div><div id='detail' style='display:none'><pre>" + JSON.stringify(d, null, 4) + "</pre></div>");
	               });
               }	
        });



        $("#tosave").click(function(){
           myparams = {
                 "start_time" : '',
                 "not_launch_in" : '',
                 "contains":'',
                 "notContains":'',
                 "launch_in":''
                 }
           var error = 0;
           getFilterParams(myparams,true);
           if(myparams.contains=="" && myparams.notContains=="" && myparams.not_launch_in == "" && myparams.launch_in== ""){
              error++;
              $(".groupcast_show").children(".tips3").show();
             }else{
              $(".groupcast_show").children(".tips3").hide();
             }
           if(!error){
           var currentIndex=$('.templateId:checked').val();
            $("#templateName").val(segments[currentIndex]);
             $("#saveSegmentDialog").dialog({
               title:　"保存筛选条件",
               modal: true,
               width: 250 ,
               dialogClass: "detail"/*
									 * , close: function(){ location.href =
									 * location.href; }
									 */
               });
             }
        });
   $(document).on('click','.filter_up',function(){
      $("#detail").show();
      this.className ='filter_down';
      this.title="收起";
   });
   $(document).on('click','.filter_down',function(){
    $("#detail").hide();
    this.className ='filter_up';
    this.title="展开";
   });
   $("#saveTemplate").click(function(){
      var name = $("#templateName").val();
      var error = 0;
      var currentIndex=$('.templateId:checked').val();
      if(name){
         name = name.trim();
         if(!ckTemplateName()){
          return;
         }
         if(currentIndex){
            if(name.trim()==segments[currentIndex]){
                myparams.segment_id=currentIndex;
                  $("#destnewtip").hide();
                 }else{
                  if(containsSegment(name)){
                     $("#destnewtip").text("模板名称已存在").show();
                     error++;
                   }else{
                    $("#destnewtip").hide();
                    myparams.name = name;
                  }
                  }
              }else{

                   if(containsSegment(name)){
                     $("#destnewtip").text("模板名称已存在").show();
                     error++;
                   }else{
                    $("#destnewtip").hide();
                   myparams.name = name;
                   }
              }
              }else{
               $("#destnewtip").text("模版名称不能为空").show();
               error++;
              }
     if(!error){
      $.post('/' + appkey + '/saveFilter', myparams, function(d){
            if(d.success=="1"){
             if(d.id){
                segments[d.id]=name;
                segmentContent[d.id]=myparams;
                var str = "";
               if(segments){
                 var num = 1;
                 for(var key in segments){
                 str += "<div class='iconDelDiv'><input type='checkbox' name='templates' value='"+key+"' class='templateId' id='"+key+"'><label  style='display:inline' for='"+key+"'>"+segments[key]+"</label><span class='deleteSegment'></span></div>";
                num++;
                }
               $("#templateList").html(str);
               $("#"+d.id+"").attr("checked",true);
             }
           }else if(currentIndex){
            segmentContent[currentIndex]=myparams;
           }
         $("#saveSegmentDialog").dialog("close");
         }else{
           $("#destnewtip").text(d.error).show();
         }
        });
        }

     });
    function containsSegment(name){
        for(key in segments){
         if(segments[key]==name){
          return true;
         }
        }
        return false;
      }
   function getFilterParams(params,saveFlag){
         var number = 0;
         var accumulate = 1;
         var prime=[2,3,5,7,11,17,19];
         $(".dimension").each(function(){
           var index = $(this).val();
           if(accumulate %prime[index]==0){
               $(this).siblings("span.tips3").text("筛选条件重复，请修改").show();
               error++;
            }else{
               accumulate = accumulate*prime[index];
            }
            var t = dimensions[index];
            var op = $(this).siblings("select.op");
            if(!op.is(":visible")){
                 op = $(this).siblings("select.opversion");
            }
            var optxt = $(this).siblings("input.launch");
            var mydata = "",contains = "",myselect;

            if(index >= 0 && index <=5){
               var opcode = op.val();
               var arr = [];
               // data = myselect.chosen().val();
               if(t == "tag"){
                   params.relation = $(this).siblings("select.tagRelation").val();
                 }
                switch(opcode){
                    case "contains":
                       myselect=$(this).siblings("select.multiple");
                       mydata = myselect.chosen().val();
                       if( mydata && mydata.length >0){
                          var str = '';
                          if(t == "device_model"){
                               str = t+":"+mydata.join(separator)+";";
                           }else{
                               str = t+":"+mydata.join(",")+";";
                           }
                          params.contains += str;
                            number ++;
                        }
                      break;
                     case "notcontains":
                        myselect=$(this).siblings("select.multiple");
                        mydata = myselect.chosen().val();
                        if( mydata && mydata.length > 0){
                             var str = '';
                             if (t == "device_model"){
                                str = t +":" + mydata.join(separator)+";";
                               }else{
                                str = t +":"+mydata.join(",")+";";
                                                    }
                                params.notContains += str;
                                number ++;
                             }

                             break;
                     case "ge":
                         myselect=$(this).siblings("select.single");
                         mydata = myselect.chosen().val();
                         if( mydata && mydata.length > 0){
                            if(t=="app_version"){
                               if(saveFlag){
                                  var str =t+":"+"ge_|"+mydata+";";
                                  params.contains += str;
                               }else{
                                  var str = t +":"+ getVersions(mydata,1).join(",")+";";
                                  params.contains += str;
                               }
                             }
                         }
                         number++;
                         break;
                     case "le":
                         myselect=$(this).siblings("select.single");
                         mydata = myselect.chosen().val();
                         if( mydata && mydata.length > 0){
                            if(t=="app_version"){
                               if(saveFlag){
                                 var str =t+":"+"le_|"+mydata+";";
                                 params.contains += str;
                               }else{
                                 var str = t +":"+ getVersions(mydata,2).join(",")+";";
                                 params.contains += str;
                               }

                             }
                         }
                           number++;
                           break;
                    }
               }else if(index == 6){
                   var activeSelect = $(this).siblings("select.activeselect").val();
                   var not_launch_in = $(optxt).val().trim();
                   var not_launch_in_int = not_launch_in - 0;
                   if(not_launch_in_int <= 0){
                     $(this).siblings("span.tips3").text("天数必须大于零").show();
                        error++;
                     }else if(not_launch_in_int > 30){
                       $(this).siblings("span.tips3").text("天数不能超过30").show();
                           error++;
               }else{
                  $(this).siblings("span.tips3").hide();
                    if(activeSelect == "0"){
                      params.not_launch_in = not_launch_in;
                    }else{
                      params.launch_in = not_launch_in;
                    }
                      number++;
              }
           }
         });
         if($("#useGender").attr('checked')){
                var  gender =[];
                $('.gendercheckbox:checked').each(function(){
                   gender.push($(this).val());
                 });
                if(gender.length!=0){
                   params.contains += "gender:"+gender.join(",")+";";
                  }
               }
            $("input[name='sendTime']").each(function() {
                        if ($(this).attr("checked")) {
                            switch($(this).val()) {
                                case "sendnow":
                                    params.start_time = "";
                                    break;
                                case "sendin":
                                    var startTime = $("#sendTime").val();
                                    if(startTime){
                                      params.start_time = startTime;
                                      }else{
                                       params.start_time = "";
                                      }
                                    break;
                            }
                        }
                    });
             return number;
       }
   function readURL(input) {

       if (input.files && input.files[0]) {
           var reader = new FileReader();

           reader.onload = function (e) {
               $('#blah').attr('src', e.target.result).attr('width', 128).attr('height', 128).show();
           }

           reader.readAsDataURL(input.files[0]);
       }
   }

   $("#uploadfile").change(function(){
       var filename=$(this).val();
       if(filename!=""&&(filename.toLowerCase().indexOf(".jpg")>0 || filename.toLowerCase().indexOf(".png")>0 || filename.toLowerCase().indexOf(".jpeg")>0)){
          $("#isValid").hide();
         readURL(this);
       }else{
        $("#isValid").show();
        $(this).val("");
       }

   });
    function getMsgParams(){
        var params = {
            "appkey" : appkey,
            // "title":$("#pushTitle").val(),
            // "text":$("#pushText").val(),
            "description" : $("#pushDes").val().trim(),
            "type" : '',
            "device_tokens" : '',
            "start_time" : '',
            "expire_time" : '',
            "not_launch_in" : '',
            "contains":'',
            "notcontains":'',
            "launch_in":'',
            "fromtest":''
            // "push_type" : $("#push_type").val() - 0
        }, error = $("#pushDes").val().trim() ? 0 : 1;
        if(fromtest){
        	params.fromtest = true;
        }
        ckPushDes();

        
        var number = 0;
        $("input[name='sendTo']").each(function(i, e) {
            if ($(this).attr("checked")) {
                switch($(this).val()) {
                    case "broadcast":
                        break;
                    case "groupcast":

                        var accumulate = 1;
                        var prime=[2,3,5,7,11,17,19];
                        $(".dimension").each(function(){
                              var index = $(this).val();
                              if(accumulate %prime[index]==0){
                                    $(this).siblings("span.tips3").text("筛选条件重复，请修改").show();
                                          error++;
                               }else{
                                     accumulate = accumulate*prime[index];
                                     }
                                var t = dimensions[index];
                                var op = $(this).siblings("select.op");
                                if(!op.is(":visible")){
                                  op = $(this).siblings("select.opversion");
                                }
                                var optxt = $(this).siblings("input.launch");
                                var myselect = $(this).siblings("select.multiple");
                                var data = "";
                                var contains = "";

                                if(index >= 0 && index <=5){
                                  var opcode = op.val();
                                  var arr = [];
                                  data = myselect.chosen().val();

                                  if(t == "tag"){
                                   params.relation = $(this).siblings("select.tagRelation").val();
                                  }
                                  switch(opcode){
                                   case "contains":
                                       if( data && data.length >0){
                                         var str = '';
                                         if(t == "device_model"){
                                          str = t+":"+data.join(separator)+";";
                                         }else{
                                          str = t+":"+data.join(",")+";";
                                          }
                                         params.contains += str;
                                         number ++;
                                         }
                                       break;
                                   case "notcontains":
                                         if( data && data.length > 0){
                                          var str = '';
                                          if (t == "device_model"){
                                            str = t +":" + data.join(separator)+";";
                                          }else{
                                            str = t +":"+data.join(",")+";";
                                            }
                                          params.notcontains += str;
                                          number ++;
                                          }

                                         break;
                                   case "ge":
                                        data = $(this).siblings("select.single").chosen().val();
                                        if(data && data.length>0){
                                         var str = t +":"+ getVersions(data,1).join(",")+";";
                                         params.contains += str;
                                        }
                                        params.operation = 'ge';
                                        break;
                                   case "le":
                                        data = $(this).siblings("select.single").chosen().val();
                                        if(data && data.length>0){
                                           var str = t +":"+ getVersions(data,2).join(",")+";";
                                           params.contains += str;
                                          }
                                          params.operation = 'le';
                                        break;
                                  }
                                }else if(index == 6){
                                   var activeSelect = $(this).siblings("select.activeselect").val();
                                   var not_launch_in = $(optxt).val().trim();
                                   var not_launch_in_int = not_launch_in - 0;
                                   if(not_launch_in_int <= 0){
                                      $(this).siblings("span.tips3").text("天数必须大于零").show();
                                       error++;
                                   }else if(not_launch_in_int > 30){
                                      $(this).siblings("span.tips3").text("天数不能超过30").show();
                                            error++;
                                   }else{
                                   $(this).siblings("span.tips3").hide();
                                   if(activeSelect == "0"){

                                     params.not_launch_in = not_launch_in;
                                   }
                                   else{
                                     params.launch_in = not_launch_in;
                                   }
                                    number++;
                                   }
                                }


                             });
                        if($("#useGender").attr('checked')){
                          var  gender =[];
                          $('.gendercheckbox:checked').each(function(){
                           gender.push($(this).val());
                          });
                          if(gender.length!=0){
                           params.contains += "gender:"+gender.join(",")+";";
                          }
                        }
                        break;
                    case "unicast":
                        var devicetokens=$("#deviceId").val().trim().replace(/\s/g, "");
                        devicetokens = devicetokens.replace(/，/g,",");
                        params.device_tokens = devicetokens;
                        break;

                    case "customizedcast":
                        params.alias = $.trim($('#customizedcast_val').val());
                        params.aliasType = $.trim($("#alias_type_select").val())
                }
                params.type = $(this).val();
            }

        });



        if ( params.type != "broadcast") {
            if (params.type == 'unicast' && params.device_tokens == "") {
                error++;
                $("#deviceId").next().show();
            }else if(params.type == 'unicast'){
               var flag = true;
               var ds = params.device_tokens.split(",");
               for(var k=0;k<ds.length;k++){
               if(($("[name='pushToTarget']:checked").val()=="android" && ds[k].length !=44)||($("[name='pushToTarget']:checked").val()=="ios" && ds[k].length !=64)){
                 $("#deviceId").next().text("Device Token不合法").show();
                 flag = false;
                 error++;
                  break;
                 }
                }
              if(flag){
                $("#deviceId").next().hide();
              }

            }
            if (params.type == 'groupcast') {
                if(number >5){
                    error++;
                    $(".groupcast_show").children(".tips3").text("最多只能选择5个筛选条件").show();
                }else if(params.contains=="" && params.notcontains=="" && params.not_launch_in == "" && params.launch_in== ""){
                    error++;
                    $(".groupcast_show").children(".tips3").show();
                } else {
                    $(".groupcast_show").children(".tips3").hide();
                }
            }
        }

        if(params.type == 'customizedcast'){
            if(!params.alias){
             $('#customizedcastTip').show();
              error++;
            }else if(params.alias.indexOf(",")>=0){
              error ++;
              $("#customizedcastTip").text("只限输入一个用户ID").show();
            }
            if(!params.aliasType){
              error++;
              $("#aliasTypeTip").show();
            }



        }

        $("input[name='sendTime']").each(function() {
            if ($(this).attr("checked")) {
                switch($(this).val()) {
                    case "sendnow":
                        var nowD = new Date();
                        params.start_time = nowD.getFullYear() + "-" + (((nowD.getMonth() + 1) < 10) ? ("0" + (nowD.getMonth() + 1)) : (nowD.getMonth() + 1)) + "-" + ((nowD.getDate() < 10) ? ("0" + nowD.getDate()) : nowD.getDate()) + " " + nowD.getHours() + ":" + nowD.getMinutes() + ":" + nowD.getSeconds();
                        params.start_time = "";
                        break;
                    case "sendin":
                        if ($('#sendTime').val() == "") {
                           $("#sendTime").next("span").html("定时发送时间不能为空").show();
                            error++;
                        } else {
                            var startTime = $("#sendTime").val();
                            if(checkDateTime(startTime)){
                            var distance = getDistance(startTime);
                            if(getDistance(startTime)>diff){
                               error++;
                               $("#sendTime").next("span").html("定时发送时间不能超过当前时间7天").show();
                            }else if(distance <=0){
                               $("#sendTime").next("span").html("定时发送时间必须大于当前时间").show();
                               error++;
                            }else{
                            params.start_time = startTime;
                            $("#sendTime").next("span").hide();
                            }
                           }else{
                             error++;
                             $("#sendTime").next("span").html("时间格式不正确").show();
                           }
                        }
                        break;
                }
            }
        });

         if ($('#expireTime').val() == "") {
                 params.expire_time = '';
              } else {
                 var endTime = $('#expireTime').val();
                 if(checkDateTime(endTime)){
                 var distance = getDistance(endTime,params.start_time);
                       if(distance>diff){
                         error++;
                         $('#expireTime').next("span").html("过期时间不能超过发送时间7天").show();
                       }else if(distance <= 0){
                         error++;
                         $('#expireTime').next("span").html("过期时间必须大于发送时间").show();
                       }else{
                           $("#expireTime").next("span").hide();
                           params.expire_time = endTime;
                       }
                     }else{
                        error++;
                        $('#expireTime').next("span").html("时间格式不正确").show();
                     }
                  }


        // android
        if(APPPLATFORM == "android"){
            params.push_type = $("#push_type").val() - 0;

            if (params.push_type) {
                var res = ckTextInputLength($("#custom_content"), 800, 1);
                if (res==1) {
                    params.custom_content = $("#custom_content").val().trim();
                } else {
                    if(res == -1){
                      $("#customContentTip").text("最多只可输入800个字符").show();
                    }else{
                      $("#customContentTip").text("内容不能为空").show();
                    }

                    error++;
                }
                if( $('#extrakv').attr('checked')){

                    var extra = {};
                    $('.customkv').each(function(){
                    var k = $.trim($('input[name="key"]', this).val().replace(/\s/g,"")), v = $.trim($('input[name="value"]', this).val());

                    if(!k || !v){
                           error++;
                           $('.tips3', this).text('请完整填写自定义参数').show();
                     } else {
                        if(extra[''+k]){
                           error++;
                           $('.tips3', this).text('该Key已经存在').show();
                          }
                         }

                         extra[''+k] = v;
                       })

                      params.extra = JSON.stringify(extra);
                      var customlength = utf8strlen(params.custom_content);
                       customlength += utf8strlen(params.extra);
                      if(customlength > 800){
                             error++;
                             $('#customkverror').show();
                        } else {
                            $('#customkverror').hide();
                            }
              }

            } else {
                if (!checkWord()) {
                    error++;
                }

                params.title = $("#pushTitle").val().trim();
                params.text = $("#pushText").val().trim();
                // params.description = $("#pushDes").val();
                if (!ckPushTitle()) {
                    error++;
                }

                if (!ckPushText()) {
                    error++;
                }

                if (!ckPushDes()) {
                    error++;
                }

                if ($("input[name='vibraPrompt']").attr('checked')) {
                    params.play_vibrate = "true";
                } else {
                    params.play_vibrate = "false";
                }
                if ($("input[name='soundPrompt']").attr('checked')) {
                    params.play_sound = "true";
                } else {
                    params.play_sound = "false";
                }
                if ($("input[name='lightPrompt']").attr('checked')) {
                    params.play_lights = "true";
                } else {
                    params.play_lights = "false";
                }

                var sendafterlength = 0;
                $("input[name='sendAfter']").each(function() {
                    if ($(this).attr("checked")) {
                        params.after_open = "go_" + $(this).val().replace("open", "").toLowerCase();
                        switch($(this).val()) {
                            case 'openUrl':
                                if ($("#openUrl_val").val().trim().match(/^(http[s]?):\/\//i)) {
                                    params.url = $("#openUrl_val").val().replace(/\s/g,"");
                                    sendafterlength += utf8strlen(params.url);
                                } else {
                                    $("#urlTip").show();
                                    error++;
                                }
                                break;
                            case 'openActivity':
                                var ac = $("#openActivity_val").val().replace(/\s/g,"");
                                if (ac) {
                                    params.activity = ac;
                                    sendafterlength += utf8strlen(params.activity);
                                } else {
                                    $('#activityTip').show();
                                    error++;
                                }
                                break;

                            case 'openCustom':
                                var res = ckTextInputLength($("#openCustom_val"), 50 * 3, 1);
                                if (res == 1) {
                                    params.custom = $("#openCustom_val").val().trim();
                                    sendafterlength += utf8strlen(params.custom);

                                } else {
                                    if(res == -1){
                                     $("#customTextTip").text("最多只能输入150字符").show();
                                    }else{
                                     $("#customTextTip").text("内容不能为空").show();
                                    }

                                    error++;
                                }
                                break;
                            case 'openApp':
                                break;

                        }
                    }
                });

                // add extra key value
                if($('#extrakeyvalue').attr('checked')){

                    var extra = {};
                    $('.customrow').each(function(){
                        var k = $.trim($('input[name="key"]', this).val().replace(/\s/g,"")), v = $.trim($('input[name="value"]', this).val());

                        if(!k || !v){
                            error++;
                            $('.tips3', this).text('请完整填写自定义参数').show();
                        } else {
                            if(extra[''+k]){
                                error++;
                                $('.tips3', this).text('该Key已经存在').show();
                            }
                        }

                        extra[''+k] = v;
                    })

                    params.extra = JSON.stringify(extra);

                    sendafterlength += utf8strlen(params.extra);
                }

                if(sendafterlength > 300){
                    error++;
                    $('#customkeyvalueerror').show();        
                } else {
                    $('#customkeyvalueerror').hide();
                }

                // 自定义通知栏图标

                if($('#customiconckb').attr('checked')){
                    params.icon_type = $('input[name="icon_type"]:checked').val();

                    if(params.icon_type == 1){
                        var largeIcon = $('#customIconShow input[name="largeIcon"]').val().trim(),
                        icon = $.trim($('#customIconShow input[name="icon"]').val());

                        if(largeIcon || icon){
                            params.largeIcon = largeIcon;
                            params.icon = icon;
                            $('#custom_icon_error').hide()
                        } else{
                            error++;
                            $('#custom_icon_error').show();
                        }
                         if (!ckLargeIcon()) {
                               error++;
                                        }
                          if(!ckIcon()){
                             error++;
                          }

                    } else {
                        var largeIcon = $('#uploadiconarea input[name="largeIcon"]').val().trim(),
                        icon = $.trim($('#uploadiconarea input[name="icon"]').val());
                        if(largeIcon || icon){
                            params.img = largeIcon;
                            params.icon = icon;
                            $('#custom_icon_error').hide()
                        } else{
                            error++;
                            $('#custom_icon_error').show();
                        }
                        if(!ckIcon2()){
                          error++;
                        }
                    }
                }

                if($('#customsoundckb').attr('checked')){

                    var csound = $.trim($('#customsoundrow input[name="sound"]').val());
                    if(csound){
                        $('#custom_sound_error').hide();
                        params.sound = csound;
                    } else {
                        error++;
                        $('#custom_sound_error').show();
                    }
                    if(!ckSound()){
                         error++;
                          }

                }
                var styleSelf=$('#alertStyle').val();
                if(styleSelf){
                    params.buildid = styleSelf;
                }
            }
        } else { // iOS

            if (!ckPushText()) {
                error++;
            }

            params.text = $("#pushText").val().trim();
            $("input[name='sound_ios']").each(function(i, e) {
                        if ($(this).attr("checked")) {
                            switch($(this).val()) {
                                case "default":
                                    params.ios_sound = "default";
                                    break;
                                case "":
                                     params.ios_sound = "";
                                     break;
                                case "define":
                                     params.ios_sound = $("#ios_sound").val().trim();
                                     break;
                                    }
                       }
                     });

            params.ios_badge = $.trim($("#ios_badge").val());
            params.category=$.trim($("#category").val());
            var custom_keyvalue = {};
            
            $(".customkeyvalue").each(function(){
                if($('input[name="ios_custom_key"]', this).val().trim()){
                    custom_keyvalue[$('input[name="ios_custom_key"]', this).val().replace(/\s/g,"")] = $('input[name="ios_custom_value"]', this).val();
                }
            });
            params.ios_custom = JSON.stringify(custom_keyvalue);
        }

        if($('#ristrictSpeech').attr('checked')){
            var max_send_num = $('#max_send_num').val() - 0;
            if(max_send_num >= 500 && max_send_num <= 10000){
                params.max_send_num = max_send_num;
                $('#max_send_num_error').hide();
            } else {
                error++;
                $('#max_send_num_error').show();    
            }
        }

        if (error < 1) {
            // show details before post
            return params;
        } 

        return null;
    }
    function getVersions(version,op){
     var versions = dimensionData[0];
     var strs = version.split(".");
     var cstrs;
     var rightVersions=[];
     rightVersions.push(version);
     var currentIsDigital = isDecimal(version);
     if(op == 1){
       for(var i=0;i<versions.length;i++){
         if(versions[i] ==version ){continue;}
         if(currentIsDigital && isDecimal(versions[i])){
           cstrs = versions[i].split(".");
           if(isGT(cstrs,strs)){
           rightVersions.push(versions[i]);
          }
         }else{
           if(versions[i]>=version){
             rightVersions.push(versions[i]);
           }
         }
       }
     }else if(op == 2){
       for(var i=0;i<versions.length;i++){
       if(versions[i] ==version ){continue;}
       if(currentIsDigital && isDecimal(versions[i])){
       cstrs = versions[i].split(".");
       if(isGT(strs,cstrs)){
         rightVersions.push(versions[i]);
        }
        }else{
         if(version >=versions[i]){
          rightVersions.push(versions[i]);
         }
        }
      }
     }
     return rightVersions;
    }

    function isGT(versionA,versionB){
      var lenga = versionA.length,lengb = versionB.length;
      var  maxIndex = (lenga > lengb?lengb:lenga);
      var a=0,b=0;
      try{
      for(var i = 0;i< maxIndex;i++){
        a = parseInt(versionA[i],10);
        b = parseInt(versionB[i],10);
        if(isNaN(a)){ a = 0;}
        if(isNaN(b)) {b=0;}
        if(a>b){
          return true;
        }else if(a<b){
         return false;
        }
      }
      }catch(err){
       return versionA>versionB;
      }
      if(lenga==maxIndex){
         return false;
       }else{
         return true;
       }
    }
    function isDecimal(str){
      var res=str.replace(/\./g,'').replace(/\d+/g,'');
      if(res!=""){
       return false;
      }
      return true;
    }
    function getDistance(expire,startTime){
      expire = expire.replace(/\-/g, "/");
      var start = new Date();
      if(startTime){
       startTime = startTime.replace(/\-/g, "/");
       start = new Date(startTime);
      }
      var end = new Date(expire);
       return end.getTime() - start.getTime();
    }

    function after_open(k, data){
       var v = {
        'go_app':'打开应用',
        'go_url':'打开指定网页',
        'go_activity':'打开指定页面',
        'go_custom':'自定义事件'
       };
       var res =  v[k]?v[k]:k;
       if(k != 'go_app'){
        var ao = k.replace("go_","");
        res = res+'&emsp;('+htmlEscape(data[ao])+')';
       }
       return res;
     }

    function showAndroidDetail(params){
        var msg_arr = [];
        // 描述
        msg_arr.push('<div class="pushDetail"><table><tr><td>消息描述</td><td>'+ htmlEscape(params.description)+'</td></tr><tr><td>发送状态</td><td>未发送</td></tr>');

        if(params.push_type != 1){
           // var content = substringWithChinese(params.text,40);
            msg_arr.push('<tr><td>标题</td><td>'+htmlEscape(params.title)+'</td></tr><tr><td>内容</td><td style="word-wrap: break-word; word-break: break-all;; ">'+htmlEscape(params.text)+'</td></tr><tr><td>查看通知后</td><td>'+after_open(params.after_open, params)+'</td></tr>');

            if($('#extrakeyvalue').attr('checked')){
                msg_arr.push('<tr><td>自定义参数</td><td><table><tr><td style="text-align:left;"><b>Key</b></td><td><b>Value</b></td></tr>')
                $('.customrow').each(function(){
                    msg_arr.push('<tr><tr><td style="text-align:left;word-break: break-all;">'+ htmlEscape($('input[name="key"]', this).val()).replace(/\s/g,"") + '</td><td style="word-break: break-all;">'+ htmlEscape($('input[name="value"]', this).val()) +'</td></tr>');
                })
                msg_arr.push('</table></td></tr>');
            }

        } else {
            // var custom_content =
			// substringWithChinese(params.custom_content,60);
            msg_arr.push('<tr><td>自定义消息</td><td style="word-wrap: break-word; word-break: break-all;; ">' + htmlEscape(params.custom_content) + '</td></tr>');
            if($('#extrakv').attr('checked')){
                msg_arr.push('<tr><td>自定义参数</td><td><table><tr><td style="text-align:left;"><b>Key</b></td><td><b>Value</b></td></tr>')
                $('.customkv').each(function(){
                    msg_arr.push('<tr><tr><td style="text-align:left;word-break: break-all;">'+ htmlEscape($('input[name="key"]', this).val()).replace(/\s/g,"") + '</td><td style="word-break: break-all;">'+ htmlEscape($('input[name="value"]', this).val()) +'</td></tr>');
                     })
                  msg_arr.push('</table></td></tr>');
             }
        }
        var pushToAndroid=$("[name='pushToAndroid']");
        var pushToIOS=$("[name='pushToIOS']");
        var tempStr='<tr><td>推送目标</td><td>';
        if(!pushToAndroid[0].checked&&!pushToIOS[0].checked){
        	alert("至少选择一个推送目标");
        	return false;
        }
        if(pushToAndroid[0].checked){
        	tempStr+='Android客户端'
        }
        tempStr+='&nbsp;&nbsp;&nbsp;&nbsp;';
        if(pushToIOS[0].checked){
        	tempStr+='IOS客户端';
        }
        tempStr+='</td></tr>';
    	msg_arr.push(tempStr)  
        // 消息提醒方式
        var play = "";
        if(params.play_sound == "true"){
            play += '<b class="icon-play icon-play-sound"></b><span class="playMethod">响铃</span>';
        }
        if(params.play_vibrate == "true"){
            play += '<b class="icon-play icon-play-vibrate"></b><span class="playMethod">振动</span>';
        }
        if(params.play_lights == "true"){
            play += '<b class="icon-play icon-play-lights"></b><span class="playMethod">呼吸灯</span>';
        }
        
        switch(params.type){
          case 'broadcast':
           var tip = ("所有人");
            break;
          case 'groupcast':
            var groupRes='';
            var res = {};
            // var g = params.device_tokens.split(';');
            if(params.contains || params.not_launch_in || params.launch_in){
            var tip = $("<div class='tip1'><ul class='group-tip'></ul></div>");
            if(params.contains){
             var g = params.contains.split(';');
             for(i in g){
              var gi = g[i].split(':');
              if(gi[1]){
                gi[0] = gi[0].replace("app_","");
                if(gi[0]=="device_model"){
                  if( gi[1].split(separator).length>0){
                          res[gi[0]] = gi[1].split(separator);
                   }
                }else if(gi[0] == "language"){

                  var str=new Array();
                  var index_number = 0;
                  var gires = gi[1].split(",");
                  for(var key in language){
                    var all=language_option[key].split(',');
                    for(var innerKey in all){
                      if(gires.indexOf(all[innerKey]) >= 0){
                        str[index_number] = language[key];
                        index_number = index_number + 1;
                        break;
                      }
                    }
                  }
                  if(index_number > 0){
                      res[gi[0]] = str;
                 }

                }else if(gi[0] == "gender"){
                  res[gi[0]]=gi[1].split(",");
                  for(var k=0;k<res[gi[0]].length;k++){
                   switch(res[gi[0]][k]){
                     case 'm':
                          res[gi[0]][k]="男";
                          break;
                     case 'f':
                          res[gi[0]][k] = "女";
                          break;
                     case 'o':
                          res[gi[0]][k]="其他";
                          break;
                   }
                  }
                }else{
                   if( gi[1].split(",").length>0){
                       res[gi[0]] = gi[1].split(",");
                     }
                  }
               for(var k = 0;k < res[gi[0]].length;k++){
                 if(k != 0){
                   if(gi[0] == "tag" && params.relation == "and"){
                     res[gi[0]][k] = " <font color='red'>与</font> " + res[gi[0]][k];
                   }else{
                     res[gi[0]][k] = " <font color='red'>或</font> "+res[gi[0]][k];
                   }
                }
              }
              }
             }

            $.each(res,function(x,y){
              tip.children("ul").append("<li class='groupList groupList_"+x+"'><span class='group-icon group-"+x+"-icon'></span>"+lang(x)+"<ul></ul></li>");
              for(m in y){
                // tip.find(".groupList_"+x).children("ul").append('<li>
				// '+htmlEscape(y[m])+' </li>');
                tip.find(".groupList_"+x).children("ul").append('<li> '+y[m]+' </li>');
              }
            });
            }

            if(params.not_launch_in || params.launch_in){
               tip.children("ul").append("<li class='groupList groupList_not_launch_in'><span class='group-icon group-not_launch_in-icon'></span>"+lang("active")+"<ul></ul></li>");
               if(params.not_launch_in){
                tip.find(".groupList_not_launch_in").children("ul").append('<li>'+params.not_launch_in+'天内不活跃用户 </li>');
               }else if(params.launch_in){
                tip.find(".groupList_not_launch_in").children("ul").append('<li>'+params.launch_in+'天内活跃用户 </li>');
               }
               }
             tip = tip.html();
             groupRes += '<tr><td>包含</td><td>'+tip+'</td></tr>';

            }
            res = {};

            if(params.notcontains){
             var tip2 = $("<div class='tip'><ul class='group-tip'></ul></div>");
             g = params.notcontains.split(';');
             for(i in g){
                var gi = g[i].split(':');
                if(gi[1]){
                    gi[0] = gi[0].replace("app_","");
                    if(gi[0]=="device_model"){
                      res[gi[0]] = gi[1].split(separator);
                    }else if(gi[0] == "language"){
                       var str=new Array();
                       var index_number = 0;
                       var gires = gi[1].split(",");
                       for(var key in language){
                          var all=language_option[key].split(',');
                          for(var innerKey in all){
                            if(gires.indexOf(all[innerKey]) >= 0){
                              str[index_number] = language[key];
                              index_number = index_number + 1;
                              break;
                             }
                         }
                      }
                      if(index_number > 0){
                       res[gi[0]] = str;
                      }

                    }else if( gi[1].split(",").length>0){
                      res[gi[0]] = gi[1].split(",");
                     }
                     for(var k = 0;k < res[gi[0]].length;k++){
                        if(k != 0){
                          if(gi[0] == "tag" && params.relation == "and"){
                            res[gi[0]][k] = " <font color='red'>与</font> " + res[gi[0]][k];
                          }else{
                            res[gi[0]][k] = " <font color='red'>或</font> "+res[gi[0]][k];
                            }
                         }
                       }
                    }
                 }


             $.each(res,function(x,y){
              tip2.children("ul").append("<li class='groupList groupList_"+x+"'><span class='group-icon group-"+x+"-icon'></span>"+lang(x)+"<ul></ul></li>");
             for(m in y){
                // tip2.find(".groupList_"+x).children("ul").append('<li>
				// '+htmlEscape(y[m]+" ")+' </li>');
                tip2.find(".groupList_"+x).children("ul").append('<li> '+y[m]+' </li>');
                  }
               });

               tip2 = tip2.html();
               groupRes += '<tr><td>不包含</td><td>'+tip2+'</td></tr>';
               }
            break;
          case 'unicast':
            var uni = params.device_tokens.split(',');
            var tip = "";
            for(x in uni){
              tip += "<li>"+htmlEscape(uni[x])+"</li>";
            }
            tip = "<ul>"+tip+"</ul>";
            break;
          case 'customizedcast':
            // var tip = htmlEscape(params.aliasType+" -- "+params.alias);
            var tip = "<li>alias_type: "+htmlEscape(params.aliasType)+"</li>";
            tip += "<li>alias: "+htmlEscape(params.alias)+"</li>";
            tip = "<ul>"+tip+"</ul>";
            break;                      
        }
        if(groupRes){
           msg_arr.push('<tr><td style="height:30px;">发送对象</td></tr>'+groupRes+'\
               <tr><td>发送时间</td><td>'+(!params.start_time?"立即发送":("定时发送 "+params.start_time))+'</td></tr>\
               <tr><td>过期时间</td><td>'+params.expire_time +'</td></tr>');
        }else{
          msg_arr.push('<tr><td>发送对象</td><td>'+tip+'</td></tr>\
               <tr><td>发送时间</td><td>'+(!params.start_time?"立即发送":("定时发送 "+params.start_time))+'</td></tr>\
               <tr><td>过期时间</td><td>'+params.expire_time +'</td></tr>');
        }

        if(params.push_type != 1){    
          msg_arr.push('<tr><td>消息提醒方式</td><td>'+play+'</td></tr>');
        }

        if($('#ristrictSpeech').attr('checked')){
            msg_arr.push('<tr><td>发送速度</td><td>每秒'+ params.max_send_num +'条信息</td></tr>')
        }

        if($('#customiconckb').attr('checked')){
            if(params.icon_type == 1){
                if(params.largeIcon){
                    msg_arr.push('<tr><td>自定义大图标</td><td>'+ htmlEscape(params.largeIcon) +'</td></tr>')
                }
            } else {
                if(params.img){
                    // msg_arr.push('<tr><td>自定义大图标</td><td><img src="'+
					// params.img +'" width="128" height="128"/></td></tr>')
                    msg_arr.push('<tr><td>自定义大图标</td><td>'+ htmlEscape(params.img) +'</td></tr>')
                }
            }
            if(params.icon){
                msg_arr.push('<tr><td>自定义小图标</td><td>'+ htmlEscape(params.icon) +'</td></tr>')
            }
        }

        if($('#customsoundckb').attr('checked')){
            msg_arr.push('<tr><td>自定义声音文件</td><td>'+ htmlEscape(params.sound) +'</td></tr>')
        }

        if($('#alertStyle').val()){
            msg_arr.push('<tr><td>通知栏样式编号</td><td>'+ params.buildid +'</td></tr>')    
        }
        if(params.fromtest){
          msg_arr.push('<tr><td></td><td class="testtip">友情提示：本消息将以正式消息的模式发送给线上用户。</td></tr>');
        }
        $(window).scroll(function(){
        	var top=$(window).scrollTop();
        	$(".detail").animate({"top":parseInt(top+20)+"px"},100);
        });
        $(msg_arr.join('')).dialog({
            title:'消息详情',
            dialogClass: "detail", 
            width:820,
            buttons:{
                "确认": function(){
                    var btn = $($(".ui-dialog-buttonset button").get(0)).attr("disabled", "disabled");
                    $(".mod-body").loadStart();
                    var url ='contentsMobileController.do?sendPush&cmeId='+$("#cmeId").val()+'&cmeUrl='+$("#cmeUrl").val()+'&pushToAndroid='+pushToAndroid[0].checked+'&pushToIOS='+pushToIOS[0].checked,self = $( this );// '/msg/' + appkey + '/addPush';
                   if($("[name='sendTo']:checked").val()=="groupcast"){
                	   if($("#filter").val()==""){
                		   $(".ui-icon-closethick").trigger("click");
                		   $(".loading").css("display","none");
                		   alert("请先生成维度过滤Json串!");
                		   return ;
                	   }else{
                		   url+="&filter="+$("#filter").val();
                	   }
                   }
                    if(IS_TEST){
                        params.test_mode = 1;
                    }
                    $.post(url, params, function(resultCode) {
                        $(".mod-body").loadEnd();
                        var d=eval(resultCode);
                        if (d == 1) {
                            self.dialog( "close" );
                            alert("添加Push成功！");
                            setTimeout(function() {
                            	window.location.reload();
//                                if(!IS_TEST){
//                                    location.href = '/' + appkey + '/dashboard';
//                                } else {
//                                    location.href = '/' + appkey + '/testMsg';
//                                }
                            }, 1000);
                        } else {
                            alert("添加Push失败！请重试");
                        }
                        btn.removeAttr("disabled");
                    });
                },
                "取消": function(){
                    $( this ).dialog( "close" );
                }
            }
        });

    }

    function showIOSDetail(params){

        var msg_arr = [];
        // 描述
        msg_arr.push('<div class="pushDetail"><table><tr><td>消息描述</td><td>'+ htmlEscape(params.description)+'</td></tr><tr><td>发送状态</td><td>未发送</td></tr>');

        // 内容
       // var msg_text = substringWithChinese(params.text,60);
        msg_arr.push('<tr><td>内容</td><td style="word-wrap: break-word; word-break: break-all; ">'+ htmlEscape(params.text)+'</td></tr>');

        // 音频文件

        // 角标
        if(params.ios_sound){
            msg_arr.push('<tr><td>音频文件(Sound):</td><td>' + htmlEscape(params.ios_sound) + '</td></tr>');
        }
        if(params.ios_badge){
            msg_arr.push('<tr><td>角标(Badge):</td><td>' + htmlEscape(params.ios_badge) + '</td></tr>');
        }
        if(params.category){
           msg_arr.push('<tr><td>CategoryID:</td><td>'+htmlEscape(params.category)+'</td></tr>');
        }
        if(params.ios_custom && params.ios_custom.length > 2){

            // '自定义字段':
            msg_arr.push('<tr><td>自定义字段:</td><td>&nbsp;</td></tr>');
            // msg_arr.push('<tr><td></td><td><table><tr><th>Key</th><th>Value</th></tr>');
            msg_arr.push('<tr><td></td><td><table>');
            $(".customkeyvalue").each(function(){
                if($('input[name="ios_custom_key"]', this).val()){
                    msg_arr.push('<tr><td style="text-align:left;padding:0px;height:40px;">Key: ' + htmlEscape($('input[name="ios_custom_key"]', this).val().replace(/\s/g, "")) +'</td><td> value: ' + htmlEscape($('input[name="ios_custom_value"]', this).val()) + '</td></tr>');
                    // msg_arr.push('<tr><td>' +
					// $('input[name="ios_custom_key"]', this).val()
					// +'</td><td>' + $('input[name="ios_custom_value"]',
					// this).val() + '</td></tr>');

                }
            });
            msg_arr.push('</table></td></tr>')
        }

        switch(params.type){
          case 'broadcast':
           var tip = ("所有人");
            break;
          case 'groupcast':
            var groupRes='';
            var res = {};
            if(params.contains || params.not_launch_in || params.launch_in){
             var tip = $("<div class='tip1'><ul class='group-tip'></ul></div>");
            if(params.contains){
            var g = params.contains.split(';');
            for(i in g){
              var gi = g[i].split(':');
              if(gi[1]){
               gi[0] = gi[0].replace("app_","");
               if(gi[0] == "device_model"){
                 if( gi[1].split(separator).length>0){
                      res[gi[0]] = gi[1].split(separator);
                    }
               }else if(gi[0] == "language"){
                 var str=new Array();
                 var index_number = 0;
                 var gires = gi[1].split(",");
                 for(var key in language){
                    var all=language_option[key].split(',');
                    for(var innerKey in all){
                      if(gires.indexOf(all[innerKey]) >= 0){
                         str[index_number] = language[key];
                         index_number = index_number + 1;
                         break;
                        }
                       }
                      }
                  if(index_number > 0){
                     res[gi[0]] = str;
                  }
               }else if(gi[0] == "gender"){
                res[gi[0]]=gi[1].split(",");
                for(var k=0;k<res[gi[0]].length;k++){
                   switch(res[gi[0]][k]){
                     case 'm':
                       res[gi[0]][k]="男";
                       break;
                     case 'f':
                       res[gi[0]][k] = "女";
                       break;
                     case 'o':
                       res[gi[0]][k]="其他";
                       break;
                    }
                  }
              }else{
                if( gi[1].split(",").length>0){
                    res[gi[0]] = gi[1].split(",");
                  }
                  }
                for(var k = 0;k < res[gi[0]].length;k++){
                   if(k != 0){
                     if(gi[0] == "tag" && params.relation == "and"){
                         res[gi[0]][k] = " <font color='red'>与</font> " + res[gi[0]][k];
                      }else{
                         res[gi[0]][k] = " <font color='red'>或</font> "+res[gi[0]][k];
                           }
                     }
                }
              }
            }

            $.each(res,function(x,y){
              tip.children("ul").append("<li class='groupList groupList_"+x+"'><span class='group-icon group-"+x+"-icon'></span>"+lang(x)+"<ul></ul></li>");
              for(m in y){
                // tip.find(".groupList_"+x).children("ul").append('<li>
				// '+htmlEscape(y[m])+' </li>');
                tip.find(".groupList_"+x).children("ul").append('<li> '+y[m]+' </li>');
              }
            });
            }
            if(params.not_launch_in || params.launch_in){
              tip.children("ul").append("<li class='groupList groupList_not_launch_in'><span class='group-icon group-not_launch_in-icon'></span>"+lang("active")+"<ul></ul></li>");
              if(params.not_launch_in){
                tip.find(".groupList_not_launch_in").children("ul").append('<li>'+params.not_launch_in+'天内不活跃用户 </li>');
               }else if(params.launch_in){
                  tip.find(".groupList_not_launch_in").children("ul").append('<li>'+params.launch_in+'天内活跃用户 </li>');
                 }
              }
            tip = tip.html();
            groupRes += '<tr><td>包含</td><td>'+tip+'</td></tr>';

            }
            res = {};
            var tip2 = $("<div class='tip'><ul class='group-tip'></ul></div>");
            if(params.notcontains ){
            g = params.notcontains.split(';');
            for(i in g){
               var gi = g[i].split(':');
               if(gi[1]){
                   gi[0] = gi[0].replace("app_","");
                   if(gi[0] == "device_model"){
                      if( gi[1].split(separator).length>0){
                         res[gi[0]] = gi[1].split(separator);
                        }
                   }else if(gi[0] == 'language'){
                      var str=new Array();
                      var index_number = 0;
                      gires = gi[1].split(",");
                      for(var key in language){
                         var all=language_option[key].split(',');
                         for(var innerKey in all){
                           if(gires.indexOf(all[innerKey]) >= 0){
                             str[index_number] = language[key];
                             index_number = index_number + 1;
                             break;
                           }
                     }
                    }
                     if(index_number > 0){
                       res[gi[0]] = str;
                     }
                   }else if( gi[1].split(",").length>0){
                        res[gi[0]] = gi[1].split(",");
                     }
                    for(var k = 0;k < res[gi[0]].length;k++){
                      if(k != 0){
                        if(gi[0] == "tag" && params.relation == "and"){
                          res[gi[0]][k] = " <font color='red'>与</font> " + res[gi[0]][k];
                           }else{
                            res[gi[0]][k] = " <font color='red'>或</font> "+res[gi[0]][k];
                          }
                        }
                     }
                   }
                 }

            $.each(res,function(x,y){
                 tip2.children("ul").append("<li class='groupList groupList_"+x+"'><span class='group-icon group-"+x+"-icon'></span>"+lang(x)+"<ul></ul></li>");
                 for(m in y){
                // tip2.find(".groupList_"+x).children("ul").append('<li>
				// '+htmlEscape(y[m])+' </li>');
                tip2.find(".groupList_"+x).children("ul").append('<li> '+y[m]+' </li>');
                }
             });
               tip2 = tip2.html();
               groupRes += '<tr><td>不包含</td><td>'+tip2+'</td></tr>';
            }

            break;
          case 'unicast':
            var uni = params.device_tokens.split(',');
            var tip = "";
            for(x in uni){
              tip += "<li>"+htmlEscape(uni[x])+"</li>";
            }
            tip = "<ul>"+tip+"</ul>";
            break; 
          case 'customizedcast':
           // var tip = htmlEscape(params.aliasType+" -- "+params.alias);
           var tip = "<li>alias_type: "+htmlEscape(params.aliasType)+"</li>";
           tip += "<li>alias: "+htmlEscape(params.alias)+"</li>";
           tip = "<ul>"+tip+"</ul>";
            break;            
        }

        if(groupRes){
            msg_arr.push('<tr><td style="height:30px;">发送对象</td></tr>'+groupRes+
            '<tr><td>发送时间</td><td>'+(!params.start_time?"立即发送":("定时发送 "+params.start_time))+'</td></tr>\
             <tr><td>过期时间</td><td>'+params.expire_time +'</td></tr>');
        }else{
         msg_arr.push('<tr><td>发送对象</td><td>'+tip+'</td></tr>'+
            '<tr><td>发送时间</td><td>'+(!params.start_time?"立即发送":("定时发送 "+params.start_time))+'</td></tr>\
            <tr><td>过期时间</td><td>'+params.expire_time +'</td></tr>');
         }

        if($('#ristrictSpeech').attr('checked')){
            msg_arr.push('<tr><td>发送速度</td><td>每秒'+ params.max_send_num +'条信息</td></tr>');    
        }
        if(params.fromtest){
          msg_arr.push('<tr><td></td><td class="testtip">友情提示：本消息将以正式消息的模式发送给线上用户。</td></tr>');
          }
        $(msg_arr.join('')).dialog({
            title:'消息详情',
            dialogClass: "detail", 
            width:820,
            buttons:{
                "确认": function(){

                    var btn = $($(".ui-dialog-buttonset button").get(0)).attr("disabled", "disabled");

                    $(".mod-body").loadStart();
            
                    var url = '/msg/' + appkey + '/addPush', self = $(this);
                    
                    if(IS_TEST){
                        params.test_mode = 1;
                    }
                    $.post(url, params, function(d) {
                        $(".mod-body").loadEnd();
                        if (d.success == 1) {
                            self.dialog("close");
                            alert("添加Push成功！");
                            setTimeout(function() {
                                if(!IS_TEST){
                                    location.href = '/' + appkey + '/dashboard';
                                } else {
                                    location.href = '/' + appkey + '/testMsg';
                                }
                            }, 3000);
                        } else {
                            alert('<div style="word-break: break-all;">添加Push失败！(' + d.error + ')请重试</div>');
                        }
                        btn.removeAttr("disabled");
                    });
                },
                "取消": function(){
                    $( this ).dialog( "close" );
                }
            }
        });
    }

    $("#pageSubmit").click(function() {
        var params = getMsgParams();
        if (params) {
            /*
			 * $(".mod-body").loadStart();
			 * 
			 * var url = '/msg/' + appkey + '/addPush';
			 * 
			 * if(IS_TEST){ params.test_mode = 1; }
			 * 
			 * $.post(url, params, function(d) { $(".mod-body").loadEnd(); if
			 * (d.success == 1) { alert("添加Push成功！"); setTimeout(function() {
			 * if(!IS_TEST){ location.href = '/' + appkey + '/dashboard'; } else {
			 * location.href = '/' + appkey + '/testMsg'; } }, 3000); } else {
			 * alert("添加Push失败！(" + d.error + ")请重试"); } });
			 */

            if(APPPLATFORM == "android"){
                showAndroidDetail(params);
            } else {
                showIOSDetail(params);
            }
        }else{
        	alert("请参考页面给出的错误提示,正确完善参数!");
        }
    });

    $('#addCustomKeyValue').on('click', function(){
        // var par = $(this).parent(), node = par.clone();
        // $('button',
		// node).removeAttr("id").addClass("removeKeyvalue").html("删除");
        var ck = $(".customkeyvalue");
        if(ck.length == 0){
            $(this).parent().after('<div class="formItem customkeyvalue"><label>key:</label><input class="input" type="text" name="ios_custom_key" value=""/><label>Value:</label><input class="input" type="text" name="ios_custom_value" value=""/><span class="deleteIcon"></span></div>');
        } else {
            var node = ck.eq(0).clone();
            ck.last().after($('<div class="formItem customkeyvalue"><label>key:</label><input class="input" type="text" name="ios_custom_key" value=""/><label>Value:</label><input class="input" type="text" name="ios_custom_value" value=""/><span class="deleteIcon"></span></div>'));
        }
    });


 $('#addCondition').on('click', function(){
         var count = $(".dimension").length;
         if (count>=4){ return ;}
           $(this).before(component+'<span class="tips3" style="display: none;">筛选条件>重复，请修改</span></div>');
           var dimensionSelect = $(".dimension").last();
           var myselect = dimensionSelect.siblings("select.multiple");
           dimensionSelect.siblings("select.single").hide();
           myselect.chosen();
           myselect.trigger("liszt:updated");
});
    function addIOSKyeValue(key, value){
        var ck = $(".customkeyvalue");
        value = htmlEscape(value);

        if(ck.length == 0){
            $('#addCustomKeyValue').parent().after('<div class="formItem customkeyvalue"><label>key:</label><input class="input" type="text" name="ios_custom_key" value="' + key + '"/><label>Value:</label><input class="input" type="text" name="ios_custom_value" value="' + value + '"/><span class="deleteIcon"></span></div>');
        } else {
            var node = $('<div class="formItem customkeyvalue"><label>key:</label><input class="input" type="text" name="ios_custom_key" value="' + key + '"/><label>Value:</label><input class="input" type="text" name="ios_custom_value" value="' + value + '"/><span class="deleteIcon"></span></div>');
            ck.last().after(node);
        }    
    }

    $(document).on('click', '.deleteIcon', function(){
        $(this).parent().remove();
    });

    $(document).on('click','.deleteGroupIcon',function(){
       $(this).parent().parent().remove();
    });

    $(document).on('click','.deleteSegment',function(){
       var currentIndex=$('.templateId:checked').val();
       var index=$(this).siblings("input").val();
      $.post('/'+appkey+'/delFilter',{'segment-id':index},function(d){

      });
       delete segments[index];
       delete segmentContent[index];
      // $(this).parent().remove();
      var str = "";
      if(segments){
         var num = 1;
         for(var key in segments){
           str += "<div class='iconDelDiv'><input type='checkbox' name='templates' value='"+key+"' class='templateId' id='"+key+"'><label  style='display:inline' for='"+key+"'>"+segments[key]+"</label><span class='deleteSegment'></span></div>";
               num++;
         }
         if(num ==1){ str="暂无可用模板";}
         $("#templateList").html(str);
         if(currentIndex && currentIndex != index){
           $("#"+currentIndex+"").attr("checked",true);
           }
        }
    });


    $('#extrakeyvalue').on('click', function(){
        var self = $(this);
        if(self.attr('checked')){
            $('#addExtrakeyvalue').removeAttr('disabled').show();
            $('.customrow').show();
            if($('.customrow').length == 0){
                var el = $('<div class="customrow"><label>Key:<input type="text" class="input" name="key"/></label><label>Value:<input type="text" class="input" name="value"/></label><span class="deleteIcon"></span><span class="tips3" style="display: none;">请完整填写自定义参数</span></div>');
                $('#addExtrakeyvalue').before(el);
                $('input[name="key"]', el).focus();
            }
        } else {
            $('.customrow').hide();
            $('#addExtrakeyvalue').attr('disabled', true).hide();
        }
    });

    $('#addExtrakeyvalue').on('click', function(){
        var count = $('.customrow').length;
        if(count < 5){
            var el = $('<div class="customrow"><label>Key:<input type="text" class="input" name="key"/></label><label>Value:<input type="text" class="input" name="value"/></label><span class="deleteIcon"></span><span class="tips3" style="display: none;">请完整填写自定义参数</span></div>');
            $(this).before(el);
            $('input[name="key"]', el).focus();
        }
    });
    $('#extrakv').on('click', function(){
            var self = $(this);
            var next = self.parent().siblings(".alert");
            if(self.attr('checked')){
                next.show();
                $('#addMessageExtra').removeAttr('disabled').show();
                $('.customkv').show();
                if($('.customkv').length == 0){
                    var el = $('<div class="customkv"><label>Key:<input type="text" class="input" name="key"/></label><label>Value:<input type="text" class="input" name="value"/></label><span class="deleteIcon"></span><span class="tips3" style="display: none;">请完整填写自定义参数</span></div>');
                    $('#addMessageExtra').before(el);
                    $('input[name="key"]', el).focus();
                }
            } else {
                $('.customkv').hide();
                next.hide();
                $('#addMessageExtra').attr('disabled', true).hide();
            }
        });
        $('#addMessageExtra').on('click', function(){
                var count = $('.customkv').length;
                if(count < 10){
                    var el = $('<div class="customkv"><label>Key:<input type="text" class="input" name="key"/></label><label>Value:<input type="text" class="input" name="value"/></label><span class="deleteIcon"></span><span class="tips3" style="display: none;">请完整填写自定义参数</span></div>');
                    $(this).before(el);
                    $('input[name="key"]', el).focus();
                }
            });

    $(document).on('click', '.customrow .deleteIcon', function(){
        $(this).parent().remove();
    });

    $(document).on('focus', '.customrow input[name="key"], .customrow  input[name="value"]', function(){
        $('.tips3', $(this).parents('.customrow')).hide();
    });

   $(document).on('click', '.customkv .deleteIcon', function(){
            $(this).parent().remove();
        });

    $(document).on('focus', '.customkv input[name="key"], .customrow  input[name="value"]', function(){
            $('.tips3', $(this).parents('.customkv')).hide();
        });
    $('.arrowicon').bind('click', function(){
      var el = $(this), target = $("#"+el.attr("data-target"));
      if(el.hasClass("up-arrow")) {
        target.hide();
        el.removeClass("up-arrow").addClass("down-arrow");
      } else {
        target.show();
        el.removeClass("down-arrow").addClass("up-arrow");
      }
    });

    $('#ristrictSpeech').on('click', function(){
        var self = $(this);
        if(self.attr('checked')){
            $('#max_send_num').removeAttr('disabled');
        } else {
            $('#max_send_num_error').hide();
            $('#max_send_num').attr('disabled', true);
        }
    });

    $('#max_send_num').on('input', function(evt){
        inputNumber(this);
    });

    $('#max_send_num').on('focus', function(evt){
        $('#max_send_num_error').hide();    
    });

    $('#max_send_num').on('blur', function(evt){
        if($('#ristrictSpeech').attr('checked')){
            var v = $('#max_send_num').val();
            if(v > 10000 || v < 500){
                $('#max_send_num_error').show();
            } else {
                $('#max_send_num_error').hide();
            }   
        } else {

        }
    });

    $('#customizedcast_val').on('focus', function(data){
        $('#customizedcastTip').hide();
        $('#aliasTypeTip').hide();
     });


    $('#customiconckb').on('click', function(){
        var self = $(this);
        if(self.attr('checked')){
            $('#customIconShow').show();
        } else {
            $('#customIconShow').hide();
        }    
    });

    
    $('#customsoundckb').on('click', function(){
        var self = $(this);
        if(self.attr('checked')){
            $('#customsoundrow').show();
        } else {
            $('#customsoundrow').hide();
        }    
    });

    $('input[name="icon_type"]').on('click', function(){
        var self = $(this);
        if(self.attr('checked')){
            
            if(self.val() == "1"){
                $('#uploadiconarea').hide();
                $('#appiconarea').show(); 
            } else {
                $('#uploadiconarea').show();
                $('#appiconarea').hide();    
            }

        }
    });

    $('#alertStyle').on('input', function(evt){
        validateInputNumber(this);
    });
    $("[name='sendTo']").change(function(){
    	if($(this).val()=="unicast"){
    		$("[name='pushToIOS'],[name='pushToAndroid']").attr("checked",false).hide();
    		$("[name='pushToTarget']").show();
    		$("[name='pushToTarget']").first().attr("checked",true).trigger("change");
    	}else{
    		$("[name='pushToTarget']").hide();
    		$("[name='pushToIOS'],[name='pushToAndroid']").attr("checked",true).show();
    	}
    });
    $("[name='pushToTarget']").hide().change(function(){
    	if($(this).val()=="android"){
    		//单播到Android客户端
    		$("[name='pushToAndroid']").attr("checked",true);
    		$("[name='pushToIOS']").attr("checked",false);
    	}else{
    		//单播到IOS客户端
    		$("[name='pushToAndroid']").attr("checked",false);
    		$("[name='pushToIOS']").attr("checked",true);
    	}
    });
    $("#PushTarget input:checkbox").attr("checked",true);//默认推送Android和IOS客户端
    $("#pushText").trigger("keyup");
    
    init();
});
