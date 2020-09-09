package br.com.zapelini.lanzendorf.facialrecognitionapi.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Data
@Entity
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "professor")
@PrimaryKeyJoinColumn(name = "idUsuario")
public class Professor extends Usuario {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_professor")
    private Long idProfessor;

    @Override
    public String getTipo() {
        return "Professor";
    }
}
