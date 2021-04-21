package mx.gob.eventosComunitarios.controller;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import mx.gob.eventosComunitarios.dao.CambioInterface;
import mx.gob.eventosComunitarios.dao.InscripcionEquipoInterface;
import mx.gob.eventosComunitarios.dao.InscripcionPersonaInterface;
import mx.gob.eventosComunitarios.dao.OfertaInterface;
import mx.gob.eventosComunitarios.dao.UsuarioInterface;
import mx.gob.eventosComunitarios.entity.Cambio;
import mx.gob.eventosComunitarios.entity.InscripcionEquipo;
import mx.gob.eventosComunitarios.entity.Oferta;
import mx.gob.eventosComunitarios.entity.Usuario;

@Controller
public class InscripcionEquipoController {

	@Autowired
	private InscripcionEquipoInterface equipoInterface;
	@Autowired
	private UsuarioInterface usuarioInterface;
	@Autowired
	private OfertaInterface OfertaInterface;
	@Autowired
	private CambioInterface cambioInterface;
	@Autowired
	private InscripcionPersonaInterface inscripcionPersonaInterface; 
	/*
	 * @GetMapping("/inscripcionEquipo/all") public String
	 * getEquipo(@ModelAttribute("inscripcionEquipo")InscripcionEquipo
	 * inscripcionEquipo, BindingResult result, Model model) {
	 * List<InscripcionEquipo> equipos =
	 * equipoInterface.findAll(inscripcionEquipo.getUsuario().getIdUsuario());
	 * List<Usuario> usuarios = usuarioInterface.findAll(); List<Oferta> ofertas =
	 * OfertaInterface.findAll(); model.addAttribute("listaUsuario", usuarios);
	 * model.addAttribute("listaOfertas", ofertas);
	 * model.addAttribute("listaEquipo", equipos); return "views/perfil"; }
	 */

	@PostMapping("/inscripcion/equipo/{idOferta}")
	public String createEquipo(@ModelAttribute("inscripcionEquipo") InscripcionEquipo inscripcionEquipo,
			@PathVariable("idOferta") int idOferta, BindingResult result, Model model, HttpServletRequest request) {

		System.out.println("Controller de equipo");
		ZoneId defaultZoneId = ZoneId.systemDefault();
		LocalDate localDate = LocalDate.now();
		Date date = Date.from(localDate.atStartOfDay(defaultZoneId).toInstant());
		inscripcionEquipo.setFechaInscripcion(date);
		inscripcionEquipo.setStatus(1);

		Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		String username = "";
		if (principal instanceof UserDetails) {
			System.out.println("Hallado user");
			username = ((UserDetails) principal).getUsername();
			Usuario owner = usuarioInterface.findByCorreo(username);
			System.out.println("ID owner: " + owner.getIdUsuario());
			inscripcionEquipo.setUsuario(owner);
			inscripcionEquipo.setOferta(OfertaInterface.findById(idOferta));
			List<InscripcionEquipo> inscripciones = equipoInterface.findAll(owner.getIdUsuario());
			if (inscripciones.size() == 0) {
				Cambio c1 = new Cambio();
				c1.setDescripcion("CREATE inscripcionEquipo");
				Date date2 = new Date();
				DateFormat hourdateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
				c1.setFecha(hourdateFormat.format(date2));

				c1.setUsuario(owner);
				final String[] HEADER_LIST = { "X-Forwarded-For", "Proxy-Client-IP", "WL-Proxy-Client-IP",
						"HTTP_X_FORWARDED_FOR", "HTTP_X_FORWARDED", "HTTP_X_CLUSTER_CLIENT_IP", "HTTP_CLIENT_IP",
						"HTTP_FORWARDED_FOR", "HTTP_FORWARDED", "HTTP_VIA", "REMOTE_ADDR" };

				for (String header : HEADER_LIST) {
					String ip = request.getHeader(header);
					if (ip != null && ip.length() != 0 && !"unknown".equalsIgnoreCase(ip)) {
						return ip;
					}
				}
				c1.setHost(request.getRemoteAddr());
				System.out.println(c1.getHost());
				cambioInterface.save(c1);

				equipoInterface.save(inscripcionEquipo);
			} else {
				for (int i = 0; i < inscripciones.size(); i++) {
					if (inscripciones.get(i).getOferta().getIdOferta() == idOferta) {
						System.out.println("Ya estas inscrito en este evento");
						return "redirect:/oferta/inscripciones/all";
					} else {
						Cambio c1 = new Cambio();
						c1.setDescripcion("CREATE inscripcionEquipo");
						Date date2 = new Date();
						DateFormat hourdateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
						c1.setFecha(hourdateFormat.format(date2));

						c1.setUsuario(owner);
						final String[] HEADER_LIST = { "X-Forwarded-For", "Proxy-Client-IP", "WL-Proxy-Client-IP",
								"HTTP_X_FORWARDED_FOR", "HTTP_X_FORWARDED", "HTTP_X_CLUSTER_CLIENT_IP",
								"HTTP_CLIENT_IP", "HTTP_FORWARDED_FOR", "HTTP_FORWARDED", "HTTP_VIA", "REMOTE_ADDR" };

						for (String header : HEADER_LIST) {
							String ip = request.getHeader(header);
							if (ip != null && ip.length() != 0 && !"unknown".equalsIgnoreCase(ip)) {
								return ip;
							}
						}
						c1.setHost(request.getRemoteAddr());
						System.out.println(c1.getHost());
						cambioInterface.save(c1);
						equipoInterface.save(inscripcionEquipo);
					}
				}
			}

		} else {
			System.out.println("No hallado user");
		}

		return "redirect:/oferta/inscripcion/all";
	}

	@GetMapping("/inscripcion/equipo/delete/{idEquipo}")
	public String deleteEquipo(@PathVariable("idEquipo") long idEquipo,
			@ModelAttribute("inscripcionEquipo") InscripcionEquipo equipo, BindingResult result, Model model,
			HttpServletRequest request) {
		System.out.println("idEquipo:> " + idEquipo);
		Cambio c = new Cambio();
		c.setDescripcion("UPDATE inscripcionEquipo");
		Date date = new Date();
		DateFormat hourdateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		c.setFecha(hourdateFormat.format(date));
		Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		String username = "";
		if (principal instanceof UserDetails) {
			System.out.println("5");
			username = ((UserDetails) principal).getUsername();
			System.out.println("6");
			System.out.println(username);
			Usuario owner = usuarioInterface.findByCorreo(username);
			System.out.println("7");
			System.out.println("ID owner: " + owner.getIdUsuario());
			c.setUsuario(owner);

			final String[] HEADER_LIST = { "X-Forwarded-For", "Proxy-Client-IP", "WL-Proxy-Client-IP",
					"HTTP_X_FORWARDED_FOR", "HTTP_X_FORWARDED", "HTTP_X_CLUSTER_CLIENT_IP", "HTTP_CLIENT_IP",
					"HTTP_FORWARDED_FOR", "HTTP_FORWARDED", "HTTP_VIA", "REMOTE_ADDR" };

			for (String header : HEADER_LIST) {
				String ip = request.getHeader(header);
				if (ip != null && ip.length() != 0 && !"unknown".equalsIgnoreCase(ip)) {
					return ip;
				}
			}
			c.setHost(request.getRemoteAddr());
			System.out.println(c.getHost());
			cambioInterface.save(c);
		}
		equipoInterface.update(equipoInterface.findById(idEquipo));
		return "redirect:/inscripcionPersona/all";
	}

	@GetMapping("/equipo/one/{id}")
	public String oneEquipo(@PathVariable("id") long id, @ModelAttribute("inscripcionEquipo") InscripcionEquipo equipo,
			BindingResult result, Model model) {
		equipo = equipoInterface.findById(id);
		model.addAttribute("equipo", equipo);

		List<InscripcionEquipo> equipos = equipoInterface.findAll(id);
		List<Usuario> usuarios = usuarioInterface.findAll();
		List<Oferta> ofertas = OfertaInterface.findAll();
		model.addAttribute("listaUsuario", usuarios);
		model.addAttribute("listaOfertas", ofertas);
		model.addAttribute("listaEquipo", equipos);

		return "views/equipo";
	}


}
