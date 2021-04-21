package mx.gob.eventosComunitarios.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name="evento")
public class Evento {
	@Id
	@Column(name="idEvento")
	private long idEvento;
	
	@Column(name="nombre")
	private String nombre;
	
	@Column(name="tipo")
	private Tipo tipo;
	
	@Column(name="capacidadMinima")
	private long capacidadMinima;
	
	@Column(name="capacidadMaxima")
	private long capacidadMaxima;
	
	@Column(name="costo")
	private double costo;
	
	@Column(name="descripcion")
	private String descripcion;
	
	@Column(name="edadMinima")
	private long edadMinima;
	
	@Column(name="publicoObjetivo")
	private String publicoObjetivo;
	
	@Column(name="status")
	private long status;
	
	@Column(name="modalidad")
	private Modalidad modalidad;
	
	@Column(name="imagen")
	private String imagen;

	public Evento() {
		super();
		// TODO Auto-generated constructor stub
		this.tipo = new Tipo();
		this.modalidad = new Modalidad();
	}

	public Evento(String nombre, Tipo tipo, long capacidadMinima, long capacidadMaxima, double costo,
			String descripcion, long edadMinima, String publicoObjetivo, long status, Modalidad modalidad,
			String imagen) {
		super();
		this.nombre = nombre;
		this.tipo = tipo;
		this.capacidadMinima = capacidadMinima;
		this.capacidadMaxima = capacidadMaxima;
		this.costo = costo;
		this.descripcion = descripcion;
		this.edadMinima = edadMinima;
		this.publicoObjetivo = publicoObjetivo;
		this.status = status;
		this.modalidad = modalidad;
		this.imagen = imagen;
	}

	public long getIdEvento() {
		return idEvento;
	}

	public void setIdEvento(long idEvento) {
		this.idEvento = idEvento;
	}

	public String getNombre() {
		return nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	public Tipo getTipo() {
		return tipo;
	}

	public void setTipo(Tipo tipo) {
		this.tipo = tipo;
	}

	public long getCapacidadMinima() {
		return capacidadMinima;
	}

	public void setCapacidadMinima(long capacidadMinima) {
		this.capacidadMinima = capacidadMinima;
	}

	public long getCapacidadMaxima() {
		return capacidadMaxima;
	}

	public void setCapacidadMaxima(long capacidadMaxima) {
		this.capacidadMaxima = capacidadMaxima;
	}

	public double getCosto() {
		return costo;
	}

	public void setCosto(double costo) {
		this.costo = costo;
	}

	public String getDescripcion() {
		return descripcion;
	}

	public void setDescripcion(String descripcion) {
		this.descripcion = descripcion;
	}

	public long getEdadMinima() {
		return edadMinima;
	}

	public void setEdadMinima(long edadMinima) {
		this.edadMinima = edadMinima;
	}

	public String getPublicoObjetivo() {
		return publicoObjetivo;
	}

	public void setPublicoObjetivo(String publicoObjetivo) {
		this.publicoObjetivo = publicoObjetivo;
	}

	public long getStatus() {
		return status;
	}

	public void setStatus(long status) {
		this.status = status;
	}

	public Modalidad getModalidad() {
		return modalidad;
	}

	public void setModalidad(Modalidad modalidad) {
		this.modalidad = modalidad;
	}

	public String getImagen() {
		return imagen;
	}

	public void setImagen(String imagen) {
		this.imagen = imagen;
	}
}
