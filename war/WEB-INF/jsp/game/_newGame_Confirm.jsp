<%@ include file="/WEB-INF/jsp/common/taglibs.jsp" %>
<s:useActionBean id="bean" beanclass="com.wordpong.app.action.game.NewGameActionBean"/>
<fmt:message var="acceptLbl" key="accept" />
<fmt:message var="cancelLbl" key="cancel" />
<fmt:message var="shareLbl" key="share" />
<fmt:message var="withLbl" key="with" />

<div data-role="header"  data-nobackbtn="true" data-theme="b">
<s:form  beanclass="com.wordpong.app.action.game.NewGameActionBean" method="post">
    <div>
	    <!-- Back Button -->
	    <div style="float:left;margin-left:10px">
	        <div data-theme="a" class="ui-btn ui-btn-up-a ui-btn-icon-left ui-btn-corner-all ui-shadow">
	            <span class="ui-btn-inner ui-btn-corner-all">
	               <span class="ui-btn-text">${cancelLbl}</span>
	               <span class="ui-icon ui-icon-arrow-l ui-icon-shadow"></span>
	            </span>
	            <input name="back" value="${cancelLbl}" data-theme="a" class="process ui-btn-left  ui-btn-hidden" data-icon="arrow-l" type="submit">
	        </div> 
	    </div>
    </div>
</s:form>   
</div>
<div style="clear:both"></div>

<div data-role="content" style="padding-top:0px;">
	<tags:messages/> 
	<s:form  beanclass="com.wordpong.app.action.game.NewGameActionBean" method="post">
		<ul data-role="listview" data-inset="true" data-theme="c" data-dividertheme="b" style="margin-top:0px;">
			<li data-role="list-divider" ><s:label for="confirm game"/></li> 
			<li>
			    <label>${shareLbl}: ${actionBean.questionTitle}</label>
			</li>			
			<li>			
				<label>${withLbl}: ${actionBean.friendDetails}</label>
			</li>
		</ul>
        <div style="float:right">
			<input name="startGame" value="Start Game" data-theme="a" class="process ui-btn-left  ui-btn-hidden"  type="submit">
        </div>
        <div style="float:left">
            <input data-theme="a" class="process ui-btn-left " data-icon='arrow-l' name="back" value="${cancelLbl}" type="submit" /> 
        </div>
        <s:hidden id="answerKeyStringEncrypted" name="answerKeyStringEncrypted" value="${actionBean.answerKeyStringEncrypted}"/>
        <s:hidden id="questionTitle" name="questionTitle" value="${actionBean.questionTitle}"/>
        <s:hidden id="friendKeyStringEncrypted" name="friendKeyStringEncrypted" value="${actionBean.friendKeyStringEncrypted}" />	
        <s:hidden id="friendDetails" name="friendDetails" value="${actionBean.friendDetails}" />	        
        <s:hidden id="friendPictureUrl" name="friendPictureUrl" value="${actionBean.friendPictureUrl}" />	        
    </s:form>   
</div>
<%@ include file="/WEB-INF/jsp/common/footer.jsp" %>
<script>
wpFooterFile = "newGame_Confirm"
</script>


