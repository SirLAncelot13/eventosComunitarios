package mx.gob.eventosComunitarios.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;

import mx.gob.eventosComunitarios.dao.ModalidadInterface;
import mx.gob.eventosComunitarios.entity.Modalidad;

@Controller
public class ModalidadController {
	
	@Autowired
	private ModalidadInterface modalidadInterface;
	
	@GetMapping("/modalidad/all")
	public String getModalidad(@ModelAttribute("modalidad")Modalidad modalidad, BindingResult result, Model model) {
		List<Modalidad> modalidades = modalidadInterface.findAll();
		model.addAttribute("listaModalidad", modalidades);
		return "views/modalidad";
	}
}
