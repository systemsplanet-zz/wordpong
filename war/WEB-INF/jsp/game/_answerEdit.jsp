<%@ include file="/WEB-INF/jsp/common/taglibs.jsp" %>
<s:useActionBean id="bean" beanclass="com.wordpong.app.action.game.AnswerEditActionBean"/>
<fmt:message var="backLbl" key="back" />
<fmt:message var="enterAnswersLbl" key="answerEdit.enterAnswers" />
<div data-role="content" style="padding-top:0px;">
	<%-- NAVIGATION --%>
	<span class="wp-nav">
		<a data-icon="arrow-l" 		onclick="javascript:$('#back').click()" 		href="#" data-role="button" data-iconpos="notext" >Back</a>
		<a data-icon="home" 		onclick="javascript:$('#homeBtn').click()" 		href="#" data-role="button" data-iconpos="notext">Home</a>
		<a data-icon="wp-friend" 	onclick="javascript:$('#friendsBtn').click()" 	href="#" data-role="button" data-iconpos="notext">Friends</a>
		<a data-icon="wp-question" 	onclick="javascript:$('#questionsBtn').click()" href="#" data-role="button" data-iconpos="notext">Question</a>
		<a data-icon="check" 		onclick="javascript:$('#answersBtn').click()" 	href="#" data-role="button" data-iconpos="notext">Answer</a>
		<a data-icon="gear"			onclick="javascript:$('#profileBtn').click()" 	href="#" data-role="button" data-iconpos="notext">Profile</a>
		<a data-icon="plus" 		onclick="javascript:$('#').click()" 			href="#" data-role="button" data-iconpos="notext" style='visibility:hidden;'></a>
		<tags:navigation/>
	</span>
	<div style="clear:both"></div>
	<span class="wp-invisible">
		<s:form id="answerEditFormBack" beanclass="com.wordpong.app.action.game.AnswerEditActionBean" method="post">
			<input name="back" id="back" value="${backLbl}" data-theme="a" class="process ui-btn-left  ui-btn-hidden" data-icon="arrow-l" type="submit">
		</s:form>
	</span>

	<s:form id="answerEditForm" beanclass="com.wordpong.app.action.game.AnswerEditActionBean" method="post">
	    <ul data-role="listview" data-inset="true" data-theme="c" data-dividertheme="b"  style="margin-top:0px;">
	        <li data-role="list-divider" >${enterAnswersLbl}: ${actionBean.questionTitle}</li> 
        </ul>
        <tags:messages/> 
        <small>            
 	       <c:forEach items="${actionBean.questions}" var="i"  varStatus="s">	 	       
	          <div data-role="fieldcontain" style="padding:4px;">
	              <s:label for="answers[${s.index}]" class="ui-input-text">${i}</s:label>		              
	              <sdyn:text name="answers[${s.index}]" id="answers[${s.index}]" tabindex="1" maxlength="100"  class="ui-input-text ui-body-null ui-corner-all ui-shadow-inset ui-body-a"/>
	          </div>
	       </c:forEach>
	       <input id="answerKeyStringEncrypted" name="answerKeyStringEncrypted" type="hidden" value="${actionBean.answerKeyStringEncrypted}"/>
	       <input id="questionTitle" name="questionTitle" type="hidden" value="${actionBean.questionTitle}"/>
        </small>         
        <div style="float:left">
           <input data-theme="a" class="process ui-btn-left " data-icon='arrow-l' name="back" value="${backLbl}" type="submit" /> 
        </div>
        <div style="float:right">
           <input data-theme="a" class="process ui-btn-left "  name="save" value="SAVE" type="submit" /> 
        </div>
	</s:form>	 
</div>
<%@ include file="/WEB-INF/jsp/common/footer.jsp" %>
<script>
wpFooterFile = "answerEdit"
</script>

