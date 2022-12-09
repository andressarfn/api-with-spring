package com.disciplina.v1.secretaria.services;

import com.disciplina.v1.secretaria.models.Disciplina;
import com.disciplina.v1.secretaria.repositories.DisciplinaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class DisciplinaService {

    @Autowired
    DisciplinaRepository repository;

    public Disciplina create(Disciplina disciplina){
        return  repository.save(disciplina);
    }
}
