<%@ include file="../_partials/header.jsp" %>
    <div class="templatemo-content-wrapper">
        <div class="templatemo-content">
            <ol class="breadcrumb">
                <li><a href="/home/dashboard">Dashboard</a></li>
                <li><a href="/indicators/home">Indicator Home</a></li>
            </ol>
            <h5>Question with Indicator Definition</h5>
            <input type="hidden" name="userName" id="userName" value="${sessionScope.userName}" />

            <!--FORM for Indicator Editor-->
            <div class="tab-content">
                <form:form role="form" id="sessionSelection"  method="POST" modelAttribute="selectNumberParameters" action="${flowExecutionUrl}">
                    <!--Select GOAL-->
                    <div class="col s12 m6 card">
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
                    <div class="col s12 m6 card">
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
                                                <div class="col-md-6 margin-bottom-15">
                                                    <button class="btn-info btn" type="button" title="Click to add new current Indicator."
                                                            name="addIndicator" id="addIndicator" value="New Indicator" >Add Indicator
                                                    </button>
                                                </div>
                                            </div>
                                        </tr>
                                        <tr>
                                            <div class="row">
                                                <div class="col-md-6 margin-bottom-15">
                                                    <label for="associatedIndicators">Associated Indicators </label>
                                                    <%--<div id="associatedIndicators"></div>--%>
                                                    <div class="chip">
                                                        Tag
                                                        <i class="material-icons">close</i>
                                                    </div>
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
                    <div id="indicatorDefinition" class="col-md-12">
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
                    <div class="col s12 m6 l10 right-align">
                        <button class="btn waves-effect waves-light" type="button" title="Click to save the current Indicator."
                                name="graphGeneration" value="Finalize Settings" onclick="finalizeIndicator()">Save
                        </button>
                        <button class="btn waves-effect waves-light" type="button" title="Click to cancel the current Indicator."
                                name="cancelIndicator" id="cancelIndicator" value="Cancel Indicator">Cancel
                        </button>
                    </div>
                </form:form>
            </div>

        </div>
    </div>
<%@ include file="../_partials/footer.jsp" %>