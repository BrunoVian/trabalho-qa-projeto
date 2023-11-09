package br.com.securityprofit.api.models.pessoa;

public class PessoaDTO {

    private Long id;
    private FisicaJuridicaEnum fisicaJuridica;
    private String nomeRazao;
    private String apelidoFantasia;
    private String cpfCnpj;
    private String email;
    private String telefone;
    private String celular;
    private TipoPessoaEnum tipo;
    private Boolean status;
    private EnderecoDTO endereco;
    private String observacao;

    public PessoaDTO(){

    }

    public PessoaDTO(Long id, FisicaJuridicaEnum fisicaJuridica, String nomeRazao, String apelidoFantasia, String cpfCnpj, String email, String telefone, String celular, TipoPessoaEnum tipo, Boolean status, EnderecoDTO endereco, String observacao) {
        this.id = id;
        this.fisicaJuridica = fisicaJuridica;
        this.nomeRazao = nomeRazao;
        this.apelidoFantasia = apelidoFantasia;
        this.cpfCnpj = cpfCnpj;
        this.email = email;
        this.telefone = telefone;
        this.celular = celular;
        this.tipo = tipo;
        this.status = status;
        this.endereco = endereco;
        this.observacao = observacao;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public FisicaJuridicaEnum getFisicaJuridica() {
        return fisicaJuridica;
    }

    public void setFisicaJuridica(FisicaJuridicaEnum fisicaJuridica) {
        this.fisicaJuridica = fisicaJuridica;
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

    public EnderecoDTO getEndereco() {
        return endereco;
    }

    public void setEndereco(EnderecoDTO endereco) {
        this.endereco = endereco;
    }

    public String getObservacao() {
        return observacao;
    }

    public void setObservacao(String observacao) {
        this.observacao = observacao;
    }
}
