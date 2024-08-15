<%@ page import="com.example.news.model.Category" %>
<%@ page import="java.util.List" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Category List</title>
    <style>
        .error {
            color: red;
            font-weight: bold;
        }
    </style>
</head>
<body>
<h1>Category List</h1>
<%
    String errorMessage = request.getParameter("error");
    if (errorMessage != null && !errorMessage.isEmpty()) {
%>
<div class="error">
    <%= errorMessage %>
</div>
<%
    }
%>
<a href="categories?action=create">Add New Category</a>
<table>
    <thead>
    <tr>
        <th>ID</th>
        <th>Name</th>
        <th>Actions</th>
    </tr>
    </thead>
    <tbody>
    <%
        List<Category> categories = (List<Category>) request.getAttribute("categories");
        if (categories != null) {
            for (Category category : categories) {
    %>
    <tr>
        <td><%= category.getId() %></td>
        <td><%= category.getName() %></td>
        <td>
            <a href="categories?action=delete&id=<%= category.getId() %>">Delete</a>
        </td>
    </tr>
    <%
            }
        }
    %>
    </tbody>
</table>
</body>
</html>
