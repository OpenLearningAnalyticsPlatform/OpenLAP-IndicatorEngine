<%--
  ~ /*
  ~  * Copyright (C) 2015  Tanmaya Mahapatra
  ~  *
  ~  * This program is free software; you can redistribute it and/or
  ~  * modify it under the terms of the GNU General Public License
  ~  * as published by the Free Software Foundation; either version 2
  ~  * of the License, or (at your option) any later version.
  ~  *
  ~  * This program is distributed in the hope that it will be useful,
  ~  * but WITHOUT ANY WARRANTY; without even the implied warranty of
  ~  * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
  ~  * GNU General Public License for more details.
  ~  *
  ~  * You should have received a copy of the GNU General Public License
  ~  * along with this program; if not, write to the Free Software
  ~  * Foundation, Inc., 51 Franklin Street, Fifth Floor, Boston, MA  02110-1301, USA.
  ~  */
  --%>

<%--
  Created by IntelliJ IDEA.
  User: Tanmaya Mahapatra
  Date: 16-03-2015
  Time: 12:00
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%
    if ((session.getAttribute("activationStatus") != "false"))
    response.sendRedirect("/welcome");
    else
    {


%>
<html>
<head>
    <meta charset="utf-8">
    <!--[if IE]><meta http-equiv="X-UA-Compatible" content="IE=edge,chrome=1"><![endif]-->
    <title>Goal Oriented LA Toolkit Activation : Status</title>
    <meta name="keywords" content="" />
    <meta name="description" content="" />
    <meta name="viewport" content="width=device-width">
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/templatemo_main.css">
    <script language="JavaScript" src="${pageContext.request.contextPath}/js/otp_validation.js"> </script>
</head>
<body>
<div id="main-wrapper">
    <div class="navbar navbar-inverse" role="navigation">
        <div class="navbar-header">
            <div class="logo"><h1>Goal Oriented LA Toolkit : Activation Status</h1></div>
        </div>
    </div>
    <div align="center">
        <table border="0">
            <tr>
                <td colspan="2" align="center"><h2>Attention !</h2></td>
            </tr>
            <tr>
                <td colspan="2" align="center">
                    <h3>Thank you for registering! Before You can use the Services. You need to activate your user acount.</h3>
                </td>
            </tr>
        </table>
    </div>
</div>
<div class="template-page-wrapper">
    <form class="form-horizontal templatemo-signin-form" role="form">
        <div class="form-group">
            <div class="col-md-12">
                <label for="otp" class="col-sm-2 control-label">OTP</label>
                <div class="col-sm-10">
                    <input type="password" class="form-control" id="otp" placeholder="Enter the OTP">
                </div>
            </div>
        </div>
        <div class="form-group">
            <div class="col-md-12">
                <div class="col-sm-10">
                    <input type="hidden" class="form-control" id="username" value=<%= session.getAttribute("userName")%>>
                </div>
            </div>
        </div>

        <div class="form-group">
            <div class="col-md-12">
                <div class="col-sm-offset-2 col-sm-10">
                    <input type="button" value="Validate" class="btn btn-default" onclick="validateOTP()">
                </div>
            </div>
        </div>
        <div class="form-group">
            <div class="col-md-12">
                <div class="col-sm-offset-2 col-sm-10">
                    <input type="text" id = "status" value="Your Activation Status" class="form-control">
                </div>
            </div>
        </div>
        <div class="form-group">
            <div class="col-md-12">
                <div class="col-sm-offset-2 col-sm-10">
                    <input type="button" value="Logoff" class="btn btn-default" onclick="Logout()">
                </div>
            </div>
        </div>
    </form>
</div>
</body>
</html>
<%
    }
%>
