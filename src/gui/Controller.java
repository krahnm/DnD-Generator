package gui;

import java.util.ArrayList;

import elementclasses.Chamber;
import elementclasses.Door;
import elementclasses.LevelGenerator;
import elementclasses.Passage;
import javafx.scene.control.ComboBox;
import javafx.scene.control.ListView;
import saves.Save;
import saves.Load;

public class Controller {
    private MyGui gui;
    private LevelGenerator levGen;
    private Save saveData;
    private Load loadData;

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

        saveData = new Save(chambers);
        loadData = new Load();

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

   /* public String getNewDescription(){
        //return "this would normally be a description pulled from the model of the Dungeon level.";
        return getChamberList();
    }*/
}
