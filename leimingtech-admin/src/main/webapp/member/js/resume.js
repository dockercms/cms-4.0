$(document).ready(
function()
{
  $("#rightcontent dt").click(
  function()
  {
    $("#rightcontent dl").removeClass("current");
    $(this).parent().addClass("current");
  }
  );
}
);