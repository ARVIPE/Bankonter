package servlets;

import java.io.IOException;


import java.util.HashMap;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.apache.geronimo.mail.util.Base64;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

import Utils.SuperTipoServlet;
import model.Imagen;
import model.Usuario;
import model.UsuarioControlador;

/**
 * Servlet implementation class GetDatosUsuarioFicha
 */
@WebServlet("/GetDatosUsuarioFicha")
public class GetDatosUsuarioFicha extends SuperTipoServlet {
	private static final long serialVersionUID = 1L;
	private static final Logger logger = LogManager.getLogger(GetDatosUsuarioFicha.class);
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public GetDatosUsuarioFicha() {
        super();
    }



	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// Obtengo el usuario guardado en la sesión
		Usuario u = null;
		ObjectMapper mapper = new ObjectMapper();
		HashMap<String, Object> dto = new HashMap<String, Object>(); // "dto" significa Data Transfer Object
		
		try {
			// Obtengo los datos recibidos en el JSON
			JsonNode rootNode = mapper.readTree(request.getInputStream());
			boolean miniatura = false; // comienzo pensando que no me piden la miniatura
			boolean contenido = false;
			
			try {
				// Intento obtener el valor de si el json incorpora o no el valor del campo "miniatura".
				miniatura = Boolean.parseBoolean(rootNode.path("miniatura").asText());
				contenido = Boolean.parseBoolean(rootNode.path("contenido").asText());
			} catch (Exception e) {
				// si ocurre una execpci�n no se env�a la miniatura
			}
			
			// El usuario guardado en sesi�n puede no tener la imagen u otros datos, primero recuperar� el usuario
			// de la sesi�n y despu�s lo buscar� en la BBDD.
			u = (Usuario) request.getSession().getAttribute(LoginUsuario.ID_USER_SESSION);
			
			if (u != null) { // Si existe un usuario guardado en la sesi�n, obtengo sus datos
				dto.put("userName", u.getNombreUsuario()); // Relleno el dto para construir el json de respuesta al servlet
				dto.put("image", (miniatura)? u.getImagen().getMiniatura() : u.getImagen().getContenido());
				dto.put("email", u.getEmail());
				dto.put("password", u.getPassword());
				dto.put("nombreComp", u.getNombreComp());
				dto.put("apellidos", u.getApellidos());
				dto.put("dniNie",u.getDniNie());
				dto.put("direccion", u.getDireccion());
				dto.put("localidad", u.getLocalidad());
				dto.put("telefono", u.getTelefono());
			} 
			
			// Buscamos la acci�n a realizar
			String accion = null;
			if (rootNode.path("accion") != null) {
				accion = rootNode.path("accion").asText();
			}
			Imagen i = null;
			
			// Si el usuario existe y la acci�n es la de almacenar, obtenemos los datos del cliente por el JSON
			if (u != null && accion != null && accion.equals("almacenar")) {
				u.setNombreUsuario(rootNode.path("userName").asText());
				if (rootNode.path("imagen") != null) {
					String strImagen = rootNode.path("imagen").asText();
					if(strImagen.contains("base64,")) {
						String imageData = strImagen.split("base64,")[1];
						i.setContenido(Base64.decode(imageData));
						u.setImagen(i);
					}
				}
				u.setEmail(rootNode.path("email").asText());
				u.setPassword(rootNode.path("password").asText());
				u.setNombreComp(rootNode.path("nombreComp").asText());
				u.setApellidos(rootNode.path("apellidos").asText());
				u.setDniNie(rootNode.path("dniNie").asText());
				u.setDireccion(rootNode.path("direccion").asText());
				u.setLocalidad(rootNode.path("localidad").asText());
				u.setTelefono(rootNode.path("telefono").asText());
				
				UsuarioControlador.getControlador().save(u);
			}

			
		} catch (Exception ex) {
			// Ocurri� una excepci�n en el acceso a datos o un error que nos impide acceder a los campos del JSON.
			logger.error("Excepci�n al localizar los datos del usuario en sesi�n.", ex);
		}
		
		response.setContentType("application/json;charset=UTF-8");	// Codificaci�n UTF-8 de los datos JSON que devuelve el Servlet
		// Creo el JSON de salida y lo devuelvo al cliente
		response.getWriter().println(mapper.writeValueAsString(dto));

	}

}
