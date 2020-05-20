package servlets;

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
       
	public LoginUsuario () {
		super();
	}
	

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		ObjectMapper mapper = new ObjectMapper();
		JsonNode rootNode = mapper.readTree(request.getInputStream());
		String userOrEmail = rootNode.path("usuarioOMail").asText();
		String password = rootNode.path("pass").asText();
		
		System.out.println("Usuario: " + userOrEmail + " - Password: " + password);
		
		Usuario u = UsuarioControlador.getControlador().find(1);
		
		// Creo el JSON de salida y lo devuelvo al cliente
		response.getWriter().println(mapper.writeValueAsString(u));

	}

}
