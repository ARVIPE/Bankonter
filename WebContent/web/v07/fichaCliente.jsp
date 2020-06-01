<%@ page 
	import="java.util.List, 
	java.util.HashMap,
	java.util.Date,
	java.text.SimpleDateFormat,
	Utils.RequestUtils,
	model.Usuario,
	model.UsuarioControlador"%>

<jsp:include page="cabeceraFicha.jsp" flush="true">
	<jsp:param name="tituloDePagina" value="Ficha de cliente" />
</jsp:include>

<%
SimpleDateFormat sdfFechaNac = new SimpleDateFormat("dd/MM/yyyy");
// Obtengo un HashMap con todos los parámetros del request, sea del tipo que sea;
HashMap<String, Object> hashMap = RequestUtils.requestToHashMap(request);

// Para plasmar la info del Usuario usaremos un parámetro que debe llevar al Servlet, esa búsqueda la haremos con el "idUsuario" y así
// obtenemos la información del usuario y sacamos los datos por pantalla en la ficha

Usuario usuario = null;

// Obtengo el usuario a editar, en el caso de que el cliente exista se cargarán los datos, y si no, quedará a null

try{
	int idUsuario = RequestUtils.getIntParameterFromHashMap(hashMap, "idUsuario"); // Necesito obtener el id del usuario que se va  a editar
	// si el usuario es nuevo, el valor del id será 0
	if(idUsuario != 0){
		usuario = (Usuario) UsuarioControlador.getControlador().find(idUsuario);
	}
}catch (Exception e){
	e.printStackTrace();
}

// Iniciamos la carga de los valores
if (usuario == null){
	usuario = new Usuario();
}
if (usuario.getDniNie() == null) usuario.setDniNie("");
if (usuario.getNombreComp() == null) usuario.setNombreComp("");
if (usuario.getApellidos() == null) usuario.setApellidos("");
if (usuario.getFechaNac() == null) usuario.setFechaNac(null);
if (usuario.getDireccion() == null) usuario.setDireccion("");
if (usuario.getLocalidad() == null) usuario.setLocalidad("");
if (usuario.getTelefono() == null) usuario.setTelefono("");
if (usuario.getEmail() == null) usuario.setEmail("");

//En función de los parámetros de entrada del Servlet, debemos determinar qué se hará según 3 acciones:
//- "eliminar" sabemos que es esta opción porque recibe un parámetro con el nombre "elimiar" en el request
//- "guardar" sabemos que es esta opción porque recibe un parámetro con el nombre "guardar" en el request
//- Sin acción. En este caso, sólo se quiere editar

//Variable con mensaje de información al usuario sobre alguna acción requerido
String mensajeAlUsuario = "";

//Primera acción posible: eliminar
if(RequestUtils.getStringParameterFromHashMap(hashMap, "eliminar") != null){
	// Intento eliminar un registro, si se ejecuta volvemos a la pantalla global
	try{
		UsuarioControlador.getControlador().remove(usuario);
		response.sendRedirect(request.getContextPath() + "login.jsp");
	}catch (Exception ex){
		mensajeAlUsuario = "Error! Imposible eliminar. Es posible que existan restricciones";
	}
}

// Segunda acción posible: guardar
if (RequestUtils.getStringParameterFromHashMap(hashMap, "guardar") != null){
	// Obtengo los datos del usuario y se guardan en la bbdd
	try{
		usuario.setDniNie(RequestUtils.getStringParameterFromHashMap(hashMap, "dniNie"));
		usuario.setNombreComp(RequestUtils.getStringParameterFromHashMap(hashMap, "nombreCom"));
		usuario.setApellidos(RequestUtils.getStringParameterFromHashMap(hashMap, "apellidos"));
		try{
			usuario.setFechaNac(sdfFechaNac.parse(RequestUtils.getStringParameterFromHashMap(hashMap, "fechaNac")));
		}catch(Exception e){
			e.printStackTrace();
			
		}
		usuario.setDireccion(RequestUtils.getStringParameterFromHashMap(hashMap, "direccion"));
		usuario.setLocalidad(RequestUtils.getStringParameterFromHashMap(hashMap, "localidad"));
		usuario.setTelefono(RequestUtils.getStringParameterFromHashMap(hashMap, "telefono"));
		usuario.setEmail(RequestUtils.getStringParameterFromHashMap(hashMap, "email"));
		
		// Guardamos el objeto de tipo Usuario
		UsuarioControlador.getControlador().save(usuario);
		mensajeAlUsuario = "Guardado correctamente";
	}catch (Exception e){
		throw new ServletException(e);
	}
}
//Ahora muestro la pantalla de respuesta al usuario
%>

<div class="container py-3">
	<%
		String tipoAlerta = "alert-success";
	if (mensajeAlUsuario != null && mensajeAlUsuario != ""){
		if(mensajeAlUsuario.startsWith("ERROR")){
			tipoAlerta = "alert-danger";
		}
	%>
	<div class="alert <%=tipoAlerta %> alert-dismissible fade show">
		<button type="button" class="close" data-dismiss="alert">&times;</button>
		<%=mensajeAlUsuario %>
	</div>
	<%
	}
	%>
	<div class="row">
		<div class="mx-auto col-sm-6">
			<!-- for user info -->
			<div class="card">
				<div class="card-header">
					<h4 class="mb-0">Ficha de cliente</h4>
				</div>
				<div class="card-body">
					<a href="portal.jsp" class="btn btn-info btn-sm" role="button">Volver al portal</a>
					<form id="form1" name="form1" method="post" 
					action="fichaCliente.jsp" enctype="multipart/form-data"
					class="form" role="form" autocomplete="off">
					 <p />
					 <input type="hidden" name="idUsuario" value="<%=usuario.getId()%>"/>
					 <div class="form-group row">
					 	<label class="col-lg-3 col-form-label form-control-label" for="dniNie">Dni o Nie:</label>
					 	<div class="col-lg-9">
					 		<input name="dniNie" class="form-control" type="text" 
					 		id="dniNie" value="<%=usuario.getDniNie() %>" />
					 	</div>
					 </div>
					 <div class="form-group row">
					 	<label class="col-lg-3 col-form-label form-control-label" for="nombreCom">Nombre:</label>
					 	<div class="col-lg-9">
					 		<input name="nombreCom" class="form-control" type="text" id="nombreCom" value="<%=usuario.getNombreComp()%>" />
					 	</div>
					 </div>
	    			  <div class="form-group row">
					 	<label class="col-lg-3 col-form-label form-control-label" for="apellidos">Apellidos:</label>
					 	<div class="col-lg-9">
					 		<input name="apellidos" class="form-control" type="text" id="apellidos" value="<%=usuario.getApellidos()%>" />
					 	</div>
					 </div>
 					  <div class="form-group row">
					 	<label class="col-lg-3 col-form-label form-control-label" for="fechaNac">Fecha de Nacimiento:</label>
					 	<div class="col-lg-9">
					 		<input name="fechaNac" class="form-control" type="text"
					 		 id="fechaNac" value="<%= ((usuario.getFechaNac() != null)? sdfFechaNac.format(usuario.getFechaNac()) : "") %>" />
					 	</div>
					 </div>
					  <div class="form-group row">
					 	<label class="col-lg-3 col-form-label form-control-label" for="direccion">Dirección:</label>
					 	<div class="col-lg-9">
					 		<input name="direccion" class="form-control" type="text" id="direccion" value="<%=usuario.getDireccion()%>" />
					 	</div>
					 </div>
					  <div class="form-group row">
					 	<label class="col-lg-3 col-form-label form-control-label" for="localidad">Localidad:</label>
					 	<div class="col-lg-9">
					 		<input name="localidad" class="form-control" type="text" id="localidad" value="<%=usuario.getLocalidad()%>" />
					 	</div>
					 </div>
					  <div class="form-group row">
					 	<label class="col-lg-3 col-form-label form-control-label" for="telefono">Teléfono:</label>
					 	<div class="col-lg-9">
					 		<input name="telefono" class="form-control" type="text" id="telefono" value="<%=usuario.getTelefono()%>" />
					 	</div>
					 </div>
					  <div class="form-group row">
					 	<label class="col-lg-3 col-form-label form-control-label" for="mail">Email:</label>
					 	<div class="col-lg-9">
					 		<input name="email" class="form-control" type="text" id="email" value="<%=usuario.getEmail()%>" />
					 	</div>
					 </div>
 					 <div class="form-group row">
							<div class="col-lg-9">
								<input type="submit" name="guardar" class="btn btn-info" value="Guardar" /> 
								<input type="submit" name="eliminar" class="btn btn-secondary" value="Eliminar" />
							</div>
						</div>
					</form>
				</div>
				
			</div>
		</div>
	</div>
</div>

</body>
</html>
