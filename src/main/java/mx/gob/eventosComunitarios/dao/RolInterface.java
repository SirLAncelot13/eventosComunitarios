package mx.gob.eventosComunitarios.dao;

import java.util.List;

import mx.gob.eventosComunitarios.entity.Rol;

public interface RolInterface {
	public List<Rol> findAll(); //Registro por parte del admin
	
	public Rol findNotAdmin(long id); //Registro general
	
}
