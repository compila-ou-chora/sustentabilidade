package br.com.fiap.sustentabilidade.repository;

import br.com.fiap.sustentabilidade.model.EmissaoCarbono;
import org.springframework.data.jpa.repository.JpaRepository;

public interface EmissaoCarbonoRepository extends JpaRepository<EmissaoCarbono, Long> {
}
