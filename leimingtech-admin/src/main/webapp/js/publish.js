/*
 * 生成静态
 */
$.fn.coffee = function(obj) {
	for ( var eName in obj)
		for ( var selector in obj[eName])
			$(this).on(eName, selector, obj[eName][selector]);
}
$(function (){
	$(".publishcatalog,.publishsite,.publishSiteAllInfo,.publisharticle,.publishindex").unbind();

	$('body').coffee({
		click : {
			'.publishcatalog' : function(e) {
				// 发布栏目
				e.preventDefault();
				UIToastr.info("栏目发布中...");
				var self = $(".publishcatalog:eq(0)");
//				var btninfo=$(self).text();
//				$(self).text("栏目发布中...");
				var catalogid = $(self).attr("catalogid");
//				var el = $(this).parents(".page-header-fixed");
//				App.blockUI(el);

				var urlStr = "publishAct.do?publishCatalog&catalogid=" + catalogid;
				SystemProgressBar.start();
				$.ajax({
					type : 'post',
					url : urlStr,
					data : 't=' + new Date().getTime(),
					dataType : 'text',
					success : function(msg) {
//						App.unblockUI(el);
//						$(self).text(btninfo);
						SystemProgressBar.stop();

						var obj = JSON.parse(eval(msg));
						if (obj.isSuccess) {
//							lmAlert(obj.msg);
							UIToastr.success(obj.msg);
						} else {
//							lmAlert(obj.msg);
							UIToastr.error(obj.msg);
						}
					},
					error : function() {
						SystemProgressBar.stop();
						lmAlert("load page error, page url is " + urlStr);
					}
				});
			},
			'.publishCatalogAndAllContent' : function(e) {
				// 发布栏目
				e.preventDefault();
				
				UIToastr.info("栏目发布中...");
				
				var self = $(".publishcatalog:eq(0)");
//				var btninfo=$(self).text();
//				$(self).text("栏目发布中...");
				var catalogid = $(self).attr("catalogid");
//				var el = $(this).parents(".page-header-fixed");
//				App.blockUI(el);

				var urlStr = "publishAct.do?publishCatalogAndAllContent&catalogid=" + catalogid;
				SystemProgressBar.start();
				$.ajax({
					type : 'post',
					url : urlStr,
					data : 't=' + new Date().getTime(),
					dataType : 'text',
					success : function(msg) {
//						App.unblockUI(el);
//						$(self).text(btninfo);
						SystemProgressBar.stop();
						var obj = JSON.parse(eval(msg));
						if (obj.isSuccess) {
//							lmAlert(obj.msg);
							UIToastr.success(obj.msg);
						} else {
//							lmAlert(obj.msg);
							UIToastr.error(obj.msg);
						}
					},
					error : function() {
						SystemProgressBar.stop();
						alert("load page error, page url is " + urlStr);
					}
				});
			},
			'.publishsite' : function(e) {
				// 发布站点
				e.preventDefault();
				
				UIToastr.info("站点正在发布中...");
				
				var sitebtn = $(".publishsite:eq(0)");
				var self = this;
//				var btninfo=$(self).text();
//				var sitebtninfo=$(sitebtn).text();
//				$(self).text("站点发布中...");
//				$(sitebtn).text("站点发布中...");
//				var el = $(this).parents(".page-header-fixed");
//				App.blockUI(el);

				var urlStr = "publishAct.do?publishSite";
				SystemProgressBar.start();
				$.ajax({
					type : 'post',
					url : urlStr,
					timeout : 1000*60*2, //超时时间设置，单位毫秒
					data : 't=' + new Date().getTime(),
					dataType : 'text',
					success : function(msg) {
//						App.unblockUI(el);
//						$(self).text(btninfo);
//						$(sitebtn).text(sitebtninfo);
						SystemProgressBar.stop();
						var obj = JSON.parse(eval(msg));
						if (obj.isSuccess) {
//							lmAlert(obj.msg);
							UIToastr.success(obj.msg);
						} else {
//							lmAlert(obj.msg);
							UIToastr.error(obj.msg);
						}
					},
					error : function() {
						SystemProgressBar.stop();
						//lmAlert("load page error, page url is " + urlStr);
					}
				});
			},
			'.publishSiteAllInfo' : function(e) {
				// 发布全站信息
				e.preventDefault();
				
				UIToastr.info("站点发布中...");
				
				var sitebtn = $(".publishsite:eq(0)");
				var self = this;
//				var btninfo=$(self).text();
//				var sitebtninfo=$(sitebtn).text();
//				$(self).text("站点发布中...");
//				$(sitebtn).text("站点发布中...");
//				var el = $(this).parents(".page-header-fixed");
//				App.blockUI(el);

				var urlStr = "publishAct.do?publishSiteAllInfo";
				SystemProgressBar.start();
				$.ajax({
					type : 'post',
					url : urlStr,
					timeout : 1000*60*2, //超时时间设置，单位毫秒
					data : 't=' + new Date().getTime(),
					dataType : 'text',
					success : function(msg) {
//						App.unblockUI(el);
//						$(self).text(btninfo);
//						$(sitebtn).text(sitebtninfo);
						SystemProgressBar.stop();
						var obj = JSON.parse(eval(msg));
						if (obj.isSuccess) {
//							lmAlert(obj.msg);
							UIToastr.success(obj.msg);
						} else {
//							lmAlert(obj.msg);
							UIToastr.error(obj.msg);
						}
					},
					error : function() {
						SystemProgressBar.stop();
						//lmAlert("load page error, page url is " + urlStr);
					}
				});
			},
			'.publisharticle' : function(e) {
				// 发布文章
				e.preventDefault();
				var self = this;
//				var btninfo=$(self).val();
				var checkboxArray = $("input[name='contentsids']:checked");
				if (checkboxArray.length == 0) {
					lmAlert("请选择您要生成的文章！");
					return;
				}
				
				UIToastr.info("稿件发布中...");
//				$(self).val("静态页生成中...");
//				var catalogid = $(self).attr("catalogid");
//				var el = $(this).parents(".page-header-fixed");
				// App.blockUI(el);
				var ids = new Array();
				for ( var i = 0; i < checkboxArray.length; i++) {
					ids[i] = parseInt($(checkboxArray[i]).val());
				}
				var catalogid = $(self).attr("catalogid");
				var urlStr = "publishAct.do?publishArticle&catalogid=" + catalogid;
				SystemProgressBar.start();
				$.ajax({
					type : 'post',
					url : urlStr,
					data : "&idsStr=" + ids,
					dataType : 'text',
					success : function(msg) {
//						App.unblockUI(el);
//						$(self).val(btninfo);
						SystemProgressBar.stop();
						var obj = JSON.parse(eval(msg));
						if (obj.isSuccess) {
//							lmAlert(obj.msg);
							UIToastr.success(obj.msg);
						} else {
//							lmAlert(obj.msg);
							UIToastr.error(obj.msg);
						}
					},
					error : function() {
						SystemProgressBar.stop();
						alert("load page error, page url is " + urlStr);
					}
				});
			},
			'.publishindex' : function(e) {
				// 发布首页
				e.preventDefault();
				var self = this;
//				var btninfo=$(self).text();
				UIToastr.info("首页发布中...");
//				$(self).text("首页发布中...");
//				var el = $(this).parents(".page-header-fixed");
//				App.blockUI(el);
				var urlStr = "publishAct.do?publishIndex";
				SystemProgressBar.start();
				$.ajax({
					type : 'post',
					url : urlStr,
					data : 't=' + new Date().getTime(),
					dataType : 'text',
					success : function(msg) {
//						App.unblockUI(el);
//						$(self).text(btninfo);
						SystemProgressBar.stop();
						var obj = JSON.parse(eval(msg));
						if (obj.isSuccess) {
//							lmAlert(obj.msg);
							UIToastr.success(obj.msg);
						} else {
//							lmAlert(obj.msg);
							UIToastr.error(obj.msg);
						}
					},
					error : function() {
						SystemProgressBar.stop();
						lmAlert("load page error, page url is " + urlStr);
					}
				});
			}
		}
	});
});

