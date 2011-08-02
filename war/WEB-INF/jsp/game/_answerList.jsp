<%@ include file="/WEB-INF/jsp/common/taglibs.jsp" %>
<s:useActionBean id="bean" beanclass="com.wordpong.app.action.game.AnswerListActionBean"/>
<fmt:message var="backLbl" key="back" />
<fmt:message var="addAnswerLbl" key="answerList.addAnswers" />
<fmt:message var="myAnswersLbl" key="answerList.myAnswers" />
<div data-role="content" style="padding-top:0px;">
	<%-- NAVIGATION --%>
	<span class="wp-nav">
		<a data-icon="arrow-l" 		  onclick="javascript:$('#homeBtn').click()" 		href="#" data-role="button" data-iconpos="notext" style='visibility:hidden;'>Back</a>
		<a data-icon="home" 		  onclick="javascript:$('#homeBtn').click()" 		href="#" data-role="button" data-iconpos="notext">Home</a>
		<a data-icon="wp-friend" 	  onclick="javascript:$('#friendsBtn').click()" 	href="#" data-role="button" data-iconpos="notext">Friends</a>
		<a data-icon="wp-question" 	  onclick="javascript:$('#questionsBtn').click()" href="#" data-role="button" data-iconpos="notext">Question</a>	
		<a data-icon="check" 	 	  onclick="javascript:$('#answersBtn').click()" 	href="#" data-role="button" data-iconpos="notext" class="ui-btn-active">Answer</a>
		<a data-icon="gear"		 	  onclick="javascript:$('#profileBtn').click()" 	href="#" data-role="button" data-iconpos="notext" style='visibility:hidden;'>Profile</a>
		<a data-icon="wp-answer-add"  onclick="javascript:$('#addAnswer').click()"    href="#" data-role="button" data-iconpos="notext">Add Answer</a>
		<tags:navigation/>
	</span>
	<div style="clear:both"></div>
	<s:form id="answerListForm" beanclass="com.wordpong.app.action.game.AnswerListActionBean" method="post">
		<tags:messages/> 
		<ul data-role="listview" data-inset="true" data-theme="c" data-dividertheme="b" style="margin-top:0px;"> 
			<li data-role="list-divider">${myAnswersLbl}</li> 
		</ul>
	
		<ul data-role="listview" data-inset="true" data-theme="c" data-dividertheme="b"  style="margin-top:0px;">
		    <c:forEach items="${actionBean.answers}" var="a" >
				<li data-role="list-divider"   style="white-space:normal;" >
					<a  onclick="javascript:$('#answerKeyStringEncrypted').val('${a.keyStringEncrypted}');$('#questionTitle').val('${a.questionTitle}');$('#editAnswers').val('${a.questionTitle}');$('#editAnswers').click();" href="#">
						<h3 style="white-space:normal;">${a.questionTitle}</h3> 
			       	</a>
	        	</li>	        	    
		    </c:forEach>
    	</ul>            
    	<div style='visibility:hidden; height:0; padding:0 margin:0'>
			<s:button id="editAnswers" name="editAnswers" value="?editAnswers?" class="process"/>    
	        <input id="answerKeyStringEncrypted" name="answerKeyStringEncrypted" type="hidden" value=""/>
    	    <input id="questionTitle" name="questionTitle" type="hidden" value=""/>
		</div>
	
        <div style="float:left">
            <input data-theme="a" class="process ui-btn-left " data-icon='arrow-l' name="back" value="${backLbl}" type="submit" /> 
        </div>
	</s:form>	
</div>
<%@ include file="/WEB-INF/jsp/common/footer.jsp" %>
<script>
wpFooterFile = "answerList"
</script>

