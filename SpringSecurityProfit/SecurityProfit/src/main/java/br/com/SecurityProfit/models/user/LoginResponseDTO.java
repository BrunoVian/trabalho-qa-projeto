package br.com.SecurityProfit.models.user;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class LoginResponseDTO {

    private Long id;

    private String token;

    private String login;

    private String nomePessoaLogin;

    private UsuarioRole role;

}
