
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

        // + "id='" + etKey + "_" + etValue.replace(" ", "_") + "'"
        $('#appliedAttributeFiltersDiv').append("<div class='chip filter-chip' "
            + "data='" + etKey + "~" + etValue + "' "
            + "<span>" + etTitle + ": " + etValue
            + "</span><i class='material-icons' onclick='showDeleteEntityFilterModal(this, event);'>close</i></div>");

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
            + " data='" + timeSpec.type + "~" + timeSpec.timestamp + "'>" +
            "<span>" + timeTitle + ": " + formattedDate
            + "</span><i class='material-icons' onclick='showDeleteTimeFilterModal(this, event);'>close</i></div>");
    }
}

function loadAssociatedUserFilters(userSpecs) {
    if(userSpecs == null || userSpecs.length ==0 ){
        $("#userFilterAll").prop("checked", true);
        //$("#userFilterRadioAction").hide();
        $("#userEncryptedHash").val("");
    }
    else{
        var userSpec = userSpecs[0];

        if(userSpec.key == "mine"){
            $("#userFilterMy").prop("checked", true)
        }
        else if(userSpec.key == "notmine"){
            $("#userFilterOthers").prop("checked", true)
        }

        //$("#userFilterRadioAction").show();
        //$("#userEncryptedHash").val(userSpec.value);
    }
}


function deleteEntityFilter() {
    var indicatoType = localStorage.getItem("indType");
    var dsid = "0";
    if(indicatoType=='multianalysis') {
        dsid = $("#mlaids_datasourceId").val();
    }

    $(function() {
        $.ajax({
            type: "GET",
            url: "/indicators/deleteEntities",
            // data: {filter: $(filter).closest('div').attr("id")},
            data: {filter: $("#deleteEntityFilterValue").val(), dsid: dsid},
            dataType: "json",
            success: function (response) {
                if(indicatoType=='multianalysis')
                    loadMLAIEntityFilters(response);
                else
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

function userFilterChanged() {
    var checkId = $("#userFilterRadioDiv input:checked").attr("id");

    var userFilter = "all";
    var userHash = $("#rid").val();

    if(checkId == "userFilterMy")
        userFilter = "mine";
    else if(checkId == "userFilterOthers")
        userFilter = "notmine";

    $.ajax({
        type: "GET",
        url: "/indicators/setUserFilter?userFilter=" + userFilter + "&userHash=" + userHash,
        dataType: "json",
        success: function (userFilterResponse) {
            //console.log(userFilterResponse);
        }
    });
}

// function userFilterChanged() {
//     var checkId = $("#userFilterRadioDiv input:checked").attr("id");
//
//     if(checkId == "userFilterAll"){
//         $("#userFilterRadioAction").hide();
//
//         $.ajax({
//             type: "GET",
//             url: "/indicators/setUserFilter?userFilter=all&userHash=",
//             dataType: "json",
//             success: function (userFilterResponse) {
//                 console.log("user filter set to all");
//             }
//         });
//     }
//     else if(checkId == "userFilterMy"){
//         $("#userFilterRadioAction").show();
//     }
//     else if(checkId == "userFilterOthers"){
//         $("#userFilterRadioAction").show();
//     }
// }
//
// function setUserFilter() {
//     var checkId = $("#userFilterRadioDiv input:checked").attr("id");
//
//     var userFilter = "all";
//     var userHash = $("#userEncryptedHash").val();
//
//     if(checkId == "userFilterMy")
//         userFilter = "mine";
//     else if(checkId == "userFilterOthers")
//         userFilter = "notmine";
//
//     $.ajax({
//         type: "GET",
//         url: "/indicators/setUserFilter?userFilter=" + userFilter + "&userHash=" + userHash,
//         dataType: "json",
//         success: function (userFilterResponse) {
//             console.log("user filter set: " + userFilter + "-" + userHash);
//         }
//     });
// }

function deleteTimeFilter() {
    var indicatoType = localStorage.getItem("indType");
    var dsid = "0";
    if(indicatoType=='multianalysis') {
        dsid = $("#mlaids_datasourceId").val();
    }

    $.ajax({
        type: "GET",
        url: "/indicators/deleteTimeFilters",
        data: {filter: $("#deleteTimeFilterValue").val(), dsid: dsid},
        dataType: "json",
        success: function (timeFilterResponse) {
            if(indicatoType=='multianalysis')
                loadMLAIDSTimeFilters(timeFilterResponse);
            else
                loadAssociatedTimeFilters(timeFilterResponse);
        }
    });
}

function showDeleteEntityFilterModal(filter, event) {
    $("#deleteEntityFilterValue").val($(filter).closest('div').attr("data"));
    $('#confirmEntityDeleteModal').openModal();
    event.stopPropagation();
}

// function showDeleteUserFilterModal(filter, event) {
//     $("#deleteUserFilterValue").val($(filter).closest('div').attr("id"));
//     $('#confirmUserDeleteModal').openModal();
//     event.stopPropagation();
// }

function showDeleteTimeFilterModal(filter, event) {
    $("#deleteTimeFilterValue").val($(filter).closest('div').attr("data"));
    $('#confirmTimeDeleteModal').openModal();
    event.stopPropagation();
}