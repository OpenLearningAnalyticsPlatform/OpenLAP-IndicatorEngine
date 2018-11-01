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


// function createRequest() {
//     try {
//         request = new XMLHttpRequest();
//     } catch(failed){
//         alert("Error creating request object!");
//         request = null;
//     }
// }

function createRequest(success) {
    var request;
    if (window.XMLHttpRequest) {
        request = new XMLHttpRequest();
    }
    else {
        request = new ActiveXObject("Microsoft.XMLHTTP");
    }
    request.onreadystatechange = function (request) {
        if (request.currentTarget.readyState == 4 && success != undefined) {
            success();
        }
    };
    return request;
}