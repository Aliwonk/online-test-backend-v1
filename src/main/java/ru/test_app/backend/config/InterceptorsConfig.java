package ru.test_app.backend.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import ru.test_app.backend.interceptors.AuthInterceptor;

@Configuration
@EnableWebMvc
public class InterceptorsConfig implements WebMvcConfigurer {
    @Autowired
    private AuthInterceptor authInterceptor;

    @Override
    public void addCorsMappings(CorsRegistry registry) {
        registry.addMapping("/**")
                .allowedOrigins("*")
                .allowedHeaders("Content-Type", "Authorization")
                .allowedMethods("GET", "POST", "PUT", "DELETE", "PATCH", "OPTIONS");
    }

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(this.authInterceptor).addPathPatterns("/**").excludePathPatterns("/auth/**");
    }
}
