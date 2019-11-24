package elementclasses;
import dnd.die.D20;
import dnd.die.D10;
import dnd.die.D6;
import dnd.exceptions.NotProtectedException;
import dnd.exceptions.UnusualShapeException;
import dnd.models.Monster;
import dnd.models.Treasure;
import dnd.models.ChamberShape;
import dnd.models.Exit;
import dnd.models.ChamberContents;
import dnd.models.Stairs;

import java.util.ArrayList;

/**
 * @author Matthew Krahn
 */

public class Chamber extends Space implements java.io.Serializable {

/**
     * holds contents.
     */
private ChamberContents myContents;
/**
     * holds chamber shape.
     */
private ChamberShape mySize;

/**
     * Holds a list of monsters.
     */
private ArrayList<Monster> monstList = new ArrayList<Monster>();
/**
     * Holds a list of treasure.
     */
private ArrayList<Treasure> treasList  = new ArrayList<Treasure>();
		/**
     * Holds a list of doors.
     */
private ArrayList<Door> doorList;
		/**
     * Holds a list of chamber shapes.
     */
private ArrayList<ChamberShape> shapeList;
		/**
     * Holds a list of chamber contents.
     */
private ArrayList<ChamberContents> contentsList;

private ArrayList<Chamber> chamberList  = new ArrayList<Chamber>();

	/* note:  Some of these methods would normally be protected or private, but because we
	don't want to dictate how you set up your packages we need them to be public
	for the purposes of running an automated test suite (junit) on your code.  */

	/**
     * creates a chamber.
     */
public Chamber() {
	myContents = new ChamberContents();

	mySize.selectChamberShape(roll20());
	myContents.chooseContents(roll20());
	setShape(mySize);

	shapeList = new ArrayList<ChamberShape>();
	shapeList.add(mySize);
	contentsList = new ArrayList<ChamberContents>();
	contentsList.add(myContents);
}
	/**
     * creates chamber.
     * @param theShape set shape
     * @param theContents set contents
     */
public Chamber(ChamberShape theShape, ChamberContents theContents) {
	theContents.chooseContents(roll20());
	setShape(theShape);
	shapeList = new ArrayList<ChamberShape>();
	shapeList.add(theShape);
	contentsList = new ArrayList<ChamberContents>();
	contentsList.add(theContents);
}

	/**
     * @return list of chambershaper
     */
public ArrayList<ChamberShape> getShape() {
	return shapeList;
}

	/**
     * @return list contents
     */
public ArrayList<ChamberContents> getContents() {
	return contentsList;
}

	/**
     * @param theShape set chamber to this shape
     */
public void setShape(ChamberShape theShape) {
	mySize = theShape.selectChamberShape(roll20());
	setDoor(mySize);
	//mySize.setShape(roll20());


}

public ArrayList<Chamber> getChamberList() {
		return chamberList;
	}

public void setDoor(ChamberShape theShape){

	if (theShape.getNumExits() == 0) {
		theShape.setNumExits(roll20());
	}
	if (theShape.getNumExits() <= 0) {
		for (int i = 0; i < theShape.getNumExits(); i++) {

			Door door = new Door();
			doorList.add(door);
			Exit exit = new Exit();

			theShape.getExits().add(exit);
		}
	} else {
		Exit exit = new Exit();
		theShape.getExits().add(exit);

	}

}

int getNumberOfExits(){

	return mySize.getNumExits();
}

	/**
     * list to hold doors.
     * @return doors
     */
public ArrayList<Door> getDoors() {
	return doorList;
}

	/**
     * @param theMonster adds monster
     */
public void addMonster(Monster theMonster) {
	theMonster.setType(roll20());
	monstList.add(theMonster);
}

	/**
     * list to hold monster.
     * @return monster
     */
public ArrayList<Monster> getMonsters() {
	return monstList;
}

	/**
     * @param theTreasure treasure
     */
public void addTreasure(Treasure theTreasure) {
	theTreasure.chooseTreasure(roll20());
	theTreasure.setContainer(roll20());

	treasList.add(theTreasure);


}


	/**
     * list to hold treasure.
     * @return treasure
     */
public ArrayList<Treasure> getTreasureList() {
	return treasList;
}



@Override
public String getDescription() {
	Monster theMonster = new Monster();
	Treasure theTreasure = new Treasure();

	String combined = "";

	if (getContents().get(0).getDescription().equals("empty")) {
		combined = combined + ("The room is Empty!\n");
	} else if (getContents().get(0).getDescription().equals("monster only")) {
		addMonster(theMonster);
		combined = combined + ("There is between " + getMonsters().get(0).getMinNum() + " and " + getMonsters().get(0).getMaxNum() + " " + getMonsters().get(0).getDescription() + "\n");

	} else if (getContents().get(0).getDescription().equals("monster and treasure")) {
		addMonster(theMonster);
		addTreasure(theTreasure);
		combined = combined + ("There is between " + getMonsters().get(0).getMinNum() + " and " + getMonsters().get(0).getMaxNum() + " " + getMonsters().get(0).getDescription() + "\n");

		combined = combined + ("You find " + getTreasureList().get(0).getDescription() + " contained in " + getTreasureList().get(0).getContainer());
		try {
			combined = combined + (". The treasure is guarded by " + getTreasureList().get(0).getProtection() + "\n");
		} catch (NotProtectedException e) {
			combined = combined + (". The treasure is unguarded") + "\n";
		}
	} else if (getContents().get(0).getDescription().equals("stairs")) {
		Stairs stairs = new Stairs();

		stairs.setType(roll20());
		combined = combined + ("There are stairs that go " + stairs.getDescription() + "\n");

	} else if (getContents().get(0).getDescription().equals("trap")) {
		combined = combined + ("There is a trap\n");

	} else if (getContents().get(0).getDescription().equals("treasure")) {
		addTreasure(theTreasure);
		combined = combined + ("You find " + getTreasureList().get(0).getDescription() + " contained in " + getTreasureList().get(0).getContainer());
		try {
			combined = combined + (". The treasure is guarded by " + getTreasureList().get(0).getProtection() + "\n");
		} catch (NotProtectedException e) {
			combined = combined + (". The treasure is unguarded\n");
		}
	}

	
	combined = combined + ("The room is " + getShape().get(0).getShape() + " shaped, with an area of " + getShape().get(0).getArea());
	try {
		combined = combined + (" (" + getShape().get(0).getLength() + "x" + getShape().get(0).getWidth() + ")");
	} catch (UnusualShapeException e) {

	}
	combined = combined + (". There are " + getShape().get(0).getNumExits() + " exits\n");

		for (int i = 0; i < getShape().get(0).getNumExits(); i++) {
			combined = combined + "Door " + (i + 1) + " is on the " + getShape().get(0).getExits().get(i).getLocation() + ".\n";
		}

	return combined;
}

@Override
public void setDoor(Door newDoor) {
	//should add a door connection to this room
	if (roll10() == 1) {
		newDoor.setOpen(true);
	} else {
		if (roll20() == 1) {
			newDoor.setTrapped(true, roll10());
		}
		if (roll6() == 1) {
			newDoor.setOpen(false); /*locked*/
		}
	}

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
