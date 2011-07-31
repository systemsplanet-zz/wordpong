<%@ include file="/WEB-INF/jsp/common/taglibs.jsp" %>
<s:useActionBean id="bean" beanclass="com.wordpong.app.action.game.GamePlaySuccessActionBean"/>
<fmt:message var="continueLbl" key="continue" />

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
	<s:form id="gamePlaySuccessForm" beanclass="com.wordpong.app.action.game.GamePlaySuccessActionBean" method="post">
        <tags:messages/> 
	    <ul data-role="listview" data-inset="true" data-theme="c" data-dividertheme="b"  style="margin-top:0px;">
            <li data-role="list-divider" >
            	<h3 style="white-space:normal">Congratulations! All Questions Answered Correctly.</h3> 
            </li>
        </ul>
        <center>
	       	<img src="/i/a/adsense-adchoices-expanded.png">
       	</center>
	    <input id="gameKeyStringEncrypted" name="gameKeyStringEncrypted" type="hidden" value=""/>    
        <div style="float:right">
           <input value="${continueLbl}" name="done" id="done" type="submit" data-theme="a"  class="process" /> 
        </div>
	</s:form>	 
</div>
<pre>






</pre>
<%@ include file="/WEB-INF/jsp/common/footer.jsp" %>
<script>
wpFooterFile = "gamePlaySuccess"
</script>
