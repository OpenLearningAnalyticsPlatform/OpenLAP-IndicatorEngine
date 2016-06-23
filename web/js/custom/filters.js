
function loadAssociatedEntityFilters(entityValues) {

    $('#appliedAttributeFiltersDiv').empty();
    if (entityValues.length == 0) {
        $('#appliedAttributeFiltersDiv').hide();
        $('#appliedAttributeFiltersLabel').hide();
        return;
    }
    // if (entityValues.length > 0) {
        $('#appliedAttributeFiltersDiv').show();
        $('#appliedAttributeFiltersLabel').show();
    // }

    for (var entityValuesIndex = 0;
         entityValuesIndex < entityValues.length;
         entityValuesIndex++) {

        var entityValue = entityValues[entityValuesIndex];
        $('#appliedAttributeFiltersDiv').append("<div class='chip filter-chip'"
            + "' id='" + entityValue.key + "_" + entityValue.eValues + "' " +
            "title='" + entityValue.key + "_" + entityValue.eValues +"'>" +
            "<span>" + entityValue.key + ": " + entityValue.eValues +
            "</span><i class='material-icons' onclick='showDeleteEntityFilterModal(this, event);'>close</i></div>");
    }
}

function loadAssociatedSessionFilters(sessionSpecs) {

    $('#appliedSessionFiltersDiv').empty();
    if (sessionSpecs.length == 0) {
        $('#appliedSessionFiltersDiv').hide();
        $('#appliedSessionFiltersLabel').hide();
        return;
    }
    // if (sessionSpecs.length > 0) {
        $('#appliedSessionFiltersDiv').show();
        $('#appliedSessionFiltersLabel').show();
    // }

    for (var sessionSpecsIndex = 0;
         sessionSpecsIndex < sessionSpecsIndex.length;
         sessionSpecsIndex++) {
        var sessionSpec = sessionSpecs[sessionSpecsIndex];
        $('#appliedSessionFiltersDiv').append("<div class='chip filter-chip'"
            + "' id='session-" + sessionSpecsIndex + "' title='Session-" + sessionSpecsIndex + "-" + sessionSpec.key + "'>" +
            "<span>Session-" + sessionSpecsIndex + "-" + sessionSpec.key
            + "</span><i class='material-icons' onclick='deleteIndicator(this, event);'>close</i></div>");
    }
}

function loadAssociatedUserTimeFilters(userSpecs, timeSpecs) {

    $('#appliedUserTimeFiltersDiv').empty();
    if (userSpecs.length == 0 && timeSpecs.length == 0) {
        $('#appliedUserTimeFiltersDiv').hide();
        $('#appliedUserTimeFiltersLabel').hide();
        return;
    }
    // if (userSpecs.length > 0 || timeSpecs.length > 0) {
        $('#appliedUserTimeFiltersDiv').show();
        $('#appliedUserTimeFiltersLabel').show();
    // }

    for (var userSpecsIndex = 0;
         userSpecsIndex < userSpecs.length;
         userSpecsIndex++) {
        var userSpec = userSpecs[userSpecsIndex];
        $('#appliedUserTimeFiltersDiv').append("<div class='chip filter-chip'"
            + "' id='" + userSpec.key + "_" + userSpec.value +
            "' title='" + userSpec.key + "-" + userSpec.value +"'>" +
            "<span>" + userSpec.key + ": " + userSpec.value
            + "</span><i class='material-icons' onclick='showDeleteUserFilterModal(this, event);'>close</i></div>");
    }

    for (var timeSpecsIndex = 0;
         timeSpecsIndex < timeSpecs.length;
         timeSpecsIndex++) {
        var timeSpec = timeSpecs[timeSpecsIndex];
        var parsedDate = new Date(parseInt(timeSpec.timestamp[0]));
        var formattedDate = parsedDate.toDateString();
        $('#appliedUserTimeFiltersDiv').append("<div class='chip filter-chip'"
            + "' id='" + timeSpec.type + "_" + timeSpec.timestamp[0] +
            "' title='" + timeSpec.type + "_" + formattedDate + "'>" +
            "<span>" + timeSpec.type + ": " + formattedDate
            + "</span><i class='material-icons' onclick='showDeleteTimeFilterModal(this, event);'>close</i></div>");
    }
}

function deleteEntityFilter() {
    // var e = event;
    $(function() {
        $.ajax({
            type: "GET",
            url: "/indicators/deleteEntities",
            // data: {filter: $(filter).closest('div').attr("id")},
            data: {filter: $("#deleteEntityFilterValue").val()},
            dataType: "json",
            success: function (response) {
                loadAssociatedEntityFilters(response);
            }
        });
    });
}

function addTimeFilter() {
    $(function() {
        var dateType = $("#dateType").val();
        var dateFilterVal = $("#dateFilterVal").val();
        var isUserData = ( $("#isMyData").is(':checked') ) ? true : false;
        var date = [];
        date[0] = +(new Date(dateFilterVal));
        $.ajax({
            type: "GET",
            url: "/indicators/addTimeFilter?time=" + date + "&timeType=" + dateType + "&isUserData=" + isUserData,
            dataType: "json",
            success: function (timeFilterResponse) {
                if (timeFilterResponse.length > 0) {
                    $.ajax({
                        type: "GET",
                        url: "/indicators/getUserFilters",
                        dataType: "json",
                        success: function (userFilterResponse) {
                            loadAssociatedUserTimeFilters(userFilterResponse, timeFilterResponse);
                        }
                    });
                }
            }
        });
    });
}

function deleteTimeFilter() {
    // var e = event;
    $(function() {
        $.ajax({
            type: "GET",
            url: "/indicators/deleteTimeFilters",
            // data: {filter: $(filter).closest('div').attr("id")},
            data: {filter: $("#deleteTimeFilterValue").val()},
            dataType: "json",
            success: function (timeFilterResponse) {
                // if (timeFilterResponse.length > 0) {
                    $.ajax({
                        type: "GET",
                        url: "/indicators/getUserFilters",
                        dataType: "json",
                        success: function (userFilterResponse) {
                            loadAssociatedUserTimeFilters(userFilterResponse, timeFilterResponse);
                        }
                    });
                // }
            }
        });
    });
}

function deleteUserFilter() {
    // var e = event;
    $(function() {
        $.ajax({
            type: "GET",
            url: "/indicators/deleteUserFilters",
            // data: {filter: $(filter).closest('div').attr("id")},
            data: {filter: $("#deleteUserFilterValue").val()},
            dataType: "json",
            success: function (userFilterResponse) {
                // if (userFilterResponse.length > 0) {
                    $.ajax({
                        type: "GET",
                        url: "/indicators/getTimeFilters",
                        dataType: "json",
                        success: function (timeFilterResponse) {
                            loadAssociatedUserTimeFilters(userFilterResponse, timeFilterResponse);
                        }
                    });
                // }
            }
        });
    });
}

function showDeleteEntityFilterModal(filter, event) {
    $("#deleteEntityFilterValue").val($(filter).closest('div').attr("id"));
    $('#confirmEntityDeleteModal').openModal();
    event.stopPropagation();
}

function showDeleteUserFilterModal(filter, event) {
    $("#deleteUserFilterValue").val($(filter).closest('div').attr("id"));
    $('#confirmUserDeleteModal').openModal();
    event.stopPropagation();
}

function showDeleteTimeFilterModal(filter, event) {
    $("#deleteTimeFilterValue").val($(filter).closest('div').attr("id"));
    $('#confirmTimeDeleteModal').openModal();
    event.stopPropagation();
}