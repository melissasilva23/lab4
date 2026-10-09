import java.time.LocalTime;
import java.util.InputMismatchException;
import java.util.Scanner;

public class Main {

    private static final String DIRECT = "Direct";
    private static final String INDIRECT = "Indirect";

    private static final String INIT_MSG = "Insert the word 'HELP' to view commands.";
    private static final String HELP_MSG = "Commands: HELP, FLIGHT, AIRCRAFT, COMPANY, QUIT";
    private static final String DEPARTURE_MSG = "Enter departure time: " +
            "(must be two integers, one for hours, another for seconds)";
    private static final String COST_MSG = "Enter cost:";
    private static final String TYPE_MSG = "Enter flight type: (must be direct or indirect)";
    private static final String DESTINATION_DIRECT_MSG = "Enter destination airport:";
    private static final String DESTINATION_INDIRECT_MSG = "Enter transit airport %d:\n";
    private static final String AIRPORT_NUMBER_MSG = "Enter the number of stops:";
    private static final String ADDITIONAL_TAX_MSG = "Enter the additional tax " +
            "(relative value of the percentage, from 0 to 1):";
    private static final String AIRCRAFT_MODEL_MSG = "Enter aircraft model:";
    private static final String AIRCRAFT_FABRICANT_MSG = "Enter aircraft fabricant:";
    private static final String AIRCRAFT_CAPACITY_MSG = "Enter aircraft capacity:";
    private static final String COMPANY_NAME_MSG = "Enter company name:";
    private static final String COMPANY_PROFIT_RATE_MSG = "Enter company profit rate: " +
            "(relative value of the percentage, from 0 to 1):";
    private static final String FLIGHT_NUMBER_MSG = "Insert flight number:";
    private static final String INSERT_AIRPORT_MSG = "Insert airport for boarding:";
    private static final String IS_TRANSIT_AIRPORT_MSG = "Boarding will occur in the transit airport.";
    private static final String NOT_TRANSIT_AIRPORT_MSG = "Boarding will not occur in the transit airport.";
    private static final String SUCCESS_MSG = "Success.";
    private static final String EXIT_MSG = "Goodbye!";

    private static final String INPUT_ERR = "Incorrect input!";
    private static final String TYPE_ERR = "Type must be direct or indirect.";
    private static final String AIRCRAFT_COMPANY_ERR = "Aircraft or company doesn't exist!";
    private static final String AIRCRAFT_EXISTS_ERR = "Aircraft already exists!";
    private static final String COMPANY_EXISTS_ERR = "Company already exists!";
    private static final String FLIGHT_NONEXISTENT_ERR = "Flight does not exist!";
    private static final String FLIGHT_NOT_INDIRECT_ERR = "Flight is not indirect!";
    private static final String AIRPORT_OFF_TRACK_ERR = "Flight does not go through this airport!";
    private static final String AIRPORT_IS_DESTINATION_ERR = "Airport is the destination!";

    private static final String HELP = "HELP";
    private static final String FLIGHT = "FLIGHT";
    private static final String AIRCRAFT = "AIRCRAFT";
    private static final String COMPANY = "COMPANY";
    private static final String BOARDING = "BOARDING";
    private static final String QUIT = "QUIT";

    private static void processCommand(String cmd, AirportSystem sys, Scanner in) {
        switch (cmd) {
            case HELP -> System.out.println(HELP_MSG);
            case FLIGHT -> handleFlight(sys, in);
            case AIRCRAFT -> handleAircraft(sys, in);
            case COMPANY -> handleCompany(sys, in);
            case BOARDING -> handleBoarding(sys, in);
        }
    }

    private static void handleFlight(AirportSystem sys, Scanner in) {
        try {
            System.out.println(DEPARTURE_MSG);
            LocalTime departure = LocalTime.of(in.nextInt(), in.nextInt(), 0, 0);
            in.nextLine();
            System.out.println(COST_MSG);
            float cost = in.nextFloat();
            in.nextLine();
            System.out.println(TYPE_MSG);
            String type = in.nextLine();
            if (!type.equalsIgnoreCase(DIRECT) && !type.equalsIgnoreCase(INDIRECT)) {
                throw new InvalidTypeException(TYPE_ERR);
            }
            String directDestination = null;
            int numberOfStops = 0;
            float additionalTax = 0;
            if (type.equalsIgnoreCase(DIRECT)) {
                System.out.println(DESTINATION_DIRECT_MSG);
                directDestination = in.nextLine();
            }
            if (type.equalsIgnoreCase(INDIRECT)) {
                System.out.println(ADDITIONAL_TAX_MSG);
                additionalTax = in.nextFloat();
                in.nextLine();
                System.out.println(AIRPORT_NUMBER_MSG);
                numberOfStops = in.nextInt();
                in.nextLine();
            }
            String[] stops = new String[numberOfStops];
            if (type.equalsIgnoreCase(INDIRECT)) {
                for (int i = 0; i < numberOfStops; i++) {
                    if (i < numberOfStops - 1) {
                        System.out.printf(DESTINATION_INDIRECT_MSG, i+1);
                    } else System.out.println(DESTINATION_DIRECT_MSG);
                    stops[i] = in.nextLine();
                }
            }
            System.out.println(AIRCRAFT_MODEL_MSG);
            String aircraft = in.nextLine();
            System.out.println(COMPANY_NAME_MSG);
            String company = in.nextLine();
            if (sys.doesAircraftExist(aircraft) && sys.doesCompanyExist(company)) {
                sys.addFlight(departure, cost, type, aircraft, company,
                        directDestination, numberOfStops, stops, additionalTax);
                System.out.println(SUCCESS_MSG);
            } else System.out.println(AIRCRAFT_COMPANY_ERR);
        } catch (InputMismatchException e) {
            System.out.println(INPUT_ERR);
        }
    }

    private static void handleAircraft(AirportSystem sys, Scanner in) {
        System.out.println(AIRCRAFT_MODEL_MSG);
        String aircraft = in.nextLine();
        if (sys.doesAircraftExist(aircraft)) {
            System.out.println(AIRCRAFT_EXISTS_ERR);
        } else {
            System.out.println(AIRCRAFT_FABRICANT_MSG);
            String fabricant = in.nextLine();
            System.out.println(AIRCRAFT_CAPACITY_MSG);
            int capacity = in.nextInt();
            in.nextLine();
            sys.addAircraft(aircraft, fabricant, capacity);
            System.out.println(SUCCESS_MSG);
        }
    }

    private static void handleCompany(AirportSystem sys, Scanner in) {
        System.out.println(COMPANY_NAME_MSG);
        String company = in.nextLine();
        if (sys.doesCompanyExist(company)) {
            System.out.println(COMPANY_EXISTS_ERR);
        } else {
            System.out.println(COMPANY_PROFIT_RATE_MSG);
            float profitRate = in.nextFloat();
            in.nextLine();
            sys.addCompany(company, profitRate);
            System.out.println(SUCCESS_MSG);
        }
    }

    private static void handleBoarding(AirportSystem sys, Scanner in) {
        System.out.println(FLIGHT_NUMBER_MSG);
        int flightNumber = in.nextInt();
        in.nextLine();
        if (sys.flightDoesNotExist(flightNumber)) {
            System.out.println(FLIGHT_NONEXISTENT_ERR);
        } else if (sys.flightIsDirect(flightNumber)) {
            System.out.println(FLIGHT_NOT_INDIRECT_ERR);
        } else {
            System.out.println(INSERT_AIRPORT_MSG);
            String airport = in.nextLine();
            if (!sys.flightPassesThroughAirport(flightNumber, airport)) {
                System.out.println(AIRPORT_OFF_TRACK_ERR);
            } else if (sys.isAirportLastStop(flightNumber, airport)) {
                System.out.println(AIRPORT_IS_DESTINATION_ERR);
            } else if (sys.isBoardingTransitAirport(flightNumber, airport)) {
                System.out.println(IS_TRANSIT_AIRPORT_MSG);
            } else System.out.println(NOT_TRANSIT_AIRPORT_MSG);
        }
    }

    public static void main(String[] args) {
        System.out.println(INIT_MSG);
        Scanner in = new Scanner(System.in);
        String cmd = in.nextLine();
        AirportSystem sys = new AirportSystem();

        while (!cmd.equals(QUIT)) {
            processCommand(cmd, sys, in);
            cmd = in.nextLine();
        }
        System.out.println(EXIT_MSG);
    }
}