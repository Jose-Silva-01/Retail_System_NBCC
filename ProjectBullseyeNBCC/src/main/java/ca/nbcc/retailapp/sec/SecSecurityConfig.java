package ca.nbcc.retailapp.sec;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;

import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;


import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

@Configuration
@EnableWebSecurity

public class SecSecurityConfig {

	@Bean
	public InMemoryUserDetailsManager userDetailsService() {
		UserDetails kyleEmployee = User.withUsername("kyleEmployee")
	            .password(passwordEncoder().encode("empPass"))
	            .roles("EMP")
	            .build();
	        /*UserDetails user2 = User.withUsername("manager")
	            .password(passwordEncoder().encode("manPass"))
	            .roles("MAN")
	            .build();*/
	        UserDetails kateAdmin = User.withUsername("kateAdmin")
		            .password(passwordEncoder().encode("adminPass"))
		            .roles("ADMIN")
		            .build();
	        UserDetails joseAdmin = User.withUsername("joseAdmin")
		            .password(passwordEncoder().encode("adminPass"))
		            .roles("ADMIN")
		            .build();
	        UserDetails anaAdmin = User.withUsername("anaAdmin")
		            .password(passwordEncoder().encode("adminPass"))
		            .roles("ADMIN")
		            .build();
	        return new InMemoryUserDetailsManager(kyleEmployee, kateAdmin, joseAdmin, anaAdmin);
	}

	 /**@Bean
	    public SecurityFilterChain configure(HttpSecurity http) throws Exception {
		 http.csrf()
         .disable()
         .authorizeRequests()
         .antMatchers("/admin/**")
         .hasRole("ADMIN")
         .antMatchers("/anonymous*")
         .anonymous()
         .antMatchers("/login*")
         .permitAll()
         .anyRequest()
         .authenticated()
         .and()
         .formLogin()
         .loginPage("/login.html")
         .permitAll()
         //.loginProcessingUrl("/login.html")
         .defaultSuccessUrl("/index.html", true)
         .failureUrl("/login.html?error=true")
         .and()
         .logout();
         
		 return http.build();
		 
	 }
	 */
	 
	 protected void configure(HttpSecurity http) throws Exception {    
	         
	       http.authorizeRequests().  
	       antMatchers("/index", "/user","/").permitAll()  
	       .antMatchers("/admin").authenticated()  
	       .and()  
	       .formLogin()  
	       .loginPage("/login")  
	       .permitAll()
	       .loginProcessingUrl("/login")
	       .and()  
	       .logout()  
	       .logoutRequestMatcher(new AntPathRequestMatcher("/logout"));  
	 }    
	   
	 
	 @Bean 
	 public PasswordEncoder passwordEncoder() { 
	     return new BCryptPasswordEncoder(); 
	 }
}