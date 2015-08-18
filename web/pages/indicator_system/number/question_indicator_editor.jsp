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

            <div id="indViewDialog" title="Selected Indicator Property">
                <div id="accordionIndProp">
                    <h3>Basic Properties</h3>
                    <div>
                        <p>
                        <div id="indBasicProperty"></div>
                        </p>
                    </div>
                    <h3>Entity Filters</h3>
                    <div>
                        <p>
                            <div class="row">
                            <div class="col-md-12">

                                <h2 >Listing of All Attribute Filters <br><br></h2>
                                <table width="70%" style="border: 3px;background: rgb(243, 244, 248);"><tr><td>
                                    <table id="indEntityFilters" class="display" cellspacing="0" width="100%">
                                        <thead>
                                        <tr>
                                            <th>Filter Key</th>
                                            <th>Search Keyword</th>
                                            <th>Search Pattern</th>
                                        </tr>
                                        </thead>
                                    </table>
                                </td></tr></table>

                            </div>
                        </div>
                        </p>
                    </div>
                    <h3>User Filters</h3>
                    <div>
                        <p>
                            <h2 >Listing of All User Filters <br><br></h2>
                            <table width="70%" style="border: 3px;background: rgb(243, 244, 248);"><tr><td>
                                <table id="indUserFilters" class="display" cellspacing="0" width="100%">
                                    <thead>
                                    <tr>
                                        <th>User Search Type</th>
                                        <th>User Search Pattern</th>
                                        <th>User Value</th>
                                    </tr>
                                    </thead>
                                </table>
                            </td></tr></table>
                        </p>
                    </div>
                    <h3>Session Filters</h3>
                    <div>
                        <p>
                            <h2 >Listing of All Session Filters <br><br></h2>
                            <table width="70%" style="border: 3px;background: rgb(243, 244, 248);"><tr><td>
                                <table id="indSessionFilters" class="display" cellspacing="0" width="100%">
                                    <thead>
                                    <tr>
                                        <th>Session Value</th>
                                        <th>Search Pattern</th>
                                    </tr>
                                    </thead>
                                </table>
                            </td></tr></table>
                        </p>
                    </div>
                    <h3>Time Filters</h3>
                    <div>
                        <p>
                            <h2 >Listing of All Time Filters <br><br></h2>
                            <table width="70%" style="border: 3px;background: rgb(243, 244, 248);"><tr><td>
                                <table id="indTimeFilters" class="display" cellspacing="0" width="100%">
                                    <thead>
                                    <tr>
                                        <th>Timestamp</th>
                                        <th>Search Pattern</th>
                                    </tr>
                                    </thead>
                                </table>
                            </td></tr></table>
                        </p>
                    </div>
                </div>
            </div>
            <div id="indDeleteDialog" >
            </div>
            <ul class="nav nav-tabs" role="tablist" id="qiEditorTab">
                <li class="active"><a href="#QuestionIndicatorEditor" role="tab" data-toggle="tab">Editor</a></li>
                <li><a href="#TemplateLoad" role="tab" data-toggle="tab">Template Load</a></li>
                <li><a href="#QuestionRun" role="tab" data-toggle="tab">Question Visualization</a></li>
            </ul>
            <div class="tab-content">
                <div class="tab-pane fade in active" id="QuestionIndicatorEditor">
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
                                                <form:input path="questionsContainer.questionName" type="text" class="form-control" name ="questionNaming"
                                                            id="questionNaming" onchange="validateQuestionName()" required="required"
                                                            placeholder="Type your Question Name" title="Question Name msut be unique & must be more than 3 characters."/>
                                            </div>
                                        </div>
                                    </tr>
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
                                                <label for="operationSelection">Select Indicator Type (Operation) </label>
                                                <form:select multiple="false" class="form-control margin-bottom-15" title="You can select an Operation type for the Indicator."
                                                             path="selectedOperation" items="${selectNumberParameters.operations}" name ="operationSelection" id="operationSelection" />
                                            </div>
                                        </div>
                                    </tr>
                                    <tr>
                                        <div class="row templatemo-form-buttons">
                                            <div class="col-md-12">
                                                <label for="summaryOperations">Operations</label>
                                                <br/>
                                                <button  type="button" name="indicatorTemplate" title="Load Existing Indicator as a Template"
                                                         value="Load Template" onclick="loadIndfromDB()">
                                                    <img src="${pageContext.request.contextPath}/images/template.png" alt="button" width="48" height="48"/>
                                                </button>
                                                <button  type="button" name="newIndicator" title="Click to Start a new Indicator Definition Process." value="Add New Indicator" onclick="addNewIndicator()" >
                                                    <img src="${pageContext.request.contextPath}/images/new.png" alt="button" width="48" height="48"/>
                                                </button>
                                                <button  type="button" name="RunQuestion" title="Click to run the Question & all its indicators."
                                                         value="Run Question" onclick="QuestionVisualize()">
                                                    <img src="${pageContext.request.contextPath}/images/run.png" alt="button" width="48" height="48"/>
                                                </button >
                                                <button  type="button" name="QuestionSave" title="Click to save the Question & all its indicators."
                                                         value="Save Question" onclick="SaveQuestionDB()">
                                                    <img src="${pageContext.request.contextPath}/images/save.png" alt="button" width="48" height="48"/>
                                                </button>
                                                <button  type="button" name="helpQuestionInfo" id ="helpQuestionInfo" title="Click to know the icon & Process details"
                                                         value="Help" onclick="displayQnInfoIcons()" >
                                                    <img src="${pageContext.request.contextPath}/images/help.png" alt="button" width="48" height="48"/>
                                                </button>
                                            </div>
                                        </div>
                                    </tr>
                                    <tr>
                                        <br/>
                                        <div class="alert alert-danger alert-dismissible" role="alert">
                                            <button type="button" class="close" data-dismiss="alert"><span aria-hidden="true">&times;</span><span class="sr-only">Close</span></button>
                                            Indicator Definition has already started. Please do not click to start another process. <br/>
                                            Please complete this Indicator and if you require another Indicator then proceed by clicking on
                                            <img src="${pageContext.request.contextPath}/images/new.png" alt="button" width="48" height="48"/>.
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
                                    <tr>
                                        <div class="row">
                                            <div class="col-md-6 margin-bottom-15">
                                                <label for="qNamefromBean">Question Name</label>
                                                <input type="text" disabled class="form-control margin-bottom-15"  title="Name of the Question which is being defined now."
                                                       name ="qNamefromBean" id="qNamefromBean" onfocus="this.selectedIndex = -1;"/>
                                            </div>
                                        </div>
                                    </tr>
                                    <tr>
                                        <div class="row">
                                            <div class="col-md-6 margin-bottom-15">
                                                <label for="associatedIndicators">Associated Indicators</label>
                                                <select class="form-control margin-bottom-15"  title="List of Indicators already defined for this Question"
                                                        name ="associatedIndicators" id="associatedIndicators" onfocus="this.selectedIndex = -1;"/>
                                            </div>
                                        </div>
                                    </tr>
                                    <tr>
                                        <div class="row templatemo-form-buttons">
                                            <div class="col-md-12">
                                                <label for="summaryOperations">Operations</label>
                                                <br/>
                                                <button  type="button" id="summaryOperations" title="Refresh Question Summary"
                                                         value="Refresh" onclick="refreshQuestionSummary()">
                                                    <img  src="${pageContext.request.contextPath}/images/refresh.png" alt="button" width="48" height="48"/>
                                                </button>
                                                <button  type="button" id="indView" title="View Summary of the selected Indicator" value="View">
                                                    <img  src="${pageContext.request.contextPath}/images/view.png" alt="button" width="48" height="48"/>
                                                </button>
                                                <button  type="button" id="indLoad" title="Load the Selected Indicator in Editor" value="Load">
                                                    <img  src="${pageContext.request.contextPath}/images/load.png" alt="button" width="48" height="48"/>
                                                </button>
                                                <button  type="button" id="indDelete" title="Delete the Selected Indicator" value="Delete">
                                                    <img  src="${pageContext.request.contextPath}/images/delete.png" alt="button" width="48" height="48"/>
                                                </button>
                                                <button  type="button" name="helpQuestionSummary" onclick="displayQnSummaryIcons()" id ="helpQuestionSummary" title="Click to know the icon & Process details"
                                                         value="Help" >
                                                    <img src="${pageContext.request.contextPath}/images/help.png" alt="button" width="48" height="48"/>
                                                </button>
                                            </div>
                                        </div>
                                    </tr>
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
                                    <tr>
                                        <div class="alert alert-danger alert-dismissible" role="alert">
                                            <button type="button" class="close" data-dismiss="alert"><span aria-hidden="true">&times;</span><span class="sr-only">Close</span></button>
                                            Do not Forget to add at least <strong> one Filter </strong> in the Indicator Property window to successfully complete the Definition process.
                                        </div>
                                    </tr>
                                    <tr>
                                        <div class="row templatemo-form-buttons">
                                            <div class="col-md-12">
                                                <label for="summaryOperations">Operations</label>
                                                <br/>
                                                <button  type="button" name="indicatorMemorySave" title="Click to generate a graph with current selections"
                                                         value="Refresh Graph" onclick="refreshGraph()" >
                                                    <img src="${pageContext.request.contextPath}/images/refresh_graph.png" alt="button" width="48" height="48"/>
                                                </button>
                                                <button  type="button" title="Click to save the current Indicator."
                                                         name="graphGeneration" value="Finalize Settings" onclick="finalizeIndicator()" >
                                                    <img src="${pageContext.request.contextPath}/images/finalize.png" alt="button" width="48" height="48"/>
                                                </button>
                                                <button  type="button" name="helpIndicatorInfo" onclick="displayIndInfoIcons()" id ="helpIndicatorInfo" title="Click to know the icon & Process details"
                                                         value="Help" >
                                                    <img src="${pageContext.request.contextPath}/images/help.png" alt="button" width="48" height="48"/>
                                                </button>
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
                                        <div id="accordionFilter">
                                            <h3>Attribute Filters</h3>
                                            <div>
                                                <p>
                                                    <label for="entityKeySelection">Select an Attribute </label>
                                                        <form:select class="form-control margin-bottom-15" path="selectedKeys" items="${selectNumberParameters.keys}" name ="entityKeySelection" id="entityKeySelection" />
                                                    <label for="specificationType">Select Specification Type </label>
                                                        <form:select class="form-control margin-bottom-15" path="selectedentityValueTypes" items="${selectNumberParameters.entityValueTypes}" name ="specificationType" id="specificationType" />
                                                    <label for="entityValue" class="control-label">Search Keyword</label>
                                                        <form:input class="form-control" path="evalue"  name="entityValue" id ="entityValue"/>
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
                                            <h3>User Filters</h3>
                                            <div>
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
                                            <h3>Session Filters</h3>
                                            <div>
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
                                            <h3>Time Filters</h3>
                                            <div>
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
                                        </div>
                                    </div>
                                    <div class="tab-pane fade " id="FilterSummary">
                                        <div id="accordionFilterSummary">
                                            <h3>Attribute Filters Summary</h3>
                                            <div>
                                                <p>
                                                <div class="panel-heading">Applied Filters</div>
                                                <div id="entity_filters">
                                                </div>
                                                <br/>
                                                <form:select class="form-control margin-bottom-15" path="selectedKeys" name ="entityFilterListing" id="entityFilterListing" />
                                                <div class="row templatemo-form-buttons">
                                                    <div class="col-md-12">
                                                        <button   type="button" id="refreshEntity" value="Refresh" onclick="refreshEntityFilters()">
                                                            <img src="${pageContext.request.contextPath}/images/refresh.png" alt="button" width="48" height="48"/>
                                                        </button >
                                                        <button   type="button" id ="deleteEntities" value="Delete"  onclick="deleteEntity()">
                                                            <img src="${pageContext.request.contextPath}/images/delete.png" alt="button" width="48" height="48"/>
                                                        </button >
                                                    </div>
                                                </div>
                                                <br/>
                                                </p>
                                            </div>
                                            <h3>User Filters Summary</h3>
                                            <div>
                                                <p>
                                                <div class="panel-heading">Applied Filters</div>
                                                <div id="user_filters">
                                                </div>
                                                <br/>
                                                <form:select class="form-control margin-bottom-15" path="selectedKeys" name ="userFilterListing" id="userFilterListing" />
                                                <div class="row templatemo-form-buttons">
                                                    <div class="col-md-12">
                                                        <button   type="button" id="refreshUserSettings" value="Refresh" onclick="refreshUserFilters()">
                                                            <img src="${pageContext.request.contextPath}/images/refresh.png" alt="button" width="48" height="48"/>
                                                        </button >
                                                        <button   type="button" id ="deleteUserSettings" value="Delete"  onclick="deleteUserFilters()">
                                                            <img src="${pageContext.request.contextPath}/images/delete.png" alt="button" width="48" height="48"/>
                                                        </button >
                                                    </div>
                                                </div>
                                                <br/>
                                                </p>
                                            </div>
                                            <h3>Session Filters Summary</h3>
                                            <div>
                                                <p>
                                                <div class="panel-heading">Applied Filters</div>
                                                <div id="session_filters">
                                                </div>
                                                <br/>
                                                <form:select class="form-control margin-bottom-15" path="selectedKeys" name ="sessionFilterListing" id="sessionFilterListing" />
                                                <div class="row templatemo-form-buttons">
                                                    <div class="col-md-12">
                                                        <button  type="button" id="refreshSessionSettings" value="Refresh" onclick="refreshSessionFilters()">
                                                            <img src="${pageContext.request.contextPath}/images/refresh.png" alt="button" width="48" height="48"/>
                                                        </button >
                                                        <button   type="button" id ="deleteSessionSettings" value="Delete"  onclick="deleteSessionFilters()">
                                                            <img src="${pageContext.request.contextPath}/images/delete.png" alt="button" width="48" height="48"/>
                                                        </button >
                                                    </div>
                                                </div>
                                                <br/>
                                                </p>
                                            </div>
                                            <h3>Time Filters Summary</h3>
                                            <div>
                                                <p>
                                                <div class="panel-heading">Applied Filters</div>
                                                <div id="time_filters">
                                                </div>
                                                <br/>
                                                <form:select class="form-control margin-bottom-15" path="selectedKeys" name ="timeFilterListing" id="timeFilterListing" />
                                                <div class="row templatemo-form-buttons">
                                                    <div class="col-md-12">
                                                        <button  type="button" id="refreshTimeSettings" value="Refresh" onclick="refreshTimeFilters()">
                                                            <img src="${pageContext.request.contextPath}/images/refresh.png" alt="button" width="48" height="48"/>
                                                        </button >
                                                        <button   type="button" id ="deleteTimeSettings" value="Delete"  onclick="deleteTimeFilters()">
                                                            <img src="${pageContext.request.contextPath}/images/delete.png" alt="button" width="48" height="48"/>
                                                        </button >
                                                    </div>
                                                </div>
                                                <br/>
                                                </p>
                                            </div>
                                        </div>
                                    </div>
                                    <div class="tab-pane fade" id="indicatorSummary">
                                        <div id="accordionIndicatorSummary">
                                            <h3>Basic Information</h3>
                                            <div>
                                                <p>
                                                <div id="current_ind_basic_info">
                                                </div>
                                                <br/>
                                                <div class="row templatemo-form-buttons">
                                                    <div class="col-md-12">
                                                        <button   type="button" id="refreshIndInfo" value="Refresh" onclick="refreshCurrentIndicator()">
                                                            <img src="${pageContext.request.contextPath}/images/refresh.png" alt="button" width="48" height="48"/>
                                                        </button >
                                                    </div>
                                                </div>
                                                <br/>
                                                </p>
                                            </div>
                                            <h3>Filters At a Glance</h3>
                                            <div>
                                                <p>
                                                <div id="filters_at_a_glance">
                                                </div>
                                                </p>
                                            </div>
                                            <h3>Hibernate Query</h3>
                                            <div>
                                                <p>
                                                <div id="currentIndHQL">
                                                </div>
                                                </p>
                                            </div>
                                        </div>
                                    </div>
                                    <div class="tab-pane fade" id="graphs">
                                        <div id="accordionGraphSettings">
                                            <h3>Graph Settings</h3>
                                            <div>
                                                <p>
                                                    <label for="selectedChartType">Select Graph Type </label>
                                                    <form:select class="form-control margin-bottom-15" path="selectedChartType"
                                                                 items="${selectNumberParameters.chartTypes}" name ="selectedChartType" id="selectedChartType" />
                                                </p>
                                            </div>
                                            <h3>Graph Engine Settings</h3>
                                            <div>
                                                <p>
                                                    <label for="EngineSelect">Select Graph Engine </label>
                                                    <form:select class="form-control margin-bottom-15" path="selectedChartEngine"
                                                                 items="${selectNumberParameters.chartEngines}" name ="EngineSelect" id="EngineSelect" />
                                                </p>
                                            </div>
                                        </div>
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
                <div class="tab-pane fade" id="QuestionRun">
                    <h1>Question Visualization</h1>
                    <div class="table-responsive">
                        <h4> Associated Indicators Visualization</h4>
                        You can combine various Indicators to save as a new composite Indicator. You can define only one composite Indicator at one time.
                        Please select the indicators and also fill out other details like Name, graphing type etc. The New Composite Indicator will be available
                        in Memory and You can view that in "Question Summary" Properties window.
                        <div id ="runIndMem">
                        </div>
                        <div class="col-md-6 margin-bottom-15">
                            <input type="text" class="form-control" placeholder="Type the New Composite Indicator Name"
                                   title="Type the New Composite Indicator Name" name ="compositeIndName" id="compositeIndName" />
                            <br/>
                            <select class="form-control margin-bottom-15" id="compositeGraphType">
                                <option value="Pie">Pie</option>
                                <option value="Bar">Bar</option>
                            </select>
                            <br/>
                            <select class="form-control margin-bottom-15"  id="compositeGraphEngine">
                                <option value="JGraph">JGraph</option>
                            </select>
                            <br/>
                            <button  type="button" name="CompositeIndButton" value="Add" onclick="addCompositeIndicator()" >
                                <img src="${pageContext.request.contextPath}/images/apply.png" alt="button" width="48" height="48"/>
                            </button>
                        </div>

                    </div>
                </div>
                <div class="tab-pane fade" id="TemplateLoad">
                    <h1>Use an Existing Indicator as a template</h1>
                    <p>Search an Existing Indicator</p>
                    <div class="col-md-12">
                        <h2 >Listing of All Existing Non-Composite Indicators<br><br></h2>
                        <table width="70%" style="border: 3px;background: rgb(243, 244, 248);"><tr><td>
                            <table id="indicatorData" class="display" cellspacing="0" width="100%">
                                <thead>
                                <tr>
                                    <th>Indicator ID</th>
                                    <th>Indicator Name</th>
                                    <th>Short Name</th>
                                </tr>
                                </thead>
                            </table>
                        </td></tr></table>
                        <div class="col-md-6 margin-bottom-15">

                            <br/>
                            <label > Search Type</label>
                            <select class="form-control margin-bottom-15" id="searchIndType">
                                <option value="ID">ID</option>
                                <option value="IndicatorName">IndicatorName</option>
                            </select>
                            <br/>
                            <label > Search String</label>
                            <input type="text" class="form-control" placeholder="Type the Indicator Name/ID"
                                   title="Type the Indicator Name/ID" name ="IndSearch" id="IndSearch" />
                            <br/>
                            <button  type="button" name="CompositeIndButton" value="Add" onclick="searchIndicator()" >
                                <img src="${pageContext.request.contextPath}/images/search.png" alt="button" width="48" height="48"/>
                            </button>
                            <br/>
                            <label > Search Results</label>
                            <select class="form-control margin-bottom-15" id="searchResults">

                            </select>
                            <button  type="button" name="CompositeIndButton" title="Click to view the properties of the selected Indicator." value="Add" onclick="viewIndicatorProp()" >
                                <img src="${pageContext.request.contextPath}/images/view.png" alt="button" width="48" height="48"/>
                            </button>
                            <button  type="button" name="CompositeIndButton" title="Click to use the selected Indicator as a template." value="Add" onclick="loadFromTemplate()" >
                                <img src="${pageContext.request.contextPath}/images/load.png" alt="button" width="48" height="48"/>
                            </button>
                            <br/>
                            <div id="IndPropsFromDB">
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