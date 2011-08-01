<%@ include file="/WEB-INF/jsp/common/taglibs.jsp" %>
<s:useActionBean id="bean" beanclass="com.wordpong.app.action.game.NewGameActionBean"/>
<fmt:message var="backLbl" key="back" />
<fmt:message var="addLbl" key="add" />
<fmt:message var="selectFriendLbl" key="newGame.selectFriend" />
<div data-role="content" style="padding-top:0px;">
<%-- NAVIGATION --%>
<div class="wp-nav">
<a data-icon="arrow-l" 		onclick="javascript:$('#homeBtn').click()" 		href="#" data-role="button" data-iconpos="notext" style='visibility:hidden;'>Back</a>
<a data-icon="home" 	   	onclick="javascript:$('#homeBtn').click()" 		href="#" data-role="button" data-iconpos="notext">Home</a>
<a data-icon="wp-friend" 	onclick="javascript:$('#friendsBtn').click()" 	href="#" data-role="button" data-iconpos="notext">Friends</a>
<a data-icon="wp-question" 	onclick="javascript:$('#questionsBtn').click()" href="#" data-role="button" data-iconpos="notext">Question</a>
<a data-icon="check" 		onclick="javascript:$('#answersBtn').click()" 	href="#" data-role="button" data-iconpos="notext">Answer</a>
<a data-icon="gear"			onclick="javascript:$('#profileBtn').click()" 	href="#" data-role="button" data-iconpos="notext">Profile</a>
<a data-icon="plus" 		onclick="javascript:$('#friendInvite').click()" href="#" data-role="button" data-iconpos="notext">Invite Friend</a>
<tags:navigation/>
</div>
<div style="clear:both"></div>
	<s:form id="newGameFriendForm" beanclass="com.wordpong.app.action.game.NewGameActionBean" method="post">
    <ul data-role="listview" data-inset="true" data-theme="c" data-dividertheme="b"  style="margin-top:0px;">
        <li data-role="list-divider" >${selectFriendLbl}</li> 
    </ul>
	<tags:messages/> 
        <c:forEach items="${actionBean.myFriends}" var="friend" varStatus="status" >
            <ul data-role="listview" data-inset="true" data-theme="c" data-dividertheme="b"  style="margin-top:0px;">			
        	  <li data-role="list-divider" id="item-${s.index}"  style="white-space:normal;" >
             	  <a href="#" OnClick="javascript:$('#friendKeyStringEncrypted').val('${friend.keyStringEncrypted}');$('#friendDetails').val('${friend.details}');$('#friendPictureUrl').val('${friend.pictureUrl}');$('#selectFriend').click();return false;"> 
	       	         <img src="${friend.pictureUrl}"  />
	       	         <h3><small>${friend.fullName}</small></h3> 
	       	         <p><small>${friend.email}</small></p>
	       	      </a>
        	  </li>	        	    
            </ul>
        </c:forEach>        
        <div style="float:left">
            <input data-theme="a" class="process ui-btn-left " data-icon='arrow-l' name="back" value="${backLbl}" type="submit" /> 
        </div>
        <div style='visibility:hidden'>
           <input class="process" id="selectFriend" name="selectFriend" value="select friend" type="submit"  />
        </div>
        <s:hidden id="friendKeyStringEncrypted" name="friendKeyStringEncrypted" value="?friendKeyStringEncrypted?" />	
        <s:hidden id="friendDetails" 			name="friendDetails" 			value="?friendDetails?" />	
        <s:hidden id="friendPictureUrl" 		name="friendPictureUrl" 		value="?friendPictureUrl?" />	
	</s:form>	
</div>
<%@ include file="/WEB-INF/jsp/common/footer.jsp" %>
<script>
wpFooterFile = "newGame"
</script>

