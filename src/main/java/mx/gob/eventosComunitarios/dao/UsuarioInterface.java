package mx.gob.eventosComunitarios.dao;

import java.util.List;

import mx.gob.eventosComunitarios.entity.Usuario;

public interface UsuarioInterface {
	public void save(Usuario user);
	
	public void update(Usuario user);
	
	public void delete(long id);
	
	public List<Usuario> findAll();
	
	public Usuario findById(long id);
	
	public List<Usuario> findByStatus(long status);
	
	public Usuario findByCorreo(String email);
	
	public long countCorreo(String correo);

}
