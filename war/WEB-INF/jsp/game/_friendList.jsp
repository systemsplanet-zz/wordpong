<%@ include file="/WEB-INF/jsp/common/taglibs.jsp" %>
<s:useActionBean id="bean" beanclass="com.wordpong.app.action.game.FriendListActionBean"/>
<fmt:message var="backLbl" key="back" />
<fmt:message var="addFriendsLbl" key="friendList.addFriends" />
<fmt:message var="myFriendsLbl" key="friendList.myFriends" />

<div data-role="header"  data-nobackbtn="true" data-theme="b">
    <div class="wp-right-button">
        <form id="loginForm" action="/game/FriendList.wp" method="post">
        <div style="float:left">
            <input data-theme="a" class="process ui-btn-left " data-icon='arrow-l' name="back" value="${backLbl}" type="submit" /> 
        </div>
            <input data-theme="a" class="process ui-btn-left"  name="friendInvite" value="${addFriendsLbl}" type="submit" /> 
        </form>
    </div>
    <tags:logo/>
</div>
<div data-role="content" style="padding-top:0px;">
	<tags:messages/> 
	<s:form id="friendListForm" beanclass="com.wordpong.app.action.game.FriendListActionBean" method="post">
        <c:set var="group" value=""/>
        <ul data-role="listview" data-inset="true" data-theme="c" data-dividertheme="b"  style="margin-top:0px;">
            <li data-role="list-divider" >${myFriendsLbl}</li> 
            
        <!-- TODO: change to friends -->    
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
                    <s:param name="myTurnId" value="${myTurn.id}"/>
                </s:url>
                <a href="${myTurnListUrl}">${myTurn.id}</a>
            </li>

            <c:if test="${status.last}">
                        </ul>
                    </li>
            </c:if>
        </c:forEach>
        </ul>

        <div style="float:left">
            <input data-theme="a" class="process ui-btn-left " data-icon='arrow-l' name="back" value="${backLbl}" type="submit" /> 
        </div>
	</s:form>	
</div>
<%@ include file="/WEB-INF/jsp/common/footer.jsp" %>

