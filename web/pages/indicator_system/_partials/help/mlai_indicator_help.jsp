<div id="mlaiIndicatorHelpModel" class="modal modal-fixed-footer openlap-help-model">
    <div class="modal-content">
        <h4>Multi Level Analysis (MLA) Indicator</h4>
        <div class="row">
            <div class="col-md-12 openlap-help">
                <p >MLA indicators are useful in situations where the raw data needs to be processed before analyzing it. E.g. if you want to group students based on their learning material views and average grades then you need to first calculate learning material views and average grades of each user. After that you can apply some clustering method to group them. In order to generate MLA indicator, the following steps are taken:</p>

                <ul class="collapsible" data-collapsible="accordion">
                    <li>
                        <div id="mlai_help_first" class="light-blue lighten-5 collapsible-header"><b>Step 1 - Define First Level Anlaysis</b></div>
                        <div class="collapsible-body panel-body">
                            <p>The first step consists of two part:</p>

                            <ul class="collapsible" data-collapsible="accordion">
                                <li>
                                    <div class="grey lighten-4 collapsible-header">Define First Level Anlaysis</div>
                                    <div class="collapsible-body panel-body">
                                        <ul class="collection">
                                            <p>Specify the dataset parameters that will be used to define the data for this analysis.</p>
                                            <li class="collection-item">
                                                <ul>
                                                    <li><p ><b>- Source</b>: From which applications the data should be used, such as L2P, Moodle, etc.</p></li>
                                                    <li><p ><b>- Platform</b>: From which technology platforms the data should be used, such as mobile device, web based, or stationary device</p></li>
                                                    <li><p ><b>- Action</b>: What kind of learning activities should be considered in dataset? such as view, update, post, start, end, etc. </p></li>
                                                    <li><p ><b>- Category</b>: the category specifies the modules in learning environment for which data should be used, such as Wiki, Discussion forum, Learning Materials.</p></li>
                                                </ul>
                                            </li>

                                            <p style="margin-top:10px">Apply different filters to the basic dataset defined in last step. Two types of filters can be applied, namely "Attribute" and "Time".</p>
                                            <li class="collection-item">
                                                <ul>
                                                    <li>
                                                        <p><b>- Attribute</b>: the attribute filters are applied on the additional parameters of the dataset based on the selected categories. If multiple categories are selected, the additional parameters common to all selected categories will be available. Select an appropriate attribute, search for the possible values and add the required value(s) as a filter.</p>
                                                        <p>E.g. "Course ID" is used to specify the required courseroom id and "Title" is used to specify the name of the item. </p>
                                                    </li>
                                                    <li>
                                                        <p ><b>- Time</b>: the time filter specifies the starting and/or ending dates for which the data should be used. Select Date Type, specify the date and apply the required date filter.</p>
                                                    </li>
                                                </ul>
                                            </li>

                                            <p style="margin-top:10px">Select appropirate analytics method to analyze the filtered dataset.</p>
                                            <li class="collection-item">
                                                <ul>
                                                    <li>
                                                        <p ><b>- Analytics Method:</b> Here you have to select the analytics method which will be used to analyze the filtered dataset defined in previous steps. More detailed description of the selected analytics method is shown next to the dropdown.</p>
                                                    </li>
                                                    <li>
                                                        <p ><b>- Mappings:</b> Here you have to specify which dataset column should be used as an input for the analytics method. The dataset columns are divided into two groups, first group above the divider contains columns of datasets that are common for all categories whereas the group below the divider contains columns specific to category selected or common columns if multiple categories are selected.</p>
                                                        <p >E.g. the "Count N most occurring items" analytics method expects one input "Items" of type String. Depending on what you want to count you can map the column. Such as, if you want to count the top 10 learning materials then you can map "Title" column to the input which contains the names of learning materials, if you want to get most viewed file types you can map "File Extension" column to the input, and if you have selected multiple categories then you can select "Category" column to see which selected categories are view most.</p>
                                                        <div class="center-align">
                                                            <img style="width:400px;" src="\images\help\method-mapping.png"/>
                                                        </div>
                                                    </li>
                                                </ul>
                                            </li>
                                        </ul>
                                    </div>
                                </li>
                                <li>
                                    <div class="grey lighten-4 collapsible-header">Combine Outputs</div>
                                    <div class="collapsible-body panel-body">
                                        <p>After defining multiple first level analysis, their outputs need to be combined before they can be passed as input to the second level analysis. The following steps are performed to combine the outputs of the analysis.</p>
                                        <ul>
                                            <li><p ><b>- Select two analysis</b>: The combining of two analysis is performed at a time.</p></li>
                                            <li>
                                                <p ><b>- Select fields with common data</b>: All output fields of the two selected analysis will be listed. Select one output from each analysis that contains the same data.</p>
                                            </li>
                                            <li><p ><b>- Combine</b>: Clicking the combine button will combine the selected analysis. The two selected analysis will be removed and a new combined analysis will be added which will be selected by default to combine it with more analysis.</p></li>
                                        </ul>

                                        <div class="center-align">
                                            <img style="width:400px;" src="\images\help\mlaids-combine.png"/>
                                        </div>
                                    </div>
                                </li>
                            </ul>
                        </div>
                    </li>

                    <li>
                        <div id="mlai_help_second" class="light-blue lighten-5 collapsible-header"><b>Step 2 - Define Second Level Anlaysis</b></div>
                        <div class="collapsible-body panel-body">
                            <p >The second step is to analyze the output of combined analysis generated in the previous step. </p>

                            <ul class="collection">
                                <li class="collection-item"></li>
                                <li class="collection-item">
                                    <p ><b>Second Level Analytics Method:</b> Here you have to select the second level analytics method which will be used to analyze the output of combined analysis defined in previous steps. More detailed description of the selected analytics method is shown next to the dropdown.</p>
                                </li>
                                <li class="collection-item">
                                    <p ><b>Mappings:</b> Here you have to specify which output of the combined analysis should be used as an input for the second level analytics method.</p>

                                    <p ><b>E.g.</b></p>
                                    <div class="center-align">
                                        <img style="width:400px;" src="\images\help\method-mapping.png"/>
                                    </div>
                                </li>
                                <li class="collection-item"></li>
                            </ul>
                        </div>
                    </li>
                    <li>
                        <div id="mlai_help_visualize" class="light-blue lighten-5 collapsible-header"><b>Step 3 - Visualize</b></div>
                        <div class="collapsible-body panel-body">
                            <p >The third and final step is to visualize the second level analysis data. </p>

                            <ul class="collection">
                                <li class="collection-item"></li>
                                <li class="collection-item">
                                    <p ><b>Visualization Library:</b> Here you have to select the visualization library with which you want to visualize the analyzed data. Such as, Google Charts, C3/D3.js.</p>
                                </li>
                                <li class="collection-item">
                                    <p ><b>Visualization Type:</b> based on the selected visualization library, the list of available visualization types is provided here. Such as, Bar Chart, Stacked Bar Chart, Grouped Area Chart.</p>
                                </li>
                                <li class="collection-item">
                                    <p ><b>Mappings:</b> Here you have to specify which output of the analytics method should go into which input of the visualization type. </p>
                                    <p ><b>E.g.</b></p>
                                    <div class="center-align">
                                        <img style="width:400px;" src="\images\help\vis-mapping.png"/>
                                    </div>
                                </li>
                                <li class="collection-item"></li>
                            </ul>
                        </div>
                    </li>
                </ul>

                <br>
                <span class="green-text text-darken-4"><b>Future Plans</b></span>
                <p>
                    1. Automatic detection of the columns which contains the similar information based on the inputs, suggest the merging and perform it with one click.
                </p>
            </div>
        </div>
    </div>
    <div class="modal-footer">
        <button class="modal-close waves-effect waves-light btn light-blue darken-2" title="Close the dialog box." >
            Close
        </button>
    </div>
</div>