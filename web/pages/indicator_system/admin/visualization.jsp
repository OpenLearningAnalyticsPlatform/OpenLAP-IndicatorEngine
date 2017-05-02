<%@ include file="../_partials/header.jsp" %>

    <title>New Visualization Technique</title>
    <meta name="description" content="OpenLAP : Administration Panel" />

    <script type="text/javascript" src="${pageContext.request.contextPath}/js/custom/admin.js"></script>
</head>

<%@ include file="../_partials/nav_bar.jsp" %>

<div class="templatemo-content-wrapper">
    <div class="templatemo-content">
        <ol class="breadcrumb">
            <li><a class="materialize-breadcrumb" href="/home/dashboard">Dashboard</a></li>
            <li><a class="materialize-breadcrumb" href="/admin/visualization/new">Admin</a></li>
            <li><a class="materialize-breadcrumb" href="/admin/visualization/new">Visualization</a></li>
        </ol>
        <input type="hidden" name="userName" id="userName" value="${sessionScope.userName}" />
        <div class="tab-content">
            <form id="visualizationForm" enctype="multipart/form-data">
                <div class="col s12 m6 card">
                    <div class="panel panel-default">
                        <div class="panel-heading light-blue darken-2">New Visualization</div>
                        <div class="panel-body">

                            <div class="col-md-12">
                                <h5>Jar File</h5>
                            </div>
                            <div class="row">
                                <div class="col-md-6">
                                    <div class="file-field input-field">
                                        <div class="btn grey">
                                            <i class="large material-icons">file_upload</i>
                                            <input type="file" id="visualization-file" name="file">
                                        </div>
                                        <div class="file-path-wrapper">
                                            <input class="file-path validate" id="visualization-file-name" type="text" name="fileName">
                                        </div>
                                    </div>
                                </div>
                            </div>

                            <hr class="extra-margins">
                            <div class="col-md-12">
                                <h5>Visualization Library</h5>
                            </div>

                            <div class="row">
                                <div class="col-md-6">
                                    <label for="visualization-name">User Friendly Name * </label>
                                    <input type="text" name ="name" id="visualization-name"
                                           required="required" placeholder="E.g Google Chart, D3, Highcharts"/>
                                </div>
                            </div>
                            <div class="row">
                                <div class="col-md-6">
                                    <label for="visualization-desc">Description * </label>
                                    <textarea type="text" name ="desc" id="visualization-desc"
                                              required="required" placeholder="Visualization library description" rows="4"></textarea>
                                </div>
                            </div>

                            <hr class="extra-margins">
                            <div class="col-md-12 valign-wrapper">
                                <h5 class="valign pull-left">Visualization Types</h5> <a class="btn-floating btn waves-effect waves-light grey" style="margin-top: 6px;margin-left: 10px;" title="Add fields for additional visualization types."  onclick="addVisualizationMethodFields(); return false;"><i class="material-icons">add</i></a>
                            </div>

                            <div id="visualization-method-item-template" class="hidden">
                                <div id="visualization-method-item">
                                    <div class="row" style="margin-left: 15px">
                                        <div class="col-md-6 card-panel">
                                            <a class="grey-text pull-right" title="Remove this visualization method\type"  onclick="removeVisualizationMethodFields(this); return false;"><i class="material-icons">clear</i></a>
                                            <label for="visualization-method-name">User Friendly Name * </label>
                                            <input type="text" name ="methodName" id="visualization-method-name"
                                                   required="required" placeholder="E.g. Bar Chart, Pie Chart"/>
                                            <label for="visualization-method-implementing-class">Implementing class name including package * </label>
                                            <input type="text" name ="methodClass" id="visualization-method-implementing-class"
                                                   required="required" placeholder="E.g. de.rwth.aachen.openlap.vizMethodImplementation,   vizMethodImplementation"/>
                                            <label for="data-transformer-method-name">Data Transformer Name * </label>
                                            <input type="text" name ="dataTransformerName" id="data-transformer-method-name"
                                                   required="required" placeholder="Data Transformer name"/>
                                            <label for="data-transformer-method-name">Data Transformer Implementing class name including package * </label>
                                            <input type="text" name ="dataTransformerClass" id="data-transformer-implementing-class"
                                                   required="required" placeholder="E.g. de.rwth.aachen.openlap.transformerImplementation,   transformerImplementation"/>
                                        </div>
                                    </div>
                                </div>
                                <input type="hidden" id="visualization-method-item-count" value="1">
                            </div>
                            <div id="visualization-method-panel">
                                <div id="visualization-method-item-1">
                                    <div class="row" style="margin-left: 15px">
                                        <div class="col-md-6 card-panel">
                                            <label for="visualization-method-name-1">User Friendly Name * </label>
                                            <input type="text" name ="methodName" id="visualization-method-name-1"
                                                   required="required" placeholder="E.g. Bar Chart, Pie Chart"/>
                                            <label for="visualization-method-implementing-class-1">Implementing class name including package * </label>
                                            <input type="text" name ="methodClass" id="visualization-method-implementing-class-1"
                                                   required="required" placeholder="E.g. de.rwth.aachen.openlap.vizMethodImplementation,   vizMethodImplementation"/>
                                            <label for="data-transformer-method-name-1">Data Transformer name * </label>
                                            <input type="text" name ="dataTransformerName" id="data-transformer-method-name-1"
                                                   required="required" placeholder="Data Transformer name"/>
                                            <label for="data-transformer-method-name-1">Data Transformer implementing class name including package * </label>
                                            <input type="text" name ="dataTransformerClass" id="data-transformer-implementing-class-1"
                                                   required="required" placeholder="E.g. de.rwth.aachen.openlap.transformerImplementation,   transformerImplementation"/>
                                        </div>
                                    </div>
                                </div>
                            </div>
                            <div class="row">
                                <div class="col-md-6 right-align">
                                    <table id="upload-actions">
                                        <tr>
                                            <td class="alert-section" id="visualization-alert"></td>
                                            <td class="action-section"><button type="button" class="waves-effect waves-light btn pull-right light-blue" title="Click to submit new visualization." onclick="saveVisualizationJar(); return false;">
                                                Submit
                                            </button></td>
                                        </tr>
                                    </table>
                                </div>
                            </div>
                        </div>
                    </div>
                </div>
                </form>
        </div>

    </div>
</div>

<%@ include file="../_partials/footer.jsp" %>