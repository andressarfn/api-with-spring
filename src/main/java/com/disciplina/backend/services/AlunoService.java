package com.disciplina.backend.services;

import com.disciplina.backend.models.Aluno;
import com.disciplina.backend.repositories.AlunoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class AlunoService {

    @Autowired
    AlunoRepository repository;

    public Aluno create(Aluno aluno){
        return repository.save(aluno);
    }
}
