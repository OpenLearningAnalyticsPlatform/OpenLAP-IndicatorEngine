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

    populateMLAISources();
    populateMLAIPlatforms();
    populateMLAIActions();
    populateMLAIDSAnalyticsMethods();

    $('#mlai_selectedMinorSpinner').hide();
    $('#mlai_inputForMethodsSpinner').hide();
    $('#mlai_selectedChartTypeSpinner').hide();
    $("#mlai_inputForVisualizerSpinner").hide();
    $("#mlai_entityValueSpinner").hide();

    $("#mlaids_selectedMinorSpinner").hide();
    $("#mlaids_entityValueSpinner").hide();
    $("#mlaids_inputForMethodsSpinner").hide();
    $("#mlaids_filterAttributeSpinner").hide();

    $('#mlaids_appliedAttributeFiltersDiv').empty();
    $('#mlaids_appliedAttributeFiltersDiv').hide();
    $('#mlaids_appliedAttributeFiltersLabel').hide();

    $('#mlaids_analyticsMethodDesc').empty();
    $('#mlaids_analyticsMethod option:selected').prop('selected', false);

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
            },
            mlai_combineMethodsSelection: {
                singleDatasetRemaining: true
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

    $('#MLAIDSForm').validate({
        ignore: false,
        onkeyup: false,
        ignoreTitle: true,
        rules: {
            mlaids_datasetName: {
                required: true,
                maxlength: 10
            },
            mlaids_analyticsMethod: {
                required: true
            },
            mlaids_inputForMethods: {
                hasNoOptionLeft: true
            },
            mlaids_inputForVisualizer: {
                hasNoOptionLeft: true
            }
        },
        messages: {
            mlaids_datasetName: {
                required: "Dataset short name is required.",
                maxlength: "Dataset shourt name should be of maximum 10 characters."
            },
            mlaids_analyticsMethod: {
                required: "Select analytics method."
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

        $("#indicatorDefinition").hide();
        $("#comp_indicatorDefinition").hide();

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

function openDSModel(mode){

    localStorage.setItem('mlaids_open_mode', mode);

    $.ajax({
        type: "GET",
        url: "/engine/initializeDataSource",
        dataType: "text",
        success: function (response) {
            $('#mlaids_datasourceId').val(response);
            $('#addFirstMethodModal').openModal({starting_top:'0%'});
        }
    });
}

function cancelDSModel(){
    var dsid = $("#mlaids_datasourceId").val();

    var mode = localStorage.getItem('mlaids_open_mode');

    if(mode == "new") {
        $.ajax({
            type: "GET",
            url: "/engine/cancelDataSource?dsid=" + dsid,
            dataType: "json",
            success: function (response) {
                postDatasetAdding(response);

                $('#mlaids_datasourceId').val("");
                $('#addFirstMethodModal').closeModal();
                clearMLAIDSIndicatorArea();
            }
        });
    }
    else if (mode == "edit") {
        $('#mlaids_datasourceId').val("");
        $('#addFirstMethodModal').closeModal();
        clearMLAIDSIndicatorArea();
    }

    localStorage.removeItem("mlaids_open_mode");
}

function loadMLAIIndicatorTemplate(selectedIndicator) {

    $('#loading-screen').removeClass('loader-hide');

    $("#mlai_preview_chart").hide();

    var indicatorData = JSON.parse(selectedIndicator.parameters);

    localStorage.setItem('mlai_combinedDatasets', JSON.stringify(indicatorData.combineMappingList));
    $('#mlai_indicatorNaming').val(selectedIndicator.name);

    postDatasetAdding(indicatorData);

    populateMLAIAnalyticsMethods(selectedIndicator.parameters);



    $('#loading-screen').addClass('loader-hide');
}

function populateMLAISources(){
    $.ajax({
        type: "GET",
        url: "/engine/listAllEventSources",
        dataType: "json",
        success: function (response) {
            var element = $('#mlaids_sourceSelection');
            element.empty();

            for (var i=0;i< response.length;i++) {
                element
                    .append($("<option></option>")
                        .attr("value", response[i])
                        .text(response[i]));
            }
            $("select#mlaids_sourceSelection")[0].selectedIndex = 0;
        }
    });
}

function populateMLAIPlatforms(){
    $.ajax({
        type: "GET",
        url: "/engine/listAllEventPlatforms",
        dataType: "json",
        success: function (response) {
            var element = $('#mlaids_PlatformSelection');
            element.empty();

            for (var i=0;i< response.length;i++) {
                element
                    .append($("<option></option>")
                        .attr("value", response[i])
                        .text(response[i]));
            }
            $("select#mlaids_PlatformSelection")[0].selectedIndex = 0;
        }
    });
}

function populateMLAIActions(){
    $.ajax({
        type: "GET",
        url: "/engine/listAllEventActions",
        dataType: "json",
        success: function (response) {
            var element = $('#mlaids_actionSelection');
            element.empty();

            for (var i=0;i< response.length;i++) {
                element
                    .append($("<option></option>")
                        .attr("value", response[i])
                        .text(response[i]));
            }
            $("select#mlaids_actionSelection")[0].selectedIndex = 0;
        }
    });
}

function populateMLAIDSCategories(selectedValue){
    var selectedValue = selectedValue || null;

    var spinner = $('#mlaids_selectedMinorSpinner');
    spinner.show();

    var dsid = $("#mlaids_datasourceId").val();

    var selectedSources = $("#mlaids_sourceSelection").val();
    var selectedSource = selectedSources.join();

    var selectedActions = $('#mlaids_actionSelection').val();
    var selectedAction = selectedActions.join();

    var selectedPlatforms = $('#mlaids_PlatformSelection').val();
    var selectedPlatform = selectedPlatforms.join();

    $.ajax({
        context: true,
        type: "GET",
        url: "/engine/getDistinctCategories?action="+selectedAction+"&platform="+selectedPlatform+"&source="+selectedSource+"&dsid="+dsid,
        dataType: "json",
        success: function (response) {
            var selectedMinor = $('#mlaids_selectedMinor');
            selectedMinor.empty();

            $.each(response, function(k, v) {
                selectedMinor.append($('<option>', {value:v, text:k}));
            });

            if(selectedValue !== null) {
                selectedMinor.val(selectedValue);
            }

            spinner.hide();

            getMLAIDataColumns();
        }
    });
}

function getMLAIDataColumns() {
    var selectedMinors = $("#mlaids_selectedMinor").val();
    var dsid = $("#mlaids_datasourceId").val();

    if(selectedMinors){
        $("#mlaids_filterAttributeSpinner").show();

        var selectedMinor = selectedMinors.join();

        var selectedSources = $("#mlaids_sourceSelection").val();
        var selectedSource = selectedSources.join();

        var selectedActions = $('#mlaids_actionSelection').val();
        var selectedAction = selectedActions.join();

        var selectedPlatforms = $('#mlaids_PlatformSelection').val();
        var selectedPlatform = selectedPlatforms.join();

        $.ajax({
            type: "GET",
            url: "/engine/getDataColumnsByCatIDs?categoryIDs=" + selectedMinor + "&action="+selectedAction+"&platform="+selectedPlatform+"&source="+selectedSource + "&dsid=" + dsid,
            dataType: "json",
            success: function (response) {

                var entityKeySelection = $('#mlaids_entityKeySelection');
                entityKeySelection.empty();

                var methodDataColumnsSelect = $('#mlaids_methodDataColumns');
                methodDataColumnsSelect.empty();

                var dividerNotAdded = true;

                for (var i=0;i< response.length;i++) {

                    if(response[i].required == false){
                        entityKeySelection
                            .append($("<option></option>")
                                .attr("value", response[i].id)
                                .attr("title", response[i].description)
                                .attr("data-value", JSON.stringify(response[i]))
                                .text(response[i].title));

                        if(dividerNotAdded) {
                            methodDataColumnsSelect
                                .append($("<option disabled>──────────────────</option>"));
                            dividerNotAdded = false;
                        }
                    }

                    methodDataColumnsSelect
                        .append($("<option></option>")
                            .attr("value", response[i].id)
                            .attr("title", response[i].description)
                            .attr("data-value", JSON.stringify(response[i]))
                            .text(response[i].title + " (" + response[i].type + ")" ));
                }
                $("select#mlaids_methodDataColumns")[0].selectedIndex = 0;

                $("#mlaids_filterAttributeSpinner").hide();
            }
        });
    }
}

function populateMLAIAnalyticsMethods(data){

    data = data || false;

    $.ajax({
        type: "GET",
        url: "/engine/listAllAnalyticsMethods",
        dataType: "json",
        success: function (response) {
            var analyticsMethodSelection = document.getElementById("mlai_analyticsMethod");
            removeOptions(analyticsMethodSelection);

            for (var i=0;i< response.length;i++) {
                var newOption = new Option(response[i].name, response[i].id);
                newOption.setAttribute('title', response[i].description);
                analyticsMethodSelection.appendChild(newOption);
            }
            analyticsMethodSelection.selectedIndex = -1;

            if (data) {
                var dataObj = JSON.parse(data);
                $('#mlai_analyticsMethod').val(dataObj.indicatorDataset[0].analyticsMethodId);
                getMLAIAnalyticsMethodInputs(data);
            }

            populateMLAIVisualizationFrameworks(data);
        }
    });
}

function populateMLAIDSAnalyticsMethods(data, dsid){

    data = data || false;

    $.ajax({
        type: "GET",
        url: "/engine/listAllAnalyticsMethods",
        dataType: "json",
        success: function (response) {
            var analyticsMethodSelection = document.getElementById("mlaids_analyticsMethod");
            removeOptions(analyticsMethodSelection);

            for (var i=0;i< response.length;i++) {
                var newOption = new Option(response[i].name, response[i].id);
                newOption.setAttribute('title', response[i].description);
                analyticsMethodSelection.appendChild(newOption);
            }
            analyticsMethodSelection.selectedIndex = -1;
            if (data) {
                var dataObj = JSON.parse(data);
                $('#mlaids_analyticsMethod').val(dataObj.indicatorDataset[dsid].analyticsMethodId);
                getMLAIDSAnalyticsMethodInputs(data, dsid);
            }
        }
    });
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
            localStorage.removeItem("mlai_selectedMethods");
            toggleMLAIVisibilityMethodMappingTable()

            for (var i=0;i< response.length;i++) {
                inputForMethodsSelect
                    .append($("<option></option>")
                        .attr("value", response[i].id)
                        .attr("title", response[i].description + (response[i].required?" (Required)":" (Optional)"))
                        .attr("is-required", response[i].required)
                        .attr("data-value", JSON.stringify(response[i]))
                        .attr("class",response[i].required?"required-column":"optional-column")
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

    getMLAIAnalyticsMethodParams(data);
}

function getMLAIAnalyticsMethodParams(data) {
    data = data || false;

    var analyticsMethodId = $("#mlai_analyticsMethod").val();
    $.ajax({
        type: "GET",
        url: "/engine/getAnalyticsMethodParams?id=" + analyticsMethodId,
        dataType: "json",
        success: function (response) {
            renderDynamicParams("mlai_methodDynamicParams", response, data, 0);
        },
        error: function (response) {
            $('#methodDynamicParams').empty();
            $('#methodDynamicParams').append("<div class='select-desc' style='margin-top: 5px;'><span>No additional parameters</span></div>");
            //$("#methodDynamicParamsRow").hide();
        }
    });
}

function getMLAIDSAnalyticsMethodInputs(data, dsid) {

    data = data || false;

    var spinner = $('#mlaids_inputForMethodsSpinner');
    spinner.show();

    var analyticsMethodId = $("#mlaids_analyticsMethod").val();
    $.ajax({
        type: "GET",
        url: "/engine/getAnalyticsMethodInputs?id=" + analyticsMethodId,
        dataType: "json",
        success: function (response) {
            var inputForMethodsSelect = $('#mlaids_inputForMethods');
            inputForMethodsSelect.empty();

            $('#mlaids_methodMappingTable >tbody').empty();
            localStorage.removeItem("mlaids_methodMappings");
            localStorage.removeItem("mlaids_selectedMethods");
            toggleMLAIDSVisibilityMethodMappingTable()

            for (var i=0;i< response.length;i++) {
                inputForMethodsSelect
                    .append($("<option></option>")
                        .attr("value", response[i].id)
                        .attr("title", response[i].description + (response[i].required?" (Required)":" (Optional)"))
                        .attr("is-required", response[i].required)
                        .attr("data-value", JSON.stringify(response[i]))
                        .attr("class",response[i].required?"required-column":"optional-column")
                        .text(response[i].title + " (" + response[i].type + ")" ));
            }
            $("select#mlaids_inputForMethods")[0].selectedIndex = 0;

            var inputForMethodOptionSize = $('#mlaids_inputForMethods option').size();
            if (inputForMethodOptionSize > 0) {
                $('#mlaids_addMethodMapping').prop('disabled', false);
            }

            spinner.hide();

            if (data) {
                loadMLAIDSMethodMappingToTable(data,dsid);
            }
        }
    });

    getMLAIDSAnalyticsMethodParams(data, dsid);
}

function getMLAIDSAnalyticsMethodParams(data, dsid) {
    data = data || false;

    var analyticsMethodId = $("#mlaids_analyticsMethod").val();
    $.ajax({
        type: "GET",
        url: "/engine/getAnalyticsMethodParams?id=" + analyticsMethodId,
        dataType: "json",
        success: function (response) {
            renderDynamicParams("mlaids_methodDynamicParams", response, data, dsid);
        },
        error: function (response) {
            $('#mlaids_methodDynamicParams').empty();
            $('#mlaids_methodDynamicParams').append("<div class='select-desc' style='margin-top: 5px;'><span>No additional parameters</span></div>");
            //$("#methodDynamicParamsRow").hide();
        }
    });
}

function updateMLAIAnalyticsMethodDesc() {
    $('#mlai_analyticsMethodDesc').html('<i class="material-icons">info_outline</i>' + "&nbsp;" + $('#mlai_analyticsMethod').find('option:selected').attr('title'));
}

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

    $('#mlai_addMethodMapping_msg').empty();

    var methodDataColumnsSelect = $('#mlai_methodDataColumns');
    var inputForMethodsSelect = $('#mlai_inputForMethods');

    if (methodDataColumnsSelect.val() && inputForMethodsSelect.val()) {

        var methodDataColumnsData = methodDataColumnsSelect.find(':selected').data('value');
        var inputForMethodsData = inputForMethodsSelect.find(':selected').data('value');

        if(validateMapping(methodDataColumnsData, inputForMethodsData)) {
            var row = "<tr data-methodcols='" + JSON.stringify(methodDataColumnsData) + "' data-methodsdata='" + JSON.stringify(inputForMethodsData) + "'>" +
                "<td>" + methodDataColumnsData.title + " (" + methodDataColumnsData.type + ")" + "</td>" +
                "<td>" + inputForMethodsData.title + " (" + inputForMethodsData.type + ")" + "</td>" +
                "<td><i class='material-icons' onclick='deleteMLAIMethodMappingTableRow(this, event);'>close</i></td>" +
                "</tr>";

            var methodMappings = JSON.parse(localStorage.getItem('mlai_methodMappings')) || [];
            methodMappings.push({outputPort: methodDataColumnsData, inputPort: inputForMethodsData});
            localStorage.setItem('mlai_methodMappings', JSON.stringify(methodMappings));

            //console.log(methodDataColumnsData);
            //only adding to the selected Methods if required is false means the selected column is from Entity table
            if (methodDataColumnsData.required == false) {
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
        else{
            $('#mlai_addMethodMapping_msg').html("<p>Fields of the same type can be added.</p>");
        }
    }
    else{
        $('#mlai_addMethodMapping_msg').html("<p>Select a field from 'Combined Outputs' and from 'Inputs for Method' before adding.</p>");
    }

    $('#mlai_inputForMethods').valid();
}

function loadMLAIMethodMappingToTable(data) {

    data = data || false;

    if(data) {

        $("#mlai_methodMappingTable > tbody:last").children().remove();

        var dataObj = JSON.parse(data);
        var queryToMethodConfig = dataObj.indicatorDataset[0].queryToMethodConfig;

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
            .attr("title", inputForMethodsData.description + (inputForMethodsData.required?" (Required)":" (Optional)"))
            .attr("is-required", inputForMethodsData.required)
            .attr("data-value", JSON.stringify(inputForMethodsData))
            .attr("class",inputForMethodsData.required?"required-column":"optional-column")
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
                            .attr("title", response[i].description + (response[i].required?" (Required)":" (Optional)"))
                            .attr("is-required", response[i].required)
                            .attr("data-value", JSON.stringify(response[i]))
                            .attr("class",response[i].required?"required-column":"optional-column")
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

    $('#mlai_addVisualizationMapping_msg').empty();

    var outputForMethodsSelect = $('#mlai_outputForMethods');
    var inputForVisualizerSelect = $('#mlai_inputForVisualizer');

    if (outputForMethodsSelect.val() && inputForVisualizerSelect.val()) {

        var outputForMethodsData = outputForMethodsSelect.find(':selected').data('value');
        var inputForVisualizerData = inputForVisualizerSelect.find(':selected').data('value');

        if(validateMapping(outputForMethodsData, inputForVisualizerData)) {
            var row = "<tr data-methodcols='" + JSON.stringify(outputForMethodsData) + "' data-visualdata='" + JSON.stringify(inputForVisualizerData) + "'>" +
                "<td>" + outputForMethodsData.title + " (" + outputForMethodsData.type + ")" + "</td>" +
                "<td>" + inputForVisualizerData.title + " (" + inputForVisualizerData.type + ")" + "</td>" +
                "<td><i class='material-icons' onclick='deleteMLAIVisualizerMappingTableRow(this, event);'>close</i></td>" +
                "</tr>";

            var visualizationMappings = JSON.parse(localStorage.getItem('mlai_visualizationMappings')) || [];
            visualizationMappings.push({outputPort: outputForMethodsData, inputPort: inputForVisualizerData});
            localStorage.setItem('mlai_visualizationMappings', JSON.stringify(visualizationMappings));

            // $("#mlai_outputForMethods option:selected").remove();
            $("#mlai_inputForVisualizer option:selected").remove();
            var outputForMethodsOptionSize = $('#mlai_outputForMethods option').size();
            var inputForVisualizerOptionSize = $('#mlai_inputForVisualizer option').size();

            if (outputForMethodsOptionSize == 0 || inputForVisualizerOptionSize == 0) {
                $('#mlai_addVisualizationMapping').prop('disabled', true);
            }

            $('#mlai_visualizerMappingTable > tbody:last').append(row);
            toggleMLAIVisibilityVisualizerMappingTable();
        }
        else{
            $('#mlai_addVisualizationMapping_msg').html("<p>Fields of the same type can be added.</p>");
        }

    }
    else{
        $('#mlai_addVisualizationMapping_msg').html("<p>Select a field from 'Outputs of Method' and from 'Inputs of Visualization' before adding.</p>");
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
            .attr("title", inputForVisualizerData.description + (inputForVisualizerData.required?" (Required)":" (Optional)"))
            .attr("is-required", inputForVisualizerData.required)
            .attr("data-value", JSON.stringify(inputForVisualizerData))
            .attr("class",inputForVisualizerData.required?"required-column":"optional-column")
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
                $('#mlai_EngineSelect').val(dataObj.visualizationLibrary);
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
                $('#mlai_selectedChartType').val(dataObj.visualizationType);

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

    var isSingleDatasetRemaining = true;
    if($("#mlai_combineMethodsSelection").find(".valign-wrapper").length !== 1)
        isSingleDatasetRemaining = false;
    else
        $("#mlai_combineMethodsSelection").removeClass("invalidBorder");


    var isQuestionValid = $('#GQSelectionForm').valid();
    var isFormValid = $('#MLAIForm').valid();

    if (isQuestionValid && isFormValid && isSingleDatasetRemaining) {

        slideToElement("mlai_previewChartLabel");

        $('#mlai_preview_chart').hide();
        $('#mlai_graphLoaderSpinner').show();
        $("#mlai_generateGraph").attr('disabled', 'disabled');

        var methodMappings = localStorage.getItem('mlai_methodMappings') || "";
        var visualizationMappings = localStorage.getItem('mlai_visualizationMappings') || "";
        var combineMapping = localStorage.getItem('mlai_combinedDatasets') || "";

        var methodParams = getDynamicParamsValues("mlai_methodDynamicParams");
        var visParams = "";


        var request = createRequest();
        var url = "/engine/getMLAIPreviewVisualizationCode?width=xxxwidthxxx"
            + "&height=xxxheightxxx"
            + "&analyticsMethodId=" + $("#mlai_analyticsMethod").val()
            + "&EngineSelect=" + $("#mlai_EngineSelect").val()
            + "&selectedChartType=" + $("#mlai_selectedChartType").val()
            + "&indicatorNaming=" + $("#mlai_indicatorNaming").val()
            + "&methodMappings=" + methodMappings
            + "&visualizationMappings=" + visualizationMappings
            + "&combineMapping=" + combineMapping
            + "&methodParams=" + methodParams
            + "&visParams=" + visParams;

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

        if(!isSingleDatasetRemaining) {
            msg = msg + "<p id='mlai_combineMethodsSelection_errmsg'><a onclick='slideToElement(\"mlai_combineMethodsSelection\")'>Please combine all first level analysis.</a></p>";
            $("#mlai_combineMethodsSelection").addClass("invalidBorder");
        }
        //msg = msg.substring(0,msg.length-4);

        $('#mlai_preview_msg').html(msg);
    }
}

function embedMLAIIndicatorPreviewVisualizationCode(request) {
    if (request.readyState == 4) {
        if (request.status == 200) {

            $('#mlai_graphLoaderSpinner').hide();

            var parsedJSON = JSON.parse(request.responseText);
            var decodedGraphData;
            if(parsedJSON.isSuccess) {
                decodedGraphData = decodeURIComponent(parsedJSON.visualizationCode);
                decodedGraphData = decodedGraphData.replace("xxxwidthxxx","$('#chart_wrap').outerWidth(true)");
                decodedGraphData = decodedGraphData.replace("xxxheightxxx","$('#chart_wrap').outerHeight(true)");
            }
            else {
                decodedGraphData = '<div class="alert alert-error">' + parsedJSON.errorMessage + '</div>';
            }

            $("#mlai_generateGraph").removeAttr('disabled');

            try {
                $('#mlai_preview_chart').show();
                $("#mlai_preview_chart").html(decodedGraphData);
            }
            catch(err) {
                $("#mlai_preview_chart").append("<span>" + err.message + "</span>");
            }
        }
    }
}

function finalizeMLAIIndicator() {
    $(".chip").removeClass('chip-bg');

    $('#loading-screen').removeClass('loader-hide');

    var goalId = document.getElementById("GoalSelection").value;
    var questionName = document.getElementById("questionNaming").value;

    $('#mlai_preview_chart').hide();
    $('#mlai_graphLoaderSpinner').show();
    $("#mlai_generateGraph").attr('disabled', 'disabled');

    var methodMappings = localStorage.getItem('mlai_methodMappings') || "";
    var visualizationMappings = localStorage.getItem('mlai_visualizationMappings') || "";
    var combineMapping = localStorage.getItem('mlai_combinedDatasets') || "";

    var methodParams = getDynamicParamsValues("mlai_methodDynamicParams");
    var visParams = "";

    var indicatorIdentifier = localStorage.getItem("selectedIndicatorIdentifier");

    var url = "/engine/finalizeMLAIIndicator"
        + "?goalId=" + goalId
        + "&questionName=" + questionName
        + "&indicatorNaming=" + $("#mlai_indicatorNaming").val()
        + "&indicatorIdentifier=" + indicatorIdentifier
        + "&analyticsMethodId=" + $("#mlai_analyticsMethod").val()
        + "&EngineSelect=" + $("#mlai_EngineSelect").val()
        + "&selectedChartType=" + $("#mlai_selectedChartType").val()
        + "&methodMappings=" + methodMappings
        + "&visualizationMappings=" + visualizationMappings
        + "&combineMapping=" + combineMapping
        + "&methodParams=" + methodParams
        + "&visParams=" + visParams;

    $('#mlai_saveIndicator').prop('disabled', true);
    $.ajax({
        type: "GET",
        url: url,
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
}

function searchMLAIDSAttributeValues(){
    $("#mlaids_entityValueSpinner").show();

    var key = $('#mlaids_entityKeySelection').val();

    var selectedSources = $("#mlaids_sourceSelection").val();
    var selectedSource = selectedSources.join();

    var selectedActions = $('#mlaids_actionSelection').val();
    var selectedAction = selectedActions.join();

    var selectedPlatforms = $('#mlaids_PlatformSelection').val();
    var selectedPlatform = selectedPlatforms.join();

    var selectedCategories = $('#mlaids_selectedMinor').val();
    var selectedCategory = selectedCategories.join();

    $.ajax({
        context: true,
        type: "GET",
        url: "/engine/getAttributesValues?categoryIDs="+selectedCategory+"&key="+key+"&action="+selectedAction+"&source="+selectedSource+"&platform="+selectedPlatform,
        dataType: "json",
        success: function (response) {
            var entityValue = document.getElementById("mlaids_entityValue");
            removeOptions(entityValue);
            for (var i=0;i< response.length;i++) {
                var newOption = new Option(response[i], response[i]);
                entityValue.appendChild(newOption);
            }

            $("#mlaids_entityValueSpinner").hide();
        }
    });
}

function addMLAIDSEntity(){
    //var request = createRequest();
    var keySelected = document.getElementById("mlaids_entityKeySelection").value;
    var selectedTitle = $('#mlaids_entityKeySelection :selected').text();
    var selectedValues = $('#mlaids_entityValue').val();
    var selectedJoinedValues = selectedValues.join();

    var dsid = $("#mlaids_datasourceId").val();

    $.ajax({
        context: true,
        type: "GET",
        url: "/indicators/addEntity?key="+keySelected+"&value="+selectedJoinedValues+"&title="+selectedTitle+"&dsid="+dsid,
        dataType: "json",
        success: function (response) {
            var entityValues = response;
            $('#mlaids_appliedAttributeFiltersDiv').empty();

            if (entityValues.length == 0) {
                $('#mlaids_appliedAttributeFiltersDiv').hide();
                $('#mlaids_appliedAttributeFiltersLabel').hide();
                return;
            }
            $('#mlaids_appliedAttributeFiltersDiv').show();
            $('#mlaids_appliedAttributeFiltersLabel').show();


            for (var entityValuesIndex = 0; entityValuesIndex < entityValues.length; entityValuesIndex++) {

                var entityValue = entityValues[entityValuesIndex];

                var etKey = entityValue.key;
                var etValue = entityValue.eValues;
                var etTitle = entityValue.title;

                // + "id='" + etKey + "_" + etValue.replace(" ", "_") + "'"
                $('#mlaids_appliedAttributeFiltersDiv').append("<div class='chip filter-chip' "
                    + "data='" + etKey + "~" + etValue + "' "
                    + "<span>" + etTitle + ": " + etValue
                    + "</span><i class='material-icons' onclick='showDeleteEntityFilterModal(this, event);'>close</i></div>");
            }
        }
    });
}

function loadMLAIEntityFilters(entityValues) {

    $('#mlaids_appliedAttributeFiltersDiv').empty();

    if (entityValues.length == 0) {
        $('#mlaids_appliedAttributeFiltersDiv').hide();
        $('#mlaids_appliedAttributeFiltersLabel').hide();
        return;
    }

    $('#mlaids_appliedAttributeFiltersDiv').show();
    $('#mlaids_appliedAttributeFiltersLabel').show();

    for (var entityValuesIndex = 0; entityValuesIndex < entityValues.length; entityValuesIndex++) {

        var entityValue = entityValues[entityValuesIndex];

        var etKey = entityValue.key;
        var etValue = entityValue.eValues;
        var etTitle = entityValue.title;

        // + "id='" + etKey + "_" + etValue.replace(" ", "_") + "' "
        $('#mlaids_appliedAttributeFiltersDiv').append("<div class='chip filter-chip' "
            + "data='" + etKey + "~" + etValue + "' "
            + "<span>" + etTitle + ": " + etValue
            + "</span><i class='material-icons' onclick='showDeleteEntityFilterModal(this, event);'>close</i></div>");
    }
}

function addMLAIDSTimeFilter() {
    var dateType = $("#mlaids_dateType").val();
    var dateFilterVal = $("#mlaids_dateFilterVal").val();

    var dsid = $("#mlaids_datasourceId").val();

    var date = new Date(dateFilterVal).getTime()/1000|0; //Converting selected date to unix timestamp

    $.ajax({
        type: "GET",
        url: "/indicators/addTimeFilter?time=" + date + "&timeType=" + dateType+"&dsid="+dsid,
        dataType: "json",
        success: function (timeFilterResponse) {
            loadMLAIDSTimeFilters(timeFilterResponse);
        }
    });
}

function MLAIUserFilterChanged() {
    var checkId = $("#mlaids_userFilterRadioDiv input:checked").attr("id");
    var dsid = $("#mlaids_datasourceId").val();

    var userFilter = "all";
    var userHash = $("#rid").val();

    if(checkId == "mlaids_userFilterMy")
        userFilter = "mine";
    else if(checkId == "mlaids_userFilterOthers")
        userFilter = "notmine";

    $.ajax({
        type: "GET",
        url: "/indicators/setUserFilter?userFilter=" + userFilter + "&userHash=" + userHash + "&dsid=" + dsid,
        dataType: "json",
        success: function (userFilterResponse) {
            //console.log(userFilterResponse);
        }
    });
}

// function MLAIUserFilterChanged() {
//     var checkId = $("#mlaids_userFilterRadioDiv input:checked").attr("id");
//     var dsid = $("#mlaids_datasourceId").val();
//
//     if(checkId == "mlaids_userFilterAll"){
//         $("#mlaids_userFilterRadioAction").hide();
//
//         $.ajax({
//             type: "GET",
//             url: "/indicators/setUserFilter?userFilter=all&userHash=&dsid="+dsid,
//             dataType: "json",
//             success: function (userFilterResponse) {
//                 console.log("user filter set to all");
//             }
//         });
//     }
//     else if(checkId == "mlaids_userFilterMy"){
//         $("#mlaids_userFilterRadioAction").show();
//     }
//     else if(checkId == "mlaids_userFilterOthers"){
//         $("#mlaids_userFilterRadioAction").show();
//     }
// }
//
// function setMLAIUserFilter() {
//     var checkId = $("#mlaids_userFilterRadioDiv input:checked").attr("id");
//     var dsid = $("#mlaids_datasourceId").val();
//
//     var userFilter = "all";
//     var userHash = $("#mlaids_userEncryptedHash").val();
//
//     if(checkId == "mlaids_userFilterMy")
//         userFilter = "mine";
//     else if(checkId == "mlaids_userFilterOthers")
//         userFilter = "notmine";
//
//     $.ajax({
//         type: "GET",
//         url: "/indicators/setUserFilter?userFilter=" + userFilter + "&userHash=" + userHash + "&dsid=" + dsid,
//         dataType: "json",
//         success: function (userFilterResponse) {
//             console.log("user filter set: " + userFilter + "-" + userHash);
//         }
//     });
// }

function toggleMLAIDSVisibilityMethodMappingTable() {
    var rowCount = $('#mlaids_methodMappingTable >tbody >tr').length;
    if (rowCount == 0) {
        $('#mlaids_methodMappingTable').hide();
    } else {
        $('#mlaids_methodMappingTable').show();
    }
}

function loadMLAIDSMethodMappingToTable(data, dsid) {

    data = data || false;

    if(data) {

        $("#mlaids_methodMappingTable > tbody:last").children().remove();

        var dataObj = JSON.parse(data);
        var queryToMethodConfig = dataObj.indicatorDataset[dsid].queryToMethodConfig;

        var selectedMethods = JSON.parse(localStorage.getItem('mlaids_selectedMethods')) || [];
        var methodMappings = [];

        for (var i = 0; i < queryToMethodConfig.mapping.length; i++) {

            var methodDataColumnsData = queryToMethodConfig.mapping[i].outputPort;
            var inputForMethodsData = queryToMethodConfig.mapping[i].inputPort;

            var row = "<tr data-methodcols='" + JSON.stringify(methodDataColumnsData) + "' data-methodsdata='" + JSON.stringify(inputForMethodsData) + "'>" +
                "<td>" + methodDataColumnsData.title + "</td>" +
                "<td>" + inputForMethodsData.title + "</td>" +
                "<td><i class='material-icons' onclick='deleteMLAIDSMethodMappingTableRow(this, event);'>close</i></td>" +
                "</tr>";

            methodMappings.push({outputPort: methodDataColumnsData, inputPort: inputForMethodsData});

            if(methodDataColumnsData.required == false) {
                var index = selectedMethods.indexOf(methodDataColumnsData.id);
                if (index < 0) {
                    selectedMethods.push(methodDataColumnsData.id);
                }
            }

            $('#mlaids_inputForMethods option[value="' + inputForMethodsData.id + '"]').remove();
            $('#mlaids_methodMappingTable > tbody:last').append(row);
        }

        localStorage.setItem('mlaids_methodMappings', JSON.stringify(methodMappings));
        localStorage.setItem('mlaids_selectedMethods', JSON.stringify(selectedMethods));

        var methodDataColumnsOptionSize = $('#mlaids_methodDataColumns option').size();
        var inputForMethodsOptionSize = $('#mlaids_inputForMethods option').size();
        if (methodDataColumnsOptionSize == 0 || inputForMethodsOptionSize == 0) {
            $('#mlaids_addMethodMapping').prop('disabled', true);
        }
        toggleMLAIDSVisibilityMethodMappingTable();
    }
}

function deleteMLAIDSMethodMappingTableRow(column, event) {

    var methodDataColumnsData = $(column).parent().parent().data('methodcols');
    var inputForMethodsData = $(column).parent().parent().data('methodsdata');

    var methodMappings = JSON.parse(localStorage.getItem('mlaids_methodMappings'));
    var newMethodMappings = methodMappings.filter(function(val){
        return (JSON.stringify(val.outputPort) !== JSON.stringify(methodDataColumnsData) &&
        JSON.stringify(val.inputPort) !== JSON.stringify(inputForMethodsData));
    });
    localStorage.setItem('mlaids_methodMappings', JSON.stringify(newMethodMappings));

    var selectedMethods = JSON.parse(localStorage.getItem('mlaids_selectedMethods')) || [];
    var newSelectedMethods = selectedMethods.filter(function(val){
        return (JSON.stringify(val) !== JSON.stringify(methodDataColumnsData.id));
    });
    localStorage.setItem('mlaids_selectedMethods', JSON.stringify(newSelectedMethods));

    $('#mlaids_inputForMethods')
        .prepend($("<option></option>")
            .attr("value", inputForMethodsData.id)
            .attr("title", inputForMethodsData.description + (inputForMethodsData.required?" (Required)":" (Optional)"))
            .attr("is-required", inputForMethodsData.required)
            .attr("data-value", JSON.stringify(inputForMethodsData))
            .attr("class",inputForMethodsData.required?"required-column":"optional-column")
            .text(inputForMethodsData.title + " (" + inputForMethodsData.type + ")"));

    var methodDataColumnsOptionSize = $('#mlaids_methodDataColumns option').size();
    var inputForMethodsOptionSize = $('#mlaids_inputForMethods option').size();

    if (methodDataColumnsOptionSize > 0 && inputForMethodsOptionSize > 0) {
        $('#mlaids_addMethodMapping').prop('disabled', false);
    }
    $(column).closest('tr').remove();
    toggleMLAIDSVisibilityMethodMappingTable();
    event.stopPropagation();
}

function addMLAIDSMethodMappingToTable() {

    $('#mlaids_addMethodMapping_msg').empty();

    var methodDataColumnsSelect = $('#mlaids_methodDataColumns');
    var inputForMethodsSelect = $('#mlaids_inputForMethods');

    if (methodDataColumnsSelect.val() && inputForMethodsSelect.val()) {

        var methodDataColumnsData = methodDataColumnsSelect.find(':selected').data('value');
        var inputForMethodsData = inputForMethodsSelect.find(':selected').data('value');

        if(validateMapping(methodDataColumnsData, inputForMethodsData)) {
            var row = "<tr data-methodcols='" + JSON.stringify(methodDataColumnsData) + "' data-methodsdata='" + JSON.stringify(inputForMethodsData) + "'>" +
                "<td>" + methodDataColumnsData.title + " (" + methodDataColumnsData.type + ")" + "</td>" +
                "<td>" + inputForMethodsData.title + " (" + inputForMethodsData.type + ")" + "</td>" +
                "<td><i class='material-icons' onclick='deleteMethodMappingTableRow(this, event);'>close</i></td>" +
                "</tr>";

            var methodMappings = JSON.parse(localStorage.getItem('mlaids_methodMappings')) || [];
            methodMappings.push({outputPort: methodDataColumnsData, inputPort: inputForMethodsData});
            localStorage.setItem('mlaids_methodMappings', JSON.stringify(methodMappings));

            //console.log(methodDataColumnsData);
            //only adding to the selected Methods if required is false means the selected column is from Entity table
            if (methodDataColumnsData.required == false) {
                var selectedMethods = JSON.parse(localStorage.getItem('mlaids_selectedMethods')) || [];

                var index = selectedMethods.indexOf(methodDataColumnsData.id);
                if (index < 0) {
                    selectedMethods.push(methodDataColumnsData.id);
                    localStorage.setItem('mlaids_selectedMethods', JSON.stringify(selectedMethods));
                }
            }
            // $("#methodDataColumns option:selected").remove();
            $("#mlaids_inputForMethods option:selected").remove();
            var methodDataColumnsOptionSize = $('#mlaids_methodDataColumns option').size();
            var inputForMethodsOptionSize = $('#mlaids_inputForMethods option').size();

            if (methodDataColumnsOptionSize == 0 || inputForMethodsOptionSize == 0) {
                $('#mlaids_addMethodMapping').prop('disabled', true);
            }

            $('#mlaids_methodMappingTable > tbody:last').append(row);
            toggleMLAIDSVisibilityMethodMappingTable();
        }
        else{
            $('#mlaids_addMethodMapping_msg').html("<p>Fields of the same type can be added.</p>");
        }
    }
    else{
        $('#mlaids_addMethodMapping_msg').html("<p>Select a field from 'Data Columns' and from 'Inputs for Method' before adding.</p>");
    }

    $('#mlaids_inputForMethods').valid();
}

function saveFirstMethod(){
    $('#mlaids_preview_msg').empty();

    if ($('#MLAIDSForm').valid()) {

        var methodMappings = localStorage.getItem('mlaids_methodMappings') || "";
        var selectedMethods = localStorage.getItem('mlaids_selectedMethods') || "";
        if(selectedMethods.length > 0)
            selectedMethods = JSON.parse(selectedMethods).join(',');

        var datasetName = document.getElementById("mlaids_datasetName").value;
        var analyticsMethod = document.getElementById("mlaids_analyticsMethod").value;

        var dsid = $("#mlaids_datasourceId").val();
        var methodMappings = localStorage.getItem('mlaids_methodMappings') || "";

        var selectedMethods = localStorage.getItem('mlaids_selectedMethods') || "";
        if(selectedMethods.length > 0)
            selectedMethods = JSON.parse(selectedMethods).join(',');

        var methodParams = getDynamicParamsValues("mlaids_methodDynamicParams");

        $('#saveIndicator').prop('disabled', true);
        $.ajax({
            type: "GET",
            url: "/engine/finalizeDataSource?datasetName=" + datasetName
            + "&dsid=" + dsid + "&analyticsMethod=" + analyticsMethod + "&methodMappings=" + methodMappings
            + "&methodParams=" + methodParams + "&selectedMethods=" + selectedMethods,
            dataType: "json",
            success: function (response) {
                postDatasetAdding(response);

                $('#mlaids_datasourceId').val("");
                $('#addFirstMethodModal').closeModal();
                clearMLAIDSIndicatorArea();

                localStorage.removeItem("mlaids_open_mode");
            }
        });
    }
    else{
        var msg = "";

        formValidator = $('#MLAIDSForm').data("validator");
        invalidElements = formValidator.invalidElements(); // contain the invalid elements


        for (i = 0; i < invalidElements.length; i++) {
            if($(invalidElements[i]).is("select"))
                msg = msg + "<p id='"+invalidElements[i].id+"_msg'>" + $(invalidElements[i]).prev("label").attr("data-error") + "</p>";
            else
                msg = msg + "<p id='"+invalidElements[i].id+"_msg'>" + $(invalidElements[i]).next("label").attr("data-error") + "</p>";
        }

        $('#mlaids_preview_msg').html(msg);
    }
}

function postDatasetAdding(parsedJSON) {
    $('#mlai_firstMethodDiv').empty();
    $('#mlai_combineMethodsSelection').empty();

    if(parsedJSON == null || Object.keys(parsedJSON.indicatorDataset).length < 2) {
        $('#mlai_firstMethodDiv').empty();
        $('#mlai_firstMethodDiv').append("<span class='smallgraytext'>No analysis defined.</span>");

        $('#mlai_combineMethodsSelection').empty();
        $('#mlai_combineMethodsSelection').append("<span class='smallgraytext'>No analysis defined.</span>");
    }

    $("#mlai_method1outputsLabel").html("Outputs of: ");
    $("#mlai_method1outputs").empty();
    $('#mlai_method1outputsId').removeAttr('value');

    $("#mlai_method2outputsLabel").html("Outputs of: ");
    $("#mlai_method2outputs").empty();
    $('#mlai_method2outputsId').removeAttr('value');

    if(parsedJSON != null) {
        for (var key in parsedJSON.indicatorDataset) {
            if(key != "0") {
                $('#mlai_firstMethodDiv').append("<div class='chip' name='dataset-" + key + "' id='dataset-" + key + "' data='" + key
                    + "' onclick='loadDataset(this);'><span >" + parsedJSON.indicatorDataset[key].datasetName
                    + "</span><i class='material-icons' onclick='showDeleteDatasetModal(this, event);'>close</i></div>");


                $('#mlai_combineMethodsSelection').append("<div class='valign-wrapper' id='mlai_dscheckdiv_"+key
                    +"'><div><input type='checkbox' id='mlai_dscheck_"+key
                    +"' onclick='getFirstLevelMethodOutputs(this);' methodId='" + parsedJSON.indicatorDataset[key].analyticsMethodId
                    + "' datasetKey='"+key+"' combineLevel='0' /><label for='mlai_dscheck_"+key+"'>"+parsedJSON.indicatorDataset[key].datasetName+"</label></div></div>");

            }
        }

        var datasetsDivs = $('#mlai_combineMethodsSelection').find(".valign-wrapper");
        if(datasetsDivs.length == 1) {
            var dataset1Checkbox = $(datasetsDivs[0]).find("input");
            $(dataset1Checkbox).prop("checked", true);
            getFirstLevelMethodOutputs($(dataset1Checkbox).get(0));
        }
        else if(datasetsDivs.length > 1){
            clearCombinedOutputs();
            var combinedDatasets = JSON.parse(localStorage.getItem('mlai_combinedDatasets')) || [];

            if(combinedDatasets.length > 0) {
                var count = 50;
                while (combinedDatasets.length > 0) {
                    var dataset1Checkbox = document.getElementById("mlai_dscheck_" + combinedDatasets[0].indRefKey1);
                    var dataset2Checkbox = document.getElementById("mlai_dscheck_" + combinedDatasets[0].indRefKey2);

                    if (dataset1Checkbox && dataset2Checkbox) {
                        $(dataset1Checkbox).prop("checked", true);
                        $(dataset2Checkbox).prop("checked", true);

                        getFirstLevelMethodOutputs(dataset1Checkbox, dataset2Checkbox, combinedDatasets[0], false);

                        combinedDatasets.splice(0, 1);
                    }
                    else {
                        combinedDatasets.splice(combinedDatasets.length - 1, 0, combinedDatasets.splice(0, 1)[0]);
                    }

                    if (count-- == 0) break;
                }
            }
        }
    }
}

function loadDataset(datasetelement){

    //localStorage.setItem('mlaids_selectedMethods', "[]");

    localStorage.removeItem("mlaids_methodMappings");
    localStorage.removeItem("mlaids_selectedMethods");

    var dsid = $(datasetelement).attr("data");

    $.ajax({
        type: "GET",
        url: "/engine/getIndicatorParameters",
        dataType: "json",
        success: function (response) {
            if (response == null) {
                datasetelement.parentElement.removeChild(datasetelement);
            }
            else {
                $('#mlaids_datasourceId').val($(datasetelement).attr("data"));
                setDatasetModalWithData(response, dsid);
                localStorage.setItem('mlaids_open_mode', "edit");
                $('#addFirstMethodModal').openModal();
            }
        }
    });
}

function setDatasetModalWithData(data, dsid) {
    $('#mlaids_datasetName').val(data.indicatorDataset[dsid].datasetName);
    $('#mlaids_sourceSelection').val(data.indicatorDataset[dsid].selectedSource);
    $('#mlaids_PlatformSelection').val(data.indicatorDataset[dsid].selectedPlatform);
    $('#mlaids_actionSelection').val(data.indicatorDataset[dsid].selectedAction);

    var entityValues = data.indicatorDataset[dsid].entityValues;
    var userSpecs = data.indicatorDataset[dsid].userSpecifications;
    var sessionSpecs = data.indicatorDataset[dsid].sessionSpecifications;
    var timeSpecs = data.indicatorDataset[dsid].timeSpecifications;

    loadMLAIDSEntityFilters(entityValues);
    //loadMLAIDSSessionFilters(sessionSpecs);
    loadMLAIDSTimeFilters(timeSpecs);
    loadMLAIDSAssociatedUserFilters(userSpecs);

    populateMLAIDSAnalyticsMethods(JSON.stringify(data), dsid);

    populateMLAIDSCategories(data.indicatorDataset[dsid].selectedMinor);
}

function loadMLAIDSEntityFilters(entityValues) {

    $('#mlaids_appliedAttributeFiltersDiv').empty();

    if (entityValues.length == 0) {
        $('#mlaids_appliedAttributeFiltersDiv').hide();
        $('#mlaids_appliedAttributeFiltersLabel').hide();
        return;
    }

    $('#mlaids_appliedAttributeFiltersDiv').show();
    $('#mlaids_appliedAttributeFiltersLabel').show();

    for (var entityValuesIndex = 0; entityValuesIndex < entityValues.length; entityValuesIndex++) {

        var entityValue = entityValues[entityValuesIndex];

        var etKey = entityValue.key;
        var etValue = entityValue.eValues;
        var etTitle = entityValue.title;

        $('#mlaids_appliedAttributeFiltersDiv').append("<div class='chip filter-chip' "
            + "data='" + etKey + "~" + etValue + "' "
            + "<span>" + etTitle + ": " + etValue
            + "</span><i class='material-icons' onclick='showDeleteEntityFilterModal(this, event);'>close</i></div>");
    }
}

// function loadMLAIDSSessionFilters(sessionSpecs) {
//
//     $('#appliedSessionFiltersDiv').empty();
//     if (sessionSpecs.length == 0) {
//         $('#appliedSessionFiltersDiv').hide();
//         $('#appliedSessionFiltersLabel').hide();
//         return;
//     }
//     // if (sessionSpecs.length > 0) {
//     $('#appliedSessionFiltersDiv').show();
//     $('#appliedSessionFiltersLabel').show();
//     // }
//
//     for (var sessionSpecsIndex = 0;
//          sessionSpecsIndex < sessionSpecsIndex.length;
//          sessionSpecsIndex++) {
//         var sessionSpec = sessionSpecs[sessionSpecsIndex];
//         $('#appliedSessionFiltersDiv').append("<div class='chip filter-chip'"
//             + "' id='session-" + sessionSpecsIndex + "' title='Session-" + sessionSpecsIndex + "-" + sessionSpec.key + "'>" +
//             "<span>Session-" + sessionSpecsIndex + "-" + sessionSpec.key
//             + "</span><i class='material-icons' onclick='deleteIndicator(this, event);'>close</i></div>");
//     }
// }

function loadMLAIDSTimeFilters(timeSpecs) {

    $('#mlaids_appliedUserTimeFiltersDiv').empty();

    if (timeSpecs.length == 0) {
        $('#mlaids_appliedUserTimeFiltersDiv').hide();
        $('#mlaids_appliedUserTimeFiltersLabel').hide();
        return;
    }

    $('#mlaids_appliedUserTimeFiltersDiv').show();
    $('#mlaids_appliedUserTimeFiltersLabel').show();

    for (var timeSpecsIndex = 0; timeSpecsIndex < timeSpecs.length; timeSpecsIndex++) {
        var timeSpec = timeSpecs[timeSpecsIndex];
        var parsedDate = new Date(parseInt(timeSpec.timestamp)*1000);
        var formattedDate = parsedDate.toDateString();

        var timeTitle = "";
        if(timeSpec.type == "fromDate")
            timeTitle = "Start Date";
        else if(timeSpec.type == "toDate")
            timeTitle = "End Date";

        $('#mlaids_appliedUserTimeFiltersDiv').append("<div class='chip filter-chip'"
            + " data='" + timeSpec.type + "~" + timeSpec.timestamp + "'>" +
            "<span>" + timeTitle + ": " + formattedDate
            + "</span><i class='material-icons' onclick='showDeleteTimeFilterModal(this, event);'>close</i></div>");
    }
}

function loadMLAIDSAssociatedUserFilters(userSpecs) {
    if(userSpecs == null || userSpecs.length ==0 ){
        $("#mlaids_userFilterAll").prop("checked", true);
    }
    else{
        var userSpec = userSpecs[0];

        if(userSpec.key == "mine"){
            $("#mlaids_userFilterMy").prop("checked", true)
        }
        else if(userSpec.key == "notmine"){
            $("#mlaids_userFilterOthers").prop("checked", true)
        }
    }
}

function showDeleteDatasetModal(filter, event) {
    $("#deleteDatasetValue").val($(filter).closest('div').attr("data"));
    $('#confirmDatasetDeleteModal').openModal();
    event.stopPropagation();
}

function deleteDataset() {

    var datasetKey = $("#deleteDatasetValue").val();

    var combinedDatasets = JSON.parse(localStorage.getItem('mlai_combinedDatasets')) || [];

    var keyToRemove = datasetKey;

    while (keyToRemove != null) {
        var keyBeingRemoved = keyToRemove;
        for (i = combinedDatasets.length - 1; i > -1; i--) {
            if (combinedDatasets[i].indRefKey1 == keyToRemove || combinedDatasets[i].indRefKey2 == keyToRemove) {
                if (combinedDatasets[i].indRefKey1.indexOf("-") > -1)
                    keyToRemove = "("+combinedDatasets[i].indRefKey1+")-"+combinedDatasets[i].indRefKey2;
                else
                    keyToRemove = combinedDatasets[i].indRefKey1+"-"+combinedDatasets[i].indRefKey2;

                combinedDatasets.splice(i, 1);
            }
        }

        if(keyBeingRemoved == keyToRemove)
            keyToRemove = null;
    }

    localStorage.setItem('mlai_combinedDatasets', JSON.stringify(combinedDatasets));

    $.ajax({
        type: "GET",
        url: "/engine/cancelDataSource",
        data: {dsid: datasetKey},
        dataType: "json",
        success: function (response) {
            postDatasetAdding(response);

            $('#mlaids_datasourceId').val("");
            $('#addFirstMethodModal').closeModal();
            clearMLAIDSIndicatorArea();
        }
    });
}

function getFirstLevelMethodOutputs(checkbox, checkbox2, data, isSecond) {
    var analyticsMethodId = $(checkbox).attr("methodId");
    var datasetKey = $(checkbox).attr("datasetKey");
    var datasetName = $(checkbox).next().html();

    var selected1Id = $("#mlai_method1outputsId").val();
    var selected2Id = $("#mlai_method2outputsId").val();

    if(checkbox.checked) {
        if (selected1Id && selected2Id) {
            checkbox.checked = false;
            alert('Only two analaytics methods outputs can be combined at a time.')
        }
        else {
            if($(checkbox).attr("columnData")) {
                var columnData = $(checkbox).attr("columnData");
                var response = JSON.parse(columnData);

                var selectElementName;

                if(!selected1Id)
                    selectElementName = '#mlai_method1outputs';
                else
                    selectElementName = '#mlai_method2outputs';

                var methodOutputSelect = $(selectElementName);
                methodOutputSelect.empty();
                for (var i = 0; i < response.length; i++) {
                    methodOutputSelect
                        .append($("<option></option>")
                            .attr("value", response[i].id)
                            .attr("title", response[i].description)
                            .attr("data-value", JSON.stringify(response[i]))
                            .text(response[i].title + " (" + response[i].type + ")"));
                }
                $(selectElementName).selectedIndex = 0;

                $(selectElementName+"Label").html("Outputs of: " + datasetName);
                $(selectElementName+"Id").val(datasetKey)

                if(data) {
                    if(!isSecond){
                        methodOutputSelect.val(data.indRefField1);
                        getFirstLevelMethodOutputs(checkbox2, null, data, true);
                    }
                    else{
                        methodOutputSelect.val(data.indRefField2);
                        combineDatasets();
                    }
                }
                else{
                    if($('#mlai_combineMethodsSelection').find(".valign-wrapper").length == 1) {
                        showCombinedOutputs(response);
                    }
                }


            }
            else {
                $.ajax({
                    type: "GET",
                    url: "/engine/getAnalyticsMethodOutputs?id=" + analyticsMethodId,
                    dataType: "json",
                    success: function (response) {
                        $(checkbox).attr("columnData", JSON.stringify(response));

                        var selectElementName;

                        if(!selected1Id)
                            selectElementName = '#mlai_method1outputs';
                        else
                            selectElementName = '#mlai_method2outputs';

                        var methodOutputSelect = $(selectElementName);
                        methodOutputSelect.empty();
                        for (var i = 0; i < response.length; i++) {
                            methodOutputSelect
                                .append($("<option></option>")
                                    .attr("value", response[i].id)
                                    .attr("title", response[i].description)
                                    .attr("data-value", JSON.stringify(response[i]))
                                    .text(response[i].title + " (" + response[i].type + ")"));
                        }
                        $(selectElementName).selectedIndex = 0;

                        $(selectElementName+"Label").html("Outputs of: " + datasetName);
                        $(selectElementName+"Id").val(datasetKey)

                        if(data) {
                            if(!isSecond){
                                methodOutputSelect.val(data.indRefField1);
                                getFirstLevelMethodOutputs(checkbox2, null, data, true);
                            }
                            else{
                                methodOutputSelect.val(data.indRefField2);
                                combineDatasets();
                            }
                        }
                        else{
                            if($('#mlai_combineMethodsSelection').find(".valign-wrapper").length == 1) {
                                showCombinedOutputs(response);
                            }
                        }
                    }
                });
            }
        }
    }
    else {
        if (selected1Id && selected1Id == datasetKey) {
            $("#mlai_method1outputsLabel").html("Outputs of: ");
            $("#mlai_method1outputs").empty();
            $('#mlai_method1outputsId').removeAttr('value');
        }

        if (selected2Id && selected2Id == datasetKey) {
            $("#mlai_method2outputsLabel").html("Outputs of: ");
            $("#mlai_method2outputs").empty();
            $('#mlai_method2outputsId').removeAttr('value');
        }
    }
}

function combineDatasets(){
    $('#mlai_addMergeMapping_msg').empty();

    var selectedCheckboxes = [];
    $('#mlai_combineMethodsSelection input:checked').each(function() {
        selectedCheckboxes.push($(this));
    });

    if(selectedCheckboxes.length < 2) {
        $('#mlai_addMergeMapping_msg').html("<p>select two datasets to combine them.</p>");
    }
    else if (selectedCheckboxes.length > 2) {
        $('#mlai_addMergeMapping_msg').html("<p>select only two datasets to combine them at a time.</p>");
    }
    else if (selectedCheckboxes.length = 2) {
        var combineColumns = [];

        var dataset1Id = $("#mlai_method1outputsId").val();
        var dataset2Id = $("#mlai_method2outputsId").val();

        var dataset1Name = "#mlai_method1outputs";
        var dataset2Name = "#mlai_method2outputs";

        if(parseInt(dataset1Id) > parseInt(dataset2Id)){
            dataset1Name = "#mlai_method2outputs";
            dataset2Name = "#mlai_method1outputs";
        }

        var method1Outputs = $(dataset1Name);
        var method2Outputs = $(dataset2Name);

        var method1Data;
        var method2Data;

        if (method1Outputs.val() && method2Outputs.val()) {
            method1Data = method1Outputs.find(':selected').data('value');
            method2Data = method2Outputs.find(':selected').data('value');

            $(dataset1Name + " option").each(function(i){
                var data = $(this).data('value');

                if(data.id == method1Data.id)
                    data.title = data.title + " - " + method2Data.title;

                combineColumns.push(data)
            });

            $(dataset2Name + " option").each(function(i){
                var data = $(this).data('value');

                if(data.id != method2Data.id) {
                    combineColumns.push(data)
                }
            });
        }

        var dataset1Checkbox = selectedCheckboxes[0];
        var dataset2Checkbox = selectedCheckboxes[1];

        var dataset1Key = $(dataset1Checkbox).attr("datasetKey");
        var dataset2Key = $(dataset2Checkbox).attr("datasetKey");

        var combinedKey = "";
        if(dataset1Key.indexOf("-") == -1 && dataset2Key.indexOf("-") == -1 ) {
            if(parseInt(dataset1Key)<parseInt(dataset2Key))
                combinedKey = dataset1Key + "-" + dataset2Key;
            else
                combinedKey = dataset2Key + "-" + dataset1Key;
        }
        else if(dataset1Key.indexOf("-") > -1) {
            combinedKey = "(" + dataset1Key + ")-" + dataset2Key;
        }
        else if(dataset2Key.indexOf("-") > -1) {
            combinedKey = "(" + dataset2Key + ")-" + dataset1Key;
        }

        var combineKeyId = combinedKey.split(/\(|\)/).join("_");

        var dataset1Level = parseInt($(dataset1Checkbox).attr("combineLevel"));
        var dataset2Level = parseInt($(dataset2Checkbox).attr("combineLevel"));

        var combineLevel = dataset1Level<=dataset2Level?dataset2Level+1:dataset1Level+1;

        // $(dataset1Checkbox).closest(".valign-wrapper").hide();
        // $(dataset1Checkbox).prop("disabled", false);
        // $(dataset1Checkbox).trigger('click');
        //
        // $(dataset2Checkbox).closest(".valign-wrapper").hide();
        // $(dataset2Checkbox).prop("disabled", false);
        // $(dataset2Checkbox).trigger('click');

        var dataset1Title = $(dataset1Checkbox).next().html();
        dataset1Title = dataset1Title.indexOf("-")>-1?"("+dataset1Title+")":dataset1Title;
        var dataset2Title = $(dataset2Checkbox).next().html();
        var currentTitle = dataset1Title + " - "+ dataset2Title;

        $(dataset1Checkbox).trigger('click');
        $(dataset1Checkbox).closest(".valign-wrapper").remove();

        $(dataset2Checkbox).trigger('click');
        $(dataset2Checkbox).closest(".valign-wrapper").remove();

        $('#mlai_combineMethodsSelection').prepend("<div class='valign-wrapper' id='mlai_dscheckdiv_"+combineKeyId
            +"'><div><input type='checkbox' id='mlai_dscheck_"+combineKeyId
            +"' onclick='getFirstLevelMethodOutputs(this);' methodId='"
            + "' datasetKey='"+combinedKey+"' combineLevel='" + combineLevel
            + "' columnData='" + JSON.stringify(combineColumns) + "'/><label for='mlai_dscheck_"+combineKeyId+"'>"
            + currentTitle +"</label></div>"
            + "<i class='material-icons' onclick='deleteCombinedDataset(this);'>close</i></div>");

        $("#mlai_dscheck_"+combineKeyId).trigger('click');
        $("#mlai_dscheck_"+combineKeyId).prop("disabled", true);

        var combinedDatasets = JSON.parse(localStorage.getItem('mlai_combinedDatasets')) || [];
        var newCombinedDatasets = combinedDatasets.filter(function(val){
            return (dataset1Key !== val.indRefKey1 && dataset2Key !== val.indRefKey2);
        });
        newCombinedDatasets.push({indRefKey1: dataset1Key, indRefKey2: dataset2Key, indRefField1: method1Data.id, indRefField2: method2Data.id});

        localStorage.setItem('mlai_combinedDatasets', JSON.stringify(newCombinedDatasets));

        if($('#mlai_combineMethodsSelection').find(".valign-wrapper").length == 1) {
            $("#mlai_combineMethodsSelection").removeClass("invalidBorder");
            showCombinedOutputs(combineColumns);
        }
        else{
            $("#mlai_combineMethodsSelection").addClass("invalidBorder");
        }
    }
    //$('#inputForMethods').valid();
}

function deleteCombinedDataset(closeButton) {
    var curKey = $(closeButton).parent().find("input").attr("datasetKey");

    var dataset1Key = curKey.includes("(")? curKey.substring(1, curKey.lastIndexOf("-")-1):curKey.substring(0, curKey.lastIndexOf("-"));
    var dataset2Key = curKey.substring(curKey.lastIndexOf("-")+1);

    var combinedDatasets = JSON.parse(localStorage.getItem('mlai_combinedDatasets')) || [];

    for (i = combinedDatasets.length-1; i > -1; i--) {
        if(combinedDatasets[i].indRefKey1 == curKey || combinedDatasets[i].indRefKey2 == curKey ||
            (combinedDatasets[i].indRefKey1 == dataset1Key && combinedDatasets[i].indRefKey2 == dataset2Key) ||
            (combinedDatasets[i].indRefKey1 == dataset2Key && combinedDatasets[i].indRefKey2 == dataset1Key ) ) {

            combinedDatasets.splice(i, 1);
        }
    }

    localStorage.setItem('mlai_combinedDatasets', JSON.stringify(combinedDatasets));

    $.ajax({
        type: "GET",
        url: "/engine/getIndicatorParameters",
        dataType: "json",
        success: function (response) {
            postDatasetAdding(response);
        }
    });
}

function showCombinedOutputs(response){

    var inputForMethodsSelect = $('#mlai_methodDataColumns');
    inputForMethodsSelect.empty();

    for (var i=0;i< response.length;i++) {
        inputForMethodsSelect
            .append($("<option></option>")
                .attr("value", response[i].id)
                .attr("title", response[i].description)
                .attr("is-required", response[i].required)
                .attr("data-value", JSON.stringify(response[i]))
                .text(response[i].title + " (" + response[i].type + ")" ));
    }
    $("select#mlai_methodDataColumns")[0].selectedIndex = 0;

    // if (data) {
    //     loadMLAIMethodMappingToTable(data);
    // }
    //
    // getMLAIAnalyticsMethodOutputs();
    //
    // if(!data) {
    //     getMLAIVisualizationMethodInputs();
    // }
}

function clearCombinedOutputs(){
    var inputForMethodsSelect = $('#mlai_methodDataColumns');
    inputForMethodsSelect.empty();
}