<div id="compositeIndicatorModel" class="modal modal-fixed-footer">
    <div class="modal-content">
        <h4>Composite Indicator</h4>
        <div class="table-responsive">
            <div id="compositeIndicatorModelContentDesc">
                You can combine various Indicators to save as a new composite Indicator. You can define only one composite Indicator at one time.
                Please select the indicators and also fill out other details like Name, graphing type etc. The New Composite Indicator will be available
                in Memory and You can view that in "Question Summary" Properties window.
            </div>
            <div id ="runIndMem"></div>
            <div id="compositeIndicatorModelContentControls">
                <div class="col-md-6">
                    <input type="text" class="form-control" placeholder="Type the New Composite Indicator Name"
                           title="Type the New Composite Indicator Name" name ="compositeIndName" id="compositeIndName" />
                    <br/>
                    <select class="form-control" id="compositeGraphType">
                        <option value="Pie">Pie</option>
                        <option value="Bar">Bar</option>
                    </select>
                    <br/>
                    <select class="form-control"  id="compositeGraphEngine">
                        <option value="JGraph">JGraph</option>
                    </select>
                    <br/>
                </div>
            </div>
        </div>
    </div>
    <div class="modal-footer">
        <button class="modal-action modal-close waves-effect waves-light btn light-blue darken-2" id="CompositeIndButton" name="CompositeIndButton" onclick="addCompositeIndicator()" >
            Add
        </button>
        <button class="modal-action modal-close waves-effect waves-light btn light-blue darken-2" id="CompositeClosedButton">
            Close
        </button>
    </div>
</div>