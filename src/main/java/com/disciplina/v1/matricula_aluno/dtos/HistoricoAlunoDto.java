package com.disciplina.v1.matricula_aluno.dtos;

import lombok.Data;

import java.util.List;

@Data
public class HistoricoAlunoDto {
    private String nomeAluno;
    private String cursoAluno;
    private List<DisciplinaAlunoDto> disciplinasAlunoList;

}
