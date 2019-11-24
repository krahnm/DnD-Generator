package gui;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.stage.Stage;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.geometry.Orientation;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.*;
import javafx.stage.Popup;
import javafx.geometry.Insets;


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
int i = 0;
            for(Object o : selectedIndices){
                desc = theController.getChambPassDesc(finalListView.getSelectionModel().getSelectedIndex());
                //System.out.println(o + " t " + theController.getChambPassDesc(finalListView.getSelectionModel().getSelectedIndex()));
                Node centre = setCentrePanel(desc);
                root.setCenter(centre);
                //Scene scene = new Scene(root, 1200, 800);
                primaryStage.setTitle("DND Level Generator");
                //primaryStage.setScene(scene);
                primaryStage.show();

                i++;
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

        //TODO: Add clickabillity https://www.youtube.com/watch?v=AP4e6Lxncp4

        Menu fileMenu = new Menu("File");

        //items
        MenuItem saveFile = new MenuItem("Save File");
        saveFile.setOnAction(e -> System.out.println("Save File"));
        fileMenu.getItems().add(saveFile);
        MenuItem loadFile = new MenuItem("Load File");
        loadFile.setOnAction(e -> System.out.println("Load File"));
        fileMenu.getItems().add(loadFile);
        //menu bar
        MenuBar menuBar = new MenuBar();
        menuBar.getMenus().add(fileMenu);

        return menuBar;
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
        VBox temp = new VBox(10);
        temp.setPadding(new Insets(20, 20, 20, 20));
        temp.getChildren().addAll(comboBox);

        return temp;
    }

    private Node setLeftButtonPanel() {
        /*this method should be broken down into even smaller methods, maybe one per button*/
        VBox temp = new VBox();
        temp.setStyle("-fx-padding: 100;" +
                "-fx-border-style: solid inside;" +
                "-fx-border-width: 2;" +
                "-fx-border-insets: 50;" +
                "-fx-border-radius: 5;" +
                "-fx-border-color: blue;");
        /*This button listener is an example of a button changing something
        in the controller but nothing happening in the view */

        Button firstButton = createButton("Hello world", "-fx-background-color: #ff0000; -fx-background-radius: 10, 10, 10, 10;");
        firstButton.setOnAction((ActionEvent event) -> {
            theController.reactToButton();
        });
        temp.getChildren().add(firstButton);

        /*This button listener is only changing the view and doesn't need
        to contact the controller
         */
        Button showButton = createButton("Show Description", "-fx-background-color: #FFFFFF; ");
        showButton.setOnAction((ActionEvent event) -> {
            descriptionPane.show(primaryStage);
        });
        temp.getChildren().add(showButton);
        /*this button listener is an example of getting data from the controller */
        Button hideButton = createButton("Hide Description", "-fx-background-color: #FFFFFF; ");
        hideButton.setOnAction((ActionEvent event) -> {
            descriptionPane.hide();
            /*changeDescriptionText(theController.getNewDescription());*/
        });
        temp.getChildren().add(hideButton);
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
