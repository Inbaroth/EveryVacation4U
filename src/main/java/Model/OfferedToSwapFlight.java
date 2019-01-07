package Model;

public class OfferedToSwapFlight {

    private int FlightIdPending;
    private int FlightIdChosen;

    public OfferedToSwapFlight(int flightIdPending, int flightIdOffered) {
        FlightIdPending = flightIdPending;
        FlightIdChosen = flightIdOffered;
    }

    public int getFlightIdPending() {
        return FlightIdPending;
    }

    public int getFlightIdOffered() {
        return FlightIdChosen;
    }
}
