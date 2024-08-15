<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ page import="java.util.List" %>
<%@ page import="com.example.news.model.Category" %>
<%@ page import="com.example.news.model.News" %>
<html>
<head>
    <meta charset="UTF-8">
    <title>News List</title>
    <style>
        table {
            width: 100%;
            border-collapse: collapse;
        }

        table, th, td {
            border: 1px solid black;
        }

        th, td {
            padding: 8px;
            text-align: left;
        }
    </style>
</head>
<body>
<h1>News List</h1>
<a href="news?action=create">Create New News</a>
<hr>
<%
    List<Category> categories = (List<Category>) request.getAttribute("categories");
    List<News> newsList = (List<News>) request.getAttribute("newsList");
%>
<form method="get" action="news">
    <label for="category">Filter by category:</label>
    <select id="category" name="id">
        <option value="">Select category</option>
        <% for (Category category : categories) { %>
        <option value="<%= category.getId() %>"><%= category.getName() %>
        </option>
        <% } %>
    </select>
    <input type="submit" name="action" value="filter">
</form>
<table>
    <thead>
    <tr>
        <th>Title</th>
        <th>Content</th>
        <th>Published Date</th>
        <th>Category</th>
        <th>Actions</th>
    </tr>
    </thead>
    <tbody>
    <% for (News news : newsList) { %>
    <tr>
        <td><%= news.getTitle() %></td>
        <td><%= news.getContent()%></td>
        <td><%= new java.text.SimpleDateFormat("yyyy-MM-dd").format(news.getPublishedDate()) %></td>
        <td><%= news.getCategory().getName() %></td>
        <td>
            <a href="news?action=edit&id=<%= news.getId() %>">Edit</a> |
            <a href="news?action=delete&id=<%= news.getId() %>"
               onclick="return confirm('Are you sure you want to delete this news item?');">Delete</a>
        </td>
    </tr>
    <% } %>
    </tbody>
</table>
</body>
</html>
