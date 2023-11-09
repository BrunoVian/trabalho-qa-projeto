package br.com.securityprofit.api.models.escolta;

import java.time.LocalDateTime;
import java.util.List;

public class EscoltaAbrirDTO {

    private Long pessoaOrigemId;
    private Long pessoaDestinoId;
    private List<Long> agentesIds;
    private List<Long> veiculosIds;
    private Long checklistId;
    private LocalDateTime dataHoraViagem;

    public EscoltaAbrirDTO(){

    }

    public EscoltaAbrirDTO(Long pessoaOrigemId, Long pessoaDestinoId, List<Long> agentesIds, List<Long> veiculosIds, Long checklistId, LocalDateTime dataHoraViagem) {
        this.pessoaOrigemId = pessoaOrigemId;
        this.pessoaDestinoId = pessoaDestinoId;
        this.agentesIds = agentesIds;
        this.veiculosIds = veiculosIds;
        this.checklistId = checklistId;
        this.dataHoraViagem = dataHoraViagem;
    }

    public Long getPessoaOrigemId() {
        return pessoaOrigemId;
    }

    public void setPessoaOrigemId(Long pessoaOrigemId) {
        this.pessoaOrigemId = pessoaOrigemId;
    }

    public Long getPessoaDestinoId() {
        return pessoaDestinoId;
    }

    public void setPessoaDestinoId(Long pessoaDestinoId) {
        this.pessoaDestinoId = pessoaDestinoId;
    }

    public List<Long> getAgentesIds() {
        return agentesIds;
    }

    public void setAgentesIds(List<Long> agentesIds) {
        this.agentesIds = agentesIds;
    }

    public List<Long> getVeiculosIds() {
        return veiculosIds;
    }

    public void setVeiculosIds(List<Long> veiculosIds) {
        this.veiculosIds = veiculosIds;
    }

    public Long getChecklistId() {
        return checklistId;
    }

    public void setChecklistId(Long checklistId) {
        this.checklistId = checklistId;
    }

    public LocalDateTime getDataHoraViagem() {
        return dataHoraViagem;
    }

    public void setDataHoraViagem(LocalDateTime dataHoraViagem) {
        this.dataHoraViagem = dataHoraViagem;
    }
}
