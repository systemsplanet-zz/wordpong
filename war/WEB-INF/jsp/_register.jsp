<%@ include file="/WEB-INF/jsp/common/taglibs.jsp" %>

<div data-role="header" data-theme="b">
	<tags:logo/>
</div>


<div data-role="content" data-theme="a">

	<ul data-role="listview" data-inset="true" data-theme="c"
		data-dividertheme="b">
		<li data-role="list-divider">Sign up for WordPong</li>
	</ul>
	<tags:messages/>
	
	<s:form  beanclass="com.wordpong.app.action.RegisterActionBean" method="post">
	
	
	
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

		
		<p><small>By submitting this information, I acknowledge that
		I have read and agree to: <br />
		&nbsp;&nbsp;<a href="/static/tos.html">Terms of Service</a> and <a
			href="/static/privacy.html">Privacy Policy</a> <br />
		</small></p>
		<s:submit name="process" value="Sign Up" class="process"/>
	</s:form>
	<br />
	<p /><br />
	<div style="float: left;"><a href="/" rel="external">Login</a><br />
	</div>
	<div style="float: right;"><a href="/static/contact.html">Contact Us</a></div>
</div>
<%@ include file="/WEB-INF/jsp/common/footer.jsp" %>

