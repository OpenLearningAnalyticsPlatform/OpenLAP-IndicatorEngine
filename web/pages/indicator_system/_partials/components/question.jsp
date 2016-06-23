<div class="col s12 m6 card">
    <div class="panel panel-default">
        <div class="panel-heading light-blue darken-2">Question</div>
        <div class="panel-body">
            <div class="row">
                <div class="col m6 input-field">
                    <div class="col m10">
                        <form:input path="questionsContainer.questionName" type="text" class="form-control" name ="questionNaming"
                                    id="questionNaming" required="required" placeholder="Type your Question Name" title="Type your Question Name"/>
                        <label for="questionNaming">Question Name </label>
                    </div>
                    <div class="col m2">
                        <div class="valign-wrapper">
                            <a class="valign modal-trigger" id="searchQuestions" href="#loadQuestionTemplateModel" title="Search Existing Questions">
                                <i class="material-icons">search</i>
                            </a>
                        </div>
                    </div>
                </div>
            </div>
            <div class="row">
                <div class="col s12 m8 l6">
                    <div class="col s7 m5 l5" id="associatedIndicatorsLabelDiv">
                        <label for="associatedIndicatorsDiv" title="Associated Indicators to the Question">Associated Indicators </label>
                    </div>
                    <div class="col s5 m2 l1" id="floatingButtonDiv">
                        <div class="fixed-action-btn horizontal" style="position: relative; display: inline-block; right: 0px; top: 0px;">
                            <a class="btn-floating light-blue darken-2" id="addIndicator" onclick="addNewIndicator()" title="Add New Indicator">
                                <i class="material-icons">add</i>
                            </a>
                            <ul>
                                <li>
                                    <%--<a class="btn-floating yellow darken-1 modal-trigger" id="loadIndicatorTemplate" href="#loadIndicatorTemplateModel" onclick="LoadExistingIndicator()" title="Load Existing Indicator">--%>
                                    <a class="btn-floating yellow darken-1" id="loadIndicatorTemplate" onclick="LoadExistingIndicator()" title="Load Existing Indicator">
                                        <i class="material-icons">cached</i>
                                    </a>
                                </li>
                                <li>
                                    <a class="btn-floating red modal-trigger" id="compositeIndicator" onclick="LoadIndicatorVisualizations()" href="#compositeIndicatorModel" title="Combine Existing Indicators">
                                        <i class="material-icons">view_quilt</i>
                                    </a>
                                </li>
                            </ul>
                        </div>
                    </div>
                </div>
                <div class="col s9 m9 l9">
                    <div id="associatedIndicatorsDiv" title="Associated Indicators to the Question"></div>
                </div>
            </div>
            <div class="col s12 m6 l10 right-align">
                <button class="waves-effect waves-light btn modal-trigger light-blue" type="button" href="#visualizeQuestionModel" title="Click to visualize Question."
                        name="visualizeQuestion" id="visualizeQuestion" onclick="QuestionVisualize()">Visualize
                </button>
                <button class="waves-effect waves-light btn modal-trigger light-blue" type="button" name="QuestionSave" id="saveQuestion" href="#visualizeQuestionModel" title="Click to save Question & all its indicators."
                        value="Save Question" onclick="SaveQuestionDB()">Save
                </button>
            </div>


        </div>
    </div>
</div>