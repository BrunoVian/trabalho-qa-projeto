package br.com.securityprofit.api.models.usuario;

public class UsuarioSenhaDTO {
    private Long usuarioId;
    private String novaSenha;
    private String repitaSenha;
    public UsuarioSenhaDTO() {
    }

    public UsuarioSenhaDTO(Long usuarioId, String novaSenha, String repitaSenha) {
        this.usuarioId = usuarioId;
        this.novaSenha = novaSenha;
        this.repitaSenha = repitaSenha;
    }

    public String getRepitaSenha() {
        return repitaSenha;
    }

    public void setRepitaSenha(String repitaSenha) {
        this.repitaSenha = repitaSenha;
    }

    public Long getUsuarioId() {
        return usuarioId;
    }

    public void setUsuarioId(Long usuarioId) {
        this.usuarioId = usuarioId;
    }

    public String getNovaSenha() {
        return novaSenha;
    }

    public void setNovaSenha(String novaSenha) {
        this.novaSenha = novaSenha;
    }

}
