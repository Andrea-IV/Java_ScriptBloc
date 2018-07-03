package GUI;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.input.*;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import org.json.JSONArray;

import java.util.*;

public class GUIController {

    @FXML
    private FlowPane Blockpane;
    @FXML
    private VBox Architecture;

    private ArrayList<BlockDisplay> SourceList = new ArrayList<BlockDisplay>();
    private BlockDisplay tempBlock;
    private Integer counter = 0;

    @FXML
    protected void initialize() {
        ApiCall api = new ApiCall("http://127.0.0.1:8080/");
        BlockDisplay source;
        Blockpane.setAlignment(Pos.TOP_LEFT);
        try{
            JSONArray jsonObj = new JSONArray(api.ApiGetResponse("block/full?instruction=0"));
            for (int i = 0; i < jsonObj.length(); i++) {
                //System.out.println(jsonObj.getJSONObject(i));
                System.out.println("----------------------------------------------------------------");
                source = new BlockDisplay(new Label((String)jsonObj.getJSONObject(i).get("name")),0);
                source.blockLabel.setStyle("-fx-background-color: slateblue;");
                source.block = new Block((int)jsonObj.getJSONObject(i).get("id"),(String)jsonObj.getJSONObject(i).get("name"),(String)jsonObj.getJSONObject(i).get("description"),(String)jsonObj.getJSONObject(i).get("type"));
                JSONArray Obj = (JSONArray)jsonObj.getJSONObject(i).get("Arguments");

                if(Obj.length() != 0){
                    for(int j = 0; j < Obj.length(); j++){
                        source.block.args.add(new Arguments((int)Obj.getJSONObject(j).get("id"), (String)Obj.getJSONObject(j).get("name"), (String)Obj.getJSONObject(j).get("value"), (String)Obj.getJSONObject(j).get("description")));
                    }
                }
                blockLabelinit(source);
                Blockpane.getChildren().add(source.getBlockLabel());
                Blockpane.setMargin(source.blockLabel, new Insets(10, 10, 0, 10));
            }
        }catch(Exception e){
            System.out.println(e);
        }
        source = new BlockDisplay(new Label("IF"),1);
        BlockDisplay source2 = new BlockDisplay(new Label("Simple"),0);

        blockLabelinit(source);
        blockLabelinit(source2);

        Label target = targetLabelCreation(0);
        target.setPrefWidth(535);
        target.setPrefWidth(438);

        //Blockpane.setAlignment(Pos.TOP_LEFT);
        //Blockpane.getChildren().addAll(source.getBlockLabel(),source2.getBlockLabel());
        Architecture.getChildren().addAll(target);
    }

    private void refreshLabel(BlockDisplay selected, String position, int type){
        counter++;

        int counterPadding = 1;
        int counter_2 = 0;
        BlockDisplay endNew = null;

        ArrayList<Label> ResultList = new ArrayList<Label>();
        BlockDisplay newOne = new BlockDisplay(selected);
        newOne.getBlockLabel().setTextFill(Color.BLACK);

        for (BlockDisplay temp : SourceList) {
            ResultList.add(targetLabelCreation(counter_2));
            if(counter_2 == Integer.parseInt(position)) {
                newOne.getBlockLabel().setStyle("-fx-label-padding: 0 " + (10 * counterPadding) + ";");
                ResultList.add(newOne.getBlockLabel());
                if (newOne.getType() == 1) {
                    counter++;
                    counter_2++;
                    ResultList.add(targetLabelCreation(counter_2));
                    endNew = new BlockDisplay(new Label("END"+selected.g),2);
                    endNew.getBlockLabel().setStyle("-fx-label-padding: 0 " + (10 * counterPadding) + ";");
                    ResultList.add(endNew.getBlockLabel());
                }
                counter_2++;
                ResultList.add(targetLabelCreation(counter_2));
            }
            if(temp.getType() == 2){
                counterPadding--;
            }
            temp.getBlockLabel().setStyle("-fx-label-padding: 0 "+(10 * counterPadding)+";");
            if(temp.getType() == 1){
                counterPadding++;
            }
            ResultList.add(temp.getBlockLabel());
            counter_2++;
        }

        if(counter == 1 || counter_2 == Integer.parseInt(position)) {
            newOne.getBlockLabel().setStyle("-fx-label-padding: 0 "+(10 * counterPadding)+";");
            ResultList.add(targetLabelCreation(counter_2));
            ResultList.add(newOne.getBlockLabel());
            if (newOne.getType() == 1) {
                counter++;
                counter_2++;
                ResultList.add(targetLabelCreation(counter_2));
                endNew = new BlockDisplay(new Label("END"+addString),2);
                endNew.getBlockLabel().setStyle("-fx-label-padding: 0 " + (10 * counterPadding) + ";");
                ResultList.add(endNew.getBlockLabel());
            }
            counter_2++;
        }

        SourceList.add(Integer.parseInt(position), newOne);
        if(newOne.getType() == 1){
            SourceList.add(Integer.parseInt(position)+1, endNew);
        }
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
                //System.out.println("onDragOver");

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
                //System.out.println("onDragEntered");
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
                target.setStyle("-fx-background-color: #505c7c;");
                event.consume();
            }
        });

        target.setOnDragDropped(new EventHandler <DragEvent>() {
            public void handle(DragEvent event) {
                /* data dropped */
                //System.out.println("onDragDropped");
                /* if there is a string data on dragboard, read it and use it */
                Dragboard db = event.getDragboard();
                boolean success = false;
                if (db.hasString()) {
                    //target.setText(db.getString());
                    refreshLabel(tempBlock,target.getText(),tempBlock.getType());
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

    private void blockLabelinit(BlockDisplay source) {
        source.getBlockLabel().setOnDragDetected(new EventHandler<MouseEvent>() {
            public void handle(MouseEvent event) {
                /* drag was detected, start drag-and-drop gesture*/
                //System.out.println("onDragDetected");

                /* allow any transfer mode */
                Dragboard db = source.getBlockLabel().startDragAndDrop(TransferMode.ANY);

                /* put a string on dragboard */
                ClipboardContent content = new ClipboardContent();
                content.putString(source.getBlockLabel().getText());
                db.setContent(content);
                tempBlock = source;
                event.consume();
            }
        });

        source.getBlockLabel().setOnDragDone(new EventHandler<DragEvent>() {
            public void handle(DragEvent event) {
                /* the drag-and-drop gesture ended */
                //System.out.println("onDragDone");
                /* if the data was successfully moved, clear it */
                    /*if (event.getTransferMode() == TransferMode.MOVE) {
                        source.setText("");
                    }*/
                event.consume();
            }
        });
        source.getBlockLabel().setTextFill(Color.WHITE);
    }
}
