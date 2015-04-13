<%@ taglib uri="http://www.springframework.org/tags" prefix="spring" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>

<div class="col-lg-12">
<form:form action="${urlUsuarioForm}" method="post" commandName="usuario" class="form-horizontal">
  <fieldset>
    <legend>${titleUsuarioForm}</legend>
    <div class="form-group">
      <label for="nombre" class="col-lg-1 control-label">Nombre<span class="required" title="Campo obligatorio">*</span></label>
      <spring:bind path="nombre">
	      <div class="${status.error ? 'has-error' : ''}">
	      <div class="col-lg-6">
	        <form:input type="text" class="form-control" path="nombre" id="nombre" placeholder="Nombre" />
	      	</div>
	      	<form:errors path="nombre" cssClass="control-label control-label-left col-lg-5" element="label" />
	      </div>
      </spring:bind>
    </div>
    <div class="form-group">
      <label for="apellidos" class="col-lg-1 control-label">Apellidos<span class="required" title="Campo obligatorio">*</span></label>
      <spring:bind path="apellidos">
	      <div class="${status.error ? 'has-error' : ''}">
	      <div class="col-lg-6">
        	<form:input type="text" class="form-control" path="apellidos" id="apellidos" placeholder="Apellidos" />
      	  </div>
	      <form:errors path="apellidos" cssClass="control-label control-label-left col-lg-5" element="label" />
	    </div>
      </spring:bind>
    </div>
    <div class="form-group">
      <label for="dni" class="col-lg-1 control-label">DNI<span class="required" title="Campo obligatorio">*</span></label>
      <spring:bind path="dni">
	      <div class="${status.error ? 'has-error' : ''}">
	      <div class="col-lg-6">
        	<form:input type="text" class="form-control" path="dni" id="dni" placeholder="DNI" />
      	  </div>
	      <form:errors path="dni" cssClass="control-label control-label-left col-lg-5" element="label" />
	    </div>
      </spring:bind>
    </div>
    <div class="form-group">
      <label for="email" class="col-lg-1 control-label">Email<span class="required" title="Campo obligatorio">*</span></label>
      <spring:bind path="email">
	      <div class="${status.error ? 'has-error' : ''}">
	      <div class="col-lg-6">
        	<form:input type="text" class="form-control" path="email" id="email" placeholder="Email" />
      	  </div>
	      <form:errors path="email" cssClass="control-label control-label-left col-lg-5" element="label" />
	    </div>
      </spring:bind>
    </div>
    <div class="form-group">
      <label for="email" class="col-lg-1 control-label">Dirección<span class="required" title="Campo obligatorio">*</span></label>
      <spring:bind path="direccion">
	      <div class="${status.error ? 'has-error' : ''}">
	      <div class="col-lg-6">
        	<form:input type="text" class="form-control" path="direccion" id="direccion" placeholder="Dirección" />
      	  </div>
	      <form:errors path="direccion" cssClass="control-label control-label-left col-lg-5" element="label" />
	    </div>
      </spring:bind>
    </div>
    <div class="form-group">
      <div class="col-lg-10 col-lg-offset-1">
        <button type="reset" class="btn btn-default">Cancelar</button>
        <button type="submit" class="btn btn-primary">${btnUsuarioForm}</button>
      </div>
    </div>
  </fieldset>
</form:form>
</div>