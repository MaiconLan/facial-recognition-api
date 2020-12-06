package br.com.zapelini.lanzendorf.facialrecognitionapi.service.aula;

import br.com.zapelini.lanzendorf.facialrecognitionapi.exceptionhandler.exception.ApiException;
import br.com.zapelini.lanzendorf.facialrecognitionapi.exceptionhandler.exception.RecursoInexistenteException;
import br.com.zapelini.lanzendorf.facialrecognitionapi.model.Aluno;
import br.com.zapelini.lanzendorf.facialrecognitionapi.model.Aula;
import br.com.zapelini.lanzendorf.facialrecognitionapi.model.Turma;
import br.com.zapelini.lanzendorf.facialrecognitionapi.repository.aula.AulaRepository;
import br.com.zapelini.lanzendorf.facialrecognitionapi.repository.presenca.PresencaRepository;
import br.com.zapelini.lanzendorf.facialrecognitionapi.resource.aluno.dto.FotoDTO;
import br.com.zapelini.lanzendorf.facialrecognitionapi.resource.aula.dto.AulaDTO;
import br.com.zapelini.lanzendorf.facialrecognitionapi.service.facial.RecognitionService;
import br.com.zapelini.lanzendorf.facialrecognitionapi.service.freemarker.FreemarkerService;
import br.com.zapelini.lanzendorf.facialrecognitionapi.service.turma.TurmaService;
import br.com.zapelini.lanzendorf.facialrecognitionapi.service.turma.dto.ExportacaoAulaPdfDTO;
import br.com.zapelini.lanzendorf.facialrecognitionapi.service.turma.dto.ExportacaoTurmaPdfDTO;
import com.lowagie.text.DocumentException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
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

    @Autowired
    private FreemarkerService freemarkerService;

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
        List<Aluno> alunos = recognitionService.detectarVariosRostos(idAula, foto);
        aula.getPresencas().forEach(presenca -> alunos.forEach(aluno -> {
            if (presenca.getAluno().getIdAluno().equals(aluno.getIdAluno())) {
                presenca.setPresenca(Boolean.TRUE);
                presencaRepository.save(presenca);
            }
        }));
    }

    public List<FotoDTO> buscarRostosNaoReconhecidos(Long idAula) throws IOException, RecursoInexistenteException {
        Aula aula = getAula(idAula);
        return recognitionService.buscarRostosNaoReconhecidos(idAula);
    }

    public byte[] exportarAulas(Long idAula) throws Exception {
        Aula aula = getAula(idAula);
        Turma turma = aula.getTurma();

        ExportacaoTurmaPdfDTO turmaDTO = new ExportacaoTurmaPdfDTO(turma);
        turmaDTO.setAulas(Collections.singletonList(new ExportacaoAulaPdfDTO(aula)));

        Map<String, Object> parametros = new HashMap<>();
        parametros.put("turma", turmaDTO);

        return freemarkerService.gerarArquivo("aula.ftl", parametros);
    }
}
