package mx.gob.eventosComunitarios.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name="modalidad")
public class Modalidad {
	@Id
	@Column(name="idModalidad")
	private long idModalidad;
	
	@Column(name="nombre")
	private String nombre;

	public Modalidad() {
		super();
		// TODO Auto-generated constructor stub
	}

	public Modalidad(String nombre) {
		super();
		this.nombre = nombre;
	}

	public long getIdModalidad() {
		return idModalidad;
	}

	public void setIdModalidad(long idModalidad) {
		this.idModalidad = idModalidad;
	}

	public String getNombre() {
		return nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}
}
