<%@ include file="/WEB-INF/jsp/common/taglibs.jsp" %>
<s:useActionBean id="bean" beanclass="com.wordpong.app.action.game.AnswerListActionBean"/>
<fmt:message var="backLbl" key="back" />
<fmt:message var="addAnswerLbl" key="answerList.addAnswers" />
<fmt:message var="myAnswersLbl" key="answerList.myAnswers" />

<div data-role="header"  data-nobackbtn="true" data-theme="b">
<form id="answerListForm" action="/game/AnswerList.wp" method="post">
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
</form>
<div style="clear:both"></div>

<div data-role="content" style="padding-top:0px;">
	<tags:messages/> 
	<s:form id="answerListForm" beanclass="com.wordpong.app.action.game.AnswerListActionBean" method="post">
    <ul data-role="listview" data-inset="true" data-theme="c" data-dividertheme="b"  style="margin-top:0px;">
        <li data-role="list-divider" >${myAnswersLbl}</li> 
        <small>            

        <c:forEach items="${actionBean.myAnswerList}" var="myAnswerList" >
                <input data-theme="a" class="process"  name="accept" value="${myAnswerList.answerInfo}" type="submit" />
        </c:forEach>
        
        </small>         
    </ul>

        <div style="float:left">
            <input data-theme="a" class="process ui-btn-left " data-icon='arrow-l' name="back" value="${backLbl}" type="submit" /> 
        </div>
	</s:form>	
</div>
<%@ include file="/WEB-INF/jsp/common/footer.jsp" %>
