<%@ include file="/WEB-INF/jsp/common/taglibs.jsp" %>
<s:useActionBean id="bean" beanclass="com.wordpong.app.action.game.FriendInviteActionBean"/>
<fmt:message var="submitLbl" key="submit" />
<fmt:message var="backLbl" key="back" />
<fmt:message var="emailLbl" key="email" />
<div data-role="content" style="padding-top:0px; ">
	<%-- NAVIGATION --%>
	<span class="wp-nav">
		<a data-icon="arrow-l" 		onclick="javascript:$('#addGameBtn').click()" 	href="#" data-role="button" data-iconpos="notext" style='visibility:hidden;'>Back</a>
		<a data-icon="home" 		onclick="javascript:$('#homeBtn').click()" 		href="#" data-role="button" data-iconpos="notext">Home</a>
		<a data-icon="wp-friend" 	onclick="javascript:$('#friendsBtn').click()" 	href="#" data-role="button" data-iconpos="notext">Friends</a>
		<a data-icon="wp-question" 	onclick="javascript:$('#questionsBtn').click()" href="#" data-role="button" data-iconpos="notext">Question</a>
		<a data-icon="check" 		onclick="javascript:$('#answersBtn').click()" 	href="#" data-role="button" data-iconpos="notext">Answer</a>
		<a data-icon="gear"			onclick="javascript:$('#profileBtn').click()" 	href="#" data-role="button" data-iconpos="notext">Profile</a>
		<a data-icon="plus" 		onclick="javascript:$('#friendInvite').click()" href="#" data-role="button" data-iconpos="notext" class="ui-btn-active">Add Friend</a>
		<tags:navigation/>
	</span>
	<div style="clear:both"></div>
	<ul data-role="listview" data-inset="true" data-theme="c" data-dividertheme="b" style="margin-top:0px;">
		<li data-role="list-divider" ><s:label for="friendInvite.title"/></li> 
	</ul>
	<tags:messages/> 
	<s:form id="friendInviteForm" beanclass="com.wordpong.app.action.game.FriendInviteActionBean" method="post">
		<div data-role="fieldcontain">
    		<label for="email">${emailLbl}</label>
    		<input type="text" name="email" id="email" value=""  tabindex="1" />
		</div>	
			
		<div style="float:right">
			<input name="invite" id="invite" data-theme="a" class="process" value="${submitLbl}" type="submit" />
		</div>	
	</s:form>   
</div>
<%@ include file="/WEB-INF/jsp/common/footer.jsp" %>
<script>
wpFooterFile = "friendInvite"
</script>
