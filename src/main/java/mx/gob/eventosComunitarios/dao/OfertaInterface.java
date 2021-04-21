package mx.gob.eventosComunitarios.dao;

import java.util.Date;
import java.util.List;

import mx.gob.eventosComunitarios.entity.Evento;
import mx.gob.eventosComunitarios.entity.Oferta;

public interface OfertaInterface {
	public void save(Oferta promo,int evento);
	
	public void update(Oferta promo,int evento);
	
	public void delete(long id);
	
	public List<Oferta> findAll();
	
	public Oferta findById(long id);
	
	// --- Filtros de busqueda ---
	
	public List<Oferta> findByDate(Date date);
	
	public List<Oferta> findByEvento(long id);
	
	public List<Oferta> findByStatus(long status);
	
	public List<Oferta> ofertasPesona();
	
	public List<Oferta> ofertasEquipo();
	
	public List<Oferta> ofertasPesonaBySearchword(String searchword);
	
	public List<Oferta> ofertasEquipoBySearchword(String searchword);
	
	
}
