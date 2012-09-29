<%@ page contentType="text/html;charset=UTF-8" language="java"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>    
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<fmt:setBundle basename="ApplicationResources" />
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Maintain Schedule</title>
</head>
<body>
<script type = "text/javascript">

   var presenterProdVar ;
   function searchSchedule(){
	   window.open( "<%=request.getContextPath()%>"+"/controller/searchSchedule","myWindow", "status = 1, height = 300, width = 600, resizable = 0" ) ;
   }
   
   function searchProgram(){
	   window.open( "<%=request.getContextPath()%>"+"/controller/searchProgram","myWindow", "status = 1, height = 300, width = 600, resizable = 0" ) ;
   }
   
   function searchPresenter(){
	   presenterProdVar = "presenter";
	   window.open( "<%=request.getContextPath()%>"+"/controller/searchPresenter","myWindow", "status = 1, height = 300, width = 600, resizable = 0" ) ;
   }
   
   function searchProducer(){
	   presenterProdVar = "producer";
	   window.open( "<%=request.getContextPath()%>"+"/controller/searchPresenter","myWindow", "status = 1, height = 300, width = 600, resizable = 0" ) ;
   }
   
   function afterProgramSearch(selectedProgramName){
	   var selectedValueTxtObj=document.getElementById('programName');
	   
	   if(selectedValueTxtObj){
   		   selectedValueTxtObj.value=selectedProgramName;
   		   
   	   }else{
   		   alert("Select the program name");
   	   }
   }
   
   function afterPresenterSearch(selectedPresenterName){
	   
	   if(presenterProdVar=="presenter"){
		   var selectedValueTxtObj=document.getElementById('presenterName');
		  
		   if(selectedValueTxtObj){
	   		   selectedValueTxtObj.value=selectedPresenterName;
	   	   }else{
	   		   alert("Select the Presenter Name");
	   	   }   
	   }
	   
	   if(presenterProdVar =="producer" ){
		   var selectedValueTxtObj=document.getElementById('producerName');
		   
		   if(selectedValueTxtObj){
	   		   selectedValueTxtObj.value=selectedPresenterName;
	   		   
	   	   }else{
	   		   alert("Select the Producer Name");
	   	   }
	   }
       
	   
   }
   
   function afterScheduleSearch(scheduleId,programName,presenterName,
			  producerName,programDate,startTime,endTime){
	   
	   document.getElementById('scheduleId').value=scheduleId ;
	   document.getElementById('programName').value=programName;
	   document.getElementById('programDate').value=programDate;
	   document.getElementById('presenterName').value=presenterName;
	   document.getElementById('producerName').value=producerName;
	   document.getElementById('startTime').value=startTime;
	   document.getElementById('endTime').value=endTime;
	   

	   
	   
   }
   
   
</script>
<form action="${pageContext.request.contextPath}/controller/maintainSchedule" method="post">

<table>
<c:if test="${param.option=='init'}">
 <h3> Select the option in the left menu</h3>
</c:if>

<c:if test="${param.option!='init'}">
<c:if test="${param.option!='create'}">
<tr>
      <td><fmt:message key="label.schedule.scheduleId" /></td>
      
	  <td><input type="text" id="scheduleId" name="scheduleId" value="${param['name']}" size=12 maxlength=12></td>
	  <td><input type="button" name="search" value="Search Schedule" onClick="searchSchedule()"/></td>
	  
</tr>
</c:if>
<tr>
      <td><fmt:message key="label.schedule.program"/></td>
	  <td><input type="text" id="programName" name="programName" value="${param['name']}"  size=15 maxlength=20>
	  <img style="cursor:hand;" 
             onClick="javaScript:searchProgram();" src="${pageContext.request.contextPath}/img/search.gif" /></td>
	  
	  <td><fmt:message key="label.schedule.programDate"/> </td>
      <td><input type="text" id="programDate" name="programDate" value="${param['name']}" size=15 maxlength=20></td>

</tr>
<tr>
      <td><fmt:message key="label.schedule.presenter" /></td>
	  <td><input type="text" id="presenterName" name="presenterName" value="${param['name']}" size=15 maxlength=20>
	  <img style="cursor:hand;" 
             onClick="javaScript:searchPresenter();" src="${pageContext.request.contextPath}/img/search.gif" /></td>
	   <td><fmt:message key="label.schedule.producer" /></td>
     <td><input type="text" id="producerName" name="producerName" value="${param['name']}" size=15 maxlength=20>
     <img style="cursor:hand;" 
             onClick="javaScript:searchProducer();" src="${pageContext.request.contextPath}/img/search.gif" /></td>
</tr>

<tr>
   <td><fmt:message key="label.schedule.timeSlot" /></td>
</tr>
<tr>
   
    <td><fmt:message key="label.schedule.startTime" /></td>
     <td><input type="text" id="startTime" name="startTime" value="${param['name']}" size=15 maxlength=20></td>
     <td><fmt:message key="label.schedule.endTime" /></td>
     <td><input type="text" id="endTime" name="endTime" value="${param['name']}" size=15 maxlength=20></td>
</tr>


</c:if>					
</table>
<table>
	<c:if test="${param.option=='create' }">
	 <td colspan="2" align="left"><input type="submit" value="Create Schedule">
	 </c:if>
	 <c:if test="${param.option=='createByCopy' }">
	 <td colspan="2" align="left"><input type="submit" value="Create Schedule">
	 </c:if>
	 <c:if test="${param.option=='modify' }">
	 <td colspan="2" align="right"><input type="submit" value="Modify Schedule">
	 </c:if>
	 <c:if test="${param.option=='delete' }">
	 <td colspan="2" align="right"><input type="submit" value="Delete Schedule">
	 </c:if>
 
</table>
<input type="hidden" name="option" value="${param.option}" />

</form>				
</body>
</html>