package model;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.UUID;

public class User {
    private UUID userId;
    private String name;
    private String surname;
    private Date birthDate;
    private String username;
    private String password;
    private int active = 1;
    // Per inserire più ruoli, inserirli tutti nella stessa stringa divisi con ","
    private String permissions;
    private String roles;
    private List<Car> ownedCars = new ArrayList<>();
    private List<Reservation> reservations = new ArrayList<>();
    private List<Feedback> feedbacks = new ArrayList<>();

    public User(UUID userId, String name, String surname, Date birthDate, String username, String password, int active, String permissions, String roles, List<Car> ownedCars, List<Reservation> reservations, List<Feedback> feedbacks) {
        /*
        * Complete constructor
        * */
        this.userId = userId;
        this.name = name;
        this.surname = surname;
        this.birthDate = birthDate;
        this.username = username;
        this.password = password;
        this.active = active;
        this.permissions = permissions;
        this.roles = roles;
        this.ownedCars = ownedCars;
        this.reservations = reservations;
        this.feedbacks = feedbacks;
    }

    public User(UUID userId, String name, String surname, Date birthDate, String username, String password, int active, String permissions, String roles) {
        this.userId = userId;
        this.name = name;
        this.surname = surname;
        this.birthDate = birthDate;
        this.username = username;
        this.password = password;
        this.active = active;
        this.permissions = permissions;
        this.roles = roles;
    }

    public UUID getUserId() {
        return userId;
    }

    public void setUserId(UUID userId) {
        this.userId = userId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSurname() {
        return surname;
    }

    public void setSurname(String surname) {
        this.surname = surname;
    }

    public Date getBirthDate() {
        return birthDate;
    }

    public void setBirthDate(Date birthDate) {
        this.birthDate = birthDate;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public int getActive() {
        return active;
    }

    public void setActive(int active) {
        this.active = active;
    }

    public String getPermissions() {
        return permissions;
    }

    public void setPermissions(String permissions) {
        this.permissions = permissions;
    }

    public String getRoles() {
        return roles;
    }

    public void setRoles(String roles) {
        this.roles = roles;
    }

    public List<Car> getOwnedCars() {
        return ownedCars;
    }

    public void setOwnedCars(List<Car> ownedCars) {
        this.ownedCars = ownedCars;
    }

    public List<Reservation> getReservations() {
        return reservations;
    }

    public void setReservations(List<Reservation> reservations) {
        this.reservations = reservations;
    }

    public List<Feedback> getFeedbacks() {
        return feedbacks;
    }

    public void setFeedbacks(List<Feedback> feedbacks) {
        this.feedbacks = feedbacks;
    }
}


