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

%>

</body>
</html>
