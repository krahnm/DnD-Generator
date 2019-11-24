package elementclasses;

import dnd.die.D20;
import dnd.models.Monster;

import java.util.ArrayList;

/**
 * @author Matthew Krahn
 */

/* Represents a 10 ft section of passageway */

public class PassageSection {

/**
     * list to hold doors.
     */
private ArrayList<Door> doorList = new ArrayList<Door>();

/**
     * list to hold monsters.
     */
private ArrayList<Monster> monstList = new ArrayList<Monster>();

/**
     * list to hold strings.
     */
private ArrayList<String> passageStringList = new ArrayList<String>();


/**
     * Create passageSection.
     */
public PassageSection() {
//sets up the 10 foot section with default settings
     System.out.println("10 foot section");
     passageStringList.add("10 foot section");
     Door door = new Door();
     door.setTrapped(false);
     doorList.add(door);
}

/**
     * Creates passage section.
     * @param description descriving section
     */
public PassageSection(String description) {
     //sets up a specific passage based on the values sent in from
     //modified table 1
     Passage p = new Passage();
     passageStringList.add(description);
     if (description.equals("passage goes straight for 10 ft")) {
          Door door = new Door();
          doorList.add(door);
          } else if (description.equals("passage ends in Door to a Chamber")) {
               Door door = new Door(); /*TODO link to a chamber*/

               doorList.add(door);
          } else if (description.equals("archway (door) to right (main passage continues straight for 10 ft)")) {
               Door door = new Door();
               door.setArchway(true);
               doorList.add(door);
          } else if (description.equals("archway (door) to left (main passage continues straight for 10 ft)")) {
               Door door = new Door();
               door.setArchway(true);
               doorList.add(door);
          } else if (description.equals("passage turns to left and continues for 10 ft")) {
               Door door = new Door();
               doorList.add(door);
          } else if (description.equals("passage turns to right and continues for 10 ft")) {
               Door door = new Door();
               doorList.add(door);
          } else if (description.equals("passage ends in archway (door) to chamber")) {
               Door door = new Door();
               door.setArchway(true);
               doorList.add(door);
          } else if (description.equals("Stairs, (passage continues straight for 10 ft)")) {
               Door door = new Door();
               doorList.add(door);
          } else if (description.equals("Dead End")) {
               Door door = new Door();
               doorList.add(door);
          } else if (description.equals("Wandering Monster (passage continues straight for 10 ft)")) {
               addMonster();
               Door door = new Door();
               doorList.add(door);
          } else {
               Door door = new Door();
               doorList.add(door);
          }
     }

/**
     * @return gets a door
     */
public Door getDoor() {
          //returns the door that is in the passage section, if there is one
          return doorList.get(0); //TODO turn the rest of them from 0 to this
     }

/**
     * @return gets a monster
     */
public Monster getMonster() {
     //returns the monster that is in the passage section, if there is one
          if (monstList.size() <= 0) {
               return null;
          } else {
               return monstList.get(0);
          }
     }

/**
     * @return a description of section
     */
public String getDescription() {
     String combined = "";

     combined = combined + (passageStringList.get(0));
     if (getMonster() != null) {
          combined = combined + (getMonster().getDescription());
     }
     if (getDoor() != null) {
          combined = combined + (getDoor().getDescription());
     }

     return combined;
     }

/**
     * adds a monster.
     * @param theMonster add to section
     */
public void addMonster(Monster theMonster) {
          monstList.add(theMonster);
     }

/**
     * adds monster.
     */
public void addMonster() {
          Monster theMonster = new Monster();
          theMonster.setType(roll20());
          monstList.add(theMonster);
     }

     private int roll20() {
          D20 dice20 = new D20();
          int num = dice20.roll();
          return num;
     }
}
