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
		// Obtengo el usuario guardado en la sesiÃ³n
		Usuario u = null;
		ObjectMapper mapper = new ObjectMapper();
		HashMap<String, Object> dto = new HashMap<String, Object>(); // "dto" significa Data Transfer Object
		
		try {
			// Obtengo los datos recibidos en el JSON
			JsonNode rootNode = mapper.readTree(request.getInputStream());
			boolean miniatura = false; // comienzo pensando que no me piden la miniatura
			
			try {
				// Intento obtener el valor de si el json incorpora o no el valor del campo "miniatura".
				miniatura = Boolean.parseBoolean(rootNode.path("miniatura").asText());
			} catch (Exception e) {
				// si ocurre una execpción no se envía la miniatura
			}
			
			// El usuario guardado en sesión puede no tener la imagen u otros datos, primero recuperaré el usuario
			// de la sesión y después lo buscaré en la BBDD.
			u = (Usuario) request.getSession().getAttribute(LoginUsuario.ID_USER_SESSION);
			
			if (u != null) { // Si existe un usuario guardado en la sesión, obtengo sus datos
				dto.put("userName", u.getNombreUsuario()); // Relleno el dto para construir el json de respuesta al servlet
				dto.put("image", (miniatura)? u.getImagen().getMiniatura() : u.getImagen().getContenido());
				dto.put("email", u.getEmail());
				dto.put("nombreComp", u.getNombreComp());
				dto.put("apellidos", u.getApellidos());
				dto.put("dniNie",u.getDniNie());
				dto.put("direccion", u.getDireccion());
				dto.put("localidad", u.getLocalidad());
				dto.put("telefono", u.getTelefono());
			} 

			
		} catch (Exception ex) {
			// Ocurrió una excepción en el acceso a datos o un error que nos impide acceder a los campos del JSON.
			logger.error("Excepción al localizar los datos del usuario en sesión.", ex);
		}
		
		response.setContentType("application/json;charset=UTF-8");	// Codificación UTF-8 de los datos JSON que devuelve el Servlet
		// Creo el JSON de salida y lo devuelvo al cliente
		response.getWriter().println(mapper.writeValueAsString(dto));

	}

}
