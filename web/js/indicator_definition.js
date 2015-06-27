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
    console.log(indicatorNameEntered);
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
                document.getElementById("indicatornaming").value=null;
            }
            else if (request.responseText == "short") {

                alert("Indicator Name is too Short");
                document.getElementById("indicatornaming").value=null;
            }
            else if (request.responseText == "null") {

                alert("Indicator Name cannot be Empty");
                document.getElementById("indicatornaming").value=null;
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
    var indicatorName = document.getElementById("indicatornaming").value;
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

function addNewIndicator() {
    createRequest();
    var url ="/indicators/addNewIndicator";
    request.open("GET",url,true);
    request.onreadystatechange=processScreenForNextIndicator;
    request.send(null);
}

function processScreenForNextIndicator() {
    if (request.readyState == 4) {
        if (request.status == 200) {
            var parsedJSON = JSON.parse(request.responseText);
            alert(request.responseText);
            document.getElementById("indicatornaming").value = "";
            var selectedMinor = document.getElementById("selectedMinor");
            removeOptions(selectedMinor);

        }
    }


}