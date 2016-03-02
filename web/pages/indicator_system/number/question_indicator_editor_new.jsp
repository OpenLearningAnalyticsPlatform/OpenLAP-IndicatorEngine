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
  Date: 28-06-2015
  Time: 00:09
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
    <title> Question Indicator Editor</title>
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <meta name="description" content="Goal Oriented LA ToolKit : Question Indicator Editor" />
    <meta name="author" content="Tanmaya Mahapatra" />
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/templatemo_main.css">
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/error.css">
    <link href="${pageContext.request.contextPath}/js/jquery-ui/jquery-ui.css" rel="stylesheet">
    <link href="${pageContext.request.contextPath}/css/indicator_editor.css" rel="stylesheet">
    <link href="${pageContext.request.contextPath}/css/animate.css" rel="stylesheet">
    <link href="//cdn.datatables.net/1.10.7/js/jquery.dataTables.min.js" rel="stylesheet">
    <script type="text/javascript" src="${pageContext.request.contextPath}/js/jquery/dist/jquery.js"></script>
    <script type="text/javascript" src="${pageContext.request.contextPath}/js/jquery-ui/jquery-ui.js"></script>
    <script type="text/javascript" src="${pageContext.request.contextPath}/js/noty-2.3.5/js/noty/jquery.noty.js"  ></script>
    <script type="text/javascript" src="${pageContext.request.contextPath}/js/noty-2.3.5/js/noty/layouts/bottomRight.js"></script>
    <script type="text/javascript" src="${pageContext.request.contextPath}/js/noty-2.3.5/js/noty/themes/relax.js"></script>
    <script type="text/javascript" src="//cdn.datatables.net/1.10.7/js/jquery.dataTables.js"></script>
    <script type="text/javascript" src="${pageContext.request.contextPath}/js/indicator_editor.js"></script>
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

                $("#indicatorData").dataTable( {
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
                    "sAjaxSource": "/indicators/fetchExistingIndicatorsData.web",
                    "aoColumns": [
                        { "mData": "id" },
                        { "mData": "indicator_name" },
                        { "mData": "short_name" },
                    ]
                } );

            } );
        })(jQuery);
    </script>

</head>
<body>
<div class="navbar navbar-inverse" role="navigation">
    <div class="navbar-header">
        <div class="logo"><h1>Question - Indicator Editor</h1></div>
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
            <input type="hidden" name="userName" id="userName" value="${sessionScope.userName}" />

            <!--FORM for Indicator Editor-->
            <div class="tab-content">
                <form:form role="form" id="sessionSelection"  method="POST" modelAttribute="selectNumberParameters" action="${flowExecutionUrl}">
                    <!--Select GOAL-->
                    <div class="col-md-12">
                        <div class="panel panel-primary">
                            <div class="panel-heading">Goal</div>
                            <div class="panel-body">
                                <table class="table table-striped">
                                    <tbody>
                                        <tr>
                                            <div class="row">
                                                <div class="col-md-6 margin-bottom-15">
                                                   <label for="PlatformSelection">Select Goal </label>
                                                    <form:select class="form-control margin-bottom-15" title="Please select a goal." path="selectedPlatform" name ="GoalSelection" id="GoalSelection" onfocus="this.selectedIndex = -1;">
                                                        <form:option value="Goal1" label="Goal1" />
                                                        <form:option value="Goal2" label="Goal2" />
                                                    </form:select>
                                                </div>
                                            </div>
                                        </tr>
                                    </tbody>
                                </table>
                            </div>
                        </div>
                    </div>
                    <!--Enter QUESTION-->
                    <div class="col-md-12">
                        <div class="panel panel-primary">
                            <div class="panel-heading">Question</div>
                            <div class="panel-body">
                                <table class="table table-striped">
                                    <tbody>
                                        <tr>
                                            <div class="row">
                                                <div class="col-md-6 margin-bottom-15">
                                                    <label for="questionNaming">Enter Question Name </label>
                                                    <form:input path="questionsContainer.questionName" type="text" class="form-control" name ="questionNaming"
                                                                id="questionNaming" onchange="validateQuestionName()" required="required"
                                                                placeholder="Type your Question Name" title="Question Name msut be unique & must be more than 3 characters."/>
                                                </div>
                                            </div>
                                        </tr>
                                        <tr>
                                            <div class="row">
                                                <div class="col-md-6 margin-bottom-15">
                                                    <label for="associatedIndicators">Associated Indicators </label>
                                                    <select class="form-control margin-bottom-15"  title="List of Indicators already defined for this Question"
                                                            name ="associatedIndicators" id="associatedIndicators" onfocus="this.selectedIndex = -1;"/>
                                                </div>
                                            </div>
                                        </tr>
                                    </tbody>
                                </table>
                            </div>
                        </div>
                    </div>
                    <!--Enter INDICATOR-->
                    <div class="col-md-12">
                        <div class="panel panel-primary">
                            <div class="panel-heading">Indicator</div>
                            <div class="panel-body">
                                <table class="table table-striped">
                                    <tbody>
                                        <tr>
                                            <div class="row">
                                                <div class="col-md-6 margin-bottom-15">
                                                    <label for="indicatorNaming">Indicator Name </label>
                                                    <form:input path="indicatorName" type="text" class="form-control" name ="indicatorNaming"
                                                                id="indicatorNaming" onchange="validateIndicatorName()" required="required"
                                                                placeholder="Type your Indicator Name" title="Indicator Name msut be unique & must be more than 3 characters."/>
                                                </div>
                                            </div>
                                        </tr>
                                        <tr>
                                            <div class="row">
                                                <div class="col-md-6 margin-bottom-15">
                                                    <label for="sourceSelection">Select a Source </label>
                                                    <form:select multiple="true" class="form-control margin-bottom-15" title="You can select multiple sourrces i.e from where the data comes."
                                                                 path="selectedSource" items="${selectNumberParameters.source}" name ="sourceSelection" id="sourceSelection" onchange="sourceChanged();" />
                                                </div>
                                            </div>
                                        </tr>
                                        <tr>
                                            <div class="row">
                                                <div class="col-md-6 margin-bottom-15">
                                                    <label for="PlatformSelection">Select a Platform </label>
                                                    <form:select class="form-control margin-bottom-15" title="You can select a single platform."
                                                                 path="selectedPlatform" items="${selectNumberParameters.platform}" name ="PlatformSelection" id="PlatformSelection" onchange="platformChanged();" onfocus="this.selectedIndex = -1;"/>
                                                </div>
                                            </div>
                                        </tr>
                                        <tr>
                                            <div class="row">
                                                <div class="col-md-6 margin-bottom-15">
                                                    <label for="actionSelection">Select an Action </label>
                                                    <form:select class="form-control margin-bottom-15" title="Please select an action to poulate the Available Minors."
                                                                 path="selectedAction" items="${selectNumberParameters.action}" name ="actionSelection" id="actionSelection"  onchange="populateCategories();" onfocus="this.selectedIndex = -1;"/>
                                                </div>
                                            </div>
                                        </tr>
                                        <tr>
                                            <div class="row">
                                                <div class="col-md-6 margin-bottom-15">
                                                    <label for="actionSelection">Select a Category (Minors) </label>
                                                    <form:select class="form-control margin-bottom-15" title="Please select a Minor to populate the relevant Attributes"
                                                                 path="selectedMinor" items="${selectNumberParameters.minors}" name ="selectedMinor" id="selectedMinor" onchange="populateEntities();" onfocus="this.selectedIndex = -1;"/>
                                                </div>
                                            </div>
                                        </tr>
                                    </tbody>
                                </table>
                            </div>
                        </div>
                    </div>
                    <!--Enter Filters-->
                    <div class="col-md-12">
                        <div class="panel panel-primary">
                            <div class="panel-heading">Filters</div>
                            <div class="panel-body">
                                <!-- Nav tabs -->
                                <ul class="nav nav-tabs" role="tablist" id="templatemo-tabs">
                                    <li class="active"><a href="#attribute" role="tab" data-toggle="tab">Attribute</a></li>
                                    <li><a href="#user" role="tab" data-toggle="tab">User</a></li>
                                    <li><a href="#session" role="tab" data-toggle="tab">Session</a></li>
                                    <li><a href="#time" role="tab" data-toggle="tab">Time</a></li>
                                </ul>
                                <!-- Tab panes -->
                                <div class="tab-content">

                                    <div class="tab-pane fade in active" id="attribute">
                                        <p>
                                            <label for="entityKeySelection">Select an Attribute </label>
                                                <form:select class="form-control margin-bottom-15" path="selectedKeys" items="${selectNumberParameters.keys}" name ="entityKeySelection" id="entityKeySelection" />
                                            <br/>
                                            <button  type="button" name="_eventId_searchAttributes" value="Search" onfocus="searchAttributes()" >
                                                <img src="${pageContext.request.contextPath}/images/search.png" alt="button" width="48" height="48"/>
                                            </button>
                                            <br/>
                                            <label for="multipleSelect">Search Results </label>
                                            <form:select class="form-control" path="evalue"  name="entityValue" id ="entityValue">
                                                <form:options items="${selectNumberParameters.searchResults}" />
                                            </form:select>
                                            <label for="specificationType">Select Specification Type </label>
                                                <form:select class="form-control margin-bottom-15" path="selectedentityValueTypes" items="${selectNumberParameters.entityValueTypes}" name ="specificationType" id="specificationType" />
                                            <br/>
                                            <div class="alert alert-success alert-dismissible" role="alert">
                                                <button type="button" class="close" data-dismiss="alert"><span aria-hidden="true">&times;</span><span class="sr-only">Close</span></button>
                                                <strong>Note !</strong> Please use 'ALL' to search all values.
                                            </div>
                                            <button  type="button" name="attributebutton" id ="attributebutton"  value="Add" onclick="addEntity()" >
                                                <img src="${pageContext.request.contextPath}/images/apply.png" alt="button" width="48" height="48"/>
                                            </button>
                                            <br>
                                            <div id="entity_filter_add_msg">
                                            </div>
                                            <br/>
                                        </p>
                                    </div>
                                    <div class="tab-pane fade" id="user">
                                        <p>
                                            <label for="userType">User Type </label>
                                            <form:select class="form-control margin-bottom-15" path="selecteduserSearchTypes" items="${selectNumberParameters.userSearchTypes}" name ="userType" id="userType" />
                                            <label for="searchUserString" class="control-label">Search Keyword</label>
                                            <form:input class="form-control" path="searchUserString"  name="searchUserString" id ="searchUserString"/>
                                            <br/>
                                            <button  type="button" name="_eventId_searchUser" value="Search" onfocus="searchUser()" >
                                                <img src="${pageContext.request.contextPath}/images/search.png" alt="button" width="48" height="48"/>
                                            </button>
                                            <br/>
                                            <label for="multipleSelect">Search Results </label>
                                            <form:select class="form-control" path="selectedUserString" name="multipleSelect" id="usersearchResults">
                                                <form:options items="${selectNumberParameters.searchResults}" />
                                            </form:select>
                                            <label for="UsersearchType">Search Type </label>
                                            <form:select class="form-control margin-bottom-15" path="selectedSearchType" items="${selectNumberParameters.searchType}" name ="UsersearchType" id="UsersearchType" />
                                            <button  type="button" name="_eventId_specifyUser" value="Add" onclick="addUserFilter()" >
                                                <img src="${pageContext.request.contextPath}/images/apply.png" alt="button" width="48" height="48"/>
                                            </button>
                                        </p>
                                    </div>
                                    <div class="tab-pane fade" id="session">
                                        <p>
                                            <label for="sessionSearchType">Session Search Type </label>
                                            <form:select class="form-control margin-bottom-15" path="selectedsessionSearchType" items="${selectNumberParameters.sessionSearchType}" name ="sessionSearchType" id="sessionSearchType" />
                                            <label for="sessionSearchString" class="control-label">Search Keyword</label>
                                            <input class="form-control" path="sessionSearch"  name="sessionSearchString" id ="sessionSearchString"/>
                                            <br/>
                                            <button  type="button" name="_eventId_searchSession" value="Search" onclick="searchSession()"  >
                                                <img src="${pageContext.request.contextPath}/images/search.png" alt="button" width="48" height="48"/>
                                            </button>
                                            <br/>
                                            <label for="multipleSelect">Search Results </label>
                                            <form:select class="form-control"  path="selectedUserString" name="multipleSelect" id="SessionsearchResults">
                                                <form:options items="${selectNumberParameters.searchResults}" />
                                            </form:select>
                                            <label for="SessionsearchType">Session Search Type</label>
                                            <form:select class="form-control margin-bottom-15" path="selectedSearchType" items="${selectNumberParameters.searchType}" name ="SessionsearchType" id="SessionsearchType" />
                                            <button  type="button" name="_eventId_specifySession" value="Add"  onclick="addSessionFilter()">
                                                <img src="${pageContext.request.contextPath}/images/apply.png" alt="button" width="48" height="48"/>
                                            </button>
                                        </p>
                                    </div>
                                    <div class="tab-pane fade" id="time">
                                        <p>
                                            <label for="timeSearchType">TimeStamp Search Type </label>
                                            <form:select class="form-control margin-bottom-15" path="selectedTimeSearchType" items="${selectNumberParameters.timeSearchType}" name ="timeSearchType" id="timeSearchType" />
                                            <label for="timeSearchString" class="control-label">Search Keyword</label>
                                            <input class="form-control" path="timeSearch"  name="searchString" id ="timeSearchString"/>
                                            <br/>
                                            <button  type="button" name="_eventId_searchTime" onclick="searchTime()" value="Search">
                                                <img src="${pageContext.request.contextPath}/images/search.png" alt="button" width="48" height="48"/>
                                            </button>
                                            <br/>
                                            <label for="multipleSelect">Search Results </label>
                                            <form:select class="form-control" path="selectedSearchStrings" id = "timeSearchResults" name="multipleSelect">
                                                <form:options items="${selectNumberParameters.searchResults}" />
                                            </form:select>
                                            <br/>
                                            From Date: <input type="text" id="fromDate">
                                            To Date: <input type="text" id="toDate">
                                            <label for="timeSelectionType">TimeStamp Search Type</label>
                                            <form:select class="form-control margin-bottom-15" path="selectedTimeType" items="${selectNumberParameters.timeType}" name ="timeSelectionType" id="timeSelectionType" />
                                            <button  type="button" name="_eventId_specifyTime" onclick="addTimeFilter()" value="Add">
                                                <img src="${pageContext.request.contextPath}/images/apply.png" alt="button" width="48" height="48"/>
                                            </button>
                                        </p>
                                    </div>
                                </div> <!-- tab-content -->
                            </div>
                        </div>
                    </div>

                    <!--Select Method-->
                    <div class="col-md-12">
                        <div class="panel panel-primary">
                            <div class="panel-heading">Method</div>
                            <div class="panel-body">
                                <table class="table table-striped">
                                    <tbody>
                                    <tr>
                                        <div class="row">
                                            <div class="col-md-6 margin-bottom-15">
                                                <label for="timeSelectionType">Select a Method</label>
                                                <form:select class="form-control margin-bottom-15" title="Please select a method." path="selectedPlatform" name ="MethodSelection" id="MethodSelection" onfocus="this.selectedIndex = -1;">
                                                    <form:option value="Method1" label="Method1" />
                                                    <form:option value="Method2" label="Method2" />
                                                </form:select>
                                            </div>
                                        </div>
                                    </tr>
                                    </tbody>
                                </table>
                            </div>
                        </div>
                    </div>

                    <!--Select Visualization-->
                    <div class="col-md-12">
                        <div class="panel panel-primary">
                            <div class="panel-heading">Visualization</div>
                            <div class="panel-body">
                                <table class="table table-striped">
                                    <tbody>
                                    <tr>
                                        <div class="row">
                                            <div class="col-md-6 margin-bottom-15">
                                                <label for="selectedChartType">Select Graph Type </label>
                                                <form:select class="form-control margin-bottom-15" path="selectedChartType"
                                                             items="${selectNumberParameters.chartTypes}" name ="selectedChartType" id="selectedChartType" />
                                            </div>
                                        </div>
                                    </tr>
                                    <tr>
                                        <div class="row">
                                            <div class="col-md-6 margin-bottom-15">
                                                <label for="EngineSelect">Select Graph Engine </label>
                                                <form:select class="form-control margin-bottom-15" path="selectedChartEngine"
                                                             items="${selectNumberParameters.chartEngines}" name ="EngineSelect" id="EngineSelect" />
                                            </div>
                                        </div>
                                    </tr>
                                    <tr>
                                        <div class="tab-pane fade" id="graphGeneration">
                                            <img src="/graphs/jgraph?default=true" id="graphImage"/>
                                        </div>
                                    </tr>
                                    </tbody>
                                </table>
                            </div>
                        </div>
                    </div>
                    <div class="col-md-12 margin-bottom-15">
                        <button class="btn-info btn pull-right" type="button" title="Click to save the current Indicator."
                                name="graphGeneration" value="Finalize Settings" onclick="finalizeIndicator()">Save
                        </button>
                    </div>
                </form:form>
            </div>

        </div>
    </div>

    <footer class="templatemo-footer">
        <div class="templatemo-copyright">
            <p>Copyright &copy; 2015 Learning Technologies Group, RWTH</p>
        </div>
    </footer>



    <script src="${pageContext.request.contextPath}/js/bootstrap.min.js"></script>
    <script src="${pageContext.request.contextPath}/js/templatemo_script.js"></script>
    <script type="text/javascript">
    </script>
</div>
</body>
</html>
<%
    }
%>