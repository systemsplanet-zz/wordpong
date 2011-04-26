<%@ include file="/WEB-INF/jsp/common/taglibs.jsp" %>
<%@ page import="java.util.logging.Logger" %>
<% 
Logger log = Logger.getLogger("com.wordpong.app.jsp.admin.index.jsp");
log.info("Admin run by user:"+ session.getAttribute("com.wordpong.session.user")); 	
%>

<div data-role="header"  data-nobackbtn="true" data-theme="b">
	<a href="${actionBean.logoutUrl}" rel="external"  class="process">Logout</a>
	<tags:logo/>
</div>

<div data-role="content">
	<p>
	${actionBean.message}<br/>
	isLoggedIn=${actionBean.isLoggedIn}<br/>
	isUserAdmin=${actionBean.isUserAdmin}<br/>
	user=${actionBean.user}
	</p>
	
<%--	<s:url beanclass="com.wordpong.app.action.admin.AdminActionBean" event="clearCache" var="clearCacheUrl"/>  --%>
		<%-- <s:param name="param1" value="${actionBean.message}"/> --%>
<%-- 	<a href="${clearCacheUrl}" rel="external"  class="process">Clear Cache</a><br /> --%>
	 <s:form  beanclass="com.wordpong.app.action.admin.AdminActionBean" method="post">
			<input name="clearCache" value="Clear Cache" type="submit" class="process" style="height:20px"/>
 	 </s:form>
		
	
		<a href="/ktrwjr/index.html" rel="external" target="_blank">Junit Tests</a><br />
		
		
		
		<a href="/admin/debugenv" rel="external">Dump Env</a><br />
		<a href="/testq" rel="external">Test EnQueue (/testq)</a><br/>
		<a href="/stats" rel="external" target="_blank">App Stats (/stats)</a><br/>
		<a href="https://appengine.google.com/dashboard?&app_id=wordpong" rel="external" target="_blank">App Engine Console (https://appengine.google.com/dashboard?&app_id=wordpong)</a><br/>
<p />
<%--		
        <s:url beanclass="com.wordpong.app.action.admin.AdminActionBean" event="logout" var="logoutUrl"/>
	<a href="${logoutUrl}" rel="external">Logout WordPong</a>
--%>	




    <s:form  beanclass="com.wordpong.app.action.admin.AdminActionBean" method="post">
        <input name="seedQuestions" value="Seed Questions" type="submit" class="process" style="height:20px"/>
    </s:form>
    <s:form  beanclass="com.wordpong.app.action.admin.AdminActionBean" method="post">
        <input name="logout" value="Logout WordPong" type="submit" class="process" style="height:20px"/>
    </s:form>
	
    <br>
    <a href="${actionBean.logoutUrl}" rel="external">Logout Google</a></br>
    <br>            
</div>
