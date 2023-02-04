package sportify;

import java.util.List;
import java.util.Vector;

/**
 * Class representing a subject object
 * that notifies registered observers
 * whenever its state changes.
 */
public abstract class Subject {

    /**
     * List of registered observers.
     */
    protected final List<Observer> observers;
    protected final Object mutex = new Object();

    /**
     * The constructor method.
     */
    protected Subject() {
        this((Observer) null);
    }

    /**
     * The constructor method.
     *
     * @param obs The first observer
     *            of the subject
     */
    protected Subject(Observer obs) {
        this(new Vector<>());
        if (obs != null)
            this.observers.add(obs);
    }

    /**
     * The constructor method.
     *
     * @param list The list of observers
     *             to be added to the subject
     */
    protected Subject(List<Observer> list) {
        this.observers = list;
    }

    /**
     * Register a new observer to be notified
     * whenever the subject's status changes.
     *
     * @param obs new observer to register
     */
    public void attach(Observer obs) {
        synchronized (mutex) {
            this.observers.add(obs);
        }
    }

    /**
     * Removes an observer from the list of
     * registered observers.
     *
     * @param obs observer to remove
     */
    public void detach(Observer obs) {
        synchronized (mutex) {
            this.observers.remove(obs);
        }
    }

    /**
     * Notifies registered observers of the
     * change in the subject's status.
     */
    protected abstract void notifyAddCourse();
}
