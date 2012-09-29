<%@ page contentType="text/html;charset=UTF-8" language="java"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>

<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<fmt:setBundle basename="ApplicationResources" />
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Insert title here</title>
</head>
<body>
<script type = "text/javascript">
  function onDblClickTR(pragramName){
	  if(self.opener){
		  self.opener.afterProgramSearch(programName);
	  }
	  window.close();
  }
  
  function onSelectTR(programName){
	  if(self.opener){
		  self.opener.afterProgramSearch(programName);
	  }
	  window.close();
	  
  }
</script>

<table border='1' cellpadding="1" cellspacing="1" width='100%' align='center'>
<tr>
<td></td>
  <th align="left"><b> <fmt:message key="label.programSearch.programName" /></b></th>
 </tr>
<c:forEach items="${programList}" var="program">

<tr style="cursor:hand;" ondblclick="javaScript:onDblClickTR('${program}');" onClick="javaScript:onSelectTR('${program}');" onMouseOver="this.bgColor='silver';" onMouseOut="this.bgColor='white';" >

<td width='10%' align='left'><input type="radio" id ='radio' name ='radio' /></td>
  <td> ${program}</td>
  
  </tr>  
</c:forEach>
</table>
</body>
</html>