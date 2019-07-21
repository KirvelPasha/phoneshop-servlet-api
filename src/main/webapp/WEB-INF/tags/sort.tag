<%@ tag trimDirectiveWhitespaces="true" %>
<%@ attribute name="sortBy" required="true" %>
<%@ attribute name="order" required="true" %>

<a href="?query=${param.query}&sortBy=${sortBy}&order=${order}">${order}</a>