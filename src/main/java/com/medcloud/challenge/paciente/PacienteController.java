package com.medcloud.challenge.paciente;

import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.IanaLinkRelations;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@RestController
@RequestMapping("/api")
@Validated
public class PacienteController {

    private final PacienteService pacienteService;

    public PacienteController(PacienteService pacienteService) {

        this.pacienteService = pacienteService;
    }

    /**
     * Busca pacientes cadastrados no sistema.
     * @param email Parâmetro opcional para busca por email.
     * @return Representação JSON RESTful de pacientes.
     */
    @GetMapping("/pacientes")
    CollectionModel<EntityModel<Paciente>> getAll(@RequestParam(required = false) String email) {

        return CollectionModel.of(
                pacienteService.getAll(email),
                linkTo(methodOn(PacienteController.class).getAll(email)).withSelfRel()
        );
    }


    /**
     * Busca todos os pacientes cadastrados em um mesmo endereço.
     * @param enderecoId Idedntificador do endereço.
     * @return Representação JSON RESTful de pacientes.
     */
    @GetMapping("/enderecos/{enderecoId}/pacientes")
    CollectionModel<EntityModel<Paciente>> getAllPacientesByEndereco(@PathVariable Long enderecoId) {

        return CollectionModel.of(
                pacienteService.getAllPacientesByEndereco(enderecoId),
                linkTo(methodOn(PacienteController.class).getAllPacientesByEndereco(enderecoId)).withSelfRel()
        );
    }


    /**
     * Busca um paciente específico no sistema.
     * @param id Identificador do paciente a ser buscado.
     * @return Representação JSON RESTful do paciente.
     */
    @GetMapping("/pacientes/{id}")
    EntityModel<Paciente> getOne(@PathVariable Long id) {

        return pacienteService.getOne(id);
    }


    /**
     * Armazena os dados do paciente recebido em uma nova tupla no banco de dados.
     * @param novoPaciente Representação do paciente.
     * @return Representação JSON RESTful do paciente após o cadastro.
     */
    @PostMapping("/pacientes")
    ResponseEntity<?> store(@RequestBody Paciente novoPaciente) {

        EntityModel<Paciente> pacienteModel = pacienteService.store(novoPaciente);

        return ResponseEntity.created(pacienteModel.getRequiredLink(IanaLinkRelations.SELF).toUri()).body(pacienteModel);
    }


    /**
     * Atualiza um registro de um paciente préviamente cadastrado.
     * @param novoPaciente Representação do paciente após atualizações.
     * @param id Identificador do paciente a ser atualizado.
     * @return Representação RESTful do paciente após atualizações.
     */
    @PutMapping("/pacientes/{id}")
    ResponseEntity<?> update(@RequestBody Paciente novoPaciente, @PathVariable Long id) {

        EntityModel<Paciente> pacienteModel = pacienteService.update(novoPaciente, id);

        return ResponseEntity.created(pacienteModel.getRequiredLink(IanaLinkRelations.SELF).toUri()).body(pacienteModel);
    }


    /**
     * Remove o registro de um paciente no sistema.
     * @param id Identificador do paciente
     * @return Resposta sem corpo.
     */
    @DeleteMapping("/pacientes/{id}")
    ResponseEntity<?> delete(@PathVariable Long id) {

        pacienteService.delete(id);

        return ResponseEntity.noContent().build();
    }

}
