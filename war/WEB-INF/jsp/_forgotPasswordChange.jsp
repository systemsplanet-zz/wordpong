<%@ include file="/WEB-INF/jsp/common/taglibs.jsp" %>
<s:useActionBean id="bean" beanclass="com.wordpong.app.action.ForgotPasswordChangeActionBean"/>
<div data-role="content" style="padding-top:0px;">
	<ul data-role="listview" data-inset="true" data-theme="c" data-dividertheme="b" style="margin-top:0px;">
		<li data-role="list-divider" >Change Password</li> 
	</ul>
	<tags:messages/> 
	<s:form id="forgotPasswordForm" beanclass="com.wordpong.app.action.ForgotPasswordChangeActionBean" method="post">
		<ul data-role="listview" data-inset="true" data-theme="c" data-dividertheme="b">
			<li>
				<div data-role="fieldcontain" style="padding:4px;">
					<s:label for="code" class="ui-input-text"/>
					<s:text name="code" tabindex="2"/>
				</div>
			</li>
			<li>
				<div data-role="fieldcontain" style="padding:4px;">
					<s:label for="password" class="ui-input-text"/>
					<s:password name="password" repopulate="true" tabindex="3" />
				</div>
			</li>			
		</ul>
		<div style="float:left">
			<input data-theme="a" class="process ui-btn-left " data-icon='arrow-l' name="back" value="Back" type="submit" /> 
		</div>
		<div style="float:right">
			<input data-theme="a" class="process"  name="submit" value="Submit" type="submit" />
		</div>	
	</s:form>	
</div>
<%@ include file="/WEB-INF/jsp/common/footer.jsp" %>

