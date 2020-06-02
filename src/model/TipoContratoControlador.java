package model;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.Query;

import model.Controlador;
import model.Tipocontrato;

public class TipoContratoControlador extends Controlador {
	
	private static TipoContratoControlador controller = null;
	
	// Definimos constantes asociadas a los tipos de contratos
	public static Tipocontrato CUENTA_CORRIENTE = getControlador().find(1);

	public TipoContratoControlador() {
		super(Contrato.class, "Bankonter");
	}

	/**
	 * 
	 * @return
	 */
	public static TipoContratoControlador getControlador () {
		if(controller == null) {
			controller = new TipoContratoControlador();
		}
		return controller;
	}
	
	/**
	 * 
	 */
	public Tipocontrato find (int id) {
		return (Tipocontrato) super.find(id);
	}
	
	public List<Tipocontrato> findAllContratos(){
		List<Tipocontrato> entities = new ArrayList<Tipocontrato>();
		EntityManager em = getEntityManagerFactory().createEntityManager();
		try {
			Query q = em.createNativeQuery("SELECT * FROM tipocontrato", Tipocontrato.class);
			entities = (List<Tipocontrato>) q.getResultList();
			
		} catch (NoResultException nrEx) {
			
		}
		em.close();
		return entities;
	}
	
}
