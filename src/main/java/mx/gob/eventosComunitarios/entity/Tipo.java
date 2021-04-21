package mx.gob.eventosComunitarios.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name="tipo")
public class Tipo {
	@Id
	@Column(name="idTipo")
	private long idTipo;
	
	@Column(name="nombre")
	private String nombre;

	public Tipo() {
		super();
		// TODO Auto-generated constructor stub
	}

	public Tipo(String nombre) {
		super();
		this.nombre = nombre;
	}

	public long getIdTipo() {
		return idTipo;
	}

	public void setIdTipo(long idTipo) {
		this.idTipo = idTipo;
	}

	public String getNombre() {
		return nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}
}
