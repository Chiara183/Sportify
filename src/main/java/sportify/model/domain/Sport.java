package sportify.model.domain;

public record Sport(String name, String type, String description) {
    public String getName() {
        return name;
    }

    public String getDescription() {
        return description;
    }

    public String getType() {
        return type;
    }
}
