package railway_simulation.ui;

import railway_simulation.exceptions.WrongInputException;
import railway_simulation.railway.RailwaySimulation;

import java.util.regex.Matcher;
import java.util.regex.Pattern;
//TODO Complete Commands/Regex

public enum Commands {
    ADD_TRACK(Commands.ADD_TRACK_PATTERN),
    ADD_SWITCH(Commands.ADD_SWITCH_PATTERN),
    DELETE_TRACK(Commands.DELETE_TRACK_PATTERN),
    LIST_TRACKS(Commands.LIST_TRACKS_PATTERN),
    SET_SWITCH(Commands.SET_SWITCH_PATTERN),
    CREATE_ENGINE(Commands.CREATE_ENGINE_PATTERN),
    LIST_ENGINES(Commands.LIST_ENGINES_PATTERN)
            /*
            {

        @Override
        public void execute(Matcher matcher, RailwaySimulation simulation) {
            String out = simulation.listEngines();
        }
    }

             */
    ,

    CREATE_COACH(Commands.CREATE_COACH_PATTERN),
    LIST_COACHES(Commands.LIST_COACHSE_PATTERN),
    CREATE_TRAIN_SET(Commands.CREATE_TRAIN_SET_PATTERN),
    LIST_TRAIN_SETS(Commands.LIST_TRAIN_SETS_PATTERN),
    DELETE_ROLLING_STOCK(Commands.DELETE_ROLLING_STOCK_PATTERN), //TODO Look which ID can be put in
    ADD_TRAIN(Commands.ADD_TRAIN_PATTERN),
    DELETE_TRAIN(Commands.DELETE_TRAIN_PATTERN),
    LIST_TRAINS(Commands.LIST_TRAINS_PATTERN),
    SHOW_TRAINS(Commands.SHOW_TRAIN_PATTERNS),
    PUT_TRAINS(Commands.PUT_TRAINS_PATTERN),
    STEP(Commands.STEP_PATTERN),
    EXIT(Commands.EXIT_PATTERN);

    Commands(String pattern) {
        this.pattern = Pattern.compile(pattern);
    }
    //TODO Add execute to every command
    /*
    public static Commands executeUserInputs(String userInputs, RailwaySimulation simulation) throws WrongInputException {
        for (Commands command : Commands.values()) {
            Matcher matcher = command.pattern.matcher(userInputs);
            if (matcher.matches()) {
                command.execute(matcher, simulation);
                return command;
            }
        }
        throw new WrongInputException(INVALID_COMMAND_EXCPETION_MESSAGE);
    }

    public abstract void execute(Matcher matcher, RailwaySimulation simulation);
    */
    private static final String INVALID_COMMAND_EXCPETION_MESSAGE = "invalid command";


    private Pattern pattern;
    //TODO check if patterns are right
    //Pattern building Blocks for Command Pattern
    private static final String COORDINATE_PATTERN = "[0-9]+"; // accept int
    private static final String POINT_PATTERN = "(" + COORDINATE_PATTERN + "," + COORDINATE_PATTERN + ")";
    //TODO Add Pattern (Regex (Zugname/Baureihe): [\\p{L}0-9]+ (Whatsapp))
    private static final String POSITIVE_INT_PATTERN_WITHOUT_ZERO = "";
    private static final String BOOLEAN_PATTERN = "";
    private static final String TRACK_ID_PATTERN = POSITIVE_INT_PATTERN_WITHOUT_ZERO; //accept positive int
    private static final String ENGINE_TYPE_PATTERN = "";
    private static final String CLASS_PATTERN = "";
    private static final String NAME_PATTERN = "";//accept String
    private static final String LENGTH_PATTERN = POSITIVE_INT_PATTERN_WITHOUT_ZERO; //accept positive int
    private static final String COUPLING_AVAILABILITY_PATTERN = BOOLEAN_PATTERN; // "true" or "false"
    private static final String COACH_TYPE_PATTERN = "";
    private static final String ROLLING_STOCK_ID_PATTERN = ""; // //Coach ID: positive int,  TODO maybe lock for W in the beginning (used in delete rolling Stock and train)
    private static final String TRAIN_ID_PATTERN = "";
    private static final String SPEED_PATTERN = "";

    //Command Patterns
    private static final String ADD_TRACK_PATTERN = "add track " +
            Commands.POINT_PATTERN +
            " -> " +
            Commands.POINT_PATTERN;
    private static final String ADD_SWITCH_PATTERN = "add switch " +
            Commands.POINT_PATTERN +
            " -> " +
            Commands.POINT_PATTERN +
            "," +
            Commands.POINT_PATTERN;
    private static final String DELETE_TRACK_PATTERN = "delete track " +
            Commands.TRACK_ID_PATTERN;
    private static final String LIST_TRACKS_PATTERN = "list tracks";
    private static final String SET_SWITCH_PATTERN = "set switch " +
            Commands.TRACK_ID_PATTERN +
            " position " +
            Commands.POINT_PATTERN;
    private static final String CREATE_ENGINE_PATTERN = "create engine " +
            Commands.ENGINE_TYPE_PATTERN +
            " " +
            Commands.CLASS_PATTERN +
            " " +
            Commands.NAME_PATTERN +
            " " +
            Commands.LENGTH_PATTERN +
            " " +
            Commands.COUPLING_AVAILABILITY_PATTERN +
            " " +
            Commands.COUPLING_AVAILABILITY_PATTERN;
    private static final String LIST_ENGINES_PATTERN = "list engines";
    private static final String CREATE_COACH_PATTERN = "create coach " +
            Commands.COACH_TYPE_PATTERN +
            " " +
            Commands.LENGTH_PATTERN +
            " " +
            Commands.COUPLING_AVAILABILITY_PATTERN +
            " " +
            Commands.COUPLING_AVAILABILITY_PATTERN;
    private static final String LIST_COACHSE_PATTERN = "list coaches";
    private static final String CREATE_TRAIN_SET_PATTERN = "create train-set " +
            Commands.CLASS_PATTERN +
            " " +
            Commands.NAME_PATTERN +
            " " +
            Commands.LENGTH_PATTERN +
            " " +
            Commands.COUPLING_AVAILABILITY_PATTERN +
            " " +
            Commands.COUPLING_AVAILABILITY_PATTERN;
    private static final String LIST_TRAIN_SETS_PATTERN = "list train-sets";
    private static final String DELETE_ROLLING_STOCK_PATTERN = "delete rolling stock " +
            Commands.ROLLING_STOCK_ID_PATTERN; //TODO Look which ID can be put in
    private static final String ADD_TRAIN_PATTERN = "add train " +
            Commands.TRAIN_ID_PATTERN +
            " " +
            Commands.ROLLING_STOCK_ID_PATTERN;
    private static final String DELETE_TRAIN_PATTERN = "delete train " +
            Commands.TRAIN_ID_PATTERN;
    private static final String LIST_TRAINS_PATTERN = "list trains";
    private static final String SHOW_TRAIN_PATTERNS = "show train " +
            Commands.TRAIN_ID_PATTERN;
    private static final String PUT_TRAINS_PATTERN = "put train " +
            Commands.TRAIN_ID_PATTERN +
            " at " +
            Commands.POINT_PATTERN +
            " in direction " +
            Commands.COORDINATE_PATTERN +
            "," +
            Commands.COORDINATE_PATTERN;
    private static final String STEP_PATTERN = "step " +
            Commands.SPEED_PATTERN;
    private static final String EXIT_PATTERN = "exit";


}
