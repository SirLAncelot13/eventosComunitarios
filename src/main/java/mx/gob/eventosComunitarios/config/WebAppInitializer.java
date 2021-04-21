package mx.gob.eventosComunitarios.config;

import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.servlet.DispatcherServlet;
import org.springframework.web.servlet.FrameworkServlet;
import org.springframework.web.servlet.support.AbstractAnnotationConfigDispatcherServletInitializer;


public class WebAppInitializer extends AbstractAnnotationConfigDispatcherServletInitializer{

	@Override
	protected Class<?>[] getRootConfigClasses() {
		// TODO Auto-generated method stub
		return new Class[] {SecurityConfig.class};
	}

	@Override
	protected Class<?>[] getServletConfigClasses() {
		// TODO Auto-generated method stub
		return new Class[] { WebAppConfig.class };
	}

	@Override
	protected String[] getServletMappings() {
		// TODO Auto-generated method stub
		return new String[] {"/"};
	} 
	@Override
	protected FrameworkServlet createDispatcherServlet(WebApplicationContext servletAppContext) {
		// TODO Auto-generated method stub
		DispatcherServlet dispatcher = (DispatcherServlet) super.createDispatcherServlet(servletAppContext);
		dispatcher.setThrowExceptionIfNoHandlerFound(true);
		return dispatcher;
	}
}
