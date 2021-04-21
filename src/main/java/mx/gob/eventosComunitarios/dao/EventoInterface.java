package mx.gob.eventosComunitarios.dao;

import java.util.List;

import mx.gob.eventosComunitarios.entity.Evento;
import mx.gob.eventosComunitarios.entity.Modalidad;
import mx.gob.eventosComunitarios.entity.Tipo;

public interface EventoInterface {
	
	public void save(Evento event, int tipo, int modalidad);
	
	public void update(Evento event, int tipo, int modalidad);
	
	public void deleteLogical(long id);
	
	public List<Evento> findAll();
	
	public Evento findById(long id);
	
	public List<Evento> findByName(String name); //barra de busqueda
	
	// --- Filtros en la busqueda
	
	public List<Evento> findByTipo(Tipo type);
	
	public List<Evento> findByModalidad(Modalidad modality);
	
	public List<Evento> findByStatus(long status); //para el admin
	
	public List<Evento> findByPublico(String publico);
	
	public List<Evento> findByEdad(long age);
	
}
