
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
                    $('#analyticsMethodForm').reset();
                    $('#analytics-method-alert').append('<div class="alert alert-success"> Analytics Method Uploaded Successfully. </div>');
                }
            }).error(function (xhr, status, error) {
                var errorObject = JSON.parse(xhr.responseText);
                $('#analytics-method-alert').append('<div class="alert alert-danger"> Error: ' + errorObject.content.errorMessage + ' </div>');
            });

        }
    }
}