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
			<a class="navbar-brand" href="ListComputer"> Application -
				Computer Database </a>
		</div>
	</header>
	<section id="main">
		<div class="container">
			<div class="row">
				<div class="col-xs-8 col-xs-offset-2 box">
					<div class="label label-default pull-right">
						id:
						<c:out value="${id}"></c:out>
					</div>
					<h1>
						<fmt:message key="label.editComputer" />
					</h1>

					<form:form action="EditComputer" method="POST"
						id="editComputerForm" modelAttribute="computerDTOEdit">
						<form:input type="hidden" value="${id}" id="id" path="id" />
						<fieldset>
							<div class="form-group">
								<form:label path="computerName">
									<fmt:message key="label.computerName" />
								</form:label>
								<form:input type="text" class="form-control" id="computerName"
									path="computerName" value="${name}" placeholder="${name}" />
								<form:errors path="computerName" cssClass="errorPerso" />
								<div class="alert alert-danger" role="alert" id="nameError"
									<c:if test="${ empty erreurName }">style="display: none;" </c:if>>The
									computer name must not be empty.</div>

							</div>

							<div class="form-group">
								<form:label path="introduced">
									<fmt:message key="label.introduced" />
								</form:label>
								<form:input type="date" class="form-control" id="introduced"
									path="introduced" placeholder="Introduced date"
									value="${introduced}" />
							</div>
							<div class="form-group">
								<form:label path="discontinued">
									<fmt:message key="label.discontinued" />
								</form:label>
								<form:input type="date" class="form-control" path="discontinued"
									value="${discontinued}" id="discontinued"
									placeholder="Discontinued date" />
								<form:errors path="discontinued" cssClass="errorPerso" />

								<div class="alert alert-danger" role="alert"
									id="discontinuedError"
									<c:if test="${ empty erreurDiscoBeforeIntro }">style="display: none;" </c:if>>Discontinued
									is before introduced.</div>


								<div
									<c:if test="${ empty erreurNoIntro }"> style="display: none;" </c:if>
									class="alert alert-danger" role="alert" id="noIntroError">Introduced
									is needed to input discontinued.</div>

							</div>
							<div class="form-group">
								<form:label path="companyId">
									<fmt:message key="label.company" />
								</form:label>
								<form:select class="form-control" path="companyId"
									id="companyId">
									<form:option value="0"> ... </form:option>
									<form:options items="${listCompanies}" />

								</form:select>
							</div>
						</fieldset>
						<div class="actions pull-right">
							<input type="submit" value="<fmt:message key="label.edit" />"
								class="btn btn-primary">
							<fmt:message key="label.or" />
							<a href="ListComputer" class="btn btn-default"><fmt:message
									key="label.cancel" /></a>
						</div>
					</form:form>
					<c:if test="${ !empty computerEdited }">
						<span id="computerEdited"><c:out value="${computerEdited}"></c:out>
						</span>
					</c:if>
				</div>
			</div>
		</div>
	</section>
	<script type="text/javascript"
		src="http://ajax.googleapis.com/ajax/libs/jquery/1.9.1/jquery.min.js"></script>
	<%-- <script src="${pageContext.request.contextPath}/js/addCompValidator.js"></script> --%>
	<script type="text/javascript">
		$(function() {
			 $('#companyId').val(${companyId});
		});
	</script>
</body>
</html>