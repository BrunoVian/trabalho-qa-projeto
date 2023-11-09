package br.com.SecurityProfit.models.user;

import br.com.SecurityProfit.models.pessoa.Pessoa;

public class UsuarioDTO {

    private Long id;

    private String login;

    private Pessoa pessoa;

    private UsuarioRole role;

    private Boolean status;

    public UsuarioDTO() {
    }

    public UsuarioDTO(Long id, String login, Pessoa pessoa, UsuarioRole role, Boolean status) {
        this.id = id;
        this.login = login;
        this.pessoa = pessoa;
        this.role = role;
        this.status = status;
    }



    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getLogin() {
        return login;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    public Pessoa getPessoa() {
        return pessoa;
    }

    public void setPessoa(Pessoa pessoa) {
        this.pessoa = pessoa;
    }

    public UsuarioRole getRole() {
        return role;
    }

    public void setRole(UsuarioRole role) {
        this.role = role;
    }

    public Boolean getStatus() {
        return status;
    }

    public void setStatus(Boolean status) {
        this.status = status;
    }
}
