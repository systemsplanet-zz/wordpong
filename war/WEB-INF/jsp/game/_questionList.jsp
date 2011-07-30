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
	    <ul data-role="listview" data-inset="true" data-theme="c" data-dividertheme="b"  style="margin-top:0px;">
	        <li data-role="list-divider" >${myQuestionsLbl}</li> 
	    </ul>
        <small>            
	        <c:forEach items="${actionBean.questions}" var="questions" >
	           <input onClick="javascript:$('#questionKeyStringEncrypted').val('${questions.keyStringEncrypted}');$('#questionTitle').val('${questions.title}');" data-theme="a" class="process"  name="editQuestions" value="${questions.title}" type="submit" />
	        </c:forEach>
        </small>         
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

