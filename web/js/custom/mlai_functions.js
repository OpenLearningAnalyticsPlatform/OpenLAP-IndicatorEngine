/*
 * Open Platform Learning Analytics : Indicator Engine
 * Copyright (C) 2015  Learning Technologies Group, RWTH
 *
 * This program is free software; you can redistribute it and/or
 * modify it under the terms of the GNU General Public License
 * as published by the Free Software Foundation; either version 2
 * of the License, or (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program; if not, write to the Free Software
 * Foundation, Inc., 51 Franklin Street, Fifth Floor, Boston, MA  02110-1301, USA.
 */

/**
 * Created by arham on 2/1/2017.
 */

$(function() {
    populateMLAIAnalyticsMethods();
    populateMLAIVisualizationFrameworks();
    toggleMLAIVisibilityMethodMappingTable();
    toggleMLAIVisibilityVisualizerMappingTable();

    $('#mlai_selectedMinorSpinner').hide();
    $('#mlai_inputForMethodsSpinner').hide();
    $('#mlai_selectedChartTypeSpinner').hide();
    $("#mlai_inputForVisualizerSpinner").hide();
    $("#mlai_entityValueSpinner").hide();

    $('#mlai_graphLoaderSpinner').hide();
    $("#mlai_indicatorDefinition").hide();
    //$("#mlai_CompositeClosedButton").hide();
    $("#mlai_preview_chart").hide();
    $("#mlai_saveQuestion").attr('disabled', 'disabled');

    if( $('#mlai_associatedIndicatorsDiv').is(':empty') ) {
        $('#mlai_associatedIndicatorsDiv').append("No Associated Indicators");
    }

    if( $('#mlai_appliedAttributeFiltersDiv').is(':empty') ) {
        $('#mlai_appliedAttributeFiltersDiv').hide();
        $('#mlai_appliedAttributeFiltersLabel').hide();
    }
    if( $('#mlai_appliedSessionFiltersDiv').is(':empty') ) {
        $('#mlai_appliedSessionFiltersDiv').hide();
        $('#mlai_appliedSessionFiltersLabel').hide();
    }
    if( $('#mlai_appliedUserTimeFiltersDiv').is(':empty') ) {
        $('#mlai_appliedUserTimeFiltersDiv').hide();
        $('#mlai_appliedUserTimeFiltersLabel').hide();
    }

    $('#mlai_saveIndicator').click(function() {
        if ($('#GQSelectionForm').valid() && $('#MLAIForm').valid()) {
            finalizeMLAIIndicator();
        }
    });

    $('#MLAIForm').validate({
        ignore: false,
        onkeyup: false,
        ignoreTitle: true,
        rules: {
            mlai_indicatorNaming: {
                required: true,
                minlength: 6,
                validateIndicator: true
            },
            mlai_analyticsMethod: {
                required: true
            },
            mlai_EngineSelect: {
                required: true
            },
            mlai_selectedChartType: {
                required: true
            },
            mlai_inputForMethods: {
                hasNoOptionLeft: true
            },
            mlai_inputForVisualizer: {
                hasNoOptionLeft: true
            }
        },
        messages: {
            mlai_indicatorNaming: {
                required: "Indicator name is required.",
                minlength: "Indicator name should be at least 6 characters."
            },
            mlai_analyticsMethod: {
                required: "Select analytics method."
            },
            mlai_EngineSelect: {
                required: "Select visualization library."
            },
            mlai_selectedChartType: {
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

    $("#addmlaiIndicator").click(function() {
        addNewIndicator('multianalysis');
        localStorage.setItem('indType', "multianalysis");
        $("#mlai_indicatorDefinition").show();
        $('body').animate({
            scrollTop: $("#mlai_indicatorDefinition").offset().top
        }, 1000);
    });

    $("#mlai_cancelIndicator").click(function() {
        $(".chip").removeClass('chip-bg');
        localStorage.removeItem("indType");
        $("#mlai_indicatorDefinition").hide();
        $('body').animate({
            scrollTop: $("body").offset().top
        }, 1000);
    });

    $('#mlai_analyticsMethod').change(function() {
        updateMLAIAnalyticsMethodDesc();
    });
});

function populateMLAIAnalyticsMethods(data){

    data = data || false;

    var request = createRequest();
    var url = "/engine/listAllAnalyticsMethods";
    request.open("GET",url,true);
    request.onreadystatechange=function(){processReceivedMLAIAnalyticsMethods(request, data)};
    request.send(null);

}

function processReceivedMLAIAnalyticsMethods(request, data) {
    if (request.readyState == 4) {
        if (request.status == 200) {
            var parsedJSON = JSON.parse(request.responseText);
            var analyticsMethodSelection = document.getElementById("mlai_analyticsMethod");
            removeOptions(analyticsMethodSelection);

            for (var i=0;i< parsedJSON.length;i++) {
                var newOption = new Option(parsedJSON[i].name, parsedJSON[i].id);
                newOption.setAttribute('description', parsedJSON[i].description);
                analyticsMethodSelection.appendChild(newOption);
            }
            analyticsMethodSelection.selectedIndex = -1;
            if (data) {
                var dataObj = JSON.parse(data);
                $('#mlai_analyticsMethod').val(dataObj.analyticsMethodId);
                getMLAIAnalyticsMethodInputs(data);
            }
            populateMLAIVisualizationFrameworks(data);
        }
    }
}

function getMLAIAnalyticsMethodInputs(data) {

    data = data || false;

    var spinner = $('#mlai_inputForMethodsSpinner');
    spinner.show();

    var analyticsMethodId = $("#mlai_analyticsMethod").val();
    $.ajax({
        type: "GET",
        url: "/engine/getAnalyticsMethodInputs?id=" + analyticsMethodId,
        dataType: "json",
        success: function (response) {
            var inputForMethodsSelect = $('#mlai_inputForMethods');
            inputForMethodsSelect.empty();

            $('#mlai_methodMappingTable >tbody').empty();
            localStorage.removeItem("mlai_methodMappings");
            toggleMLAIVisibilityMethodMappingTable()

            for (var i=0;i< response.length;i++) {
                inputForMethodsSelect
                    .append($("<option></option>")
                        .attr("value", response[i].id)
                        .attr("title", response[i].description)
                        .attr("is-required", response[i].required)
                        .attr("data-value", JSON.stringify(response[i]))
                        .text(response[i].title + " (" + response[i].type + ")" ));
            }
            $("select#mlai_inputForMethods")[0].selectedIndex = 0;

            var inputForMethodOptionSize = $('#mlai_inputForMethods option').size();
            if (inputForMethodOptionSize > 0) {
                $('#mlai_addMethodMapping').prop('disabled', false);
            }

            spinner.hide();

            if (data) {
                loadMLAIMethodMappingToTable(data);
            }

            getMLAIAnalyticsMethodOutputs();

            if(!data) {
                getMLAIVisualizationMethodInputs();
            }
        }
    });
}

function updateMLAIAnalyticsMethodDesc() {
    $('#mlai_analyticsMethodDesc').html('<i class="material-icons">info</i>' + "&nbsp;" + $('#mlai_analyticsMethod').find('option:selected').attr('title'));
};

/*function getMLAIAnalyticsMethodInputs(data) {

    data = data || false;

    var spinner = $('#mlai_inputForMethodsSpinner');
    spinner.show();

    var analyticsMethodId = $("#mlai_analyticsMethod").val();
    $.ajax({
        type: "GET",
        url: "/engine/getAnalyticsMethodInputs?id=" + analyticsMethodId,
        dataType: "json",
        success: function (response) {
            var inputForMethodsSelect = $('#mlai_inputForMethods');
            inputForMethodsSelect.empty();

            $('#mlai_methodMappingTable >tbody').empty();
            localStorage.removeItem("mlai_methodMappings");
            toggleMLAIVisibilityMethodMappingTable();

            for (var i=0;i< response.length;i++) {
                inputForMethodsSelect
                    .append($("<option></option>")
                        .attr("value", response[i].id)
                        .attr("title", response[i].description)
                        .attr("is-required", response[i].required)
                        .attr("data-value", JSON.stringify(response[i]))
                        .text(response[i].title + " (" + response[i].type + ")" ));
            }
            $("select#mlai_inputForMethods")[0].selectedIndex = 0;

            var inputForMethodOptionSize = $('#mlai_inputForMethods option').size();
            if (inputForMethodOptionSize > 0) {
                $('#mlai_addMethodMapping').prop('disabled', false);
            }

            spinner.hide();

            if (data) {
                loadMLAIMethodMappingToTable(data);
            }

            getMLAIAnalyticsMethodOutputs();

            if(!data) {
                getVisualizationMethodInputs();
            }
        }
    });
}*/

function getMLAIAnalyticsMethodOutputs() {
    var analyticsMethodId = $("#mlai_analyticsMethod").val();
    $.ajax({
        type: "GET",
        url: "/engine/getAnalyticsMethodOutputs?id=" + analyticsMethodId,
        dataType: "json",
        success: function (response) {
            var methodOutputSelect = $('#mlai_outputForMethods');
            methodOutputSelect.empty();
            for (var i=0;i< response.length;i++) {
                methodOutputSelect
                    .append($("<option></option>")
                        .attr("value", response[i].id)
                        .attr("title", response[i].description)
                        .attr("data-value", JSON.stringify(response[i]))
                        .text(response[i].title + " (" + response[i].type + ")" ));
            }
            $("select#mlai_outputForMethods")[0].selectedIndex = 0;
        }
    });
}

function toggleMLAIVisibilityMethodMappingTable() {
    var rowCount = $('#methodMappingTable >tbody >tr').length;
    if (rowCount == 0) {
        $('#methodMappingTable').hide();
    } else {
        $('#methodMappingTable').show();
    }
}

function addMLAIMethodMappingToTable() {

    var methodDataColumnsSelect = $('#mlai_methodDataColumns');
    var inputForMethodsSelect = $('#mlai_inputForMethods');

    if (methodDataColumnsSelect.val() && inputForMethodsSelect.val()) {

        var methodDataColumnsData = methodDataColumnsSelect.find(':selected').data('value');
        var inputForMethodsData = inputForMethodsSelect.find(':selected').data('value');

        var row = "<tr data-methodcols='" + JSON.stringify(methodDataColumnsData) + "' data-methodsdata='" + JSON.stringify(inputForMethodsData) + "'>" +
            "<td>" + methodDataColumnsData.title+ " (" + methodDataColumnsData.type + ")" + "</td>" +
            "<td>" + inputForMethodsData.title + " (" + inputForMethodsData.type + ")"  + "</td>" +
            "<td><i class='material-icons' onclick='deleteMLAIMethodMappingTableRow(this, event);'>close</i></td>" +
            "</tr>";

        var methodMappings = JSON.parse(localStorage.getItem('mlai_methodMappings')) || [];
        methodMappings.push({outputPort: methodDataColumnsData, inputPort: inputForMethodsData});
        localStorage.setItem('mlai_methodMappings', JSON.stringify(methodMappings));

        //console.log(methodDataColumnsData);
        //only adding to the selected Methods if required is false means the selected column is from Entity table
        if(methodDataColumnsData.required == false) {
            var selectedMethods = JSON.parse(localStorage.getItem('mlai_selectedMethods')) || [];

            var index = selectedMethods.indexOf(methodDataColumnsData.id);
            if (index < 0) {
                selectedMethods.push(methodDataColumnsData.id);
                localStorage.setItem('mlai_selectedMethods', JSON.stringify(selectedMethods));
            }
        }
        // $("#methodDataColumns option:selected").remove();
        $("#mlai_inputForMethods option:selected").remove();
        var methodDataColumnsOptionSize = $('#mlai_methodDataColumns option').size();
        var inputForMethodsOptionSize = $('#mlai_inputForMethods option').size();

        if (methodDataColumnsOptionSize == 0 || inputForMethodsOptionSize == 0) {
            $('#mlai_addMethodMapping').prop('disabled', true);
        }

        $('#mlai_methodMappingTable > tbody:last').append(row);
        toggleMLAIVisibilityMethodMappingTable();
    }

    $('#mlai_inputForMethods').valid();
}

function loadMLAIMethodMappingToTable(data) {

    data = data || false;

    if(data) {

        $("#mlai_methodMappingTable > tbody:last").children().remove();

        var dataObj = JSON.parse(data);
        var queryToMethodConfig = dataObj.queryToMethodConfig;

        var selectedMethods = JSON.parse(localStorage.getItem('mlai_selectedMethods')) || [];
        var methodMappings = [];

        for (var i = 0; i < queryToMethodConfig.mapping.length; i++) {

            var methodDataColumnsData = queryToMethodConfig.mapping[i].outputPort;
            var inputForMethodsData = queryToMethodConfig.mapping[i].inputPort;

            var row = "<tr data-methodcols='" + JSON.stringify(methodDataColumnsData) + "' data-methodsdata='" + JSON.stringify(inputForMethodsData) + "'>" +
                "<td>" + methodDataColumnsData.title + "</td>" +
                "<td>" + inputForMethodsData.title + "</td>" +
                "<td><i class='material-icons' onclick='deleteMLAIMethodMappingTableRow(this, event);'>close</i></td>" +
                "</tr>";

            methodMappings.push({outputPort: methodDataColumnsData, inputPort: inputForMethodsData});

            if(methodDataColumnsData.required == false) {
                var index = selectedMethods.indexOf(methodDataColumnsData.id);
                if (index < 0) {
                    selectedMethods.push(methodDataColumnsData.id);
                }
            }

            $('#mlai_inputForMethods option[value="' + inputForMethodsData.id + '"]').remove();
            $('#mlai_methodMappingTable > tbody:last').append(row);
        }

        localStorage.setItem('mlai_methodMappings', JSON.stringify(methodMappings));
        localStorage.setItem('mlai_selectedMethods', JSON.stringify(selectedMethods));

        var methodDataColumnsOptionSize = $('#mlai_methodDataColumns option').size();
        var inputForMethodsOptionSize = $('#mlai_inputForMethods option').size();
        if (methodDataColumnsOptionSize == 0 || inputForMethodsOptionSize == 0) {
            $('#mlai_addMethodMapping').prop('disabled', true);
        }
        toggleMLAIVisibilityMethodMappingTable();
    }
}

function deleteMLAIMethodMappingTableRow(column, event) {

    var methodDataColumnsData = $(column).parent().parent().data('methodcols');
    var inputForMethodsData = $(column).parent().parent().data('methodsdata');

    var methodMappings = JSON.parse(localStorage.getItem('mlai_methodMappings'));
    var newMethodMappings = methodMappings.filter(function(val){
        return (JSON.stringify(val.outputPort) !== JSON.stringify(methodDataColumnsData) &&
        JSON.stringify(val.inputPort) !== JSON.stringify(inputForMethodsData));
    });
    localStorage.setItem('mlai_methodMappings', JSON.stringify(newMethodMappings));

    var selectedMethods = JSON.parse(localStorage.getItem('mlai_selectedMethods')) || [];
    var newSelectedMethods = selectedMethods.filter(function(val){
        return (JSON.stringify(val) !== JSON.stringify(methodDataColumnsData.id));
    });
    localStorage.setItem('mlai_selectedMethods', JSON.stringify(newSelectedMethods));

    // $('#mlai_methodDataColumns')
    //     .prepend($("<option></option>")
    //         .attr("value", methodDataColumnsData.id)
    //         .attr("title", methodDataColumnsData.description)
    //         .attr("data-value", JSON.stringify(methodDataColumnsData))
    //         .text(methodDataColumnsData.title));

    $('#mlai_inputForMethods')
        .prepend($("<option></option>")
            .attr("value", inputForMethodsData.id)
            .attr("title", inputForMethodsData.description)
            .attr("is-required", inputForMethodsData.required)
            .attr("data-value", JSON.stringify(inputForMethodsData))
            .text(inputForMethodsData.title + " (" + inputForMethodsData.type + ")"));

    var methodDataColumnsOptionSize = $('#mlai_methodDataColumns option').size();
    var inputForMethodsOptionSize = $('#mlai_inputForMethods option').size();

    if (methodDataColumnsOptionSize > 0 && inputForMethodsOptionSize > 0) {
        $('#mlai_addMethodMapping').prop('disabled', false);
    }
    $(column).closest('tr').remove();
    toggleVisibilityMethodMappingTable();
    event.stopPropagation();
}

function getMLAIVisualizationMethodInputs(isMethodChanged) {
    var chartTypeId = $("#mlai_selectedChartType").val();
    var engineSelect = $("#mlai_EngineSelect").val();

    isMethodChanged = isMethodChanged || false;

    if(chartTypeId && engineSelect) {
        $("#mlai_inputForVisualizerSpinner").show();

        $.ajax({
            type: "GET",
            url: "/engine/getVisualizationMethodInputs?frameworkId=" + engineSelect + "&methodId=" + chartTypeId,
            dataType: "json",
            success: function (response) {
                var visualizationInputSelect = $('#mlai_inputForVisualizer');
                visualizationInputSelect.empty();

                $('#mlai_visualizerMappingTable >tbody').empty();
                localStorage.removeItem("mlai_visualizationMappings");
                toggleMLAIVisibilityVisualizerMappingTable();

                for (var i = 0; i < response.length; i++) {
                    visualizationInputSelect
                        .append($("<option></option>")
                            .attr("value", response[i].id)
                            .attr("title", response[i].description)
                            .attr("is-required", response[i].required)
                            .attr("data-value", JSON.stringify(response[i]))
                            .text(response[i].title + " (" + response[i].type + ")"));
                }
                $("select#mlai_inputForVisualizer")[0].selectedIndex = 0;

                var inputForVisualizerOptionSize = $('#mlai_inputForVisualizer option').size();
                if (inputForVisualizerOptionSize > 0) {
                    $('#mlai_addVisualizationMapping').prop('disabled', false);
                }

                $("#mlai_inputForVisualizerSpinner").hide();
            }
        });
    }
}

function addMLAIVisualizationMappingToTable() {

    var outputForMethodsSelect = $('#mlai_outputForMethods');
    var inputForVisualizerSelect = $('#mlai_inputForVisualizer');

    if (outputForMethodsSelect.val() && inputForVisualizerSelect.val()) {

        var outputForMethodsData = outputForMethodsSelect.find(':selected').data('value');
        var inputForVisualizerData = inputForVisualizerSelect.find(':selected').data('value');

        var row = "<tr data-methodcols='" + JSON.stringify(outputForMethodsData) + "' data-visualdata='" + JSON.stringify(inputForVisualizerData) + "'>" +
            "<td>" + outputForMethodsData.title + " (" + outputForMethodsData.type + ")" + "</td>" +
            "<td>" + inputForVisualizerData.title + " (" + inputForVisualizerData.type + ")" + "</td>" +
            "<td><i class='material-icons' onclick='deleteMLAIVisualizerMappingTableRow(this, event);'>close</i></td>" +
            "</tr>";

        var visualizationMappings = JSON.parse(localStorage.getItem('mlai_visualizationMappings')) || [];
        visualizationMappings.push({outputPort: outputForMethodsData, inputPort: inputForVisualizerData});
        localStorage.setItem('mlai_visualizationMappings', JSON.stringify(visualizationMappings));

        // $("#outputForMethods option:selected").remove();
        $("#inputForVisualizer option:selected").remove();
        var outputForMethodsOptionSize = $('#mlai_outputForMethods option').size();
        var inputForVisualizerOptionSize = $('#mlai_inputForVisualizer option').size();

        if (outputForMethodsOptionSize == 0 || inputForVisualizerOptionSize == 0) {
            $('#mlai_addVisualizationMapping').prop('disabled', true);
        }

        $('#mlai_visualizerMappingTable > tbody:last').append(row);
        toggleMLAIVisibilityVisualizerMappingTable();
    }

    $('#mlai_inputForVisualizer').valid();
}

function toggleMLAIVisibilityVisualizerMappingTable() {
    var rowCount = $('#mlai_visualizerMappingTable >tbody >tr').length;
    if (rowCount == 0) {
        $('#mlai_visualizerMappingTable').hide();
    } else {
        $('#mlai_visualizerMappingTable').show();
    }
}

function deleteMLAIVisualizerMappingTableRow(column, event) {

    var outputForMethodsData = $(column).parent().parent().data('methodcols');
    var inputForVisualizerData = $(column).parent().parent().data('visualdata');

    var visualizationMappings = JSON.parse(localStorage.getItem('mlai_visualizationMappings'));
    var newVisualizationMappings = visualizationMappings.filter(function(val){
        return (JSON.stringify(val.outputPort) !== JSON.stringify(outputForMethodsData) &&
        JSON.stringify(val.inputPort) !== JSON.stringify(inputForVisualizerData));
    });
    localStorage.setItem('mlai_visualizationMappings', JSON.stringify(newVisualizationMappings));

    // $('#mlai_outputForMethods')
    //     .prepend($("<option></option>")
    //         .attr("value", outputForMethodsData.id)
    //         .attr("title", outputForMethodsData.description)
    //         .attr("data-value", JSON.stringify(outputForMethodsData))
    //         .text(outputForMethodsData.title));

    $('#mlai_inputForVisualizer')
        .prepend($("<option></option>")
            .attr("value", inputForVisualizerData.id)
            .attr("title", inputForVisualizerData.description)
            .attr("is-required", inputForVisualizerData.required)
            .attr("data-value", JSON.stringify(inputForVisualizerData))
            .text(inputForVisualizerData.title + " (" + inputForVisualizerData.type + ")"));

    var outputForMethodsOptionSize = $('#mlai_outputForMethods option').size();
    var inputForVisualizerOptionSize = $('#mlai_inputForVisualizer option').size();

    if (outputForMethodsOptionSize > 0 && inputForVisualizerOptionSize > 0) {
        $('#mlai_addVisualizationMapping').prop('disabled', false);
    }
    $(column).closest('tr').remove();
    toggleMLAIVisibilityVisualizerMappingTable();
    event.stopPropagation();
}

function populateMLAIVisualizationFrameworks(data) {

    data = data || false;

    var request = createRequest();
    var url = "/engine/listAllVisualizationFrameworks";
    request.open("GET",url,true);
    request.onreadystatechange=function(){processReceivedMLAIVisualizationFrameworks(request, data)};
    request.send(null);

}

function processReceivedMLAIVisualizationFrameworks(request, data) {
    if (request.readyState == 4) {
        if (request.status == 200) {
            var parsedJSON = JSON.parse(request.responseText);
            var visualizationFrameworksSelection = document.getElementById("mlai_EngineSelect");
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
                $('#mlai_EngineSelect').val(dataObj.selectedChartEngine);
                // $('#selectedChartType').val(dataObj.selectedChartType);
                populateMLAIVisualizationMethods(data);
                // loadVisualizationMappingToTable(data);
            }
        }
    }
}

function populateMLAIVisualizationMethods(data) {

    data = data || false;

    $('#mlai_selectedChartTypeSpinner').show();

    var engineSelect = $("#mlai_EngineSelect").val();
    var request = createRequest();
    var url = "/engine/getVisualizationMethods?frameworkId=" + engineSelect;
    request.open("GET",url,true);
    request.onreadystatechange=function(){processReceivedMLAIVisualizationMethods(request, data)};
    request.send(null);

}

function processReceivedMLAIVisualizationMethods(request, data) {
    if (request.readyState == 4) {
        if (request.status == 200) {
            var parsedJSON = JSON.parse(request.responseText);

            // var visualizationFrameworksSelection = document.getElementById("EngineSelect");
            var chartTypeSelection = document.getElementById("mlai_selectedChartType");
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

            $('#mlai_selectedChartTypeSpinner').hide();

            if (data) {
                var dataObj = JSON.parse(data);
                // $('#EngineSelect').val(dataObj.selectedChartEngine);
                $('#mlai_selectedChartType').val(dataObj.selectedChartType);

                loadMLAIVisualizationMappingToTable(data);
            }
        }
    }
}

function loadMLAIVisualizationMappingToTable(data) {

    data = data || false;

    if(data) {

        $("#mlai_visualizerMappingTable > tbody:last").children().remove();

        var dataObj = JSON.parse(data);
        var methodToVisualizationConfig = dataObj.methodToVisualizationConfig;

        // var visualizationMappings = JSON.parse(localStorage.getItem('visualizationMappings')) || [];
        var visualizationMappings = [];

        for (var i = 0; i < methodToVisualizationConfig.mapping.length; i++) {

            var outputForMethodsData = methodToVisualizationConfig.mapping[i].outputPort;
            var inputForVisualizerData = methodToVisualizationConfig.mapping[i].inputPort;

            var row = "<tr data-methodcols='" + JSON.stringify(outputForMethodsData) + "' data-visualdata='" + JSON.stringify(inputForVisualizerData) + "'>" +
                "<td>" + outputForMethodsData.title + "</td>" +
                "<td>" + inputForVisualizerData.title + "</td>" +
                "<td><i class='material-icons' onclick='deleteMLAIVisualizerMappingTableRow(this, event);'>close</i></td>" +
                "</tr>";

            visualizationMappings.push({outputPort: outputForMethodsData, inputPort: inputForVisualizerData});

            $('#mlai_inputForVisualizer option[value="' + inputForVisualizerData.id + '"]').remove();
            $('#mlai_visualizerMappingTable > tbody:last').append(row);
        }

        localStorage.setItem('mlai_visualizationMappings', JSON.stringify(visualizationMappings));

        var outputForMethodsOptionSize = $('#mlai_outputForMethods option').size();
        var inputForVisualizerOptionSize = $('#mlai_inputForVisualizer option').size();
        if (outputForMethodsOptionSize == 0 || inputForVisualizerOptionSize == 0) {
            $('#mlai_addVisualizationMapping').prop('disabled', true);
        }
        toggleMLAIVisibilityVisualizerMappingTable();
    }
}

function getMLAIIndicatorPreviewVisualizationCode() {

    $('#mlai_preview_msg').empty();

    if ($('#GQSelectionForm').valid() && $('#MLAIForm').valid()) {

        slideToElement("mlai_previewChartLabel");

        $('#preview_chart').hide();
        $('#graphLoaderSpinner').show();
        $("#generateGraph").attr('disabled', 'disabled');

        var methodMappings = localStorage.getItem('mlai_methodMappings') || "";
        var visualizationMappings = localStorage.getItem('mlai_visualizationMappings') || "";
        var selectedMethods = localStorage.getItem('mlai_selectedMethods') || "";
        if(selectedMethods.length > 0)
            selectedMethods = JSON.parse(selectedMethods).join(',');

        var request = createRequest();
        var url = "/engine/getIndicatorPreviewVisualizationCode?width=xxxwidthxxx"
            + "&height=xxxheightxxx"
            + "&analyticsMethodId=" + $("#mlai_analyticsMethod").val()
            + "&EngineSelect=" + $("#mlai_EngineSelect").val()
            + "&selectedChartType=" + $("#mlai_selectedChartType").val()
            + "&indicatorNaming=" + $("#mlai_indicatorNaming").val()
            + "&methodMappings=" + methodMappings
            + "&visualizationMappings=" + visualizationMappings
            + "&selectedMethods=" + selectedMethods;
        request.open("GET", url, true);
        request.onreadystatechange = function () {
            embedMLAIIndicatorPreviewVisualizationCode(request)
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


        formValidator = $('#MLAIForm').data("validator");
        invalidElements = formValidator.invalidElements(); // contain the invalid elements

        for (i = 0; i < invalidElements.length; i++) {
            if($(invalidElements[i]).is("select"))
                msg = msg + "<p id='"+invalidElements[i].id+"_msg'><a onclick='slideToElement(\""+ invalidElements[i].id +"\")'>" + $(invalidElements[i]).prev("label").attr("data-error") + "</a></p>";
            else
                msg = msg + "<p id='"+invalidElements[i].id+"_msg'><a onclick='slideToElement(\""+ invalidElements[i].id +"\")'>" + $(invalidElements[i]).next("label").attr("data-error") + "</a></p>";
        }

        //msg = msg.substring(0,msg.length-4);

        $('#mlai_preview_msg').html(msg);
    }
}

function embedMLAIIndicatorPreviewVisualizationCode(request) {
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
                $('#mlai_preview_chart').show();
                $("#mlai_preview_chart").html(decodedGraphData);
            }
            catch(err) {
                $("#mlai_preview_chart").append("<span>" + err.message + "</span>");
            }


            // }
        }
    }
}

//function finalizeMLAIIndicator(filterPresent) {
function finalizeMLAIIndicator() {
    $(".chip").removeClass('chip-bg');

    //if(filterPresent) {

    $('#loading-screen').removeClass('loader-hide');
    var request = createRequest();
    var goalId = document.getElementById("GoalSelection").value;
    var questionName = document.getElementById("questionNaming").value;
    var indicatorName = document.getElementById("indicatorNaming").value;
    var graphType = document.getElementById("selectedChartType").value;
    var graphEngine = document.getElementById("EngineSelect").value;
    var analyticsMethod = document.getElementById("analyticsMethod").value;

    var indicatorIndex = localStorage.getItem("mlai_selectedIndicatorIdentifier");
    var methodMappings = localStorage.getItem('mlai_methodMappings') || "";
    var visualizationMappings = localStorage.getItem('mlai_visualizationMappings') || "";

    var selectedMethods = localStorage.getItem('mlai_selectedMethods') || "";
    if(selectedMethods.length > 0)
        selectedMethods = JSON.parse(selectedMethods).join(',');

    $('#saveIndicator').prop('disabled', true);
    $.ajax({
        type: "GET",
        url: "/indicators/finalize?goalId="+goalId+"&questionName="+questionName+"&indicatorName="+indicatorName+"&graphType="+graphType
        +"&graphEngine="+graphEngine+"&indicatorIdentifier="+indicatorIndex+"&analyticsMethod="+analyticsMethod + "&methodMappings=" + methodMappings
        + "&visualizationMappings=" + visualizationMappings + "&selectedMethods=" + selectedMethods,
        dataType: "json",
        success: function (response) {
            $('#loading-screen').addClass('loader-hide');

            postrefreshQuestionSummary(response);

            $('#mlai_saveIndicator').prop('disabled', false);
            $(function() {
                $("#mlai_indicatorDefinition").hide();
                $('body').animate({
                    scrollTop: $("body").offset().top
                }, 1000);
            });
        }
    });

    // }
    // else
    //     checkForDefaultRule(2);

}

function addFirstMethod(){
    $('#addFirstMethodModal').openModal();
}

function saveFirstMethod(){

}