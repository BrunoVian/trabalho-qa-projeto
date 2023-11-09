package br.com.securityprofit.api.models.usuario;

public class AuthenticationDTO {
    private String login;
    private String senha;

    public AuthenticationDTO(){

    }

    public AuthenticationDTO(String login, String senha) {
        this.login = login;
        this.senha = senha;
    }

    public String getLogin() {
        return login;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    public String getSenha() {
        return senha;
    }

    public void setSenha(String senha) {
        this.senha = senha;
    }

}
