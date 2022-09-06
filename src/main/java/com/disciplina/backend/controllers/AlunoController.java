package com.disciplina.backend.controllers;

import com.disciplina.backend.models.Aluno;
import com.disciplina.backend.services.AlunoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;

@RestController
@RequestMapping("/alunos")
public class AlunoController {

    @Autowired
    AlunoService service;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public ResponseEntity<Aluno> create(@RequestBody Aluno aluno){
        Aluno alunoCreated = service.create(aluno);

        URI uri = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}").buildAndExpand(alunoCreated.getId()).toUri();

        return ResponseEntity.created(uri).body(alunoCreated);
    }
}
