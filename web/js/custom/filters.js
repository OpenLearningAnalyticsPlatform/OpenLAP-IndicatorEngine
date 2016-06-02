
function loadIndicatorAssociatedFilters(entityValues, userSpecs, sessionSpecs, timeSpecs) {

    if (entityValues.length > 0 || userSpecs.length > 0 || sessionSpecs.length > 0 || timeSpecs.length > 0) {
        $('#appliedFiltersDiv').empty();
        $('#appliedFiltersDiv').show();
        $('#appliedFiltersLabel').show();
    }

    for (var entityValuesIndex = 0;
         entityValuesIndex < entityValues.length;
         entityValuesIndex++) {

        var entityValue = entityValues[entityValuesIndex];
        $('#appliedFiltersDiv').append("<div class='chip filter-chip'"
            + "' id='" + entityValue.key + "_" + entityValue.eValues + "_" + entityValue.type + "' " +
            "title='Attr_" + entityValue.key + "_" + entityValue.eValues + "_" + entityValue.type +"'>" +
            "<span>Attr_" + entityValue.key + ": " + entityValue.eValues
            + "</span><i class='material-icons' onclick='deleteEntityFilter(this, event);'>close</i></div>");
    }

    for (var userSpecsIndex = 0;
         userSpecsIndex < userSpecs.length;
         userSpecsIndex++) {
        var userSpec = userSpecs[userSpecsIndex];
        $('#appliedFiltersDiv').append("<div class='chip filter-chip'"
            + "' id='" + userSpec.key + "_" + userSpec.value +
            "' title='User_" + userSpec.key + "-" + userSpec.value +"'>" +
            "<span>User_" + userSpec.key + ": " + userSpec.value
            + "</span><i class='material-icons' onclick='deleteIndicator(this, event);'>close</i></div>");
    }

    for (var sessionSpecsIndex = 0;
         sessionSpecsIndex < sessionSpecsIndex.length;
         sessionSpecsIndex++) {
        var sessionSpec = sessionSpecs[sessionSpecsIndex];
        $('#appliedFiltersDiv').append("<div class='chip filter-chip'"
            + "' id='session-" + sessionSpecsIndex + "' title='Session-" + sessionSpecsIndex + "-" + sessionSpec.key + "'>" +
            "<span>Session-" + sessionSpecsIndex + "-" + sessionSpec.key
            + "</span><i class='material-icons' onclick='deleteIndicator(this, event);'>close</i></div>");
    }

    for (var timeSpecsIndex = 0;
         timeSpecsIndex < timeSpecs.length;
         timeSpecsIndex++) {
        var timeSpec = timeSpecs[timeSpecsIndex];
        var parsedDate = new Date(parseInt(timeSpec.timestamp[0]));
        var formattedDate = parsedDate.toDateString();
        $('#appliedFiltersDiv').append("<div class='chip filter-chip'"
            + "' id='" + timeSpec.type + "_" + timeSpec.timestamp[0] +
            "' title='Time_" + timeSpec.type + "_" + formattedDate + "'>" +
            "<span>Time_" + timeSpec.type + ": " + formattedDate
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
            success: function (response) {
                loadIndicatorAssociatedFilters([], [], [], response);
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