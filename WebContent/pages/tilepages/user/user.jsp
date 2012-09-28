<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>

<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">

<fmt:setBundle basename="ApplicationResources" />

<title><fmt:message key="title.modifyuser" /></title>
</head>
<body>
	<c:if test="${param['insert']=='true'}">
		<form action="${pageContext.request.contextPath}/controller/createuser" method=post>
	</c:if>	
	<c:if test="${param['insert']=='false'}">
		<form action="${pageContext.request.contextPath}/controller/modifyuser" method=post>
	</c:if>	
		<center>
			<table cellpadding=4 cellspacing=2 border=0>
				<tr>
					<td><fmt:message key="label.user.id" /></td>
					<td><c:if test="${param['insert'] == 'true'}">
							<input type="text" name="userid" value="${param['userid']}" size=15
								maxlength=20>
							<input type="hidden" name="ins" value="true" />
						</c:if> 
						<c:if test="${param['insert']=='false'}">
							<input type="text" name="userid" value="${param['userid']}" size=15
								maxlength=20 readonly="readonly">
							<input type="hidden" name="ins" value="false" />
						</c:if></td>
				</tr>
				<tr>
					<td><fmt:message key="label.user.name" /></td>
					<td><input type="text" name="username"
						value="${selecteduser.name}" size=45 maxlength=20></td>
				</tr>
				<tr>
					<td><fmt:message key="label.user.password" /></td>
					<td><input type="password" name="password"
						value="${selecteduser.password}" size=15 maxlength=20></td>
				</tr>
				<tr>
					<td><fmt:message key="label.user.confirmpassword" /></td>
					<td><input type="password" name="confirmPassword"
						value="${selecteduser.password}" size=15 maxlength=20></td>
				</tr>	
				<tr>
					<td><fmt:message key="label.user.address1" /></td>
					<td><input type="text" name="address1"
						value="${selecteduser.address1}" size=50 maxlength=100></td>
				</tr>
				<tr>
					<td><fmt:message key="label.user.address2" /></td>
					<td><input type="text" name="address2"
						value="${selecteduser.address2}" size=50 maxlength=100></td>
				</tr>	
				<tr>
					<td><fmt:message key="label.user.address3" /></td>
					<td><input type="text" name="address3"
						value="${selecteduser.address3}" size=50 maxlength=100></td>
				</tr>														
				<tr>
					<td><fmt:message key="label.user.city" /></td>
					<td><input type="text" name="city"
						value="${selecteduser.city}" size=20 maxlength=50></td>
				</tr>
				<tr>
					<td><fmt:message key="label.user.state" /></td>
					<td><input type="text" name="state"
						value="${selecteduser.state}" size=20 maxlength=50></td>
				</tr>				
				<tr>
					<td><fmt:message key="label.user.country" /></td>
					<td>
					<select name="country">
						<option value="zz">Select a Country</option>
						<c:forEach var="country" items="${countries}" varStatus="countrystatus">
							<option value="${country[0]}"
								<c:if test="${country[0] == selecteduser.country}">selected</c:if>
							 >${country[1]}</option>
						</c:forEach>
					</select>
					</td>
				</tr>	
				<tr>
					<td><fmt:message key="label.user.homephone" /></td>
					<td><input type="text" name="homephone"
						value="${selecteduser.homePhone}" size=15 maxlength=50></td>
				</tr>	
				<tr>
					<td><fmt:message key="label.user.mobile" /></td>
					<td><input type="text" name="mobile"
						value="${selecteduser.mobile}" size=15 maxlength=50></td>
				</tr>
				<tr>
					<td><fmt:message key="label.user.email" /></td>
					<td><input type="text" name="email"
						value="${selecteduser.emailAddress}" size=50 maxlength=50></td>
				</tr>
				<tr>
					<td><fmt:message key="label.user.role" /></td>
					<td>
					<table>
						<c:forEach var="systemRole" items="${systemroles}" varStatus="rolestatus">
							<tr><td><input type="checkbox" name="role" value="${systemRole.role}"
									<c:if test="${selecteduser.roles.contains(systemRole)}">
										checked = "true"
									</c:if>
							></td><td>${systemRole.role}</td></tr>
						</c:forEach>
					</table>
					</td>
				</tr>				
				
				</table>
		</center>
		<input type="hidden" name="action" value="${param['action']}" />
		<c:if test="${empty param['action']}">
			<input type="submit" value="Save"> 
			<input type="button" value="Cancel" onclick="javascript:history.back(-1)" >		
			<input type="reset" value="Reset">
		</c:if>	
		<c:if test="${param['action'] == 'modify'}">
			<input type="submit" value="Update"> 
			<input type="button" value="Cancel" onclick="javascript:history.back(-1)" >		
		</c:if>	
		<c:if test="${param['action'] == 'delete'}">
			<input type="submit" value="Confirm Delete"> 
			<input type="button" value="Cancel" onclick="javascript:history.back(-1)" >	
		</c:if>	
	</form>
</body>
</html>