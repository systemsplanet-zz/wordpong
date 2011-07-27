<%@ include file="/WEB-INF/jsp/common/taglibs.jsp" %>
<s:useActionBean id="bean" beanclass="com.wordpong.app.action.game.QuestionAddEditActionBean"/>
<fmt:message var="backLbl" key="back" />
<fmt:message var="enterQuestionsLbl" key="questionAddEdit.enterQuestions" />

<div data-role="header"  data-nobackbtn="true" data-theme="b">
<form id="questionAddEditFormHead" action="/game/QuestionAddEdit.wp" method="post">
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
        
    </div>
</form>
</div>
<div style="clear:both"></div>

<div data-role="content" style="padding-top:0px;">
	<s:form id="questionAddEditForm" beanclass="com.wordpong.app.action.game.QuestionAddEditActionBean" method="post">
	    <ul data-role="listview" data-inset="true" data-theme="c" data-dividertheme="b"  style="margin-top:0px;">
	        <li data-role="list-divider" >${enterQuestionsLbl}</li> 
        </ul>
        <tags:messages/> 
	        <small>      
	           
	        <!-- TODO: required -->
			<span data-role="fieldcontain">
				<label for="title">Title:</label>	
				<s:text id="title" name="title" tabindex="1"/>
			</span>
	
			<!-- TODO: required -->
			<span data-role="fieldcontain">
				<label for="description">Description:</label>	
				<s:text id="description" name="description" tabindex="2"   />
			</span>
			
 	        <c:forEach items="${actionBean.questions}" var="i"  varStatus="s">
	           <div data-role="fieldcontain" style="padding:4px;">
	               <s:label for="questions[${s.index}]" class="ui-input-text">Question ${s.index + 1}:</s:label>		              
	               <sdyn:text name="questions[${s.index}]" id="questions[${s.index}]" tabindex="3" maxlength="100" type="text" class="ui-input-text ui-body-null ui-corner-all ui-shadow-inset ui-body-a"/>
	           </div>
 	        </c:forEach>
	
			<span data-role="fieldcontain">
				<label for="intimacyLevel">Intimacy Level:</label>	
				<s:select name="intimacyLevel" id="intimacyLevel" size="1" tabindex="4">
				    <%-- <s:option value="" SELECTED="true">Choose One</s:option> --%>
				    <s:option value="0">Cliche</s:option>
				    <s:option value="1">Fact</s:option>
				    <s:option value="2">Opinion</s:option>
				    <s:option value="3">Hopes and Dreams</s:option>
				    <s:option value="4">Feelings</s:option>
				    <s:option value="5">Fears/Failures/Weaknesses</s:option>
				    <s:option value="6">Needs</s:option>
			 	</s:select>
			</span>
	
			<span data-role="fieldcontain">
				<label for="visibility">Visibility:</label>	
				<s:select name="visibility" id="visibility" size="1" tabindex="5">
				 <s:option value="0" >Private</s:option>
				<s:option value="1">Public</s:option>
			 	</s:select>
			</span>
	
	           		       
	        </small>         
        <div style="float:left">
           <input data-theme="a" class="process ui-btn-left " data-icon='arrow-l' name="back" value="${backLbl}" type="submit" tabindex="6"/> 
        </div>
        <div style="float:right">
           <input data-theme="a" class="process ui-btn-left "  name="save" value="SAVE" type="submit" /> 
        </div>
	</s:form>	 
</div>
<%@ include file="/WEB-INF/jsp/common/footer.jsp" %>
<script>
wpFooterFile = "questionAddEdit"
</script>

