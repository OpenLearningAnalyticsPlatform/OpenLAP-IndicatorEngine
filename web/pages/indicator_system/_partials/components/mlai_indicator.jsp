<div id="mlai_indicatorDefinition" class="col s12 m6 card">
    <div class="panel panel-default">
        <div class="panel-heading light-blue darken-2">
            Multi Level Analysis Indicator
            <a class="modal-trigger amber-text openlap-help-icon tooltipped" id="mlaiIndicatorHelp" data-position="right" data-delay="50" data-tooltip="Click to see help related to indicator defination."
               href="#mlaiIndicatorHelpModel">
                <i class="material-icons">help_outline</i>
            </a>
        </div>
        <div class="panel-body">
            <ul class="collapsible" data-collapsible="expandable">
                <li>
                    <div class="light-blue lighten-5 collapsible-header active">Information</div>
                    <div class="collapsible-body panel-body">
                        <table class="table table-striped">
                            <tbody>
                                <tr>
                                    <div class="row">
                                        <div class="col-md-6 input-field">
                                            <input type="text" class="form-control" name ="mlai_indicatorNaming"
                                                        id="mlai_indicatorNaming" placeholder="Type your Indicator Name"/>
                                            <label for="mlai_indicatorNaming">Indicator Name</label>
                                        </div>
                                    </div>
                                </tr>
                            </tbody>
                        </table>
                    </div>
                </li>
                <li>
                    <div class="light-blue lighten-5 collapsible-header active">
                        First Level Analysis
                        <a class="modal-trigger amber-text openlap-help-icon tooltipped" data-position="right" data-delay="50" data-tooltip="Click to see help related to the First Level Analysis section."
                           href="#mlaiIndicatorHelpModel" onclick="clickElement('mlai_help_first', event)">
                            <i class="material-icons">help_outline</i>
                        </a>
                    </div>
                    <div class="collapsible-body panel-body">
                        <div class="row">
                            <div class="col s12 m8 l6">
                                <div class="action-btn horizontal add-datasource-div">
                                    <a class="btn-floating light-blue darken-2 add-datasource-btn tooltipped" id="mlai_addFirstMethod" onclick="openDSModel('new')" data-position="bottom" data-delay="50" data-tooltip="Add New first level analytics method">
                                        <i class="material-icons add-datasource-label">add</i>
                                    </a>
                                </div>
                                <label id="mlai_firstMethodLabel" >First Level Analysis</label>
                            </div>
                        </div>
                        <div class="row">
                            <div class="col s12 m8 l6">
                                <div id="mlai_firstMethodDiv">
                                    <span class='smallgraytext'>
                                        No analysis defined.
                                    </span>
                                </div>
                            </div>
                        </div>
                        <div class="row">
                            <div class="col s12 m8 l6">
                                <label id="mlai_combineMethodsLabel">Combine Outputs
                                    <a class="green-text openlap-help-icon tooltipped" data-position="right" data-delay="50"
                                       data-tooltip="<ul style='margin:0px;text-align:left;'>
                                               <li>- Each defined first level analysis will be available below as checkbox.</li>
                                               <li>- Select two checkboxes to combine their outputs.</li>
                                               <li>- Outputs of the selected first level anlaysis will be available in lists.</li>
                                               <li>- Select the outputs from both lists which have the same data.</li>
                                               <li>- Click the 'Combine' button to combine two outputs.</li>
                                               <li>- Perform the same task till only one first level analysis is left.</li>
                                               </ul>">
                                        <i class="material-icons">info_outline</i>
                                    </a>
                                </label>
                                <div class="divider"></div>
                            </div>
                        </div>
                        <div class="row">
                            <div class="col s12 m8 l6">
                                <div id="mlai_combineMethodsSelection" name="mlai_combineMethodsSelection">
                                    <span class='smallgraytext'>
                                        No analysis defined.
                                    </span>
                                </div>
                            </div>
                        </div>
                        <div class="row">
                            <div class="col s12 m6 l6">
                                <div class="col s6 m6 l6">
                                    <label id="mlai_method1outputsLabel" for="mlai_method1outputs">Outputs of :</label>
                                    <select class="form-control browser-default" size="6" name ="mlai_method1outputs" id="mlai_method1outputs"></select>
                                    <input id="mlai_method1outputsId" type="hidden"/>
                                </div>
                                <div class="col s6 m6 l6">
                                    <label id="mlai_method2outputsLabel" for="mlai_method2outputs">Outputs of :</label>
                                    <select class="form-control browser-default" size="6" name ="mlai_method2outputs" id="mlai_method2outputs"></select>
                                    <input id="mlai_method2outputsId" type="hidden"/>
                                </div>
                            </div>
                        </div>
                        <div class="row">
                            <div class="col s12 m6 l6 right-align">
                                <table>
                                    <tbody>
                                    <tr>
                                        <td class="full-width">
                                            <span id="mlai_addMergeMapping_msg" class="preview-err-msg"></span>
                                        </td>
                                        <td>
                                            <button class="btn waves-effect waves-light light-blue tooltipped" type="button"
                                                    id="mlai_addMergeMapping" name="mlai_addMergeMapping" value="Add Mapping" onclick="combineDatasets();" data-position="right" data-delay="50"
                                                    data-tooltip="Combine selected first level analysis outputs.">Combine
                                            </button>
                                        </td>
                                    </tr>
                                    </tbody>
                                </table>
                            </div>
                        </div>
                    </div>
                </li>
                <li>
                    <div class="light-blue lighten-5 collapsible-header active">
                        Second Level Analysis
                        <a class="modal-trigger amber-text openlap-help-icon tooltipped" data-position="right" data-delay="50" data-tooltip="Click to see help related to the Second Level Analysis section."
                           href="#mlaiIndicatorHelpModel" onclick="clickElement('mlai_help_second', event)">
                            <i class="material-icons">help_outline</i>
                        </a>
                    </div>
                    <div class="collapsible-body panel-body">
                        <div class="row">
                            <div class="col s12 m6 l6">
                                <label for="mlai_analyticsMethod">Second Level Analytics Method
                                    <a class="green-text openlap-help-icon tooltipped" data-position="right" data-delay="50"
                                       data-tooltip="<ul style='margin:0px;text-align:left;'>
                                       <li>- Select the second level analytics method which you want to apply on the combined outputs of first level analysis.</li>
                                       <li>- Description of each analtyics method is available when you hover over its name.</li>
                                       </ul>">
                                        <i class="material-icons">info_outline</i>
                                    </a>
                                </label>
                                <select class="browser-default" name ="mlai_analyticsMethod" id="mlai_analyticsMethod"
                                        onchange="getMLAIAnalyticsMethodInputs();"></select>
                            </div>
                            <div class="col m6 l6">
                                <div class="select-desc hide-on-small-only">
                                    <span id="mlai_analyticsMethodDesc"></span>
                                </div>
                            </div>
                        </div>
                        <div class="row" id="mlai_methodDynamicParamsRow">
                            <div class="col s12 m6 l6">
                                <label>Additional Parameters</label>
                                <div class="divider"></div>
                                <div id="mlai_methodDynamicParams" class="dynamic-params">
                                    <div class='select-desc' style='margin-top: 5px;'><span>No additional parameters</span></div>
                                </div>
                            </div>
                        </div>
                        <div class="row">
                            <br/>
                            <div class="col s12 m6 l6">
                                <div id="mlai_inputForMethodsSpinner" class="preloader-wrapper small active" style="left:160px;">
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
                                <label >Mappings
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
                                    <label for="mlai_methodDataColumns">Combined Outputs</label>
                                    <select class="form-control browser-default" size="6"
                                            name ="mlai_methodDataColumns" id="mlai_methodDataColumns" size="4">
                                    </select>
                                </div>
                                <div class="col s6 m6 l6">
                                    <label for="mlai_inputForMethods">Inputs for Method</label>
                                    <select class="form-control browser-default" size="6"
                                            name ="mlai_inputForMethods" id="mlai_inputForMethods" size="4"></select>
                                </div>
                            </div>
                        </div>
                        <div class="row">
                            <div class="col s12 m6 l6 right-align">
                                <table>
                                    <tbody>
                                    <tr>
                                        <td class="full-width">
                                            <span id="mlai_addMethodMapping_msg" class="preview-err-msg"></span>
                                        </td>
                                        <td>
                                            <button class="btn waves-effect waves-light light-blue" type="button"
                                                    id="mlai_addMethodMapping" name="mlai_addMethodMapping" value="Add Mapping" onclick="addMLAIMethodMappingToTable();">Add
                                            </button>
                                        </td>
                                    </tr>
                                    </tbody>
                                </table>
                            </div>
                        </div>
                        <div class="row">
                            <div class="col s12 m6 l6 right-align">
                                <table id="mlai_methodMappingTable" class="centered">
                                    <thead>
                                        <tr>
                                            <th data-field="id">Combined Outputs</th>
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
                <li>
                    <div class="light-blue lighten-5 collapsible-header active">
                        Visualization
                        <a class="modal-trigger amber-text openlap-help-icon tooltipped" data-position="right" data-delay="50" data-tooltip="Click to see help related to the Visualization section."
                           href="#mlaiIndicatorHelpModel" onclick="clickElement('mlai_help_visualize', event)">
                            <i class="material-icons">help_outline</i>
                        </a>
                    </div>
                    <div class="collapsible-body panel-body">
                        <div class="row">
                            <div class="col s6 m6 l6">
                                <div class="row">
                                    <label for="mlai_EngineSelect" >Visualization Library
                                        <a class="green-text openlap-help-icon tooltipped" data-position="right" data-delay="50"
                                           data-tooltip="<ul style='margin:0px;text-align:left;'>
                                       <li>- Select a visualization library (e.g. Google Charts, C3.js) using which you want to visualize the indicator.</li>
                                       <li>- Select a visualization type (e.g. Bar Chart, Pie Chart) using which you want to visualize the indicator.</li>
                                       </ul>">
                                            <i class="material-icons">info_outline</i>
                                        </a>
                                    </label>
                                    <select class="browser-default" name ="mlai_EngineSelect" id="mlai_EngineSelect" onchange="populateMLAIVisualizationMethods();">
                                    </select>
                                </div>
                                <div class="row">
                                    <div id="mlai_selectedChartTypeSpinner" class="preloader-wrapper small active" style="left:220px;">
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
                                    <label for="mlai_selectedChartType" >Visualizationaph Type</label>
                                    <select class="browser-default" name ="mlai_selectedChartType" id="mlai_selectedChartType" onchange="getMLAIVisualizationMethodInputs();"></select>
                                </div>
                                <div class="row">
                                    <br/>
                                    <div class="col s12 m12 l12">
                                        <div id="mlai_inputForVisualizerSpinner" class="preloader-wrapper small active" style="left:170px;">
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
                                                   <li>- Specify mappings for all the required inputs (red) in 'Inputs of Visualization' list.</li>
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
                                        <label for="mlai_outputForMethods" >Outputs of Method</label>
                                        <select class="form-control browser-default"
                                                name ="mlai_outputForMethods" id="mlai_outputForMethods" size="4">
                                        </select>
                                    </div>
                                    <div class="col s6 m6 l6">
                                        <label for="mlai_inputForVisualizer" >Inputs of  Visualization</label>
                                        <select class="form-control browser-default"
                                                name ="mlai_inputForVisualizer" id="mlai_inputForVisualizer" size="4"></select>
                                    </div>
                                </div>
                                <div class="row">
                                    <div class="col s12 m12 l12 right-align">
                                        <table>
                                            <tbody>
                                            <tr>
                                                <td class="full-width">
                                                    <span id="mlai_addVisualizationMapping_msg" class="preview-err-msg"></span>
                                                </td>
                                                <td>
                                                    <button class="btn waves-effect waves-light light-blue" type="button"
                                                            id="mlai_addVisualizationMapping" name="mlai_addVisualizationMapping" value="Add Mapping" onclick="addMLAIVisualizationMappingToTable();">Add
                                                    </button>
                                                </td>
                                            </tr>
                                            </tbody>
                                        </table>
                                    </div>
                                </div>
                                <div class="row">
                                    <div class="col s12 m12 l12 right-align">
                                        <table id="mlai_visualizerMappingTable" class="centered">
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
                                                    <span id="mlai_preview_msg" class="preview-err-msg"></span>
                                                </td>
                                                <td>
                                                    <button class="btn waves-effect waves-light light-blue tooltipped" type="button" data-position="bottom" data-delay="50" data-tooltip="Click to generate indicator preview."
                                                            name="mlai_generateGraph" id="mlai_generateGraph" value="Generate Graph" onclick="getMLAIIndicatorPreviewVisualizationCode()">Preview
                                                    </button>
                                                </td>
                                            </tr>
                                            </tbody>
                                        </table>
                                    </div>
                                </div>
                            </div>
                            <div class="col s6 m6 l6">
                                <label id="mlai_previewChartLabel" class="center-align" >Indicator Preview (small dataset)</label>
                                <br/>
                                <div id="mlai_graphLoaderSpinner" class="preloader-wrapper big active graphLoader">
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
                                <div id="mlai_chart_wrap" >
                                    <div id="mlai_preview_chart"></div>
                                </div>
                            </div>
                        </div>
                    </div>
                </li>
            </ul>
            <div class="col s12 m6 l10 right-align">
                <button class="btn waves-effect waves-light light-blue tooltipped" type="button" data-position="top" data-delay="50" data-tooltip="Cancel current indicator."
                        name="mlai_cancelIndicator" id="mlai_cancelIndicator" value="Cancel Indicator">Cancel
                </button>
                <button class="btn waves-effect waves-light light-blue tooltipped" type="button" data-position="top" data-delay="50" data-tooltip="Save current indicator."
                        id="mlai_saveIndicator" name="mlai_saveIndicator" value="Finalize Settings">Associate
                </button>
            </div>
        </div>
    </div>
</div>
