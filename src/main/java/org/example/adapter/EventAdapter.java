package org.example.adapter;

import java.util.ArrayList;
import org.example.model.Evento;
import org.example.model.Ong;

class ExternalEvent {
    public String title;
    public String city;
    public String audience;

    public ExternalEvent(String title, String city, String audience) {
        this.title = title;
        this.city = city;
        this.audience = audience;
    }
}

public class EventAdapter extends Evento {
    public EventAdapter(ExternalEvent extEvent, Ong ong) {
        super(extEvent.title, extEvent.city, extEvent.audience, new ArrayList<>(), ong);
    }
}
