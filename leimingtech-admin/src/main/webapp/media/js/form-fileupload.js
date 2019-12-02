var FormFileUpload = function () {
    return {
        //main function to initiate the module
        init: function () {
            // Initialize the jQuery File Upload widget:
            $('#fileupload').fileupload({
                // Uncomment the following to send cross-domain cookies:
                //xhrFields: {withCredentials: true},
                url: '/uploadimg'
            });

            // Load existing files:
            // Demo settings:
            $.ajax({
                // Uncomment the following to send cross-domain cookies:
                //xhrFields: {withCredentials: true},
            	contentType: "multipart/form-data; charset=UTF-8;boundary=boundary",
                url: $('#fileupload').fileupload('option', 'url'),
                dataType: 'json',
                context: $('#fileupload')[0],
                maxFileSize: 5000000,
                acceptFileTypes: /(\.|\/)(gif|jpe?g|png)$/i,
                process: [{
                        action: 'load',
                        fileTypes: /^image\/(gif|jpeg|png)$/,
                        maxFileSize: 20000000 // 20MB
                    }, {
                        action: 'resize',
                        maxWidth: 1440,
                        maxHeight: 900
                    }, {
                        action: 'save'
                    }
                ]
            }).done(function (result) {
                $(this).fileupload('option', 'done').call(this, null, {
                    result: result
                });
            });
            
           /* 测试获取数据
			$('#fileupload').bind('fileuploaddone', function (e, data) {
					console.log('Processing ' + data.files[0].name);
					console.log('Processing ' + data.files[0].size);
					console.log('Processing ' + data.files[0].url);有问题
					console.log('Processing'+ data.files[0].thumbnailUrl);有问题
					var json= eval(data.files[0]);
					for(var i=0; i<json.length; i++){   
						console.log('Processing' + json[i].text+" " + json[i].value );   
					} 
			});*/
            
            // Upload server status check for browsers with CORS support:
            if ($.support.cors) {
                $.ajax({
                    url: '/uploadimg',
                    type: 'HEAD'
                }).fail(function () {
                    $('<span class="alert alert-error"/>').text('上传服务器当前不可用 - ' +new Date()).appendTo('#fileupload');
                });
            }

            // initialize uniform checkboxes  
            App.initUniform('.fileupload-toggle-checkbox');
        }
    };
}();