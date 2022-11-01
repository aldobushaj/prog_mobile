package model;
import java.io.Serializable;
import java.util.UUID;

public class Feedback implements Serializable {
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



    public Feedback(UUID idFeedback, String comment, Car car, User user) {
        this.comment = comment;
        this.car = car;
        this.user = user;

        this.idFeedback = idFeedback;
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


    @Override
    public String toString() {
        return "Feedback{" +
                "idFeedback=" + idFeedback +
                ", comment='" + comment + '\'' +
                ", user=" + user +
                ", car=" + car +
                '}';
    }
}
