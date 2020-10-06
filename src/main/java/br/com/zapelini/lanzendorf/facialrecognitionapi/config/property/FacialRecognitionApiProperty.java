package br.com.zapelini.lanzendorf.facialrecognitionapi.config.property;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;

@Getter
@ConfigurationProperties("facialrecognition")
public class FacialRecognitionApiProperty {

    private final Seguranca seguranca = new Seguranca();

    @Getter
    @Setter
    public static class Seguranca {
        private Boolean enableHttps;
        private String originPermitida;
    }

}
