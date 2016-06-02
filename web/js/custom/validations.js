$(document).ready(function() {
    $.validator.addMethod("validateQuestion",function(val, elem){
        var enteredQuestionName = $('#questionNaming').val();
        var errorMsg = '', $valid = false;
        $.ajax({
            async: false,
            url:'/indicators/validateQName?qname='+enteredQuestionName,
            type:"GET",
            data : {},
            success:function(response){
                if(response == "exists"){
                    errorMsg = "Question name already exists. Duplicate names are not allowed";
                    $valid = false;
                }
                else if(response == "short"){
                    errorMsg = "Question Name must have at least 6 characters";
                    $valid = false;
                }
                else if(response == "null"){
                    errorMsg = "Question Name is a required field";
                    $valid = false;
                }
                else{
                    $valid = true;
                }
            },
            error: function(jqXHR, exception) {
                console.log("Unable to fetch question name validation: " + exception);
            }
        });
        $.validator.messages["validateQuestion"] = errorMsg;
        return $valid;
    },'');

    $.validator.addMethod("validateIndicator",function(val, elem){
        var enteredIndicatorName = $('#indicatorNaming').val();
        var errorMsg = '', $valid = false;
        var indicatorIndexUrlParam = (localStorage.getItem("selectedIndicatorIndex") === null) ? "" : '&indicatorIndex=' + localStorage.getItem("selectedIndicatorIndex");
        $.ajax({
            async: false,
            url:'/indicators/validateIndName?indname=' + enteredIndicatorName + indicatorIndexUrlParam,
            type:"GET",
            data : {},
            success:function(response){
                if(response == "exists"){
                    errorMsg = "Indicator name already exists. Duplicate names are not allowed";
                    $valid = false;
                }
                else if(response == "short"){
                    errorMsg = "Indicator Name must have at least 6 characters";
                    $valid = false;
                }
                else if(response == "null"){
                    errorMsg = "Indicator Name is a required field";
                    $valid = false;
                }
                else{
                    $valid = true;
                }
            },
            error: function(jqXHR, exception) {
                console.log("Unable to fetch indicator name validation: " + exception);
            }
        });
        $.validator.messages["validateIndicator"] = errorMsg;
        return $valid;
    },'');


    $('#sessionSelection').validate({
        ignore: false,
        onkeyup: false,
        rules: {
            "questionsContainer.questionName": {
                required: true,
                minlength: 6,
                validateQuestion: true
            },
            GoalSelection: {
                required: true
            },
            indicatorName: {
                required: true,
                minlength: 6,
                validateIndicator: true
            },
            selectedSource: {
                required: true
            },
            selectedPlatform: {
                required: true
            },
            selectedAction: {
                required: true
            },
            selectedMinor: {
                required: true
            },
            analyticsMethod: {
                required: true
            },
            selectedChartEngine: {
                required: true
            },
            selectedChartType: {
                required: true
            }
        },
        messages: {
        },
        errorClass: 'invalid',
        errorPlacement: function (error, element) {
            element.next("label").attr("data-error", error.contents().text());
        }
    });
});