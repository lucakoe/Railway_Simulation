package railway_simulation.trains;

public enum EngineTypes {
    ELECTRIC(EngineTypes.ELECTRIC_SPRITE), DIESEL(EngineTypes.DIESEL_SPRITE), STEAM(EngineTypes.STEAM_SPRITE);

    EngineTypes(String sprite) {
        this.SPRITE = sprite;
    }


    private final String SPRITE;
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

    @Override
    public String toString() {
        return SPRITE;
    }
}
