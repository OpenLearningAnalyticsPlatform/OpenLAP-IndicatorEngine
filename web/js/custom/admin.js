
function saveAnalyticsMethodJar() {

    if ($('#analyticsMethodForm').valid()) {

        var name = $('#analytics-method-name').val();
        var username = $('#userName').val();
        var desc = $('#analytics-method-desc').val();
        var implementingClass = $('#analytics-method-implementing-class').val();
        var filename = $('#analytics-method-file-name').val();
        var file = $('#analytics-method-file')[0].files[0];

        if (name && username && desc && implementingClass && filename && file) {
            var url = 'http://137.226.117.226:8080/AnalyticsMethods';
            var fd = new FormData();

            var metaDataArr = new Object();
            metaDataArr.name = name;
            metaDataArr.creator = username;
            metaDataArr.description = desc;
            metaDataArr.implementingClass = implementingClass;
            metaDataArr.filename = filename.replace(/\.[^/.]+$/, "");

            fd.append("jarBundle", file);
            fd.append("methodMetadata", JSON.stringify(metaDataArr));

            $.ajax({
                url: url,
                type: 'POST',
                data: fd,
                processData: false,
                contentType: false,
                success: function (response) {
                    $('#analyticsMethodForm')[0].reset();

                    var alertDiv = $('#analytics-method-alert');
                    alertDiv.empty();
                    alertDiv.append('<div class="alert alert-success"> Analytics Method Uploaded Successfully. </div>');
                }
            }).error(function (xhr, status, error) {
                var errorObject = JSON.parse(xhr.responseText);

                var alertDiv = $('#analytics-method-alert');
                alertDiv.empty();
                alertDiv.append('<div class="alert alert-danger"> Error: ' + errorObject.content.errorMessage + ' </div>');
            });

        }
    }
}

function saveVisualizationJar() {

    if ($('#visualizationForm').valid()) {

        var name = $('#visualization-name').val();
        var username = $('#userName').val();
        var desc = $('#visualization-desc').val();
        var visualizationMethodName = $('#visualization-method-name').val();
        var visualizationMethodClass = $('#visualization-method-implementing-class').val();
        var dataTransformerMethodName = $('#data-transformer-method-name').val();
        var dataTransformerClass = $('#data-transformer-implementing-class').val();


        var implementingClass = $('#visualization-implementing-class').val();
        var filename = $('#visualization-file-name').val();
        var file = $('#visualization-file')[0].files[0];

        if (name && username && desc && visualizationMethodName && visualizationMethodClass && dataTransformerMethodName && dataTransformerClass) {
            var url = 'http://137.226.231.18:8080/frameworks/upload';
            var fd = new FormData();

            var visualizationFrameworks = [];

            var framework = new Object();
            framework.name = name;
            framework.creator = username;
            framework.description = desc;
            framework.visualizationMethods = [];

            var visualizationMethod = new Object();
            visualizationMethod.name = visualizationMethodName;
            visualizationMethod.implementingClass = visualizationMethodClass;
            visualizationMethod.dataTransformerMethod = new Object();

            var dataTransformerMethod = new Object();
            dataTransformerMethod.name = dataTransformerMethodName;
            dataTransformerMethod.implementingClass = dataTransformerClass;
            visualizationMethod.dataTransformerMethod = dataTransformerMethod;

            framework.visualizationMethods.push(visualizationMethod);
            visualizationFrameworks.push(framework);

            var visualizationFrameworksWrapper = new Object();
            visualizationFrameworksWrapper.visualizationFrameworks = visualizationFrameworks;

            fd.append("frameworkJarBundle", file);
            fd.append("frameworkConfig", JSON.stringify(visualizationFrameworksWrapper));

            console.log(JSON.stringify(visualizationFrameworksWrapper));
            $.ajax({
                url: url,
                type: 'POST',
                data: fd,
                processData: false,
                contentType: false,
                success: function (response) {
                    $('#visualizationForm')[0].reset();

                    var alertDiv = $('#visualization-alert');
                    alertDiv.empty();
                    alertDiv.append('<div class="alert alert-success"> Visualization Uploaded Successfully. </div>');
                }
            }).error(function (xhr, status, error) {
                var errorObject = JSON.parse(xhr.responseText);

                var alertDiv = $('#visualization-alert');
                alertDiv.empty();
                alertDiv.append('<div class="alert alert-danger"> Error: ' + errorObject.errorMessage + ' </div>');
            });

        }
    }
}