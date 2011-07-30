<%@ include file="/WEB-INF/jsp/common/taglibs.jsp" %>
<s:useActionBean id="bean" beanclass="com.wordpong.app.action.game.AnswerAddActionBean"/>
<fmt:message var="backLbl" key="back" />
<fmt:message var="selectQuestionLbl" key="answerAdd.selectQuestion" />

<div data-role="header"  data-nobackbtn="true" data-theme="b">
    <div>
        <!-- Back Button -->
        <span style="float:left;margin-left:10px">
            <span data-theme="a" class="ui-btn ui-btn-up-a ui-btn-icon-left ui-btn-corner-all ui-shadow">
	            <span class="ui-btn-inner ui-btn-corner-all">
	               <span class="ui-btn-text">${backLbl}</span>
    	           <span class="ui-icon ui-icon-arrow-l ui-icon-shadow"></span>
	            </span>
				<form id="answerAddFormHead" action="/game/AnswerAdd.wp" method="post">
                	<input name="back" value="${backLbl}" data-theme="a" class="process ui-btn-left  ui-btn-hidden" data-icon="arrow-l" type="submit">
				</form>
            </span> 
        </span>
    </div>
</div>
<div style="clear:both"></div>

<div data-role="content" style="padding-top:0px;">
	<tags:messages/> 
	<s:form id="answerAddForm" beanclass="com.wordpong.app.action.game.AnswerAddActionBean" method="post">

		<ul data-role="listview" data-inset="true" data-theme="c" data-dividertheme="b"> 
			<li data-role="list-divider">${selectQuestionLbl}</li> 
		</ul>
	
		<ul data-role="listview" data-inset="true" data-theme="c" data-dividertheme="b"  style="margin-top:0px;">
		    <c:forEach items="${actionBean.questionList}" var="q" >
				<li data-role="list-divider"   style="white-space:normal;" >
					<a  onclick="javascript:$('#questionKeyStringEncrypted').val('${q.keyStringEncrypted}');$('#questionTitle').val('${q.title}');$('#editAnswer').val('${q.title}');$('#editAnswer').click();" href="#">
						<%-- <img src="${game.inviteePictureUrl}"  > --%>
						<h3 style="white-space:normal;">${q.title}</h3> 
						<p style="white-space:normal;">${q.description}</p>
<%-- TODO:				<p>visibilityLbl: ${q.visibility}</p>
						<p>intimacyLbl: ${q.intimacyLevel}</p>
--%>
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

