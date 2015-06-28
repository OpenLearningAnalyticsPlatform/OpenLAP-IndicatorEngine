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
            <h3>Attribute Settings</h3>
            <div>
                <p>
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
                <button  type="button" name="attributebutton" id ="attributebutton"  value="Add" onclick="addEntity()" >
                    <img src="${pageContext.request.contextPath}/images/apply.png" alt="button" width="48" height="48"/>
                </button>
                <br>
                <div id="entity_filter_add_msg">
                </div>
                <br/>
                </p>
            </div>
            <h3>User Settings</h3>
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
            <h3>Session Settings</h3>
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
            <h3>Date & Time Settings</h3>
            <div>
                <p>
                    <label for="timeSearchType">TimeStamp Search Type </label>
                    <form:select class="form-control margin-bottom-15" path="selectedTimeSearchType" items="${selectNumberParameters.timeSearchType}" name ="timeSearchType" id="timeSearchType" />
                    <label for="searchString" class="control-label">Search Keyword</label>
                    <input class="form-control" path="timeSearch"  name="searchString" id ="searchString"/>
                    <br/>
                    <button  type="button" name="_eventId_searchTime"value="Search">
                        <img src="${pageContext.request.contextPath}/images/search.png" alt="button" width="48" height="48"/>
                    </button>
                    <br/>
                    <label for="multipleSelect">Search Results </label>
                    <form:select size="2" class="form-control" path="selectedSearchStrings" name="multipleSelect">
                        <form:options items="${selectNumberParameters.searchResults}" />
                    </form:select>
                    <label for="timeSelectionType">TimeStamp Search Type</label>
                    <form:select class="form-control margin-bottom-15" path="selectedTimeType" items="${selectNumberParameters.timeType}" name ="timeSelectionType" id="timeSelectionType" />
                    <button  type="button" name="_eventId_specifyTime" value="Add">
                        <img src="${pageContext.request.contextPath}/images/apply.png" alt="button" width="48" height="48"/>
                    </button>
                    <button  value="Delete All">
                        <img src="${pageContext.request.contextPath}/images/delete.png" alt="button" width="48" height="48"/>
                    </button >
                </p>
            </div>
        </div>
    </div>
    <div class="tab-pane fade in active" id="FilterSummary">
        <div id="accordionFilterSummary">
            <h3>Attributes Summary</h3>
            <div>
                <p>
                <div class="panel-heading">Applied Filters</div>
                <div id="entity_filters">
                </div>
                <br/>
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
            <h3>User Filter Summary</h3>
            <div>
                <p>
                <div class="panel-heading">Applied Filters</div>
                <div id="user_filters">
                </div>
                <br/>
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
            <h3>Session Filter Summary</h3>
            <div>
                <p>
                <div class="panel-heading">Applied Filters</div>
                <div id="session_filters">
                </div>
                <br/>
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
        </div>
    </div>
    <div class="tab-pane fade in active" id="indicatorSummary">
        <div id="accordionIndicatorSummary">
            <h3>Basic Information</h3>
            <div>
                <p>
                    will be posted here
                </p>
            </div>
            <h3>Filters At a Glance</h3>
            <div>
                <p>
                    will be posted here
                </p>
            </div>
            <h3>Hibernate Query</h3>
            <div>
                <p>
                    will be posted here
                </p>
            </div>
        </div>
    </div>
    <div class="tab-pane fade in active" id="graphs">
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