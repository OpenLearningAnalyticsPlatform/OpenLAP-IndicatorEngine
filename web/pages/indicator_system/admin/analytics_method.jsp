<%@ include file="../_partials/header.jsp" %>

<div class="templatemo-content-wrapper">
    <div class="templatemo-content">
        <ol class="breadcrumb">
            <li><a class="materialize-breadcrumb" href="/home/dashboard">Dashboard</a></li>
            <li><a class="materialize-breadcrumb" href="/admin/analytics_method/new">Admin</a></li>
            <li><a class="materialize-breadcrumb" href="/admin/analytics_method/new">Analytics Method</a></li>
        </ol>
        <input type="hidden" name="userName" id="userName" value="${sessionScope.userName}" />
        <div id="analytics-method-alert"></div>
        <div class="tab-content">
            <form id="analyticsMethodForm" enctype="multipart/form-data">
                <div class="col s12 m6 card">
                    <div class="panel panel-default">
                        <div class="panel-heading light-blue darken-2">New Analytics Method</div>
                        <div class="panel-body">
                            <div class="row">
                                <div class="col-md-6">
                                    <label for="analytics-method-name">Name * </label>
                                    <input type="text" name ="name" id="analytics-method-name"
                                           required="required" placeholder="Analytics Method Name"/>
                                </div>
                            </div>
                            <div class="row">
                                <div class="col-md-6">
                                    <label for="analytics-method-desc">Description * </label>
                                    <textarea type="text" name ="desc" id="analytics-method-desc"
                                              required="required" placeholder="Analytics Method Description" rows="4"></textarea>
                                </div>
                            </div>
                            <div class="row">
                                <div class="col-md-6">
                                    <label for="analytics-method-implementing-class">Implementing Class * </label>
                                    <input type="text" name ="class" id="analytics-method-implementing-class"
                                           required="required" placeholder="Implementing Class"/>
                                </div>
                            </div>
                            <div class="row">
                                <div class="col-md-6">
                                    <div class="file-field input-field">
                                        <div class="btn grey lighten-1">
                                            <span>Jar File * </span>
                                            <input type="file" id="analytics-method-file" name="file">
                                        </div>
                                        <div class="file-path-wrapper">
                                            <input class="file-path validate" id="analytics-method-file-name" type="text" name="fileName">
                                        </div>
                                    </div>
                                </div>
                            </div>
                            <div class="col s12 m6 l10 right-align">
                                <button type="button" class="waves-effect waves-light btn pull-right light-blue" title="Click to submit Analytics Method." onclick="saveAnalyticsMethodJar(); return false;">
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