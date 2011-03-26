<%@ include file="/WEB-INF/jsp/common/taglibs.jsp" %>
<div data-role="header"  data-theme="b">
	<div class="wp-right-button" >
	    <s:form  id="loginForm" beanclass="com.wordpong.app.action.RegisterActionBean" method="post">
			<input name="view" value="Register" type="submit" class="process" style="height:35px"/>
		</s:form>
	</div>
	<tags:logo/>
</div>

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
		<s:submit name="process" value="Login" class="process"/>
	</s:form>
	<p/>
	<small>
		<div style="float:left">
			<s:form  beanclass="com.wordpong.app.action.ForgotPasswordActionBean" method="post">
				<input data-theme="a" class="process" name="view" value="Forgot Password" type="submit" /> 
			</s:form>
		</div>
		<div style="float:right; margin-top:20px; margin-right:10px;" >
			<a href="/static/contact.html" data-theme="a">Contact Us</a> 
		</div>
	</small>
</div>
<%@ include file="/WEB-INF/jsp/common/footer.jsp" %>

