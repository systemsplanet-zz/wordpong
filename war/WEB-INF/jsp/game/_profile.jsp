<%@ include file="/WEB-INF/jsp/common/taglibs.jsp" %>
<s:useActionBean id="myBean" beanclass="com.wordpong.app.action.game.ProfileActionBean"/>

<!DOCTYPE html> 
<html> 
</head> 
<body> 

<div data-role="page" id="profilePage" data-theme="a">

<div data-role="content" style="padding-top:0px;">
    
<ul data-role="listview" data-inset="true" data-theme="c" data-dividertheme="b"  style="margin-top:0px;">
   <li data-role="list-divider" >Edit Profile</li> 
</ul>

<s:form  id="profileForm" beanclass="com.wordpong.app.action.game.ProfileActionBean" method="post">
<ul data-role="listview" data-inset="true" data-theme="c" data-dividertheme="b">
	<li data-role="list-divider" > 
		<a href=""><img src="/i/p/u.png" style="padding-right:20" /></a>
		<h3>mike@systemsplanet.com</h3> 
		<p>Mike Lawrence</p> 
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
				<s:password name="firstName" tabindex="2" />
			</div>
	</li>
	<li>
			<div data-role="fieldcontain" style="padding:4px;">
				<s:label for="lastName" class="ui-input-text"/>
				<s:password name="lastName" tabindex="2" />
			</div>
	</li>
	<li>
			<div data-role="fieldcontain" style="padding:4px;">
				<s:label for="password" class="ui-input-text"/>
				<s:password name="password" tabindex="3" />
			</div>
	</li>
	<li>
			<div data-role="fieldcontain" style="padding:4px;">
				<s:label for="pictureUrl" class="ui-input-text"/>
				<s:password name="pictureUrl" tabindex="4" />
			</div>
	</li>
</ul>
<div style="float:left">
<input data-theme="a" class="process" name="cancel" value="Cancel" type="submit" />
</div>
<div style="float:right">
	<input data-theme="a" class="process" name="save" value="Save" type="submit" />
</div>


</s:form>
	
	
</div>

</div> <!--  page -->
</body>
</html>
