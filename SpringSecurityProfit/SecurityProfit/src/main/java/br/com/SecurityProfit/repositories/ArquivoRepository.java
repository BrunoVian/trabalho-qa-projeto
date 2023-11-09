package br.com.SecurityProfit.repositories;

import br.com.SecurityProfit.models.escolta.Arquivo;
import br.com.SecurityProfit.models.escolta.ArquivoEditDTO;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ArquivoRepository extends JpaRepository<Arquivo, Long> {

    @Query("SELECT a FROM Arquivo a WHERE a.escolta.id = :escoltaId")
    List<Arquivo> findArquivoByEscoltaId(@Param("escoltaId") Long escoltaId);

    @Query("UPDATE Arquivo a SET a.descricao = :novaDescricao WHERE a.id = :arquivoId")
    Arquivo editArquivo(@Param("arquivoId") Long arquivoId, @Param("novaDescricao") String novaDescricao);

}
