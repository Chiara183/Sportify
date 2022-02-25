package com.example.sportify;

import java.util.List;
import java.util.Vector;

public abstract class Subject {

    protected final List<Observer> observers;
    protected final Object mutex = new Object();

    protected Subject() {
        this((Observer) null);
    }

    protected Subject(Observer obs) {
        this(new Vector<>());
        if (obs != null)
            this.observers.add(obs);
    }

    protected Subject(List<Observer> list) {
        this.observers = list;
    }

    public void attach(Observer obs) {
        synchronized (mutex) {
            this.observers.add(obs);
        }
    }

    public void detach(Observer obs) {
        synchronized (mutex) {
            this.observers.remove(obs);
        }
    }


    protected abstract void notifyAddCourse();
}
