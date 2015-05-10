<%--
  ~ Open Platform Learning Analytics : Indicator Engine
  ~ Copyright (C) 2015  Learning Technologies Group, RWTH
  ~
  ~ This program is free software; you can redistribute it and/or
  ~ modify it under the terms of the GNU General Public License
  ~ as published by the Free Software Foundation; either version 2
  ~ of the License, or (at your option) any later version.
  ~
  ~ This program is distributed in the hope that it will be useful,
  ~ but WITHOUT ANY WARRANTY; without even the implied warranty of
  ~ MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
  ~ GNU General Public License for more details.
  ~
  ~ You should have received a copy of the GNU General Public License
  ~ along with this program; if not, write to the Free Software
  ~ Foundation, Inc., 51 Franklin Street, Fifth Floor, Boston, MA  02110-1301, USA.
  --%>

<%--
  Created by IntelliJ IDEA.
  User: Tanmaya Mahapatra
  Date: 18-03-2015
  Time: 03:15
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%
    if ((session.getAttribute("loggedIn") == null) || (session.getAttribute("loggedIn") == ""))
        response.sendRedirect("/login");
    if ((session.getAttribute("loggedIn") != null) && (session.getAttribute("loggedIn") != "") && (session.getAttribute("role").equals("ROLE_USER")) && (session.getAttribute("admin_access").equals("NO")))
        response.sendRedirect("/home/dashboard");
    if ((session.getAttribute("loggedIn") != null) && (session.getAttribute("loggedIn") != "") && (session.getAttribute("admin_access").equals("YES")))
    {
%>

<html>
<head>
    <meta charset="utf-8">
    <!--[if IE]><meta http-equiv="X-UA-Compatible" content="IE=edge,chrome=1"><![endif]-->
    <title>Goal LA Tookit Admin Center</title>
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
        <div class="logo"><h1>Goal LA Tookit Admin Center</h1></div>
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
            <li class="active"><a href="/toolkit/admin"><i class="fa fa-dashboard"></i>Admin Dashboard</a></li>
            <li class="sub open">
                <a href="javascript:;">
                    <i class="fa fa-database"></i> Manage Data <div class="pull-right"><span class="caret"></span></div>
                </a>
                <ul class="templatemo-submenu">
                    <li><a href="/toolkit/data_control_center"><i class="fa fa-certificate"></i><span class="badge pull-right"></span>Datasets</a></li>
                </ul>
            </li>
            <li class="sub open">
                <a href="javascript:;">
                    <i class="fa fa-database"></i> Manage Operations <div class="pull-right"><span class="caret"></span></div>
                </a>
                <ul class="templatemo-submenu">
                    <li><a href="/toolkit/operations_control_center"><i class="fa fa-certificate"></i><span class="badge pull-right"></span>Operations</a></li>
                </ul>
            </li>
            <li><a href="javascript:;" data-toggle="modal" data-target="#confirmModal"><i class="fa fa-sign-out"></i>Sign Out</a></li>
        </ul>
    </div><!--/.navbar-collapse -->

    <div class="templatemo-content-wrapper">
        <div class="templatemo-content">
            <ol class="breadcrumb">
                <li><a href="/home/dashboard">Dashboard</a></li>
                <li><a href="/toolkit/admin">Toolkit Admin Home</a></li>
            </ol>
            <h1> Manipulate Datsets</h1>
            <p>Here You can add or View Data Sets. Please be careful there are no checks for the operatios here in. You can ruin all data.</p>
            <div class="col-md-6 col-sm-6 margin-bottom-30">
                <div class="panel panel-primary">
                    <div class="panel-heading">Add Datasets</div>
                    <div class="panel-body">
                        <table class="table table-striped">
                            <thead>
                            <tr>
                                <th>S/L</th>
                                <th>Operation Name</th>
                                <th>Description</th>
                                <th>Link</th>
                            </tr>
                            </thead>
                            <tbody>
                            <tr>
                                <td>1</td>
                                <td>Add User</td>
                                <td>Adds a new User</td>
                                <td><a href="/toolkit/add_userdata"><i class="fa fa-user"></i><span class="badge pull-right"></span>Add User</a></td>
                            </tr>
                            <tr>
                                <td>2</td>
                                <td>Add Category</td>
                                <td>Adds a new Category</td>
                                <td><a href="/toolkit/add_categorydata"><i class="fa fa-lemon-o"></i><span class="badge pull-right"></span>Add Category</a></td>
                            </tr>
                            <tr>
                                <td>3</td>
                                <td>Add Event</td>
                                <td>Adds a new Event</td>
                                <td><a href="/toolkit/add_eventdata"><i class="fa fa-tasks"></i><span class="badge pull-right"></span>Add Event</a></td>
                            </tr>
                            <tr>
                                <td>4</td>
                                <td>Add Entities</td>
                                <td>Adds entities for a Particular Event ID.</td>
                                <td><a href="/toolkit/add_entitydata"><i class="fa fa-certificate"></i><span class="badge pull-right"></span>Add Entity</a></td>
                            </tr>
                            </tbody>
                        </table>
                    </div>
                </div>
            </div>
            <div class="col-md-6 col-sm-6 margin-bottom-30">
                <div class="panel panel-primary">
                    <div class="panel-heading">View Datasets</div>
                    <div class="panel-body">
                        <table class="table table-striped">
                            <thead>
                            <tr>
                                <th>S/L</th>
                                <th>Operation Name</th>
                                <th>Description</th>
                                <th>Link</th>
                            </tr>
                            </thead>
                            <tbody>
                            <tr>
                                <td>1</td>
                                <td>View User</td>
                                <td>Listing of all Users present</td>
                                <td><a href="/toolkit/view_userdata"><i class="fa fa-user"></i><span class="badge pull-right"></span>View User</a></td>
                            </tr>
                            <tr>
                                <td>2</td>
                                <td>View Category</td>
                                <td>Listing of all Categories present</td>
                                <td><a href="/toolkit/view_categorydata"><i class="fa fa-lemon-o"></i><span class="badge pull-right"></span>View Category</a></td>
                            </tr>
                            <tr>
                                <td>3</td>
                                <td>View Event</td>
                                <td>Listing of all Events present</td>
                                <td><a href="/toolkit/view_eventdata"><i class="fa fa-tasks"></i><span class="badge pull-right"></span>View Event</a></td>
                            </tr>
                            <tr>
                                <td>4</td>
                                <td>View Entities</td>
                                <td>Listing of all Entities present</td>
                                <td><a href="/toolkit/view_entitydata"><i class="fa fa-certificate"></i><span class="badge pull-right"></span>View Entity</a>
                                </td>
                            </tr>
                            </tbody>
                        </table>
                    </div>
                </div>
            </div>
            <div class="col-md-6 col-sm-6 margin-bottom-30">
                <div class="panel panel-primary">
                    <div class="panel-heading">Add Datasets from JSON (Should be in C:)</div>
                    <div class="panel-body">
                        <table class="table table-striped">
                            <thead>
                            <tr>
                                <th>S/L</th>
                                <th>Operation Name</th>
                                <th>Description</th>
                                <th>Link</th>
                            </tr>
                            </thead>
                            <tbody>
                            <tr>
                                <td>1</td>
                                <td>Add User from JSON</td>
                                <td>Adds a new User</td>
                                <td><a href="/toolkit/add_json_user"><i class="fa fa-user"></i><span class="badge pull-right"></span>Add User</a></td>
                            </tr>
                            <tr>
                                <td>2</td>
                                <td>Add Category from JSON</td>
                                <td>Adds a new Category</td>
                                <td><a href="/toolkit/add_json_category"><i class="fa fa-lemon-o"></i><span class="badge pull-right"></span>Add Category</a></td>
                            </tr>
                            <tr>
                                <td>3</td>
                                <td>Add Event from JSON</td>
                                <td>Adds a new Event</td>
                                <td><a href="/add_json_events"><i class="fa fa-tasks"></i><span class="badge pull-right"></span>Add Event</a></td>
                            </tr>
                            <tr>
                                <td>4</td>
                                <td>Add Entities from JSON</td>
                                <td>Adds entities for a Particular Event ID.</td>
                                <td><a href="/add_json_entity"><i class="fa fa-certificate"></i><span class="badge pull-right"></span>Add Entity</a></td>
                            </tr>
                            </tbody>
                        </table>
                    </div>
                </div>
            </div>
            <div class="col-md-6 col-sm-6 margin-bottom-30">
                <div class="panel panel-primary">
                    <div class="panel-heading">Delete Datasets </div>
                    <div class="panel-body">
                        <table class="table table-striped">
                            <thead>
                            <tr>
                                <th>S/L</th>
                                <th>Operation Name</th>
                                <th>Description</th>
                                <th>Link</th>
                            </tr>
                            </thead>
                            <tbody>
                            <tr>
                                <td>1</td>
                                <td>Delete User</td>
                                <td>Deletes a Specific User and all its data.</td>
                                <td><a href="/toolkit/delete_userdata"><i class="fa fa-user"></i><span class="badge pull-right"></span>Delete User</a></td>
                            </tr>
                            <tr>
                                <td>2</td>
                                <td>Delete Category</td>
                                <td>Deletes a Specific Category and all its data.</td>
                                <td><a href="/toolkit/delete_categorydata"><i class="fa fa-lemon-o"></i><span class="badge pull-right"></span>Delete Category</a></td>
                            </tr>
                            <tr>
                                <td>3</td>
                                <td>Delete Event</td>
                                <td>Deletes a Specific Event and all its Entities.</td>
                                <td><a href="/toolkit/delete_eventdata"><i class="fa fa-tasks"></i><span class="badge pull-right"></span>Delete Event</a></td>
                            </tr>
                            <tr>
                                <td>4</td>
                                <td>Delete Entity</td>
                                <td>Deletes a Specific Entity</td>
                                <td><a href="/toolkit/delete_entitydata"><i class="fa fa-certificate"></i><span class="badge pull-right"></span>Delete Entity</a></td>
                            </tr>
                            </tbody>
                        </table>
                    </div>
                </div>
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