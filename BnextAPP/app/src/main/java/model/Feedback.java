package model;
import java.util.UUID;

public class Feedback {
    public UUID idFeedback;
    public String comment;
    private User user;
    private Car car;

    public Feedback(UUID idFeedback, String comment, User user, Car car) {
        this.idFeedback = idFeedback;
        this.comment = comment;
        this.user = user;
        this.car = car;
    }

    public Feedback(String comment, User user, Car car) {
        this.comment = comment;
        this.user = user;
        this.car = car;
        this.idFeedback = UUID.randomUUID();
    }

    public UUID getIdFeedback() {
        return idFeedback;
    }

    public void setIdFeedback(UUID idFeedback) {
        this.idFeedback = idFeedback;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public Car getCar() {
        return car;
    }

    public void setCar(Car car) {
        this.car = car;
    }
}
