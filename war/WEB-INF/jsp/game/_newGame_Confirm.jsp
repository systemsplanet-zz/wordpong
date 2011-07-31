<%@ include file="/WEB-INF/jsp/common/taglibs.jsp" %>
<s:useActionBean id="bean" beanclass="com.wordpong.app.action.game.NewGameActionBean"/>
<fmt:message var="acceptLbl" key="accept" />
<fmt:message var="cancelLbl" key="cancel" />
<fmt:message var="shareLbl" key="share" />
<fmt:message var="withLbl" key="with" />
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
	<s:form  beanclass="com.wordpong.app.action.game.NewGameActionBean" method="post">
		<ul data-role="listview" data-inset="true" data-theme="c" data-dividertheme="b" style="margin-top:0px;">
			<li data-role="list-divider" ><s:label for="confirm game"/></li> 
			<li><label>${shareLbl}: ${actionBean.questionTitle}</label></li>			
			<li><label>${withLbl}: ${actionBean.friendDetails}</label></li>
		</ul>
		<tags:messages/> 
        <div style="float:right">
			<input name="startGame" value="Start Game" data-theme="a" class="process ui-btn-left  ui-btn-hidden"  type="submit">
        </div>
        <div style="float:left">
            <input data-theme="a" class="process ui-btn-left " data-icon='arrow-l' name="back" value="${cancelLbl}" type="submit" /> 
        </div>
        <s:hidden id="answerKeyStringEncrypted" name="answerKeyStringEncrypted" value="${actionBean.answerKeyStringEncrypted}"/>
        <s:hidden id="questionTitle" name="questionTitle" value="${actionBean.questionTitle}"/>
        <s:hidden id="friendKeyStringEncrypted" name="friendKeyStringEncrypted" value="${actionBean.friendKeyStringEncrypted}" />	
        <s:hidden id="friendDetails" name="friendDetails" value="${actionBean.friendDetails}" />	        
        <s:hidden id="friendPictureUrl" name="friendPictureUrl" value="${actionBean.friendPictureUrl}" />	        
    </s:form>   
</div>
<%@ include file="/WEB-INF/jsp/common/footer.jsp" %>
<script>
wpFooterFile = "newGame_Confirm"
</script>


