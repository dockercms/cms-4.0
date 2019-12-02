/* jquery-livetip.js - jquery livetip插件 liuzhen 2014年8月14日 21:13:32
 选项
 max_width: 悬浮图片展示最大宽度
 max_heigth:悬浮图片展示最大高度
 url_Suffix:悬浮图片路径加后缀 获取缩略图
 ----------------------------------------------------------------------*/
(function($){
    $.fn.livetip = function(options){
        var livetipOptions = {
            position: 'fixed',
            backgroundColor: '#cfc',
            padding: '4px',
            border: '2px solid #9c9',
            borderRadius: '4px',
            WebkitBorderRadius: '4px',
            MozBorderRadius: '4px'
        };
        options = $.extend({
            max_width: 200,
            max_height: 200,
            url_Suffix: '.thumb.jpg'
        }, options);
        return this.each(function(){
            var $self = $(this);
            
            var max_z_index = 0;
            $self.parents().each(function(){
                if ($(this).css("zIndex") != 'auto' && $(this).css("zIndex") > max_z_index) {
                    max_z_index = $(this).css("zIndex");
                }
            });
            max_z_index += 10;
            var $showImage = $('<img/>').css({
                maxWidth: options.max_width,
                maxHeight: options.max_height
            });
            var $liveTip = $('<div></div>').hide().css(livetipOptions).css("zIndex", max_z_index).html($showImage).insertAfter($self);
            $self.bind('mouseover mouseout mousemove', function(event){
                var $this = $(this);
                var imageurl = $this.val();
                if (!imageurl || imageurl=='像素标准900*380') {
                    $liveTip.hide();
                    return;
                }
                if (event.type == 'mouseover' || event.type == 'mousemove') {
                    $liveTip.css({
                        top: event.clientY + 12,
                        left: event.clientX + 12
                    });
                };
                if (options.url_Suffix && imageurl.indexOf("http://")==-1) {
                    imageurl += options.url_Suffix;
                }
                $showImage.attr("src", imageurl);
                if (event.type == 'mouseover') {
                    $liveTip.show();
                };
                if (event.type == 'mouseout') {
                    $liveTip.hide();
                };
                            });
        });
    }
})(jQuery);
