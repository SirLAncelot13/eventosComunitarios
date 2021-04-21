package mx.gob.eventosComunitarios.controller;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
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
import mx.gob.eventosComunitarios.dao.TipoInterface;
import mx.gob.eventosComunitarios.dao.UsuarioInterface;
import mx.gob.eventosComunitarios.entity.Cambio;
import mx.gob.eventosComunitarios.entity.Tipo;
import mx.gob.eventosComunitarios.entity.Usuario;

@Controller
public class TipoController {

	@Autowired
	private TipoInterface tipoInterface;
	@Autowired
	private UsuarioInterface usuarioInterface;
	@Autowired
	private CambioInterface cambioInterface;
	
	@GetMapping("/tipo/all")
	public String tipoAll(@ModelAttribute("tipo") Tipo tipo, BindingResult result, Model model) {
		List<Tipo> tipos = tipoInterface.findAll();
		model.addAttribute("listaTipo",tipos);
		return"views/tipo";
	}
	
	@PostMapping("/tipo/create")
	public String tipoCreate(@ModelAttribute("tipo") Tipo tipo, BindingResult result, Model model, HttpServletRequest request) {
		
		if(tipo.getIdTipo()==0) {
			Cambio c = new Cambio();
			c.setDescripcion("CREATE tipo");
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
			tipoInterface.save(tipo);
		}else {
			Cambio c = new Cambio();
			c.setDescripcion("UPDATE tipo");
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
			tipoInterface.update(tipo);
		}
		
		return "redirect:/tipo/all";
	}
	
	@GetMapping("/tipo/one/{id}")
	public String tipoOne(@PathVariable("id")long id, @ModelAttribute("tipo") Tipo tipo, BindingResult result, Model model) {
		Tipo t = new Tipo();
		t= tipoInterface.findOne(id);
		List<Tipo> tipos = tipoInterface.findAll();
		model.addAttribute("listaTipo",tipos);
		model.addAttribute("tipo", t);
		return"views/tipo";
	}
	
}
