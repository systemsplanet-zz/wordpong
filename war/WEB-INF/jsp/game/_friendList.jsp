<%@ include file="/WEB-INF/jsp/common/taglibs.jsp" %>
<s:useActionBean id="bean" beanclass="com.wordpong.app.action.game.FriendListActionBean"/>
<fmt:message var="backLbl" key="back" />
<fmt:message var="addFriendsLbl" key="friendList.addFriends" />
<fmt:message var="myFriendsLbl" key="friendList.myFriends" />

<div data-role="header"  data-nobackbtn="true" data-theme="b">
<form id="loginForm" action="/game/FriendList.wp" method="post">
    <div>
        <!-- Back Button -->
        <span style="float:left;margin-left:10px">
            <div data-theme="a" class="ui-btn ui-btn-up-a ui-btn-icon-left ui-btn-corner-all ui-shadow">
	            <span class="ui-btn-inner ui-btn-corner-all">
	               <span class="ui-btn-text">${backLbl}</span>
    	           <span class="ui-icon ui-icon-arrow-l ui-icon-shadow"></span>
	            </span>
                <input name="back" value="${backLbl}" data-theme="a" class="process ui-btn-left  ui-btn-hidden" data-icon="arrow-l" type="submit">
            </div> 
        </span>
        
        <!-- Add Button -->
        <span style="float:right;margin-right:10px">
            <div data-theme="a" class="ui-btn ui-btn-up-a ui-btn-icon-right ui-btn-corner-all ui-shadow">
                <span class="ui-btn-inner ui-btn-corner-all">
                    <span class="ui-btn-text">${addFriendsLbl}</span>
                    <span class="ui-icon ui-icon-plus ui-icon-shadow"></span>                    
                </span>
                <input name="friendInvite" value="${addFriendsLbl}" data-theme="a" class="process ui-btn-left  ui-btn-hidden" type="submit">
            </div> 
        </span>
    </div>
</form>
<div style="clear:both"></div>

<div data-role="content" style="padding-top:0px;">
	<tags:messages/> 
	<s:form id="friendListForm" beanclass="com.wordpong.app.action.game.FriendListActionBean" method="post">
    <ul data-role="listview" data-inset="true" data-theme="c" data-dividertheme="b"  style="margin-top:0px;">
        <li data-role="list-divider" >${myFriendsLbl}</li> 
        <small>            
        <c:forEach items="${actionBean.myFriendGames}" var="myFriendGames" >
                <input data-theme="a" class="process"  name="accept" value="chat: ${myFriendGames.friendInfo}" type="submit" />
        </c:forEach>
        </small>         
    </ul>

        <div style="float:left">
            <input data-theme="a" class="process ui-btn-left " data-icon='arrow-l' name="back" value="${backLbl}" type="submit" /> 
        </div>
	</s:form>	
</div>
<%@ include file="/WEB-INF/jsp/common/footer.jsp" %>
<script>
wpFooterFile = "friendList"
</script>

