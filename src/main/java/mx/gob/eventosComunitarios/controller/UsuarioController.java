package mx.gob.eventosComunitarios.controller;

import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

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
import mx.gob.eventosComunitarios.dao.RolInterface;
import mx.gob.eventosComunitarios.dao.UsuarioInterface;
import mx.gob.eventosComunitarios.entity.Cambio;
import mx.gob.eventosComunitarios.entity.Rol;
import mx.gob.eventosComunitarios.entity.Usuario;

@Controller
public class UsuarioController {

	@Autowired
	private UsuarioInterface usuarioInterface;
	@Autowired
	private RolInterface rolInterface;
	@Autowired
	private CambioInterface cambioInterface;

	@GetMapping("/usuario/all")
	public String getUsuario(@ModelAttribute("usuario") Usuario usuario, BindingResult result, Model model) {
		List<Usuario> usuarios = usuarioInterface.findAll();
		List<Rol> roles = rolInterface.findAll();
		model.addAttribute("listaUsuario", usuarios);
		model.addAttribute("listaRol", roles);

		return "views/usuario";
	}

	@GetMapping("/registro/all")
	public String registroForm(@ModelAttribute("usuario") Usuario usuario, BindingResult result, Model model) {
		List<Usuario> usuarios = usuarioInterface.findAll();
		List<Rol> roles = rolInterface.findAll();
		model.addAttribute("listaUsuario", usuarios);
		model.addAttribute("listaRol", roles);

		return "views/registro";
	}

	@PostMapping("/usuario/create")
	public String usuarioCreate(@Valid Usuario usuario, BindingResult result, Model model, HttpServletRequest request)
			throws IOException {

		System.out.println("entre yaaa");
		if (result.hasErrors()) {
			List<Usuario> usuarios = usuarioInterface.findAll();
			List<Rol> roles = rolInterface.findAll();
			model.addAttribute("listaUsuario", usuarios);
			model.addAttribute("listaRol", roles);
			model.addAttribute("usuario", usuario);
			return "views/registro";
		}

			if (usuario.getIdUsuario() == 0) {
				System.out.println("1");

				if (!(usuarioInterface.countCorreo(usuario.getCorreo()) == 0)) {
					System.out.println("el correo ya existe");
				} else {

					Rol rol = new Rol(2, "ROLE_USER");

					usuario.setRol(rol);
					usuario.setStatus(1);
					usuarioInterface.save(usuario);
				}
			} else {
				System.out.print("id:  " + usuario.getIdUsuario());
				Rol r = new Rol();
				r.setIdRol(2);
				r.setNombre("ROLE_USER");
				usuario.setRol(r);
				usuario.setStatus(1);
				usuarioInterface.update(usuario);

				Cambio c = new Cambio();
				c.setDescripcion("UPDATE usuario");
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

				return "redirect:/inscripcionPersona/all";
			}
		return "redirect:/";
	}

	@GetMapping("/usuario/delete/{id}")
	public String usuarioDelete(@PathVariable("id") long id, HttpServletRequest request) {
		Cambio c = new Cambio();
		c.setDescripcion("DELETE usuario");
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
		usuarioInterface.delete(id);
		return "redirect:/usuario/all";
	}

	@GetMapping("/usuario/one")
	public String OneUsuario(@ModelAttribute("usuario") Usuario usuario, BindingResult result, Model model) {
		Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		String username = "";
		if (principal instanceof UserDetails) {
			username = ((UserDetails) principal).getUsername();
			Usuario owner = usuarioInterface.findByCorreo(username);
			System.out.println("ID owner: " + owner.getIdUsuario());
			System.out.println("rol owner: " + owner.getRol().getIdRol());

			usuario = usuarioInterface.findById(owner.getIdUsuario());
			List<Rol> roles = rolInterface.findAll();
			model.addAttribute("listaRol", roles);
			model.addAttribute("usuario", usuario);
		}
		return "views/modificar";
	}

	@GetMapping("/usuario/email/{email}")
	public String EmailUsuario(@PathVariable("email") String email, @ModelAttribute("usuario") Usuario usuario,
			BindingResult result, Model model) {

		usuario = usuarioInterface.findByCorreo(email);
		List<Usuario> usuarios = usuarioInterface.findAll();
		model.addAttribute("usuario", usuario);
		model.addAttribute("listaUsuario", usuarios);
		return "views/usuario";
	}

	@GetMapping("/usuario/status/{email}")
	public String statusUsuario(@PathVariable("status") long status, @ModelAttribute("usuario") Usuario usuario,
			BindingResult result, Model model) {
		List<Usuario> usuariosStatus = usuarioInterface.findByStatus(status);
		List<Usuario> usuarios = usuarioInterface.findAll();
		model.addAttribute("ListaStatus", usuariosStatus);
		model.addAttribute("listaUsuario", usuarios);
		return "views/usuario";
	}
}
