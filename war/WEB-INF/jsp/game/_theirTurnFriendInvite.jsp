<%@ include file="/WEB-INF/jsp/common/taglibs.jsp" %>
<s:useActionBean id="bean" beanclass="com.wordpong.app.action.game.TheirTurnFriendInviteActionBean"/>
<fmt:message var="submitLbl" key="submit" />
<fmt:message var="backLbl" key="back" />
<fmt:message var="cancelInviteLbl" key="theirTurnFriendInvite.cancelInvite" />
<fmt:message var="friendInviteeLbl" key="theirTurnFriendInvite.friendInvitee" />
<div data-role="content" style="padding-top:0px;">
	<%-- NAVIGATION --%>
	<span class="wp-nav">
		<a data-icon="arrow-l" 		onclick="javascript:$('#addGameBtn').click()" 	href="#" data-role="button" data-iconpos="notext" style='visibility:hidden;'>Back</a>
		<a data-icon="home" 		onclick="javascript:$('#homeBtn').click()" 		href="#" data-role="button" data-iconpos="notext">Home</a>
		<a data-icon="wp-friend" 	onclick="javascript:$('#friendsBtn').click()" 	href="#" data-role="button" data-iconpos="notext">Friends</a>
		<a data-icon="wp-question" 	onclick="javascript:$('#questionsBtn').click()" href="#" data-role="button" data-iconpos="notext">Question</a>
		<a data-icon="check" 		onclick="javascript:$('#answersBtn').click()" 	href="#" data-role="button" data-iconpos="notext">Answer</a>
		<a data-icon="gear"			onclick="javascript:$('#profileBtn').click()" 	href="#" data-role="button" data-iconpos="notext">Profile</a>
		<a data-icon="plus" 		onclick="javascript:$('#').click()" href="#" data-role="button" data-iconpos="notext" style='visibility:hidden;'></a>
		<tags:navigation/>
	</span>
	<div style="clear:both"></div>
	<s:form id="friendInviteForm" beanclass="com.wordpong.app.action.game.TheirTurnFriendInviteActionBean" method="post">
		<ul data-role="listview" data-inset="true" data-theme="c" data-dividertheme="b" style="margin-top:0px;">
			<li data-role="list-divider" ><s:label for="theirTurnFriendInvite.title"/></li> 
		</ul>
		<tags:messages/> 
		<ul data-role="listview" data-inset="true" data-theme="c" data-dividertheme="b">
			<li>
				<div data-role="fieldcontain" style="padding:4px;">
                    <label >${friendInviteeLbl}: ${actionBean.inviteFriend.inviteeDetails}</label>
                    <s:hidden   name="inviteFriendKeyStringEncrypted" value="${inviteFriend.keyStringEncrypted}" />			 
				</div>
			</li>
		</ul>
		<div style="float:right">
			<input id="cancelInvite" name="cancelInvite" data-theme="a" class="process" value="${cancelInviteLbl}" type="submit" />
		</div>	
	</s:form>   
</div>
<%@ include file="/WEB-INF/jsp/common/footer.jsp" %>
<script>
wpFooterFile = "theirTurnFriendInvite"
</script>
