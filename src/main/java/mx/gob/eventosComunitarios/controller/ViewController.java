package mx.gob.eventosComunitarios.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Controller
public class ViewController {
	
	@GetMapping("/")
	public String index() {
		return "index";
	}
	
	@GetMapping("/login")
	public String login() {
		
		return "login";
	}
	
	@RequestMapping(value="/login", method= RequestMethod.POST)
	public String auth() {
		return "login";
	}
}
