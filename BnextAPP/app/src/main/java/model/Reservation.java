package model;

import java.io.Serializable;
import java.util.Date;
import java.util.UUID;


public class Reservation implements Serializable {

    private UUID reservationId;
    private Date startOfBook;
    private Date endOfBook;
    private User user;
    private Car car;
    private Position destination;
    private Position startPosition;

    public Reservation(UUID reservationId) {
        this.reservationId = reservationId;
    }

    public Reservation(UUID reservationId, Date startOfBook, Date endOfBook, User user, Car car, Position destination, Position startPosition) {
        /*
        * Provide reservation ID
        * */
        this.startOfBook = startOfBook;
        this.endOfBook = endOfBook;
        this.user = user;
        this.car = car;
        this.destination = destination;
        this.startPosition = startPosition;
        this.reservationId= reservationId;
    }
    public Reservation( Date startOfBook, Date endOfBook, User user, Car car, Position destination, Position startPosition) {
        /*
        * No need to provide ID
        * */
        this.startOfBook = startOfBook;
        this.endOfBook = endOfBook;
        this.user = user;
        this.car = car;
        this.destination = destination;
        this.startPosition = startPosition;
        this.reservationId= UUID.randomUUID();
    }

    public UUID getReservationId() {
        return reservationId;
    }

    public void setReservationId(UUID reservationId) {
        this.reservationId = reservationId;
    }

    public Date getStartOfBook() {
        return startOfBook;
    }

    public void setStartOfBook(Date startOfBook) {
        this.startOfBook = startOfBook;
    }

    public Date getEndOfBook() {
        return endOfBook;
    }

    public void setEndOfBook(Date endOfBook) {
        this.endOfBook = endOfBook;
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

    public Position getDestination() {
        return destination;
    }

    public void setDestination(Position destination) {
        this.destination = destination;
    }

    public Position getStartPosition() {
        return startPosition;
    }

    public void setStartPosition(Position startPosition) {
        this.startPosition = startPosition;
    }

    @Override
    public String toString() {
        return "Reservation{" +
                "reservationId=" + reservationId +
                ", startOfBook=" + startOfBook +
                ", endOfBook=" + endOfBook +
                ", user=" + user +
                ", car=" + car +
                ", destination=" + destination +
                ", startPosition=" + startPosition +
                '}';
    }
}
