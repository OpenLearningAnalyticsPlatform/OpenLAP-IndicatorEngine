$(function() {



    $('.datepicker').pickadate({
        selectMonths: true, // Creates a dropdown to control month
        selectYears: 15 // Creates a dropdown of 15 years to control year
    });

    populateAnalyticsGoal();
    populateAnalyticsMethods();
    populateVisualizationFrameworks();
    toggleVisibilityMethodMappingTable();
    toggleVisibilityVisualizerMappingTable();


    $("#questionNaming").keydown(function() {
        if(!$('#warnings').is(':empty')) {
            $('#warnings').empty();
        }
    });

    clearLocalStorage();
    
    $('.modal-trigger').leanModal();

    $('#selectedMinorSpinner').hide();
    $('#inputForMethodsSpinner').hide();
    $('#selectedChartTypeSpinner').hide();
    $("#inputForVisualizerSpinner").hide();
    $("#entityValueSpinner").hide();

    $('#graphLoaderSpinner').hide();
    $("#indicatorDefinition").hide();
    //$("#CompositeClosedButton").hide();
    $("#preview_chart").hide();
    $("#saveQuestion").attr('disabled', 'disabled');

    if( $('#associatedIndicatorsDiv').is(':empty') ) {
        $('#associatedIndicatorsDiv').append("No Associated Indicators");
    }

    if( $('#appliedAttributeFiltersDiv').is(':empty') ) {
        $('#appliedAttributeFiltersDiv').hide();
        $('#appliedAttributeFiltersLabel').hide();
    }
    if( $('#appliedSessionFiltersDiv').is(':empty') ) {
        $('#appliedSessionFiltersDiv').hide();
        $('#appliedSessionFiltersLabel').hide();
    }
    if( $('#appliedUserTimeFiltersDiv').is(':empty') ) {
        $('#appliedUserTimeFiltersDiv').hide();
        $('#appliedUserTimeFiltersLabel').hide();
    }

    $('#saveIndicator').click(function() {
        if ($('#GQSelectionForm').valid() && $('#SimpleForm').valid()) {
            finalizeIndicator();
        }
    });

    $("#addIndicator").click(function() {
        addNewIndicator('simple');
        localStorage.setItem('indType', "simple");
        $("#indicatorDefinition").show();
        $('body').animate({
            scrollTop: $("#indicatorDefinition").offset().top
        }, 1000);
    });
    $("#cancelIndicator").click(function() {
        $(".chip").removeClass('chip-bg');
        $("#indicatorDefinition").hide();
        $('body').animate({
            scrollTop: $("body").offset().top
        }, 1000);
    });

    $("#compositeIndicator").click(function() {
        addNewIndicator('composite');
        localStorage.setItem('indType', "composite");
        OpenCompositeModal();
        $("#comp_indicatorDefinition").show();
        $('body').animate({
            scrollTop: $("#comp_indicatorDefinition").offset().top
        }, 1000);
    });
    $("#comp_CloseButton").click(function() {
        $(".chip").removeClass('chip-bg');
        $("#comp_indicatorDefinition").hide();
        $('body').animate({
            scrollTop: $("body").offset().top
        }, 1000);
    });
    // $("#compositeIndicator").click(function() {
    //     LoadIndicatorVisualizations();
    // });
    $('#analyticsMethod').change(function() {
        updateAnalyticsMethodDesc();
    });

    $('#GoalSelection').change(function() {
        updateAnalyticsGoalDesc();
    });

    $("#indicatorData").on('click', 'tr', (function() {
        $(this).parent().children().removeClass("selected");
        $(this).addClass("selected");
        enableIndicatorLoad();
        //viewIndicatorProp();
        //displayIndicatorProp();
    }));

    $("#questionData").on('click', 'tr', (function() {
        $(this).parent().children().removeClass("selected");
        $(this).addClass("selected");
    }));

    refreshQuestionSummary();
});

function updateAnalyticsGoalDesc() {
    //$('#GoalSelectionDesc').html('<i class="material-icons">info</i>' + "&nbsp;" + $('#GoalSelection').find('option:selected').attr('data-tooltip'));
    $('#GoalSelectionDesc').html('<i class="material-icons">info</i>' + "&nbsp;" + $('#GoalSelection').find('option:selected').attr('title'));
};

function updateAnalyticsMethodDesc() {
    $('#analyticsMethodDesc').html('<i class="material-icons">info</i>' + "&nbsp;" + $('#analyticsMethod').find('option:selected').attr('title'));
};

function deleteIndicator() {
    // var e = event;
    // $(function() {
        $.ajax({
            type: "GET",
            url: "/indicators/deleteIndFromQn",
            // data: {indName: $(indicatorName).closest('div').attr("id")},
            // data: {indName: $("#deleteIndicatorValue").val()},
            data: {indIdentifier: $("#deleteIndicatorValue").val()},
            dataType: "html",
            success: function (response) {
                refreshQuestionSummary();
            }
        });
    // });
}

function loadIndicator(indicatorName){
    // $(function() {
        $('#loading-screen').removeClass('loader-hide');
        $("#preview_chart").hide();
        $(indicatorName).addClass("chip-bg").siblings().removeClass('chip-bg');
        localStorage.setItem("selectedIndicatorIdentifier", $(indicatorName).attr("id"));
        localStorage.setItem('selectedMethods', "[]");
        $("#indicatorDefinition").show();
        $('body').animate({
            scrollTop: $("#indicatorDefinition").offset().top
        }, 1000);
        $.ajax({
            type: "GET",
            url: "/indicators/loadIndFromQnSetToEditor",
            data: {
                // indName: $(indicatorName).attr("id")
                indIdentifier: $(indicatorName).attr("id")
            },
            dataType: "json",
            success: function (response) {
                if (response == null) {
                    refreshQuestionSummary();
                }
                else {
                    //console.log(response);
                    updateScreenAfterLoadInd(response);
                }
            }
        });
    // });
}

function loadIndicatorTemplate(selectedIndicator) {

    $('#loading-screen').removeClass('loader-hide');

    $("#preview_chart").hide();

    var properties = JSON.parse(selectedIndicator.parameters);

    $('#indicatorNaming').val(selectedIndicator.name);
    $('#sourceSelection').val(properties.indicatorDataset[0].selectedSource);
    $('#PlatformSelection').val(properties.indicatorDataset[0].selectedPlatform);
    $('#actionSelection').val(properties.indicatorDataset[0].selectedAction);

    var entityValues = properties.indicatorDataset[0].entityValues;
    //var userSpecs = properties.indicatorDataset[0].userSpecifications;
    var sessionSpecs = properties.indicatorDataset[0].sessionSpecifications;
    var timeSpecs = properties.indicatorDataset[0].timeSpecifications;
    loadAssociatedEntityFilters(entityValues);
    loadAssociatedSessionFilters(sessionSpecs);
    loadAssociatedTimeFilters(timeSpecs);
    //loadAssociatedUserFilters(userSpecs);

    populateAnalyticsMethods(JSON.stringify(properties));

    populateCategories(properties.indicatorDataset[0].selectedMinor);


    //We dont need to add them to associate indicators list yet. the user can edit it and finalize is
    /*setTimeout(function(){
        addLoadedIndicatorToAssociatedIndicatorList(selectedIndicator.name, properties);
    },2500);*/

    $('#loading-screen').addClass('loader-hide');
}

function addLoadedIndicatorToAssociatedIndicatorList(indicatorName, properties) {

    var goalId = document.getElementById("GoalSelection").value;
    var questionName = document.getElementById("questionNaming").value;
    var graphType = properties.selectedChartType;
    var graphEngine = properties.selectedChartEngine;
    var indicatorIndex = null;
    var analyticsMethod = properties.analyticsMethodId;

    var methodMappings = JSON.stringify(properties.queryToMethodConfig.mapping);
    var visualizationMappings = JSON.stringify(properties.methodToVisualizationConfig.mapping);
    //var selectedMethods = properties.retrievableObjects;
    var selectedMethods = properties.entityDisplayObjects;
    if(selectedMethods.length > 0)
        selectedMethods = JSON.parse(selectedMethods).join(',');


    $.ajax({
        type: "GET",
        url: "/indicators/finalize?goalId="+goalId+"&questionName=" + questionName + "&indicatorName=" + indicatorName + "&graphType=" + graphType
        + "&graphEngine=" + graphEngine + "&indicatorIdentifier=" + indicatorIndex + "&analyticsMethod=" + analyticsMethod + "&methodMappings=" + methodMappings
        + "&visualizationMappings=" + visualizationMappings + "&selectedMethods=" + selectedMethods,
        dataType: "json",
        success: function (response) {
            $('#loading-screen').addClass('loader-hide');
            postrefreshQuestionSummary(response, true);
        }
    });
}

function populateAnalyticsMethods(data){

    data = data || false;

    var request = createRequest();
    var url = "/engine/listAllAnalyticsMethods";
    request.open("GET",url,true);
    request.onreadystatechange=function(){processReceivedAnalyticsMethods(request, data)};
    request.send(null);

}

function processReceivedAnalyticsMethods(request, data) {
    if (request.readyState == 4) {
        if (request.status == 200) {
            var parsedJSON = JSON.parse(request.responseText);
            var analyticsMethodSelection = document.getElementById("analyticsMethod");
            removeOptions(analyticsMethodSelection);

            for (var i=0;i< parsedJSON.length;i++) {
                var newOption = new Option(parsedJSON[i].name, parsedJSON[i].id);
                newOption.setAttribute('title', parsedJSON[i].description);
                analyticsMethodSelection.appendChild(newOption);
            }
            analyticsMethodSelection.selectedIndex = -1;
            if (data) {
                var dataObj = JSON.parse(data);
                $('#analyticsMethod').val(dataObj.analyticsMethodId[0]);
                getAnalyticsMethodInputs(data);
            }
            populateVisualizationFrameworks(data);
        }
    }
}

function populateAnalyticsGoal(){
    var request = createRequest();
    var url = "/engine/listAllGoals";
    request.open("GET",url,true);
    request.onreadystatechange=function(){processReceivedAnalyticsGoal(request)};
    request.send(null);

}

function processReceivedAnalyticsGoal(request) {
    if (request.readyState == 4) {
        if (request.status == 200) {
            var parsedJSON = JSON.parse(request.responseText);
            var goalSelection = document.getElementById("GoalSelection");
            removeOptions(goalSelection);

            // var emptyOption = new Option("Select analtyics goal", "");
            // emptyOption.setAttribute("disabled", "");
            // emptyOption.setAttribute("selected", "");
            // goalSelection.appendChild(emptyOption);

            for (var i=0;i< parsedJSON.length;i++) {
                var newOption = new Option(parsedJSON[i].name, parsedJSON[i].id);
                newOption.setAttribute('title', parsedJSON[i].description);
                // newOption.setAttribute("data-position", "right");
                // newOption.setAttribute("data-tooltip", parsedJSON[i].description);
                // newOption.setAttribute("data-delay", "100");
                goalSelection.appendChild(newOption);

                //$(newOption).tooltip({delay: 100});
            }
            goalSelection.selectedIndex = -1;

            //$(goalSelection).material_select();
        }
    }
}

function populateVisualizationFrameworks(data) {

    data = data || false;

    var request = createRequest();
    var url = "/engine/listAllVisualizationFrameworks";
    request.open("GET",url,true);
    request.onreadystatechange=function(){processReceivedVisualizationFrameworks(request, data)};
    request.send(null);

}

function processReceivedVisualizationFrameworks(request, data) {
    if (request.readyState == 4) {
        if (request.status == 200) {
            var parsedJSON = JSON.parse(request.responseText);
            var visualizationFrameworksSelection = document.getElementById("EngineSelect");
            // var chartTypeSelection = document.getElementById("selectedChartType");
            removeOptions(visualizationFrameworksSelection);
            // removeOptions(chartTypeSelection);
            for (var i=0;i< parsedJSON.length;i++) {
                var newOption = new Option(parsedJSON[i].name, parsedJSON[i].id);
                visualizationFrameworksSelection.appendChild(newOption);

                // for (var j=0; j<parsedJSON[i].visualizationMethods.length; j++) {
                //     var newChartTypeOption = new Option(parsedJSON[i].visualizationMethods[j].name, parsedJSON[i].visualizationMethods[j].id);
                //     chartTypeSelection.appendChild(newChartTypeOption);
                // }
            }
            visualizationFrameworksSelection.selectedIndex = -1;
            // chartTypeSelection.selectedIndex = -1;

            if (data) {
                var dataObj = JSON.parse(data);
                $('#EngineSelect').val(dataObj.visualizationLibrary);
                // $('#selectedChartType').val(dataObj.selectedChartType);
                populateVisualizationMethods(data);
                // loadVisualizationMappingToTable(data);
            }
        }
    }
}


function populateVisualizationMethods(data) {

    data = data || false;

    $('#selectedChartTypeSpinner').show();

    var engineSelect = $("#EngineSelect").val();
    var request = createRequest();
    var url = "/engine/getVisualizationMethods?frameworkId=" + engineSelect;
    request.open("GET",url,true);
    request.onreadystatechange=function(){processReceivedVisualizationMethods(request, data)};
    request.send(null);

}

function processReceivedVisualizationMethods(request, data) {
    if (request.readyState == 4) {
        if (request.status == 200) {
            var parsedJSON = JSON.parse(request.responseText);

            // var visualizationFrameworksSelection = document.getElementById("EngineSelect");
            var chartTypeSelection = document.getElementById("selectedChartType");
            // removeOptions(visualizationFrameworksSelection);
            removeOptions(chartTypeSelection);
            // for (var i=0;i< parsedJSON.length;i++) {
            //     var newOption = new Option(parsedJSON[i].name, parsedJSON[i].id);
            //     visualizationFrameworksSelection.appendChild(newOption);

                for (var j=0; j<parsedJSON.visualizationMethods.length; j++) {
                    var newChartTypeOption = new Option(parsedJSON.visualizationMethods[j].name, parsedJSON.visualizationMethods[j].id);
                    chartTypeSelection.appendChild(newChartTypeOption);
                }
            // }
            // visualizationFrameworksSelection.selectedIndex = -1;
            chartTypeSelection.selectedIndex = -1;

            $('#selectedChartTypeSpinner').hide();

            if (data) {
                var dataObj = JSON.parse(data);
                // $('#EngineSelect').val(dataObj.selectedChartEngine);
                $('#selectedChartType').val(dataObj.visualizationType);

                loadVisualizationMappingToTable(data);
            }
        }
    }
}

function getIndicatorPreviewVisualizationCode() {

    $('#preview_msg').empty();

    if ($('#GQSelectionForm').valid() && $('#SimpleForm').valid()) {

        slideToElement("previewChartLabel");

        $('#preview_chart').hide();
        $('#graphLoaderSpinner').show();
        $("#generateGraph").attr('disabled', 'disabled');

        var methodMappings = localStorage.getItem('methodMappings') || "";
        var visualizationMappings = localStorage.getItem('visualizationMappings') || "";
        var selectedMethods = localStorage.getItem('selectedMethods') || "";
        if(selectedMethods.length > 0)
            selectedMethods = JSON.parse(selectedMethods).join(',');

        var request = createRequest();
        var url = "/engine/getIndicatorPreviewVisualizationCode?width=xxxwidthxxx"
            + "&height=xxxheightxxx"
            + "&analyticsMethodId=" + $("#analyticsMethod").val()
            + "&EngineSelect=" + $("#EngineSelect").val()
            + "&selectedChartType=" + $("#selectedChartType").val()
            + "&indicatorNaming=" + $("#indicatorNaming").val()
            + "&methodMappings=" + methodMappings
            + "&visualizationMappings=" + visualizationMappings
            + "&selectedMethods=" + selectedMethods;
        request.open("GET", url, true);
        request.onreadystatechange = function () {
            embedIndicatorPreviewVisualizationCode(request)
        };
        request.send(null);
    }
    else{
        var formValidator = $('#GQSelectionForm').data("validator");
        var invalidElements = formValidator.invalidElements(); // contain the invalid elements

        var msg = "";

        for (i = 0; i < invalidElements.length; i++) {
            if($(invalidElements[i]).is("select"))
                msg = msg + "<p id='"+invalidElements[i].id+"_msg'><a onclick='slideToElement(\""+ invalidElements[i].id +"\")'>" + $(invalidElements[i]).prev("label").attr("data-error") + "</a></p>";
            else
                msg = msg + "<p id='"+invalidElements[i].id+"_msg'><a onclick='slideToElement(\""+ invalidElements[i].id +"\")'>" + $(invalidElements[i]).next("label").attr("data-error") + "</a></p>";
        }

        formValidator = $('#SimpleForm').data("validator");
        invalidElements = formValidator.invalidElements(); // contain the invalid elements


        for (i = 0; i < invalidElements.length; i++) {
            if($(invalidElements[i]).is("select"))
                msg = msg + "<p id='"+invalidElements[i].id+"_msg'><a onclick='slideToElement(\""+ invalidElements[i].id +"\")'>" + $(invalidElements[i]).prev("label").attr("data-error") + "</a></p>";
            else
                msg = msg + "<p id='"+invalidElements[i].id+"_msg'><a onclick='slideToElement(\""+ invalidElements[i].id +"\")'>" + $(invalidElements[i]).next("label").attr("data-error") + "</a></p>";
        }

        //msg = msg.substring(0,msg.length-4);

        $('#preview_msg').html(msg);
    }
}

function slideToElement(id){
    $('body').animate({
        scrollTop: ($("#"+id).offset().top - 20)
    }, 1000);
}

function embedIndicatorPreviewVisualizationCode(request) {
    if (request.readyState == 4) {
        if (request.status == 200) {
            $('#graphLoaderSpinner').hide();
            /*if ((typeof google === 'undefined') || (typeof google.visualization === 'undefined')) {
                console.log('Google Charts Lib is not loaded');
            }
            else {*/
                var parsedJSON = JSON.parse(request.responseText);

                //console.log(parsedJSON);

                var decodedGraphData;

                if(parsedJSON.isSuccess) {
                    decodedGraphData = decodeURIComponent(parsedJSON.visualizationCode);

                    decodedGraphData = decodedGraphData.replace("xxxwidthxxx","$('#chart_wrap').outerWidth(true)");
                    decodedGraphData = decodedGraphData.replace("xxxheightxxx","$('#chart_wrap').outerHeight(true)");
                }
                else {
                    decodedGraphData = '<div class="alert alert-error">' + parsedJSON.errorMessage + '</div>';
                }

            $("#generateGraph").removeAttr('disabled');

                try {
                    $('#preview_chart').show();
                    $("#preview_chart").html(decodedGraphData);
                }
                catch(err) {
                    $("#preview_chart").append("<span>" + err.message + "</span>");
                }


            // }
        }
    }
}

function getQuestionVisualizationCode(){
    var request = createRequest();
    var url = "/engine/getQuestionVisualizationCode?width=" + $('#chart_wrap').parent().width()
        + "&height=" + $('#chart_wrap').parent().height();
    request.open("GET",url,true);
    request.onreadystatechange=function(){embedQuestionVisualizationCode(request)};
    request.send(null);

}

function embedQuestionVisualizationCode(request) {
    if (request.readyState == 4) {
        if (request.status == 200) {
            if ((typeof google === 'undefined') || (typeof google.visualization === 'undefined')) {
                console.log('Google Charts Lib is not loaded');
            }
            else {
                var parsedJSON = JSON.parse(request.responseText);
                var decodedGraphData = decodeURIComponent(parsedJSON);

                $("#preview_chart").html(decodedGraphData);
            }
        }
    }
}

function showDeleteIndicatorModal(filter, event) {
    // $("#deleteIndicatorValue").val($(filter).closest('div').attr("name"));
    $("#deleteIndicatorValue").val($(filter).closest('div').attr("id"));
    $('#confirmIndicatorDeleteModal').openModal();
    event.stopPropagation();
}

function LoadExistingIndicator() {
    //var questionName = document.getElementById("questionNaming").value;

    //if (questionName) {
        $('#loadIndicatorTemplateModel').openModal();
    /*} else {
        $('#warnings').append('<div class="alert alert-danger"> Please enter a question name. </div>');
        $('body').animate({
            scrollTop: $("#warnings").offset().top
        }, 1000);
    }*/
}

function loadQuestionFromTemplate() {

    // var questionId = $('tr.selected:first td:nth-child(3)', '#questionData').text();
    // $.ajax({
    //     type: "GET",
    //     url: "/engine/getIndicatorsByQuestionId?id=" + questionId,
    //     success: function (response) {
    //         console.log(response);
    //     }
    // });

    var questionName = $('tr.selected:first td:nth-child(2)', '#questionData').text();
    $.ajax({
        type: "GET",
        url: "/indicators/loadQuestion?name=" + questionName,
        dataType: "json",
        success: function (response) {
            //console.log(response);
            //console.log(response.questionName);

            $('#questionNaming').val(response.questionName);

            for(var i=0; i<response.sessionIndicators.length; i++) {
                var indicatorObj = response.sessionIndicators[i];
                var jsonProps = JSON.parse(indicatorObj.genIndicatorProps.jsonData);

                var indicatorName = indicatorObj.indicatorName;
                var properties = new Object();
                properties.selectedChartEngine = indicatorObj.genIndicatorProps.chartEngine;
                properties.selectedChartType = indicatorObj.genIndicatorProps.chartType;
                properties.queryToMethodConfig = jsonProps.queryToMethodConfig;
                properties.methodToVisualizationConfig = jsonProps.methodToVisualizationConfig;
                properties.retrievableObjects = jsonProps.retrievableObjects;

                // var methodMappings = JSON.stringify(properties.queryToMethodConfig.mapping);
                // var visualizationMappings = JSON.stringify(properties.methodToVisualizationConfig.mapping);
                // var selectedMethods = properties.retrievableObjects;

                // addLoadedIndicatorToAssociatedIndicatorList(indicatorName, properties);
                break;

            }
        }
    });
}

function clearLocalStorage(type) {
    if(type=='simple') {
        localStorage.removeItem("selectedIndicatorIdentifier");
        localStorage.removeItem("selectedMethods");
        localStorage.removeItem("methodMappings");
        localStorage.removeItem("visualizationMappings");
    }
    else if(type=='composite') {
        localStorage.removeItem("comp_visualizationMappings");
    }
    else if(type=='multianalysis') {
        localStorage.removeItem("mlai_selectedIndicatorIdentifier");
        localStorage.removeItem("mlai_selectedMethods");
        localStorage.removeItem("mlai_methodMappings");
        localStorage.removeItem("mlai_visualizationMappings");
    }
}

function clearGoalArea(){
    //$('#GoalSelection option:selected').prop("selectedIndex", -1);
    $('#GoalSelectionDesc').empty();
    var goalSelection = document.getElementById("GoalSelection");
    goalSelection.selectedIndex = -1;
}

function clearQuestionArea(){
    $('#questionNaming').val("");
    $('#associatedIndicatorsDiv').empty();
    $('#associatedIndicatorsDiv').append("No Associated Indicators");
    $("#saveQuestion").attr('disabled', 'disabled');
}

function clearIndicatorArea(type){
    if(type=='simple') {
        clearSimpleIndicatorArea();
    }
    else if(type=='multianalysis') {
        clearMLAIIndicatorArea();
    }
    else if(type=='composite') {
        clearCompIndicatorArea();
    }
    else {
        clearSimpleIndicatorArea();
        clearCompIndicatorArea();
        //clearMLAIIndicatorArea();
    }
}

function clearSimpleIndicatorArea(){
    $('#indicatorNaming').val("");

    $('#sourceSelection option:selected').prop('selected', false);
    $('#PlatformSelection option:selected').prop('selected', false);
    $('#actionSelection option:selected').prop('selected', false);

    $('#selectedMinor').empty();
    $('#entityKeySelection').empty();
    $('#entityValue').empty();

    $('#appliedAttributeFiltersDiv').empty();
    $('#appliedAttributeFiltersDiv').hide();
    $('#appliedAttributeFiltersLabel').hide();

    // $('#appliedSessionFiltersDiv').empty();
    // $('#appliedSessionFiltersDiv').hide();
    // $('#appliedSessionFiltersLabel').hide();

    // $('#isMyData').attr('checked', false);

    $('#appliedUserTimeFiltersDiv').empty();
    $('#appliedUserTimeFiltersDiv').hide();
    $('#appliedUserTimeFiltersLabel').hide();

    $('#analyticsMethodDesc').empty();
    $('#analyticsMethod option:selected').prop('selected', false);

    $('#methodDataColumns').empty();
    $('#inputForMethods').empty();
    $('#addMethodMapping').prop('disabled', false);

    $("#methodMappingTable > tbody:last").children().remove();
    toggleVisibilityMethodMappingTable();


    $('#EngineSelect option:selected').prop('selected', false);
    $('#selectedChartType option:selected').prop('selected', false);

    $('#outputForMethods').empty();
    $('#inputForVisualizer').empty();
    $('#addVisualizationMapping').prop('disabled', false);

    $("#visualizerMappingTable > tbody:last").children().remove();
    toggleVisibilityVisualizerMappingTable();

    $("#preview_chart").empty();
    $("#preview_chart").hide();

    populateAnalyticsMethods();
}

/*function clearMLAIIndicatorArea(){
    $('#mlai_indicatorNaming').val("");

    $('#mlai_analyticsMethodDesc').empty();
    $('#mlai_analyticsMethod option:selected').prop('selected', false);

    $('#mlai_methodDataColumns').empty();
    $('#mlai_inputForMethods').empty();
    $('#mlai_addMethodMapping').prop('disabled', false);

    $("#mlai_methodMappingTable > tbody:last").children().remove();
    toggleMLAIVisibilityMethodMappingTable();


    $('#mlai_EngineSelect option:selected').prop('selected', false);
    $('#mlai_selectedChartType option:selected').prop('selected', false);

    $('#mlai_outputForMethods').empty();
    $('#mlai_inputForVisualizer').empty();
    $('#mlai_addVisualizationMapping').prop('disabled', false);

    $("#mlai_visualizerMappingTable > tbody:last").children().remove();
    toggleMLAIVisibilityVisualizerMappingTable();

    $("#mlai_preview_chart").empty();
    $("#mlai_preview_chart").hide();

    populateAnalyticsMethods();
}*/

function clearCompIndicatorArea(){
 $('#comp_indicatorNaming').val("");

 $('#comp_EngineSelect option:selected').prop('selected', false);
 $('#comp_selectedChartType option:selected').prop('selected', false);

 $('#comp_outputForMethods').empty();
 $('#comp_inputForVisualizer').empty();
 $('#comp_addVisualizationMapping').prop('disabled', false);

 $("#comp_visualizerMappingTable > tbody:last").children().remove();
 toggleCompVisibilityVisualizerMappingTable();

 $("#comp_preview_chart").empty();
 $("#comp_preview_chart").hide();
 }

function copyCode() {
    try {
        $('#questionRequestCode').select();
        document.execCommand('copy');
    } catch(e) {
        alert(e);
    }
}