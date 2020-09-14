package br.com.zapelini.lanzendorf.facialrecognitionapi.model;

import lombok.Data;

import javax.persistence.*;

@Data
@Entity
@Table(name = "coordenador")
public class Coordenador {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_coordenador")
    private Long idCoordenador;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "id_usuario")
    private Usuario usuario;

}
