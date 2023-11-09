package br.com.securityprofit.api.models.arquivo;

public class ArquivoDTO {
    private byte[] conteudo;

    private String descricao;

    private Long escoltaId;

    public ArquivoDTO(){

    }

    public ArquivoDTO(byte[] conteudo, String descricao, Long escoltaId) {
        this.conteudo = conteudo;
        this.descricao = descricao;
        this.escoltaId = escoltaId;
    }

    public byte[] getConteudo() {
        return conteudo;
    }

    public void setConteudo(byte[] conteudo) {
        this.conteudo = conteudo;
    }

    public String getDescricao() {
        return descricao;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }

    public Long getEscoltaId() {
        return escoltaId;
    }

    public void setEscoltaId(Long escoltaId) {
        this.escoltaId = escoltaId;
    }
}
