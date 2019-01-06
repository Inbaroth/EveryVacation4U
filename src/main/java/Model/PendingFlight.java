package Model;

import Model.PurchaseFlightHandler;

import java.sql.*;
import java.util.ArrayList;

public class PendingFlight extends PurchaseFlightHandler {


    public PendingFlight(String databaseName) {
        super(databaseName);
    }

    public PendingFlight(int flightId, String seller, String buyer) {
        super(flightId, seller, buyer);
    }
}
