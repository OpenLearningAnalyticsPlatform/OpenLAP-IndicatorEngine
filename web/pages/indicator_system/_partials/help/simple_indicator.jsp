<div id="simpleIndicatorHelpModel" class="modal modal-fixed-footer">
    <div class="modal-content">
        <h4>How to generate a simple indicator</h4>
        <div class="row">
            <div class="col-md-12 openlap-help">
                    <p >Generating a new Indicator is a four step process:</p>

                    <ul class="collapsible" data-collapsible="accordion">
                    <li>
                        <div id="simple_help_dataset" class="light-blue lighten-5 collapsible-header"><b>Step 1 - Define dataset</b></div>
                        <div class="collapsible-body panel-body">
                            <p >The first step is to specify the dataset parameters that will be used to define the data for the indicator. Each parameter support multiple selection.</p>

                            <ul class="collection">
                                <li class="collection-item"></li>
                                <li class="collection-item"><p ><b>Source</b>: From which applications the data should be used, such as, L2P, Moodle, etc.</p></li>
                                <li class="collection-item"><p ><b>Platform</b>: From which technology platforms the data should be used, such as mobile device, web based, or stationary device</p></li>
                                <li class="collection-item">
                                    <p ><b>Action</b>: What kind of learning activities should be considered in dataset? such as, view, update, post, start, end, etc. </p>
                                    <p ><b>E.g.</b> if you want see how many time the Learningmaterials has been viewed than select View or if you want to see how many time the wiki page has been changed than select Update.</p>
                                </li>
                                <li class="collection-item"><p ><b>Category</b>: the category specifies the modules in learning environment for which data should be used, such as, Wiki, Discussion forum, Learning Materials.</p></li>
                                <li class="collection-item"></li>
                            </ul>
                        </div>
                    </li>
                    <li>
                        <div id="simple_help_filter" class="light-blue lighten-5 collapsible-header"><b>Step 2 - Apply filters</b></div>
                        <div class="collapsible-body panel-body">

                            <p >The second step is to apply different filters to the basic dataset defined in last step. Two types of filters can be applied, namely "Attribute" and "Time".</p>

                            <ul class="collection">
                                <li class="collection-item"></li>
                                <li class="collection-item">
                                    <p ><b>Attribute</b>: the attribute filters are applied on the additional parameters of the dataset based on the selected categories. If more than one category are selected than the additional parameters common to all selected categories will be available. Select an appropriate attribute, search for the possible values and add the required value(s) as filter. </p>
                                    <p ><b>E.g.</b> "Course ID" is used to specify the required courseroom id and "Title" is used to specify the name of the item. </p>
                                </li>
                                <li class="collection-item">
                                    <p ><b>Time</b>: the time filter specify the starting and/or ending dates for which the data should be used. Select Date Type, specify the date and apply the required date filter.</p>
                                </li>
                                <li class="collection-item"></li>
                            </ul>
                        </div>
                    </li>
                    <li>
                        <div id="simple_help_analysis" class="light-blue lighten-5 collapsible-header"><b>Step 3 - Analyze</b></div>
                        <div class="collapsible-body panel-body">
                            <p >The third step is to analyze the filtered dataset. </p>

                            <ul class="collection">
                                <li class="collection-item"></li>
                                <li class="collection-item">
                                    <p ><b>Analytics Method:</b> Here you have to select the analytics method which will be used to analyze the filtered dataset defined in previous steps. More detailed description of the selected analytics method is shown next to the dropdown.</p>
                                </li>
                                <li class="collection-item">
                                    <p ><b>Mappings:</b> Here you have to specify which dataset column should be used as an input for the analytics method. The dataset columns are divided into two groups, first group above the divider contains columns of datasets that are common for all categories whereas the group below the divider contains columns specific to category selected or common columns if multiple categories are selected.</p>
                                    <p ><b>E.g.</b> the "Top 10 Item Counter" analytics method expects one input "Items" of type String. Depending on what you want to count you can map the column. Such as, if you want to count the top 10 learning materials than you can map "Title" column to the input which contains the names of learning materials, if you want to get most viewed file types you can map "File Extension" column to the input, and if you have selected multiple categories than you can select "Category" column to see which selected categories are view most.</p>
                                </li>
                                <li class="collection-item"></li>
                            </ul>
                        </div>
                    </li>
                    <li>
                        <div id="simple_help_visualize" class="light-blue lighten-5 collapsible-header"><b>Step 4 - Visualize</b></div>
                        <div class="collapsible-body panel-body">
                            <p >The fourth and final step of the indicator generation is to visualize the analyzed data. </p>

                            <ul class="collection">
                                <li class="collection-item"></li>
                                <li class="collection-item">
                                    <p ><b>Visualization Library:</b> Here you have to select the visualization library with you want visualize the analyzed data. Such as, Google Charts, C3/D3.js.</p>
                                </li>
                                <li class="collection-item">
                                    <p ><b>Visualization Type:</b> based on the selected visualization library, the list of available visualization types is provided here. Such as, Bar Chart, Stacked Bar Chart, Grouped Area Chart.</p>
                                </li>
                                <li class="collection-item">
                                    <p ><b>Mappings:</b> Here you have to specify which output of the analytics method should go into which input of the visualization type. </p>
                                    <p ><b>E.g.</b> the "Top 10 Item Counter" analytics method returns two outputs "Item Count" of type INTEGER and "Item Names" of type STRING. This can be visualized with almost all visualization types, such as, "Bar Chart" which accepts "X-Axis Items" of STRING type and "Y-Axis Values" of INTEGER type. Thus "Item Count" can be mapped to "Y-Axis Values" and "Item Names" can be mapped to "X-Axis Items".</p>
                                </li>
                                <li class="collection-item"></li>
                            </ul>
                        </div>
                    </li>
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