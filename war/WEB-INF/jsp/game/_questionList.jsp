<%@ include file="/WEB-INF/jsp/common/taglibs.jsp" %>
<s:useActionBean id="bean" beanclass="com.wordpong.app.action.game.QuestionListActionBean"/>
<fmt:message var="backLbl" key="back" />
<fmt:message var="addQuestionLbl" key="questionList.addQuestions" />
<fmt:message var="myQuestionsLbl" key="questionList.myQuestions" />

<div data-role="header"  data-nobackbtn="true" data-theme="b">
<form id="questionListForm" action="/game/QuestionList.wp" method="post">
    <div>
        <!-- Back Button -->
        <span style="float:left;margin-left:10px">
            <span data-theme="a" class="ui-btn ui-btn-up-a ui-btn-icon-left ui-btn-corner-all ui-shadow">
	            <span class="ui-btn-inner ui-btn-corner-all">
	               <span class="ui-btn-text">${backLbl}</span>
    	           <span class="ui-icon ui-icon-arrow-l ui-icon-shadow"></span>
	            </span>
                <input name="back" value="${backLbl}" data-theme="a" class="process ui-btn-left  ui-btn-hidden" data-icon="arrow-l" type="submit">
            </span> 
        </span>
        
        <!-- Add Button -->
        <span style="float:right;margin-right:10px">
            <span data-theme="a" class="ui-btn ui-btn-up-a ui-btn-icon-right ui-btn-corner-all ui-shadow">
                <span class="ui-btn-inner ui-btn-corner-all">
                    <span class="ui-btn-text">${addQuestionLbl}</span>
                    <span class="ui-icon ui-icon-plus ui-icon-shadow"></span>                    
                </span>
                <input name="addQuestion" value="${addQuestionLbl}" data-theme="a" class="process ui-btn-left  ui-btn-hidden" type="submit">
            </span> 
        </span>
    </div>
</form>
</div>
<div style="clear:both"></div>

<div data-role="content" style="padding-top:0px;">
	<tags:messages/> 
	<s:form id="questionListForm" beanclass="com.wordpong.app.action.game.QuestionListActionBean" method="post">
		<ul data-role="listview" data-inset="true" data-theme="c" data-dividertheme="b"> 
			<li data-role="list-divider">${myQuestionsLbl}</li> 
		</ul>
	
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

