package model;

import java.util.List;

import javax.management.Query;
import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.TypedQuery;

public class UsuarioControlador extends Controlador {
	private  static UsuarioControlador controlador = null;
	

	public UsuarioControlador() {
		super(Usuario.class, "Bankonter");
		// TODO Auto-generated constructor stub
	}
	
	
	/**
	 * 
	 * @return
	 */
	public static UsuarioControlador getControlador() {
		if (controlador == null) {
			controlador = new UsuarioControlador();
		}
		return controlador;
	}
	
	/**
	 * 
	 */
	public Usuario find (int id) {
		return (Usuario) super.find(id);
	}

	/**
	 * 
	 */
	public Usuario findFirst() {
		try {
			EntityManager em = getEntityManagerFactory().createEntityManager();
			Query q = (Query) em.createQuery("SELECT u FROM Usuario u order by u.id", Usuario.class);
			Usuario resultado = (Usuario) ((javax.persistence.Query) q).setMaxResults(1).getSingleResult();
			em.close();
			return resultado;
			
		} catch (NoResultException nrEx) {
			return null;
		}
	}
	
	public List<Usuario> findAll () {
		EntityManager em = getEntityManagerFactory().createEntityManager();
		Query q = (Query) em.createQuery("SELECT u FROM Usuario u", Usuario.class);
		List<Usuario> resultado = (List<Usuario>) ((javax.persistence.Query) q).getResultList();
		em.close();
		return resultado;
	}
	
	
	public static void main (String args[]) {
		Usuario u = UsuarioControlador.getControlador().find(1);
		System.out.println("Usuario: "  + u.getNombreUsuario());
	}
	
}
