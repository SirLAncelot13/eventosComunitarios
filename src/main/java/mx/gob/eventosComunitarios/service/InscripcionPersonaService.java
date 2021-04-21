package mx.gob.eventosComunitarios.service;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

import javax.sql.DataSource;

import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;

import mx.gob.eventosComunitarios.dao.InscripcionPersonaInterface;
import mx.gob.eventosComunitarios.entity.Evento;
import mx.gob.eventosComunitarios.entity.InscripcionPersona;
import mx.gob.eventosComunitarios.entity.Modalidad;
import mx.gob.eventosComunitarios.entity.Oferta;
import mx.gob.eventosComunitarios.entity.Rol;
import mx.gob.eventosComunitarios.entity.Tipo;
import mx.gob.eventosComunitarios.entity.Usuario;

public class InscripcionPersonaService implements InscripcionPersonaInterface {

	private JdbcTemplate jdbc;
	String sql;

	public InscripcionPersonaService(DataSource dataSource) {
		// TODO Auto-generated constructor stub
		this.jdbc = new JdbcTemplate(dataSource);
	}

	@Override
	public void save(InscripcionPersona people) {
		System.out.println("Entre al save");
		// TODO Auto-generated method stub
		sql = "INSERT INTO inscripcionpersona(usuario, fechaInscripcion, oferta)values(?,?,?)";
		jdbc.update(sql, people.getUsuario().getIdUsuario(), people.getFechaInscripcion(),
				people.getOferta().getIdOferta());
	}

	@Override
	public void delete(long id) {
		// TODO Auto-generated method stub
		sql = "DELETE FROM inscripcionpersona WHERE idPersona = ?";
		jdbc.update(sql, id);
	}

	@Override
	public List<InscripcionPersona> findAll(long id) {
		// TODO Auto-generated method stub
		sql = "select * from tipo " + "inner join evento on evento.tipo = idTipo "
				+ "inner join modalidad on evento.modalidad = idModalidad "
				+ "inner join oferta on idEvento = oferta.evento "
				+ "inner join inscripcionpersona on inscripcionpersona.oferta = idOferta "
				+ "inner join usuario on inscripcionpersona.usuario = idUsuario "
				+ "inner join rol on rol.idRol = usuario.rol where idUsuario = ? and inscripcionpersona.status =1";

		List<InscripcionPersona> inscripcionesPersona = this.jdbc.query(sql, new Object[] { id },
				new RowMapper<InscripcionPersona>() {

					@Override
					public InscripcionPersona mapRow(ResultSet rs, int rowNum) throws SQLException {
						// TODO Auto-generated method stub
						InscripcionPersona p = new InscripcionPersona();
						p.setIdPersona(rs.getLong("idPersona"));
						p.setFechaInscripcion(rs.getDate("fechaInscripcion"));
						p.setStatus(rs.getLong("inscripcionpersona.status"));
						Usuario u = new Usuario();
						u.setIdUsuario(rs.getLong("idUsuario"));
						u.setNombre(rs.getString("usuario.nombre"));
						u.setApellidoPaterno(rs.getString("apellidoPaterno"));
						u.setApellidoMaterno(rs.getString("apellidoMaterno"));
						u.setTelefono(rs.getString("telefono"));
						u.setEdad(rs.getInt("edad"));
						u.setGenero(rs.getLong("genero"));
						u.setDireccion(rs.getString("direccion"));
						u.setCorreo(rs.getString("correo"));
						u.setContrasena(rs.getString("contrasena"));
						u.setStatus(rs.getInt("usuario.status"));
						Rol r = new Rol();
						r.setIdRol(rs.getLong("idRol"));
						r.setNombre(rs.getString("rol.nombre"));

						u.setRol(r);

						p.setUsuario(u);

						Evento e = new Evento();
						e.setIdEvento(rs.getLong("idEvento"));
						e.setNombre(rs.getString("evento.nombre"));
						Tipo t = new Tipo();
						t.setIdTipo(rs.getLong("idTipo"));
						t.setNombre(rs.getString("tipo.nombre"));
						e.setTipo(t);
						e.setCapacidadMinima(rs.getInt("capacidadMinima"));
						e.setCapacidadMaxima(rs.getInt("capacidadMaxima"));
						e.setCosto(rs.getDouble("costo"));
						e.setDescripcion(rs.getString("descripcion"));
						e.setEdadMinima(rs.getInt("edadMinima"));
						e.setPublicoObjetivo(rs.getString("publicoObjetivo"));
						e.setStatus(rs.getInt("evento.status"));
						e.setImagen(rs.getString("imagen"));
						
						Modalidad m = new Modalidad();
						m.setIdModalidad(rs.getLong("idModalidad"));
						m.setNombre(rs.getString("modalidad.nombre"));

						e.setModalidad(m);
						Oferta o = new Oferta();
						o.setIdOferta(rs.getLong("idOferta"));
						o.setFechaInicio(rs.getDate("fechaInicio"));
						o.setFechaFin(rs.getDate("fechaFin"));
						o.setFechaInicioRegistro(rs.getDate("fechaInicioRegistro"));
						o.setFechaFinRegistro(rs.getDate("fechaFinRegistro"));
						o.setEvento(e);
						o.setHoraInicio(rs.getTime("horaInicio"));
						o.setHoraFin(rs.getTime("horaFin"));
						o.setDetalles(rs.getString("detalles"));
						o.setStatus(rs.getInt("oferta.status"));
						p.setOferta(o);
						return p;
					}

				});
		return inscripcionesPersona;

	}

	@Override
	public long countPeople(long id) {
		// TODO Auto-generated method stub
		sql="SELECT count(*) FROM inscripcionpersona where oferta =? and status = 1";
		long count = this.jdbc.queryForLong(sql, new Object[] {id});
		return count;
	}

	@Override
	public InscripcionPersona findByUser(long idUser, long idEvent) {
		// TODO Auto-generated method stub
		sql = "SELECT * fROM inscripcionpersona WHERE usuario =? AND evento = ?";
		return this.jdbc.queryForObject(sql, new Object[] { idUser, idEvent },
				BeanPropertyRowMapper.newInstance(InscripcionPersona.class));
	}

	@Override
	public void update(InscripcionPersona people) {
		// TODO Auto-generated method stub
		sql ="UPDATE inscripcionpersona SET usuario = ?, fechaInscripcion =?, oferta=?, status=0 WHERE idPersona = ?";
		jdbc.update(sql,people.getUsuario().getIdUsuario(),people.getFechaInscripcion(),people.getOferta().getIdOferta(),people.getIdPersona());
		

	}

	@Override
	public InscripcionPersona findById(long id) {
		sql="select * from tipo "
				+ "inner join evento on evento.tipo = idTipo "
				+ "inner join modalidad on evento.modalidad = idModalidad "
				+ "inner join oferta on idEvento = oferta.evento "
				+ "inner join inscripcionpersona on inscripcionpersona.oferta = idOferta "
				+ "inner join usuario on inscripcionpersona.usuario = usuario.idUsuario "
				+ "inner join rol on rol.idRol = usuario.rol where idPersona = ?";
		
		InscripcionPersona inscripcionPersona =  this.jdbc.queryForObject(sql,new Object[] {id}, new RowMapper<InscripcionPersona>() {

			@Override
			public InscripcionPersona mapRow(ResultSet rs, int rowNum) throws SQLException {
				InscripcionPersona p = new InscripcionPersona();
				p.setIdPersona(rs.getLong("idPersona"));
				p.setFechaInscripcion(rs.getDate("fechaInscripcion"));	
				p.setStatus(rs.getLong("inscripcionpersona.status"));
				
				Usuario u = new Usuario();
				u.setIdUsuario(rs.getLong("idUsuario"));
				u.setNombre(rs.getString("usuario.nombre"));
				u.setApellidoPaterno(rs.getString("apellidoPaterno"));
				u.setApellidoMaterno(rs.getString("apellidoMaterno"));
				u.setTelefono(rs.getString("telefono"));
				u.setEdad(rs.getInt("edad"));
				u.setGenero(rs.getLong("genero"));
				u.setDireccion(rs.getString("direccion"));
				u.setCorreo(rs.getString("correo"));
				u.setContrasena(rs.getString("contrasena"));
				u.setStatus(rs.getInt("usuario.status"));
				Rol r = new Rol();
				r.setIdRol(rs.getLong("idRol"));
				r.setNombre(rs.getString("rol.nombre"));

				
				Evento e = new Evento();
				e.setIdEvento(rs.getLong("idEvento"));
				e.setNombre(rs.getString("evento.nombre"));
				Tipo t = new Tipo();
				t.setIdTipo(rs.getLong("idTipo"));
				t.setNombre(rs.getString("tipo.nombre"));
				e.setTipo(t);
				e.setCapacidadMinima(rs.getInt("capacidadMinima"));
				e.setCapacidadMaxima(rs.getInt("capacidadMaxima"));
				e.setCosto(rs.getDouble("costo"));
				e.setDescripcion(rs.getString("descripcion"));
				e.setEdadMinima(rs.getInt("edadMinima"));
				e.setPublicoObjetivo(rs.getString("publicoObjetivo"));
				e.setStatus(rs.getInt("evento.status"));
				Modalidad m = new Modalidad();
				m.setIdModalidad(rs.getLong("idModalidad"));
				m.setNombre(rs.getString("modalidad.nombre"));
				
				
				Oferta o = new Oferta();
				o.setIdOferta(rs.getLong("idOferta"));
				o.setFechaInicio(rs.getDate("fechaInicio"));
				o.setFechaFin(rs.getDate("fechaFin"));
				o.setFechaInicioRegistro(rs.getDate("fechaInicioRegistro"));
				o.setFechaFinRegistro(rs.getDate("fechaFinRegistro"));
				o.setEvento(e);
				o.setHoraInicio(rs.getTime("horaInicio"));
				o.setHoraFin(rs.getTime("horaFin"));
				o.setDetalles(rs.getString("detalles"));
				o.setStatus(rs.getInt("oferta.status"));	
				
				e.setModalidad(m);
				u.setRol(r);
				p.setUsuario(u);
				p.setOferta(o);
				
				return p;
			}
			
		});
		return inscripcionPersona;
	}

	@Override
	public List<InscripcionPersona> historial(long id) {
		sql = "select * from tipo " + "inner join evento on evento.tipo = idTipo "
				+ "inner join modalidad on evento.modalidad = idModalidad "
				+ "inner join oferta on idEvento = oferta.evento "
				+ "inner join inscripcionpersona on inscripcionpersona.oferta = idOferta "
				+ "inner join usuario on inscripcionpersona.usuario = idUsuario "
				+ "inner join rol on rol.idRol = usuario.rol where idUsuario = ?";
		List<InscripcionPersona> inscripcionesPersona = this.jdbc.query(sql, new Object[] { id },
				new RowMapper<InscripcionPersona>() {

					@Override
					public InscripcionPersona mapRow(ResultSet rs, int rowNum) throws SQLException {
						// TODO Auto-generated method stub
						InscripcionPersona p = new InscripcionPersona();
						p.setIdPersona(rs.getLong("idPersona"));
						p.setFechaInscripcion(rs.getDate("fechaInscripcion"));
						p.setStatus(rs.getLong("inscripcionpersona.status"));
						Usuario u = new Usuario();
						u.setIdUsuario(rs.getLong("idUsuario"));
						u.setNombre(rs.getString("usuario.nombre"));
						u.setApellidoPaterno(rs.getString("apellidoPaterno"));
						u.setApellidoMaterno(rs.getString("apellidoMaterno"));
						u.setTelefono(rs.getString("telefono"));
						u.setEdad(rs.getInt("edad"));
						u.setGenero(rs.getLong("genero"));
						u.setDireccion(rs.getString("direccion"));
						u.setCorreo(rs.getString("correo"));
						u.setContrasena(rs.getString("contrasena"));
						u.setStatus(rs.getInt("usuario.status"));
						Rol r = new Rol();
						r.setIdRol(rs.getLong("idRol"));
						r.setNombre(rs.getString("rol.nombre"));

						u.setRol(r);

						p.setUsuario(u);

						Evento e = new Evento();
						e.setIdEvento(rs.getLong("idEvento"));
						e.setNombre(rs.getString("evento.nombre"));
						Tipo t = new Tipo();
						t.setIdTipo(rs.getLong("idTipo"));
						t.setNombre(rs.getString("tipo.nombre"));
						e.setTipo(t);
						e.setCapacidadMinima(rs.getInt("capacidadMinima"));
						e.setCapacidadMaxima(rs.getInt("capacidadMaxima"));
						e.setCosto(rs.getDouble("costo"));
						e.setDescripcion(rs.getString("descripcion"));
						e.setEdadMinima(rs.getInt("edadMinima"));
						e.setPublicoObjetivo(rs.getString("publicoObjetivo"));
						e.setStatus(rs.getInt("evento.status"));
						e.setImagen(rs.getString("imagen"));
						
						Modalidad m = new Modalidad();
						m.setIdModalidad(rs.getLong("idModalidad"));
						m.setNombre(rs.getString("modalidad.nombre"));

						e.setModalidad(m);
						Oferta o = new Oferta();
						o.setIdOferta(rs.getLong("idOferta"));
						o.setFechaInicio(rs.getDate("fechaInicio"));
						o.setFechaFin(rs.getDate("fechaFin"));
						o.setFechaInicioRegistro(rs.getDate("fechaInicioRegistro"));
						o.setFechaFinRegistro(rs.getDate("fechaFinRegistro"));
						o.setEvento(e);
						o.setHoraInicio(rs.getTime("horaInicio"));
						o.setHoraFin(rs.getTime("horaFin"));
						o.setDetalles(rs.getString("detalles"));
						o.setStatus(rs.getInt("oferta.status"));
						p.setOferta(o);
						return p;
					}

				});
		return inscripcionesPersona;

	}

	
	}


