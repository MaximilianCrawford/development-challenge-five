package com.medcloud.challenge.paciente;

import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.server.RepresentationModelAssembler;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Component;


import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@Component
class PacienteModelAssembler implements RepresentationModelAssembler<Paciente, EntityModel<Paciente>> {

    @Override
    @NonNull
    public EntityModel<Paciente> toModel(@NonNull Paciente paciente) {

        return EntityModel.of(
                paciente,
                linkTo(methodOn(PacienteController.class).getOne(paciente.getIdPaciente())).withSelfRel(),
                linkTo(methodOn(PacienteController.class).getAll(null)).withRel("pacientes")
        );
    }

}
