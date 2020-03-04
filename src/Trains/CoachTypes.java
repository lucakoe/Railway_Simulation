package Trains;

public enum CoachTypes {
    PASSENGERS(CoachTypes.PASSENGER_SPRITE), GOODS(CoachTypes.GOODS_SPRITE), SPECIAL(CoachTypes.SPECIAL_SPRITE);

    CoachTypes(String sprite) {
        this.SPRITE = sprite;
    }


    private final String SPRITE;
    //TODO edit all sprites, so they have the same amout of spaces in the empty parts in all sprites
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
}

