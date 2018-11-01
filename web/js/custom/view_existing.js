(function($) {
    //Plug-in to fetch page data
    jQuery.fn.dataTableExt.oApi.fnPagingInfo = function ( oSettings )
    {
        return {
            "iStart":         oSettings._iDisplayStart,
            "iEnd":           oSettings.fnDisplayEnd(),
            "iLength":        oSettings._iDisplayLength,
            "iTotal":         oSettings.fnRecordsTotal(),
            "iFilteredTotal": oSettings.fnRecordsDisplay(),
            "iPage":          oSettings._iDisplayLength === -1 ?
                0 : Math.ceil( oSettings._iDisplayStart / oSettings._iDisplayLength ),
            "iTotalPages":    oSettings._iDisplayLength === -1 ?
                0 : Math.ceil( oSettings.fnRecordsDisplay() / oSettings._iDisplayLength )
        };
    };

    $(document).ready(function() {
        var questionTable = $("#questionData").dataTable( {
            "bProcessing": true,
            "bServerSide": true,
            "sort": "position",
            "bLengthChange": false,
            "language": {
                "searchPlaceholder": "Search Question  (press enter to perform search)",
                "sSearch": ""
            },
            "dom": '<"pull-left"f><"pull-right"l>tip',
            "bStateSave": false,
            "iDisplayLength": 5,
            "iDisplayStart": 0,
            "searchDelay": 0,
            "sAjaxSource": "/engine/searchAllQuestions",
            "aoColumns": [
                { "mData": "id" },
                { "mData": "name" },
                { "mData": "indicatorCount" },
            ]
        } );

        $('#questionData_filter input').unbind();
        $('#questionData_filter input').bind('keyup', function(e) {
            if(e.which == 13 || e.keyCode == 13) {
                questionTable.fnFilter(this.value);
            }
        });

        $("#questionData").on('click', 'tr', (function() {

            if($(this).hasClass("selected")) {
                $(this).parent().children().removeClass("selected");

                $("#visualizeQuestionbtn").attr('disabled', 'disabled');

                getIndicatorsByQuestion(0);
            }
            else {
                $(this).parent().children().removeClass("selected");
                $(this).addClass("selected");

                $("#visualizeQuestionbtn").removeAttr('disabled');

                var qTable = $('#questionData').DataTable();

                var selectedRows = qTable.rows(".selected").data();
                var selectedQuestion = selectedRows[0];

                getIndicatorsByQuestion(selectedQuestion.id);
            }
        }));

        $("#indicatorData").on('click', 'tr', (function() {

            if($(this).hasClass("selected")) {
                $(this).parent().children().removeClass("selected");

                $("#visualizeIndicatorbtn").attr('disabled', 'disabled');
            }
            else {
                $(this).parent().children().removeClass("selected");
                $(this).addClass("selected");

                $("#visualizeIndicatorbtn").removeAttr('disabled');
            }
        }));

        getIndicatorsByQuestion(0);
    } );
})(jQuery);

function getIndicatorsByQuestion(questionId){
    $("#visualizeIndicatorbtn").attr('disabled', 'disabled');

    $.ajax({
        type: "GET",
        url: "/engine/getIndicatorsByQuestionId?questionId="+questionId,
        dataType: "json",
        success: function (response) {
            fillIndicatorTable(response);
        }
    });
}

function fillIndicatorTable(response){
    $("#indicatorData").dataTable().fnDestroy();

    $("#indicatorData").find("tbody:last").empty();

    var trClass = "odd";

    for (var i = 0; i < response.aaData.length; i++) {
        $("#indicatorData").find("tbody:last").append("<tr  role='row' class='"+trClass+"'><td>" + response.aaData[i].id + "</td><td>" + response.aaData[i].name + "</td></tr>");

        if(trClass == "odd")
            trClass = "even";
        else
            trClass = "odd";
    }

    $("#indicatorData").dataTable({
        "bLengthChange": false,
        "language": {
            "searchPlaceholder": "Search Indicator",
            "sSearch": ""
        },
        "dom": '<"pull-left"f><"pull-right"l>tip',
        "iDisplayLength": 10
    });
}

function visualizeQuestion() {
    $('#visualizeQuestionContent').empty();
    $('#visualizeHead').empty();

    $('#visualizeQuestionContent').html("<div style='height: 450px;'><div class='preloader-wrapper big active graphLoader'><div class='spinner-layer spinner-blue-only'><div class='circle-clipper left'><div class='circle'></div></div><div class='gap-patch'><div class='circle'></div></div><div class='circle-clipper right'><div class='circle'></div></div></div></div></div>");

    var qTable = $('#questionData').DataTable();
    var selectedRows = qTable.rows(".selected").data();
    var selectedQuestion = selectedRows[0];

    $('#visualizeHead').html('<h5>' + selectedQuestion.name + '</h5>');

    $('#visualizationHead').trigger('click');

    $.ajax({
        type: "GET",
        url: "/engine/getQuestionRequestCode?questionId="+selectedQuestion.id,
        dataType: "json",
        success: function (response) {
            if ((typeof google === 'undefined') || (typeof google.visualization === 'undefined')) {
                console.log('Google Charts Lib is not loaded');
            }
            else {
                $('#visualizeQuestionContent').empty();
                generateQuestionVisualization(response);
            }
        },
        error: function (request, status, error) {
            ("#visualizeQuestionContent").html('<div class="alert alert-warning">' + error + '</div>');
            console.log(status + " - " + error);
        }
    });
}

function generateQuestionVisualization(response){
    if (!response.questionSaved) {
        alert(response.errorMessage);
    } else {
        var userHash = $("#rid").val();

        var visualizeQModelHtml = document.getElementById("visualizeQuestionContent");
        visualizeQModelHtml.innerHTML = "";

        var qColDiv = document.createElement("div");
        qColDiv.className = "col-md-12";

        var qCardDiv = document.createElement("div");
        qCardDiv.className = "question-vis-card card";
        qCardDiv.id = "questionCard";

        var qCardImageDiv = document.createElement("div");
        qCardImageDiv.className = "full-width row";
        qCardImageDiv.id = "questionCard_Image";

        qCardDiv.appendChild(qCardImageDiv);

        var qCardContentDiv = document.createElement("div");
        qCardContentDiv.className = "card-content";
        qCardContentDiv.id = "questionCardContent";

        var qCardContentSpan = document.createElement("span");
        qCardContentSpan.className = "card-title activator grey-text text-darken-4";
        qCardContentSpan.style.display = "block";
        var qCardTitle = document.createTextNode(response.errorMessage); //question name coming in the errorMessage
        qCardContentSpan.appendChild(qCardTitle);

        var qCardMoreAnchor = document.createElement("a");
        var qCardMoreIcon = document.createElement("i");
        qCardMoreIcon.setAttribute("data-position", "bottom");
        qCardMoreIcon.setAttribute("data-tooltip", "Get Question Request Code");
        qCardMoreIcon.className = "material-icons right";
        qCardMoreIcon.style.fontSize = "48px";
        //qCardMoreIcon.setAttribute("title", "Get Indicator Request Code.");
        var qCardMoreIconText = document.createTextNode("code");
        qCardMoreIcon.appendChild(qCardMoreIconText);
        qCardMoreAnchor.appendChild(qCardMoreIcon);
        qCardContentSpan.appendChild(qCardMoreAnchor);
        $(qCardMoreIcon).tooltip({delay: 100});


        qCardContentDiv.appendChild(qCardContentSpan);
        qCardDiv.appendChild(qCardContentDiv);

        var qCardRevealDiv = document.createElement("div");
        qCardRevealDiv.className = "card-reveal";
        qCardRevealDiv.style.wordWrap = "break-word";

        var qCardRevealTitleDiv = document.createElement("div");
        qCardRevealTitleDiv.className = "col-md-12";

        var qCardRevealSpan = document.createElement("span");
        qCardRevealSpan.className = "card-title grey-text text-darken-4";

        var qCardRevealTitle = document.createTextNode(response.errorMessage);

        qCardRevealSpan.appendChild(qCardRevealTitle);

        var qCardCloseAnchor = document.createElement("a");
        var qCardCloseIcon = document.createElement("i");
        qCardCloseIcon.setAttribute("data-position", "bottom");
        qCardCloseIcon.setAttribute("data-tooltip", "Close question request code area");
        qCardCloseIcon.className = "material-icons right";
        var qCardCloseIconText = document.createTextNode("close");
        qCardCloseIcon.appendChild(qCardCloseIconText);
        qCardCloseAnchor.appendChild(qCardCloseIcon);
        qCardRevealSpan.appendChild(qCardCloseAnchor);
        $(qCardCloseIcon).tooltip({delay: 100});

        var qCardCopyAnchor = document.createElement("a");
        qCardCopyAnchor.setAttribute("onclick", "copyIndicatorRequestCode(this,event);");
        var qCardCopyIcon = document.createElement("i");
        qCardCopyIcon.setAttribute("data-position", "bottom");
        qCardCopyIcon.setAttribute("data-tooltip", "Copy question request code");
        qCardCopyIcon.className = "material-icons right";
        var qCardCopyIconText = document.createTextNode("content_copy");
        qCardCopyIcon.appendChild(qCardCopyIconText);
        qCardCopyAnchor.appendChild(qCardCopyIcon);
        qCardRevealSpan.appendChild(qCardCopyAnchor);
        $(qCardCopyIcon).tooltip({delay: 100});

        qCardRevealTitleDiv.appendChild(qCardRevealSpan);

        qCardRevealDiv.appendChild(qCardRevealTitleDiv);

        var qCardRevealDividerDiv = document.createElement("div");
        qCardRevealDividerDiv.className = "col-md-12 divider";

        qCardRevealDiv.appendChild(qCardRevealDividerDiv);

        var qCardRevealCodeDiv = document.createElement("div");
        qCardRevealCodeDiv.className = "col-md-12 request-code";

        var qCardRevealTextPara = document.createElement("p");
        qCardRevealTextPara.setAttribute("contenteditable", "true");
        qCardRevealTextPara.setAttribute("onfocus", "document.execCommand('selectAll',false,null);");

        var questionRequestCode = response.questionRequestCode;
        questionRequestCode = questionRequestCode.replace(/xxxridxxx/g, userHash);
        var qCardRevealText = document.createTextNode(questionRequestCode);


        qCardRevealTextPara.appendChild(qCardRevealText);
        qCardRevealCodeDiv.appendChild(qCardRevealTextPara);
        qCardRevealDiv.appendChild(qCardRevealCodeDiv);

        qCardDiv.appendChild(qCardRevealDiv);

        qColDiv.appendChild(qCardDiv);
        visualizeQModelHtml.appendChild(qColDiv);

        for(var i=0; i<response.indicatorSaveResponses.length; i++) {

            var decodedGraphData = response.indicatorSaveResponses[i].indicatorRequestCode;
            decodedGraphData = decodedGraphData.replace(/xxxridxxx/g, userHash);

            var indicatorName = response.indicatorSaveResponses[i].errorMessage;

            var colDiv = document.createElement("div");
            colDiv.className = "col-md-6";

            var cardDiv = document.createElement("div");
            cardDiv.className = "question-vis-card card";
            cardDiv.id = "visualizeCardGraph_" + i;

            var cardImageDiv = document.createElement("div");
            cardImageDiv.className = "full-width";
            cardImageDiv.style.height='300px';

            var divId = "visualizeCardGraph_" + indicatorName.replace(/ /g,"_");
            cardImageDiv.id = divId;

            cardDiv.appendChild(cardImageDiv);

            var cardContentDiv = document.createElement("div");
            cardContentDiv.className = "card-content";
            cardContentDiv.id = "visualizeCardGraphContent_" + i;

            var cardContentSpan = document.createElement("span");
            cardContentSpan.className = "card-title activator grey-text text-darken-4";
            cardContentSpan.style.display = "block";
            var cardTitle = document.createTextNode(indicatorName);
            cardContentSpan.appendChild(cardTitle);

            // var cardMoreIcon = document.createElement("i");
            // cardMoreIcon.className = "material-icons right";
            // cardMoreIcon.style.fontSize = "48px";
            // cardMoreIcon.setAttribute("title", "Get Indicator Request Code.");
            // var cardMoreIconText = document.createTextNode("code");
            // cardMoreIcon.appendChild(cardMoreIconText);
            // cardContentSpan.appendChild(cardMoreIcon);

            var cardMoreAnchor = document.createElement("a");
            var cardMoreIcon = document.createElement("i");
            cardMoreIcon.setAttribute("data-position", "bottom");
            cardMoreIcon.setAttribute("data-tooltip", "Get Indicator Request Code");
            cardMoreIcon.className = "material-icons right";
            cardMoreIcon.style.fontSize = "48px";
            var cardMoreIconText = document.createTextNode("code");
            cardMoreIcon.appendChild(cardMoreIconText);
            cardMoreAnchor.appendChild(cardMoreIcon);
            cardContentSpan.appendChild(cardMoreAnchor);
            $(cardMoreIcon).tooltip({delay: 100});


            cardContentDiv.appendChild(cardContentSpan);

            cardDiv.appendChild(cardContentDiv);


            var cardRevealDiv = document.createElement("div");
            cardRevealDiv.className = "card-reveal";
            cardRevealDiv.style.wordWrap = "break-word";

            var cardRevealTitleDiv = document.createElement("div");
            cardRevealTitleDiv.className = "col-md-12";

            var cardRevealSpan = document.createElement("span");
            cardRevealSpan.className = "card-title grey-text text-darken-4";

            var cardRevealTitle = document.createTextNode(indicatorName);

            cardRevealSpan.appendChild(cardRevealTitle);

            // var cardCloseIcon = document.createElement("i");
            // cardCloseIcon.className = "material-icons right";
            // var cardCloseIconText = document.createTextNode("close");
            // cardCloseIcon.appendChild(cardCloseIconText);
            // cardRevealSpan.appendChild(cardCloseIcon);

            var cardCloseAnchor = document.createElement("a");
            var cardCloseIcon = document.createElement("i");
            cardCloseIcon.setAttribute("data-position", "bottom");
            cardCloseIcon.setAttribute("data-tooltip", "Close indicator request code area");
            cardCloseIcon.className = "material-icons right";
            var cardCloseIconText = document.createTextNode("close");
            cardCloseIcon.appendChild(cardCloseIconText);
            cardCloseAnchor.appendChild(cardCloseIcon);
            cardRevealSpan.appendChild(cardCloseAnchor);
            $(cardCloseIcon).tooltip({delay: 100});

            var cardCopyAnchor = document.createElement("a");
            cardCopyAnchor.setAttribute("onclick", "copyIndicatorRequestCode(this,event);");
            var cardCopyIcon = document.createElement("i");
            cardCopyIcon.setAttribute("data-position", "bottom");
            cardCopyIcon.setAttribute("data-tooltip", "Copy indicator request code");
            cardCopyIcon.className = "material-icons right";
            var cardCopyIconText = document.createTextNode("content_copy");
            cardCopyIcon.appendChild(cardCopyIconText);
            cardCopyAnchor.appendChild(cardCopyIcon);
            cardRevealSpan.appendChild(cardCopyAnchor);
            $(cardCopyIcon).tooltip({delay: 100});

            cardRevealTitleDiv.appendChild(cardRevealSpan);

            cardRevealDiv.appendChild(cardRevealTitleDiv);

            var cardRevealDividerDiv = document.createElement("div");
            cardRevealDividerDiv.className = "col-md-12 divider";

            cardRevealDiv.appendChild(cardRevealDividerDiv);

            var cardRevealCodeDiv = document.createElement("div");
            cardRevealCodeDiv.className = "col-md-12 request-code";

            var cardRevealTextPara = document.createElement("p");
            cardRevealTextPara.setAttribute("contenteditable", "true");
            cardRevealTextPara.setAttribute("onfocus", "document.execCommand('selectAll',false,null);");
            var cardRevealText = document.createTextNode(decodedGraphData);

            cardRevealTextPara.appendChild(cardRevealText);

            cardRevealCodeDiv.appendChild(cardRevealTextPara);

            cardRevealDiv.appendChild(cardRevealCodeDiv);

            cardDiv.appendChild(cardRevealDiv);

            colDiv.appendChild(cardDiv);

            qCardImageDiv.appendChild(colDiv);


            decodedGraphData = decodedGraphData.replace("xxxwidthxxx","$('#" + divId + "').outerWidth(true)");
            decodedGraphData = decodedGraphData.replace("xxxheightxxx","$('#" + divId + "').outerHeight(true)");

            $('#'+divId).html(decodedGraphData);
        }
    }
}

function visualizeIndicator(){
    //$('#visualizeHead').empty();

    $('#visualizeQuestionContent').empty();
    //$('#visualizeQuestionContent').height(400);

    var iTable = $('#indicatorData').DataTable();
    var selectedRows = iTable.rows(".selected").data();
    var selectedIndicator = selectedRows[0];

    //$('#visualizeHead').html('<h5>' + selectedIndicator[1] + '</h5>');

    $('#visualizationHead').trigger('click');

    $.ajax({
        type: "GET",
        url: "/engine/getIndicatorRequestCode?indicatorId="+selectedIndicator[0],
        dataType: "json",
        success: function (response) {
            generateIndicatorVisualization(response)
        },
        error: function (request, status, error) {
            $("#visualizeQuestionContent").html('<div class="alert alert-warning">' + error + '</div>');
            console.log(status + " - " + error);
        }
    });
}

function generateIndicatorVisualization(response){
    if (!response.indicatorSaved) {
        alert(response.errorMessage);
    } else {
        var userHash = $("#rid").val();
        var decodedGraphData = response.indicatorRequestCode;
        decodedGraphData = decodedGraphData.replace(/xxxridxxx/g, userHash);

        var visualizeQModelHtml = document.getElementById("visualizeQuestionContent");
        visualizeQModelHtml.innerHTML = "";

        var indicatorName = response.errorMessage;

        var colDiv = document.createElement("div");
        colDiv.className = "col-md-6";

        var cardDiv = document.createElement("div");
        cardDiv.className = "question-vis-card card";
        cardDiv.id = "visualizeCardGraph";

        var cardImageDiv = document.createElement("div");
        cardImageDiv.className = "full-width";
        cardImageDiv.style.height='300px';

        var divId = "visualizeCardGraph_" + indicatorName.replace(/ /g, "_");
        cardImageDiv.id = divId;

        cardDiv.appendChild(cardImageDiv);

        var cardContentDiv = document.createElement("div");
        cardContentDiv.className = "card-content";
        cardContentDiv.id = "visualizeCardGraphContent";

        var cardContentSpan = document.createElement("span");
        cardContentSpan.className = "card-title activator grey-text text-darken-4";
        cardContentSpan.style.display = "block";
        var cardTitle = document.createTextNode(indicatorName);
        cardContentSpan.appendChild(cardTitle);

        var cardMoreAnchor = document.createElement("a");
        // cardMoreAnchor.setAttribute("data-position", "bottom");
        // cardMoreAnchor.setAttribute("data-tooltip", "Get Indicator Request Code");
        var cardMoreIcon = document.createElement("i");
        cardMoreIcon.setAttribute("data-position", "bottom");
        cardMoreIcon.setAttribute("data-tooltip", "Get Indicator Request Code");
        cardMoreIcon.className = "material-icons right";
        cardMoreIcon.style.fontSize = "48px";
        //cardMoreIcon.setAttribute("title", "Get Indicator Request Code.");
        var cardMoreIconText = document.createTextNode("code");
        cardMoreIcon.appendChild(cardMoreIconText);
        cardMoreAnchor.appendChild(cardMoreIcon);
        cardContentSpan.appendChild(cardMoreAnchor);
        $(cardMoreIcon).tooltip({delay: 100});

        cardContentDiv.appendChild(cardContentSpan);

        cardDiv.appendChild(cardContentDiv);


        var cardRevealDiv = document.createElement("div");
        cardRevealDiv.className = "card-reveal";
        cardRevealDiv.style.wordWrap = "break-word";

        var cardRevealTitleDiv = document.createElement("div");
        cardRevealTitleDiv.className = "col-md-12";

        var cardRevealSpan = document.createElement("span");
        cardRevealSpan.className = "card-title grey-text text-darken-4";

        var cardRevealTitle = document.createTextNode(indicatorName);

        cardRevealSpan.appendChild(cardRevealTitle);

        var cardCloseAnchor = document.createElement("a");
        // cardCloseAnchor.setAttribute("data-position", "bottom");
        // cardCloseAnchor.setAttribute("data-tooltip", "Close indicator request code area");
        var cardCloseIcon = document.createElement("i");
        cardCloseIcon.setAttribute("data-position", "bottom");
        cardCloseIcon.setAttribute("data-tooltip", "Close indicator request code area");
        cardCloseIcon.className = "material-icons right";
        var cardCloseIconText = document.createTextNode("close");
        cardCloseIcon.appendChild(cardCloseIconText);
        cardCloseAnchor.appendChild(cardCloseIcon)
        cardRevealSpan.appendChild(cardCloseAnchor);
        $(cardCloseIcon).tooltip({delay: 100});

        var cardCopyAnchor = document.createElement("a");
        cardCopyAnchor.setAttribute("onclick", "copyIndicatorRequestCode(this,event);");
        // cardCopyAnchor.setAttribute("data-position", "bottom");
        // cardCopyAnchor.setAttribute("data-tooltip", "Copy indicator request code");
        var cardCopyIcon = document.createElement("i");
        cardCopyIcon.setAttribute("data-position", "bottom");
        cardCopyIcon.setAttribute("data-tooltip", "Copy indicator request code");
        cardCopyIcon.className = "material-icons right";
        var cardCopyIconText = document.createTextNode("content_copy");
        cardCopyIcon.appendChild(cardCopyIconText);
        cardCopyAnchor.appendChild(cardCopyIcon)
        cardRevealSpan.appendChild(cardCopyAnchor);
        $(cardCopyIcon).tooltip({delay: 100});

        cardRevealTitleDiv.appendChild(cardRevealSpan);

        cardRevealDiv.appendChild(cardRevealTitleDiv);

        var cardRevealDividerDiv = document.createElement("div");
        cardRevealDividerDiv.className = "col-md-12 divider";

        cardRevealDiv.appendChild(cardRevealDividerDiv);

        var cardRevealCodeDiv = document.createElement("div");
        cardRevealCodeDiv.className = "col-md-12 request-code";

        var cardRevealTextPara = document.createElement("p");
        cardRevealTextPara.setAttribute("contenteditable", "true");
        cardRevealTextPara.setAttribute("onfocus", "document.execCommand('selectAll',false,null);");
        var cardRevealText = document.createTextNode(decodedGraphData);

        cardRevealTextPara.appendChild(cardRevealText);

        cardRevealCodeDiv.appendChild(cardRevealTextPara);

        cardRevealDiv.appendChild(cardRevealCodeDiv);

        cardDiv.appendChild(cardRevealDiv);

        colDiv.appendChild(cardDiv);

        visualizeQModelHtml.appendChild(colDiv);

        decodedGraphData = decodedGraphData.replace("xxxwidthxxx", "$('#" + divId + "').outerWidth(true)");
        decodedGraphData = decodedGraphData.replace("xxxheightxxx", "$('#" + divId + "').outerHeight(true)");

        $('#' + divId).html(decodedGraphData);
    }
}