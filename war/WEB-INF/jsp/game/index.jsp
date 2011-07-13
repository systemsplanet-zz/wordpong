<%@ include file="/WEB-INF/jsp/common/taglibs.jsp" %>
<fmt:message var="myTurnLbl" key="game.myTurn" />
<fmt:message var="theirTurnLbl" key="game.theirTurn" />
<fmt:message var="myStuffLbl" key="game.myStuff" />
<fmt:message var="answersLbl" key="game.answers" />
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
        <div data-theme="a" class="ui-btn ui-btn-up-a ui-btn-icon-right ui-btn-corner-all ui-shadow">
            <small>
    	        <span class="ui-btn-inner ui-btn-corner-all">
	                <span class="ui-btn-text">${addLbl}</span>
                	<span class="ui-icon ui-icon-plus ui-icon-shadow"></span>                    
            	</span>
				<form id="addGameForm" action="/game/Game.wp" method="post">
            		<input id="addGame" name="addGame" value="${addLbl}" data-theme="a" class="process ui-btn-left ui-btn-hidden" type="submit">
  				</form>
            </small>
        </div> 
    </span>
</div>
<div style="clear:both"></div>

<%-- MY TURN --%>

<div data-role="content" style="padding-top:0px;"  >

<s:form  id="gameForm" beanclass="com.wordpong.app.action.game.GameActionBean" method="post">		        	    
    <s:messages/>      
    <s:errors/>
    <%-- MY TURN --%>    
    <ul data-role="listview" data-inset="true" data-theme="c" data-dividertheme="b"  style="margin-top:0px;">
        <li data-role="list-divider" >${myTurnLbl} (${myBean.user.fullName})</li>              
        <small>
        <c:forEach items="${actionBean.myTurnInviteFriends}" var="inviteFriend" >
        	<input name="processFriendInvite" value="${newFriendLbl}: ${inviteFriend.inviterDetails}" 
        	   onClick="javascript:$('#inviteFriendKeyStringEncrypted').val('${inviteFriend.keyStringEncrypted}');" data-theme="a" class="process"  type="submit" />
        </c:forEach>
        
        <c:forEach items="${actionBean.myTurnGames}" var="game" >
        	<input name="playGame" value="${gameLbl}: ${game.inviterDetails} ${game.questionDescription}" 
        	   onClick="javascript:$('#gameKeyStringEncrypted').val('${game.keyStringEncrypted}');" data-theme="a" class="process"  type="submit" />
        </c:forEach>
        <input id="gameKeyStringEncrypted" name="gameKeyStringEncrypted" type="hidden" value=""/>    
        </small>         
	</ul>
	
	<%-- THEIR TURN --%>	
	<ul data-role="listview" data-inset="true" data-theme="c" data-dividertheme="b"> 
		<li data-role="list-divider">${theirTurnLbl}</li> 
		<small>
        <c:forEach items="${actionBean.theirTurnsInviteFriend}" var="inviteFriend" >
			<input  name="viewTheirTurnFriendInvite"  value="${friendInviteLbl}: ${inviteFriend.inviteeDetails}"  
			   onClick="javascript:$('#inviteFriendKeyStringEncrypted').val('${inviteFriend.keyStringEncrypted}');" data-theme="a" class="process" type="submit" />
        </c:forEach>
        <c:forEach items="${actionBean.theirTurnsGame}" var="game" >
			<input  name="theirTurnGameCancel"  value="${gameLbl}: ${game.inviteeDetails}  ${game.questionDescription}"  
			   onClick="javascript:$('#gameKeyStringEncrypted').val('${game.keyStringEncrypted}');" data-theme="a" class="process" type="submit" />
        </c:forEach>
        </small>
	</ul>
   	<s:hidden id="inviteFriendKeyStringEncrypted" name="inviteFriendKeyStringEncrypted" value="?inviteFriendKeyStringEncrypted?"/>    
	<s:hidden id="gameKeyStringEncrypted" name="gameKeyStringEncrypted" value="?gameKeyStringEncrypted?"/>    
     
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
<script>
wpFooterFile = "index"
</script>

