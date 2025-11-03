package org.example.view;

import java.util.Scanner;
import java.util.List;

import org.example.dao.UsuarioDAO;
import org.example.model.Usuario;
import org.example.model.Voluntario;
import org.example.model.Ong;

public class MenuAdmin {
    private final Scanner sc = new Scanner(System.in);
    private final Usuario admin;
    private final UsuarioDAO usuarioDAO;

    public MenuAdmin(Usuario admin, UsuarioDAO usuarioDAO) {
        this.admin = admin;
        this.usuarioDAO = usuarioDAO;
    }

    public void exibir() {
        while (true) {
            System.out.println("\n--- Menu Admin ---");
            System.out.println("1. Cadastrar ONG");
            System.out.println("2. Listar Voluntários");
            System.out.println("0. Logout");
            System.out.print("\nEscolha: ");
            String op = sc.nextLine();
            switch (op) {
                case "1" -> cadastrarOng();
                case "2" -> listarVoluntarios();
                case "0" -> { System.out.println("Logout realizado.\n"); return; }
                default -> System.out.println(" Opção inválida!\n");
            }
        }
    }

    private void cadastrarOng() {
        System.out.print("Nome ONG: ");
        String nome = sc.nextLine();
        System.out.print("Email: ");
        String email = sc.nextLine();
        System.out.print("Senha: ");
        String senha = sc.nextLine();

        Ong o = new Ong(nome, email, senha);
        if (usuarioDAO.cadastrarOng(o))
            System.out.println("\nONG cadastrada com sucesso!\n");
        else
            System.out.println(" Erro ao cadastrar ONG.\n");
    }

    private void listarVoluntarios() {
        List<Voluntario> voluntarios = usuarioDAO.listarVoluntarios();

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