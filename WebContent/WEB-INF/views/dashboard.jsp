<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html>
<html>
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
			<h1 id="homeTitle">${countComputer} Computers found</h1>
			<div id="actions" class="form-horizontal">
				<div class="pull-left">
					<form id="searchForm" action="SearchComputer" method="GET"
						class="form-inline">

						<input type="search" id="searchbox" name="search"
							class="form-control" placeholder="Search name" /> <input
							type="submit" id="searchsubmit" value="Filter by name"
							class="btn btn-primary" />
					</form>
				</div>
				<div class="pull-right">
					<a class="btn btn-success" id="addComputer"
						href="${pageContext.request.contextPath}/AddComputer">Add
						Computer</a> <a class="btn btn-default" id="editComputer" href="#"
						onclick="$.fn.toggleEditMode();">Edit</a>
				</div>
			</div>
		</div>

		<form id="deleteForm" action="DeleteComputer" method="POST">
			<input type="hidden" name="selection" value="">
		</form>
		
		<form id="orderForm" action="OrderBy" method="POST">
			<input type="hidden" name="orderByAttribute" value="">
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
						<th><a href="#"
							id="orderName"
							onclick="$.fn.orderBy('computer.name');"><i
								class="fa fa-sort"></i></a> Computer name</th>
						<th><a href="#"
							id="orderName"
							onclick="$.fn.orderBy('computer.introduced');"><i
								class="fa fa-sort"></i></a>Introduced date</th>
						<!-- Table header for Discontinued Date -->
						<th><a href="#"
							id="orderName"
							onclick="$.fn.orderBy('computer.discontinued');"><i
								class="fa fa-sort"></i></a>Discontinued date</th>
						<!-- Table header for Company -->
						<th><a href="#"
							id="orderName"
							onclick="$.fn.orderBy('company.name');"><i
								class="fa fa-sort"></i></a>Company</th>

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
							<td><a
								href="EditComputer?id=${c.getId()}&name=${c.getName()}"><c:out
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
		<div class="container text-center">
			<ul class="pagination">
				<c:choose>
					<c:when test="${!empty searchPage}">
					<li><a id="firstPage" href="SearchComputer?pageno=1&search=${search}"
							aria-label="Previous"> <span aria-hidden="true">&laquo;</span>
						</a></li>
						<c:if test="${ numeroPage!=1}">
							<li><a id="previousPage"
								href="SearchComputer?pageno=${numeroPage - 1}&search=${search}">Previous</a></li>
						</c:if>
						<c:forEach var="entry" begin="${indexDebut}" end="${indexFin}"
							step="1">

							<li><a id="page${entry}" href="SearchComputer?pageno=${entry}&search=${search}">${entry}</a></li>

						</c:forEach>
						<c:if test="${ (numeroPage!=pageMax) and (pageMax!=0)}">
							<li><a id="nextPage"
								href="SearchComputer?pageno=${numeroPage + 1}&search=${search}">Next</a></li>
						</c:if>
						<li><a id="lastPage" href="SearchComputer?pageno=${pageMax}&search=${search}"
							aria-label="Next"> <span aria-hidden="true">&raquo;</span>
						</a></li>
					</c:when>
					<c:otherwise>
						<li><a id="firstPage" href="ListComputer?pageno=1"
							aria-label="Previous"> <span aria-hidden="true">&laquo;</span>
						</a></li>
						<c:if test="${ numeroPage!=1}">
							<li><a id="previousPage"
								href="ListComputer?pageno=${numeroPage - 1}">Previous</a></li>
						</c:if>
						<c:forEach var="entry" begin="${indexDebut}" end="${indexFin}"
							step="1">

							<li><a id="page${entry}" href="ListComputer?pageno=${entry}">${entry}</a></li>

						</c:forEach>
						<c:if test="${ (numeroPage!=pageMax) and (pageMax!=0)}">
							<li><a id="nextPage"
								href="ListComputer?pageno=${numeroPage + 1}">Next</a></li>
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