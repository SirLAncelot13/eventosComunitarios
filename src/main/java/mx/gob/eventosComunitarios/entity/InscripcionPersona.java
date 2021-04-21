package mx.gob.eventosComunitarios.entity;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "inscripcionPersona")
public class InscripcionPersona {
	@Id
	@Column(name = "idPersona")
	private long idPersona;

	@Column(name = "usuario")
	private Usuario usuario;

	@Column(name = "fechaInscripcion")
	private Date fechaInscripcion;

	@Column(name = "oferta")
	private Oferta oferta;

	@Column(name = "status")
	private long status;

	public InscripcionPersona() {
		super();
		// TODO Auto-generated constructor stub
	}

	public InscripcionPersona(Usuario usuario, Date fechaInscripcion, Oferta oferta, long status) {
		super();
		this.usuario = usuario;
		this.fechaInscripcion = fechaInscripcion;
		this.oferta = oferta;
		this.status = status;
	}

	public long getIdPersona() {
		return idPersona;
	}

	public void setIdPersona(long idPersona) {
		this.idPersona = idPersona;
	}

	public Usuario getUsuario() {
		return usuario;
	}

	public void setUsuario(Usuario usuario) {
		this.usuario = usuario;
	}

	public Date getFechaInscripcion() {
		return fechaInscripcion;
	}

	public void setFechaInscripcion(Date fechaInscripcion) {
		this.fechaInscripcion = fechaInscripcion;
	}

	public Oferta getOferta() {
		return oferta;
	}

	public void setOferta(Oferta oferta) {
		this.oferta = oferta;
	}

	public long getStatus() {
		return status;
	}

	public void setStatus(long status) {
		this.status = status;
	}
	
	
}
