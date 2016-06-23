$(function() {

    $('.datepicker').pickadate({
        selectMonths: true, // Creates a dropdown to control month
        selectYears: 15 // Creates a dropdown of 15 years to control year
    });

    populateAnalyticsGoal();
    populateAnalyticsMethods();
    toggleVisibilityMethodMappingTable();
    toggleVisibilityVisualizerMappingTable();

    localStorage.removeItem("selectedMethods");
    localStorage.removeItem("methodMappings");
    localStorage.removeItem("visualizationMappings");

    $('.modal-trigger').leanModal();

    $('#selectedMinorSpinner').hide();
    $('#graphLoaderSpinner').hide();
    $("#indicatorDefinition").hide();
    $("#CompositeClosedButton").hide();
    // $("#graphImage").hide();
    $("#preview_chart").hide();
    $("#saveQuestion").attr('disabled', 'disabled');
    // $("#loadIndicatorTemplateModelTable").hide();

    if( $('#associatedIndicatorsDiv').is(':empty') ) {
        $('#associatedIndicatorsDiv').append("No Associated Indicators Found");
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
        if ($('#sessionSelection').valid()) {
            finalizeIndicator();
        }
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
        LoadIndicatorVisualizations();
    });
    $('#analyticsMethod').change(function() {
        updateAnalyticsMethodDesc();
    });
    $("#indicatorData").on('click', 'tr', (function() {
        $(this).parent().children().removeClass("selected");
        $(this).addClass("selected");
        viewIndicatorProp();
    }));

    $("#questionData").on('click', 'tr', (function() {
        $(this).parent().children().removeClass("selected");
        $(this).addClass("selected");
    }));

});

function updateAnalyticsMethodDesc() {
    $('#analyticsMethodDesc').html('<i class="material-icons">info</i>' + "&nbsp;" + $('#analyticsMethod').find('option:selected').attr('description'));
};

function deleteIndicator() {
    // var e = event;
    $(function() {
        $.ajax({
            type: "GET",
            url: "/indicators/deleteIndFromQn",
            // data: {indName: $(indicatorName).closest('div').attr("id")},
            data: {indName: $("#deleteIndicatorValue").val()},
            dataType: "html",
            success: function (response) {
                // $("#indDeleteDialog").text(response);
                // $('.indDeleteDialog').dialog('option', 'title', 'Indicator Deletion Message');
                refreshQuestionSummary();
                // $("#indDeleteDialog").dialog("open");
                // e.stopPropagation();
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
                    // $("#indDeleteDialog").html("The selected Indicator cannot be loaded into the editor as it a composite Indicator or a NULL value.");
                    refreshQuestionSummary();
                    // $('.indDeleteDialog').dialog('option', 'title', 'Indicator Load Message');
                    // $("#indDeleteDialog").dialog("open");
                }
                else {
                    // $("#indDeleteDialog").html("The selected Indicator has been successfully loaded into the Editor.<br/> Please note that it has been <strong>deleted </strong>" +
                    //     "from the Question. So after making changes, please save it again if you want it to be associated with the Question. <br/>" +
                    //     "Also note that you have to select again Platform and Action to populate the List of Categories.");
                    // $('.indDeleteDialog').dialog('option', 'title', 'Indicator Load Message');
                    //refreshQuestionSummary();
                    console.log(response);
                    updateScreenAfterLoadInd(response);
                    // $("#indDeleteDialog").dialog("open");
                }
            }
        });
    });
}

function loadIndicatorTemplate(rawData) {

    var properties = JSON.parse(rawData.glaIndicatorProps.json_data);
    // addLoadedIndicatorToAssociatedIndicatorList(rawData.indicator_name, properties);

    $('#indicatorNaming').val(rawData.indicator_name);

    $('#PlatformSelection').val(properties.platform);

    $('#actionSelection').val(properties.action);

    $('#selectedChartType').val(properties.selectedChartType);

    $('#EngineSelect').val(properties.selectedChartEngine);

    $('#analyticsMethod').val(properties.analyticsMethodId);

    var optionsToSelect = properties.source;
    var select = document.getElementById( 'sourceSelection' );

    for ( var i = 0, l = select.options.length, o; i < l; i++ )
    {
        o = select.options[i];
        if ( optionsToSelect.indexOf( o.text ) != -1 )
        {
            o.selected = true;
        }
    }

    var entityValues = properties.entityValues;
    var userSpecs = properties.userSpecifications;
    var sessionSpecs = properties.sessionSpecifications;
    var timeSpecs = properties.timeSpecifications;
    loadAssociatedEntityFilters(entityValues);
    loadAssociatedSessionFilters(sessionSpecs);
    loadAssociatedUserTimeFilters(userSpecs, timeSpecs);
    setTimeout(function(){
        addLoadedIndicatorToAssociatedIndicatorList(rawData.indicator_name, properties);
    },5000);
    populateCategories(properties.minor);
}

function addLoadedIndicatorToAssociatedIndicatorList(indicatorName, properties) {
    
    var questionName = document.getElementById("questionNaming").value;
    var graphType = properties.selectedChartType;
    var graphEngine = properties.selectedChartEngine;
    var indicatorIndex = null;
    // var analyticsMethod = properties.analyticsMethodId;
    var analyticsMethod = 1;

    $.ajax({
        type: "GET",
        url: "/indicators/finalize?questionName=" + questionName + "&indicatorName=" + indicatorName + "&graphType=" + graphType
        + "&graphEngine=" + graphEngine + "&indicatorIndex=" + indicatorIndex + "&analyticsMethod=" + analyticsMethod,
        dataType: "json",
        success: function (response) {
            postrefreshQuestionSummary(response);
        }
    });
}

function populateAnalyticsMethods(){
    var request = createRequest();
    var url = "/engine/listAllAnalyticsMethods";
    request.open("GET",url,true);
    request.onreadystatechange=function(){processReceivedAnalyticsMethods(request)};
    request.send(null);

}

function processReceivedAnalyticsMethods(request) {
    if (request.readyState == 4) {
        if (request.status == 200) {
            var parsedJSON = JSON.parse(request.responseText);

            var analyticsMethodSelection = document.getElementById("analyticsMethod");
            removeOptions(analyticsMethodSelection);
            for (var i=0;i< parsedJSON.length;i++) {
                var newOption = new Option(parsedJSON[i].name, parsedJSON[i].id);
                newOption.setAttribute('description', parsedJSON[i].description);
                analyticsMethodSelection.appendChild(newOption);
            }
            analyticsMethodSelection.selectedIndex = -1;
            populateVisualizationFrameworks();
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
            for (var i=0;i< parsedJSON.length;i++) {
                var newOption = new Option(parsedJSON[i].name, parsedJSON[i].id);
                goalSelection.appendChild(newOption);
            }
            goalSelection.selectedIndex = 0;
        }
    }
}

function populateVisualizationFrameworks(){
    var request = createRequest();
    var url = "/engine/listAllVisualizationFrameworks";
    request.open("GET",url,true);
    request.onreadystatechange=function(){processReceivedVisualizationFrameworks(request)};
    request.send(null);

}

function processReceivedVisualizationFrameworks(request) {
    if (request.readyState == 4) {
        if (request.status == 200) {
            var parsedJSON = JSON.parse(request.responseText);
            var visualizationFrameworksSelection = document.getElementById("EngineSelect");
            var chartTypeSelection = document.getElementById("selectedChartType");
            removeOptions(visualizationFrameworksSelection);
            removeOptions(chartTypeSelection);
            for (var i=0;i< parsedJSON.length;i++) {
                var newOption = new Option(parsedJSON[i].name, parsedJSON[i].id);
                visualizationFrameworksSelection.appendChild(newOption);

                for (var j=0; j<parsedJSON[i].visualizationMethods.length; j++) {
                    var newChartTypeOption = new Option(parsedJSON[i].visualizationMethods[j].name, parsedJSON[i].visualizationMethods[j].id);
                    chartTypeSelection.appendChild(newChartTypeOption);
                }
            }
            visualizationFrameworksSelection.selectedIndex = -1;
            chartTypeSelection.selectedIndex = -1;
            // getVisualizationMethodInputs();
        }
    }
}

function getIndicatorPreviewVisualizationCode() {

    if ($('#sessionSelection').valid()) {

        $('#graphLoaderSpinner').show();

        var methodMappings = localStorage.getItem('methodMappings') || "";
        var visualizationMappings = localStorage.getItem('visualizationMappings') || "";
        var selectedMethods = localStorage.getItem('selectedMethods') || "";
        selectedMethods = JSON.parse(selectedMethods).join(',');

        var request = createRequest();
        var url = "/engine/getIndicatorPreviewVisualizationCode?width=" + $('#chart_wrap').parent().width()
            + "&height=" + $('#chart_wrap').parent().height()
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
}

function embedIndicatorPreviewVisualizationCode(request) {
    if (request.readyState == 4) {
        if (request.status == 200) {
            $('#graphLoaderSpinner').hide();
            if ((typeof google === 'undefined') || (typeof google.visualization === 'undefined')) {
                console.log('Google Charts Lib is not loaded');
            }
            else {
                var parsedJSON = JSON.parse(request.responseText);
                var decodedGraphData = decodeURIComponent(parsedJSON);
                // decodedGraphData = "<TemporaryDiv>" + decodedGraphData + "</TemporaryDiv>";
                //
                // var parser = new DOMParser();
                // var dataDOMObj = parser.parseFromString(decodedGraphData, "text/xml");
                //
                // var scriptTagData = dataDOMObj.getElementsByTagName('script')[0];
                // var randomNo = Math.floor((Math.random() * 100000) + 1000);
                // scriptTagData.insertAdjacentHTML('afterbegin', '<script type="text/javascript"> google.charts.setOnLoadCallback(drawChart_' + randomNo + '); function drawChart_' + randomNo + '() {');
                // scriptTagData.insertAdjacentHTML('beforeend', '} </script>');
                //
                // var divTagData = dataDOMObj.getElementsByTagName('div')[0];
                // $("#preview_chart").html(dataDOMObj.getElementsByTagName('TemporaryDiv')[0].textContent + divTagData.outerHTML);
                $("#preview_chart").html(decodedGraphData);
                $('#preview_chart').show();
            }
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
                // decodedGraphData = "<TemporaryDiv>" + decodedGraphData + "</TemporaryDiv>";
                //
                // var parser = new DOMParser();
                // var dataDOMObj = parser.parseFromString(decodedGraphData, "text/xml");
                //
                // var scriptTagData = dataDOMObj.getElementsByTagName('script')[0];
                // var randomNo = Math.floor((Math.random() * 100000) + 1000);
                // scriptTagData.insertAdjacentHTML('afterbegin', '<script type="text/javascript"> google.charts.setOnLoadCallback(drawChart_' + randomNo + '); function drawChart_' + randomNo + '() {');
                // scriptTagData.insertAdjacentHTML('beforeend', '} </script>');
                //
                // var divTagData = dataDOMObj.getElementsByTagName('div')[0];
                // $("#preview_chart").html(dataDOMObj.getElementsByTagName('TemporaryDiv')[0].textContent + divTagData.outerHTML);
                $("#preview_chart").html(decodedGraphData);
            }
        }
    }
}

function showDeleteIndicatorModal(filter, event) {
    $("#deleteIndicatorValue").val($(filter).closest('div').attr("id"));
    $('#confirmIndicatorDeleteModal').openModal();
    event.stopPropagation();
}

function LoadExistingIndicator() {
    var questionName = document.getElementById("questionNaming").value;

    if (questionName) {
        $('#loadIndicatorTemplateModel').openModal();
    } else {
        $('#warnings').append('<div class="alert alert-danger"> Please enter a question name. </div>');
        $('body').animate({
            scrollTop: $("#warnings").offset().top
        }, 2000);
    }
}

function loadQuestionFromTemplate() {

    var questionId = $('tr.selected:first td:nth-child(3)', '#questionData').text();
    $.ajax({
        type: "GET",
        url: "/engine/getIndicatorsByQuestionId?id=" + questionId,
        success: function (response) {
            console.log(response);
        }
    });
}