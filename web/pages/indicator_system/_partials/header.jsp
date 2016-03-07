<%--
  ~ Open Platform Learning Analytics : Indicator Engine
  ~ Copyright (C) 2015  Learning Technologies Group, RWTH
  ~
  ~ This program is free software; you can redistribute it and/or
  ~ modify it under the terms of the GNU General Public License
  ~ as published by the Free Software Foundation; either version 2
  ~ of the License, or (at your option) any later version.
  ~
  ~ This program is distributed in the hope that it will be useful,
  ~ but WITHOUT ANY WARRANTY; without even the implied warranty of
  ~ MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
  ~ GNU General Public License for more details.
  ~
  ~ You should have received a copy of the GNU General Public License
  ~ along with this program; if not, write to the Free Software
  ~ Foundation, Inc., 51 Franklin Street, Fifth Floor, Boston, MA  02110-1301, USA.
  --%>

<%--
  Created by IntelliJ IDEA.
  User: Tanmaya Mahapatra
  Date: 28-06-2015
  Time: 00:09
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%
    if ((session.getAttribute("loggedIn") == null) || (session.getAttribute("loggedIn") == ""))
        response.sendRedirect("/login");

    if ((session.getAttribute("loggedIn") != null) && (session.getAttribute("userName") != null) && (session.getAttribute("activationStatus")== "false"))
        response.sendRedirect("/activate");
    else{


%>
<html>
<head>
    <meta charset="utf-8">
    <!--[if IE]><meta http-equiv="X-UA-Compatible" content="IE=edge,chrome=1"><![endif]-->
    <title> Question Indicator Editor</title>
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <meta name="description" content="Goal Oriented LA ToolKit : Question Indicator Editor" />
    <meta name="author" content="Tanmaya Mahapatra" />
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/templatemo_main.css">
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/error.css">
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/custom.css">
    <link href="${pageContext.request.contextPath}/js/jquery-ui/jquery-ui.css" rel="stylesheet">
    <link href="${pageContext.request.contextPath}/css/indicator_editor.css" rel="stylesheet">
    <link href="${pageContext.request.contextPath}/css/animate.css" rel="stylesheet">
    <link href="//cdn.datatables.net/1.10.7/js/jquery.dataTables.min.js" rel="stylesheet">
    <link href="${pageContext.request.contextPath}/js/materialize/css/materialize.min.css" rel="stylesheet">
    <link href="https://fonts.googleapis.com/icon?family=Material+Icons" rel="stylesheet">
    <script type="text/javascript" src="${pageContext.request.contextPath}/js/jquery/dist/jquery.js"></script>
    <script type="text/javascript" src="${pageContext.request.contextPath}/js/jquery-ui/jquery-ui.js"></script>
    <script type="text/javascript" src="${pageContext.request.contextPath}/js/noty-2.3.5/js/noty/jquery.noty.js"  ></script>
    <script type="text/javascript" src="${pageContext.request.contextPath}/js/noty-2.3.5/js/noty/layouts/bottomRight.js"></script>
    <script type="text/javascript" src="${pageContext.request.contextPath}/js/noty-2.3.5/js/noty/themes/relax.js"></script>
    <script type="text/javascript" src="//cdn.datatables.net/1.10.7/js/jquery.dataTables.js"></script>
    <script type="text/javascript" src="${pageContext.request.contextPath}/js/indicator_editor.js"></script>
    <script type="text/javascript" src="${pageContext.request.contextPath}/js/materialize/js/materialize.min.js"></script>
    <script type="text/javascript" src="${pageContext.request.contextPath}/js/custom/ui_tricks.js"></script>
    <script type="text/javascript">

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

                $("#indicatorData").dataTable( {
                    "bProcessing": true,
                    "bServerSide": true,
                    "sort": "position",
                    //bStateSave variable you can use to save state on client cookies: set value "true"
                    "bStateSave": false,
                    //Default: Page display length
                    "iDisplayLength": 10,
                    //We will use below variable to track page number on server side(For more information visit: http://legacy.datatables.net/usage/options#iDisplayStart)
                    "iDisplayStart": 0,
                    "fnDrawCallback": function () {
                        //Get page numer on client. Please note: number start from 0 So
                        //for the first page you will see 0 second page 1 third page 2...
                        //Un-comment below alert to see page number
                        //alert("Current page number: "+this.fnPagingInfo().iPage);
                    },
                    "sAjaxSource": "/indicators/fetchExistingIndicatorsData.web",
                    "aoColumns": [
                        { "mData": "id" },
                        { "mData": "indicator_name" },
                        { "mData": "short_name" },
                    ]
                } );

            } );
        })(jQuery);
    </script>

</head>
<%@ include file="nav_bar.jsp" %>