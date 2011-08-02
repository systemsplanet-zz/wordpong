<%@ include file="/WEB-INF/jsp/common/taglibs.jsp" %>
<s:useActionBean id="bean" beanclass="com.wordpong.app.action.game.NewGameActionBean"/>
<fmt:message var="backLbl" key="back" />
<fmt:message var="addAnswerLbl" key="newGame.addAnswers" />
<fmt:message var="selectAnswerLbl" key="newGame.selectAnswer" />
<div data-role="content" style="padding-top:0px;">
	<%-- NAVIGATION --%>
	<span class="wp-nav">
		<a data-icon="arrow-l" 		 onclick="javascript:$('#addGameBtn').click()" 		href="#" data-role="button" data-iconpos="notext">Back</a>
		<a data-icon="home" 		 onclick="javascript:$('#homeBtn').click()" 		href="#" data-role="button" data-iconpos="notext">Home</a>
		<a data-icon="wp-friend" 	 onclick="javascript:$('#friendsBtn').click()" 	href="#" data-role="button" data-iconpos="notext" class="ui-btn-active" >Friends</a>
		<a data-icon="wp-question" 	 onclick="javascript:$('#questionsBtn').click()" href="#" data-role="button" data-iconpos="notext">Question</a>
		<a data-icon="check" 		 onclick="javascript:$('#answersBtn').click()" 	href="#" data-role="button" data-iconpos="notext">Answer</a>
		<a data-icon="gear"			 onclick="javascript:$('#profileBtn').click()" 	href="#" data-role="button" data-iconpos="notext" style='visibility:hidden;'>Profile</a>
		<a data-icon="wp-answer-add" onclick="javascript:$('#addAnswer').click()" href="#" data-role="button" data-iconpos="notext">Add Answer</a>
		<tags:navigation/>
	</span>
	<div style="clear:both"></div>
	<s:form id="newGame_AnswerForm" beanclass="com.wordpong.app.action.game.NewGameActionBean" method="post">
		<ul data-role="listview" data-inset="true" data-theme="c" data-dividertheme="b" style="margin-top:0px;"> 
			<li data-role="list-divider">${selectAnswerLbl}</li> 
		</ul>
		<tags:messages/> 
	
		<ul data-role="listview" data-inset="true" data-theme="c" data-dividertheme="b"  style="margin-top:0px;">
		    <c:forEach items="${actionBean.answers}" var="answers" >
				<li data-role="list-divider"   style="white-space:normal;" >
					<a  onclick="javascript:$('#answerKeyStringEncrypted').val('${answers.keyStringEncrypted}');$('#questionTitle').val('${answers.questionTitle}');$('#selectAnswer').val('${answers.questionTitle}');$('#selectAnswer').click();" href="#">
						<%-- <img src="${game.inviteePictureUrl}"  > --%>
						<h3 style="white-space:normal;">${answers.questionTitle}</h3> 						
			       	</a>
	        	</li>	        	    
		    </c:forEach>
    	</ul>            
    	<div style='visibility:hidden; height:0; padding:0 margin:0'>
			<s:button id="selectAnswer" 			name="selectAnswer" value="selectAnswer" class="process"/>    
			<s:hidden id="answerKeyStringEncrypted" name="answerKeyStringEncrypted" value="?answerKeyStringEncrypted?"/>
			<s:hidden id="questionTitle" 			name="questionTitle" value="?questionTitle?"/>
			<s:hidden id="friendKeyStringEncrypted" name="friendKeyStringEncrypted" value="${actionBean.friendKeyStringEncrypted}" />	
			<s:hidden id="friendDetails" 			name="friendDetails" value="${actionBean.friendDetails}" />
			<s:hidden id="friendPictureUrl" 		name="friendPictureUrl" value="${actionBean.friendPictureUrl}" />	
		</div>
	
        <div style="float:left">
            <input data-theme="a" class="process ui-btn-left " data-icon='arrow-l' name="back" value="${backLbl}" type="submit" /> 
        </div>
	</s:form>	
</div>
<%@ include file="/WEB-INF/jsp/common/footer.jsp" %>
<script>
wpFooterFile = "newGame_Answer"
</script>

