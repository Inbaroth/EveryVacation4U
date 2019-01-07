package Model;

import Model.PurchaseFlightHandler;

import java.sql.*;
import java.util.ArrayList;

public class ConfirmedFlight extends PurchaseFlightHandler {


    public ConfirmedFlight(String DBName) {
        super(DBName);
    }

    public ConfirmedFlight(int FlightId,String seller,String buyer) {
        super( FlightId, seller, buyer);
    }





}
