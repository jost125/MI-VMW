<%@taglib uri="http://www.springframework.org/tags/form" prefix="form" %>
<jsp:include page="header.jsp" />

<form:form modelAttribute="searchForm" method="get" action="results" class="form-signin">
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