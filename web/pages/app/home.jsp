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
    <title>Goal Oriented LA Toolkit Dashboard</title>
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
        <div class="logo"><h1>Goal Oriented LA Toolkit</h1></div>
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
                <li><a href="/toolkit/admin">Toolkit Admin</a></li>
                <li><a href="/home/dashboard">Dashboard</a></li>
                <li><a href="/welcome">Home</a></li>
            </ol>
            <h1>Goal Oriented LA</h1>
            <p>This is the Dashboard. In Progress.</p>

            <div class="margin-bottom-30">
                <div class="row">
                    <div class="col-md-12">
                        <ul class="nav nav-pills">
                            <li class="active"><a href="/welcome">Home<span class="badge"></span></a></li>
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
            <div class="templatemo-panels">
                <div class="row">
                    <div class="col-md-6 col-sm-6 margin-bottom-30">
                        <div class="panel panel-success">
                            <div class="panel-heading">Data Visualization</div>
                            <canvas id="templatemo-line-chart" height="120" width="500"></canvas>
                        </div>
                        <span class="btn btn-success"><a href="data-visualization.html">More Charts</a></span>
                    </div>
                    <div class="col-md-6 col-sm-6 margin-bottom-30">
                        <div class="panel panel-primary">
                            <div class="panel-heading">User Table</div>
                            <div class="panel-body">
                                <table class="table table-striped">
                                    <thead>
                                    <tr>
                                        <th>#</th>
                                        <th>First Name</th>
                                        <th>Last Name</th>
                                        <th>Username</th>
                                    </tr>
                                    </thead>
                                    <tbody>
                                    <tr>
                                        <td>1</td>
                                        <td>John</td>
                                        <td>Smith</td>
                                        <td>@js</td>
                                    </tr>
                                    <tr>
                                        <td>2</td>
                                        <td>Bill</td>
                                        <td>Jones</td>
                                        <td>@bj</td>
                                    </tr>
                                    <tr>
                                        <td>3</td>
                                        <td>Marry</td>
                                        <td>James</td>
                                        <td>@mj</td>
                                    </tr>
                                    </tbody>
                                </table>
                            </div>
                        </div>
                        <span class="btn btn-primary"><a href="tables.html">See Tables</a></span>
                    </div>
                </div>
                <div class="row">
                    <div class="col-md-6 col-sm-6">
                        <!-- Nav tabs -->
                        <ul class="nav nav-tabs" role="tablist" id="templatemo-tabs">
                            <li class="active"><a href="#home" role="tab" data-toggle="tab">Home</a></li>
                            <li><a href="#profile" role="tab" data-toggle="tab">Profile</a></li>
                            <li><a href="#messages" role="tab" data-toggle="tab">Messages</a></li>
                            <li><a href="#settings" role="tab" data-toggle="tab">Settings</a></li>
                        </ul>

                        <!-- Tab panes -->
                        <div class="tab-content">
                            <div class="tab-pane fade in active" id="home">
                                <ul class="list-group">
                                    <li class="list-group-item"><input type="checkbox" name="" value=""> Suspendisse dapibus sodales</li>
                                    <li class="list-group-item"><input type="checkbox" name="" value=""> Proin mattis ex vitae</li>
                                    <li class="list-group-item"><input type="checkbox" name="" value=""> Aenean euismod dui vel</li>
                                    <li class="list-group-item"><input type="checkbox" name="" value=""> Vivamus dictum posuere odio</li>
                                    <li class="list-group-item"><input type="checkbox" name="" value=""> Morbi convallis sed nisi suscipit</li>
                                </ul>
                            </div>
                            <div class="tab-pane fade" id="profile">
                                <ul class="list-group">
                                    <li class="list-group-item">
                                        <span class="badge">33</span>
                                        Vivamus dictum posuere odio
                                    </li>
                                    <li class="list-group-item">
                                        <span class="badge">9</span>
                                        Dapibus ac facilisis in
                                    </li>
                                    <li class="list-group-item">
                                        <span class="badge">0</span>
                                        Morbi convallis sed nisi suscipit
                                    </li>
                                    <li class="list-group-item">
                                        <span class="badge">14</span>
                                        Cras justo odio
                                    </li>
                                    <li class="list-group-item">
                                        <span class="badge">2</span>
                                        Vestibulum at eros
                                    </li>
                                </ul>
                            </div>
                            <div class="tab-pane fade" id="messages">
                                <div class="list-group">
                                    <a href="#" class="list-group-item active">
                                        Morbi convallis sed nisi suscipit
                                    </a>
                                    <a href="#" class="list-group-item">Dapibus ac facilisis in</a>
                                    <a href="#" class="list-group-item">Morbi leo risus</a>
                                    <a href="#" class="list-group-item">Porta ac consectetur ac</a>
                                    <a href="#" class="list-group-item">Vestibulum at eros</a>
                                </div>
                            </div>
                            <div class="tab-pane fade" id="settings">
                                <div class="list-group">
                                    <a href="#" class="list-group-item disabled">
                                        Vivamus dictum posuere odio
                                    </a>
                                    <a href="#" class="list-group-item">Porta ac consectetur ac</a>
                                    <a href="#" class="list-group-item">Vestibulum at eros</a>
                                    <a href="#" class="list-group-item">Dapibus ac facilisis in</a>
                                    <a href="#" class="list-group-item">Morbi leo risus</a>
                                </div>
                            </div>
                        </div> <!-- tab-content -->
                    </div>
                    <div class="col-md-6 col-sm-6">
                        <div class="panel-group" id="accordion">
                            <div class="panel panel-default">
                                <div class="panel-heading">
                                    <h4 class="panel-title">
                                        <a data-toggle="collapse" data-parent="#accordion" href="#collapseOne">
                                            Accordion Item 1
                                        </a>
                                    </h4>
                                </div>
                                <div id="collapseOne" class="panel-collapse collapse in">
                                    <div class="panel-body">
                                        Anim pariatur cliche reprehenderit, enim eiusmod high life accusamus terry richardson ad squid. 3 wolf moon officia aute, non cupidatat skateboard dolor brunch. Food truck quinoa nesciunt laborum eiusmod. Brunch 3 wolf moon tempor, sunt aliqua put a bird on it squid single-origin coffee nulla assumenda shoreditch et. Nihil anim keffiyeh helvetica, craft beer labore wes anderson cred nesciunt sapiente ea proident. Ad vegan excepteur butcher vice lomo. Leggings occaecat craft beer farm-to-table, raw denim aesthetic synth nesciunt you probably haven't heard of them accusamus labore sustainable VHS.
                                    </div>
                                </div>
                            </div>
                            <div class="panel panel-default">
                                <div class="panel-heading">
                                    <h4 class="panel-title">
                                        <a data-toggle="collapse" data-parent="#accordion" href="#collapseTwo">
                                            Accordion Item 2
                                        </a>
                                    </h4>
                                </div>
                                <div id="collapseTwo" class="panel-collapse collapse">
                                    <div class="panel-body">
                                        Anim pariatur cliche reprehenderit, enim eiusmod high life accusamus terry richardson ad squid. 3 wolf moon officia aute, non cupidatat skateboard dolor brunch. Food truck quinoa nesciunt laborum eiusmod. Brunch 3 wolf moon tempor, sunt aliqua put a bird on it squid single-origin coffee nulla assumenda shoreditch et. Nihil anim keffiyeh helvetica, craft beer labore wes anderson cred nesciunt sapiente ea proident. Ad vegan excepteur butcher vice lomo. Leggings occaecat craft beer farm-to-table, raw denim aesthetic synth nesciunt you probably haven't heard of them accusamus labore sustainable VHS.
                                    </div>
                                </div>
                            </div>
                            <div class="panel panel-default">
                                <div class="panel-heading">
                                    <h4 class="panel-title">
                                        <a data-toggle="collapse" data-parent="#accordion" href="#collapseThree">
                                            Accordion Item 3
                                        </a>
                                    </h4>
                                </div>
                                <div id="collapseThree" class="panel-collapse collapse">
                                    <div class="panel-body">
                                        <button type="button" id="loading-example-btn" data-loading-text="Loading..." class="btn btn-primary">
                                            Click here
                                        </button> to load.
                                    </div>
                                </div>
                            </div>
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