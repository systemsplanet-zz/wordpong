<%@ include file="/WEB-INF/jsp/common/taglibs.jsp" %>
<s:useActionBean id="bean" beanclass="com.wordpong.app.action.game.FriendInviteAcceptActionBean"/>
<fmt:message var="acceptLbl" key="accept" />
<fmt:message var="ignoreLbl" key="ignore" />
<fmt:message var="backLbl" key="back" />
<fmt:message var="friendInviteFromLbl" key="friendInviteAccept.friendInviteFrom" />
<div data-role="content" style="padding-top:0px;">
	<%-- NAVIGATION --%>
	<span class="wp-nav">
		<a data-icon="arrow-l" 		onclick="javascript:$('#').click()" 			href="#" data-role="button" data-iconpos="notext" style='visibility:hidden;'>Back</a>
		<a data-icon="home" 		onclick="javascript:$('#homeBtn').click()" 		href="#" data-role="button" data-iconpos="notext">Home</a>
		<a data-icon="wp-friend" 	onclick="javascript:$('#friendsBtn').click()" 	href="#" data-role="button" data-iconpos="notext">Friends</a>
		<a data-icon="wp-question" 	onclick="javascript:$('#questionsBtn').click()" href="#" data-role="button" data-iconpos="notext">Question</a>
		<a data-icon="check" 		onclick="javascript:$('#answersBtn').click()" 	href="#" data-role="button" data-iconpos="notext">Answer</a>
		<a data-icon="gear"			onclick="javascript:$('#profileBtn').click()" 	href="#" data-role="button" data-iconpos="notext" style='visibility:hidden;'>Profile</a>
		<a data-icon="plus" 		onclick="javascript:$('#').click()" 			href="#" data-role="button" data-iconpos="notext" style='visibility:hidden;'></a>
		<tags:navigation/>
	</span>
	<div style="clear:both"></div>
	<ul data-role="listview" data-inset="true" data-theme="c" data-dividertheme="b" style="margin-top:0px;">
		<li data-role="list-divider" ><s:label for="friendInviteAccept.title"/></li> 
	</ul>
	<tags:messages/> 
	<s:form id="friendInviteAcceptForm" beanclass="com.wordpong.app.action.game.FriendInviteAcceptActionBean" method="post">
		<ul data-role="listview" data-inset="true" data-theme="c" data-dividertheme="b">
			<li>
       			<label>${friendInviteFromLbl}: ${actionBean.inviteFriend.inviterDetails}</label>
   	            <s:hidden   name="inviteFriendKeyStringEncrypted" value="${inviteFriend.keyStringEncrypted}" />			
			</li>
		</ul>
        <div style="float:right">
            <input data-theme="a" class="process" name="acceptInviteConfirm" value="${acceptLbl}" type="submit" />
        </div>  
        <div style="float:right">
            <input data-theme="a" class="process" name="ignoreInvite" value="${ignoreLbl}" type="submit" />
        </div>  
   	</s:form>   
</div>
<%@ include file="/WEB-INF/jsp/common/footer.jsp" %>
<script>
wpFooterFile = "friendInviteAccept"
</script>
