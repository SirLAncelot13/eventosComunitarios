package mx.gob.eventosComunitarios.controller;

import java.sql.Time;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.propertyeditors.CustomDateEditor;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.context.request.WebRequest;

import mx.gob.eventosComunitarios.dao.CambioInterface;
import mx.gob.eventosComunitarios.dao.EventoInterface;
import mx.gob.eventosComunitarios.dao.InscripcionEquipoInterface;
import mx.gob.eventosComunitarios.dao.InscripcionPersonaInterface;
import mx.gob.eventosComunitarios.dao.OfertaInterface;
import mx.gob.eventosComunitarios.dao.UsuarioInterface;
import mx.gob.eventosComunitarios.entity.Cambio;
import mx.gob.eventosComunitarios.entity.Evento;
import mx.gob.eventosComunitarios.entity.Oferta;
import mx.gob.eventosComunitarios.entity.Usuario;

@Controller
public class OfertaController {

	@Autowired
	private OfertaInterface ofertaInterface;
	@Autowired
	private EventoInterface eventoInterface;
	@Autowired
	private InscripcionPersonaInterface personaInterface;
	@Autowired
	private InscripcionEquipoInterface equipoInterface;
	@Autowired
	private UsuarioInterface usuarioInterface;
	@Autowired
	private CambioInterface cambioInterface;

	@InitBinder("oferta")
	public void customizeBinding(WebDataBinder binder) {
		SimpleDateFormat dateFormatter = new SimpleDateFormat("yyyy-MM-dd");
		SimpleDateFormat horaActual = new SimpleDateFormat("HH:mm:ss");

		dateFormatter.setLenient(false);
		horaActual.setLenient(false);
		binder.registerCustomEditor(Date.class, "fechaInicio", new CustomDateEditor(dateFormatter, true));
		binder.registerCustomEditor(Date.class, "fechaFin", new CustomDateEditor(dateFormatter, true));
		binder.registerCustomEditor(Date.class, "fechaInicioRegistro", new CustomDateEditor(dateFormatter, true));
		binder.registerCustomEditor(Date.class, "fechaFinRegistro", new CustomDateEditor(dateFormatter, true));
		binder.registerCustomEditor(Time.class, "horaInicio", new CustomDateEditor(horaActual, true));
		binder.registerCustomEditor(Date.class, "horaFin", new CustomDateEditor(horaActual, true));
		
	}

	@GetMapping("/oferta/all")
	public String getAll(@ModelAttribute("oferta") Oferta oferta, BindingResult result, Model model) {
		List<Oferta> ofertas = ofertaInterface.findAll();
		List<Evento> eventos = eventoInterface.findAll();

		model.addAttribute("eventos", eventos);
		model.addAttribute("listaOferta", ofertas);

		return "views/oferta";
	}

	@PostMapping("/oferta/save")
	public String saveOferta(Oferta oferta, @ModelAttribute("eventoName") int eventoName, BindingResult result, Model model, HttpServletRequest request) {

		System.out.println("Entre al oferta save");
		if (oferta.getIdOferta() == 0) {
			oferta.setStatus(1);

			System.out.println("HoraInicio:> "+oferta.getHoraInicio());
			System.out.println("HoraFin:> "+oferta.getHoraFin());
			
			Cambio c = new Cambio();
			c.setDescripcion("CREATE oferta");
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
				
				final String[] HEADER_LIST = { 
			            "X-Forwarded-For",
			            "Proxy-Client-IP",
			            "WL-Proxy-Client-IP",
			            "HTTP_X_FORWARDED_FOR",
			            "HTTP_X_FORWARDED",
			            "HTTP_X_CLUSTER_CLIENT_IP",
			            "HTTP_CLIENT_IP",
			            "HTTP_FORWARDED_FOR",
			            "HTTP_FORWARDED",
			            "HTTP_VIA",
			            "REMOTE_ADDR" 
			        };

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
			
			ofertaInterface.save(oferta, eventoName);
		} else {
			oferta.setStatus(1);
			
			Cambio c = new Cambio();
			c.setDescripcion("UPDATE oferta");
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
				
				final String[] HEADER_LIST = { 
			            "X-Forwarded-For",
			            "Proxy-Client-IP",
			            "WL-Proxy-Client-IP",
			            "HTTP_X_FORWARDED_FOR",
			            "HTTP_X_FORWARDED",
			            "HTTP_X_CLUSTER_CLIENT_IP",
			            "HTTP_CLIENT_IP",
			            "HTTP_FORWARDED_FOR",
			            "HTTP_FORWARDED",
			            "HTTP_VIA",
			            "REMOTE_ADDR" 
			        };

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
			
			ofertaInterface.update(oferta, eventoName);
		}
		return "redirect:/oferta/all";
	}

	@GetMapping("/oferta/delete/{id}")
	public String byeOferta(@PathVariable("id") long id, HttpServletRequest request) {
		Cambio c = new Cambio();
		c.setDescripcion("DELETE oferta");
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
			
			final String[] HEADER_LIST = { 
		            "X-Forwarded-For",
		            "Proxy-Client-IP",
		            "WL-Proxy-Client-IP",
		            "HTTP_X_FORWARDED_FOR",
		            "HTTP_X_FORWARDED",
		            "HTTP_X_CLUSTER_CLIENT_IP",
		            "HTTP_CLIENT_IP",
		            "HTTP_FORWARDED_FOR",
		            "HTTP_FORWARDED",
		            "HTTP_VIA",
		            "REMOTE_ADDR" 
		        };

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
		ofertaInterface.delete(id);
		return "redirect:/oferta/all";
	}
	
	@GetMapping("/eventoOferta/delete/{idEvento}")
	public String byeEventoOferta(@PathVariable("idEvento") long id, HttpServletRequest request) {
		Evento event = eventoInterface.findById(id);
		Cambio c = new Cambio();
		c.setDescripcion("UPDATE evento");
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
			
			final String[] HEADER_LIST = { 
		            "X-Forwarded-For",
		            "Proxy-Client-IP",
		            "WL-Proxy-Client-IP",
		            "HTTP_X_FORWARDED_FOR",
		            "HTTP_X_FORWARDED",
		            "HTTP_X_CLUSTER_CLIENT_IP",
		            "HTTP_CLIENT_IP",
		            "HTTP_FORWARDED_FOR",
		            "HTTP_FORWARDED",
		            "HTTP_VIA",
		            "REMOTE_ADDR" 
		        };

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
		eventoInterface.deleteLogical(id);
		List<Oferta> oferta= ofertaInterface.findByEvento(event.getIdEvento());
		for (int i = 0; i < oferta.size(); i++) {
			ofertaInterface.delete(id);
		}
		
		return "redirect:/evento/all";
	}

	@GetMapping("/oferta/one/{id}")
	public String oneOferta(@PathVariable("id") long id, @ModelAttribute("oferta") Oferta oferta, BindingResult result,
			Model model) {
		oferta = ofertaInterface.findById(id);
		List<Evento> eventos = eventoInterface.findAll();

		model.addAttribute("eventos", eventos);
		List<Oferta> ofertas = ofertaInterface.findAll();
		model.addAttribute("listaOferta", ofertas);
		model.addAttribute("oferta", oferta);
		System.out.println(oferta.getEvento().getIdEvento());
		return "views/oferta";
	}
	
	@GetMapping("/ofertas/all")
	public String getAlls(@ModelAttribute("oferta") Oferta oferta,BindingResult result, Model model) {
		List<Oferta> ofertas = ofertaInterface.findAll();
		List<Evento> eventos = eventoInterface.findAll();
		
		model.addAttribute("eventos", eventos);
		model.addAttribute("listaOferta",ofertas);
		return "views/muestra";
	}
	
	
	@GetMapping("/oferta/inscripcion/all")
	public String ofertaPersona(@ModelAttribute("oferta") Oferta oferta, BindingResult result, Model model) {
		List<Oferta> persona = ofertaInterface.ofertasPesona();
		List<Oferta> equipos = ofertaInterface.ofertasEquipo();
		
		
		
		List<Oferta> person= new ArrayList<Oferta>();
		List<Oferta> team= new ArrayList<Oferta>();
		
		long count=0;
		long count2=0;
		for (int i = 0; i < persona.size(); i++) {
		count = personaInterface.countPeople(persona.get(i).getIdOferta());
			if((persona.get(i).getStatus()==1)&&(persona.get(i).getEvento().getCapacidadMaxima()>count)) {
				person.add(persona.get(i));
			}
		}
		
		for (int i = 0; i < equipos.size(); i++) {
		count = equipoInterface.countPeople(equipos.get(i).getIdOferta());
			if((equipos.get(i).getStatus()==1)&&(equipos.get(i).getEvento().getCapacidadMaxima()>count2)) {
				team.add(equipos.get(i));
			}
		}
		
		//System.out.println(person.get(0).getEvento().getImagen());
		
		model.addAttribute("persona",person);
		model.addAttribute("equipos",team);
		return "views/inscripciones";
	}
	
	@PostMapping("/search/ofertas")
	public String searchEvento(Model model,WebRequest request) {
		String searchword = request.getParameter("searchword");
		System.out.println("searchword:> " + searchword);
		List<Oferta> persona = ofertaInterface.ofertasPesona();
		List<Oferta> equipos = ofertaInterface.ofertasEquipo();
		
		
		
		List<Oferta> person= new ArrayList<Oferta>();
		List<Oferta> team= new ArrayList<Oferta>();
		
		long count=0;
		long count2=0;
		for (int i = 0; i < persona.size(); i++) {
		count = personaInterface.countPeople(persona.get(i).getIdOferta());
			if((persona.get(i).getStatus()==1)&&(persona.get(i).getEvento().getCapacidadMaxima()>count)) {
				person.add(persona.get(i));
				//System.out.println("evento persona:> " +person.get(i).getEvento().getNombre());
			}
		}
		
		for (int i = 0; i < equipos.size(); i++) {
		count = equipoInterface.countPeople(equipos.get(i).getIdOferta());
			if((equipos.get(i).getStatus()==1)&&(equipos.get(i).getEvento().getCapacidadMaxima()>count2)) {
				team.add(equipos.get(i));
				//System.out.println("evento equipo:> " +team.get(i).getEvento().getNombre());
			}
		}
		
		List<Oferta> person2= new ArrayList<Oferta>();
		List<Oferta> team2= new ArrayList<Oferta>();
		
		person2 = ofertaInterface.ofertasPesonaBySearchword(searchword);
		team2 = ofertaInterface.ofertasEquipoBySearchword(searchword);
		
		for (int i = 0; i < person2.size(); i++) {
			
			System.out.println("evento person:> " +person2.get(i).getEvento().getNombre());
		}
		
		for (int i = 0; i < team2.size(); i++) {
			System.out.println("evento equipo:> " +team2.get(i).getEvento().getNombre());
		}
		
		model.addAttribute("persona",person2);
		model.addAttribute("equipos",team2);
		
		
	return "views/inscripciones";	
	}

}
