<%@ include file="/WEB-INF/jsp/common/taglibs.jsp" %>
<fmt:message var="titleLbl" key="register.title" />
<fmt:message var="privacyPolicyLbl" key="register.privacyPolicy" />
<fmt:message var="tosLbl" key="register.tos" />
<fmt:message var="acknowledgementLbl" key="register.acknowledgement" />
<fmt:message var="andLbl" key="and" />
<fmt:message var="signUpLbl" key="register.signUp" />
<fmt:message var="contactUsLbl" key="contactUs" />
<fmt:message var="loginLbl" key="login" />

<div data-role="header" data-theme="b">
	<center>
		<tags:logo/>
	</center>
</div>

<div data-role="content" data-theme="a" style="padding-top:0px;" >
	<s:form  beanclass="com.wordpong.app.action.RegisterActionBean" method="post">	
	<ul data-role="listview" data-inset="true" data-theme="c"
		data-dividertheme="b">
		<li data-role="list-divider">${titleLbl}</li>
	</ul>
	<tags:messages/>
	
		<div data-role="fieldcontain" style="padding:4px;">
				<s:label for="firstName" class="ui-input-text"/>
				<s:text name="firstName" tabindex="1"/>
		</div>
		<div data-role="fieldcontain" style="padding:4px;">
				<s:label for="lastName" class="ui-input-text"/>
				<s:text name="lastName" tabindex="2"/>
		</div>
		<div data-role="fieldcontain" style="padding:4px;">
				<s:label for="email" class="ui-input-text"/>
				<s:text name="email" tabindex="3"/>
		</div>
		<div data-role="fieldcontain" style="padding:4px;">
				<s:label for="password" class="ui-input-text"/>
				<s:password name="password" tabindex="4"/>
		</div>
			
		<p><small>${acknowledgementLbl}: <br />
		&nbsp;&nbsp;<a href="/static/tos.html">${tosLbl}</a> ${andLbl} <a
			href="/static/privacy.html">${privacyPolicyLbl}</a> <br />
		</small></p>
		<s:submit name="process" value="${signUpLbl}" class="process"/>
	<br />
	<p />
	<br />
	<div style="float: left;">
		<sdyn:submit name="login" value="${loginLbl}" class="process ui-btn-left " data-icon='arrow-l'/>
		<br />
	</div>
	<div style="float: right;"><a href="/static/contact.html">${contactUsLbl}</a></div>
	</s:form>
</div>
<%@ include file="/WEB-INF/jsp/common/footer.jsp" %>
<script>
wpFooterFile = "register"
</script>
