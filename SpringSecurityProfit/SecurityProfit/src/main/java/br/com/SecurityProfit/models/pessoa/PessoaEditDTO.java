package br.com.SecurityProfit.models.pessoa;

import java.util.List;

public class PessoaEditDTO {
    private Long id;
    private FisicaJuridicaEnum fisicaJurica;

    private String nomeRazao;
    private String apelidoFantasia;
    private String cpfCnpj;
    private String email;
    private String telefone;
    private String celular;
    private TipoPessoaEnum tipo;
    private Boolean status;
    private List<EnderecoDTO> enderecos;
    private String observacao;

    public PessoaEditDTO() {
    }

    public PessoaEditDTO(Long id, FisicaJuridicaEnum fisicaJurica, String nomeRazao, String apelidoFantasia, String cpfCnpj, String email, String telefone, String celular, TipoPessoaEnum tipo, Boolean status, List<EnderecoDTO> enderecos, String observacao) {
        this.id = id;
        this.fisicaJurica = fisicaJurica;
        this.nomeRazao = nomeRazao;
        this.apelidoFantasia = apelidoFantasia;
        this.cpfCnpj = cpfCnpj;
        this.email = email;
        this.telefone = telefone;
        this.celular = celular;
        this.tipo = tipo;
        this.status = status;
        this.enderecos = enderecos;
        this.observacao = observacao;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public FisicaJuridicaEnum getFisicaJurica() {
        return fisicaJurica;
    }

    public void setFisicaJurica(FisicaJuridicaEnum fisicaJurica) {
        this.fisicaJurica = fisicaJurica;
    }

    public String getNomeRazao() {
        return nomeRazao;
    }

    public void setNomeRazao(String nomeRazao) {
        this.nomeRazao = nomeRazao;
    }

    public String getApelidoFantasia() {
        return apelidoFantasia;
    }

    public void setApelidoFantasia(String apelidoFantasia) {
        this.apelidoFantasia = apelidoFantasia;
    }

    public String getCpfCnpj() {
        return cpfCnpj;
    }

    public void setCpfCnpj(String cpfCnpj) {
        this.cpfCnpj = cpfCnpj;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getTelefone() {
        return telefone;
    }

    public void setTelefone(String telefone) {
        this.telefone = telefone;
    }

    public String getCelular() {
        return celular;
    }

    public void setCelular(String celular) {
        this.celular = celular;
    }

    public TipoPessoaEnum getTipo() {
        return tipo;
    }

    public void setTipo(TipoPessoaEnum tipo) {
        this.tipo = tipo;
    }

    public Boolean getStatus() {
        return status;
    }

    public void setStatus(Boolean status) {
        this.status = status;
    }

    public List<EnderecoDTO> getEnderecos() {
        return enderecos;
    }

    public void setEnderecos(List<EnderecoDTO> enderecos) {
        this.enderecos = enderecos;
    }

    public String getObservacao() {
        return observacao;
    }

    public void setObservacao(String observacao) {
        this.observacao = observacao;
    }
}
