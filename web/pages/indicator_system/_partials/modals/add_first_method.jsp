<div id="addFirstMethodModal" class="modal modal-fixed-footer">
    <input id="mlaids_datasourceId" type="hidden" />
    <div class="modal-content">
        <form:form role="form" id="MLAIDSForm" method="GET">
            <ul class="collapsible" data-collapsible="expandable">
            <li>
                <div class="light-blue lighten-5 collapsible-header active">Dataset</div>
                <div class="collapsible-body panel-body">
                    <table class="table table-striped">
                        <tbody>
                        <tr>
                            <div class="row">
                                <div class="col-md-6 input-field">
                                    <input type="text" class="form-control" name ="mlaids_datasetName" id="mlaids_datasetName" required="required" placeholder="Type short dataset name"/>
                                    <label for="mlaids_datasetName">Dataset Short Name</label>
                                </div>
                            </div>
                        </tr>
                        <tr>
                            <div class="row">
                                <div class="col-md-6">
                                    <%--class="tooltipped" data-position="right" data-delay="50" data-tooltip="Select data source(s) for the dataset"--%>
                                    <label for="mlaids_sourceSelection" >Source</label>
                                    <select multiple="true" size="2" class="form-control browser-default" name ="mlaids_sourceSelection" id="mlaids_sourceSelection"></select>
                                </div>
                            </div>
                        </tr>
                        <tr>
                            <div class="row">
                                <div class="col-md-6">
                                    <%-- class="tooltipped" data-position="right" data-delay="50" data-tooltip="Select platform(s) for the dataset"--%>
                                    <label for="mlaids_PlatformSelection">Platform</label>
                                    <select multiple="true" size="2" class="form-control browser-default"
                                                 name ="mlaids_PlatformSelection" id="mlaids_PlatformSelection"></select>
                                </div>
                            </div>
                        </tr>
                        <tr>
                            <div class="row">
                                <div class="col-md-6">
                                    <%-- class="tooltipped" data-position="right" data-delay="50" data-tooltip="Select learning activity action(s) for the dataset"--%>
                                    <label for="mlaids_actionSelection">Action </label>
                                    <select multiple="true" size="4" class="form-control browser-default"
                                                name ="mlaids_actionSelection" id="mlaids_actionSelection" onchange="populateMLAIDSCategories();"></select>
                                </div>
                            </div>
                        </tr>
                        <tr>
                            <div class="row">
                                <div class="col-md-6">
                                    <div id="mlaids_selectedMinorSpinner" class="preloader-wrapper small active" style="left:160px;">
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
                                    <label for="mlaids_selectedMinor">Category(ies)
                                        <a class="green-text openlap-help-icon tooltipped" data-position="right" data-delay="50"
                                           data-tooltip="<ul style='margin:0px;text-align:left;'>
                                               <li>- Categories are available after the Sources, Platforms and Actions parameters have been selected.</li>
                                               <li>- Select category(ies) related to which data should be used in dataset.</li>
                                               </ul>">
                                            <i class="material-icons">info_outline</i>
                                        </a>
                                    </label>
                                    <select multiple="true" size="6" class="form-control browser-default" title="Please select a Minor to populate the relevant Attributes"
                                                 name ="mlaids_selectedMinor" id="mlaids_selectedMinor" onchange="getMLAIDataColumns();"></select>
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
                                <li class="tab col s3"><a href="#mlaids_attribute" class="active">Attribute</a></li>
                                <%--<li class="tab col s3"><a title="Session Filters"href="#session">Session</a></li>--%>
                                <%--<li class="tab col s3"><a title="User and Time Filters" href="#userAndTime">User & Time</a></li>--%>
                                <li class="tab col s3"><a href="#mlaids_userAndTime">Time / User</a></li>
                            </ul>
                        </div>
                        <div class="col s12 m12 l12">
                            <div class="divider"></div>
                        </div>
                        <div id="mlaids_attribute" class="col s12">
                            <div class="row" id="mlaids_appliedAttributeFiltersPanel">
                                <div class="col s12 m8 l6">
                                    <label id="mlaids_appliedAttributeFiltersLabel" for="mlaids_appliedAttributeFiltersDiv" >Applied Attribute Filters</label>
                                </div>
                                <div class="col s12 m12 l12">
                                    <div id="mlaids_appliedAttributeFiltersDiv"></div>
                                </div>
                                <div class="col s12 m12 l12">
                                    <div class="divider"></div>
                                </div>
                            </div>
                            <div class="row">
                                <div class="col s12 m6 l6">
                                    <br/>
                                    <div id="mlaids_filterAttributeSpinner" class="preloader-wrapper small active" style="left:205px;">
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
                                    <label for="mlaids_entityKeySelection">Attribute
                                        <a class="green-text openlap-help-icon tooltipped" data-position="right" data-delay="50"
                                           data-tooltip="<ul style='margin:0px;text-align:left;'>
                                               <li>- Select appropriate attribute and click Search.</li>
                                               <li>- Select one or more values and click Apply.</li>
                                               </ul>">
                                            <i class="material-icons">info_outline</i>
                                        </a>

                                        <a style="margin-left:30px;" class="red-text openlap-help-icon tooltipped" data-position="right" data-delay="50"
                                           data-tooltip="<ul style='margin:0px;text-align:left;'>
                                               <li>- At least add 'Course ID' filter to use data relevant to your course only.</li>
                                               <li>- Search results shows data from the whole database. Already applied filters do not affect new search.</li>
                                               <li>- If no filters are applied then all values will be considered.</li>
                                               </ul>">
                                            <i class="material-icons">info_outline</i>
                                        </a>
                                    </label>
                                    <select class="browser-default" name ="mlaids_entityKeySelection" id="mlaids_entityKeySelection" title="Select Attribute"></select>
                                    <br/>
                                    <div class="right-align">
                                        <button class="waves-effect waves-light btn light-blue tooltipped" type="button" name="_eventId_searchAttributes" value="Search" onfocus="searchMLAIDSAttributeValues()" data-position="right" data-delay="50" data-tooltip="Search values for the selected attributes." >Search</button>
                                    </div>
                                    <br/>
                                    <div id="mlaids_entityValueSpinner" class="preloader-wrapper small active" style="left:200px;">
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
                                    <label for="mlaids_entityValue" title="Attribute Values">Attribute Values </label>
                                    <select class="browser-default" size="6" multiple="true" name="mlaids_entityValue" id ="mlaids_entityValue" title="Select from Attribute values">
                                    </select>
                                    <%--<form:select multiple="true" class="form-control browser-default" title="Select from Attribute values"--%>
                                    <%--path="evalue" items="${selectNumberParameters.searchResults}" name ="entityValue" id="mlaids_entityValue"/>--%>
                                    <%--<label for="specificationType">Specification Type </label>--%>
                                    <%--<form:select class="browser-default" path="selectedentityValueTypes" items="${selectNumberParameters.entityValueTypes}" name ="specificationType" id="mlaids_specificationType" title="Select Specification Type" />--%>
                                    <br/>
                                    <%--<div id="mlaids_entity_filter_add_msg">--%>
                                    <%--</div>--%>
                                    <div class="right-align">
                                        <button class="waves-effect waves-light btn light-blue tooltipped" type="button" name="attributebutton" id="mlaids_attributebutton"  value="Add" onclick="addMLAIDSEntity()" data-position="right" data-delay="50" data-tooltip="Apply the attribute filter" >Apply</button>
                                    </div>
                                </div>
                            </div>
                        </div>
                        <%--<div id="mlaids_session" class="col s12">
                            <div class="row" id="mlaids_appliedSessionFiltersPanel">
                                <div class="col s12 m8 l6">
                                    <label id="mlaids_appliedSessionFiltersLabel" for="appliedSessionFiltersDiv" title="Associated Session Filters to the Indicator">Applied Session Filters </label>
                                </div>
                                <div class="col s12 m12 l12">
                                    <div id="mlaids_appliedSessionFiltersDiv" title="Associated Session Filters to the Indicator"></div>
                                </div>
                                <div class="col s12 m12 l12">
                                    <div class="divider"></div>
                                </div>
                            </div>
                            <div class="row">
                                <div class="col s12 m6 l6">
                                    <br/>
                                    <label for="sessionSearchType" title="Session search type">Session Search Type </label>
                                    <form:select class="browser-default" path="selectedsessionSearchType" items="${selectNumberParameters.sessionSearchType}" name ="sessionSearchType" id="mlaids_sessionSearchType" title="Select Session Type" />
                                    <label for="sessionSearchString" class="control-label" title="Search Keyword">Search Keyword</label>
                                    <input class="form-control" path="sessionSearch"  name="sessionSearchString" id ="sessionSearchString" placeholder="Keywords"/>
                                    <br/>
                                    <div class="right-align">
                                        <button class="waves-effect waves-light btn light-blue" type="button" name="_eventId_searchSession" value="Search" onclick="searchSession()" title="Search for Session" >Search</button>
                                    </div>
                                    <br/>
                                    <label for="multipleSelect" title="Session Attribute Values">Session Values </label>
                                    <form:select class="browser-default"  path="selectedUserString" name="multipleSelect" id="mlaids_SessionsearchResults" title="Select from Search Results">
                                        <form:options items="${selectNumberParameters.searchResults}" />
                                    </form:select>
                                    &lt;%&ndash;<label for="SessionsearchType">Session Search Type</label>&ndash;%&gt;
                                    &lt;%&ndash;<form:select class="browser-default" path="selectedSearchType" items="${selectNumberParameters.searchType}" name ="SessionsearchType" id="mlaids_SessionsearchType" title="Select Search Type" />&ndash;%&gt;
                                    <br/>
                                    <div class="right-align">
                                        <button class="waves-effect waves-light btn light-blue" type="button" name="_eventId_specifySession" value="Add" onclick="addSessionFilter()" title="Apply Session Filter">Apply</button>
                                    </div>
                                </div>
                            </div>
                        </div>--%>
                        <div id="mlaids_userAndTime" class="col s12">
                            <div class="row" id="mlaids_appliedUserTimeFiltersPanel">
                                <div class="col s12 m8 l6">
                                    <label id="mlaids_appliedUserTimeFiltersLabel" for="mlaids_appliedUserTimeFiltersDiv">Applied Time Filters </label>
                                </div>
                                <div class="col s12 m12 l12">
                                    <div id="mlaids_appliedUserTimeFiltersDiv"></div>
                                </div>
                                <div class="col s12 m12 l12">
                                    <div class="divider"></div>
                                </div>
                            </div>
                            <div class="row">
                                <br/>
                                <div class="col s12 m6 l6">
                                    <label>Time Filter</label>
                                    <div class="divider"></div>
                                </div>
                                <br/>
                            </div>
                            <div class="row">
                                <div class="col s12 m6 l6">
                                    <label for="mlaids_dateType">Date Type
                                        <a class="green-text openlap-help-icon tooltipped" data-position="right" data-delay="50"
                                           data-tooltip="<ul style='margin:0px;text-align:left;'>
                                               <li>- Select the Date Type, such as Start date or End date.</li>
                                               <li>- Specify the Date for the selected type and click Apply.</li>
                                               <li>- If no date filters are applied then all data will be considered.</li>
                                               </ul>">
                                            <i class="material-icons">info_outline</i>
                                        </a>
                                    </label>
                                    <select class="browser-default" name="mlaids_dateType" id="mlaids_dateType">
                                        <option value="fromDate">Start date</option>
                                        <option value="toDate">End date</option>
                                    </select>

                                    <label for="mlaids_dateFilterVal">Date</label>
                                    <input type="date" class="datepicker" id="mlaids_dateFilterVal">

                                    <br/>
                                    <div class="right-align">
                                        <button class="waves-effect waves-light btn light-blue tooltipped" type="button" name="_eventId_specifyTime" onclick="addMLAIDSTimeFilter()" value="Add" data-position="right" data-delay="50" data-tooltip="Apply the date filter.">Apply</button>
                                    </div>
                                </div>
                            </div>

                            <div class="row">
                                <br/>
                                <div class="col s12 m6 l6">
                                    <label>User Filter</label>
                                    <div class="divider"></div>
                                </div>
                                <br/>
                            </div>
                            <div class="row">
                                <div class="col s12 m6 l6">
                                    <div id="mlaids_userFilterRadioDiv">
                                        <input class="with-gap" name="user-filter-group" type="radio" id="mlaids_userFilterAll" checked onclick="MLAIUserFilterChanged()"/>
                                        <label for="mlaids_userFilterAll">Use all data</label>

                                        <input class="with-gap" name="user-filter-group" type="radio" id="mlaids_userFilterMy"  onclick="MLAIUserFilterChanged()"/>
                                        <label for="mlaids_userFilterMy">Use my data only</label>

                                        <input class="with-gap"  name="user-filter-group" type="radio" id="mlaids_userFilterOthers"  onclick="MLAIUserFilterChanged()"/>
                                        <label for="mlaids_userFilterOthers">Exclude my data</label>
                                    </div>

                                    <%--<div id="mlaids_userFilterRadioAction" style="display:none;">--%>
                                        <%--<div class="input-field">--%>
                                            <%--<input placeholder="Enter your encrypted user key here." id="mlaids_userEncryptedHash" name="mlaids_userEncryptedHash" type="text" class="form-control">--%>
                                            <%--<label for="mlaids_userEncryptedHash">User Encrypted Key--%>
                                                <%--<a class="red-text openlap-help-icon tooltipped" data-position="right" data-delay="50"--%>
                                                   <%--data-tooltip="<ul style='margin:0px;text-align:left;'>--%>
                                               <%--<li>- OpenLAP in not fully integrated with L<sup>2</sup>P.</li>--%>
                                               <%--<li>- Therefore the encrypted key of users is not automatically available.</li>--%>
                                               <%--<li>- Contact OpenLAP admins to get your encrypted key.</li>--%>
                                               <%--</ul>">--%>
                                                    <%--<i class="material-icons">info_outline</i>--%>
                                                <%--</a>--%>
                                            <%--</label>--%>
                                        <%--</div>--%>

                                        <%--<div class="right-align">--%>
                                            <%--<button class="waves-effect waves-light btn light-blue tooltipped" type="button" name="btn_mlaids_SetUserFilter" onclick="setMLAIUserFilter()" data-position="right" data-delay="50" data-tooltip="Set the selected user filter.">Set Filter</button>--%>
                                        <%--</div>--%>
                                    <%--</div>--%>
                                </div>
                            </div>
                        </div>
                    </div>
                </div>
            </li>
            <li>
                <div class="light-blue lighten-5 collapsible-header active">Analysis</div>
                <div class="collapsible-body panel-body">
                    <div class="row">
                        <div class="col s12 m6 l6">
                            <label for="mlaids_analyticsMethod">Method
                                <a class="green-text openlap-help-icon tooltipped" data-position="right" data-delay="50"
                                   data-tooltip="<ul style='margin:0px;text-align:left;'>
                                       <li>- Select a first level analytics method which you want to apply on the dataset.</li>
                                       <li>- Description of each analtyics method is available when you hover over its name.</li>
                                       </ul>">
                                    <i class="material-icons">info_outline</i>
                                </a>
                            </label>
                            <select class="browser-default" name ="mlaids_analyticsMethod" id="mlaids_analyticsMethod"
                                    onchange="getMLAIDSAnalyticsMethodInputs();"></select>
                        </div>
                        <div class="col m6 l6">
                            <div class="select-desc hide-on-small-only">
                                <span id="mlaids_analyticsMethodDesc" title="Analytics method description"></span>
                            </div>
                        </div>
                    </div>
                    <div class="row" id="mlaids_methodDynamicParamsRow">
                        <div class="col s12 m6 l6">
                            <label>Additional Parameters</label>
                            <div class="divider"></div>
                            <div id="mlaids_methodDynamicParams" class="dynamic-params">
                                <div class='select-desc' style='margin-top: 5px;'><span>No additional parameters</span></div>
                            </div>
                        </div>
                    </div>
                    <div class="row">
                        <br/>
                        <div class="col s12 m6 l6">
                            <div id="mlaids_inputForMethodsSpinner" class="preloader-wrapper small active" style="left:170px;">
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
                            <label>Mappings
                                <a class="green-text openlap-help-icon tooltipped" data-position="right" data-delay="50"
                                   data-tooltip="<ul style='margin:0px;text-align:left;'>
                                       <li>- Select a column from the 'Data Columns' list.</li>
                                       <li>- Select an input from 'Inputs of Methods' list in which you want to send the selected column.</li>
                                       <li>- Click 'Add' to finalize this mapping.</li>
                                       <li>- Specify mappings for all the required inputs (in Red) in 'Inputs for Methods' list.</li>
                                       <li>- Green colored mappings are optional and can be skipped. Read their tooltips for more info.</li>
                                       <li>- Description of each data column and input is available when you hover over its name.</li>
                                       </ul>">
                                    <i class="material-icons">info_outline</i>
                                </a>
                            </label>
                            <div class="divider"></div>
                        </div>
                        <br/>
                    </div>
                    <div class="row">
                        <div class="col s12 m6 l6">
                            <div class="col s6 m6 l6">
                                <label for="mlaids_methodDataColumns" title="Data Column Values">Data Columns</label>
                                <select class="form-control browser-default" size="6" title="Select method data columns in here"
                                        name ="mlaids_methodDataColumns" id="mlaids_methodDataColumns" size="4">
                                </select>
                            </div>
                            <div class="col s6 m6 l6">
                                <label for="mlaids_inputForMethods" title="Input values fro Methods">Inputs for Method</label>
                                <select class="form-control browser-default" size="6"
                                        name ="mlaids_inputForMethods" id="mlaids_inputForMethods" size="4"></select>
                            </div>
                        </div>
                    </div>
                    <div class="row">
                        <div class="col s12 m6 l6 right-align">
                            <table>
                                <tbody>
                                <tr>
                                    <td class="full-width">
                                        <span id="mlaids_addMethodMapping_msg" class="preview-err-msg"></span>
                                    </td>
                                    <td>
                                        <button class="btn waves-effect waves-light light-blue" type="button"
                                                id="mlaids_addMethodMapping" name="mlaids_addMethodMapping" value="Add Mapping" onclick="addMLAIDSMethodMappingToTable();">Add
                                        </button>
                                    </td>
                                </tr>
                                </tbody>
                            </table>
                        </div>
                    </div>
                    <div class="row">
                        <div class="col s12 m6 l6 right-align">
                            <table id="mlaids_methodMappingTable" class="centered">
                                <thead>
                                <tr>
                                    <th data-field="id">Data Column</th>
                                    <th data-field="name">Inputs for Method</th>
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
        </form:form>
    </div>
    <div class="modal-footer">
        <div class="right-align preview-action">
            <table>
                <tbody>
                <tr>
                    <td>
                        <span id="mlaids_preview_msg" class="preview-err-msg"></span>
                    </td>
                    <td style="width:230px;">
                        <button class="waves-effect waves-light btn light-blue tooltipped" onclick="saveFirstMethod();" data-position="top" data-delay="50" data-tooltip="Save dataset" style="margin-left: 10px;">Save</button>
                        <button class="modal-close waves-effect waves-light btn light-blue cancelFilterDelete tooltipped" onclick="cancelDSModel();" data-position="top" data-delay="50" data-tooltip="Cancel current dataset">Cancel</button> &nbsp;
                    </td>
                </tr>
                </tbody>
            </table>
        </div>
    </div>
</div>
