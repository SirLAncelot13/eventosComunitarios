package mx.gob.eventosComunitarios.service;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

import javax.sql.DataSource;

import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;

import mx.gob.eventosComunitarios.dao.InscripcionEquipoInterface;
import mx.gob.eventosComunitarios.entity.Evento;
import mx.gob.eventosComunitarios.entity.InscripcionEquipo;
import mx.gob.eventosComunitarios.entity.Modalidad;
import mx.gob.eventosComunitarios.entity.Oferta;
import mx.gob.eventosComunitarios.entity.Rol;
import mx.gob.eventosComunitarios.entity.Tipo;
import mx.gob.eventosComunitarios.entity.Usuario;

public class InscripcionEquipoService implements InscripcionEquipoInterface {

	private JdbcTemplate jdbc;
	String sql;

	public InscripcionEquipoService(DataSource dataSource) {
		// TODO Auto-generated constructor stub
		this.jdbc = new JdbcTemplate(dataSource);
	}

	@Override
	public void save(InscripcionEquipo team) {
		System.out.println("Controller");
		// TODO Auto-generated method stub
		sql = "INSERT INTO inscripcionequipo(usuario, nombre, fechaInscripcion, oferta,status)values(?,?,?,?,?)";
		jdbc.update(sql, team.getUsuario().getIdUsuario(), team.getNombre(), team.getFechaInscripcion(),
				team.getOferta().getIdOferta(), team.getStatus());
	}

	@Override
	public void update(InscripcionEquipo team) {
		sql = "UPDATE inscripcionequipo SET usuario = ?, nombre =?,  fechaInscripcion =?, oferta=?, status=0 WHERE idEquipo = ?";
		jdbc.update(sql, team.getUsuario().getIdUsuario(), team.getNombre(), team.getFechaInscripcion(),
				team.getOferta().getIdOferta(), team.getIdEquipo());
	}

	@Override
	public void delete(long id) {
		// TODO Auto-generated method stub
		sql = "DELETE FROM inscripcionequipo WHERE idEquipo = ?";
		jdbc.update(sql, id);
	}

	@Override
	public List<InscripcionEquipo> findAll(long id) {

		sql = "select * from tipo " + "inner join evento on evento.tipo = idTipo "
				+ "inner join modalidad on evento.modalidad = idModalidad "
				+ "inner join oferta on idEvento = oferta.evento "
				+ "inner join inscripcionequipo on inscripcionequipo.oferta = idOferta "
				+ "inner join usuario on inscripcionequipo.usuario = idUsuario "
				+ "inner join rol on rol.idRol = usuario.rol where idUsuario = ? and inscripcionequipo.status =1";
		List<InscripcionEquipo> equipos = this.jdbc.query(sql, new Object[] { id }, new RowMapper<InscripcionEquipo>() {

			@Override
			public InscripcionEquipo mapRow(ResultSet rs, int rowNum) throws SQLException {
				InscripcionEquipo p = new InscripcionEquipo();
				p.setIdEquipo(rs.getLong("idEquipo"));
				p.setFechaInscripcion(rs.getDate("fechaInscripcion"));
				p.setNombre(rs.getString("inscripcionequipo.nombre"));

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

				Oferta o = new Oferta();
				o.setIdOferta(rs.getLong("idOferta"));
				o.setFechaInicio(rs.getDate("fechaInicio"));
				o.setFechaFin(rs.getDate("fechaFin"));
				o.setFechaInicioRegistro(rs.getDate("fechaInicioRegistro"));
				o.setFechaFinRegistro(rs.getDate("fechaFinRegistro"));

				o.setHoraInicio(rs.getTime("horaInicio"));
				o.setHoraFin(rs.getTime("horaFin"));
				o.setDetalles(rs.getString("detalles"));
				o.setStatus(rs.getInt("oferta.status"));

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
				o.setEvento(e);
				p.setOferta(o);
				return p;
			}

		});
		return equipos;

	}

	@Override
	public InscripcionEquipo findById(long id) {
		// TODO Auto-generated method stub
		sql = "select * from tipo " + "inner join evento on evento.tipo = idTipo "
				+ "inner join modalidad on evento.modalidad = idModalidad "
				+ "inner join oferta on idEvento = oferta.evento "
				+ "inner join inscripcionequipo on inscripcionequipo.oferta = idOferta "
				+ "inner join usuario on inscripcionequipo.usuario = usuario.idUsuario "
				+ "inner join rol on rol.idRol = usuario.rol where idEquipo = ?";
		InscripcionEquipo inscripcionEquipo = this.jdbc.queryForObject(sql, new Object[] { id },
				new RowMapper<InscripcionEquipo>() {

					@Override
					public InscripcionEquipo mapRow(ResultSet rs, int rowNum) throws SQLException {
						InscripcionEquipo eq = new InscripcionEquipo();

						eq.setIdEquipo(rs.getLong("idEquipo"));
						eq.setFechaInscripcion(rs.getDate("fechaInscripcion"));
						eq.setNombre(rs.getString("inscripcionequipo.nombre"));
						eq.setStatus(rs.getLong("inscripcionequipo.status"));

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
						eq.setUsuario(u);
						eq.setOferta(o);

						return eq;
					}

				});
		return inscripcionEquipo;
	}

	@Override
	public InscripcionEquipo findByUser(long idUser, long idEvent) {
		// TODO Auto-generated method stub
		sql = "SELECT * FROM inscripcionequipo WHERE usuario = ? AND evento = ?";
		return jdbc.queryForObject(sql, new Object[] { idUser, idEvent },
				BeanPropertyRowMapper.newInstance(InscripcionEquipo.class));
	}

	@Override
	public long countPeople(long id) {
		sql = "select count(*) from inscripcionequipo where oferta = ? and status = 1";
		long count = this.jdbc.queryForLong(sql, new Object[] {id});
	return count;
	}

	@Override
	public List<InscripcionEquipo> historial(long id) {
		sql = "select * from tipo " + "inner join evento on evento.tipo = idTipo "
				+ "inner join modalidad on evento.modalidad = idModalidad "
				+ "inner join oferta on idEvento = oferta.evento "
				+ "inner join inscripcionequipo on inscripcionequipo.oferta = idOferta "
				+ "inner join usuario on inscripcionequipo.usuario = idUsuario "
				+ "inner join rol on rol.idRol = usuario.rol where idUsuario = ?";
		List<InscripcionEquipo> equipos = this.jdbc.query(sql, new Object[] { id }, new RowMapper<InscripcionEquipo>() {

			@Override
			public InscripcionEquipo mapRow(ResultSet rs, int rowNum) throws SQLException {
				InscripcionEquipo p = new InscripcionEquipo();
				p.setIdEquipo(rs.getLong("idEquipo"));
				p.setFechaInscripcion(rs.getDate("fechaInscripcion"));
				p.setNombre(rs.getString("inscripcionequipo.nombre"));

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

				Oferta o = new Oferta();
				o.setIdOferta(rs.getLong("idOferta"));
				o.setFechaInicio(rs.getDate("fechaInicio"));
				o.setFechaFin(rs.getDate("fechaFin"));
				o.setFechaInicioRegistro(rs.getDate("fechaInicioRegistro"));
				o.setFechaFinRegistro(rs.getDate("fechaFinRegistro"));

				o.setHoraInicio(rs.getTime("horaInicio"));
				o.setHoraFin(rs.getTime("horaFin"));
				o.setDetalles(rs.getString("detalles"));
				o.setStatus(rs.getInt("oferta.status"));

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
				o.setEvento(e);
				p.setOferta(o);
				return p;
			}

		});
		return equipos;

	}

}
