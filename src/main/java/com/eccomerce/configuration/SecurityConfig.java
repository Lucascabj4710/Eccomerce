package com.eccomerce.configuration;

import com.eccomerce.configuration.filter.JwtTokenValidator;
import com.eccomerce.util.JwtUtils;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.boot.web.servlet.server.CookieSameSiteSupplier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

import java.util.Arrays;

@Configuration
@EnableWebSecurity
@EnableMethodSecurity
public class SecurityConfig {

    private final JwtUtils jwtUtils;

    public SecurityConfig(JwtUtils jwtUtils) {
        this.jwtUtils = jwtUtils;
    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity httpSecurity) throws Exception {

        return httpSecurity
                .cors(Customizer.withDefaults())
                .csrf(csrf -> csrf.disable())
                .sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                .authorizeHttpRequests(auth -> {
                    auth.requestMatchers(HttpMethod.POST, "user/login").permitAll();
                    // Categorias
                    auth.requestMatchers(HttpMethod.POST, "/category/**").hasRole("ADMIN");
                    auth.requestMatchers(HttpMethod.PUT, "/category/**").hasRole("ADMIN");
                    auth.requestMatchers(HttpMethod.DELETE, "/category/**").hasRole("ADMIN");
                    auth.requestMatchers(HttpMethod.PATCH, "/category/**").hasRole("ADMIN");
                    // Clientes
                    auth.requestMatchers(HttpMethod.POST, "/client/**").hasRole("ADMIN");
                    auth.requestMatchers(HttpMethod.DELETE, "/client/**").hasRole("ADMIN");
                    auth.requestMatchers(HttpMethod.PATCH, "/client/**").hasRole("ADMIN");
                    auth.requestMatchers(HttpMethod.GET, "/client/get/**").hasRole("ADMIN");
                    // Producto detalle de ordenes
                    auth.requestMatchers(HttpMethod.GET, "/orderDetail/get/*").hasRole("ADMIN");
                    auth.requestMatchers(HttpMethod.DELETE, "/orderDetail/delete/**").hasRole("ADMIN");
                    // Productos
                    auth.requestMatchers(HttpMethod.POST,"/products/create").hasRole("ADMIN");
                    auth.requestMatchers(HttpMethod.PUT,"/products/update/**").hasRole("ADMIN");
                    auth.requestMatchers(HttpMethod.DELETE,"/products/delete/**").hasRole("ADMIN");
                    auth.requestMatchers(HttpMethod.GET, "/products/getProducts/**").permitAll();
                    auth.requestMatchers(HttpMethod.GET, "/products/get/**").permitAll();
                    auth.requestMatchers(HttpMethod.GET, "/products/getMaterial").permitAll();
                    auth.requestMatchers("/imgfolder/**").permitAll();

                    // UserEntity
                    auth.requestMatchers(HttpMethod.PUT, "/user/update").hasAnyRole("ADMIN", "USER");

                    // Swagger
                    auth.requestMatchers(HttpMethod.GET, "/swagger-ui.html").permitAll();
                    auth.requestMatchers("/swagger-ui/**").permitAll();
                    auth.requestMatchers("/v3/api-docs/**").permitAll();

                    // Mail
                    auth.requestMatchers(HttpMethod.GET, "Mail/**").permitAll();




                    auth.anyRequest().authenticated();
                })

                .addFilterBefore(new JwtTokenValidator(jwtUtils), BasicAuthenticationFilter.class)

                .build();

    }

    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration authenticationConfiguration) throws Exception {

        return authenticationConfiguration.getAuthenticationManager();
    }

    @Bean
    public AuthenticationProvider authenticationProvider(UserDetailsService userDetailService){

        DaoAuthenticationProvider provider = new DaoAuthenticationProvider();

        provider.setUserDetailsService(userDetailService);
        provider.setPasswordEncoder(passwordEncoder());

        return provider;
    }

    @Bean
    public PasswordEncoder passwordEncoder(){

        return new BCryptPasswordEncoder();
    }

    @Bean
    public CorsConfigurationSource corsConfigurationSource() {
        CorsConfiguration configuration = new CorsConfiguration();
        configuration.setAllowedOrigins(Arrays.asList("http://localhost:5500", "http://127.0.0.1:5500", "http://localhost:5501", "http://127.0.0.1:5501"));
        configuration.setAllowedMethods(Arrays.asList("GET","POST","PUT","DELETE","OPTIONS"));
        configuration.setAllowedHeaders(Arrays.asList("*"));
        configuration.setAllowCredentials(true);
        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", configuration);
        return source;
    }




}
