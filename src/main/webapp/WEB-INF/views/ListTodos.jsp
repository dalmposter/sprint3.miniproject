<!DOCTYPE HTML>
<%@taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<link rel="stylesheet" media="screen" href="/webjars/bootstrap/3.3.7/css/bootstrap.min.css">
<html>
<head>
    <title>Todo List</title>
</head>
<body>
<h1>Todo List</h1>

<div>
	<table>	
	<tr>
	  <td><h3>Task</h3></td>
	  <td><h3>Description</h3></td>
	  <td><h3>Priority</h3></td>
	  <td><h3>Important</h3></td>
	  <td><h3></h3></td>
	  <td><h3></h3></td>
	</tr>
	<c:forEach items="${todos}" var="todo">
	<tr>
		<td><c:out value="${todo.task}"/></td>
		<td><c:out value="${todo.description}"/></td>
		<td><c:out value="${todo.priority}"/></td>
		<td><c:out value="${todo.important}"/></td>
		<td><a href="/delete?id=${todo.id}">Delete</a></td>
	</tr>
	</c:forEach>
	</table>
</div>

<div>
	<a href="/next" class="btn btn-default">Next Todo</a>

	<a href="/create" class="btn btn-default">Add Todo</a>
</div>

</body>
</html>
