<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
 <%@ page import="Inventory.*" %>
  <%@ page import="supply.*" %>
  <%@ page import="java.util.*" %>
  
<!DOCTYPE html>
<html>
	<head>
		<meta charset="UTF-8">
		<title>Create Supply order</title>
	</head>
	<body>
		<form action="SupplyServlet">
			<label name="code">Product Code </label>
			<input type="text" id="code" for="code">
			<label name="name"> Item </label>
			<input type="text" id="name" for="name ">
			<label name="qty"> Quantity </label>
			<input type="text" id="qty" for="qty">
			<label name="cost"> Unit Cost</label>
			<input type="text" id="cost" for="cost">
			<label name="job"> Assigned Job</label>
			<input type="submit" value="Save">
		</form>
		<input type="button" value="Add Item" id="addItem">
		<input type="button" value="Cancel Item" id="cancelItem">
		<input type="button" value="Cancel Order" id="cancelOrder">
		
	</body>
</html>