package mx.gob.eventosComunitarios.controller;

import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import mx.gob.eventosComunitarios.dao.CambioInterface;
import mx.gob.eventosComunitarios.entity.Cambio;

@Controller
public class CambioController {
	
	@Autowired
	private CambioInterface cambioInterface;
	
	@GetMapping("/cambio/all")
	public String getAll(@ModelAttribute("cambio")Cambio cambio, BindingResult result, Model model) {
		List<Cambio> cambios = cambioInterface.findAll();
		model.addAttribute("listaCambio", cambios);
		return "views/cambios";
	}
	
	@PostMapping("/cambio/create")
	public String createCambio(Cambio cambio, BindingResult result, Model model) {
		if(cambio.getIdCambios() == 0) {
			cambioInterface.save(cambio);
		}
		return "redirect:/cambio/all";
	}
	
	@GetMapping("/cambio/date/{date}")
	public String getDate(@PathVariable("date") Date date,@ModelAttribute("cambio") Cambio cambio, BindingResult result, Model model) {
		List<Cambio>cambiosDate = cambioInterface.findByDate(date);
		List<Cambio> cambios = cambioInterface.findAll();
		model.addAttribute("ListaCambioDate", cambiosDate);
		model.addAttribute("listaCambio", cambios);
		return "views/cambios";
	}
	
	@GetMapping("/cambio/response/{response}")
	public String cambioResponse(@PathVariable("response")String response, @ModelAttribute("cambio")Cambio cambio, BindingResult result, Model model) {
		List<Cambio> cambioResponse = cambioInterface.findByResponse(response);
		List<Cambio> cambios = cambioInterface.findAll();
		model.addAttribute("listaCambioResponse", cambioResponse);
		model.addAttribute("listaCambio", cambios);
		return "views/cambios";
	}
	
	@GetMapping("/cambio/host/{host}")
	public String cambioHost(@PathVariable("host")String host, @ModelAttribute("cambio")Cambio cambio, BindingResult result, Model model) {
		List<Cambio> cambioHost = cambioInterface.findByHost(host);
		List<Cambio> cambios = cambioInterface.findAll();
		model.addAttribute("listaCambioHost", cambioHost);
		model.addAttribute("listaCambio", cambios);
		return "views/cambios";
	}
}
