package Model;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class PurchasedFlight {
    private int FlightId;
    private String DateOfPurchase;
    private String TimeOfPurchase;
    private String UserName;
    private String DBName;

    public PurchasedFlight(String databaseName) {
        this.DBName = databaseName;
    }

    public PurchasedFlight(int flightId, String dateOfPurchase, String timeOfPurchase, String userName) {
        FlightId = flightId;
        DateOfPurchase = dateOfPurchase;
        TimeOfPurchase = timeOfPurchase;
        UserName = userName;
    }


    public int getFlightId() {
        return FlightId;
    }

    public String getDateOfPurchase() {
        return DateOfPurchase;
    }

    public String getTimeOfPurchase() {
        return TimeOfPurchase;
    }

    public String getUserName() {
        return UserName;
    }

    public void setUserName(String userName) {
        UserName = userName;
    }
}
