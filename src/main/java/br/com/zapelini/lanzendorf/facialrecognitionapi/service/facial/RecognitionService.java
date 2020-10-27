package br.com.zapelini.lanzendorf.facialrecognitionapi.service.facial;

import br.com.zapelini.lanzendorf.facialrecognitionapi.exceptionhandler.exception.ApiException;
import br.com.zapelini.lanzendorf.facialrecognitionapi.model.Aluno;
import br.com.zapelini.lanzendorf.facialrecognitionapi.model.Foto;
import br.com.zapelini.lanzendorf.facialrecognitionapi.repository.foto.FotoRepository;
import br.com.zapelini.lanzendorf.facialrecognitionapi.service.aluno.AlunoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.util.List;

@Service
public class RecognitionService {

    @Autowired
    private FotoRepository fotoRepository;

    @Autowired
    private AlunoService alunoService;

    public void treinarModelo() {
        List<Foto> fotos = fotoRepository.findAll();
        fotos.forEach(RecognitionUtil::extrairFace);
        RecognitionUtil.trainPhotos(fotos);
    }

    public void detectar(MultipartFile foto) throws IOException, ApiException {
        Long idAluno = RecognitionUtil.testPhoto(multipartToFile(foto, foto.getOriginalFilename()));
        if (idAluno == -1) {
            System.out.print("Nao reconhecido");
        } else {
            Aluno aluno = alunoService.getAluno(idAluno);
            System.out.println("ALUNO: " + aluno.getUsuario().getNome());
        }
    }

    public void detectarVariosRostos(MultipartFile foto) throws IOException, ApiException {
        List<Long> idAlunos = RecognitionUtil.testMultipleFaces(multipartToFile(foto, foto.getOriginalFilename()));
        idAlunos.forEach(idALuno -> {
            try {
                Aluno aluno = alunoService.getAluno(idALuno);
                System.out.println("Aluno reconhecido: " + aluno.getUsuario().getNome());
            } catch (ApiException e) {
                e.printStackTrace();
            }
        });
    }

    public static File multipartToFile(MultipartFile multipart, String fileName) throws IllegalStateException, IOException {
        File convFile = new File(System.getProperty("java.io.tmpdir") + "/" + fileName);
        multipart.transferTo(convFile);
        return convFile;
    }
}
