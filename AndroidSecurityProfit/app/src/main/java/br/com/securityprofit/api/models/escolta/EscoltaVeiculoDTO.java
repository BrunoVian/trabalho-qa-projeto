package br.com.securityprofit.api.models.escolta;

import br.com.securityprofit.adapter.EscoltaCardAdapter;

public class EscoltaVeiculoDTO {

    private Long escoltaId;
    private Long veiculoId;

    public EscoltaVeiculoDTO(){

    }

    public EscoltaVeiculoDTO(Long escoltaId, Long veiculoId) {
        this.escoltaId = escoltaId;
        this.veiculoId = veiculoId;
    }

    public Long getEscoltaId() {
        return escoltaId;
    }

    public void setEscoltaId(Long escoltaId) {
        this.escoltaId = escoltaId;
    }

    public Long getVeiculoId() {
        return veiculoId;
    }

    public void setVeiculoId(Long veiculoId) {
        this.veiculoId = veiculoId;
    }
}
