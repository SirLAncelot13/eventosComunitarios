package mx.gob.eventosComunitarios.service;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

import mx.gob.eventosComunitarios.dao.UsuarioInterface;
import mx.gob.eventosComunitarios.entity.Evento;
import mx.gob.eventosComunitarios.entity.Modalidad;
import mx.gob.eventosComunitarios.entity.Rol;
import mx.gob.eventosComunitarios.entity.Tipo;
import mx.gob.eventosComunitarios.entity.Usuario;

public class UsuarioService implements UsuarioInterface{
	
	private JdbcTemplate jdbc;
	String sql;
	
	@Autowired
	PasswordEncoder paswwordEncoder;
	
	@Bean
	public PasswordEncoder passwordEncoder() {
		return new BCryptPasswordEncoder();
	}
	
	public UsuarioService(DataSource dataSource) {
		// TODO Auto-generated constructor stub
		this.jdbc = new JdbcTemplate(dataSource);
	}
	
	@Override
	public void save(Usuario user) {
		// TODO Auto-generated method stub
		sql = "insert into usuario (nombre,apellidoPaterno,apellidoMaterno,telefono,edad,genero,direccion,correo,contrasena,rol,status)values(?,?,?,?,?,?,?,?,?,?,?)";
		jdbc.update(sql,user.getNombre(), user.getApellidoPaterno(),user.getApellidoMaterno(),user.getTelefono(),user.getEdad(),user.getGenero(),user.getDireccion(),user.getCorreo(), passwordEncoder().encode(user.getContrasena()), user.getRol().getIdRol(),  user.getStatus());
	}

	@Override
	public void update(Usuario user) {
		// TODO Auto-generated method stub
		sql ="update usuario set nombre=?,apellidoPaterno=?,apellidoMaterno=?,telefono=?,edad=?,genero=?,direccion=?,rol=?,status=? where idUsuario =?";
		jdbc.update(sql, user.getNombre(), user.getApellidoPaterno(),user.getApellidoMaterno(),user.getTelefono(),user.getEdad(),user.getGenero(),user.getDireccion(), user.getRol().getIdRol(),  user.getStatus(), user.getIdUsuario());
	}

	@Override
	public void delete(long id) {
		// TODO Auto-generated method stub
		sql="update usuario set status=0 WHERE idUsuario= ?";
		jdbc.update(sql, id);
	}

	/*@Override
	public List<Usuario> findAll() {
		// TODO Auto-generated method stub
		sql ="select * from usuario";
		return this.jdbc.query(sql, BeanPropertyRowMapper.newInstance(Usuario.class));
	}*/
	
	
	
	//prueba
	@Override
	public List<Usuario> findAll() {
		// TODO Auto-generated method stub
		sql ="select * from usuario "
				+ "inner join rol on usuario.rol = idRol and status=1";
		
		List<Usuario> usuarios = this.jdbc.query(sql, new RowMapper<Usuario>() {

			@Override
			public Usuario mapRow(ResultSet rs, int rowNum) throws SQLException {
				
				Usuario u = new Usuario();
				
				u.setIdUsuario(rs.getLong("idUsuario"));
				u.setNombre(rs.getString("nombre"));
				u.setApellidoPaterno(rs.getString("apellidoPaterno"));
				u.setApellidoMaterno(rs.getString("apellidoMaterno"));
				u.setTelefono(rs.getString("telefono"));
				u.setEdad(rs.getInt("edad"));
				u.setGenero(rs.getLong("genero"));
				u.setDireccion(rs.getString("direccion"));
				u.setCorreo(rs.getString("correo"));
				u.setContrasena(rs.getString("contrasena"));
				u.setStatus(rs.getLong("status"));
				
				Rol r = new Rol();
				r.setIdRol(rs.getLong("idRol"));
				r.setNombre(rs.getString("rol.nombre"));

				u.setRol(r);
				
				return u;
			}
			
		});
		return usuarios;
	}

	@Override
	public Usuario findById(long id) {
		// TODO Auto-generated method stub
		sql = "select * from usuario "
				+ "inner join rol on usuario.rol = idRol where idUsuario = ?;";
		Usuario usuario = jdbc.queryForObject(sql, new Object[] { id }, new RowMapper<Usuario>() {

			@Override
			public Usuario mapRow(ResultSet rs, int rowNum) throws SQLException {

				Usuario u = new Usuario();
				u.setIdUsuario(rs.getLong("idUsuario"));
				u.setNombre(rs.getString("nombre"));
				u.setApellidoPaterno(rs.getString("apellidoPaterno"));
				u.setApellidoMaterno(rs.getString("apellidoMaterno"));
				u.setTelefono(rs.getString("telefono"));
				u.setEdad(rs.getInt("edad"));
				u.setGenero(rs.getLong("genero"));
				u.setDireccion(rs.getString("direccion"));
				u.setCorreo(rs.getString("correo"));
				u.setContrasena(rs.getString("contrasena"));
				u.setStatus(rs.getLong("status"));

				Rol r = new Rol();
				r.setIdRol(rs.getLong("idRol"));
				r.setNombre(rs.getString("rol.nombre"));

				u.setRol(r);

				return u;
			}
		});

		return usuario;
	}

	@Override
	public List<Usuario> findByStatus(long status) {
		// TODO Auto-generated method stub
		sql="SELECT * FROM usuario WHERE status = ?";
		return this.jdbc.query(sql, new Object[] {status}, BeanPropertyRowMapper.newInstance(Usuario.class));
	}

	@Override
	public Usuario findByCorreo(String email) {
		// TODO Auto-generated method stub
		sql ="SELECT * FROM usuario INNER JOIN rol ON usuario.rol = rol.idRol WHERE correo = ?";
		return this.jdbc.queryForObject(sql,new Object[] {email}, new RowMapper<Usuario>() {

			@Override
			public Usuario mapRow(ResultSet rs, int rowNum) throws SQLException {

				Usuario u = new Usuario();
				u.setIdUsuario(rs.getLong("idUsuario"));
				u.setNombre(rs.getString("nombre"));
				u.setApellidoPaterno(rs.getString("apellidoPaterno"));
				u.setApellidoMaterno(rs.getString("apellidoMaterno"));
				u.setTelefono(rs.getString("telefono"));
				u.setEdad(rs.getInt("edad"));
				u.setGenero(rs.getLong("genero"));
				u.setDireccion(rs.getString("direccion"));
				u.setCorreo(rs.getString("correo"));
				u.setContrasena(rs.getString("contrasena"));
				u.setStatus(rs.getLong("status"));

				Rol r = new Rol();
				r.setIdRol(rs.getInt("idRol"));
				r.setNombre(rs.getString("rol.nombre"));

				u.setRol(r);

				return u;
			}
		});

	}

	@Override
	public long countCorreo(String correo) {
		sql ="SELECT count(*) FROM usuario INNER JOIN rol ON usuario.rol = rol.idRol WHERE correo = ?";
		long count = this.jdbc.queryForLong(sql, new Object[] {correo});
		return count;
		
	}

}
