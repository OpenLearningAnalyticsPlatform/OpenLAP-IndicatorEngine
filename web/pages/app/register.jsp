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
  Date: 02-03-2015
  Time: 19:07
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%
    if ((session.getAttribute("loggedIn") != null) && (session.getAttribute("userName") != null) && (session.getAttribute("activationStatus")== "true"))
        response.sendRedirect("/home/dashboard");
    else{
%>
<!--[if lt IE 7 ]> <html lang="en" class="no-js ie6 lt8"> <![endif]-->
<!--[if IE 7 ]>    <html lang="en" class="no-js ie7 lt8"> <![endif]-->
<!--[if IE 8 ]>    <html lang="en" class="no-js ie8 lt8"> <![endif]-->
<!--[if IE 9 ]>    <html lang="en" class="no-js ie9"> <![endif]-->
<!--[if (gt IE 9)|!(IE)]><!--> <html lang="en" class="no-js"> <!--<![endif]-->
<html>
<head>
    <meta charset="UTF-8" />
    <!-- <meta http-equiv="X-UA-Compatible" content="IE=edge,chrome=1">  -->
    <title>OpenLAP : Login</title>
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <meta name="description" content="OpenLAP : Register" />
    <meta name="keywords" content="html5, css3, form, switch, animation, :target, pseudo-class" />
    <meta name="author" content="Tanmaya Mahapatra" />
    <script language="JavaScript" src="${pageContext.request.contextPath}/js/user_registration_checks.js"> </script>
    <link rel="stylesheet" type="text/css" href="../../css/demo.css" />
    <link rel="stylesheet" type="text/css" href="../../css/login_style.css" />
    <link rel="stylesheet" type="text/css" href="../../css/animate-custom.css" />
    <title>Please Login</title>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/error.css">
</head>
<body>
<div class="container">
    <!-- Codrops top bar -->
    <div class="codrops-top">
        <a href="">
            <strong>&laquo; Attention: </strong>You may experience Problems !!
        </a>
                <span class="right">
                    <a href="/login">
                        <strong>Back to Home Page</strong>
                    </a>
                </span>
        <div class="clr"></div>
    </div><!--/ Codrops top bar -->
    <header>
        <h1>Goal Oriented <span>Learning Analytics ToolKit</span></h1>

    </header>
    <section>
        <div id="container_demo" >
            <!-- hidden anchor to stop jump http://www.css3create.com/Astuce-Empecher-le-scroll-avec-l-utilisation-de-target#wrap4  -->
            <a class="hiddenanchor" id="toregister"></a>
            <a class="hiddenanchor" id="tologin"></a>
            <div id="wrapper">
                <div id="login" class="animate form">
                    <form:form  action="/register" method="post" autocomplete="on"  commandName="RegisterForm">
                        <h1> Register </h1>
                        <p>

                            <label for="usernamesignup" class="uname" data-icon="u">Your username</label>
                            <form:input  path="userName" name="usernamesignup" id = "usernamesignup" required="required" type="text" placeholder="mysuperusername690" onchange="checkPreExistingUserName()" />

                        </p>
                        <p>
                            <label for="emailsignup" class="youmail" data-icon="e" > Your email</label>
                            <form:input id="emailsignup" name="emailsignup" required="required" type="email" placeholder="mysupermail@mail.com" path="email" onchange="checkPreExistingEmailID()"/>
                        </p>
                        <p>
                            <label for="passwordsignup" class="youpasswd" data-icon="p">Your password </label>
                            <form:input id="passwordsignup" name="passwordsignup" required="required" type="password" placeholder="eg. X8df!90EO" path="password"/>
                        </p>
                        <p>
                            <label for="passwordsignup_confirm" class="youpasswd" data-icon="p">Please confirm your password </label>
                            <form:input id="passwordsignup_confirm" name="passwordsignup_confirm" required="required" type="password" placeholder="eg. X8df!90EO" path="confirmpassword" onchange="checkPassword()"/>
                        </p>
                        <p>
                            <label for="dob" class="dob" data-icon="d"> Date of Brith </label>
                            <form:input id="dob" name="dob"  type="text" required="required" placeholder="DD-MM-yyyy" path="dob" onchange="checkDate()"/>
                        </p>
                        <p>
                            <form:errors path="*" cssClass="errorblock" element="div" />
                        </p>

                        <p class="signin button">
                            <input type="reset" value="Reset"/>
                        </p>
                        <p class="signin button">
                            <input type="submit" value="Sign up"/>
                        </p>
                        <p class="change_link">
                            Already a member ?
                            <a href="/login"> Go and log in </a>
                        </p>
                    </form:form>
                </div>
            </div>
        </div>


</section>
</div>

</body>
</html>

<%
    }
%>