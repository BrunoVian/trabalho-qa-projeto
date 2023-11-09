package br.com.securityprofit.api.models.checklist;

public class ChecklistEditDTO {

    private Long checklistId;
    private String nome;
    private boolean status;

    public ChecklistEditDTO() {
    }

    public ChecklistEditDTO(Long checklistId, String nome, boolean status) {
        this.checklistId = checklistId;
        this.nome = nome;
        this.status = status;
    }

    public Long getChecklistId() {
        return checklistId;
    }

    public void setChecklistId(Long checklistId) {
        this.checklistId = checklistId;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public boolean isStatus() {
        return status;
    }

    public void setStatus(boolean status) {
        this.status = status;
    }
}