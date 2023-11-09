package br.com.SecurityProfit.repositories;

import br.com.SecurityProfit.models.pessoa.Pessoa;
import br.com.SecurityProfit.models.user.*;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UsuarioRepository extends JpaRepository<Usuario, String> {

    Optional<Usuario> findUsuarioById(@Param("id") Long id);

    UserDetails findByLogin(String login);

    @Query("SELECT u.login FROM Usuario u WHERE u.login = :login")
    String findEmailByLogin(@Param("login") String login);

    @Query("SELECT u.pessoa.nomeRazao FROM Usuario u WHERE u.login = :login")
    String findNomePessoaByLogin(@Param("login") String nomePessoa);

    @Modifying
    @Query("DELETE FROM Usuario u WHERE u.id = :id")
    void deleteUsuarioById(@Param("id") Long id);

    @Modifying
    @Query("UPDATE Usuario u SET u.login = :login, u.pessoa = :pessoa, u.role = :role, u.status = :status WHERE u.id = :id")
    void updateUsuario(@Param("id") Long id, @Param("login") String login, @Param("pessoa") Pessoa pessoa, @Param("role") UsuarioRole role, @Param("status") Boolean status);
}
