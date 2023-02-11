package it.uniroma2.dicii.ispw.sportify.model.domain;

/**
 * A class representing a sport.
 */
public record Sport(String name, String type, String description) {

    /**
     * Returns the name of the sport.
     *
     * @return the name of the sport
     */
    public String getName() {
        return name;
    }

    /**
     * Returns the description of the sport.
     *
     * @return the description of the sport
     */
    public String getDescription() {
        return description;
    }

    /**
     * Returns the type of the sport.
     *
     * @return the type of the sport
     */
    public String getType() {
        return type;
    }
}
