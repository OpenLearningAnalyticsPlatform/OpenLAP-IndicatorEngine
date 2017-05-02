$(function() {
    populateCompVisualizationFrameworks();
    toggleCompVisibilityVisualizerMappingTable();

    $('#comp_selectedMinorSpinner').hide();
    $('#comp_inputForMethodsSpinner').hide();
    $('#comp_selectedChartTypeSpinner').hide();
    $("#comp_inputForVisualizerSpinner").hide();
    $("#comp_entityValueSpinner").hide();

    $('#comp_graphLoaderSpinner').hide();
    $("#comp_indicatorDefinition").hide();
    $("#comp_preview_chart").hide();


    $('#CompForm').validate({
        ignore: false,
        onkeyup: false,
        ignoreTitle: true,
        rules: {
            comp_indicatorNaming: {
                required: true,
                minlength: 6,
                validateIndicator: true
            },
            comp_EngineSelect: {
                required: true
            },
            comp_selectedChartType: {
                required: true
            },
            comp_inputForVisualizer: {
                hasNoOptionLeft: true
            }
        },
        messages: {
            comp_indicatorNaming: {
                required: "Indicator name is required.",
                minlength: "Indicator name should be at least 6 characters."
            },
            comp_EngineSelect: {
                required: "Select visualization library."
            },
            comp_selectedChartType: {
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
});

function populateCompVisualizationFrameworks() {
    var request = createRequest();
    var url = "/engine/listAllVisualizationFrameworks";
    request.open("GET",url,true);
    request.onreadystatechange=function(){processReceivedCompVisualizationFrameworks(request)};
    request.send(null);

}

function processReceivedCompVisualizationFrameworks(request) {
    if (request.readyState == 4) {
        if (request.status == 200) {
            var parsedJSON = JSON.parse(request.responseText);
            var visualizationFrameworksSelection = document.getElementById("comp_EngineSelect");
            // var chartTypeSelection = document.getElementById("selectedChartType");
            removeOptions(visualizationFrameworksSelection);
            // removeOptions(chartTypeSelection);
            for (var i=0;i< parsedJSON.length;i++) {
                var newOption = new Option(parsedJSON[i].name, parsedJSON[i].id);
                visualizationFrameworksSelection.appendChild(newOption);
            }
            visualizationFrameworksSelection.selectedIndex = -1;
        }
    }
}

function populateCompVisualizationMethods() {

    $('#comp_selectedChartTypeSpinner').show();

    var engineSelect = $("#comp_EngineSelect").val();
    var request = createRequest();
    var url = "/engine/getVisualizationMethods?frameworkId=" + engineSelect;
    request.open("GET",url,true);
    request.onreadystatechange=function(){processReceivedCompVisualizationMethods(request)};
    request.send(null);

}

function processReceivedCompVisualizationMethods(request) {
    if (request.readyState == 4) {
        if (request.status == 200) {
            var parsedJSON = JSON.parse(request.responseText);
            var chartTypeSelection = document.getElementById("comp_selectedChartType");
            removeOptions(chartTypeSelection);

            for (var j=0; j<parsedJSON.visualizationMethods.length; j++) {
                var newChartTypeOption = new Option(parsedJSON.visualizationMethods[j].name, parsedJSON.visualizationMethods[j].id);
                chartTypeSelection.appendChild(newChartTypeOption);
            }

            chartTypeSelection.selectedIndex = -1;

            $('#comp_selectedChartTypeSpinner').hide();
        }
    }
}

function getCompVisualizationMethodInputs() {
    var chartTypeId = $("#comp_selectedChartType").val();
    var engineSelect = $("#comp_EngineSelect").val();

    if(chartTypeId && engineSelect) {
        $("#comp_inputForVisualizerSpinner").show();

        $.ajax({
            type: "GET",
            url: "/engine/getVisualizationMethodInputs?frameworkId=" + engineSelect + "&methodId=" + chartTypeId,
            dataType: "json",
            success: function (response) {
                var visualizationInputSelect = $('#comp_inputForVisualizer');
                visualizationInputSelect.empty();

                $('#comp_visualizerMappingTable >tbody').empty();
                localStorage.removeItem("comp_visualizationMappings");
                toggleCompVisibilityVisualizerMappingTable();

                for (var i = 0; i < response.length; i++) {
                    visualizationInputSelect
                        .append($("<option></option>")
                            .attr("value", response[i].id)
                            .attr("title", response[i].description)
                            .attr("is-required", response[i].required)
                            .attr("data-value", JSON.stringify(response[i]))
                            .text(response[i].title + " (" + response[i].type + ")"));
                }
                $("select#comp_inputForVisualizer")[0].selectedIndex = 0;

                var inputForVisualizerOptionSize = $('#comp_inputForVisualizer option').size();
                if (inputForVisualizerOptionSize > 0) {
                    $('#comp_addVisualizationMapping').prop('disabled', false);
                }

                $("#comp_inputForVisualizerSpinner").hide();
            }
        });
    }
}

function addCompVisualizationMappingToTable() {

    var outputForMethodsSelect = $('#comp_outputForMethods');
    var inputForVisualizerSelect = $('#comp_inputForVisualizer');

    if (outputForMethodsSelect.val() && inputForVisualizerSelect.val()) {

        var outputForMethodsData = outputForMethodsSelect.find(':selected').data('value');
        var inputForVisualizerData = inputForVisualizerSelect.find(':selected').data('value');

        var row = "<tr data-methodcols='" + JSON.stringify(outputForMethodsData) + "' data-visualdata='" + JSON.stringify(inputForVisualizerData) + "'>" +
            "<td>" + outputForMethodsData.title + " (" + outputForMethodsData.type + ")" + "</td>" +
            "<td>" + inputForVisualizerData.title + " (" + inputForVisualizerData.type + ")" + "</td>" +
            "<td><i class='material-icons' onclick='deleteCompVisualizerMappingTableRow(this, event);'>close</i></td>" +
            "</tr>";

        var visualizationMappings = JSON.parse(localStorage.getItem('comp_visualizationMappings')) || [];
        visualizationMappings.push({outputPort: outputForMethodsData, inputPort: inputForVisualizerData});
        localStorage.setItem('comp_visualizationMappings', JSON.stringify(visualizationMappings));

        // $("#outputForMethods option:selected").remove();
        $("#comp_inputForVisualizer option:selected").remove();
        var outputForMethodsOptionSize = $('#comp_outputForMethods option').size();
        var inputForVisualizerOptionSize = $('#comp_inputForVisualizer option').size();

        if (outputForMethodsOptionSize == 0 || inputForVisualizerOptionSize == 0) {
            $('#comp_addVisualizationMapping').prop('disabled', true);
        }

        $('#comp_visualizerMappingTable > tbody:last').append(row);
        toggleCompVisibilityVisualizerMappingTable();
    }

    $('#comp_inputForVisualizer').valid();
}

function deleteCompVisualizerMappingTableRow(column, event) {

    var outputForMethodsData = $(column).parent().parent().data('methodcols');
    var inputForVisualizerData = $(column).parent().parent().data('visualdata');

    var visualizationMappings = JSON.parse(localStorage.getItem('comp_visualizationMappings'));
    var newVisualizationMappings = visualizationMappings.filter(function(val){
        return (JSON.stringify(val.outputPort) !== JSON.stringify(outputForMethodsData) &&
        JSON.stringify(val.inputPort) !== JSON.stringify(inputForVisualizerData));
    });
    localStorage.setItem('comp_visualizationMappings', JSON.stringify(newVisualizationMappings));

    // $('#comp_outputForMethods')
    //     .prepend($("<option></option>")
    //         .attr("value", outputForMethodsData.id)
    //         .attr("title", outputForMethodsData.description)
    //         .attr("data-value", JSON.stringify(outputForMethodsData))
    //         .text(outputForMethodsData.title));

    $('#comp_inputForVisualizer')
        .prepend($("<option></option>")
            .attr("value", inputForVisualizerData.id)
            .attr("title", inputForVisualizerData.description)
            .attr("is-required", inputForVisualizerData.required)
            .attr("data-value", JSON.stringify(inputForVisualizerData))
            .text(inputForVisualizerData.title + " (" + inputForVisualizerData.type + ")"));

    var outputForMethodsOptionSize = $('#comp_outputForMethods option').size();
    var inputForVisualizerOptionSize = $('#comp_inputForVisualizer option').size();

    if (outputForMethodsOptionSize > 0 && inputForVisualizerOptionSize > 0) {
        $('#comp_addVisualizationMapping').prop('disabled', false);
    }
    $(column).closest('tr').remove();
    toggleCompVisibilityVisualizerMappingTable();
    event.stopPropagation();
}

function toggleCompVisibilityVisualizerMappingTable() {
    var rowCount = $('#comp_visualizerMappingTable >tbody >tr').length;
    if (rowCount == 0) {
        $('#comp_visualizerMappingTable').hide();
    } else {
        $('#comp_visualizerMappingTable').show();
    }
}

function OpenCompositeModal() {
    var request = createRequest();
    var url ="/indicators/refreshQuestionSummary";
    request.open("GET",url,true);
    request.onreadystatechange=function(){updateCompositeModal(request)};
    request.send(null);
}

function updateCompositeModal(request) {
    if (request.readyState == 4) {
        if (request.status == 200) {
            var parsedJSON = JSON.parse(request.responseText);

            if(parsedJSON.sessionIndicators.length ==0) {

                var visualizeQModelHtml = document.getElementById("comp_IndicatorContent");
                visualizeQModelHtml.innerHTML = "";

                var newDiv = document.createElement("div");
                newDiv.className = "alert alert-warning";
                var alertDesc = document.createTextNode("Please add indicators to form composite.");
                newDiv.appendChild(alertDesc);
                visualizeQModelHtml.appendChild(newDiv);

                $("#comp_VisualizationSection").hide();
                $('#comp_AddButton').hide();
            }
            else {
                $("#comp_VisualizationSection").show();
                $('#comp_AddButton').show();


                var visualizeQModelHtml = document.getElementById("comp_IndicatorContent");
                visualizeQModelHtml.innerHTML = "";

                // var qColDiv = document.createElement("div");
                // qColDiv.className = "col-md-12";
                //
                // var qCardDiv = document.createElement("div");
                // qCardDiv.className = "question-vis-card card";
                // qCardDiv.id = "comp_questionCard";
                //
                // var qCardImageDiv = document.createElement("div");
                // qCardImageDiv.className = "full-width row";
                // qCardImageDiv.id = "comp_questionCard_Image";
                //
                // qCardDiv.appendChild(qCardImageDiv);
                //
                // var qCardContentDiv = document.createElement("div");
                // qCardContentDiv.className = "card-content comp-card-padding";
                // qCardContentDiv.id = "comp_questionCardContent";
                //
                // var qCardContentSpan = document.createElement("span");
                // qCardContentSpan.className = "card-title activator grey-text text-darken-4";
                // qCardContentSpan.style.display = "block";
                // var qCardTitle = document.createTextNode(parsedJSON.questionName);
                // qCardContentSpan.appendChild(qCardTitle);
                //
                // qCardContentDiv.appendChild(qCardContentSpan);
                // qCardDiv.appendChild(qCardContentDiv);
                // qColDiv.appendChild(qCardDiv);
                // visualizeQModelHtml.appendChild(qColDiv);


                for(var i=0; i<parsedJSON.sessionIndicators.length; i++) {
                    //visualization card
                    var colDiv = document.createElement("div");
                    colDiv.className = "col-md-6";

                    var cardDiv = document.createElement("div");
                    cardDiv.className = "question-vis-card card";
                    var cardDivId = "comp_visualizeCardGraph_" + parsedJSON.sessionIndicators[i].identifier;
                    cardDiv.id = cardDivId;

                    var cardImageDiv = document.createElement("div");
                    //cardImageDiv.className = "card-image waves-effect waves-block waves-light";
                    cardImageDiv.className = "full-width";

                    var divId = "comp_visualizeCardGraph_" + parsedJSON.sessionIndicators[i].indicatorName.replace(/ /g,"_");
                    cardImageDiv.id = divId;

                    cardDiv.appendChild(cardImageDiv);

                    var cardContentDiv = document.createElement("div");
                    cardContentDiv.className = "card-content comp-card-padding comp-card-blue";
                    var contentDivId = "comp_visualizeCardGraphContent_" + parsedJSON.sessionIndicators[i].identifier;
                    cardContentDiv.id = contentDivId;

                    var cardContentSpan = document.createElement("span");
                    cardContentSpan.className = "card-title activator grey-text text-darken-4 comp-card-label";
                    cardContentSpan.style.display = "block";
                    var cardTitle = document.createTextNode(parsedJSON.sessionIndicators[i].indicatorName);
                    cardContentSpan.appendChild(cardTitle);

                    if(parsedJSON.sessionIndicators[i].indicatorType == 'simple') {

                        var checkbox = document.createElement("input");
                        checkbox.id = "comp_checkbox_" + parsedJSON.sessionIndicators[i].identifier;
                        checkbox.type = "checkbox";    // make the element a checkbox
                        checkbox.name = "comp_checkbox_" + parsedJSON.sessionIndicators[i].identifier;
                        checkbox.value = parsedJSON.sessionIndicators[i].identifier;         // make its value "pair"
                        checkbox.setAttribute("method_id", parsedJSON.sessionIndicators[i].indicatorParameters.analyticsMethodId[0]);
                        checkbox.addEventListener("click", function() {
                            enableValidCompIndicators(this);
                        });

                        var label = document.createElement("label");
                        label.className = "comp-checkbox"
                        label.setAttribute("for", "comp_checkbox_" + parsedJSON.sessionIndicators[i].identifier);

                        cardContentDiv.appendChild(checkbox);
                        cardContentDiv.appendChild(label);
                    }

                    cardContentDiv.appendChild(cardContentSpan);

                    cardDiv.appendChild(cardContentDiv);

                    colDiv.appendChild(cardDiv);

                    visualizeQModelHtml.appendChild(colDiv);
                    //qCardImageDiv.appendChild(colDiv);

                    //var parsedVisualization = JSON.parse(parsedJSON.sessionIndicators[i].visualization);
                    var decodedGraphData = decodeURIComponent(parsedJSON.sessionIndicators[i].visualization);
                    decodedGraphData = decodedGraphData.replace("xxxwidthxxx","$('#" + divId + "').outerWidth(true)");
                    decodedGraphData = decodedGraphData.replace("xxxheightxxx","$('#" + divId + "').outerHeight(true)");

                    $('#'+divId).html(decodedGraphData);
                }
            }
        }
    }
}

function enableValidCompIndicators(checkbox){
    var inputs = $('#comp_IndicatorContent input');

    if(checkbox.checked) {
        for (var i = 0; i < inputs.length; i++) {
            if (checkbox.id != inputs[i].id) {
                if (checkbox.getAttribute("method_id") != inputs[i].getAttribute("method_id")){
                    inputs[i].setAttribute("disabled", "disabled");
                    $(inputs[i]).parent().removeClass("comp-card-green");
                    $(inputs[i]).parent().addClass("comp-card-red");
                }
                else {
                    inputs[i].removeAttribute("disabled");
                    $(inputs[i]).parent().removeClass("comp-card-red");

                    if(!inputs[i].checked)
                        $(inputs[i]).parent().addClass("comp-card-green");
                }
            }
            else{
                $(checkbox).parent().removeClass("comp-card-red");
                $(checkbox).parent().removeClass("comp-card-green");
            }
        }

        getCompAnalyticsMethodOutputs(checkbox.getAttribute("method_id"));
    }
    else {
        var isChecked = false;
        var checkedCheckbox;
        for (var i = 0; i < inputs.length; i++) {
            if(inputs[i].checked) {
                isChecked = true;
                checkedCheckbox = inputs[i];
                break;
            }
        }

        if(!isChecked) {
            $('#comp_IndicatorContent input').removeAttr("disabled");

            $('#comp_IndicatorContent input').parent().removeClass("comp-card-red");
            $('#comp_IndicatorContent input').parent().removeClass("comp-card-green");

            $('#comp_outputForMethods').empty();

            $('#comp_visualizerMappingTable >tbody').empty();
            localStorage.removeItem("comp_visualizationMappings");
            toggleCompVisibilityVisualizerMappingTable();
        }
        else{
            for (var i = 0; i < inputs.length; i++) {
                if (checkedCheckbox.id != inputs[i].id) {
                    if (checkedCheckbox.getAttribute("method_id") != inputs[i].getAttribute("method_id")){
                        inputs[i].setAttribute("disabled", "disabled");
                        $(inputs[i]).parent().addClass("comp-card-red");
                        $(inputs[i]).parent().removeClass("comp-card-green");
                    }
                    else {
                        inputs[i].removeAttribute("disabled");
                        $(inputs[i]).parent().removeClass("comp-card-red");

                        if(!inputs[i].checked)
                            $(inputs[i]).parent().addClass("comp-card-green");
                    }
                }
            }
        }
    }
}

function getCompAnalyticsMethodOutputs(analyticsMethodId) {
    $.ajax({
        type: "GET",
        url: "/engine/getAnalyticsMethodOutputs?id=" + analyticsMethodId,
        dataType: "json",
        success: function (response) {
            var methodOutputSelect = $('#comp_outputForMethods');
            methodOutputSelect.empty();
            for (var i=0;i< response.length;i++) {
                methodOutputSelect
                    .append($("<option></option>")
                        .attr("value", response[i].id)
                        .attr("title", response[i].description)
                        .attr("data-value", JSON.stringify(response[i]))
                        .text(response[i].title + " (" + response[i].type + ")" ));
            }

            methodOutputSelect
            .append($("<option></option>")
                .attr("value", "indicator_names")
                .attr("title", "Names of the indicators combines together to form the composite.")
                .attr("data-value", "{\"type\":\"STRING\",\"id\":\"indicator_names\",\"required\":true,\"title\":\"Indicator Names\",\"description\":\"Names of the indicators combines together to form the composite.\"}")
                .text("Indicator Names (STRING)" ));

            $("select#comp_outputForMethods")[0].selectedIndex = 0;
        }
    });
}

function getCompIndicatorPreview() {

    $('#comp_preview_msg').empty();

    if ($('#CompForm').valid()) {

        slideToElement("comp_previewChartLabel");

        $('#comp_preview_chart').hide();
        $('#comp_graphLoaderSpinner').show();
        $("#comp_generateGraph").attr('disabled', 'disabled');

        var selectedInd = "";
        var indCheckboxes = $('#comp_IndicatorContent input');
        for (var i = 0; i < indCheckboxes.length; i++) {
            if(indCheckboxes[i].checked) {
                selectedInd = selectedInd + indCheckboxes[i].value + ",";
            }
        }

        var visualizationMappings = localStorage.getItem('comp_visualizationMappings') || "";

        var request = createRequest();
        var url = "/engine/getCompIndicatorPreview?width=xxxwidthxxx&height=xxxheightxxx"
            + "&indicatorSelect=" + selectedInd
            + "&EngineSelect=" + $("#comp_EngineSelect").val()
            + "&selectedChartType=" + $("#comp_selectedChartType").val()
            + "&indicatorNaming=" + $("#comp_indicatorNaming").val()
            + "&visualizationMappings=" + visualizationMappings;
        request.open("GET", url, true);
        request.onreadystatechange = function () {
            embedCompIndicatorPreview(request)
        };
        request.send(null);
    }
    else{
        var formValidator = $('#CompForm').data("validator");
        var invalidElements = formValidator.invalidElements(); // contain the invalid elements
        var msg = "";

        for (i = 0; i < invalidElements.length; i++) {
            if($(invalidElements[i]).is("select"))
                msg = msg + "<p id='"+invalidElements[i].id+"_msg'><a onclick='slideToElement(\""+ invalidElements[i].id +"\")'>" + $(invalidElements[i]).prev("label").attr("data-error") + "</a></p>";
            else
                msg = msg + "<p id='"+invalidElements[i].id+"_msg'><a onclick='slideToElement(\""+ invalidElements[i].id +"\")'>" + $(invalidElements[i]).next("label").attr("data-error") + "</a></p>";
        }

        $('#comp_preview_msg').html(msg);
    }
}

function embedCompIndicatorPreview(request) {
    if (request.readyState == 4) {
        $('#comp_graphLoaderSpinner').hide();
        $("#comp_generateGraph").removeAttr('disabled');
        if (request.status == 200) {
            var parsedJSON = JSON.parse(request.responseText);
            var decodedGraphData;
            if(parsedJSON.isSuccess) {
                decodedGraphData = decodeURIComponent(parsedJSON.visualizationCode);

                decodedGraphData = decodedGraphData.replace("xxxwidthxxx","$('#comp_chart_wrap').outerWidth(true)");
                decodedGraphData = decodedGraphData.replace("xxxheightxxx","$('#comp_chart_wrap').outerHeight(true)");
            }
            else {
                decodedGraphData = '<div class="alert alert-error">' + parsedJSON.errorMessage + '</div>';
            }

            try {
                $('#comp_preview_chart').show();
                $("#comp_preview_chart").html(decodedGraphData);
            }
            catch(err) {
                $("#comp_preview_chart").append("<span>" + err.message + "</span>");
            }
        }
    }
}

function finalizeCompositeIndicator() {
    if ($('#CompForm').valid()) {
        $('#loading-screen').removeClass('loader-hide');

        var selectedInd = "";
        var indCheckboxes = $('#comp_IndicatorContent input');
        for (var i = 0; i < indCheckboxes.length; i++) {
            if(indCheckboxes[i].checked) {
                selectedInd = selectedInd + indCheckboxes[i].value + ",";
            }
        }

        var visualizationMappings = localStorage.getItem('comp_visualizationMappings') || "";

        // var request = createRequest();
        // var url = "/engine/finalizeCompIndicator?indicatorSelect=" + selectedInd
        //     + "&EngineSelect=" + $("#comp_EngineSelect").val()
        //     + "&selectedChartType=" + $("#comp_selectedChartType").val()
        //     + "&indicatorNaming=" + $("#comp_indicatorNaming").val()
        //     + "&visualizationMappings=" + visualizationMappings;
        //
        // request.open("GET",url,true);
        // request.onreadystatechange=function(){postFinalizeCompositeIndicator(request)};
        // request.send(null);

        $.ajax({
            type: "GET",
            url: "/engine/finalizeCompIndicator?indicatorSelect=" + selectedInd
                    + "&EngineSelect=" + $("#comp_EngineSelect").val()
                    + "&selectedChartType=" + $("#comp_selectedChartType").val()
                    + "&indicatorNaming=" + $("#comp_indicatorNaming").val()
                    + "&visualizationMappings=" + visualizationMappings,
            dataType: "json",
            success: function (response) {
                $('#loading-screen').addClass('loader-hide');

                var visualizeQModelHtml = document.getElementById("comp_IndicatorContent");
                visualizeQModelHtml.innerHTML = "";

                postrefreshQuestionSummary(response);

                $(function() {
                    $("#comp_indicatorDefinition").hide();
                    $('body').animate({
                        scrollTop: $("body").offset().top
                    }, 1000);
                });
            }
        });
    }
}

function postFinalizeCompositeIndicator(request) {
    if (request.readyState == 4) {
        if (request.status == 200) {
            postrefreshQuestionSummary(response);
        }
    }
}

/*function updateCompositeModal(request) {

 if (request.readyState == 4) {
 if (request.status == 200) {
 var parsedJSON = JSON.parse(request.responseText);
 if(parsedJSON.sessionIndicators.length ==0) {

 var compositeModelHtml = document.getElementById("runIndMem");
 compositeModelHtml.innerHTML = "";

 var div = document.createElement("div");
 div.className = "alert alert-warning";
 var alertDescription = document.createTextNode("Please add Indicators to build composite Indicators.");
 div.appendChild(alertDescription);
 compositeModelHtml.appendChild(div);

 $(function() {
 $("#compositeIndicatorModelContentDesc").hide();
 $("#compositeIndicatorModelContentControls").hide();
 $('#CompositeIndButton').hide();
 $('#CompositeClosedButton').show();
 });
 }
 else {
 $(function() {
 $('#CompositeIndButton').show();
 $('#CompositeClosedButton').hide();
 });
 var compositeModelHtml = document.getElementById("runIndMem");
 compositeModelHtml.innerHTML = "";

 for(var i=0; i<parsedJSON.sessionIndicators.length; i++) {

 //composite Indicator
 var pTag= document.createElement("p");
 var label= document.createElement("label");
 label.setAttribute("for", "checkbox-" + parsedJSON.sessionIndicators[i].indicatorName);
 var description = document.createTextNode(parsedJSON.sessionIndicators[i].indicatorName);
 var checkbox = document.createElement("input");
 checkbox.id = "checkbox-" + parsedJSON.sessionIndicators[i].indicatorName;
 checkbox.type = "checkbox";    // make the element a checkbox
 checkbox.name = parsedJSON.sessionIndicators[i].indicatorName;      // give it a name we can check on the server side
 checkbox.value = parsedJSON.sessionIndicators[i].indicatorName;         // make its value "pair"
 checkbox.className = "filled-in";


 label.appendChild(description);// add the description to the element
 pTag.appendChild(checkbox);   // add the box to the element
 pTag.appendChild(label);   // add the box to the element
 // add the label element to your div
 compositeModelHtml.appendChild(pTag);

 var imgDiv = document.createElement("div");
 imgDiv.className = "card col-md-10";

 var compositeGraphId = "compositeIndicatorGraph_" + parsedJSON.sessionIndicators[i].indicatorName.replace(/ /g,"_");
 imgDiv.id = compositeGraphId;
 imgDiv.style.width = "500px";
 imgDiv.style.height = "400px";

 compositeModelHtml.appendChild(imgDiv);

 $.ajax(
 {
 context: this,
 async: false,
 type: "GET",
 url: "/engine/getQuestionVisualizationCode?width=400&height=400",
 success: function(response) {

 var parsedJSON = JSON.parse(response);
 var decodedGraphData = decodeURIComponent(parsedJSON);
 $('#'+compositeGraphId).html(decodedGraphData);
 }
 });

 }
 }

 }
 }
 }*/
