<div id="indicatorDefinition" class="col s12 m6 card">
    <div class="panel panel-default">
        <div class="panel-heading light-blue darken-2">
            Indicator
            <a class="modal-trigger amber-text openlap-help-icon tooltipped" id="simpleIndicatorHelp" data-position="right" data-delay="50" data-tooltip="Click to see help related to indicator defination."
               href="#simpleIndicatorHelpModel">
                <i class="material-icons">help_outline</i>
            </a>
        </div>
        <div class="panel-body">
            <ul class="collapsible" data-collapsible="expandable">
                <li>
                    <div class="light-blue lighten-5 collapsible-header active">
                        Dataset
                        <a class="modal-trigger amber-text openlap-help-icon tooltipped" id="simpleIndicatorHelpDataset" data-position="right" data-delay="50" data-tooltip="Click to see help related to Dataset section."
                           href="#simpleIndicatorHelpModel" onclick="clickElement('simple_help_dataset', event)">
                            <i class="material-icons">help_outline</i>
                        </a>
                    </div>
                    <div class="collapsible-body panel-body">
                        <table class="table table-striped">
                            <tbody>
                            <tr>
                                <div class="row">
                                    <div class="col-md-6 input-field">
                                        <input type="text" class="form-control" name ="indicatorNaming"
                                                    id="indicatorNaming"  required="required" placeholder="Type your Indicator Name"/>
                                        <label class="item-title" for="indicatorNaming">Indicator Name</label>
                                    </div>
                                </div>
                            </tr>
                            <tr>
                                <div class="row">
                                    <div class="col-md-6">
                                        <%--class="item-title tooltipped" data-position="right" data-delay="50" data-tooltip="Select data source(s) for the dataset"--%>
                                        <label for="sourceSelection">Source(s)</label>
                                        <select multiple="true" size="2" class="form-control browser-default" name ="sourceSelection" id="sourceSelection"/>
                                    </div>
                                </div>
                            </tr>
                            <tr>
                                <div class="row">
                                    <div class="col-md-6">
                                        <%--class="item-title tooltipped" data-position="right" data-delay="50" data-tooltip="Select platform(s) for the dataset"--%>
                                        <label for="PlatformSelection">Platform(s)</label>
                                        <select multiple="true" size="2" class="form-control browser-default" name ="PlatformSelection" id="PlatformSelection"/>
                                    </div>
                                </div>
                            </tr>
                            <tr>
                                <div class="row">
                                    <div class="col-md-6">
                                        <%--class="item-title tooltipped" data-position="right" data-delay="50" data-tooltip="Select learning activity action(s) for the dataset"--%>
                                        <label for="actionSelection">Action(s)</label>
                                        <select multiple="true" size="4" class="form-control browser-default" name ="actionSelection" id="actionSelection" onchange="populateCategories();"/>
                                    </div>
                                </div>
                            </tr>
                            <tr>
                                <div class="row">
                                    <div class="col-md-6">
                                        <div id="selectedMinorSpinner" class="preloader-wrapper small active" style="left:160px;">
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
                                        <label for="selectedMinor" >Category(ies)
                                            <a class="green-text openlap-help-icon tooltipped" data-position="right" data-delay="50"
                                               data-tooltip="<ul style='margin:0px;text-align:left;'>
                                               <li>- Categories are available after the Sources, Platforms and Actions parameters have been selected.</li>
                                               <li>- Select category(ies) related to which data should be used in dataset.</li>
                                               </ul>">
                                                <i class="material-icons">info_outline</i>
                                            </a>
                                        </label>
                                        <form:select multiple="true" size="6" class="form-control browser-default"
                                                     path="selectedMinor" items="${selectNumberParameters.minors}" name ="selectedMinor" id="selectedMinor" onchange="populateEntities();"/>
                                    </div>
                                </div>
                            </tr>
                            </tbody>
                        </table>
                    </div>
                </li>
                <li>
                    <div class="light-blue lighten-5 collapsible-header active">
                        Filters
                        <a class="modal-trigger amber-text openlap-help-icon tooltipped" id="simpleIndicatorHelpFilter" data-position="right" data-delay="50" data-tooltip="Click to see help related to Filters section."
                           href="#simpleIndicatorHelpModel" onclick="clickElement('simple_help_filter', event)">
                            <i class="material-icons">help_outline</i>
                        </a>
                    </div>
                    <div class="collapsible-body panel-body">
                        <div class="row">
                            <div class="col s12">
                                <ul class="tabs" id="filterTabs">
                                    <li class="tab col s3"><a href="#attribute" class="active">Attribute</a></li>
                                    <%--<li class="tab col s3"><a href="#session">Session</a></li>--%>
                                    <%--<li class="tab col s3"><a href="#userAndTime">User & Time</a></li>--%>
                                    <li class="tab col s3"><a href="#userAndTime">Time / User</a></li>
                                </ul>
                            </div>
                            <div class="col s12 m12 l12">
                                <div class="divider"></div>
                            </div>
                            <div id="attribute" class="col s12">
                                <div class="row" id="appliedAttributeFiltersPanel">
                                    <div class="col s12 m8 l6">
                                        <label id="appliedAttributeFiltersLabel" for="appliedAttributeFiltersDiv">Applied Attribute Filters</label>
                                    </div>
                                    <div class="col s12 m12 l12">
                                        <div id="appliedAttributeFiltersDiv"></div>
                                    </div>
                                    <div class="col s12 m12 l12">
                                        <div class="divider"></div>
                                    </div>
                                </div>
                                <div class="row">
                                    <div class="col s12 m6 l6">
                                        <br/>
                                        <div id="filterAttributeSpinner" class="preloader-wrapper small active" style="left:200px;">
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
                                        <label for="entityKeySelection">Attribute
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
                                        <form:select class="browser-default" path="selectedKeys" items="${selectNumberParameters.keys}" name ="entityKeySelection" id="entityKeySelection"/>
                                        <br/>
                                        <div class="right-align">
                                            <button class="waves-effect waves-light btn light-blue tooltipped" type="button" name="_eventId_searchAttributes" value="Search" onfocus="searchAttributes()" data-position="right" data-delay="50" data-tooltip="Search values for the selected attributes." >Search</button>
                                        </div>
                                        <br/>
                                        <div id="entityValueSpinner" class="preloader-wrapper small active" style="left:200px;">
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
                                        <label for="entityValue">Attribute Value(s)</label>
                                        <form:select class="browser-default" size="6" multiple="true"  path="evalue"  name="entityValue" id ="entityValue">
                                            <form:options items="${selectNumberParameters.searchResults}" />
                                        </form:select>
                                        <%--<form:select multiple="true" class="form-control browser-default" --%>
                                                     <%--path="evalue" items="${selectNumberParameters.searchResults}" name ="entityValue" id="entityValue"/>--%>
                                        <%--<label for="specificationType">Specification Type </label>--%>
                                        <%--<form:select class="browser-default" path="selectedentityValueTypes" items="${selectNumberParameters.entityValueTypes}" name ="specificationType" id="specificationType" title="Select Specification Type" />--%>
                                        <br/>
                                        <%--<div id="entity_filter_add_msg">--%>
                                        <%--</div>--%>
                                        <div class="right-align">
                                            <button class="waves-effect waves-light btn light-blue tooltipped" type="button" name="attributebutton" id ="attributebutton"  value="Add" onclick="addEntity()" data-position="right" data-delay="50" data-tooltip="Apply the attribute filter" >Apply</button>
                                        </div>
                                    </div>
                                </div>
                            </div>
                            <%--<div id="session" class="col s12">
                                <div class="row" id="appliedSessionFiltersPanel">
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
                                        &lt;%&ndash;<label for="SessionsearchType">Session Search Type</label>&ndash;%&gt;
                                        &lt;%&ndash;<form:select class="browser-default" path="selectedSearchType" items="${selectNumberParameters.searchType}" name ="SessionsearchType" id="SessionsearchType" title="Select Search Type" />&ndash;%&gt;
                                        <br/>
                                        <div class="right-align">
                                            <button class="waves-effect waves-light btn light-blue" type="button" name="_eventId_specifySession" value="Add" onclick="addSessionFilter()" title="Apply Session Filter">Apply</button>
                                        </div>
                                    </div>
                                </div>
                            </div>--%>
                            <div id="userAndTime" class="col s12">
                                <div class="row" id="appliedUserTimeFiltersPanel">
                                    <div class="col s12 m8 l6">
                                        <label id="appliedUserTimeFiltersLabel" for="appliedUserTimeFiltersDiv">Applied Time Filters </label>
                                    </div>
                                    <div class="col s12 m12 l12">
                                        <div id="appliedUserTimeFiltersDiv"></div>
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
                                        <label for="dateType">Date Type
                                            <a class="green-text openlap-help-icon tooltipped" data-position="right" data-delay="50"
                                               data-tooltip="<ul style='margin:0px;text-align:left;'>
                                               <li>- Select the Date Type, such as Start date or End date.</li>
                                               <li>- Specify the Date for the selected type and click Apply.</li>
                                               <li>- If no date filters are applied then all data will be considered.</li>
                                               </ul>">
                                                <i class="material-icons">info_outline</i>
                                            </a>
                                        </label>
                                        <select class="browser-default"name ="dateType" id="dateType">
                                            <option value="fromDate">Start date</option>
                                            <option value="toDate">End date</option>
                                        </select>

                                        <label for="dateFilterVal">Date</label>
                                        <input type="date" class="datepicker" id="dateFilterVal">

                                        <br/>
                                        <div class="right-align">
                                            <button class="waves-effect waves-light btn light-blue tooltipped" type="button" name="_eventId_specifyTime" onclick="addTimeFilter()" value="Add" data-position="right" data-delay="50" data-tooltip="Apply the date filter.">Apply</button>
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
                                        <div id="userFilterRadioDiv">
                                            <input class="with-gap" name="user-filter-group" type="radio" id="userFilterAll" checked onclick="userFilterChanged()"/>
                                            <label for="userFilterAll">Use all data</label>

                                            <input class="with-gap" name="user-filter-group" type="radio" id="userFilterMy"  onclick="userFilterChanged()"/>
                                            <label for="userFilterMy">Use my data only</label>

                                            <input class="with-gap"  name="user-filter-group" type="radio" id="userFilterOthers"  onclick="userFilterChanged()"/>
                                            <label for="userFilterOthers">Exclude my data</label>
                                        </div>

                                        <%--<div id="userFilterRadioAction" style="display:none;">--%>
                                            <%--<div class="input-field">--%>
                                                <%--<input placeholder="Enter your encrypted user key here." id="userEncryptedHash" name="userEncryptedHash" type="text" class="form-control">--%>
                                                <%--<label for="userEncryptedHash">User Encrypted Key--%>
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
    <%----%>
                                            <%--<div class="right-align">--%>
                                                <%--<button class="waves-effect waves-light btn light-blue tooltipped" type="button" name="btn_SetUserFilter" onclick="setUserFilter()" data-position="right" data-delay="50" data-tooltip="Set the selected user filter.">Set Filter</button>--%>
                                            <%--</div>--%>
                                        <%--</div>--%>
                                    </div>
                                </div>
                            </div>
                        </div>
                    </div>
                </li>
                <li>
                    <div class="light-blue lighten-5 collapsible-header active">
                        Analysis
                        <a class="modal-trigger amber-text openlap-help-icon tooltipped" id="simpleIndicatorHelpAnalyze" data-position="right" data-delay="50" data-tooltip="Click to see help related to Analysis section."
                           href="#simpleIndicatorHelpModel" onclick="clickElement('simple_help_analysis', event)">
                            <i class="material-icons">help_outline</i>
                        </a>
                    </div>
                    <div class="collapsible-body panel-body">
                        <div class="row">
                            <div class="col s12 m6 l6">
                                <label for="analyticsMethod">Method
                                    <a class="green-text openlap-help-icon tooltipped" data-position="right" data-delay="50"
                                       data-tooltip="<ul style='margin:0px;text-align:left;'>
                                       <li>- Select an analytics method which you want to apply on the dataset.</li>
                                       <li>- Description of each analtyics method is available when you hover over its name.</li>
                                       </ul>">
                                        <i class="material-icons">info_outline</i>
                                    </a>
                                </label>
                                <select class="browser-default" name ="analyticsMethod" id="analyticsMethod"
                                        onchange="getAnalyticsMethodInputs();"></select>
                            </div>
                            <div class="col m6 l6">
                                <div class="select-desc hide-on-small-only">
                                    <span id="analyticsMethodDesc"></span>
                                </div>
                            </div>
                        </div>
                        <div class="row" id="methodDynamicParamsRow">
                            <div class="col s12 m6 l6">
                                <label>Additional Parameters</label>
                                <div class="divider"></div>
                                <div id="methodDynamicParams" class="dynamic-params">
                                    <div class='select-desc' style='margin-top: 5px;'><span>No additional parameters</span></div>
                                </div>
                            </div>
                        </div>
                        <div class="row">
                            <br/>
                            <div class="col s12 m6 l6">
                                <div id="inputForMethodsSpinner" class="preloader-wrapper small active" style="left:160px;">
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
                                    <label for="methodDataColumns">Data Columns</label>
                                    <select class="form-control browser-default" size="6" name ="methodDataColumns" id="methodDataColumns" size="4"></select>
                                </div>
                                <div class="col s6 m6 l6">
                                    <label for="inputForMethods" >Inputs of Method</label>
                                    <select class="form-control browser-default" size="6" name ="inputForMethods" id="inputForMethods" size="4"></select>
                                </div>
                            </div>
                        </div>
                        <div class="row">
                            <div class="col s12 m6 l6 right-align">
                                <table>
                                    <tbody>
                                    <tr>
                                        <td class="full-width">
                                            <span id="addMethodMapping_msg" class="preview-err-msg"></span>
                                        </td>
                                        <td>
                                            <button class="btn waves-effect waves-light light-blue" type="button"
                                                    id="addMethodMapping" name="addMethodMapping" value="Add Mapping" onclick="addMethodMappingToTable();">Add
                                            </button>
                                        </td>
                                    </tr>
                                    </tbody>
                                </table>
                            </div>
                        </div>
                        <div class="row">
                            <div class="col s12 m6 l6 right-align">
                                <table id="methodMappingTable" class="centered">
                                    <thead>
                                        <tr>
                                            <th data-field="id">Dataset Columns</th>
                                            <th data-field="name">Input of Methods</th>
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
                    <div class="light-blue lighten-5 collapsible-header active">
                        Visualization
                        <a class="modal-trigger amber-text openlap-help-icon tooltipped" id="simpleIndicatorHelpVisualize" data-position="right" data-delay="50" data-tooltip="Click to see help related to the Visualization section."
                           href="#simpleIndicatorHelpModel" onclick="clickElement('simple_help_visualize', event)">
                            <i class="material-icons">help_outline</i>
                        </a>
                    </div>
                    <div class="collapsible-body panel-body">
                        <div class="row">
                            <div class="col s6 m6 l6">
                                <div class="row">
                                    <label for="EngineSelect">Visualization Library
                                        <a class="green-text openlap-help-icon tooltipped" data-position="right" data-delay="50"
                                           data-tooltip="<ul style='margin:0px;text-align:left;'>
                                       <li>- Select a visualization library (e.g. Google Charts, C3.js) using which you want to visualize the indicator.</li>
                                       <li>- Select a visualization type (e.g. Bar Chart, Pie Chart) using which you want to visualize the indicator.</li>
                                       </ul>">
                                            <i class="material-icons">info_outline</i>
                                        </a>
                                    </label>
                                    <select class="browser-default" name ="EngineSelect" id="EngineSelect" onchange="populateVisualizationMethods();">
                                    </select>
                                </div>
                                <div class="row">
                                    <div id="selectedChartTypeSpinner" class="preloader-wrapper small active" style="left:190px;">
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
                                    <label for="selectedChartType">Visualization Type </label>
                                    <select class="browser-default" name ="selectedChartType" id="selectedChartType" onchange="getVisualizationMethodInputs();"></select>
                                </div>
                                <div class="row">
                                    <br/>
                                    <div class="col s12 m12 l12">
                                        <div id="inputForVisualizerSpinner" class="preloader-wrapper small active" style="left:170px;">
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
                                                   <li>- Select an output from the 'Outputs of Method' list.</li>
                                                   <li>- Select an input from 'Inputs of Visualization' list in which you want to send the selected input.</li>
                                                   <li>- Click 'Add' to finalize this mapping.</li>
                                                   <li>- Specify mappings for all the required inputs (in Red) in 'Inputs of Visualization' list.</li>
                                                   <li>- Green colored mappings are optional and can be skipped. Read their tooltips for more info.</li>
                                                   <li>- Description of each input and output is available when you hover over its name.</li>
                                                   </ul>">
                                                <i class="material-icons">info_outline</i>
                                            </a>
                                        </label>
                                        <div class="divider"></div>
                                    </div>
                                    <br/>
                                </div>
                                <div class="row">
                                    <div class="col s6 m6 l6">
                                        <label for="outputForMethods">Outputs of Method</label>
                                        <select class="form-control browser-default" name ="outputForMethods" id="outputForMethods" size="4"></select>
                                    </div>
                                    <div class="col s6 m6 l6">
                                        <label for="inputForVisualizer">Inputs of Visualization</label>
                                        <select class="form-control browser-default" name ="inputForVisualizer" id="inputForVisualizer" size="4"></select>
                                    </div>
                                </div>
                                <div class="row">
                                   <div class="col s12 m12 l12 right-align">
                                        <table>
                                            <tbody>
                                            <tr>
                                                <td class="full-width">
                                                    <span id="addVisualizationMapping_msg" class="preview-err-msg"></span>
                                                </td>
                                                <td>
                                                    <button class="btn waves-effect waves-light light-blue" type="button"
                                                            id="addVisualizationMapping" name="addVisualizationMapping" value="Add Mapping" onclick="addVisualizationMappingToTable();">Add
                                                    </button>
                                                </td>
                                            </tr>
                                            </tbody>
                                        </table>
                                    </div>
                                </div>
                                <div class="row">
                                    <div class="col s12 m12 l12 right-align">
                                        <table id="visualizerMappingTable" class="centered">
                                            <thead>
                                            <tr>
                                                <th data-field="id">Outputs of Method</th>
                                                <th data-field="name">Inputs of Visualization</th>
                                            </tr>
                                            </thead>
                                            <tbody>
                                            </tbody>
                                        </table>
                                    </div>
                                </div>
                                <div class="row">
                                    <div class="divider"></div>
                                </div>
                                <div class="row">
                                    <div class="right-align preview-action">
                                        <table>
                                            <tbody>
                                            <tr>
                                                <td class="full-width">
                                                    <span id="preview_msg" class="preview-err-msg"></span>
                                                </td>
                                                <td>
                                                    <button class="btn waves-effect waves-light light-blue tooltipped" type="button" data-position="bottom" data-delay="50" data-tooltip="Click to generate indicator preview."
                                                            name="generateGraph" id="generateGraph" value="Generate Graph" onclick="getIndicatorPreviewVisualizationCode()">Preview
                                                    </button>
                                                </td>
                                            </tr>
                                            </tbody>
                                        </table>
                                    </div>
                                </div>
                            </div>
                            <div class="col s6 m6 l6">
                                <label id="previewChartLabel" class="center-align">Indicator Preview (small dataset)</label>
                                <br/>
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
                                <div id="chart_wrap">
                                    <div id="preview_chart"></div>
                                </div>
                            </div>
                        </div>
                    </div>
                </li>
            </ul>
            <div class="col s12 m6 l10 right-align">
                <button class="btn waves-effect waves-light light-blue tooltipped" type="button" data-position="top" data-delay="50" data-tooltip="Cancel current indicator."
                        name="cancelIndicator" id="cancelIndicator" value="Cancel Indicator">Cancel
                </button>
                <button class="btn waves-effect waves-light light-blue tooltipped" type="button" data-position="top" data-delay="50" data-tooltip="Save current indicator."
                        id="saveIndicator" name="saveIndicator" value="Finalize Settings">Associate
                </button>
            </div>
        </div>
    </div>
</div>
