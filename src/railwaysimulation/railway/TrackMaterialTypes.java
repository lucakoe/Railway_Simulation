package railwaysimulation.railway;

public enum TrackMaterialTypes {
    TRACK(TrackMaterialTypes.TRACK_NUMBER_OF_ENDPOINTS, TrackMaterialTypes.SYMBOL_FOR_TRACK), SWITCH(TrackMaterialTypes.SWITCH_NUMBER_OF_ENDPOINTS, TrackMaterialTypes.SYMBOL_FOR_SWITCH);

    TrackMaterialTypes(int numberOfEndPoints, String symbolForTrackMaterialType) {
        SYMBOL = symbolForTrackMaterialType;
        NUMBER_OF_ENDPOINTS=numberOfEndPoints;
    }

    private final String SYMBOL;
    private final int NUMBER_OF_ENDPOINTS;
    private static final String SYMBOL_FOR_TRACK = "t";
    private static final String SYMBOL_FOR_SWITCH = "s";
    private static final String NO_TRACKS_EXIST_MESSAGE = "No track exists";
    private static final int SWITCH_NUMBER_OF_ENDPOINTS = 2;
    private static final int TRACK_NUMBER_OF_ENDPOINTS = 1;



    public static String getNoTracksExistMessage() {
        return NO_TRACKS_EXIST_MESSAGE;
    }


    @Override
    public String toString() {
        return this.SYMBOL;
    }

    public int getNumberOfEndPoints() {
        return NUMBER_OF_ENDPOINTS;
    }


}
