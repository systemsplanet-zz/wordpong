<%@ include file="/WEB-INF/jsp/common/taglibs.jsp" %>
<fmt:message var="myTurnLbl" key="game.myTurn" />
<fmt:message var="theirTurnLbl" key="game.theirTurn" />
<fmt:message var="myStuffLbl" key="game.myStuff" />
<fmt:message var="answersLbl" key="game.answers" />
<fmt:message var="questionsLbl" key="game.questions" />
<fmt:message var="friendInviteLbl" key="game.friendInvite" />

<fmt:message var="logoutLbl" key="logout" />
<fmt:message var="friendsLbl" key="friends" />
<fmt:message var="profileLbl" key="profile" />
<fmt:message var="newFriendLbl" key="newFriend" />
<fmt:message var="addLbl" key="add" />
<fmt:message var="gameLbl" key="game.game" />
<s:useActionBean id="myBean" beanclass="com.wordpong.app.action.game.GameActionBean"/>
<div data-role="header"  data-nobackbtn="true" data-theme="b" >
    <span style="float:left;margin-left:12px;margin-top:5px">
		<tags:logo/>
    </span>
    <!-- Add Button -->
    <span style="float:right;margin-right:10px">
        <span data-theme="a" class="ui-btn ui-btn-up-a ui-btn-icon-right ui-btn-corner-all ui-shadow">
            <small>
    	        <span class="ui-btn-inner ui-btn-corner-all">
	                <span class="ui-btn-text">${addLbl}</span>
                	<span class="ui-icon ui-icon-plus ui-icon-shadow"></span>                    
            	</span>
				<s:form  name="addGameForm" id="addGameForm" beanclass="com.wordpong.app.action.game.GameActionBean" method="post">		        	    
            		<input id="addGame" name="addGame" value="${addLbl}" data-theme="a" class="process ui-btn-left ui-btn-hidden" type="submit"/>
				</s:form>
            </small>
        </span> 
    </span>
</div>
<div style="clear:both"></div>

<%-- MY TURN --%>

<div data-role="content" style="padding-top:0px;"  >

<s:form  id="gameForm" name="gameForm" beanclass="com.wordpong.app.action.game.GameActionBean" method="post">		        	    
    <s:messages/>      
    <s:errors/>
    <%-- MY TURN --%>    
    <ul data-role="listview" data-inset="true" data-theme="c" data-dividertheme="b"  style="margin-top:0px;">
        <li data-role="list-divider" >${myTurnLbl} (${myBean.user.fullName})</li>              
	</ul>
	<%-- FRIEND INVITES --%>
	<ul data-role="listview" data-inset="true" data-theme="c" data-dividertheme="b"  style="margin-top:0px;">
	    <c:forEach items="${actionBean.myTurnInviteFriends}" var="inviteFriend" >
			<li data-role="list-divider"   style="white-space:normal;" >
				<a  onclick="javascript:$('#inviteFriendKeyStringEncrypted').val('${inviteFriend.keyStringEncrypted}');$('#processFriendInviteBtn').click();" href="#">
					<img src="https://wordpong.appspot.com/i/p/u.png"  >
					<p>${newFriendLbl}</p>
					<p style="white-space:normal;">${inviteFriend.inviterDetails}</p> 
		       	</a>
        	</li>	        	    
	    </c:forEach>
    </ul>            
    
	<%-- GAMES --%>
	<ul data-role="listview" data-inset="true" data-theme="c" data-dividertheme="b"  style="margin-top:0px;">
	    <c:forEach items="${actionBean.myTurnGames}" var="game" >
			<li data-role="list-divider"   style="white-space:normal;" >
				<a  onclick="javascript:$('#gameKeyStringEncrypted').val('${game.keyStringEncrypted}');$('#playGameBtn').click();" href="#">
					<img src="${game.inviterPictureUrl}"  >
					<%--<p><b>${gameLbl}</b></p> --%>
					<p style="white-space:normal;">${game.questionDescription}</p> 
					<p>${game.inviterDetails}</p>
		       	</a>
        	</li>	        	    
	    </c:forEach>
    </ul>            
    <div style='visibility:hidden; height:0; padding:0 margin:0'>
		<input id="playGameBtn" name="playGame" value="play game" type="submit" class="process"/>
		<input id="processFriendInviteBtn" name="processFriendInvite" value="process friend invite" type="submit" class="process"/>		
	</div>
    
	
	<%-- THEIR TURN --%>	
	<ul data-role="listview" data-inset="true" data-theme="c" data-dividertheme="b"> 
		<li data-role="list-divider">${theirTurnLbl}</li> 
	</ul>

	<%-- FRIEND INVITE --%>
	
	<ul data-role="listview" data-inset="true" data-theme="c" data-dividertheme="b"  style="margin-top:0px;">
	    <c:forEach items="${actionBean.theirTurnsInviteFriend}" var="inviteFriend" >
			<li data-role="list-divider"   style="white-space:normal;" >
				<a  onclick="javascript:$('#inviteFriendKeyStringEncrypted').val('${inviteFriend.keyStringEncrypted}');$('#viewTheirTurnFriendInviteBtn').click();" href="#">
					<img src="https://wordpong.appspot.com/i/p/u.png"  >
					<p>${friendInviteLbl}</p>
					<p style="white-space:normal;">${inviteFriend.inviteeDetails}</p> 
		       	</a>
        	</li>	        	    
	    </c:forEach>
    </ul>            
	<%-- GAMES --%>
	<ul data-role="listview" data-inset="true" data-theme="c" data-dividertheme="b"  style="margin-top:0px;">
	    <c:forEach items="${actionBean.theirTurnsGame}" var="game" >
			<li data-role="list-divider"   style="white-space:normal;" >
				<a  onclick="javascript:$('#gameKeyStringEncrypted').val('${game.keyStringEncrypted}');$('#theirTurnGameCancelBtn').click();" href="#">
					<img src="${game.inviteePictureUrl}"  >
<!-- TODO: get user's image -->
					<%--<p><b>${gameLbl}</b></p> --%>
					<p style="white-space:normal;">${game.questionDescription}</p> 
					<p>${game.inviteeDetails}</p>
		       	</a>
        	</li>	        	    
	    </c:forEach>
    </ul>            
    <div style='visibility:hidden; height:0; padding:0 margin:0'>
		<input id="viewTheirTurnFriendInviteBtn" name="viewTheirTurnFriendInvite" value="viewTheirTurnFriendInvite" type="submit" class="process"/>
		<input id="theirTurnGameCancelBtn" name="theirTurnGameCancel" value="theirTurnGameCancel" type="submit" class="process"/>
   		<s:hidden id="inviteFriendKeyStringEncrypted" name="inviteFriendKeyStringEncrypted" value="?inviteFriendKeyStringEncrypted?"/>    
		<s:hidden id="gameKeyStringEncrypted" name="gameKeyStringEncrypted" value="?gameKeyStringEncrypted?"/>    
	</div>
    


     
	<ul data-role="listview" data-inset="true" data-theme="c" data-dividertheme="b"> 
		<li data-role="list-divider">${myStuffLbl}</li> 
<%-- 		<li><a href="support.html">Ad-Free WordPong</a></li>   --%> 
	</ul> 
        <input name="questionList"  data-theme="a" data-iconpos="right" data-icon='arrow-r' class="process" value="${questionsLbl}" type="submit" />
        <input name="answerList"  data-theme="a" data-iconpos="right" data-icon='arrow-r' class="process" value="${answersLbl}" type="submit" />
        <input name="friendList"  data-theme="a" data-iconpos="right" data-icon='arrow-r' class="process" value="${friendsLbl}" type="submit" />
		<input name="profileEdit"  data-theme="a" data-iconpos="right" data-icon="gear" class="process" value="${profileLbl}" type="submit" />


</s:form>
<br/>
<s:form id="logoutFrm" beanclass="com.wordpong.app.action.LoginActionBean" method="post">
   <small> <input name="logout" value="${logoutLbl}" data-icon='arrow-l' class="process"  data-theme="c" type="submit"  /></small>
</s:form>

</div>
<%@ include file="/WEB-INF/jsp/common/footer.jsp" %>
<script>
wpFooterFile = "index"
</script>

