package UI;

import java.util.regex.Pattern;
//TODO Complete Commands/Regex

public enum Commands {
    ADD_TRACK("add track " + Commands.POINT_PATTERN + " -> " + Commands.POINT_PATTERN),
    ADD_SWITCH("add switch " + Commands.POINT_PATTERN + " -> " + Commands.POINT_PATTERN + "," + Commands.POINT_PATTERN),
    DELETE_TRACK("delete track "+Commands.TRACK_ID_PATTERN),
    LIST_TRACKS("list tracks"),
    SET_SWITCH("set switch "+Commands.TRACK_ID_PATTERN+" position "+Commands.POINT_PATTERN),
    CREATE_ENGINE("create engine "+Commands.ENGINE_TYPE_PATTERN+" "+Commands.CLASS_PATTERN+" "+ Commands.NAME_PATTERN +" "+ Commands.LENGTH_PATTERN +" "+Commands.COUPLING_AVAILABILITY_PATTERN+" "+Commands.COORDINATE_PATTERN),
    LIST_ENGINES("list engines"),
    CREATE_COACH("create coach "+Commands.COACH_TYPE_PATTERN+" "+Commands.LENGTH_PATTERN+" "+Commands.COUPLING_AVAILABILITY_PATTERN+" "+Commands.COUPLING_AVAILABILITY_PATTERN),
    LIST_COACHS("list coaches"),
    CREATE_TRAIN_SET("create train-set "+Commands.CLASS_PATTERN+" "+Commands.NAME_PATTERN+" "+Commands.LENGTH_PATTERN+" "+Commands.COUPLING_AVAILABILITY_PATTERN+" "+Commands.COUPLING_AVAILABILITY_PATTERN),
    LIST_TRAIN_SETS("list train-sets"),
    DELETE_ROLLING_STOCK("delete rolling stock "+Commands.ID_PATTERN), //TODO Look which ID can be put in
    ADD_TRAIN("add train "+Commands.TRACK_ID_PATTERN+" "+ Commands.TRAIN_ID_PATTERN),
    DELETE_TRAIN("delete train "+Commands.ID_PATTERN),
    LIST_TRAINS("list trains"),
    SHOW_TRAINS("show train "+Commands.TRACK_ID_PATTERN),
    PUT_TRAINS("put train "+Commands.TRACK_ID_PATTERN+" at "+Commands.POINT_PATTERN+" in direction "+Commands.COORDINATE_PATTERN+","+Commands.COORDINATE_PATTERN),
    STEP("step "+Commands.SPEED_PATTERN),
    EXIT("exit");

    Commands(String pattern) {
        this.pattern = Pattern.compile(pattern);
    }

    private Pattern pattern;
    //TODO check if patterns are right
    private static final String COORDINATE_PATTERN = "[0-9]+"; // accept int
    private static final String POINT_PATTERN = "(" + COORDINATE_PATTERN + "," + COORDINATE_PATTERN + ")";
    //TODO Add Pattern (Regex (Zugname/Baureihe): [\\p{L}0-9]+ (Whatsapp))
    private static final String POSITIVE_INT_PATTERN="";
    private static final String BOOLEAN_PATTERN="";
    private static final String TRACK_ID_PATTERN = POSITIVE_INT_PATTERN; //accept positive int
    private static final String ENGINE_TYPE_PATTERN = "";
    private static final String CLASS_PATTERN = "";
    private static final String NAME_PATTERN = "";//accept String
    private static final String LENGTH_PATTERN = POSITIVE_INT_PATTERN; //accept positive int
    private static final String COUPLING_AVAILABILITY_PATTERN = BOOLEAN_PATTERN; // "true" or "false"
    private static final String COACH_TYPE_PATTERN = "";
    private static final String ID_PATTERN = ""; // //Coach ID: positive int,  TODO maybe lock for W in the beginning (used in delete rolling Stock and train)
    private static final String TRAIN_ID_PATTERN = "";
    private static final String SPEED_PATTERN = "";


}
