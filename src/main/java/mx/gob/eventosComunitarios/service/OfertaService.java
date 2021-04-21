package mx.gob.eventosComunitarios.service;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.sql.DataSource;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;

import mx.gob.eventosComunitarios.dao.OfertaInterface;
import mx.gob.eventosComunitarios.entity.Dia;
import mx.gob.eventosComunitarios.entity.Evento;
import mx.gob.eventosComunitarios.entity.Modalidad;
import mx.gob.eventosComunitarios.entity.Oferta;
import mx.gob.eventosComunitarios.entity.Tipo;

public class OfertaService implements OfertaInterface {

	private JdbcTemplate jdbc;
	String sql;

	public OfertaService(DataSource dataSource) {
		// TODO Auto-generated constructor stub
		this.jdbc = new JdbcTemplate(dataSource);
	}

	@Override
	public void save(Oferta promo, int evento) {
		// TODO Auto-generated method stub
		sql = "INSERT INTO oferta(fechaInicio, fechaFin, fechaInicioRegistro, fechaFinRegistro, evento, detalles,horaInicio,horaFin, status)values(?,?,?,?,?,?,?,?,?)";
		jdbc.update(sql, promo.getFechaInicio(), promo.getFechaFin(), promo.getFechaInicioRegistro(),
				promo.getFechaFinRegistro(), evento, promo.getDetalles(), promo.getHoraInicio(), promo.getHoraFin(),
				promo.getStatus());
	}

	@Override
	public void update(Oferta promo, int evento) {
		// TODO Auto-generated method stub
		System.out.println("Service HoraInicio:> " + promo.getHoraInicio());
		System.out.println("Service HoraFin:> " + promo.getHoraFin());
		sql = "UPDATE oferta SET fechaInicio=?, fechaFin=?, fechaInicioRegistro =?, fechaFinRegistro=?, evento=?, detalles=?, horaInicio=?, horaFin=?, status=? where idOferta=?";
		jdbc.update(sql, promo.getFechaInicio(), promo.getFechaFin(), promo.getFechaInicioRegistro(),
				promo.getFechaFinRegistro(), evento, promo.getDetalles(), promo.getHoraInicio(), promo.getHoraFin(),
				promo.getStatus(), promo.getIdOferta());
	}

	@Override
	public void delete(long id) {
		// TODO Auto-generated method stub
		sql = "update oferta set status=0 WHERE idOferta=?";
		jdbc.update(sql, id);
		System.out.println("idOferta:> " + id);
		if (modalidad(id) >= 2) {
			System.out.println("si entre 1");
			deleteEquipoOferta(id);
			//jdbc.update(sql, id);
		} else {
			System.out.println("si entre persona");
			deletePersonaOferta(id);
			
			System.out.println("pase oferta");
		}

	}

	public void deletePersonaOferta(long id) {

		sql = "update inscripcionpersona set status=0 Where oferta=?";
		jdbc.update(sql, id);
	}

	public void deleteEquipoOferta(long id) {
		sql = "update inscripcionequipo set status=0 Where oferta=?";
		jdbc.update(sql, id);
	}

	public long modalidad(long id) {
		sql = "select modalidad.idModalidad from modalidad "
				+ "inner join evento on modalidad.idModalidad = evento.modalidad "
				+ "inner join oferta on oferta.evento = evento.idEvento where idOferta=?";
		long modalidad = this.jdbc.queryForLong(sql, new Object[] { id });
		System.out.println("modalidad:> " + modalidad);
		return modalidad;
	}

	@Override
	public List<Oferta> findAll() {
		// TODO Auto-generated method stub
		sql = "select * from oferta " + "inner join evento on evento = idEvento " + "inner join tipo on tipo = idTipo "
				+ "inner join modalidad on modalidad = idModalidad WHERE oferta.status=1";
		List<Oferta> ofertas = this.jdbc.query(sql, new RowMapper() {

			@Override
			public Object mapRow(ResultSet rs, int rowNum) throws SQLException {

				Oferta o = new Oferta();
				o.setIdOferta(new Long(rs.getInt("idOferta")));
				o.setFechaInicio(rs.getDate("fechaInicio"));
				o.setFechaFin(rs.getDate("fechaFin"));
				o.setFechaInicioRegistro(rs.getDate("fechaInicioRegistro"));
				o.setFechaFinRegistro(rs.getDate("fechaFinRegistro"));
				o.setDetalles(rs.getString("detalles"));
				o.setStatus(new Long(rs.getInt("status")));

				Evento e = new Evento();
				e.setIdEvento(rs.getLong("idEvento"));
				e.setNombre(rs.getString("evento.nombre"));
				e.setCapacidadMinima(new Long(rs.getInt("capacidadMinima")));
				e.setCapacidadMaxima(new Long(rs.getInt("capacidadMaxima")));
				e.setCosto(rs.getDouble("costo"));
				e.setDescripcion(rs.getString("descripcion"));
				e.setEdadMinima(new Long(rs.getInt("edadMinima")));
				e.setImagen(rs.getString("imagen"));
				e.setPublicoObjetivo(rs.getString("publicoObjetivo"));
				e.setStatus(new Long(rs.getInt("status")));
				Modalidad m = new Modalidad();
				m.setIdModalidad(new Long(rs.getInt("idModalidad")));
				m.setNombre(rs.getString("modalidad.nombre"));

				Tipo t = new Tipo();
				t.setIdTipo(new Long(rs.getInt("idTipo")));
				t.setNombre(rs.getString("tipo.nombre"));

				e.setModalidad(m);
				e.setTipo(t);
				o.setEvento(e);

				o.setHoraInicio(rs.getTime("horaInicio"));
				o.setHoraFin(rs.getTime("horaFin"));

				return o;
			}

		});
		return ofertas;
	}

	@Override
	public Oferta findById(long id) {
		// TODO Auto-generated method stub
		sql = "select * from oferta " 
				+ "inner join evento on evento = idEvento " 
				+ "inner join tipo on tipo = idTipo "
				+ "inner join modalidad on modalidad = idModalidad " 
				+ "where idOferta = ?";
		Oferta oferta = this.jdbc.queryForObject(sql, new Object[] { id }, new RowMapper<Oferta>() {

			@Override
			public Oferta mapRow(ResultSet rs, int rowNum) throws SQLException {
				Oferta o = new Oferta();
				o.setIdOferta(new Long(rs.getInt("idOferta")));
				o.setFechaInicio(rs.getDate("fechaInicio"));
				o.setFechaFin(rs.getDate("fechaFin"));
				o.setFechaInicioRegistro(rs.getDate("fechaInicioRegistro"));

				o.setFechaFinRegistro(rs.getDate("fechaFinRegistro"));
				o.setDetalles(rs.getString("detalles"));
				o.setStatus(new Long(rs.getInt("status")));

				Evento e = new Evento();
				e.setIdEvento(rs.getLong("idEvento"));
				e.setNombre(rs.getString("evento.nombre"));
				e.setCapacidadMinima(new Long(rs.getInt("capacidadMinima")));
				e.setCapacidadMaxima(new Long(rs.getInt("capacidadMaxima")));
				e.setCosto(rs.getDouble("costo"));
				e.setDescripcion(rs.getString("descripcion"));
				e.setEdadMinima(new Long(rs.getInt("edadMinima")));
				e.setPublicoObjetivo(rs.getString("publicoObjetivo"));
				e.setStatus(new Long(rs.getInt("status")));
				Modalidad m = new Modalidad();
				m.setIdModalidad(new Long(rs.getInt("idModalidad")));
				m.setNombre(rs.getString("modalidad.nombre"));

				Tipo t = new Tipo();
				t.setIdTipo(new Long(rs.getInt("idTipo")));
				t.setNombre(rs.getString("tipo.nombre"));

				e.setModalidad(m);
				e.setTipo(t);
				o.setEvento(e);

				o.setHoraInicio(rs.getTime("horaInicio"));
				o.setHoraFin(rs.getTime("horaFin"));

				return o;
			}

		});
		return oferta;
	}

	@Override
	public List<Oferta> findByDate(Date date) {
		// TODO Auto-generated method stub
		sql = "select * from oferta " + "inner join evento on evento = idEvento " + "inner join tipo on tipo = idTipo "
				+ "inner join modalidad on modalidad = idModalidad " + "where fechaInicio = ?";
		List<Oferta> ofertas = this.jdbc.query(sql, new Object[] { date }, new RowMapper() {

			@Override
			public Object mapRow(ResultSet rs, int rowNum) throws SQLException {
				// TODO Auto-generated method stub
				Oferta o = new Oferta();
				o.setIdOferta(new Long(rs.getInt("idOferta")));
				o.setFechaInicio(rs.getDate("fechaInicio"));
				o.setFechaFin(rs.getDate("fechaFin"));
				o.setFechaInicioRegistro(rs.getDate("fechaInicioRegistro"));
				o.setFechaFinRegistro(rs.getDate("fechaFinRegistro"));
				o.setDetalles(rs.getString("detalles"));
				o.setStatus(new Long(rs.getInt("status")));

				Evento e = new Evento();
				e.setIdEvento(rs.getLong("idEvento"));
				e.setNombre(rs.getString("evento.nombre"));
				e.setCapacidadMinima(new Long(rs.getInt("capacidadMinima")));
				e.setCapacidadMaxima(new Long(rs.getInt("capacidadMaxima")));
				e.setCosto(rs.getDouble("costo"));
				e.setDescripcion(rs.getString("descripcion"));
				e.setEdadMinima(new Long(rs.getInt("edadMinima")));
				e.setPublicoObjetivo(rs.getString("publicoObjetivo"));
				e.setStatus(new Long(rs.getInt("status")));
				Modalidad m = new Modalidad();
				m.setIdModalidad(new Long(rs.getInt("idModalidad")));
				m.setNombre(rs.getString("modalidad.nombre"));

				Tipo t = new Tipo();
				t.setIdTipo(new Long(rs.getInt("idTipo")));
				t.setNombre(rs.getString("tipo.nombre"));

				e.setModalidad(m);
				e.setTipo(t);
				o.setEvento(e);

				o.setHoraInicio(rs.getTime("horaInicio"));
				o.setHoraFin(rs.getTime("horaFin"));

				return o;
			}

		});
		return ofertas;
	}

	@Override
	public List<Oferta> findByEvento(long id) {
		// TODO Auto-generated method stub
		sql = "select * from oferta " + "inner join evento on evento = idEvento " + "inner join tipo on tipo = idTipo "
				+ "inner join modalidad on modalidad = idModalidad " + "where evento = ?";
		List<Oferta> ofertas = this.jdbc.query(sql, new Object[] { id }, new RowMapper() {

			@Override
			public Object mapRow(ResultSet rs, int rowNum) throws SQLException {
				// TODO Auto-generated method stub
				Oferta o = new Oferta();
				o.setIdOferta(new Long(rs.getInt("idOferta")));
				o.setFechaInicio(rs.getDate("fechaInicio"));
				o.setFechaFin(rs.getDate("fechaFin"));
				o.setFechaInicioRegistro(rs.getDate("fechaInicioRegistro"));
				o.setFechaFinRegistro(rs.getDate("fechaFinRegistro"));
				o.setDetalles(rs.getString("detalles"));
				o.setStatus(new Long(rs.getInt("status")));

				Evento e = new Evento();
				e.setIdEvento(rs.getLong("idEvento"));
				e.setNombre(rs.getString("evento.nombre"));
				e.setCapacidadMinima(new Long(rs.getInt("capacidadMinima")));
				e.setCapacidadMaxima(new Long(rs.getInt("capacidadMaxima")));
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
				o.setEvento(e);

				o.setHoraInicio(rs.getTime("horaInicio"));
				o.setHoraFin(rs.getTime("horaFin"));

				return o;
			}

		});
		return ofertas;
	}

	@Override
	public List<Oferta> findByStatus(long status) {
		// TODO Auto-generated method stub
		sql = "select * from oferta " + "inner join evento on evento = idEvento " + "inner join tipo on tipo = idTipo "
				+ "inner join modalidad on modalidad = idModalidad " + "where oferta.status = ?";
		List<Oferta> ofertas = this.jdbc.query(sql, new Object[] { status }, new RowMapper() {

			@Override
			public Object mapRow(ResultSet rs, int rowNum) throws SQLException {
				// TODO Auto-generated method stub
				Oferta o = new Oferta();
				o.setIdOferta(new Long(rs.getInt("idOferta")));
				o.setFechaInicio(rs.getDate("fechaInicio"));
				o.setFechaFin(rs.getDate("fechaFin"));
				o.setFechaInicioRegistro(rs.getDate("fechaInicioRegistro"));
				o.setDetalles(rs.getString("detalles"));
				o.setFechaFinRegistro(rs.getDate("fechaFinRegistro"));
				o.setStatus(new Long(rs.getInt("status")));

				Evento e = new Evento();
				e.setIdEvento(rs.getLong("idEvento"));
				e.setNombre(rs.getString("evento.nombre"));
				e.setCapacidadMinima(new Long(rs.getInt("capacidadMinima")));
				e.setCapacidadMaxima(new Long(rs.getInt("capacidadMaxima")));
				e.setCosto(rs.getDouble("costo"));
				e.setDescripcion(rs.getString("descripcion"));
				e.setEdadMinima(new Long(rs.getInt("edadMinima")));
				e.setPublicoObjetivo(rs.getString("publicoObjetivo"));
				e.setStatus(new Long(rs.getInt("status")));
				Modalidad m = new Modalidad();
				m.setIdModalidad(new Long(rs.getInt("idModalidad")));
				m.setNombre(rs.getString("modalidad.nombre"));

				Tipo t = new Tipo();
				t.setIdTipo(new Long(rs.getInt("idTipo")));
				t.setNombre(rs.getString("tipo.nombre"));

				e.setModalidad(m);
				e.setTipo(t);
				o.setEvento(e);

				o.setHoraInicio(rs.getTime("horaInicio"));
				o.setHoraFin(rs.getTime("horaFin"));

				return o;
			}

		});
		return ofertas;
	}

	@Override
	public List<Oferta> ofertasPesona() {
		// TODO Auto-generated method stub
		sql = "select * from oferta " + "inner join evento on evento = idEvento " + "inner join tipo on tipo = idTipo "
				+ "inner join modalidad on modalidad = idModalidad " + "where idModalidad = 1 and oferta.status=1";
		List<Oferta> ofertas = this.jdbc.query(sql, new RowMapper() {

			@Override
			public Object mapRow(ResultSet rs, int rowNum) throws SQLException {

				Oferta o = new Oferta();
				o.setIdOferta(new Long(rs.getInt("idOferta")));
				o.setFechaInicio(rs.getDate("fechaInicio"));
				o.setFechaFin(rs.getDate("fechaFin"));
				o.setFechaInicioRegistro(rs.getDate("fechaInicioRegistro"));
				o.setFechaFinRegistro(rs.getDate("fechaFinRegistro"));
				o.setDetalles(rs.getString("detalles"));
				o.setStatus(new Long(rs.getInt("status")));

				Evento e = new Evento();
				e.setIdEvento(rs.getLong("idEvento"));
				e.setNombre(rs.getString("evento.nombre"));
				e.setCapacidadMinima(new Long(rs.getInt("capacidadMinima")));
				e.setCapacidadMaxima(new Long(rs.getInt("capacidadMaxima")));
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
				o.setEvento(e);

				o.setHoraInicio(rs.getTime("horaInicio"));
				o.setHoraFin(rs.getTime("horaFin"));

				return o;
			}

		});
		return ofertas;
	}

	@Override
	public List<Oferta> ofertasEquipo() {

		sql = "select * from oferta " + "inner join evento on evento = idEvento " + "inner join tipo on tipo = idTipo "
				+ "inner join modalidad on modalidad = idModalidad "
				+ "where idModalidad = 2 or idModalidad = 3 and oferta.status=1";

		List<Oferta> ofertas = this.jdbc.query(sql, new RowMapper() {

			@Override
			public Object mapRow(ResultSet rs, int rowNum) throws SQLException {

				Oferta o = new Oferta();
				o.setIdOferta(new Long(rs.getInt("idOferta")));
				o.setFechaInicio(rs.getDate("fechaInicio"));
				o.setFechaFin(rs.getDate("fechaFin"));
				o.setFechaInicioRegistro(rs.getDate("fechaInicioRegistro"));
				o.setFechaFinRegistro(rs.getDate("fechaFinRegistro"));
				o.setDetalles(rs.getString("detalles"));
				o.setStatus(new Long(rs.getInt("status")));

				Evento e = new Evento();
				e.setIdEvento(rs.getLong("idEvento"));
				e.setNombre(rs.getString("evento.nombre"));
				e.setCapacidadMinima(new Long(rs.getInt("capacidadMinima")));
				e.setCapacidadMaxima(new Long(rs.getInt("capacidadMaxima")));
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
				o.setEvento(e);

				o.setHoraInicio(rs.getTime("horaInicio"));
				o.setHoraFin(rs.getTime("horaFin"));

				return o;
			}

		});
		return ofertas;
	}
////////////////////////////////////////////
	@Override
	public List<Oferta> ofertasPesonaBySearchword(String searchword) {
		// TODO Auto-generated method stub
		sql = "select * from oferta " 
				+ "inner join evento on evento = idEvento " 
				+ "inner join tipo on tipo = idTipo "
				+ "inner join modalidad on modalidad = idModalidad " 
				+ "where idModalidad = 1 and oferta.status=1 and evento.nombre LIKE ('%' ? '%')";
		List<Oferta> ofertas = this.jdbc.query(sql,new Object[] {searchword}, new RowMapper() {

			@Override
			public Object mapRow(ResultSet rs, int rowNum) throws SQLException {

				Oferta o = new Oferta();
				o.setIdOferta(new Long(rs.getInt("idOferta")));
				o.setFechaInicio(rs.getDate("fechaInicio"));
				o.setFechaFin(rs.getDate("fechaFin"));
				o.setFechaInicioRegistro(rs.getDate("fechaInicioRegistro"));
				o.setFechaFinRegistro(rs.getDate("fechaFinRegistro"));
				o.setDetalles(rs.getString("detalles"));
				o.setStatus(new Long(rs.getInt("status")));

				Evento e = new Evento();
				e.setIdEvento(rs.getLong("idEvento"));
				e.setNombre(rs.getString("evento.nombre"));
				e.setCapacidadMinima(new Long(rs.getInt("capacidadMinima")));
				e.setCapacidadMaxima(new Long(rs.getInt("capacidadMaxima")));
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
				o.setEvento(e);

				o.setHoraInicio(rs.getTime("horaInicio"));
				o.setHoraFin(rs.getTime("horaFin"));

				return o;
			}

		});
		return ofertas;
	}

	@Override
	public List<Oferta> ofertasEquipoBySearchword(String searchword) {

		sql = "select * from oferta " 
				+ "inner join evento on evento = idEvento " 
				+ "inner join tipo on tipo = idTipo "
				+ "inner join modalidad on modalidad = idModalidad "
				+ "where (idModalidad = 2 or idModalidad = 3) and (oferta.status=1) and (evento.nombre LIKE ('%' ? '%'))";

		List<Oferta> ofertas = this.jdbc.query(sql,new Object[] {searchword} ,new RowMapper() {

			@Override
			public Object mapRow(ResultSet rs, int rowNum) throws SQLException {

				Oferta o = new Oferta();
				o.setIdOferta(new Long(rs.getInt("idOferta")));
				o.setFechaInicio(rs.getDate("fechaInicio"));
				o.setFechaFin(rs.getDate("fechaFin"));
				o.setFechaInicioRegistro(rs.getDate("fechaInicioRegistro"));
				o.setFechaFinRegistro(rs.getDate("fechaFinRegistro"));
				o.setDetalles(rs.getString("detalles"));
				o.setStatus(new Long(rs.getInt("status")));

				Evento e = new Evento();
				e.setIdEvento(rs.getLong("idEvento"));
				e.setNombre(rs.getString("evento.nombre"));
				e.setCapacidadMinima(new Long(rs.getInt("capacidadMinima")));
				e.setCapacidadMaxima(new Long(rs.getInt("capacidadMaxima")));
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
				o.setEvento(e);

				o.setHoraInicio(rs.getTime("horaInicio"));
				o.setHoraFin(rs.getTime("horaFin"));

				return o;
			}

		});
		return ofertas;
	}
}
