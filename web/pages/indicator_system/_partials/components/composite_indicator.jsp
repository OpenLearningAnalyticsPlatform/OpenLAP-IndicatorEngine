<div id="comp_indicatorDefinition" class="col s12 m6 card">
    <div class="panel panel-default">
        <div class="panel-heading light-blue darken-2">
            Composite Indicator
            <a class="tooltipped modal-trigger orange-text" data-html="true" data-position="bottom" data-delay="50" id="compIndicatorHelp" href="#compIndicatorHelpModel" data-tooltip="How to define a composite indicator">
            <i class="material-icons">help</i>
        </a>
        </div>
        <div class="panel-body">
            <ul class="collapsible" data-collapsible="expandable">
                <li>
                    <div class="light-blue lighten-5 collapsible-header active">Information</div>
                    <div class="collapsible-body panel-body">
                        <div class="row">
                            <div class="col-md-6 input-field">
                                <input type="text" class="form-control" name ="comp_indicatorNaming"
                                       id="comp_indicatorNaming" placeholder="Type your Indicator Name"/>
                                <label for="comp_indicatorNaming"title="Indicator Name">Indicator Name </label>
                            </div>
                        </div>
                    </div>
                </li>
                <li>
                    <div class="light-blue lighten-5 collapsible-header active">Available Indicators</div>
                    <div class="collapsible-body panel-body">
                        <div class="select-desc hide-on-small-only">
                            <span>Select two or more indicators which you would like to combine to form composite indicator.</span>
                        </div>
                        <div id="comp_IndicatorContent"></div>
                    </div>
                </li>
                <li id="comp_VisualizationSection">
                    <div class="light-blue lighten-5 collapsible-header active">Visualization</div>
                    <div class="collapsible-body panel-body">
                        <div class="row">
                            <div class="col s6 m6 l6">
                                <div class="row">
                                    <label for="comp_EngineSelect" title="Graph library">Visualization Library </label>
                                    <select class="browser-default" name ="comp_EngineSelect" id="comp_EngineSelect" title="Select Graph Library for Visualization" onchange="populateCompVisualizationMethods();">
                                    </select>
                                </div>
                                <div class="row">
                                    <div id="comp_selectedChartTypeSpinner" class="preloader-wrapper small active" style="left:220px;">
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
                                    <label for="comp_selectedChartType" title="Graph Type">Visualizationaph Type </label>
                                    <select class="browser-default" name ="comp_selectedChartType" id="comp_selectedChartType" title="Select Graph type for Visualization" onchange="getCompVisualizationMethodInputs();"></select>
                                </div>
                                <div class="row">
                                    <br/>
                                    <div class="col s12 m12 l12">
                                        <div id="comp_inputForVisualizerSpinner" class="preloader-wrapper small active" style="left:140px;">
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
                                        <label for="comp_outputForMethods" title="Output for Method columns">Output for Methods</label>
                                        <select class="form-control browser-default" title="You can select Method Outputs in here"
                                                name ="comp_outputForMethods" id="comp_outputForMethods" size="4">
                                        </select>
                                    </div>
                                    <div class="col s6 m6 l6">
                                        <label for="comp_inputForVisualizer" title="Input for Visualizer">Input for Visualization</label>
                                        <select class="form-control browser-default"
                                                name ="comp_inputForVisualizer" id="comp_inputForVisualizer" size="4"></select>
                                        <br/>
                                        <div class="right-align">
                                            <button class="btn waves-effect waves-light light-blue" type="button"
                                                    id="comp_addVisualizationMapping" name="addVisualizationMapping" value="Add Mapping" onclick="addCompVisualizationMappingToTable();">Add
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
                                        <table id="comp_visualizerMappingTable" class="centered">
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
                                                    <span id="comp_preview_msg" class="preview-err-msg"></span>
                                                </td>
                                                <td>
                                                    <button class="btn waves-effect waves-light light-blue" type="button" title="Click to generate Graph."
                                                            name="generateGraph" id="comp_generateGraph" value="Generate Graph" onclick="getCompIndicatorPreview()">Preview
                                                    </button>
                                                </td>
                                            </tr>
                                            </tbody>
                                        </table>
                                    </div>
                                </div>
                            </div>
                            <div class="col s6 m6 l6">
                                <label id="comp_previewChartLabel" class="center-align" title="Indicator Preview">Indicator Preview (small dataset)</label>
                                <br/>
                                <div id="comp_graphLoaderSpinner" class="preloader-wrapper big active graphLoader">
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
                                <div id="comp_chart_wrap" title="Graph Preview">
                                    <div id="comp_preview_chart"></div>
                                </div>
                            </div>
                        </div>
                    </div>
                </li>
            </ul>
            <div class="col s12 m6 l10 right-align">
                <button class="btn waves-effect waves-light light-blue" type="button" title="Cancel the Indicator."
                        name="comp_CloseButton" id="comp_CloseButton" value="Cancel Indicator">Cancel
                </button>
                <button class="btn waves-effect waves-light light-blue" type="button" title="Save the Indicator."
                        id="comp_AddButton" name="comp_AddButton" value="Finalize Indicator" onclick="finalizeCompositeIndicator()">Save
                </button>
            </div>
        </div>
    </div>
</div>