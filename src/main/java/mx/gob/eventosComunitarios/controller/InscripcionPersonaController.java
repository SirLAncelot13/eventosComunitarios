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
import mx.gob.eventosComunitarios.entity.InscripcionPersona;
import mx.gob.eventosComunitarios.entity.Oferta;
import mx.gob.eventosComunitarios.entity.Usuario;

@Controller
public class InscripcionPersonaController {

	@Autowired
	private InscripcionPersonaInterface inscripcionPersonaInterface;
	@Autowired
	private UsuarioInterface usuarioInterface;
	@Autowired
	private OfertaInterface ofertaInterface;
	@Autowired
	private InscripcionEquipoInterface inscripcionEquipo;
	@Autowired
	private CambioInterface cambioInterface;

	@GetMapping("/inscripcionPersona/all")
	public String getPersona(@ModelAttribute("inscripcionPersona") InscripcionPersona persona, BindingResult result,
			Model model) {
		List<InscripcionPersona> personas = null;
		List<InscripcionEquipo> listaEquipo = null;

		Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		String username = "";
		if (principal instanceof UserDetails) {
			username = ((UserDetails) principal).getUsername();
			Usuario owner = usuarioInterface.findByCorreo(username);
			System.out.println("ID owner: " + owner.getIdUsuario());
			personas = inscripcionPersonaInterface.findAll(owner.getIdUsuario());
			listaEquipo = inscripcionEquipo.findAll(owner.getIdUsuario());
			model.addAttribute("listaPersona", personas);
			model.addAttribute("listaEquipo", listaEquipo);
		}

		model.addAttribute("listaPersona", personas);
		model.addAttribute("listaEquipo", listaEquipo);

		return "views/perfil";
	}

	@PostMapping("/inscripcion/persona/{idOferta}")
	public String createPersona(@ModelAttribute("inscripcionPersona") InscripcionPersona inscripcionPersona,
			@PathVariable("idOferta") int idOferta, BindingResult result, Model model, HttpServletRequest request) {
		System.out.println("1");
		ZoneId defaultZoneId = ZoneId.systemDefault();
		LocalDate localDate = LocalDate.now();
		Date date = Date.from(localDate.atStartOfDay(defaultZoneId).toInstant());
		inscripcionPersona.setFechaInscripcion(date);
		inscripcionPersona.setOferta(ofertaInterface.findById(idOferta));
		inscripcionPersona.setStatus(1);
		System.out.println("Controller de persona inscripcion");

		System.out.println("2");

		System.out.println("4");
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
			inscripcionPersona.setUsuario(owner);
			inscripcionPersona.setOferta(ofertaInterface.findById(idOferta));
			List<InscripcionPersona> inscripciones = inscripcionPersonaInterface.findAll(owner.getIdUsuario());
			if (inscripciones.size() == 0) {
				System.out.println("save");
				Cambio c = new Cambio();
				c.setDescripcion("CREATE inscripcionPersona");
				Date date2 = new Date();
				DateFormat hourdateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
				c.setFecha(hourdateFormat.format(date2));

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

				inscripcionPersonaInterface.save(inscripcionPersona);
			} else {
				for (int i = 0; i < inscripciones.size(); i++) {
					System.out.println("3");
					if (inscripciones.get(i).getOferta().getIdOferta() == idOferta) {
						System.out.println("Ya estas inscrito en este evento");
						return "redirect:/oferta/inscripciones/all";
					} else {
						Cambio c = new Cambio();
						c.setDescripcion("CREATE inscripcionPersona");
						Date date2 = new Date();
						DateFormat hourdateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
						c.setFecha(hourdateFormat.format(date2));

						c.setUsuario(owner);
						final String[] HEADER_LIST = { "X-Forwarded-For", "Proxy-Client-IP", "WL-Proxy-Client-IP",
								"HTTP_X_FORWARDED_FOR", "HTTP_X_FORWARDED", "HTTP_X_CLUSTER_CLIENT_IP",
								"HTTP_CLIENT_IP", "HTTP_FORWARDED_FOR", "HTTP_FORWARDED", "HTTP_VIA", "REMOTE_ADDR" };

						for (String header : HEADER_LIST) {
							String ip = request.getHeader(header);
							if (ip != null && ip.length() != 0 && !"unknown".equalsIgnoreCase(ip)) {
								return ip;
							}
						}
						c.setHost(request.getRemoteAddr());
						System.out.println(c.getHost());
						cambioInterface.save(c);
						inscripcionPersonaInterface.save(inscripcionPersona);
					}
				}
			}
		}

		return "redirect:/oferta/inscripcion/all";
	}

	@GetMapping("/inscripcion/cancelar/{idPersona}")
	public String deletePersona(@PathVariable("idPersona") long idPersona,
			@ModelAttribute("inscripcionPersona") InscripcionPersona persona, BindingResult result, Model model,
			HttpServletRequest request) {
		Cambio c = new Cambio();
		c.setDescripcion("DELETE inscripcionPersona");
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
		inscripcionPersonaInterface.update(inscripcionPersonaInterface.findById(idPersona));
		return "redirect:/inscripcionPersona/all";
	}

	@GetMapping("/inscripcionPersona/historial")
	public String getPersonaHistorial(@ModelAttribute("inscripcionPersona") InscripcionPersona persona,
			BindingResult result, Model model) {
		List<InscripcionPersona> personas = null;
		List<InscripcionEquipo> listaEquipo = null;

		Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		String username = "";
		if (principal instanceof UserDetails) {
			username = ((UserDetails) principal).getUsername();
			Usuario owner = usuarioInterface.findByCorreo(username);
			System.out.println("ID owner: " + owner.getIdUsuario());
			personas = inscripcionPersonaInterface.historial(owner.getIdUsuario());
			listaEquipo = inscripcionEquipo.historial(owner.getIdUsuario());
			model.addAttribute("listaPersona", personas);
			model.addAttribute("listaEquipo", listaEquipo);
		}

		model.addAttribute("listaPersona", personas);
		model.addAttribute("listaEquipo", listaEquipo);

		return "views/historial";
	}

	@GetMapping("/oferta/inscripciones/all")
	public String ofertaPersona(@ModelAttribute("oferta") Oferta oferta, BindingResult result, Model model) {
		List<Oferta> persona = ofertaInterface.ofertasPesona();
		List<Oferta> equipos = ofertaInterface.ofertasEquipo();

		List<Oferta> person = new ArrayList<Oferta>();
		List<Oferta> team = new ArrayList<Oferta>();

		long count = 0;
		long count2 = 0;
		for (int i = 0; i < persona.size(); i++) {
			count = inscripcionPersonaInterface.countPeople(persona.get(i).getIdOferta());
			if ((persona.get(i).getStatus() == 1) && (persona.get(i).getEvento().getCapacidadMaxima() > count)) {
				person.add(persona.get(i));
			}
		}

		for (int i = 0; i < equipos.size(); i++) {
			count = inscripcionEquipo.countPeople(equipos.get(i).getIdOferta());
			if ((equipos.get(i).getStatus() == 1) && (equipos.get(i).getEvento().getCapacidadMaxima() > count2)) {
				team.add(equipos.get(i));
			}
		}

		// System.out.println(person.get(0).getEvento().getImagen());

		model.addAttribute("persona", person);
		model.addAttribute("equipos", team);
		model.addAttribute("message", "ya estas inscrito en el curso sdjcosc");
		return "views/inscripcioness";
	}

}
