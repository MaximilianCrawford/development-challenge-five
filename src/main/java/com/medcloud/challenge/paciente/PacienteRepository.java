package com.medcloud.challenge.paciente;

import com.medcloud.challenge.endereco.Endereco;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
interface PacienteRepository extends JpaRepository<Paciente, Long> {

    List<Paciente> findByEmail(String email);
    List<Paciente> findByEndereco(Endereco endereco);

}
