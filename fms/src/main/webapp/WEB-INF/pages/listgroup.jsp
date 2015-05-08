<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<c:set var="contextPath" value="${pageContext.request.contextPath}" />
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Insert title here</title>
</head>
<body>
	<c:if test="${not empty msg}">
		<tr>
			<td colspan="2" align="center">${msg}</td>
		</tr>
	</c:if>

	<h3>Users List</h3>
	<c:if test="${!empty grouplist}">
		<table class="data">
			<tr>
				<th>Group Name</th>
				<th>Priority</th>
				<th>Created Time</th>
				<th>Modified Time</th>
				<th>&nbsp;</th>
				<th>&nbsp;</th>
			</tr>
			<c:forEach items="${grouplist}" var="groupDetail">
				<tr>
					<td>${groupDetail.groupname}</td>
					<td>${groupDetail.priority}</td>
					<td>${groupDetail.createdtime}</td>
					<td>${groupDetail.modifiedtime}</td>
					<td><a
						href="${contextPath}/loadGroupdetail?group=${groupDetail.groupid}">Modify</a></td>
					<td><a
						href="${contextPath}/deleteGroup?group=${groupDetail.groupid}">Delete</a></td>
				</tr>
			</c:forEach>
		</table>
	</c:if>
</body>
</html>