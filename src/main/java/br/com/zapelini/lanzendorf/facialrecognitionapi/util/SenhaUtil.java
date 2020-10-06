package br.com.zapelini.lanzendorf.facialrecognitionapi.util;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Component;

@Component
public class SenhaUtil {

    public static void main(String[] args) {
        System.out.println(new BCryptPasswordEncoder().encode("angular"));
    }

    public String criptografar(String text) {
        return new BCryptPasswordEncoder().encode(text);
    }

}
