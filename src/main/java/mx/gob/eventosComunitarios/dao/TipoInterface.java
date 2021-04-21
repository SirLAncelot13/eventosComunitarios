package mx.gob.eventosComunitarios.dao;

import java.util.List;

import mx.gob.eventosComunitarios.entity.Tipo;

public interface TipoInterface {
	public List<Tipo> findAll();
public void save(Tipo tipo);
	
	public void update(Tipo tipo);
	
	public Tipo findOne(long id);
}
