<%@ taglib uri="http://www.springframework.org/tags" prefix="spring" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib tagdir="/WEB-INF/tags" prefix="t" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<t:base>

<jsp:attribute name="title">Listado de usuarios</jsp:attribute>

<jsp:body>

<ul class="breadcrumb">
  <li><a href="<spring:url value="/" />">Inicio</a></li>
  <li class="active">Listado de usuarios</li>
</ul>

<spring:url var="urlFiltrarUsuarioForm" value="/usuario/" />
<form:form action="${urlFiltrarUsuarioForm}" method="get" commandName="usuario" class="form-horizontal">
<div class="no-padding overflow">
	<div class="col-lg-2">
		<a href="<spring:url value="/usuario/nuevo" />" class="btn btn-success"><span class="glyphicon glyphicon-plus"></span> Nuevo usuario</a>
	</div>
	<div class="col-lg-2 col-lg-offset-3">
		<label for="nombre" class="control-label">Buscar por nombre: </label>
	</div>
	<div class="col-lg-3">
		<form:input type="text" class="form-control" path="nombre" id="nombre" placeholder="Nombre parcial" />
	</div>
	<div class="col-lg-2">
		<button type="submit" class="btn btn-primary"><span class="glyphicon glyphicon-search"></span> Buscar usuarios</button>
	</div>
</div>
</form:form>

<div class="panel panel-primary">
  <div class="panel-heading">
    <h3 class="panel-title">Listado de usuarios</h3>
  </div>
  <div class="panel-body">
    <table class="table table-striped table-hover ">
	  <thead>
	    <tr>
	      <th>ID</th>
	      <th>Nombre</th>
	      <th>Apellidos</th>
	      <th>DNI</th>
	      <th>Email</th>
	      <th>Dirección</th>
	      <th>Acciones</th>
	    </tr>
	  </thead>
	  <tbody>
	  <c:forEach items="${usuarios}" var="usuario"> 
		  <tr>
		    <td>${usuario.id}</td>
		    <td>${usuario.nombre}</td>
		    <td>${usuario.apellidos}</td>
		    <td>${usuario.dni}</td>
		    <td>${usuario.email}</td>
		    <td>${usuario.direccion}</td>
		    <td>
		    	<a href="<spring:url value="/usuario/ver/${usuario.id}" />" class="btn btn-info btn-xs">Ver</a>
		    	<a href="<spring:url value="/usuario/editar/${usuario.id}" />" class="btn btn-primary btn-xs">Editar</a>
		    	<a href="<spring:url value="/usuario/eliminar/${usuario.id}" />" class="btn btn-${usuario.hasPrestamosPendientes ? 'default disabled': 'danger'} btn-xs">Eliminar</a>
		    </td>
		  </tr>
		</c:forEach>
	  </tbody>
    </table>
  </div>
</div>
</jsp:body>

</t:base>