package railwaysimulation.trains;

import railwaysimulation.exceptions.ConversionException;

public enum CoachTypes {
    PASSENGERS(CoachTypes.PASSENGER_SPRITE, CoachTypes.PASSENGER_STRING,CoachTypes.PASSENGER_INFO_STRING),
    GOODS(CoachTypes.GOODS_SPRITE,CoachTypes.GOODS_STRING, CoachTypes.GOODS_INFO_STRING),
    SPECIAL(CoachTypes.SPECIAL_SPRITE,CoachTypes.SPECIAL_STRING, CoachTypes.SPECIAL_INFO_STRING);
    //TODO change goods to fright
    CoachTypes(String sprite,String stringRepresentation, String infoString) {
        this.SPRITE = sprite;
        this.STRING_REPRESENTATION = stringRepresentation;
        this.INFO_STRING = infoString;

    }


    private final String SPRITE;
    private final String STRING_REPRESENTATION;
    private final String INFO_STRING;
    //TODO edit all sprites, so they have the same amout of spaces in the empty parts in all sprites

    private static final String PASSENGER_INFO_STRING = "p";
    private static final String GOODS_INFO_STRING = "f";
    private static final String SPECIAL_INFO_STRING = "s";

    private static final String PASSENGER_STRING = "passenger";
    private static final String GOODS_STRING = "freight";
    private static final String SPECIAL_STRING = "special";



    private static final String ID_PREFIX = "W";


    private static final String PASSENGER_SPRITE =
            "____________________\n" +
                    "|  ___ ___ ___ ___ |\n" +
                    "|  |_| |_| |_| |_| |\n" +
                    "|__________________|\n" +
                    "|__________________|\n" +
                    "   (O)        (O)   ";
    private static final String GOODS_SPRITE =
            "|                  |\n" +
                    "|                  |\n" +
                    "|                  |\n" +
                    "|__________________|\n" +
                    "   (O)        (O)   ";
    private static final String SPECIAL_SPRITE =
            "               ____\n" +
                    "/--------------|  |\n" +
                    "\\--------------|  |\n" +
                    "  | |          |  |\n" +
                    " _|_|__________|  |\n" +
                    "|_________________|\n" +
                    "   (O)       (O)   ";

    public static CoachTypes parseCoachType(String parsableString) throws ConversionException {
        switch (parsableString){
            case CoachTypes.PASSENGER_STRING:
                return PASSENGERS;

            case CoachTypes.GOODS_STRING:
                return GOODS;

            case CoachTypes.SPECIAL_STRING:
                return SPECIAL;

            default:
                throw new ConversionException();
        }
    }

    public String getSprite() {
        return SPRITE;
    }

    public static String getIDPrefix() {
        return ID_PREFIX;
    }

    public String getInfoString() {
        return INFO_STRING;
    }

    @Override
    public String toString() {
        return STRING_REPRESENTATION;
    }
}

