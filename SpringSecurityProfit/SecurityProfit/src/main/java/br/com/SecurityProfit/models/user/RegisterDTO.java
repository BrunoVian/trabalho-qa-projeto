package br.com.SecurityProfit.models.user;

public class RegisterDTO {
    private String login;
    private String senha;
    private String repitaSenha;
    private UsuarioRole role;
    private Long pessoaId;

    private Boolean status;

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
