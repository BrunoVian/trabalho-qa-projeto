package br.com.securityprofit.api.models.checklist;

import java.util.List;

public class Checklist {

        private Long id;
        private String nome;
        private boolean status;
        private List<Pergunta> perguntas;

        public Checklist() {
        }

        public Checklist(Long id, String nome, Boolean status, List<Pergunta> perguntas) {
            this.nome = nome;
            this.status = status;
            this.perguntas = perguntas;
        }

        public Long getId() {
            return id;
        }

        public void setId(Long id) {
            this.id = id;
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

        public List<Pergunta> getPerguntas() {
            return perguntas;
        }

        public void setPerguntas(List<Pergunta> perguntas) {
            this.perguntas = perguntas;
        }

}
