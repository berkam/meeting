package meeting.test.model;

public enum BusinessError {
    IncorrectTime("Not correct time");

    private final String description;

    BusinessError(String description) {
        this.description = description;
    }

    public String description() {
        return description;
    }
}
