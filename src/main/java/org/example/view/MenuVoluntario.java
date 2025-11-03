package org.example.view;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import org.example.dao.EventoDAO;
import org.example.dao.UsuarioDAO;
import org.example.model.Evento;
import org.example.model.Voluntario;

public class MenuVoluntario {
    private final Scanner sc = new Scanner(System.in);
    private final Voluntario voluntario;
    private final EventoDAO eventDAO;
    private final UsuarioDAO usuarioDAO;

    public MenuVoluntario(Voluntario v, EventoDAO eventDAO, UsuarioDAO usuarioDAO) {
        this.voluntario = v;
        this.eventDAO = eventDAO;
        this.usuarioDAO = usuarioDAO;
    }

    public void exibir() {
        while (true) {
            System.out.println("\n--- Menu Voluntário ---");
            System.out.println("1. Listar Todos os Eventos");
            System.out.println("2. Filtrar Eventos por Cidade");
            System.out.println("3. Inscrever-se em Evento");
            System.out.println("0. Logout");
            System.out.print("Escolha: ");
            String op = sc.nextLine();
            switch (op) {
                case "1" -> listarEventos();
                case "2" -> filtrarEventosCidade();
                case "3" -> inscreverEvento();
                case "0" -> { System.out.println("Logout realizado.\n"); return; }
                default -> System.out.println("  Opção inválida!\n");
            }
        }
    }

    private void listarEventos() {
        List<Evento> eventos = eventDAO.listarEventos();

        if (eventos.isEmpty()) {
            System.out.println("\nNenhum evento cadastrado no momento.\n");
            return;
        }

        System.out.println("\n=======================================");
        System.out.println("        LISTA DE EVENTOS        ");
        System.out.println("=======================================\n");

        for (int i = 0; i < eventos.size(); i++) {
            Evento e = eventos.get(i);
            System.out.printf("%d. %s | Cidade: %s | ONG: %s%n",
                    i + 1, e.getNome(), e.getCidade(), e.getOng().getNome());
        }
    }

    private void filtrarEventosCidade() {
        List<Evento> todosEventos = eventDAO.listarEventos();

        if (todosEventos.isEmpty()) {
            System.out.println(" Nenhum evento cadastrado ainda.\n");
            return;
        }

        System.out.println("\n--- Cidades Disponíveis ---");
        List<String> cidades = new ArrayList<>();
        for (Evento e : todosEventos) {
            if (!cidades.contains(e.getCidade())) cidades.add(e.getCidade());
        }

        for (int i = 0; i < cidades.size(); i++) {
            System.out.println((i + 1) + ". " + cidades.get(i));
        }

        System.out.print("\nEscolha o número da cidade: ");
        int escolha = Integer.parseInt(sc.nextLine());
        if (escolha < 1 || escolha > cidades.size()) {
            System.out.println(" Cidade inválida!\n");
            return;
        }

        String cidadeEscolhida = cidades.get(escolha - 1);
        List<Evento> eventosFiltrados = eventDAO.listarEventosPorCidade(cidadeEscolhida);

        System.out.println("\n--- Eventos na cidade: " + cidadeEscolhida + " ---");
        if (eventosFiltrados.isEmpty()) {
            System.out.println(" Nenhum evento encontrado nessa cidade.\n");
            return;
        }

        for (int i = 0; i < eventosFiltrados.size(); i++) {
            Evento e = eventosFiltrados.get(i);
            System.out.printf("%d. %s | ONG: %s%n", i + 1, e.getNome(), e.getOng().getNome());
        }
    }

    private void inscreverEvento() {
        List<Evento> eventos = eventDAO.listarEventos();

        if (eventos.isEmpty()) {
            System.out.println("\nNenhum evento cadastrado.\n");
            return;
        }

        for (int i = 0; i < eventos.size(); i++) {
            Evento e = eventos.get(i);
            System.out.printf("%d. %s | Cidade: %s | ONG: %s%n",
                    i + 1, e.getNome(), e.getCidade(), e.getOng().getNome());
        }

        System.out.println("\n-------------------------------------------");
        System.out.print("Digite o número do evento que deseja se inscrever: ");
        int escolha = Integer.parseInt(sc.nextLine()) - 1;

        if (escolha < 0 || escolha >= eventos.size()) {
            System.out.println("\nEvento inválido!\n");
            return;
        }

        Evento eventoEscolhido = eventos.get(escolha);
        eventDAO.inscreverVoluntario(eventoEscolhido, voluntario);

        System.out.println("\n\n===========================================");
        System.out.println("         INSCRIÇÃO REALIZADA COM SUCESSO   ");
        System.out.println("===========================================\n");
        System.out.println("Evento: " + eventoEscolhido.getNome());
        System.out.println("Cidade: " + eventoEscolhido.getCidade());
        System.out.println("ONG responsável: " + eventoEscolhido.getOng().getNome());
        System.out.println("-------------------------------------------\n");
    }
}