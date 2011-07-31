<%@ taglib prefix="s" uri="http://stripes.sourceforge.net/stripes.tld"
%><%@taglib prefix="sdyn"  uri="http://stripes.sourceforge.net/stripes-dynattr.tld"%>
<span class="wp-invisible">
	<s:form  id="addGameForm" name="addGameForm" beanclass="com.wordpong.app.action.game.GameActionBean" method="post">		        	    
		<sdyn:button name="friendsBtn"   	id="friendsBtn"   		class="process" />
		<sdyn:button name="questionsBtn" 	id="questionsBtn" 		class="process" />
		<sdyn:button name="answersBtn"   	id="answersBtn"   		class="process" />
		<sdyn:button name="profileBtn"   	id="profileBtn"   		class="process" />
		<sdyn:button name="addGameBtn" 	 	id="addGameBtn"   		class="process" />		
	</s:form>
	<s:form  id="loginForm" beanclass="com.wordpong.app.action.LoginActionBean" method="post">
		<sdyn:button name="homeBtn"      	id="homeBtn"      		class="process" />
	</s:form>
	<s:form  id="friendForm" beanclass="com.wordpong.app.action.game.FriendListActionBean" method="post">
		<sdyn:button name="friendInvite"	id="friendInvite"	class="process"  />
	</s:form>
	<s:form  id="questionForm" beanclass="com.wordpong.app.action.game.QuestionListActionBean" method="post">
		<sdyn:button name="addQuestion"		id="addQuestion"	class="process"  />
	</s:form>
	<s:form  id="answerForm" beanclass="com.wordpong.app.action.game.AnswerListActionBean" method="post">
		<sdyn:button name="addAnswer"		id="addAnswer"	class="process"  />
	</s:form>
</span>
    

