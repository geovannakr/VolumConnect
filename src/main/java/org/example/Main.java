package org.example;

import org.example.dao.EventoDAO;
import org.example.dao.UsuarioDAO;
import org.example.view.MenuPrincipal;

public class Main {
    public static void main(String[] args) {
        UsuarioDAO usuarioDAO = new UsuarioDAO();
        EventoDAO eventoDAO = new EventoDAO();

        new MenuPrincipal(usuarioDAO, eventoDAO).exibir();
    }
}
