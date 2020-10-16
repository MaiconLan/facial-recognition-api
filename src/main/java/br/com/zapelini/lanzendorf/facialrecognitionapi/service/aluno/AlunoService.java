package br.com.zapelini.lanzendorf.facialrecognitionapi.service.aluno;

import br.com.zapelini.lanzendorf.facialrecognitionapi.exceptionhandler.exception.ApiException;
import br.com.zapelini.lanzendorf.facialrecognitionapi.model.Aluno;
import br.com.zapelini.lanzendorf.facialrecognitionapi.model.Foto;
import br.com.zapelini.lanzendorf.facialrecognitionapi.model.Usuario;
import br.com.zapelini.lanzendorf.facialrecognitionapi.repository.aluno.AlunoRepository;
import br.com.zapelini.lanzendorf.facialrecognitionapi.repository.foto.FotoRepository;
import br.com.zapelini.lanzendorf.facialrecognitionapi.resource.aluno.dto.AlunoDTO;
import br.com.zapelini.lanzendorf.facialrecognitionapi.resource.aluno.dto.AlunoDashboardDTO;
import br.com.zapelini.lanzendorf.facialrecognitionapi.service.usuario.UsuarioService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.security.NoSuchAlgorithmException;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class AlunoService {

    private static final String PATH_FOTO = "C:\\facial-api\\";

    @Autowired
    private AlunoRepository alunoRepository;

    @Autowired
    private FotoRepository fotoRepository;

    @Autowired
    private UsuarioService usuarioService;

    public AlunoDTO criarAluno(AlunoDTO alunoDTO) throws NoSuchAlgorithmException, ApiException {
        Aluno aluno = new Aluno(alunoDTO);

        Usuario usuario = usuarioService.saveUsuario(alunoDTO, aluno.getUsuario());

        aluno.setUsuario(usuario);

        return new AlunoDTO(alunoRepository.save(aluno));
    }

    public AlunoDTO atualizarAluno(Long idAluno, AlunoDTO alunoDTO) throws ApiException, NoSuchAlgorithmException {
        Aluno alunoSaved = getAluno(idAluno);
        Aluno aluno = new Aluno(alunoDTO);

        BeanUtils.copyProperties(aluno, alunoSaved, "idAluno", "usuario");
        Usuario usuario = usuarioService.saveUsuario(alunoDTO, alunoSaved.getUsuario());

        alunoSaved.setUsuario(usuario);
        alunoRepository.save(alunoSaved);

        return getAlunoDTO(idAluno);
    }

    public Aluno getAluno(Long idAluno) throws ApiException {
        return alunoRepository.findById(idAluno).orElseThrow(() -> new ApiException("Aluno não encontrado!"));
    }

    public AlunoDTO getAlunoDTO(Long idAluno) throws ApiException {
        Aluno aluno = getAluno(idAluno);
        return new AlunoDTO(aluno);
    }

    public Page<AlunoDTO> filtrar(Pageable pageable, String nome, String email, String matricula) {
        List<AlunoDTO> alunos = alunoRepository.filter(pageable, nome, email, matricula)
                .stream()
                .map(AlunoDTO::new)
                .collect(Collectors.toList());

        return new PageImpl<>(alunos,
                              pageable,
                alunoRepository.filterCount(nome, email, matricula)
        );
    }

    public void excluir(Long idAluno) throws ApiException {
        Aluno aluno = getAluno(idAluno);

        validarExclusao(aluno);

        alunoRepository.delete(aluno);
    }

    private void validarExclusao(Aluno aluno) throws ApiException {
        if(alunoRepository.hasTurma(aluno.getIdAluno())){
            throw new ApiException("Este aluno está vinculado em ao menos uma turma");
        }
    }

    public AlunoDashboardDTO getDadosDashboard() {
        AlunoDashboardDTO dados = new AlunoDashboardDTO();
        dados.setAlunosCadastrados(alunoRepository.count());
        return dados;
    }

    @Transactional
    public String uploadFoto(Long idAluno, MultipartFile file) throws IOException, ApiException {
        Aluno aluno = getAluno(idAluno);
        String nomeOriginal = file.getOriginalFilename();
        int indexExtensao = nomeOriginal.lastIndexOf(".");
        String extensao = nomeOriginal.substring(indexExtensao + 1);

        Foto foto = new Foto();
        foto.setAluno(aluno);
        foto.setExtensao(extensao);

        foto = fotoRepository.save(foto);

        String nome = criarNomeFoto(aluno, foto);

        foto.setNome(nome);
        fotoRepository.save(foto);

        OutputStream outputStream = new FileOutputStream(PATH_FOTO + nome + "." + extensao);

        outputStream.write(file.getBytes());
        outputStream.close();

        aluno.getFotos().add(foto);
        alunoRepository.save(aluno);

        return foto.getNome() + "." + foto.getExtensao();
    }

    private String criarNomeFoto(Aluno aluno, Foto foto) {
        return aluno.getIdAluno() + "_" + foto.getIdFoto().toString();
    }
}
