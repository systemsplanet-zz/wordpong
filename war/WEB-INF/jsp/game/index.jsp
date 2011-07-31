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
<s:useActionBean id="myBean" beanclass="com.wordpong.app.action.game.GameActionBean"/>
<div data-role="content" style="padding-top:0px;"  >
	<%-- NAVIGATION --%>
	<div class="wp-nav">
		<a data-icon="arrow-l" 		onclick="javascript:$('#backBtn').click()" 		href="#" data-role="button" data-iconpos="notext" style='visibility:hidden;'>Back</a>
		<a data-icon="home" 		onclick="javascript:$('#homeBtn').click()" 		href="#" data-role="button" data-iconpos="notext" class="ui-btn-active">Home</a>
		<a data-icon="wp-friend" 	onclick="javascript:$('#friendsBtn').click()" 	href="#" data-role="button" data-iconpos="notext">Friends</a>
		<a data-icon="wp-question" 	onclick="javascript:$('#questionsBtn').click()" href="#" data-role="button" data-iconpos="notext">Question</a>
		<a data-icon="check" 		onclick="javascript:$('#answersBtn').click()" 	href="#" data-role="button" data-iconpos="notext">Answer</a>
		<a data-icon="gear"			onclick="javascript:$('#profileBtn').click()" 	href="#" data-role="button" data-iconpos="notext">Profile</a>
		<a data-icon="arrow-r" 		onclick="javascript:$('#addGameBtn').click()" 	href="#" data-role="button" data-iconpos="notext">Next</a>
		<tags:navigation/>
	</div>
	<div style="clear:both"></div>
	<%-- MY TURN --%>	
	<s:form  id="gameForm" name="gameForm" beanclass="com.wordpong.app.action.game.GameActionBean" method="post">		        	    
		<tags:messages/> 
	    <%-- MY TURN --%>    
	    <ul data-role="listview" data-inset="true" data-theme="c" data-dividertheme="b"  style="margin-top:0px;">
	        <li data-role="list-divider" >${myTurnLbl} (${myBean.user.fullName})</li>              
		</ul>
		<%-- FRIEND INVITES --%>
		<ul data-role="listview" data-inset="true" data-theme="c" data-dividertheme="b"  style="margin-top:0px;">
		    <c:forEach items="${actionBean.myTurnInviteFriends}" var="inviteFriend" >
				<li data-role="list-divider"   style="white-space:normal;" >
					<a  onclick="javascript:$('#inviteFriendKeyStringEncrypted').val('${inviteFriend.keyStringEncrypted}');$('#processFriendInviteBtn').click();" href="#">
						<img src="${inviteFriend.inviterPictureUrl}"  >
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
						<p style="white-space:normal;">${game.questionTitle}</p> 
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
						<img src="${inviteFriend.inviteePictureUrl}"  >
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
						<p style="white-space:normal;">${game.questionTitle}</p> 
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
	<%-- 	<li><a href="support.html">Ad-Free WordPong</a></li>   --%> 
		</ul> 
		<sdyn:button data-icon='wp-friend' 	 name="friendsBtn"   id="friendsBtn"   value="${friendsLbl}"	data-theme="a" data-iconpos="right" class="process" />
		<sdyn:button data-icon='wp-question' name="questionsBtn" id="questionsBtn" value="${questionsLbl}" 	data-theme="a" data-iconpos="right" class="process" />
		<sdyn:button data-icon='check' 		 name="answersBtn"   id="answersBtn"   value="${answersLbl}" 	data-theme="a" data-iconpos="right" class="process" />
		<sdyn:button data-icon="gear" 		 name="profileBtn"   id="profileBtn"   value="${profileLbl}"	data-theme="a" data-iconpos="right" class="process" />
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

