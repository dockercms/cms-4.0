/**
 * Created by sunyaoxu on 2017/4/18.
 */


/**
 * 点赞
 */
var contentId = contentid;
$(function (){
    //获取 评论数量
    var urlStr = contextpath+"/front/contentsFrontController.do?calculationCommentCount";
    $.ajax({
        type : 'post',
        url : urlStr,
        data:"contentId="+contentId,
        success : function(msg) {
            var obj = JSON.parse(eval(msg));
            if(obj.commentCount=='0'){
                $("#commtent_a").attr("href","javascript:void(0);");
            }
            var commentCountStr = obj.commentCount+"条评论";
            $('#flushCommentCount').text(commentCountStr);
        }
    });


    //获取赞数、贬数
    var urlStr = contextpath+"/front/contentsFrontController.do?getSupport";
    $.ajax({
        type:'post',
        url:urlStr,
        data:"contentId="+contentId,
        success:function(msg){
            var obj = JSON.parse(eval(msg));
            var vSupport = obj.support;//获取js对象的值
            var vOppose = obj.oppose;
            document.getElementById("zantong_count").innerHTML = vSupport;
            document.getElementById("fandui_count").innerHTML = vOppose;
        }
    });
});

function setCookie(name,value)
{
    document.cookie = encodeURIComponent(name) + "=" + encodeURIComponent(value)
}
function getCookie(name) {
    var search = name + "="//查询检索的值
    var returnvalue = "";//返回值
    if (document.cookie.length > 0) {
        sd = document.cookie.indexOf(search);
        if (sd!= -1) {
            sd += search.length;
            end = document.cookie.indexOf(";", sd);
            if (end == -1)
                end = document.cookie.length;
            //unescape() 函数可对通过 escape() 编码的字符串进行解码。
            returnvalue=unescape(document.cookie.substring(sd, end))
        }
    }
    return returnvalue;
}

function addSupport(){

        // 为 文章的赞
        var a=getCookie("dianzan_"+contentId);
        if(a != 1){
            var urlStr = contextpath+"/front/contentsFrontController.do?addSupport";
            $.ajax({
                type:'post',
                url:urlStr,
                data:"contentId="+contentId,
                success:function(msg){
                    document.getElementById("zantong_count").innerHTML=msg;
                    setCookie("dianzan_"+contentId,"1");
                }
            });
        }


}

function deleteSupport(){
       // 为 文章的踩
        var a=getCookie("dianzan_"+contentId);
        if(a!=1){
            var urlStr = contextpath+"/front/contentsFrontController.do?addOppose";
            $.ajax({
                type:'post',
                url:urlStr,
                data:"contentId="+contentId,
                success:function(msg){
                    document.getElementById("fandui_count").innerHTML=msg;
                    setCookie("dianzan_"+contentId,"1");
                }
            });
        }
}
function addComment(){
    var content = $("#commentcontent").val();
    content = content.trim();
    if(content == null || content == "") {
        layer.msg("评论不能为空");
        return false;
    }
    $.ajax({
        type: "POST",
        url:contextpath+"/front/commentFrontController.do?save",
        data:$('#addComment').serialize(),// 序列化表单值
        success: function(msg) {
            var obj = JSON.parse(eval(msg));
            layer.msg(obj.msg);
            $("#commentcontent").val("");
            if(obj.commentCount!='0'){
                $("#commtent_a").attr("href",contextpath+"/front/commentFrontController.do?commentaryList&contentid="+contentId);
            }
            $("#flushCommentCount").html(obj.commentCount);
        }
    });
}



//        限定评论框中的字数
function   chkmaxsms(vobj1,vmax)   {
    var   str=vobj1.value;
    var   strlen=str.length;
    if(strlen>vmax)   {
        layer.msg('字数超过限制');
        eval(vobj1.value=str.substr(0,vmax));
    }
}
