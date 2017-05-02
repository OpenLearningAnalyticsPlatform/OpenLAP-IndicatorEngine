<div id="mlai_indicatorDefinition" class="col s12 m6 card">
    <div class="panel panel-default">
        <div class="panel-heading light-blue darken-2">Multi Level Analysis Indicator</div>
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
                                            <label for="mlai_indicatorNaming"title="Indicator Name">Indicator Name </label>
                                        </div>
                                    </div>
                                </tr>
                            </tbody>
                        </table>
                    </div>
                </li>
                <li>
                    <div class="light-blue lighten-5 collapsible-header">Analysis</div>
                    <div class="collapsible-body panel-body">
                        <div class="row">
                            <div class="col s12 m8 l6">
                                <div class="action-btn horizontal add-datasource-div">
                                    <%--<a class="btn-floating light-blue darken-2" id="addIndicator" onclick="addNewIndicator()" title="Add New Indicator">--%>
                                    <a class="btn-floating modal-trigger light-blue darken-2 add-datasource-btn" id="mlai_addFirstMethod" title="Add New first level analytics method" href="#addFirstMethodModal">
                                        <i class="material-icons add-datasource-label">add</i>
                                    </a>
                                    <a class="btn-floating yellow darken-1 add-datasource-btn" id="mlai_loadIndicatorTemplate" onclick="LoadExistingIndicator()" title="Load Existing Indicator">
                                        <i class="material-icons add-datasource-label">file_download</i>
                                    </a>
                                    <a class="btn-floating red modal-trigger add-datasource-btn" id="mlai_compositeIndicator" onclick="LoadIndicatorVisualizations()" href="#compositeIndicatorModel" title="Combine Existing Indicators">
                                        <i class="material-icons add-datasource-label">view_quilt</i>
                                    </a>
                                </div>
                                <label id="mlai_firstMethodLabel" title="First Level Analytics Methods">First Level Analytics Methods</label>
                            </div>
                            <div class="col s12 m12 l12">
                                <div id="mlai_firstMethodDiv"></div>
                            </div>
                        </div>

                        <div class="row">
                            <div class="col s12 m8 l6">
                                <label id="mlai_combineMethodsLabel" title="First Level Analytics Methods">Combining outputs of analytics methods</label>
                            </div>
                            <div class="col s12 m12 l12">
                                <div id="mlai_combineMethodDiv"></div>
                            </div>
                        </div>

                        <div class="row">
                            <div class="col s12 m6 l6">
                                <label for="mlai_analyticsMethod" title="Analytics Method">Second level Analytics Method</label>
                                <select class="browser-default" title="Please select a method." name ="mlai_analyticsMethod" id="mlai_analyticsMethod"
                                        onchange="getMLAIAnalyticsMethodInputs();"></select>
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
                                            name ="mlai_methodDataColumns" id="mlai_methodDataColumns" size="4">
                                    </select>
                                </div>
                                <div class="col s6 m6 l6">
                                    <label for="mlai_inputForMethods" title="Input values fro Methods">Inputs for Method</label>
                                    <select class="form-control browser-default" size="6"
                                            name ="mlai_inputForMethods" id="mlai_inputForMethods" size="4"></select>
                                </div>
                            </div>
                        </div>
                        <div class="row">
                            <div class="col s12 m6 l6 right-align">
                                <button class="btn waves-effect waves-light light-blue" type="button"
                                        id="mlai_addMethodMapping" name="mlai_addMethodMapping" value="Add Mapping" onclick="addMLAIMethodMappingToTable();">Add
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
                <li>
                    <div class="light-blue lighten-5 collapsible-header">Visualization</div>
                    <div class="collapsible-body panel-body">
                        <div class="row">
                            <div class="col s6 m6 l6">
                                <div class="row">
                                    <label for="mlai_EngineSelect" title="Graph library">Visualization Library </label>
                                    <select class="browser-default" name ="mlai_EngineSelect" id="mlai_EngineSelect" title="Select Graph Library for Visualization" onchange="populateMLAIVisualizationMethods();">
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
                                    <label for="mlai_selectedChartType" title="Graph Type">Visualizationaph Type </label>
                                    <select class="browser-default" name ="mlai_selectedChartType" id="mlai_selectedChartType" title="Select Graph type for Visualization" onchange="getMLAIVisualizationMethodInputs();"></select>
                                </div>
                                <div class="row">
                                    <br/>
                                    <div class="col s12 m12 l12">
                                        <div id="mlai_inputForVisualizerSpinner" class="preloader-wrapper small active" style="left:140px;">
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
                                        <label title="Mappings between Method Outputs and Visualizer Inputs">Mappings </label>
                                        <div class="divider"></div>
                                    </div>
                                    <br/>
                                </div>
                                <div class="row">
                                    <div class="col s6 m6 l6">
                                        <label for="mlai_outputForMethods" title="Output for Method columns">Output for Methods</label>
                                        <select class="form-control browser-default" title="You can select Method Outputs in here"
                                                name ="mlai_outputForMethods" id="mlai_outputForMethods" size="4">
                                        </select>
                                    </div>
                                    <div class="col s6 m6 l6">
                                        <label for="mlai_inputForVisualizer" title="Input for Visualizer">Input for Visualization</label>
                                        <select class="form-control browser-default"
                                                name ="mlai_inputForVisualizer" id="mlai_inputForVisualizer" size="4"></select>
                                        <br/>
                                        <div class="right-align">
                                            <button class="btn waves-effect waves-light light-blue" type="button"
                                                    id="mlai_addVisualizationMapping" name="mlai_addVisualizationMapping" value="Add Mapping" onclick="addMLAIVisualizationMappingToTable();">Add
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
                                        <table id="mlai_visualizerMappingTable" class="centered">
                                            <thead>
                                            <tr>
                                                <th data-field="id">Output for Methods</th>
                                                <th data-field="name">Input for Visualization</th>
                                            </tr>
                                            </thead>
                                            <tbody>
                                            </tbody>
                                        </table>
                                    </div>
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
                                                    <button class="btn waves-effect waves-light light-blue" type="button" title="Click to generate Graph."
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
                                <label id="mlai_previewChartLabel" class="center-align" title="Indicator Preview">Indicator Preview (small dataset)</label>
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
                                <div id="mlai_chart_wrap" title="Graph Preview">
                                    <div id="mlai_preview_chart"></div>
                                </div>
                            </div>
                        </div>
                    </div>
                </li>
            </ul>
            <div class="col s12 m6 l10 right-align">
                <button class="btn waves-effect waves-light light-blue" type="button" title="Cancel the Indicator."
                        name="mlai_cancelIndicator" id="mlai_cancelIndicator" value="Cancel Indicator">Cancel
                </button>
                <button class="btn waves-effect waves-light light-blue" type="button" title="Save the Indicator."
                        id="mlai_saveIndicator" name="mlai_saveIndicator" value="Finalize Settings">Save
                </button>
            </div>
        </div>
    </div>
</div>
