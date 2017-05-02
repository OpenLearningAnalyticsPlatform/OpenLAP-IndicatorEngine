<%--
  ~ Open Learning Analytics Platform (OpenLAP) : Indicator Engine

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
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%
    if ((session.getAttribute("loggedIn") == null) || (session.getAttribute("loggedIn") == ""))
        response.sendRedirect("/login");
    if ((session.getAttribute("loggedIn") != null) && (session.getAttribute("loggedIn") != "") && (session.getAttribute("role").equals("User")) && (session.getAttribute("admin_access").equals("NO")))
        response.sendRedirect("/home/dashboard");
    if ((session.getAttribute("loggedIn") != null) && (session.getAttribute("loggedIn") != "") && (session.getAttribute("admin_access").equals("YES")))
    {
%>

<html>
<head>
    <meta charset="utf-8">
    <!--[if IE]><meta http-equiv="X-UA-Compatible" content="IE=edge,chrome=1"><![endif]-->
    <title>View Data</title>
    <meta name="keywords" content="" />
    <meta name="description" content="" />
    <meta name="viewport" content="width=device-width">
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/templatemo_main.css">
    <script type="javascript" src="${pageContext.request.contextPath}/js/user_profile_checks.js"> </script>
    <link rel="stylesheet" type="text/css" href="//cdn.datatables.net/1.10.0/css/jquery.dataTables.css">
    <script type="text/javascript" src="//code.jquery.com/jquery-1.10.2.min.js"></script>
    <script type="text/javascript" src="//cdn.datatables.net/1.10.0/js/jquery.dataTables.js"></script>
    <script type="text/javascript">

        (function($) {
            //Plug-in to fetch page data
            jQuery.fn.dataTableExt.oApi.fnPagingInfo = function ( oSettings )
            {
                return {
                    "iStart":         oSettings._iDisplayStart,
                    "iEnd":           oSettings.fnDisplayEnd(),
                    "iLength":        oSettings._iDisplayLength,
                    "iTotal":         oSettings.fnRecordsTotal(),
                    "iFilteredTotal": oSettings.fnRecordsDisplay(),
                    "iPage":          oSettings._iDisplayLength === -1 ?
                            0 : Math.ceil( oSettings._iDisplayStart / oSettings._iDisplayLength ),
                    "iTotalPages":    oSettings._iDisplayLength === -1 ?
                            0 : Math.ceil( oSettings.fnRecordsDisplay() / oSettings._iDisplayLength )
                };
            };

            $(document).ready(function() {

                $("#gla_categoryData").dataTable( {
                    "bProcessing": true,
                    "bServerSide": true,
                    "sort": "position",
                    //bStateSave variable you can use to save state on client cookies: set value "true"
                    "bStateSave": false,
                    //Default: Page display length
                    "iDisplayLength": 10,
                    //We will use below variable to track page number on server side(For more information visit: http://legacy.datatables.net/usage/options#iDisplayStart)
                    "iDisplayStart": 0,
                    "fnDrawCallback": function () {
                        //Get page numer on client. Please note: number start from 0 So
                        //for the first page you will see 0 second page 1 third page 2...
                        //Un-comment below alert to see page number
                        //alert("Current page number: "+this.fnPagingInfo().iPage);
                    },
                    "sAjaxSource": "/toolkit/fetchGlaCategoryData.web",
                    "aoColumns": [
                        { "mData": "id" },
                        { "mData": "type" },
                        { "mData": "major" },
                        { "mData": "minor" },
                    ]
                } );

            } );
        })(jQuery);
    </script>
</head>
<body>
<div class="navbar navbar-inverse" role="navigation">
    <div class="navbar-header">
        <div class="logo"><h1>View Category</h1></div>
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
            <h1>VIew Categories</h1>
            <p>You can view all the categories present in GLA_CATEGORY.</p>
            <div class="row">
                <div class="col-md-12">
                    <form:form action="" method="GET">
                        <h2 >Listing of All Category Data from gla_Category Table<br><br></h2>
                        <table width="70%" style="border: 3px;background: rgb(243, 244, 248);"><tr><td>
                            <table id="gla_categoryData" class="display" cellspacing="0" width="100%">
                                <thead>
                                <tr>
                                    <th>ID</th>
                                    <th>TYPE</th>
                                    <th>MAJOR</th>
                                    <th>MINOR</th>
                                </tr>
                                </thead>
                            </table>
                        </td></tr></table>
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
            <p>Open Learning Analytics Platform (OpenLAP)</p>
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