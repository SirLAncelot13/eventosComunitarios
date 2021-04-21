package mx.gob.eventosComunitarios.controller;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

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
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.commons.CommonsMultipartFile;

import mx.gob.eventosComunitarios.dao.CambioInterface;
import mx.gob.eventosComunitarios.dao.EventoInterface;
import mx.gob.eventosComunitarios.dao.ModalidadInterface;
import mx.gob.eventosComunitarios.dao.TipoInterface;
import mx.gob.eventosComunitarios.dao.UsuarioInterface;
import mx.gob.eventosComunitarios.entity.Cambio;
import mx.gob.eventosComunitarios.entity.Evento;
import mx.gob.eventosComunitarios.entity.Modalidad;
import mx.gob.eventosComunitarios.entity.Tipo;
import mx.gob.eventosComunitarios.entity.Usuario;

@Controller
public class EventoController {
	@Autowired
	private EventoInterface eventoInterface;
	@Autowired
	private TipoInterface tipoInterfaces;
	@Autowired
	private ModalidadInterface modalidadInterfaces;
	@Autowired
	private UsuarioInterface usuarioInterface;
	@Autowired
	private CambioInterface cambioInterface;
	
	@GetMapping("/evento/all")
	public String getAll(@ModelAttribute("evento") Evento evento, BindingResult result, Model model) {
		List<Evento> eventos = eventoInterface.findAll();
		List<Tipo> tipos = tipoInterfaces.findAll();
		List<Modalidad> modalidades = modalidadInterfaces.findAll();
		model.addAttribute("tipos", tipos);
		model.addAttribute("modalidades", modalidades);
		model.addAttribute("listaEventos",eventos);
		return "views/eventos";
	}
	
	@PostMapping("/evento/save")
	public String saveEvento(Evento evento,@ModelAttribute("tipoName")int tipoName,@ModelAttribute("modalidadName") int modalidadName, 
			BindingResult result, Model model, @RequestParam("file") CommonsMultipartFile file, HttpSession session, HttpServletRequest request) throws IOException {
		System.out.println(file.getOriginalFilename());
		String path = session.getServletContext().getRealPath("/");
		
		if(!file.isEmpty()) {
			byte[] bytes = file.getBytes();
			Path pathupload = Paths.get(path+"/WEB-INF/uploads/category/"+file.getOriginalFilename());
			Files.write(pathupload, bytes);
			evento.setImagen(file.getOriginalFilename());
		}else {
			evento.setImagen("default.png");
		}
		
		System.out.println(path);
		if(evento.getIdEvento()==0) {
			System.out.println("Imagen: "+evento.getImagen());
			evento.setStatus(1);
			
			Cambio c = new Cambio();
			c.setDescripcion("CREATE evento");
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
			
			eventoInterface.save(evento, tipoName, modalidadName);
		}else {
			Evento ev = eventoInterface.findById(evento.getIdEvento());
			if(evento.getImagen().equals("default.png")) {
				evento.setImagen(ev.getImagen());
			}
			evento.setStatus(1);
			
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
			
			eventoInterface.update(evento, tipoName, modalidadName);
		}
		return"redirect:/evento/all";
	}
	
	@GetMapping("/evento/bye/{id}")
	public String byeEvento(@PathVariable("id") long id, HttpServletRequest request) {
		Cambio c = new Cambio();
		c.setDescripcion("DELETE evento");
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
		return"redirect:/evento/all";
	}
	
	@GetMapping("/evento/one/{id}")
	public String oneEvento(@PathVariable("id") long id, @ModelAttribute("evento") Evento evento, BindingResult result, Model model) {
		evento = eventoInterface.findById(id);
		List<Tipo> tipos = tipoInterfaces.findAll();
		List<Modalidad> modalidades = modalidadInterfaces.findAll();
		List<Evento> eventos = eventoInterface.findAll();
		model.addAttribute("tipos", tipos);
		model.addAttribute("modalidades", modalidades);
		model.addAttribute("listaEventos",eventos);
		model.addAttribute("evento", evento);
		return "views/eventos";
	}
	
	
	@GetMapping("/eventos/all")
	public String getAlls(@ModelAttribute("evento") Evento evento, BindingResult result, Model model) {
		List<Evento> eventos = eventoInterface.findAll();
		List<Tipo> tipos = tipoInterfaces.findAll();
		List<Modalidad> modalidades = modalidadInterfaces.findAll();
		
		model.addAttribute("tipos", tipos);
		model.addAttribute("modalidades", modalidades);
		model.addAttribute("listaEventos",eventos);
		return "views/muestra";
	}
}
