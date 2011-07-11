<%@ include file="/WEB-INF/jsp/common/taglibs.jsp" %>
<s:useActionBean id="bean" beanclass="com.wordpong.app.action.game.NewGameActionBean"/>
<fmt:message var="backLbl" key="back" />
<fmt:message var="addAnswerLbl" key="newGame.addAnswers" />
<fmt:message var="selectAnswerLbl" key="newGame.selectAnswer" />

<div data-role="header"  data-nobackbtn="true" data-theme="b">
<s:form  beanclass="com.wordpong.app.action.game.NewGameActionBean" method="post">
    <div>
        <!-- Back Button -->
        <span style="float:left;margin-left:10px">
            <div data-theme="a" class="ui-btn ui-btn-up-a ui-btn-icon-left ui-btn-corner-all ui-shadow">
	            <span class="ui-btn-inner ui-btn-corner-all">
	               <span class="ui-btn-text">${backLbl}</span>
    	           <span class="ui-icon ui-icon-arrow-l ui-icon-shadow"></span>
	            </span>
                <input name="back" value="${backLbl}" data-theme="a" class="process ui-btn-left  ui-btn-hidden" data-icon="arrow-l" type="submit">
            </div> 
        </span>
        
        <!-- Add Button -->
        <span style="float:right;margin-right:10px">
            <div data-theme="a" class="ui-btn ui-btn-up-a ui-btn-icon-right ui-btn-corner-all ui-shadow">
                <span class="ui-btn-inner ui-btn-corner-all">
                    <span class="ui-btn-text">${addAnswerLbl}</span>
                    <span class="ui-icon ui-icon-plus ui-icon-shadow"></span>                    
                </span>
                <input name="addAnswer" value="${addAnswerLbl}" data-theme="a" class="process ui-btn-left  ui-btn-hidden" type="submit">
            </div> 
        </span>
    </div>
</s:form>
<div style="clear:both"></div>

<div data-role="content" style="padding-top:0px;">
	<tags:messages/> 
	<s:form id="newGame_AnswerForm" beanclass="com.wordpong.app.action.game.NewGameActionBean" method="post">
	    <ul data-role="listview" data-inset="true" data-theme="c" data-dividertheme="b"  style="margin-top:0px;">
	        <li data-role="list-divider" >${selectAnswerLbl}</li> 
	        <small>            
		        <c:forEach items="${actionBean.answers}" var="answers" >
		           <input onClick="javascript:$('#answerKeyStringEncrypted').val('${answers.keyStringEncrypted}');$('#questionDescription').val('${answers.questionDescription}');" data-theme="a" class="process"  name="selectAnswer" value="${answers.questionDescription}" type="submit" />
		        </c:forEach>
	        </small>         
	        <s:hidden id="answerKeyStringEncrypted" name="answerKeyStringEncrypted" value="?answerKeyStringEncrypted?"/>
	        <s:hidden id="questionDescription" name="questionDescription" value="?questionDescription?"/>
            <s:hidden id="friendKeyStringEncrypted" name="friendKeyStringEncrypted" value="${actionBean.friendKeyStringEncrypted}" />	
            <s:hidden id="friendDetails" name="friendDetails" value="${actionBean.friendDetails}" />	
	    </ul>
        <div style="float:left">
            <input data-theme="a" class="process ui-btn-left " data-icon='arrow-l' name="back" value="${backLbl}" type="submit" /> 
        </div>
	</s:form>	
</div>
<%@ include file="/WEB-INF/jsp/common/footer.jsp" %>
<script>
wpFooterFile = "newGame_Answer"
</script>

