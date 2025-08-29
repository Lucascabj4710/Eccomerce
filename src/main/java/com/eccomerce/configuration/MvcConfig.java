package com.eccomerce.configuration;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import java.nio.file.Path;
import java.nio.file.Paths;

@Configuration
public class MvcConfig implements WebMvcConfigurer {

    @Value("${img.folder.path}")
    private String imgFolderPath;

    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        // Convertimos la ruta a absoluta en tiempo de ejecución
        Path uploadDir = Paths.get(imgFolderPath).toAbsolutePath();
        String uploadPath = "file:" + uploadDir + "/";

        registry.addResourceHandler("/imgfolder/**")
                .addResourceLocations(uploadPath);
    }
}
