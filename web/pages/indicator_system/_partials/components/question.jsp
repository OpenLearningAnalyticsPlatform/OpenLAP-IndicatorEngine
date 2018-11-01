<div class="col s12 m6 card">
    <div class="panel panel-default">
        <div class="panel-heading light-blue darken-2">
            Question
            <a class="modal-trigger amber-text openlap-help-icon tooltipped" id="questionHelp" data-position="right" data-delay="50" data-tooltip="Click to see help related to formulating a question"
               href="#questionHelpModel" >
                <i class="material-icons">help_outline</i>
            </a>
        </div>
        <div class="panel-body">
            <div class="row">
                <div class="col m6 input-field">
                    <%--<div class="col m10">--%>
                        <%--<form:input path="questionsContainer.questionName" type="text" class="form-control" name ="questionNaming"--%>
                                    <%--id="questionNaming" required="required" placeholder="Type your Question Name"/>--%>
                        <input type="text" class="form-control" name ="questionNaming"
                                    id="questionNaming" required="required" placeholder="Enter your analytics question."/>
                        <label for="questionNaming">Question</label>
                    <%--</div>--%>
                    <%--<div class="col m2 searchIcon">--%>
                        <%--<div class="valign-wrapper">--%>
                            <%--<a class="valign modal-trigger" id="searchQuestions" href="#loadQuestionTemplateModel" title="Search Existing Questions">--%>
                                <%--<i class="material-icons">search</i>--%>
                            <%--</a>--%>
                        <%--</div>--%>
                    <%--</div>--%>
                </div>
            </div>
            <div class="row">
                <div class="col s12 m8 l6">
                    <div class="col s7 m5 l5" id="associatedIndicatorsLabelDiv">
                        <label for="associatedIndicatorsDiv">Associated Indicators </label>
                    </div>
                    <div>
                        <a class="btn-floating light-blue darken-2 tooltipped" id="addIndicator" data-position="bottom" data-delay="50" data-tooltip="Create Basic Indicator">
                            <i class="material-icons">add</i>
                        </a>
                        <a class="btn-floating green darken-2 tooltipped question-actions" id="loadIndicatorTemplate" onclick="LoadExistingIndicator()" data-position="bottom" data-delay="50" data-tooltip="Load Existing Indicator">
                            <i class="material-icons">file_download</i>
                        </a>

                        <a class="btn-floating yellow darken-2 tooltipped question-actions" id="compositeIndicator" data-position="bottom" data-delay="50" data-tooltip="Create Composite Indicator">
                            <i class="material-icons">view_column</i>
                        </a>
                        <a class="btn-floating red darken-2 tooltipped question-actions" id="addmlaiIndicator" data-position="bottom" data-delay="50" data-tooltip="Add Multi Level Analysis Indicator">
                            <i class="material-icons">view_quilt</i>
                        </a>
                    </div>
                    <%--<div class="col s5 m2 l1" id="floatingButtonDiv">--%>
                        <%--<div class="fixed-action-btn horizontal" style="position: relative; display: inline-block; right: 0px; top: 0px;">--%>
                            <%--&lt;%&ndash;<a class="btn-floating light-blue darken-2" id="addIndicator" onclick="addNewIndicator()" title="Add New Indicator">&ndash;%&gt;--%>
                            <%--<a class="btn-floating light-blue darken-2 tooltipped" id="addIndicator" data-position="bottom" data-delay="50" data-tooltip="Create Basic Indicator">--%>
                                <%--<i class="material-icons">add</i>--%>
                            <%--</a>--%>
                            <%--<ul>--%>
                                <%--<li>--%>
                                    <%--&lt;%&ndash;<a class="btn-floating yellow darken-1 modal-trigger" id="loadIndicatorTemplate" href="#loadIndicatorTemplateModel" onclick="LoadExistingIndicator()" title="Load Existing Indicator">&ndash;%&gt;--%>
                                    <%--<a class="btn-floating green darken-2 tooltipped" id="loadIndicatorTemplate" onclick="LoadExistingIndicator()" data-position="bottom" data-delay="50" data-tooltip="Load Existing Indicator">--%>
                                        <%--<i class="material-icons">file_download</i>--%>
                                    <%--</a>--%>
                                <%--</li>--%>
                                <%--<li>--%>
                                    <%--<a class="btn-floating yellow darken-2 tooltipped" id="compositeIndicator" data-position="bottom" data-delay="50" data-tooltip="Create Composite Indicator">--%>
                                        <%--<i class="material-icons">view_column</i>--%>
                                    <%--</a>--%>
                                <%--</li>--%>
                                <%--&lt;%&ndash;MLAI button&ndash;%&gt;--%>
                                <%--<li>--%>
                                    <%--<a class="btn-floating red darken-2 tooltipped" id="addmlaiIndicator" data-position="bottom" data-delay="50" data-tooltip="Add Multi Level Analysis Indicator">--%>
                                        <%--<i class="material-icons">view_quilt</i>--%>
                                    <%--</a>--%>
                                <%--</li>--%>
                            <%--</ul>--%>
                        <%--</div>--%>
                    <%--</div>--%>
                </div>
                <div class="col s9 m9 l9">
                    <div id="associatedIndicatorsDiv"></div>
                </div>
            </div>
            <div class="col s12 m6 l10 right-align">
                <button class="waves-effect waves-light btn modal-trigger light-blue tooltipped" type="button" href="#visualizeQuestionModel" data-position="bottom" data-delay="50" data-tooltip="Click to visualize the question."
                        name="visualizeQuestion" id="visualizeQuestion" onclick="QuestionVisualize()">Visualize
                </button>
                <button class="waves-effect waves-light btn modal-trigger light-blue tooltipped" type="button" name="QuestionSave" id="saveQuestion" href="#visualizeQuestionModel" data-position="bottom" data-delay="50" data-tooltip="Click to save the question and associated indicators."
                        value="Save Question" onclick="SaveQuestionIndicators()">Save
                </button>
            </div>
        </div>
    </div>
</div>