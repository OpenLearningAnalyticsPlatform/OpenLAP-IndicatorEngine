$(document).ready(function() {
    $.validator.addMethod("validateQuestion",function(val, elem){
        var enteredQuestionName = $('#questionNaming').val();
        var errorMsg = '', $valid = false;
        $.ajax({
            async: false,
            url:'/engine/validateQuestionName?name='+enteredQuestionName,
            type:"GET",
            success:function(response){
                if(response == "server"){
                    errorMsg = "Entered question already exists in OpenLAP.";
                    $valid = false;
                }
                else if(response == "invalid"){
                    errorMsg = "Enter question name.";
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
        var enteredIndicatorName = elem.value;

        var errorMsg = '', $valid = false;
        var indicatorIndexUrlParam = (localStorage.getItem("selectedIndicatorIdentifier") === null) ? "" : '&index=' + localStorage.getItem("selectedIndicatorIdentifier");
        $.ajax({
            async: false,
            url:'/engine/validateIndicatorName?name=' + enteredIndicatorName + indicatorIndexUrlParam,
            type:"GET",
            success:function(response){
                if(response == "server"){
                    errorMsg = "Entered indicator name already exists in OpenLAP.";
                    $valid = false;
                }
                else if(response == "session"){
                    errorMsg = "Entered indicator name already exists in current Question.";
                    $valid = false;
                }
                else if(response == "invalid"){
                    errorMsg = "Enter indicator name.";
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

    $.validator.addMethod("hasNoOptionLeft",function(val, elem){
        var $valid = true;
        var options =  elem.options;
        for (i = 0; i < options.length; i++) {
            if (options[i].hasAttribute("is-required")) {
                var isRequired = options[i].getAttribute("is-required");
                if(isRequired == "true")
                    $valid = false;
            }
        }

        $.validator.messages["hasNoOptionLeft"] = "All required inputs must be selected";
        return $valid;
    },'');

    $.validator.addMethod("singleDatasetRemaining",function(val, elem){
        if($(elem).find(".valign-wrapper").length !== 1) {
            $.validator.messages["singleDatasetRemaining"] = "Please combine all first level analysis.";
            return false;
        }
        return true;
    },'');

    $('#GQSelectionForm').validate({
        ignore: false,
        onkeyup: false,
        ignoreTitle: true,
        rules: {
            "questionNaming": {
                required: true,
                minlength: 6,
                validateQuestion: true
            },
            GoalSelection: {
                required: true
            }
        },
        messages: {
            GoalSelection: {
                required: "Select analytics goal."
            },
            "questionNaming": {
                required: "Question name is required.",
                minlength: "Question name should be at least 6 characters."
            }
        },
        errorClass: 'invalid',
        errorPlacement: function (error, element) {
            if(element.is("select"))
                element.prev("label").attr("data-error", error.contents().text());
            else
                element.next("label").attr("data-error", error.contents().text());
        },
        onfocusout: function(element, event) {
            if($(element).valid())
                $('#preview_msg').find("#"+element.id+"_msg").remove();
        }
    });

    $('#SimpleForm').validate({
        ignore: false,
        onkeyup: false,
        ignoreTitle: true,
        rules: {
            indicatorNaming: {
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
            EngineSelect: {
                required: true
            },
            selectedChartType: {
                required: true
            },
            inputForMethods: {
                hasNoOptionLeft: true
            },
            inputForVisualizer: {
                hasNoOptionLeft: true
            }
        },
        messages: {
            indicatorNaming: {
                required: "Indicator name is required.",
                minlength: "Indicator name should be at least 6 characters."
            },
            selectedSource: {
                required: "Select sources for dataset."
            },
            selectedPlatform: {
                required: "Select platforms for dataset."
            },
            selectedAction: {
                required: "Select actions for dataset."
            },
            selectedMinor: {
                required: "Select categories for dataset."
            },
            analyticsMethod: {
                required: "Select analytics method."
            },
            EngineSelect: {
                required: "Select visualization library."
            },
            selectedChartType: {
                required: "Select visualization type."
            }
        },
        errorClass: 'invalid',
        errorPlacement: function (error, element) {
            if(element.is("select"))
                element.prev("label").attr("data-error", error.contents().text());
            else
                element.next("label").attr("data-error", error.contents().text());
        },
        onfocusout: function(element, event) {
            if($(element).valid())
                $('#preview_msg').find("#"+element.id+"_msg").remove();
        }
    });

    $('#analyticsMethodForm').validate({
        ignore: false,
        onkeyup: false,
        rules: {
            name: {
                required: true,
                minlength: 6
            },
            desc: {
                required: true,
                minlength: 6
            },
            class: {
                required: true,
                minlength: 6
            },
            file: {
                required: true
            },
            fileName: {
                required: true,
                extension: "jar"
            }
        },
        messages: {
        },
        errorClass: 'invalid',
        errorPlacement: function (error, element) {
            element.next("label").attr("data-error", error.contents().text());
        }
    });

    $('#visualizationForm').validate({
        ignore: false,
        onkeyup: false,
        rules: {
            name: {
                required: true,
                minlength: 6
            },
            desc: {
                required: true,
                minlength: 6
            },
            methodName: {
                required: true,
                minlength: 6
            },
            methodClass: {
                required: true,
                minlength: 6
            },
            dataTransformerName: {
                required: true,
                minlength: 6
            },
            dataTransformerClass: {
                required: true,
                minlength: 6
            },
            file: {
                required: true
            },
            fileName: {
                required: true,
                extension: "jar"
            }
        },
        messages: {
        },
        errorClass: 'invalid',
        errorPlacement: function (error, element) {
            element.next("label").attr("data-error", error.contents().text());
        }
    });

/*    $('#goalRequestTemplateModel').validate({
        ignore: false,
        onkeyup: false,
        rules: {
            "new-analytics-goal-name": {
                required: true
            },
            "new-analytics-goal-desc": {
                required: true,
            }
        },
        messages: {
        },
        errorClass: 'invalid',
        errorPlacement: function (error, element) {
            element.next("label").attr("data-error", error.contents().text());
        }
    });*/
});