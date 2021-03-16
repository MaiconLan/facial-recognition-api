package br.com.zapelini.lanzendorf.facialrecognitionapi.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.view.freemarker.FreeMarkerConfigurer;

@Configuration
public class FreemarkerConfig {

    @Bean
    public FreeMarkerConfigurer freemarker() {
        FreeMarkerConfigurer freeMarkerConfigurer = new FreeMarkerConfigurer();
        freeMarkerConfigurer.setTemplateLoaderPath("classpath:/freemarker");
        return freeMarkerConfigurer;
    }
}
