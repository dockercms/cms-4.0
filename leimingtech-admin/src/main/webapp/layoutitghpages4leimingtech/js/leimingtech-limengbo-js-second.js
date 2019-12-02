/*
 * <script type="text/javascript" src="js/leimingtech-limengbo-js.js"></script>
<script type="text/javascript" src="js/scripts.js"></script>
<script type="text/javascript" src="js/leimingtech-limengbo-js-second.js"></script>
 * */
$(function(){//由于执行顺序的要求，才编写此代码
	$.fn.coffee = function(obj){
		  for(var eName in obj)
		    for(var selector in obj[eName])
		      $(this).on(eName, selector, obj[eName][selector]);
		};
	var leimingtech_limengbo_second={
			init:function(){
				var _this=leimingtech_limengbo_second;
				_this.bind_edit_button_click();
				_this.bind_edit_select_change();
				_this.bind_close_edit_button_click();
				_this.bind_click_slider();
				_this.bind_click_radio();
				_this.bind_change_select();
				_this.bind_color_pick();
				_this.bind_click_delete();
			},
			bind_edit_button_click:function(){//重新绑定单击事件，刷新当前页面后，【编辑样式】窗口中的元素下拉框消失了，为了解决这个问题，编写了这个函数。
				$('button[leimingtech-limenbo-content-model-number-toggle]').click(function(){//单击【编辑样式】按钮后，加载当前内容模型中，可编辑样式的元素，到 【编辑样式】窗口的下拉列表中。
					var $this=$(this);
					var value=$this.attr('leimingtech-limenbo-content-model-number-toggle');
					var $select=$('<select style="position:absolute;top:50%;margin-top:-14px;" name="'+value+'"></select>');
					$('[leimingtech-limenbo-content-model-number="'+value+'"]')
					.find('[leimingtech-limengbo-edit-style]')
					.each(function(){
						var $option=$('<option value="'+value+'">'+($(this).attr('leimingtech-limengbo-edit-style'))+'</option>');
						if(!$option.is(function(){
							var $objects=$select.find('option');
							for(var i in $objects){
								if($objects.eq(i).text()==$(this).text()){
									return true;
								}
							}
						})){
							$select.append($option);
						}
					});
					$('#leimingtech-limengbo-edit-element').empty().append($select);
				});
			},
			bind_edit_select_change:function(){//重新绑定下拉列表change事件，【刷新当前页面导致，change 事件丢失】
				$('#editorModalStyle').find('.hero-unit').find('select').change(function(){
					var $this=$(this);
					var element_number=$('#leimingtech-limengbo-edit-element').find('select').eq(0).attr('name');
					var child_selected_index=$('#leimingtech-limengbo-edit-element').find('select').eq(0).get(0).selectedIndex;
					var child_text=$('#leimingtech-limengbo-edit-element').find('select').eq(0).find('option').eq(child_selected_index).text();
					
					$('[leimingtech-limenbo-content-model-number="'+element_number+'"]')
					.find('[leimingtech-limengbo-edit-style="'+child_text+'"]')
					.css($this.attr('lei-name'),$this.val());
					
					//把样式修改更新到模板文件中【iframe】
					var $iframe_document=$('#iframe_template_file').contents();
					$iframe_document.find('[leimingtech-limenbo-content-model-number="'+element_number+'"]')
					.find('[leimingtech-limengbo-edit-style="'+child_text+'"]')
					.css($this.attr('lei-name'),$this.val());
					//为【编辑样式】窗口中，指定的select 元素添加单击事件，以改变指定元素的样式
				});
			},
			bind_close_edit_button_click:function(){//重新绑定透明遮罩，当单击【编辑样式】按钮后，显示透明遮罩
				$('button[leimingtech-limenbo-content-model-number-toggle]').click(function(){
					$('#leimingtech-limengbo-shade').show();
				});
			},
			bind_click_slider:function(){//单击slider滚动条时，向对应的文本框中添加数据，并模型blur 事件
				$('.leimingtech-limengbo-slider').click(function(){
					var $this=$(this);
					var min=$this.attr('min')-0;
					var max=$this.attr('max')-0;
					var reality_length=max-min+1;
				});
			},
			bind_click_radio:function(){
				$('#editorModalStyle').find('.hero-unit').find(':radio').click(function(e){
					var $this=$(this);
					var element_number=$('#leimingtech-limengbo-edit-element').find('select').eq(0).attr('name');
					var child_selected_index=$('#leimingtech-limengbo-edit-element').find('select').eq(0).get(0).selectedIndex;
					var child_text=$('#leimingtech-limengbo-edit-element').find('select').eq(0).find('option').eq(child_selected_index).text();
					
					$('[leimingtech-limenbo-content-model-number="'+element_number+'"]')
					.find('[leimingtech-limengbo-edit-style="'+child_text+'"]').andSelf()
					.css($this.attr('lei-name'),$this.attr('lei-value'));
					
					//把样式修改更新到模板文件中【iframe】
					var $iframe_document=$('#iframe_template_file').contents();
					$iframe_document.find('[leimingtech-limenbo-content-model-number="'+element_number+'"]')
					.find('[leimingtech-limengbo-edit-style="'+child_text+'"]').andSelf()
					.css($this.attr('lei-name'),$this.attr('lei-value'));
				});
			},
			bind_change_select:function(){
				$('select.leimingtech-limengbo-border').unbind('change');
				$('select.leimingtech-limengbo-border').change(function(){
					var $this=$(this);
					var tig='-';
					if($this.val()==''){
						tig='';
					}
					$this.parent().parent().parent().find('[lei-name]').each(function(){
						var lei_name='border-'+$this.val()+tig+$(this).attr('tig');
						console.log(lei_name);
						$(this).attr('lei-name',lei_name);
					});
				});
			},
			remove_repetition_element:function($objects,fn){//去除某种条件相同的元素，只保留第一个
				//已废弃。
			},
			bind_color_pick:function(){//把颜色文本框与颜色选择器绑定在一起。
				$('[lei-name*="color"]').pickAColor({
					  showSpectrum            : true,
						showSavedColors         : false,
						saveColorsPerElement    : true,
						fadeMenuToggle          : true,
						showAdvanced			: true,
						showHexInput            : true,
						showBasicColors         : true,
						allowBlank              : true
					}).on('change',function(){
						var $this=$(this);
						var element_number=$('#leimingtech-limengbo-edit-element').find('select').eq(0).attr('name');
						var child_selected_index=$('#leimingtech-limengbo-edit-element').find('select').eq(0).get(0).selectedIndex;
						var child_text=$('#leimingtech-limengbo-edit-element').find('select').eq(0).find('option').eq(child_selected_index).text();
						$('[leimingtech-limenbo-content-model-number="'+element_number+'"]')
						.find('[leimingtech-limengbo-edit-style="'+child_text+'"]')
						.css($this.attr('lei-name'),'#'+$this.val());
						
						//把样式修改更新到模板文件中【iframe】
						var $iframe_document=$('#iframe_template_file').contents();
						$iframe_document.find('[leimingtech-limenbo-content-model-number="'+element_number+'"]')
						.find('[leimingtech-limengbo-edit-style="'+child_text+'"]')
						.css($this.attr('lei-name'),'#'+$this.val());
					});
			},
			bind_click_delete:function(){//点击删除按钮，删除模板中相应的内容模型
				$(document).coffee({
					click:{
						'.remove':function(){
							var $this=$(this);
							var value=$this.attr('leimingtech-limenbo-remove-toggle');
							if(value)
							$('#iframe_template_file').contents().find('[leimingtech-limenbo-content-model-number="'+value+'"]').remove();
							
							//remove row 
							var value_row=$this.attr('leimingtech-limenbo-row-toggle');
							if(value_row)
							$('#iframe_template_file').contents().find('[leimingtech-limenbo-row="'+value_row+'"]').remove();
						}
					},
					mouseup:{
//						'.drag':function(){
////							console.log("mouseup:"+new Date());
////							console.log(this);
//							
//							var $this=$(this);
//							var content_model_number=$this.attr('leimingtech-limenbo-drag-toggle');
//							$('div[class*="span"]').each(function(){
//								var $other=$(this);
//								$('[]')
//							});
//						}
					}
				});
			},
			bind_draggable_button:function(){// 当内容模型块被拖拽到其他地方时，更改模板中相应的结构
				/*$('.box').draggable({
					handle: ".drag",
					start:function(e,t){
						
					},
					drag:function(e,t){
						
					},
					stop:function(e,t){
						console.log('stop:'+new Date());
					}
				});*/
			},
			bind_contentmodel_and_button:function(){
				
			},
			disabed_layout_nested:function(){//禁止布局嵌套
				
			}
	};
	leimingtech_limengbo_second.init();
});