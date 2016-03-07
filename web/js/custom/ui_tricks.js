$(function() {
    $("#indicatorDefinition").hide();
    $("#addIndicator").click(function() {
        addNewIndicator();
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

function loadIndicator(indicatorName){
    $(function() {
        $.ajax({
            type: "GET",
            url: "/indicators/loadIndFromQnSetToEditor",
            data: {indName: $(indicatorName).attr("id")},
            dataType: "json",
            success: function (response) {
                if (response == null) {
                    $("#indDeleteDialog").html("The selected Indicator cannot be loaded into the editor as it a composite Indicator or a NULL value.");
                    refreshQuestionSummary();
                    $('.indDeleteDialog').dialog('option', 'title', 'Indicator Load Message');
                    $("#indDeleteDialog").dialog("open");
                }
                else {
                    $("#indDeleteDialog").html("The selected Indicator has been successfully loaded into the Editor.<br/> Please note that it has been <strong>deleted </strong>" +
                        "from the Question. So after making changes, please save it again if you want it to be associated with the Question. <br/>" +
                        "Also note that you have to select again Platform and Action to populate the List of Categories.");
                    $('.indDeleteDialog').dialog('option', 'title', 'Indicator Load Message');
                    refreshQuestionSummary();
                    updateScreenAfterLoadInd(response);
                    $("#indDeleteDialog").dialog("open");
                }
            }
        });
    });
}