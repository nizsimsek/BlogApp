package com.nizsimsek.blogapp.config.security;

import com.nizsimsek.blogapp.filter.CustomAuthenticationFilter;
import com.nizsimsek.blogapp.filter.CustomAuthorizationFilter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import static org.springframework.http.HttpMethod.*;

@Configuration
@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    private final UserDetailsService userDetailsService;
    private final BCryptPasswordEncoder bCryptPasswordEncoder;

    public SecurityConfig(UserDetailsService userDetailsService,
                          BCryptPasswordEncoder bCryptPasswordEncoder) {
        this.userDetailsService = userDetailsService;
        this.bCryptPasswordEncoder = bCryptPasswordEncoder;
    }

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(userDetailsService).passwordEncoder(bCryptPasswordEncoder);
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        CustomAuthenticationFilter customAuthenticationFilter = new CustomAuthenticationFilter(authenticationManagerBean());
        customAuthenticationFilter.setFilterProcessesUrl("/api/login");
        http.csrf().disable();
        http.sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS);
        http.authorizeRequests().antMatchers("/api/login/**", "/api/token/refresh/**", "/swagger-ui.html", "/swagger-ui/**").permitAll().and().httpBasic();
        http.authorizeRequests()
                .antMatchers(GET,
                        "/api/v1/users/**",
                        "/api/v1/roles/**",
                        "/api/v1/posts/**",
                        "/api/v1/tags/**",
                        "/api/v1/comments/**")
                .hasAnyAuthority("ROLE_ADMIN");
        http.authorizeRequests()
                .antMatchers(
                        POST,
                        "/api/v1/users/**",
                        "/api/v1/roles/**",
                        "/api/v1/posts/**",
                        "/api/v1/tags/**",
                        "/api/v1/comments/**")
                .hasAnyAuthority("ROLE_ADMIN");
        http.authorizeRequests()
                .antMatchers(
                        PUT,
                        "/api/v1/users/**",
                        "/api/v1/roles/**",
                        "/api/v1/posts/**",
                        "/api/v1/tags/**",
                        "/api/v1/comments/**")
                .hasAnyAuthority("ROLE_ADMIN");
        http.authorizeRequests()
                .antMatchers(
                        DELETE,
                        "/api/v1/users/**",
                        "/api/v1/roles/**",
                        "/api/v1/posts/**",
                        "/api/v1/tags/**",
                        "/api/v1/comments/**")
                .hasAnyAuthority("ROLE_ADMIN");
        http.authorizeRequests().anyRequest().authenticated();
        http.addFilter(customAuthenticationFilter);
        http.addFilterBefore(new CustomAuthorizationFilter(), UsernamePasswordAuthenticationFilter.class);
    }

    @Bean
    @Override
    public AuthenticationManager authenticationManagerBean() throws Exception {
        return super.authenticationManagerBean();
    }
}
