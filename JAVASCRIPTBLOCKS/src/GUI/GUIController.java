package GUI;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.input.*;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;

import java.util.ArrayList;

public class GUIController {

    @FXML
    private FlowPane Blockpane;
    @FXML
    private VBox Architecture;

    ArrayList<Label> SourceList = new ArrayList<Label>();

    Integer counter = 0;

    @FXML
    protected void initialize() {
        System.out.println("init Flow Pane");
        Label source = new Label("DRAG ME");
        source.setTextFill(Color.WHITE);
        Label target = targetLabelCreation(0);
        target.setPrefWidth(535);
        target.setPrefWidth(438);

        source.setOnDragDetected(new EventHandler <MouseEvent>() {
            public void handle(MouseEvent event) {
                /* drag was detected, start drag-and-drop gesture*/
                System.out.println("onDragDetected");

                /* allow any transfer mode */
                Dragboard db = source.startDragAndDrop(TransferMode.ANY);

                /* put a string on dragboard */
                ClipboardContent content = new ClipboardContent();
                content.putString(source.getText());
                db.setContent(content);

                event.consume();
            }
        });

        source.setOnDragDone(new EventHandler <DragEvent>() {
            public void handle(DragEvent event) {
                /* the drag-and-drop gesture ended */
                System.out.println("onDragDone");
                /* if the data was successfully moved, clear it */
                /*if (event.getTransferMode() == TransferMode.MOVE) {
                    source.setText("");
                }*/

                event.consume();
            }
        });

        Blockpane.setAlignment(Pos.TOP_LEFT);
        Blockpane.getChildren().addAll(source);
        Architecture.getChildren().addAll(target);
    }

    private void refreshLabel(String addString, String position){
        counter++;

        int counter_2 = 0;

        ArrayList<Label> ResultList = new ArrayList<Label>();
        Label newOne = new Label(addString+counter);
        newOne.setTextFill(Color.BLACK);

        for (Label temp : SourceList) {
            ResultList.add(targetLabelCreation(counter_2));
            if(counter_2 == Integer.parseInt(position)){
                ResultList.add(newOne);
                counter_2++;
                ResultList.add(targetLabelCreation(counter_2));
            }
            ResultList.add(temp);
            counter_2++;
        }

        if(counter == 1 || counter_2 == Integer.parseInt(position)) {
            ResultList.add(targetLabelCreation(counter_2));
            ResultList.add(newOne);
            counter_2++;
        }

        SourceList.add(Integer.parseInt(position), newOne);
        Label last = targetLabelCreation(counter_2);
        last.setPrefHeight(535);
        ResultList.add(last);

        Architecture.getChildren().clear();
        Architecture.getChildren().addAll(ResultList);
    }

    private Label targetLabelCreation(Integer number){
        Label target = new Label(number.toString());
        target.setPrefWidth(438);
        target.setMinHeight(10);
        target.setFont(new Font(0));
        target.setOnDragOver(new EventHandler <DragEvent>() {
            public void handle(DragEvent event) {
                /* data is dragged over the target */
                System.out.println("onDragOver");

                /* accept it only if it is  not dragged from the same node
                 * and if it has a string data */
                if (event.getGestureSource() != target &&
                        event.getDragboard().hasString()) {
                    /* allow for both copying and moving, whatever user chooses */
                    event.acceptTransferModes(TransferMode.COPY_OR_MOVE);
                }

                event.consume();
            }
        });

        target.setOnDragEntered(new EventHandler <DragEvent>() {
            public void handle(DragEvent event) {
                /* the drag-and-drop gesture entered the target */
                System.out.println("onDragEntered");
                /* show to the user that it is an actual gesture target */
                if (event.getGestureSource() != target &&
                        event.getDragboard().hasString()) {
                    target.setStyle("-fx-background-color: black;");
                }

                event.consume();
            }
        });

        target.setOnDragExited(new EventHandler <DragEvent>() {
            public void handle(DragEvent event) {
                /* mouse moved away, remove the graphical cues */
                target.setTextFill(Color.BLACK);

                event.consume();
            }
        });

        target.setOnDragDropped(new EventHandler <DragEvent>() {
            public void handle(DragEvent event) {
                /* data dropped */
                System.out.println("onDragDropped");
                /* if there is a string data on dragboard, read it and use it */
                Dragboard db = event.getDragboard();
                boolean success = false;
                if (db.hasString()) {
                    //target.setText(db.getString());
                    refreshLabel(db.getString(),target.getText());
                    success = true;
                }
                /* let the source know whether the string was successfully
                 * transferred and used */
                event.setDropCompleted(success);
                event.consume();
            }
        });
        return target;
    }
}
