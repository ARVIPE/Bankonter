<!DOCTYPE html>
<html lang='es'>

<head>
	<meta charset="utf-8">
	<% String tituloDePagina = request.getParameter("tituloDePagina"); %>
	<title><%= (tituloDePagina != null)? tituloDePagina : "Sin titulo" %></title>
	<meta http-equiv="X-UA-Compatible" content="IE=edge">
	<meta name="viewport" content="width=device-width, initial-scale=1, shrink-to-fit=no">
	<meta name="description" content="">
	<meta name="author" content="">
	
	<script	src="https://ajax.googleapis.com/ajax/libs/jquery/3.4.1/jquery.min.js"></script>
    <script	src="https://maxcdn.bootstrapcdn.com/bootstrap/4.0.0-beta.3/js/bootstrap.min.js"></script>
    <link rel="stylesheet"href="https://maxcdn.bootstrapcdn.com/bootstrap/4.0.0-beta.3/css/bootstrap.min.css"
    integrity="sha384-Zug+QiDoJOrZ5t4lssLdxGhVrurbmBWopoEl+M6BdEfwnCJZtKxi1KgxUyJq13dy"
    crossorigin="anonymous">
    <script srd="https://cdnjs.cloudflare.com/ajax/libs/jquery-easing/1.4.1/jquery.easing.min.js"></script>
    
    <!-- Comprobar cu�l es la direcci�n correcta -->
    <script src="../js/jquery-utils.js"></script>
    
    <!-- La siguiente l�nea es importante para que en cada versi�n de Bankonter se llame a los servlets de la misma versi�n.
    	Se est� llamando a una funci�n contenida dentro del fichero "../js/jquery-utils.js" y estableciendo un valor que, despu�s,
    	ser� utilizado por la funci�n sendJSonRequest. 
    	Este valor se debe cambiar en cada versi�n de Bankonter
     -->
     
     <!-- HAY QUE COMPROBAR LA RUTA CORRECTAR DE ESTE CONTEXPATH -->
    <script>
    	setServletsPrefix("<%=request.getContextPath() + "/v02" %>");
    </script>
    
    <!-- Custom fonts for this template-->
    <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/5.13.0/css/all.min.css" type="text/css">
    <link href="https://fonts.googleapis.com/css?family=Nunito:200,200i,300,300i,400,400i,600,600i,700,700i,800,800i,900,900i" rel="stylesheet">
    <link href="https://fonts.googleapis.com/css2?family=Lora:wght@600&display=swap" rel="stylesheet">


    <!-- Custom styles for this template-->
    <link href="../css/sb-admin-2.min.css" rel="stylesheet">
    <link href="../css/sb-admin-2.css" rel="stylesheet">
	
</head>
