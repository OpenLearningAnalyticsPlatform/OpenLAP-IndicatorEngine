<div class="col s12 m6 card">
    <div class="panel panel-default">
        <div class="panel-heading light-blue darken-2">Goal</div>
        <div class="panel-body">
            <div class="row">
                <div class="col-md-6">
                    <label for="GoalSelection">Select Goal </label>
                    <form:select class="form-control" title="Please select a goal." path="selectedPlatform" name ="GoalSelection" id="GoalSelection" onfocus="this.selectedIndex = -1;">
                    </form:select>
                </div>
            </div>
            <div class="col s12 m6 l10 right-align">
                <button class="waves-effect waves-light btn pull-right light-blue darken-2" type="button" title="Click to create new question." onclick="resetSession()">
                    New Question
                </button>
            </div>

        </div>
    </div>
</div>