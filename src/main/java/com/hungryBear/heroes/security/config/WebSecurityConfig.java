package com.hungryBear.heroes.security.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

/**
 * This class extends the WebSecurityConfigurerAdapter is a convenience class that allows customization to both
 * WebSecurity and HttpSecurity.
 */
@Configuration
@EnableAutoConfiguration
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {

  @Autowired
  private UnauthorizedEntryPoint unauthorizedEntryPoint;

  @Autowired
  private UserDetailsService userDetailsService;

  @Autowired
  private TokenAuthenticationFilter tokenAuthenticationFilter;

  @Autowired
  public void configureGlobal(AuthenticationManagerBuilder auth) throws Exception {
    // configure AuthenticationManager so that it knows from where to load user for matching credentials
    // Use BCryptPasswordEncoder
    auth.authenticationProvider(authProvider()).userDetailsService(userDetailsService)
        .passwordEncoder(passwordEncoder());
  }

  @Bean
  public DaoAuthenticationProvider authProvider() {
    DaoAuthenticationProvider authProvider = new DaoAuthenticationProvider();
    authProvider.setUserDetailsService(userDetailsService);
    authProvider.setPasswordEncoder(passwordEncoder());
    return authProvider;
  }

  @Bean
  public PasswordEncoder passwordEncoder() {
    return new BCryptPasswordEncoder();
  }

  @Bean
  @Override
  public AuthenticationManager authenticationManagerBean() throws Exception {
    return super.authenticationManagerBean();
  }

  @Override
  protected void configure(HttpSecurity httpSecurity) throws Exception {
    // We don't need CSRF for this example
    httpSecurity
        // dont authenticate this particular request
        .authorizeRequests()
        // We can config all the request here or use in the controllers the annotation
        // @PreAuthorize("hasRole('ROLE_ADMIN')")
//        .antMatchers(HttpMethod.POST, "/heroes/**").hasRole("ADMIN")
//        .antMatchers(HttpMethod.PUT, "/heroes/**").hasRole("ADMIN")
//        .antMatchers(HttpMethod.DELETE, "/heroes/**").hasRole("ADMIN")
        .antMatchers("/heroes/ping/*").permitAll()
        .antMatchers(HttpMethod.GET, "/heroes/**").hasAnyRole("ADMIN", "USER")
        .antMatchers("/auth/**").permitAll()

        // all other requests need to be authenticated
        .anyRequest().authenticated()
        .and().csrf().disable()
        // make sure we use stateless session; session won't be used to store user's state.
        .exceptionHandling().authenticationEntryPoint(unauthorizedEntryPoint).and().sessionManagement()
        .sessionCreationPolicy(SessionCreationPolicy.STATELESS);

    // Add a filter to validate the tokens with every request
    httpSecurity.addFilterBefore(tokenAuthenticationFilter, UsernamePasswordAuthenticationFilter.class);
  }
}
