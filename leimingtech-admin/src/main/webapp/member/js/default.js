$(document).ready(
function()
{
  $("#leftcontent dt").hover(function(){$(this).addClass("mouseover");},function(){$(this).removeClass("mouseover");});
  $("#leftcontent dt").click(function(){$("#leftcontent dl").removeClass("current");$(this).parent().addClass("current");});
  $("#hidemenu").click(
  function()
  {
    if($("#leftcontent").attr("class").indexOf("bg") >= 0)
    {
     $("#leftcontent").css("width","15px");
     $("#rightcontent").css("margin-left","15px");
     $("#leftcontent").removeClass("bg");
     $("#miancontent").addClass("maincontent_");
     $("#hidemenu").addClass("hidemenu_");
     w = -170;
    }
    else
    {
     $("#leftcontent").css("width","180px");
     $("#rightcontent").css("margin-left","180px");
     $("#miancontent").removeClass("maincontent_");
     $("#leftcontent").addClass("bg");
     $("#hidemenu").removeClass("hidemenu_");
     w = 0;
    }
  }
  );
//  $("#rightbody .change").mouseover(
//  function()
//  {
//    $("#tradelink").css({left:$(this).offset().left - w -225,top:$(this).offset().top-115});
//    $("#tradelink").show();
//  }
//  );
//  $("#rightbody .change").mouseout(
//  function()
//  {
//    $("#tradelink").hide();
//  }
//  );
//  $("#tradelink").mouseover(
//  function()
//  {
//    $(this).show();
//  }
//  );
//   $("#tradelink").mouseout(
//  function()
//  {
//    $(this).hide();
//  }
//  );
}
);
var h;
var w = 0;