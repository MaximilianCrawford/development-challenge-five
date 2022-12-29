package com.medcloud.challenge.paciente;

import com.medcloud.challenge.endereco.Endereco;
import com.medcloud.challenge.endereco.EnderecoRepository;
import com.medcloud.challenge.exceptions.ModelNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.EntityModel;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class PacienteService {

    @Autowired
    private PacienteRepository pacienteRepository;
    @Autowired
    private EnderecoRepository enderecoRepository;
    @Autowired
    private PacienteModelAssembler assembler;


    public List<EntityModel<Paciente>> getAll(String email) {

        // Operador ternário para determinar a necessidade de busca por email
        return new ArrayList<>(email == null
                ? pacienteRepository.findAll().stream().map(assembler::toModel).toList()
                : pacienteRepository.findByEmail(email).stream().map(assembler::toModel).toList()
        );
    }


    public List<EntityModel<Paciente>> getAllPacientesByEndereco(Long enderecoId) {

        Endereco endereco = enderecoRepository.findById(enderecoId).orElseThrow(() -> new ModelNotFoundException("Endereço", enderecoId));

        return pacienteRepository.findByEndereco(endereco).stream().map(assembler::toModel).toList();
    }


    public EntityModel<Paciente> getOne(Long id) {

        Paciente paciente = pacienteRepository.findById(id).orElseThrow(() -> new ModelNotFoundException("Paciente", id));

        return assembler.toModel(paciente);
    }


    public EntityModel<Paciente> store(Paciente novoPaciente) {

        return assembler.toModel(pacienteRepository.save(novoPaciente));
    }


    public EntityModel<Paciente> update(Paciente novoPaciente, Long id) {

        Paciente pacienteAtualizado = pacienteRepository.findById(id).map(paciente -> {
            paciente.setEmail(novoPaciente.getEmail() != null ? novoPaciente.getEmail() : paciente.getEmail());
            paciente.setNome(novoPaciente.getNome() != null ? novoPaciente.getNome() : paciente.getNome());
            paciente.setDataNascimento(novoPaciente.getDataNascimento() != null ? novoPaciente.getDataNascimento() : paciente.getDataNascimento());
            paciente.setEndereco(novoPaciente.getEndereco() != null ? novoPaciente.getEndereco() : paciente.getEndereco());
            return pacienteRepository.save(paciente);
        }).orElseGet(() -> {
            novoPaciente.setIdPaciente(id);
            return pacienteRepository.save(novoPaciente);
        });

        return assembler.toModel(pacienteAtualizado);
    }


    public void delete(Long id) {

        pacienteRepository.deleteById(id);
    }

}
