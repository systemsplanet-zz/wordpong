<%@ include file="/WEB-INF/jsp/common/taglibs.jsp" %>
<fmt:message var="registerLbl" key="login.register" />
<fmt:message var="forgotPasswordLbl" key="login.forgotPassword" />
<fmt:message var="loginLbl" key="login" />
<fmt:message var="contactUsLbl" key="contactUs" />

<div data-role="header" data-theme="b">
	<div style="float:left;margin:12px 0 0 12px" >
	    <tags:logo/>
	</div>
	<div class="wp-right-button" >
	    <s:form  id="loginForm" beanclass="com.wordpong.app.action.RegisterActionBean" method="post">
	        <div style="float:right;margin-right:10px">
	            <div data-theme="a" class="ui-btn ui-btn-up-a ui-btn-icon-right ui-btn-corner-all ui-shadow">
	                <span class="ui-btn-inner ui-btn-corner-all">
	                    <span class="ui-btn-text">${registerLbl}</span>
	                    <span class="ui-icon ui-icon-plus ui-icon-shadow"></span>                    
	                </span>
	                <input name="view" value="${registerLbl}" data-theme="a" class="process ui-btn-left  ui-btn-hidden" data-icon="arrow-l" type="submit">
	            </div> 
	        </div>
		</s:form>
	</div>
</div>
<div style="clear:both"></div>
<div data-role="content" data-theme="a" >
	<tags:messages/>
        
	<s:form  id="loginForm" beanclass="com.wordpong.app.action.LoginActionBean" method="post">
		<div class="box">
			<div data-role="fieldcontain" style="padding:4px;">
				<s:label for="email" class="ui-input-text"/>
				<s:text name="email" tabindex="1"/>
			</div>
			<div data-role="fieldcontain" style="padding:4px;">
				<s:label for="password" class="ui-input-text"/>
				<s:password name="password" tabindex="2" />
			</div>
		</div>
		<s:submit name="process" value="${loginLbl}" class="process"/>
	</s:form>
	<p/>
	<small>
		<span style="float:left">
			<s:form  beanclass="com.wordpong.app.action.ForgotPasswordActionBean" method="post">
				<input data-theme="a" class="process" name="view" value="${forgotPasswordLbl}" type="submit" /> 
			</s:form>
		</span>
		<span style="float:right; margin-top:20px; margin-right:10px;" >
			<a href="/static/contact.html" data-theme="a">${contactUsLbl}</a> 
		</span>
	</small>
</div>
<%@ include file="/WEB-INF/jsp/common/footer.jsp" %>

<script>
wpFooterFile = "login"
</script>
