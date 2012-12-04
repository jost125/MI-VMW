<%@taglib uri="http://www.springframework.org/tags/form" prefix="form" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<div id="ajax-spinner">We're processing your request. <br /> Images are being loaded from Flickr.</div>
<div class="container-fluid">
<div class="row-fluid">
	<div class="span2">
		<form:form modelAttribute="searchForm" method="get" action="results">
			<form:input path="term" class="input-block-level" value="${search.term}" placeholder="keyword to search" />
			<form:input path="numberOfResults" value="${search.numberOfResults}" class="input-block-level" placeholder="number of results" />
			<form:hidden path="color" value="${search.color}" />
			<div id="colorpicker-showColor" style="background-color: ${search.color}"></div>
			<div id="colorpicker"></div>

			<br />
			<input type="submit" name="submit" value="Search" class="btn">
		</form:form>

	</div>
	<jsp:include page="header-result.jsp" />
	<div class="results span10">
		<c:forEach var="photo" items="${photos}"><a
				href="http://farm${photo.farm}.staticflickr.com/${photo.server}/${photo.id}_${photo.secret}.jpg"><img
					width="75"
					height="75"
					src="http://farm${photo.farm}.staticflickr.com/${photo.server}/${photo.id}_${photo.secret}_s.jpg"
					style="opacity: 0"
				/></a></c:forEach>
	</div>
	<jsp:include page="footer.jsp" />
</div>

</div>