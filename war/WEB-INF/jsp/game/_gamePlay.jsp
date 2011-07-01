<%@ include file="/WEB-INF/jsp/common/taglibs.jsp" %>
<s:useActionBean id="bean" beanclass="com.wordpong.app.action.game.GamePlayActionBean"/>
<fmt:message var="backLbl" key="back" />
<fmt:message var="skipLbl" key="skip" />
<fmt:message var="enterAnswersLbl" key="gamePlay.enterAnswers" />

<div data-role="header"  data-nobackbtn="true" data-theme="b">
<form id="gamePlayFormHead" action="/game/GamePlay.wp" method="post">
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
	        <li data-role="list-divider" >${actionBean.game.inviterDetails}</li> 
	        <li data-role="list-divider" >${actionBean.game.questionDescription}</li> 
            <li data-role="list-divider" ><h3 id="question" style="white-space:normal">${actionBean.question.questions[0]}</h3> 
        </ul>
        <tags:messages/> 
        <ul  data-role="listview" data-inset="true" data-theme="c" data-dividertheme="b"  style="margin-top:0px;">
			<c:forEach items="${actionBean.answer.answers}" var="i"  varStatus="s">	 	       
			  <li id="item-${s.index}" OnClick="javascript:match(${s.index});" style="white-space:normal" >${i}</li>
			</c:forEach>
		    <input id="gameKeyStringEncrypted" name="gameKeyStringEncrypted" type="hidden" value=""/>    
	    </ul>
        <div style="float:right">
           <input value="${skipLbl}" 
              	OnClick="javascript:skip();return false;" type="submit" data-theme="a" /> 
        </div>
        <div style='visibility:hidden'>
    	<input name="success" id="success" action="success" data-theme="a" class="process" value="SUCCESS" type="submit" />
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
		// alert("Sorry, try again.");
   		$('#item-'+a).slideToggle();
   		$('#item-'+a).slideToggle();   		
   	} else {
   		//alert('correct'); 
   		//strike through the answer
   		$('#item-'+qr[i]).css('text-decoration','line-through');
   		//remove the question from qr
   		qr.splice(i, 1);
   		if (qr.length==0) {
    		 $('#success').click();
   		} else {
   	  		 skip();
   		}
   	}
}
</script>
