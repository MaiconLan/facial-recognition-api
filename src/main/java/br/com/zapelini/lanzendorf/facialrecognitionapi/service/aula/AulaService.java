package br.com.zapelini.lanzendorf.facialrecognitionapi.service.aula;

import br.com.zapelini.lanzendorf.facialrecognitionapi.exceptionhandler.exception.ApiException;
import br.com.zapelini.lanzendorf.facialrecognitionapi.exceptionhandler.exception.RecursoInexistenteException;
import br.com.zapelini.lanzendorf.facialrecognitionapi.model.Aluno;
import br.com.zapelini.lanzendorf.facialrecognitionapi.model.Aula;
import br.com.zapelini.lanzendorf.facialrecognitionapi.model.Turma;
import br.com.zapelini.lanzendorf.facialrecognitionapi.repository.aula.AulaRepository;
import br.com.zapelini.lanzendorf.facialrecognitionapi.repository.presenca.PresencaRepository;
import br.com.zapelini.lanzendorf.facialrecognitionapi.resource.aula.dto.AulaDTO;
import br.com.zapelini.lanzendorf.facialrecognitionapi.service.facial.RecognitionService;
import br.com.zapelini.lanzendorf.facialrecognitionapi.service.turma.TurmaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class AulaService {

    @Autowired
    private AulaRepository aulaRepository;

    @Autowired
    private TurmaService turmaService;

    @Autowired
    private RecognitionService recognitionService;

    @Autowired
    private PresencaRepository presencaRepository;

    public Aula getAula(Long idAula) throws RecursoInexistenteException {
        return aulaRepository.findById(idAula).orElseThrow(() -> new RecursoInexistenteException("Aula não encontrada"));
    }

    public AulaDTO getAulaDTO(Long idAula) throws RecursoInexistenteException {
        return new AulaDTO(getAula(idAula));
    }

    public List<AulaDTO> getAulasDTO(Long idTurma) throws RecursoInexistenteException {
        Turma turma = turmaService.getTurma(idTurma);
        return aulaRepository.findByTurma(turma).stream().map(AulaDTO::new).collect(Collectors.toList());
    }

    public void detectarVariosRostos(Long idAula, MultipartFile foto) throws IOException, ApiException {
        Aula aula = getAula(idAula);
        List<Aluno> alunos = recognitionService.detectarVariosRostos(foto);
        aula.getPresencas().forEach(presenca -> alunos.forEach(aluno -> {
            if(presenca.getAluno().getIdAluno().equals(aluno.getIdAluno())){
                presenca.setPresenca(Boolean.TRUE);
                presencaRepository.save(presenca);
            }
        }));
    }
}
