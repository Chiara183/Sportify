package sportify.pattern;

import java.util.List;
import java.util.Vector;

/**
 * An abstract class representing a subject
 * in the Observer pattern. A subject can
 * have multiple observers, and notifies them
 * when there is a change in its state. The
 * observer pattern allows for loose coupling
 * between the subject and the observers.
 * This class provides methods for attaching
 * and detaching observers, as well as a
 * protected method for notifying observers
 * of a change in the subject.
 */
public abstract class Subject {

    /**
     *  A list of observers for this subject.
     */
    protected final List<Observer> OBSERVERS;

    /**
     * A mutex used for synchronizing access to the list of observers.
     */
    protected final Object MUTEX = new Object();

    /**
     * Constructs a new subject with no observers.
     */
    protected Subject() {
        this((Observer) null);
    }

    /**
     * Constructs a new subject with
     * a single observer.
     *
     * @param obs The observer to
     *            attach to this subject.
     */
    protected Subject(Observer obs) {
        this(new Vector<>());
        if (obs != null)
            this.OBSERVERS.add(obs);
    }

    /**
     * Constructs a new subject with a
     * list of observers.
     *
     * @param list The list of observers
     *            to attach to this subject.
     */
    protected Subject(List<Observer> list) {
        this.OBSERVERS = list;
    }

    /**
     * Attaches an observer to this subject.
     *
     * @param obs The observer to attach.
     */
    public void attach(Observer obs) {
        synchronized (MUTEX) {
            this.OBSERVERS.add(obs);
        }
    }

    /**
     * Detaches an observer from this subject.
     *
     * @param obs The observer to detach.
     */
    public void detach(Observer obs) {
        synchronized (MUTEX) {
            this.OBSERVERS.remove(obs);
        }
    }

    /**
     * Notifies observers of a change in the subject.
     * The exact change being notified is defined by
     * concrete subclasses of this class.
     */
    protected abstract void notifyAddCourse();
}
