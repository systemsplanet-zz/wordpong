<%@ include file="/WEB-INF/jsp/common/taglibs.jsp" %>
<s:useActionBean id="bean" beanclass="com.wordpong.app.action.game.ProfileActionBean"/>
<div data-role="content" style="padding-top:0px;">
<ul data-role="listview" data-inset="true" data-theme="c" data-dividertheme="b"  style="margin-top:0px;">
	<li data-role="list-divider" >Edit Profile</li> 
</ul>
	<tags:messages/> 

<s:form  id="profileForm" beanclass="com.wordpong.app.action.game.ProfileActionBean" method="post">
<ul data-role="listview" data-inset="true" data-theme="c" data-dividertheme="b">
	<li data-role="list-divider" > 
		<a href=""><img src="/i/p/u.png" style="padding-right:20" /></a>
		<h3>${bean.user.email}</h3> 
		<p>${bean.user.fullName}</p> 
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
				<s:text name="password" tabindex="3" />
			</div>
	</li>
	<li>
			<div data-role="fieldcontain" style="padding:4px;">
				<s:label for="pictureUrl" class="ui-input-text"/>
				<s:text name="pictureUrl" tabindex="4" />
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
