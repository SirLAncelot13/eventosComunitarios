package mx.gob.eventosComunitarios.controller;

import java.sql.SQLException;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.NoHandlerFoundException;

import mx.gob.eventosComunitarios.dao.EventoInterface;
import mx.gob.eventosComunitarios.entity.Evento;

@ControllerAdvice
public class AdvisorController {

	@Autowired
	private EventoInterface eventoInterface;
	
	@ExceptionHandler(NoHandlerFoundException.class)
	public ModelAndView handle(Exception ex) {
		ModelAndView mv = new ModelAndView();
		mv.addObject("message", "---- HA OCURRIDO UN ERROR AL INTENTAR ACCEDER A ESTA DIRECCIÓN ----");
		mv.setViewName("views/Error");
		return mv;
	}
	
	@ExceptionHandler(NullPointerException.class)
	public ModelAndView handleNull(Exception ex) {
		ModelAndView mv = new ModelAndView();
		mv.addObject("message", "Ha ocurrido un error 500");
		mv.setViewName("views/Error");
		return mv;
	}
	
	@ModelAttribute("")
	public List<Evento> menu(){
		return eventoInterface.findAll();
	}
	
	@ExceptionHandler(SQLException.class)
	public ModelAndView handleForbiden(Exception ex) {
		ModelAndView mv = new ModelAndView();
		mv.addObject("message", "error de base de datos");
		mv.setViewName("views/Error");
		return mv;
	}
	
	@ExceptionHandler(DataAccessException.class)
	public ModelAndView handleAccess(Exception ex) {
		ModelAndView mv = new ModelAndView();
		mv.addObject("message", "error de acceso");
		mv.setViewName("views/Error");
		return mv;
	}
	
	@ExceptionHandler(Exception.class)
	public ModelAndView handleAccess2(HttpServletRequest request, HttpServletResponse response,
			  AccessDeniedException accessDeniedException) {
		ModelAndView mv = new ModelAndView();
		mv.addObject("message", "error");
		mv.setViewName("views/Error");
		return mv;
	}
	
	@ExceptionHandler(IllegalStateException.class)
	public ModelAndView ilegal(Exception ex) {
		ModelAndView mv = new ModelAndView();
		mv.addObject("message", "ambiguo");
		mv.setViewName("views/Error");
		return mv;
	}
	
}
