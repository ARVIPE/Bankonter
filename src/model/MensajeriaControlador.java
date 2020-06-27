package model;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.Query;

public class MensajeriaControlador extends Controlador {
	
	private static MensajeriaControlador controller = null;

	public MensajeriaControlador() {
		super(Mensajeria.class, "Bankonter");
	}

	
	/**
	 * 
	 * @return
	 */
	public static MensajeriaControlador getControlador () {
		if (controller == null) {
			controller = new MensajeriaControlador();
		}
		return controller;
	}
	
	
	/**
	 * 
	 */
	public Mensajeria find (int id) {
		return (Mensajeria) super.find(id);
	}
	
	/**
	 * 
	 * @return
	 */
	public List<Mensajeria> findAllMensajes () {
		List<Mensajeria> entities = new ArrayList<Mensajeria>();
		EntityManager em = getEntityManagerFactory().createEntityManager();
		try {
			Query q = em.createNativeQuery("SELECT * FROM mensajeria", Mensajeria.class);
			entities =(List<Mensajeria>) q.getResultList();
		} catch (NoResultException	 nrEx) {
		}
		em.close();
		return entities;
	}
}
