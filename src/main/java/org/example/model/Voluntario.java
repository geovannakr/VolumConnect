package org.example.model;

import java.util.ArrayList;
import java.util.List;

    public class Voluntario extends Usuario {
        private List<String> habilidades = new ArrayList<>();

        public Voluntario(String nome, String email, String senha) {
            super(nome,email,senha);
        }

        public List<String> getHabilidades() { return habilidades; }
        public void adicionarHabilidade(String h) { habilidades.add(h); }
    }