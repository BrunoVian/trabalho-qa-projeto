package br.com.SecurityProfit.controllers;

import br.com.SecurityProfit.infra.security.TokenService;
import br.com.SecurityProfit.models.pessoa.Pessoa;
import br.com.SecurityProfit.models.user.*;

import javax.validation.Valid;

import br.com.SecurityProfit.repositories.UsuarioRepository;
import br.com.SecurityProfit.services.AuthorizationService;
import br.com.SecurityProfit.services.PessoaService;
import br.com.SecurityProfit.services.UsuarioService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.extern.java.Log;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;


@RestController
@RequestMapping("/auth")
@Api(tags = "Autenticação", description = "Endpoints para operações relacionadas a autentição de usuários")
public class AuthenticationController {

    @Autowired
    private AuthenticationManager authenticationManager;


    @Autowired
    private TokenService tokenService;

    @Autowired
    private PessoaService pessoaService;

    @Autowired
    private UsuarioService usuarioService;

    @Autowired
    private AuthorizationService authorizationService;

    @ApiOperation(value = "Realiza Login na Aplicação")
    @PostMapping("/login")
    public ResponseEntity login(@RequestBody @Valid AuthenticationDTO data) {
        var usernamePassword = new UsernamePasswordAuthenticationToken(data.getLogin(), data.getSenha());

        var auth = this.authenticationManager.authenticate(usernamePassword);

        var token = tokenService.generateToken((Usuario) auth.getPrincipal());

        var usuario = (Usuario) auth.getPrincipal();

        if (usuarioService.usuarioAtivo(usuario)) {
            return ResponseEntity.ok(
                    new LoginResponseDTO(
                            usuario.getId(),
                            token,
                            authorizationService.buscaDadosUsuarioLogado(data.getLogin()).getLogin(),
                            authorizationService.buscaDadosUsuarioLogado(data.getLogin()).getNomePessoaLogin(),
                            usuario.getRole()
                    )
            );
        } else {
            throw new ResponseStatusException(HttpStatus.FORBIDDEN, "O usuário não existe ou não está ativo.");
        }


    }

    @ApiOperation(value = "Registra Login Para a Aplicação")
    @PostMapping("/register")
    public ResponseEntity register(@RequestBody @Valid RegisterDTO data) throws Exception {

        return ResponseEntity.status(HttpStatus.CREATED).body(usuarioService.registrar(data));

    }

}
