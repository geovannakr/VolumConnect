package org.example.controller;

import org.example.dao.EventoDAO;
import org.example.model.Evento;
import org.example.model.Ong;

import java.util.List;

public class EventoController {
    private EventoDAO eventoDAO;

    public EventoController() {
        this.eventoDAO = new EventoDAO();
    }

    public boolean criarEvento(String nome, String cidade, String publico, List<String> capacidades, Ong ong) {
        Evento evento = new Evento(nome, cidade, publico, capacidades, ong);
        boolean sucesso = eventoDAO.cadastrarEvento(evento);
        if (sucesso) {
            System.out.println("Evento criado com sucesso!");
        } else {
            System.out.println("Falha ao criar evento.");
        }
        return sucesso;
    }

    public List<Evento> listarEventos() {
        return eventoDAO.listarEventos();
    }
}
