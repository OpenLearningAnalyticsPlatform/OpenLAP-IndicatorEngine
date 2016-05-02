<%@ include file="../_partials/header.jsp" %>

    <div class="templatemo-content-wrapper">
        <div class="templatemo-content">
            <ol class="breadcrumb">
                <li><a class="materialize-breadcrumb" href="/home/dashboard">Dashboard</a></li>
                <li><a class="materialize-breadcrumb" href="/indicators/home">Indicator Home</a></li>
            </ol>
            <h5>Indicator Editor</h5>
            <input type="hidden" name="userName" id="userName" value="${sessionScope.userName}" />

            <!--FORM for Indicator Editor-->
            <div class="tab-content">
                <form:form role="form" id="sessionSelection"  method="POST" modelAttribute="selectNumberParameters" action="${flowExecutionUrl}">

                    <%@ include file="../_partials/components/goal.jsp" %>

                    <%@ include file="../_partials/components/question.jsp" %>

                    <%@ include file="../_partials/components/indicator.jsp" %>

                </form:form>
            </div>

        </div>
    </div>

<%@ include file="../_partials/footer.jsp" %>

<%@ include file="../_partials/modals/load_indicator_template.jsp" %>

<%@ include file="../_partials/modals/composite_indicator.jsp" %>

<%@ include file="../_partials/modals/visualize_question.jsp" %>