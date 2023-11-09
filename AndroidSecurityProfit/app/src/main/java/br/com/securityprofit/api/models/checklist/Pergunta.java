package br.com.securityprofit.api.models.checklist;

public class Pergunta {
    private Long id;
    private String descricao;
    private TipoRespostaEnum tipoRespostaEnum;
    private boolean status;

    public Pergunta() {
    }

    public Pergunta(String descricao, TipoRespostaEnum tipoRespostaEnum, boolean status) {
        this.descricao = descricao;
        this.tipoRespostaEnum = tipoRespostaEnum;
        this.status = status;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getDescricao() {
        return descricao;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }

    public TipoRespostaEnum getTipoRespostaEnum() {
        return tipoRespostaEnum;
    }

    public void setTipoRespostaEnum(TipoRespostaEnum tipoRespostaEnum) {
        this.tipoRespostaEnum = tipoRespostaEnum;
    }

    public boolean isStatus() {
        return status;
    }

    public void setStatus(boolean status) {
        this.status = status;
    }

    @Override
    public String toString() {
        return "Pergunta{" +
                "id=" + id +
                ", descricao='" + descricao + '\'' +
                ", tipoRespostaEnum=" + tipoRespostaEnum +
                ", status=" + status +
                '}';
    }
}

