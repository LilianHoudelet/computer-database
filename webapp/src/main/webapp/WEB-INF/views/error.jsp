<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<!DOCTYPE html>
<html>
<head>
<meta name="viewport" content="width=device-width, initial-scale=1.0">
<!-- Bootstrap -->
<link href="${pageContext.request.contextPath}/css/bootstrap.min.css"
	rel="stylesheet" media="screen">
<link href="${pageContext.request.contextPath}/css/font-awesome.css"
	rel="stylesheet" media="screen">
<link href="${pageContext.request.contextPath}/css/main.css"
	rel="stylesheet" media="screen">
<title>Error</title>
</head>
<body>
	<header class="navbar navbar-inverse navbar-fixed-top">
		<div class="container">
			<a class="navbar-brand" href="ListComputer"> Application -
				Computer Database </a>
		</div>
	</header>

	<section id="main">
		<div class="container">
			<div class="alert alert-danger" id="nameError">
				${timestamp} :
				<fmt:message key="${errorOccured}">
					<fmt:param value="${value}" />
				</fmt:message>
			</div>
		</div>
	</section>


</body>
</html>