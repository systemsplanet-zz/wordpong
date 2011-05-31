<%@ include file="/WEB-INF/jsp/common/taglibs.jsp" %>
<fmt:message var="myTurnLbl" key="game.myTurn" />
<fmt:message var="theirTurnLbl" key="game.theirTurn" />
<fmt:message var="myStuffLbl" key="game.myStuff" />
<fmt:message var="answersLbl" key="game.answers" />

<fmt:message var="logoutLbl" key="logout" />
<fmt:message var="friendsLbl" key="friends" />
<fmt:message var="profileLbl" key="profile" />
<fmt:message var="newFriendLbl" key="newFriend" />
<fmt:message var="newGameLbl" key="newGame" />


<s:useActionBean id="myBean" beanclass="com.wordpong.app.action.game.GameActionBean"/>
  
<div data-role="header"  data-nobackbtn="true" data-theme="b" >
	<center>
		<tags:logo/>
	</center>
</div>

<%-- MY TURN --%>

<div data-role="content" style="padding-top:0px;"  >
<s:form  id="gameForm" beanclass="com.wordpong.app.action.game.GameActionBean" method="post">		        	    
    
    <%-- MY TURN --%>    
    <ul data-role="listview" data-inset="true" data-theme="c" data-dividertheme="b"  style="margin-top:0px;">
        <li data-role="list-divider" >${myTurnLbl} (${myBean.user.fullName})</li>              
        <small>
        <c:forEach items="${actionBean.inviteFriends}" var="inviteFriend" >
        	<input value="${newFriendLbl}: ${inviteFriend.inviterDetails}" name="processFriendInvite"  onClick="javascript:$('#inviteFriendKeyStringEncrypted').val('${inviteFriend.keyStringEncrypted}');" data-theme="a" class="process"  type="submit" />
        </c:forEach>
    	<input id="inviteFriendKeyStringEncrypted" name="inviteFriendKeyStringEncrypted" type="hidden" value=""/>    

        <c:forEach items="${actionBean.inviteGames}" var="inviteGame" >
        	<input value="${newGameLbl}: ${inviteGame.inviterDetails}" name="processGameInvite"  onClick="javascript:$('#inviteGameKeyStringEncrypted').val('${inviteGame.keyStringEncrypted}');" data-theme="a" class="process"  type="submit" />
        </c:forEach>
        </small>         
	    <input id="inviteGameKeyStringEncrypted" name="inviteGameKeyStringEncrypted" type="hidden" value=""/>    
	</ul>
	
	<%-- THEIR TURN --%>	
	<ul data-role="listview" data-inset="true" data-theme="c" data-dividertheme="b"> 
		<li data-role="list-divider">${theirTurnLbl}</li> 
		<small>
        <c:forEach items="${actionBean.theirTurns}" var="theirTurn" varStatus="theirStatus">
                <s:url beanclass="com.wordpong.app.action.game.GameActionBean" event="theirTurnSelect" var="theirTurnListUrl">
                    <s:param name="theirTurnId" value="${theirTurn.id}"/>
                </s:url>
                <input onClick="javascript:$('#email').val('${theirTurn.id}');$('#createdAtString').val('${theirTurn.createdAtString}');" data-theme="a" class="process"  name="viewInvite" value="${theirTurn.details}" type="submit" />
        </c:forEach>
        </small>
	</ul>
     
	<ul data-role="listview" data-inset="true" data-theme="c" data-dividertheme="b"> 
		<li data-role="list-divider">${myStuffLbl}</li> 
        <input name="answerList"  data-theme="a" data-iconpos="right" data-icon='arrow-r' class="process" value="${answersLbl}" type="submit" />
        <input name="friendList"  data-theme="a" data-iconpos="right" data-icon='arrow-r' class="process" value="${friendsLbl}" type="submit" />
		<input name="profileEdit"  data-theme="a" data-iconpos="right" data-icon="gear" class="process" value="${profileLbl}" type="submit" />

<!-- 		<li><a href="support.html">Ad-Free WordPong</a></li>  --> 
	</ul> 
</s:form>
<br/>
<s:form  beanclass="com.wordpong.app.action.LoginActionBean" method="post">
   <small> <input name="logout" value="${logoutLbl}" data-icon='arrow-l' class="process"  data-theme="c" type="submit"  /></small>
</s:form>

</div>
<%@ include file="/WEB-INF/jsp/common/footer.jsp" %>
