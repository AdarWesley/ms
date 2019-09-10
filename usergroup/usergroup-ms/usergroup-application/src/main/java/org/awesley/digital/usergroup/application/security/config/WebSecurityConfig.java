package org.awesley.digital.usergroup.application.security.config;

import javax.servlet.http.HttpServletResponse;

import org.awesley.infra.security.JwtAuthenticationTokenFilter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {

//    @Autowired
//    private UserDetailsService userDetailsService;
//    
//    @Autowired
//    private PasswordEncoder passwordEncoder;
//
//    @Autowired
//    public void configureAuthentication(AuthenticationManagerBuilder authenticationManagerBuilder) throws Exception {
//        authenticationManagerBuilder
//                .userDetailsService(this.userDetailsService)
//                .passwordEncoder(passwordEncoder);
//    }

    @Bean
    public JwtAuthenticationTokenFilter authenticationTokenFilterBean() throws Exception {
        return new JwtAuthenticationTokenFilter();
    }

//    @Bean
//    public JwtAuthenticationEntryPoint jwtAuthenticationEntryPoint() {
//    	return new JwtAuthenticationEntryPoint();
//    }
    
    @Override
    public void configure(WebSecurity web) throws Exception {
    	web
    		.ignoring()
    			.antMatchers(
    				"/auditevents",
    				"/autoconfig",
    				"/beans",
    				"/configprops",
    				"/dump",
    				"/env",
    				"/env/**",
    				"/health", 
       				"/heapdump",
       				"/info",
       				"/loggers",
       				"/loggers/**",
   					"/mappings", 
    				"/metrics",
    				"/metrics/**",
    				"/trace"
     				);
    	
    	
    }
    
    @Override
    protected void configure(HttpSecurity httpSecurity) throws Exception {
        httpSecurity
	        // we don't need CSRF because our token is invulnerable
	        .csrf().disable()
	
	        // don't create session
	        .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS).and()
	
	        .exceptionHandling()
	        	.authenticationEntryPoint((req, res, ex) -> 
	        		res.setStatus(HttpServletResponse.SC_UNAUTHORIZED)).and()

//	        .authorizeRequests()
//				.antMatchers(HttpMethod.OPTIONS, "/**").permitAll().and()
//	
	        // allow anonymous resource requests
			.authorizeRequests()
	            .antMatchers(HttpMethod.GET,
	                "/autoconfig",
	                "/health",
	                "/mappings",
	                "/metrics"
	            ).permitAll().and()
	
	        // This is invoked when user tries to access a secured REST resource without supplying any credentials
	        // We should just send a 401 Unauthorized response because there is no 'login page' to redirect to
			.authorizeRequests()
	            .anyRequest().authenticated().and()
	
	// Custom JWT based security filter
	
	        .addFilterBefore(authenticationTokenFilterBean(), UsernamePasswordAuthenticationFilter.class)
	        .httpBasic().disable();

        // disable page caching
//        httpSecurity
//                .headers()
//                .frameOptions().sameOrigin()  // required to set for H2 else H2 Console will be blank.
//                .cacheControl();
    }
}