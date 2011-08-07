<%@ include file="/WEB-INF/jsp/common/taglibs.jsp" %>
<s:useActionBean id="bean" beanclass="com.wordpong.app.action.game.FriendAnswerActionBean"/>
<fmt:message var="backLbl" key="back" />
<div data-role="content" style="padding-top:0px;">
	<%-- NAVIGATION --%>
	<span class="wp-nav">
		<a data-icon="arrow-l" 		onclick="javascript:$('#back').click()" 		href="#" data-role="button" data-iconpos="notext" >Back</a>
		<a data-icon="home" 		onclick="javascript:$('#homeBtn').click()" 		href="#" data-role="button" data-iconpos="notext">Home</a>
		<a data-icon="wp-friend" 	onclick="javascript:$('#friendsBtn').click()" 	href="#" data-role="button" data-iconpos="notext">Friends</a>
		<a data-icon="wp-question" 	onclick="javascript:$('#questionsBtn').click()" href="#" data-role="button" data-iconpos="notext">Question</a>
		<a data-icon="check" 		onclick="javascript:$('#answersBtn').click()" 	href="#" data-role="button" data-iconpos="notext">Answer</a>
		<a data-icon="gear"			onclick="javascript:$('#profileBtn').click()" 	href="#" data-role="button" data-iconpos="notext" style='visibility:hidden;'>Profile</a>
		<a data-icon="plus" 		onclick="javascript:$('#').click()" 			href="#" data-role="button" data-iconpos="notext" style='visibility:hidden;'></a>
		<tags:navigation/>
	</span>
	<div style="clear:both"></div>
	<span class="wp-invisible">
		<s:form id="friendAnswerFormBack" beanclass="com.wordpong.app.action.game.FriendAnswerActionBean" method="post">
			<input name="back" id="back" value="${backLbl}" data-theme="a" class="process ui-btn-left  ui-btn-hidden" data-icon="arrow-l" type="submit">
		</s:form>
	</span>

	<s:form id="friendAnswerForm" beanclass="com.wordpong.app.action.game.FriendAnswerActionBean" method="post">
	    <ul data-role="listview" data-inset="true" data-theme="c" data-dividertheme="b"  style="margin-top:0px;">
	        <li data-role="list-divider" >${actionBean.question.title}</li> 
	        <li>
	     	   <img src="${actionBean.game.inviterPictureUrl}"  >
	     	   <h3 style="white-space:normal;"><small>${actionBean.game.inviterDetails}</small></h3> 
     	   </li>
        </ul>
        <tags:messages/>
         
        <small>            
 	       <c:forEach items="${actionBean.question.questions}" var="i"  varStatus="s">	 	       
	          <div data-role="fieldcontain" style="padding:4px;">
	              <s:label for="questions[${s.index}]" class="ui-input-text">${i}</s:label>
	              <s:label name="questions[${s.index}]" id="questions[${s.index}]" >${actionBean.answer.answers[s.index]}</s:label>
	          </div>
	       </c:forEach>
        </small>         
        <div style="float:left">
           <input data-theme="a" class="process ui-btn-left " data-icon='arrow-l' name="back" value="${backLbl}" type="submit" /> 
        </div>
	</s:form>	 
</div>
<%@ include file="/WEB-INF/jsp/common/footer.jsp" %>
<script>
wpFooterFile = "friendAnswer"
</script>

