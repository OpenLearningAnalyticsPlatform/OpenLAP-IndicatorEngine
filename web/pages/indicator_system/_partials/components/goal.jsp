<div class="col s12 m6 card">
    <div class="panel panel-default">
        <div class="panel-heading light-blue darken-2">Goal</div>
        <div class="panel-body">
            <div class="row">
                <div class="col s12 m6 l6">
                    <label for="GoalSelection" title="Analytics Goal for Question Definition">Goal </label>
                    <select class="browser-default" name ="GoalSelection" id="GoalSelection"></select>
                </div>
                <div class="col m6 l6">
                    <div class="select-desc hide-on-small-only">
                        <a class="btn-floating light-blue darken-2" id="requestGoal" onclick="openGoalRequestModal()" title="Request new analytics goal">
                            <i class="material-icons">send</i>
                        </a>
                        <span id="GoalSelectionDesc" title="Analytics Goal description"></span>
                    </div>
                </div>
            </div>
            <div class="col s12 m6 l10 right-align">
                <button class="waves-effect waves-light btn pull-right light-blue" type="button" title="Click to create new question." onclick="resetSession()">
                    New Question
                </button>
            </div>
        </div>
    </div>
</div>