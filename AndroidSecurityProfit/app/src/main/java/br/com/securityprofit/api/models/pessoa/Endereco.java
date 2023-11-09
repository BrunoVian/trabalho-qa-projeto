package br.com.securityprofit.api.models.pessoa;

public class Endereco {

    private Long id;
    private String descrição;
    private String cep;
    private String rua;
    private String bairro;
    private int numero;
    private String complemento;
    private Boolean status;
    private Long cidadeId;
    private Long pessoaId;
    private String estado;

    public Endereco(){

    }

    public Endereco(Long id, String descrição, String cep, String rua, String bairro,int numero, String complemento, Boolean status, Long cidadeId, Long pessoaId) {
        this.id = id;
        this.descrição = descrição;
        this.cep = cep;
        this.rua = rua;
        this.bairro = bairro;
        this.numero = numero;
        this.complemento = complemento;
        this.status = status;
        this.cidadeId = cidadeId;
        this.pessoaId = pessoaId;
        this.estado = estado;
    }

    public int getNumero() {
        return numero;
    }

    public void setNumero(int numero) {
        this.numero = numero;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getDescrição() {
        return descrição;
    }

    public void setDescrição(String descrição) {
        this.descrição = descrição;
    }

    public String getCep() {
        return cep;
    }

    public void setCep(String cep) {
        this.cep = cep;
    }

    public String getRua() {
        return rua;
    }

    public void setRua(String rua) {
        this.rua = rua;
    }

    public String getBairro() {
        return bairro;
    }

    public void setBairro(String bairro) {
        this.bairro = bairro;
    }

    public String getComplemento() {
        return complemento;
    }

    public void setComplemento(String complemento) {
        this.complemento = complemento;
    }

    public Boolean getStatus() {
        return status;
    }

    public void setStatus(Boolean status) {
        this.status = status;
    }

    public Long getCidadeId() {
        return cidadeId;
    }

    public void setCidadeId(Long cidadeId) {
        this.cidadeId = cidadeId;
    }

    public Long getPessoaId() {
        return pessoaId;
    }

    public void setPessoaId(Long pessoaId) {
        this.pessoaId = pessoaId;
    }

    public String getEstado() {
        return estado;
    }

    public void setEstado(String estado) {
        this.estado = estado;
    }

}
