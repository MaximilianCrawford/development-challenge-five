package com.medcloud.challenge.endereco;

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
public class EnderecoController {

    private final EnderecoService enderecoService;

    public EnderecoController(EnderecoService enderecoService) {

        this.enderecoService = enderecoService;
    }


    @GetMapping("/enderecos")
    CollectionModel<EntityModel<Endereco>> getAll() {

        return CollectionModel.of(
                enderecoService.getAll(),
                linkTo(methodOn(EnderecoController.class).getAll()).withSelfRel()
        );
    }


    @GetMapping("/enderecos/{id}")
    EntityModel<Endereco> getOne(@PathVariable("id") Long id) {

        return enderecoService.getOne(id);
    }


    @PostMapping("/enderecos")
    ResponseEntity<EntityModel<Endereco>> store(@RequestBody Endereco endereco) {

        EntityModel<Endereco> enderecoModel = enderecoService.store(endereco);

        return ResponseEntity.created(enderecoModel.getRequiredLink(IanaLinkRelations.SELF).toUri()).body(enderecoModel);

    }


    @PutMapping("/enderecos/{id}")
    ResponseEntity<?> update(@RequestBody Endereco novoEndereco, @PathVariable Long id) {

        EntityModel<Endereco> enderecoModel = enderecoService.update(novoEndereco, id);

        return ResponseEntity.created(enderecoModel.getRequiredLink(IanaLinkRelations.SELF).toUri()).body(enderecoModel);
    }


    @DeleteMapping("/enderecos/{id}")
    ResponseEntity<?> delete(@PathVariable Long id) {

        enderecoService.delete(id);

        return ResponseEntity.noContent().build();
    }

}
