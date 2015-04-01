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
  </div>
</div>
</jsp:body>

</t:base>