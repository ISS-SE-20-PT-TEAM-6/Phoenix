<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<!-- Error Message JSP -->
<fmt:setBundle basename="ApplicationResources" />
<c:if test="${not empty errors}">
<c:forEach var="error" items="${errors}" varStatus="status">
	<c:choose>
		<c:when test="${error.type == 0}">
       		<div class="success"><fmt:message key="${error.code}" /></div>
  	  	</c:when>
		<c:when test="${error.type == 1}">
       		<div class="info"><fmt:message key="${error.code}" /></div>
  	  	</c:when>
  	  	<c:when test="${error.type == 2}">
       		<div class="warning"><fmt:message key="${error.code}" /></div>
  	  	</c:when>
  	  	<c:when test="${error.type == 3}">
       		<div class="error"><fmt:message key="${error.code}" /></div>
  	  	</c:when>  	  	
	</c:choose>
</c:forEach>
</c:if>


