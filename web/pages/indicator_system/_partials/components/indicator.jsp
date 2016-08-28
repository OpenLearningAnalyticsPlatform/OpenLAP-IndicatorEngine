<div id="indicatorDefinition" class="col s12 m6 card">
    <div class="panel panel-default">
        <div class="panel-heading light-blue darken-2">Indicator</div>
        <div class="panel-body">
            <ul class="collapsible" data-collapsible="expandable">
                <li>
                    <div class="light-blue lighten-5 collapsible-header active">Basic Information</div>
                    <div class="collapsible-body panel-body">
                        <table class="table table-striped">
                            <tbody>
                            <tr>
                                <div class="row">
                                    <div class="col-md-6 input-field">
                                        <form:input path="indicatorName" type="text" class="form-control" name ="indicatorNaming"
                                                    id="indicatorNaming"  required="required" placeholder="Type your Indicator Name"/>
                                        <label for="indicatorNaming"title="Indicator Name">Indicator Name </label>
                                    </div>
                                </div>
                            </tr>
                            <tr>
                                <div class="row">
                                    <div class="col-md-6">
                                        <label for="sourceSelection" title="Data Source for Indicator Definition">Source</label>
                                        <form:select multiple="true" class="form-control browser-default" title="Multiple sources can be selected i.e from where the data comes."
                                                     path="selectedSource" items="${selectNumberParameters.source}" name ="sourceSelection" id="sourceSelection" />
                                    </div>
                                </div>
                            </tr>
                            <tr>
                                <div class="row">
                                    <div class="col-md-6">
                                        <label for="PlatformSelection" title="Platform (web, mobile, etc) for the data">Platform</label>
                                        <form:select class="browser-default" title="Select a platform."
                                                     path="selectedPlatform" items="${selectNumberParameters.platform}" name ="PlatformSelection" id="PlatformSelection"/>
                                    </div>
                                </div>
                            </tr>
                            <tr>
                                <div class="row">
                                    <div class="col-md-6">
                                        <label for="actionSelection" title="Action related data to be populated">Action </label>
                                        <form:select class="browser-default" title="Please select an action to populate related Category/Minors."
                                                     path="selectedAction" items="${selectNumberParameters.action}" name ="actionSelection" id="actionSelection" onchange="populateCategories();"/>
                                    </div>
                                </div>
                            </tr>
                            <tr>
                                <div class="row">
                                    <div class="col-md-6">
                                        <div class="pull-left">
                                            <label for="selectedMinor" title="Minor of the selected action">Category &nbsp;</label>
                                        </div>
                                        <div style="padding-top: 10px">
                                            <div id="selectedMinorSpinner" class="preloader-wrapper small active">
                                                <div class="spinner-layer spinner-green-only">
                                                    <div class="circle-clipper left">
                                                        <div class="circle"></div>
                                                    </div>
                                                    <div class="gap-patch">
                                                        <div class="circle"></div>
                                                    </div>
                                                    <div class="circle-clipper right">
                                                        <div class="circle"></div>
                                                    </div>
                                                </div>
                                            </div>
                                        </div>
                                        <form:select class="browser-default" title="Please select a Minor to populate the relevant Attributes"
                                                     path="selectedMinor" items="${selectNumberParameters.minors}" name ="selectedMinor" id="selectedMinor" onchange="populateEntities();"/>
                                    </div>
                                </div>
                            </tr>
                            </tbody>
                        </table>
                    </div>
                </li>
                <li>
                    <div class="light-blue lighten-5 collapsible-header active">Filters</div>
                    <div class="collapsible-body panel-body">
                        <div class="row">
                            <div class="col s12">
                                <ul class="tabs">
                                    <li class="tab col s3"><a title="Attribute Filters" href="#attribute" class="active">Attribute</a></li>
                                    <li class="tab col s3"><a title="Session Filters"href="#session">Session</a></li>
                                    <li class="tab col s3"><a title="User and Time Filters" href="#userAndTime">User & Time</a></li>
                                </ul>
                            </div>
                            <div id="attribute" class="col s12">
                                <div class="row">
                                    <div class="col s12 m8 l6">
                                        <label id="appliedAttributeFiltersLabel" for="appliedAttributeFiltersDiv" title="Associated Attribute Filters to the Indicator">Applied Attribute Filters </label>
                                    </div>
                                    <div class="col s12 m12 l12">
                                        <div id="appliedAttributeFiltersDiv"  title="Associated Attribute Filters to the Indicator"></div>
                                    </div>
                                    <div class="col s12 m12 l12">
                                        <div class="divider"></div>
                                    </div>
                                </div>
                                <div class="row">
                                    <div class="col s12 m6 l6">
                                        <br/>
                                        <label for="entityKeySelection" title="Attribute (Entity)">Attribute </label>
                                        <form:select class="browser-default" path="selectedKeys" items="${selectNumberParameters.keys}" name ="entityKeySelection" id="entityKeySelection" title="Select Attribute"/>
                                        <br/>
                                        <div class="right-align">
                                            <button class="waves-effect waves-light btn light-blue" type="button" name="_eventId_searchAttributes" value="Search" onfocus="searchAttributes()" title="Search for Attribute" >Search</button>
                                        </div>
                                        <br/>
                                        <label for="multipleSelect" title="Attribute Values">Attribute Values </label>
                                        <form:select class="browser-default" path="evalue"  name="entityValue" id ="entityValue" title="Select from Attribute values">
                                            <form:options items="${selectNumberParameters.searchResults}" />
                                        </form:select>
                                        <%--<form:select multiple="true" class="form-control browser-default" title="Select from Attribute values"--%>
                                                     <%--path="evalue" items="${selectNumberParameters.searchResults}" name ="entityValue" id="entityValue"/>--%>
                                        <%--<label for="specificationType">Specification Type </label>--%>
                                        <%--<form:select class="browser-default" path="selectedentityValueTypes" items="${selectNumberParameters.entityValueTypes}" name ="specificationType" id="specificationType" title="Select Specification Type" />--%>
                                        <br/>
                                        <%--<div id="entity_filter_add_msg">--%>
                                        <%--</div>--%>
                                        <div class="right-align">
                                            <button class="waves-effect waves-light btn light-blue" type="button" name="attributebutton" id ="attributebutton"  value="Add" onclick="addEntity()" title="Apply Attribute Filter" >Apply</button>
                                        </div>
                                    </div>
                                </div>
                            </div>
                            <div id="session" class="col s12">
                                <div class="row">
                                    <div class="col s12 m8 l6">
                                        <label id="appliedSessionFiltersLabel" for="appliedSessionFiltersDiv" title="Associated Session Filters to the Indicator">Applied Session Filters </label>
                                    </div>
                                    <div class="col s12 m12 l12">
                                        <div id="appliedSessionFiltersDiv" title="Associated Session Filters to the Indicator"></div>
                                    </div>
                                    <div class="col s12 m12 l12">
                                        <div class="divider"></div>
                                    </div>
                                </div>
                                <div class="row">
                                    <div class="col s12 m6 l6">
                                        <br/>
                                        <label for="sessionSearchType" title="Session search type">Session Search Type </label>
                                        <form:select class="browser-default" path="selectedsessionSearchType" items="${selectNumberParameters.sessionSearchType}" name ="sessionSearchType" id="sessionSearchType" title="Select Session Type" />
                                        <label for="sessionSearchString" class="control-label" title="Search Keyword">Search Keyword</label>
                                        <input class="form-control" path="sessionSearch"  name="sessionSearchString" id ="sessionSearchString" placeholder="Keywords"/>
                                        <br/>
                                        <div class="right-align">
                                            <button class="waves-effect waves-light btn light-blue" type="button" name="_eventId_searchSession" value="Search" onclick="searchSession()" title="Search for Session" >Search</button>
                                        </div>
                                        <br/>
                                        <label for="multipleSelect" title="Session Attribute Values">Session Values </label>
                                        <form:select class="browser-default"  path="selectedUserString" name="multipleSelect" id="SessionsearchResults" title="Select from Search Results">
                                            <form:options items="${selectNumberParameters.searchResults}" />
                                        </form:select>
                                        <%--<label for="SessionsearchType">Session Search Type</label>--%>
                                        <%--<form:select class="browser-default" path="selectedSearchType" items="${selectNumberParameters.searchType}" name ="SessionsearchType" id="SessionsearchType" title="Select Search Type" />--%>
                                        <br/>
                                        <div class="right-align">
                                            <button class="waves-effect waves-light btn light-blue" type="button" name="_eventId_specifySession" value="Add" onclick="addSessionFilter()" title="Apply Session Filter">Apply</button>
                                        </div>
                                    </div>
                                </div>
                            </div>
                            <div id="userAndTime" class="col s12">
                                <div class="row">
                                    <div class="col s12 m8 l6">
                                        <label id="appliedUserTimeFiltersLabel" for="appliedUserTimeFiltersDiv" title="Associated User&Time Filters to the Indicator">Applied User&Time Filters </label>
                                    </div>
                                    <div class="col s12 m12 l12">
                                        <div id="appliedUserTimeFiltersDiv" title="Associated User&Time Filters to the Indicator"></div>
                                    </div>
                                    <div class="col s12 m12 l12">
                                        <div class="divider"></div>
                                    </div>
                                </div>
                                <div class="row">
                                    <div class="col s12 m6 l6">
                                        <br/>
                                        <br/>
                                        <input type="checkbox" id="isMyData"/>
                                        <label for="isMyData">My data only</label>
                                        <br/>
                                        <br/>
                                        <div class="col s12 m12 l12">
                                            <div class="divider"></div>
                                        </div>
                                        <br/>
                                        <br/>
                                        <label for="dateType">Date Type</label>
                                        <select class="browser-default" title="Please select a date type." name ="dateType" id="dateType">
                                            <option value="toDate">Start date</option>
                                            <option value="fromDate">End Date</option>
                                        </select>

                                        <label for="dateFilterVal">Date</label>
                                        <input type="date" class="datepicker" id="dateFilterVal" title="Enter Date">

                                        <br/>
                                        <div class="right-align">
                                            <button class="waves-effect waves-light btn light-blue" type="button" name="_eventId_specifyTime" onclick="addTimeFilter()" value="Add" title="Apply Timestamp Filter">Apply</button>
                                        </div>
                                    </div>
                                </div>
                            </div>
                        </div>
                    </div>
                </li>
                <li>
                    <div class="light-blue lighten-5 collapsible-header">Analytics Method</div>
                    <div class="collapsible-body panel-body">
                        <div class="row">
                            <div class="col s12 m6 l6">
                                <label for="analyticsMethod" title="Analytics Method">Method</label>
                                <select class="browser-default" title="Please select a method." name ="analyticsMethod" id="analyticsMethod"
                                        onchange="getAnalyticsMethodInputs();"></select>
                            </div>
                            <div class="col m6 l6">
                                <div class="select-desc hide-on-small-only">
                                    <span id="analyticsMethodDesc" title="Analytics method description"></span>
                                </div>
                            </div>
                        </div>
                        <div class="row">
                            <br/>
                            <div class="col s12 m6 l6">
                                <label title="Mappings between Data Columns and Method Inputs">Mappings </label>
                                <div class="divider"></div>
                            </div>
                            <br/>
                        </div>
                        <div class="row">
                            <div class="col s12 m6 l6">
                                <div class="col s6 m6 l6">
                                    <label for="methodDataColumns" title="Data Column Values">Data Columns</label>
                                    <select class="form-control browser-default" title="Select method data columns in here"
                                            name ="methodDataColumns" id="methodDataColumns" size="4">
                                    </select>
                                </div>
                                <div class="col s6 m6 l6">
                                    <label for="inputForMethods" title="Input values fro Methods">Input for Methods</label>
                                    <select class="form-control browser-default" title="Select method inputs in here"
                                            name ="inputForMethods" id="inputForMethods" size="4"></select>
                                </div>
                            </div>
                        </div>
                        <div class="row">
                            <div class="col s12 m6 l6 right-align">
                                <button class="btn waves-effect waves-light light-blue" type="button" title="Click to add method mapping."
                                        id="addMethodMapping" name="addMethodMapping" value="Add Mapping" onclick="addMethodMappingToTable();">Add
                                </button>
                            </div>
                        </div>
                        <div class="row">
                            <div class="col s12 m6 l6 right-align">
                                <table id="methodMappingTable" class="centered">
                                    <thead>
                                        <tr>
                                            <th data-field="id">Data Column</th>
                                            <th data-field="name">Input for Methods</th>
                                        </tr>
                                    </thead>
                                    <tbody>
                                    </tbody>
                                </table>
                            </div>
                        </div>
                    </div>
                </li>
                <li>
                    <div class="light-blue lighten-5 collapsible-header">Visualization</div>
                    <div class="collapsible-body panel-body">
                        <div class="row">
                            <div class="col s6 m6 l6">
                                <div class="row">
                                    <label for="EngineSelect" title="Graph library">Graph Library </label>
                                    <select class="browser-default" name ="EngineSelect" id="EngineSelect" title="Select Graph Library for Visualization" onchange="populateVisualizationMethods();">
                                    </select>
                                </div>
                                <div class="row">
                                    <label for="selectedChartType" title="Graph Type">Graph Type </label>
                                    <select class="browser-default" name ="selectedChartType" id="selectedChartType" title="Select Graph type for Visualization" onchange="getVisualizationMethodInputs();"></select>
                                </div>
                                <div class="row">
                                    <br/>
                                    <div class="col s12 m12 l12">
                                        <label title="Mappings between Method Outputs and Visualizer Inputs">Mappings </label>
                                        <div class="divider"></div>
                                    </div>
                                    <br/>
                                </div>
                                <div class="row">
                                    <div class="col s6 m6 l6">
                                        <label for="outputForMethods" title="Output for Method columns">Output for Methods</label>
                                        <select class="form-control browser-default" title="You can select Method Outputs in here"
                                                name ="outputForMethods" id="outputForMethods" size="4">
                                        </select>
                                    </div>
                                    <div class="col s6 m6 l6">
                                        <label for="inputForVisualizer" title="Input for Visualizer">Input for Visualizer</label>
                                        <select class="form-control browser-default" title="You can select Visualizer Inputs in here"
                                                name ="inputForVisualizer" id="inputForVisualizer" size="4"></select>
                                        <br/>
                                        <div class="right-align">
                                            <button class="btn waves-effect waves-light light-blue" type="button" title="Click to add visulaization mapping."
                                                    id="addVisualizationMapping" name="addVisualizationMapping" value="Add Mapping" onclick="addVisualizationMappingToTable();">Add
                                            </button>
                                        </div>
                                    </div>
                                </div>
                                <div class="row">
                                    <div class="col s12 m12 l12">
                                        <div class="divider"></div>
                                    </div>
                                </div>
                                <div class="row">
                                    <div class="col s12 m12 l12 right-align">
                                        <table id="visualizerMappingTable" class="centered">
                                            <thead>
                                            <tr>
                                                <th data-field="id">Output for Methods</th>
                                                <th data-field="name">Input for Visualizer</th>
                                            </tr>
                                            </thead>
                                            <tbody>
                                            </tbody>
                                        </table>
                                    </div>
                                </div>
                                <div class="row">
                                    <div class="right-align">
                                            <button class="btn waves-effect waves-light light-blue" type="button" title="Click to generate Graph."
                                                    name="generateGraph" id="generateGraph" value="Generate Graph" onclick="getIndicatorPreviewVisualizationCode()">Preview
                                            </button>
                                    </div>
                                </div>
                            </div>
                            <div class="col s6 m6 l6">
                                <div id="graphLoaderSpinner" class="preloader-wrapper big active graphLoader">
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
                                <div id="chart_wrap" title="Graph Preview">
                                    <div id="preview_chart"></div>
                                </div>
                            </div>
                        </div>
                    </div>
                </li>
            </ul>
            <div class="col s12 m6 l10 right-align">
                <button class="btn waves-effect waves-light light-blue" type="button" title="Cancel the Indicator."
                        name="cancelIndicator" id="cancelIndicator" value="Cancel Indicator">Cancel
                </button>
                <button class="btn waves-effect waves-light light-blue" type="button" title="Save the Indicator."
                        id="saveIndicator" name="saveIndicator" value="Finalize Settings">Save
                </button>
            </div>
        </div>
    </div>
</div>
