package railwaysimulation.ui;

import edu.kit.informatik.Terminal;
import exceptions.CouplingConfigurationInvalidException;
import exceptions.NotAllSwitchesSetException;
import exceptions.TrainCanNotBePlacedException;
import railwaysimulation.exceptions.*;
import railwaysimulation.railway.Coordinate;
import railwaysimulation.railway.RailwaySimulation;

import railwaysimulation.trains.CoachTypes;
import railwaysimulation.trains.EngineTypes;

import java.util.regex.Matcher;
import java.util.regex.Pattern;
//TODO Complete Commands/Regex

public enum Commands {

    ADD_TRACK(Commands.ADD_TRACK_PATTERN) {
        @Override
        public void execute(Matcher matcher, RailwaySimulation simulation) throws WrongInputException {
            Coordinate startCoordinate;
            Coordinate endCoordinate;
            try {
                startCoordinate = new Coordinate(Integer.parseInt(matcher.group(1)), Integer.parseInt(matcher.group(2)));
                endCoordinate = new Coordinate(Integer.parseInt(matcher.group(3)), Integer.parseInt(matcher.group(4)));

            } catch (NumberFormatException e) {
                throw new WrongInputException(WRONG_PARAMETERS_MESSAGE);
            }

            //TODO add try catch
            try {
                Terminal.printLine(simulation.addTrack(startCoordinate, endCoordinate));
            } catch (TrackMaterialCanNotBeAddedOrRemovedException e) {
                throw new WrongInputException(e.getMessage());
            }

        }
    },
    ADD_SWITCH(Commands.ADD_SWITCH_PATTERN) {
        @Override
        public void execute(Matcher matcher, RailwaySimulation simulation) throws WrongInputException {
            Coordinate startCoordinate;
            Coordinate endCoordinate1;
            Coordinate endCoordinate2;
            try {
                startCoordinate = new Coordinate(Integer.parseInt(matcher.group(1)), Integer.parseInt(matcher.group(2)));
                endCoordinate1 = new Coordinate(Integer.parseInt(matcher.group(3)), Integer.parseInt(matcher.group(4)));
                endCoordinate2 = new Coordinate(Integer.parseInt(matcher.group(5)), Integer.parseInt(matcher.group(6)));
            } catch (NumberFormatException e) {
                throw new WrongInputException(WRONG_PARAMETERS_MESSAGE);
            }


            try {
                Terminal.printLine(simulation.addSwitch(startCoordinate, endCoordinate1, endCoordinate2));
            } catch (TrackIsOccupiedException | TrackMaterialCanNotBeAddedOrRemovedException e) {
                throw new WrongInputException(e.getMessage());
            }

        }
    },
    DELETE_TRACK(Commands.DELETE_TRACK_PATTERN) {
        @Override
        public void execute(Matcher matcher, RailwaySimulation simulation) throws WrongInputException {
            int trackID;
            try {
                trackID = Integer.parseInt(matcher.group(1));
            } catch (NumberFormatException e) {
                throw new WrongInputException(WRONG_PARAMETERS_MESSAGE);
            }

            //TODO add try catch
            try {
                simulation.deleteTrack(trackID);
            } catch (TrackIsOccupiedException | TrackMaterialCanNotBeAddedOrRemovedException e) {
                throw new WrongInputException(e.getMessage());
            }
            Terminal.printLine(POSITIVE_OUTCOME_MESSAGE);

        }
    },
    LIST_TRACKS(Commands.LIST_TRACKS_PATTERN) {
        @Override
        public void execute(Matcher matcher, RailwaySimulation simulation) {
            Terminal.printLine(simulation.listTracks());
        }
    },
    SET_SWITCH(Commands.SET_SWITCH_PATTERN) {
        @Override
        public void execute(Matcher matcher, RailwaySimulation simulation) throws WrongInputException {
            int trackID;
            Coordinate coordinate;
            try {
                trackID = Integer.parseInt(matcher.group(1));
                coordinate = new Coordinate(Integer.parseInt(matcher.group(2)), Integer.parseInt(matcher.group(3)));

            } catch (NumberFormatException e) {
                throw new WrongInputException(WRONG_PARAMETERS_MESSAGE);
            }

            //TODO add try catch
            try {
                String out=simulation.setSwitch(trackID, coordinate);
                if (out.equals("")){
                    out=POSITIVE_OUTCOME_MESSAGE;
                }
                Terminal.printLine(out);
            } catch (IDDoesNotExistExcepiton | WrongCoordinatesException e) {
                throw new WrongInputException(e.getMessage());
            }


        }
    },


    CREATE_ENGINE(Commands.CREATE_ENGINE_PATTERN) {
        @Override
        public void execute(Matcher matcher, RailwaySimulation simulation) throws WrongInputException {
            EngineTypes engineType;
            String engineClass = matcher.group(2);
            String name = matcher.group(3);
            long length;
            boolean couplingAvailabilityFront;
            boolean couplingAvailabilityBack;
            try {
                engineType = EngineTypes.parseEngineType(matcher.group(1));
                length = Long.parseLong(matcher.group(4));
                couplingAvailabilityFront = Boolean.parseBoolean(matcher.group(5));
                couplingAvailabilityBack = Boolean.parseBoolean(matcher.group(6));
            } catch (ConversionException e) {
                throw new WrongInputException(WRONG_PARAMETERS_MESSAGE);
            }

            try {
                Terminal.printLine(simulation.createEngine(engineType, engineClass, name, length, couplingAvailabilityFront, couplingAvailabilityBack));
            } catch (IDAlreadyExistsException | CouplingConfigurationInvalidException e) {
                Terminal.printError(e.getMessage());
            }
        }
    },
    LIST_ENGINES(Commands.LIST_ENGINES_PATTERN) {
        @Override
        public void execute(Matcher matcher, RailwaySimulation simulation) {
            Terminal.printLine(simulation.listEngines());
        }
    },

    CREATE_COACH(Commands.CREATE_COACH_PATTERN) {
        @Override
        public void execute(Matcher matcher, RailwaySimulation simulation) throws WrongInputException {
            CoachTypes coachType;
            long length;
            boolean couplingAvailabilityFront;
            boolean couplingAvailabilityBack;
            try {
                coachType = CoachTypes.parseCoachType(matcher.group(1));
                length = Long.parseLong(matcher.group(2));
                couplingAvailabilityFront = Boolean.parseBoolean(matcher.group(3));
                couplingAvailabilityBack = Boolean.parseBoolean(matcher.group(4));
            } catch (ConversionException e) {
                throw new WrongInputException(WRONG_PARAMETERS_MESSAGE);
            }


            try {
                Terminal.printLine(simulation.createCoach(coachType, length, couplingAvailabilityFront, couplingAvailabilityBack));
            } catch (IDAlreadyExistsException | CouplingConfigurationInvalidException e) {
                Terminal.printError(e.getMessage());
            }
        }
    },
    LIST_COACHES(Commands.LIST_COACHE_PATTERN) {
        @Override
        public void execute(Matcher matcher, RailwaySimulation simulation) {
            Terminal.printLine(simulation.listCoaches());
        }
    },
    CREATE_TRAIN_SET(Commands.CREATE_TRAIN_SET_PATTERN) {
        @Override
        public void execute(Matcher matcher, RailwaySimulation simulation) throws WrongInputException {

            String engineClass = matcher.group(1);
            String name = matcher.group(2);
            long length;
            boolean couplingAvailabilityFront;
            boolean couplingAvailabilityBack;
            try {

                length = Long.parseLong(matcher.group(3));
                couplingAvailabilityFront = Boolean.parseBoolean(matcher.group(4));
                couplingAvailabilityBack = Boolean.parseBoolean(matcher.group(5));
            } catch (NumberFormatException e) {
                throw new WrongInputException(e.getMessage());
            }


            try {
                Terminal.printLine(simulation.createTrainSet(engineClass, name, length, couplingAvailabilityFront, couplingAvailabilityBack));
            } catch (IDAlreadyExistsException | CouplingConfigurationInvalidException e) {
                Terminal.printError(e.getMessage());
            }
        }
    },
    LIST_TRAIN_SETS(Commands.LIST_TRAIN_SETS_PATTERN) {
        @Override
        public void execute(Matcher matcher, RailwaySimulation simulation) {
            Terminal.printLine(simulation.listTrainSets());
        }
    },
    DELETE_ROLLING_STOCK(Commands.DELETE_ROLLING_STOCK_PATTERN) {
        @Override
        public void execute(Matcher matcher, RailwaySimulation simulation) throws WrongInputException {
            String rollingStockID = matcher.group(1);

            try {
                simulation.deleteRollingStock(rollingStockID);
                Terminal.printLine(POSITIVE_OUTCOME_MESSAGE);
            } catch (RollingStockCouldntBeenAddedOrRemovedException | IDDoesNotExistExcepiton e) {
                throw new WrongInputException(e.getMessage());
            }
        }
    },
    ADD_TRAIN(Commands.ADD_TRAIN_PATTERN) {
        @Override
        public void execute(Matcher matcher, RailwaySimulation simulation) throws WrongInputException {
            int trainID;
            String rollingStockID = matcher.group(2);

            try {
                trainID = Integer.parseInt(matcher.group(1));
            } catch (NumberFormatException e) {
                throw new WrongInputException(WRONG_PARAMETERS_MESSAGE);
            }

            try {
                Terminal.printLine(simulation.addTrain(trainID, rollingStockID));
            } catch (RollingStockCouldntBeenAddedOrRemovedException | IDDoesNotExistExcepiton e) {
                throw new WrongInputException(e.getMessage());
            }
        }
    },
    DELETE_TRAIN(Commands.DELETE_TRAIN_PATTERN) {
        @Override
        public void execute(Matcher matcher, RailwaySimulation simulation) throws WrongInputException {
            int trainID;
            try {
                trainID = Integer.parseInt(matcher.group(1));
            } catch (NumberFormatException e) {
                throw new WrongInputException(WRONG_PARAMETERS_MESSAGE);
            }

            try {
                simulation.deleteTrain(trainID);
                Terminal.printLine(POSITIVE_OUTCOME_MESSAGE);
            } catch (IDDoesNotExistExcepiton e) {
                throw new WrongInputException(e.getMessage());
            }
        }
    },
    LIST_TRAINS(Commands.LIST_TRAINS_PATTERN) {
        @Override
        public void execute(Matcher matcher, RailwaySimulation simulation) {

            Terminal.printLine(simulation.listTrains());


        }
    },
    SHOW_TRAINS(Commands.SHOW_TRAIN_PATTERNS) {
        @Override
        public void execute(Matcher matcher, RailwaySimulation simulation) throws WrongInputException {
            int trainID;
            try {
                trainID = Integer.parseInt(matcher.group(1));
            } catch (NumberFormatException e) {
                throw new WrongInputException(WRONG_PARAMETERS_MESSAGE);
            }

            try {
                Terminal.printLine(simulation.showTrain(trainID));
            } catch (IDDoesNotExistExcepiton e) {
                Terminal.printError(e.getMessage());
            }
        }
    },

    PUT_TRAINS(Commands.PUT_TRAINS_PATTERN) {
        @Override
        public void execute(Matcher matcher, RailwaySimulation simulation) throws WrongInputException {
            int trainID;
            Coordinate coordinate;
            long directionX;
            long directionY;
            try {
                trainID = Integer.parseInt(matcher.group(1));
                coordinate = new Coordinate(Integer.parseInt(matcher.group(2)), Integer.parseInt(matcher.group(3)));
                directionX = Long.parseLong(matcher.group(4));
                directionY = Long.parseLong(matcher.group(5));
            } catch (NumberFormatException e) {
                throw new WrongInputException(WRONG_PARAMETERS_MESSAGE);
            }
            //TODO maybe add new catch
            try {
                simulation.putTrain(trainID, coordinate, directionX, directionY);
            } catch (TrainIsInvalidException | TrainCanNotBePlacedException | IDDoesNotExistExcepiton e) {
                throw new WrongInputException(e.getMessage());
            }
            Terminal.printLine(POSITIVE_OUTCOME_MESSAGE);
        }
    },
    STEP(Commands.STEP_PATTERN) {
        @Override
        public void execute(Matcher matcher, RailwaySimulation simulation) throws WrongInputException {
            short speed;

            try {
                speed = Short.parseShort(matcher.group(1));
            } catch (NumberFormatException e) {
                throw new WrongInputException(WRONG_PARAMETERS_MESSAGE);
            }
            String out= null;
            try {
                out = simulation.step(speed);
            } catch (NotAllSwitchesSetException e) {
                throw new WrongInputException(e.getMessage());
            }
            if (out.equals("")){
                out=POSITIVE_OUTCOME_MESSAGE;
            }
            Terminal.printLine(out);
        }
    },


    EXIT(Commands.EXIT_PATTERN) {
        @Override
        public void execute(Matcher matcher, RailwaySimulation simulation) throws WrongInputException {
            this.exit();
        }
    };

    protected void exit() {
        this.running = false;
    }

    private static final String WRONG_PARAMETERS_MESSAGE = "The given parameters are wrong";
    private static final String POSITIVE_OUTCOME_MESSAGE = "OK";

    Commands(String pattern) {
        this.pattern = Pattern.compile(pattern);
        this.running = true;
    }

    //TODO Add execute to every command

    public static Commands executeUserInputs(String userInputs, RailwaySimulation simulation) throws WrongInputException {
        for (Commands command : Commands.values()) {
            Matcher matcher = command.pattern.matcher(userInputs);
            if (matcher.matches()) {
                command.execute(matcher, simulation);
                return command;
            }
        }
        throw new WrongInputException(INVALID_COMMAND_EXCEPTION_MESSAGE);
    }

    public abstract void execute(Matcher matcher, RailwaySimulation simulation) throws WrongInputException;

    public boolean isRunning() {
        return running;
    }

    private static final String INVALID_COMMAND_EXCEPTION_MESSAGE = "invalid command";


    private Pattern pattern;
    private boolean running;
    //TODO check if patterns are right
    //Pattern building Blocks for Command Pattern
    private static final String COORDINATE_PATTERN = "([+|-]?\\d*)"; // accept int
    private static final String POINT_PATTERN = "\\(" + COORDINATE_PATTERN + "," + COORDINATE_PATTERN + "\\)";
    //TODO Add Pattern (Regex (Zugname/Baureihe): [\\p{L}0-9]+ (Whatsapp))
    private static final String POSITIVE_INT_PATTERN_WITHOUT_ZERO = "(\\+?0*[1-9]\\d*)";
    private static final String BOOLEAN_PATTERN = "(true|false)";
    private static final String TRACK_ID_PATTERN = POSITIVE_INT_PATTERN_WITHOUT_ZERO; //accept positive int
    private static final String ENGINE_TYPE_PATTERN = "(electrical|steam|diesel)";
    private static final String CLASS_PATTERN = "[\\w&&[^W]]*";
    private static final String NAME_PATTERN = "[\\w]*";//accept String
    private static final String LENGTH_PATTERN = POSITIVE_INT_PATTERN_WITHOUT_ZERO; //accept positive int
    private static final String COUPLING_AVAILABILITY_PATTERN = BOOLEAN_PATTERN; // "true" or "false"
    private static final String COACH_TYPE_PATTERN = "(passenger|freight|special)";
    private static final String ROLLING_STOCK_ID_PATTERN = "(" + Commands.COACH_ID_PATTERN + "|" + Commands.MOTORIZED_ROLLING_STOCK_ID_PATTERN + ")"; // //Coach ID: positive int,
    private static final String MOTORIZED_ROLLING_STOCK_ID_PATTERN = CLASS_PATTERN + "-" + NAME_PATTERN;
    private static final String COACH_ID_PATTERN = "[W][\\d]*";
    private static final String TRAIN_ID_PATTERN = POSITIVE_INT_PATTERN_WITHOUT_ZERO;
    private static final String SPEED_PATTERN = "(\\d)";

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
            " (" +
            Commands.CLASS_PATTERN +
            ") (" +
            Commands.NAME_PATTERN +
            ") " +
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
    private static final String LIST_COACHE_PATTERN = "list coaches";
    private static final String CREATE_TRAIN_SET_PATTERN = "create train-set (" +
            Commands.CLASS_PATTERN +
            ") (" +
            Commands.NAME_PATTERN +
            ") " +
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
