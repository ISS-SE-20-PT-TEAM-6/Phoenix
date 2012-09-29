<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<html>
<head>
<fmt:setBundle basename="ApplicationResources" />
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title><fmt:message key="title.userlistpage" /></title>
</head>
<body>
	<h2>
		<fmt:message key="title.userlistpage" />
	</h2>
    <c:url var="url" scope="page" value="/controller/inputuser">
            <c:param name="insert" value="true"/>
            <c:param name="action" value="create"/>
    </c:url>
    <a href="${url}"><fmt:message key="label.user.create"/></a>
    <br/><br/>	
	<c:if test="${! empty  users}">
		<table class="borderAll">
			<tr>
				<th><fmt:message key="label.user.id" /></th>
				<th><fmt:message key="label.user.name" /></th>
			</tr>
			<c:forEach var="user" items="${users}" varStatus="status">
				<tr class="${status.index%2==0?'even':'odd'}">
					<td class="nowrap">${user.id}</td>
					<td class="nowrap">${user.name}</td>
					
                    <td class="nowrap">
                        <c:url var="updurl" scope="page" value="/controller/inputuser">
                            <c:param name="userid" value="${user.id}"/>
                            <c:param name="insert" value="false"/>
                            <c:param name="action" value="modify"/>
                        </c:url>
                        <a href="${updurl}"><fmt:message key="label.user.edit"/></a>
                        &nbsp;&nbsp;&nbsp;
                        <c:url var="delurl" scope="page" value="/controller/inputuser">
                            <c:param name="userid" value="${user.id}"/>
                            <c:param name="insert" value="false"/>
                            <c:param name="action" value="delete"/>
                        </c:url>
                        <a href="${delurl}"><fmt:message key="label.user.delete"/></a>
                    </td>					
				</tr>
			</c:forEach>
		</table>
	</c:if>

</body>
</html>