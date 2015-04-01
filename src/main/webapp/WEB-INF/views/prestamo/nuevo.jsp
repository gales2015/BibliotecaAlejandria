<%@ taglib uri="http://www.springframework.org/tags" prefix="spring" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib tagdir="/WEB-INF/tags" prefix="t" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<t:base>

<jsp:attribute name="title">Nuevo préstamo</jsp:attribute>


<jsp:attribute name="jsDocumentReady">
$("#libro").change(function(){
	var libro_isbn = $("#libro").val();
	console.log("ISBN: " + libro_isbn);
	
	if (libro_isbn == -1) {
		// Si no ha seleccionado ningún Libro, deshabilitamos los Ejemplares
		$("#ejemplar").prop('disabled', true);
		$("#ejemplar").val('-1');
	} else {
		// En caso contrario, obtenemos los ejemplares disponibles del Libro
		console.log("Iniciando AJAX...");
		$.getJSON('${urlEjemplaresLibro}', {
			isbn : libro_isbn
		}, function(data) {
			console.log("Resultado: " + data);
			var html = '<option value="-1">Selecciona un ejemplar</option>';
			var len = data.length;
			for ( var i = 0; i < len; i++) {
				html += '<option value="' + data[i] + '">ID '
						+ data[i] + '</option>';
			}
			$('#ejemplar').html(html);
			$("#ejemplar").prop('disabled', false);
		});
	}
});

// Al cargar la página, hacemos la primera comprobación
$("#libro").trigger("change");
</jsp:attribute>

<jsp:body>

<ul class="breadcrumb">
  <li><a href="<spring:url value="/" />">Inicio</a></li>
  <li><a href="<spring:url value="/prestamo/" />">Listado de préstamos</a></li>
  <li class="active">Nuevo préstamo</li>
</ul>


<spring:url var="urlPrestamoForm" value="/prestamo/nuevo"/>

<spring:url var="urlEjemplaresLibro" value="/libro/ejemplares-libres.json"/>

<div class="col-lg-12">
<form:form action="${urlPrestamoForm}" method="post" commandName="prestamo" class="form-horizontal">
  <fieldset>
    <legend>Nuevo préstamo</legend>
    <div class="form-group">
      <label for="usuario" class="col-lg-1 control-label">Usuario<span class="required" title="Campo obligatorio">*</span></label>
      <spring:bind path="usuario">
	      <div class="${status.error ? 'has-error' : ''}">
	      <div class="col-lg-6">
	      <form:select path="usuario" id="usuario" class="form-control">
				<form:option value="-1">Selecciona un usuario</form:option>
				<form:options items="${usuarios}" itemLabel="nombre" itemValue="id" />
			</form:select>
	      	</div>
	      	<form:errors path="usuario" cssClass="control-label control-label-left col-lg-5" element="label" />
	      </div>
      </spring:bind>
    </div>
    <div class="form-group">
      <label for="libro" class="col-lg-1 control-label">Libro<span class="required" title="Campo obligatorio">*</span></label>
	      <div class="">
	      <div class="col-lg-6">
	      <select id="libro" class="form-control">
			<option value="-1">Selecciona un libro</option>
	      	<c:forEach items="${libros}" var="libro">
               <option value="${libro.isbn}">${libro}</option>
            </c:forEach>
			</select>
	      	</div>
	      </div>
    </div>
    <div class="form-group">
      <label for="ejemplar" class="col-lg-1 control-label">Ejemplar<span class="required" title="Campo obligatorio">*</span></label>
      <spring:bind path="ejemplar">
	      <div class="${status.error ? 'has-error' : ''}">
	      <div class="col-lg-6">
	      <form:select path="ejemplar" id="ejemplar" class="form-control">
				<form:option value="-1">Selecciona un ejemplar</form:option>
				<form:options items="${ejemplares}" itemLabel="libro" itemValue="id" />
			</form:select>
	      	</div>
	      	<form:errors path="ejemplar" cssClass="control-label control-label-left col-lg-5" element="label" />
	      </div>
      </spring:bind>
    </div>
    <div class="form-group">
      <div class="col-lg-10 col-lg-offset-1">
        <button type="reset" class="btn btn-default">Cancelar</button>
        <button type="submit" class="btn btn-primary">Crear préstamo</button>
      </div>
    </div>
  </fieldset>
</form:form>
</div>

</jsp:body>

</t:base>