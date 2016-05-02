<div class="col s12 m6 card">
    <div class="panel panel-default">
        <div class="panel-heading light-blue darken-2">Question</div>
        <div class="panel-body">

            <div class="row">
                <div class="col-md-6">
                    <label for="questionNaming">Enter Question Name </label>
                    <form:input path="questionsContainer.questionName" type="text" class="form-control" name ="questionNaming"
                                id="questionNaming" onchange="validateQuestionName()" required="required"
                                placeholder="Type your Question Name" title="Question Name msut be unique & must be more than 3 characters."/>
                </div>
            </div>
            <div class="row">
                <div class="col-md-9">
                    <label for="associatedIndicatorsDiv">Associated Indicators </label>
                    <div id="associatedIndicatorsDiv"></div>
                    <div class="fixed-action-btn horizontal" style="position: absolute; display: inline-block; right: 510px; bottom: 50px;">
                        <a class="btn-floating light-blue darken-2" id="addIndicator" onclick="addNewIndicator()" title="Add Indicator">
                            <i class="material-icons">add</i>
                        </a>
                        <ul>
                            <li>
                                <a class="btn-floating yellow darken-1 modal-trigger" id="loadIndicatorTemplate" href="#loadIndicatorTemplateModel" title="Load Indicator Template">
                                    <i class="material-icons">cached</i>
                                </a>
                            </li>
                            <li>
                                <a class="btn-floating red modal-trigger" id="compositeIndicator" href="#compositeIndicatorModel" title="Add Composite Indicator">
                                    <i class="material-icons">view_quilt</i>
                                </a>
                            </li>
                        </ul>
                    </div>
                </div>
            </div>
            <div class="col s12 m6 l10 right-align">
                <button class="waves-effect waves-light btn modal-trigger light-blue darken-2" type="button" href="#visualizeQuestionModel" title="Click to visualize Question."
                        name="visualizeQuestion" id="visualizeQuestion" onclick="QuestionVisualize()">Visualize
                </button>
                <button class="waves-effect waves-light btn modal-trigger light-blue darken-2" type="button" name="QuestionSave" id="saveQuestion" href="#visualizeQuestionModel" title="Click to save the Question & all its indicators."
                        value="Save Question" onclick="SaveQuestionDB()">Save
                </button>
            </div>


        </div>
    </div>
</div>