<div id="editorHelpModel" class="modal modal-fixed-footer openlap-help-model">
    <div class="modal-content">
        <h4>OpenLAP - Indicator Editor</h4>
        <div class="row">
            <div class="col-md-12 openlap-help">
                <p>Indicator Editor is the UI for OpenLAP that provides users with the option to generate and customize their personalized analysis in the form of indicators. It follows the Goal, Question, and Indicator (GQI) approach to structure the analysis.</p>
                <ul style="padding-left:20px;">
                    <li><b>Goal</b> is the main objective of the analysis.</li>
                    <li><b>Question</b> specify the main analytics issue for which the user wants to perform the analysis.</li>
                    <li><b>Indicator</b> is the final visualization of analyzed data that answers the specified question. There can be multiple indicators answering a single question.</li>
                </ul>

                <p>There are three different types of indicators that the user can be generated. Their conceptual flow is shown in the figure below.</p>
                <div class="center-align">
                    <img style="width:70%;" src="\images\help\indicator-types.png"/>
                </div>

                <br>
                <b>Basic Indicator</b>
                <p>It is the most simple and commonly used indicator. The user defines the basic indicator by following the steps:</p>
                <ul style="padding-left:20px;">
                    <li>- Defines the dataset</li>
                    <li>- Apply various filters</li>
                    <li>- Select the analytics method and specify which columns of the dataset should be used as input for the analytics method.</li>
                    <li>- Select an appropriate visualization and specify which outputs of the analytics method should go to which inputs of the visualization.</li>
                    <li>- Preview the indicator and save it.</li>
                    <li>- Get the Indicator Request Code of each indicator after saving the question. This code can be embedded in any HTML page.</li>
                    <li>- More details available in the basic indicator section.</li>
                </ul>

                <br>
                <b>Composite Indicator</b>
                <p>As the name suggests, it is a composition of multiple basic indicators.</p>
                <ul style="padding-left:20px;">
                    <li>- Only those basic indicators that are associated with the current question can be combined.</li>
                    <li>- The dataset and the filters of each basic indicator can be different but the analytics methods should be the same as shown in the above figure.</li>
                    <li>- The outputs of all basic indicator analysis are combined by appending the data and adding an additional column that specifies the name of the basic indicator.</li>
                    <li>- More details available in the composite indicator section.</li>
                </ul>

                <br>
                <b>Multi Level Analysis Indicator</b>
                <p>These are advance indicators which perform analysis at two levels as shown in the above figure.</p>
                <ul style="padding-left:20px;">
                    <li>- First, the user defines multiple First Level Analysis which is the same as defining the basic indicator without visualization.</li>
                    <li>- All First Level Analysis should have one output that contains the similar information. This output will be used to merge the analyzed data of all First Level Analysis.</li>
                    <li>- The merged analyzed data of First Level Analays will act as the input for the Second Level Analysis.</li>
                    <li>- More details available in the multi-level analysis indicator section.</li>
                </ul>
            </div>
        </div>
    </div>
    <div class="modal-footer">
        <button class="modal-close waves-effect waves-light btn light-blue darken-2" title="Close the dialog box." >
            Close
        </button>
    </div>
</div>