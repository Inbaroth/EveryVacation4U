package Model;

public class Flight {
    //here or at model  or both(?)
   // private static int flightId = 0;
    private int flightId;
    private String origin;
    private String destination;
    private int price;
    private String destinationAirport;
    private String dateOfDeparture;
    private String dateOfArrival;
    private String airlineCompany;
    private int numOfTickets;
    //None/Only hand bag/Up to 8 kg/Up to 23 kg/ Up to 31 kg/ More than 31 kg
    private String baggage;
    //adult,child,baby
    private String ticketsType;
    //Urban/ Exotic/ Natures/ Multi
    private String vacationStyle;
    private String seller;
    private int originalPrice;

    public Flight(int flightId, String origin, String destination, int price, String destinationAirport, String dateOfDeparture, String dateOfArrival, String airlineCompany, int numOfTickets, String baggage, String ticketsType, String vacationStyle, String seller, int originalPrice) {
        this.flightId = flightId;
        this.origin = origin;
        this.destination = destination;
        this.price = price;
        this.destinationAirport = destinationAirport;
        this.dateOfDeparture = dateOfDeparture;
        this.dateOfArrival = dateOfArrival;
        this.airlineCompany = airlineCompany;
        this.numOfTickets = numOfTickets;
        this.baggage = baggage;
        this.ticketsType = ticketsType;
        this.vacationStyle = vacationStyle;
        this.seller=seller;
        this.originalPrice =originalPrice;

    }


    public Flight(String origin, String destination, int price, String destinationAirport, String dateOfDeparture, String dateOfArrival, String airlineCompany, int numOfTickets, String baggage, String ticketsType, String vacationStyle, String seller, int originalPrice) {
        this.flightId =0;
        this.origin = origin;
        this.destination = destination;
        this.price = price;
        this.destinationAirport = destinationAirport;
        this.dateOfDeparture = dateOfDeparture;
        this.dateOfArrival = dateOfArrival;
        this.airlineCompany = airlineCompany;
        this.numOfTickets = numOfTickets;
        this.baggage = baggage;
        this.ticketsType = ticketsType;
        this.vacationStyle = vacationStyle;
        this.seller=seller;
        this.originalPrice =originalPrice;


    }

    public Flight(String origin, String destination, String dateOfDeparture, String dateOfArrival, int numOfTickets) {
        this.origin = origin;
        this.destination = destination;
        this.dateOfDeparture = dateOfDeparture;
        this.dateOfArrival = dateOfArrival;
        this.numOfTickets = numOfTickets;
    }

    public int getFlightId() {
        return flightId;
    }

    public String getOrigin() {
        return origin;
    }

    public String getDestination() {
        return destination;
    }

    public int getPrice() {
        return price;
    }

    public String getDestinationAirport() {
        return destinationAirport;
    }

    public String getDateOfDeparture() {
        return dateOfDeparture;
    }

    public String getDateOfArrival() {
        return dateOfArrival;
    }

    public String getAirlineCompany() {
        return airlineCompany;
    }

    public int getNumOfTickets() {
        return numOfTickets;
    }

    public String getBaggage() {
        return baggage;
    }

    public String getTicketsType() {
        return ticketsType;
    }

    public String getVacationStyle() {
        return vacationStyle;
    }

    public String getSeller() {
        return seller;
    }

    public int getOriginalPrice() { return originalPrice; }

    public void setSeller(String seller) {
        this.seller = seller;
    }

    public void setFlightId(int flightId) {
        this.flightId = flightId;
    }
}
