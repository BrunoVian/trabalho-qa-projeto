package br.com.SecurityProfit.models.checklist;

import javax.persistence.EnumType;
import javax.persistence.Enumerated;

public class PerguntaDTO {

    private Long id;

    private String descricao;

    @Enumerated(EnumType.STRING)
    private TipoRespostaEnum tipoRespostaEnum;

    private boolean status;

    public PerguntaDTO() {
    }

    public PerguntaDTO(Long id, String descricao, TipoRespostaEnum tipoRespostaEnum, boolean status) {
        this.id = id;
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
}
