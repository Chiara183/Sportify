package sportify.pattern;

/**
 * Interface representing an observer object that
 * is notified whenever the state of a subject
 * object changes.
 */
public interface Observer {

    /**
     * Method that is called whenever the
     * state of a subject object changes.
     *
     * @param sport the sport to be added
     * @param gym the gym that wants to
     *            add that sport
     * @param time the hours when sports
     *             can be practiced in
     *             the gym
     */
    void addCourse(String sport, String gym, String time);
}
