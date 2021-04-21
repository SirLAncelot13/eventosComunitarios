package mx.gob.eventosComunitarios.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name="rol")
public class Rol {
	@Id
	@Column(name="idRol")
	private long idRol;
	
	@Column(name="nombre")
	private String nombre;

	public Rol() {
		super();
		// TODO Auto-generated constructor stub
	}
	
	

	public Rol(long idRol, String nombre) {
		super();
		this.idRol = idRol;
		this.nombre = nombre;
	}



	public Rol(String nombre) {
		super();
		this.nombre = nombre;
	}

	public long getIdRol() {
		return idRol;
	}

	public void setIdRol(long idRol) {
		this.idRol = idRol;
	}

	public String getNombre() {
		return nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}
	
	
}
