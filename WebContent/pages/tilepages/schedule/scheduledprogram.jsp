<%@ page language="java" contentType="text/html; charset=US-ASCII"
    pageEncoding="US-ASCII"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=US-ASCII">

<fmt:setBundle basename="ApplicationResources" />
<title><fmt:message key="title.scheduledprogram" /></title>
</head>
<body>
<div>
<h1>Search Scheduled Program</h1>
	<form action="${pageContext.request.contextPath}/controller/searchschedule" method=post class="form-horizontal">
	
	  <div class="control-group">
	  	<label class="control-label" for="programName"><fmt:message key="label.program.name"/></label>
	    <div class="controls">
	      <input type="text" name="programName" placeholder="Program Name">
	    </div>
	  </div>
	  <div class="control-group">
	  	<label class="control-label" for="startdate"><fmt:message key="label.startdate"/></label>
	    <div class="controls">
	      <input type="text" name="startdate" placeholder="mm/dd/yyyy" class="datepicker">
	    </div>
	  </div>
	  <div class="control-group">
	  	<label class="control-label" for="enddate"><fmt:message key="label.enddate"/></label>
	    <div class="controls">
	      <input type="text" name="enddate" placeholder="mm/dd/yyyy" class="datepicker">
	    </div>
	  </div>
	  <div class="control-group">
	    <div class="controls">
	     <input type="submit" value="Search" class="btn btn-primary" />
	    </div>
	  </div>
	</form>
	<c:if test="${! empty  searchrpslist}">
		<table id="sortTable">
			<tr>
				<th><fmt:message key="label.programslot.programName" /></th>
				<th><fmt:message key="label.programslot.programDate" /></th>
				<th><fmt:message key="label.programslot.presenter" /></th>
				<th><fmt:message key="label.programslot.producer" /></th>
				<th><fmt:message key="label.programslot.startTime" /></th>
				<th><fmt:message key="label.programslot.endTime" /></th>
			</tr>
			<c:forEach var="programslot" items="${searchrpslist}" varStatus="status">
				<tr class="${status.index%2==0?'even':'odd'}">
					<td class="nowrap">${programslot.programName}</td>
					<td class="nowrap">${programslot.programDate}</td>
					<td class="nowrap">${programslot.presenter}</td>
					<td class="nowrap">${programslot.producer}</td>
					<td class="nowrap">${programslot.startTime}</td>
					<td class="nowrap">${programslot.endTime}</td>
				</tr>
			</c:forEach>
		</table>
	</c:if>
</div>

</body>
</html>