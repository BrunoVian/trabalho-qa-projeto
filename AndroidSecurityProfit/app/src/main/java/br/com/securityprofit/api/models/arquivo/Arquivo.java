package br.com.securityprofit.api.models.arquivo;

public class Arquivo {

    private String conteudo;

    private String descricao;

    private Long escoltaId;

    public Arquivo(){

    }

    public Arquivo(String conteudo, String descricao, Long escoltaId) {
        this.conteudo = conteudo;
        this.descricao = descricao;
        this.escoltaId = escoltaId;
    }

    public String getConteudo() {
        return conteudo;
    }

    public void setConteudo(String conteudo) {
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
