package org.example.model;

public class Ong extends Usuario {
    private int id; // 🔹 identificador da ONG (ligado à tabela ong)

    public Ong(String nome, String email, String senha) {
        super(nome, email, senha);
    }


    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }
}
