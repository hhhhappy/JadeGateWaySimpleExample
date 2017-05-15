<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Insert title here</title>
</head>
<body>
	<form id=fr_add name=fr_add method=get action="AddNumbers">
		A : <input type=text value="" name=a><br>
		B : <input type=text value="" name=b><br>
		<input type=submit name=sb value="Add A+B">
	</form>
	
	<br/>
	<br/>
	<p>Response:</p>
	<p id = "result"></p>

</body>
</html>