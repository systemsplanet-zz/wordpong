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
            <li data-role="list-divider" ><h3  id="question" >${actionBean.question.questions[0]}</h3> 
        </ul>
        <tags:messages/> 
        <ul  data-inset="true" data-theme="c" data-dividertheme="b">
	        <small>         
				<div data-role="fieldcontain"  style="padding:0;"> 
				    <fieldset data-role="controlgroup" style="margin-bottom:0px;"> 
						<c:forEach items="${actionBean.answer.answers}" var="i"  varStatus="s">	 	       
							<input type="radio" name="answer-1" id="answer-${s.index}" value="answer-${s.index}"  /> 
							<label for="answer-${s.index}">${i}</label>		              
						</c:forEach>
				    </fieldset> 
				</div> 		       
		       <input id="gameKeyStringEncrypted" name="gameKeyStringEncrypted" type="hidden" value=""/>    
	        </small>         
	    </ul>
        <div style="float:right">
           <input name="back" value="Skip Lbl" 
              	OnClick="javascript:skip();return false;" type="submit" data-theme="a" /> 
        </div>
        <div style="float:right">
           <input  name="save" value="Match Lbl" 
           		OnClick="javascript:match();return false;" type="submit" data-theme="a" /> 
        </div>
	</s:form>	 
</div>
<%@ include file="/WEB-INF/jsp/common/footer.jsp" %>
<script>
wpFooterFile = "gamePlay"
</script>
<script> 
var i=0;
var q=new Array();
<c:forEach items="${actionBean.question.questions}" var="i"  varStatus="s">	 	       
	q[${s.index}]="${i}";			              
</c:forEach>

function skip(){
   i=(i+1) % q.length;
   $("#question").text(q[i]);
}
function match(){
   alert("Sorry, try again.");
}
 
</script>
