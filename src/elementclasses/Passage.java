package elementclasses;

import dnd.models.Monster;

import java.util.ArrayList;
import dnd.die.D20;

/**
 * @author Matthew Krahn
 */

public class Passage extends Space {

/**
     * list to hold passage sections.
     */
private ArrayList<PassageSection> thePassageSect = new ArrayList<PassageSection>();

/**
     * list to hold doors.
     */
private ArrayList<Door> doorList = new ArrayList<Door>();



/**
     * list to hold doors.
     * @return doors
     */
public ArrayList<Door> getDoors() {
     //gets all of the doors in the entire passage
     return doorList;
     }

/**
     * @param i sets door to it
     * @return door
     */
public Door getDoor(int i) {

     return doorList.get(i);
     }

/**
     * @param theMonster monster to add
     * @param i index
     */
public void addMonster(Monster theMonster, int i) {
     // adds a monster to section 'i' of the passage
     thePassageSect.get(i).addMonster(theMonster);

}

/**
     * @param i return monster in i
     * @return a monster
     */
public Monster getMonster(int i) {
     //returns Monster door in section 'i'. If there is no Monster, returns null
     if (thePassageSect.size() < 0) {
          return null;
     } else {
          return thePassageSect.get(i).getMonster();
     }

}


/**
     * @param toAdd add passage
     */
public void addPassageSection(PassageSection toAdd) {
     //adds the passage section to the passageway
     thePassageSect.add(toAdd);
}



/**
     *@param newDoor door to add
     */
public void setDoor(Door newDoor) {

     doorList.add(newDoor);

}

@Override
public String getDescription() {
     String combined1 = "";

for (int i = 0; i < this.thePassageSect.size(); i++) {
     combined1 = combined1 + (thePassageSect.get(i).getDescription()) + "\n";
}
//System.out.println(combined1);
return (combined1);
}

/**
     * @return string
     */
public String getPSDescription() {
     String desc = "";
     int num = roll20();
     if (num >= 1 && num <= 2) {
          desc = "passage goes straight for 10 ft";
     } else if (num <= 5) {
          desc = "passage ends in Door to a Chamber";
     } else if (num <= 7) {
          desc = "archway (door) to right (main passage continues straight for 10 ft)";
     } else if (num <= 9) {
          desc = "archway (door) to left (main passage continues straight for 10 ft)";
     } else if (num <= 11) {
          desc = "passage turns to left and continues for 10 ft";
     } else if (num <= 13) {
          desc = "passage turns to right and continues for 10 ft";
     } else if (num <= 16) {
          desc = "passage ends in archway (door) to chamber";
          Door door = new Door();
          doorList.add(door);
     } else if (num == 17) {
          desc = "Stairs, (passage continues straight for 10 ft)";
     } else if (num <= 19) {
          desc = "passage goes straight for 10 ft";
     } else if (num == 20) {
          desc = "Wandering Monster (passage continues straight for 10 ft)";

     }
     return desc;
}

     private int roll20() {
          D20 dice20 = new D20();
          int num = dice20.roll();
          return num;
     }


}
