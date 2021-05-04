<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jstl/fmt"%>
<!DOCTYPE html>
<html lang="${lang}">
<head>
<title>Computer Database</title>
<meta name="viewport" content="width=device-width, initial-scale=1.0">
<meta charset="utf-8">
<!-- Bootstrap -->
<link href="${pageContext.request.contextPath}/css/bootstrap.min.css"
	rel="stylesheet" media="screen">
<link href="${pageContext.request.contextPath}/css/font-awesome.css"
	rel="stylesheet" media="screen">
<link href="${pageContext.request.contextPath}/css/main.css"
	rel="stylesheet" media="screen">

<link href="${pageContext.request.contextPath}/css/flags-sprite.css"
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
			<h1 id="homeTitle">${countComputer}
				<fmt:message key="label.found" />
			</h1>
			<div id="actions" class="form-horizontal">
				<div class="pull-left">
					<form id="searchForm" action="ListComputer" method="GET"
						class="form-inline">

						<input type="search" id="searchbox" name="search"
							class="form-control"
							placeholder="<fmt:message key="label.searchName"/>" /> <input
							type="submit" id="searchsubmit"
							value="<fmt:message key="label.filterByName"/>"
							class="btn btn-primary" />
					</form>
				</div>
				<div class="pull-right">
					<a class="btn btn-success" id="addComputer" href="AddComputer"><fmt:message
							key="label.addComputer" /></a> <a class="btn btn-default"
						id="editComputer" href="#" onclick="$.fn.toggleEditMode();"><fmt:message
							key="label.edit" /></a>
				</div>
			</div>
		</div>

		<form id="deleteForm" action="ListComputer" method="POST">
			<input type="hidden" name="selection" value="">
		</form>

		<form id="orderForm" action="ListComputer" method="POST">
			<input type="hidden" name="orderByAttribute" value="">
			<c:if test="${ !empty search }">
				<input type="hidden" name="search" value="${search}">
			</c:if>
		</form>

		<div class="container" style="margin-top: 10px;">
			<table class="table table-striped table-bordered">
				<thead>
					<tr>
						<!-- Variable declarations for passing labels as parameters -->
						<!-- Table header for Computer Name -->

						<th class="editMode" style="width: 60px; height: 22px;"><input
							type="checkbox" id="selectall" /> <span
							style="vertical-align: top;"> - <a href="#"
								id="deleteSelected" onclick="$.fn.deleteSelected();"> <i
									class="fa fa-trash-o fa-lg"></i>
							</a>
						</span></th>
						<th><a href="#" id="orderName"
							onclick="$.fn.orderBy('computer.name');"><i
								class="fa fa-sort"></i></a> <fmt:message key="label.computerName" /></th>
						<th><a href="#" id="orderName"
							onclick="$.fn.orderBy('computer.introduced');"><i
								class="fa fa-sort"></i></a> <fmt:message key="label.introduced" /></th>
						<!-- Table header for Discontinued Date -->
						<th><a href="#" id="orderName"
							onclick="$.fn.orderBy('computer.discontinued');"><i
								class="fa fa-sort"></i></a> <fmt:message key="label.discontinued" /></th>
						<!-- Table header for Company -->
						<th><a href="#" id="orderName"
							onclick="$.fn.orderBy('company.name');"><i class="fa fa-sort"></i></a>
							<fmt:message key="label.company" /></th>

					</tr>
				</thead>
				<!-- Browse attribute computers -->
				<c:if test="${ !empty erreur }">
					<span class="error" style="color: red"> There is an error in
						the DB date : <c:out value="${ erreur }" />
					</span>
				</c:if>
				<tbody id="results">

					<c:forEach items="${listeComputers}" var="c">
						<tr>
							<td class="editMode"><input type="checkbox" name="cb"
								class="cb" value="${c.getId()}"></td>
							<td><a href="EditComputer?id=${c.getId()}"><c:out
										value="${c.getName()}"></c:out></a></td>
							<td><c:out value="${c.getIntroduced()}"></c:out></td>
							<td><c:out value="${c.getDiscontinued()}"></c:out></td>
							<td><c:out value="${c.getCompanyName()}"></c:out></td>
						<tr>
					</c:forEach>

				</tbody>
			</table>
		</div>
	</section>

	<footer class="navbar-fixed-bottom">
		<div class="btn-group btn-group-sm pull-left" role="group">

			<a href="?lang=fr" title="fr"><span class="flag-icon reset fr"></span></a>
			<a href="?lang=en" title="en"><span class="flag-icon reset gb"></span></a>
		</div>
		<div class="container text-center">
			<ul class="pagination">
				<c:choose>
					<c:when test="${!empty search}">
						<li><a id="firstPage"
							href="ListComputer?pageno=1&search=${search}"
							aria-label="Previous"> <span aria-hidden="true">&laquo;</span>
						</a></li>
						<c:if test="${ numeroPage!=1}">
							<li><a id="previousPage"
								href="ListComputer?pageno=${numeroPage - 1}&search=${search}">Previous</a></li>
						</c:if>
						<c:forEach var="entry" begin="${indexDebut}" end="${indexFin}"
							step="1">

							<li><a id="page${entry}"
								href="ListComputer?pageno=${entry}&search=${search}">${entry}</a></li>

						</c:forEach>
						<c:if test="${ (numeroPage!=pageMax) and (pageMax!=0)}">
							<li><a id="nextPage"
								href="ListComputer?pageno=${numeroPage + 1}&search=${search}">Next</a></li>
						</c:if>
						<li><a id="lastPage"
							href="ListComputer?pageno=${pageMax}&search=${search}"
							aria-label="Next"> <span aria-hidden="true">&raquo;</span>
						</a></li>
					</c:when>
					<c:otherwise>
						<li><a id="firstPage" href="ListComputer?pageno=1"
							aria-label="Previous"> <span aria-hidden="true">&laquo;</span>
						</a></li>
						<c:if test="${ numeroPage!=1}">
							<li><a id="previousPage"
								href="ListComputer?pageno=${numeroPage - 1}"><fmt:message
										key="label.previous" /></a></li>
						</c:if>
						<c:forEach var="entry" begin="${indexDebut}" end="${indexFin}"
							step="1">

							<li><a id="page${entry}" href="ListComputer?pageno=${entry}">${entry}</a></li>

						</c:forEach>
						<c:if test="${ (numeroPage!=pageMax) and (pageMax!=0)}">
							<li><a id="nextPage"
								href="ListComputer?pageno=${numeroPage + 1}"><fmt:message
										key="label.next" /></a></li>
						</c:if>
						<li><a id="lastPage" href="ListComputer?pageno=${pageMax}"
							aria-label="Next"> <span aria-hidden="true">&raquo;</span>
						</a></li>
					</c:otherwise>
				</c:choose>


			</ul>

			<div class="btn-group btn-group-sm pull-right" role="group">
				<form action="${pageContext.request.contextPath}/ListComputer"
					method="post">
					<button name="nbObject" id="nbObject10" type="submit"
						class="btn btn-default" value="10">10</button>
					<button name="nbObject" id="nbObject50" type="submit"
						class="btn btn-default" value="50">50</button>
					<button name="nbObject" id="nbObject100" type="submit"
						class="btn btn-default" value="100">100</button>
				</form>
			</div>
		</div>
	</footer>
	<script src="${pageContext.request.contextPath}/js/jquery.min.js"></script>
	<script src="${pageContext.request.contextPath}/js/bootstrap.min.js"></script>
	<script src="${pageContext.request.contextPath}/js/dashboard.js"></script>
	<script src="${pageContext.request.contextPath}/js/orderBy.js"></script>
</body>
</html>