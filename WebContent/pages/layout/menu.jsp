<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>

<fmt:setBundle basename="ApplicationResources" />
<div class="nav nav-list bs-docs-sidenav affix">
<ul class="nav nav-tabs nav-stacked">
<c:if test="${sessionScope.user==null}">
<li><a href="<c:url value="/pages/login.jsp"/>"> <fmt:message
					key="caption.menu.login" /></a></li>
</c:if>
<c:if test="${sessionScope.user.roles[0].role=='admin'}">
<li>
				<a href="<c:url value="/controller/inputuser?insert=true&action=create"/>"> <fmt:message
						key="caption.menu.createuser" />
				</a>
</li>
<li>

			<a href="<c:url value="/controller/loaduser"/>"> <fmt:message
					key="caption.menu.modifyuser" />
			</a>
</li>
<li>

			<a href="<c:url value="/controller/loaduser"/>"> <fmt:message
					key="caption.menu.deleteuser" />
			</a>
</li>
</c:if>
<c:if test="${sessionScope.user.roles[0].role=='manager'}">
<li>

				<a href="<c:url value="/controller/searchrp"/>"> <fmt:message
						key="caption.menu.searchrp" />
				</a>
</li>
<li>
				<a href="<c:url value="/controller/loadrp"/>"> <fmt:message
						key="caption.menu.managerp" />
				</a>
</li>
<!-- 
<li>
				<a href="<c:url value="/controller/scheduledprogram"/>"> <fmt:message
						key="caption.menu.scheduledprogram" />
				</a>
</li> -->
<li> 
  <a href="<c:url value="/controller/initMaintainSchedule?option=create" />"> 
  <fmt:message key="title.createSchedule"/></a>
</li>
<li>
 <a href="<c:url value="/controller/initMaintainSchedule?option=createByCopy" />"> 
	  <fmt:message key="title.createByCopySchedule"/></a>
</li>
<!-- 
<li>
 <a href="<c:url value="/controller/initMaintainSchedule?option=createByCopy" />"> 
	  <fmt:message key="title.createByCopySchedule"/></a>
</li>  -->
<li>
<a href="<c:url value="/controller/initMaintainSchedule?option=modify" />"> 
	  <fmt:message key="title.modifySchedule"/></a>
</li>
<li>
 <a href="<c:url value="/controller/initMaintainSchedule?option=delete" />"> 
	  <fmt:message key="title.deleteSchedule"/></a>
</li>
</c:if>
<c:if test="${sessionScope.user!=null}">
<li><a href="<c:url value="/controller/logout"/>"> <fmt:message
					key="caption.menu.logout" />
		</a>
</li>
</c:if>
</ul>
</div>


