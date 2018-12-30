package Model;

public class OfferedToSwapFlight {

    private int FlightIdPending;
    private int FlightIdOffered;

    public OfferedToSwapFlight(int flightIdPending, int flightIdOffered) {
        FlightIdPending = flightIdPending;
        FlightIdOffered = flightIdOffered;
    }

    public int getFlightIdPending() {
        return FlightIdPending;
    }

    public int getFlightIdOffered() {
        return FlightIdOffered;
    }
}
