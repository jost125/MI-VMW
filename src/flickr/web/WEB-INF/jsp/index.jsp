<%@taglib uri="http://www.springframework.org/tags/form" prefix="form" %>
<jsp:include page="header.jsp" />

<script type="text/javascript">
	$(function() {
		$('#colorpicker-showColor').click(function() {
			$('#colorpicker').toggle('slide', {direction: 'left'});
		});
		$('#colorpicker').farbtastic(function(color) {
			$('input[name=color]').attr('value', color);
			$('#colorpicker-showColor').attr('style', 'background-color:' + color);
		});
	});
</script>


<form:form modelAttribute="searchForm" method="get" action="showResults.htm" class="form-signin">
	<label>Keywords</label>
	<form:input path="term" class="input-block-level"/>

	<label>Number of results</label>
	<form:input path="numberOfResults" value="100" class="input-block-level" />

	<label>Color</label>
	<form:hidden path="color" value="#d1ff94" />
	<div id="colorpicker-showColor"></div>
	<div id="colorpicker"></div>
	
	<br />
	<input type="submit" name="submit" value="Search" class="btn">
</form:form>


<jsp:include page="footer.jsp" />