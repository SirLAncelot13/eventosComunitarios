package mx.gob.eventosComunitarios.config;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.jdbc.datasource.DriverManagerDataSource;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter{
	
	@Autowired
	PasswordEncoder passwordEncoder;
	
	@Autowired
	DataSource datasource;
	
	@Bean
	public DataSource getDataSource() {
		DriverManagerDataSource datasource = new DriverManagerDataSource();
		datasource.setDriverClassName("com.mysql.jdbc.Driver");
		datasource.setUrl("jdbc:mysql://localhost:3306/eventosCom?serverTimezone=UTC");
		datasource.setUsername("root");
		datasource.setPassword("root");
		return datasource;
	}
	
	@Bean
	public PasswordEncoder passwordEncoder() {
		return new BCryptPasswordEncoder();
	}
	
	public  void configure(AuthenticationManagerBuilder auth) throws Exception{

		auth.jdbcAuthentication().dataSource(this.getDataSource())
			.passwordEncoder(this.passwordEncoder())
			.usersByUsernameQuery("SELECT correo as email, contrasena as password, 'true' as enabled FROM usuario WHERE correo = ? and status=1")
			.authoritiesByUsernameQuery("SELECT correo as email, rol.nombre FROM usuario INNER JOIN rol ON usuario.rol = rol.idRol WHERE correo = ? and status=1");

	}
	
	public void configure(HttpSecurity http) throws Exception{
		//Controlar el acceso a las RUTAS
		http.authorizeRequests()
		.antMatchers("/", "/login", "/usuario/**", "/uploads/**","/oferta/inscripcion/all", "/registro/all", "/oferta/one/**", "/usuario/create")
			.permitAll()
		.antMatchers("/inscripcion/equipo/**", "/equipo/one/**", "/inscripcionPersona/**", "/inscripcion/persona/**","/oferta/inscripciones/all")
			.hasAnyRole("USER")
		.antMatchers("/evento/**", "/oferta/**", "/rol/**", "/tipo/**","/usuario/all", "/usuario/delete/**","/tipo/**","/cambio/**")
			.hasAnyRole("ADMIN")
		.and()
		.formLogin()
		.defaultSuccessUrl("/")
		.loginPage("/login")
			.permitAll()
		.and().exceptionHandling().accessDeniedPage("/acceso")
		.and()
			.logout()
			.invalidateHttpSession(true)
			.clearAuthentication(true)
			.logoutRequestMatcher(new AntPathRequestMatcher("/logout"))
			.logoutSuccessUrl("/login")
			.permitAll()
		.and()
			.csrf()
			.disable();
	}
}
