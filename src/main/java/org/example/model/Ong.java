package org.example.model;

public class Ong extends Usuario {
    private int id; 

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
