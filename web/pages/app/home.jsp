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
  Date: 15-03-2015
  Time: 12:54
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%
if ((session.getAttribute("loggedIn") == null) || (session.getAttribute("loggedIn") == ""))
    response.sendRedirect("/login");

if ((session.getAttribute("loggedIn") != null) && (session.getAttribute("userName") != null) && (session.getAttribute("activationStatus")== "false"))
response.sendRedirect("/activate");
else{

%>
<html>
<head>
    <meta charset="utf-8">
    <!--[if IE]><meta http-equiv="X-UA-Compatible" content="IE=edge,chrome=1"><![endif]-->
    <title>OpenLAP Dashboard</title>
    <meta name="keywords" content="" />
    <meta name="description" content="" />
    <meta name="viewport" content="width=device-width">
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/templatemo_main.css">
    <!--
    Dashboard Template
    http://www.templatemo.com/preview/templatemo_415_dashboard
    -->
</head>
<body>
<div class="navbar navbar-inverse" role="navigation">
    <div class="navbar-header">
        <div class="logo"><h1>OpenLAP</h1></div>
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
            <li class="active"><a href="#"><i class="fa fa-home"></i>Dashboard</a></li>
            <li class="sub open">
                <a href="javascript:;">
                    <i class="fa fa-database"></i> LA Control Centre <div class="pull-right"><span class="caret"></span></div>
                </a>
                <ul class="templatemo-submenu">
                    <li><a href="/toolkit/admin"><i class="fa fa-cogs"></i><span class="badge pull-right"></span>Toolkit Admin</a></li>
                    <li><a href="/indicators/home"><i class="fa fa-road"></i><span class="badge pull-right"></span>Indicators</a></li>

                </ul>
            </li>
            <li class="sub open">
                <a href="javascript:;">
                    <i class="fa fa-database"></i> User Control Centre <div class="pull-right"><span class="caret"></span></div>
                </a>
                <ul class="templatemo-submenu">
                    <li><a href="/home/user_profile"><i class="fa fa-user"></i>Preferences</a></li>
                </ul>
            </li>
            <li><a href="javascript:;" data-toggle="modal" data-target="#confirmModal"><i class="fa fa-sign-out"></i>Sign Out</a></li>
        </ul>
    </div><!--/.navbar-collapse -->

    <div class="templatemo-content-wrapper">
        <div class="templatemo-content">
            <ol class="breadcrumb">
                <li><a href="/toolkit/admin">Toolkit Admin</a></li>
                <li><a href="/home/dashboard">Dashboard</a></li>
                <li><a href="/login">Home</a></li>
            </ol>
            <h1>Open Learning Analytics Platform (OpenLAP)</h1>
            <p>This is the Dashboard. In Progress.</p>

            <div class="margin-bottom-30">
                <div class="row">
                    <div class="col-md-12">
                        <ul class="nav nav-pills">
                            <li class="active"><a href="/login">Home<span class="badge"></span></a></li>
                            <li class="active"><a href="/home/user_profile">My Profile <span class="badge"></span></a></li>
                            <li class="active"><a href="/toolkit/admin">Toolkit Admin<span class="badge"></span></a></li>
                        </ul>
                    </div>
                </div>
            </div>

            <div class="row">
                <div class="col-md-6">
                    <div class="templatemo-alerts">
                        <div class="row">
                            <div class="col-md-12">
                                <div class="alert alert-success alert-dismissible" role="alert">
                                    <button type="button" class="close" data-dismiss="alert"><span aria-hidden="true">&times;</span><span class="sr-only">Close</span></button>
                                    <strong>Success!</strong> Goal LA User Profile Development completed.
                                </div>
                                <div class="alert alert-info alert-dismissible" role="alert">
                                    <button type="button" class="close" data-dismiss="alert"><span aria-hidden="true">&times;</span><span class="sr-only">Close</span></button>
                                    Goal LA Dashboard fixation is in progress.
                                </div>
                                <div class="alert alert-warning alert-dismissible" role="alert">
                                    <button type="button" class="close" data-dismiss="alert"><span aria-hidden="true">&times;</span><span class="sr-only">Close</span></button>
                                    Goal LA Admin Panel is under development.
                                </div>
                                <div class="alert alert-danger alert-dismissible" role="alert">
                                    <button type="button" class="close" data-dismiss="alert"><span aria-hidden="true">&times;</span><span class="sr-only">Close</span></button>
                                    Goal LA Messaging System not available.
                                </div>
                            </div>
                        </div>
                    </div>
                </div>
                <div class="col-md-6">
                    <div class="templatemo-progress">
                        <div class="list-group">
                            <a href="#" class="list-group-item active">
                                <h4 class="list-group-item-heading">Goal LA</h4>
                                <p class="list-group-item-text">Some update</p>
                            </a>
                            <a href="#" class="list-group-item">
                                <h4 class="list-group-item-heading">Learning Analytics</h4>
                                <p class="list-group-item-text">Some random update</p>
                            </a>
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

    // Line chart
    var randomScalingFactor = function(){ return Math.round(Math.random()*100)};
    var lineChartData = {
        labels : ["January","February","March","April","May","June","July"],
        datasets : [
            {
                label: "My First dataset",
                fillColor : "rgba(220,220,220,0.2)",
                strokeColor : "rgba(220,220,220,1)",
                pointColor : "rgba(220,220,220,1)",
                pointStrokeColor : "#fff",
                pointHighlightFill : "#fff",
                pointHighlightStroke : "rgba(220,220,220,1)",
                data : [randomScalingFactor(),randomScalingFactor(),randomScalingFactor(),randomScalingFactor(),randomScalingFactor(),randomScalingFactor(),randomScalingFactor()]
            },
            {
                label: "My Second dataset",
                fillColor : "rgba(151,187,205,0.2)",
                strokeColor : "rgba(151,187,205,1)",
                pointColor : "rgba(151,187,205,1)",
                pointStrokeColor : "#fff",
                pointHighlightFill : "#fff",
                pointHighlightStroke : "rgba(151,187,205,1)",
                data : [randomScalingFactor(),randomScalingFactor(),randomScalingFactor(),randomScalingFactor(),randomScalingFactor(),randomScalingFactor(),randomScalingFactor()]
            }
        ]

    }

    window.onload = function(){
        var ctx_line = document.getElementById("templatemo-line-chart").getContext("2d");
        window.myLine = new Chart(ctx_line).Line(lineChartData, {
            responsive: true
        });
    };

    $('#myTab a').click(function (e) {
        e.preventDefault();
        $(this).tab('show');
    });

    $('#loading-example-btn').click(function () {
        var btn = $(this);
        btn.button('loading');
        // $.ajax(...).always(function () {
        //   btn.button('reset');
        // });
    });
</script>
</body>
</html>
<%
    }
%>