<%@ include file="/WEB-INF/jsp/common/taglibs.jsp" %>
<fmt:message var="myTurnLbl" key="game.myTurn" />
<fmt:message var="theirTurnLbl" key="game.theirTurn" />
<fmt:message var="myStuffLbl" key="game.myStuff" />
<fmt:message var="answersLbl" key="game.answers" />

<fmt:message var="logoutLbl" key="logout" />
<fmt:message var="friendsLbl" key="friends" />
<fmt:message var="profileLbl" key="profile" />
<fmt:message var="invitedLbl" key="game.inviteTo" />

<s:useActionBean id="myBean" beanclass="com.wordpong.app.action.game.GameActionBean"/>
  
<div data-role="header"  data-nobackbtn="true" data-theme="b" >
	<center>
		<tags:logo/>
	</center>
</div>

<%-- MY TURN --%>

<div data-role="content" style="padding-top:0px;"  >
<s:form  id="gameForm" beanclass="com.wordpong.app.action.game.GameActionBean" method="post">		        	    
    <c:set var="group" value=""/>
    <ul data-role="listview" data-inset="true" data-theme="c" data-dividertheme="b"  style="margin-top:0px;">
        <li data-role="list-divider" >${myTurnLbl} (${actionBean.user.fullName})</li> 
        <c:forEach items="${actionBean.myTurns}" var="myTurn" varStatus="myStatus">
            <c:choose>
                <c:when test="${group != myTurn.actionString && !myStatus.first}">
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
                <s:url beanclass="com.wordpong.app.action.game.GameActionBean" event="myTurnSelect" var="myTurnListUrl">
                    <s:param name="myTurnId" value="${myTurn.id}"/>
                </s:url>
                   <a href="${myTurnListUrl}">${myTurn.id}</a> 
\            </li>

            <c:if test="${myStatus.last}">
                        </ul>
                    </li>
            </c:if>
        </c:forEach>
	</ul>
	
	
	<%-- THEIR TURN --%>
	
	<ul data-role="listview" data-inset="true" data-theme="c" data-dividertheme="b"> 
		<li data-role="list-divider">${theirTurnLbl}</li> 
        <c:forEach items="${actionBean.theirTurns}" var="theirTurn" varStatus="theirStatus">
                <s:url beanclass="com.wordpong.app.action.game.GameActionBean" event="theirTurnSelect" var="theirTurnListUrl">
                    <s:param name="theirTurnId" value="${theirTurn.id}"/>
                </s:url>
                <input onClick="javascript:$('#email').val('${theirTurn.id}');" data-theme="a" class="process" action="viewInvite" name="viewInvite" value="${invitedLbl}: ${theirTurn.id}" type="submit" />
        </c:forEach>
	</ul>
    <input id="email" name="email" type="hidden" value=""/>
    
     
	<ul data-role="listview" data-inset="true" data-theme="c" data-dividertheme="b"> 
		<li data-role="list-divider">${myStuffLbl}</li> 
        <input name="answersList" action="answerList" data-theme="a" data-iconpos="right" data-icon='arrow-r' class="process" value="${answersLbl}" type="submit" />
        <input name="friendList" action="friendList" data-theme="a" data-iconpos="right" data-icon='arrow-r' class="process" value="${friendsLbl}" type="submit" />
		<input name="profileEdit" action="profileEdit" data-theme="a" data-iconpos="right" data-icon="gear" class="process" value="${profileLbl}" type="submit" />

<!-- 		<li><a href="support.html">Ad-Free WordPong</a></li>  --> 
	</ul> 
</s:form>
<br/>
<s:form  beanclass="com.wordpong.app.action.LoginActionBean" method="post">
   <small> <input name="logout" value="${logoutLbl}" data-icon='arrow-l' class="process"  data-theme="c" type="submit"  /></small>
</s:form>

</div>
<%@ include file="/WEB-INF/jsp/common/footer.jsp" %>
