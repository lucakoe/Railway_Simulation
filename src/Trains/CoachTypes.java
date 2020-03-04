package Trains;

public enum CoachTypes {
    PASSENGERS(CoachTypes.PASSENGER_SPRITE, CoachTypes.PASSENGER_INFO_STRING),
    GOODS(CoachTypes.GOODS_SPRITE, CoachTypes.GOODS_INFO_STRING),
    SPECIAL(CoachTypes.SPECIAL_SPRITE, CoachTypes.SPECIAL_INFO_STRING);

    CoachTypes(String sprite, String infoString) {
        this.SPRITE = sprite;
        this.INFO_STRING = infoString;

    }


    private final String SPRITE;
    private final String INFO_STRING;
    //TODO edit all sprites, so they have the same amout of spaces in the empty parts in all sprites

    private static final String PASSENGER_INFO_STRING = "p";
    private static final String GOODS_INFO_STRING = "f";
    private static final String SPECIAL_INFO_STRING = "s";



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

    @Override
    public String toString() {
        return SPRITE;
    }

    public static String getIDPrefix() {
        return ID_PREFIX;
    }

    public String getInfoString() {
        return INFO_STRING;
    }
}

