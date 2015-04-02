<%@ taglib uri="http://www.springframework.org/tags" prefix="spring" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib tagdir="/WEB-INF/tags" prefix="t" %>
<t:base>

<jsp:attribute name="title">Ver usuario</jsp:attribute>

<jsp:body>

<ul class="breadcrumb">
  <li><a href="<spring:url value="/" />">Inicio</a></li>
  <li><a href="<spring:url value="/usuario/" />">Listado de usuarios</a></li>
  <li class="active">Ver usuario</li>
</ul>

<div class="panel panel-info">
  <div class="panel-heading">
    <h3 class="panel-title">Ver usuario</h3>
  </div>
  <div class="panel-body">
  	<h3>Datos del usuario</h3>
	  <dl class="dl-horizontal">
	  	<dt>ID</dt>
	  	<dd>${usuario.id}</dd>
	  	<dt>Nombre</dt>
	  	<dd>${usuario.nombre}</dd>
	  	<dt>Apellidos</dt>
	  	<dd>${usuario.apellidos}</dd>
	  	<dt>DNI</dt>
	  	<dd>${usuario.dni}</dd>
	  	<dt>Email</dt>
	  	<dd>${usuario.email}</dd>
	  	<dt>Dirección</dt>
	  	<dd>${usuario.direccion}</dd>
	  </dl>
  	<h3>Préstamos del usuario</h3>
  	
  	<p><a href="<spring:url value="/prestamo/nuevo?usuario=${usuario.id}" />" class="btn btn-success"><span class="glyphicon glyphicon-plus"></span> Nuevo préstamo</a></p>

    <table class="table table-striped table-hover ">
	  <thead>
	    <tr>
	      <th>ID</th>
	      <th>Fecha inicio</th>
	      <th>Fecha fin</th>
	      <th>Fecha devolución</th>
	      <th>Libro</th>
	      <th>Ejemplar</th>
	      <th>Acciones</th>
	    </tr>
	  </thead>
	  <tbody>
	  <c:forEach items="${usuario.prestamos}" var="prestamo"> 
		  <tr>
		    <td>${prestamo.id}</td>
		    <td>${prestamo.fechaInicioFormat}</td>
		    <td>${prestamo.fechaFinFormat}</td>
		    <td>${prestamo.devuelto ? prestamo.fechaDevolucionFormat : "<em>Pendiente</em>"}</td>
		    <td>${prestamo.ejemplar.libro}</td>
		    <td>${prestamo.ejemplar.id}</td>
		    <td>
		    	<c:if test="${!prestamo.devuelto}">
		    		<a href="<spring:url value="/prestamo/devolver/${prestamo.id}" />" class="btn btn-success btn-xs">Devolver</a>
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