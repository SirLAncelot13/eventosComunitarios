package mx.gob.eventosComunitarios.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name="cambio")
public class Cambio {
	@Id
	@Column(name="idCambios")
	private long idCambios;
	
	@Column(name="descripcion")
	private String descripcion;
	
	@Column(name="fecha")
	private String fecha;
	
	@Column(name="host")
	private String host;
	
	@Column(name="usuario")
	private Usuario usuario;

	public Cambio() {
		super();
		// TODO Auto-generated constructor stub
		this.usuario = new Usuario();
	}

	public Cambio(String descripcion, String fecha, String host, Usuario usuario) {
		super();
		this.descripcion = descripcion;
		this.fecha = fecha;
		this.host = host;
		this.usuario = usuario;
	}

	public long getIdCambios() {
		return idCambios;
	}

	public void setIdCambios(long idCambios) {
		this.idCambios = idCambios;
	}

	public String getDescripcion() {
		return descripcion;
	}

	public void setDescripcion(String descripcion) {
		this.descripcion = descripcion;
	}

	public String getFecha() {
		return fecha;
	}

	public void setFecha(String fecha) {
		this.fecha = fecha;
	}

	public String getHost() {
		return host;
	}

	public void setHost(String host) {
		this.host = host;
	}

	public Usuario getUsuario() {
		return usuario;
	}

	public void setUsuario(Usuario usuario) {
		this.usuario = usuario;
	}
}