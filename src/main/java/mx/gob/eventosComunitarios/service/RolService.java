package mx.gob.eventosComunitarios.service;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

import javax.sql.DataSource;

import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;

import mx.gob.eventosComunitarios.dao.RolInterface;
import mx.gob.eventosComunitarios.entity.Evento;
import mx.gob.eventosComunitarios.entity.Modalidad;
import mx.gob.eventosComunitarios.entity.Rol;
import mx.gob.eventosComunitarios.entity.Tipo;

public class RolService implements RolInterface{

	private JdbcTemplate jdbc;
	String sql;
		
	public RolService(DataSource dataSource) {
		// TODO Auto-generated constructor stub
		this.jdbc = new JdbcTemplate(dataSource);
	}
	
	@Override
	public List<Rol> findAll() {
		// TODO Auto-generated method stub
		sql="SELECT * FROM rol";
		return this.jdbc.query(sql, BeanPropertyRowMapper.newInstance(Rol.class));
	}

	@Override
	public Rol findNotAdmin(long id) {
		sql="select * from rol where nombre != 'Administrador'";
		
		Rol roll = jdbc.queryForObject(sql, new Object[] {id}, new RowMapper<Rol>() {

			@Override
			public Rol mapRow(ResultSet rs, int rowNum) throws SQLException {
				// TODO Auto-generated method stub
				Rol r = new Rol();
				r.setIdRol(rs.getLong("idRol"));
				r.setNombre(rs.getString("nombre"));
				return r;
			}
		});
		return roll;

	}


}
