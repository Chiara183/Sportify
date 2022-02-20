package com.example.sportify;

import java.util.List;
import java.util.Vector;

public abstract class Subject {

    protected List<Observer> observers;
    protected final Object MUTEX = new Object();

    public Subject() {
        this((Observer) null);
    }

    public Subject(Observer obs) {
        this(new Vector<Observer>());
        if (obs != null)
            this.observers.add(obs);
    }

    public Subject(List<Observer> list) {
        this.observers = list;
    }

    public void attach(Observer obs) {
        synchronized (MUTEX) {
            this.observers.add(obs);
        }
    }

    public void detach(Observer obs) {
        synchronized (MUTEX) {
            this.observers.remove(obs);
        }
    }


    protected abstract void notifyAddCourse();
}
