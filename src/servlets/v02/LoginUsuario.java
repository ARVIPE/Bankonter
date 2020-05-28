package servlets.v02;

import Utils.SuperTipoServlet;
import model.Usuario;
import model.UsuarioControlador;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

/**
 * Servlet implementation class AutenticarUsuario
 */
@WebServlet("/LoginUsuario")
public class LoginUsuario extends HttpServlet {
	private static final long serialVersionUID = 1L;
     
	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public LoginUsuario () {
		super();
	}
	

	/**
	 * Obtengo un JSON tipo  
	 *    {"usuOrEmail": xxxxx, "pass": xxxxx}
	 * Hago una b�squeda de ese usuario en la BBDD, si lo encuentro lo devuelvo en formato JSON
	 * Si el usuario no se encuentra se debe responder de una manera adecuada
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		Usuario u= null;
		String userOrEmail = null;
		String password = null;
		
		try {
			ObjectMapper mapper = new ObjectMapper();
			JsonNode rootNode = mapper.readTree(request.getInputStream());
		    userOrEmail = rootNode.path("usuOrEmail").asText();
			password = rootNode.path("pass").asText();
			
			// Busco al usuario en la BBDD
			u = UsuarioControlador.getControlador().findByPasswordAndUsernameOrMail(userOrEmail, userOrEmail, password);
			// Si no encuntro al usuario, informo
			if (u == null) {
				System.out.println("No se puede localizar al usuario " + userOrEmail + " con password " + password);
			}
			// Si encuentro al usuario establezco su imagen a null, porque en este caso no quiero que dicha imagen viaje en el JSON de salida
			else {
				u.setImagen(null);
			}
			
		}
		catch (Exception ex){
			//Ocurrio una excepción en el acceso a datos o un error que no nos impide acceder a los campos del JSON
			ex.printStackTrace();
		}
		
		
		
		//System.out.println("Usuario: " + userOrEmail + " - Password: " + password);
		
		//Usuario u = UsuarioControlador.getControlador().find(1);
		
		// Creo el JSON de salida y lo devuelvo al cliente
		ObjectMapper mapper = new ObjectMapper();
		response.getWriter().println(mapper.writeValueAsString(u));

	}

}
