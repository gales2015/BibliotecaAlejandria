<%@ taglib uri="http://www.springframework.org/tags" prefix="spring" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib tagdir="/WEB-INF/tags" prefix="t" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<t:base>

<jsp:attribute name="title">Listado de libros</jsp:attribute>

<jsp:body>

<ul class="breadcrumb">
  <li><a href="<spring:url value="/" />">Inicio</a></li>
  <li class="active">Listado de libros</li>
</ul>

<spring:url var="urlFiltrarLibroForm" value="/libro/" />
<form:form action="${urlFiltrarUsuarioForm}" method="get" commandName="libro" class="form-horizontal">
<div class="no-padding overflow">
	<div class="col-lg-2 col-lg-offset-5">
		<label for="titulo" class="control-label">Buscar por título: </label>
	</div>
	<div class="col-lg-3">
		<form:input type="text" class="form-control" path="titulo" id="titulo" placeholder="Título parcial" />
	</div>
	<div class="col-lg-2">
		<button type="submit" class="btn btn-primary"><span class="glyphicon glyphicon-search"></span> Buscar libros</button>
	</div>
</div>
</form:form>

<div class="panel panel-primary">
  <div class="panel-heading">
    <h3 class="panel-title">Listado de libros</h3>
  </div>
  <div class="panel-body">
    <table class="table table-striped table-hover ">
	  <thead>
	    <tr>
	      <th>ISBN</th>
	      <th>Título</th>
	      <th>Autor</th>
	      <th>Categoría</th>
	      <th>Ejemplares</th>
	      <th>Acciones</th>
	    </tr>
	  </thead>
	  <tbody>
	  <c:forEach items="${libros}" var="libro"> 
		  <tr>
		    <td>${libro.isbn}</td>
		    <td>${libro.titulo}</td>
		    <td>${libro.autor}</td>
		    <td>${libro.categoria}</td>
		    <td>${libro.totalEjemplaresLibres}/${libro.totalEjemplares}</td>
		    <td>
		    	<a href="<spring:url value="/libro/ver/${libro.isbn}" />" class="btn btn-info btn-xs">Ver</a>
		    </td>
		  </tr>
		</c:forEach>
	  </tbody>
    </table>
  </div>
</div>
</jsp:body>

</t:base>