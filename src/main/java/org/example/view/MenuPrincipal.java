package org.example.view;

import java.util.Scanner;

import org.example.dao.EventoDAO;
import org.example.dao.UsuarioDAO;
import org.example.model.Ong;
import org.example.model.Usuario;
import org.example.model.Voluntario;

public class MenuPrincipal {
    private final Scanner sc = new Scanner(System.in);
    private final UsuarioDAO usuarioDAO;
    private final EventoDAO eventoDAO;

    public MenuPrincipal(UsuarioDAO usuarioDAO, EventoDAO eventoDAO) {
        this.usuarioDAO = usuarioDAO;
        this.eventoDAO = eventoDAO;
    }

    public void exibir() {
        while (true) {
            System.out.println();
            System.out.println("========================================================");
            System.out.println("                 BEM-VINDO AO VolunConnect  ");
            System.out.println("========================================================");
            System.out.println("--------------------------------------------------------");
            System.out.println("                   [1] Cadastrar Voluntário");
            System.out.println("                   [2] Login");
            System.out.println("                   [0] Logout");
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

    private void cadastrarVoluntario() {
        System.out.print("Nome: ");
        String nome = sc.nextLine();
        System.out.print("Email: ");
        String email = sc.nextLine();
        System.out.print("Senha: ");
        String senha = sc.nextLine();

        Voluntario v = new Voluntario(nome, email, senha);
        System.out.println("Digite suas habilidades técnicas (separar por vírgula):");
        String[] habs = sc.nextLine().split(",");
        for (String h : habs) if (!h.trim().isEmpty()) v.adicionarHabilidade(h.trim());

        if (usuarioDAO.cadastrarVoluntario(v))
            System.out.println("\n Voluntário cadastrado com sucesso!\n");
        else
            System.out.println(" Erro ao cadastrar voluntário.\n");
    }

    private void login() {
        System.out.print("Email: ");
        String email = sc.nextLine();
        System.out.print("Senha: ");
        String senha = sc.nextLine();

        Usuario u = usuarioDAO.login(email, senha);
        if (u == null) {
            System.out.println(" Login inválido!\n");
            return;
        }

        if (u instanceof Voluntario) {
            new MenuVoluntario((Voluntario) u, eventoDAO, usuarioDAO).exibir();
        } else if (u instanceof Ong) {
            new MenuOng((Ong) u, usuarioDAO, eventoDAO).exibir();
        } else {
            new MenuAdmin(u, usuarioDAO).exibir();
        }
    }
}