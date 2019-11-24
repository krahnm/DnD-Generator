package gui;

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
import javafx.geometry.Insets;

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
            descriptionPane = createPopUp(200, 300, desc);
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
        ComboBox<String> comboBox = new ComboBox<>();
        comboBox = theController.setMonsterList(comboBox);
        comboBox.setPromptText("Add Monster");
        VBox temp = new VBox(10);
        temp.setPadding(new Insets(20, 20, 20, 20));
        temp.setLayoutX(250);
        temp.setLayoutY(0);
        temp.getChildren().addAll(comboBox);

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
            theController.load(getUserFile());
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
        textA.setMinWidth(80);
        textA.setMinHeight(50);
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
        /*TextInputDialog dialog = new TextInputDialog();
        dialog.setTitle("Create File");
        dialog.setHeaderText("Enter your name:");
        dialog.setContentText("Full Path Name and File:");

        Optional<String> result = dialog.showAndWait();
        String entered = "";

        if(result.isPresent()){
            entered = result.get();
        }
        return entered;*/
        JButton btn = new JButton();
        JFileChooser fc = new JFileChooser();
       // File file = fc.showSaveDialog(btn);
        fc.setCurrentDirectory(new java.io.File("."));
        fc.setDialogTitle("File Chooser");
        fc.setFileSelectionMode(JFileChooser.FILES_AND_DIRECTORIES);
        if( fc.showSaveDialog(btn) == JFileChooser.APPROVE_OPTION){
            System.out.println(fc.getSelectedFile().getAbsolutePath());

        }
        return fc.getSelectedFile().getAbsolutePath();
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
