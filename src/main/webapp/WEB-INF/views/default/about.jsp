<%@ taglib uri="http://www.springframework.org/tags" prefix="spring" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib tagdir="/WEB-INF/tags" prefix="t" %>
<t:base>

<jsp:attribute name="title">Acerca de Biblioteca de Alejandría</jsp:attribute>

<jsp:body>

<h1>¿Qué es ésto?</h1>
<p class="lead">Es un proyecto perteneciente a una práctica de la asignatura <strong>Programación Cliente-Servidor</strong> para alumnos de 3º curso del <em>Grado en Ciencias de la Computación</em> por la <em>Universidad de Gales</em>.</p>
<p class="lead">Este proyecto consiste en una investigación acerca de <strong>Spring 3 + Hibernate</strong> (framework de Java y ORM, respectivamente).</p>

<h1>¿Quiénes han realizado esta práctica?</h1>
<ul class="lead">
	<li>Daniel Sánchez</li>
	<li>Daniel Miñana</li>
	<li>Garikoitz Aguirre</li>
	<li>Jon Ander Cermeño</li>
</ul>

<h1>¿Dónde puedo encontrar el código fuente de esta práctica?</h1>
<p class="lead">En el momento de la presentación, el código se puede encontrar en su repositorio de Github: <a href="https://github.com/gales2015/BibliotecaAlejandria" target="_blank">gales2015/BibliotecaAlejandria</a></p>

</jsp:body>

</t:base>