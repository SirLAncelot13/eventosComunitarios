package mx.gob.eventosComunitarios.service;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Date;
import java.util.List;

import javax.sql.DataSource;

import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;

import mx.gob.eventosComunitarios.dao.CambioInterface;
import mx.gob.eventosComunitarios.entity.Cambio;
import mx.gob.eventosComunitarios.entity.Usuario;

public class CambioService implements CambioInterface{
	
	private JdbcTemplate jdbc;
	String sql;
	
	public CambioService(DataSource dataSource) {
		// TODO Auto-generated constructor stub
		this.jdbc = new JdbcTemplate(dataSource);
	}
	
	
	@Override
	public void save(Cambio change) {
		sql ="INSERT INTO cambio(host, descripcion, fecha, usuario)values(?,?,?,?)";
		jdbc.update(sql, change.getHost(), change.getDescripcion(), change.getFecha(), change.getUsuario().getIdUsuario());
		// TODO Auto-generated method stub
		
	}

	@Override
	public List<Cambio> findAll() {
		// TODO Auto-generated method stub
		sql ="SELECT * FROM cambio INNER JOIN usuario ON cambio.usuario = usuario.idUsuario ORDER BY fecha ASC";
		List<Cambio> cambios = this.jdbc.query(sql, new RowMapper<Cambio>() {

			@Override
			public Cambio mapRow(ResultSet rs, int rowNum) throws SQLException {
				// TODO Auto-generated method stub
				Cambio c = new Cambio();
				c.setIdCambios(rs.getLong("cambio.idCambios"));
				c.setHost(rs.getString("cambio.host"));
				c.setDescripcion(rs.getString("cambio.descripcion"));
				c.setFecha(rs.getString("cambio.fecha"));
				
				Usuario u = new Usuario();
				u.setIdUsuario(rs.getLong("usuario.idUsuario"));
				u.setCorreo(rs.getString("usuario.correo"));
				c.setUsuario(u);
				return c;
			}
			
		});
		return cambios;
	}

	@Override
	public List<Cambio> findByDate(Date date) {
		// TODO Auto-generated method stub
		sql ="SELECT * FROM cambio where fecha = ?";
		return this.jdbc.query(sql, new Object[] {date}, BeanPropertyRowMapper.newInstance(Cambio.class));
	}

	@Override
	public List<Cambio> findByResponse(String response) {
		// TODO Auto-generated method stub
		sql ="SELECT * FROM cambio where response = ?";
		return this.jdbc.query(sql, new Object[] {response},  BeanPropertyRowMapper.newInstance(Cambio.class));
	}

	@Override
	public List<Cambio> findByHost(String host) {
		// TODO Auto-generated method stub
		sql ="SELECT * FROM cambio where host = ?";
		return this.jdbc.query(sql, new Object[] {host}, BeanPropertyRowMapper.newInstance(Cambio.class));
	}
	
}