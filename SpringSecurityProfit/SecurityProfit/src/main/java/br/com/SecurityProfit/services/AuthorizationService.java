package br.com.SecurityProfit.services;

import br.com.SecurityProfit.models.user.LoginResponseDTO;
import br.com.SecurityProfit.models.user.Usuario;
import br.com.SecurityProfit.repositories.UsuarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class AuthorizationService implements UserDetailsService {

    @Autowired
    UsuarioRepository repository;
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        UserDetails user = repository.findByLogin(username);

        if (user == null) {
            throw new UsernameNotFoundException("Usuário inexistente ou senha inválida");
        }
        return user;
    }

    public LoginResponseDTO buscaDadosUsuarioLogado(String login) {

        LoginResponseDTO loginResponseDTO = new LoginResponseDTO();
        loginResponseDTO.setLogin(repository.findEmailByLogin(login));
        loginResponseDTO.setNomePessoaLogin(repository.findNomePessoaByLogin(login));

        return loginResponseDTO;
    }


}
