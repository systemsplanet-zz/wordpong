<%@ include file="/WEB-INF/jsp/common/taglibs.jsp" %>
<s:useActionBean id="bean" beanclass="com.wordpong.app.action.game.FriendListActionBean"/>
<fmt:message var="backLbl" key="back" />
<fmt:message var="addLbl" key="add" />
<fmt:message var="myFriendsLbl" key="friendList.myFriends" />
<div data-role="content" style="padding-top:0px;">
	<%-- NAVIGATION --%>
	<span class="wp-nav">
		<a data-icon="arrow-l" 		onclick="javascript:$('#homeBtn').click()" 		href="#" data-role="button" data-iconpos="notext" style='visibility:hidden;'>Back</a>
		<a data-icon="home" 		onclick="javascript:$('#homeBtn').click()" 		href="#" data-role="button" data-iconpos="notext">Home</a>
		<a data-icon="wp-friend" 	onclick="javascript:$('#friendsBtn').click()" 	href="#" data-role="button" data-iconpos="notext" class="ui-btn-active" >Friends</a>
		<a data-icon="wp-question" 	onclick="javascript:$('#questionsBtn').click()" href="#" data-role="button" data-iconpos="notext">Question</a>
		<a data-icon="check" 		onclick="javascript:$('#answersBtn').click()" 	href="#" data-role="button" data-iconpos="notext">Answer</a>
		<a data-icon="gear"			onclick="javascript:$('#profileBtn').click()" 	href="#" data-role="button" data-iconpos="notext">Profile</a>
		<a data-icon="plus" 		onclick="javascript:$('#friendInvite').click()" href="#" data-role="button" data-iconpos="notext">Add Friend</a>
		<tags:navigation/>
	</span>
	<div style="clear:both"></div>
	<s:form id="friendListForm" beanclass="com.wordpong.app.action.game.FriendListActionBean" method="post"> 
		<ul data-role="listview" data-inset="true" data-theme="c" data-dividertheme="b"  style="margin-top:0px;">
			<li data-role="list-divider" >${myFriendsLbl}</li> 
		</ul>
		<tags:messages/> 
        <c:forEach items="${actionBean.myFriends}" var="friend" varStatus="status" >
           <ul data-role="listview" data-inset="true" data-theme="c" data-dividertheme="b"  style="margin-top:0px;">
	       	  <li data-role="list-divider" id="item-${s.index}"  style="white-space:normal;" >
	       	      <a href="javascript:return false;">
		       	      <img src="${friend.pictureUrl}"  >
		       	      <h3><small>${friend.fullName}</small></h3> 
		       	      <p>${friend.email} <span class="ui-li-count">${friend.totalPoints}</span></p>
	       	      </a>
	       	      
	       	      <c:forEach items="${friend.games}" var="game" varStatus="s">	    	     	
	        	    <li id="item-${s.index}"  style="white-space:normal;">  
	        	        <small>${game.questionTitle}</small>
	      	            <span class="ui-li-count">${game.points}</span>
	        	    </li>	        	    
	        	  </c:forEach>
	       	  </li>	        	    
           </ul>
       </c:forEach>        
       <div style="float:left">
           <input data-theme="a" class="process ui-btn-left " data-icon='arrow-l' name="back" value="${backLbl}" type="submit" /> 
       </div>
	</s:form>	
</div>
<%@ include file="/WEB-INF/jsp/common/footer.jsp" %>
<script>
wpFooterFile = "friendList"
</script>

