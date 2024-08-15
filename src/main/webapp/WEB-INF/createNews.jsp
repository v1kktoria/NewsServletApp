<%@ page import="com.example.news.model.Category" %>
<%@ page import="java.util.List" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Create News</title>
</head>
<body>
<h1>Create News</h1>
<form action="news" method="post">
    <input type="hidden" name="action" value="add"/>
    <label for="title">Title</label>
    <input type="text" id="title" name="title" required><br/><br/>
    <label for="content">Content:</label>
    <textarea id="content" name="content" required></textarea><br/><br/>
    <label for="publishedDate">Published Date:</label>
    <input type="date" id="publishedDate" name="publishedDate" required/><br/><br/>
    <label for="category">Category:</label>
    <select name="categoryId" id="category">
        <option value="">Select a category</option>
        <%
            List<Category> categories = (List<Category>) request.getAttribute("categories");
            if (categories != null) {
                for (Category category : categories) {
        %>
        <option value="<%= category.getId() %>">
            <%= category.getName() %>
        </option>
        <%
                }
            }
        %>
    </select>
    <a href="categories?action=create">Add New Category</a><br/><br/>
    <input type="submit" value="Save"/>
</form>
<a href="news">Back to News List</a>
</body>
</html>
