package mx.gob.eventosComunitarios.service;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

import javax.sql.DataSource;

import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;

import mx.gob.eventosComunitarios.dao.EventoInterface;
import mx.gob.eventosComunitarios.entity.Evento;
import mx.gob.eventosComunitarios.entity.Modalidad;
import mx.gob.eventosComunitarios.entity.Tipo;

public class EventoService implements EventoInterface{

	private JdbcTemplate jdbc;
	String sql;
	
	public EventoService(DataSource dataSource) {
		// TODO Auto-generated constructor stub
		this.jdbc = new JdbcTemplate(dataSource);
	}
	
	@Override
	public void save(Evento event, int tipo, int modalidad) {
		// TODO Auto-generated method stub
		sql="INSERT INTO evento(nombre, tipo, capacidadMinima, capacidadMaxima, costo, descripcion, edadMinima, publicoObjetivo, status, modalidad, imagen)VALUES(?,?,?,?,?,?,?,?,?,?,?)";
		jdbc.update(sql, event.getNombre(), tipo, event.getCapacidadMinima(), event.getCapacidadMaxima(), event.getCosto(), event.getDescripcion(), event.getEdadMinima(), event.getPublicoObjetivo(), event.getStatus(), modalidad, event.getImagen());
	}

	@Override
	public void update(Evento event, int tipo, int modalidad) {
		// TODO Auto-generated method stub
		sql="UPDATE evento set nombre=?, tipo = ?, capacidadMinima=?, capacidadMaxima=?, costo=?, descripcion=?, edadMinima=?, publicoObjetivo=?, status=?, modalidad=?,imagen=? WHERE idEvento=?";
		jdbc.update(sql, event.getNombre(), tipo, event.getCapacidadMinima(), event.getCapacidadMaxima(), event.getCosto(), event.getDescripcion(), event.getEdadMinima(), event.getPublicoObjetivo(), event.getStatus(), modalidad,event.getImagen(), event.getIdEvento());
	}

	@Override
	public void deleteLogical(long id) {
		// TODO Auto-generated method stub
		sql="Update evento set status = 0 WHERE idEvento=?";
		jdbc.update(sql, id);
	}

	@Override
	public List<Evento> findAll() {
		// TODO Auto-generated method stub
		sql="select * from evento "
				+ "inner join tipo on evento.tipo = idTipo "
				+ "inner join modalidad on evento.modalidad = idModalidad "
				+ "where evento.status = 1";
		
		List<Evento> eventos = this.jdbc.query(sql, new RowMapper<Evento>() {

			@Override
			public Evento mapRow(ResultSet rs, int rowNum) throws SQLException {
				// TODO Auto-generated method stub
				Evento e = new Evento();
				e.setIdEvento(rs.getLong("idEvento"));
				e.setNombre(rs.getString("evento.nombre"));
				e.setCapacidadMinima(new Long(rs.getInt("capacidadMinima")));
				e.setCapacidadMaxima(new Long (rs.getInt("capacidadMaxima")));
				e.setCosto(rs.getDouble("costo"));
				e.setDescripcion(rs.getString("descripcion"));
				e.setEdadMinima(new Long(rs.getInt("edadMinima")));
				e.setPublicoObjetivo(rs.getString("publicoObjetivo"));
				e.setStatus(new Long(rs.getInt("status")));
				e.setImagen(rs.getString("imagen"));
				
				Modalidad m = new Modalidad();
				m.setIdModalidad(new Long(rs.getInt("idModalidad")));
				m.setNombre(rs.getString("modalidad.nombre"));
				
				Tipo t = new Tipo();
				t.setIdTipo(new Long(rs.getInt("idTipo")));
				t.setNombre(rs.getString("tipo.nombre"));
				
				e.setModalidad(m);
				e.setTipo(t);
				
				return e;
			}
		});
		
		return eventos;
	}

	@Override
	public Evento findById(long id) {
		// TODO Auto-generated method stub
		sql="select * from evento "
				+ "inner join tipo on evento.tipo = idTipo "
				+ "inner join modalidad on evento.modalidad = idModalidad Where idEvento = ?";
		Evento evento = jdbc.queryForObject(sql, new Object[] {id}, new RowMapper<Evento>() {

			@Override
			public Evento mapRow(ResultSet rs, int rowNum) throws SQLException {
				// TODO Auto-generated method stub
				Evento e = new Evento();
				e.setIdEvento(rs.getLong("idEvento"));
				e.setNombre(rs.getString("evento.nombre"));
				e.setCapacidadMinima(new Long(rs.getInt("capacidadMinima")));
				e.setCapacidadMaxima(new Long (rs.getInt("capacidadMaxima")));
				e.setCosto(rs.getDouble("costo"));
				e.setDescripcion(rs.getString("descripcion"));
				e.setEdadMinima(new Long(rs.getInt("edadMinima")));
				e.setPublicoObjetivo(rs.getString("publicoObjetivo"));
				e.setStatus(new Long(rs.getInt("status")));
				e.setImagen(rs.getString("imagen"));
				
				
				Modalidad m = new Modalidad();
				m.setIdModalidad(new Long(rs.getInt("idModalidad")));
				m.setNombre(rs.getString("modalidad.nombre"));
				
				Tipo t = new Tipo();
				t.setIdTipo(new Long(rs.getInt("idTipo")));
				t.setNombre(rs.getString("tipo.nombre"));
				
				e.setModalidad(m);
				e.setTipo(t);
				
				return e;
			}
			
		});
		return evento;
	}

	@Override
	public List<Evento> findByName(String name) {
		// TODO Auto-generated method stub
		sql="SELECT * FROM evento WHERE nombre=?";
		return this.jdbc.query(sql, new Object[] {name}, BeanPropertyRowMapper.newInstance(Evento.class));
	}

	@Override
	public List<Evento> findByTipo(Tipo type) {
		// TODO Auto-generated method stub
		sql="SELECT * FROM evento WHERE tipo=?";
		return this.jdbc.query(sql, new Object[] {type}, BeanPropertyRowMapper.newInstance(Evento.class));
	}

	@Override
	public List<Evento> findByModalidad(Modalidad modality) {
		// TODO Auto-generated method stub
		sql="SELECT * FROM evento WHERE modalidad=?";
		return this.jdbc.query(sql, new Object[] {modality}, BeanPropertyRowMapper.newInstance(Evento.class));
	}

	@Override
	public List<Evento> findByStatus(long status) {
		// TODO Auto-generated method stub
		sql="SELECT * FROM evento WHERE status=?";
		return this.jdbc.query(sql, new Object[] {status}, BeanPropertyRowMapper.newInstance(Evento.class));
	}

	@Override
	public List<Evento> findByPublico(String publico) {
		// TODO Auto-generated method stub
		sql="SELECT * FROM evento WHERE publicoObjetivo=?";
		return this.jdbc.query(sql, new Object[] {publico}, BeanPropertyRowMapper.newInstance(Evento.class));
	}

	@Override
	public List<Evento> findByEdad(long age) {
		// TODO Auto-generated method stub
		sql="SELECT * FROM evento edadMinima=?";
		return this.jdbc.query(sql, new Object[] {age}, BeanPropertyRowMapper.newInstance(Evento.class));
	}

}
