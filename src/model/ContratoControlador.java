package model;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.Query;

public class ContratoControlador extends Controlador {
	
	private static ContratoControlador controller = null;

	public ContratoControlador() {
		super(Contrato.class, "Bankonter");
	}

	/**
	 * 
	 * @return
	 */
	public static ContratoControlador getControlador () {
		if(controller == null) {
			controller = new ContratoControlador();
		}
		return controller;
	}
	
	/**
	 * 
	 */
	public Contrato find (int id) {
		return (Contrato) super.find(id);
	}
	
	public List<Contrato> findAllContratos(){
		List<Contrato> entities = new ArrayList<Contrato>();
		EntityManager em = getEntityManagerFactory().createEntityManager();
		try {
			Query q = em.createNativeQuery("SELECT * FROM contrato", Contrato.class);
			entities = (List<Contrato>) q.getResultList();
			
		} catch (NoResultException nrEx) {
			
		}
		em.close();
		return entities;
	}
	
}
