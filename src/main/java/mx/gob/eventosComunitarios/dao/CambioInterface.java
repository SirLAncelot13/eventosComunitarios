package mx.gob.eventosComunitarios.dao;

import java.util.Date;
import java.util.List;

import mx.gob.eventosComunitarios.entity.Cambio;

public interface CambioInterface {
	public void save(Cambio change);
	
	public List<Cambio> findAll();
	
	// -- filtros 
	
	public List<Cambio> findByDate(Date date);
	
	public List<Cambio> findByResponse(String response);
	
	public List<Cambio> findByHost(String host);
}
