<!DOCTYPE HTML>
<%@taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<link rel="stylesheet" media="screen" href="/webjars/bootstrap/3.3.7/css/bootstrap.min.css">
<html>
<head>
    <title>Next Todo</title>
</head>
<body>
<h1>Next Todo</h1>

<div>
	<table>
	<tr>
	  <td><h3>Task</h3></td>
	  <td><h3>Description</h3></td>
	  <td><h3>Priority</h3></td>
	  <td><h3>Important</h3></td>
	</tr>
	<tr>
		<td><c:out value="${todo.task}"/></td>
		<td><c:out value="${todo.description}"/></td>
		<td><c:out value="${todo.priority}"/></td>
		<td><c:out value="${todo.important}"/></td>
	</tr>
	</table>
</div>

<div>
	<a href="/list" class="btn btn-default">List All</a>

	<a href="/delete?id=${todo.id}" class="btn btn-default">Delete</a>
</div>

</body>
</html>
