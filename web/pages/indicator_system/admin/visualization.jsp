<%@ include file="../_partials/header.jsp" %>

<div class="templatemo-content-wrapper">
    <div class="templatemo-content">
        <ol class="breadcrumb">
            <li><a class="materialize-breadcrumb" href="/home/dashboard">Dashboard</a></li>
            <li><a class="materialize-breadcrumb" href="/admin/visualization/new">Admin</a></li>
            <li><a class="materialize-breadcrumb" href="/admin/visualization/new">Visualization</a></li>
        </ol>
        <input type="hidden" name="userName" id="userName" value="${sessionScope.userName}" />
        <div id="visualization-alert"></div>
        <div class="tab-content">
            <form id="visualizationForm" enctype="multipart/form-data">
                <div class="col s12 m6 card">
                    <div class="panel panel-default">
                        <div class="panel-heading light-blue darken-2">New Visualization</div>
                        <div class="panel-body">
                            <div class="row">
                                <div class="col-md-6">
                                    <label for="visualization-name">Name * </label>
                                    <input type="text" name ="name" id="visualization-name"
                                           required="required" placeholder="Visualization Name"/>
                                </div>
                            </div>
                            <div class="row">
                                <div class="col-md-6">
                                    <label for="visualization-desc">Description * </label>
                                    <textarea type="text" name ="desc" id="visualization-desc"
                                              required="required" placeholder="Visualization Description" rows="4"></textarea>
                                </div>
                            </div>
                            <div class="row">
                                <div class="col-md-6">
                                    <label for="visualization-method-name">Method Name * </label>
                                    <input type="text" name ="methodName" id="visualization-method-name"
                                           required="required" placeholder="Visualization Method Name"/>
                                </div>
                            </div>
                            <div class="row">
                                <div class="col-md-6">
                                    <label for="visualization-method-implementing-class">Method Implementing Class * </label>
                                    <input type="text" name ="methodClass" id="visualization-method-implementing-class"
                                           required="required" placeholder="Visualization Method Implementing Class"/>
                                </div>
                            </div>
                            <div class="row">
                                <div class="col-md-6">
                                    <label for="data-transformer-method-name">Data Transformer Method Name * </label>
                                    <input type="text" name ="dataTransformerName" id="data-transformer-method-name"
                                           required="required" placeholder="Data Transformer Method Name"/>
                                </div>
                            </div>
                            <div class="row">
                                <div class="col-md-6">
                                    <label for="data-transformer-method-name">Data Transformer Implementing Class * </label>
                                    <input type="text" name ="dataTransformerClass" id="data-transformer-implementing-class"
                                           required="required" placeholder="Data Transformer Implementing Class"/>
                                </div>
                            </div>
                            <div class="row">
                                <div class="col-md-6">
                                    <div class="file-field input-field">
                                        <div class="btn grey lighten-1">
                                            <span>Jar File * </span>
                                            <input type="file" id="visualization-file" name="file">
                                        </div>
                                        <div class="file-path-wrapper">
                                            <input class="file-path validate" id="visualization-file-name" type="text" name="fileName">
                                        </div>
                                    </div>
                                </div>
                            </div>
                            <div class="col s12 m6 l10 right-align">
                                <button type="button" class="waves-effect waves-light btn pull-right light-blue" title="Click to submit Visualization." onclick="saveVisualizationJar(); return false;">
                                    Submit
                                </button>
                            </div>
                        </div>
                    </div>
                </div>
                </form>
        </div>

    </div>
</div>

<%@ include file="../_partials/footer.jsp" %>