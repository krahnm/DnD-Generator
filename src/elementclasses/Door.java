package elementclasses;
import dnd.models.Exit;
import dnd.models.Trap;
import java.util.ArrayList;
import dnd.die.D20;
import dnd.die.D10;
import dnd.die.D6;

/**
 * @author Matthew Krahn
 */

public class Door implements java.io.Serializable{
/**
     * list to hold doors.
     */
private ArrayList<Door> doorList;
	/**
     * list to hold spaces.
	*/
	private ArrayList<Space> spaceList;
	/**
     * list to hold traps.
     */
	private ArrayList<Trap> trapList = new ArrayList<Trap>();
	/**
     * @return flag
     */
	private boolean trapped;
	/**
     * @return flag
     */
	private boolean open;
	/**
     * @return flag
     */
	private boolean archway;

/**
     * @return door
     */
     public Door() {
          //needs to set defaults


		if (roll10() == 1) {
			this.setOpen(true);
		} else {
			if (roll20() == 1) {
				this.setTrapped(true, roll10());
			}
			if (roll6() == 1) {
				this.setOpen(false); /*locked*/
			}
		}
	}
	    /**
     *
     * @param theExit can get the drection of the door
     */
	public Door(Exit theExit) {
		//sets up the door based on the Exit from the tables


		if (roll10() == 1) {
			this.setOpen(true);
		} else {
			if (roll20() == 1) {
				this.setTrapped(true, roll10());
			}
			if (roll6() == 1) {
				this.setOpen(false); /*locked*/
			}
		}
	}


	/* note:  Some of these methods would normally be protected or private, but because we
	don't want to dictate how you set up your packages we need them to be public
	for the purposes of running an automated test suite (junit) on your code.  */


    /**
     * @param flag tells to set trap
     * @param roll choses what to set the trap to
     */
	public void setTrapped(boolean flag, int... roll) {
		// true == trapped.  Trap must be rolled if no integer is given
		if (flag) {
			trapped = true;
			Trap trap = new Trap();
			if (roll == null) {
				trap.chooseTrap(roll20());
			}
			else{
				trap.chooseTrap(roll[0]);
			}

			trapList.add(trap);
		}
	}

	 /**
     *
     * @param flag tells to set open
     */
	public void setOpen(boolean flag) {
		//true == open
		if (flag || archway) {
			open = true;
		} else {
			open = false;
		}
	}

	/**
     * @param flag tells to set to archway
     */
	public void setArchway(boolean flag) {
		//true == is archway
		if (flag) {
			archway = true;
			open = true;
		} else {
			archway = false;
		}
	}

	/**
     * @return returns if the door is trapped
     */
	public boolean isTrapped() {
		return trapped;
	}

	/**
     * @return returns if the door is open
     */
	public boolean isOpen() {
		return open;
	}

	/**
     * @return returns if the door is an archway
     */
	public boolean isArchway() {
		return archway;
	}

	/**
     * @return returns description of the door
     */
	public String getTrapDescription() {

		return trapList.get(0).getDescription();
	}

	/**
     * list to hold spaces.
     * @return list of spaces
     */
	public ArrayList<Space> getSpaces() {
		//returns the two spaces that are connected by the door
		return spaceList;
	}

	/**
     * @param spaceOne gives user a space
     * @param spaceTwo gives user a space
     */
	public void setSpaces(Space spaceOne, Space spaceTwo) {
		//identifies the two spaces with the door
		// this method should also call the setDoor method from Space

		spaceOne.setDoor(this);
		spaceTwo.setDoor(this);

		spaceList.add(spaceOne);
		spaceList.add(spaceTwo);

	}

	/**
     * @return string
     */
	public String getDescription() {
		String combined = "";
		if(isArchway()||isOpen()||isTrapped()) {
            if (isArchway()) {
                combined = combined + ("There is an arch.");
            }
            if (isOpen()) {
                combined = combined + ("The door is open. ");
            }
            if (isTrapped()) {
                combined = combined + "The door is trapped by " + getTrapDescription() + ". ";
            }
        }
		else {
            combined = combined + ("The door is open. ");
        }
		return combined;
	}

	private int roll20() {
		D20 dice20 = new D20();
		int num = dice20.roll();
		return num;
	}

	private int roll10() {
		D10 dice10 = new D10();
		int num = dice10.roll();
		return num;
	}

	private int roll6() {
		D6 dice6 = new D6();
		int num = dice6.roll();
		return num;
	}

}
