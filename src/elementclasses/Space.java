package elementclasses;

public abstract class Space implements java.io.Serializable{
/**
     * @return gets a description of the space
     */
public abstract String getDescription();

/**
     * @param theDoor sets the door to the given door
     */
public abstract void setDoor(Door theDoor);

}
