<%@ include file="/WEB-INF/jsp/common/taglibs.jsp" %>
<s:useActionBean id="bean" beanclass="com.wordpong.app.action.game.AnswerAddActionBean"/>
<fmt:message var="backLbl" key="back" />
<fmt:message var="selectQuestionLbl" key="answerAdd.selectQuestion" />

<div data-role="header"  data-nobackbtn="true" data-theme="b">
<form id="answerAddFormHead" action="/game/AnswerAdd.wp" method="post">
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
        
    </div>
</form>
<div style="clear:both"></div>

<div data-role="content" style="padding-top:0px;">
	<tags:messages/> 
	<s:form id="answerAddForm" beanclass="com.wordpong.app.action.game.AnswerAddActionBean" method="post">
    <ul data-role="listview" data-inset="true" data-theme="c" data-dividertheme="b"  style="margin-top:0px;">
        <li data-role="list-divider" >${selectQuestionLbl}</li> 
        <small>            

        <c:forEach items="${actionBean.questionList}" var="questionList" >
                <input onClick="javascript:$('#questionKeyString').val('${questionList.questionKeyString}');$('#questionDescription').val('${questionList.questionDescription}');" data-theme="a" class="process"  name="editAnswer" value="${questionList.questionDescription}" type="submit" />
        </c:forEach>
        
        </small>         
    </ul>
    <input id="questionKeyString" name="questionKeyString" type="hidden" value=""/>
    <input id="questionDescription" name="questionDescription" type="hidden" value=""/>

        <div style="float:left">
            <input data-theme="a" class="process ui-btn-left " data-icon='arrow-l' name="back" value="${backLbl}" type="submit" /> 
        </div>
	</s:form>	 
</div>
<%@ include file="/WEB-INF/jsp/common/footer.jsp" %>

