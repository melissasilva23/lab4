import java.time.LocalTime;

public class IndirectFlight extends Flight {

    private int numberOfStops;
    private float additionalTax;
    private String[] stops;

    public IndirectFlight(int flightNumber, LocalTime departure, float cost, Aircraft aircraft, Company company,
                          int numberOfStops, String[] stops, float additionalTax) {
        super(flightNumber, departure, cost, aircraft, company);
        this.numberOfStops = numberOfStops;
        this.stops = stops;
        this.additionalTax = additionalTax;
    }

    public boolean isLastStop(String airport) {
        return stops[numberOfStops - 1].equals(airport);
    }

    public boolean isBoardingTransitAirport(String airport) {
        for (int i = 0; i < numberOfStops - 1; i++) {
            if (airport.equals(stops[i])) {
                return true;
            }
        }
        return false;
    }

    public boolean passesThroughAirport(String airport) {
        return isBoardingTransitAirport(airport) || isLastStop(airport);
    }
}
