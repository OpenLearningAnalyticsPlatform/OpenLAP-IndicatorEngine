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
    $( "#toDate" ).datepicker();
    $( "#fromDate" ).datepicker();

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
                if (response == null ) {
                    $("#indDeleteDialog").html("The Selected Indicator Property cannot be viewed as it a Composite Indicator. It can only be deleted/Saved to DB.");
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
    $( "#indLoad" ).click(function(e){
        $.ajax({type: "GET",
            url: "/indicators/loadIndFromQnSetToEditor",
            data: { indName: $("#associatedIndicators").val() },
            dataType: "json",
            success: function(response){
                if (response == null ) {
                    $("#indDeleteDialog").html("The Selected Indicator Cannot be Loaded as it a Composite Indicator. It can only be deleted/Saved to DB.");
                    refreshQuestionSummary();
                    $('.indDeleteDialog').dialog('option', 'title', 'Indicator Load Message');
                    $("#indDeleteDialog").dialog("open");
                }
                else {
                    $("#indDeleteDialog").html("The Selected Indicator has been successfully loaded into the Editor.<br/> Please note that it has been <strong>deleted</strong>" +
                    "from the Question Set. So after making changes, please save it again if you want it to bve associated with the current indicator. <br/>" +
                    "Also note that you have to select again Platform and Axction to populate the List of Minors.");
                    $('.indDeleteDialog').dialog('option', 'title', 'Indicator Load Message');
                    refreshQuestionSummary();
                    updateScreenAfterLoadInd(response);
                    $("#indDeleteDialog").dialog("open");
                }
            }
        });
    });

    $.noty.defaults = {
        layout: 'bottomRight',
        theme: 'relax', // or 'relax'
        type: 'information',
        text: '', // can be html or string
        dismissQueue: true, // If you want to use queue feature set this true
        template: '<div class="noty_message"><span class="noty_text"></span><div class="noty_close"></div></div>',
        animation: {
            open: 'animated bounceInLeft', // Animate.css class names
            close: 'animated bounceOutLeft', // Animate.css class names
            easing: 'swing', // unavailable - no need
            speed: 500 // unavailable - no need
        },
        timeout: 30000, // delay for closing event. Set false for sticky notifications
        force: false, // adds notification to the beginning of queue when set to true
        modal: false,
        maxVisible: 7, // you can set max visible notification for dismissQueue true option,
        killer: false, // for close all notifications before show
        closeWith: ['click'], // ['click', 'button', 'hover', 'backdrop'] // backdrop click will close all notifications
        callback: {
            onShow: function() {},
            afterShow: function() {},
            onClose: function() {},
            afterClose: function() {},
            onCloseClick: function() {}
        },
        buttons: false // an array of buttons
    };


});
$(window).load(function(){
    $.noty.defaults.killer = true;
    noty({
        text: '<strong>Warning</strong> <br/> Please do not click any icons. <br/>' +
        'The <strong> Question : Indicator </strong> Definition process has started. Please follow the instructions in the Information Notification Area to continue.<br/>' +
        'You would receive further instructions as you proceed.',
        type: 'warning'
    });
    $.noty.defaults.killer = true;
    noty({
        text: '<strong>Information</strong> <br/>Please Type the Question Name to continue...',
        type: 'information'
    });
})
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
    document.getElementById('indicatorNaming').value = data.indicatorName;
    var sel = document.getElementById('PlatformSelection');
    var opts = sel.options;
    for(var opt, j = 0; opt = opts[j]; j++) {
        if(opt.value == data.indicatorXMLData.platform) {
            sel.selectedIndex = j;
            break;
        }
    }
    sel = document.getElementById('actionSelection');
    var opts = sel.options;
    for(var opt, j = 0; opt = opts[j]; j++) {
        if(opt.value == data.indicatorXMLData.action) {
            sel.selectedIndex = j;
            break;
        }
    }
    sel = document.getElementById('selectedChartType');
    var opts = sel.options;
    for(var opt, j = 0; opt = opts[j]; j++) {
        if(opt.value == data.genIndicatorProps.chartType) {
            sel.selectedIndex = j;
            break;
        }
    }
    sel = document.getElementById('EngineSelect');
    var opts = sel.options;
    for(var opt, j = 0; opt = opts[j]; j++) {
        if(opt.value == data.genIndicatorProps.chartEngine) {
            sel.selectedIndex = j;
            break;
        }
    }
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

                noty({
                    text: '<strong>Error</strong> <br/> Question name already Existing. Duplicate names not allowed',
                    type: 'error'
                });
                document.getElementById("questionNaming").value=null;
            }
            else if (request.responseText == "short") {

                noty({
                    text: '<strong>Error</strong> <br/> Question Name is too Short!',
                    type: 'error'
                });
                noty({
                    text: '<strong>Information</strong> <br/> Question Name must have at least 6 characters',
                    type: 'information'
                });
                document.getElementById("questionNaming").value=null;
            }
            else if (request.responseText == "null") {

                noty({
                    text: '<strong>Error</strong> <br/> Question Name cannot be Empty !',
                    type: 'error'
                });
                document.getElementById("questionNaming").value=null;
            }
            else {
                $.noty.defaults.killer = true;
                noty({
                    text: '<strong>Success</strong> <br/> Question Name satisfies all constraints',
                    type: 'success'
                });
                noty({
                    text: '<strong>Next Step</strong> <br/> Type the Indicator Name',
                    type: 'information'
                });
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

                $.noty.defaults.killer = true;
                noty({
                    text: '<strong>Error</strong> <br/> Indicator name already Existing. Duplicate names not allowed',
                    type: 'error'
                });
                document.getElementById("indicatorNaming").value=null;
            }
            else if (request.responseText == "short") {

                $.noty.defaults.killer = true;
                noty({
                    text: '<strong>Error</strong> <br/> Indicator Name is too Short',
                    type: 'error'
                });
                noty({
                    text: '<strong>Information</strong> <br/> Indicator Name must have at least 6 characters',
                    type: 'information'
                });
                document.getElementById("indicatorNaming").value=null;
            }
            else if (request.responseText == "null") {

                $.noty.defaults.killer = true;
                noty({
                    text: '<strong>Error</strong> <br/> Indicator Name cannot be Empty',
                    type: 'error'
                });
                document.getElementById("indicatorNaming").value=null;
            }
            else {
                $.noty.defaults.killer = true;
                noty({
                    text: '<strong>Success</strong> <br/> Indicator Name satisfies all constraints',
                    type: 'success'
                });

                noty({
                    text: '<strong>Next Step</strong> <br/> Select 1 or more sources. !',
                    type: 'information'
                });
            }
        }
    }
}

function sourceChanged() {
    $.noty.defaults.killer = true;
    noty({
        text: '<strong>Success</strong> <br/>Source Selected',
        type: 'success'
    });

    noty({
        text: '<strong>Next Step</strong> <br/>Select a Platform !',
        type: 'information'
    });
}

function platformChanged(){
    $.noty.defaults.killer = true;
    noty({
        text: '<strong>Success</strong> <br/>Platform Selected',
        type: 'success'
    });

    noty({
        text: '<strong>Next Step</strong> <br/>Select an Action !',
        type: 'information'
    });
}

function populateCategories(){
    var request = createRequest();
    $.noty.defaults.killer = true;
    noty({
        text: '<strong>Success</strong> <br/> Action Selected <br/> Retreiving Minors...',
        type: 'success'
    });
    noty({
        text: '<strong>Next Step</strong> <br/> Select a Minor!',
        type: 'information'
    });
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
    request.onreadystatechange=function(){processReceivedCategories(request)};
    request.send(null);

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
}

function populateEntities() {
    var request = createRequest();
    $.noty.defaults.killer = true;
    noty({
        text: '<strong>Success</strong> <br/> Minor Selected <br/> Retreiving Entities...',
        type: 'success'
    });

    noty({
        text: '<strong>Next Step</strong> <br/> You may add <strong> Filters </strong> to your Indicator in the <strong> Indicator Properties & Summary </strong> window.<br/> ' +
        'You can click on the Graph icon to generate a graph which can be viewed in <strong> Graph Preview </strong> Tab.',
        type: 'information'
    });
    noty({
        text: '<strong>Warning</strong> <br/> We will add a default filter to select all values from the data sources selected above if you do not add any Filter !',
        type: 'warning'
    });

    var minorSelected = document.getElementById('selectedMinor').value;
    var url ="/indicators/populateEntities?minor="+minorSelected;
    request.open("GET",url,true);
    request.onreadystatechange=function(){processReceivedEntities(request)};
    request.send(null);
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
    var searchType = document.getElementById("specificationType").value;
    var evalue = document.getElementById("entityValue").value;
    var url ="/indicators/addEntity?key="+keySelected+"&search="+searchType+"&value="+evalue;
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
    var request = createRequest();
    var url ="/indicators/deleteEntities";
    request.open("GET",url,true);
    request.onreadystatechange = function(){displayEntityFilters(3,request)};
    request.send(null);
}

function displayEntityFilters(callstatus,request){
    if (request.readyState == 4) {
        if (request.status == 200) {
            if(callstatus == 1) {
                $.noty.defaults.killer = true;
                noty({
                    text: '<strong>Success</strong> <br/> 1 <strong>Attribute Filter</strong> successfully added. <br/>' +
                    'You can view the newly added filter in <strong> Attribute Filters Summary </strong> sub-tab available under <strong>Filter Summary</strong> Tab.<br/>' +
                    'Deletion of Filters is also possible from there.',
                    type: 'success'
                });
                noty({
                    text: '<strong>Next Step</strong> <br/> You can add more filters of the same type or from other categories like <strong>User Filters</strong>,' +
                    '<strong>Session Filters</strong>, <strong>Time Filters</strong>, which are available in this filter sub-tab. <br/>' +
                    'You can also directly generate the graph by clicking the Graph icon in <strong> Indicator Information</strong> Tab.',
                    type: 'information'
                });
            }
            else if(callstatus == 2) {
                $.noty.defaults.killer = true;
                noty({
                    text: '<strong>Success</strong> <br/>  <strong>Attribute Filter Summary</strong> successfully refreshed. <br/>',
                    type: 'success'
                });
            }
            else if (callstatus == 3) {

                $.noty.defaults.killer = true;
                noty({
                    text: '<strong>Success</strong> <br/>  <strong>All Attribute Filters </strong> successfully deleted. <br/>',
                    type: 'success'
                });
            }

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
    var url ="/indicators/deleteUserFilters";
    request.open("GET",url,true);
    request.onreadystatechange=  function(){displayUserFilters(3,request)};
    request.send(null);
}

function displayUserFilters(callstatus,request) {
    if (request.readyState == 4) {
        if (request.status == 200) {
            if(callstatus == 1) {
                $.noty.defaults.killer = true;
                noty({
                    text: '<strong>Success</strong> <br/> 1 <strong>User Filter</strong> successfully added. <br/>' +
                    'You can view the newly added filter in <strong> User Filters Summary </strong> sub-tab available under <strong>Filter Summary</strong> Tab.<br/>' +
                    'Deletion of Filters is also possible from there.',
                    type: 'success'
                });
                noty({
                    text: '<strong>Next Step</strong> <br/> You can add more filters of the same type or from other categories like <strong>Attribute Filters</strong>,' +
                    '<strong>Session Filters</strong>, <strong>Time Filters</strong>, which are available in this sub-tab. <br/>' +
                    'You can also directly generate the graph by clicking the Graph icon in <strong> Indicator Information</strong> Tab.',
                    type: 'information'
                });
            }
            else if(callstatus == 2) {
                $.noty.defaults.killer = true;
                noty({
                    text: '<strong>Success</strong> <br/>  <strong>User Filter Summary</strong> successfully refreshed. <br/>',
                    type: 'success'
                });
            }
            else if (callstatus == 3) {

                $.noty.defaults.killer = true;
                noty({
                    text: '<strong>Success</strong> <br/>  <strong>All User Filters </strong> successfully deleted. <br/>',
                    type: 'success'
                });
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

function addTimeFilter() {
    var request = createRequest();
    var timeSearchType = document.getElementById("timeSelectionType").value;
    if(timeSearchType === "EXACT"){
        var timeString = document.getElementById("timeSearchResults").value;
        var UTCDate = +(new Date(timeString));
        var url ="/indicators/addTimeFilter?time="+UTCDate+"&timeType="+timeSearchType;
        request.open("GET",url,true);
        request.onreadystatechange= function(){displayTimeFilters(1,request)};
        request.send(null);
    }
    else {
        var startRange = document.getElementById("fromDate").value;
        var endRange = document.getElementById("toDate").value;
        var time = new Array();
        time[0] = +(new Date(startRange));
        time[1] = +(new Date(endRange));
        url ="/indicators/addTimeFilter?time="+time+"&timeType="+timeSearchType;
        request.open("GET",url,true);
        request.onreadystatechange= function(){displayTimeFilters(1,request)};
        request.send(null);
    }

}

function refreshTimeFilters() {
    var request = createRequest();
    var url ="/indicators/getTimeFilters";
    request.open("GET",url,true);
    request.onreadystatechange= function(){displayTimeFilters(2,request)};
    request.send(null);
}

function deleteTimeFilters() {
    var request = createRequest();
    var url ="/indicators/deleteTimeFilters";
    request.open("GET",url,true);
    request.onreadystatechange= function(){displayTimeFilters(3,request)};
    request.send(null);
}

function displayTimeFilters(callstatus,request) {
    if (request.readyState == 4) {
        if (request.status == 200) {
            if(callstatus == 1) {
                $.noty.defaults.killer = true;
                noty({
                    text: '<strong>Success</strong> <br/> 1 <strong>Time Filter</strong> successfully added. <br/>' +
                    'You can view the newly added filter in <strong> Time Filters Summary </strong> sub-tab available under <strong>Filter Summary</strong> Tab.<br/>' +
                    'Deletion of Filters is also possible from there.',
                    type: 'success'
                });
                noty({
                    text: '<strong>Next Step</strong> <br/> You can add more filters of the same type or from other categories like <strong>User Filters</strong>,' +
                    '<strong>Session Filters</strong>, <strong>Attribute Filters</strong>, which are available below in this sub-tab. <br/>' +
                    'You can also directly generate the graph by clicking the Graph icon in <strong> Indicator Information</strong> Tab.',
                    type: 'information'
                });
            }
            else if(callstatus == 2) {
                $.noty.defaults.killer = true;
                noty({
                    text: '<strong>Success</strong> <br/>  <strong>Time Filter Summary</strong> successfully refreshed. <br/>',
                    type: 'success'
                });
            }
            else if (callstatus == 3) {

                $.noty.defaults.killer = true;
                noty({
                    text: '<strong>Success</strong> <br/>  <strong>All Time Filters </strong> successfully deleted. <br/>',
                    type: 'success'
                });
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
    var url ="/indicators/deleteSessionFilters";
    request.open("GET",url,true);
    request.onreadystatechange= function(){displaySessionFilters(3,request)};
    request.send(null);
}

function displaySessionFilters(callstatus, request) {
    if (request.readyState == 4) {
        if (request.status == 200) {
            if(callstatus == 1) {
                $.noty.defaults.killer = true;
                noty({
                    text: '<strong>Success</strong> <br/> 1 <strong>Session Filter</strong> successfully added. <br/>' +
                    'You can view the newly added filter in <strong> Session Filters Summary </strong> sub-tab available under <strong>Filter Summary</strong> Tab.<br/>' +
                    'Deletion of Filters is also possible from there.',
                    type: 'success'
                });
                noty({
                    text: '<strong>Next Step</strong> <br/> You can add more filters of the same type or from other categories like <strong>User Filters</strong>,' +
                    '<strong>Attribute Filters</strong>, <strong>Time Filters</strong>, which are available in this sub-tab. <br/>' +
                    'You can also directly generate the graph by clicking the Graph icon in <strong> Indicator Information</strong> Tab.',
                    type: 'information'
                });
            }
            else if(callstatus == 2) {
                $.noty.defaults.killer = true;
                noty({
                    text: '<strong>Success</strong> <br/>  <strong>Session Filter Summary</strong> successfully refreshed. <br/>',
                    type: 'success'
                });
            }
            else if (callstatus == 3) {

                $.noty.defaults.killer = true;
                noty({
                    text: '<strong>Success</strong> <br/>  <strong>All Session Filters </strong> successfully deleted. <br/>',
                    type: 'success'
                });
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
            $.noty.defaults.killer = true;
            noty({
                text: '<strong>Success</strong> <br/>  <strong>Current Indicator Summary</strong> successfully refreshed. <br/>',
                type: 'success'
            });
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
                var searchType = document.getElementById("specificationType").value;
                var evalue = 'ALL';
                var url ="/indicators/addEntity?key="+keySelected+"&search="+searchType+"&value="+evalue;
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
            $.noty.defaults.killer = true;
            noty({
                text: '<strong>Success</strong> <br/>  <strong>Congratulations ! </strong> Your graph for the current Indicator has been generated',
                type: 'success'
            });
            noty({
                text: '<strong>Information</strong> <br/>  You can view the graph by clicking on the <strong> Graph Preview</strong>  tab in the ' +
                '<strong> Indicator Properties & Summary </strong> Window',
                type: 'information'
            });
            var graphImage = document.getElementById("graphImage");
            graphImage.src="/graphs/jgraph?bean=true"+"&time="+new Date().getTime();
            $('#templatemo-tabs a[href="#graphGeneration"]').tab('show');
        }
    }
}

function refreshQuestionSummary() {
    var request = createRequest();
    var url ="/indicators/refreshQuestionSummary";
    request.open("GET",url,true);
    request.onreadystatechange=function(){postrefreshQuestionSummary(request)};
    request.send(null);
}

function postrefreshQuestionSummary(request) {
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

function finalizeIndicator(filterPresent) {
    if(filterPresent) {
        var request = createRequest();
        var questionName = document.getElementById("questionNaming").value;
        var indicatorName = document.getElementById("indicatorNaming").value;
        var graphType = document.getElementById("selectedChartType").value;
        var graphEngine = document.getElementById("EngineSelect").value;
        var url ="/indicators/finalize?questionName="+questionName+"&indicatorName="+indicatorName+"&graphType="+graphType
            +"&graphEngine="+graphEngine;
        request.open("GET",url,true);
        request.onreadystatechange=function(){postrefreshQuestionSummary(request)};
        request.send(null);
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
            removeOptions(selectedMinor);
            refreshQuestionSummary();
        }
    }
}

function SaveQuestionDB() {
    var request = createRequest();
    var userName = document.getElementById("userName").value;
    var url ="/indicators/saveQuestionDB?userName="+userName;
    request.open("GET",url,true);
    request.onreadystatechange=function(){processScreenForNextQuestion(request)};
    request.send(null);
}

function processScreenForNextQuestion(request) {
    if (request.readyState == 4) {
        if (request.status == 200) {
            var parsedJSON = JSON.parse(request.responseText);
            document.getElementById("questionNaming").value = "";
            document.getElementById("indicatorNaming").value = "";
            var selectedMinor = document.getElementById("selectedMinor");
            removeOptions(selectedMinor);
            refreshQuestionSummary();
            $.noty.defaults.killer = true;
            noty({
                text: '<strong>Success</strong> <br/>  <strong>Congratulations ! </strong> Your Question and all its indicators have been saved.',
                type: 'success'
            });
            noty({
                text: '<strong>Information</strong> <br/>  You can define a new Question or you can view all existing Questions by clicking the ' +
                '<strong> View Existing <strong> link of the left side of the page.',
                type: 'information'
            });
        }
    }
}

function QuestionVisualize() {
    var request = createRequest();
    var url ="/indicators/refreshQuestionSummary";
    request.open("GET",url,true);
    request.onreadystatechange=function(){updateVisualisationTab(request)};
    request.send(null);
}
function updateVisualisationTab(request) {
    if (request.readyState == 4) {
        if (request.status == 200) {
            var parsedJSON = JSON.parse(request.responseText);
            if(parsedJSON.genQueries.length ==0) {
                $.noty.defaults.killer = true;
                noty({
                    text: '<strong>Error</strong> <br/>  No Indicators have been defined for Visualization',
                    type: 'error'
                });
            }
            else {
                $.noty.defaults.killer = true;
                noty({
                    text: '<strong>Success</strong> <br/>  Current Question has been visualized successfully.',
                    type: 'success'
                });
                var src = document.getElementById("runIndMem");
                src.innerHTML = "";
                for(i=0; i<parsedJSON.genQueries.length; i++) {

                    var label= document.createElement("label");
                    var description = document.createTextNode(parsedJSON.genQueries[i].indicatorName);
                    var checkbox = document.createElement("input");
                    checkbox.type = "checkbox";    // make the element a checkbox
                    checkbox.name = parsedJSON.genQueries[i].indicatorName;      // give it a name we can check on the server side
                    checkbox.value = parsedJSON.genQueries[i].indicatorName;         // make its value "pair"
                    checkbox.className = "messageCheckbox";

                    label.appendChild(checkbox);   // add the box to the element
                    label.appendChild(description);// add the description to the element
                    // add the label element to your div
                    src.appendChild(label);
                    var img = document.createElement("img");
                    img.src = "/graphs/jgraph?runFromMemory=true&indicator="+parsedJSON.genQueries[i].indicatorName;
                    src.appendChild(img);
                }
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
            $.noty.defaults.killer = true;
            noty({
                text: '<strong>Success</strong> <br/>  A New composite Indicator has been added',
                type: 'success'
            });
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
            $.noty.defaults.killer = true;
            noty({
                text: '<strong>Success</strong> <br/>  Matching Indicators have been fetched from DB',
                type: 'success'
            });
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
    var indName = document.getElementById("searchResults").value;
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
            $.noty.defaults.killer = true;
            noty({
                text: '<strong>Success</strong> <br/>  Selected Indicator Property loaded',
                type: 'success'
            });
            var indicatorData = new Array();
            indicatorData.push(["S/L", "Property", "Value"]);
            indicatorData.push([1, "Indicator Name", parsedJSON.indicator_name]);
            indicatorData.push([2, "Chart Type", parsedJSON.glaIndicatorProps.chartType]);
            indicatorData.push([3, "Chart Engine", parsedJSON.glaIndicatorProps.chartEngine]);
            indicatorData.push([4, "Entity Filters", parsedindProp.entityValues.length]);
            indicatorData.push([5, "Session Filters", parsedindProp.sessionSpecifications.length]);
            indicatorData.push([6, "User Filters", parsedindProp.userSpecifications.length]);
            indicatorData.push([7, "Time Filters",parsedindProp.timeSpecifications.length]);
            indicatorData.push([8, "Sources", parsedindProp.source]);
            indicatorData.push([9, "Platform", parsedindProp.platform]);
            indicatorData.push([10, "Action", parsedindProp.action]);
            indicatorData.push([11, "Minor", parsedindProp.minor]);
            indicatorData.push([12, "Major", parsedindProp.major]);
            indicatorData.push([13, "Hibernate Query", parsedJSON.hql]);

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

            var dvTable = document.getElementById("IndPropsFromDB");
            dvTable.innerHTML = "";
            dvTable.appendChild(table);
        }
    }
}

function loadFromTemplate() {
    var indName = document.getElementById("searchResults").value;
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
            var parsedindProp = JSON.parse(parsedJSON.glaIndicatorProps.json_data);

            var sel = document.getElementById('PlatformSelection');
            var opts = sel.options;
            for (var opt, j = 0; opt = opts[j]; j++) {
                if (opt.value == parsedindProp.platform) {
                    sel.selectedIndex = j;
                    break;
                }
            }
            sel = document.getElementById('actionSelection');
            var opts = sel.options;
            for (var opt, j = 0; opt = opts[j]; j++) {
                if (opt.value == parsedindProp.action) {
                    sel.selectedIndex = j;
                    break;
                }
            }
            sel = document.getElementById('selectedChartType');
            var opts = sel.options;
            for (var opt, j = 0; opt = opts[j]; j++) {
                if (opt.value == parsedindProp.chartType) {
                    sel.selectedIndex = j;
                    break;
                }
            }
            sel = document.getElementById('EngineSelect');
            var opts = sel.options;
            for (var opt, j = 0; opt = opts[j]; j++) {
                if (opt.value == parsedindProp.chartEngine) {
                    sel.selectedIndex = j;
                    break;
                }
            }
            var optionsToSelect = parsedindProp.source;
            var select = document.getElementById('sourceSelection');

            for (var i = 0, l = select.options.length, o; i < l; i++) {
                o = select.options[i];
                if (optionsToSelect.indexOf(o.text) != -1) {
                    o.selected = true;
                }
            }
            // Refresh the Filter Summary
            $("#refreshSessionSettings").click();
            $("#refreshTimeSettings").click();
            $("#refreshUserSettings").click();
            $("#refreshEntity").click();
            populateCategories();
            $.noty.defaults.killer = true;
            noty({
                text: '<strong>Success</strong> <br/>  Selected Indicator has been loaded as a Template',
                type: 'success'
            });
        }
    }
}