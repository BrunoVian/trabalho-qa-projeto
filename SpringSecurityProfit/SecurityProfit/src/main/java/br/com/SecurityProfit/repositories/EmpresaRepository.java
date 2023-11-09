package br.com.SecurityProfit.repositories;

import br.com.SecurityProfit.models.escolta.Empresa;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface EmpresaRepository extends JpaRepository<Empresa, Long> {
}
