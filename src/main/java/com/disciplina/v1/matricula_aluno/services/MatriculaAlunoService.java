package com.disciplina.v1.matricula_aluno.services;

import com.disciplina.v1.matricula_aluno.dtos.DisciplinaAlunoDto;
import com.disciplina.v1.matricula_aluno.dtos.HistoricoAlunoDto;
import com.disciplina.v1.matricula_aluno.dtos.MatriculaAlunoNotasOnlyDto;
import com.disciplina.v1.matricula_aluno.models.MatriculaAluno;
import com.disciplina.v1.matricula_aluno.repositories.MatriculaAlunoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.server.ResponseStatusException;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

@Service
public class MatriculaAlunoService {

    @Autowired
    MatriculaAlunoRepository repository;

    public MatriculaAluno create(MatriculaAluno matriculaAluno){
        matriculaAluno.setStatus("MATRICULADO");
        return repository.save(matriculaAluno);
    }

    public void updateGrades(MatriculaAlunoNotasOnlyDto notasOnlyDto, Long matriculaAlunoId) {
        Optional<MatriculaAluno> matriculaAlunoToUpdate = repository.findById(matriculaAlunoId);

        boolean needUpdate = false;

        if (StringUtils.hasLength(notasOnlyDto.getNota1().toString())) {
            matriculaAlunoToUpdate.ifPresent(matriculaAluno -> matriculaAluno.setNota1(notasOnlyDto.getNota1()));
            needUpdate = true;
        }

        if (StringUtils.hasLength(notasOnlyDto.getNota2().toString())) {
            matriculaAlunoToUpdate.ifPresent(matriculaAluno -> matriculaAluno.setNota2(notasOnlyDto.getNota2()));
            needUpdate = true;
        }

        if (needUpdate) {
            if (matriculaAlunoToUpdate.get().getNota1() != null && matriculaAlunoToUpdate.get().getNota2() != null) {
                if ((matriculaAlunoToUpdate.get().getNota1() + matriculaAlunoToUpdate.get().getNota2()) / 2 >= 7) {
                    matriculaAlunoToUpdate.ifPresent(matriculaAluno -> matriculaAluno.setStatus("APROVADO"));
                } else {
                    matriculaAlunoToUpdate.ifPresent(matriculaAluno -> matriculaAluno.setStatus("REPROVADO"));
                }

            }
            repository.save(matriculaAlunoToUpdate.get());
        }

    }

    public void updateStatusToBreak(Long matriculaAlunoId){
        Optional<MatriculaAluno> matriculaAlunoToUpdate = repository.findById(matriculaAlunoId);

        if(matriculaAlunoToUpdate.isPresent()){
            if(Objects.equals(matriculaAlunoToUpdate.get().getStatus(), "MATRICULADO")){
                matriculaAlunoToUpdate.ifPresent(matriculaAluno -> matriculaAluno.setStatus("TRANCADA"));
            } else {
                throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Só é possível trancar com status MATRICULADO");
            }
        } else {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Matricula não encontrada");
        }
        repository.save(matriculaAlunoToUpdate.get());
    }

    public HistoricoAlunoDto getHistoricoFromAluno(Long alunoId){
        List<MatriculaAluno> matriculasDoAluno = repository.findByAlunoId(alunoId);

        if(!matriculasDoAluno.isEmpty()){
            HistoricoAlunoDto historico = new HistoricoAlunoDto();

            historico.setNomeAluno(matriculasDoAluno.get(0).getAluno().getNome());
            historico.setCursoAluno(matriculasDoAluno.get(0).getAluno().getCurso());
            List<DisciplinaAlunoDto> disciplinaList = new ArrayList<>();

            for(MatriculaAluno matricula: matriculasDoAluno){
                DisciplinaAlunoDto disciplinasAlunoDto = new DisciplinaAlunoDto();

                disciplinasAlunoDto.setNomeDisciplina(matricula.getDisciplina().getNome());
                disciplinasAlunoDto.setProfessorDisciplina(matricula.getDisciplina().getProfessor().getNome());
                disciplinasAlunoDto.setNota1(matricula.getNota1());
                disciplinasAlunoDto.setNota2(matricula.getNota2());
                if(matricula.getNota1() != null && matricula.getNota2() != null){
                    disciplinasAlunoDto.setMedia((matricula.getNota1() + matricula.getNota2()) / 2);
                } else {
                    disciplinasAlunoDto.setMedia(null);
                }
                disciplinasAlunoDto.setStatus(matricula.getStatus());
                disciplinaList.add(disciplinasAlunoDto);
            }
            historico.setDisciplinasAlunoList(disciplinaList);

            return historico;
        }
        throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Esse aluno não possui matriculas");
    }
}
