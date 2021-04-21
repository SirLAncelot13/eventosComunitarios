package mx.gob.eventosComunitarios.service;

import java.util.List;

import javax.sql.DataSource;

import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;

import mx.gob.eventosComunitarios.dao.TipoInterface;
import mx.gob.eventosComunitarios.entity.Tipo;

public class TipoService implements TipoInterface{

	private JdbcTemplate jdbc;
	String sql;
	
	
	public TipoService(DataSource dataSource) {
		// TODO Auto-generated constructor stub
		this.jdbc = new JdbcTemplate(dataSource);
	}
	
	@Override
	public List<Tipo> findAll() {
		// TODO Auto-generated method stub
		sql = "SELECT * FROM tipo";
		return this.jdbc.query(sql, BeanPropertyRowMapper.newInstance(Tipo.class));
	}
	@Override
	public void save(Tipo tipo) {
		// TODO Auto-generated method stub
		sql ="INSERT INTO tipo(nombre)values(?)";
		jdbc.update(sql, tipo.getNombre());
	}

	@Override
	public void update(Tipo tipo) {
		// TODO Auto-generated method stub
		sql="update tipo set nombre=? WHERE idTipo=?";
		jdbc.update(sql, tipo.getNombre(), tipo.getIdTipo());
	}

	@Override
	public Tipo findOne(long id) {
		// TODO Auto-generated method stub
		sql="SELECT * FROM tipo WHERE idTipo=?";
		return this.jdbc.queryForObject(sql, new Object[] {id}, BeanPropertyRowMapper.newInstance(Tipo.class));
		
	}
}
