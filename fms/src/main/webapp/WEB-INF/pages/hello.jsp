<%@taglib prefix="sec"
	uri="http://www.springframework.org/security/tags"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<html>
<c:set var="contextPath" value="${pageContext.request.contextPath}" />
<body>

	<!-- For login user -->
	<c:url value="/logout" var="logoutUrl" />
	<form action="${logoutUrl}" method="post" id="logoutForm">
		<input type="hidden" name="${_csrf.parameterName}"
			value="${_csrf.token}" />
		<script>
			function formSubmit() {
				document.getElementById("logoutForm").submit();
			}
		</script>

		<c:if test="${pageContext.request.userPrincipal.name != null}">
				
					User : ${pageContext.request.userPrincipal.name} | 
					<a href="javascript:formSubmit()"> Logout</a> | 
					<a href="${contextPath}/changePassword">Change Password</a> |
					<a href="${contextPath}/uploadfile">Upload File</a>| 
					<a href="${contextPath}/listfiles">List Files</a>| 
		
					<a
				href="${contextPath}/loadUserdetail?user=${pageContext.request.userPrincipal.name}">Update
				Profile</a>|
					<sec:authorize access="hasRole('ROLE_ADMIN')">
				<a href="${contextPath}/newuser">Create New User</a>| 
					<a href="${contextPath}/listusers">List User</a>| 
					<a href="${contextPath}/settings">Settings</a>|
					</sec:authorize>

		</c:if>
		<c:if test="${not empty msg}">
			<div class="msg">${msg}</div>
		</c:if>
	</form>


</body>
</html>