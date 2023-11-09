package br.com.securityprofit.api.models.escolta;

public class EscoltaAgenteDTO {

    private Long escoltaId;
    private Long pessoaAgenteId;

    public EscoltaAgenteDTO(){

    }

    public EscoltaAgenteDTO(Long escoltaId, Long pessoaAgenteId) {
        this.escoltaId = escoltaId;
        this.pessoaAgenteId = pessoaAgenteId;
    }

    public Long getEscoltaId() {
        return escoltaId;
    }

    public void setEscoltaId(Long escoltaId) {
        this.escoltaId = escoltaId;
    }

    public Long getPessoaAgenteId() {
        return pessoaAgenteId;
    }

    public void setPessoaAgenteId(Long pessoaAgenteId) {
        this.pessoaAgenteId = pessoaAgenteId;
    }
}
