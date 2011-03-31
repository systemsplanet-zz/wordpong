<%@page contentType="text/html;charset=ISO-8859-1" language="java"
%><%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" 
%><%@taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" 
%><%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"
%><%@taglib prefix="s" uri="http://stripes.sourceforge.net/stripes.tld"
%><%@taglib prefix="s-dyn"  uri="http://stripes.sourceforge.net/stripes-dynattr.tld"
%><%@taglib prefix="sec" uri="http://www.stripes-stuff.org/security.tld"
%><%@taglib uri="/WEB-INF/tags/tld/appversion.tld"  prefix="wp" 
%><%@taglib tagdir="/WEB-INF/tags" prefix="tags" 
%><c:set var="contextPath" value="${pageContext.request.contextPath}"/><%
    response.addHeader("Pragma", "no-cache");
    response.addHeader("Cache-control", "no-cache");
    response.addHeader("Expires", "-1");
%>
<%-- <%@ page isELIgnored="false" %>  --%>

