package org.example;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import org.example.dao.EventoDAO;
import org.example.dao.UsuarioDAO;
import org.example.model.Evento;
import org.example.model.Ong;
import org.example.model.Usuario;
import org.example.model.Voluntario;

public class Main {

    static Scanner sc = new Scanner(System.in);
    static UsuarioDAO userDAO = new UsuarioDAO();
    static EventoDAO eventDAO = new EventoDAO();

    public static void main(String[] args) {
        while (true) {
            System.out.println();
            System.out.println("========================================================");
            System.out.println("                 BEM-VINDO AO VolunConnect  ");
            System.out.println("========================================================");
            System.out.println("--------------------------------------------------------");
            System.out.println("                   [1] Cadastrar Voluntário");
            System.out.println("                   [2] Login");
            System.out.println("                   [0] Sair");
            System.out.println("--------------------------------------------------------");
            System.out.print("                  Escolha uma opção: ");

            String op = sc.nextLine();
            System.out.println();

            switch (op) {
                case "1" -> cadastrarVoluntario();
                case "2" -> login();
                case "0" -> {
                    System.out.println("\n Obrigado por usar o VolunConnect!");
                    System.out.println("Até logo");
                    System.out.println("========================================================");
                    System.exit(0);
                }
                default -> System.out.println("  Opção inválida! Tente novamente.\n");
            }
        }
    }

    // -------------------- CADASTROS --------------------
    static void cadastrarVoluntario() {
        System.out.print("Nome: "); 
        String nome = sc.nextLine();
        System.out.print("Email: "); 
        String email = sc.nextLine();
        System.out.print("Senha: "); 
        String senha = sc.nextLine();

        Voluntario v = new Voluntario(nome, email, senha);
        System.out.println("Digite suas habilidades técnicas (separar por vírgula):");
        String[] habs = sc.nextLine().split(",");
        for (String h : habs) v.adicionarHabilidade(h.trim());

        if (userDAO.cadastrarVoluntario(v)) 
            System.out.println("\n Voluntário cadastrado com sucesso!\n");
        else 
            System.out.println(" Erro ao cadastrar voluntário.\n");
    }

    static void cadastrarOng() {
        System.out.print("Nome ONG: "); 
        String nome = sc.nextLine();
        System.out.print("Email: "); 
        String email = sc.nextLine();
        System.out.print("Senha: "); 
        String senha = sc.nextLine();

        Ong o = new Ong(nome, email, senha);
        if (userDAO.cadastrarOng(o)) 
            System.out.println("\n ONG cadastrada com sucesso!\n");
        else 
            System.out.println(" Erro ao cadastrar ONG.\n");
    }

    // -------------------- LOGIN --------------------
    static void login() {
        System.out.print("Email: "); 
        String email = sc.nextLine();
        System.out.print("Senha: "); 
        String senha = sc.nextLine();

        Usuario u = userDAO.login(email, senha);
        if (u == null) { 
            System.out.println(" Login inválido!\n"); 
            return; 
        }

        if (u instanceof Voluntario) menuVoluntario((Voluntario) u);
        else if (u instanceof Ong) menuOng((Ong) u, userDAO);
        else menuAdmin(u);
    }

    // -------------------- MENUS --------------------
    static void menuAdmin(Usuario admin) {
        while (true) {
            System.out.println("\n--- Menu Admin ---");
            System.out.println("1. Cadastrar ONG");
            System.out.println("2. Listar Voluntários");
            System.out.println("0. Logout");
            System.out.print("Escolha: ");
            String op = sc.nextLine();
            switch (op) {
                case "1" -> cadastrarOng();
                case "2" -> listarVoluntarios();
                case "0" -> { System.out.println("Logout realizado.\n"); return; }
                default -> System.out.println(" Opção inválida!\n");
            }
        }
    }

    static void menuOng(Ong ong, UsuarioDAO usuarioDAO) {
        while (true) {
            System.out.println("\n--- Menu ONG ---");
            System.out.println("1. Criar Evento");
            System.out.println("2. Listar Eventos");
            System.out.println("0. Logout");
            System.out.print("Escolha: ");
            String op = sc.nextLine();
            switch (op) {
                case "1" -> criarEvento(usuarioDAO);
                case "2" -> listarEventos();
                case "0" -> { System.out.println("Logout realizado.\n"); return; }
                default -> System.out.println("  Opção inválida!\n");
            }
        }
    }

    static void menuVoluntario(Voluntario v) {
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
                case "3" -> inscreverEvento(v);
                case "0" -> { System.out.println("Logout realizado.\n"); return; }
                default -> System.out.println("  Opção inválida!\n");
            }
        }
    }

    // -------------------- EVENTOS --------------------
    static void criarEvento(UsuarioDAO usuarioDAO) {
        List<Ong> ongs = usuarioDAO.listarOngs();
        if (ongs.isEmpty()) {
            System.out.println(" Nenhuma ONG cadastrada!\n");
            return;
        }
    
        System.out.println("\n===========================================");
        System.out.println("        LISTA DE ONGs CADASTRADAS        ");
        System.out.println("===========================================\n");
    
        System.out.printf("%-5s | %-25s | %-30s%n", "ID", "Nome da ONG", "Email");
        System.out.println("---------------------------------------------------------------" +
                           "----------------");
    
        for (int i = 0; i < ongs.size(); i++) {
            Ong ong = ongs.get(i);
            System.out.printf("%-5d | %-25s | %-30s%n", i + 1, ong.getNome().trim(), ong.getEmail().trim());
        }
    
        System.out.println("---------------------------------------------------------------" +
                           "----------------");
        System.out.println("Total de ONGs cadastradas: " + ongs.size());
        System.out.println("---------------------------------------------------------------" +
                           "----------------\n");
    
        System.out.print("Digite o número da ONG que vai criar o evento: ");
        int ongEscolhidaIndex = Integer.parseInt(sc.nextLine()) - 1;
        if (ongEscolhidaIndex < 0 || ongEscolhidaIndex >= ongs.size()) {
            System.out.println(" ONG inválida!\n");
            return;
        }
        Ong ong = ongs.get(ongEscolhidaIndex);
    
        System.out.print("Nome do evento: ");
        String nome = sc.nextLine().trim();
        System.out.print("Cidade: ");
        String cidade = sc.nextLine().trim();
        System.out.print("Público: ");
        String publico = sc.nextLine().trim();
        System.out.println("Capacidades (separadas por vírgula): ");
        String[] caps = sc.nextLine().split(",");
        List<String> capacidades = new ArrayList<>();
        for (String c : caps) capacidades.add(c.trim());
    
        Evento e = new Evento(nome, cidade, publico, capacidades, ong);
        if (eventDAO.cadastrarEvento(e))
            System.out.println(" Evento criado com sucesso!\n");
        else
            System.out.println(" Erro ao criar evento.\n");
    }
    

    static void listarEventos() {
        List<Evento> eventos = eventDAO.listarEventos();

        if (eventos.isEmpty()) {
            System.out.println("\n Nenhum evento cadastrado no momento.\n");
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

    static void filtrarEventosCidade() {
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

        for (Evento e : eventosFiltrados) {
            System.out.println("Evento: " + e.getNome() + " | ONG: " + e.getOng().getNome());
        }
    }

    static void inscreverEvento(Voluntario v) {
        listarEventos();
        System.out.print("Digite o nome do evento que deseja se inscrever: "); 
        String nomeEv = sc.nextLine();
        List<Evento> eventos = eventDAO.listarEventos();
        for (Evento e : eventos) {
            if (e.getNome().equalsIgnoreCase(nomeEv)) {
                eventDAO.inscreverVoluntario(e, v);
                System.out.println(" Inscrição realizada!\n");
                return;
            }
        }
        System.out.println(" Evento não encontrado.\n");
    }

    // -------------------- LISTAR VOLUNTÁRIOS --------------------
    static void listarVoluntarios() {
        List<Voluntario> voluntarios = userDAO.listarVoluntarios();

        if (voluntarios.isEmpty()) {
            System.out.println("\n Nenhum voluntário cadastrado.\n");
            return;
        }

        System.out.println("\n=======================================");
        System.out.println("        LISTA DE VOLUNTÁRIOS        ");
        System.out.println("=======================================\n");

        System.out.printf("%-5s | %-25s | %-30s%n", "ID", "Nome", "Email");
        System.out.println("----------------------------------------------------------");

        int index = 1;
        for (Voluntario v : voluntarios) {
            System.out.printf("%-5d | %-25s | %-30s%n", index++, v.getNome(), v.getEmail());
        }

        System.out.println("----------------------------------------------------------");
        System.out.println("Total de voluntários cadastrados: " + voluntarios.size());
        System.out.println("----------------------------------------------------------\n");
    }
}
