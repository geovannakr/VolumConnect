package org.example.observer;

import java.util.ArrayList;
import java.util.List;

public class EventNotifier {
    private List<Observer> observers = new ArrayList<>();

    public void addObserver(Observer o) { observers.add(o); }
    public void removeObserver(Observer o) { observers.remove(o); }

    public void notifyObservers(String mensagem) {
        for (Observer o : observers) {
            o.update(mensagem);
        }
    }
}
