package com.ewei.pma.security;

import java.util.concurrent.TimeUnit;

import javax.sql.DataSource;

import com.ewei.pma.services.EmployeeAuthService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

@Configuration
@EnableWebSecurity
public class SecurityConfiguration extends WebSecurityConfigurerAdapter{

	@Autowired
	DataSource dataSource;

	@Autowired EmployeeAuthService empAuthService;
	
	@Autowired
	BCryptPasswordEncoder bCryptEncoder;
	
	@Override
	protected void configure(AuthenticationManagerBuilder auth) throws Exception {
		// auth.jdbcAuthentication()
		// 	.usersByUsernameQuery("select username, password, enabled "+
		// 		"from employee where username = ?" )
		// 	.authoritiesByUsernameQuery("select username, role "+
		// 		"from employee where username = ?")
		// 	.dataSource(dataSource)
		// 	.passwordEncoder(bCryptEncoder);
		
		auth.userDetailsService(empAuthService).passwordEncoder(bCryptEncoder);
	}
	
	@Override
	protected void configure(HttpSecurity http) throws Exception{
		http.authorizeRequests()
			.antMatchers("/css/**").permitAll()
			.antMatchers("/img/**").permitAll()
			.antMatchers("/js/**").permitAll()
			.antMatchers("/scss/**").permitAll()
			.antMatchers("/vendor/**").permitAll()
			.antMatchers("/forgot-password").permitAll()
			.antMatchers("/guest").permitAll()
			.antMatchers("/register", "/register/save").permitAll()
			// .antMatchers("/projects/new").hasRole("ADMIN")
			// .antMatchers("/projects/save").hasRole("ADMIN")
			// .antMatchers("/employees/new").hasRole("ADMIN")
			// .antMatchers("/employees/save").hasRole("ADMIN")
			.antMatchers("/", "/**").authenticated()
			.and()
			.formLogin()
			.loginPage("/login")
			.defaultSuccessUrl("/", true)
			.permitAll()
			.and()
			.rememberMe()
				.tokenValiditySeconds((int)TimeUnit.DAYS.toSeconds(21))
				.key("somethingverysecured")
			.and()
			.logout()
				.logoutUrl("/logout")
				.logoutRequestMatcher(new AntPathRequestMatcher("/logout", "GET"))
				.logoutSuccessUrl("/login");
		
		http.csrf().disable();
		
	}
	
}