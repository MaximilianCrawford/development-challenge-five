package com.medcloud.challenge.endereco;

import com.medcloud.challenge.exceptions.ModelNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.EntityModel;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class EnderecoService {

    @Autowired
    private EnderecoRepository repository;
    @Autowired
    private EnderecoModelAssembler assembler;


    public List<EntityModel<Endereco>> getAll() {

        return repository.findAll().stream().map(assembler::toModel).toList();
    }


    public EntityModel<Endereco> getOne(Long id) {

        Endereco endereco = repository.findById(id).orElseThrow(() -> new ModelNotFoundException("Endereço", id));

        return assembler.toModel(endereco);
    }


    public EntityModel<Endereco> store(Endereco endereco) {

        return assembler.toModel(repository.save(endereco));
    }


    EntityModel<Endereco> update(Endereco novoEndereco, Long id) {

        Endereco enderecoAtualizado = repository.findById(id).map(endereco -> {
            endereco.setNumero(novoEndereco.getNumero() != null ? novoEndereco.getNumero() : endereco.getNumero());
            endereco.setComplemento(novoEndereco.getComplemento() != null ? novoEndereco.getComplemento() : endereco.getComplemento());
            endereco.setBairro(novoEndereco.getBairro() != null ? novoEndereco.getBairro() : endereco.getBairro());
            endereco.setCep(novoEndereco.getCep() != null ? novoEndereco.getCep() : endereco.getCep());
            endereco.setUf(novoEndereco.getUf() != null ? novoEndereco.getUf() : endereco.getUf());
            endereco.setRua(novoEndereco.getRua() != null ? novoEndereco.getRua() : endereco.getRua());
            endereco.setCidade(novoEndereco.getCidade() != null ? novoEndereco.getCidade() : endereco.getCidade());
            return repository.save(endereco);
        }).orElseGet(() -> {
            novoEndereco.setIdEndereco(id);
            return repository.save(novoEndereco);
        });

        return assembler.toModel(enderecoAtualizado);
    }


    void delete(Long id) {

        repository.deleteById(id);
    }

}
