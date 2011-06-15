<%@ include file="/WEB-INF/jsp/common/taglibs.jsp" %>
<fmt:message var="myTurnLbl" key="game.myTurn" />
<fmt:message var="theirTurnLbl" key="game.theirTurn" />
<fmt:message var="myStuffLbl" key="game.myStuff" />
<fmt:message var="answersLbl" key="game.answers" />
<fmt:message var="friendInviteLbl" key="game.friendInvite" />
<fmt:message var="gameInviteLbl" key="game.gameInvite" />

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
    <s:messages/>      
    <s:errors/>
    <%-- MY TURN --%>    
    <ul data-role="listview" data-inset="true" data-theme="c" data-dividertheme="b"  style="margin-top:0px;">
        <li data-role="list-divider" >${myTurnLbl} (${myBean.user.fullName})</li>              
        <small>
        <c:forEach items="${actionBean.inviteFriends}" var="inviteFriend" >
        	<input name="processFriendInvite" value="${newFriendLbl}: ${inviteFriend.inviterDetails}" onClick="javascript:$('#inviteFriendKeyStringEncrypted').val('${inviteFriend.keyStringEncrypted}');" data-theme="a" class="process"  type="submit" />
        </c:forEach>
    	<input id="inviteFriendKeyStringEncrypted" name="inviteFriendKeyStringEncrypted" type="hidden" value=""/>    

        <c:forEach items="${actionBean.inviteGames}" var="inviteGame" >
        	<input name="processGameInvite" value="${newGameLbl}: ${inviteGame.inviterDetails}" onClick="javascript:$('#inviteGameKeyStringEncrypted').val('${inviteGame.keyStringEncrypted}');" data-theme="a" class="process"  type="submit" />
        </c:forEach>
        </small>         
	</ul>
	
	<%-- THEIR TURN --%>	
	<ul data-role="listview" data-inset="true" data-theme="c" data-dividertheme="b"> 
		<li data-role="list-divider">${theirTurnLbl}</li> 
		<small>
        <c:forEach items="${actionBean.theirTurnsInviteFriend}" var="inviteFriend" >
			<input  name="viewTheirTurnFriendInvite" onClick="javascript:$('#inviteFriendKeyStringEncrypted').val('${inviteFriend.keyStringEncrypted}');" data-theme="a" class="process"  value="${friendInviteLbl}: ${inviteFriend.inviteeDetails}" type="submit" />
        </c:forEach>
        <c:forEach items="${actionBean.theirTurnsInviteGame}" var="inviteGame" >
			<input name="viewTheirTurnGameInvite"    onClick="javascript:$('#inviteGameKeyStringEncrypted').val('${inviteGame.keyStringEncrypted}');" data-theme="a" class="process"  value="${gameInviteLbl}: ${inviteGame.inviteeDetails}" type="submit" />
        </c:forEach>
        </small>
	</ul>
	<input id="inviteFriendKeyStringEncrypted" name="inviteFriendKeyStringEncrypted" type="hidden" value=""/>    
	<input id="inviteGameKeyStringEncrypted" name="inviteGameKeyStringEncrypted" type="hidden" value=""/>    
     
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
