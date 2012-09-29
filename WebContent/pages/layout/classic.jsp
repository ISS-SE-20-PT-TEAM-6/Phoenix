<%@ taglib uri="http://tiles.apache.org/tags-tiles" prefix="tiles"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>

<link href="<c:url value='/css/main.css'/>" rel="stylesheet"
	type="text/css" />
	
<link href="<c:url value='/css/bootstrap.min.css'/>" rel="stylesheet"
	type="text/css" />
<script src="/phoenix/javascript/jquery.js"></script>
<script src="/phoenix/javascript/bootstrap-datepicker.js"></script>
<table class="borderAll">
	<tr>
		<td colspan="2"><tiles:insertAttribute name="header" /></td>
	</tr>
	<tr>
		<td width="15%" align="center" valign="top"><tiles:insertAttribute name="menu" /></td>
		<td valign="top">
		<table>
			<tr><td><tiles:insertAttribute name="error" /></td></tr>
			<tr><td><tiles:insertAttribute name="body" /></td></tr>
		</table>
		</td>
	</tr>
	<tr>
		<td colspan="2"><tiles:insertAttribute name="footer" /></td>
	</tr>
</table>
<script type="text/javascript">
$('.datepicker').datepicker();

/* Table initialisation */
$(document).ready(function() {
	$('#sortTable').dataTable( {
		"sDom": "<'row'<'span6'l><'span6'f>r>t<'row'<'span6'i><'span6'p>>",
		"sPaginationType": "bootstrap",
		"oLanguage": {
			"sLengthMenu": "_MENU_ records per page"
		}
	} );
} );
</script>