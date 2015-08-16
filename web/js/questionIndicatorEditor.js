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
 * Created by Tanmaya Mahapatra on 26-06-2015.
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

function populateSPA() {

    populateSources();
    populatePlatform();
    populateAction();
}

function FilterInit() {
    request = createRequest();
    var url ="/indicators/initFilters";
    request.open("GET",url,false);
    request.onreadystatechange=processReceivedFilters;
    request.send(null);
}

function processReceivedFilters() {
    if (request.readyState == 4) {
        if (request.status == 200) {
            var parsedJSON = JSON.parse(request.responseText);
            console.log(parsedJSON);
            var attributeSpecificationType = document.getElementById("specificationType");
            removeOptions(attributeSpecificationType);
            for (var i=0;i< parsedJSON.length;i++) {
                var newOption = new Option(parsedJSON[i], parsedJSON[i]);
                attributeSpecificationType.appendChild(newOption);
            }
        }
    }

}
function populateSources() {
    request = createRequest();
    var url ="/indicators/initSources";
    request.open("GET",url,false);
    request.onreadystatechange=processReceivedSources;
    request.send(null);
}

function processReceivedSources() {
    if (request.readyState == 4) {
        if (request.status == 200) {
            var parsedJSON = JSON.parse(request.responseText);
            var sources = document.getElementById("sourceSelection");
            removeOptions(sources);
            for (var i=0;i< parsedJSON.length;i++) {
                var newOption = new Option(parsedJSON[i], parsedJSON[i]);
                sources.appendChild(newOption);
            }
        }
    }
}

function populatePlatform() {
    request = createRequest();
    var url ="/indicators/initPlatform";
    request.open("GET",url,false);
    request.onreadystatechange=processReceivedPlatforms;
    request.send(null);
}

function processReceivedPlatforms() {
    if (request.readyState == 4) {
        if (request.status == 200) {
            var parsedJSON = JSON.parse(request.responseText);
            var platforms = document.getElementById("PlatformSelection");
            removeOptions(platforms);
            for (var i=0;i< parsedJSON.length;i++) {
                var newOption = new Option(parsedJSON[i], parsedJSON[i]);
                platforms.appendChild(newOption);
            }
        }
    }
}

function populateAction() {
    request = createRequest();
    var url ="/indicators/initAction";
    request.open("GET",url,false);
    request.onreadystatechange=processReceivedActions;
    request.send(null);
}

function processReceivedActions() {
    if (request.readyState == 4) {
        if (request.status == 200) {
            var parsedJSON = JSON.parse(request.responseText);
            var actions = document.getElementById("actionSelection");
            removeOptions(actions);
            for (var i=0;i< parsedJSON.length;i++) {
                var newOption = new Option(parsedJSON[i], parsedJSON[i]);
                actions.appendChild(newOption);
            }
        }
    }
}
