package br.com.SecurityProfit.services;

import br.com.SecurityProfit.models.pessoa.Pessoa;
import br.com.SecurityProfit.models.user.*;
import br.com.SecurityProfit.repositories.UsuarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class UsuarioService {

    @Autowired
    UsuarioRepository usuarioRepository;

    @Autowired
    PessoaService pessoaService;

    public List<UsuarioDTO> findAll() {
        List<Usuario> usuarios = usuarioRepository.findAll();
        return usuarios.stream()
                .map(usuario -> new UsuarioDTO(usuario.getId(), usuario.getLogin(), usuario.getPessoa(), usuario.getRole(), usuario.getStatus()))
                .collect(Collectors.toList());
    }

    public UsuarioDTO findById(Long id) throws Exception {
        Optional<Usuario> retorno = usuarioRepository.findUsuarioById(id);
        if (retorno.isPresent()) {
            return new UsuarioDTO(retorno.get().getId(), retorno.get().getLogin(), retorno.get().getPessoa(), retorno.get().getRole(), retorno.get().getStatus());
        } else {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Usuário com id " + id + " não identificado");
        }
    }

    public boolean usuarioAtivo(Usuario usuario){
        Optional<Usuario> retorno = usuarioRepository.findUsuarioById(usuario.getId());
        if(retorno.isPresent()) {
            if (retorno.get().getStatus().equals(false)) {
                return false;
            } else {
                return true;
            }
        } else {
            return false;
        }
    }

    public UsuarioDTO alterarSenha(UsuarioSenhaDTO usuarioSenhaDTO) throws Exception {
        if (senhaValida(usuarioSenhaDTO.getNovaSenha(), usuarioSenhaDTO.getRepitaSenha())) {
            Optional<Usuario> usuario = usuarioRepository.findUsuarioById(usuarioSenhaDTO.getUsuarioId());
            String encryptedPassword = new BCryptPasswordEncoder().encode(usuarioSenhaDTO.getNovaSenha());
            usuario.get().setSenha(encryptedPassword);
            usuarioRepository.saveAndFlush(usuario.get());
            return new UsuarioDTO(usuario.get().getId(), usuario.get().getLogin(), usuario.get().getPessoa(), usuario.get().getRole(), usuario.get().getStatus());
        } else {
            throw new Exception("As senhas não conferem.");
        }
    }

    public boolean senhaValida(String novaSenha, String repitaSenha) {
        return Objects.equals(novaSenha, repitaSenha) || !novaSenha.isEmpty() || !repitaSenha.isEmpty();
    }

    @Transactional
    public UsuarioDTO edit(UsuarioEditDTO usuarioEditDTO) throws Exception {

        Optional<Usuario> usuario = usuarioRepository.findUsuarioById(usuarioEditDTO.getId());

        if (usuario.isPresent()) {
            pessoaService.findById(usuarioEditDTO.getPessoaId());
            Pessoa pessoaUsuario = pessoaService.findById(usuarioEditDTO.getPessoaId());
            usuarioRepository.updateUsuario(usuarioEditDTO.getId(), usuarioEditDTO.getLogin(), pessoaUsuario, usuarioEditDTO.getRole(), usuarioEditDTO.getStatus());
            return new UsuarioDTO(usuarioEditDTO.getId(), usuarioEditDTO.getLogin(), pessoaUsuario, usuarioEditDTO.getRole(), usuarioEditDTO.getStatus());
        } else {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "O usuário com id " + usuarioEditDTO.getId() + " não foi encontrado.");
        }
    }

    public Usuario findUsuarioById(Long id) throws Exception {
        Optional<Usuario> usuario = usuarioRepository.findUsuarioById(id);
        if (usuario.isPresent()) {
            return usuario.get();
        } else {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "O usuário com id " + id + " não foi encontrado.");
        }
    }

    @Transactional
    public void remove(Long id) throws Exception{
        UsuarioDTO usuario = findById(id);
        if(id == 1L){
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, "Não é possível excluir o usuário padrão.");
        } else {
            usuarioRepository.deleteUsuarioById(usuario.getId());
        }

    }

    public String registrar(RegisterDTO data) throws Exception{

        if (this.usuarioRepository.findByLogin(data.getLogin()) != null){
            throw new Exception("Usuario já cadastrado.");
        }

        if(senhaValida(data.getSenha(), data.getRepitaSenha())){
            String encryptedPassword = new BCryptPasswordEncoder().encode(data.getSenha());
            Pessoa pessoa = pessoaService.findById(data.getPessoaId());

            Usuario newUser = new Usuario(data.getLogin(), encryptedPassword, data.getRole(), data.getStatus(), pessoa);
            this.usuarioRepository.save(newUser);

            return "Usuário cadastrado com sucesso.";

        } else {
            throw new Exception("Senhas não conferem.");
        }
    }

}