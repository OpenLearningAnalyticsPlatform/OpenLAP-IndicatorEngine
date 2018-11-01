<%--
~ Open Platform Learning Analytics : Indicator Engine
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

    <%@ include file="../_partials/header.jsp" %>

    <title>Indicator Editor</title>
    <meta name="description" content="OpenLAP : Indicator Editor" />


    <script type="text/javascript" src="${pageContext.request.contextPath}/js/custom/view_existing.js"></script>
    <script type="text/javascript" src="${pageContext.request.contextPath}/js/google/loader.js"></script>

    <script type="text/javascript" src="${pageContext.request.contextPath}/js/d3/d3.v3.min.js"></script>
    <script type="text/javascript" src="${pageContext.request.contextPath}/js/d3/d3.layout.cloud.js"></script>
    <script type="text/javascript" src="${pageContext.request.contextPath}/js/d3/d3.chart.v0.2.1.min.js"></script>

    <script type="text/javascript" src="${pageContext.request.contextPath}/js/highcharts/highcharts.js"></script>
    <script type="text/javascript" src="${pageContext.request.contextPath}/js/highcharts/exporting.js"></script>

    <link type='text/css' href='${pageContext.request.contextPath}/js/c3/c3.min.css' rel='stylesheet'>
    <script type="text/javascript" src='${pageContext.request.contextPath}/js/c3/c3.min.js'></script>
    <script>
        google.charts.load("current", {packages:["corechart"]});
        google.charts.load("current", {packages:["treemap"]});
        google.charts.load("current", {packages:["timeline"]});
        google.charts.load("current", {packages:["sankey"]});
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
            <ul class="collapsible" data-collapsible="accordion">
                <li>
                    <div id="searchHead" class="light-blue darken-2 white-text collapsible-header active">Search</div>
                    <div class="collapsible-body panel-body">
                        <div>
                            <div class="">
                                <h5>Search Question</h5>
                                <div class="row">
                                    <div class="col-md-12">
                                        <div id="loadQuestionTemplateModelTable">
                                            <table id="questionData" class="display" cellspacing="0" width="100%">
                                                <thead>
                                                <tr>
                                                    <th>ID</th>
                                                    <th>Name</th>
                                                    <th>Associated Indicators</th>
                                                </tr>
                                                </thead>
                                            </table>
                                        </div>
                                        <br/>
                                    </div>
                                </div>
                            </div>
                            <div class="modal-footer">
                                <button id="visualizeQuestionbtn" class="modal-close waves-effect waves-light btn light-blue darken-2" disabled="disabled"
                                        title="Visualize the selected Question." onclick="visualizeQuestion()" >
                                    Visualize Question
                                </button>
                            </div>
                        </div>

                        <div>
                            <div class="">
                                <h5>Search Indicator</h5>
                                <div class="row">
                                    <div class="col-md-12">
                                        <div id="loadIndicatorTemplateModelTable">
                                            <table id="indicatorData" class="display" cellspacing="0" width="100%">
                                                <thead>
                                                <tr>
                                                    <th>ID</th>
                                                    <th>Name</th>
                                                </tr>
                                                </thead>
                                                <tbody>
                                                </tbody>
                                            </table>
                                        </div>
                                        <br/>
                                    </div>
                                </div>
                            </div>
                            <div class="modal-footer">
                                <button id="visualizeIndicatorbtn" class="modal-close waves-effect waves-light btn light-blue darken-2"  disabled="disabled"
                                        title="Visualize the selected Indicator." onclick="visualizeIndicator()" >
                                    Visualize Indicator
                                </button>
                            </div>
                        </div>

                    </div>
                </li>
                <li>
                    <div id="visualizationHead" class="light-blue darken-2 white-text collapsible-header">Visualization</div>
                    <div class="collapsible-body panel-body">
                        <div class="center-align red-text">
                            <p>Indicators that use personalized data will show your data here and not the data of user who created it. <br> All indicator request code provided here are personalized. Please do not put them where others can see them, e.g. L<sup>2</sup>P course dashboard.</p>
                        </div>
                        <%--<div id="visualizeHead" class="col-md-12 center-align"></div>--%>
                        <div id="visualizeQuestionContent" class="col-md-12"></div>
                    </div>
                </li>
            </ul>
        </div>

    </div>
</div>

<%@ include file="../_partials/footer.jsp" %>
