<%@ page import="com.indicator_engine.datamodel.UserProfile" %>
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
  Time: 04:33
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%
    if ((session.getAttribute("loggedIn") == null) || (session.getAttribute("loggedIn") == ""))
        response.sendRedirect("/login");

    if ((session.getAttribute("loggedIn") != null) && (session.getAttribute("userName") != null) && (session.getAttribute("activationStatus")== "false"))
        response.sendRedirect("/activate");
    else{
        UserProfile up = (UserProfile) request.getAttribute("UserProfile");

%>
<html>
<head>
    <meta charset="utf-8">
    <!--[if IE]><meta http-equiv="X-UA-Compatible" content="IE=edge,chrome=1"><![endif]-->
    <title>User Profile</title>
    <meta name="keywords" content="" />
    <meta name="description" content="" />
    <meta name="viewport" content="width=device-width">
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/templatemo_main.css">
    <script type="javascript" src="${pageContext.request.contextPath}/js/user_profile_checks.js"> </script>
    <!--
    Dashboard Template
    http://www.templatemo.com/preview/templatemo_415_dashboard
    -->
</head>
<body>
<div class="navbar navbar-inverse" role="navigation">
    <div class="navbar-header">
        <div class="logo"><h1>Manage your Profile</h1></div>
        <button type="button" class="navbar-toggle" data-toggle="collapse" data-target=".navbar-collapse">
            <span class="sr-only">Toggle navigation</span>
            <span class="icon-bar"></span>
            <span class="icon-bar"></span>
            <span class="icon-bar"></span>
        </button>
    </div>
</div>
<div class="template-page-wrapper">
    <div class="navbar-collapse collapse templatemo-sidebar">
        <ul class="templatemo-sidebar-menu">
            <li>
                <form class="navbar-form">
                    <input type="text" class="form-control" id="templatemo_search_box" placeholder="Search...">
                    <span class="btn btn-default">Go</span>
                </form>
            </li>
            <li class="active"><a href="/home/dashboard"><i class="fa fa-home"></i>Dashboard</a></li>
            <li class="sub open">
                <a href="javascript:;">
                    <i class="fa fa-database"></i> LA Control Centre <div class="pull-right"><span class="caret"></span></div>
                </a>
                <ul class="templatemo-submenu">
                    <li><a href="/toolkit/admin"><i class="fa fa-cogs"></i><span class="badge pull-right"></span>Toolkit Admin</a></li>
                    <li><a href="/indicators/home"><i class="fa fa-road"></i><span class="badge pull-right"></span>Indicators</a></li>
                    <li><a href="/visualisation/home"><i class="fa fa-bar-chart-o"></i><span class="badge pull-right"></span>Visualisation</a></li>
                </ul>
            </li>
            <li class="sub open">
                <a href="javascript:;">
                    <i class="fa fa-database"></i> User Control Centre <div class="pull-right"><span class="caret"></span></div>
                </a>
                <ul class="templatemo-submenu">
                    <li><a href="/home/user_profile"><i class="fa fa-user"></i>Preferences</a></li>
                    <li><a href="#"><i class="fa fa-envelope"></i>Messages</a></li>
                </ul>
            </li>
            <li><a href="javascript:;" data-toggle="modal" data-target="#confirmModal"><i class="fa fa-sign-out"></i>Sign Out</a></li>
        </ul>
    </div><!--/.navbar-collapse -->

    <div class="templatemo-content-wrapper">
        <div class="templatemo-content">
            <ol class="breadcrumb">
                <li><a href="/home/dashboard">Home</a></li>
                <li><a href="/home/user_profile">User Profile</a></li>
            </ol>
            <h1>User Profile</h1>
            <p>You can view your profile here and can make necessary updates. You are not allowed to change the Unique Identification Number.</p>
            <div class="row">
                <div class="col-md-12">
                    <form:form role="form" id="user-profile-form" action="/home/update_user_profile" method="post" commandName="UprofileForm">
                        <div class="row">
                            <div class="col-md-6 margin-bottom-15">
                                <label for="firstName" class="control-label">First Name</label>
                                <form:input path="fname" type="text" class="form-control" name ="firstName" id="firstName"  value="${UprofileForm.fname}"/>
                            </div>
                            <div class="col-md-6 margin-bottom-15">
                                <label for="lastName" class="control-label">Last Name</label>
                                <form:input path="lname" type="text" class="form-control" name ="lastName" id="lastName" value="${UprofileForm.lname}"/>
                            </div>
                        </div>
                        <div class="row">
                            <div class="col-md-6 margin-bottom-15">
                                <label for="uid">User Identification Number</label>
                                <input type="text" class="form-control" name ="id" id="uid"  value="${UprofileForm.uid}" disabled/>
                            </div>
                            <div class="col-md-6 margin-bottom-15">
                                <label for="username">Login/User Name</label>
                                <input type="text" class="form-control" name ="username" id="username" value=<%=session.getAttribute("userName")%> disabled>
                            </div>
                        </div>
                        <div class="row">
                            <div class="col-md-6 margin-bottom-15">
                                <label for="phonenumber" class="control-label">Phone Number</label>
                                <form:input path="phonenumber" type="number" class="form-control" name ="phonenumber" id="phonenumber" value="${UprofileForm.phonenumber}"/>
                            </div>
                            <div class="col-md-6 margin-bottom-15">
                                <label for="dob" class="control-label">Date of Birth</label>
                                <input type="text" class="form-control" name ="dob" id="dob" value="${UprofileForm.dob}" disabled/>
                            </div>
                        </div>
                        <div class="row">
                            <div class="col-md-6 margin-bottom-15">
                                <label for="address" class="control-label">Street Address</label>
                                <form:input path="address" type="text" class="form-control" name ="address" id="address" value="${UprofileForm.address}"/>
                            </div>
                            <div class="col-md-6 margin-bottom-15">
                                <label for="zip" class="control-label">ZIP</label>
                                <form:input path="zip" type="number" class="form-control" name ="zip" id="zip" value="${UprofileForm.zip}"/>
                            </div>
                        </div>
                        <div class="row">
                            <div class="col-md-6 margin-bottom-15">
                                <label for="city" class="control-label">City</label>
                                <form:input path="city" type="text" class="form-control" name ="city" id="city" value="${UprofileForm.city}"/>
                            </div>
                            <div class="col-md-6 margin-bottom-15">
                                <label for="country" class="control-label">Country</label>
                                <form:input path="country" type="text" class="form-control" name ="country" id="country" value="${UprofileForm.country}"/>
                            </div>
                        </div>
                        <div class="row">
                            <div class="col-md-6 margin-bottom-15">
                                <label for="state">State</label>
                                <form:input path="state" type="text" class="form-control" name ="state" id="state" value="${UprofileForm.state}"/>
                            </div>

                            <div class="col-md-6 margin-bottom-15">
                                <label for="emailid">Email address</label>
                                <input type="text" class="form-control" name ="emailid" id="emailid"  value="${UprofileForm.email}" disabled/>
                            </div>
                        </div>
                        <div class="row">
                            <div class="col-md-6 margin-bottom-15">
                                <label for="currentPassword">Current Password</label>
                                <input type="password" class="form-control" id="currentPassword" value="********" disabled>
                            </div>
                            <div class="col-md-12 margin-bottom-15">
                                <label for="changepasswd">Change Password ?</label>
                                <form:checkbox  class="checkbox-inline" path="changepasswd" name="changepasswd" value="${UprofileForm.changepasswd}"/>
                            </div>
                        </div>
                        <div class="row">
                            <div class="col-md-6 margin-bottom-15">
                                <label for="password_1">New Password</label>
                                <form:input path="password" type="password" class="form-control" name ="pass1" id="password_1" placeholder="New Password" value="${UprofileForm.password}"/>>
                            </div>
                            <div class="col-md-6 margin-bottom-15">
                                <label for="password_2">Confirm New Password</label>
                                <form:input path="confirmpassword" type="password" class="form-control" name ="pass2" id="password_2" placeholder="Confirm New Password" value="${UprofileForm.confirmpassword}"/>
                            </div>
                        </div>
                        <div class="row templatemo-form-buttons">
                            <div class="col-md-12">
                                <button type="submit" class="btn btn-primary">Update</button>
                                <button type="reset" class="btn btn-default">Reset</button>
                            </div>
                        </div>
                    </form:form>
                </div>
            </div>
        </div>
    </div>
    <!-- Modal -->
    <div class="modal fade" id="confirmModal" tabindex="-1" role="dialog" aria-labelledby="myModalLabel" aria-hidden="true">
        <div class="modal-dialog">
            <div class="modal-content">
                <div class="modal-header">
                    <button type="button" class="close" data-dismiss="modal"><span aria-hidden="true">&times;</span><span class="sr-only">Close</span></button>
                    <h4 class="modal-title" id="myModalLabel">Are you sure you want to sign out?</h4>
                </div>
                <div class="modal-footer">
                    <a href="/logoff" class="btn btn-primary">Yes</a>
                    <button type="button" class="btn btn-default" data-dismiss="modal">No</button>
                </div>
            </div>
        </div>
    </div>
    <footer class="templatemo-footer">
        <div class="templatemo-copyright">
            <p>Copyright &copy; 2015 Learning Technologies Group, RWTH</p>
        </div>
    </footer>
</div>

<script src="${pageContext.request.contextPath}/js/jquery.min.js"></script>
<script src="${pageContext.request.contextPath}/js/bootstrap.min.js"></script>
<script src="${pageContext.request.contextPath}/js/Chart.min.js"></script>
<script src="${pageContext.request.contextPath}/js/templatemo_script.js"></script>
<script type="text/javascript">
</script>
</body>
</html>
<%
    }
%>
