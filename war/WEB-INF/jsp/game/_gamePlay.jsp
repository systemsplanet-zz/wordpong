<%@ include file="/WEB-INF/jsp/common/taglibs.jsp" %>
<s:useActionBean id="bean" beanclass="com.wordpong.app.action.game.GamePlayActionBean"/>
<fmt:message var="backLbl" key="back" />
<fmt:message var="enterAnswersLbl" key="gamePlay.enterAnswers" />

<div data-role="header"  data-nobackbtn="true" data-theme="b">
<form id="gamePlayFormHead" action="/game/AnswerEdit.wp" method="post">
    <div>
        <!-- Back Button -->
        <span style="float:left;margin-left:10px">
            <div data-theme="a" class="ui-btn ui-btn-up-a ui-btn-icon-left ui-btn-corner-all ui-shadow">
	            <span class="ui-btn-inner ui-btn-corner-all">
	               <span class="ui-btn-text">${backLbl}</span>
    	           <span class="ui-icon ui-icon-arrow-l ui-icon-shadow"></span>
	            </span>
                <input name="back" value="${backLbl}" data-theme="a" class="process ui-btn-left  ui-btn-hidden" data-icon="arrow-l" type="submit">
            </div> 
        </span>
        
    </div>
</form>
<div style="clear:both"></div>

<div data-role="content" style="padding-top:0px;">
	<s:form id="gamePlayForm" beanclass="com.wordpong.app.action.game.GamePlayActionBean" method="post">
	    <ul data-role="listview" data-inset="true" data-theme="c" data-dividertheme="b"  style="margin-top:0px;">
	        <li data-role="list-divider" >Guess Lbl: ${actionBean.game.questionDescription}</li> 
        </ul>
        <tags:messages/> 
        <ul  data-inset="true" data-theme="c" data-dividertheme="b">
	        <small>            
<%--
 	 	       <c:forEach items="${actionBean.questions}" var="i"  varStatus="s">	 	       
		          <div data-role="fieldcontain" style="padding:4px;">
		              <s:label for="answers[${s.index}]" class="ui-input-text">${i}</s:label>		              
		              <sdyn:text name="answers[${s.index}]" id="answers[${s.index}]" maxlength="100"  class="ui-input-text ui-body-null ui-corner-all ui-shadow-inset ui-body-a"/>
		          </div>
		       </c:forEach>
--%>		       
		       <input id="gameKeyStringEncrypted" name="gameKeyStringEncrypted" type="hidden" value=""/>    
	        </small>         
	    </ul>
        <div style="float:right">
           <input name="back" value="Skip Lbl" type="submit" data-theme="a" class="process"/> 
        </div>
        <div style="float:right">
           <input  name="save" value="Match Lbl" type="submit" data-theme="a" class="process" /> 
        </div>
	</s:form>	 
</div>
<%@ include file="/WEB-INF/jsp/common/footer.jsp" %>
<script>
wpFooterFile = "gamePlay"
</script>

