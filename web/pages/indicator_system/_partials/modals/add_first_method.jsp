<div id="addFirstMethodModal" class="modal modal-fixed-footer">
    <div class="modal-content">
        <ul class="collapsible" data-collapsible="expandable">
            <li>
                <div class="light-blue lighten-5 collapsible-header active">Dataset</div>
                <div class="collapsible-body panel-body">
                    <table class="table table-striped">
                        <tbody>
                        <tr>
                            <div class="row">
                                <div class="col-md-6">
                                    <label for="mlai_sourceSelection" title="Data Source for Indicator Definition">Source</label>
                                    <select multiple="true" size="2" class="form-control browser-default" name ="mlai_sourceSelection"
                                                 id="mlai_sourceSelection"/>
                                </div>
                            </div>
                        </tr>
                        <tr>
                            <div class="row">
                                <div class="col-md-6">
                                    <label for="mlai_PlatformSelection" title="Platform (web, mobile, etc) for the data">Platform</label>
                                    <select multiple="true" size="2" class="form-control browser-default"
                                                 name ="mlai_PlatformSelection" id="mlai_PlatformSelection"/>
                                </div>
                            </div>
                        </tr>
                        <tr>
                            <div class="row">
                                <div class="col-md-6">
                                    <label for="mlai_actionSelection" title="Action related data to be populated">Action </label>
                                    <select multiple="true" size="4" class="form-control browser-default"
                                                name ="mlai_actionSelection" id="mlai_actionSelection" onchange="populateCategories();"/>
                                </div>
                            </div>
                        </tr>
                        <tr>
                            <div class="row">
                                <div class="col-md-6">
                                    <div id="mlai_selectedMinorSpinner" class="preloader-wrapper small active" style="left:90px;">
                                        <div class="spinner-layer spinner-blue-only">
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
                                    <label for="mlai_selectedMinor" title="Minor of the selected action">Category &nbsp;</label>
                                    <select multiple="true" size="6" class="form-control browser-default" title="Please select a Minor to populate the relevant Attributes"
                                                 name ="mlai_selectedMinor" id="mlai_selectedMinor" onchange="populateEntities();"/>
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
                                <%--<li class="tab col s3"><a title="Session Filters"href="#session">Session</a></li>--%>
                                <%--<li class="tab col s3"><a title="User and Time Filters" href="#userAndTime">User & Time</a></li>--%>
                                <li class="tab col s3"><a title="Time Filters" href="#userAndTime">Time</a></li>
                            </ul>
                        </div>
                        <div class="col s12 m12 l12">
                            <div class="divider"></div>
                        </div>
                        <div id="mlai_attribute" class="col s12">
                            <div class="row" id="mlai_appliedAttributeFiltersPanel">
                                <div class="col s12 m8 l6">
                                    <label id="mlai_appliedAttributeFiltersLabel" for="mlai_appliedAttributeFiltersDiv" title="Associated Attribute Filters to the Indicator">Applied Attribute Filters</label>
                                </div>
                                <div class="col s12 m12 l12">
                                    <div id="mlai_appliedAttributeFiltersDiv"  title="Associated Attribute Filters to the Indicator"></div>
                                </div>
                                <div class="col s12 m12 l12">
                                    <div class="divider"></div>
                                </div>
                            </div>
                            <div class="row">
                                <div class="col s12 m6 l6">
                                    <br/>
                                    <label for="mlai_entityKeySelection" title="Attribute (Entity)">Attribute </label>
                                    <select class="browser-default" name ="mlai_entityKeySelection" id="mlai_entityKeySelection" title="Select Attribute"/>
                                    <br/>
                                    <div class="right-align">
                                        <button class="waves-effect waves-light btn light-blue" type="button" name="_eventId_searchAttributes" value="Search" onfocus="searchAttributes()" title="Search for Attribute" >Search</button>
                                    </div>
                                    <br/>
                                    <div id="mlai_entityValueSpinner" class="preloader-wrapper small active" style="left:190px;">
                                        <div class="spinner-layer spinner-blue-only">
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
                                    <label for="mlai_entityValue" title="Attribute Values">Attribute Values </label>
                                    <select class="browser-default" size="6" multiple="true" name="mlai_entityValue" id ="mlai_entityValue" title="Select from Attribute values">
                                    </select>
                                    <%--<form:select multiple="true" class="form-control browser-default" title="Select from Attribute values"--%>
                                    <%--path="evalue" items="${selectNumberParameters.searchResults}" name ="entityValue" id="mlai_entityValue"/>--%>
                                    <%--<label for="specificationType">Specification Type </label>--%>
                                    <%--<form:select class="browser-default" path="selectedentityValueTypes" items="${selectNumberParameters.entityValueTypes}" name ="specificationType" id="mlai_specificationType" title="Select Specification Type" />--%>
                                    <br/>
                                    <%--<div id="mlai_entity_filter_add_msg">--%>
                                    <%--</div>--%>
                                    <div class="right-align">
                                        <button class="waves-effect waves-light btn light-blue" type="button" name="attributebutton" id="mlai_attributebutton"  value="Add" onclick="addEntity()" title="Apply Attribute Filter" >Apply</button>
                                    </div>
                                </div>
                            </div>
                        </div>
                        <%--<div id="mlai_session" class="col s12">
                            <div class="row" id="mlai_appliedSessionFiltersPanel">
                                <div class="col s12 m8 l6">
                                    <label id="mlai_appliedSessionFiltersLabel" for="appliedSessionFiltersDiv" title="Associated Session Filters to the Indicator">Applied Session Filters </label>
                                </div>
                                <div class="col s12 m12 l12">
                                    <div id="mlai_appliedSessionFiltersDiv" title="Associated Session Filters to the Indicator"></div>
                                </div>
                                <div class="col s12 m12 l12">
                                    <div class="divider"></div>
                                </div>
                            </div>
                            <div class="row">
                                <div class="col s12 m6 l6">
                                    <br/>
                                    <label for="sessionSearchType" title="Session search type">Session Search Type </label>
                                    <form:select class="browser-default" path="selectedsessionSearchType" items="${selectNumberParameters.sessionSearchType}" name ="sessionSearchType" id="mlai_sessionSearchType" title="Select Session Type" />
                                    <label for="sessionSearchString" class="control-label" title="Search Keyword">Search Keyword</label>
                                    <input class="form-control" path="sessionSearch"  name="sessionSearchString" id ="sessionSearchString" placeholder="Keywords"/>
                                    <br/>
                                    <div class="right-align">
                                        <button class="waves-effect waves-light btn light-blue" type="button" name="_eventId_searchSession" value="Search" onclick="searchSession()" title="Search for Session" >Search</button>
                                    </div>
                                    <br/>
                                    <label for="multipleSelect" title="Session Attribute Values">Session Values </label>
                                    <form:select class="browser-default"  path="selectedUserString" name="multipleSelect" id="mlai_SessionsearchResults" title="Select from Search Results">
                                        <form:options items="${selectNumberParameters.searchResults}" />
                                    </form:select>
                                    &lt;%&ndash;<label for="SessionsearchType">Session Search Type</label>&ndash;%&gt;
                                    &lt;%&ndash;<form:select class="browser-default" path="selectedSearchType" items="${selectNumberParameters.searchType}" name ="SessionsearchType" id="mlai_SessionsearchType" title="Select Search Type" />&ndash;%&gt;
                                    <br/>
                                    <div class="right-align">
                                        <button class="waves-effect waves-light btn light-blue" type="button" name="_eventId_specifySession" value="Add" onclick="addSessionFilter()" title="Apply Session Filter">Apply</button>
                                    </div>
                                </div>
                            </div>
                        </div>--%>
                        <div id="mlai_userAndTime" class="col s12">
                            <div class="row" id="mlai_appliedUserTimeFiltersPanel">
                                <div class="col s12 m8 l6">
                                    <label id="mlai_appliedUserTimeFiltersLabel" for="mlai_appliedUserTimeFiltersDiv" title="Associated User&Time Filters to the Indicator">Applied Time Filters </label>
                                </div>
                                <div class="col s12 m12 l12">
                                    <div id="mlai_appliedUserTimeFiltersDiv" title="Applied Time Filters to the Indicator"></div>
                                </div>
                                <div class="col s12 m12 l12">
                                    <div class="divider"></div>
                                </div>
                            </div>
                            <div class="row">
                                <div class="col s12 m6 l6">
                                    <%--<br/>
                                    <br/>
                                    <input type="checkbox" id="mlai_isMyData"/>
                                    <label for="isMyData">My data only</label>
                                    <br/>
                                    <br/>
                                    <div class="col s12 m12 l12">
                                        <div class="divider"></div>
                                    </div>
                                    <br/>
                                    <br/>--%>
                                    <label for="mlai_dateType">Date Type</label>
                                    <select class="browser-default" title="Please select a date type." name ="dateType" id="mlai_dateType">
                                        <option value="fromDate">Start date</option>
                                        <option value="toDate">End Date</option>
                                    </select>

                                    <label for="mlai_dateFilterVal">Date</label>
                                    <input type="date" class="datepicker" id="mlai_dateFilterVal" title="Enter Date">

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
                <div class="light-blue lighten-5 collapsible-header">Analysis</div>
                <div class="collapsible-body panel-body">
                    <div class="row">
                        <div class="col s12 m6 l6">
                            <label for="mlai_analyticsMethod" title="Analytics Method">Method</label>
                            <select class="browser-default" title="Please select a method." name ="analyticsMethod" id="mlai_analyticsMethod"
                                    onchange="getAnalyticsMethodInputs();"></select>
                        </div>
                        <div class="col m6 l6">
                            <div class="select-desc hide-on-small-only">
                                <span id="mlai_analyticsMethodDesc" title="Analytics method description"></span>
                            </div>
                        </div>
                    </div>
                    <div class="row">
                        <br/>
                        <div class="col s12 m6 l6">
                            <div id="mlai_inputForMethodsSpinner" class="preloader-wrapper small active" style="left:120px;">
                                <div class="spinner-layer spinner-blue-only">
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
                            <label title="Mappings between Data Columns and Method Inputs">Mappings </label>
                            <div class="divider"></div>
                        </div>
                        <br/>
                    </div>
                    <div class="row">
                        <div class="col s12 m6 l6">
                            <div class="col s6 m6 l6">
                                <label for="mlai_methodDataColumns" title="Data Column Values">Data Columns</label>
                                <select class="form-control browser-default" size="6" title="Select method data columns in here"
                                        name ="methodDataColumns" id="mlai_methodDataColumns" size="4">
                                </select>
                            </div>
                            <div class="col s6 m6 l6">
                                <label for="mlai_inputForMethods" title="Input values fro Methods">Input for Methods</label>
                                <select class="form-control browser-default" size="6"
                                        name ="inputForMethods" id="mlai_inputForMethods" size="4"></select>
                            </div>
                        </div>
                    </div>
                    <div class="row">
                        <div class="col s12 m6 l6 right-align">
                            <button class="btn waves-effect waves-light light-blue" type="button"
                                    id="mlai_addMethodMapping" name="addMethodMapping" value="Add Mapping" onclick="addMethodMappingToTable();">Add
                            </button>
                        </div>
                    </div>
                    <div class="row">
                        <div class="col s12 m6 l6 right-align">
                            <table id="mlai_methodMappingTable" class="centered">
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
        </ul>
    </div>
    <div class="modal-footer">
        <button class="modal-action waves-effect waves-light btn light-blue darken-2" onclick="saveFirstMethod();">Save</button>
        <button class="modal-action modal-close waves-effect waves-light btn light-blue darken-2 cancelFilterDelete">Cancel</button>
    </div>
</div>
