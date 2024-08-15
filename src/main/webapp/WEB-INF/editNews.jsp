<%@ page import="com.example.news.model.News" %>
<%@ page import="com.example.news.model.Category" %>
<%@ page import="java.util.List" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Edit News</title>
</head>
<body>
<h1>Edit News</h1>
<form action="news" method="post">
    <input type="hidden" name="action" value="update"/>
    <input type="hidden" name="id" value="<%= ((News) request.getAttribute("news")).getId() %>"/>
    <label for="title">Title:</label>
    <input type="text" id="title" name="title" value="<%= ((News) request.getAttribute("news")).getTitle() %>"
           required/><br/><br/>
    <label for="content">Content:</label>
    <textarea id="content" name="content"
              required><%= ((News) request.getAttribute("news")).getContent() %></textarea><br/><br/>
    <label for="publishedDate">Published Date:</label>
    <input type="date" id="publishedDate" name="publishedDate"
           value="<%= new java.text.SimpleDateFormat("yyyy-MM-dd").format(((News) request.getAttribute("news")).getPublishedDate()) %>"
           required/><br/><br/>
    <label for="category">Category:</label>
    <select name="categoryId" id="category">
        <%
            List<Category> categories = (List<Category>) request.getAttribute("categories");
            Long currentCategoryId = ((News) request.getAttribute("news")).getCategory().getId();
            if (categories != null) {
                for (Category category : categories) {
        %>
        <option value="<%= category.getId() %>"
                <%= (currentCategoryId != null && currentCategoryId.equals(category.getId())) ? "selected" : "" %>>
            <%= category.getName() %>
        </option>
        <%
                }
            }
        %>
    </select><br/><br/>
    <input type="submit" value="Save"/>
</form>
<a href="news">Back to News List</a>
</body>
</html>
