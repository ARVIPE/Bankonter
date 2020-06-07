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

import com.fasterxml.jackson.databind.ObjectMapper;

import Utils.SuperTipoServlet;
import model.Contrato;
import model.TipoContratoControlador;
import model.Tipocontrato;
import model.Usuario;
import model.UsuarioControlador;

/**
 * Servlet implementation class situacionGlobalUsuario
 */
@WebServlet("/situacionGlobalUsuario")
public class GetSituacionGlobalUsuario extends SuperTipoServlet {
	private static final long serialVersionUID = 1L;
	private static final Logger logger = LogManager.getLogger(GetSituacionGlobalUsuario.class);
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public GetSituacionGlobalUsuario() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doPost(request, response);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
// Obtengo el usuario guardado en la sesión
		Usuario u = null;
		ObjectMapper mapper = new ObjectMapper();
		HashMap<String, Object> dto = new HashMap<String, Object>();
		
		try {
			// En el servlet no necesito obtener los datos de entrada como antes, necesitamos los datos del usuario que está guardado en la sesión
			u = (Usuario) request.getSession().getAttribute(servlets.v04.LoginUsuario.ID_USER_SESSION);
			
			if (u != null) {
				// Si existe un usuario guardado en la sesión, obtenemos sus datos
				u = UsuarioControlador.getControlador().find(u.getId());
				
				// hay que acceder a las cuentas que tiene el usuario, para ello los obtendremos con arrays
				List<HashMap<String, Object>> cuentasCorrientes = new ArrayList<HashMap<String,Object>>();
				List<HashMap<String, Object>> tarjetasDebito = new ArrayList<HashMap<String,Object>>();
				List<HashMap<String, Object>> tarjetasCredito = new ArrayList<HashMap<String,Object>>();
				List<HashMap<String, Object>> prestamos = new ArrayList<HashMap<String,Object>>();
				
				for(Contrato c : u.getContratos()) {
					HashMap<String, Object> hm = new HashMap<String, Object>();
					hm.put("id", c.getId());
					hm.put("descriptor", c.getDescriptor());
					hm.put("balance", c.getSaldo());
					
					if (c.getTipocontrato().getId() == TipoContratoControlador.CUENTA_CORRIENTE.getId()) {
						cuentasCorrientes.add(hm);
					} 
					else if (c.getTipocontrato().getId() == TipoContratoControlador.TARJETA_DEBITO.getId()) {
						hm.put("limite", c.getLimite());
						tarjetasDebito.add(hm);
					}
					else if (c.getTipocontrato().getId() == TipoContratoControlador.TARJETA_CREDITO.getId()) {
						tarjetasCredito.add(hm);
					}
					else if (c.getTipocontrato().getId() == TipoContratoControlador.PRESTAMO.getId()) {
						prestamos.add(hm);
					}
				}
				
				// Relleno el dto para construir el json de respuesta al servlet
				dto.put("userName", u.getNombreUsuario());
				dto.put("accounts", cuentasCorrientes);
				dto.put("debitCards", tarjetasDebito);
				dto.put("creditCards", tarjetasCredito);
				dto.put("loans", prestamos);
				
			}
		}
		catch (Exception ex) {
			//puede ocurrir un fallo al acceder a los datos y no podemos acceder a los datos del JSON
			logger.error("Error al localizar los datos del usuario en la sesión", ex);
		}
		
		// Codificación UTF-8 de los datos JSON que devuelve el Servlet
		response.setContentType("application/json;charset=UTF-8");	
		
		// Creo el JSON de salida y lo devuelvo al cliente
		response.getWriter().println(mapper.writeValueAsString(dto));
		
		
	}

}
