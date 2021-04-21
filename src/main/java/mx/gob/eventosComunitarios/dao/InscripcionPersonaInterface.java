package mx.gob.eventosComunitarios.dao;

import java.util.List;

import mx.gob.eventosComunitarios.entity.InscripcionEquipo;
import mx.gob.eventosComunitarios.entity.InscripcionPersona;

public interface InscripcionPersonaInterface {
	public void save(InscripcionPersona people);
	
	public void delete(long id);
	
	public List<InscripcionPersona> findAll(long id);
	
	public long countPeople(long id); //contar las personas 
	
	public InscripcionPersona findByUser(long idUser, long idEvent); //evitar repeticiones
	
	public void update(InscripcionPersona people);
	
	public InscripcionPersona findById(long id);
	
	public List<InscripcionPersona> historial(long id);
}
