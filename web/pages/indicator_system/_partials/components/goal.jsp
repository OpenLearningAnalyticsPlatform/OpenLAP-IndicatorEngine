<div class="col s12 m6 card">
    <div class="panel panel-default">
        <div class="panel-heading light-blue darken-2">
            Goal
            <a class="modal-trigger amber-text openlap-help-icon tooltipped" id="goalHelp" data-position="right" data-delay="50" data-tooltip="Click to see help related to selecting the learning analytics goal"
               href="#goalHelpModel" >
                <i class="material-icons">help_outline</i>
            </a>
        </div>
        <div class="panel-body">
            <div class="row">
                <div class="col s12 m6 l6">
                    <div id="goalSpinner" class="preloader-wrapper small active" style="left:70px;">
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
                    <label for="GoalSelection">Goal</label>
                    <select class="browser-default" name ="GoalSelection" id="GoalSelection"></select>
                </div>
                <div class="col m6 l6">
                    <div class="select-desc hide-on-small-only">
                        <a class="btn-floating light-blue darken-2 tooltipped" id="requestGoal" onclick="openGoalRequestModal()" data-position="bottom" data-delay="50" data-tooltip="Request new analytics goal">
                            <i class="material-icons">mail_outline</i>
                        </a>
                        <span id="GoalSelectionDesc"></span>
                    </div>
                </div>
            </div>
            <div class="col s12 m6 l10 right-align">
                <button class="waves-effect waves-light btn pull-right light-blue tooltipped" type="button" data-position="bottom" data-delay="50" data-tooltip="Start new question" onclick="resetSession()">
                    New Question
                </button>
            </div>
        </div>
    </div>
</div>