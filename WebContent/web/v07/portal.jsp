<jsp:include page="header.jsp" flush="true">
	<jsp:param name="tituloDePagina" value="portal" />
</jsp:include>

<%
	// Compruebo que la sesi�n tenga un usuario autenticado, en caso contrario redirijo al login.jsp
	if (request.getSession().getAttribute("USR") == null) {
		response.sendRedirect("login.jsp");
	}
%>

<script>
  $(document).ready(function() {
    // Realizo la carga de las diferentes partes de la página del portal
    $("#datosUsuarioEnSesion").load("infoUsuarioEnPortal.html"); // Cargo la esquina superior izquierda, con los datos del usuario y su foto
    $("#pageContent").load("situacionGlobal.html"); // Cargo la situación global, un resumen de cuentas, préstamos y tarjetas, de momento estático
})

</script>


<body id="page-top">

  <!-- Page Wrapper -->
  <div id="wrapper">

    <!-- Sidebar -->
    <ul class="navbar-nav bg-gradient-primary sidebar sidebar-dark accordion" id="accordionSidebar">

      <!-- Sidebar - Brand -->
      <a class="sidebar-brand d-flex align-items-center justify-content-center" href="portal.jsp">
        <div class="sidebar-brand-icon rotate-n-15">
          <i class="fas fa-kiwi-bird"></i>
        </div>
        <div class="sidebar-brand-text mx-3">Bankonter</div>
      </a>

      <!-- Divider -->
      <hr class="sidebar-divider my-0">

      <!-- Heading -->
      <div class="sidebar-heading">
        Tus enlaces
      </div>

      <!-- Nav Item - Charts -->
      <li class="nav-item">
  	      <span role="button" class="nav-link bankonterNavBarLink" toLoadPageContent="situacionGlobal.html">
          <i class="fas fa-fw fa-chart-area"></i>
          <span>Situaci&oacute;n global</span></a>
      </li>

      <!-- Nav Item - Tables -->
      <li class="nav-item">
         <span role="button" class="nav-link bankonterNavBarLink" toLoadPageContent="transferencia.html">       <a class="nav-link" href="portal.jsp">
          <i class="fas fa-fw fa-table"></i>
          <span>Transferencias</span></a>
      </li>  

      <!-- Divider -->
      <hr class="sidebar-divider d-none d-md-block">

      <!-- Sidebar Toggler (Sidebar) -->
      <div class="text-center d-none d-md-inline">
        <button class="rounded-circle border-0" id="sidebarToggle"></button>
      </div>

    </ul>
    <!-- End of Sidebar -->

    <!-- Content Wrapper -->
    <div id="content-wrapper" class="d-flex flex-column">

      <!-- Main Content -->
      <div id="content">

        <!-- Topbar -->
        <nav class="navbar navbar-expand navbar-light bg-white topbar mb-4 static-top shadow">

          <!-- Sidebar Toggle (Topbar) -->
          <button id="sidebarToggleTop" class="btn btn-link d-md-none rounded-circle mr-3">
            <i class="fa fa-bars"></i>
          </button>

          <!-- Topbar Navbar -->
          <ul class="navbar-nav ml-auto">

 
            <!-- Nav Item - Messages -->
            <li class="nav-item dropdown no-arrow mx-1">
              <a class="nav-link dropdown-toggle" href="#" id="messagesDropdown" role="button" data-toggle="dropdown" aria-haspopup="true" aria-expanded="false">
                <i class="fas fa-envelope fa-fw"></i>
                <!-- Counter - Messages -->
                <span class="badge badge-danger badge-counter">0</span>
              </a>
              <!-- Dropdown - Messages -->
              <div class="dropdown-list dropdown-menu dropdown-menu-right shadow animated--grow-in" aria-labelledby="messagesDropdown">
                <h6 class="dropdown-header">
                  Mensajer&iacute;a
                </h6>
                <a class="dropdown-item text-center small text-gray-500" href="#">No hay mensajes nuevos</a>
              </div>
            </li>

            <div class="topbar-divider d-none d-sm-block"></div>

            <!-- Nav Item - User Information -->
            <li class="nav-item dropdown no-arrow" id="datosUsuarioEnSesion">
            </li>

          </ul>

        </nav>
        <!-- End of Topbar -->

        <!-- Begin Page Content -->
        <div class="container-fluid" id="pageContent">
        </div>
        <!-- /.container-fluid -->

      </div>
      <!-- End of Main Content -->

      <!-- Footer -->
      <footer class="sticky-footer bg-white">
        <div class="container my-auto">
          <div class="copyright text-center my-auto">
            <span>Copyright &copy; Bankonter 2020</span>
          </div>
        </div>
      </footer>
      <!-- End of Footer -->

    </div>
    <!-- End of Content Wrapper -->

  </div>
  <!-- End of Page Wrapper -->  

  <!-- Scroll to Top Button-->
  <a class="scroll-to-top rounded" href="#page-top">
    <i class="fas fa-angle-up"></i>
  </a>

  <!-- Logout Modal-->
  <div class="modal fade" id="logoutModal" tabindex="-1" role="dialog" aria-labelledby="exampleModalLabel" aria-hidden="true">
    <div class="modal-dialog" role="document">
      <div class="modal-content">
        <div class="modal-header">
          <h5 class="modal-title" id="exampleModalLabel">&iquest;Realmente desea salir?</h5>
          <button class="close" type="button" data-dismiss="modal" aria-label="Close">
            <span aria-hidden="true">&times;</span>
          </button>
        </div>
        <div class="modal-body">Selecciona "Deseo salir" si realmente quieres abandonar la sesi&oacute;n</div>
        <div class="modal-footer">
          <button class="btn btn-secondary" type="button" data-dismiss="modal">Quedarme</button>
          <a class="btn btn-primary" href="login.jsp">Deseo salir</a>
        </div>
      </div>
    </div>
  </div>


  <script src="../js/sb-admin-2.js"></script>
 

</body>

</html>
