<%@ include file="/WEB-INF/jsp/common/taglibs.jsp" %>
<s:useActionBean id="bean" beanclass="com.wordpong.app.action.game.TheirTurnGameCancelActionBean"/>
<fmt:message var="resendLbl" key="resend" />
<fmt:message var="cancelInviteLbl" key="theirTurnGameCancel.cancelInvite" />
<fmt:message var="backLbl" key="back" />
<fmt:message var="shareLbl" key="share" />
<fmt:message var="withLbl" key="with" />

<s:form id="theirTurnGameCancelForm" beanclass="com.wordpong.app.action.game.TheirTurnGameCancelActionBean" method="post">
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
    </div>
    <div style="clear:both"></div>
    <div data-role="content" style="padding-top:0px;">
		<ul data-role="listview" data-inset="true" data-theme="c" data-dividertheme="b" style="margin-top:0px;">
			<li data-role="list-divider" ><s:label for="theirTurnGameCancel.title"/></li> 
		</ul>
		<tags:messages/> 
			<ul data-role="listview" data-inset="true" data-theme="c" data-dividertheme="b">
				<li>
                <%-- TODO: display details about game being canceled --%>
                    <label>${shareLbl}: ${actionBean.game.questionTitle}</label>
				</li>
				<li>	                    
                    <label>${withLbl}: ${actionBean.game.inviteeDetails}</label>
				</li>
			</ul>
	        <div style="float:right">
	            <input data-theme="a" class="process" id="cancelGame" name="cancelGame" value="${cancelInviteLbl}" type="submit" />
	        </div>  
    </div>
    <s:hidden id="gameKeyStringEncrypted" name="gameKeyStringEncrypted" value="${actionBean.gameKeyStringEncrypted}"/>    
    
</s:form>   
<%@ include file="/WEB-INF/jsp/common/footer.jsp" %>
<script>
wpFooterFile = "theirTurnGameCancel"
</script>
