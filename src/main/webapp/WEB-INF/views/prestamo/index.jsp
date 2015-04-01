<%@ taglib uri="http://www.springframework.org/tags" prefix="spring" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib tagdir="/WEB-INF/tags" prefix="t" %>
<t:base>

<jsp:attribute name="title">Listado de préstamos</jsp:attribute>

<jsp:body>

<ul class="breadcrumb">
  <li><a href="<spring:url value="/" />">Inicio</a></li>
  <li class="active">Listado de préstamos</li>
</ul>

<p><a href="<spring:url value="/prestamo/nuevo" />" class="btn btn-success"><span class="glyphicon glyphicon-plus"></span> Nuevo préstamo</a></p>

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
		    <td>${prestamo.devuelto ? fechaDevolucionFormat : "<em>Pendiente</em>"}</td>
		    <td>${prestamo.ejemplar.libro}</td>
		    <td>${prestamo.ejemplar.id}</td>
		    <td>${prestamo.usuario}</td>
		    <td>
		    	<a href="<spring:url value="/prestamo/ver/${prestamo.id}" />" class="btn btn-info btn-xs">Ver</a>
		    	<a href="<spring:url value="/prestamo/devolver/${prestamo.id}" />" class="btn btn-success btn-xs">Devolver</a>
		    </td>
		  </tr>
		</c:forEach>
	  </tbody>
    </table>
  </div>
</div>
</jsp:body>

</t:base>