package org.example.model;

import java.util.ArrayList;
import java.util.List;
import org.example.observer.Observer;

public class Voluntario extends Usuario implements Observer {
    private int id;
    private List<String> habilidades = new ArrayList<>();

    public Voluntario(String nome, String email, String senha) {
        super(nome, email, senha);
    }

    public Voluntario(int id, String nome, String email, String senha) {
        super(nome, email, senha);
        this.id = id;
    }

    public int getId() { return id; }
    public void setId(int id) { this.id = id; }
    public List<String> getHabilidades() { return habilidades; }
    public void adicionarHabilidade(String h) { habilidades.add(h); }

    @Override
    public void update(String mensagem) {
        System.out.println("🔔 [Notificação para " + getNome() + "]: " + mensagem);
    }
}
