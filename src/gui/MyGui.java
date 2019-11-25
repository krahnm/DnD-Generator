package gui;

import java.util.ArrayList;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.stage.Stage;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.layout.*;
import javafx.stage.Popup;
import javafx.stage.FileChooser;
import javafx.geometry.Insets;
import javafx.scene.control.Dialog;
import javafx.scene.control.DialogPane;
import javafx.scene.control.TextField;

import javax.swing.*;
import java.io.File;
import java.util.Optional;


public class MyGui extends Application {


    private String desc;
    private Controller theController;
    private BorderPane root;  //the root element of this GUI
    private Popup descriptionPane;
    private Stage primaryStage;  //The stage that is passed in on initialization

    /*a call to start replaces a call to the constructor for a JavaFX GUI*/
    @Override
    public void start(Stage assignedStage) {
        /*Initializing instance variables */
        theController = new Controller(this);
        primaryStage = assignedStage;
        /*Border Panes have  top, left, right, center and bottom sections */
       root = setUpRoot();

		theController.fillMonstDatabase();

        Scene scene = new Scene(root, 1200, 800);
        primaryStage.setTitle("DND Level Generator");
        primaryStage.setScene(scene);
        primaryStage.show();

    }

    private BorderPane setUpRoot() {
        BorderPane root = new BorderPane();
        root.setTop(new Label("The name or identifier of the thing below"));
        Node left = setListView();  //separate method for the left section
        Node right = setRightButtonPanel();
        Node top = setFileMenu();
        Node centre = setCentrePanel(desc);
        root.setLeft(left);
        root.setRight(right);
        root.setTop(top);
        root.setCenter(centre);
        

        return root;
    }

    private Node setListView() {
        ListView<String> listView = new ListView<>();
        listView.getSelectionModel().setSelectionMode(SelectionMode.MULTIPLE);
        listView = theController.setChambPassList(listView);
       // System.out.println(listView.getSelectionModel().getSelectedItem());
        //listView.onMouseClickedProperty();
        Button btn = createButton("Edit", "-fx-background-color: #ff0000; -fx-background-radius: 10, 10, 10, 10;");
        btn.setOnAction((ActionEvent event) -> {
            theController.reactToButton();
            Node left = treasureSelect();
            Node right = monsterSelect();
            descriptionPane = createPopUp(500, 500, "");
            descriptionPane.getContent().addAll(right, left);
            descriptionPane.show(primaryStage);
        });

        ListView<String> finalListView = listView;
        listView.setOnMouseClicked(event -> {
            ObservableList selectedIndices = finalListView.getSelectionModel().getSelectedIndices();

            for(Object o : selectedIndices){
                desc = theController.getChambPassDesc(finalListView.getSelectionModel().getSelectedIndex());
                Node centre = setCentrePanel(desc);
                root.setCenter(centre);
                primaryStage.setTitle("DND Level Generator");
                primaryStage.show();
            }
        });
        listView.setMaxWidth(300);
        listView.setMaxHeight(200);

        VBox temp = new VBox(10);
        temp.setPadding(new Insets(20, 30, 20, 20));
        temp.getChildren().addAll(listView, btn);


        return temp;
    }

    private Node monsterSelect() {
		ArrayList<String> description = new ArrayList();
        ComboBox<String> comboBox = new ComboBox<>();
        Button btn = new Button("Empty Database");
        Button addBtn = new Button("Add Monster");
        Button removeBtn = new Button("Remove Selected Monster");
        comboBox = theController.setMonsterList(comboBox);
        comboBox.setPromptText("List");
        
        TextField name = new TextField();
        name.setPromptText("Name");
        TextField lo = new TextField();
        lo.setPromptText("Low Number");
        TextField hi = new TextField();
        hi.setPromptText("High Number");
        TextField desc = new TextField();
        desc.setPromptText("Description");
		Button enterbtn = new Button("Submit");
        
        btn.setOnMouseClicked(e -> {
            theController.clearMonstDatabase();
            refreshPopup();
        });
        
        
        
        VBox temp = new VBox(10);
        temp.setPadding(new Insets(20, 20, 20, 20));
        temp.setLayoutX(250);
        temp.setLayoutY(0);
        temp.getChildren().addAll(comboBox, btn, addBtn, removeBtn);

		addBtn.setOnMouseClicked(e -> {
			temp.getChildren().addAll(name, lo, hi, desc, enterbtn);
           // refreshPopup();
        });
        
        enterbtn.setOnMouseClicked(e -> {
			description.add(name.getText());
            description.add(lo.getText());
            description.add(hi.getText());
            description.add(desc.getText());
            theController.addMonster(description.get(0), description.get(1), description.get(2), description.get(3));
            refreshPopup();
		});
        
        return temp;
    }
    
    

    private Node treasureSelect() {
        ComboBox<String> comboBox = new ComboBox<>();
        comboBox = theController.setTreasureList(comboBox);
        comboBox.setPromptText("Add Treasure");
        VBox temp = new VBox(10);
        temp.setPadding(new Insets(20, 20, 20, 20));
        temp.getChildren().addAll(comboBox);

        return temp;
    }

    private Node setFileMenu() {
        Menu fileMenu = new Menu("File");

        //items
        MenuItem saveFile = new MenuItem("Save File");
        saveFile.setOnAction(e -> theController.save(getUserFile())        );
        fileMenu.getItems().add(saveFile);

        MenuItem loadFile = new MenuItem("Load File");
        loadFile.setOnAction(e -> {
            theController.load(lodFile());
            refreshLeft();
        });
        fileMenu.getItems().add(loadFile);
        //menu bar
        MenuBar menuBar = new MenuBar();
        menuBar.getMenus().add(fileMenu);

        return menuBar;
    }
    public void refreshLeft(){
        Node left = setListView();
        root.setLeft(left);
        primaryStage.show();

    }
    
    public void refreshPopup(){
		Node left = treasureSelect();
		Node right = monsterSelect();
		descriptionPane = createPopUp(500, 500, "");
		descriptionPane.getContent().addAll(right, left);
		descriptionPane.show(primaryStage);
	}

    private Node setCentrePanel(String string) {
        TextArea textArea = new TextArea();
        textArea.setText(string);
        VBox temp = new VBox(10);
        temp.setPadding(new Insets(20, 30, 20, 20));
        temp.getChildren().addAll(textArea);
        return temp;
    }

    private Node setRightButtonPanel() {
        ComboBox<String> comboBox = new ComboBox<>();
        comboBox = theController.setDoorList(comboBox);
        comboBox.setPromptText("List of Doors");

        ComboBox<String> finalComboBox = comboBox;
        comboBox.setOnAction(event -> {

            int selectedIndex = finalComboBox.getSelectionModel().getSelectedIndex();

            //for(Object o : selectedIndices){
                desc = theController.getDoorDesc(selectedIndex);
                Node centre = setCentrePanel(desc);
                root.setCenter(centre);
                primaryStage.setTitle("DND Level Generator");
                primaryStage.show();
            //}
        });

        VBox temp = new VBox(10);
        temp.setPadding(new Insets(20, 20, 20, 20));
        temp.getChildren().addAll(comboBox);

        return temp;
    }


    /* an example of a popup area that can be set to nearly any
    type of node
     */
    private Popup createPopUp(int x, int y, String text) {
        Popup popup = new Popup();
        popup.setX(x);
        popup.setY(y);
        TextArea textA = new TextArea(text);
        popup.getContent().addAll(textA);
        textA.setStyle(" -fx-background-color: white;");
        textA.setMinWidth(800);
        textA.setMinHeight(800);
        return popup;
    }

    /*generic button creation method ensure that all buttons will have a
    similar style and means that the style only need to be in one place
     */
    private Button createButton(String text, String format) {
        Button btn = new Button();
        btn.setText(text);
        btn.setStyle("");
        return btn;
    }

    private String getUserFile(){

        FileChooser fc = new FileChooser();
        File file = fc.showSaveDialog(primaryStage);


        return file.getAbsolutePath();
    }


    private String lodFile(){

        FileChooser fc = new FileChooser();
        File file = fc.showOpenDialog(primaryStage);


        return file.getAbsolutePath();
    }

    private void changeDescriptionText(String text) {
        ObservableList<Node> list = descriptionPane.getContent();
        for (Node t : list) {
            if (t instanceof TextArea) {
                TextArea temp = (TextArea) t;
                temp.setText(text);
            }

        }

    }
    public static void main(String[] args) {
        launch(args);
    }
}
