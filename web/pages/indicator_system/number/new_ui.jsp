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
  Date: 16-03-2015
  Time: 04:33
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
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
    <title>Indicator Definition Home</title>
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <meta name="description" content="Goal Oriented LA ToolKit : Indicator Definition" />
    <meta name="author" content="Tanmaya Mahapatra" />
    <script type="text/javascript" src="${pageContext.request.contextPath}/js/jquery/dist/jquery.js"></script>
    <script type="text/javascript" src="${pageContext.request.contextPath}/js/indicator_definition.js"></script>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/templatemo_main.css">
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/error.css">
</head>
<body>
<div class="navbar navbar-inverse" role="navigation">
    <div class="navbar-header">
        <div class="logo"><h1>Indicator Definition</h1></div>
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
                    <i class="fa fa-database"></i> Indicator System <div class="pull-right"><span class="caret"></span></div>
                </a>
                <ul class="templatemo-submenu">
                    <li><a href="/indicators/indicators_definition"><i class="fa fa-file"></i><span class="badge pull-right"></span>Define New</a></li>
                    <li><a href="/indicators/viewall"><i class="fa fa-th-large"></i><span class="badge pull-right"></span>View Existing</a></li>
                    <li><a href="/indicators/modify"><i class="fa fa-edit"></i><span class="badge pull-right"></span>Modify Existing</a></li>
                    <li><a href="/indicators/delete"><i class="fa fa-trash-o"></i><span class="badge pull-right"></span>Delete a Specific Indicator</a></li>
                    <li><a href="/indicators/trialrun"><i class="fa fa-play"></i><span class="badge pull-right"></span>Trial Run</a></li>
                </ul>
            </li>

            <li><a href="javascript:;" data-toggle="modal" data-target="#confirmModal"><i class="fa fa-sign-out"></i>Sign Out</a></li>
        </ul>
    </div><!--/.navbar-collapse -->

    <div class="templatemo-content-wrapper">
        <div class="templatemo-content">
            <ol class="breadcrumb">
                <li><a href="/home/dashboard">Dashboard</a></li>
                <li><a href="/indicators/home">Indicator Home</a></li>
            </ol>
            <h1>Question with Indicator Definition</h1>
            <p>Information to be added</p>
            <form:form role="form" id="sessionSelection"  method="POST" modelAttribute="selectNumberParameters" action="${flowExecutionUrl}">
                <div class="col-md-12">
                    <div class="col-md-6 col-sm-6 margin-bottom-30">

                        <div class="panel panel-primary">
                            <div class="panel-heading">Question Information</div>
                                <div class="panel-body">
                                    <table class="table table-striped">
                                     <tbody>
                                        <tr>
                                            <div class="row">
                                                <div class="col-md-6 margin-bottom-15">
                                                    <label for="questionNaming">Enter Question Name </label>
                                                    <form:input path="questionsContainer.questionName" type="text" class="form-control" name ="questionNaming" id="questionNaming" onchange="validateQuestionName()" required="required" placeholder="Type your Question Name" />
                                                </div>
                                            </div>
                                        </tr>
                                        <tr>
                                            <div class="row">
                                                <div class="col-md-6 margin-bottom-15">
                                                        <label for="indicatorNaming">Indicator Name </label>
                                                        <form:input path="indicatorName" type="text" class="form-control" name ="indicatornaming" id="indicatornaming" onchange="validateIndicatorName()" required="required" placeholder="Type your Indicator Name"/>
                                                        <br/>
                                                        <button class="btn btn-primary">Delete Indicator</button>
                                                        <br/>
                                                        <button class="btn btn-primary">Add a New Indicator</button>
                                                </div>
                                            </div>
                                        </tr>
                                     </tbody>
                                    </table>
                            </div>
                        </div>
                    </div>
                    <div class="col-md-6 col-sm-6 margin-bottom-30">
                        <div class="panel panel-primary">
                            <div class="panel-heading">Question Summary</div>
                                <div class="panel-body">
                                    <table class="table table-striped">
                                        <tbody>
                                        </tbody>
                                    </table>
                                </div>
                            </div>
                    </div>
                </div>
                <div class="col-md-12">
                    <div class="col-md-6 col-sm-6 margin-bottom-30">
                        <div class="panel panel-primary">
                            <div class="panel-heading">Indicator Information</div>
                            <div class="panel-body">
                                <table class="table table-striped">
                                    <tbody>
                                    <tr>
                                        <div class="row">
                                            <div class="col-md-6 margin-bottom-15">
                                                <label for="sourceSelection">Select a Source </label>
                                                <form:select multiple="true" class="form-control margin-bottom-15" path="selectedSource" items="${selectNumberParameters.source}" name ="sourceSelection" id="sourceSelection" />
                                            </div>
                                        </div>
                                    </tr>

                                    <tr>
                                        <div class="row">
                                            <div class="col-md-6 margin-bottom-15">
                                                <label for="PlatformSelection">Select a Platform </label>
                                                <form:select class="form-control margin-bottom-15" path="selectedPlatform" items="${selectNumberParameters.platform}" name ="PlatformSelection" id="PlatformSelection" />
                                            </div>
                                        </div>
                                    </tr>
                                    <tr>
                                        <div class="row">
                                            <div class="col-md-6 margin-bottom-15">
                                                <label for="actionSelection">Select an Action </label>
                                                <form:select class="form-control margin-bottom-15" path="selectedAction" items="${selectNumberParameters.action}" name ="actionSelection" id="actionSelection"  onchange="populateCategories();" onfocus="this.selectedIndex = -1;"/>
                                            </div>
                                        </div>
                                    </tr>
                                    <tr>
                                        <div class="row">
                                            <div class="col-md-6 margin-bottom-15">
                                                <label for="actionSelection">Select Number of  </label>
                                                <form:select class="form-control margin-bottom-15" path="selectedMinor" items="${selectNumberParameters.minors}" name ="selectedMinor" id="selectedMinor" onchange="populateEntities();" onfocus="this.selectedIndex = -1;"/>
                                            </div>
                                        </div>
                                    </tr>
                                    </tbody>
                                </table>
                            </div>
                        </div>
                    </div>
                    <div class="col-md-6 col-sm-6 margin-bottom-30">
                        <div class="panel panel-primary">
                            <div class="panel-heading">Indicator Properties & Summary</div>
                            <div class="panel-body">
                            <!-- Nav tabs -->
                            <ul class="nav nav-tabs" role="tablist" id="templatemo-tabs">
                                <li class="active"><a href="#filters" role="tab" data-toggle="tab">Filters</a></li>
                                <li><a href="#FilterSummary" role="tab" data-toggle="tab">Filter Summary</a></li>
                                <li><a href="#graphs" role="tab" data-toggle="tab">Graph Options</a></li>
                                <li><a href="#indicatorSummary" role="tab" data-toggle="tab">Indicator Summary</a></li>
                                <li><a href="#graphGeneration" role="tab" data-toggle="tab">Graph Preview</a></li>
                            </ul>
                            <!-- Tab panes -->
                            <div class="tab-content">

                                <div class="tab-pane fade in active" id="filters">
                                    <ul class="list-group">
                                        <li class="list-group-item">
                                            <div class="panel panel-default">
                                                <div class="panel-heading">
                                                    <h4 class="panel-title">
                                                        <a data-toggle="collapse" data-parent="#accordion" href="#collapseOne">
                                                            Attribute Settings
                                                        </a>
                                                    </h4>
                                                </div>
                                                <div id="collapseOne" class="panel-collapse collapse">
                                                    <div class="panel-body">
                                                        <label for="entityKeySelection">Select an Entity </label>
                                                        <form:select class="form-control margin-bottom-15" path="selectedKeys" items="${selectNumberParameters.keys}" name ="entityKeySelection" id="entityKeySelection" />
                                                        <label for="specificationType">Select Specification Type </label>
                                                        <form:select class="form-control margin-bottom-15" path="selectedentityValueTypes" items="${selectNumberParameters.entityValueTypes}" name ="specificationType" id="specificationType" />
                                                        <label for="entityValue" class="control-label">Filter Specification</label>
                                                        <form:input class="form-control" path="evalue"  name="entityValue" id ="entityValue"/>
                                                        <br/>
                                                        <div class="alert alert-success alert-dismissible" role="alert">
                                                             <button type="button" class="close" data-dismiss="alert"><span aria-hidden="true">&times;</span><span class="sr-only">Close</span></button>
                                                             <strong>Note !</strong> Please use 'ALL' to search all values.
                                                        </div>
                                                        <input class="btn btn-primary" type="button" name="addEntity" id ="addEntity"
                                                        value="Add" onclick="addEntity()" />
                                                        <br>
                                                        <div id="entity_filter_add_msg">
                                                        </div>
                                                        <br/>
                                                    </div>
                                                </div>
                                            </div>
                                        </li>
                                        <li class="list-group-item">
                                            <div class="panel panel-default">
                                                <div class="panel-heading">
                                                    <h4 class="panel-title">
                                                        <a data-toggle="collapse" data-parent="#accordion" href="#collapseTwo">
                                                            User Settings
                                                        </a>
                                                    </h4>
                                                </div>
                                                <div id="collapseTwo" class="panel-collapse collapse">
                                                    <div class="panel-body">
                                                        <label for="userType">User Type </label>
                                                        <form:select class="form-control margin-bottom-15" path="selecteduserSearchTypes" items="${selectNumberParameters.userSearchTypes}" name ="userType" id="userType" />
                                                        <label for="searchUserString" class="control-label">Search Keyword</label>
                                                        <form:input class="form-control" path="searchUserString"  name="searchUserString" id ="searchUserString"/>
                                                        <br/>
                                                        <input type="button" class="btn btn-primary" name="_eventId_searchUser"
                                                               value="Search" onfocus="searchUser()"/>
                                                        <br/>
                                                        <label for="multipleSelect">Search Results </label>
                                                        <form:select class="form-control" path="selectedUserString" name="multipleSelect" id="usersearchResults">
                                                            <form:options items="${selectNumberParameters.searchResults}" />
                                                        </form:select>
                                                        <label for="UsersearchType">Search Type </label>
                                                        <form:select class="form-control margin-bottom-15" path="selectedSearchType" items="${selectNumberParameters.searchType}" name ="UsersearchType" id="UsersearchType" />
                                                        <input class="btn btn-primary" type="button" name="_eventId_specifyUser"
                                                               value="Add" onclick="addUserFilter()" />
                                                    </div>
                                                </div>
                                            </div>
                                        </li>
                                        <li class="list-group-item">
                                            <div class="panel panel-default">
                                                <div class="panel-heading">
                                                    <h4 class="panel-title">
                                                        <a data-toggle="collapse" data-parent="#accordion" href="#collapseThree">
                                                            Session Settings
                                                        </a>
                                                    </h4>
                                                </div>
                                                <div id="collapseThree" class="panel-collapse collapse">
                                                    <div class="panel-body">
                                                        <label for="sessionSearchType">Session Search Type </label>
                                                        <form:select class="form-control margin-bottom-15" path="selectedsessionSearchType" items="${selectNumberParameters.sessionSearchType}" name ="sessionSearchType" id="sessionSearchType" />
                                                        <label for="sessionSearchString" class="control-label">Search Keyword</label>
                                                        <input class="form-control" path="sessionSearch"  name="sessionSearchString" id ="sessionSearchString"/>
                                                        <br/>
                                                        <input type="button" class="btn btn-primary" name="_eventId_searchSession"
                                                               value="Search" onclick="searchSession()" />
                                                        <br/>
                                                        <label for="multipleSelect">Search Results </label>
                                                        <form:select class="form-control"  path="selectedUserString" name="multipleSelect" id="SessionsearchResults">
                                                            <form:options items="${selectNumberParameters.searchResults}" />
                                                        </form:select>
                                                        <label for="SessionsearchType">Session Search Type</label>
                                                        <form:select class="form-control margin-bottom-15" path="selectedSearchType" items="${selectNumberParameters.searchType}" name ="SessionsearchType" id="SessionsearchType" />
                                                        <input type="button" class="btn btn-primary" name="_eventId_specifySession"
                                                               value="Add"  onclick="addSessionFilter()" />
                                                    </div>
                                                </div>
                                            </div>
                                        </li>
                                        <li class="list-group-item">
                                            <div class="panel panel-default">
                                                <div class="panel-heading">
                                                    <h4 class="panel-title">
                                                        <a data-toggle="collapse" data-parent="#accordion" href="#collapseFour">
                                                            Date & Time Settings
                                                        </a>
                                                    </h4>
                                                </div>
                                                <div id="collapseFour" class="panel-collapse collapse">
                                                    <div class="panel-body">
                                                        <label for="timeSearchType">TimeStamp Search Type </label>
                                                        <form:select class="form-control margin-bottom-15" path="selectedTimeSearchType" items="${selectNumberParameters.timeSearchType}" name ="timeSearchType" id="timeSearchType" />
                                                        <label for="searchString" class="control-label">Search Keyword</label>
                                                        <input class="form-control" path="timeSearch"  name="searchString" id ="searchString"/>
                                                        <br/>
                                                        <input type="submit" class="btn btn-primary" name="_eventId_searchTime"
                                                               value="Search" />
                                                        <br/>
                                                        <label for="multipleSelect">Search Results </label>
                                                        <form:select size="2" class="form-control" path="selectedSearchStrings" name="multipleSelect">
                                                            <form:options items="${selectNumberParameters.searchResults}" />
                                                        </form:select>
                                                        <label for="timeSelectionType">TimeStamp Search Type</label>
                                                        <form:select class="form-control margin-bottom-15" path="selectedTimeType" items="${selectNumberParameters.timeType}" name ="timeSelectionType" id="timeSelectionType" />
                                                        <input  type="submit" class="btn btn-primary" name="_eventId_specifyTime"
                                                                value="Add" />
                                                        <input  type="submit" class="btn btn-primary"name="_eventId_clearTimeValues"
                                                                value="Delete All" />
                                                    </div>
                                                </div>
                                            </div>
                                        </li>
                                    </ul>
                                </div>
                                <div class="tab-pane fade" id="FilterSummary">
                                    <ul class="list-group">
                                        <li class="list-group-item">
                                            <div class="panel panel-default">
                                                <div class="panel-heading">
                                                    <h4 class="panel-title">
                                                        <a data-toggle="collapse" data-parent="#accordion" href="#collapseSix">
                                                            Attributes Summary
                                                        </a>
                                                    </h4>
                                                </div>
                                                <div id="collapseSix" class="panel-collapse collapse">
                                                    <div class="panel-heading">Applied Filters</div>
                                                    <div id="entity_filters">
                                                    </div>
                                                    <br/>
                                                    <input class="btn btn-primary" type="button" id="refreshEntity" value="Refresh" onclick="refreshEntityFilters()"/>
                                                    <input class="btn btn-primary" type="button" id ="deleteEntities" value="Delete"  onclick="deleteEntity()"/>
                                                    <br/>
                                                </div>
                                            </div>
                                        </li>
                                        <li class="list-group-item">
                                            <div class="panel panel-default">
                                                <div class="panel-heading">
                                                    <h4 class="panel-title">
                                                        <a data-toggle="collapse" data-parent="#accordion" href="#collapseSeven">
                                                            User Filter Summary
                                                        </a>
                                                    </h4>
                                                </div>
                                                <div id="collapseSeven" class="panel-collapse collapse">
                                                    <div class="panel-heading">Applied Filters</div>
                                                    <div id="user_filters">
                                                    </div>
                                                    <br/>
                                                    <input class="btn btn-primary" type="button" id="refreshUserSettings" value="Refresh" onclick="refreshUserFilters()"/>
                                                    <input class="btn btn-primary" type="button" id ="deleteUserSettings" value="Delete"  onclick="deleteUserFilters()"/>
                                                    <br/>
                                                </div>
                                            </div>
                                        </li>
                                        <li class="list-group-item">
                                            <div class="panel panel-default">
                                                <div class="panel-heading">
                                                    <h4 class="panel-title">
                                                        <a data-toggle="collapse" data-parent="#accordion" href="#collapseEight">
                                                            Session Filter Summary
                                                        </a>
                                                    </h4>
                                                </div>
                                                <div id="collapseEight" class="panel-collapse collapse">
                                                    <div class="panel-heading">Applied Filters</div>
                                                    <div id="session_filters">
                                                    </div>
                                                    <br/>
                                                    <input class="btn btn-primary" type="button" id="refreshSessionSettings" value="Refresh" onclick="refreshSessionFilters()"/>
                                                    <input class="btn btn-primary" type="button" id ="deleteSessionSettings" value="Delete"  onclick="deleteSessionFilters()"/>
                                                    <br/>
                                                </div>
                                            </div>
                                        </li>
                                    </ul>
                                </div>
                                <div class="tab-pane fade" id="indicatorSummary">
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
                                <div class="tab-pane fade" id="graphs">
                                    <ul class="list-group">
                                        <li class="list-group-item">
                                            <div class="panel panel-default">
                                                <div class="panel-heading">
                                                    <h4 class="panel-title">
                                                        <a data-toggle="collapse" data-parent="#accordion" href="#collapseFive">
                                                            Graph Settings
                                                        </a>
                                                    </h4>
                                                </div>
                                                <div id="collapseFive" class="panel-collapse collapse">
                                                    <div class="panel-body">
                                                        <label for="selectedChartType">Select Graph Type </label>
                                                        <form:select class="form-control margin-bottom-15" path="selectedChartType" items="${selectNumberParameters.chartTypes}" name ="selectedChartType" id="selectedChartType" />
                                                        <label for="EngineSelect">Select Graph Engine </label>
                                                        <form:select class="form-control margin-bottom-15" path="selectedChartEngine" items="${selectNumberParameters.chartEngines}" name ="EngineSelect" id="EngineSelect" />
                                                        <input class="btn btn-primary" type="button" name="_eventId_indicatorNameEntered"
                                                               value="Refresh Graph" onclick="refreshGraph()" />
                                                    </div>
                                                </div>
                                            </div>
                                        </li>
                                    </ul>
                                </div>
                                <div class="tab-pane fade" id="graphGeneration">
                                    <img src="/graphs/jgraph?default=true" id="graphImage"/>
                                </div>


                            </div> <!-- tab-content -->
                    </div>
                    </div>
                    </div>
                </div>
            </form:form>


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
<div class="modal fade" id="findCompany" tabindex="-1" role="dialog" aria-labelledby="exampleModalLabel" aria-hidden="true">
    <div class="modal-dialog">
        <div class="modal-content">

        </div>
    </div>
</div>
    <footer class="templatemo-footer">
        <div class="templatemo-copyright">
            <p>Copyright &copy; 2015 Learning Technologies Group, RWTH</p>
        </div>
    </footer>


<script src="${pageContext.request.contextPath}/js/jquery.min.js"></script>
<script src="${pageContext.request.contextPath}/js/bootstrap.min.js"></script>
<script src="${pageContext.request.contextPath}/js/Chart.min.js"></script>
<script src="${pageContext.request.contextPath}/js/templatemo_script.js"></script>
<script type="text/javascript">
</script>
    </div>
</body>
</html>
<%
    }
%>