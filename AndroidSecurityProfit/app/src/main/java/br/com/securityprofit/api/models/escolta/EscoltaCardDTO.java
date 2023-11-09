package br.com.securityprofit.api.models.escolta;

import br.com.securityprofit.api.enums.StatusViagemEnum;
import lombok.Getter;
import lombok.Setter;

public class EscoltaCardDTO {

    private Long escoltaId;
    private String nomeRazao;
    private String cpfCnpj;
    private String endereco;
    private String cidadeEstado;
    private StatusViagemEnum statusViagem;
    private String dataHoraViagem;

    public EscoltaCardDTO(){

    }

    public EscoltaCardDTO(Long escoltaId, String nomeRazao, String cpfCnpj, String endereco, String cidadeEstado, StatusViagemEnum statusViagem, String dataHoraViagem) {
        this.escoltaId = escoltaId;
        this.nomeRazao = nomeRazao;
        this.cpfCnpj = cpfCnpj;
        this.endereco = endereco;
        this.cidadeEstado = cidadeEstado;
        this.statusViagem = statusViagem;
        this.dataHoraViagem = dataHoraViagem;
    }

    public Long getEscoltaId() {
        return escoltaId;
    }

    public void setEscoltaId(Long escoltaId) {
        this.escoltaId = escoltaId;
    }

    public String getNomeRazao() {
        return nomeRazao;
    }

    public void setNomeRazao(String nomeRazao) {
        this.nomeRazao = nomeRazao;
    }

    public String getCpfCnpj() {
        return cpfCnpj;
    }

    public void setCpfCnpj(String cpfCnpj) {
        this.cpfCnpj = cpfCnpj;
    }

    public String getEndereco() {
        return endereco;
    }

    public void setEndereco(String endereco) {
        this.endereco = endereco;
    }

    public String getCidadeEstado() {
        return cidadeEstado;
    }

    public void setCidadeEstado(String cidadeEstado) {
        this.cidadeEstado = cidadeEstado;
    }

    public StatusViagemEnum getStatusViagem() {
        return statusViagem;
    }

    public void setStatusViagem(StatusViagemEnum statusViagem) {
        this.statusViagem = statusViagem;
    }

    public String getDataHoraViagem() {
        return dataHoraViagem;
    }

    public void setDataHoraViagem(String dataHoraViagem) {
        this.dataHoraViagem = dataHoraViagem;
    }
}
