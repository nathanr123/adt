<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN"
    "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Settings</title>
</head>
<body>
	<form:form action="savesettings" method="post"
		commandName="savesettingsForm">
	<input type="hidden" name="id"
				value="${savesettingsForm.getId()}" />

		<table id="login-table">
			<tr>
				<td style="width: 15%">Folder Name</td>
				<td style="width: 85%"><input type='text' name='foldername' 
					value="${savesettingsForm.getFoldername()}"></td>
			</tr>
			<tr>
				<td style="width: 15%"></td>
				<td style="width: 85%"></td>
			</tr>
			<tr>
				<td style="width: 15%">File Path</td>
				<td style="width: 85%"><input type='text' name='folderpath'
					value="${savesettingsForm.getFolderpath()}" style="width: 85%"></td>
			</tr>
			<tr>
				<td style="width: 15%"></td>
				<td style="width: 85%"></td>
			</tr>
			<tr>
				<td style="width: 15%">Last modified</td>
				<td style="width: 85%">"${savesettingsForm.getModifiedtime()}"</td>
			</tr>
			<tr>
				<td style="width: 15%"></td>
				<td style="width: 85%" align="left"><input class="normaltext"
					name="submit" type="submit" value="Save"  /></td>

			</tr>
		</table>

		<input type="hidden" name="${_csrf.parameterName}"
			value="${_csrf.token}" />

	</form:form>
</body>
</html>