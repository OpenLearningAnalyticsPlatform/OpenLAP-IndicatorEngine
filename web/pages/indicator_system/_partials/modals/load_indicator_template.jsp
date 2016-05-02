<div id="loadIndicatorTemplateModel" class="modal modal-fixed-footer">
    <div class="modal-content">
        <h4>Load Indicator Template</h4>
        <div class="row">
            <div class="col-md-12">
                <button id="toggleLoadIndicatorTemplateModelTable" class="waves-effect waves-light btn light-blue darken-2">
                    Show Indicators
                </button>
                <br><br>

                <div id="loadIndicatorTemplateModelTable">
                    <p>Listing of All Existing Non-Composite Indicators<br><br></p>
                    <table id="indicatorData" class="display bordered" cellspacing="0" width="100%">
                        <thead>
                        <tr>
                            <th>Indicator ID</th>
                            <th>Indicator Name</th>
                            <th>Short Name</th>
                        </tr>
                        </thead>
                    </table>
                </div>
                <br/>
            </div>
        </div>
        <div class="row">
            <div class="col-md-7">
                <label > Search Type</label>
                <select class="form-control" id="searchIndType">
                    <option value="ID">ID</option>
                    <option value="IndicatorName">IndicatorName</option>
                </select>
                <br/>
                <label > Search String</label>
                <input type="text" class="form-control" placeholder="Type the Indicator Name/ID"
                       title="Type the Indicator Name/ID" name ="IndSearch" id="IndSearch" />
                <br/>
                <div class="right-align">
                    <button class="waves-effect waves-light btn light-blue darken-2" onclick="searchIndicator()" >
                        Search
                    </button>
                </div>
                <br/>
                <label > Search Results</label>
                <select class="form-control" id="searchResults"></select>
                <br/>
                <div id="IndPropsFromDB">
                </div>
            </div>
        </div>
    </div>
    <div class="modal-footer">
        <button class="waves-effect waves-light btn light-blue darken-2" title="Click to view the properties of the selected Indicator." onclick="viewIndicatorProp()" >
            View
        </button>
        <button class="modal-close waves-effect waves-light btn light-blue darken-2" title="Click to use the selected Indicator as a template." onclick="loadFromTemplate()" >
            Load
        </button>
    </div>
</div>