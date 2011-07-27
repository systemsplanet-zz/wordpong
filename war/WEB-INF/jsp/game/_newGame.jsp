<%@ include file="/WEB-INF/jsp/common/taglibs.jsp" %>
<s:useActionBean id="bean" beanclass="com.wordpong.app.action.game.NewGameActionBean"/>
<fmt:message var="backLbl" key="back" />
<fmt:message var="addLbl" key="add" />
<fmt:message var="selectFriendLbl" key="newGame.selectFriend" />

<div data-role="header"  data-nobackbtn="true" data-theme="b">
<s:form beanclass="com.wordpong.app.action.game.NewGameActionBean" method="post">
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
        
        <!-- Add Button -->
        <span style="float:right;margin-right:10px">
            <div data-theme="a" class="ui-btn ui-btn-up-a ui-btn-icon-right ui-btn-corner-all ui-shadow">
                <span class="ui-btn-inner ui-btn-corner-all">
                    <span class="ui-btn-text">${addLbl}</span>
                    <span class="ui-icon ui-icon-plus ui-icon-shadow"></span>                    
                </span>
                <input name="friendInvite" value="${addLbl}" data-theme="a" class="process ui-btn-left ui-btn-hidden" type="submit">
            </div> 
        </span>
    </div>
</s:form>
<div style="clear:both"></div>

<div data-role="content" style="padding-top:0px;">
	<tags:messages/> 
	<s:form id="newGameFriendForm" beanclass="com.wordpong.app.action.game.NewGameActionBean" method="post">
    <ul data-role="listview" data-inset="true" data-theme="c" data-dividertheme="b"  style="margin-top:0px;">
        <li data-role="list-divider" >${selectFriendLbl}</li> 
    </ul>
        <c:forEach items="${actionBean.myFriends}" var="friend" varStatus="status" >
            <ul data-role="listview" data-inset="true" data-theme="c" data-dividertheme="b"  style="margin-top:0px;">
        	  <li data-role="list-divider" id="item-${s.index}"  style="white-space:normal;" >
             	  <a href="#" OnClick="javascript:$('#friendKeyStringEncrypted').val('${friend.keyStringEncrypted}');$('#friendDetails').val('${friend.details}');$('#selectFriend').click();return false;"> 
	       	         <img src="${friend.pictureUrl}"  >
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
        <s:hidden name="friendKeyStringEncrypted" id="friendKeyStringEncrypted" value="?friendKeyStringEncrypted?" />	
        <s:hidden name="friendDetails" id="friendDetails" value="?friendDetails?" />	
	</s:form>	
</div>
<%@ include file="/WEB-INF/jsp/common/footer.jsp" %>
<script>
wpFooterFile = "newGame"
</script>

