<%@ taglib uri="http://tiles.apache.org/tags-tiles" prefix="tiles"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>

<link href="<c:url value='/css/main.css'/>" rel="stylesheet"
	type="text/css" />
	
<link href="<c:url value='/css/bootstrap.min.css'/>" rel="stylesheet"
	type="text/css" />
<script src="/phoenix/javascript/jquery.js"></script>
<script src="/phoenix/javascript/bootstrap-datepicker.js"></script>
<script src="/phoenix/javascript/jquery-datatable.js"></script>
<div><tiles:insertAttribute name="header" /></div>
<div class="container-fluid">
  <div class="row-fluid">
    <div class="span2">
      <tiles:insertAttribute name="menu" />
    </div>
    <div class="span10">
      <tiles:insertAttribute name="error" />
      <tiles:insertAttribute name="body" />
    </div>
  </div>
</div>
<div><tiles:insertAttribute name="footer" /></div>
<script type="text/javascript">
$('.datepicker').datepicker();
</script>