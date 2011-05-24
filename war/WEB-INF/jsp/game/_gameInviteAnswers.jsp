<%@ include file="/WEB-INF/jsp/common/taglibs.jsp" %>
<s:useActionBean id="bean" beanclass="com.wordpong.app.action.game.GameInviteAnswersActionBean"/>
<fmt:message var="acceptLbl" key="accept" />
<fmt:message var="ignoreLbl" key="ignore" />
<fmt:message var="backLbl" key="back" />

<div data-role="header"  data-nobackbtn="true" data-theme="b">
<s:form id="gameInviteAnswersForm" beanclass="com.wordpong.app.action.game.GameInviteAnswersActionBean" method="post">
    <div>
	    <!-- Back Button -->
	    <div style="float:left;margin-left:10px">
	        <div data-theme="a" class="ui-btn ui-btn-up-a ui-btn-icon-left ui-btn-corner-all ui-shadow">
	            <span class="ui-btn-inner ui-btn-corner-all">
	               <span class="ui-btn-text">${backLbl}</span>
	               <span class="ui-icon ui-icon-arrow-l ui-icon-shadow"></span>
	            </span>
	            <input name="back" value="${backLbl}" data-theme="a" class="process ui-btn-left  ui-btn-hidden" data-icon="arrow-l" type="submit">
	        </div> 
	    </div>
    </div>
</s:form>   
</div>
<div style="clear:both"></div>

<div data-role="content" style="padding-top:0px;">
	<tags:messages/> 
	<s:form id="gameInviteAnswersForm" beanclass="com.wordpong.app.action.game.GameInviteAnswersActionBean" method="post">
		<ul data-role="listview" data-inset="true" data-theme="c" data-dividertheme="b" style="margin-top:0px;">
			<li data-role="list-divider" ><s:label for="select questions to send title"/></li> 
	        <small>            
		        <c:forEach items="${actionBean.answers}" var="answers" >
					<input onClick="javascript:$('#answerKeyString').val('${answers.keyString}');$('#questionDescription').val('${answers.questionDescription}');" data-theme="a" class="process"  name="selectAnswers" value="${answers.questionDescription}" type="submit" />
	 	        </c:forEach>
	        </small>         
	        <input id="answerKeyString" name="answerKeyString" type="hidden" value=""/>
	        <input id="questionDescription" name="questionDescription" type="hidden" value=""/>
		</ul>
        <div style="float:left">
            <input data-theme="a" class="process ui-btn-left " data-icon='arrow-l' name="back" value="${backLbl}" type="submit" /> 
        </div>
    </s:form>   
</div>
<%@ include file="/WEB-INF/jsp/common/footer.jsp" %>


