package br.com.securityprofit.api.models.usuario;

public class UsuarioEditDTO {
    private Long id;
    private String login;
    private Long pessoaId;
    private UsuarioRole role;
    private Boolean status;

    public UsuarioEditDTO() {
    }

    public UsuarioEditDTO(Long id, String login, Long pessoaId, UsuarioRole role, Boolean status) {
        this.id = id;
        this.login = login;
        this.pessoaId = pessoaId;
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

    public Long getPessoaId() {
        return pessoaId;
    }

    public void setPessoaId(Long pessoaId) {
        this.pessoaId = pessoaId;
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

