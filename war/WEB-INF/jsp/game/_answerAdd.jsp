<%@ include file="/WEB-INF/jsp/common/taglibs.jsp" %>
<s:useActionBean id="bean" beanclass="com.wordpong.app.action.game.AnswerAddActionBean"/>
<fmt:message var="backLbl" key="back" />
<fmt:message var="selectQuestionLbl" key="answerAdd.selectQuestion" />
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
	<s:form id="answerAddForm" beanclass="com.wordpong.app.action.game.AnswerAddActionBean" method="post">
		<ul data-role="listview" data-inset="true" data-theme="c" data-dividertheme="b" style="margin-top:0px;"> 
			<li data-role="list-divider">${selectQuestionLbl}</li> 
		</ul>
		<tags:messages/> 
		<ul data-role="listview" data-inset="true" data-theme="c" data-dividertheme="b"  style="margin-top:0px;">
		    <c:forEach items="${actionBean.questionList}" var="q" >
				<li data-role="list-divider"   style="white-space:normal;" >
					<a  onclick="javascript:$('#questionKeyStringEncrypted').val('${q.keyStringEncrypted}');$('#questionTitle').val('${q.title}');$('#editAnswer').val('${q.title}');$('#editAnswer').click();" href="#">
						<%-- <img src="${game.inviteePictureUrl}"  > --%>
						<h3 style="white-space:normal;">${q.title}</h3> 
						<p style="white-space:normal;">${q.description}</p>
			       	</a>
	        	</li>	        	    
		    </c:forEach>
    	</ul>            
    	<div style='visibility:hidden; height:0; padding:0 margin:0'>
			<s:button id="editAnswer" name="editAnswer" value="?editAnswer?" class="process"/>    
		    <input id="questionKeyStringEncrypted" name="questionKeyStringEncrypted" type="hidden" value=""/>
		    <input id="questionTitle" name="questionTitle" type="hidden" value=""/>
		</div>
        <div style="float:left">
            <input data-theme="a" class="process ui-btn-left " data-icon='arrow-l' name="back" value="${backLbl}" type="submit" /> 
        </div>
	</s:form>	 
</div>
<%@ include file="/WEB-INF/jsp/common/footer.jsp" %>
<script>
wpFooterFile = "answerAdd"
</script>

