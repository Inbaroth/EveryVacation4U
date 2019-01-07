package Database;

import Model.Flight;

import java.sql.*;
import java.util.ArrayList;

public class DB {

    protected String DBName;

    public DB(String DBName) {

        this.DBName = DBName;
        createAllFlightsTable();
        createAvailableFlightsTable();
        createConfirmedSaleFlightsTable();
        createOfferedToSwapFlightsTable();
        createPendingFlightsTable();
        createPendingSwapFlightsTable();
        createPurchasedFlightsTable();
        createSwappedFlightsTable();
        createUsersTable();
    }

    /**
     * This method create if doesn't exist a new database by the name which equal to the databaseName field.
     */
    public void connect(String databaseName) {
        Connection connection = null;

        try {
            Class.forName("org.sqlite.JDBC");
            connection = DriverManager.getConnection("jdbc:sqlite:" + databaseName + ".db");
            connection.close();

        } catch ( Exception e ) {
            System.err.println( e.getClass().getName() + ": " + e.getMessage() );
            System.exit(0);
        }
    }

    /**
     * Create a new table in the test database
     *
     */
    public void createAllFlightsTable() {
        // SQLite connection string
        String url ="jdbc:sqlite:" + DBName + ".db";
        // SQL statement for creating a new table
        String createStatement = "CREATE TABLE IF NOT EXISTS AllFlights (\n"
                + "	FlightId integer PRIMARY KEY,\n"
                + "	Origin text NOT NULL,\n"
                + "	Destination text NOT NULL,\n"
                + "	Price integer NOT NULL,\n"
                + "	DestinationAirport text NOT NULL,\n"
                + "	DateOfDeparture text NOT NULL,\n"
                + "	DateOfArrival text NOT NULL,\n"
                + " AirlineCompany text NOT NULL,\n"
                + " NumberOfTickets integer NOT NULL,\n"
                + " Baggage text NOT NULL,\n"
                + " TicketsType text NOT NULL,\n"
                + " VacationStyle text NOT NULL,\n"
                + " SellerUserName text NOT NULL,\n"
                + " OriginalPrice integer NOT NULL\n"
                + ");";
        try (Connection conn = DriverManager.getConnection(url);
             Statement stmt = conn.createStatement()) {
            // create a new table
            stmt.execute(createStatement);
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    /**
     * Create a new table in the test database
     *
     */
    public void createAvailableFlightsTable() {
        // SQLite connection string
        String url ="jdbc:sqlite:" + DBName + ".db";
        // SQL statement for creating a new table
        String createStatement = "CREATE TABLE IF NOT EXISTS AvailableFlights (\n"
                + "	FlightId integer PRIMARY KEY,\n"
                + "	Origin text NOT NULL,\n"
                + "	Destination text NOT NULL,\n"
                + "	Price integer NOT NULL,\n"
                + "	DestinationAirport text NOT NULL,\n"
                + "	DateOfDeparture text NOT NULL,\n"
                + "	DateOfArrival text NOT NULL,\n"
                + " AirlineCompany text NOT NULL,\n"
                + " NumberOfTickets integer NOT NULL,\n"
                + " Baggage text NOT NULL,\n"
                + " TicketsType text NOT NULL,\n"
                + " VacationStyle text NOT NULL,\n"
                + " SellerUserName text NOT NULL,\n"
                + " OriginalPrice integer NOT NULL\n"
                + ");";
        try (Connection conn = DriverManager.getConnection(url);
             Statement stmt = conn.createStatement()) {
            // create a new table
            stmt.execute(createStatement);
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    /**
     * Create a new table in the test database
     *
     */
    public void createConfirmedSaleFlightsTable() {
        // SQLite connection string
        String url ="jdbc:sqlite:" + DBName + ".db";
        // SQL statement for creating a new table
        String createStatement = "CREATE TABLE IF NOT EXISTS ConfirmedSaleFlights (\n"
                + "	FlightId integer PRIMARY KEY,\n"
                + "	sellerUserName text NOT NULL,\n"
                + "	buyerUserName text NOT NULL \n"
                + ");";
        try (Connection conn = DriverManager.getConnection(url);
             Statement stmt = conn.createStatement()) {
            // create a new table
            stmt.execute(createStatement);
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    /**
     * Create a new table 'OfferedToSwapFlights' in Db
     */
    public void createOfferedToSwapFlightsTable() {
        // SQLite connection string
        String url ="jdbc:sqlite:" + DBName + ".db";
        // SQL statement for creating a new table
        String createStatement = "CREATE TABLE IF NOT EXISTS OfferedToSwapFlights (\n"
                + " FlightIdPending integer not null ,\n"
                + "	FlightIdChosen integer not null ,\n"
                + "PRIMARY KEY (FlightIdChosen,FlightIdChosen)"
                + ");";

        try (Connection conn = DriverManager.getConnection(url);
             Statement stmt = conn.createStatement()) {
            // create a new table
            stmt.execute(createStatement);
        } catch (SQLException e) {
            System.out.println(e.getMessage());

        }
    }

    /**
     * Create a new table in the test database
     *
     */
    public void createPendingFlightsTable() {
        // SQLite connection string
        String url ="jdbc:sqlite:" + DBName + ".db";
        // SQL statement for creating a new table
        String createStatement = "CREATE TABLE IF NOT EXISTS PendingFlights (\n"
                + "FlightId integer PRIMARY KEY,\n"
                + "	sellerUserName text NOT NULL,\n"
                + " buyerUserName text NOT NULL\n"
                + ");";


        try (Connection conn = DriverManager.getConnection(url);
             Statement stmt = conn.createStatement()) {
            // create a new table
            stmt.execute(createStatement);
        } catch (SQLException e) {
            System.out.println(e.getMessage());

        }
    }

    public void createPendingSwapFlightsTable() {
        // SQLite connection string
        String url ="jdbc:sqlite:" + DBName + ".db";
        // SQL statement for creating a new table
        String createStatement = "CREATE TABLE IF NOT EXISTS PendingToSwapFlights (\n"
                + " FlightId integer PRIMARY KEY\n"
                + ");";

        try (Connection conn = DriverManager.getConnection(url);
             Statement stmt = conn.createStatement()) {
            // create a new table
            stmt.execute(createStatement);
        } catch (SQLException e) {
            System.out.println(e.getMessage());

        }
    }

    /**
     * Create a new table in the test database
     *
     */
    public void createPurchasedFlightsTable() {
        // SQLite connection string
        String url ="jdbc:sqlite:" + DBName + ".db";
        // SQL statement for creating a new table
        String createStatement = "CREATE TABLE IF NOT EXISTS PurchasedFlights (\n"
                + "	FlightId integer PRIMARY KEY,\n"
                + "	DateOfPurchase text NOT NULL,\n"
                + " TimeOfPurchase text NOT NULL,\n"
                + " UserName text NOT NULL \n"
                + ");";
        try (Connection conn = DriverManager.getConnection(url);
             Statement stmt = conn.createStatement()) {
            // create a new table
            stmt.execute(createStatement);
        } catch (SQLException e) {
            System.out.println(e.getMessage());

        }
    }

    public void createSwappedFlightsTable() {
        // SQLite connection string
        String url ="jdbc:sqlite:" + DBName + ".db";
        // SQL statement for creating a new table
        String createStatement = "CREATE TABLE IF NOT EXISTS SwappedFlights (\n"
                + "	FlightId1 integer NOT NULL ,\n"
                + "	FlightId2 integer NOT NULL ,\n"
                + "PRIMARY KEY (FlightId1,FlightId2)"
                + ");";
        try (Connection conn = DriverManager.getConnection(url);
             Statement stmt = conn.createStatement()) {
            // create a new table
            stmt.execute(createStatement);
        } catch (SQLException e) {
            System.out.println(e.getMessage());

        }
    }

    /**
     * This method create a new table in the data base by the name tableName
     */
    public void createUsersTable(){
        String createStatement = "CREATE TABLE IF NOT EXISTS Users (\n"
                + "	user_name text PRIMARY KEY,\n"
                + "	password text NOT NULL,\n"
                + " first_name text NOT NULL,\n"
                + " last_name text NOT NULL,\n"
                + "	birthday text,\n"
                + "	address text,\n"
                + "	email text,\n"
                + " profilePicture text NOT NULL\n"
                + ");";

        String url = "jdbc:sqlite:" + DBName + ".db";
        try (Connection conn = DriverManager.getConnection(url);
             Statement stmt = conn.createStatement()) {
            // create a new table
            stmt.execute(createStatement);
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }

    }

    public ArrayList<Flight> searchFlight(String url, String query){
        ArrayList<Flight> flights = new ArrayList<Flight>();
        try (Connection conn = DriverManager.getConnection(url);
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(query)) {

            // loop through the result set
            while (rs.next()) {
                //creating flights objects only so we could display them, there are not going to be a real availableVacation obects
                Flight flight = new Flight(rs.getInt(1), rs.getString(2), rs.getString(3), rs.getInt(4), rs.getString(5), rs.getString(6), rs.getString(7), rs.getString(8), rs.getInt(9), rs.getString(10), rs.getString(11), rs.getString(12), rs.getString(13), rs.getInt(14));
                flights.add(flight);
                flights = null;
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return flights;
    }
}
