<%@ taglib uri="http://www.springframework.org/tags" prefix="spring" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib tagdir="/WEB-INF/tags" prefix="t" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<t:base>

<jsp:attribute name="title">Editar usuario</jsp:attribute>

<jsp:body>

<ul class="breadcrumb">
  <li><a href="<spring:url value="/" />">Inicio</a></li>
  <li><a href="<spring:url value="/usuario/" />">Listado de usuarios</a></li>
  <li class="active">Editar usuario</li>
</ul>


<spring:url var="urlUsuarioForm" value="/usuario/editar/${usuario.id}" />
<c:set var="titleUsuarioForm" value="Editar usuario" />
<c:set var="btnUsuarioForm" value="Editar usuario" />

<%@ include file="/WEB-INF/partials/usuarioForm.jsp" %>

</jsp:body>

</t:base>