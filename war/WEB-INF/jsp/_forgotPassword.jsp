<%@ include file="/WEB-INF/jsp/common/taglibs.jsp" %>
<s:useActionBean id="bean" beanclass="com.wordpong.app.action.ForgotPasswordActionBean"/>
<fmt:message var="submitLbl" key="submit" />
<fmt:message var="backLbl" key="back" />
<div data-role="content" style="padding-top:0px;">
	<ul data-role="listview" data-inset="true" data-theme="c" data-dividertheme="b" style="margin-top:0px;">
		<li data-role="list-divider"><s:label for="forgotPassword.title"/></li> 
	</ul>
	<tags:messages/> 
	<s:form id="forgotPasswordForm" beanclass="com.wordpong.app.action.ForgotPasswordActionBean" method="post">
		<div data-role="fieldcontain" style="padding:4px;">
			<s:label for="email" class="ui-input-text"/>
			<s:text name="email" tabindex="1"/>
		</div>
		<div style="float:left">
			<input data-theme="a" class="process ui-btn-left" data-icon='arrow-l' name="back" value="${backLbl}" type="submit" /> 
		</div>
		<div style="float:right">

			<input data-theme="a" class="process"  name="submit" value="${submitLbl}" type="submit" />
		</div>	
	</s:form>	
</div>
<%@ include file="/WEB-INF/jsp/common/footer.jsp" %>
<script>
wpFooterFile = "forgotPassword"
</script>
