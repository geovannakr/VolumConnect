package org.example.view;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import org.example.dao.EventoDAO;
import org.example.dao.UsuarioDAO;
import org.example.model.Evento;
import org.example.model.Ong;
import org.example.model.Voluntario;

public class MenuOng {
    private final Scanner sc = new Scanner(System.in);
    private final Ong ong;
    private final UsuarioDAO usuarioDAO;
    private final EventoDAO eventDAO;

    public MenuOng(Ong ong, UsuarioDAO usuarioDAO, EventoDAO eventDAO) {
        this.ong = ong;
        this.usuarioDAO = usuarioDAO;
        this.eventDAO = eventDAO;
    }

    public void exibir() {
        while (true) {
            System.out.println("\n--- Menu ONG ---");
            System.out.println("1. Criar Evento");
            System.out.println("2. Listar Eventos");
            System.out.println("0. Logout");
            System.out.print("Escolha: ");
            String op = sc.nextLine();
            switch (op) {
                case "1" -> criarEvento();
                case "2" -> listarEventos();
                case "0" -> { System.out.println("Logout realizado.\n"); return; }
                default -> System.out.println("  Opção inválida!\n");
            }
        }
    }

    private void criarEvento() {
        System.out.print("\nDeseja importar um evento externo? (S/N): ");
        String opcao = sc.nextLine().trim();
    
        if (opcao.equalsIgnoreCase("S")) {
    
            List<Evento> eventosExternos = new ArrayList<>();
            eventosExternos.add(new Evento("Limpeza de Praia", "Jaraguá", "Voluntários", new ArrayList<>(), ong));
            eventosExternos.add(new Evento("Organização da Horta", "Blumenau", "Voluntários", new ArrayList<>(), ong));
            eventosExternos.add(new Evento("Plantio de Árvores", "Joinville", "Voluntários", new ArrayList<>(), ong));
            eventosExternos.add(new Evento("Limpeza de Canil", "Massaranduba", "Voluntários", new ArrayList<>(), ong));
    
            org.example.observer.EventNotifier notifier = new org.example.observer.EventNotifier();
            for (Voluntario v : usuarioDAO.listarVoluntarios()) {
                notifier.addObserver(v);
            }
    
            for (Evento e : eventosExternos) {
                if (eventDAO.cadastrarEvento(e)) {
                    System.out.println("\nEvento importado com sucesso: " + e.getNome() + " em " + e.getCidade());
    
                    notifier.notifyObservers("Novo evento criado: " + e.getNome() + " em " + e.getCidade());
                } else {
                    System.out.println("\nErro ao importar evento: " + e.getNome());
                }
            }
    
            System.out.println("\n=======================================");
            System.out.println("      EVENTOS IMPORTADOS NO SISTEMA     ");
            System.out.println("=======================================");
            for (Evento e : eventosExternos) {
                System.out.println("- " + e.getNome() + " | Cidade: " + e.getCidade() + " | Público: " + e.getPublico());
            }
            System.out.println("=======================================\n");
    
            return;
        }

        System.out.println("\n===========================================");
        System.out.println("           CADASTRO DE NOVO EVENTO         ");
        System.out.println("===========================================\n");

        System.out.print("Nome do evento: ");
        String nome = sc.nextLine().trim();
        System.out.print("Cidade: ");
        String cidade = sc.nextLine().trim();
        System.out.print("Público-alvo: ");
        String publico = sc.nextLine().trim();
        System.out.println("Capacidades exigidas (separadas por vírgula): ");
        String[] caps = sc.nextLine().split(",");
        List<String> capacidades = new ArrayList<>();
        for (String c : caps) if (!c.trim().isEmpty()) capacidades.add(c.trim());

        Evento e = new Evento(nome, cidade, publico, capacidades, ong);

        if (eventDAO.cadastrarEvento(e)) {
            System.out.println("\n-------------------------------------------");
            System.out.println("Evento criado com sucesso!");
            System.out.println("\n---- Detalhes do evento ----:");
            System.out.println("Nome: " + e.getNome());
            System.out.println("Cidade: " + e.getCidade());
            System.out.println("Público: " + e.getPublico());
            System.out.println("ONG responsável: " + e.getOng().getNome());
            System.out.println("Capacidades exigidas: " + String.join(", ", e.getCapacidades()));
            System.out.println("-------------------------------------------\n");

            org.example.observer.EventNotifier notifier = new org.example.observer.EventNotifier();
            for (Voluntario v : usuarioDAO.listarVoluntarios()) {
                notifier.addObserver(v);
            }
            notifier.notifyObservers("Novo evento criado: " + e.getNome() + " em " + e.getCidade());

        } else {
            System.out.println("\nErro ao criar evento.\n");
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

        System.out.printf("%-5s | %-25s | %-15s | %-20s%n", "ID", "Evento", "Cidade", "ONG");
        System.out.println("--------------------------------------------------------------------------");

        int index = 1;
        for (Evento e : eventos) {
            System.out.printf("%-5d | %-25s | %-15s | %-20s%n",
                    index++, e.getNome(), e.getCidade(), e.getOng().getNome());
        }

        System.out.println("--------------------------------------------------------------------------");
        System.out.println("Total de eventos cadastrados: " + eventos.size());
        System.out.println("--------------------------------------------------------------------------\n");
    }
}