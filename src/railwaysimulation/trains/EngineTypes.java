package railwaysimulation.trains;

import railwaysimulation.exceptions.ConversionException;
//TODO be carefull bad refactor could have destroyed shit
public enum EngineTypes {
    ELECTRIC(EngineTypes.ELECTRIC_SPRITE, EngineTypes.ELECTRIC_STRING,EngineTypes.ELECTRIC_INFO_STRING), DIESEL(EngineTypes.DIESEL_SPRITE,EngineTypes.DIESEL_STRING,EngineTypes.DIESEL_INFO_STRING), STEAM(EngineTypes.STEAM_SPRITE, EngineTypes.STEAM_STRING,EngineTypes.STEAM_INFO_STRING);

    EngineTypes(String sprite, String stringRepresentation,String infoString) {
        this.SPRITE = sprite;
        this.STRING_REPRESENTATION = stringRepresentation;
        this.INFO_STRING=infoString;
    }


    private final String SPRITE;
    private final String INFO_STRING;

    private static final String ELECTRIC_SPRITE =
            "               ___    \n" +
                    "                 \\    \n" +
                    "  _______________/__  \n" +
                    " /_| ____________ |_\\ \n" +
                    "/   |____________|   \\\n" +
                    "\\                    /\n" +
                    " \\__________________/ \n" +
                    "  (O)(O)      (O)(O)  ";
    private static final String DIESEL_SPRITE =

                    "  _____________|____  \n" +
                    " /_| ____________ |_\\ \n" +
                    "/   |____________|   \\\n" +
                    "\\                    /\n" +
                    " \\__________________/ \n" +
                    "  (O)(O)      (O)(O)  ";
    private static final String STEAM_SPRITE =

                    "     ++      +------\n" +
                    "     ||      |+-+ | \n" +
                    "   /---------|| | | \n" +
                    "  + ========  +-+ | \n" +
                    " _|--/~\\------/~\\-+ \n" +
                    "//// \\_/      \\_/   ";

    public String getSprite() {
        return SPRITE;
    }


    @Override
    public String toString() {
        return this.STRING_REPRESENTATION;
    }

    public static EngineTypes parseEngineType(String parsableString) throws ConversionException {
        switch (parsableString){
            case EngineTypes.ELECTRIC_STRING:
                return ELECTRIC;

            case EngineTypes.DIESEL_STRING:
                return DIESEL;

            case EngineTypes.STEAM_STRING:
                return STEAM;

            default:
                throw new ConversionException();
        }
    }

    public String getInfoString() {
        return INFO_STRING;
    }

    private static final String ELECTRIC_INFO_STRING = "e";
    private static final String DIESEL_INFO_STRING = "d";
    private static final String STEAM_INFO_STRING = "s";

    private final String STRING_REPRESENTATION;
    private static final String ELECTRIC_STRING = "electrical";
    private static final String DIESEL_STRING = "diesel";
    private static final String STEAM_STRING = "steam";


}
