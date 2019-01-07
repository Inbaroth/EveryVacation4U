package Model;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class PurchaseFlightHandler {

    protected String DBName;
    protected int FlightId;
    protected String seller;
    protected String buyer;

    public PurchaseFlightHandler(String DBName) {
        this.DBName = DBName;
    }

    public PurchaseFlightHandler(int flightId, String seller, String buyer) {
        FlightId = flightId;
        this.seller = seller;
        this.buyer = buyer;
    }

    public String getDBName() {
        return DBName;
    }

    public int getFlightId() {
        return FlightId;
    }

    public String getSeller() {
        return seller;
    }

    public String getBuyer() {
        return buyer;
    }
}
