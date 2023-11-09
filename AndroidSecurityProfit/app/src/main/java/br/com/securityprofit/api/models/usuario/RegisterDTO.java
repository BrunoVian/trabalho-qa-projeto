package br.com.securityprofit.api.models.usuario;

public class RegisterDTO {
    private Long id;
    private String login;
    private String senha;
    private String repitaSenha;
    private UsuarioRole role;
    private Long pessoaId;
    private Boolean status;

    public RegisterDTO(Long id,String login, Long pessoaId, String repitaSenha, UsuarioRole role, String senha, Boolean status) {
        this.id = id;
        this.login = login;
        this.pessoaId = pessoaId;
        this.repitaSenha = repitaSenha;
        this.role = role;
        this.senha = senha;
        this.status = status;
    }

    public Long getId() {return id;}

    public void setId(Long id) { this.id = id;}

    public String getRepitaSenha() {
        return repitaSenha;
    }

    public void setRepitaSenha(String repitaSenha) {
        this.repitaSenha = repitaSenha;
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

    public UsuarioRole getRole() {
        return role;
    }

    public void setRole(UsuarioRole role) {
        this.role = role;
    }

    public Long getPessoaId() {
        return pessoaId;
    }

    public void setPessoaId(Long pessoaId) {
        this.pessoaId = pessoaId;
    }

    public Boolean getStatus() {
        return status;
    }

    public void setStatus(Boolean status) {
        this.status = status;
    }

}
