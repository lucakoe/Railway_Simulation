package railwaysimulation.exceptions;

public class NotAvailableException extends Exception {
    public NotAvailableException(String thingThatsNotAvailable) {
        super(thingThatsNotAvailable+"is not available");
        THING_THATS_NOT_AVAILABLE=thingThatsNotAvailable;

    }
    private final String THING_THATS_NOT_AVAILABLE;

    public String getThingThatsNotAvailable() {
        return THING_THATS_NOT_AVAILABLE;
    }
}
