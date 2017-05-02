
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


    //var selectedMethods = JSON.parse(localStorage.getItem('selectedMethods')) || [];

    for (var entityValuesIndex = 0; entityValuesIndex < entityValues.length; entityValuesIndex++) {

        var entityValue = entityValues[entityValuesIndex];

        var etKey = entityValue.key;
        var etValue = entityValue.eValues;
        var etTitle = entityValue.title;

        $('#appliedAttributeFiltersDiv').append("<div class='chip filter-chip' "
            + "data='" + etKey + "_" + etValue + "' " +
            + "id='" + etKey + "_" + etValue.replace(",", "_") + "' " +
            "title='" + etTitle + "_" + etValue +"'>" +
            "<span>" + etTitle + ": " + etValue +
            "</span><i class='material-icons' onclick='showDeleteEntityFilterModal(this, event);'>close</i></div>");

        // var index = selectedMethods.indexOf(entityValue.key);
        // if (index < 0) {
        //     selectedMethods.push(entityValue.key);
        //     localStorage.setItem('selectedMethods', JSON.stringify(selectedMethods));
        // }
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

function loadAssociatedTimeFilters(timeSpecs) {

    $('#appliedUserTimeFiltersDiv').empty();

    if (timeSpecs.length == 0) {
        $('#appliedUserTimeFiltersDiv').hide();
        $('#appliedUserTimeFiltersLabel').hide();
        return;
    }

    $('#appliedUserTimeFiltersDiv').show();
    $('#appliedUserTimeFiltersLabel').show();

    for (var timeSpecsIndex = 0; timeSpecsIndex < timeSpecs.length; timeSpecsIndex++) {
        var timeSpec = timeSpecs[timeSpecsIndex];
        var parsedDate = new Date(parseInt(timeSpec.timestamp)*1000);
        var formattedDate = parsedDate.toDateString();

        var timeTitle = "";
        if(timeSpec.type == "fromDate")
            timeTitle = "Start Date";
        else if(timeSpec.type == "toDate")
            timeTitle = "End Date";

        $('#appliedUserTimeFiltersDiv').append("<div class='chip filter-chip'"
            + "' id='" + timeSpec.type + "_" + timeSpec.timestamp +
            "' title='" + timeSpec.type + "_" + formattedDate + "'>" +
            "<span>" + timeTitle + ": " + formattedDate
            + "</span><i class='material-icons' onclick='showDeleteTimeFilterModal(this, event);'>close</i></div>");
    }
}
//
// function loadAssociatedUserFilters(userSpecs) {
//
//     for (var userSpecsIndex = 0; userSpecsIndex < userSpecs.length; userSpecsIndex++) {
//         var userSpec = userSpecs[userSpecsIndex];
//
//         $('#appliedUserTimeFiltersDiv').append("<div class='chip filter-chip'"
//             + "' id='" + userSpec.key + "_" + userSpec.value +
//             "' title='" + userSpec.key + "-" + userSpec.value +"'>" +
//             "<span>" + userSpec.key + ": " + userSpec.value
//             + "</span><i class='material-icons' onclick='showDeleteUserFilterModal(this, event);'>close</i></div>");
//     }
// }
//

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

        /*var date = [];
        date[0] = +(new Date(dateFilterVal).getTime()/1000|0); //Converting selected date to unix timestamp*/

        var date = new Date(dateFilterVal).getTime()/1000|0; //Converting selected date to unix timestamp

        $.ajax({
            type: "GET",
            url: "/indicators/addTimeFilter?time=" + date + "&timeType=" + dateType,
            dataType: "json",
            success: function (timeFilterResponse) {
                loadAssociatedTimeFilters(timeFilterResponse);
            }
        });
    });
}

function addUserFilter() {
    $(function() {
        var isUserData = ( $("#isMyData").is(':checked') ) ? true : false;
        $.ajax({
            type: "GET",
            url: "/indicators/addUserFilter?isUserData=" + isUserData,
            dataType: "json",
            success: function (userFilterResponse) {
                loadAssociatedUserFilters(userFilterResponse);
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
                loadAssociatedTimeFilters(timeFilterResponse);

                // if (timeFilterResponse.length > 0) {
                //     $.ajax({
                //         type: "GET",
                //         url: "/indicators/getUserFilters",
                //         dataType: "json",
                //         success: function (userFilterResponse) {
                //             loadAssociatedUserTimeFilters(userFilterResponse, timeFilterResponse);
                //         }
                //     });
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
                loadAssociatedUserFilters(userFilterResponse);
                // if (userFilterResponse.length > 0) {
                //     $.ajax({
                //         type: "GET",
                //         url: "/indicators/getTimeFilters",
                //         dataType: "json",
                //         success: function (timeFilterResponse) {
                //             loadAssociatedUserTimeFilters(userFilterResponse, timeFilterResponse);
                //         }
                //     });
                // }
            }
        });
    });
}

function showDeleteEntityFilterModal(filter, event) {
    $("#deleteEntityFilterValue").val($(filter).closest('div').attr("data"));
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