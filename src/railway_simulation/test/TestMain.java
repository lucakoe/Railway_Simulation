package railway_simulation.test;

import railway_simulation.railway.*;
import edu.kit.informatik.Terminal;

import java.util.TreeMap;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

//TODO check in every class if any variables should be set final and privat/protected
//TODO check every method for null pointer exception
//TODO maybe use exception instead of boolean (everywhere)
//TODO check for Wildcards and remove them
public class TestMain {
    public static void main(String[] args) {

        Point[] points=new Point[50];
        Point[] points2=new Point[50];
        for (int i=0;i<points.length;i++){
            points[i]=new Point(new Coordinate(0,i));
            points2[i]=new Point(new Coordinate(1,i));
        }
        for (int i=0;i<points.length-1;i++){
            points[i].connectToPoint(points[i+1]);
        }
        for (int i=0;i<points2.length-1;i++){
            points[i].disconnectFromPoint(points[i+1]);
            //points2[i].connectToPoint(points[i]);
        }
        /*
        {
            String out="";
            String testString = "This\nis\na\n test";
            String[] testStringSplit=testString.split("\n");
            for (int i =0;i<testStringSplit.length;i++){
                out+=testStringSplit[i];
            }
            Terminal.printLine(out);
        }

        Train testTrain = new Train();
        Engine engine=new Engine(EngineTypes.STEAM,"PP","Popo",2,false,true);
        Coach coach1=new Coach(CoachTypes.PASSENGERS,2,true,true);
        Coach coach2=new Coach(CoachTypes.PASSENGERS,2,true,true);
        //Coach coach3=new Coach(CoachTypes.SPECIAL,2,true,true);
        testTrain.add(engine);
        testTrain.add(coach1);
        testTrain.add(coach2);
        //testTrain.add(coach3);
        Terminal.printLine(engine);
        Terminal.printLine(coach1);
        Terminal.printLine(coach2);
        //Terminal.printLine(coach3);
        Terminal.printLine(testTrain);

         */
        //TreeMap<Direction,Train> testMap=new TreeMap<Direction,Train>();
        //Terminal.printLine(testMap.containsKey(Direction.NORTH));

        CoordinateSystemMap map=new CoordinateSystemMap();
        map.addTrack(new Coordinate(10,1),new Coordinate(5,1));
        map.addTrack(new Coordinate(5,1),new Coordinate(5,2));

        TreeMap<String,Integer> testTreeMap = new TreeMap<String, Integer>();
        testTreeMap.put("Abba", 1);
        testTreeMap.put("aAdol", 2);
        testTreeMap.put("zkbba", 3);
        testTreeMap.put("Dbba", 4);
        String[] testStringArray= testTreeMap.navigableKeySet().toArray(new String[testTreeMap.size()]);
        for (int i=0;i<testStringArray.length;i++){
            Terminal.printLine(testStringArray[i]);
        }
        Pattern testPattern = Pattern.compile("^[1-9]\\d*$");
        Matcher testMatcher = testPattern.matcher(Long.toString(((long)Integer.MAX_VALUE)+10));
        Terminal.printLine(testMatcher.matches());
        Terminal.printLine(Integer.parseInt(Long.toString(((long)Integer.MAX_VALUE)+10)));


        Terminal.printLine("end");



    }
}
