
function saveAnalyticsMethodJar() {

    if ($('#analyticsMethodForm').valid()) {

        var alertDiv = $('#analytics-method-alert');
        alertDiv.empty();
        alertDiv.append('<div class="preloader-wrapper small active" style="margin-right: 10px"><div class="spinner-layer spinner-green-only"><div class="circle-clipper left"><div class="circle"></div></div><div class="gap-patch"><div class="circle"></div></div><div class="circle-clipper right"><div class="circle"></div></div></div></div>');


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
                    alertDiv.append('<div class="alert alert-success"><p class="flow-text" style="font-size: 14px">Analytics Method Uploaded Successfully.</p></div>');
                }
            }).error(function (xhr, status, error) {
                var errorObject = JSON.parse(xhr.responseText);

                var alertDiv = $('#analytics-method-alert');
                alertDiv.empty();
                alertDiv.append('<div class="alert alert-danger"><p class="flow-text" style="font-size: 14px">' + errorObject.content.errorMessage + '</p></div>');
            });

        }
    }
}

function saveVisualizationJar() {

    $("#visualization-method-item-template input").prop('disabled', true);

    if ($('#visualizationForm').valid()) {

        var alertDiv = $('#visualization-alert');
        alertDiv.empty();
        alertDiv.append('<div class="preloader-wrapper small active"><div class="spinner-layer spinner-green-only"><div class="circle-clipper left"><div class="circle"></div></div><div class="gap-patch"><div class="circle"></div></div><div class="circle-clipper right"><div class="circle"></div></div></div></div>');

        var name = $('#visualization-name').val();
        var username = $('#userName').val();
        var desc = $('#visualization-desc').val();


        var implementingClass = $('#visualization-implementing-class').val();
        var filename = $('#visualization-file-name').val();
        var file = $('#visualization-file')[0].files[0];

        if (name && username && desc) {
            var url = 'http://137.226.231.18:8081/frameworks/upload';
            var fd = new FormData();

            var visualizationFrameworks = [];

            var framework = new Object();
            framework.name = name;
            framework.creator = username;
            framework.description = desc;
            framework.visualizationMethods = [];

            var visMethods = $("#visualization-method-panel > div");

            $(visMethods).each(function (i, el) {
                var visualizationMethodName = $(el).find('input[id^="visualization-method-name"]').val();
                var visualizationMethodClass = $(el).find('input[id^="visualization-method-implementing-class"]').val();
                var dataTransformerMethodName = $(el).find('input[id^="data-transformer-method-name"]').val();
                var dataTransformerClass = $(el).find('input[id^="data-transformer-implementing-class"]').val();

                var visualizationMethod = new Object();
                visualizationMethod.name = visualizationMethodName;
                visualizationMethod.implementingClass = visualizationMethodClass;
                visualizationMethod.dataTransformerMethod = new Object();

                var dataTransformerMethod = new Object();
                dataTransformerMethod.name = dataTransformerMethodName;
                dataTransformerMethod.implementingClass = dataTransformerClass;
                visualizationMethod.dataTransformerMethod = dataTransformerMethod;

                framework.visualizationMethods.push(visualizationMethod);
            });

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
                    alertDiv.append('<div class="alert alert-success"><p class="flow-text" style="font-size: 14px">Visualization Technique Uploaded Successfully.</p></div>');
                }
            }).error(function (xhr, status, error) {
                var errorObject = JSON.parse(xhr.responseText);

                var errorMessages = errorObject.errorMessage.split(';');

                errorMessages = errorMessages.filter(function(e){return e});

                var errorMessage =  errorMessages.join("<br>");

                var alertDiv = $('#visualization-alert');
                alertDiv.empty();
                alertDiv.append('<div class="alert alert-danger"><p class="flow-text" style="font-size: 14px">' + errorMessage + '</p></div>');
            });

        }
    }

    $("#visualization-method-item-template input").prop('disabled', false);
}

function addVisualizationMethodFields(){
    //var methodCount = $("#visualization-method-panel > div").length

    var methodCount = parseInt($("#visualization-method-item-template #visualization-method-item-count").val(),10);

    var newMethod = $( "#visualization-method-item-template #visualization-method-item" ).clone();
    newMethod.attr("id","visualization-method-item-"+(methodCount+1));
    newMethod.find("input").each(function (i, el) {
        $(el).attr("id",$(el).attr("id")+"-"+(methodCount+1));
    });
    newMethod.find("label").each(function (i, el) {
        $(el).attr("for",$(el).attr("for")+"-"+(methodCount+1));
    });
    newMethod.appendTo( "#visualization-method-panel");

    $("#visualization-method-item-template #visualization-method-item-count").val(methodCount+1);
}

function removeVisualizationMethodFields(element) {
    if (confirm('Do you want to remove this Visualization Method\\Type?')) {
        $(element).parent().parent().parent().remove();
    }
}