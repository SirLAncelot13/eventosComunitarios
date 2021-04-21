package mx.gob.eventosComunitarios.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name="dia")
public class Dia {
	@Id
	@Column(name="idDia")
	private long idDIa;
	
	@Column(name="dia")
	private String dia;

	public Dia() {
		super();
		// TODO Auto-generated constructor stub
	}

	public Dia(long idDIa, String dia) {
		super();
		this.idDIa = idDIa;
		this.dia = dia;
	}

	public long getIdDIa() {
		return idDIa;
	}

	public void setIdDIa(long idDIa) {
		this.idDIa = idDIa;
	}

	public String getDia() {
		return dia;
	}

	public void setDia(String dia) {
		this.dia = dia;
	}
}
