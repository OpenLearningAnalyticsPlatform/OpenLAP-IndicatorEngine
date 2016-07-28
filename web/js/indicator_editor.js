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
        heightStyle: "fill"
    });
    $( document ).tooltip();
    // $( "#toDate" ).datepicker();
    // $( "#fromDate" ).datepicker();

    var indPropEntityFilterTable = $('#indEntityFilters').dataTable({
        "aoColumns": [
        { "mDataProp": "key" },
        { "mDataProp": "eValues" },
        { "mDataProp": "type" }
        ]
    }).api();

    var indPropUserFilterTable = $('#indUserFilters').dataTable({
        "aoColumns": [
            { "mDataProp": "userSearchType" },
            { "mDataProp": "searchPattern" },
            { "mDataProp": "userSearch" }
        ]
    }).api();
    var indPropSessionFilterTable = $('#indSessionFilters').dataTable({
        "aoColumns": [
            { "mDataProp": "session" },
            { "mDataProp": "type" }
        ]
    }).api();
    var indPropTimeFilterTable = $('#indTimeFilters').dataTable({
        "aoColumns": [
            { "mDataProp": "timestamp" },
            { "mDataProp": "type" }
        ]
    }).api();


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
        width: 'auto',
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

    $( "#indView" ).click(function(e){
        $.ajax({type: "GET",
            url: "/indicators/refreshQuestionSummary",
            data: { indName: $("#associatedIndicators").val() },
            dataType: "json", // json
            success:function(response){
                if (response == null ) {
                    $("#indDeleteDialog").html("The selected Indicator's property cannot be viewed as it a composite Indicator or a NULL value.");
                    refreshQuestionSummary();
                    $('.indDeleteDialog').dialog('option', 'title', 'Indicator View Message');
                    $("#indDeleteDialog").dialog("open");
                }
                else {
                    //$("#indViewDialog").text(JSON.stringify(response));
                    GenerateTable(response);
                    indPropEntityFilterTable.clear();
                    indPropUserFilterTable.clear();
                    indPropSessionFilterTable.clear();
                    indPropTimeFilterTable.clear();

                    indPropEntityFilterTable.rows.add(response.indicatorXMLData.entityValues);
                    indPropUserFilterTable.rows.add(response.indicatorXMLData.userSpecifications);
                    indPropSessionFilterTable.rows.add(response.indicatorXMLData.sessionSpecifications);
                    indPropTimeFilterTable.rows.add(response.indicatorXMLData.timeSpecifications);

                    indPropEntityFilterTable.draw();
                    indPropUserFilterTable.draw();
                    indPropSessionFilterTable.draw();
                    indPropTimeFilterTable.draw();

                    $("#indViewDialog").dialog("open");

                }

            }});
    });
    $( "#indDelete" ).click(function(e){
        $.ajax({
            type: "GET",
            url: "/indicators/deleteIndFromQn",
            data: {indName: $("#associatedIndicators").val()},
            dataType: "html",
            success: function (response) {
                $("#indDeleteDialog").text(response);
                $('.indDeleteDialog').dialog('option', 'title', 'Indicator Deletion Message');
                refreshQuestionSummary();
                $("#indDeleteDialog").dialog("open");
            }
        });
    });
    $( "#indLoad" ).click(function(e){
        $.ajax({type: "GET",
            url: "/indicators/loadIndFromQnSetToEditor",
            data: { indName: $("#associatedIndicators").val() },
            dataType: "json",
            success: function(response){
                if (response == null ) {
                    $("#indDeleteDialog").html("The selected Indicator cannot be loaded into the editor as it a composite Indicator or a NULL value.");
                    refreshQuestionSummary();
                    $('.indDeleteDialog').dialog('option', 'title', 'Indicator Load Message');
                    $("#indDeleteDialog").dialog("open");
                }
                else {
                    $("#indDeleteDialog").html("The selected Indicator has been successfully loaded into the Editor.<br/> Please note that it has been <strong>deleted </strong>" +
                    "from the Question. So after making changes, please save it again if you want it to be associated with the Question. <br/>" +
                    "Also note that you have to select again Platform and Action to populate the List of Categories.");
                    $('.indDeleteDialog').dialog('option', 'title', 'Indicator Load Message');
                    refreshQuestionSummary();
                    updateScreenAfterLoadInd(response);
                    $("#indDeleteDialog").dialog("open");
                }
            }
        });
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
    indicatorData.push([8, "Sources", data.indicatorXMLData.source]);
    indicatorData.push([9, "Platform", data.indicatorXMLData.platform]);
    indicatorData.push([10, "Action", data.indicatorXMLData.action]);
    indicatorData.push([11, "Minor", data.indicatorXMLData.minor]);
    indicatorData.push([12, "Major", data.indicatorXMLData.major]);
    indicatorData.push([13, "Hibernate Query", data.query]);

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

function updateScreenAfterLoadInd(data) {

    $('#indicatorNaming').val(data.indicatorName);
    $('#PlatformSelection').val(data.indicatorXMLData.platform);
    $('#actionSelection').val(data.indicatorXMLData.action);
    $('#selectedChartType').val(data.genIndicatorProps.chartType);
    $('#EngineSelect').val(data.genIndicatorProps.chartEngine);

    var optionsToSelect = data.indicatorXMLData.source;
    var select = document.getElementById( 'sourceSelection' );

    for ( var i = 0, l = select.options.length, o; i < l; i++ )
    {
        o = select.options[i];
        if ( optionsToSelect.indexOf( o.text ) != -1 )
        {
            o.selected = true;
        }
    }

    var entityValues = data.indicatorXMLData.entityValues;
    var userSpecs = data.indicatorXMLData.userSpecifications;
    var sessionSpecs = data.indicatorXMLData.sessionSpecifications;
    var timeSpecs = data.indicatorXMLData.timeSpecifications;
    loadAssociatedEntityFilters(entityValues);
    loadAssociatedSessionFilters(sessionSpecs);
    loadAssociatedUserTimeFilters(userSpecs, timeSpecs);

    populateAnalyticsMethods(JSON.stringify(data.indicatorXMLData));

    populateCategories(data.indicatorXMLData.minor);
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
        var request = new XMLHttpRequest();
    } catch(failed){
        alert("Error creating request object!");
        request = null;
    } finally {
        return request;
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
    var request = createRequest();
    var questionNameEntered = document.getElementById('questionNaming').value;
    var url ="/indicators/validateQName?qname="+questionNameEntered;
    request.open("GET",url,true);
    request.onreadystatechange=function(){alert_questionName(request)};
    request.send(null);
}

function alert_questionName(request) {
    if (request.readyState == 4) {
        if (request.status == 200) {

            if (request.responseText == "exists") {
                return "Question name already Existing. Duplicate names not allowed";
            }
            else if (request.responseText == "short") {
                return "Question Name must have at least 6 characters";
            }
            else if (request.responseText == "null") {
                return "Question Name cannot be Empty";
            }
            else {
                return true;
            }
        }
    }
}

function validateIndicatorName(obj){
    var request = createRequest();
    var indicatorNameEntered = document.getElementById('indicatorNaming').value;
    var url ="/indicators/validateIndName?indname="+indicatorNameEntered;
    request.open("GET",url,true);
    request.onreadystatechange=function(){alert_indicatorName(request)};
    request.send(null);
}

function alert_indicatorName(request) {
    if (request.readyState == 4) {
        if (request.status == 200) {

            if (request.responseText == "exists") {
                document.getElementById("indicatorNaming").value=null;
            }
            else if (request.responseText == "short") {
                document.getElementById("indicatorNaming").value=null;
            }
            else if (request.responseText == "null") {
                document.getElementById("indicatorNaming").value=null;
            }
            else {
            }
        }
    }
}

    function populateCategories(selectedValue){
    var selectedValue = selectedValue || null;
    var spinner = $('#selectedMinorSpinner');
    spinner.show();

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
    var selectedAction = $('#actionSelection').val();
    var selectedPlatform = $('#PlatformSelection').val();

    $.ajax({
        context: true,
        type: "GET",
        url: "/indicators/populateCategories?action="+selectedAction+"&platform="+selectedPlatform+"&sources="+selectedSources,
        dataType: "json",
        success: function (response) {
            var selectedMinor = document.getElementById("selectedMinor");
            removeOptions(selectedMinor);
            for (var i = 0; i < response.length; i++) {
                var newOption = new Option(response[i].minor, response[i].minor);
                selectedMinor.appendChild(newOption);
            }
            if(selectedValue !== null) {
                $('#selectedMinor').val(selectedValue);
            }
            spinner.hide();
            populateEntities();
        }
    });

}

function processReceivedCategories(request) {
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
    $('#selectedMinorSpinner').hide();
}

function populateEntities(data) {

    $.ajax({
        type: "GET",
        url: "/indicators/populateEntities",
        data: {
            minor: $('#selectedMinor').val()
        },
        dataType: "json",
        success: function (response) {
            var entityKeySelection = document.getElementById("entityKeySelection");
            removeOptions(entityKeySelection);
            for (var i=0;i< response.length;i++) {
                var newOption = new Option(response[i], response[i]);
                entityKeySelection.appendChild(newOption);
            }
            getDataColumns();
        }
    });
}

function processReceivedEntities(request) {
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

function addEntity(){
    var request = createRequest();
    var keySelected = document.getElementById("entityKeySelection").value;

    var selectedMethods = JSON.parse(localStorage.getItem('selectedMethods')) || [];
    var index = selectedMethods.indexOf(keySelected);
    if (index < 0) {
        console.log(keySelected);
        selectedMethods.push(keySelected);
        localStorage.setItem('selectedMethods', JSON.stringify(selectedMethods));
    }
    var evalue = $('#entityValue').val();
    var url ="/indicators/addEntity?key="+keySelected+"&value="+evalue;
    request.open("GET",url,true);
    request.onreadystatechange = function(){displayEntityFilters(1,request)};
    request.send(null);
}

function refreshEntityFilters() {
    var request = createRequest();
    var url ="/indicators/getEntities";
    request.open("GET",url,true);
    request.onreadystatechange = function(){displayEntityFilters(2,request)};
    request.send(null);
}

function deleteEntity(){

    var selectedMethods = JSON.parse(localStorage.getItem('selectedMethods')) || [];
    var newSelectedMethods = selectedMethods.filter(function(val){
        return (JSON.stringify(val) !== JSON.stringify(entityFilterListing.value));
    });
    localStorage.setItem('selectedMethods', JSON.stringify(newSelectedMethods));

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

function searchUser() {
    var request = createRequest();
    var userType = document.getElementById("userType").value;
    var searchUserString = document.getElementById("searchUserString").value;
    var url ="/indicators/searchUser?keyword="+searchUserString+"&searchtype="+userType;
    request.open("GET",url,true);
    request.onreadystatechange=function(){displaySearchUserResults(request)};
    request.send(null);
}

function displaySearchUserResults(request) {
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

function addUserFilter() {
    var request = createRequest();
    var userType = document.getElementById("userType").value;
    var userdata = document.getElementById("usersearchResults").value;
    var UsersearchType = document.getElementById("UsersearchType").value;
    var url ="/indicators/addUserFilter?userdata="+userdata+"&searchType="+UsersearchType+"&userType="+userType;
    request.open("GET",url,true);
    request.onreadystatechange = function(){displayUserFilters(1,request)};
    request.send(null);
}

function refreshUserFilters() {
    var request = createRequest();
    var url ="/indicators/getUserFilters";
    request.open("GET",url,true);
    request.onreadystatechange=  function(){displayUserFilters(2,request)};
    request.send(null);
}

function deleteUserFilters() {
    var request = createRequest();
    var userFilterListing = document.getElementById("userFilterListing");
    var url ="/indicators/deleteUserFilters?filter="+userFilterListing.value;
    request.open("GET",url,true);
    request.onreadystatechange=  function(){displayUserFilters(3,request,userFilterListing.value)};
    request.send(null);
}

function displayUserFilters(callstatus,request,msg) {
    if (request.readyState == 4) {
        if (request.status == 200) {
            if(callstatus == 1) {
            }
            else if(callstatus == 2) {
            }
            else if (callstatus == 3) {
            }

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
        var userFilterListing = document.getElementById("userFilterListing");
        removeOptions(userFilterListing);
        var newOption = new Option('ALL', 'ALL');
        for (var i=0;i< parsedJSON.length;i++) {
            if(i==0)
                userFilterListing.appendChild(newOption);
            var txt = parsedJSON[i].userSearchType+'_'+parsedJSON[i].userSearch+'_'+parsedJSON[i].searchPattern;
            newOption = new Option(txt, txt);
            userFilterListing.appendChild(newOption);
        }
        document.getElementById("searchUserString").value = null;
        removeOptions(document.getElementById("usersearchResults"));
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

function refreshTimeFilters() {
    var request = createRequest();
    var url ="/indicators/getTimeFilters";
    request.open("GET",url,true);
    request.onreadystatechange= function(){displayTimeFilters(2,request)};
    request.send(null);
}


function displayTimeFilters(callstatus,request,msg) {
    if (request.readyState == 4) {
        if (request.status == 200) {
            if(callstatus == 1) {
            }
            else if(callstatus == 2) {
            }
            else if (callstatus == 3) {
            }
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
        var timeFilterListing = document.getElementById("timeFilterListing");
        removeOptions(timeFilterListing);
        var newOption = new Option('ALL', 'ALL');
        for (var i=0;i< parsedJSON.length;i++) {
            if(i==0)
                timeFilterListing.appendChild(newOption);
            var txt = parsedJSON[i].type+'_'+parsedJSON[i].timestamp;
            newOption = new Option(txt, txt);
            timeFilterListing.appendChild(newOption);
        }
        document.getElementById("timeSearchString").value = null;
        document.getElementById("fromDate").value = null;
        document.getElementById("toDate").value = null;
        removeOptions(document.getElementById("timeSearchResults"));
    }
}

function searchSession() {
    var request = createRequest();
    var SessionSearchType = document.getElementById("sessionSearchType").value;
    var sessionSearchString = document.getElementById("sessionSearchString").value;
    var url ="/indicators/searchSession?keyword="+sessionSearchString+"&searchType="+SessionSearchType;
    request.open("GET",url,true);
    request.onreadystatechange=function(){displaySearchSessionResults(request)};
    request.send(null);
}

function displaySearchSessionResults(request) {
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

function addSessionFilter() {
    var request = createRequest();
    var SessionData = document.getElementById("SessionsearchResults").value;
    var SessionSearchType = document.getElementById("SessionsearchType").value;
    var url ="/indicators/addSessionFilter?sessionData="+SessionData+"&searchType="+SessionSearchType;
    request.open("GET",url,true);
    request.onreadystatechange= function(){displaySessionFilters(1,request)};
    request.send(null);

}

function refreshSessionFilters() {
    var request = createRequest();
    var url ="/indicators/getSessionFilters";
    request.open("GET",url,true);
    request.onreadystatechange= function(){displaySessionFilters(2,request)};
    request.send(null);
}

function deleteSessionFilters() {
    var request = createRequest();
    var sessionFilterListing = document.getElementById("sessionFilterListing");
    var url ="/indicators/deleteSessionFilters?filter="+sessionFilterListing.value;
    request.open("GET",url,true);
    request.onreadystatechange= function(){displaySessionFilters(3,request,sessionFilterListing)};
    request.send(null);
}

function displaySessionFilters(callstatus, request,msg) {
    if (request.readyState == 4) {
        if (request.status == 200) {
            if(callstatus == 1) {
            }
            else if(callstatus == 2) {
            }
            else if (callstatus == 3) {
            }

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
        var sessionFilterListing = document.getElementById("sessionFilterListing");
        removeOptions(sessionFilterListing);
        var newOption = new Option('ALL', 'ALL');
        for (var i=0;i< parsedJSON.length;i++) {
            if(i==0)
                sessionFilterListing.appendChild(newOption);
            var txt = parsedJSON[i].session+'_'+parsedJSON[i].type;
            newOption = new Option(txt, txt);
            sessionFilterListing.appendChild(newOption);
        }
        document.getElementById("sessionSearchString").value = null;
        removeOptions(document.getElementById("SessionsearchResults"));
    }
}

function refreshCurrentIndicator(){
    var request = createRequest();
    var url ="/indicators/refreshCurrentIndicator";
    request.open("GET",url,true);
    request.onreadystatechange =function(){displayCurrentIndProps(request)};
    request.send(null);
}

function displayCurrentIndProps(request) {
    if (request.readyState == 4) {
        if (request.status == 200) {

            var parsedJSON = JSON.parse(request.responseText);

            var placementDiv = document.getElementById("current_ind_basic_info");
            placementDiv.innerHTML = "";
            $("#current_ind_basic_info").append("<ul id='list'></ul>");
            $("#list").append("<li>" + "Name : "+ parsedJSON.name +"</li>");
            $("#list").append("<li>" + "Action "+ parsedJSON.action +"</li>");
            $("#list").append("<li>" + "Platform : "+ parsedJSON.platform +"</li>");
            $("#list").append("<li>" + "Chart Type : "+ parsedJSON.chartType +"</li>");
            $("#list").append("<li>" + "Chart Engine : "+ parsedJSON.chartEngine +"</li>");

            placementDiv = document.getElementById("filters_at_a_glance");
            placementDiv.innerHTML = "";
            $("#filters_at_a_glance").append("<ul id='filterInfoList'></ul>");
            $("#filterInfoList").append("<li>" + "Entity Filters : "+ parsedJSON.entityFilters +"</li>");
            $("#filterInfoList").append("<li>" + "User Filters : "+ parsedJSON.userFilters +"</li>");
            $("#filterInfoList").append("<li>" + "Session Filters : "+ parsedJSON.sessionFilters +"</li>");
            $("#filterInfoList").append("<li>" + "Time Filters : "+ parsedJSON.timeFilters +"</li>");

            placementDiv = document.getElementById("currentIndHQL");
            placementDiv.innerHTML = "";
            $("#currentIndHQL").append("<ul id='queryList'></ul>");
            $("#queryList").append("<li>" + "Hibernate Query : "+ parsedJSON.hql +"</li>");
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

function refreshGraph(filterPresent) {

    if(filterPresent) {
        var questionName = document.getElementById("questionNaming").value;
        var indicatorName = document.getElementById("indicatorNaming").value;
        var graphType = document.getElementById("selectedChartType").value;
        var graphEngine = document.getElementById("EngineSelect").value;
        var request = createRequest();
        var url ="/indicators/refreshGraph?questionName="+questionName+"&indicatorName="+indicatorName+"&graphType="+graphType
            +"&graphEngine="+graphEngine;
        request.open("GET",url,true);
        request.onreadystatechange=function(){drawGraph(request)};
        request.send(null);
    }
    else {
        checkForDefaultRule(1);
    }

}
function checkForDefaultRule(funcId) {
    var request = createRequest();
    var url ="/indicators/getEntities?size=Y";
    request.open("GET",url,false);
    request.onreadystatechange = function(){addDefaultRule(funcId,request)};
    request.send(null);
}

function addDefaultRule(funcID,request) {
    if (request.readyState == 4) {
        if (request.status == 200) {
            if(request.responseText == 0) {

                request = createRequest();
                var keySelected = document.getElementById("entityKeySelection").value;
                // var searchType = document.getElementById("specificationType").value;
                var evalue = 'ALL';
                // var url ="/indicators/addEntity?key="+keySelected+"&search="+searchType+"&value="+evalue;
                var url ="/indicators/addEntity?key="+keySelected+"&value="+evalue;
                request.open("GET",url,false);
                if(funcID == 1)
                    request.onreadystatechange = function(){refreshGraph(new Boolean(true))};
                else
                    request.onreadystatechange = function(){finalizeIndicator(new Boolean(true))};
                request.send(null);
            }
            else {
                if(funcID == 1)
                   refreshGraph(new Boolean(true));
                else
                    finalizeIndicator(new Boolean(true));

            }
        }
    }
}

function drawGraph(request) {
    if (request.readyState == 4) {
        if (request.status == 200) {
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

function postrefreshQuestionSummary(parsedJSON, isLoadTemplate) {

    isLoadTemplate = isLoadTemplate || false;
    var qNamefromBean = document.getElementById("questionNaming");

    // $(function () {
        $('#associatedIndicatorsDiv').empty();

        if (!isLoadTemplate) {
            $('#appliedAttributeFiltersDiv').empty();
            $('#appliedAttributeFiltersDiv').hide();
            $('#appliedFiltersLabel').hide();

            $('#appliedSessionFiltersDiv').empty();
            $('#appliedSessionFiltersDiv').hide();
            $('#appliedSessionFiltersLabel').hide();

            $('#appliedSessionFiltersDiv').empty();
            $('#appliedSessionFiltersDiv').hide();
            $('#appliedSessionFiltersLabel').hide();
        }
        if(parsedJSON == null || parsedJSON.genQueries.length == 0) {
            $('#associatedIndicatorsDiv').append("No Associated Indicators Found");
        }
    // });
    if(parsedJSON != null) {
        if (parsedJSON.genQueries.length > 0) {
            qNamefromBean.value = parsedJSON.questionName;
        }

        for (var i = 0; i < parsedJSON.genQueries.length; i++) {
            // $(function () {
                if(parsedJSON.genQueries[i].genIndicatorProps['isComposite']) {
                    $('#associatedIndicatorsDiv').append("<div class='chip composite-chip' name='chip-" + parsedJSON.genQueries[i].identifier + "' id='" + parsedJSON.genQueries[i].identifier
                        + "' title='" + parsedJSON.genQueries[i].indicatorName
                        +"'><span>" + parsedJSON.genQueries[i].indicatorName
                        + "</span><i class='material-icons' onclick='showDeleteIndicatorModal(this, event);'>close</i></div>");
                }
                else {
                    $('#associatedIndicatorsDiv').append("<div class='chip' name='chip-" + parsedJSON.genQueries[i].identifier + "' id='" + parsedJSON.genQueries[i].identifier
                        + "' title='" + parsedJSON.genQueries[i].indicatorName
                        + "' onclick='loadIndicator(this);'><span >" + parsedJSON.genQueries[i].indicatorName
                        + "</span><i class='material-icons' onclick='showDeleteIndicatorModal(this, event);'>close</i></div>");
                }
            // });
            if (isLoadTemplate) {
                $(".chip[name=chip-" + parsedJSON.genQueries[parsedJSON.genQueries.length-1].identifier + "]").addClass("chip-bg").siblings().removeClass('chip-bg');
                localStorage.setItem("selectedIndicatorIndex", parsedJSON.genQueries[parsedJSON.genQueries.length-1].identifier);
            }
        }
    }

}

function finalizeIndicator(filterPresent) {
    $(".chip").removeClass('chip-bg');
    if(filterPresent) {
        var request = createRequest();
        var goalId = document.getElementById("GoalSelection").value;
        var questionName = document.getElementById("questionNaming").value;
        var indicatorName = document.getElementById("indicatorNaming").value;
        var graphType = document.getElementById("selectedChartType").value;
        var graphEngine = document.getElementById("EngineSelect").value;
        var analyticsMethod = document.getElementById("analyticsMethod").value;

        var indicatorIndex = localStorage.getItem("selectedIndicatorIndex");
        var methodMappings = localStorage.getItem('methodMappings') || "";
        var visualizationMappings = localStorage.getItem('visualizationMappings') || "";
        var selectedMethods = localStorage.getItem('selectedMethods') || "";
        selectedMethods = JSON.parse(selectedMethods).join(',');

        $.ajax({
            type: "GET",
            url: "/indicators/finalize?goalId="+goalId+"&questionName="+questionName+"&indicatorName="+indicatorName+"&graphType="+graphType
                    +"&graphEngine="+graphEngine+"&indicatorIdentifier="+indicatorIndex+"&analyticsMethod="+analyticsMethod + "&methodMappings=" + methodMappings
                    + "&visualizationMappings=" + visualizationMappings + "&selectedMethods=" + selectedMethods,
            dataType: "json",
            success: function (response) {
                console.log(response);
                postrefreshQuestionSummary(response);

                $(function() {
                    $("#indicatorDefinition").hide();
                    $('body').animate({
                        scrollTop: $("body").offset().top
                    }, 2000);
                });
            }
        });
    }
    else
        checkForDefaultRule(2);

}

function addNewIndicator() {
    var request = createRequest();
    var url ="/indicators/addNewIndicator";
    request.open("GET",url,true);
    request.onreadystatechange=function(){processScreenForNextIndicator(request)};
    request.send(null);
}

function processScreenForNextIndicator(request) {
    if (request.readyState == 4) {
        if (request.status == 200) {
            var parsedJSON = JSON.parse(request.responseText);
            document.getElementById("indicatorNaming").value = "";
            var selectedMinor = document.getElementById("selectedMinor");

            clearLocalStorage();
            removeOptions(selectedMinor);
            populateAnalyticsMethods();

            $('#methodDataColumns').empty();
            $('#outputForMethods').empty();
            $('#addMethodMapping').prop('disabled', false);
            $('#addVisualizationMapping').prop('disabled', false);

            $("#methodMappingTable > tbody:last").children().remove();
            toggleVisibilityMethodMappingTable();

            $("#visualizerMappingTable > tbody:last").children().remove();
            toggleVisibilityVisualizerMappingTable();

            $("#preview_chart").hide();
            refreshQuestionSummary();
        }
    }
}

function resetSession() {
    var request = createRequest();
    var url ="/indicators/deleteDataFromSession";
    request.open("GET",url,true);
    request.onreadystatechange=function(){processScreenForNextQuestion(request, true)};
    request.send(null);
}

function SaveQuestionDB() {
    var request = createRequest();
    var userName = document.getElementById("userName").value;
    var url ="/indicators/saveQuestionDB?userName="+userName;
    request.open("GET",url,true);
    request.onreadystatechange=function(){processScreenForNextQuestion(request)};
    request.send(null);
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
            $(function () {
                $("#saveQuestion").attr('disabled', 'disabled');
            });

            if(!isResetSession) {
                if (!parsedJSON.isQuestionSaved) {
                    alert(parsedJSON.errorMessage);
                } else {
                    console.log(parsedJSON);
                    $("#questionRequestCode").text(parsedJSON.questionRequestCode);
                }
            }
        }
    }
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
            if(parsedJSON.genQueries.length ==0) {

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
                for(i=0; i<parsedJSON.genQueries.length; i++) {

                    //visualization card
                    var cardDiv = document.createElement("div");
                    cardDiv.className = "card small col-md-6";

                    var cardImageDiv = document.createElement("div");
                    cardImageDiv.className = "card-image waves-effect waves-block waves-light";

                    var divId = "visualizeCardGraph_" + parsedJSON.genQueries[i].indicatorName.replace(/ /g,"_");
                    cardImageDiv.id = divId;

                    cardDiv.appendChild(cardImageDiv);

                    var cardContentDiv = document.createElement("div");
                    cardContentDiv.className = "card-content";

                    var cardContentSpan = document.createElement("span");
                    cardContentSpan.className = "card-title activator grey-text text-darken-4";
                    var cardTitle = document.createTextNode(parsedJSON.genQueries[i].indicatorName);
                    cardContentSpan.appendChild(cardTitle);
                    // var cardMoreIcon = document.createElement("i");
                    // cardMoreIcon.className = "material-icons right";
                    // var cardMoreIconText = document.createTextNode("more_vert");
                    // cardMoreIcon.appendChild(cardMoreIconText);
                    // cardContentSpan.appendChild(cardMoreIcon);

                    cardContentDiv.appendChild(cardContentSpan);

                    cardDiv.appendChild(cardContentDiv);


                    // var cardRevealDiv = document.createElement("div");
                    // cardRevealDiv.className = "card-reveal";
                    //
                    // var cardRevealSpan = document.createElement("span");
                    // cardRevealSpan.className = "card-title grey-text text-darken-4";
                    // var cardRevealTitle = document.createTextNode(parsedJSON.genQueries[i].indicatorName);
                    // cardRevealSpan.appendChild(cardRevealTitle);
                    // var cardCloseIcon = document.createElement("i");
                    // cardCloseIcon.className = "material-icons right";
                    // var cardCloseIconText = document.createTextNode("close");
                    // cardCloseIcon.appendChild(cardCloseIconText);
                    // cardRevealSpan.appendChild(cardCloseIcon);
                    //
                    // cardRevealDiv.appendChild(cardRevealSpan);

                    // var cardRevealTextPara = document.createElement("p");
                    // var cardRevealText = document.createTextNode(
                    //     "Here is some more information about this product that is only revealed once clicked on." +
                    //     "Here is some more information about this product that is only revealed once clicked on." +
                    //     "Here is some more information about this product that is only revealed once clicked on." +
                    //     "Here is some more information about this product that is only revealed once clicked on." +
                    //     "Here is some more information about this product that is only revealed once clicked on." +
                    //     "Here is some more information about this product that is only revealed once clicked on." +
                    //     "Here is some more information about this product that is only revealed once clicked on." +
                    //     "Here is some more information about this product that is only revealed once clicked on." +
                    //     "Here is some more information about this product that is only revealed once clicked on." +
                    //     "Here is some more information about this product that is only revealed once clicked on." +
                    //     "Here is some more information about this product that is only revealed once clicked on." +
                    //     "Here is some more information about this product that is only revealed once clicked on." +
                    //     "Here is some more information about this product that is only revealed once clicked on." +
                    //     "Here is some more information about this product that is only revealed once clicked on." +
                    //     "Here is some more information about this product that is only revealed once clicked on." +
                    //     "Here is some more information about this product that is only revealed once clicked on." +
                    //     "Here is some more information about this product that is only revealed once clicked on." +
                    //     "Here is some more information about this product that is only revealed once clicked on." +
                    //     "Here is some more information about this product that is only revealed once clicked on." +
                    //     "Here is some more information about this product that is only revealed once clicked on." +
                    //     "Here is some more information about this product that is only revealed once clicked on." +
                    //     "Here is some more information about this product that is only revealed once clicked on." +
                    //     "Here is some more information about this product that is only revealed once clicked on." +
                    //     "Here is some more information about this product that is only revealed once clicked on." +
                    //     "Here is some more information about this product that is only revealed once clicked on." +
                    //     "Here is some more information about this product that is only revealed once clicked on." +
                    //     "Here is some more information about this product that is only revealed once clicked on." +
                    //     "Here is some more information about this product that is only revealed once clicked on." +
                    //     "Here is some more information about this product that is only revealed once clicked on." +
                    //     "Here is some more information about this product that is only revealed once clicked on." +
                    //     "Here is some more information about this product that is only revealed once clicked on." +
                    //     "Here is some more information about this product that is only revealed once clicked on." +
                    //     "Here is some more information about this product that is only revealed once clicked on." +
                    //     "Here is some more information about this product that is only revealed once clicked on." +
                    //     "Here is some more information about this product that is only revealed once clicked on." +
                    //     "Here is some more information about this product that is only revealed once clicked on.");

                    // cardRevealTextPara.appendChild(cardRevealText);
                    // cardRevealDiv.appendChild(cardRevealTextPara);

                    // cardDiv.appendChild(cardRevealDiv);

                    visualizeQModelHtml.appendChild(cardDiv);

                    $.ajax(
                        {
                            context: this,
                            async: false,
                            type: "GET",
                            url: "/engine/getQuestionVisualizationCode?width=400&height=205",
                            // url: "/engine/getQuestionVisualizationCode?width=" + $('#'+divId).parent().width() + "&height=" + $('#'+divId).parent().height(),
                            success: function(response) {

                                var parsedJSON = JSON.parse(response);
                                var decodedGraphData = decodeURIComponent(parsedJSON);
                                $('#'+divId).html(decodedGraphData);
                            }
                        });

                }
                $(function() {
                    $("#saveQuestion").removeAttr('disabled');
                });

            }

        }
    }
}

function LoadIndicatorVisualizations() {
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
            if(parsedJSON.genQueries.length ==0) {

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

                for(i=0; i<parsedJSON.genQueries.length; i++) {

                    //composite Indicator
                    var pTag= document.createElement("p");
                    var label= document.createElement("label");
                    label.setAttribute("for", "checkbox-" + parsedJSON.genQueries[i].indicatorName);
                    var description = document.createTextNode(parsedJSON.genQueries[i].indicatorName);
                    var checkbox = document.createElement("input");
                    checkbox.id = "checkbox-" + parsedJSON.genQueries[i].indicatorName;
                    checkbox.type = "checkbox";    // make the element a checkbox
                    checkbox.name = parsedJSON.genQueries[i].indicatorName;      // give it a name we can check on the server side
                    checkbox.value = parsedJSON.genQueries[i].indicatorName;         // make its value "pair"
                    checkbox.className = "filled-in";


                    label.appendChild(description);// add the description to the element
                    pTag.appendChild(checkbox);   // add the box to the element
                    pTag.appendChild(label);   // add the box to the element
                    // add the label element to your div
                    compositeModelHtml.appendChild(pTag);

                    var imgDiv = document.createElement("div");
                    imgDiv.className = "card col-md-10";

                    var compositeGraphId = "compositeIndicatorGraph_" + parsedJSON.genQueries[i].indicatorName.replace(/ /g,"_");
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
}

function updateVisualisationTab(request) {
    if (request.readyState == 4) {
        if (request.status == 200) {
            var parsedJSON = JSON.parse(request.responseText);
            if(parsedJSON.genQueries.length ==0) {
                var compositeModelHtml = document.getElementById("runIndMem");
                var visualizeQModelHtml = document.getElementById("visualizeQuestionContent");
                compositeModelHtml.innerHTML = "";
                visualizeQModelHtml.innerHTML = "";

                var div = document.createElement("div");
                div.className = "alert alert-warning";
                var alertDescription = document.createTextNode("Please add Indicators to build composite Indicators.");
                div.appendChild(alertDescription);
                compositeModelHtml.appendChild(div);

                var div1 = document.createElement("div");
                div1.className = "alert alert-warning";
                var alertDescription1 = document.createTextNode("Please add Indicators for visualization.");
                div1.appendChild(alertDescription1);
                visualizeQModelHtml.appendChild(div1);

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
                var src = document.getElementById("runIndMem");
                var src1 = document.getElementById("visualizeQuestionContent");
                src.innerHTML = "";
                src1.innerHTML = "";
                for(var i=0; i<parsedJSON.genQueries.length; i++) {

                    var pTag= document.createElement("p");
                    var label= document.createElement("label");
                    label.setAttribute("for", "checkbox-" + parsedJSON.genQueries[i].indicatorName);
                    var description = document.createTextNode(parsedJSON.genQueries[i].indicatorName);
                    var checkbox = document.createElement("input");
                    checkbox.id = "checkbox-" + parsedJSON.genQueries[i].indicatorName;
                    checkbox.type = "checkbox";    // make the element a checkbox
                    checkbox.name = parsedJSON.genQueries[i].indicatorName;      // give it a name we can check on the server side
                    checkbox.value = parsedJSON.genQueries[i].indicatorName;         // make its value "pair"
                    checkbox.className = "filled-in";


                    label.appendChild(description);// add the description to the element
                    pTag.appendChild(checkbox);   // add the box to the element
                    pTag.appendChild(label);   // add the box to the element
                    // add the label element to your div
                    src.appendChild(pTag);

                    var imgDiv = document.createElement("div");
                    imgDiv.className = "card col-md-10";
                    var img = document.createElement("img");
                    img.src = "/graphs/jgraph?runFromMemory=true&indicator="+parsedJSON.genQueries[i].indicatorName;
                    img.className = "responsive-img center-align";
                    imgDiv.appendChild(img);
                    src.appendChild(imgDiv);


                    var cardDiv = document.createElement("div");
                    cardDiv.className = "card small col-md-6";

                    var cardImageDiv = document.createElement("div");
                    cardImageDiv.className = "card-image waves-effect waves-block waves-light";

                    var cardImg = document.createElement("img");
                    cardImg.src = "/graphs/jgraph?runFromMemory=true&indicator="+parsedJSON.genQueries[i].indicatorName;
                    cardImg.className = "activator";

                    cardImageDiv.appendChild(cardImg);

                    cardDiv.appendChild(cardImageDiv);

                    var cardContentDiv = document.createElement("div");
                    cardContentDiv.className = "card-content";

                    var cardContentSpan = document.createElement("span");
                    cardContentSpan.className = "card-title activator grey-text text-darken-4";
                    var cardTitle = document.createTextNode(parsedJSON.genQueries[i].indicatorName);
                    cardContentSpan.appendChild(cardTitle);
                    var cardMoreIcon = document.createElement("i");
                    cardMoreIcon.className = "material-icons right";
                    var cardMoreIconText = document.createTextNode("more_vert");
                    cardMoreIcon.appendChild(cardMoreIconText);
                    cardContentSpan.appendChild(cardMoreIcon);

                    cardContentDiv.appendChild(cardContentSpan);

                    cardDiv.appendChild(cardContentDiv);




                    var cardRevealDiv = document.createElement("div");
                    cardRevealDiv.className = "card-reveal";

                    var cardRevealSpan = document.createElement("span");
                    cardRevealSpan.className = "card-title grey-text text-darken-4";
                    var cardRevealTitle = document.createTextNode(parsedJSON.genQueries[i].indicatorName);
                    cardRevealSpan.appendChild(cardRevealTitle);
                    var cardCloseIcon = document.createElement("i");
                    cardCloseIcon.className = "material-icons right";
                    var cardCloseIconText = document.createTextNode("close");
                    cardCloseIcon.appendChild(cardCloseIconText);
                    cardRevealSpan.appendChild(cardCloseIcon);

                    cardRevealDiv.appendChild(cardRevealSpan);

                    var cardRevealTextPara = document.createElement("p");
                    var cardRevealText = document.createTextNode(
                        "Here is some more information about this product that is only revealed once clicked on." +
                        "Here is some more information about this product that is only revealed once clicked on." +
                        "Here is some more information about this product that is only revealed once clicked on." +
                        "Here is some more information about this product that is only revealed once clicked on." +
                        "Here is some more information about this product that is only revealed once clicked on." +
                        "Here is some more information about this product that is only revealed once clicked on." +
                        "Here is some more information about this product that is only revealed once clicked on." +
                        "Here is some more information about this product that is only revealed once clicked on." +
                        "Here is some more information about this product that is only revealed once clicked on." +
                        "Here is some more information about this product that is only revealed once clicked on." +
                        "Here is some more information about this product that is only revealed once clicked on." +
                        "Here is some more information about this product that is only revealed once clicked on." +
                        "Here is some more information about this product that is only revealed once clicked on." +
                        "Here is some more information about this product that is only revealed once clicked on." +
                        "Here is some more information about this product that is only revealed once clicked on." +
                        "Here is some more information about this product that is only revealed once clicked on." +
                        "Here is some more information about this product that is only revealed once clicked on." +
                        "Here is some more information about this product that is only revealed once clicked on." +
                        "Here is some more information about this product that is only revealed once clicked on." +
                        "Here is some more information about this product that is only revealed once clicked on." +
                        "Here is some more information about this product that is only revealed once clicked on." +
                        "Here is some more information about this product that is only revealed once clicked on." +
                        "Here is some more information about this product that is only revealed once clicked on." +
                        "Here is some more information about this product that is only revealed once clicked on." +
                        "Here is some more information about this product that is only revealed once clicked on." +
                        "Here is some more information about this product that is only revealed once clicked on." +
                        "Here is some more information about this product that is only revealed once clicked on." +
                        "Here is some more information about this product that is only revealed once clicked on." +
                        "Here is some more information about this product that is only revealed once clicked on." +
                        "Here is some more information about this product that is only revealed once clicked on." +
                        "Here is some more information about this product that is only revealed once clicked on." +
                        "Here is some more information about this product that is only revealed once clicked on." +
                        "Here is some more information about this product that is only revealed once clicked on." +
                        "Here is some more information about this product that is only revealed once clicked on." +
                        "Here is some more information about this product that is only revealed once clicked on." +
                        "Here is some more information about this product that is only revealed once clicked on.");

                    cardRevealTextPara.appendChild(cardRevealText);
                    cardRevealDiv.appendChild(cardRevealTextPara);


                    cardDiv.appendChild(cardRevealDiv);


                    src1.appendChild(cardDiv);

                }
                $(function() {
                    $("#saveQuestion").removeAttr('disabled');
                });
                $('#qiEditorTab a[href="#QuestionRun"]').tab('show');

            }

        }
    }
}

function addCompositeIndicator() {
    var checkedValues = new Array();
    var counter =0 ;
    var inputElements = document.getElementsByClassName('messageCheckbox');
    for(var i=0; inputElements[i]; ++i){
        if(inputElements[i].checked){
            checkedValues[counter] = inputElements[i].value;
            counter++;
        }
    }
    var indicatorName = document.getElementById("compositeIndName").value;
    var graphType = document.getElementById("compositeGraphType").value;
    var graphEngine = document.getElementById("compositeGraphEngine").value;
    var request = createRequest();
    var url ="/indicators/addCompositeIndicator?indName="+indicatorName+"&graphType="+graphType+"&graphEngine="+graphType
        +"&compositeSources="+checkedValues;
   request.open("GET",url,true);
   request.onreadystatechange=function(){postAddCompositeIndicator(request)};
   request.send(null);
}

function postAddCompositeIndicator(request) {

    if (request.readyState == 4) {
        if (request.status == 200) {
            var parsedJSON = JSON.parse(request.responseText);
            refreshQuestionSummary();
            $('#qiEditorTab a[href="#QuestionIndicatorEditor"]').tab('show');

        }
    }

}

function loadIndfromDB() {
    $('#qiEditorTab a[href="#TemplateLoad"]').tab('show');
}

function searchIndicator() {
    var searchString = document.getElementById("IndSearch").value;
    var searchIndType = document.getElementById("searchIndType").value;
    var request = createRequest();
    var url ="/indicators/searchIndicator?searchString="+searchString+"&searchType="+searchIndType;
    request.open("GET",url,true);
    request.onreadystatechange=function(){displayReceivedIndicators(request)};
    request.send(null);
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

function viewIndicatorProp() {
    var indName = $('tr.selected:first td:nth-child(2)', '#indicatorData').text();
    var request = createRequest();
    var url ="/indicators/loadIndicator?indName="+indName;
    request.open("GET",url,true);
    request.onreadystatechange=function(){displayIndicatorProp(request)};
    request.send(null);
}

function displayIndicatorProp(request) {
    if (request.readyState == 4) {
        if (request.status == 200) {
            var parsedJSON = JSON.parse(request.responseText);
            var parsedindProp = JSON.parse(parsedJSON.glaIndicatorProps.json_data);

            var indicatorData = new Array();
            indicatorData.push(["Property", "Value"]);
            indicatorData.push(["Indicator Name", parsedJSON.indicator_name]);
            indicatorData.push(["Chart Type", parsedJSON.glaIndicatorProps.chartType]);
            indicatorData.push(["Chart Engine", parsedJSON.glaIndicatorProps.chartEngine]);
            indicatorData.push(["Entity Filters", parsedindProp.entityValues.length]);
            indicatorData.push(["Session Filters", parsedindProp.sessionSpecifications.length]);
            indicatorData.push(["User Filters", parsedindProp.userSpecifications.length]);
            indicatorData.push(["Time Filters",parsedindProp.timeSpecifications.length]);
            indicatorData.push(["Sources", parsedindProp.source]);
            indicatorData.push(["Platform", parsedindProp.platform]);
            indicatorData.push(["Action", parsedindProp.action]);
            indicatorData.push(["Minor", parsedindProp.minor]);
            indicatorData.push(["Major", parsedindProp.major]);
            indicatorData.push(["Hibernate Query", parsedJSON.hql]);

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
}

function loadFromTemplate() {
    // var indName = document.getElementById("searchResults").value;
    var indName = $('tr.selected:first td:nth-child(2)', '#indicatorData').text();
    var dvTable = document.getElementById("IndPropsFromDB");
    dvTable.innerHTML = "";
    var request = createRequest();
    var url ="/indicators/loadIndicator?indName="+indName+"&loadTemplate=Y";
    request.open("GET",url,true);
    request.onreadystatechange=function(){loadToEditor(request)};
    request.send(null);
}

function loadToEditor(request) {
    if (request.readyState == 4) {
        if (request.status == 200) {
            var parsedJSON = JSON.parse(request.responseText);
            console.log(parsedJSON);
            // var parsedindProp = JSON.parse(parsedJSON.glaIndicatorProps.json_data);
            loadIndicatorTemplate(parsedJSON);

            $("#indicatorDefinition").show();
            $('body').animate({
                scrollTop: $("#indicatorDefinition").offset().top
            }, 2000);
            $('#indicatorData tbody').children().removeClass("selected");
        }
    }
}

function displayQnInfoIcons() {
}

function displayQnSummaryIcons() {
}

function displayIndInfoIcons() {
}

function searchAttributes() {

    var key = $('#entityKeySelection').val();
    var minor = $('#selectedMinor').val();

    $.ajax({
        context: true,
        type: "GET",
        url: "/indicators/searchAttributeValues?minor="+minor+"&key="+key,
        dataType: "json",
        success: function (response) {
            var entityValue = document.getElementById("entityValue");
            removeOptions(entityValue);
            for (var i=0;i< response.length;i++) {
                var newOption = new Option(response[i], response[i]);
                entityValue.appendChild(newOption);
            }
        }
    });
}

function processReceivedAttributeValues(request) {
    if (request.readyState == 4) {
        if (request.status == 200) {
            var parsedJSON = JSON.parse(request.responseText);
            var entityValue = document.getElementById("entityValue");
            removeOptions(entityValue);
            for (var i=0;i< parsedJSON.length;i++) {
                var newOption = new Option(parsedJSON[i], parsedJSON[i]);
                entityValue.appendChild(newOption);
            }
        }
    }

}