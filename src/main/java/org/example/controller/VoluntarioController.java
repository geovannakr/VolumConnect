package org.example.controller;

import org.example.model.Voluntario;
import java.util.ArrayList;
import java.util.List;

public class VoluntarioController {
    private List<Voluntario> voluntarios = new ArrayList<>();

    public void adicionarVoluntario(Voluntario v) {
        voluntarios.add(v);
    }

    public List<Voluntario> listarVoluntarios() {
        return voluntarios;
    }

    public void notificarTodos(String mensagem) {
        for (Voluntario v : voluntarios) {
            v.update(mensagem);
        }
    }
}
