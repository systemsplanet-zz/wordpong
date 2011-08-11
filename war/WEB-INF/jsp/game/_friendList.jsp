<%@ include file="/WEB-INF/jsp/common/taglibs.jsp" %>
<s:useActionBean id="bean" beanclass="com.wordpong.app.action.game.FriendListActionBean"/>
<fmt:message var="backLbl" key="back" />
<fmt:message var="addLbl" key="add" />
<fmt:message var="myFriendsLbl" key="friendList.myFriends" />
<fmt:message var="playLbl" key="friendList.play" />
<fmt:message var="historyLbl" key="friendList.history" />
<div data-role="content" style="padding-top:0px;">
	<%-- NAVIGATION --%>
	<span class="wp-nav">
		<a data-icon="arrow-l" 		 onclick="javascript:$('#homeBtn').click()" 		href="#" data-role="button" data-iconpos="notext" style='visibility:hidden;'>Back</a>
		<a data-icon="home" 		 onclick="javascript:$('#homeBtn').click()" 		href="#" data-role="button" data-iconpos="notext">Home</a>
		<a data-icon="wp-friend" 	 onclick="javascript:$('#friendsBtn').click()" 	href="#" data-role="button" data-iconpos="notext" class="ui-btn-active" >Friends</a>
		<a data-icon="wp-question" 	 onclick="javascript:$('#questionsBtn').click()" href="#" data-role="button" data-iconpos="notext">Question</a>
		<a data-icon="check" 		 onclick="javascript:$('#answersBtn').click()" 	href="#" data-role="button" data-iconpos="notext">Answer</a>
		<a data-icon="gear"			 onclick="javascript:$('#profileBtn').click()" 	href="#" data-role="button" data-iconpos="notext" style='visibility:hidden;'>Profile</a>
		<a data-icon="wp-friend-add" onclick="javascript:$('#friendInvite').click()" href="#" data-role="button" data-iconpos="notext">Add Friend</a>
		<tags:navigation/>
	</span>
	<div style="clear:both"></div>
	<s:form id="friendListForm" beanclass="com.wordpong.app.action.game.FriendListActionBean" method="post"> 
		<ul data-role="listview" data-inset="true" data-theme="c" data-dividertheme="b"  style="margin-top:0px;">
			<li data-role="list-divider" >${myFriendsLbl}</li> 
		</ul>
		<tags:messages/> 
        <c:forEach items="${actionBean.myFriends}" var="friend" varStatus="status" >
           <ul data-role="listview" data-inset="true" data-theme="c" data-dividertheme="c"  style="margin-top:0px;">
	       	  <li data-role="list-divider" id="item-${s.index}"  style="white-space:normal;" >
	       	      <span >
		       	      <img src="${friend.pictureUrl}" style="float:left;"  width="60" height="60" />
		       	      <div style="float:left;padding-left:8px">
			       	      <h3 style="white-space:normal;">${friend.fullName}</h3> 
			       	      <p style="white-space:normal;">${friend.email} 
		       	      </div>
					  <div style="float:right">
				 		  	<p class="wp-points">${friend.points}</p></p>		       	      
		       	      </div>
					  <div style="clear:both"></div>
		       	      <small>
			       	      <div style="float:right;">
					 		  	<input data-icon="star" type="button"  onclick="javascript:$('#friendKeyStringEncrypted').val('${friend.keyStringEncrypted}');$('#friendDetails').val('${friend.details}');$('#friendPictureUrl').val('${friend.pictureUrl}');$('#selectFriend').click();return false;" data-role="button" value="${playLbl}" data-iconpos="right"/>
			       	      </div>
			       	      <div style="float:right">
					 		  	<input data-icon="arrow-r" type="button" onclick="javascript:$('#friendHistoryKeyStringEncrypted').val('${friend.keyStringEncrypted}');$('#selectHistory').click();return false;" data-role="button" value="${historyLbl}" data-iconpos="right" />		       	      
			       	      </div>
		       	      </small>
					  <div style="clear:both"></div>
	       	      </span>
	       	  </li>	        	    
           </ul>
       </c:forEach>        
       <div style="float:left">
           <input data-theme="a" class="process ui-btn-left " data-icon='arrow-l' name="back" value="${backLbl}" type="submit" /> 
       </div>       
	</s:form>
	
<!-- hidden form for starting a new game -->	
	<s:form id="newGameFriendForm" beanclass="com.wordpong.app.action.game.NewGameActionBean" method="post">
		<div style='visibility:hidden'>
			<input class="process" id="selectFriend" name="selectFriend" value="select friend" type="submit"  />
	        <s:hidden id="friendKeyStringEncrypted" name="friendKeyStringEncrypted" value="?friendKeyStringEncrypted?" />	
	        <s:hidden id="friendDetails" 			name="friendDetails" 			value="?friendDetails?" />	
	        <s:hidden id="friendPictureUrl" 		name="friendPictureUrl" 		value="?friendPictureUrl?" />	
        </div>
	</s:form>	
<!-- hidden form for viewing game answers -->	
	<s:form id="historyForm" beanclass="com.wordpong.app.action.game.FriendHistoryActionBean" method="post">
		<div style='visibility:hidden'>
			<input class="process" id="selectHistory" name="selectHistory" value="select history" type="submit"  />
	        <s:hidden id="friendHistoryKeyStringEncrypted" name="friendHistoryKeyStringEncrypted" value="?friendHistoryKeyStringEncrypted?" />	
        </div>
	</s:form>	
</div>
<%@ include file="/WEB-INF/jsp/common/footer.jsp" %>
<script>
wpFooterFile = "friendList"
</script>

