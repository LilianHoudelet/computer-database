<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html>

<html>
<head>
<title>Computer Database</title>
<meta name="viewport" content="width=device-width, initial-scale=1.0">
<!-- Bootstrap -->
<link href="${pageContext.request.contextPath}/css/bootstrap.min.css"
	rel="stylesheet" media="screen">
<link href="${pageContext.request.contextPath}/css/font-awesome.css"
	rel="stylesheet" media="screen">
<link href="${pageContext.request.contextPath}/css/main.css"
	rel="stylesheet" media="screen">

</head>
<body>
	<header class="navbar navbar-inverse navbar-fixed-top">
		<div class="container">
			<a class="navbar-brand" href="ListComputer" id="applicationTitle">
				Application - Computer Database </a>
		</div>
	</header>
	<section id="main">
		<div class="container">
			<div class="row">
				<div class="col-xs-8 col-xs-offset-2 box">
					<h1 id="loginTitle">
						<!-- <fmt:message key="label.addComputer" /> -->
						Login Page
					</h1>
					<c:if test="${not empty errorMessge}">
						<div style="color: red; font-weight: bold; margin: 30px 0px;">${errorMessge}</div>
					</c:if>
					<form name='login' action="login" method='POST'>

						<table>
							<tr>
								<td>User:</td>
								<td><input type='text' name='username' value=''></td>
							</tr>
							<tr>
								<td>Password:</td>
								<td><input type='password' name='password' /></td>
							</tr>
							<tr>
								<td>Remember Me:</td>
								<td><input type="checkbox" name="remember-me" /></td>
							</tr>
							<tr>
								<td><input name="submit" type="submit" value="submit" /></td>
							</tr>
						</table>
						<input type="hidden" name="${_csrf.parameterName}"
							value="${_csrf.token}" />
					</form>
				</div>
			</div>
		</div>
	</section>
</body>
</html>