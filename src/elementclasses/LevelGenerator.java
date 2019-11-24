package elementclasses;

import dnd.models.ChamberShape;


import java.util.Random;
import java.util.ArrayList;
import java.util.HashMap;



/**
 * @author Matthew Krahn
 */


/*
 * java -cp lib/dnd-20190914.jar:.:build elementclasses/LevelGenerator
 */
public class LevelGenerator implements java.io.Serializable{

    private HashMap<Door, ArrayList<Chamber>> hm;
    private HashMap<Door, ArrayList<Integer>> hmVal;
    private ArrayList<Door> doorList;
    private  ArrayList<Chamber> chamberList;
    private ArrayList<Passage> passageList;
    /**
     * @param
     */
    public LevelGenerator() {
        hm = new HashMap<Door, ArrayList<Chamber>>();
        hmVal  = new HashMap<Door, ArrayList<Integer>>();
        doorList = new ArrayList<Door>();
        chamberList = new ArrayList<Chamber>();
        passageList = new ArrayList<Passage>();
    }
    /**
     * @param
     */
    public ArrayList<Chamber> getChambers(){
        return chamberList;
    }

    public ArrayList<Passage> getPassages(){
        return passageList;
    }
    public ArrayList<Door> getDoors(){
        return doorList;
    }

    public void chamberGenerator(int num){
        while (chamberList.size() < 5) {
            Chamber c = new Chamber();
            chamberList.add(c);
        }
    }

    public void passageGenerator(int num){
        int i;
        for (i = 0; i < num; ++i) {
            for (int j = 0; j < chamberList.get(i).getNumberOfExits(); j++) {
                Door d = new Door();
                doorList.add(d);
            }
        }
    }

    public void levelGen(int num){
        Door d1 = new Door();
        PassageSection ps1;
        PassageSection ps2;
        doorList.add(d1);
        int[] doorsChamb = new int[5];

        while (doorList.size() % 2 != 0) {
            doorList.clear();
            chamberList.clear();
            passageList.clear();
            chamberGenerator(num);
            passageGenerator(chamberList.size());
            System.out.println("SETTING \n\n\n\n\n");
        }

        int current = 0;
        int target = 0;
        int i = 0;
        boolean foundTarget = false;
        int testingCounter = 0;
        int numExits = 0;
        for (i = 0; i < doorList.size() / 2; i++) {
            Passage p = new Passage();
            ps1 = new PassageSection(p.getPSDescription());
            ps2 = new PassageSection(p.getPSDescription());
            p.addPassageSection(ps1);
            p.addPassageSection(ps2);
            passageList.add(p);

        }

        for (i = 0; i < chamberList.size(); i++) {
            if (chamberList.get(i).getNumberOfExits() > numExits) {
                current = i;
                numExits = chamberList.get(i).getNumberOfExits();
            }

        }
        for (i = 0; i < doorList.size(); i++) { //go through all doors setting chambers

            if (chamberList.get(current).getNumberOfExits() > doorsChamb[current]) { //current chamber
                foundTarget = false;
                testingCounter = 0;
                while (!foundTarget && testingCounter < 50) { //Looks for target. Check to make sure chamber can have another door connection added and isn't current one
                    Random r = new Random();
                    target = r.nextInt((4) + 1);

                    testingCounter++;

                    if ((chamberList.get(target).getNumberOfExits() > doorsChamb[target]) && target != current) { //found a suitable target
                        ArrayList<Chamber> chamberList2 = new ArrayList<Chamber>();
                        ArrayList<Integer> chambValList = new ArrayList<Integer>();
                        chamberList2.add(chamberList.get(current));
                        chamberList2.add(chamberList.get(target));
                        chambValList.add(current);
                        chambValList.add(target);
                        doorsChamb[current]++;
                        doorsChamb[target]++;
                        System.out.println("*************************************************************************");
                        System.out.println("                              Passage");
                        System.out.println("Connects chamber " + (current + 1) + " to chamber " + (target + 1));
                        System.out.println(passageList.get(0).getDescription());


                        if ((i + 1) < doorList.size()) {
                            hm.put(doorList.get(i), chamberList2); //Adds 2 doors per match up
                            hmVal.put(doorList.get(i), chambValList);
                            hm.put(doorList.get(i + 1), chamberList2);
                            hmVal.put(doorList.get(i + 1), chambValList);
                        }
                        i++;

                        foundTarget = true;
                    }

                }

            } else {
                numExits = 0;
                int numMatches = 0;
                int j = 0;
                for (j = 0; j < chamberList.size(); j++) {
                    if (chamberList.get(j).getNumberOfExits() >= numExits && chamberList.get(j).getNumberOfExits() != doorsChamb[j]) {
                        current = j;
                        numExits = chamberList.get(j).getNumberOfExits();
                    }
                    if (chamberList.get(j).getNumberOfExits() == doorsChamb[j]) {
                        numMatches++;
                        if (numMatches >= 5) {
                            j = chamberList.size();
                        }
                    }

                }
            }

        }

        for (i = 0; i < chamberList.size(); i++) {

            System.out.println("*************************************************************************");
            System.out.println("                              Chamber " + (i + 1));
            System.out.println(chamberList.get(i).getDescription());
        }

    }

    public static void Level(String[] args) {
       /* LevelGenerator makeLevel = new LevelGenerator();
        makeLevel.levelGen(5);*/
}
}