<%@ include file="/WEB-INF/jsp/common/taglibs.jsp" %>
<s:useActionBean id="bean" beanclass="com.wordpong.app.action.game.QuestionListActionBean"/>
<fmt:message var="backLbl" key="back" />
<fmt:message var="addQuestionLbl" key="questionList.addQuestions" />
<fmt:message var="myQuestionsLbl" key="questionList.myQuestions" />
<div data-role="content" style="padding-top:0px;">
	<%-- NAVIGATION --%>
	<span class="wp-nav">
		<a data-icon="arrow-l" 			onclick="javascript:$('#homeBtn').click()" 		href="#" data-role="button" data-iconpos="notext" style='visibility:hidden;'>Back</a>
		<a data-icon="home" 			onclick="javascript:$('#homeBtn').click()" 		href="#" data-role="button" data-iconpos="notext">Home</a>
		<a data-icon="wp-friend" 		onclick="javascript:$('#friendsBtn').click()" 	href="#" data-role="button" data-iconpos="notext">Friends</a>
		<a data-icon="wp-question" 		onclick="javascript:$('#questionsBtn').click()" href="#" data-role="button" data-iconpos="notext" class="ui-btn-active"  >Question</a>
		<a data-icon="check" 			onclick="javascript:$('#answersBtn').click()" 	href="#" data-role="button" data-iconpos="notext">Answer</a>
		<a data-icon="gear"				onclick="javascript:$('#profileBtn').click()" 	href="#" data-role="button" data-iconpos="notext" style='visibility:hidden;'>Profile</a>
		<a data-icon="wp-question-add"  onclick="javascript:$('#addQuestion').click()"  href="#" data-role="button" data-iconpos="notext">Add Friend</a>
		<tags:navigation/>
	</span>
	<div style="clear:both"></div>
	<s:form id="questionListForm" beanclass="com.wordpong.app.action.game.QuestionListActionBean" method="post">
		<ul data-role="listview" data-inset="true" data-theme="c" data-dividertheme="b" style="margin-top:0px;"> 
			<li data-role="list-divider">${myQuestionsLbl}</li> 
		</ul>
		<tags:messages/> 
		<ul data-role="listview" data-inset="true" data-theme="c" data-dividertheme="b"  style="margin-top:0px;">
		    <c:forEach items="${actionBean.questions}" var="questions" >
				<li data-role="list-divider"   style="white-space:normal;" >
					<a  onclick="javascript:$('#questionKeyStringEncrypted').val('${questions.keyStringEncrypted}');$('#questionTitle').val('${questions.title}');$('#editQuestions').click();" href="#">
						<%-- <img src="${game.inviteePictureUrl}"  > --%>
						<h3 style="white-space:normal;">${questions.title}</h3> 
						<p style="white-space:normal;">${questions.description}</p>
			       	</a>
	        	</li>	        	    
		    </c:forEach>
    	</ul>            
    	<div style='visibility:hidden; height:0; padding:0 margin:0'>
			<s:button id="editQuestions" name="editQuestions" value="editQuestions" class="process"/>    
		</div>
        <div style="float:left">
            <input data-theme="a" class="process ui-btn-left " data-icon='arrow-l' name="back" value="${backLbl}" type="submit" /> 
        </div>
        <input id="questionKeyStringEncrypted" name="questionKeyStringEncrypted" type="hidden" value=""/>
        <input id="questionTitle" name="questionTitle" type="hidden" value=""/>
	</s:form>	
</div>
<%@ include file="/WEB-INF/jsp/common/footer.jsp" %>
<script>
wpFooterFile = "questionList"
</script>

