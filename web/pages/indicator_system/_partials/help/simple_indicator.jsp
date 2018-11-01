<div id="simpleIndicatorHelpModel" class="modal modal-fixed-footer openlap-help-model">
    <div class="modal-content">
        <h4>Basic Indicator</h4>
        <div class="row">
            <div class="col-md-12 openlap-help">
                <p >Generating a new basic indicator is a four step process:</p>

                <ul class="collapsible" data-collapsible="accordion">
                <li>
                    <div id="simple_help_dataset" class="light-blue lighten-5 collapsible-header"><b>Step 1 - Define dataset</b></div>
                    <div class="collapsible-body panel-body">
                        <p >The first step is to specify the dataset parameters that will be used to define the data for the indicator. Each parameter support multiple selections.</p>

                        <ul class="collection">
                            <li class="collection-item"></li>
                            <li class="collection-item"><p ><b>Source</b>: From which applications the data should be used, such as L2P, Moodle, etc.</p></li>
                            <li class="collection-item"><p ><b>Platform</b>: From which technology platforms the data should be used, such as mobile device, web based, or stationary device</p></li>
                            <li class="collection-item">
                                <p ><b>Action</b>: What kind of learning activities should be considered in dataset? such as view, update, post, reply, start, end, etc. </p>
                                <p ><b>E.g.</b> if you want to see how many time the Learning Materials has been viewed then select View or if you want to see how many time the wiki page has been changed then select Update.</p>
                            </li>
                            <li class="collection-item"><p ><b>Category</b>: the category specifies the modules in learning environment for which data should be used, such as Wiki, Discussion forum, Learning Materials.</p></li>
                            <li class="collection-item"></li>
                        </ul>
                    </div>
                </li>
                <li>
                    <div id="simple_help_filter" class="light-blue lighten-5 collapsible-header"><b>Step 2 - Apply filters</b></div>
                    <div class="collapsible-body panel-body">

                        <p >The second step is to apply different filters to the basic dataset defined in the last step. Two types of filters can be applied, namely "Attribute" and "Time".</p>

                        <ul class="collection">
                            <li class="collection-item"></li>
                            <li class="collection-item">
                                <p ><b>Attribute</b>: the attribute filters are applied on the additional parameters of the dataset based on the selected categories. If multiple categories are selected, the additional parameters common to all selected categories will be available. Select an appropriate attribute, search for the possible values and add the required value(s) as a filter. </p>
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
                                <p ><b>E.g.</b> the "Count N most occurring items" analytics method expects one input "Items" of type String. Depending on what you want to count you can map the column. Such as, if you want to count the top 10 learning materials then you can map "Title" column to the input which contains the names of learning materials, if you want to get most viewed file types you can map "File Extension" column to the input, and if you have selected multiple categories then you can select "Category" column to see which selected categories are viewed most.</p>
                                <div class="center-align">
                                    <img style="width:400px;" src="\images\help\method-mapping.png"/>
                                </div>
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
                                <p ><b>Visualization Library:</b> Here you have to select the visualization library with which you want to visualize the analyzed data. Such as Google Charts, C3/D3.js.</p>
                            </li>
                            <li class="collection-item">
                                <p ><b>Visualization Type:</b> Based on the selected visualization library, the list of available visualization types is provided here. Such as Bar Chart, Stacked Bar Chart, Grouped Area Chart.</p>
                            </li>
                            <li class="collection-item">
                                <p ><b>Mappings:</b> Here you have to specify which output of the analytics method should go into which input of the visualization type. </p>
                                <p ><b>E.g.</b> The "Count N most occurring items" analytics method returns two outputs "Item Count" of type "Numeric" and "Item Names" of type "Text". This can be visualized with almost all visualization types, such as "Bar Chart" which accepts "X-Axis Items" of "Text" type and "Y-Axis Values" of "Numeric" type. Thus "Item Count" can be mapped to "Y-Axis Values" and "Item Names" can be mapped to "X-Axis Items".</p>
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
                    1. Apply the already applied filters while search for new attribute values.<br>
                    2. Button to automatically generate obvious mappings e.g. Timestamp.<br>
                    3. Provide the list of all visualization types available in OpenLAP and allow users to select visualization types before selecting visualization library. This will help when searching for a specific visualization type that might not be available in all visualization libraries.
                </p>
            </div>
        </div>
    </div>
    <div class="modal-footer">
        <button class="modal-close waves-effect waves-light btn light-blue darken-2 tooltipped" data-position="bottom" data-delay="50" data-tooltip="Close help dialog" >
            Close
        </button>
    </div>
</div>