package com.example.bnext;

import static org.junit.Assert.assertEquals;

import org.junit.After;
import org.junit.Test;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.UUID;

import model.Car;
import model.Feedback;
import model.Position;
import model.Reservation;
import model.User;

/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * @see <a href="http://d.android.com/tools/testing">Testing documentation</a>
 */
public class ExampleUnitTest {
    @Test
    public void addition_isCorrect() {
        assertEquals(4, 2 + 2);
    }

    //User Test
    @Test
    public  void userConstructorOne() {
        User userTest = new User("username","password");

        assertEquals("username", userTest.getUsername());
        assertEquals("password", userTest.getPassword());
    }
    @Test
    public  void userConstructorTwo() {
        UUID userUUID = UUID.randomUUID();
        User userTest = new User(userUUID,"username");

        assertEquals(userUUID.toString(), userTest.getUserId().toString());
        assertEquals("username", userTest.getUsername());
    }
    @Test
    public  void userConstructorComplete() {
        UUID userUUID = UUID.randomUUID();
        User userTest = new User(userUUID,"username");

        String sDate1="31/12/1998";
        /*try {
            Date date1=new SimpleDateFormat("dd/MM/yyyy").parse(sDate1);
            userTest.setBirthDate(date1);
        } catch (ParseException e) {
            e.printStackTrace();
        }*/
        userTest.setFeedbacks(new ArrayList<Feedback>());
        userTest.setName("TestName");
        userTest.setOwnedCars(new ArrayList<Car>());
        userTest.setRoles("ADMIN");
        userTest.setPassword("password");
        userTest.setSurname("TestSurname");
        assertEquals(userUUID.toString(), userTest.getUserId().toString());
        assertEquals("username", userTest.getUsername());
        assertEquals("Thu Dec 31 00:00:00 CET 1998", userTest.getBirthDate().toString());
        assertEquals("[]", userTest.getFeedbacks().toString());
        assertEquals("[]", userTest.getOwnedCars().toString());
        assertEquals("ADMIN", userTest.getRoles());
        assertEquals("password", userTest.getPassword());
        assertEquals("TestSurname", userTest.getSurname());
    }


    /*
    * Car Test
    * */
    @Test
    public  void CarPlateNumber() {
        Car testCar = new Car("TestPlateNumber");
        assertEquals("TestPlateNumber", testCar.getPlateNumber());
    }
    @Test
    public  void CarUUID() {
        Car testCar = new Car("TestPlateNumber");
        UUID testUUID = UUID.randomUUID();
        testCar.setCarId(testUUID);
        assertEquals(testUUID.toString(), testCar.getCarId().toString());
    }

    @Test
    public  void CarName() {
        Car testCar = new Car("TestPlateNumber");
        testCar.setName("test");
        assertEquals("test", testCar.getName());
    }



    /* Feedback Test*/

    @Test
    public  void FeedbackName() {
        Car testCar = new Car("TestPlateNumber");
        UUID testUUID = UUID.randomUUID();
        User testUser = new User(testUUID,"username");
        Feedback testFeedback = new Feedback("testComment",testUser,testCar);
        assertEquals("testComment", testFeedback.getComment());
    }

    /* Reservation Test*/

    @Test
    public  void ReservationUUID() {
        Car testCar = new Car("TestPlateNumber");
        UUID testUUID = UUID.randomUUID();
        User testUser = new User(testUUID,"username");
        Feedback testFeedback = new Feedback("testComment",testUser,testCar);

        Reservation testReservation = new Reservation(testUUID);
        testReservation.setCar(testCar);
        testReservation.setUser(testUser);

        assertEquals(testUUID.toString(),testReservation.getReservationId().toString());

    }

    @Test
    public  void ReservationCar() {
        Car testCar = new Car("TestPlateNumber");
        UUID testUUID = UUID.randomUUID();
        User testUser = new User(testUUID,"username");
        Feedback testFeedback = new Feedback("testComment",testUser,testCar);

        Reservation testReservation = new Reservation(testUUID);
        testReservation.setCar(testCar);
        testReservation.setUser(testUser);

        assertEquals(testCar.toString(), testReservation.getCar().toString());
    }

    @Test
    public  void ReservationUser() {
        Car testCar = new Car("TestPlateNumber");
        UUID testUUID = UUID.randomUUID();
        User testUser = new User(testUUID,"username");
        Feedback testFeedback = new Feedback("testComment",testUser,testCar);

        Reservation testReservation = new Reservation(testUUID);
        testReservation.setCar(testCar);
        testReservation.setUser(testUser);

        assertEquals(testUser.toString(),testReservation.getUser().toString());
    }
    @Test
    public  void ReservationDestination() {
        Car testCar = new Car("TestPlateNumber");
        UUID testUUID = UUID.randomUUID();
        User testUser = new User(testUUID,"username");
        Feedback testFeedback = new Feedback("testComment",testUser,testCar);

        Reservation testReservation = new Reservation(testUUID);
        testReservation.setCar(testCar);
        testReservation.setUser(testUser);
        Position testPosition = new Position(123.00,123.00);
        testReservation.setDestination(testPosition);

        assertEquals(testPosition.toString(),testReservation.getDestination().toString());
    }

    @Test
    public  void ReservationStartPosition() {
        Car testCar = new Car("TestPlateNumber");
        UUID testUUID = UUID.randomUUID();
        User testUser = new User(testUUID,"username");
        Feedback testFeedback = new Feedback("testComment",testUser,testCar);

        Reservation testReservation = new Reservation(testUUID);
        testReservation.setCar(testCar);
        testReservation.setUser(testUser);
        Position startPosition = new Position(123.00,123.00);
        testReservation.setStartPosition(startPosition);
        assertEquals(startPosition.toString(),testReservation.getStartPosition().toString());
    }
}