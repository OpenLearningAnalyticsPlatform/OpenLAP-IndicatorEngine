<div id="compIndicatorHelpModel" class="modal modal-fixed-footer">
    <div class="modal-content">
        <h4>How to generate a composite indicator</h4>
        <div class="row">
            <div class="col-md-12 openlap-help">
                    <p >Multiple indicators can be combined to form an composite Indicator. Generating a composite indicator is a two step process:</p>

                    <ul class="collapsible" data-collapsible="expandable">
                    <li>
                        <div class="light-blue lighten-5 collapsible-header"><b>Step 1 - Select indicators</b></div>
                        <div class="collapsible-body panel-body">
                            <p >The first step is to select indicators which which uses the same analytics method. In order to provide appropriate help, the after selecting teh first indicators, OpenLAP will inform which indicators can be combined and which cannot by using green and red colors respectively</p>
                        </div>
                    </li>
                    <li>
                        <div class="light-blue lighten-5 collapsible-header"><b>Step 2 - Visualize</b></div>
                        <div class="collapsible-body panel-body">
                            <p >The second step is to visualize the composite indicator.</p>

                            <p ><b>Visualization Library:</b> Here you have to select the visualization library with you want visualize the analyzed data. </p>
                            <p >E.g. Google Charts, C3/D3.js.</p>

                            <p ><b>Visualization Type:</b> based on the selected visualization library, the list of available visualization types is provided here. </p>
                            <p >E.g. Bar Chart, Stacked Bar Chart, Grouped Area Chart.</p>

                            <p ><b>Mappings:</b> Here you have to specify which output of the analytics method should go into which input of the visualization type. Contarary to simple indicator, the composite indicator provide additional output from the analytics method which gives the names of selected indicators.</p>
                        </div>
                    </li>
                </ul>
            </div>
        </div>
    </div>
    <div class="modal-footer">
        <button class="modal-close waves-effect waves-light btn light-blue darken-2" title="Close the dialog box." >
            Close
        </button>
    </div>
</div>