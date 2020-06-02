<jsp:include page="header.jsp" flush="true">
	<jsp:param name="tituloDePagina" value="Login" />
</jsp:include>


<body class="bg-gradient-success">


  <div class="container py-3">

    <!-- Outer Row -->
    <div class="row justify-content-center">

      
        <div class="col-xl-10 col-lg-12 col-md-9 mt-5 text-white text-center" >
          <h1 class="display-2"><i class="fas fa-kiwi-bird "></i> &nbsp;ING INDIRECT</h1>

        <div class="card o-hidden border-0 shadow-lg my-5" id="login-container" >         
          <div class="card-body p-0">
            <!-- Nested Row within Card Body -->
            <div class="row">
              <div class="col-lg-6 d-none d-lg-block" id="loginImage"></div>
              <div class="col-lg-6">
                <div class="p-5">
                  <div class="text-center">
                    <h1 class="h4 text-gray-900 mb-4">Bienvenido!</h1>
                  </div>
                  <form id="formLogin">
                    <div class="form-group">
                      <input type="email" class="form-control form-control-user checkValidity" id="usuOrEmail" aria-describedby="emailHelp"
                       placeholder="Usuario o correo electr&oacute;nico" validity="email">
                       <div class="valid-feedback">Correcto</div>
                      <div class="invalid-feedback">Debes introducir un email v&aacute;lido antes de intentar entrar</div>                                      
                   </div>
                    <div class="form-group">
                      <input type="password" class="form-control form-control-user checkValidity" id="pass" 
                      placeholder="Contrase&ntilde;a" validity="noEmpty">
                   	   <div class="valid-feedback">Correcto</div>
                      <div class="invalid-feedback">Debes introducir una contrase&ntilde;a antes de intentar entrar</div>                                      
                    </div>                    
                    <button type="button" class="btn btn-primary btn-user btn-block" id="btnEntrar">
                      <span id="spinner"></span>
                      Entrar
                    </button> 
                  </form>
                  <hr>
                  <div class="text-center">
                    <a class="small" href="forgot-password.html">Has olvidado tu contrase&ntilde;a?</a>
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

 


  <!-- Bootstrap core JavaScript-->
  <script src="vendor/jquery/jquery.min.js"></script>
  <script src="vendor/bootstrap/js/bootstrap.bundle.min.js"></script>

  <!-- Core plugin JavaScript-->
  <script src="vendor/jquery-easing/jquery.easing.min.js"></script>

  <!-- Custom scripts for all pages-->
  <script src="js/sb-admin-2.min.js"></script>

  
  <!--JQuery utils de Rafa-->
  <script src="js/jquery-utils.js"></script>

  
  <!-- Script de Rafael para mandar peticiones json-->
  <script>
    $(document).ready(function () {
    	
    	var loginImages = ["1.jpg", "2.jpg", "3.jpg", "4.jpg", "5.jpg", "6.jpg", "7.jpg", "8.jpg", "9.jpg", "camaleon2.jpg",  "11.jpg"  ]
        $("#loginImage").css("background", "url('../img/" + loginImages[Math.floor(Math.random() * loginImages.length)] + "')");
        $("#loginImage").css("background-position", "center");
        $("#loginImage").css("background-size", "cover");
  
      // Acci√≥n a realizar cuando el usuario pulse el bot√≥n "#btnEntrar". Recordar que dicho bot√≥n tiene que incluir el type="button". Si no se pusiera
      // ese type el bot√≥n se comportar√≠a por defecto como type="submit" y enviar√≠a el formulario, cosa que no es lo que queremos.
      $("#btnEntrar").click(function(){
    	  
    	  // Comienzo la acci√≥n validando el formulario antes de su env√≠o
          if (!checkFormValidity($("#formLogin"))) { // Compruebo la validaci√≥n con una funci√≥n que podemos encontrar en el fichero jquery-utils.js
            showAlertMessage($("#login-container"), ALERT_DANGER, "Datos no v&aacute;lidos", "Has introducido al menos un dato no v&aacute;lido"); // Muestro una alerta
          }
          else { // En este caso hemos comprobado que el formulario es v·lido, llamamos al servidor
            insertWaitingIcon($("#spinner")); // Hago que un componente incorpore el sÌmbolo de carga
        
        // Construyo un objeto con los datos del formulario introducidos por el usuario
        var obj = { usuOrEmail: $("#usuOrEmail").val(), 
                    pass: $("#pass").val() };
  
        // EnvÌo la peticiÛn JSON
        sendJsonRequest("/v04/LoginUsuario", // URL a la que envÌo la peticiÛn
          JSON.stringify(obj),  // Datos JSON que envÌo al Servlet
          function (data, status) {  // FunciÛn que se ejecutar· cuando la peticiÛn se realice con Èxito
            if (data == null) { // Si el servicio devuelve "null" significa que no se ha encontrado un usuario como el que se quiere autenticar
              showAlertMessage($("#login-container"), ALERT_DANGER, "Error", "El usuario y/o contraseÒa introducidos no son v·lidos");
            }
            else { // AutenticaciÛn correcta, redirigimos al portal de la aplicaciÛn.
              window.location.href = "portal.jsp";
            }
            removeWaitingIcon($("#spinner"));  // Detengo la animaci√≥n de "Waiting"
          }, 
          function (resumenError) {  // Funci√≥n que se ejecutar√° si la petici√≥n web sale mal
            showAlertMessage($("#login-container"), ALERT_DANGER, "Error inesperado: ", resumenError); // Muestro una alerta
            removeWaitingIcon($("#spinner")); // Detengo la animaci√≥n de "Waiting"
          });
          }
      });
    });
  
  </script>




  <script>
    $(document).ready(function () {
   
       $("#logar").click(function(){
        sendJsonRequest ("/Bankonter/LoginUsuario", 
        '{"usuarioOMail":"rafa@rafa.com", "pass":"1234"}',
        function (data, status) {
          alert(data);
        },
        function (strError) {
          alert("Error " + strError);
        });
       });
    });
  </script>
</body>

</html>