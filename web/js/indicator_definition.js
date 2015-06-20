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

function validateQuestionName(){
    createRequest();
    var questionNameEntered = document.getElementById("questionNaming").value;
    var url ="/indicators/validateQName?qname="+questionNameEntered;
    request.open("GET",url,true);
    request.onreadystatechange=alert_questionName;
    request.send(null);
}

function validateIndicatorName(){
    createRequest();
    var indicatorNameEntered = document.getElementById("indicatornaming").value;
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
    var minorSelected = document.getElementById("selectedMinor").value;
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
    if (keySelected.value == null) {
        alert( "Please Select a Valid Key." );
        keySelected.focus();
        return false ;
    }
    if (evalue.value == "") {
        alert( "Please fill a proper Search Term. For searching everything write 'ALL'" );
        evalue.focus();
        return false ;
    }
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
        addTable(heading,data);
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

function addTable(heading,data) {
    var myTableDiv = document.getElementById("entity_filters");
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
