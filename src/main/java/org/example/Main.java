package org.example;
import org.example.dao.UsuarioDAO;
import org.example.dao.EventoDAO;
import org.example.model.*;

import java.util.*;

public class Main {

    static Scanner sc = new Scanner(System.in);
    static UsuarioDAO userDAO = new UsuarioDAO();
    static EventoDAO eventDAO = new EventoDAO();

    public static void main(String[] args) {
        while(true){
            System.out.println("\n--- Plataforma de Voluntariado ---");
            System.out.println("1. Cadastrar Voluntário");
            System.out.println("2. Login");
            System.out.println("0. Sair");
            System.out.print("Escolha: ");
            String op = sc.nextLine();

            switch(op){
                case "1": cadastrarVoluntario(); break;
                case "2": login(); break;
                case "0": System.exit(0);
                default: System.out.println("Opção inválida!");
            }
        }
    }

    static void cadastrarVoluntario(){
        System.out.print("Nome: "); String nome = sc.nextLine();
        System.out.print("Email: "); String email = sc.nextLine();
        System.out.print("Senha: "); String senha = sc.nextLine();
        Voluntario v = new Voluntario(nome,email,senha);
        System.out.println("Digite suas habilidades técnicas (separar por vírgula):");
        String[] habs = sc.nextLine().split(",");
        for(String h: habs) v.adicionarHabilidade(h.trim());

        if(userDAO.cadastrarVoluntario(v)) System.out.println("Voluntário cadastrado!");
        else System.out.println("Erro ao cadastrar.");
    }

    static void login(){
        System.out.print("Email: "); String email = sc.nextLine();
        System.out.print("Senha: "); String senha = sc.nextLine();

        Usuario u = userDAO.login(email,senha);
        if(u==null){ System.out.println("Login inválido!"); return; }

        if(u instanceof Voluntario) menuVoluntario((Voluntario)u);
        else if(u instanceof Ong) menuOng((Ong)u, userDAO);
        else menuAdmin(u);
    }

    static void menuAdmin(Usuario admin){
        while(true){
            System.out.println("\n--- Menu Admin ---");
            System.out.println("1. Cadastrar ONG");
            System.out.println("0. Logout");
            String op = sc.nextLine();
            switch(op){
                case "1": cadastrarOng(); break;
                case "0": return;
            }
        }
    }

    static void cadastrarOng(){
        System.out.print("Nome ONG: "); String nome = sc.nextLine();
        System.out.print("Email: "); String email = sc.nextLine();
        System.out.print("Senha: "); String senha = sc.nextLine();
        Ong o = new Ong(nome,email,senha);
        if(userDAO.cadastrarOng(o)) System.out.println("ONG cadastrada!");
        else System.out.println("Erro ao cadastrar ONG.");
    }

    static void menuOng(Ong ong, UsuarioDAO usuarioDAO){
        while(true){
            System.out.println("\n--- Menu ONG ---");
            System.out.println("1. Criar Evento");
            System.out.println("2. Listar Eventos");
            System.out.println("0. Logout");
            String op = sc.nextLine();
            switch(op){
                case "1":
                    criarEvento(usuarioDAO); break;
                case "2": listarEventos(); break;
                case "0": return;
            }
        }
    }

    static void criarEvento(UsuarioDAO usuarioDAO){
        List<Ong> ongs = usuarioDAO.listarOngs();
        if(ongs.isEmpty()){
            System.out.println("Nenhuma ONG cadastrada!");
            return;
        }

        System.out.println("=== Lista de ONGs ===");
        for(int i = 0; i < ongs.size(); i++){
            Ong ong = ongs.get(i);
            System.out.printf("[%d] %s (Email: %s)%n", i+1, ong.getNome(), ong.getEmail());
        }

        System.out.print("Digite o número da ONG que vai criar o evento: ");
        int ongEscolhidaIndex = Integer.parseInt(sc.nextLine()) - 1;
        if(ongEscolhidaIndex < 0 || ongEscolhidaIndex >= ongs.size()){
            System.out.println("ONG inválida!");
            return;
        }
        Ong ong = ongs.get(ongEscolhidaIndex);

        System.out.print("Nome do evento: ");
        String nome = sc.nextLine();
        System.out.print("Cidade: ");
        String cidade = sc.nextLine();
        System.out.print("Público: ");
        String publico = sc.nextLine();
        System.out.println("Capacidades (separadas por vírgula): ");
        String[] caps = sc.nextLine().split(",");
        List<String> capacidades = new ArrayList<>();
        for(String c : caps) capacidades.add(c.trim());

        Evento e = new Evento(nome, cidade, publico, capacidades, ong);
        if(eventDAO.cadastrarEvento(e))
            System.out.println("Evento criado com sucesso!");
        else
            System.out.println("Erro ao criar evento.");
    }

    static void listarEventos(){
        List<Evento> eventos = eventDAO.listarEventos();
        System.out.println("--- Eventos ---");
        for(Evento e : eventos){
            System.out.println("Evento: "+e.getNome()+" | Cidade: "+e.getCidade()+" | ONG: "+e.getOng().getNome());
        }
    }

    static void menuVoluntario(Voluntario v){
        while(true){
            System.out.println("\n--- Menu Voluntário ---");
            System.out.println("1. Listar Eventos");
            System.out.println("2. Filtrar Eventos por Cidade");
            System.out.println("3. Inscrever-se em Evento");
            System.out.println("0. Logout");
            String op = sc.nextLine();
            switch(op){
                case "1": listarEventos(); break;
                case "2": filtrarEventosCidade(); break;
                case "3": inscreverEvento(v); break;
                case "0": return;
            }
        }
    }

    static void filtrarEventosCidade(){
        System.out.print("Cidade: "); String cidade = sc.nextLine();
        List<Evento> eventos = eventDAO.listarEventos();
        System.out.println("--- Eventos na cidade: "+cidade+" ---");
        for(Evento e : eventos){
            if(e.getCidade().equalsIgnoreCase(cidade))
                System.out.println("Evento: "+e.getNome()+" | ONG: "+e.getOng().getNome());
        }
    }

    static void inscreverEvento(Voluntario v){
        listarEventos();
        System.out.print("Digite o nome do evento que deseja se inscrever: "); String nomeEv = sc.nextLine();
        List<Evento> eventos = eventDAO.listarEventos();
        for(Evento e : eventos){
            if(e.getNome().equalsIgnoreCase(nomeEv)){
                eventDAO.inscreverVoluntario(e,v);
                System.out.println("Inscrição realizada!");
                return;
            }
        }
        System.out.println("Evento não encontrado.");
    }
}