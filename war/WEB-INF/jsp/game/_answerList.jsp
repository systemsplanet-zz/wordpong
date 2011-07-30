<%@ include file="/WEB-INF/jsp/common/taglibs.jsp" %>
<s:useActionBean id="bean" beanclass="com.wordpong.app.action.game.AnswerListActionBean"/>
<fmt:message var="backLbl" key="back" />
<fmt:message var="addAnswerLbl" key="answerList.addAnswers" />
<fmt:message var="myAnswersLbl" key="answerList.myAnswers" />

<div data-role="header"  data-nobackbtn="true" data-theme="b">
    <div>
        <!-- Back Button -->
        <span style="float:left;margin-left:10px">
            <span data-theme="a" class="ui-btn ui-btn-up-a ui-btn-icon-left ui-btn-corner-all ui-shadow">
	            <span class="ui-btn-inner ui-btn-corner-all">
	               <span class="ui-btn-text">${backLbl}</span>
    	           <span class="ui-icon ui-icon-arrow-l ui-icon-shadow"></span>
	            </span>
				<form id="answerListForm1" action="/game/AnswerList.wp" method="post">
                	<input name="back" value="${backLbl}" data-theme="a" class="process ui-btn-left  ui-btn-hidden" data-icon="arrow-l" type="submit">
				</form>
            </span> 
        </span>
        
        <!-- Add Button -->
        <span style="float:right;margin-right:10px">
            <span data-theme="a" class="ui-btn ui-btn-up-a ui-btn-icon-right ui-btn-corner-all ui-shadow">
                <span class="ui-btn-inner ui-btn-corner-all">
                    <span class="ui-btn-text">${addAnswerLbl}</span>
                    <span class="ui-icon ui-icon-plus ui-icon-shadow"></span>                    
                </span>
				<form id="answerListForm2" action="/game/AnswerList.wp" method="post">
                	<input name="addAnswer" value="${addAnswerLbl}" data-theme="a" class="process ui-btn-left  ui-btn-hidden" type="submit">
				</form>
            </span> 
        </span>
    </div>
</div>
<div style="clear:both"></div>

<div data-role="content" style="padding-top:0px;">
	<tags:messages/> 
	<s:form id="answerListForm" beanclass="com.wordpong.app.action.game.AnswerListActionBean" method="post">
	
	
	
		<ul data-role="listview" data-inset="true" data-theme="c" data-dividertheme="b"> 
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

