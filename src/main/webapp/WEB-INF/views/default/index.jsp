<%@ taglib uri="http://www.springframework.org/tags" prefix="spring" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib tagdir="/WEB-INF/tags" prefix="t" %>
<t:base>

<jsp:attribute name="title">Biblioteca de Alejandría</jsp:attribute>

<jsp:body>
<div class="jumbotron">
  <h1>Biblioteca de Alejandría</h1>
  <p>¡Bienvenido/a a la Biblioteca de Alejandría! Puede comenzar a trabajar directamente.</p>
  <p><a href="<spring:url value="/prestamo/nuevo" />" class="btn btn-success btn-lg"><span class="glyphicon glyphicon-plus"></span> Realizar préstamo</a></p>
</div>
</jsp:body>

</t:base>