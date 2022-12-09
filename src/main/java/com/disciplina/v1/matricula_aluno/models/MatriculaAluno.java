package com.disciplina.v1.matricula_aluno.models;

import com.disciplina.v1.secretaria.models.Aluno;
import com.disciplina.v1.secretaria.models.Disciplina;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.io.Serializable;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class MatriculaAluno implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Double nota1;

    private Double nota2;

    // Tem que ter: Aluno, Disciplina
    // status -> String. -> APROVADO, REPROVADO, TRANCADO, MATRICULADO

    @ManyToOne
    @JoinColumn(name = "aluno_id")
    private Aluno aluno;

    @ManyToOne
    @JoinColumn(name = "disciplina_id")
    private Disciplina disciplina;

    private String status;
}
