<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jstl/fmt"%>

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
					<h1 id="addTitle">
						<fmt:message key="label.addComputer" />
					</h1>

					<form:form action="AddComputer" id="addComputerFrom" method="POST"
						modelAttribute="computerDTOAdd">
						<fieldset>
							<div class="form-group">
								<form:label path="computerName">
									<fmt:message key="label.computerName" var="computerNameVar" />
									<fmt:message key="label.computerName" />
								</form:label>

								<form:input type="text" class="form-control" path="computerName"
									id="computerName" placeholder="${computerNameVar}"
									required="required" />
								<form:errors path="computerName" cssClass="errorPerso" />
								<div class="alert alert-danger" role="alert" id="nameError"
									style="display: none;">
									<fmt:message key="computerDTOAdd.computerName.empty" />
								</div>


							</div>
							<div class="form-group">
								<form:label path="introduced">
									<fmt:message key="label.introduced" />
								</form:label>
								<form:input type="date" class="form-control" path="introduced"
									id="introduced" placeholder="Introduced date" />
							</div>
							<div class="form-group">
								<form:label path="discontinued">
									<fmt:message key="label.discontinued" />
								</form:label>
								<form:input type="date" class="form-control" path="discontinued"
									id="discontinued" placeholder="Discontinued date" />
								<form:errors path="discontinued" cssClass="errorPerso" />
								<div class="alert alert-danger" role="alert"
									id="discontinuedError" style="display: none;">
									<fmt:message
										key="computerDTOAdd.discontinued.disconBeforeIntro" />
								</div>


								<div style="display: none;" class="alert alert-danger"
									role="alert" id="noIntroError"><fmt:message
										key="computerDTOAdd.discontinued.noIntro" /></div>

							</div>
							<div class="form-group">
								<form:label path="companyId">
									<fmt:message key="label.company" />
								</form:label>
								<form:select path="companyId" class="form-control"
									id="companyId">
									<form:option value="0">...</form:option>
									<form:options items="${listCompanies}" itemLabel="name"
										itemValue="id" />

								</form:select>
							</div>
						</fieldset>
						<div class="actions pull-right">
							<input type="submit" value="<fmt:message key="label.add" />"
								class="btn btn-primary">
							<fmt:message key="label.or" />
							<a href="${pageContext.request.contextPath}/ListComputer"
								class="btn btn-default"><fmt:message key="label.cancel" /></a>
						</div>
					</form:form>
					<c:if test="${ !empty computerAdded }">
						<span id="computerAdded"><c:out value="${computerAdded}"></c:out>
						</span>
					</c:if>
				</div>
			</div>
		</div>
	</section>
	<script type="text/javascript"
		src="http://ajax.googleapis.com/ajax/libs/jquery/1.9.1/jquery.min.js"></script>
	 	<%-- <script src="${pageContext.request.contextPath}/js/addCompValidator.js"></script>  --%>
 

</body>
</html>