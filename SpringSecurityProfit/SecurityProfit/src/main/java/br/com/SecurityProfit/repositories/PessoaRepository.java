package br.com.SecurityProfit.repositories;

import br.com.SecurityProfit.models.pessoa.Pessoa;
import br.com.SecurityProfit.models.pessoa.PessoaEscoltaDTO;
import br.com.SecurityProfit.models.pessoa.PessoaFuncionarioDTO;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface PessoaRepository extends JpaRepository<Pessoa, Long> {

    @Query("SELECT new br.com.SecurityProfit.models.pessoa.PessoaFuncionarioDTO(p.id, p.nomeRazao) FROM Pessoa p WHERE p.status = true and p.tipo = br.com.SecurityProfit.models.pessoa.TipoPessoaEnum.F or p.tipo = br.com.SecurityProfit.models.pessoa.TipoPessoaEnum.A")
    List<PessoaFuncionarioDTO> findAllPessoaFuncionarioDTO();

    @Query("SELECT new br.com.SecurityProfit.models.pessoa.PessoaEscoltaDTO(p.id, p.nomeRazao, p.cpfCnpj) " +
            "FROM Pessoa p WHERE p.status = true " +
            "and p.tipo = br.com.SecurityProfit.models.pessoa.TipoPessoaEnum.F " +
            "or p.tipo = br.com.SecurityProfit.models.pessoa.TipoPessoaEnum.A")
    List<PessoaEscoltaDTO> findAllPessoaEscoltaFuncionario();

    @Query("SELECT new br.com.SecurityProfit.models.pessoa.PessoaEscoltaDTO(p.id, p.nomeRazao, p.cpfCnpj) " +
            "FROM Pessoa p WHERE p.status = true " +
            "and p.tipo = br.com.SecurityProfit.models.pessoa.TipoPessoaEnum.C " +
            "or p.tipo = br.com.SecurityProfit.models.pessoa.TipoPessoaEnum.A")
    List<PessoaEscoltaDTO> findAllPessoaEscoltaCliente();

    @Query("SELECT new br.com.SecurityProfit.models.pessoa.PessoaEscoltaDTO(p.id, p.nomeRazao, p.cpfCnpj) " +
            "FROM Pessoa p WHERE p.status = true " +
            "and p.status = true "+
            "and (p.tipo = br.com.SecurityProfit.models.pessoa.TipoPessoaEnum.C " +
            "or p.tipo = br.com.SecurityProfit.models.pessoa.TipoPessoaEnum.A) " +
            "and LOWER(p.nomeRazao) like %:parametro%")
    List<PessoaEscoltaDTO> findAllPessoaEscoltaCliente(@Param("parametro") String parametro);

}
