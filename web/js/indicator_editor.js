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
 * Created by Tanmaya Mahapatra on 18-06-2015.
 */
$(function() {

    $( "#accordionFilterSummary" ).accordion({
        event: "click hoverintent",
        heightStyle: "fill"
    });
    $( "#accordionQuestionSummary" ).accordion({
        event: "click hoverintent",
        heightStyle: "fill"
    });
    $( "#accordionIndProp" ).accordion({
        event: "click hoverintent",
        heightStyle: "content"
    });
    $( "#accordionFilter" ).accordion({
        event: "click hoverintent",
        heightStyle: "fill"

    });
    $( "#accordionGraphSettings" ).accordion({
        event: "click hoverintent",
        heightStyle: "fill"
    });
    $( "#accordionIndicatorSummary" ).accordion({
        event: "click hoverintent",
        heightStyle: "fill",
    });
    $( document ).tooltip();

    $( "#questionHelpDialog" ).dialog({
        modal: true,
        buttons: {
            Ok: function() {
                $( this ).dialog( "close" );
            }
        },
        autoOpen: false,
        show: {
            effect: "slide",
            duration: 1000
        },
        hide: {
            effect: "clip",
            duration: 1000
        },
        height: 'auto',
        maxWidth: 600,
        minWidth: 500,
        position: 'center',
        resizable: false
    });
    $( "#questionSummaryDialog" ).dialog({
        modal: true,
        buttons: {
            Ok: function() {
                $( this ).dialog( "close" );
            }
        },
        autoOpen: false,
        show: {
            effect: "slide",
            duration: 1000
        },
        hide: {
            effect: "clip",
            duration: 1000
        },
        height: 'auto',
        maxWidth: 600,
        minWidth: 500,
        position: 'center',
        resizable: false
    });
    $( "#indicatorHelpDialog" ).dialog({
        modal: true,
        buttons: {
            Ok: function() {
                $( this ).dialog( "close" );
            }
        },
        autoOpen: false,
        show: {
            effect: "slide",
            duration: 1000
        },
        hide: {
            effect: "clip",
            duration: 1000
        },
        height: 'auto',
        maxWidth: 600,
        minWidth: 500,
        position: 'center',
        resizable: false
    });
    $( "#indViewDialog" ).dialog({
        modal: true,
        buttons: {
            Ok: function() {
                $( this ).dialog( "close" );
            }
        },
        autoOpen: false,
        show: {
            effect: "slide",
            duration: 1000
        },
        hide: {
            effect: "clip",
            duration: 1000
        },
        height: 'auto',
        maxWidth: 1200,
        minWidth: 900,
        position: 'center',
        resizable: true
    });
    $( "#indDeleteDialog" ).dialog({
        modal: true,
        buttons: {
            Ok: function() {
                $( this ).dialog( "close" );
            }
        },
        autoOpen: false,
        show: {
            effect: "slide",
            duration: 1000
        },
        hide: {
            effect: "clip",
            duration: 1000
        },
        height: 'auto',
        maxWidth: 800,
        minWidth: 700,
        position: 'center',
        resizable: false
    });

    $( "#helpQuestionInfo" ).click(function() {
        $( "#questionHelpDialog" ).dialog( "open" );
    });
    $( "#helpQuestionSummary" ).click(function() {
        $( "#questionSummaryDialog" ).dialog( "open" );
    });
    $( "#helpIndicatorInfo" ).click(function() {
        $( "#indicatorHelpDialog" ).dialog( "open" );
    });
    $( "#indView" ).click(function(e){
        $.ajax({type: "GET",
            url: "/indicators/refreshQuestionSummary",
            data: { indName: $("#associatedIndicators").val() },
            dataType: "json", // json
            success:function(response){
                //$("#indViewDialog").text(JSON.stringify(response));
                GenerateTable(response)
                $("#indViewDialog").dialog("open");
            }});
    });
    $( "#indDelete" ).click(function(e){
        $.ajax({type: "GET",
            url: "/indicators/deleteIndFromQn",
            data: { indName: $("#associatedIndicators").val() },
            dataType: "html",
            success:function(response){
                $("#indDeleteDialog").text(response);
                $('.indDeleteDialog').dialog('option', 'title', 'Indicator Deletion Message');
                refreshQuestionSummary();
                $("#indDeleteDialog").dialog("open");
            }});
    });
});
function  GenerateTable(data) {

    var indicatorData = new Array();
    indicatorData.push(["S/L", "Property", "Value"]);
    indicatorData.push([1, "Indicator Name", data.indicatorName]);
    indicatorData.push([2, "Chart Type", data.genIndicatorProps.chartType]);
    indicatorData.push([3, "Chart Engine", data.genIndicatorProps.chartEngine]);
    indicatorData.push([4, "Entity Filters", data.indicatorXMLData.entityValues.length]);
    indicatorData.push([5, "Session Filters", data.indicatorXMLData.sessionSpecifications.length]);
    indicatorData.push([6, "User Filters", data.indicatorXMLData.userSpecifications.length]);
    indicatorData.push([7, "Time Filters", data.indicatorXMLData.timeSpecifications.length]);

    /*indicatorData.push([2, "Hibernate Query", data.query]);
     indicatorData.push([5, "Sources", data.indicatorXMLData.source]);
     indicatorData.push([6, "Platform", data.indicatorXMLData.platform]);
     indicatorData.push([7, "Action", data.indicatorXMLData.action]);
     indicatorData.push([8, "Minor", data.indicatorXMLData.minor]);
     indicatorData.push([9, "Major", data.indicatorXMLData.major]); */

    //Create a HTML Table element.
    var table = document.createElement("TABLE");
    table.border = "1";

    //Get the count of columns.
    var columnCount = indicatorData[0].length;

    //Add the header row.
    var row = table.insertRow(-1);
    for (var i = 0; i < columnCount; i++) {
        var headerCell = document.createElement("TH");
        headerCell.innerHTML = indicatorData[0][i];
        row.appendChild(headerCell);
    }

    //Add the data rows.
    for (var i = 1; i < indicatorData.length; i++) {
        row = table.insertRow(-1);
        for (var j = 0; j < columnCount; j++) {
            var cell = row.insertCell(-1);
            cell.innerHTML = indicatorData[i][j];
        }
    }

    var dvTable = document.getElementById("indBasicProperty");
    dvTable.innerHTML = "";
    dvTable.appendChild(table);
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

var request;
function createRequest() {
    try {
        request = new XMLHttpRequest();
    } catch(failed){
        alert("Error creating request object!");
        request = null;
    }
}
function removeOptions(selectbox)
{
    var i;
    for(i=selectbox.options.length-1;i>=0;i--)
    {
        selectbox.remove(i);
    }
}


function validateQuestionName(){
    createRequest();
    var questionNameEntered = document.getElementById('questionNaming').value;
    var url ="/indicators/validateQName?qname="+questionNameEntered;
    request.open("GET",url,true);
    request.onreadystatechange=alert_questionName;
    request.send(null);
}

function validateIndicatorName(obj){
    createRequest();
    var indicatorNameEntered = document.getElementById('indicatorNaming').value;
    var url ="/indicators/validateIndName?indname="+indicatorNameEntered;
    request.open("GET",url,true);
    request.onreadystatechange=alert_indicatorName;
    request.send(null);
}

function populateCategories(){
    createRequest();
    var selectedSources = "";
    var selectedArray = new Array();
    var selObj = document.getElementById('sourceSelection');
    var i;
    var count = 0;
    for (i=0; i<selObj.options.length; i++) {
        if (selObj.options[i].selected) {
            selectedArray[count] = selObj.options[i].value;
            count++;
        }
    }
    selectedSources = selectedArray;
    var selectedAction = document.getElementById('actionSelection').value;
    var selectedPlatform = document.getElementById('PlatformSelection').value;
    var url ="/indicators/populateCategories?action="+selectedAction+"&platform="+selectedPlatform+"&sources="+selectedSources;
    request.open("GET",url,true);
    request.onreadystatechange=processReceivedCategories;
    request.send(null);

}

function populateEntities() {
    createRequest();
    var minorSelected = document.getElementById('selectedMinor').value;
    var url ="/indicators/populateEntities?minor="+minorSelected;
    request.open("GET",url,true);
    request.onreadystatechange=processReceivedEntities;
    request.send(null);
}

function addEntity(){
    createRequest();
    var keySelected = document.getElementById("entityKeySelection").value;
    var searchType = document.getElementById("specificationType").value;
    var evalue = document.getElementById("entityValue").value;
    var url ="/indicators/addEntity?key="+keySelected+"&search="+searchType+"&value="+evalue;
    request.open("GET",url,true);
    request.onreadystatechange=displayEntityFilters;
    request.send(null);
}

function deleteEntity(){
    createRequest();
    var url ="/indicators/deleteEntities";
    request.open("GET",url,true);
    request.onreadystatechange=displayEntityFilters;
    request.send(null);
}

function searchUser() {
    createRequest();
    var userType = document.getElementById("userType").value;
    var searchUserString = document.getElementById("searchUserString").value;
    var url ="/indicators/searchUser?keyword="+searchUserString+"&searchtype="+userType;
    request.open("GET",url,true);
    request.onreadystatechange=displaySearchUserResults;
    request.send(null);
}

function searchTime(){
    createRequest();
    var timeType = document.getElementById("timeSearchType").value;
    var searchTimeString = document.getElementById("timeSearchString").value;
    var url ="/indicators/searchTime?searchTime="+searchTimeString+"&timeType="+timeType;
    request.open("GET",url,true);
    request.onreadystatechange=displaySearchTimeResults;
    request.send(null);
}

function addUserFilter() {
    createRequest();
    var userType = document.getElementById("userType").value;
    var userdata = document.getElementById("usersearchResults").value;
    var UsersearchType = document.getElementById("UsersearchType").value;
    var url ="/indicators/addUserFilter?userdata="+userdata+"&searchType="+UsersearchType+"&userType="+userType;
    request.open("GET",url,true);
    request.onreadystatechange=displayUserFilters;
    request.send(null);
}

function addTimeFilter() {
    createRequest();
    var timeSearchType = document.getElementById("timeSelectionType").value;
    var timeString;
    var selectedArray = new Array();
    var selObj = document.getElementById("timeSearchResults");
    var i;
    var count = 0;
    for (i=0; i< selObj.options.length; i++) {
        if (selObj.options[i].selected) {
            selectedArray[count] = selObj.options[i].value;
            count++;
        }
    }
    timeString = selectedArray;
    var url ="/indicators/addTimeFilter?time="+timeString+"&timeType="+timeSearchType;
    request.open("GET",url,true);
    request.onreadystatechange=displayTimeFilters;
    request.send(null);
}

function displayUserFilters() {
    if (request.readyState == 4) {
        if (request.status == 200) {
            var parsedJSON = JSON.parse(request.responseText);
            var heading = new Array();
            heading[0] = "S/L";
            heading[1] = "User Search Type";
            heading[2] = "User Search";
            heading[3] = "search Pattern";

            var data = new Array();

            for (var i=0;i< parsedJSON.length;i++) {
                data[i] = new Array(i+1, parsedJSON[i].userSearchType, parsedJSON[i].userSearch, parsedJSON[i].searchPattern);
            }
        }
        addTable(heading,data,"user_filters");
    }
}
function displayTimeFilters() {
    if (request.readyState == 4) {
        if (request.status == 200) {
            var parsedJSON = JSON.parse(request.responseText);
            var heading = new Array();
            heading[0] = "S/L";
            heading[1] = "Time Search Type";
            heading[2] = "Time Value";

            var data = new Array();

            for (var i=0;i< parsedJSON.length;i++) {
                data[i] = new Array(i+1, parsedJSON[i].type, parsedJSON[i].timestamp);
            }
        }
        addTable(heading,data,"time_filters");
    }
}

function refreshTimeFilters() {
    createRequest();
    var url ="/indicators/getTimeFilters";
    request.open("GET",url,true);
    request.onreadystatechange=displayTimeFilters;
    request.send(null);
}

function deleteTimeFilters() {
    createRequest();
    var url ="/indicators/deleteTimeFilters";
    request.open("GET",url,true);
    request.onreadystatechange=displayTimeFilters;
    request.send(null);
}

function refreshUserFilters() {
    createRequest();
    var url ="/indicators/getUserFilters";
    request.open("GET",url,true);
    request.onreadystatechange=displayUserFilters;
    request.send(null);
}

function deleteUserFilters() {
    createRequest();
    var url ="/indicators/deleteUserFilters";
    request.open("GET",url,true);
    request.onreadystatechange=displayUserFilters;
    request.send(null);
}

function displaySearchUserResults() {
    if (request.readyState == 4) {
        if (request.status == 200) {
            var parsedJSON = JSON.parse(request.responseText);
            var userSearchResults = document.getElementById("usersearchResults");
            removeOptions(userSearchResults);
            for (var i=0;i< parsedJSON.length;i++) {
                var newOption = new Option(parsedJSON[i], parsedJSON[i]);
                userSearchResults.appendChild(newOption);
            }
        }
    }
}

function displaySearchTimeResults() {
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

function searchSession() {
    createRequest();
    var SessionSearchType = document.getElementById("sessionSearchType").value;
    var sessionSearchString = document.getElementById("sessionSearchString").value;
    var url ="/indicators/searchSession?keyword="+sessionSearchString+"&searchType="+SessionSearchType;
    request.open("GET",url,true);
    request.onreadystatechange=displaySearchSessionResults;
    request.send(null);
}

function addSessionFilter() {
    createRequest();
    var SessionData = document.getElementById("SessionsearchResults").value;
    var SessionSearchType = document.getElementById("SessionsearchType").value;
    var url ="/indicators/addSessionFilter?sessionData="+SessionData+"&searchType="+SessionSearchType;
    request.open("GET",url,true);
    request.onreadystatechange=displaySessionFilters;
    request.send(null);

}

function displaySessionFilters() {
    if (request.readyState == 4) {
        if (request.status == 200) {
            var parsedJSON = JSON.parse(request.responseText);
            var heading = new Array();
            heading[0] = "S/L";
            heading[1] = "Session Data";
            heading[2] = "Filter Type";

            var data = new Array();

            for (var i=0;i< parsedJSON.length;i++) {
                data[i] = new Array(i+1, parsedJSON[i].session, parsedJSON[i].type);
            }
        }
        addTable(heading,data,"session_filters");
    }
}

function refreshSessionFilters() {
    createRequest();
    var url ="/indicators/getSessionFilters";
    request.open("GET",url,true);
    request.onreadystatechange=displaySessionFilters;
    request.send(null);
}

function deleteSessionFilters() {
    createRequest();
    var url ="/indicators/deleteSessionFilters";
    request.open("GET",url,true);
    request.onreadystatechange=displaySessionFilters;
    request.send(null);
}

function displaySearchSessionResults() {
    if (request.readyState == 4) {
        if (request.status == 200) {
            var parsedJSON = JSON.parse(request.responseText);
            var SessionSearchResults = document.getElementById("SessionsearchResults");
            removeOptions(SessionSearchResults);
            for (var i=0;i< parsedJSON.length;i++) {
                var newOption = new Option(parsedJSON[i], parsedJSON[i]);
                SessionSearchResults.appendChild(newOption);
            }
        }
    }
}

function refreshEntityFilters() {
    createRequest();
    var url ="/indicators/getEntities";
    request.open("GET",url,true);
    request.onreadystatechange=displayEntityFilters;
    request.send(null);
}

function displayEntityFilters(){
    if (request.readyState == 4) {
        if (request.status == 200) {
            var parsedJSON = JSON.parse(request.responseText);
            var heading = new Array();
            heading[0] = "S/L";
            heading[1] = "Key";
            heading[2] = "Value";
            heading[3] = "Type";

            var data = new Array();

            for (var i=0;i< parsedJSON.length;i++) {
                data[i] = new Array(i+1, parsedJSON[i].key, parsedJSON[i].eValues, parsedJSON[i].type);
            }
        }
        addTable(heading,data, "entity_filters");
    }
}

function processReceivedEntities() {
    if (request.readyState == 4) {
        if (request.status == 200) {
            var parsedJSON = JSON.parse(request.responseText);
            var entityKeySelection = document.getElementById("entityKeySelection");
            removeOptions(entityKeySelection);
            for (var i=0;i< parsedJSON.length;i++) {
                var newOption = new Option(parsedJSON[i], parsedJSON[i]);
                entityKeySelection.appendChild(newOption);
            }
        }
    }
}

function processReceivedCategories() {
    if (request.readyState == 4) {
        if (request.status == 200) {
            var parsedJSON = JSON.parse(request.responseText);
            var selectedMinor = document.getElementById("selectedMinor");
            removeOptions(selectedMinor);
            for (var i=0;i< parsedJSON.length;i++) {
                var newOption = new Option(parsedJSON[i].minor, parsedJSON[i].minor);
                selectedMinor.appendChild(newOption);
            }
        }
    }
}

function alert_questionName() {
    if (request.readyState == 4) {
        if (request.status == 200) {

            if (request.responseText == "exists") {

                alert("Question name already Existing. Duplicate names not allowed");
                document.getElementById("questionNaming").value=null;
            }
            else if (request.responseText == "short") {

                alert("Question Name is too Short");
                document.getElementById("questionNaming").value=null;
            }
            else if (request.responseText == "null") {

                alert("Question Name cannot be Empty");
                document.getElementById("questionNaming").value=null;
            }
        }
    }
}

function alert_indicatorName() {
    if (request.readyState == 4) {
        if (request.status == 200) {

            if (request.responseText == "exists") {

                alert("Indicator name already Existing. Duplicate names not allowed");
                document.getElementById("indicatorNaming").value=null;
            }
            else if (request.responseText == "short") {

                alert("Indicator Name is too Short");
                document.getElementById("indicatorNaming").value=null;
            }
            else if (request.responseText == "null") {

                alert("Indicator Name cannot be Empty");
                document.getElementById("indicatorNaming").value=null;
            }
        }
    }
}

function addTable(heading,data, tablePlacement) {
    var myTableDiv = document.getElementById(tablePlacement);
    myTableDiv.innerHTML = "";
    var table = document.createElement('TABLE');
    table.tagName = "TABLE";
    var tableBody = document.createElement('TBODY');

    table.border = '1';
    table.appendChild(tableBody);

    //TABLE COLUMNS
    var tr = document.createElement('TR');
    tableBody.appendChild(tr);
    for (i = 0; i < heading.length; i++) {
        var th = document.createElement('TH');
        th.width = '75';
        th.appendChild(document.createTextNode(heading[i]));
        tr.appendChild(th);
    }

    //TABLE ROWS
    for (i = 0; i < data.length; i++) {
        var tr = document.createElement('TR');
        for (j = 0; j < data[i].length; j++) {
            var td = document.createElement('TD');
            td.appendChild(document.createTextNode(data[i][j]));
            tr.appendChild(td)
        }
        tableBody.appendChild(tr);
    }
    myTableDiv.appendChild(table);
}

function refreshGraph() {

    var questionName = document.getElementById("questionNaming").value;
    var indicatorName = document.getElementById("indicatorNaming").value;
    var graphType = document.getElementById("selectedChartType").value;
    var graphEngine = document.getElementById("EngineSelect").value;
    createRequest();
    var url ="/indicators/refreshGraph?questionName="+questionName+"&indicatorName="+indicatorName+"&graphType="+graphType
        +"&graphEngine="+graphEngine;
    request.open("GET",url,true);
    request.onreadystatechange=drawGraph;
    request.send(null);
}

function drawGraph() {
    if (request.readyState == 4) {
        if (request.status == 200) {
            var graphImage = document.getElementById("graphImage");
            graphImage.src="/graphs/jgraph?bean=true"+"&time="+new Date().getTime();
        }
    }
}
function finalizeIndicator() {
    createRequest();
    var questionName = document.getElementById("questionNaming").value;
    var indicatorName = document.getElementById("indicatorNaming").value;
    var graphType = document.getElementById("selectedChartType").value;
    var graphEngine = document.getElementById("EngineSelect").value;
    var url ="/indicators/finalize?questionName="+questionName+"&indicatorName="+indicatorName+"&graphType="+graphType
        +"&graphEngine="+graphEngine;
    request.open("GET",url,true);
    request.onreadystatechange=postrefreshQuestionSummary;
    request.send(null);
}

function addNewIndicator() {
    createRequest();
    var url ="/indicators/addNewIndicator";
    request.open("GET",url,true);
    request.onreadystatechange=processScreenForNextIndicator;
    request.send(null);
}

function refreshQuestionSummary() {
    createRequest();
    var url ="/indicators/refreshQuestionSummary";
    request.open("GET",url,true);
    request.onreadystatechange=postrefreshQuestionSummary;
    request.send(null);
}

function processScreenForNextIndicator() {
    if (request.readyState == 4) {
        if (request.status == 200) {
            var parsedJSON = JSON.parse(request.responseText);
            document.getElementById("indicatorNaming").value = "";
            var selectedMinor = document.getElementById("selectedMinor");
            removeOptions(selectedMinor);
            refreshQuestionSummary();
        }
    }
}

function postrefreshQuestionSummary() {
    if (request.readyState == 4) {
        if (request.status == 200) {
            var parsedJSON = JSON.parse(request.responseText);
            var qNamefromBean = document.getElementById("qNamefromBean");
            var associatedIndicators = document.getElementById("associatedIndicators");
            removeOptions(associatedIndicators);
            qNamefromBean.value = parsedJSON.questionName;
            for (var i=0;i< parsedJSON.genQueries.length;i++) {

                var newOption = new Option(parsedJSON.genQueries[i].indicatorName, parsedJSON.genQueries[i].indicatorName);
                associatedIndicators.appendChild(newOption);
            }
        }
    }

}