<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN"
    "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Registration</title>
<style>
.error {
	color: #ff0000;
}

.errorblock {
	color: #000;
	background-color: #ffEEEE;
	border: 3px solid #ff0000;
	padding: 8px;
	margin: 16px;
}
</style>
</head>
<body>
	<div align="center">
		<form:form action="doChangePassword" method="post"
			commandName="changePasswordForm">
			<table style="width: 50%" border="0">
				<tr>
					<td colspan="2" align="center"><h2>Change Password</h2></td>
				</tr>
				<tr>
					<td style="width: 30%">User Name:</td>
					<td><form:input path="username" /></td>
				</tr>
				<tr>
					<td>Old Password:</td>
					<td><form:password path="oldPassword" /></td>
				</tr>
				<tr>
					<td>New Password:</td>
					<td><form:password path="newPassword" /></td>
				</tr>
				<tr>
					<td>Confirm Password:</td>
					<td><form:password path="confirmPassword" /></td>
				</tr>

				<tr>
					<td colspan="2" align="center"><form:errors path="*"
							cssClass="errorblock" element="div" /></td>
				</tr>
				<tr>
					<td colspan="2" align="center"><input type="submit"
						value="Change Password" /></td>
				</tr>
			</table>
		</form:form>
	</div>
</body>
</html>