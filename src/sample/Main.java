package sample;

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


public class Main extends Application {



    private Controller theController;
    private BorderPane root;  //the root element of this GUI
    private Popup descriptionPane;
    private Stage primaryStage;  //The stage that is passed in on initialization

    /*a call to start replaces a call to the constructor for a JavaFX GUI*/
    @Override
    public void start(Stage assignedStage) {
        /*Initializing instance variables */
        /*theController = new Controller(this);*/
        primaryStage = assignedStage;
        /*Border Panes have  top, left, right, center and bottom sections */
       root = setUpRoot();
       descriptionPane = createPopUp(200, 300, "Example Description of something");


        Scene scene = new Scene(root, 1200, 800);
        primaryStage.setTitle("Hello GUI Demo");
        primaryStage.setScene(scene);
        primaryStage.show();

    }

    private BorderPane setUpRoot() {
        BorderPane temp = new BorderPane();
        temp.setTop(new Label("The name or identifier of the thing below"));
        Node left = setListView();  //separate method for the left section
        Node right = setRightButtonPanel();
        Node top = setFileMenu();
        temp.setLeft(left);
        temp.setRight(right);
        temp.setTop(top);
        //TilePane room = createTilePanel();
        GridPane room = null;/*new ChamberView(4,4);*/
        temp.setCenter(room);
        return temp;
    }

    private Node setListView() {
        ListView<String> listView = new ListView<>();
        listView.getItems().addAll("NUMMA 1", "NUMMA 2", "NUMMA 3", "NUMMA 4", "NUMMA 5");
        listView.setMaxWidth(300);
        listView.setMaxHeight(200);

        return listView;
    }

    private Node setFileMenu() {

        //TODO: Add clickabillity https://www.youtube.com/watch?v=AP4e6Lxncp4

        Menu fileMenu = new Menu("File");

        //items
        fileMenu.getItems().add(new MenuItem("Save File"));
        fileMenu.getItems().add(new MenuItem("Load File"));

        //menu bar
        MenuBar menuBar = new MenuBar();
        menuBar.getMenus().add(fileMenu);

        return menuBar;
    }

    private Node setRightButtonPanel() {
        ComboBox<String> comboBox = new ComboBox<>();
        comboBox.getItems().addAll("WHAAAA", "BAYou", "Best Buy", "Popeyes");
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
            /*theController.reactToButton();*/
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
