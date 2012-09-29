<%@ page contentType="text/html;charset=UTF-8" language="java"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>    
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<fmt:setBundle basename="ApplicationResources" />
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Schedule Search Screen</title>
</head>
<body>
<script type = "text/javascript">
 function searchSchedule(){
	 document.getElementById("option").value ="search";
	 document.forms[0].submit();
 }
 
 function onSelectTR(scheduleId,programName,presenterName,producerName,programDate,startTime,endTime){
	 
	  if(self.opener){
		  self.opener.afterScheduleSearch(scheduleId,programName,presenterName,producerName,programDate,startTime,endTime);
	  }
	  window.close();
	  
 }
 
</script>

<form action="${pageContext.request.contextPath}/controller/searchSchedule">
<table>
  <tr>
   <td><fmt:message key="label.schedule.programDate" /></td>
   <td><input type="text" name="programDate" value="${param['name']}" size=15 maxlength=20></td>
   <td><input type="submit" value="Serach Schedule" onClick="javaScript:searchSchedule()"></td>
  </tr>
</table>

<br/><br/>
<table border='1' cellpadding="1" cellspacing="1" width='100%' align='center'>
<tr>
<td></td>
 <th align="left"><b> <fmt:message key="label.scheduleSearch.scheduleId" /></b></th>
 <th align="left"><b> <fmt:message key="label.scheduleSearch.programName" /></b></th>
 <th align="left"><b> <fmt:message key="label.scheduleSearch.presenterName" /></b></th>
 <th align="left"><b> <fmt:message key="label.scheduleSearch.producerName" /></b></th>
 <th align="left"><b> <fmt:message key="label.scheduleSearch.programDate" /></b></th>
 <th align="left"><b> <fmt:message key="label.scheduleSearch.startTime" /></b></th>
 <th align="left"><b> <fmt:message key="label.scheduleSearch.endTime" /></b></th>
 </tr>
 
 <c:forEach items="${scheduleList}" var="scheduleBean">
<tr style="cursor:hand;"  onClick="javaScript:onSelectTR('${scheduleBean.scheduleID}','${scheduleBean.programName}','${scheduleBean.presenterName}','${scheduleBean.producerName}','${scheduleBean.programDate}','${scheduleBean.startTime}','${scheduleBean.endTime}');"  onMouseOver="this.bgColor='silver';" onMouseOut="this.bgColor='white';" >

<td width='10%' align='left'><input type="radio" id ='radio' name ='radio' /></td>

  <td> ${scheduleBean.scheduleID}</td> 
  <td> ${scheduleBean.programName}</td> 
  <td> ${scheduleBean.presenterName}</td> 
  <td> ${scheduleBean.producerName}</td> 
  <td> ${scheduleBean.programDate}</td>
  <td> ${scheduleBean.startTime}</td>
  <td> ${scheduleBean.endTime}</td> 
  </tr>  
</c:forEach>
</table>
<input type="hidden" id="option" name="option"  />
</form>
</body>
</html>