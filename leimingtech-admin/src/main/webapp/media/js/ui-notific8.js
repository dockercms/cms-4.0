var UINotific8 = function () {

    return {
        show: function (msg,title,theme) {
        	if(!theme){
        		theme="teal";
        	}
        	setTimeout(function (){
        		var settings = {
        				theme: theme,//teal、amethyst、ruby、tangerine、lemon、lime、ebony、smoke
        				sticky: true,//true、false
        				horizontalEdge: "top",//top、bottom
        				verticalEdge: "right",//right、left
        				life:"1000",//sticky为false时生效，代表多久会自动消失
        				heading:$.trim(title)
        		}
        		$.notific8('zindex', 11500);
        		$.notific8($.trim(msg), settings);
        	},10);
        }
    };

}();