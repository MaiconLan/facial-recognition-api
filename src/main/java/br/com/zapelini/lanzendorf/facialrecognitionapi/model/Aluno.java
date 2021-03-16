package br.com.zapelini.lanzendorf.facialrecognitionapi.model;

import br.com.zapelini.lanzendorf.facialrecognitionapi.resource.aluno.dto.AlunoDTO;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@Entity
@Table(name = "aluno")
@NoArgsConstructor
public class Aluno {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_aluno")
    private Long idAluno;

    private String matricula;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "id_usuario")
    private Usuario usuario;

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "aluno")
    private List<Foto> fotos = new ArrayList<>();

    public Aluno(AlunoDTO alunoDTO) {
        this.usuario = new Usuario(alunoDTO);
        this.matricula = alunoDTO.getMatricula();
    }

}
