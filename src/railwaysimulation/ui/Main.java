package railwaysimulation.ui;

import edu.kit.informatik.Terminal;
import railwaysimulation.exceptions.WrongInputException;
import railwaysimulation.railway.RailwaySimulation;

public class Main {
    public static void main(String[] args) {
        RailwaySimulation simulation=new RailwaySimulation();
        Commands command=null;
        do {
            try{
                command=Commands.executeUserInputs(Terminal.readLine(),simulation);

            } catch (WrongInputException e) {
                Terminal.printError(e.getMessage());
            }
        } while (command==null||command.isRunning());

    }
}
