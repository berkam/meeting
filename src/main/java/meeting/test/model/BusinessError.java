package meeting.test.model;

public enum BusinessError {
    IncorrectTime("Not correct time"),
    MeetingNotExist("Meeting with this id not exist"),
    UserBusy("This user is busy for this time");

    private final String description;

    BusinessError(String description) {
        this.description = description;
    }

    public String description() {
        return description;
    }
}
