CKEDITOR.plugins.add('swfupload',{
	init : function(editor) {
		// Add the link and unlink buttons.
		editor.addCommand('swfupload', new CKEDITOR.dialogCommand('swfupload'));
		editor.ui.addButton('swfupload', {
			// label : editor.lang.link.toolbar,
			label : "swfupload",
			icon: this.path + 'image_upload.gif',
			command : 'swfupload'
		});
		// CKEDITOR.dialog.add( ‘link’, this.path + ‘dialogs/link.js’ );
		CKEDITOR.dialog.add('swfupload', function( editor ){
			return {
				title : '上传图片',
				minWidth : 600,
				minHeight : 300,
				contents : [{
				elements: [{
	                     type: "html",
	                     html: "<iframe id='swfuploadPage' name='swfuploadPage' width='100%' height='100%' src='/plug-in/ckeditor/plugins/swfupload/upload.html'></iframe>",
	                     style: "width:100%;height:200px;padding:0;"
		        }]
				}],
				onOk : function(){
					var paths = document.getElementById("swfuploadPage").contentDocument.getElementById("ckswfPaths").value;
					//alert(paths);
					editor.insertHtml(document.getElementById("swfuploadPage").contentDocument.getElementById("imagepath").value);
					//alert(1111);
				},
				onShow : function(){
					document.getElementById("swfuploadPage").contentDocument.getElementById("imagepath").value="";
				}
			};
		});
	},
	requires:['fakeobjects']
});



