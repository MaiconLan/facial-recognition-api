package br.com.zapelini.lanzendorf.facialrecognitionapi.exceptionhandler.exception;

public class ApiException extends Exception {
    public ApiException() {
    }

    public ApiException(String message) {
        super(message);
    }
}
