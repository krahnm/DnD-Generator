package gui;

import java.io.*;
import java.util.ArrayList;

import elementclasses.Chamber;
import elementclasses.Door;
import elementclasses.LevelGenerator;
import elementclasses.Passage;
import javafx.scene.control.ComboBox;
import javafx.scene.control.ListView;


public class Controller {
    private MyGui gui;
    private LevelGenerator levGen;

    public Controller(MyGui tempGui){
        gui = tempGui;
        this.levGen = new LevelGenerator();
        this.levGen.levelGen(5);
    }

   /* private String getNameList(){
        String nameList = new String();
        ArrayList<Hydra> hydras = hydraBattle.getBattleLineup();
        for(Hydra h: hydras){
            nameList = nameList  + h.toString()+ "\n";
        }
        return nameList;
    }*/

    public void reactToButton(){
        System.out.println("Thanks for clicking!");
    }

   /* public String getNewDescription(){
        //return "this would normally be a description pulled from the model of the Dungeon level.";
        return getNameList();
    }*/

    public ListView<String>  setChambPassList(ListView<String> listView){
        int i = 1;

        ArrayList<Chamber> chambers = levGen.getChambers();
        ArrayList<Passage> passages = levGen.getPassages();

        for(Passage h: passages){
            listView.getItems().add("Passage " + i);
            i++;
        }

        i=1;
        for(Chamber h: chambers){
            listView.getItems().add("Chamber " + i);
                    i++;
        }
        return listView;
    }

    public String  getChambPassDesc(int index){
        ArrayList<Chamber> chambers = levGen.getChambers();
        ArrayList<Passage> passages = levGen.getPassages();

        System.out.println("passage size " + passages.size());
        System.out.println("index size " + index);
        if (index <= passages.size() - 1){
            return passages.get(index).getDescription();
        }
        else{
            System.out.println("index: " + (index-passages.size()));
            return chambers.get(index-passages.size()).getDescription();
        }

    }

    public ComboBox<String> setDoorList( ComboBox<String> comboBox){
        ArrayList<Door> doors = levGen.getDoors();
        int i = 1;
        for(Door h: doors){
            comboBox.getItems().add("Door " + i);
            i++;
        }
        return comboBox;
    }

    public String  getDoorDesc(int index){
        ArrayList<Door> doors = levGen.getDoors();

        System.out.println("doors size " + doors.size());
        System.out.println("index size " + index);

            return doors.get(index).getDescription();


    }

    public ComboBox<String> setTreasureList( ComboBox<String> comboBox){
        //ArrayList<Door> doors = levGen.getDoors();
        //int i = 1;
       // for(Door h: doors){
            comboBox.getItems().addAll("COINS", "DIAMONDS", "SWORD");
        //    i++;
        //}
        return comboBox;
    }

    public ComboBox<String> setMonsterList( ComboBox<String> comboBox){
        //ArrayList<Door> doors = levGen.getDoors();
        //int i = 1;
        // for(Door h: doors){
        comboBox.getItems().addAll("BOOGEY MAN", "DRACULA", "ZOMBIE");
        //    i++;
        //}
        return comboBox;
    }

    public void save(String file) {
        try {
            FileOutputStream fileOut = new FileOutputStream(file);
            ObjectOutputStream out = new ObjectOutputStream(fileOut);
            out.writeObject(levGen);
            out.close();
            fileOut.close();
            System.out.printf("Serialized data is saved in " + file);
        } catch (IOException i) {
            i.printStackTrace();
        }
    }

    public void load(String file) {
         levGen = null;
        System.out.println("LOAD:  " + file);
        try {
            FileInputStream fileIn = new FileInputStream(file);
            ObjectInputStream in = new ObjectInputStream(fileIn);
            levGen = (LevelGenerator) in.readObject();
            in.close();
            fileIn.close();
        } catch (IOException i) {
            i.printStackTrace();
            return;
        } catch (ClassNotFoundException c) {
            System.out.println("Load file not found");
            c.printStackTrace();
            return;
        }

        System.out.println("Deserialized LevelGenerator...");

    }

   /* public String getNewDescription(){
        //return "this would normally be a description pulled from the model of the Dungeon level.";
        return getChamberList();
    }*/
}
