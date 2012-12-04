<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@taglib uri="http://www.springframework.org/tags/form" prefix="form" %>
<jsp:include page="header.jsp" />
<div id="ajax-spinner">We're processing your request. <br /> Images are being loaded from Flickr.</div>
<div class="container">
<form:form modelAttribute="searchForm" method="get" action="results" class="form-signin">
	<form:errors path="term" cssClass="error" />

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

</div>
<jsp:include page="footer.jsp" />