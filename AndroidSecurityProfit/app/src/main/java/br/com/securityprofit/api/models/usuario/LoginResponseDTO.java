package br.com.securityprofit.api.models.usuario;

public class LoginResponseDTO {

    private Long id;
    private String token;
    private String login;
    private String nomePessoaLogin;
    private UsuarioRole usuarioRole;

    public LoginResponseDTO() {
    }

    public LoginResponseDTO(String token) {
        this.token = token;
    }

    public LoginResponseDTO(Long id, String token, String login, String nomePessoaLogin, UsuarioRole usuarioRole) {
        this.id = id;
        this.token = token;
        this.login = login;
        this.nomePessoaLogin = nomePessoaLogin;
        this.usuarioRole = usuarioRole;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getToken() {
        return this.token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public String getLogin() {
        return this.login;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    public String getNomePessoaLogin() {
        return this.nomePessoaLogin;
    }

    public UsuarioRole getUsuarioRole() {
        return usuarioRole;
    }

    public void setUsuarioRole(UsuarioRole usuarioRole) {
        this.usuarioRole = usuarioRole;
    }

    public void setNomePessoaLogin(String nomePessoaLogin) {
        this.nomePessoaLogin = nomePessoaLogin;
    }

}
