<body>
<header>
    <nav class="top-nav light-blue darken-2">
        <div class="container">
            <div class="nav-wrapper"><a class="page-title">Indicator Editor</a></div>
        </div>
    </nav>
    <div class="container">
        <a href="#" data-activates="nav-mobile" class="button-collapse top-nav full hide-on-large-only">
            <i class="material-icons">menu</i>
        </a>
    </div>
    <ul id="nav-mobile" class="side-nav fixed grey darken-4" style="transform: translateX(0%);">
        <li class="bold">
            <a href="#" class="brand-logo"><img src="${pageContext.request.contextPath}/images/user.png" alt="User" class="circle responsive-img valign profile-image"></a>
        </li>
        <li class="bold">
            <a href="/home/dashboard" class="waves-effect grey-text text-lighten-2"><i class="fa fa-home"></i>&nbsp;Dashboard</a>
            <hr>
        </li>
        <li class="bold">
            <a href="/indicators/indicators_definition" class="waves-effect grey-text text-lighten-2"><i class="fa fa-file"></i>&nbsp;New Question</a>
            <hr>
        </li>
        <li class="bold">
            <a href="/admin/analytics_method/new" class="waves-effect grey-text text-lighten-2"><i class="fa fa-file"></i>&nbsp;Analytics Method</a>
            <hr>
        </li>
        <li class="bold">
            <a href="/admin/visualization/new" class="waves-effect grey-text text-lighten-2"><i class="fa fa-file"></i>&nbsp;Visualization</a>
            <hr>
        </li>
        <li class="bold">
            <a href="javascript:;" class="waves-effect grey-text text-lighten-2" data-toggle="modal" data-target="#confirmModal"><i class="fa fa-sign-out"></i>&nbsp;Sign Out</a>
        </li>
    </ul>
</header>