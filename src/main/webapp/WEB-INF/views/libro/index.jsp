<%@ taglib uri="http://www.springframework.org/tags" prefix="spring" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib tagdir="/WEB-INF/tags" prefix="t" %>
<t:base>

<jsp:attribute name="title">Listado de libros</jsp:attribute>

<jsp:body>

<ul class="breadcrumb">
  <li><a href="<spring:url value="/" />">Inicio</a></li>
  <li class="active">Listado de libros</li>
</ul>

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
		    <td>0/${libro.totalEjemplares}</td>
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