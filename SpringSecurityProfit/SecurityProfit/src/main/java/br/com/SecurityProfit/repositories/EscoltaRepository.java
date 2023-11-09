package br.com.SecurityProfit.repositories;

import br.com.SecurityProfit.models.escolta.Arquivo;
import br.com.SecurityProfit.models.escolta.Escolta;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface EscoltaRepository extends JpaRepository<Escolta, Long> {

    List<Escolta> findByAgentes_Id(Long agenteId);


}
