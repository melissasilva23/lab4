import java.time.LocalTime;

public class DirectFlight extends Flight {

    private String destination;

    public DirectFlight(int flightNumber, LocalTime departure, float cost, Aircraft aircraft,
                        Company company, String destination) {
        super(flightNumber, departure, cost, aircraft, company);
        this.destination = destination;
    }

}
