<%@ taglib uri="http://www.springframework.org/tags" prefix="spring" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib tagdir="/WEB-INF/tags" prefix="t" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<t:base>

<jsp:attribute name="title">Listado de préstamos</jsp:attribute>

<jsp:body>

<ul class="breadcrumb">
  <li><a href="<spring:url value="/" />">Inicio</a></li>
  <li class="active">Listado de préstamos</li>
</ul>

<spring:url var="urlFiltrarUsuarioForm" value="/prestamo/" />
<form:form action="${urlFiltrarUsuarioForm}" method="get" commandName="prestamoForm" class="form-horizontal">
<div class="no-padding overflow">
	<div class="col-lg-2">
		<a href="<spring:url value="/prestamo/nuevo" />" class="btn btn-success"><span class="glyphicon glyphicon-plus"></span> Nuevo préstamo</a>
	</div>
	<div class="col-lg-2 col-lg-offset-3">
		<label for="devuelto" class="control-label">Filtrar por estado: </label>
	</div>
	<div class="col-lg-3">
		<form:select class="form-control" path="devuelto" id="devuelto">
     		<form:option label="--" value="-1" />
     		<form:options items="${arrDevuelto}" />
		</form:select>
	</div>
	<div class="col-lg-2">
		<button type="submit" class="btn btn-primary"><span class="glyphicon glyphicon-search"></span> Buscar usuarios</button>
	</div>
</div>
</form:form>

<div class="panel panel-primary">
  <div class="panel-heading">
    <h3 class="panel-title">Listado de préstamos</h3>
  </div>
  <div class="panel-body">
    <table class="table table-striped table-hover ">
	  <thead>
	    <tr>
	      <th>ID</th>
	      <th>Fecha inicio</th>
	      <th>Fecha fin</th>
	      <th>Fecha devolución</th>
	      <th>Libro</th>
	      <th>Ejemplar</th>
	      <th>Usuario</th>
	      <th>Acciones</th>
	    </tr>
	  </thead>
	  <tbody>
	  <c:forEach items="${prestamos}" var="prestamo"> 
		  <tr>
		    <td>${prestamo.id}</td>
		    <td>${prestamo.fechaInicioFormat}</td>
		    <td>${prestamo.fechaFinFormat}</td>
		    <td>${prestamo.devuelto ? prestamo.fechaDevolucionFormat : "<em>Pendiente</em>"}</td>
		    <td>${prestamo.ejemplar.libro}</td>
		    <td>${prestamo.ejemplar.id}</td>
		    <td>${prestamo.usuario}</td>
		    <td>
		    	<c:if test="${!prestamo.devuelto}">
		    		<a href="<spring:url value="/prestamo/devolver/${prestamo.id}" />" class="btn btn-primary btn-xs">Devolver</a>
		    	</c:if>
		    </td>
		  </tr>
		</c:forEach>
	  </tbody>
    </table>
  </div>
</div>
</jsp:body>

</t:base>