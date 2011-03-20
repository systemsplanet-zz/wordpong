<%@ include file="/WEB-INF/jsp/common/taglibs.jsp" %>
<s:useActionBean id="bean" beanclass="com.wordpong.app.action.game.ProfileActionBean"/>
<div data-role="content" style="padding-top:0px;">
<ul data-role="listview" data-inset="true" data-theme="c" data-dividertheme="b" style="margin-top:0px;">
	<li data-role="list-divider" >Edit Profile</li> 
</ul>
<tags:messages/> 
<s:form id="profileForm" beanclass="com.wordpong.app.action.game.ProfileActionBean" method="post">
<ul data-role="listview" data-inset="true" data-theme="c" data-dividertheme="b">
	<li data-role="list-divider"> 
		<img src="${bean.user.pictureUrl}" class="ui-li-icon"/>
		<p style="padding-left:50px; padding-top:10px">${bean.user.fullName}</p> 
		<p style="padding-left:50px;">${bean.user.email}</p>
	</li> 
	<li>
		<div data-role="fieldcontain" style="padding:4px;">
			<s:label for="email" class="ui-input-text"/>
			<s:text name="email" tabindex="1"/>
		</div>
	</li>
	<li>
		<div data-role="fieldcontain" style="padding:4px;">
			<s:label for="firstName" class="ui-input-text"/>
			<s:text name="firstName" tabindex="2" />
		</div>
	</li>
	<li>
		<div data-role="fieldcontain" style="padding:4px;">
			<s:label for="lastName" class="ui-input-text"/>
			<s:text name="lastName" tabindex="2" />
		</div>
	</li>
	<li>
		<div data-role="fieldcontain" style="padding:4px;">
			<s:label for="password" class="ui-input-text"/>
			<s:password name="password" repopulate="true" tabindex="3" />
		</div>
	</li>
	<li>
		<div data-role="fieldcontain" style="padding:4px;">
			<s:label for="picture" class="ui-input-text"/>
			<a href="http://gravatar.com" rel="external">gravatar.com</a>
		</div>
	</li>
</ul>
<div style="float:left">
	<input data-theme="a" class="process ui-btn-left " data-icon='arrow-l' name="back" value="Back" type="submit" /> 
</div>
<div style="float:right">
	<input data-theme="a" class="process" action="save" name="save" value="Save" type="submit" />
</div>

</s:form>	
</div>