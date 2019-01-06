package Model;

public class SwappedFlight {

    private int flightId1;
    private int flightId2;


    public SwappedFlight(int flightId1, int flightId2) {
        this.flightId1 = flightId1;
        this.flightId2 = flightId2;
    }

    public int getFlightId1() {
        return flightId1;
    }

    public int getFlightId2() {
        return flightId2;
    }
}
