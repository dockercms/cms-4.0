/**
 * Created by sunyaoxu on 2017/4/7.
 */
/*评论列表js
 */

//document.write("<script language=javascript src='${contextpath}/member/js/comment/layer.js'></script>");

var pageNo=1;
function loadComment(urlStr,ul_comment_list){
    if(parseInt($("#commentCount_"+pageNo).val())==10){
        var comment_time=$("#time_"+pageNo+"_"+10).val();
        pageNo++;
        var data={
            "commentTime":comment_time,
            "pageNo":pageNo,
        }
        pageList(urlStr,ul_comment_list,data);

    }else{
        $(".loadMore").find("span").hide();
        $(".loadMore").find("em").removeClass("np-load-more-loading");
        $(".loadMore").find("em").show();
    }
}

/**
 * 查询评论分页
 */
function pageList(urlStr,ul_comment_list,data){
    $(".loadMore").find("span").hide();
    $(".loadMore").find("em").show();
    $.ajax({
        type: 'post',
        url: urlStr,
        data: data,
        dataType: 'text',
        success: function (msg) {
            $(".loadMore").find("em").hide();
            $(".loadMore").find("span").show();
            $('#'+ul_comment_list).append(msg);
            if(parseInt($("#commentCount_"+pageNo).val())!=10){
                $(".loadMore").find("span").hide();
                $(".loadMore").find("em").removeClass("np-load-more-loading");
                $(".loadMore").find("em").show();
            }

        }
    });

}

$(function() {
    //	是否登录
    var urlStr = contextpath+"/front/surveyLogController.do?isLogin";
    $.ajax({
        type: 'post',
        url: urlStr,
        data: 'data=0',
        dataType: 'text',
        success: function (msg) {
            $('#divLogin').empty();
            $('#divLogin').append(msg);
        }
    });


    /**
     * 自动更新评论列表
     */
    $.ajax({
        type: 'post',
        url: contextpath+'/front/commentFrontController.do?commentPageList&contentid='+contentId,
        data: 'data=0',
        dataType: 'text',
        success: function (msg) {
            $('#ul_comment_list').empty();
            $('#ul_comment_list').append(msg);
        }
    });
});
function addComment(){
    var content = $("#commentcontent").val();
    content = content.trim();
    if(content == null || content == "") {
        layer.msg('评论不能为空!');
        return false;
    }
    $.ajax({
        type: "POST",
        url:contextpath+"/front/commentFrontController.do?save",
        data:$('#addComment').serialize(),// 序列化表单值
        success: function(msg) {
            var obj = JSON.parse(eval(msg));
            layer.msg(obj.msg,{time:1000},function(){
                $("#commentcontent").val("");
                window.location.reload(true);
            });
        }
    });
}
/*点赞*/
function addSupport(commentid){
    var a=getCookie("dianzan_comment"+commentid);
    if(a != 1){
        var urlStr = contextpath+"/front/commentFrontController.do?addSupport";
        $.ajax({
            type:'post',
            url:urlStr,
            data:"commentId="+commentid,
            success:function(msg){
                document.getElementById("zantong_commentcount_"+commentid).innerHTML=msg;
                if($("#reply_zantong_commentcount_"+commentid)){
                    $("#reply_zantong_commentcount_"+commentid).html(msg);
                }
                setCookie("dianzan_comment"+commentid,"1");
            }
        });
    }
}
/**
 * 贬赞
 * @param commentid
 */
function deleteSupport(commentid){
    var a=getCookie("dianzan_comment"+commentid);
    if(a != 1){
        var urlStr = contextpath+"/front/commentFrontController.do?addOppose";
        $.ajax({
            type:'post',
            url:urlStr,
            data:"commentId="+commentid,
            success:function(msg){
                document.getElementById("fandui_commentcount_"+commentid).innerHTML=msg;
                if($("#reply_fandui_commentcount_"+commentid)){
                    $("#reply_fandui_commentcount_"+commentid).html(msg);
                }
                setCookie("dianzan_comment"+commentid,"1");
            }
        });
    }
}



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


//      显示 评论中回复的折叠框
function showreply(commentid){
    $(".reply_form_"+commentid).toggle(400);
}
//      显示 跟帖中 评论的回复折叠框
function showsmallreply(commentid){
    $(".reply_smallform_"+commentid).toggle(400);
}
//      对 回复进行保存
function addReply(commentid){
    var content = $("#replycontent_"+commentid).val();
    content = content.trim();
    if(content == null || content == "") {
        layer.msg("回复不能为空!");
        return false;
    }
    $.ajax({
        type: "POST",
        url:contextpath+"/front/replyFrontController.do?save",
        data:$('.reply_form_'+commentid).serialize(),// 序列化表单值
        success: function(msg) {
            var obj = JSON.parse(eval(msg));
            layer.msg(obj.msg);
            $("#flushReplyTotalCount_"+commentid).html(obj.replyTotalCount);
            $("#replycontent_"+commentid).val("");
            var url=contextpath+'/front/replyFrontController.do?showList&commentid='+commentid;
            if(obj.replyTotalCount==1){
                $("#r_"+commentid).append('<a class="np-postlink np-btn"  href="javascript:void(0)" ' +
                    'onclick="showAllReply(\''+url+'\')"> ' +
                    '<img src= "/pcstyle/images/transparent.gif">查看回复(<em id="flushReplyTotalCount_'+commentid+'">1</em>)</a>');
            }





        }
    });
}

function addSmallReply(commentid){
    var content = $("#smallreplycontent_"+commentid).val();
    content = content.trim();
    if(content == null || content == "") {
        layer.msg("回复不能为空!!");
        return false;
    }
    $.ajax({
        type: "POST",
        url:contextpath+"/front/replyFrontController.do?save",
        data:$('#addSmallReply_'+commentid).serialize(),// 序列化表单值
        success: function(msg) {
            var obj = JSON.parse(eval(msg));



            //$("#reply_commentcount_"+commentid).html(obj.replyTotalCount);
            layer.msg(obj.msg,{time:1000},function(){
                $("#smallreplycontent_"+commentid).val("");
                $("#reply_commentcount_"+commentid).html(obj.replyTotalCount);
                var urlStr =contextpath+'/front/replyFrontController.do?showList&commentid='+commentid;
                /**
                 * 自动更新跟帖列表
                 */
                var arr=urlStr.split("?");
                var str1 = arr[1];
                var commentId=str1.split("=")[1];
                $.ajax({
                    type: 'post',
                    url: contextpath+'/front/replyFrontController.do?replyPageList&commentId='+commentId,
                    data: 'data=0',
                    dataType: 'text',
                    success: function (msg) {
                        $('#ul_reply_list').empty();
                        $('#ul_reply_list').append(msg);
                    }
                });
            });
            $("#flushReplyTotalCount_"+commentid).html(obj.replyTotalCount);


        }
    });


}
function showAllReply(urlStr){
    $.ajax({
        type:'post',
        url:urlStr,
        data:'data=0',
        dataType:'text',
        success:function(msg){
            $('#myModel').empty();
            $('#myModel').append(msg);
            $('#myModel').modal('show');

            /**
             * 自动更新跟帖列表
             */
            var arr=urlStr.split("?");
            var str1 = arr[1];
            var commentId=str1.split("=")[1];
            $.ajax({
                type: 'post',
                url: contextpath+'/front/replyFrontController.do?replyPageList&commentId='+commentId,
                data: 'data=0',
                dataType: 'text',
                success: function (msg) {
                    $('#ul_reply_list').empty();
                    $('#ul_reply_list').append(msg);
                }
            });
        },
        error:function(){
            layer.msg("load page error, page url is " + urlStr);
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

function addReplySupport(replyid){
    var a=getCookie("dianzan_reply_"+replyid);
    if(a != 1){
        var urlStr = contextpath+"/front/replyFrontController.do?addReplySupport";
        $.ajax({
            type:'post',
            url:urlStr,
            data:"replyId="+replyid,
            success:function(msg){
                $("#zantong_replycount_"+replyid).html(msg);
                setCookie("dianzan_reply_"+replyid,"1");
            }
        });
    }
}

function deleteReplySupport(replyid){
    var a=getCookie("dianzan_reply_"+replyid);
    if(a != 1){
        var urlStr = contextpath+"/front/replyFrontController.do?deleteReplySupport";
        $.ajax({
            type:'post',
            url:urlStr,
            data:"replyId="+replyid,
            success:function(msg){
                $("#fandui_replycount_"+replyid).html(msg);
                setCookie("dianzan_reply_"+replyid,"1");
            }
        });
    }
}

//        保存 对跟帖的回复
function addreturn(replyId) {
    var content = $("#returncontent_" + replyId).val();
    content = content.trim();
    if (content == null || content == "") {
        layer.msg("回复不能为空!");
        return false;
    }
    $.ajax({
        type: "POST",
        url: contextpath + "/front/replyFrontController.do?saveReturn",
        data: $('#addreturn_' + replyId).serialize(),// 序列化表单值
        success: function (msg) {
            var obj = JSON.parse(eval(msg));
            layer.msg(obj.msg);
        }
    });
}

    /*评论列表js
     */
    var replyPageNo=1;
    function clearReplyPageNo(){
        replyPageNo=1;
    }
    function loadReply(urlStr,ul_reply_list){
        if(parseInt($("#replyVoEntityCount_"+replyPageNo).val())==10){
            var comment_time=$("#replyTime_"+replyPageNo+"_"+10).val();
            replyPageNo++;
            var data={
                "replyTime":comment_time,
                "replyPageNo":replyPageNo,
            }
            replyPageList(urlStr,ul_reply_list,data);

        }else{
            $(".loadMoreReply").find("span").hide();
            $(".loadMoreReply").find("em").removeClass("np-load-more-loading");
            $(".loadMoreReply").find("em").show();
        }
    }

    /**
     * 查询评论分页
     */
    function replyPageList(urlStr,ul_reply_list,data){
        $(".loadMoreReply").find("span").hide();
        $(".loadMoreReply").find("em").show();
        $.ajax({
            type: 'post',
            url: urlStr,
            data: data,
            dataType: 'text',
            success: function (msg) {
                $(".loadMoreReply").find("em").hide();
                $(".loadMoreReply").find("span").show();
                $('#'+ul_reply_list).append(msg);
                if(parseInt($("#commentCount_"+replyPageNo).val())!=10){
                    $(".loadMoreReply").find("span").hide();
                    $(".loadMoreReply").find("em").removeClass("np-load-more-loading");
                    $(".loadMoreReply").find("em").show();
                }

            }
        });
    }
