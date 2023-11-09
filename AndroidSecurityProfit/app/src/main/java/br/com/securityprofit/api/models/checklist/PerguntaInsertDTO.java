package br.com.securityprofit.api.models.checklist;

public class PerguntaInsertDTO {

    private Long checklistId;

    private String descricao;

    private TipoRespostaEnum tipoRespostaEnum;

    public PerguntaInsertDTO() {
    }

    public PerguntaInsertDTO(Long checklistId, String descricao, TipoRespostaEnum tipoRespostaEnum) {
        this.checklistId = checklistId;
        this.descricao = descricao;
        this.tipoRespostaEnum = tipoRespostaEnum;
    }

    public Long getChecklistId() {
        return checklistId;
    }

    public void setChecklistId(Long checklistId) {
        this.checklistId = checklistId;
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

}
