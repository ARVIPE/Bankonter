package servlets;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

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
import model.Mensajeria;
import model.MensajeriaControlador;
import model.Usuario;
import model.UsuarioControlador;

/**
 * Servlet implementation class GetMensajesUsuario
 */
@WebServlet("/GetMensajesUsuario")
public class GetMensajesUsuario extends SuperTipoServlet {
	private static final long serialVersionUID = 1L;
	private static final Logger logger = LogManager.getLogger(GetMensajesUsuario.class);
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public GetMensajesUsuario() {
        super();
        // TODO Auto-generated constructor stub
    }


	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// Obtengo el usuario gurdado en la sesión
		Usuario u = null;
		ObjectMapper mapper = new ObjectMapper();
		HashMap<String, Object> dto = new HashMap<String, Object>();
		
		try {
			// Obtengo los datos recibidos en el JSON
			JsonNode rootNode = mapper.readTree(request.getInputStream());
			
			// Recupero al usuario guardado en la sesión y luego lo busco en la BBDD
			u = (Usuario) request.getSession().getAttribute(LoginUsuario.ID_USER_SESSION);
			
			List<Mensajeria> mensajes = MensajeriaControlador.getControlador().findAllMensajes();
			List<HashMap<String, Object>> mensajesLeidos = new ArrayList<HashMap<String,Object>>();
			//List<HashMap<String, Object>> mensajesNoLeidos = new ArrayList<HashMap<String,Object>>();
			
			if (u != null) {
				// si existe un usuario guardado en la sesión, obtengo sus datos
				u = UsuarioControlador.getControlador().find(u.getId());
				
				for (Mensajeria m : mensajes) {
					HashMap<String, Object> hm = new HashMap<String, Object>();
					hm.put("id", m.getId());
					hm.put("email_emisor", m.getEmailEmisor());
					hm.put("asunto", m.getAsunto());
					hm.put("mensaje", m.getMensaje());
					hm.put("email_receptor", m.getEmailReceptor());
					hm.put("id_emisor_nombre", m.getUsuario1().getNombreUsuario());
					hm.put("id_emisor_apellido", m.getUsuario1().getApellidos());
					hm.put("id_receptor_nombre", m.getUsuario2().getNombreUsuario());
					hm.put("id_receptor_apellido", m.getUsuario2().getApellidos());
					
//					if(m.getLeido() == true) {
//						mensajesLeidos.add(hm);
//					}else {
//						mensajesNoLeidos.add(hm);
//					}
					mensajesLeidos.add(hm);
				}
				
				// Relleno el dto para construir el json de respuesta al servlet
//				dto.put("userName", u.getNombreUsuario());
//				dto.put("mensajes_leidos", mensajesLeidos);
//				dto.put("mensajes_No_Leidos", mensajesNoLeidos);
				dto.put("idMensajes", mensajesLeidos);
			}
			
			
		} catch (Exception ex) {
			//puede ocurrir un fallo al acceder a los datos y no podemos acceder a los datos del JSON
			logger.error("Error al localizar los datos del usuario en la sesiï¿½n", ex);
		}
		
		// Codificaciï¿½n UTF-8 de los datos JSON que devuelve el Servlet
		response.setContentType("application/json;charset=UTF-8");	
				
		// Creo el JSON de salida y lo devuelvo al cliente
		response.getWriter().println(mapper.writeValueAsString(dto));
	}

}
