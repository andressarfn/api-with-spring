package com.disciplina.v1.secretaria.services;

import com.disciplina.v1.secretaria.repositories.ProfessorRepository;
import com.disciplina.v1.secretaria.models.Professor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ProfessorService {

    @Autowired
    ProfessorRepository repository;

    public Professor create(Professor professor){
        return repository.save(professor);
    }

}
