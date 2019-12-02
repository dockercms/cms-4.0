/**
 * 片头广告
 */
var headeruploaderimg = WebUploader.create({
	// 选完文件后，是否自动上传。
    auto: true,
    // swf文件路径
    swf: 'plug-in/ueditor/third-party/webuploader/Uploader.swf',
    // 文件接收服务端。
    server: 'controller.jsp?action=uploadimage',
    // 选择文件的按钮。可选。
    // 内部根据当前运行是创建，可能是input元素，也可能是flash.
    pick: '#headerpickerimg',
    // 不压缩image, 默认如果是jpeg，文件上传前会压缩一把再上传！
    resize: false,
    // 只允许选择的文件。
    accept: {
        title: 'Images',
        extensions: 'gif,jpg,jpeg,bmp,png',
        mimeTypes: 'image/gif,image/jpg,image/jpeg,image/bmp,image/png'
    }
});

//上传成功
headeruploaderimg.on( 'uploadSuccess', function( file,Object ) {
	$("#headadvresource").val(Object.url);
});

headeruploaderimg.on( 'uploadProgress', function( file, percentage ) {
    var $li = $( '#'+file.id ),
        $percent = $li.find('.progress .progress-bar');

    // 避免重复创建
    if ( !$percent.length ) {
        $percent = $('<div class="progress progress-striped active">' +
          '<div class="progress-bar" role="progressbar" style="width: 0%">' +
          '</div>' +
        '</div>').appendTo( $li ).find('.progress-bar');
    }

    $li.find('p.state').text('上传中');

    $percent.css( 'width', percentage * 100 + '%' );
});




var headeruploadervideo = WebUploader.create({
	// 选完文件后，是否自动上传。
    auto: true,
    // swf文件路径
    swf: 'plug-in/ueditor/third-party/webuploader/Uploader.swf',
    // 文件接收服务端。
    server: 'controller.jsp?action=uploadvideo',
    // 选择文件的按钮。可选。
    // 内部根据当前运行是创建，可能是input元素，也可能是flash.
    pick: '#headerpickervideo',
    // 不压缩image, 默认如果是jpeg，文件上传前会压缩一把再上传！
    resize: false,
    // 只允许选择的文件。
    accept: {
        title: 'video',
        extensions: "flv,swf,mkv,avi,rm,rmvb,mpeg,mpg,ogg,ogv,mov,wmv,mp4,webm,mp3,wav,mid,png",
        mimeTypes: 'video/*,audio/*'
    }
});

//上传成功
headeruploadervideo.on( 'uploadSuccess', function( file,Object ) {
	$("#headadvresource").val(Object.url);
});

/**
 * 暂停广告
 */
var stopuploaderimg = WebUploader.create({
	// 选完文件后，是否自动上传。
    auto: true,
    // swf文件路径
    swf: 'plug-in/ueditor/third-party/webuploader/Uploader.swf',
    // 文件接收服务端。
    server: 'controller.jsp?action=uploadimage',
    // 选择文件的按钮。可选。
    // 内部根据当前运行是创建，可能是input元素，也可能是flash.
    pick: '#stoppickerimg',
    // 不压缩image, 默认如果是jpeg，文件上传前会压缩一把再上传！
    resize: false,
    // 只允许选择的文件。
    accept: {
        title: 'Images',
        extensions: 'gif,jpg,jpeg,bmp,png',
        mimeTypes: 'image/gif,image/jpg,image/jpeg,image/bmp,image/png'
    }
});

//上传成功
stopuploaderimg.on( 'uploadSuccess', function( file,Object ) {
	$("#stopadvreource").val(Object.url);
});

var stopuploadervideo = WebUploader.create({
	// 选完文件后，是否自动上传。
    auto: true,
    // swf文件路径
    swf: 'plug-in/ueditor/third-party/webuploader/Uploader.swf',
    // 文件接收服务端。
    server: 'controller.jsp?action=uploadvideo',
    // 选择文件的按钮。可选。
    // 内部根据当前运行是创建，可能是input元素，也可能是flash.
    pick: '#stoppickervideo',
    // 不压缩image, 默认如果是jpeg，文件上传前会压缩一把再上传！
    resize: false,
    // 只允许选择的文件。
    accept: {
        title: 'video',
        extensions: "flv,swf,mkv,avi,rm,rmvb,mpeg,mpg,ogg,ogv,mov,wmv,mp4,webm,mp3,wav,mid,png",
        mimeTypes: 'video/*,audio/*'
    }
});

//上传成功
stopuploadervideo.on( 'uploadSuccess', function( file,Object ) {
	$("#stopadvreource").val(Object.url);
});


/**
 * 片尾广告
 */

var footuploaderimg = WebUploader.create({
	// 选完文件后，是否自动上传。
    auto: true,
    // swf文件路径
    swf: 'plug-in/ueditor/third-party/webuploader/Uploader.swf',
    // 文件接收服务端。
    server: 'controller.jsp?action=uploadimage',
    // 选择文件的按钮。可选。
    // 内部根据当前运行是创建，可能是input元素，也可能是flash.
    pick: '#footpickerimg',
    // 不压缩image, 默认如果是jpeg，文件上传前会压缩一把再上传！
    resize: false,
    // 只允许选择的文件。
    accept: {
        title: 'Images',
        extensions: 'gif,jpg,jpeg,bmp,png',
        mimeTypes: 'image/gif,image/jpg,image/jpeg,image/bmp,image/png'
    }
});

//上传成功
footuploaderimg.on( 'uploadSuccess', function( file,Object ) {
	$("#footadvresource").val(Object.url);
});

var footuploadervideo = WebUploader.create({
	// 选完文件后，是否自动上传。
    auto: true,
    // swf文件路径
    swf: 'plug-in/ueditor/third-party/webuploader/Uploader.swf',
    // 文件接收服务端。
    server: 'controller.jsp?action=uploadvideo',
    // 选择文件的按钮。可选。
    // 内部根据当前运行是创建，可能是input元素，也可能是flash.
    pick: '#footpickervideo',
    // 不压缩image, 默认如果是jpeg，文件上传前会压缩一把再上传！
    resize: false,
    // 只允许选择的文件。
    accept: {
        title: 'video',
        extensions: "flv,swf,mkv,avi,rm,rmvb,mpeg,mpg,ogg,ogv,mov,wmv,mp4,webm,mp3,wav,mid,png",
        mimeTypes: 'video/*,audio/*'
    }
});

//上传成功
footuploadervideo.on( 'uploadSuccess', function( file,Object ) {
	$("#footadvresource").val(Object.url);
});

function formsubmit(urlStr, formName){
	$.ajax({
		type:'post',
		url:urlStr,
		data:$('#' + formName).serialize(),
		dataType:'text',
		success:function(msg){
			var obj = JSON.parse(eval(msg));
			if(obj.isSuccess){
				var toUrl = obj.toUrl;
				if (!toUrl && typeof(toUrl)!="undefined" && toUrl!=0 && toUrl != '')
				{
				    toUrl = arguments[2] + '';
				}
				
				$('#editModel').modal('hide');
				$('#editModel').empty();
				alert(obj.msg);
				lazyChangePage(toUrl);
			}else{
				alert("提交处理失败!");
			}
		},
		error:function(){
			alert("提交通讯失败!");
		}
	});
}

