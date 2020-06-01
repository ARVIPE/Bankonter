package model;

import java.io.Serializable;
import javax.persistence.*;
import java.util.List;


/**
 * The persistent class for the imagen database table.
 * 
 */
@Entity
@NamedQuery(name="Imagen.findAll", query="SELECT i FROM Imagen i")
public class Imagen implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	private int id;

	private Object contenido;

	private Object miniatura;

	//bi-directional many-to-one association to Usuario
	@OneToMany(mappedBy="imagen")
	private List<Usuario> usuarios;

	public Imagen() {
	}

	public int getId() {
		return this.id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public Object getContenido() {
		return this.contenido;
	}

	public void setContenido(Object contenido) {
		this.contenido = contenido;
	}

	public Object getMiniatura() {
		return this.miniatura;
	}

	public void setMiniatura(Object miniatura) {
		this.miniatura = miniatura;
	}

	public List<Usuario> getUsuarios() {
		return this.usuarios;
	}

	public void setUsuarios(List<Usuario> usuarios) {
		this.usuarios = usuarios;
	}

	public Usuario addUsuario(Usuario usuario) {
		getUsuarios().add(usuario);
		usuario.setImagen(this);

		return usuario;
	}

	public Usuario removeUsuario(Usuario usuario) {
		getUsuarios().remove(usuario);
		usuario.setImagen(null);

		return usuario;
	}

}