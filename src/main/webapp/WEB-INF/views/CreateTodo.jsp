<!DOCTYPE HTML>
<%@taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<link rel="stylesheet" media="screen" href="/webjars/bootstrap/3.3.7/css/bootstrap.min.css">
<html>
<head>
    <title>Create Todo</title>
    	<style>
	.error {
		color: #ff0000;
	}
	
	.errorblock{
		color: #000;
		background-color: #ffEEEE;
		border: 3px solid #ff0000;
		padding:8px;
		margin:16px;
	}
	</style>
</head>
<body>
<h1>Create Todo</h1>

<form:form method="POST" modelAttribute="todo" action="/create">
   <table>
    <tr>
        <td><form:label path="task">Task</form:label></td>
        <td><form:input path="task" /></td>
        <td><form:errors path="task"  cssClass="error" /></td>
    </tr>
    <tr>
        <td><form:label path="description">Description</form:label></td>
        <td><form:input path="description" /></td>
        <td><form:errors path="description"  cssClass="error" /></td>
    </tr>
    <tr>
        <td><form:label path="priority">Priority</form:label></td>
        <td><form:input path="priority" /></td>
        <td><form:errors path="priority"  cssClass="error" /></td>
    </tr>
    <tr>
        <td><form:label path="important">Important</form:label></td>
        <td><form:checkbox path="important" /></td>
        <td><form:errors path="important"  cssClass="error" /></td>
    </tr>
    <tr>
        <td colspan="2">
            <input type="submit" value="Add" name="add" class="btn btn-default"/>
        </td>
        <td colspan="2">
            <input type="submit" value="Cancel" name="cancel" class="btn btn-default"/>
        </td>
    </tr>
</table>  
</form:form>
</body>
</html>
