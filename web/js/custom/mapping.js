function getDataColumns() {
    var selectedMinors = $("#selectedMinor").val();

    if(selectedMinors){
        $("#filterAttributeSpinner").show();

        var selectedMinor = selectedMinors.join();

        var selectedSources = $("#sourceSelection").val();
        var selectedSource = selectedSources.join();

        var selectedActions = $('#actionSelection').val();
        var selectedAction = selectedActions.join();

        var selectedPlatforms = $('#PlatformSelection').val();
        var selectedPlatform = selectedPlatforms.join();

        $.ajax({
            type: "GET",
            url: "/engine/getDataColumnsByCatIDs?categoryIDs=" + selectedMinor+"&action="+selectedAction+"&platform="+selectedPlatform+"&source="+selectedSource,
            dataType: "json",
            success: function (response) {

                var entityKeySelection = $('#entityKeySelection');
                entityKeySelection.empty();

                var methodDataColumnsSelect = $('#methodDataColumns');
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
                $("select#methodDataColumns")[0].selectedIndex = 0;


                $("#filterAttributeSpinner").hide();
            }
        });
    }
}

function searchAttributeValues(){

    $("#entityValueSpinner").show();

    var key = $('#entityKeySelection').val();

    var selectedSources = $("#sourceSelection").val();
    var selectedSource = selectedSources.join();

    var selectedActions = $('#actionSelection').val();
    var selectedAction = selectedActions.join();

    var selectedPlatforms = $('#PlatformSelection').val();
    var selectedPlatform = selectedPlatforms.join();

    var selectedCategories = $('#selectedMinor').val();
    var selectedCategory = selectedCategories.join();

    $.ajax({
        context: true,
        type: "GET",
        url: "/engine/getAttributesValues?categoryIDs="+selectedCategory+"&key="+key+"&action="+selectedAction+"&source="+selectedSource+"&platform="+selectedPlatform,
        dataType: "json",
        success: function (response) {
            var entityValue = document.getElementById("entityValue");
            removeOptions(entityValue);
            for (var i=0;i< response.length;i++) {
                var newOption = new Option(response[i], response[i]);
                entityValue.appendChild(newOption);
            }

            $("#entityValueSpinner").hide();
        }
    });
}

function getAnalyticsMethodInputs(data) {

    data = data || false;

    var spinner = $('#inputForMethodsSpinner');
    spinner.show();

    var analyticsMethodId = $("#analyticsMethod").val();
    $.ajax({
        type: "GET",
        url: "/engine/getAnalyticsMethodInputs?id=" + analyticsMethodId,
        dataType: "json",
        success: function (response) {
            var inputForMethodsSelect = $('#inputForMethods');
            inputForMethodsSelect.empty();

            $('#methodMappingTable >tbody').empty();
            localStorage.removeItem("methodMappings");
            localStorage.removeItem("selectedMethods");

            toggleVisibilityMethodMappingTable()

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
            $("select#inputForMethods")[0].selectedIndex = 0;

            var inputForMethodOptionSize = $('#inputForMethods option').size();
            if (inputForMethodOptionSize > 0) {
                $('#addMethodMapping').prop('disabled', false);
            }

            spinner.hide();

            if (data) {
                loadMethodMappingToTable(data);
            }

            getAnalyticsMethodOutputs();

            if(!data) {
                getVisualizationMethodInputs();
            }
        }
    });

    getAnalyticsMethodParams(data);
}

function getAnalyticsMethodParams(data) {
    data = data || false;

    var analyticsMethodId = $("#analyticsMethod").val();
    $.ajax({
        type: "GET",
        url: "/engine/getAnalyticsMethodParams?id=" + analyticsMethodId,
        dataType: "json",
        success: function (response) {
            renderDynamicParams("methodDynamicParams", response, data, 0);
        },
        error: function (response) {
            $('#methodDynamicParams').empty();
            $('#methodDynamicParams').append("<div class='select-desc' style='margin-top: 5px;'><span>No additional parameters</span></div>");
            //$("#methodDynamicParamsRow").hide();
        }
    });
}

function renderDynamicParams(divId, params, data, dsid){
    data = data || false;

    var paramDiv = $('#' + divId);
    paramDiv.empty();

    if(params.length>0) {
        for (var i = 0; i < params.length; i++) {
            if (params[i].type == "Textbox") {
                var controlDiv = $("<div>", {"class": "col-md-6 input-field"});
                controlDiv.append($('<input/>')
                    .attr("id", "amdc_" + params[i].id)
                    .attr("name", "amdc_" + params[i].id)
                    .attr("type", "text")
                    .attr("class", "form-control")
                    .attr("param-id", params[i].id)
                    .attr("title", params[i].description)
                    .attr("is-required", params[i].required)
                    .attr("default-value", params[i].defaultValue)
                    .attr("value", params[i].defaultValue)
                );

                controlDiv.append($('<label/>')
                    .attr("for", "amdc_" + params[i].id)
                    .attr("title", params[i].description)
                    .attr("class", "active")
                    .text(params[i].title));

                paramDiv.append(controlDiv);

                //$("#amdc_" + params[i].id).val(params[i].defaultValue);
            }
            else if (params[i].type == "Choice") {
                var controlDiv = $("<div>", {"class": "col-md-6"});
                controlDiv.append($('<label/>')
                    .attr("for", "amdc_" + params[i].id)
                    .attr("title", params[i].description)
                    .text(params[i].title));

                var controlSelect = $("<select>")
                    .attr("id", "amdc_" + params[i].id)
                    .attr("name", "amdc_" + params[i].id)
                    .attr("param-id", params[i].id)
                    .attr("class", "form-control browser-default")
                    .attr("title", params[i].description)
                    .attr("is-required", params[i].required)
                    .attr("default-value", params[i].defaultValue);

                var possibleOptions = params[i].possibleValues.split(',');

                for (var j = 0; j < possibleOptions.length; j++) {
                    controlSelect.append($('<option></option>')
                        .attr("value", possibleOptions[j])
                        .text(possibleOptions[j]));
                }

                controlDiv.append(controlSelect);
                paramDiv.append(controlDiv);

                $("#amdc_" + params[i].id).val(params[i].defaultValue);
            }
            else if (params[i].type == "MultipleChoice") {
                var controlDiv = $("<div>", {"class": "col-md-6"});
                controlDiv.append($('<label/>')
                    .attr("for", "amdc_" + params[i].id)
                    .attr("title", params[i].description)
                    .text(params[i].title));

                var controlSelect = $("<select>")
                    .attr("id", "amdc_" + params[i].id)
                    .attr("name", "amdc_" + params[i].id)
                    .attr("class", "form-control browser-default")
                    .attr("param-id", params[i].id)
                    .attr("title", params[i].description)
                    .attr("multiple", "multiple")
                    .attr("size", "2")
                    .attr("is-required", params[i].required)
                    .attr("default-value", params[i].defaultValue);

                var possibleOptions = params[i].possibleValues.split(',');

                for (var j = 0; j < possibleOptions.length; j++) {
                    controlSelect.append($('<option></option>')
                        .attr("value", possibleOptions[j])
                        .text(possibleOptions[j]));
                }

                controlDiv.append(controlSelect);
                paramDiv.append(controlDiv);

                $("#amdc_" + params[i].id).val(params[i].defaultValue);
            }
            else if (params[i].type == "Checkbox") {
                var controlDiv = $("<div>", {"class": "col-md-6 input-field"});
                controlDiv.append($('<input/>')
                    .attr("id", "amdc_" + params[i].id)
                    .attr("name", "amdc_" + params[i].id)
                    .attr("type", "checkbox")
                    .attr("title", params[i].description)
                    .attr("param-id", params[i].id)
                    .attr("is-required", params[i].required)
                    .attr("default-value", params[i].defaultValue));

                controlDiv.append($('<label/>')
                    .attr("for", "amdc_" + params[i].id)
                    .attr("title", params[i].description)
                    .text(params[i].title));

                paramDiv.append(controlDiv);
                if(params[i].defaultValue == "true")
                    $("#amdc_" + params[i].id).prop('checked', true);
                else
                    $("#amdc_" + params[i].id).prop('checked', false);
            }
        }

        if(data)
            populateDynamicParams(divId, data, dsid);

        //$("#"+divId+"Row").show();
    }
    else{
        //$("#"+divId+"Row").hide();
        paramDiv.append("<div class='select-desc' style='margin-top: 5px;'><span>No additional parameters</span></div>");
    }

}

function populateDynamicParams(divId, data, dsid){
    var paramDiv = $('#' + divId);

    dsid = dsid || 0;

    var parsedData = JSON.parse(data);
    var methodParams;
    try {methodParams = JSON.parse(parsedData.indicatorDataset[dsid].analyticsMethodParams);}
    catch(e) {methodParams = parsedData.indicatorDataset[dsid].analyticsMethodParams;}

    for (var key in methodParams) {
        var control = paramDiv.find("#amdc_" + key);
        if(control){
            if ($(control).is('input:text') || $(control).is('select')) {
                $(control).val(methodParams[key])
            } else if ($(control).is('input:checkbox')) {
                if(methodParams[key] == "true")
                    $(control).prop('checked', true);
                else
                    $(control).prop('checked', false);
            }
        }
    }
}

function getDynamicParamsValues(divId){
    var paramDiv = $('#' + divId);
    var data = {};
    paramDiv.find("[id^='amdc_']").each(function(i) {
        if ($(this).is('input:text') || $(this).is('select')) {
            data[$(this).attr("param-id")] = $(this).val();
        } else if ($(this).is('input:checkbox')) {
            data[$(this).attr("param-id")] = $(this).is(':checked');
        }
    });

    return JSON.stringify(data);
}

function getVisualizationMethodInputs(isMethodChanged) {
    var chartTypeId = $("#selectedChartType").val();
    var engineSelect = $("#EngineSelect").val();

    isMethodChanged = isMethodChanged || false;

    if(chartTypeId && engineSelect) {
        $("#inputForVisualizerSpinner").show();

        $.ajax({
            type: "GET",
            url: "/engine/getVisualizationMethodInputs?frameworkId=" + engineSelect + "&methodId=" + chartTypeId,
            dataType: "json",
            success: function (response) {
                var visualizationInputSelect = $('#inputForVisualizer');
                visualizationInputSelect.empty();

                $('#visualizerMappingTable >tbody').empty();
                localStorage.removeItem("visualizationMappings");
                toggleVisibilityVisualizerMappingTable();

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
                $("select#inputForVisualizer")[0].selectedIndex = 0;

                var inputForVisualizerOptionSize = $('#inputForVisualizer option').size();
                if (inputForVisualizerOptionSize > 0) {
                    $('#addVisualizationMapping').prop('disabled', false);
                }

                $("#inputForVisualizerSpinner").hide();
            }
        });
    }
}

function getAnalyticsMethodOutputs() {

    // $(function() {
        var analyticsMethodId = $("#analyticsMethod").val();
        $.ajax({
            type: "GET",
            url: "/engine/getAnalyticsMethodOutputs?id=" + analyticsMethodId,
            dataType: "json",
            success: function (response) {
                var visualizationOutputSelect = $('#outputForMethods');
                visualizationOutputSelect.empty();
                for (var i=0;i< response.length;i++) {
                    visualizationOutputSelect
                        .append($("<option></option>")
                            .attr("value", response[i].id)
                            .attr("title", response[i].description)
                            .attr("data-value", JSON.stringify(response[i]))
                            .text(response[i].title + " (" + response[i].type + ")" ));
                }
                $("select#outputForMethods")[0].selectedIndex = 0;
            }
        });
    // });
}

function validateMapping(column1Data, column2Data){
    if(column1Data.type == column2Data.type)
        return true;
    else
        return false;
}

function addMethodMappingToTable() {

    $('#addMethodMapping_msg').empty();

    var methodDataColumnsSelect = $('#methodDataColumns');
    var inputForMethodsSelect = $('#inputForMethods');

    if (methodDataColumnsSelect.val() && inputForMethodsSelect.val()) {

        var methodDataColumnsData = methodDataColumnsSelect.find(':selected').data('value');
        var inputForMethodsData = inputForMethodsSelect.find(':selected').data('value');

        if(validateMapping(methodDataColumnsData, inputForMethodsData)){
            var row = "<tr data-methodcols='" + JSON.stringify(methodDataColumnsData) + "' data-methodsdata='" + JSON.stringify(inputForMethodsData) + "'>" +
                "<td>" + methodDataColumnsData.title+ " (" + methodDataColumnsData.type + ")" + "</td>" +
                "<td>" + inputForMethodsData.title + " (" + inputForMethodsData.type + ")"  + "</td>" +
                "<td><i class='material-icons' onclick='deleteMethodMappingTableRow(this, event);'>close</i></td>" +
                "</tr>";

            var methodMappings = JSON.parse(localStorage.getItem('methodMappings')) || [];
            methodMappings.push({outputPort: methodDataColumnsData, inputPort: inputForMethodsData});
            localStorage.setItem('methodMappings', JSON.stringify(methodMappings));

            //console.log(methodDataColumnsData);
            //only adding to the selected Methods if required is false means the selected column is from Entity table
            if(methodDataColumnsData.required == false) {
                var selectedMethods = JSON.parse(localStorage.getItem('selectedMethods')) || [];

                var index = selectedMethods.indexOf(methodDataColumnsData.id);
                if (index < 0) {
                    selectedMethods.push(methodDataColumnsData.id);
                    localStorage.setItem('selectedMethods', JSON.stringify(selectedMethods));
                }
            }
            // $("#methodDataColumns option:selected").remove();
            $("#inputForMethods option:selected").remove();
            var methodDataColumnsOptionSize = $('#methodDataColumns option').size();
            var inputForMethodsOptionSize = $('#inputForMethods option').size();

            if (methodDataColumnsOptionSize == 0 || inputForMethodsOptionSize == 0) {
                $('#addMethodMapping').prop('disabled', true);
            }

            $('#methodMappingTable > tbody:last').append(row);
            toggleVisibilityMethodMappingTable();
        }
        else{
            $('#addMethodMapping_msg').html("<p>Fields of the same type can be added.</p>");
        }

    }
    else{
        $('#addMethodMapping_msg').html("<p>Select a field from 'Data Columns' and from 'Inputs of Method' before adding.</p>");
    }

    $('#inputForMethods').valid();
}

function deleteMethodMappingTableRow(column, event) {

    var methodDataColumnsData = $(column).parent().parent().data('methodcols');
    var inputForMethodsData = $(column).parent().parent().data('methodsdata');

    var methodMappings = JSON.parse(localStorage.getItem('methodMappings'));
    var newMethodMappings = methodMappings.filter(function(val){
        return (JSON.stringify(val.outputPort) !== JSON.stringify(methodDataColumnsData) &&
                JSON.stringify(val.inputPort) !== JSON.stringify(inputForMethodsData));
    });
    localStorage.setItem('methodMappings', JSON.stringify(newMethodMappings));

    var selectedMethods = JSON.parse(localStorage.getItem('selectedMethods')) || [];
    var newSelectedMethods = selectedMethods.filter(function(val){
        return (JSON.stringify(val) !== JSON.stringify(methodDataColumnsData.id));
    });
    localStorage.setItem('selectedMethods', JSON.stringify(newSelectedMethods));

    // $('#methodDataColumns')
    //     .prepend($("<option></option>")
    //         .attr("value", methodDataColumnsData.id)
    //         .attr("title", methodDataColumnsData.description)
    //         .attr("data-value", JSON.stringify(methodDataColumnsData))
    //         .text(methodDataColumnsData.title));

    $('#inputForMethods')
        .prepend($("<option></option>")
            .attr("value", inputForMethodsData.id)
            .attr("title", inputForMethodsData.description + (inputForMethodsData.required?" (Required)":" (Optional)"))
            .attr("is-required", inputForMethodsData.required)
            .attr("data-value", JSON.stringify(inputForMethodsData))
            .attr("class",inputForMethodsData.required?"required-column":"optional-column")
            .text(inputForMethodsData.title + " (" + inputForMethodsData.type + ")"));

    var methodDataColumnsOptionSize = $('#methodDataColumns option').size();
    var inputForMethodsOptionSize = $('#inputForMethods option').size();

    if (methodDataColumnsOptionSize > 0 && inputForMethodsOptionSize > 0) {
        $('#addMethodMapping').prop('disabled', false);
    }
    $(column).closest('tr').remove();
    toggleVisibilityMethodMappingTable();
    event.stopPropagation();
}

function toggleVisibilityMethodMappingTable() {
    var rowCount = $('#methodMappingTable >tbody >tr').length;
    if (rowCount == 0) {
        $('#methodMappingTable').hide();
    } else {
        $('#methodMappingTable').show();
    }
}

function addVisualizationMappingToTable() {

    $('#addVisualizationMapping_msg').empty();

    var outputForMethodsSelect = $('#outputForMethods');
    var inputForVisualizerSelect = $('#inputForVisualizer');

    if (outputForMethodsSelect.val() && inputForVisualizerSelect.val()) {

        var outputForMethodsData = outputForMethodsSelect.find(':selected').data('value');
        var inputForVisualizerData = inputForVisualizerSelect.find(':selected').data('value');

        if(validateMapping(outputForMethodsData, inputForVisualizerData)) {
            var row = "<tr data-methodcols='" + JSON.stringify(outputForMethodsData) + "' data-visualdata='" + JSON.stringify(inputForVisualizerData) + "'>" +
                "<td>" + outputForMethodsData.title + " (" + outputForMethodsData.type + ")" + "</td>" +
                "<td>" + inputForVisualizerData.title + " (" + inputForVisualizerData.type + ")" + "</td>" +
                "<td><i class='material-icons' onclick='deleteVisualizerMappingTableRow(this, event);'>close</i></td>" +
                "</tr>";

            var visualizationMappings = JSON.parse(localStorage.getItem('visualizationMappings')) || [];
            visualizationMappings.push({outputPort: outputForMethodsData, inputPort: inputForVisualizerData});
            localStorage.setItem('visualizationMappings', JSON.stringify(visualizationMappings));

            // $("#outputForMethods option:selected").remove();
            $("#inputForVisualizer option:selected").remove();
            var outputForMethodsOptionSize = $('#outputForMethods option').size();
            var inputForVisualizerOptionSize = $('#inputForVisualizer option').size();

            if (outputForMethodsOptionSize == 0 || inputForVisualizerOptionSize == 0) {
                $('#addVisualizationMapping').prop('disabled', true);
            }

            $('#visualizerMappingTable > tbody:last').append(row);
            toggleVisibilityVisualizerMappingTable();
        }
        else{
            $('#addVisualizationMapping_msg').html("<p>Fields of the same type can be added.</p>");
        }

    }
    else{
        $('#addVisualizationMapping_msg').html("<p>Select a field from 'Outputs of Method' and from 'Inputs of Visualization' before adding.</p>");
    }

    $('#inputForVisualizer').valid();
}


function toggleVisibilityVisualizerMappingTable() {
    var rowCount = $('#visualizerMappingTable >tbody >tr').length;
    if (rowCount == 0) {
        $('#visualizerMappingTable').hide();
    } else {
        $('#visualizerMappingTable').show();
    }
}

function deleteVisualizerMappingTableRow(column, event) {

    var outputForMethodsData = $(column).parent().parent().data('methodcols');
    var inputForVisualizerData = $(column).parent().parent().data('visualdata');

    var visualizationMappings = JSON.parse(localStorage.getItem('visualizationMappings'));
    var newVisualizationMappings = visualizationMappings.filter(function(val){
        return (JSON.stringify(val.outputPort) !== JSON.stringify(outputForMethodsData) &&
                JSON.stringify(val.inputPort) !== JSON.stringify(inputForVisualizerData));
    });
    localStorage.setItem('visualizationMappings', JSON.stringify(newVisualizationMappings));

    // $('#outputForMethods')
    //     .prepend($("<option></option>")
    //         .attr("value", outputForMethodsData.id)
    //         .attr("title", outputForMethodsData.description)
    //         .attr("data-value", JSON.stringify(outputForMethodsData))
    //         .text(outputForMethodsData.title));

    $('#inputForVisualizer')
        .prepend($("<option></option>")
            .attr("value", inputForVisualizerData.id)
            .attr("title", inputForVisualizerData.description + (inputForVisualizerData.required?" (Required)":" (Optional)"))
            .attr("is-required", inputForVisualizerData.required)
            .attr("data-value", JSON.stringify(inputForVisualizerData))
            .attr("class",inputForVisualizerData.required?"required-column":"optional-column")
            .text(inputForVisualizerData.title + " (" + inputForVisualizerData.type + ")"));

    var outputForMethodsOptionSize = $('#outputForMethods option').size();
    var inputForVisualizerOptionSize = $('#inputForVisualizer option').size();

    if (outputForMethodsOptionSize > 0 && inputForVisualizerOptionSize > 0) {
        $('#addVisualizationMapping').prop('disabled', false);
    }
    $(column).closest('tr').remove();
    toggleVisibilityVisualizerMappingTable();
    event.stopPropagation();
}

function loadMethodMappingToTable(data) {

    data = data || false;

    if(data) {

        $("#methodMappingTable > tbody:last").children().remove();

        var dataObj = JSON.parse(data);
        var queryToMethodConfig = dataObj.indicatorDataset[0].queryToMethodConfig;

        var selectedMethods = JSON.parse(localStorage.getItem('selectedMethods')) || [];
        var methodMappings = [];

        for (var i = 0; i < queryToMethodConfig.mapping.length; i++) {

            var methodDataColumnsData = queryToMethodConfig.mapping[i].outputPort;
            var inputForMethodsData = queryToMethodConfig.mapping[i].inputPort;

            var row = "<tr data-methodcols='" + JSON.stringify(methodDataColumnsData) + "' data-methodsdata='" + JSON.stringify(inputForMethodsData) + "'>" +
                "<td>" + methodDataColumnsData.title + "</td>" +
                "<td>" + inputForMethodsData.title + "</td>" +
                "<td><i class='material-icons' onclick='deleteMethodMappingTableRow(this, event);'>close</i></td>" +
                "</tr>";

            methodMappings.push({outputPort: methodDataColumnsData, inputPort: inputForMethodsData});

            if(methodDataColumnsData.required == false) {
                var index = selectedMethods.indexOf(methodDataColumnsData.id);
                if (index < 0) {
                    selectedMethods.push(methodDataColumnsData.id);
                }
            }

            $('#inputForMethods option[value="' + inputForMethodsData.id + '"]').remove();
            $('#methodMappingTable > tbody:last').append(row);
        }

        localStorage.setItem('methodMappings', JSON.stringify(methodMappings));
        localStorage.setItem('selectedMethods', JSON.stringify(selectedMethods));

        var methodDataColumnsOptionSize = $('#methodDataColumns option').size();
        var inputForMethodsOptionSize = $('#inputForMethods option').size();
        if (methodDataColumnsOptionSize == 0 || inputForMethodsOptionSize == 0) {
            $('#addMethodMapping').prop('disabled', true);
        }
        toggleVisibilityMethodMappingTable();
    }
}

function loadVisualizationMappingToTable(data) {

    data = data || false;

    if(data) {

        $("#visualizerMappingTable > tbody:last").children().remove();

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
                "<td><i class='material-icons' onclick='deleteVisualizerMappingTableRow(this, event);'>close</i></td>" +
                "</tr>";

            visualizationMappings.push({outputPort: outputForMethodsData, inputPort: inputForVisualizerData});

            $('#inputForVisualizer option[value="' + inputForVisualizerData.id + '"]').remove();
            $('#visualizerMappingTable > tbody:last').append(row);
        }

        localStorage.setItem('visualizationMappings', JSON.stringify(visualizationMappings));

        var outputForMethodsOptionSize = $('#outputForMethods option').size();
        var inputForVisualizerOptionSize = $('#inputForVisualizer option').size();
        if (outputForMethodsOptionSize == 0 || inputForVisualizerOptionSize == 0) {
            $('#addVisualizationMapping').prop('disabled', true);
        }
        toggleVisibilityVisualizerMappingTable();
    }
}