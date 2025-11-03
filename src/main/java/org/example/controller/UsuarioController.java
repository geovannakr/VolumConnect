package org.example.controller;

import org.example.dao.UsuarioDAO;
import org.example.model.Usuario;
import org.example.model.Voluntario;
import org.example.model.Ong;

public class UsuarioController {
    private UsuarioDAO usuarioDAO;

    public UsuarioController() {
        this.usuarioDAO = new UsuarioDAO();
    }

    public boolean cadastrarVoluntario(String nome, String email, String senha) {
        Voluntario v = new Voluntario(nome, email, senha);
        boolean sucesso = usuarioDAO.cadastrarVoluntario(v);
        if (sucesso)
            System.out.println("Voluntário cadastrado com sucesso!");
        else
            System.out.println("Erro ao cadastrar voluntário.");
        return sucesso;
    }

    public boolean cadastrarOng(String nome, String email, String senha) {
        Ong o = new Ong(nome, email, senha);
        boolean sucesso = usuarioDAO.cadastrarOng(o);
        if (sucesso)
            System.out.println("ONG cadastrada com sucesso!");
        else
            System.out.println("Erro ao cadastrar ONG.");
        return sucesso;
    }

    public Usuario buscarPorEmail(String email) {
        return usuarioDAO.buscarPorEmail(email);
    }
}
