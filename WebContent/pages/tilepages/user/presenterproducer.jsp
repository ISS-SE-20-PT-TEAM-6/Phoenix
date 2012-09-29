<%@ page language="java" contentType="text/html; charset=US-ASCII"
    pageEncoding="US-ASCII"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=US-ASCII">
<fmt:setBundle basename="ApplicationResources" />
<title><fmt:message key="title.presenterproducer" /></title>
</head>
<body>
<h1>Search Producer/Presenter</h1>
	<form action="${pageContext.request.contextPath}/controller/searchschedule" method=post class="form-horizontal">
	
	  <div class="control-group">
	  	<label class="control-label" for="presenterproducername">User Name</label>
	    <div class="controls">
	      <input type="text" name="presenterproducername" placeholder="Presenter/Producer Name"> <input type="submit" value="Search" class="btn btn-primary" />
	    </div>
	  </div>
	</form>
		<c:if test="${! empty  searchuserlist}">
	<table cellpadding="0" cellspacing="0" border="0" class="display" id="sortTable">
	<thead>
	
			<tr>
				<th>Select</th>
				<th><fmt:message key="label.user.name" /></th>
			</tr>
	</thead>
	<tbody>
			<c:forEach var="presenterproducer" items="${searchuserlist}" varStatus="status">
				<tr class="odd gradeX">
					<td><input type="radio" name="programId" value="${presenterproducer.userId}" class="userId"/></td>
					<td>${user.name}</td>
				</tr>
			</c:forEach>
		</tbody>
		</table>
	<form id="selectSchedule" action="${pageContext.request.contextPath}/controller/selectschedule" method=post>
		<div class="control-group">
	    <div class="controls">
	     <input type="submit" value="Select" class="btn btn-primary" />
	    </div>
	  </div>
		<input type="hidden" name="userId" id="selectedUser" />
	</form>
	</c:if>
</div>
<script type="text/javascript">

/* Table initialisation */
$(document).ready(function() {
	$('#sortTable').dataTable( {
		"sDom": "<'row'<'span6'l><'span6'f>r>t<'row'<'span6'i><'span6'p>>",
		"sPaginationType": "bootstrap",
		"bFilter":false,
		"oLanguage": {
			"sLengthMenu": "_MENU_ records per page"
		}
	} );
	
	$('.userId').click( function() {
        $("#selectedUser").val(this.value);
        
    } );
} );
</script>
</body>
</html>