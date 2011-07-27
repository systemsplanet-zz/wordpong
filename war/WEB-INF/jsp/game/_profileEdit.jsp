<%@ include file="/WEB-INF/jsp/common/taglibs.jsp" %>
<fmt:message var="saveLbl" key="save" />
<fmt:message var="backLbl" key="back" />
<fmt:message var="emailLbl" key="email" />
<s:useActionBean id="bean" beanclass="com.wordpong.app.action.game.ProfileEditActionBean"/>

<s:form beanclass="com.wordpong.app.action.game.ProfileEditActionBean" method="post">

<div data-role="header"  data-nobackbtn="true" data-theme="b">
    <!-- Back Button -->
    <div style="float:left;margin-left:10px">
        <div data-theme="a" class="ui-btn ui-btn-up-a ui-btn-icon-left ui-btn-corner-all ui-shadow">
            <span class="ui-btn-inner ui-btn-corner-all">
               <span class="ui-btn-text">${backLbl}</span>
               <span class="ui-icon ui-icon-arrow-l ui-icon-shadow"></span>
            </span>
            <input name="back" value="${backLbl}" data-theme="a" class="process ui-btn-left  ui-btn-hidden" data-icon="arrow-l" type="submit">
        </div> 
    </div>
    <!-- Save Button -->
    <span style="float:right;margin-right:10px">
        <span data-theme="a" class="ui-btn ui-btn-up-a ui-btn-icon-right ui-btn-corner-all ui-shadow">
            <span class="ui-btn-inner ui-btn-corner-all">
                <span class="ui-btn-text">${saveLbl}</span>
            </span>
            <input name="save"  value="${saveLbl}" data-theme="a" class="process ui-btn-left  ui-btn-hidden" type="submit">
        </span> 
    </span>
</div>

<div style="clear:both"></div>

<div data-role="content" style="padding-top:0px;">
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
</div>
</s:form>   
<%@ include file="/WEB-INF/jsp/common/footer.jsp" %>
<script>
wpFooterFile = "profileEdit"
</script>
