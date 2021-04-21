package mx.gob.eventosComunitarios.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.validation.constraints.Min;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Pattern.Flag;
import javax.validation.constraints.Size;

import org.hibernate.validator.constraints.NotBlank;

import com.mysql.cj.xdevapi.Schema.CreateCollectionOptions;

@Entity
@Table(name = "usuario")
public class Usuario {
	
	//^[0-9a-zA-Z_\\-]+$
	
	@Id
	@Column(name = "idUsuario")
	private long idUsuario;


	//@Size(min=5,max=50)
	//@Pattern(regexp = "[/^([A-Z]{1}[a-zÒ·ÈÌÛ˙]+[\\s]*)+$/]")
	@Column(name = "nombre")
	private String nombre;


	//@Size(min=5,max=50)
	//@Pattern(regexp = "/^[a-zA-Z—Ò¡·…ÈÕÌ”Û⁄˙‹¸\\s]+$/", message = "Debe cumplir el formato apellido P")
	@Column(name = "apellidoPaterno")
	private String apellidoPaterno;


	//@Size(min=5,max=50)
	//@Pattern(regexp = "/^[a-zA-Z—Ò¡·…ÈÕÌ”Û⁄˙‹¸\\s]+$/", message = "Debe cumplir el formato apellido M")
	@Column(name = "apellidoMaterno")
	private String apellidoMaterno;


	//@Size(min=10,max=10)
	//@Pattern(regexp = "/^([0-9])*$/", message = "Debe cumplir el formato telefono")
	@Column(name = "telefono")
	private String telefono;
	
	//@NotBlank(message = "Ingresa una edad")
	//@Min(10)
	@Column(name = "edad")
	private long edad;

	@Column(name = "genero")
	private long genero;
	

	//@Size(min=10,max=50)
	@Column(name = "direccion")
	private String direccion;
	

	//@Size(min=10,max=20)
	//@Pattern(regexp = "/^[-\\w.%+]{1,64}@(?:[A-Z0-9-]{1,63}\\.){1,125}[A-Z]{2,63}$/i", message = "Debe cumplir el formato Correo")
	@Column(name = "correo")
	private String correo;


	//@Size(min=8,max=20)
	//@Pattern(regexp = " /^(?=.[a-z])(?=.[A-Z])(?=.\\d)(?=.[$@$!%?&])[A-Za-z\\d$@$!%?&]{8,15}/")
	@Column(name = "contrasena")
	private String contrasena;

	@Column(name = "rol")
	private Rol rol;

	@Column(name = "status")
	private long status;

	public Usuario() {
		super();
		// TODO Auto-generated constructor stub
	}

	public Usuario(String nombre, String apellidoPaterno, String apellidoMaterno, String telefono, long edad,
			long genero, String direccion, String correo, String contrasena, Rol rol, long status) {
		super();
		this.nombre = nombre;
		this.apellidoPaterno = apellidoPaterno;
		this.apellidoMaterno = apellidoMaterno;
		this.telefono = telefono;
		this.edad = edad;
		this.genero = genero;
		this.direccion = direccion;
		this.correo = correo;
		this.contrasena = contrasena;
		this.rol = rol;
		this.status = status;
	}

	public String getNombre() {
		return nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	public String getApellidoPaterno() {
		return apellidoPaterno;
	}

	public void setApellidoPaterno(String apellidoPaterno) {
		this.apellidoPaterno = apellidoPaterno;
	}

	public String getApellidoMaterno() {
		return apellidoMaterno;
	}

	public void setApellidoMaterno(String apellidoMaterno) {
		this.apellidoMaterno = apellidoMaterno;
	}

	public String getTelefono() {
		return telefono;
	}

	public void setTelefono(String telefono) {
		this.telefono = telefono;
	}

	public long getEdad() {
		return edad;
	}

	public void setEdad(long edad) {
		this.edad = edad;
	}

	public long getGenero() {
		return genero;
	}

	public void setGenero(long genero) {
		this.genero = genero;
	}

	public String getDireccion() {
		return direccion;
	}

	public void setDireccion(String direccion) {
		this.direccion = direccion;
	}

	public long getIdUsuario() {
		return idUsuario;
	}

	public void setIdUsuario(long idUsuario) {
		this.idUsuario = idUsuario;
	}

	public String getCorreo() {
		return correo;
	}

	public void setCorreo(String correo) {
		this.correo = correo;
	}

	public String getContrasena() {
		return contrasena;
	}

	public void setContrasena(String contrasena) {
		this.contrasena = contrasena;
	}

	public Rol getRol() {
		return rol;
	}

	public void setRol(Rol rol) {
		this.rol = rol;
	}

	public long getStatus() {
		return status;
	}

	public void setStatus(long status) {
		this.status = status;
	}

}
