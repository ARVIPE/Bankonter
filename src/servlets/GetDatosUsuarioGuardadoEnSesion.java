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

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

import Utils.SuperTipoServlet;
import model.Usuario;

/**
 * Comprueba que exista un usuario en la sesionn de trabajo, una vez que lo obtiene devuelve la informaci�n
 * de este usuario, incorporando la imagen del mismo.
 * En caso de que se desee obtener una miniatura de la imagen, se recibir� un JSON del tipo
 *    {"miniatura":true}
 * En caso de que no se reciba el json, o que este tenga el valor "false", se devolverá la imagen normal, no
 * la miniatura.
 * Servlet implementation class GetDatosUsuarioGuardadoEnSesion
 */
@WebServlet("/GetDatosUsuarioGuardadoEnSesion")
public class GetDatosUsuarioGuardadoEnSesion extends SuperTipoServlet {
	private static final long serialVersionUID = 1L;
	private static final Logger logger = LogManager.getLogger(GetDatosUsuarioGuardadoEnSesion.class);
       
	
    /**
     * @see HttpServlet#HttpServlet()
     */
    public GetDatosUsuarioGuardadoEnSesion() {
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
			boolean miniatura = false; // Comienzo pensando que no me est�n pidiendo la miniatura
			boolean contenido = false;
			
			try {
				// Intento obtener el valor de si el json incorpora o no el valor del campo "miniatura".
				miniatura = Boolean.parseBoolean(rootNode.path("miniatura").asText());
				contenido = Boolean.parseBoolean(rootNode.path("contenido").asText());
				
			}
			catch (Exception ex) {
				// Si ocurre una excepci�n no importa, no se enviar� la miniatura
			}

			// El usuario guardado en sesi�n puede no tener la imagen u otros datos, primero recuperar� el usuario
			// de la sesi�n y despu�s lo buscar� en la BBDD.
			u = (Usuario) request.getSession().getAttribute(LoginUsuario.ID_USER_SESSION);
			
			if (u != null) { // Si existe un usuario guardado en la sesi�n, obtengo sus datos
				dto.put("userName", u.getNombreUsuario()); // Relleno el dto para construir el json de respuesta al servlet
				dto.put("image", (miniatura)? u.getImagen().getMiniatura() : u.getImagen().getContenido());
				//dto.put("image", u.getImagen().getContenido());
			} 
		}
		catch (Exception ex) {
			// Ocurri� una excepci�n en el acceso a datos o un error que nos impide acceder a los campos del JSON.
			logger.error("Excepci�n al localizar los datos del usuario en sesi�n.", ex);
		}

		response.setContentType("application/json;charset=UTF-8");	// Codificaci�n UTF-8 de los datos JSON que devuelve el Servlet
		// Creo el JSON de salida y lo devuelvo al cliente
		response.getWriter().println(mapper.writeValueAsString(dto));
	}

}
