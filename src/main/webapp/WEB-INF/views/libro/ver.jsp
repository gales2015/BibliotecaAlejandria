<%@ taglib uri="http://www.springframework.org/tags" prefix="spring" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib tagdir="/WEB-INF/tags" prefix="t" %>
<t:base>

<jsp:attribute name="title">Ver libro</jsp:attribute>

<jsp:body>

<ul class="breadcrumb">
  <li><a href="<spring:url value="/" />">Inicio</a></li>
  <li><a href="<spring:url value="/libro/" />">Listado de libros</a></li>
  <li class="active">Ver libro</li>
</ul>

<div class="panel panel-info">
  <div class="panel-heading">
    <h3 class="panel-title">Ver libro</h3>
  </div>
  <div class="panel-body">
  	<h3>Datos del libro</h3>
	  <dl class="dl-horizontal">
	  	<dt>ISBN</dt>
	  	<dd>${libro.isbn}</dd>
	  	<dt>Título</dt>
	  	<dd>${libro.titulo}</dd>
	  	<dt>Autor</dt>
	  	<dd>${libro.autor}</dd>
	  	<dt>Categoría</dt>
	  	<dd>${libro.categoria}</dd>
	  </dl>
  	<h3>Ejemplares del libro</h3>
  </div>
</div>
</jsp:body>

</t:base>