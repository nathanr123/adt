<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<c:set var="contextPath" value="${pageContext.request.contextPath}" />
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Insert title here</title>
<script language="javascript">
	function move(tbFrom, tbTo, isreverse) {
		var arrFrom = new Array();
		var arrTo = new Array();
		var arrLU = new Array();
		var i;
		for (i = 0; i < tbTo.options.length; i++) {
			arrLU[tbTo.options[i].text] = tbTo.options[i].value;
			arrTo[i] = tbTo.options[i].text;
		}
		var fLength = 0;
		var tLength = arrTo.length;
		for (i = 0; i < tbFrom.options.length; i++) {
			arrLU[tbFrom.options[i].text] = tbFrom.options[i].value;
			if (tbFrom.options[i].selected && tbFrom.options[i].value != "") {
				arrTo[tLength] = tbFrom.options[i].text;
				tLength++;
			} else {
				arrFrom[fLength] = tbFrom.options[i].text;
				fLength++;
			}
		}

		tbFrom.length = 0;
		tbTo.length = 0;
		var ii;

		for (ii = 0; ii < arrFrom.length; ii++) {
			var no = new Option();
			no.value = arrLU[arrFrom[ii]];
			no.text = arrFrom[ii];
			tbFrom[ii] = no;
		}

		for (ii = 0; ii < arrTo.length; ii++) {
			var no = new Option();
			no.value = arrLU[arrTo[ii]];
			no.text = arrTo[ii];
			tbTo[ii] = no;
			tbTo[ii].selectedIndex = ii;
		}

		if (isreverse) {
			for (var i = 0; i < tbFrom.length; i++) {
				tbFrom.options[i].selected = true;
			}
		} else {
			for (var i = 0; i < tbTo.length; i++) {
				tbTo.options[i].selected = true;
			}
		}
	}
</script>
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
	<c:if test="${not empty msg}">
		<tr>
			<td colspan="2" align="center">${msg}</td>
		</tr>
	</c:if>
	<form:form action="assignGroupPermission" method="post"
		commandName="userGroupPermissionForm">
		<table style="width: 50%;">
			<tr>
				<td colspan="3" align="center">Assigning Users and Groups</td>
			</tr>
			<tr>
				<td colspan="3" align="left">Group List</td>
			</tr>
			<tr>
				<td style="width: 46%;"><form:select id="fromGroup"
						path="fromGroup" items="${groupList}" multiple="true"
						style="width:100%;height:350px;" /></td>
				<td style="width: 8%;">
					<table style="width: 100%; height: 50%;">
						<tr>
							<td><input type="button"
								onClick="move(this.form.fromGroup,this.form.toGroup,false)"
								value="->"></td>
						</tr>
						<tr>
							<td><input type="button"
								onClick="move(this.form.toGroup,this.form.fromGroup,true)"
								value="<-"></td>
						</tr>
					</table>
				</td>
				<td style="width: 46%;"><form:select id="toGroup"
						path="toGroup" multiple="true" style="width:100%;height:350px;" /></td>
			</tr>
			<tr>
				<td colspan="3" align="left">Users list</td>
			</tr>
			<tr>
				<td style="width: 46%;"><form:select path="fromComponent"
						items="${componentList}" multiple="true"
						style="width:100%;height:350px;" /></td>
				<td style="width: 8%;">
					<table style="width: 100%; height: 50%;">
						<tr>
							<td><input type="button"
								onClick="move(this.form.fromComponent,this.form.toComponent)" value="->"></td>
						</tr>
						<tr>
							<td><input type="button"
								onClick="move(this.form.toComponent,this.form.fromComponent)" value="<-">
							</td>
						</tr>
					</table>
				</td>
				<td style="width: 46%;"><form:select path="toComponent"
						multiple="true" style="width:100%;height:350px;" /></td>
			</tr>
			<tr>
				<td colspan="3" align="center"><p class="errorblock">These
						group of users has the following permissions for the selected
						components.</p></td>
			</tr>
			<tr>
				<td colspan="3" align="left">Permissions</td>
			</tr>
			<tr>
				<td colspan="3" align="left"><form:checkboxes
						items="${permissionList}" path="permissions" /></td>
			</tr>
			<tr>
				<td colspan="3" align="center"><form:errors path="*"
						cssClass="errorblock" element="div" /></td>
			</tr>
			<tr>
				<td colspan="3" align="center"><input type="submit"
					value="Apply" /></td>
			</tr>
		</table>
	</form:form>
</body>
</html>