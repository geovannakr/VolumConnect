package org.example.adapter;

import java.util.ArrayList;
import org.example.model.Evento;
import org.example.model.Ong;

public class EventAdapter {

    public static Evento adapt(ExternalEvent extEvent, Ong ong) {
        return new Evento(
            extEvent.title,
            extEvent.city,
            extEvent.audience,
            new ArrayList<>(),
            ong
        );
    }
}