package br.com.fiap.sustentabilidade.repository;

import br.com.fiap.sustentabilidade.model.Empresa;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Optional;

public interface EmpresaRepository extends JpaRepository<Empresa, Long> {

    UserDetails findByEmail(String email);

    boolean existsByEmail(String email);
}
