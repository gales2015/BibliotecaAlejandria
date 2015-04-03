<%@ tag language="java" pageEncoding="UTF-8"%>
<%@ taglib uri="http://www.springframework.org/tags" prefix="spring" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@attribute name="title" fragment="true"%>
<%@attribute name="breadcrums" fragment="true"%>
<%@attribute name="jsDocumentReady" fragment="true"%>
<html>
	<head>
		<title><jsp:invoke fragment="title" /></title>
		<link rel="stylesheet" href="<spring:url value="/resources/css/main.css" />">
		<link rel="icon" type="image/x-icon" href="<spring:url value="/resources/images/favicon.ico" />" />
		<link rel="icon" type="image/png" sizes="96x96" href="<spring:url value="/resources/images/favicon-96x96.png" />">
    	<link rel="apple-touch-icon" sizes="60x60" href="<spring:url value="/resources/images/apple-icon-60x60.png" />">
	</head>
	<body>
	<nav class="navbar navbar-default navbar-fixed-top">
  <div class="container">
    <div class="navbar-header">
      <button type="button" class="navbar-toggle collapsed" data-toggle="collapse" data-target="#bs-example-navbar-collapse-1">
        <span class="sr-only">Toggle navigation</span>
        <span class="icon-bar"></span>
        <span class="icon-bar"></span>
        <span class="icon-bar"></span>
      </button>
      <a class="navbar-brand" href="<spring:url value="/" />" >Alejandría</a>
    </div>

    <div class="collapse navbar-collapse" id="bs-example-navbar-collapse-1">
      <ul class="nav navbar-nav">
        <li><a href="<spring:url value="/libro/" />">Libros</a></li>
        <li><a href="<spring:url value="/prestamo/" />">Préstamos</a></li>
        <li><a href="<spring:url value="/usuario/" />">Usuarios</a></li>
      </ul>
      <ul class="nav navbar-nav navbar-right">
		<li><a href="<spring:url value="/about" />">Acerca de Biblioteca de Alejandría</a></li>
      </ul>
    </div>
  </div>
</nav>

<div class="container global-container">

<c:catch var="info"/>
<c:catch var="success"/>
<c:catch var="warning"/>
<c:catch var="danger"/>
<c:choose>
	<c:when test="${not empty info}">
		<div class="alert alert-dismissible alert-info">
		  <button type="button" class="close" data-dismiss="alert">×</button>
		  ${info}
		</div>
	</c:when>
	<c:when test="${not empty success}">
		<div class="alert alert-dismissible alert-success">
		  <button type="button" class="close" data-dismiss="alert">×</button>
		  ${success}
		</div>
	</c:when>
	<c:when test="${not empty warning}">
		<div class="alert alert-dismissible alert-warning">
		  <button type="button" class="close" data-dismiss="alert">×</button>
		  ${warning}
		</div>
	</c:when>
	<c:when test="${not empty danger}">
		<div class="alert alert-dismissible alert-danger">
		  <button type="button" class="close" data-dismiss="alert">×</button>
		  ${danger}
		</div>
	</c:when>
</c:choose>

<jsp:doBody />

</div>

<script type="text/javascript" src="<spring:url value="/resources/js/jquery.min.js" />" ></script>
<script type="text/javascript" src="<spring:url value="/resources/js/bootstrap.min.js" />" ></script>
<script type="text/javascript" src="<spring:url value="/resources/js/bootswatch.js" />" ></script>
<script type="text/javascript">
	$(document).ready(function () {
		<jsp:invoke fragment="jsDocumentReady" />
	});
</script>
</body>
</html>
