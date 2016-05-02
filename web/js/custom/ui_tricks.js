$(function() {

    $('.modal-trigger').leanModal();

    $("#indicatorDefinition").hide();
    $("#CompositeClosedButton").hide();
    $("#graphImage").hide();
    $("#saveQuestion").attr('disabled', 'disabled');
    $("#loadIndicatorTemplateModelTable").hide();


    if( $('#associatedIndicatorsDiv').is(':empty') ) {
        $('#associatedIndicatorsDiv').append("No Associated Indicators Found");
    }

    $("#generateGraph").click(function() {
        $("#graphImage").show();
    });
    $("#addIndicator").click(function() {
        addNewIndicator();
        $("#indicatorDefinition").show();
        $('body').animate({
            scrollTop: $("#indicatorDefinition").offset().top
        }, 2000);
    });
    $("#cancelIndicator").click(function() {
        $(".chip").removeClass('chip-bg');
        $("#indicatorDefinition").hide();
        $('body').animate({
            scrollTop: $("body").offset().top
        }, 2000);
    });
    $("#compositeIndicator").click(function() {
        QuestionVisualize();
    });
    $('#toggleLoadIndicatorTemplateModelTable').click(function() {
        $('#loadIndicatorTemplateModelTable').toggle();
    });
});

function deleteIndicator(indicatorName, event) {
    var e = event;
    $(function() {
        $.ajax({
            type: "GET",
            url: "/indicators/deleteIndFromQn",
            data: {indName: $(indicatorName).closest('div').attr("id")},
            dataType: "html",
            success: function (response) {
                $("#indDeleteDialog").text(response);
                $('.indDeleteDialog').dialog('option', 'title', 'Indicator Deletion Message');
                refreshQuestionSummary();
                $("#indDeleteDialog").dialog("open");
                e.stopPropagation();
            }
        });
    });
}
function loadIndicator(indicatorName){
    $(function() {
        $(indicatorName).addClass("chip-bg").siblings().removeClass('chip-bg');
        localStorage.setItem("selectedIndicatorIndex", $(indicatorName).attr("name").split("-")[1]);
        $("#indicatorDefinition").show();
        $('body').animate({
            scrollTop: $("#indicatorDefinition").offset().top
        }, 2000);
        $.ajax({
            type: "GET",
            url: "/indicators/loadIndFromQnSetToEditor",
            data: {
                indName: $(indicatorName).attr("id")
            },
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
                    //refreshQuestionSummary();
                    updateScreenAfterLoadInd(response);
                    $("#indDeleteDialog").dialog("open");
                }
            }
        });
    });
}