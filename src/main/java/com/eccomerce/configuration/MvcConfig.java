package com.eccomerce.configuration;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration // Indica que esta clase configura Spring Boot
public class MvcConfig implements WebMvcConfigurer {

    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        // Permite acceder a archivos de la carpeta 'imgfolder' vía URL
        // Ejemplo: http://localhost:8080/imgfolder/foto.jpg
        registry.addResourceHandler("/imgfolder/**")
                .addResourceLocations("file:C:/Users/lucas/Documents/IntelliJ proyectos/eccomerce/imgfolder/");

    }}
