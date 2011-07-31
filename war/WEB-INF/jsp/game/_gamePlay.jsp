<%@ include file="/WEB-INF/jsp/common/taglibs.jsp" %>
<s:useActionBean id="bean" beanclass="com.wordpong.app.action.game.GamePlayActionBean"/>
<fmt:message var="backLbl" key="back" />
<fmt:message var="skipLbl" key="skip" />
<fmt:message var="enterAnswersLbl" key="gamePlay.enterAnswers" />
<div data-role="content" style="padding-top:0px;">
	<%-- NAVIGATION --%>
	<span class="wp-nav">
		<a data-icon="arrow-l" 		onclick="javascript:$('#addGameBtn').click()" 	href="#" data-role="button" data-iconpos="notext" style='visibility:hidden;'>Back</a>
		<a data-icon="home" 		onclick="javascript:$('#homeBtn').click()" 		href="#" data-role="button" data-iconpos="notext">Home</a>
		<a data-icon="wp-friend" 	onclick="javascript:$('#friendsBtn').click()" 	href="#" data-role="button" data-iconpos="notext">Friends</a>
		<a data-icon="wp-question" 	onclick="javascript:$('#questionsBtn').click()" href="#" data-role="button" data-iconpos="notext">Question</a>
		<a data-icon="check" 		onclick="javascript:$('#answersBtn').click()" 	href="#" data-role="button" data-iconpos="notext">Answer</a>
		<a data-icon="gear"			onclick="javascript:$('#profileBtn').click()" 	href="#" data-role="button" data-iconpos="notext">Profile</a>
		<a data-icon="plus" 		onclick="javascript:$('#').click()" href="#" data-role="button" data-iconpos="notext" style='visibility:hidden;'></a>
		<tags:navigation/>
	</span>
	<div style="clear:both"></div>

	<s:form id="gamePlayForm" beanclass="com.wordpong.app.action.game.GamePlayActionBean" method="post">
	    <ul data-role="listview" data-inset="true" data-theme="c" data-dividertheme="b"  style="margin-top:0px;">
	        <li data-role="list-divider" >${actionBean.game.inviterDetails}</li> 
	        <li data-role="list-divider" >${actionBean.game.questionTitle}</li> 
            <li data-role="list-divider" ><h3 id="question" style="white-space:normal">${actionBean.question.questions[0]}</h3> 
        </ul>
        <tags:messages/> 
        <ul  data-role="listview" data-inset="true" data-theme="c" data-dividertheme="b"  style="margin-top:0px;">
			<c:forEach items="${actionBean.answer.answers}" var="i"  varStatus="s">	 	       
			  <li id="item-${s.index}" OnClick="javascript:match(${s.index});" style="white-space:normal;" >${i}</li>
			</c:forEach>
	    </ul>
		<input id="gameKeyStringEncrypted" name="gameKeyStringEncrypted" type="hidden" value="${actionBean.gameKeyStringEncrypted} }"/>    
        <div style="float:right">
           <input value="${skipLbl}" OnClick="javascript:skip();return false;" type="submit" data-theme="a" /> 
        </div>
        <div style='visibility:hidden'>
    	<input name="success" id="success" name="success" data-theme="a" class="process" value="SUCCESS" type="submit" />
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
var qr=new Array(); // random question indexes
<c:forEach items="${actionBean.question.questions}" var="i"  varStatus="s">	 	       
	q[${s.index}]="${i}";			              
	qr[${s.index}]=${s.index};
</c:forEach>

qr.shuffle(); //randomize it
skip();

function skip(){
   i=(i+1) % qr.length;
   $("#question").text(q[qr[i]]);
}

function match(a){
   	if (qr[i]!=a) {
   		// no match
   		$('#item-'+a).fadeOut('fast').fadeIn('fast')
   	} else {
   		//strike through the answer
   		  $('#item-'+qr[i]).fadeOut('fast').fadeIn('fast', function() {
             $('#item-'+qr[i]).css('text-decoration','line-through').css('color','green');
	   		//remove the question from qr
	   		qr.splice(i, 1);
	   		if (qr.length==0) {
	    		 $('#success').click();
	   		} else {
	   	  		 skip();
	   		}
          });
   	}
}
</script>
