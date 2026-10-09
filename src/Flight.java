import java.time.LocalTime;

public abstract class Flight {

    private int flightNumber;
    private LocalTime departure;
    private float cost;
    private Aircraft aircraft;
    private Company company;

    public Flight(int flightNumber, LocalTime departure, float cost, Aircraft aircraft, Company company) {
        this.flightNumber = flightNumber;
        this.departure = departure;
        this.cost = cost;
        this.aircraft = aircraft;
        this.company = company;
    }
}
