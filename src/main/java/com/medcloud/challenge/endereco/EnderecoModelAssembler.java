package com.medcloud.challenge.endereco;

import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.server.RepresentationModelAssembler;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Component;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@Component
class EnderecoModelAssembler implements RepresentationModelAssembler<Endereco, EntityModel<Endereco>> {

    @Override
    @NonNull
    public EntityModel<Endereco> toModel(@NonNull Endereco endereco) {

        return EntityModel.of(
                endereco,
                linkTo(methodOn(EnderecoController.class).getOne(endereco.getIdEndereco())).withSelfRel(),
                linkTo(methodOn(EnderecoController.class).getAll()).withRel("enderecos")
        );
    }
}
