package br.com.SecurityProfit.repositories;

import br.com.SecurityProfit.models.escolta.Veiculo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface VeiculoRepository extends JpaRepository<Veiculo, Long> {

    /*@Query
    List<Veiculo> findByModeloContainingAllIgnoringCase(String modelo);*/

    List<Veiculo> findAllByOrderByPlacaAscModeloAsc();

    @Query("SELECT v FROM Veiculo v WHERE v.modelo LIKE %:termoBusca% OR v.placa LIKE %:termoBusca%")
    List<Veiculo> findByModeloOrPlacaContainingAllIgnoringCase(@Param("termoBusca") String termoBusca);

}
