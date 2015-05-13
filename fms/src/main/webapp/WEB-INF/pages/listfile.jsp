<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<c:set var="contextPath" value="${pageContext.request.contextPath}" />
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Uploaded Files</title>
</head>
<body>
	<c:if test="${not empty msg}">
		<tr>
			<td colspan="2" align="center">${msg}</td>
		</tr>
	</c:if>

	<h3>Files List</h3>
	<c:if test="${!empty fileslist}">
		<table class="data">
			<tr>
				<th>S.No</th>
				<th>File Name</th>
				<th>Size</th>
				<th>Description</th>
				<th>Uploaded Time</th>
				<th>Request</th>
			</tr>
			<c:forEach items="${fileslist}" var="upFile">
				<tr>
					<td>${i}</td>
					<td>${upFile.filepath}</td>
					<td>2652 KBs</td>
					<td>${upFile.description}</td>
					<td>${upFile.createdtime}</td>
					<td></td>
				</tr>
			</c:forEach>
		</table>
	</c:if>
</body>
</html>