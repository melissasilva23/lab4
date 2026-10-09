import java.time.LocalTime;

public class AirportSystem {

    private Flight[] flights;
    private Aircraft[] aircrafts;
    private Company[] companies;
    private int flightCount;
    private int aircraftCount;
    private int companyCount;
    private int flightCapacity;
    private int aircraftCapacity;
    private int companyCapacity;

    public AirportSystem() {
        flightCount = 0;
        aircraftCount = 0;
        companyCount = 0;
        flightCapacity = 100;
        aircraftCapacity = 100;
        companyCapacity = 100;
        flights = new Flight[flightCapacity];
        aircrafts = new Aircraft[aircraftCapacity];
        companies = new Company[companyCapacity];

    }

    public void addFlight(LocalTime departure, float cost, String type,
                          String aircraftModel, String company, String directDestination,
                          int numberOfStops, String[] stops, float additionalTax) {
        if (flightCount == flightCapacity) {
            flights = resizeFlights(flightCapacity * 5);
        }
        Aircraft a = getAircraft(aircraftModel);
        Company c = getCompany(company);
        if (type.equalsIgnoreCase("DIRECT")) {
            cost *= (1 + getCompany(company).getProfitRate());
            flights[flightCount] = new DirectFlight(flightCount, departure, cost, a, c, directDestination);
        } else {
            cost *= (1 + getCompany(company).getProfitRate());
            cost *= (1 + additionalTax);
            flights[flightCount] = new IndirectFlight(flightCount, departure, cost, a, c,
                    numberOfStops, stops, additionalTax);
        }
        flightCount++;
    }

    public void addAircraft(String model, String fabricant, int capacity) {
        if (aircraftCount == aircraftCapacity) {
            aircrafts = resizeAircrafts(aircraftCapacity * 5);
        }
        aircrafts[aircraftCount] = new Aircraft(model, fabricant, capacity);
        aircraftCount++;
    }

    public void addCompany(String name, float profitRate) {
        if (companyCount == companyCapacity) {
            companies = resizeCompanies(companyCapacity * 5);
        }
        companies[companyCount] = new Company(name, profitRate);
        companyCount++;
    }

    public boolean doesAircraftExist(String model) {
        for (int i = 0; i < aircraftCount; i++) {
            if (aircrafts[i].getModel().equals(model)) { return true; }
        }
        return false;
    }

    public boolean doesCompanyExist(String name) {
        for (int i = 0; i < companyCount; i++) {
            if (companies[i].getName().equals(name)) { return true; }
        }
        return false;
    }

    public boolean flightDoesNotExist(int flightNumber) {
        return flightNumber >= flightCount;
    }

    public boolean flightIsDirect(int flightNumber) {
        return flights[flightNumber] instanceof DirectFlight;
    }

    public boolean flightPassesThroughAirport(int flightNumber, String airport) {
        return ((IndirectFlight) flights[flightNumber]).passesThroughAirport(airport);
    }

    public boolean isAirportLastStop(int flightNumber, String airport) {
        return ((IndirectFlight) flights[flightNumber]).isLastStop(airport);
    }

    public boolean isBoardingTransitAirport(int flightNumber, String airport) {
        return ((IndirectFlight) flights[flightNumber]).isBoardingTransitAirport(airport);
    }

    private Aircraft getAircraft(String model) {
        for (int i = 0; i < aircraftCount; i++) {
            if (aircrafts[i].getModel().equals(model)) { return aircrafts[i]; }
        }
        return null;
    }

    private Company getCompany(String name) {
        for (int i = 0; i < companyCount; i++) {
            if (companies[i].getName().equals(name)) { return companies[i]; }
        }
        return null;
    }

    private Flight[] resizeFlights(int newSize) {
        Flight[] biggerFlights = new Flight[newSize];
        for (int i = 0; i < flightCount; i++) {
            biggerFlights[i] = flights[i];
        }
        flightCapacity = newSize;
        return biggerFlights;
    }

    private Aircraft[] resizeAircrafts(int newSize) {
        Aircraft[] biggerAircrafts = new Aircraft[newSize];
        for (int i = 0; i < aircraftCount; i++) {
            biggerAircrafts[i] = aircrafts[i];
        }
        aircraftCapacity = newSize;
        return biggerAircrafts;
    }

    private Company[] resizeCompanies(int newSize) {
        Company[] biggerCompanies = new Company[newSize];
        for (int i = 0; i < aircraftCount; i++) {
            biggerCompanies[i] = companies[i];
        }
        companyCapacity = newSize;
        return biggerCompanies;
    }
}
