package Model;

public class AvailableFlight extends Flight {
    public AvailableFlight(int vacationId, String origin, String destination, int price, String destinationAirport, String dateOfDeparture, String dateOfArrival, String airlineCompany, int numOfTickets, String baggage, String ticketsType, String vacationStyle, String seller, int originalPrice) {
        super(vacationId, origin, destination, price, destinationAirport, dateOfDeparture, dateOfArrival, airlineCompany, numOfTickets, baggage, ticketsType, vacationStyle, seller, originalPrice);
    }

    public AvailableFlight(String origin, String destination, int price, String destinationAirport, String dateOfDeparture, String dateOfArrival, String airlineCompany, int numOfTickets, String baggage, String ticketsType, String vacationStyle, String seller, int originalPrice) {
        super(origin, destination, price, destinationAirport, dateOfDeparture, dateOfArrival, airlineCompany, numOfTickets, baggage, ticketsType, vacationStyle, seller, originalPrice);
    }

    public AvailableFlight(String origin, String destination, String dateOfDeparture, String dateOfArrival, int numOfTickets) {
        super(origin, destination, dateOfDeparture, dateOfArrival, numOfTickets);
    }
}
