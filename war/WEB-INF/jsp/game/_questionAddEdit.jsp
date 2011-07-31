<%@ include file="/WEB-INF/jsp/common/taglibs.jsp" %>
<s:useActionBean id="bean" beanclass="com.wordpong.app.action.game.QuestionAddEditActionBean"/>
<fmt:message var="backLbl" key="back" />
<fmt:message var="saveLbl" key="save" />
<fmt:message var="enterQuestionsLbl" key="questionAddEdit.enterQuestions" />
<fmt:message var="titleLbl" key="questionAddEdit.title" />
<fmt:message var="descriptionLbl" key="questionAddEdit.description" />
<fmt:message var="intimacyLevelLbl" key="questionAddEdit.intimacyLevel" />
<fmt:message var="clicheLbl" key="questionAddEdit.cliche" />
<fmt:message var="factLbl" key="questionAddEdit.fact" />
<fmt:message var="opinionLbl" key="questionAddEdit.opinion" />
<fmt:message var="hopesAndDreamsLbl" key="questionAddEdit.hopesAndDreams" />
<fmt:message var="feelingsLbl" key="questionAddEdit.feelings" />
<fmt:message var="fearsFailuresWeaknessesLbl" key="questionAddEdit.fearsFailuresWeaknesses" />
<fmt:message var="needsLbl" key="questionAddEdit.needs" />
<fmt:message var="questionLbl" key="questionAddEdit.question" />
<div data-role="content" style="padding-top:0px;">
	<%-- NAVIGATION --%>
	<span class="wp-nav">
		<a data-icon="arrow-l" 		onclick="javascript:$('#back').click()" 		href="#" data-role="button" data-iconpos="notext">Back</a>
		<a data-icon="home" 		onclick="javascript:$('#homeBtn').click()" 		href="#" data-role="button" data-iconpos="notext">Home</a>
		<a data-icon="wp-friend" 	onclick="javascript:$('#friendsBtn').click()" 	href="#" data-role="button" data-iconpos="notext">Friends</a>
		<a data-icon="wp-question" 	onclick="javascript:$('#questionsBtn').click()" href="#" data-role="button" data-iconpos="notext">Question</a>
		<a data-icon="check" 		onclick="javascript:$('#answersBtn').click()" 	href="#" data-role="button" data-iconpos="notext">Answer</a>
		<a data-icon="gear"			onclick="javascript:$('#profileBtn').click()" 	href="#" data-role="button" data-iconpos="notext">Profile</a>
		<a data-icon="plus" 		onclick="javascript:$('#').click()" 			href="#" data-role="button" data-iconpos="notext" style='visibility:hidden;'></a>
		<tags:navigation/>
	</span>
	<div style="clear:both"></div>
	<span class="wp-invisible">
		<s:form beanclass="com.wordpong.app.action.game.QuestionAddEditActionBean" method="post">
			<input name="back" id="back" value="${backLbl}" class="process" type="submit">
		</s:form>
	</span>
	<s:form id="questionAddEditForm" beanclass="com.wordpong.app.action.game.QuestionAddEditActionBean" method="post">
	    <ul data-role="listview" data-inset="true" data-theme="c" data-dividertheme="b"  style="margin-top:0px;">
	        <li data-role="list-divider" >${enterQuestionsLbl}</li> 
        </ul>
        <tags:messages/> 
        <small>             
			<span data-role="fieldcontain">
				<label for="title">${titleLbl}:</label>	
				<s:text id="title" name="title" tabindex="1"/>
			</span>
	
			<span data-role="fieldcontain">
				<label for="description">${descriptionLbl}:</label>	
				<s:text id="description" name="description" tabindex="2"   />
			</span>
			
 	        <c:forEach items="${actionBean.questions}" var="i"  varStatus="s">
	           <div data-role="fieldcontain" style="padding:4px;">
	               <s:label for="questions[${s.index}]" class="ui-input-text">${questionLbl} ${s.index + 1}:</s:label>		              
	               <sdyn:text name="questions[${s.index}]" id="questions[${s.index}]" tabindex="3" maxlength="100" type="text" class="ui-input-text ui-body-null ui-corner-all ui-shadow-inset ui-body-a"/>
	           </div>
 	        </c:forEach>
	
			<span data-role="fieldcontain">
				<label for="intimacyLevel">${intimacyLevelLbl}:</label>	
				<s:select name="intimacyLevel" id="intimacyLevel" size="1" tabindex="4">
				    <%-- <s:option value="" SELECTED="true">Choose One</s:option> --%>
				    <s:option value="0">${clicheLbl}</s:option>
				    <s:option value="1">${factLbl}</s:option>
				    <s:option value="2">${opinionLbl}</s:option>
				    <s:option value="3">${hopesAndDreamsLbl}</s:option>
				    <s:option value="4">${feelingsLbl}</s:option>
				    <s:option value="5">${fearsFailuresWeaknessesLbl}</s:option>
				    <s:option value="6">${needsLbl}</s:option>
			 	</s:select>
			</span>
	<%--
			<span data-role="fieldcontain">
				<label for="visibility">Visibility:</label>	
				<s:select name="visibility" id="visibility" size="1" tabindex="5">
				 <s:option value="0" >Private</s:option>
				<s:option value="1">Public</s:option>
			 	</s:select>
			</span>
	 --%>
	           		       
        </small>         
        <div style="float:left">
           <input data-theme="a" class="process ui-btn-left " data-icon='arrow-l' name="back" value="${backLbl}" type="submit" tabindex="6"/> 
        </div>
        <div style="float:right">
           <input data-theme="a" class="process ui-btn-left "  name="save" value="${saveLbl}" type="submit" /> 
        </div>
	</s:form>	 
</div>
<%@ include file="/WEB-INF/jsp/common/footer.jsp" %>
<script>
wpFooterFile = "questionAddEdit"
</script>

