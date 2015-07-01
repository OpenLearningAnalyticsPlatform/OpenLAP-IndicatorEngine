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
  Date: 22-02-2015
  Time: 10:17
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html lang="en">
<head>
    <meta charset="utf-8" />
    <title>Goal Oriented Learning Analytics</title>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/homepage_style.css" type="text/css" media="screen" />
    <link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/css/print.css" media="print" />
    <!--[if IE]><script src="http://html5shiv.googlecode.com/svn/trunk/html5.js"></script><![endif]-->
</head>
<body>
<div id="wrapper"><!-- #wrapper -->

    <nav><!-- top nav -->
        <div class="menu">
            <ul>
                <li><a href="#">Home</a></li>
                <li><a href="#">Architecture</a></li>
                <li><a href="#">Data Model</a></li>
                <li><a href="#">Prototypes</a></li>
                <li><a href="#">Project Status</a></li>
                <li><a href="#">Contact Us</a></li>
            </ul>
        </div>
    </nav><!-- end of top nav -->

    <header><!-- header -->
        <div id="plandesign"><img src="${pageContext.request.contextPath}/images/plans.png" alt="" /></div>
        <h1><a href="#">Open Learning Analytics Platform</a></h1>
        <p>A Learning Analytics System which is more dynamic and goal oriented. Active
            participation of the stakeholders in the definition of the questions and indicators.
            A personalized user driven system.</p>
    </header><!-- end of header -->

    <section id="main"><!-- #main content and sidebar area -->
        <section id="content"><!-- #content -->

            <article>
                <h2><a href="#">Indicator Engine</a></h2>
                <p>Indicator Engine allows the user to achieve personalized and goal-oriented LA in an efficient and effective way.
                </p>
            </article>

            <article>
                <h2><a href="#">Learning Analytics</a></h2>
                <p>Lorem ipsum dolor sit amet, consectetur adipiscing elit. Integer nec odio. Praesent libero. Sed cursus ante dapibus diam. Sed nisi. Nulla quis sem at nibh elementum imperdiet. Duis sagittis ipsum. Praesent mauris. Fusce nec tellus sed augue semper porta. Mauris massa. Vestibulum lacinia arcu eget nulla. Class aptent taciti sociosqu ad litora torquent per conubia nostra, per inceptos himenaeos. Curabitur sodales ligula in libero. Sed dignissim lacinia nunc.</p>
                <p>Nulla quis sem at nibh elementum imperdiet. Duis sagittis ipsum. Praesent mauris. Fusce nec tellus sed augue semper porta. Mauris massa. Vestibulum lacinia arcu eget nulla. Class aptent taciti sociosqu ad litora torquent per conubia nostra, per inceptos himenaeos.</p>
                <p>Mauris massa. Vestibulum lacinia arcu eget nulla. Class aptent taciti sociosqu ad litora torquent per conubia nostra, per inceptos himenaeos.</p>
            </article>

        </section><!-- end of #content -->

        <aside id="sidebar"><!-- sidebar -->
            <h3>Access Indicator Engine</h3>
            <ul>
                <li><a href="/login">Login</a></li>
                <li><a href="/register">Register</a></li>
                <li><a href="/home/dashboard">Control Panel</a></li>
            </ul>

            <h3>Research Group</h3>
            <img src="${pageContext.request.contextPath}/images/RWTH.png" width="50%" />
            <img src="${pageContext.request.contextPath}/images/logo.png" alt="" />

        </aside><!-- end of sidebar -->

    </section><!-- end of #main content and sidebar-->

    <footer>
        <section id="footer-area">

            <section id="footer-outer-block">
                <aside class="footer-segment">
                    <h4>Friends</h4>
                    <ul>
                        <li><a href="#">one linkylink</a></li>
                        <li><a href="#">two linkylinks</a></li>
                        <li><a href="#">three linkylinks</a></li>
                    </ul>
                </aside><!-- end of #first footer segment -->

                <aside class="footer-segment">
                    <h4>Awesome Stuff</h4>
                    <ul>
                        <li><a href="#">one linkylink</a></li>
                        <li><a href="#">two linkylinks</a></li>
                        <li><a href="#">three linkylinks</a></li>
                    </ul>
                </aside><!-- end of #second footer segment -->

                <aside class="footer-segment">
                    <h4>Coolness</h4>
                    <ul>
                        <li><a href="#">one linkylink</a></li>
                        <li><a href="#">two linkylinks</a></li>
                        <li><a href="#">three linkylinks</a></li>
                    </ul>
                </aside><!-- end of #third footer segment -->

                <aside class="footer-segment">
                    <h4>Blahdyblah</h4>
                    <p>Integer nec odio. Praesent libero. Sed cursus ante dapibus diam.</p>
                </aside><!-- end of #fourth footer segment -->

            </section><!-- end of footer-outer-block -->

        </section><!-- end of footer-area -->
    </footer>

</div><!-- #wrapper -->
</body>
</html>
