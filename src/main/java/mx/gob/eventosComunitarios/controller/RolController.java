package mx.gob.eventosComunitarios.controller;



import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;

import mx.gob.eventosComunitarios.dao.RolInterface;
import mx.gob.eventosComunitarios.entity.Rol;

@Controller
public class RolController {
	
	@Autowired
	private RolInterface rolInterface;
	
	@GetMapping("/rol/all")
	public String rolAll(@ModelAttribute("rol") Rol rol, BindingResult result, Model model) {
		List<Rol> roles = rolInterface.findAll();
		model.addAttribute("roles", roles);
		return "views/rol";
	}
	
	@GetMapping("/rol/roles")
	public String rolLes(@ModelAttribute("rol") Rol rol, BindingResult result, Model model) {
		Rol roll = rolInterface.findNotAdmin(2);
		model.addAttribute("roleNot", roll);
		return "views/rol";
	}
	
}
