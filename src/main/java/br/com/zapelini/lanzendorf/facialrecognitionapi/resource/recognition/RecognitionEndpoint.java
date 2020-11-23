package br.com.zapelini.lanzendorf.facialrecognitionapi.resource.recognition;

import br.com.zapelini.lanzendorf.facialrecognitionapi.exceptionhandler.exception.ApiException;
import br.com.zapelini.lanzendorf.facialrecognitionapi.service.facial.RecognitionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@RestController
@RequestMapping("/")
public class RecognitionEndpoint {

    @Autowired
    private RecognitionService recognitionService;

    @PostMapping("/treinamento")
    public void treinarModelo() {
        recognitionService.treinarModelo();
    }

    @PostMapping("/detectar")
    public void detectar(@RequestParam(name = "foto") MultipartFile foto) throws IOException, ApiException {
        recognitionService.detectar(foto);
    }

}

