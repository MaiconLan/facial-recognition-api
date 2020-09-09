package br.com.zapelini.lanzendorf.facialrecognitionapi.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.PrimaryKeyJoinColumn;
import javax.persistence.Table;

@Data
@Entity
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "coordenador")
@PrimaryKeyJoinColumn(name = "idUsuario")
public class Coordenador extends Usuario {

    @Override
    public String getTipo() {
        return "Coordenador";
    }
}
