<%@ include file="/WEB-INF/jsp/common/taglibs.jsp" %>
<fmt:message var="saveLbl" key="save" />
<fmt:message var="backLbl" key="back" />
<fmt:message var="emailLbl" key="email" />
<s:useActionBean id="bean" beanclass="com.wordpong.app.action.game.ProfileEditActionBean"/>
<div data-role="content" style="padding-top:0px;">
	<%-- NAVIGATION --%>
	<span class="wp-nav">
		<a data-icon="arrow-l" 		onclick="javascript:$('#homeBtn').click()" 		href="#" data-role="button" data-iconpos="notext" style='visibility:hidden;'>Back</a>
		<a data-icon="home" 		onclick="javascript:$('#homeBtn').click()" 		href="#" data-role="button" data-iconpos="notext">Home</a>
		<a data-icon="wp-friend" 	onclick="javascript:$('#friendsBtn').click()" 	href="#" data-role="button" data-iconpos="notext">Friends</a>
		<a data-icon="wp-question" 	onclick="javascript:$('#questionsBtn').click()" href="#" data-role="button" data-iconpos="notext">Question</a>
		<a data-icon="check" 		onclick="javascript:$('#answersBtn').click()" 	href="#" data-role="button" data-iconpos="notext">Answer</a>
		<a data-icon="gear"			onclick="javascript:$('#profileBtn').click()" 	href="#" data-role="button" data-iconpos="notext"  style='visibility:hidden;'>Profile</a>
		<a data-icon="arrow-r" 		onclick="javascript:$('#save').click()"         href="#" data-role="button" data-iconpos="notext">Save</a>
		<tags:navigation/>
	</span>
	<div style="clear:both"></div>
	
	<s:form beanclass="com.wordpong.app.action.game.ProfileEditActionBean" method="post">
		<ul data-role="listview" data-inset="true" data-theme="c" data-dividertheme="b" style="margin-top:0px;">
			<li data-role="list-divider" ><s:label for="profileEdit.title"/></li> 
			<li data-role="list-divider"> 
				<img src="${bean.user.pictureUrl}" class="ui-li-icon"/>
				<p style="padding-left:50px; padding-top:10px">${bean.user.fullName}</p> 
				<p style="padding-left:50px;">${bean.user.email}</p>
			</li> 
		</ul>
	    
		<tags:messages/> 
		<div data-role="fieldcontain" style="padding:4px;">
			<s:label for="email" class="ui-input-text"/>
			<s:text name="email" tabindex="1" />
		</div>
		<div data-role="fieldcontain" style="padding:4px;">
			<s:label for="firstName" class="ui-input-text"/>
			<s:text name="firstName" tabindex="2" />
		</div>	
		<div data-role="fieldcontain" style="padding:4px;">
			<s:label for="lastName" class="ui-input-text"/>
			<s:text name="lastName" tabindex="3" />
		</div>
		<div data-role="fieldcontain" style="padding:4px;">
			<s:label for="password" class="ui-input-text"/>
			<s:password name="password" repopulate="true" tabindex="4" />	
		</div>
		<div data-role="fieldcontain" style="padding:4px;">
			<s:label for="picture" class="ui-input-text"/>
			<a href="http://gravatar.com" rel="external">gravatar.com</a>
		</div>
	    <div data-role="fieldcontain" style="padding:4px;">
	           <s:label for="locale" class="ui-input-text"/>
	           <s:select name="locale" id="locale">
	               <%--<s:option value="">No Locale</s:option> --%>
	               <s:options-collection collection="${bean.supportedLocales}"
	                                     value="locale"
	                                     label="displayedName"
	                                     sort="label"/>
	           </s:select>  
	    </div>
		<div style="float:left">
			<input data-theme="a" class="process ui-btn-left " data-icon='arrow-l' name="back" value="${backLbl}" type="submit" /> 
		</div>
		<div style="float:right">
			<input data-theme="a" class="process" name="save" id="save" value="${saveLbl}" type="submit" />
		</div>
	</s:form>   
</div>
<%@ include file="/WEB-INF/jsp/common/footer.jsp" %>
<script>
wpFooterFile = "profileEdit"
</script>
