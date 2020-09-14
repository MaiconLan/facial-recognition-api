package br.com.zapelini.lanzendorf.facialrecognitionapi.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(code = HttpStatus.NOT_FOUND)
public class ApiException extends Exception {

    public ApiException(String content) {
        super(content);
    }

}
