package br.com.zapelini.lanzendorf.facialrecognitionapi.service.turma;

import br.com.zapelini.lanzendorf.facialrecognitionapi.exceptionhandler.exception.ApiException;
import br.com.zapelini.lanzendorf.facialrecognitionapi.exceptionhandler.exception.RecursoInexistenteException;
import br.com.zapelini.lanzendorf.facialrecognitionapi.model.Aluno;
import br.com.zapelini.lanzendorf.facialrecognitionapi.model.Aula;
import br.com.zapelini.lanzendorf.facialrecognitionapi.model.Presenca;
import br.com.zapelini.lanzendorf.facialrecognitionapi.model.Professor;
import br.com.zapelini.lanzendorf.facialrecognitionapi.model.Tipo;
import br.com.zapelini.lanzendorf.facialrecognitionapi.model.Turma;
import br.com.zapelini.lanzendorf.facialrecognitionapi.model.Usuario;
import br.com.zapelini.lanzendorf.facialrecognitionapi.repository.aula.AulaRepository;
import br.com.zapelini.lanzendorf.facialrecognitionapi.repository.presenca.PresencaRepository;
import br.com.zapelini.lanzendorf.facialrecognitionapi.repository.turma.TurmaRepository;
import br.com.zapelini.lanzendorf.facialrecognitionapi.resource.aluno.dto.AlunoDTO;
import br.com.zapelini.lanzendorf.facialrecognitionapi.resource.aula.dto.AulaDTO;
import br.com.zapelini.lanzendorf.facialrecognitionapi.resource.professor.dto.ProfessorDTO;
import br.com.zapelini.lanzendorf.facialrecognitionapi.resource.turma.dto.AulaDashboardDTO;
import br.com.zapelini.lanzendorf.facialrecognitionapi.resource.turma.dto.CadastroAulaDTO;
import br.com.zapelini.lanzendorf.facialrecognitionapi.resource.turma.dto.TurmaDTO;
import br.com.zapelini.lanzendorf.facialrecognitionapi.service.aluno.AlunoService;
import br.com.zapelini.lanzendorf.facialrecognitionapi.service.freemarker.FreemarkerService;
import br.com.zapelini.lanzendorf.facialrecognitionapi.service.professor.ProfessorService;
import br.com.zapelini.lanzendorf.facialrecognitionapi.service.turma.dto.ExportacaoAulaPdfDTO;
import br.com.zapelini.lanzendorf.facialrecognitionapi.service.turma.dto.ExportacaoTurmaPdfDTO;
import br.com.zapelini.lanzendorf.facialrecognitionapi.service.turma.dto.ExportacaoTurmaJsonDTO;
import br.com.zapelini.lanzendorf.facialrecognitionapi.service.usuario.UsuarioService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.commons.io.FileUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
public class TurmaService {

    @Autowired
    private TurmaRepository turmaRepository;

    @Autowired
    private ProfessorService professorService;

    @Autowired
    private AlunoService alunoService;

    @Autowired
    private AulaRepository aulaRepository;

    @Autowired
    private PresencaRepository presencaRepository;

    @Autowired
    private UsuarioService usuarioService;

    @Autowired
    private ObjectMapper mapper;

    @Autowired
    private FreemarkerService freemarkerService;

    public TurmaDTO criarTurma(TurmaDTO turmaDTO) throws ApiException {
        Turma turma = new Turma(turmaDTO);

        atualizarProfessor(turma, turmaDTO);
        atualizarAlunos(turma, turmaDTO);

        return new TurmaDTO(turmaRepository.save(turma));
    }

    private void atualizarProfessor(Turma turma, TurmaDTO turmaDTO) throws ApiException {
        ProfessorDTO professorDTO = turmaDTO.getProfessor();

        if (professorDTO != null && professorDTO.getIdProfessor() != null) {
            Professor professor = professorService.getProfessor(professorDTO.getIdProfessor());

            turma.setProfessor(professor);
        }
    }

    public TurmaDTO atualizarTurma(Long idTurma, TurmaDTO turmaDTO) throws ApiException {
        Turma turmaSaved = getTurma(idTurma);
        Turma turma = new Turma(turmaDTO);

        BeanUtils.copyProperties(turma, turmaSaved, "idTurma", "alunos", "professor");

        atualizarProfessor(turmaSaved, turmaDTO);
        atualizarAlunos(turmaSaved, turmaDTO);
        atualizarAulas(turmaSaved);

        turmaRepository.save(turmaSaved);

        return getTurmaDTO(idTurma);
    }

    private void atualizarAulas(Turma turma) {
        List<Aula> aulas = aulaRepository.findByTurma(turma);
        turma.getAlunos().forEach(aluno -> aulas.forEach(aula -> {
            Presenca presenca = presencaRepository.findPresencaByAlunoAndAula(aluno, aula).orElse(new Presenca(aula, aluno));
            presencaRepository.save(presenca);
        }));
    }

    private void atualizarAlunos(Turma turma, TurmaDTO turmaDTO) throws ApiException {
        List<Aluno> alunos = new ArrayList<>();
        for (AlunoDTO alunoDTO : turmaDTO.getAlunos()) {
            alunos.add(alunoService.getAluno(alunoDTO.getIdAluno()));
        }
        turma.setAlunos(alunos);
    }

    public Turma getTurma(Long idTurma) throws RecursoInexistenteException {
        return turmaRepository.findById(idTurma).orElseThrow(() -> new RecursoInexistenteException("Turma não encontrada"));
    }

    public TurmaDTO getTurmaDTO(Long idTurma) throws RecursoInexistenteException {
        return new TurmaDTO(getTurma(idTurma));
    }

    public Page<TurmaDTO> filtrar(Pageable pageable, String materia, String periodo, String tipo, Boolean finalizada) {
        Tipo tipoEnum = Tipo.parse(tipo);

        List<TurmaDTO> turmas = turmaRepository.filter(pageable, materia, periodo, tipoEnum, finalizada)
                .stream()
                .map(TurmaDTO::new)
                .collect(Collectors.toList());

        return new PageImpl<>(turmas,
                pageable,
                turmaRepository.filterCount(materia, periodo, tipoEnum, finalizada)
        );
    }

    public void excluir(Long idTurma) throws RecursoInexistenteException {
        Turma turma = getTurma(idTurma);
        turmaRepository.delete(turma);
    }

    public CadastroAulaDTO criarAula(Long idTurma, CadastroAulaDTO aulaDTO) throws ApiException {
        Turma turma = getTurma(idTurma);
        Aula aula = new Aula(aulaDTO);
        aula.setTurma(turma);
        aula = aulaRepository.save(aula);

        atualizarAulas(turma);

        return new CadastroAulaDTO(aula);
    }

    public List<CadastroAulaDTO> getAulas(Long idTurma) throws RecursoInexistenteException {
        Turma turma = getTurma(idTurma);
        return aulaRepository.findByTurma(turma).stream().map(CadastroAulaDTO::new).collect(Collectors.toList());
    }

    public CadastroAulaDTO getAulaDTO(Long idAula) throws RecursoInexistenteException {
        return new CadastroAulaDTO(getAula(idAula));
    }

    public Aula getAula(Long idAula) throws RecursoInexistenteException {
        return aulaRepository.findById(idAula).orElseThrow(() -> new RecursoInexistenteException("Aula não encontrada"));
    }

    public void removerAula(Long idAula) throws RecursoInexistenteException {
        Aula aula = getAula(idAula);
        aulaRepository.delete(aula);
    }

    public CadastroAulaDTO atualizarTurma(Long idAula, CadastroAulaDTO aulaDTO) throws RecursoInexistenteException {
        Aula aulaSaved = getAula(idAula);
        Aula aula = new Aula(aulaDTO);

        BeanUtils.copyProperties(aula, aulaSaved, "idAula", "turma");

        aulaRepository.save(aulaSaved);

        return getAulaDTO(idAula);
    }

    public AulaDashboardDTO getDadosDashboard() {
        AulaDashboardDTO dados = new AulaDashboardDTO();
        dados.setAulasDoDia(aulaRepository.aulasdoDia());
        return dados;
    }

    public List<TurmaDTO> listar(Long idUsuario) throws RecursoInexistenteException {
        Usuario usuario = usuarioService.findUsuario(idUsuario);

        if (usuario.isProfessor()) {
            return turmaRepository.findAllByProfessor(usuario.getProfessor())
                    .stream()
                    .map(TurmaDTO::new)
                    .collect(Collectors.toList());

        } else if (usuario.isCoordenador() || usuario.isAdministrador()) {
            return turmaRepository.findAll()
                    .stream()
                    .map(TurmaDTO::new)
                    .collect(Collectors.toList());
        }

        return new ArrayList<>();
    }

    public byte[] exportarAulas(Long idTurma, String formato) throws Exception {
        return switch (formato) {
            case "json" -> exportarAulasJson(idTurma);
            case "pdf" -> exportarAulasPDF(idTurma);
            default -> throw new ApiException("Formato não informado");
        };
    }

    private byte[] exportarAulasJson(Long idTurma) throws RecursoInexistenteException, IOException {
        Turma turma = getTurma(idTurma);
        List<Aula> aulas = aulaRepository.findByTurma(turma);

        ExportacaoTurmaJsonDTO turmaDTO = new ExportacaoTurmaJsonDTO(turma);
        turmaDTO.setAulas(aulas.stream().map(AulaDTO::new).collect(Collectors.toList()));
        turmaDTO.setAlunos(turma.getAlunos().stream().map(AlunoDTO::new).collect(Collectors.toList()));

        File tmpFile = File.createTempFile("aula", ".json");
        FileWriter writer = new FileWriter(tmpFile);
        writer.write(mapper.writeValueAsString(turmaDTO));
        writer.close();

        return FileUtils.readFileToByteArray(tmpFile);
    }

    private byte[] exportarAulasPDF(Long idTurma) throws Exception {
        Turma turma = getTurma(idTurma);
        List<Aula> aulas = aulaRepository.findByTurma(turma);


        ExportacaoTurmaPdfDTO turmaDTO = new ExportacaoTurmaPdfDTO(turma);
        turmaDTO.setAulas(aulas.stream().map(ExportacaoAulaPdfDTO::new).collect(Collectors.toList()));

        Map<String, Object> parametros = new HashMap<>();
        parametros.put("turma", turmaDTO);

        return freemarkerService.gerarArquivo("aula.ftl", parametros);
    }
}
