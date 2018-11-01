<div id="loadQuestionTemplateModel" class="modal modal-fixed-footer">
    <div class="modal-content">
        <h4>Load Question Template</h4>
        <div class="row">
            <div class="col-md-12">
                <div id="loadIndicatorTemplateModelTable">
                    <table id="questionData" class="display" cellspacing="0" width="100%">
                        <thead>
                        <tr>
                            <th>ID</th>
                            <th>Name</th>
                            <th>No of Associated Indicators</th>
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
        <button class="modal-close waves-effect waves-light btn light-blue darken-2 tooltipped" data-position="bottom" data-delay="50" data-tooltip="Load selected question as a template" onclick="loadQuestionFromTemplate()" >
            Load
        </button>
    </div>
</div>