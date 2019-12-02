$(function(){
	var leimingtech_limengbo={
			init:function(){
				$.ajax({//为页面添加，内容模型样式编辑窗口
						url:'/layoutitghpages4leimingtech/leimingtech-limengbo-edit-style2.html',
						success:function(response,status,xhr){
							$('body').append($(response));
							$('.hero-unit').css({padding:'5px'});
						},
						async:false
				});
				leimingtech_limengbo_font.initFontData();
				leimingtech_limengbo.load_content_model();
				leimingtech_limengbo.init_slider();
				leimingtech_limengbo.bind_text_blur();
				leimingtech_limengbo.bind_slider_click();
			},
			hidden_edit_button:function(){//隐藏【编辑】按钮，【/lmcms-framework/WebRoot/layoutitghpages4leimingtech/index.html】
				$('button:contains("编辑")').hide();
			},
			add_editstyle_button:function(){//在【编辑】按钮的前面添加【编辑样式】按钮
				var $editstyle_button=$('<button type="button" class="btn btn-mini" data-target="#editorModalStyle" role="button" data-toggle="modal" leimingtech-limenbo-content-model-number-toggle="">编辑样式</button>');
				$editstyle_button.insertBefore($('button:contains("编辑"):gt(1)'))
				.click(function(){
					$('#leimingtech-limengbo-shade').show();
				});
				$('.close,.modal-footer .btn:contains("关闭"),#savecontent').click(function(){
					$('#leimingtech-limengbo-shade').hide();
				});
			},
			add_element_toedit:function($object){//加载选择的内容模型中，需要编辑的元素
				var $select=$('<select></select>');
				$object.find('[leimingtech-limengbo-edit-style]').each(function(){
					var $this=$(this);
					$select.append('<option>'+$this.attr('leimingtech-limengbo-edit-style')+'</option>');//待编辑样式的元素，对应的文本，文本在当前内容模型中应该是唯一的
				});
				$('#leimingtech-limengbo-edit-element').append($select);
			},
			load_content_model:function(){//加载内容模型片段
				var urls=['/layoutitghpages4leimingtech/contentmodel/articlelist-static-1.html',
				          '/layoutitghpages4leimingtech/contentmodel/textlist-static-1.html',
				          '/layoutitghpages4leimingtech/contentmodel/imageslist-static-1.html'];
				for(var i in urls){
					$.ajax({
						url:urls[i],
						success:function(response,status,xhr){
							$(response).appendTo('#leimingtech-limengbo-content-model');
						},
						async:false
					});
				}
			},
			test:function($object){//为模块增加唯一标识符 例如：number-1,已经被废除的函数
				alert('number-1');
			},
			bind_click_select:function(){//为【编辑样式】窗口中，指定的select 元素添加单击事件，以改变指定元素的样式
				
			},
			init_slider:function(){
				//$('.leimingtech-limengbo-slider').slider();
				//1.
				var $child=$('<div style="position:absolute;left:2px;top:-3px;background:#ddd;border:1px solid black;border-radius:4px;width:20px;height:20px;cursor:pointer;"></div>');
				$('.leimingtech-limengbo-slider').css({
					'position':'relative',
					'height':'16px',
					'border':'solid 1px #aaa',
					'border-radius':'4px'
				}).append($child).find('div').each(function(){
					leimingtech_limengbo.bind_slider($(this));
				});
				
				//2.为 $child 添加拖拽行为
				//leimingtech_limengbo.bind_slider($child);
				
			},
			bind_slider:function($object){//把元素变为可拖拽的
				var $parent=$object.parent();
				var X_mouse=0;
				var X_move=0;
				$object.mousedown(function(e){
					$parent.attr('move',true);
					X_mouse=e.pageX;
					X_move=$object.position().left;
					$(this).css({'background-color':'white'});
				});
				$(document).mousemove(function(e){
					if($parent.attr('move')=='true'){
						var parent_width=$parent.outerWidth();
						parent_width=parent_width-$object.outerWidth();
						var left=parent_width-4;
						var mouse_move=e.pageX-X_mouse;
						var x =X_move+mouse_move;
						if(x<2){
							x=2;
						}else if(x>left){
							x=left;
						}
						$object.css({left:x+'px'});	
						
						
						//step2
						var $this=$(e.target).parent();
						var min=$this.attr('min')-0;
						var max=$this.attr('max')-0;
						var reality_length=max-min+1;
						var px_length=left-2+1;
						var reality_value=reality_length/px_length*(x-2)+min;
						reality_value=parseInt(reality_value);
						
						//step3
						var data_target=$this.attr('data-target');
						console.log(data_target+':'+$('[data="'+data_target+'"]').size());
						$('[data="'+data_target+'"]').val(reality_value+'px').blur();
					}
				});
				$(document).mouseup(function(){
					$object.css({'background-color':'#ddd'});
					$parent.attr('move',false);
				});
			},
			bind_text_blur:function(){//为样式编辑窗口的文本框添加焦点失去事件。
				$('.hero-unit :text').bind('blur',function(){
					var $this=$(this);
					var element_number=$('#leimingtech-limengbo-edit-element').find('select').eq(0).attr('name');
					var child_selected_index=$('#leimingtech-limengbo-edit-element').find('select').eq(0).get(0).selectedIndex;
					var child_text=$('#leimingtech-limengbo-edit-element').find('select').eq(0).find('option').eq(child_selected_index).text();
					var style_value=($this.attr('prefix')?$this.attr('prefix'):'')+$this.val()+($this.attr('suffix')?$this.attr('suffix'):'');
					$('[leimingtech-limenbo-content-model-number="'+element_number+'"]')
					.find('[leimingtech-limengbo-edit-style="'+child_text+'"]').andSelf()
					.css($this.attr('lei-name'),style_value);
					
					//把样式修改更新到模板文件中【iframe】
					var $iframe_document=$('#iframe_template_file').contents();
					$iframe_document.find('[leimingtech-limenbo-content-model-number="'+element_number+'"]').andSelf()
					.find('[leimingtech-limengbo-edit-style="'+child_text+'"]')
					.css($this.attr('lei-name'),style_value);
				});
			},
			bind_slider_click:function(){//为 slider 条，增加单击事件。
				$('.leimingtech-limengbo-slider').click(function(e){
					$this=$(this);
					var parent_width=$this.outerWidth();
					var X_mouse=e.pageX;
					var $child=$this.find('div').eq(0);
					var X_slider=$child.offset().left;
					parent_width=parent_width-$child.outerWidth();
					var left=parent_width-4;
					//console.log(new Date());
					var x=X_mouse-X_slider+$child.position().left;
					if(x<2){
						x=2;
					}else if(x>left){
						x=left;
					}
					$child.css({left:x+'px'});
					
					//step2
					var $this=$(this);
					var min=$this.attr('min')-0;
					var max=$this.attr('max')-0;
					var reality_length=max-min+1;
					var px_length=left-2+1;
					var reality_value=reality_length/px_length*(x-2)+min;
					reality_value=parseInt(reality_value);
					
					//step3
					var data_target=$this.attr('data-target');
					console.log(data_target+':'+$('[data="'+data_target+'"]').size());
					$('[data="'+data_target+'"]').val(reality_value+'px').blur();
					
				});
			}
	};
	var leimingtech_limengbo_font={
			fontSize:[12,14,16,18,24,36],
			fontFamily:['宋体','楷体','黑体','华文行楷','幼圆','华文琥珀','Arial',
			            '隶书','微软雅黑','Helvetica','Tahoma','Verdana',
			            'Lucida Grande','Times New Roman','Georgia'],
			initFontData:function(){//把为【样式编辑窗口】，初始化字体数据
				//step1
				var $select=$('<select lei-name="font-size"></select');
				for(var i in leimingtech_limengbo_font.fontSize){
					$select.append('<option value="'+(leimingtech_limengbo_font.fontSize[i])+'px">'+(leimingtech_limengbo_font.fontSize[i])+'</option>');
				}
				$('[leimingtech-limengbo-style-type="fontSize"]').append($select);
				
				//step2
				$select=$('<select lei-name="font-family"></select');
				for(var i in leimingtech_limengbo_font.fontFamily){
					$select.append('<option value="'+(leimingtech_limengbo_font.fontFamily[i])+'">'+(leimingtech_limengbo_font.fontFamily[i])+'</option>');
				}
				$('[leimingtech-limengbo-style-type="fontFamily"]').append($select);
			}
	};
	//leimingtech_limengbo.hidden_edit_button();
	leimingtech_limengbo.init();
	$('#editorModalStyle').draggable({
		handle:'.modal-header'
	});
//	$('#editorModalStyle .modal-body').bind('mousedown mousemove mouseup',function(e){//消除拖拽的影响。
//		//return false;
//		e.stopPropagation();
//	});
	//step3,
	if($('button[leimingtech-limenbo-content-model-number-toggle]').size()<=0){
		leimingtech_limengbo.add_editstyle_button();
	}else{
		$('button[leimingtech-limenbo-content-model-number-toggle]').click(function(){
			$('#leimingtech-limengbo-shade').show();
		});
		$('.close,.modal-footer .btn:contains("关闭")').click(function(){
			$('#leimingtech-limengbo-shade').hide();
		});
	}
	
	var leimingtech_limengbo_remote={
			flag:false,//当为true 时开始操作 iframe 中的html元素
			init:function(){
				var _this=leimingtech_limengbo_remote;
				_this.load_template_file_from_server();
				_this.upload_template_file();
			},
			load_template_file_from_server:function(){//【t=sepcial-topic】:模板文件名，根据该参数从服务器端加载模板文件，到浏览器端
				var _this=leimingtech_limengbo_remote;
				var param_t=location.search;
				param_t=_this.convert_param_toObject(param_t);
				if(param_t.t){//当传递模板名称时，执行下面的请求
					//step1,加载3个文件
					var $content_model_container; //要编辑的模板文件
					var $edit_element;  //加载edit_button.html文件，添加辅助的html元素
					var $edit_layout;	//加载edit_layout.html，为内容模型添加外围的辅助html元素
					$.ajax({
						url:'/special_topic_templateAction.do?te&t='+param_t.t,
						success:function(response,status,xhr){
							$content_model_container=$(response).filter(function(){
								var str=$(this).attr('class');
								return str&&str.indexOf("container")>=0;
							});
						},
						async:false
					});
					$.ajax({
						url:'/layoutitghpages4leimingtech/edit_button.html',
						success:function(response,status,xhr){
							$edit_element=$(response);
						},
						async:false
					});
					$.ajax({
						url:'/layoutitghpages4leimingtech/edit_layout.html',
						success:function(response,status,xhr){
							$edit_layout=$(response);
						},
						async:false
					});
					//step2,添加相关的class和用于编辑的html元素
					$content_model_container.find('.row').addClass('row-fluid clearfix').removeClass('row');
					$content_model_container.find('[class^="span"]').addClass('column ui-sortable');
					//step3,把edit_button.html文件中的html元素包裹在具体内容的周围
					$content_model_container.find('[leimingtech-limenbo-content-model-number]').each(function(){
						var $this=$(this);
						var $edit_element_clone=$edit_element.clone(true);
						$edit_element_clone.find('.view').append($this.clone(true)).
						end().insertBefore($this);
						$edit_element_clone.find('[leimingtech-limenbo-content-model-number-toggle]').eq(0)
						.attr('leimingtech-limenbo-content-model-number-toggle',$this.attr('leimingtech-limenbo-content-model-number'));
						$edit_element_clone.find('.remove').attr('leimingtech-limenbo-remove-toggle',$this.attr('leimingtech-limenbo-content-model-number'))
						.end().find('.drag').attr('leimingtech-limenbo-drag-toggle',$this.attr('leimingtech-limenbo-content-model-number'));
						$this.remove();
						//step2-2
					});
					//step4,把edit_layout.html文件中的 html 元素包裹在行（row）的周围
					$content_model_container.find('[class*="row"]').each(function(){
						var $this=$(this);
						var $edit_layout_clone=$edit_layout.clone(true);
						$edit_layout_clone.find('.remove').attr('leimingtech-limenbo-row-toggle',$this.attr('leimingtech-limenbo-row'));
						$edit_layout_clone.find('.view').append($this.clone(true))
						.end().insertBefore($this);
						$this.remove();
					});
					$('#leimingtech-limengbo-edit-workspace').empty().append($content_model_container.html());
					
					//加载原生的模板文件到浏览器
					leimingtech_limengbo_remote.load_native_template_file(param_t.t);
				}
			},
			dowith_template_file:function(){//初始化待编辑的模板文件，为其添加各种操作按钮
				//已废弃的函数
			},
			upload_template_file:function(){//上传编辑后的模板文件。
				$('#leimingtech-limengbo-save-button').bind('click',function(){
					leimingtech_limengbo_remote.save_changeto_iframe();
					var template_content=$('#iframe_template_file').contents().find('html').get(0).outerHTML;
					$textarea=$('<textarea>'+template_content+'</textarea>');
					template_content='<!DOCTYPE html>\n'+$textarea.val();
					var template_name='';
					var param_t=location.search;
					param_t=leimingtech_limengbo_remote.convert_param_toObject(param_t);
					console.log(param_t.t);
					if(!param_t.t){
						template_name='special_topic3';
					}
					$.ajax({
						url:'/special_topic_templateAction.do?save',
						type:'post',
						data:{
							content:template_content,
								f:template_name
						},
						success:function(response,status,xhr){
							console.log(response);
							leimingtech_limengbo_remote.add_a_link(response);
						}
					});
				});
			},
			convert_param_toObject:function(location_search){//把 location.search转变成json对象
				var result=location_search.substring(1);
				var array=result.split('&');
				var res="";
				for(var i in array){
					var arr=array[i].split('=');
					res+='"'+arr[0]+'":'+'"'+arr[1]+'",';
				}
				res=res.substring(0, res.length-1);
				res='{'+res+'}';
				res=eval('('+res+')');
				return res;
			},
			load_native_template_file:function(template_name){
				$('#iframe_template_file').remove();
				var $iframe=$('<iframe id="iframe_template_file" style="display:none;" src="/special_topic_templateAction.do?file&f='+template_name+'"></iframe>');
				$('body').append($iframe);
				var set_interval=setInterval(function(){
//					console.log($('#iframe_template_file').contents().find('body[ready="ok"]').size());
					if($('#iframe_template_file').contents().find('body[ready="ok"]').size()>0){
						clearInterval(set_interval);
						//step2-start
						var $document_iframe=$('#iframe_template_file').contents();
						$('[leimingtech-limenbo-content-model-number]').each(function(){
							var $this=$(this);
							var $all=$this.siblings().andSelf();
							console.log(("size:")+$all.size());
							if($all.size()<=1){
								$other_iframe=$document_iframe.find('[leimingtech-limenbo-content-model-number="'+($this.attr('leimingtech-limenbo-content-model-number'))+'"]');
								$other_iframe.css({display:'none'});
								$this.after($other_iframe);
							}
						});
						//step2-end
					}
				},100);
			},
			save_changeto_iframe:function(){//单击保存按钮后，将样式更改保存到iframe中
//				console.log(new Date());
				var $clone_container=$('#leimingtech-limengbo-edit-workspace').clone();
				
				
				$clone_container.find('.lyrow').each(function(){
					var $this=$(this);
//					console.log($this.find('> .view').size());
					$this.find('.row-fluid').eq(0).insertAfter($this);
					$this.remove();
				});
				//step1,更换row column 上的class属性，及其他
				
				$clone_container.find('[class*="row"]').removeClass().addClass('row');
				$clone_container.find('[class*="span"]').removeClass('column ui-sortable');
				
				$clone_container.find('[class*="span"]').each(function(){
					var $this=$(this);
					var $this_clone=$this.clone();
					$this.empty();
					$this_clone.find('.view').each(function(){
						var $this_child=$(this);
						var $last=$this_child.find('[leimingtech-limenbo-content-model-number]').last();
						$last.show();
						$this.append($last);
					});
				});
				$('#iframe_template_file').contents().find('#leimingtech').html($clone_container.html());
				$('#iframe_template_file').contents().find('body[ready="ok"]').attr('ready','');
			},
			add_a_link:function(url){//模板保存成功后，在对话框中显示其访问URL
				$('#shareModal .modal-body a').remove();
				$('#shareModal .modal-body').append('<a target="_blank" href='+url+'>'+url+'</a>');
			}
	};
	leimingtech_limengbo_remote.init();
});