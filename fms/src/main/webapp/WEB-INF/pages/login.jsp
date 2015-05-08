<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@page session="true"%>
<html>
<head>
<title>Login Page</title>
<style>
.error {
	margin: 100px auto;
	width: 300px;
	padding: 15px;
	margin-bottom: 20px;
	border: 1px solid transparent;
	border-radius: 4px;
	color: #a94442;
	background-color: #f2dede;
	border-color: #ebccd1;
}

.msg {
	margin: 100px auto;
	width: 300px;
	padding: 15px;
	margin-bottom: 20px;
	border: 1px solid transparent;
	border-radius: 4px;
	color: #31708f;
	background-color: #d9edf7;
	border-color: #bce8f1;
}

#login-box {
	color: white;
	width: 300px;
	padding: 20px;
	margin: 100px auto;
}

#login-table {
	color: black;
}

@font-face {
	font-family: 'SegoeUI_Family';
	font-style: normal;
	font-weight: 700;
	src: local('Segoe UI Bold'), local('SegoeUI-bold'), local('segoeuib');
}

@font-face {
	font-family: 'SegoeUI_Family';
	font-style: normal;
	font-weight: 600;
	src: local('Segoe UI Semibold'), local('SegoeUI-Semibold');
}

@font-face {
	font-family: 'SegoeUI_Family';
	font-style: normal;
	font-weight: 400;
	src: local('Segoe UI'), local('SegoeUI');
}

@font-face {
	font-family: 'SegoeUI_Family';
	font-style: normal;
	font-weight: 300;
	src: local('Segoe UI Light'), local('SegoeUI-Light');
}

BODY {
	font-family: 'SegoeUI_Family';
}

.boldtext {
	font-family: 'Segoe UI';
	font-weight: 700;
	color: black;
}

.normaltext {
	font-family: 'Segoe UI';
}
</style>
</head>
<body onload='document.loginForm.username.focus();'>

	<div id="login-box">

		<p class="boldtext">Sign in with Username and Password</p>

		<form name='loginForm' action="<c:url value='/login' />" method='POST'>

			<table id="login-table">
				<tr>
					<td style="width: 35%">Username</td>
					<td style="width: 65%"><input type='text' name='username'></td>
				</tr>
				<tr>
					<td style="width: 35%">Password</td>
					<td style="width: 65%"><input type='password' name='password' /></td>
				</tr>
				<tr>
					<td colspan='2' align="right"><input class="normaltext"
						name="submit" type="submit" value="Login" /></td>
				</tr>
			</table>

			<input type="hidden" name="${_csrf.parameterName}"
				value="${_csrf.token}" />

		</form>

	</div>
	<p>
		<c:if test="${not empty error}">
			<div class="error">${error}</div>
		</c:if>
		<c:if test="${not empty msg}">
			<div class="msg">${msg}</div>
		</c:if>
	</p>
</body>
</html>