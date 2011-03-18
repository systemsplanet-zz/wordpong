<%@ include file="/WEB-INF/jsp/common/taglibs.jsp" %>
<s:useActionBean id="myBean" beanclass="com.wordpong.app.action.game.GameActionBean"/>

<div data-role="header"  data-nobackbtn="true" data-theme="b">
	<div class="wp-right-button" >
		<form id="loginForm" action="/Login.wp" method="post">
			<input class="process" name="logout" value="Logout" type="submit" />
		</form>
	</div>
	<tags:logo/>
</div>
<div data-role="content" style="padding-top:0px;">
<s:form  id="gameForm" beanclass="com.wordpong.app.action.game.GameActionBean" method="post">		        	
    
        <c:set var="group" value=""/>
        <ul data-role="listview" data-inset="true" data-theme="c" data-dividertheme="b"  style="margin-top:0px;">
            <li data-role="list-divider" >My Turn (${actionBean.user.fullName})</li> 
        <c:forEach items="${actionBean.myTurns}" var="myTurn" varStatus="status">
            <c:choose>
                <c:when test="${group != myTurn.actionString && !status.first}">
                        </ul>
                    </li>
                </c:when>
            </c:choose>
            <c:choose>
                <c:when test="${group != myTurn.actionString}">
                    <c:set var="group" value="${myTurn.actionString}"/>
                    <%--The li element and account has to be on the same line for nested list in jQuerymobile to render correctly--%>
                    <li>${group}
                        <ul data-theme="c" data-header-theme="c">
                </c:when>
            </c:choose>

            <li>
                <s:url beanclass="com.wordpong.app.action.game.GameActionBean" event="myTurnList" var="myTurnListUrl">
                    <s:param name="myTurnId" value="${myTurn.account.id}"/>
                </s:url>
                <a href="${myTurnListUrl}">${myTurn.account.email}</a>
            </li>

            <c:if test="${status.last}">
                        </ul>
                    </li>
            </c:if>
        </c:forEach>
		</ul>
	
	<ul data-role="listview" data-inset="true" data-theme="c" data-dividertheme="b"> 
			<li data-role="list-divider">Their Turn</li> 
	</ul>
	<ul data-role="listview" data-inset="true" data-theme="c" data-dividertheme="b"> 
			<li data-role="list-divider">WordPong Stuff</li> 
			<li><a href="/static/privacy.html">Privacy Policy</a></li> 
			<li><a href="/static/tos.html">Terms of Service</a></li> 
			<li><a href="/static/contact.html">Contact Us</a></li> 
	</ul> 
 
	<ul data-role="listview" data-inset="true" data-theme="c" data-dividertheme="b"> 
		<li data-role="list-divider">My Stuff</li> 
		<li><a href="answers.html">Answers</a></li> 
		<li><a href="friends.html">Friends</a></li> 
		 	<input data-theme="a" class="process" name="profile" action="profile" value="Profile" type="submit" />
		<!-- 	<s:url beanclass="com.wordpong.app.action.game.ProfileActionBean" event="view" var="profileUrl"/> -->
		<!-- <a href="${profileUrl}">Profile</a> -->
		<li><a href="support.html">Ad-Free WordPong</a></li> 
	</ul> 
	
</s:form>
	
</div>

