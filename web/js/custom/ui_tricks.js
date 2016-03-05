$(function() {
    $("#indicatorDefinition").hide();
    $("#addIndicator").click(function() {
        $("#indicatorDefinition").show();
        $('body').animate({
            scrollTop: $("#indicatorDefinition").offset().top
        }, 2000);
    });
    $("#cancelIndicator").click(function() {
        $("#indicatorDefinition").hide();
        $('body').animate({
            scrollTop: $("body").offset().top
        }, 2000);
    });
});