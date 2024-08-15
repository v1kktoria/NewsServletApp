<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Add Category</title>
</head>
<body>
<h1>Add new category</h1>
<form action="categories" method="post">
    <input type="hidden" name="action" value="add"/>
    <label for="name">Category Name:</label>
    <input type="text" id="name" name="name" required>
    <input type="submit" value="Add Category"/>
</form>
<a href="categories">Category List</a>
</body>
</html>
