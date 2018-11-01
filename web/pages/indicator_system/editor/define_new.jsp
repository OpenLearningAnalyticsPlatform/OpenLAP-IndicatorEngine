<%@ include file="../_partials/header.jsp" %>

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

<title>Indicator Editor</title>
    <meta name="description" content="OpenLAP : Indicator Editor" />

    <script type="text/javascript" src="${pageContext.request.contextPath}/js/indicator_editor.js"></script>
    <script type="text/javascript" src="${pageContext.request.contextPath}/js/custom/mapping.js"></script>
    <script type="text/javascript" src="${pageContext.request.contextPath}/js/custom/ui_tricks.js"></script>
    <script type="text/javascript" src="${pageContext.request.contextPath}/js/custom/validations.js"></script>
    <script type="text/javascript" src="${pageContext.request.contextPath}/js/custom/filters.js"></script>
    <script type="text/javascript" src="${pageContext.request.contextPath}/js/custom/comp_functions.js"></script>
    <script type="text/javascript" src="${pageContext.request.contextPath}/js/custom/mlai_functions.js"></script>
    <script type="text/javascript" src="${pageContext.request.contextPath}/js/google/loader.js"></script>

    <script type="text/javascript" src="${pageContext.request.contextPath}/js/d3/d3.v3.min.js"></script>
    <script type="text/javascript" src="${pageContext.request.contextPath}/js/d3/d3.layout.cloud.js"></script>
    <script type="text/javascript" src="${pageContext.request.contextPath}/js/d3/d3.chart.v0.2.1.min.js"></script>

    <script type="text/javascript" src="${pageContext.request.contextPath}/js/highcharts/highcharts.js"></script>
    <script type="text/javascript" src="${pageContext.request.contextPath}/js/highcharts/exporting.js"></script>

    <link type="text/css" href="${pageContext.request.contextPath}/js/c3/c3.min.css" rel="stylesheet">
    <script type="text/javascript" src="${pageContext.request.contextPath}/js/c3/c3.min.js"></script>
    <script>
        google.charts.load("current", {packages:["corechart"]});
        google.charts.load("current", {packages:["treemap"]});
        google.charts.load("current", {packages:["timeline"]});
        google.charts.load("current", {packages:["sankey"]});
    </script>

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

            var indicatorTable = $("#indicatorData").dataTable( {
                "bProcessing": true,
                "bServerSide": true,
                "sort": "position",
                "bLengthChange": false,
                "language": {
                    "searchPlaceholder": "Search Indicator  (press enter to perform search)",
                    "sSearch": ""
                },
                "dom": '<"pull-left"f>tip',
                //bStateSave variable you can use to save state on client cookies: set value "true"
                "bStateSave": false,
                //Default: Page display length
                "iDisplayLength": 8,
                //We will use below variable to track page number on server side(For more information visit: http://legacy.datatables.net/usage/options#iDisplayStart)
                "iDisplayStart": 0,
                //adding the delay of 1 second before sending the request to fetch new data while typing search criteria
                "searchDelay": 1000,
                "fnDrawCallback": function () {
                    //Get page numer on client. Please note: number start from 0 So
                    //for the first page you will see 0 second page 1 third page 2...
                    //Un-comment below alert to see page number
                    //alert("Current page number: "+this.fnPagingInfo().iPage);
                },
//                    "sAjaxSource": "/indicators/fetchExistingIndicatorsData.web",
                "sAjaxSource": "/engine/searchAllIndicators",
                "aoColumns": [
                    { "mData": "id" },
                    { "mData": "name" },
                    { "mData": "parameters", "visible": false },
                    { "mData": "indicatorType" },
                ]
            } );

            $('#indicatorData_filter input').unbind();
            $('#indicatorData_filter input').bind('keyup', function(e) {
                if(e.which == 13 || e.keyCode == 13) {
                    indicatorTable.fnFilter(this.value);
                }
            });


//            var questionTable = $("#questionData").dataTable( {
//                "bProcessing": true,
//                "bServerSide": true,
//                "sort": "position",
//                "bLengthChange": false,
//                "language": {
//                    "searchPlaceholder": "Search Question  (press enter to perform search)",
//                    "sSearch": ""
//                },
//                "dom": '<"pull-left"f><"pull-right"l>tip',
//                //bStateSave variable you can use to save state on client cookies: set value "true"
//                "bStateSave": false,
//                //Default: Page display length
//                "iDisplayLength": 8,
//                //We will use below variable to track page number on server side(For more information visit: http://legacy.datatables.net/usage/options#iDisplayStart)
//                "iDisplayStart": 0,
//                //adding the delay of 1 second before sending the request to fetch new data while typing search criteria
//                "searchDelay": 1000,
//                "fnDrawCallback": function () {
//                    //Get page numer on client. Please note: number start from 0 So
//                    //for the first page you will see 0 second page 1 third page 2...
//                    //Un-comment below alert to see page number
//                    //alert("Current page number: "+this.fnPagingInfo().iPage);
//                },
//                "sAjaxSource": "/engine/searchAllQuestions",
//                "aoColumns": [
//                    { "mData": "id" },
//                    { "mData": "name" },
//                    { "mData": "indicatorCount" },
//                ]
//            } );
//            $('#questionData_filter input').unbind();
//            $('#questionData_filter input').bind('keyup', function(e) {
//                if(e.which == 13 || e.keyCode == 13) {
//                    questionTable.fnFilter(this.value);
//                }
//            });

        } );
    })(jQuery);
</script>

</head>

<%@ include file="../_partials/nav_bar.jsp" %>

<div id="loading-screen" class="loading-overlay loader-hide">
    <div class="request-loader">
        <div class="preloader-wrapper big active">
            <div class="spinner-layer spinner-blue-only">
                <div class="circle-clipper left">
                    <div class="circle"></div>
                </div><div class="gap-patch">
                <div class="circle"></div>
            </div><div class="circle-clipper right">
                <div class="circle"></div>
            </div>
            </div>
        </div>
    </div>
</div>

<div class="templatemo-content-wrapper">
    <div class="templatemo-content">

        <%--<ol class="breadcrumb">--%>
            <%--<li><a class="materialize-breadcrumb" href="/home/dashboard">Dashboard</a></li>--%>
            <%--<li><a class="materialize-breadcrumb" href="/indicators/home">Indicator Home</a></li>--%>
        <%--</ol>--%>


        <%--<h5>Indicator Editor</h5>--%>
        <input type="hidden" name="userName" id="userName" value="${sessionScope.userName}" />
        <input type="hidden" name="rid" id="rid" value="${sessionScope.rid}" />

        <!--FORM for Indicator Editor-->
        <div class="tab-content">

            <form:form role="form" id="GQSelectionForm" method="POST" modelAttribute="selectNumberParameters" action="${flowExecutionUrl}">
                <div id="warnings" class="preview-err-msg">
                    <span></span>
                </div>
                <%@ include file="../_partials/components/goal.jsp" %>
                <%@ include file="../_partials/components/question.jsp" %>
            </form:form>

            <form:form role="form" id="SimpleForm" method="POST" modelAttribute="selectNumberParameters" action="${flowExecutionUrl}">
                <%@ include file="../_partials/components/indicator.jsp" %>
            </form:form>

            <form:form role="form" id="CompForm" method="POST">
                <%@ include file="../_partials/components/composite_indicator.jsp" %>
            </form:form>

            <%--MLAI interface--%>
            <form:form role="form" id="MLAIForm" method="POST" modelAttribute="selectNumberParameters" action="${flowExecutionUrl}">
                <%@ include file="../_partials/components/mlai_indicator.jsp" %>
            </form:form>

        </div>

    </div>
</div>


<%@ include file="../_partials/modals/load_indicator_template.jsp" %>

<%--<%@ include file="../_partials/modals/load_question_template.jsp" %>--%>

<%@ include file="../_partials/modals/visualize_question.jsp" %>

<%@ include file="../_partials/modals/save_question.jsp" %>

<%@ include file="../_partials/modals/confirm_entity_delete.jsp" %>

<%--<%@ include file="../_partials/modals/confirm_user_delete.jsp" %>--%>

<%@ include file="../_partials/modals/confirm_time_delete.jsp" %>

<%@ include file="../_partials/modals/confirm_indicator_delete.jsp" %>

<%@ include file="../_partials/modals/confirm_dataset_delete.jsp" %>

<%@ include file="../_partials/modals/request_goal.jsp" %>

<%@ include file="../_partials/help/simple_indicator.jsp" %>

<%@ include file="../_partials/help/composite_indicator.jsp" %>

<%@ include file="../_partials/help/mlai_indicator_help.jsp" %>

<%@ include file="../_partials/help/question_help.jsp" %>

<%@ include file="../_partials/help/goal_help.jsp" %>

<%@ include file="../_partials/help/editor_help.jsp" %>
<%--Modal for MLAI--%>
<%@ include file="../_partials/modals/add_first_method.jsp" %>

<%@ include file="../_partials/footer.jsp" %>