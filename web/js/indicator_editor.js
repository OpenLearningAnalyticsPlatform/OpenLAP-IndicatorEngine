/*
 * Open Learning Analytics Platform (OpenLAP) : Indicator Engine

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
 * Created by Tanmaya Mahapatra on 18-06-2015.
 */
// $(function() {
//     $( document ).tooltip();
// });

function updateScreenAfterLoadInd(data) {
    $('#preview_chart').hide();
    $('#indicatorNaming').val(data.indicatorName);
    $('#sourceSelection').val(data.indicatorParameters.indicatorDataset[0].selectedSource);
    $('#PlatformSelection').val(data.indicatorParameters.indicatorDataset[0].selectedPlatform);
    $('#actionSelection').val(data.indicatorParameters.indicatorDataset[0].selectedAction);

    var entityValues = data.indicatorParameters.indicatorDataset[0].entityValues;
    var userSpecs = data.indicatorParameters.indicatorDataset[0].userSpecifications;
    var sessionSpecs = data.indicatorParameters.indicatorDataset[0].sessionSpecifications;
    var timeSpecs = data.indicatorParameters.indicatorDataset[0].timeSpecifications;

    loadAssociatedEntityFilters(entityValues);
    loadAssociatedSessionFilters(sessionSpecs);
    loadAssociatedTimeFilters(timeSpecs);
    loadAssociatedUserFilters(userSpecs);

    populateAnalyticsMethods(JSON.stringify(data.indicatorParameters));

    populateCategories(data.indicatorParameters.indicatorDataset[0].selectedMinor);
    $('#loading-screen').addClass('loader-hide');
}
/*
 * hoverIntent | Copyright 2011 Brian Cherne
 * http://cherne.net/brian/resources/jquery.hoverIntent.html
 * modified by the jQuery UI team
 */
$.event.special.hoverintent = {
    setup: function() {
        $( this ).bind( "mouseover", jQuery.event.special.hoverintent.handler );
    },
    teardown: function() {
        $( this ).unbind( "mouseover", jQuery.event.special.hoverintent.handler );
    },
    handler: function( event ) {
        var currentX, currentY, timeout,
            args = arguments,
            target = $( event.target ),
            previousX = event.pageX,
            previousY = event.pageY;

        function track( event ) {
            currentX = event.pageX;
            currentY = event.pageY;
        };

        function clear() {
            target
                .unbind( "mousemove", track )
                .unbind( "mouseout", clear );
            clearTimeout( timeout );
        }

        function handler() {
            var prop,
                orig = event;

            if ( ( Math.abs( previousX - currentX ) +
                Math.abs( previousY - currentY ) ) < 7 ) {
                clear();

                event = $.Event( "hoverintent" );
                for ( prop in orig ) {
                    if ( !( prop in event ) ) {
                        event[ prop ] = orig[ prop ];
                    }
                }
                // Prevent accessing the original event since the new event
                // is fired asynchronously and the old event is no longer
                // usable (#6028)
                delete event.originalEvent;

                target.trigger( event );
            } else {
                previousX = currentX;
                previousY = currentY;
                timeout = setTimeout( handler, 100 );
            }
        }

        timeout = setTimeout( handler, 100 );
        target.bind({
            mousemove: track,
            mouseout: clear
        });
    }
};

function createRequest() {
    var request;
    if (window.XMLHttpRequest) {
        request = new XMLHttpRequest();
    }
    else {
        request = new ActiveXObject("Microsoft.XMLHTTP");
    }
    return request;
}

function removeOptions(selectbox) {
    if(selectbox) {
        var i;
        for (i = selectbox.options.length - 1; i >= 0; i--)
            selectbox.remove(i);
    }
}

function populateSources(){
    $.ajax({
        type: "GET",
        url: "/engine/listAllEventSources",
        dataType: "json",
        success: function (response) {
            var element = $('#sourceSelection');
            element.empty();

            for (var i=0;i< response.length;i++) {
                element
                    .append($("<option></option>")
                        .attr("value", response[i])
                        .text(response[i]));
            }
            $("select#sourceSelection")[0].selectedIndex = 0;
        }
    });
}

function populatePlatforms(){
    $.ajax({
        type: "GET",
        url: "/engine/listAllEventPlatforms",
        dataType: "json",
        success: function (response) {
            var element = $('#PlatformSelection');
            element.empty();

            for (var i=0;i< response.length;i++) {
                element
                    .append($("<option></option>")
                        .attr("value", response[i])
                        .text(response[i]));
            }
            $("select#PlatformSelection")[0].selectedIndex = 0;
        }
    });
}

function populateActions(){
    $.ajax({
        type: "GET",
        url: "/engine/listAllEventActions",
        dataType: "json",
        success: function (response) {
            var element = $('#actionSelection');
            element.empty();

            for (var i=0;i< response.length;i++) {
                element
                    .append($("<option></option>")
                        .attr("value", response[i])
                        .text(response[i]));
            }
            $("select#actionSelection")[0].selectedIndex = 0;
        }
    });
}

function populateCategories(selectedValue){
    var selectedValue = selectedValue || null;

    var spinner = $('#selectedMinorSpinner');
    spinner.show();

    var selectedSources = $("#sourceSelection").val();
    var selectedSource = selectedSources.join();

    var selectedActions = $('#actionSelection').val();
    var selectedAction = selectedActions.join();

    var selectedPlatforms = $('#PlatformSelection').val();
    var selectedPlatform = selectedPlatforms.join();

    $.ajax({
        context: true,
        type: "GET",
        url: "/engine/getDistinctCategories?action="+selectedAction+"&platform="+selectedPlatform+"&source="+selectedSource,
        dataType: "json",
        success: function (response) {
            var selectedMinor = $('#selectedMinor');
            selectedMinor.empty();

            $.each(response, function(k, v) {
                selectedMinor.append($('<option>', {value:v, text:k}));
            });

            if(selectedValue !== null) {
                selectedMinor.val(selectedValue);
            }
            spinner.hide();
            populateEntities();
        }
    });

}

function populateEntities(data) {
    getDataColumns();
}

function addEntity(){
    var request = createRequest();
    var keySelected = document.getElementById("entityKeySelection").value;

    var selectedTitle = $('#entityKeySelection :selected').text();

    var selectedValues = $('#entityValue').val();
    var selectedJoinedValues = selectedValues.join();

    var url ="/indicators/addEntity?key="+keySelected+"&value="+selectedJoinedValues+"&title="+selectedTitle;
    request.open("GET",url,true);
    request.onreadystatechange = function(){displayEntityFilters(1,request)};
    request.send(null);
}


function deleteEntity(){

    // var selectedMethods = JSON.parse(localStorage.getItem('selectedMethods')) || [];
    // var newSelectedMethods = selectedMethods.filter(function(val){
    //     return (JSON.stringify(val) !== JSON.stringify(entityFilterListing.value));
    // });
    // localStorage.setItem('selectedMethods', JSON.stringify(newSelectedMethods));

    var request = createRequest();
    var entityFilterListing = document.getElementById("entityFilterListing");
    var url ="/indicators/deleteEntities?filter="+entityFilterListing.value;
    request.open("GET",url,true);
    request.onreadystatechange = function(){displayEntityFilters(3,request,entityFilterListing.value)};
    request.send(null);
}

function displayEntityFilters(callstatus,request,msg){
    if (request.readyState == 4) {
        if (request.status == 200) {
            var parsedJSON = JSON.parse(request.responseText);
            loadAssociatedEntityFilters(parsedJSON);
        }
    }
}

function searchTime(){
    var request = createRequest();
    var timeType = document.getElementById("timeSearchType").value;
    var searchTimeString = document.getElementById("timeSearchString").value;
    var url ="/indicators/searchTime?searchTime="+searchTimeString+"&timeType="+timeType;
    request.open("GET",url,true);
    request.onreadystatechange=function(){displaySearchTimeResults(request)};
    request.send(null);
}

function displaySearchTimeResults(request) {
    if (request.readyState == 4) {
        if (request.status == 200) {
            var parsedJSON = JSON.parse(request.responseText);
            var timeSearchResults = document.getElementById("timeSearchResults");
            removeOptions(timeSearchResults);
            for (var i=0;i< parsedJSON.length;i++) {
                var newOption = new Option(parsedJSON[i], parsedJSON[i]);
                timeSearchResults.appendChild(newOption);
            }
        }
    }

}

function refreshQuestionSummary() {
    $.ajax({
        type: "GET",
        url: "/indicators/refreshQuestionSummary",
        dataType: "json",
        success: function (response) {
            postrefreshQuestionSummary(response);
        }
    });
}

//updated
function postrefreshQuestionSummary(parsedJSON, isLoadTemplate) {

    isLoadTemplate = isLoadTemplate || false;
    var qNamefromBean = document.getElementById("questionNaming");
    var goalId = document.getElementById("GoalSelection");

    $('#associatedIndicatorsDiv').empty();

    if (!isLoadTemplate) {
        clearIndicatorArea();
    }
    if(parsedJSON == null || parsedJSON.sessionIndicators.length == 0) {
        $('#associatedIndicatorsDiv').append("No Associated Indicators");
    }

    if(parsedJSON != null) {
        if (parsedJSON.sessionIndicators.length > 0) {
            qNamefromBean.value = parsedJSON.questionName;
            goalId.value = parsedJSON.goalId;
        }

        for (var i = 0; i < parsedJSON.sessionIndicators.length; i++) {
            if(parsedJSON.sessionIndicators[i].indicatorType == 'composite') {
                $('#associatedIndicatorsDiv').append("<div class='chip composite-chip' name='chip-" + parsedJSON.sessionIndicators[i].identifier + "' id='" + parsedJSON.sessionIndicators[i].identifier
                    + "' title='" + parsedJSON.sessionIndicators[i].indicatorName
                    +"'><span>" + parsedJSON.sessionIndicators[i].indicatorName
                    + "</span><i class='material-icons' onclick='showDeleteIndicatorModal(this, event);'>close</i></div>");
            }
            else if(parsedJSON.sessionIndicators[i].indicatorType == 'multianalysis') {
                $('#associatedIndicatorsDiv').append("<div class='chip multianalysis-chip' name='chip-" + parsedJSON.sessionIndicators[i].identifier + "' id='" + parsedJSON.sessionIndicators[i].identifier
                    + "' title='" + parsedJSON.sessionIndicators[i].indicatorName
                    +"'><span>" + parsedJSON.sessionIndicators[i].indicatorName
                    + "</span><i class='material-icons' onclick='showDeleteIndicatorModal(this, event);'>close</i></div>");
            }
            else if(parsedJSON.sessionIndicators[i].indicatorType == 'simple') {
                $('#associatedIndicatorsDiv').append("<div class='chip' name='chip-" + parsedJSON.sessionIndicators[i].identifier + "' id='" + parsedJSON.sessionIndicators[i].identifier
                    + "' title='" + parsedJSON.sessionIndicators[i].indicatorName
                    + "' onclick='loadIndicator(this);'><span >" + parsedJSON.sessionIndicators[i].indicatorName
                    + "</span><i class='material-icons' onclick='showDeleteIndicatorModal(this, event);'>close</i></div>");
            }

            if (isLoadTemplate) {
                $(".chip[name=chip-" + parsedJSON.sessionIndicators[parsedJSON.sessionIndicators.length-1].identifier + "]").addClass("chip-bg").siblings().removeClass('chip-bg');
                localStorage.setItem("selectedIndicatorIdentifier", parsedJSON.sessionIndicators[parsedJSON.sessionIndicators.length-1].identifier);
            }
            // if(parsedJSON.sessionIndicators[i].genIndicatorProps['isComposite']) {
            //     $('#associatedIndicatorsDiv').append("<div class='chip composite-chip' name='chip-" + parsedJSON.sessionIndicators[i].identifier + "' id='" + parsedJSON.sessionIndicators[i].identifier
            //         + "' title='" + parsedJSON.sessionIndicators[i].indicatorName
            //         +"'><span>" + parsedJSON.sessionIndicators[i].indicatorName
            //         + "</span><i class='material-icons' onclick='showDeleteIndicatorModal(this, event);'>close</i></div>");
            // }
            // else {
            //     $('#associatedIndicatorsDiv').append("<div class='chip' name='chip-" + parsedJSON.sessionIndicators[i].identifier + "' id='" + parsedJSON.sessionIndicators[i].identifier
            //         + "' title='" + parsedJSON.sessionIndicators[i].indicatorName
            //         + "' onclick='loadIndicator(this);'><span >" + parsedJSON.sessionIndicators[i].indicatorName
            //         + "</span><i class='material-icons' onclick='showDeleteIndicatorModal(this, event);'>close</i></div>");
            // }
            // if (isLoadTemplate) {
            //     $(".chip[name=chip-" + parsedJSON.sessionIndicators[parsedJSON.sessionIndicators.length-1].identifier + "]").addClass("chip-bg").siblings().removeClass('chip-bg');
            //     localStorage.setItem("selectedIndicatorIdentifier", parsedJSON.sessionIndicators[parsedJSON.sessionIndicators.length-1].identifier);
            // }
        }
    }

}

//function finalizeIndicator(filterPresent) {
function finalizeIndicator() {
    $(".chip").removeClass('chip-bg');

    //if(filterPresent) {

    $('#loading-screen').removeClass('loader-hide');
    //var request = createRequest();
    var goalId = document.getElementById("GoalSelection").value;
    var questionName = document.getElementById("questionNaming").value;
    var indicatorName = document.getElementById("indicatorNaming").value;
    var graphType = document.getElementById("selectedChartType").value;
    var graphEngine = document.getElementById("EngineSelect").value;
    var analyticsMethod = document.getElementById("analyticsMethod").value;

    var indicatorIndex = localStorage.getItem("selectedIndicatorIdentifier");
    var methodMappings = localStorage.getItem('methodMappings') || "";
    var visualizationMappings = localStorage.getItem('visualizationMappings') || "";

    var selectedMethods = localStorage.getItem('selectedMethods') || "";
    if(selectedMethods.length > 0)
        selectedMethods = JSON.parse(selectedMethods).join(',');

    var methodParams = getDynamicParamsValues("methodDynamicParams");

    $('#saveIndicator').prop('disabled', true);
    $.ajax({
        type: "GET",
        url: "/indicators/finalize?goalId="+goalId+"&questionName="+questionName+"&indicatorName="+indicatorName+"&graphType="+graphType
                +"&graphEngine="+graphEngine+"&indicatorIdentifier="+indicatorIndex+"&analyticsMethod="+analyticsMethod + "&methodMappings=" + methodMappings
                + "&visualizationMappings=" + visualizationMappings + "&selectedMethods=" + selectedMethods + "&methodParams=" + methodParams,
        dataType: "json",
        success: function (response) {
            $('#loading-screen').addClass('loader-hide');

            postrefreshQuestionSummary(response);

            $('#saveIndicator').prop('disabled', false);

            $("#indicatorDefinition").hide();
            $('body').animate({
                scrollTop: $("body").offset().top
            }, 1000);

            localStorage.removeItem("indType");
        }
    });


    //}
    //else
        //checkForDefaultRule(2);

}

function addNewIndicator(type) {
    var request = createRequest();
    var url ="/indicators/addNewIndicator?type="+type;
    request.open("GET",url,true);
    request.onreadystatechange=function(){processScreenForNextIndicator(request, type)};
    request.send(null);
}

function processScreenForNextIndicator(request, type) {
    if (request.readyState == 4) {
        if (request.status == 200) {
            //var parsedJSON = JSON.parse(request.responseText);

            clearLocalStorage(type);
            clearIndicatorArea(type);

            refreshQuestionSummary();
        }
    }
}

function openGoalRequestModal() {
    $('#goalRequestTemplateModel').openModal();
}

function clearGoalRequestModal() {
    $('#new-analytics-goal-name').val("");
    $('#new-analytics-goal-desc').val("");
    $('#requestGoalMessage').empty();
}

function sendGoalRequest() {
    var userName = $('#userName').val();
    var goalName = $('#new-analytics-goal-name').val();
    var goalDesc = $('#new-analytics-goal-desc').val();

    if (goalName && goalDesc && userName) {
         $.ajax({
            type: "GET",
            url: "/engine/RequestAnalyticsGoal?name=" + goalName + "&description=" + goalName + "&author=" + userName,
            dataType: "json",
            success: function (response) {
                if (response.id)
                    $('#requestGoalMessage').append('<div class="alert alert-success" role="alert">Request to add new analytics goal successfuly sent.</div>');
            },
            fail: function (response) {
                $('#requestGoalMessage').append('<div class="alert alert-danger">Request to add new analytics goal failed.</div>');
            }
        });
    }
    else {
        $('#requestGoalMessage').append('<div class="alert alert-danger">Please enter all fields.</div>');
    }
}

function resetSession() {
    var request = createRequest();
    var url ="/indicators/deleteDataFromSession";
    request.open("GET",url,true);
    request.onreadystatechange=function(){
        processScreenForNextQuestion(request, true)
    };
    request.send(null);
}

function SaveQuestionIndicators() {
    var goalId = document.getElementById("GoalSelection").value;
    var questionName = document.getElementById("questionNaming").value;
    var userName = document.getElementById("userName").value;


    $('#question_preview_msg').html("<div class='preview-err-msg right-align'>Please wait! The question is being saved and the Indicator Request Codes are being generated.</div>");
    $.ajax({
        type: "GET",
        url: "/engine/saveQuestionIndicators?userName=" + userName + "&goalId=" + goalId + "&questionName=" + questionName,
        dataType: "json",
        success: function (response) {
            showRequestCodes(response);
            $('#question_preview_msg').html("<div class='preview-success-msg right-align'>The question has been saved and the Indicator Request Codes are available for each indicator.</div>");
        },
        fail: function (response) {
            $('#question_preview_msg').html("<div class='preview-err-msg right-align'>There was error in saving the question and the Indicator Request Codes were not generated.</div>");
        }
    });
}

function showRequestCodes(response){

    clearGoalArea();
    clearQuestionArea();
    clearIndicatorArea();

    //refreshQuestionSummary();

    if (!response.isQuestionSaved) {
        alert(response.errorMessage);
    } else {

        var visualizationModal =  $("#visualizeQuestionModel");

        var qCardContentDivID = "#questionCardContent";
        var qCardContentDiv = visualizationModal.find(qCardContentDivID + " span")[0];
        var questionName = visualizationModal.find(qCardContentDivID).text();

        // var qCardMoreIcon = document.createElement("i");
        // qCardMoreIcon.className = "material-icons right";
        // qCardMoreIcon.style.fontSize = "48px";
        // qCardMoreIcon.setAttribute("title", "Get Indicator Request Code.");
        // var cardMoreIconText = document.createTextNode("code");
        // qCardMoreIcon.appendChild(cardMoreIconText);
        // qCardContentDiv.append(qCardMoreIcon);

        var qCardMoreAnchor = document.createElement("a");
        var qCardMoreIcon = document.createElement("i");
        qCardMoreIcon.setAttribute("data-position", "bottom");
        qCardMoreIcon.setAttribute("data-tooltip", "Get Question Request Code");
        qCardMoreIcon.className = "material-icons right";
        qCardMoreIcon.style.fontSize = "48px";
        var qCardMoreIconText = document.createTextNode("code");
        qCardMoreIcon.appendChild(qCardMoreIconText);
        qCardMoreAnchor.appendChild(qCardMoreIcon);
        qCardContentDiv.appendChild(qCardMoreAnchor);
        $(qCardMoreIcon).tooltip({delay: 100});

        var qCardRevealDiv = document.createElement("div");
        qCardRevealDiv.className = "card-reveal";
        qCardRevealDiv.style.wordWrap = "break-word";

        var qCardRevealTitleDiv = document.createElement("div");
        qCardRevealTitleDiv.className = "col-md-12";


        var qCardRevealSpan = document.createElement("span");
        qCardRevealSpan.className = "card-title grey-text text-darken-4";

        var qCardRevealTitle = document.createTextNode(questionName);

        qCardRevealSpan.appendChild(qCardRevealTitle);

        // var qCardCloseIcon = document.createElement("i");
        // qCardCloseIcon.className = "material-icons right";
        // var qCardCloseIconText = document.createTextNode("close");
        // qCardCloseIcon.appendChild(qCardCloseIconText);
        // qCardRevealSpan.appendChild(qCardCloseIcon);

        var qCardCloseAnchor = document.createElement("a");
        var qCardCloseIcon = document.createElement("i");
        qCardCloseIcon.setAttribute("data-position", "bottom");
        qCardCloseIcon.setAttribute("data-tooltip", "Close question request code area");
        qCardCloseIcon.className = "material-icons right";
        var qCardCloseIconText = document.createTextNode("close");
        qCardCloseIcon.appendChild(qCardCloseIconText);
        qCardCloseAnchor.appendChild(qCardCloseIcon);
        qCardRevealSpan.appendChild(qCardCloseAnchor);
        $(qCardCloseIcon).tooltip({delay: 100});

        var qCardCopyAnchor = document.createElement("a");
        qCardCopyAnchor.setAttribute("onclick", "copyIndicatorRequestCode(this,event);");
        var qCardCopyIcon = document.createElement("i");
        qCardCopyIcon.setAttribute("data-position", "bottom");
        qCardCopyIcon.setAttribute("data-tooltip", "Copy question request code");
        qCardCopyIcon.className = "material-icons right";
        var qCardCopyIconText = document.createTextNode("content_copy");
        qCardCopyIcon.appendChild(qCardCopyIconText);
        qCardCopyAnchor.appendChild(qCardCopyIcon);
        qCardRevealSpan.appendChild(qCardCopyAnchor);
        $(qCardCopyIcon).tooltip({delay: 100});

        qCardRevealTitleDiv.appendChild(qCardRevealSpan)

        qCardRevealDiv.appendChild(qCardRevealTitleDiv);

        var qCardRevealDividerDiv = document.createElement("div");
        qCardRevealDividerDiv.className = "col-md-12 divider";

        qCardRevealDiv.appendChild(qCardRevealDividerDiv);

        var qCardRevealCodeDiv = document.createElement("div");
        qCardRevealCodeDiv.className = "col-md-12 request-code";

        var qCardRevealTextPara = document.createElement("p");
        qCardRevealTextPara.setAttribute("contenteditable", "true");
        qCardRevealTextPara.setAttribute("onfocus", "document.execCommand('selectAll',false,null);");
        var qCardRevealText;

        if (response.isQuestionSaved)
            qCardRevealText = document.createTextNode(response.questionRequestCode);
        else
            qCardRevealText = document.createTextNode(response.errorMessage);

        qCardRevealTextPara.appendChild(qCardRevealText);
        qCardRevealCodeDiv.appendChild(qCardRevealTextPara);
        qCardRevealDiv.appendChild(qCardRevealCodeDiv);

        $("#questionCard").append(qCardRevealDiv);


        for(var i=0; i<response.indicatorSaveResponses.length; i++) {
            var cardContentDivID = "#visualizeCardGraphContent_" + response.indicatorSaveResponses[i].indicatorClientID;
            var cardContentDiv = visualizationModal.find(cardContentDivID + " span")[0];
            var indicatorName = visualizationModal.find(cardContentDivID).text();

            // var cardMoreIcon = document.createElement("i");
            // cardMoreIcon.className = "material-icons right";
            // cardMoreIcon.style.fontSize = "48px";
            // cardMoreIcon.setAttribute("title", "Get Indicator Request Code.");
            // var cardMoreIconText = document.createTextNode("code");
            // cardMoreIcon.appendChild(cardMoreIconText);
            // cardContentDiv.append(cardMoreIcon);

            var cardMoreAnchor = document.createElement("a");
            var cardMoreIcon = document.createElement("i");
            cardMoreIcon.setAttribute("data-position", "bottom");
            cardMoreIcon.setAttribute("data-tooltip", "Get Indicator Request Code");
            cardMoreIcon.className = "material-icons right";
            cardMoreIcon.style.fontSize = "48px";
            var cardMoreIconText = document.createTextNode("code");
            cardMoreIcon.appendChild(cardMoreIconText);
            cardMoreAnchor.appendChild(cardMoreIcon);
            cardContentDiv.appendChild(cardMoreAnchor);
            $(cardMoreIcon).tooltip({delay: 100});

            var cardRevealDiv = document.createElement("div");
            cardRevealDiv.className = "card-reveal";
            cardRevealDiv.style.wordWrap = "break-word";

            var cardRevealTitleDiv = document.createElement("div");
            cardRevealTitleDiv.className = "col-md-12";


            var cardRevealSpan = document.createElement("span");
            cardRevealSpan.className = "card-title grey-text text-darken-4";

            var cardRevealTitle = document.createTextNode(indicatorName);
            //cardRevealTitle.innerHTML = indicatorName;

            cardRevealSpan.appendChild(cardRevealTitle);

            // var cardCloseIcon = document.createElement("i");
            // cardCloseIcon.className = "material-icons right";
            // var cardCloseIconText = document.createTextNode("close");
            // cardCloseIcon.appendChild(cardCloseIconText);
            // cardRevealSpan.appendChild(cardCloseIcon);

            var cardCloseAnchor = document.createElement("a");
            var cardCloseIcon = document.createElement("i");
            cardCloseIcon.setAttribute("data-position", "bottom");
            cardCloseIcon.setAttribute("data-tooltip", "Close indicator request code area");
            cardCloseIcon.className = "material-icons right";
            var cardCloseIconText = document.createTextNode("close");
            cardCloseIcon.appendChild(cardCloseIconText);
            cardCloseAnchor.appendChild(cardCloseIcon);
            cardRevealSpan.appendChild(cardCloseAnchor);
            $(cardCloseIcon).tooltip({delay: 100});

            var cardCopyAnchor = document.createElement("a");
            cardCopyAnchor.setAttribute("onclick", "copyIndicatorRequestCode(this,event);");
            var cardCopyIcon = document.createElement("i");
            cardCopyIcon.setAttribute("data-position", "bottom");
            cardCopyIcon.setAttribute("data-tooltip", "Copy indicator request code");
            cardCopyIcon.className = "material-icons right";
            var cardCopyIconText = document.createTextNode("content_copy");
            cardCopyIcon.appendChild(cardCopyIconText);
            cardCopyAnchor.appendChild(cardCopyIcon);
            cardRevealSpan.appendChild(cardCopyAnchor);
            $(cardCopyIcon).tooltip({delay: 100});

            cardRevealTitleDiv.appendChild(cardRevealSpan)

            cardRevealDiv.appendChild(cardRevealTitleDiv);

            var cardRevealDividerDiv = document.createElement("div");
            cardRevealDividerDiv.className = "col-md-12 divider";

            cardRevealDiv.appendChild(cardRevealDividerDiv);

            var cardRevealCodeDiv = document.createElement("div");
            cardRevealCodeDiv.className = "col-md-12 request-code";

            var cardRevealTextPara = document.createElement("p");
            cardRevealTextPara.setAttribute("contenteditable", "true");
            cardRevealTextPara.setAttribute("onfocus", "document.execCommand('selectAll',false,null);");
            var cardRevealText;

            if (response.indicatorSaveResponses[i].isIndicatorSaved)
                cardRevealText = document.createTextNode(response.indicatorSaveResponses[i].indicatorRequestCode);
            else
                cardRevealText = document.createTextNode(response.indicatorSaveResponses[i].errorMessage);

            cardRevealTextPara.appendChild(cardRevealText);

            cardRevealCodeDiv.appendChild(cardRevealTextPara);

            cardRevealDiv.appendChild(cardRevealCodeDiv);

            $("#visualizeCardGraph_" + response.indicatorSaveResponses[i].indicatorClientID).append(cardRevealDiv);
        }

        //question_preview_msg
    }
}

function processScreenForNextQuestion(request, isResetSession) {
    isResetSession = isResetSession || false;
    if (request.readyState == 4) {
        if (request.status == 200) {
            if(request.responseText) {
                var parsedJSON = JSON.parse(request.responseText);
            }
            document.getElementById("questionNaming").value = "";
            document.getElementById("indicatorNaming").value = "";
            var selectedMinor = document.getElementById("selectedMinor");
            removeOptions(selectedMinor);
            refreshQuestionSummary();
            $("#saveQuestion").attr('disabled', 'disabled');

            if(!isResetSession) {
                if (!parsedJSON.isQuestionSaved) {
                    alert(parsedJSON.errorMessage);
                } else {

                    var visualizationModal =  $("#visualizeQuestionModel");
                    for(var i=0; i<parsedJSON.indicatorSaveResponses.length; i++) {
                        if (parsedJSON.indicatorSaveResponses[i].isIndicatorSaved) {

                            var cardContentDivID = "#visualizeCardGraphContent_" + parsedJSON.indicatorSaveResponses[i].indicatorClientID;
                            var cardContentDiv = visualizationModal.find(cardContentDivID + " span");
                            var indicatorName = visualizationModal.find(cardContentDivID).text();
                            var cardMoreIcon = document.createElement("i");
                            cardMoreIcon.className = "material-icons right";
                            var cardMoreIconText = document.createTextNode("more_vert");
                            cardMoreIcon.appendChild(cardMoreIconText);
                            cardContentDiv.append(cardMoreIcon);


                            var cardRevealDiv = document.createElement("div");
                            cardRevealDiv.className = "card-reveal";

                            var cardRevealSpan = document.createElement("span");
                            cardRevealSpan.className = "card-title grey-text text-darken-4";
                            var cardRevealTitle = document.createElement('b');
                            cardRevealTitle.innerHTML = indicatorName;
                            cardRevealSpan.appendChild(cardRevealTitle);
                            var cardCloseIcon = document.createElement("i");
                            cardCloseIcon.className = "material-icons right";
                            var cardCloseIconText = document.createTextNode("close");
                            cardCloseIcon.appendChild(cardCloseIconText);
                            cardRevealSpan.appendChild(cardCloseIcon);

                            cardRevealDiv.appendChild(cardRevealSpan);

                            var cardRevealTextPara = document.createElement("p");
                            var cardRevealText = document.createTextNode(parsedJSON.indicatorSaveResponses[i].indicatorRequestCode);

                            cardRevealTextPara.appendChild(cardRevealText);
                            cardRevealDiv.appendChild(cardRevealTextPara);

                            $("#visualizeCardGraph_" + parsedJSON.indicatorSaveResponses[i].indicatorClientID).append(cardRevealDiv);
                        }
                    }
                }
            }
        }
    }
}

function resetQuestionVisualization() {
    //$("#visualizeQuestionContent").empty();
}

function QuestionVisualize() {
    var request = createRequest();
    var url ="/indicators/refreshQuestionSummary";
    request.open("GET",url,true);
    request.onreadystatechange=function(){updateVisualizationModal(request)};
    request.send(null);
}

function updateVisualizationModal(request) {
    if (request.readyState == 4) {
        if (request.status == 200) {
            var parsedJSON = JSON.parse(request.responseText);

            if(parsedJSON.sessionIndicators.length ==0) {

                var visualizeQModelHtml = document.getElementById("visualizeQuestionContent");
                visualizeQModelHtml.innerHTML = "";

                var div1 = document.createElement("div");
                div1.className = "alert alert-warning";
                var alertDescription1 = document.createTextNode("Please add Indicators for visualization.");
                div1.appendChild(alertDescription1);
                visualizeQModelHtml.appendChild(div1);
            }
            else {
                var visualizeQModelHtml = document.getElementById("visualizeQuestionContent");
                visualizeQModelHtml.innerHTML = "";

                var qColDiv = document.createElement("div");
                qColDiv.className = "col-md-12";

                var qCardDiv = document.createElement("div");
                qCardDiv.className = "question-vis-card card";
                qCardDiv.id = "questionCard";

                var qCardImageDiv = document.createElement("div");
                qCardImageDiv.className = "full-width row";
                qCardImageDiv.id = "questionCard_Image";

                qCardDiv.appendChild(qCardImageDiv);

                var qCardContentDiv = document.createElement("div");
                qCardContentDiv.className = "card-content";
                qCardContentDiv.id = "questionCardContent";

                var qCardContentSpan = document.createElement("span");
                qCardContentSpan.className = "card-title activator grey-text text-darken-4";
                qCardContentSpan.style.display = "block";
                var qCardTitle = document.createTextNode(parsedJSON.questionName);
                qCardContentSpan.appendChild(qCardTitle);

                qCardContentDiv.appendChild(qCardContentSpan);
                qCardDiv.appendChild(qCardContentDiv);
                qColDiv.appendChild(qCardDiv);
                visualizeQModelHtml.appendChild(qColDiv);


                for(var i=0; i<parsedJSON.sessionIndicators.length; i++) {

                    //visualization card
                    var colDiv = document.createElement("div");
                    colDiv.className = "col-md-6";

                    var cardDiv = document.createElement("div");
                    cardDiv.className = "question-vis-card card";
                    var cardDivId = "visualizeCardGraph_" + parsedJSON.sessionIndicators[i].identifier;
                    cardDiv.id = cardDivId;

                    var cardImageDiv = document.createElement("div");
                    //cardImageDiv.className = "card-image waves-effect waves-block waves-light";
                    cardImageDiv.className = "full-width";

                    var divId = "visualizeCardGraph_" + parsedJSON.sessionIndicators[i].indicatorName.replace(/ /g,"_");
                    cardImageDiv.id = divId;

                    cardDiv.appendChild(cardImageDiv);

                    var cardContentDiv = document.createElement("div");
                    cardContentDiv.className = "card-content";
                    var contentDivId = "visualizeCardGraphContent_" + parsedJSON.sessionIndicators[i].identifier;
                    cardContentDiv.id = contentDivId;

                    var cardContentSpan = document.createElement("span");
                    cardContentSpan.className = "card-title activator grey-text text-darken-4";
                    cardContentSpan.style.display = "block";
                    var cardTitle = document.createTextNode(parsedJSON.sessionIndicators[i].indicatorName);
                    cardContentSpan.appendChild(cardTitle);

                    cardContentDiv.appendChild(cardContentSpan);

                    cardDiv.appendChild(cardContentDiv);

                    colDiv.appendChild(cardDiv);

                    //visualizeQModelHtml.appendChild(colDiv);
                    qCardImageDiv.appendChild(colDiv);

                    //var parsedVisualization = JSON.parse(parsedJSON.sessionIndicators[i].visualization);
                    var decodedGraphData = decodeURIComponent(parsedJSON.sessionIndicators[i].visualization);
                    decodedGraphData = decodedGraphData.replace("xxxwidthxxx","$('#" + divId + "').outerWidth(true)");
                    decodedGraphData = decodedGraphData.replace("xxxheightxxx","$('#" + divId + "').outerHeight(true)");

                    //console.log(divId);
                    $('#'+divId).html(decodedGraphData);
                }
                $("#saveQuestion").removeAttr('disabled');

            }

        }
    }
}

function loadIndfromDB() {
    $('#qiEditorTab a[href="#TemplateLoad"]').tab('show');
}


function displayReceivedIndicators(request) {
    if (request.readyState == 4) {
        if (request.status == 200) {
            var parsedJSON = JSON.parse(request.responseText);
            var searchResults = document.getElementById("searchResults");
            removeOptions(searchResults);
            for (var i=0;i< parsedJSON.length;i++) {
                var newOption = new Option(parsedJSON[i], parsedJSON[i]);
                searchResults.appendChild(newOption);
            }
        }
    }
}

/*function viewIndicatorProp() {
    var indName = $('tr.selected:first td:nth-child(2)', '#indicatorData').text();
    var request = createRequest();
    var url ="/indicators/loadIndicator?indName="+indName;
    request.open("GET",url,true);
    request.onreadystatechange=function(){displayIndicatorProp(request)};
    request.send(null);
}*/

function enableIndicatorLoad(){
    var indicatorTable = $('#indicatorData').DataTable();
    var selectedRows = indicatorTable.rows(".selected").data();
    if(selectedRows && selectedRows.length > 0) {
        var indType = selectedRows[0].indicatorType;

        if (indType == 'simple' || indType == 'multianalysis' )
            $("#loadIndicatorBtn").removeAttr('disabled');
        else
            $("#loadIndicatorBtn").attr('disabled', 'disabled');
    }
    else{
        $("#loadIndicatorBtn").attr('disabled', 'disabled');
    }
}

function displayIndicatorProp() {
    var indicatorTable = $('#indicatorData').DataTable();
    var selectedRows = indicatorTable.rows(".selected").data();
    if(selectedRows && selectedRows.length > 0) {
        var selectedIndicator = selectedRows[0];


        var properties = JSON.parse(selectedIndicator.parameters);

        var indicatorData = new Array();
        indicatorData.push(["Property", "Value"]);
        indicatorData.push(["Indicator Name", selectedIndicator.name]);
        indicatorData.push(["Chart Type", properties.visualizationType]);
        indicatorData.push(["Chart Engine", properties.visualizationLibrary]);
        indicatorData.push(["Entity Filters", properties.indicatorDataset[0].entityValues.length]);
        indicatorData.push(["Session Filters", properties.indicatorDataset[0].sessionSpecifications.length]);
        indicatorData.push(["User Filters", properties.indicatorDataset[0].userSpecifications.length]);
        indicatorData.push(["Time Filters", properties.indicatorDataset[0].timeSpecifications.length]);
        indicatorData.push(["Sources", properties.indicatorDataset[0].selectedSource]);
        indicatorData.push(["Platform", properties.indicatorDataset[0].selectedPlatform]);
        indicatorData.push(["Actions", properties.indicatorDataset[0].selectedAction]);
        indicatorData.push(["Categories", properties.indicatorDataset[0].selectedMinor]);
        //indicatorData.push(["Major", properties.major]);
        //indicatorData.push(["Hibernate Query", properties.hql]);

        //Create a HTML Table element.
        var table = document.createElement("TABLE");
        table.id = "indicatorPropsTable";
        table.border = "1";
        table.className = "table table-bordered";

        //Get the count of columns.
        var columnCount = indicatorData[0].length;

        //Add the header row.
        // var row = table.insertRow(-1);

        var tableHead = document.createElement('THEAD');
        table.appendChild(tableHead);
        for (var i = 0; i < columnCount; i++) {
            var headerCell = document.createElement("TH");
            headerCell.innerHTML = indicatorData[0][i];
            tableHead.appendChild(headerCell);
        }

        //Add the data rows.
        var tableBody = document.createElement('TBODY');
        table.appendChild(tableBody);
        for (var i = 1; i < indicatorData.length; i++) {
            var tr = document.createElement('TR');
            tableBody.appendChild(tr);
            // row = table.insertRow(-1);
            for (var j = 0; j < columnCount; j++) {
                // var cell = row.insertCell(-1);
                // cell.innerHTML = indicatorData[i][j];
                var td = document.createElement('TD');
                td.appendChild(document.createTextNode(indicatorData[i][j]));
                tr.appendChild(td);
            }
        }

        var dvTable = document.getElementById("IndPropsFromDB");

        dvTable.innerHTML = "<hr><b>Indicator Properties: </b><hr>";
        dvTable.appendChild(table);
    }
}

function loadFromTemplate() {
    // var indName = document.getElementById("searchResults").value;

    var indicatorTable = $('#indicatorData').DataTable();
    var selectedRows = indicatorTable.rows(".selected").data();
    var selectedRowId = selectedRows[0].id;
    var selectedRowType = selectedRows[0].indicatorType;


    //var indName = $('tr.selected:first td:nth-child(2)', '#indicatorData').text();

    var dvTable = document.getElementById("IndPropsFromDB");
    dvTable.innerHTML = "";
    var request = createRequest();

    //var url ="/indicators/loadIndicator?indName="+indName+"&loadTemplate=Y";

    var url ="/engine/loadIndicatorToSession?indicatorId="+selectedRowId+"&loadTemplate=Y";

    request.open("GET",url,true);
    request.onreadystatechange=function(){
        if (request.readyState == 4) {
            if (request.status == 200) {
                if(selectedRowType == "simple") {
                    localStorage.setItem('indType', "simple");
                    loadIndicatorTemplate(selectedRows[0]);

                    $("#indicatorDefinition").show();
                    $('body').animate({
                        scrollTop: $("#indicatorDefinition").offset().top
                    }, 1000);
                }
                else if(selectedRowType == "multianalysis"){
                    localStorage.setItem('indType', "multianalysis");
                    loadMLAIIndicatorTemplate(selectedRows[0]);

                    $("#mlai_indicatorDefinition").show();
                    $('body').animate({
                        scrollTop: $("#mlai_indicatorDefinition").offset().top
                    }, 1000);
                }
                $('#indicatorData tbody').children().removeClass("selected");
            }
            else
                console.log("Indicator not loaded in the session. Error code:" + request.status);
        }};
    request.send(null);
}

function searchAttributes() {
    searchAttributeValues();
}

function clickElement(elementid, event){
    event.stopPropagation();
    $('#'+elementid).removeClass("active");
    $('#'+elementid).trigger('click');
}
