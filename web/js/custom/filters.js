

function loadAssociatedEntityFilters(entityValues) {

    if (entityValues.length > 0) {
        $('#appliedAttributeFiltersDiv').empty();
        $('#appliedAttributeFiltersDiv').show();
        $('#appliedAttributeFiltersLabel').show();
    }

    for (var entityValuesIndex = 0;
         entityValuesIndex < entityValues.length;
         entityValuesIndex++) {

        var entityValue = entityValues[entityValuesIndex];
        $('#appliedAttributeFiltersDiv').append("<div class='chip filter-chip'"
            + "' id='" + entityValue.key + "_" + entityValue.eValues + "_" + entityValue.type + "' " +
            "title='" + entityValue.key + "_" + entityValue.eValues + "_" + entityValue.type +"'>" +
            "<span>" + entityValue.key + ": " + entityValue.eValues
            + "</span><i class='material-icons' onclick='deleteEntityFilter(this, event);'>close</i></div>");
    }
}

function loadAssociatedSessionFilters(sessionSpecs) {

    if (sessionSpecs.length > 0) {
        $('#appliedSessionFiltersDiv').empty();
        $('#appliedSessionFiltersDiv').show();
        $('#appliedSessionFiltersLabel').show();
    }

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

    if (userSpecs.length > 0 || timeSpecs.length > 0) {
        $('#appliedUserTimeFiltersDiv').empty();
        $('#appliedUserTimeFiltersDiv').show();
        $('#appliedUserTimeFiltersLabel').show();
    }

    for (var userSpecsIndex = 0;
         userSpecsIndex < userSpecs.length;
         userSpecsIndex++) {
        var userSpec = userSpecs[userSpecsIndex];
        $('#appliedUserTimeFiltersDiv').append("<div class='chip filter-chip'"
            + "' id='" + userSpec.key + "_" + userSpec.value +
            "' title='" + userSpec.key + "-" + userSpec.value +"'>" +
            "<span>" + userSpec.key + ": " + userSpec.value
            + "</span><i class='material-icons' onclick='deleteUserFilter(this, event);'>close</i></div>");
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
            + "</span><i class='material-icons' onclick='deleteTimeFilter(this, event);'>close</i></div>");
    }
}

function deleteEntityFilter(filter, event) {
    var e = event;
    $(function() {
        $.ajax({
            type: "GET",
            url: "/indicators/deleteEntities",
            data: {filter: $(filter).closest('div').attr("id")},
            dataType: "json",
            success: function (response) {
                e.stopPropagation();
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

function deleteTimeFilter(filter, event) {
    var e = event;
    $(function() {
        $.ajax({
            type: "GET",
            url: "/indicators/deleteTimeFilters",
            data: {filter: $(filter).closest('div').attr("id")},
            dataType: "json",
            success: function (response) {
                e.stopPropagation();
            }
        });
    });
}

function deleteUserFilter(filter, event) {
    var e = event;
    $(function() {
        $.ajax({
            type: "GET",
            url: "/indicators/deleteUserFilters",
            data: {filter: $(filter).closest('div').attr("id")},
            dataType: "json",
            success: function (response) {
                e.stopPropagation();
            }
        });
    });
}