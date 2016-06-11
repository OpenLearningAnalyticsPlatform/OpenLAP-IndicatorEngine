<div id="loadIndicatorTemplateModel" class="modal modal-fixed-footer">
    <div class="modal-content">
        <h4>Load Indicator Template</h4>
        <div class="row">
            <div class="col-md-12">
                <div id="loadIndicatorTemplateModelTable">
                    <table id="indicatorData" class="display" cellspacing="0" width="100%">
                        <thead>
                        <tr>
                            <th>Indicator ID</th>
                            <th>Indicator Name</th>
                            <%--<th>Short Name</th>--%>
                        </tr>
                        </thead>
                    </table>
                </div>
                <br/>
            </div>
        </div>
        <div id="IndPropsFromDB"></div>
    </div>
    <div class="modal-footer">
        <button class="modal-close waves-effect waves-light btn light-blue darken-2" title="Click to use the selected Indicator as a template." onclick="loadFromTemplate()" >
            Load
        </button>
    </div>
</div>