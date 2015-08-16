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
 * Created by Tanmaya Mahapatra on 16-03-2015.
 */

function Logout() {
    window.location="/logoff";
}
var request;
function createRequest() {
    try {
        request = new XMLHttpRequest();
    } catch(failed){
        alert("Error creating request object!");
        request = null;
    }
}

function validateOTP(){
    request = createRequest();
    var otpEntered = document . getElementById("otp").value;
    var username = document . getElementById("username").value;;
    var url ="/validateotp?otp="+escape(otpEntered)+"&username="+escape(username);
    request.open("GET",url,true);
    request.onreadystatechange=alert_updateStatus;
    request.send(null);

}
function alert_updateStatus() {
    if (request.readyState == 4) {
        if (request.status == 200) {

            if (request.responseText == "true") {

                document.getElementById("status").value = "Your Account is Activated";
            }
            else {
                document.getElementById("status").value = "Failed to Activate your account. Please Try again!";
            }

        }
    }
}