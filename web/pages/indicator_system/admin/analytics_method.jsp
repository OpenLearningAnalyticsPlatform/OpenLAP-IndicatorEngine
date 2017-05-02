<%@ include file="../_partials/header.jsp" %>

    <title>New Analytics Method</title>
    <meta name="description" content="OpenLAP : Administration Panel" />

    <script type="text/javascript" src="${pageContext.request.contextPath}/js/custom/admin.js"></script>

</head>

<%@ include file="../_partials/nav_bar.jsp" %>

<div class="templatemo-content-wrapper">
    <div class="templatemo-content">
        <ol class="breadcrumb">
            <li><a class="materialize-breadcrumb" href="/home/dashboard">Dashboard</a></li>
            <li><a class="materialize-breadcrumb" href="/admin/analytics_method/new">Admin</a></li>
            <li><a class="materialize-breadcrumb" href="/admin/analytics_method/new">Analytics Method</a></li>
        </ol>
        <input type="hidden" name="userName" id="userName" value="${sessionScope.userName}" />
        <div class="tab-content">
            <form id="analyticsMethodForm" enctype="multipart/form-data">
                <div class="col s12 m6 card">
                    <div class="panel panel-default">
                        <div class="panel-heading light-blue darken-2">New Analytics Method</div>
                        <div class="panel-body">

                            <div class="col-md-12">
                                <h5>Jar File</h5>
                            </div>
                            <div class="row">
                                <div class="col-md-6">
                                    <div class="file-field input-field">
                                        <div class="btn grey">
                                            <i class="large material-icons">file_upload</i>
                                            <input type="file" id="analytics-method-file" name="file">
                                        </div>
                                        <div class="file-path-wrapper">
                                            <input class="file-path validate" id="analytics-method-file-name" type="text" name="fileName">
                                        </div>
                                    </div>
                                </div>
                            </div>

                            <hr class="extra-margins">
                            <div class="col-md-12">
                                <h5>Analytics Method</h5>
                            </div>

                            <div class="row">
                                <div class="col-md-6">
                                    <label for="analytics-method-name">User Friendly Name * </label>
                                    <input type="text" name ="name" id="analytics-method-name"
                                           required="required" placeholder="Analytics method name"/>
                                </div>
                            </div>
                            <div class="row">
                                <div class="col-md-6">
                                    <label for="analytics-method-desc">Comprehensive Description * </label>
                                    <textarea type="text" name ="desc" id="analytics-method-desc"
                                              required="required" placeholder="Analytics method description" rows="4"></textarea>
                                </div>
                            </div>
                            <div class="row">
                                <div class="col-md-6">
                                    <label for="analytics-method-implementing-class">Implementing class name including package * </label>
                                    <input type="text" name ="class" id="analytics-method-implementing-class"
                                           required="required" placeholder="E.g. de.rwth.aachen.openlap.methodImplementation,   methodImplementation"/>
                                </div>
                            </div>
                            <div class="row">
                                <div class="col-md-6 right-align">
                                    <table id="upload-actions">
                                        <tr>
                                            <td class="alert-section" id="analytics-method-alert"></td>
                                            <td class="action-section"><button type="button" class="waves-effect waves-light btn pull-right light-blue" title="Click to submit new analytics method." onclick="saveAnalyticsMethodJar(); return false;">
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