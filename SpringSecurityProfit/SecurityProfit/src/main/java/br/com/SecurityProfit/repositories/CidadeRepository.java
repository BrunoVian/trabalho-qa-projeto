package br.com.SecurityProfit.repositories;

import br.com.SecurityProfit.models.pessoa.Cidade;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Optional;

public interface CidadeRepository extends JpaRepository<Cidade, Long> {

    @Query
    Optional<Cidade> findCidadeByCodIbge(int CodIbge);

}
