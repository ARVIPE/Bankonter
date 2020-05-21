<jsp:include page="header.jsp" flush="true">
	<jsp:param name="tituloDePagina" value="Login" />
</jsp:include>

<script>
<!-- Haremos la funci�n para enviar los datos del registro con una petici�n JSON 
COMPROBAR LA LINEA 27 CON LA DIRECCION DEL PORTAR A DONDE REDIRIGIMOS LA PETICI�N EN CASO DE EXITO
COMPROBAR EL BODY POR FAVOR-->


$(document).ready(function(){
	//Esta acci�n se realiza cuando el usuario pulse el bot�n '#btnEntrar'. El bot�n tiene un tiene inclido un type="button". 
	//Para evitar que se comporte como un bot�n por defecto que es de type="submit"y enviara el formulario, que no es lo que queremos
	$("#btnEntrar").click(function(){
		insertarWaitingIcon($("#spinner")); //un componente va a incorporar un s�mpbolo de carga
		
		// construyo un objeto con los datos del formulario introducidos por el usuario
		var obj = {usuOrEmai: $("#usurOrEmail").val(),
				pass: $("#pass").val()};
		
		// Mando la petci�n JSON
		sendRequest("/LoginUsuario", // URL a la que env�o la petici�n
				JSON.stringify(obj),  // Datos JSON que env�o al Servlet
				function (data, status) {  // Funci�n que se ejecutar� cuando la petici�n se realice con �xito
			          if (data == null) { // Si el servicio devuelve "null" significa que no se ha encontrado un usuario como el que se quiere autenticar
			              showAlertMessage($("#login-container"), ALERT_DANGER, "Error", "El usuario y/o password introducidos no son correctos");
			            }
			            else { // Autenticación correcta, redirigimos al portal de la aplicación.
			              window.location.href = "portal.jsp";
			            }
			            removeWaitingIcon($("#spinner"));  // Detengo la animación de "Waiting"
		},
		 function (resumenError) {  // Funci�n que se ejecutar� si la petici�n web sale mal
	          showAlertMessage($("#login-container"), ALERT_DANGER, "Error inesperado: ", resumenError); // Muestro una alerta
	          removeWaitingIcon($("#spinner")); // Detengo la animaci�n de "Waiting"
	        });
				
	});
});
</script>

<body class="bg-gradient-primary">

  <div class="container py-3" id="containerPrincipal">

    <!-- Outer Row -->
    <div class="row justify-content-center" >

      <div class="col-xl-10 col-lg-12 col-md-9 mt-5 text-white text-center">

        <h1 class="display-2"><i class="fas fa-piggy-bank"></i> Bankonter v.01</h1>

        <div class="card o-hidden border-0 shadow-lg my-5"  id="login-container">
          <div class="card-body p-0" >
            <!-- Nested Row within Card Body -->
            <div class="row">
              <div class="col-lg-6 d-none d-lg-block bg-login-image"></div>
              <div class="col-lg-6">
                <div class="p-5">
                  <div class="text-center">
                    <h1 class="h4 text-gray-900 mb-4">Bienvenido!</h1>
                  </div>
                  <form class="user">
                    <div class="form-group">
                      <input type="text" class="form-control form-control-user" id="usuOrEmail" aria-describedby="emailHelp" placeholder="Usuario o correo electr&oacute;nico">
                    </div>
                    <div class="form-group">
                      <input type="password" class="form-control form-control-user" id="pass" placeholder="Contrase&ntilde;a">
                    </div>
                    <button type="button" class="btn btn-primary btn-user btn-block" id="btnEntrar"><span id="spinner"></span> Entra!</button>
                    <hr>
                  </form>
                   <div class="text-center">
                    <a class="small" href="forgot-password.html">Olvidaste tu contrase&ntilde;a?</a>
                  </div>
                  <div class="text-center">
                    <a class="small" href="register.html">Crea una cuenta!</a>
                  </div>
                </div>
              </div>
            </div>
          </div>
        </div>

      </div>

    </div>

  </div>

</body>
</html>