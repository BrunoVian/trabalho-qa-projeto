package br.com.SecurityProfit.repositories;

import br.com.SecurityProfit.models.pessoa.Estado;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface EstadoRepository extends JpaRepository<Estado, Long> {
}
