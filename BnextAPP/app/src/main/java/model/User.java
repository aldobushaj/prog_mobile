package model;

import androidx.annotation.NonNull;

import java.io.Serializable;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.UUID;

public class User implements Serializable {
    private UUID userId;
    private String name;
    private String surname;
    private Date birthDate;
    private String username;
    private String password;
    private int active;
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

    public User(UUID userId, String username) {
        /*FOr update username*/
        this.userId = userId;
        this.username = username;
    }

    public User(String username, String password){
        this.username = username;
        this.password = password;
    }

    public User(UUID userId, String name, String surname){
        this.userId = userId;
        this.name = name;
        this.surname = surname;
    }

    public User(JSONObject user) throws JSONException, ParseException {
        //System.out.println("Ecco l'utente*********************+\n"+user);
        this.userId = UUID.fromString(user.get("userId").toString());
        this.name = user.get("name").toString();
        this.surname = user.get("surname").toString();
        this.birthDate = new SimpleDateFormat("yyyy-MM-dd'T'hh:mm:ss.sssZ").parse(user.get("birthDate").toString());
        this.username = user.get("username").toString();
        this.password = user.get("password").toString();
        this.active = (int) user.get("active");
        this.permissions = user.get("permissions").toString();
        this.roles = user.get("roles").toString();

        this.ownedCars = null;
        this.reservations = null;
        this.feedbacks = null;

    }

    public User(){};

    public User(UUID userId) {
        this.userId =  userId;
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

    @NonNull
    @Override
    public String toString() {
        return "User{" +
                "userId=" + userId +
                ", name='" + name + '\'' +
                ", surname='" + surname + '\'' +
                ", birthDate=" + birthDate +
                ", username='" + username + '\'' +
                ", password='" + password + '\'' +
                ", active=" + active +
                ", permissions='" + permissions + '\'' +
                ", roles='" + roles + '\'' +
                ", ownedCars=" + ownedCars +
                ", reservations=" + reservations +
                ", feedbacks=" + feedbacks +
                '}';
    }
}


