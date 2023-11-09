package br.com.securityprofit.api.models.veiculo;

public class Veiculo {
        private Long id;
        private  String placa;
        private String ano;
        private String marca;
        private String modelo;
        private String renavam;
        private String cor;
        private Boolean status;

        public Veiculo() {
        }

    public Veiculo(Long id, String placa, String ano, String marca, String modelo, String renavam, String cor, Boolean status) {
        this.id = id;
        this.placa = placa;
        this.ano = ano;
        this.marca = marca;
        this.modelo = modelo;
        this.renavam = renavam;
        this.cor = cor;
        this.status = status;
    }

    public Long getId() {
            return this.id;
        }

        public void setId(Long id) {
            this.id = id;
        }

        public String getPlaca() {
            return this.placa;
        }

        public void setPlaca(String placa) {
            this.placa = placa;
        }

        public String getAno() {
            return this.ano;
        }

        public void setAno(String ano) {
            this.ano = ano;
        }

        public String getMarca() {
            return this.marca;
        }

        public void setMarca(String marca) {
            this.marca = marca;
        }

        public String getModelo() {
            return this.modelo;
        }

        public void setModelo(String modelo) {
            this.modelo = modelo;
        }

        public String getRenavam() {
            return this.renavam;
        }

        public void setRenavam(String renavam) {
            this.renavam = renavam;
        }

        public String getCor() {
            return this.cor;
        }

        public void setCor(String cor) {
            this.cor = cor;
        }

        public Boolean getStatus() {
            return this.status;
        }

        public void setStatus(Boolean status) {
            this.status = status;
        }

    }
