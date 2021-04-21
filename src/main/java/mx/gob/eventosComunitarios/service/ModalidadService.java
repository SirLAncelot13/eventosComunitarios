package mx.gob.eventosComunitarios.service;

import java.util.List;

import javax.sql.DataSource;

import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;

import mx.gob.eventosComunitarios.dao.ModalidadInterface;
import mx.gob.eventosComunitarios.entity.Modalidad;

public class ModalidadService implements ModalidadInterface{

	private JdbcTemplate jdbc;
	String sql;
	
	public ModalidadService(DataSource dataSource) {
		// TODO Auto-generated constructor stub
		this.jdbc = new JdbcTemplate(dataSource);
	}
	
	@Override
	public List<Modalidad> findAll() {
		// TODO Auto-generated method stub
		sql ="SELECT * FROM modalidad";
		return this.jdbc.query(sql, BeanPropertyRowMapper.newInstance(Modalidad.class));
	}

}
