package Model;

public class PendingToSwapFlight {

    private int flightId;
    private String DBName;

    public PendingToSwapFlight(int flightId) {
        this.flightId = flightId;
    }

    public int getFlightId() {
        return flightId;
    }

}
