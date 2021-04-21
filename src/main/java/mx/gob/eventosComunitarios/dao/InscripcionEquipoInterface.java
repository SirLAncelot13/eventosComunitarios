package mx.gob.eventosComunitarios.dao;

import java.util.List;

import mx.gob.eventosComunitarios.entity.InscripcionEquipo;

public interface InscripcionEquipoInterface {
	public void save(InscripcionEquipo team);
	
	public void update(InscripcionEquipo team);
	
	public void delete(long id); // conocido en la app como cancelar inscripcion
	
	public List<InscripcionEquipo> findAll(long id); //por si se necesita
	
	public InscripcionEquipo findById(long id);
	
	public InscripcionEquipo findByUser(long idUser, long idEvent); //evitar repeticiones
	
	public long  countPeople(long id); //ver cuantas personas hay
	public List<InscripcionEquipo> historial(long id);
}
