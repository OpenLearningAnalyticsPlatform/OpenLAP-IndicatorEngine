function getDataColumns() {
    $(function() {
        var selectedMinor = $("#selectedMinor").val();
        $.ajax({
            type: "GET",
            url: "/engine/getDataColumnsByCatName?categoryName=" + selectedMinor,
            dataType: "json",
            success: function (response) {
                var methodDataColumnsSelect = $('#methodDataColumns');
                methodDataColumnsSelect.empty();
                for (var i=0;i< response.length;i++) {
                    methodDataColumnsSelect
                        .append($("<option></option>")
                            .attr("value", response[i].id)
                            .attr("title", response[i].description)
                            .attr("data-value", JSON.stringify(response[i]))
                            .text(response[i].title));
                }
                $("select#methodDataColumns")[0].selectedIndex = 0;
            }
        });
    });
}

function getAnalyticsMethodInputs() {
    $(function() {
        var analyticsMethodId = $("#analyticsMethod").val();
        $.ajax({
            type: "GET",
            url: "/engine/getAnalyticsMethodInputs?id=" + analyticsMethodId,
            dataType: "json",
            success: function (response) {
                var inputForMethodsSelect = $('#inputForMethods');
                inputForMethodsSelect.empty();
                for (var i=0;i< response.length;i++) {
                    inputForMethodsSelect
                        .append($("<option></option>")
                            .attr("value", response[i].id)
                            .attr("title", response[i].description)
                            .attr("data-value", JSON.stringify(response[i]))
                            .text(response[i].title));
                }
                $("select#inputForMethods")[0].selectedIndex = 0;
                getAnalyticsMethodOutputs();
            }
        });
    });
}

function getVisualizationMethodInputs() {
    $(function() {
        var analyticsMethodId = $("#analyticsMethod").val();
        var engineSelect = $("#EngineSelect").val();
        $.ajax({
            type: "GET",
            url: "/engine/getVisualizationMethodInputs?frameworkId=" + engineSelect + "&methodId=" + analyticsMethodId,
            dataType: "json",
            success: function (response) {
                var visualizationInputSelect = $('#inputForVisualizer');
                visualizationInputSelect.empty();
                for (var i=0;i< response.length;i++) {
                    visualizationInputSelect
                        .append($("<option></option>")
                            .attr("value", response[i].id)
                            .attr("title", response[i].description)
                            .attr("data-value", JSON.stringify(response[i]))
                            .text(response[i].title));
                }
                $("select#inputForVisualizer")[0].selectedIndex = 0;
            }
        });
    });
}

function getAnalyticsMethodOutputs() {
    $(function() {
        var analyticsMethodId = $("#analyticsMethod").val();
        $.ajax({
            type: "GET",
            url: "/engine/getAnalyticsMethodOutputs?id=" + analyticsMethodId,
            dataType: "json",
            success: function (response) {
                console.log(response);
                var visualizationOutputSelect = $('#outputForMethods');
                visualizationOutputSelect.empty();
                for (var i=0;i< response.length;i++) {
                    visualizationOutputSelect
                        .append($("<option></option>")
                            .attr("value", response[i].id)
                            .attr("title", response[i].description)
                            .attr("data-value", JSON.stringify(response[i]))
                            .text(response[i].title));
                }
                $("select#outputForMethods")[0].selectedIndex = 0;
            }
        });
    });
}

function addMethodMappingToTable() {

    var methodDataColumnsSelect = $('#methodDataColumns');
    var inputForMethodsSelect = $('#inputForMethods');

    if (methodDataColumnsSelect.val() && inputForMethodsSelect.val()) {

        var methodDataColumnsData = methodDataColumnsSelect.find(':selected').data('value');
        var inputForMethodsData = inputForMethodsSelect.find(':selected').data('value');

        var row = "<tr data-methodcols='" + JSON.stringify(methodDataColumnsData) + "' data-methodsdata='" + JSON.stringify(inputForMethodsData) + "'>" +
                        "<td>" + methodDataColumnsData.title + "</td>" +
                        "<td>" + inputForMethodsData.title + "</td>" +
                        "<td><i class='material-icons' onclick='deleteMethodMappingTableRow(this, event);'>close</i></td>" +
                  "</tr>";

        var methodMappings = JSON.parse(localStorage.getItem('methodMappings')) || [];
        methodMappings.push({outputPort: methodDataColumnsData, inputPort: inputForMethodsData});
        localStorage.setItem('methodMappings', JSON.stringify(methodMappings));

        var selectedMethods = JSON.parse(localStorage.getItem('selectedMethods')) || [];
        selectedMethods.push(methodDataColumnsData.id);
        localStorage.setItem('selectedMethods', JSON.stringify(selectedMethods));
        
        
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
            .attr("title", inputForMethodsData.description)
            .attr("data-value", JSON.stringify(inputForMethodsData))
            .text(inputForMethodsData.title));

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

    var outputForMethodsSelect = $('#outputForMethods');
    var inputForVisualizerSelect = $('#inputForVisualizer');

    if (outputForMethodsSelect.val() && inputForVisualizerSelect.val()) {

        var outputForMethodsData = outputForMethodsSelect.find(':selected').data('value');
        var inputForVisualizerData = inputForVisualizerSelect.find(':selected').data('value');

        var row = "<tr data-methodcols='" + JSON.stringify(outputForMethodsData) + "' data-visualdata='" + JSON.stringify(inputForVisualizerData) + "'>" +
            "<td>" + outputForMethodsData.id + "</td>" +
            "<td>" + inputForVisualizerData.id + "</td>" +
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
            .attr("title", inputForVisualizerData.description)
            .attr("data-value", JSON.stringify(inputForVisualizerData))
            .text(inputForVisualizerData.title));

    var outputForMethodsOptionSize = $('#outputForMethods option').size();
    var inputForVisualizerOptionSize = $('#inputForVisualizer option').size();

    if (outputForMethodsOptionSize > 0 && inputForVisualizerOptionSize > 0) {
        $('#addVisualizationMapping').prop('disabled', false);
    }
    $(column).closest('tr').remove();
    toggleVisibilityVisualizerMappingTable();
    event.stopPropagation();
}