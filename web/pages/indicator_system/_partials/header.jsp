<%--
  ~ Open Learning Analytics Platform (OpenLAP) : Indicator Engine

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
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <meta name="author" content="Arham Muslim" />
    <link rel="stylesheet" href="${pageContext.request.contextPath}/js/jquery-ui/jquery-ui.css">
    <link rel="stylesheet" href="//cdn.datatables.net/1.10.7/css/jquery.dataTables.min.css">
    <link rel="stylesheet" href="https://fonts.googleapis.com/icon?family=Material+Icons">
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/animate.css">
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/indicator_editor.css">
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/templatemo_main.css">
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/error.css">
    <link rel="stylesheet" href="${pageContext.request.contextPath}/js/materialize/css/materialize.min.css">
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/custom.css">

    <script type="text/javascript" src="${pageContext.request.contextPath}/js/jquery/dist/jquery.min.js"></script>
    <script type="text/javascript" src="https://cdn.jsdelivr.net/jquery.validation/1.15.0/jquery.validate.min.js"></script>
    <script type="text/javascript" src="https://cdn.jsdelivr.net/jquery.validation/1.15.0/additional-methods.min.js"></script>
    <script type="text/javascript" src="${pageContext.request.contextPath}/js/jquery-ui/jquery-ui.min.js"></script>
    <script type="text/javascript" src="https://cdn.datatables.net/1.10.7/js/jquery.dataTables.js"></script>
    <script type="text/javascript" src="${pageContext.request.contextPath}/js/materialize/js/materialize.min.js"></script>
    <script type="text/javascript" src="${pageContext.request.contextPath}/js/custom/openlap.js"></script>