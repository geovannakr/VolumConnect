package org.example.model;
import java.util.List;

    public class Evento {
        private String nome, cidade, publico;
        private List<String> capacidades;
        private Ong ong;

        public Evento(String nome, String cidade, String publico, List<String> capacidades, Ong ong) {
            this.nome = nome;
            this.cidade = cidade;
            this.publico = publico;
            this.capacidades = capacidades;
            this.ong = ong;
        }

        public String getNome() { return nome; }
        public String getCidade() { return cidade; }
        public String getPublico() { return publico; }
        public List<String> getCapacidades() { return capacidades; }
        public Ong getOng() { return ong; }
    }
