package br.com.mercadoinstrumental.configuration;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class WebConfig implements WebMvcConfigurer {

    @Value("${upload.imagem.diretorio}")
    private String uploadDir;
    
    @Override
    public void addCorsMappings(CorsRegistry registry) {
        //registry.addMapping("/**").allowedOrigins("http://example.com");
        // ou para permitir de qualquer origem
        registry.addMapping("/**").allowedOrigins("*").allowedMethods("*").allowedHeaders("*");
    }
    
    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        // Ajusta para o prefixo file:/// e barra no fim, caso não tenha
        String resourceLocation = uploadDir;
        if (!resourceLocation.endsWith("/")) {
            resourceLocation += "/";
        }
        if (!resourceLocation.startsWith("file:/")) {
            resourceLocation = "file:///" + resourceLocation.replace("\\", "/");
        }

        registry.addResourceHandler("/uploads/**").addResourceLocations(resourceLocation);
    }
    
}