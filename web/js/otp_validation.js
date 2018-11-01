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
 * Created by Tanmaya Mahapatra on 16-03-2015.
 */

function Logout() {
    window.location="/logoff";
}
//var request;
/*function createRequest() {
    try {
        if (window.XMLHttpRequest) {
            request = new XMLHttpRequest();
        }
        else {
            request = new ActiveXObject("Microsoft.XMLHTTP");
        }
    } catch(failed){
        alert("Error creating request object!");
        request = null;
    }
}*/

function createRequest(success) {
    var xmlhttp;
    if (window.XMLHttpRequest) {
        xmlhttp = new XMLHttpRequest();
    }
    else {
        xmlhttp = new ActiveXObject("Microsoft.XMLHTTP");
    }
    xmlhttp.onreadystatechange = function (xmlhttp) {
        if (xmlhttp.currentTarget.readyState == 4 && success != undefined) {
            success();
        }
    };
    return xmlhttp;
}

function validateOTP(){
    //var request = createRequest();
    var otpEntered = document . getElementById("otp").value;
    var username = document . getElementById("username").value;;
    var url ="/validateotp?otp="+encodeURIComponent(otpEntered)+"&username="+encodeURIComponent(username);
    //request.open("GET",url,true);
    //request.onreadystatechange=alert_updateStatus;
    //request.send(null);

    var xmlhttp = createRequest(function () {
        if (xmlhttp.status == 200) {
            if (xmlhttp.responseText == "true") {
                document.getElementById("status").value = "Your Account is Activated";
            }
            else {
                document.getElementById("status").value = "Failed to Activate your account. Please Try again!";
            }
        }
    });

    xmlhttp.open("GET", url, true);
    xmlhttp.send();
}
