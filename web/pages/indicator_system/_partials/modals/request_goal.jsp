<div id="goalRequestTemplateModel" class="modal modal-fixed-footer">
    <div class="modal-content">
        <h4>Request New Analytics Goal</h4>
        <div class="row">
            <div class="col-md-6">
                <label for="new-analytics-goal-name">Name</label>
                <input type="text" name ="name" id="new-analytics-goal-name"
                       required="required" placeholder="Analytics goal name" data-error="Enter analtyics goal name"/>
            </div>
        </div>
        <div class="row">
            <div class="col-md-6">
                <label for="new-analytics-goal-desc">Description</label>
                <textarea type="text" name ="desc" id="new-analytics-goal-desc" data-error="Enter analtyics goal description"
                          required="required" placeholder="Analytics goal description"  rows="4"></textarea>
            </div>
        </div>
        <div class="row">
            <div class="col-md-6">
                <div id="requestGoalMessage"></div>
            </div>
        </div>
    </div>
    <div class="modal-footer">
        <button class="waves-effect waves-light btn light-blue darken-2" title="Send request for new analytics goal." onclick="sendGoalRequest()" >
            Send
        </button>
        <button class="modal-close waves-effect waves-light btn light-blue darken-2" title="Close the dialog box." onclick="clearGoalRequestModal()" >
            Close
        </button>
    </div>
</div>