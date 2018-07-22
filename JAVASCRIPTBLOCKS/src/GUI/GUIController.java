package GUI;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Pos;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.Label;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuItem;
import javafx.scene.control.TextField;
import javafx.scene.input.*;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import plugins.*;

import java.awt.ScrollPane;
import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.*;
import java.util.stream.Stream;

public class GUIController {

    private ArrayList<BlockDisplay> array_bd;

    @FXML
    private AnchorPane architectureBackground;
    @FXML
    private BorderPane wholeBackground;
    @FXML
    private AnchorPane listBackground;
    @FXML
    private MenuItem menu_save;
    @FXML
    private MenuItem menu_export_bash;
    @FXML
    private MenuItem menu_export_batch;
    @FXML
    private MenuItem menu_open;
    @FXML
    private MenuItem menu_close;
    @FXML
    private GridPane Blockpane;
    @FXML
    private VBox Architecture;
    @FXML
    private Text description;
    @FXML
    private Label blockTitle;
    @FXML
    private GridPane argBox;
    @FXML
    private Menu pluginMenu;
    @FXML
    private MenuItem loadPlugins;

    public Plugin[] plugins = null;

    public ArrayList<Path> filesjar = new ArrayList<Path>();
    public ArrayList pluginsLoad = new ArrayList();

    private ArrayList<BlockDisplay> SourceList = new ArrayList<BlockDisplay>();
    private BlockDisplay tempBlock;
    private int cnt = 0;

    @FXML
    @MethodInfo(name = "initialize()", date = "05/07/18", arguments = "None", comments = "Function called on the opening of the window, initialize the component, call the BDD to get the blocks", returnValue="None" ,revision = 1)
    public void initialize() {
        wholeBackground.setStyle("-fx-background-color:"+GUI.conf.getBackgroundColor()+";");
        Architecture.setStyle("-fx-background-color:"+GUI.conf.getListColor()+";");
        architectureBackground.setStyle("-fx-background-color:"+GUI.conf.getListColor()+";");
        listBackground.setStyle("-fx-background-color:"+GUI.conf.getListColor()+";");
        array_bd = new ArrayList<BlockDisplay>();
        ApiCall api = new ApiCall("http://127.0.0.1:8080/");
        BlockDisplay source;
        try{
            JSONArray jsonObj = new JSONArray(api.ApiGetResponse("block/full?instructions=0"));
            int counter = 0;
            for (int i = 0; i < jsonObj.length(); i++) {
                source = new BlockDisplay(new Label((String)jsonObj.getJSONObject(i).get("name")),0);
                source.blockLabel.setStyle("-fx-background-color: "+GUI.conf.getBlockColor()+";-fx-background-radius: 50;");
                source.blockLabel.setPrefWidth(150);
                source.blockLabel.setAlignment(Pos.CENTER);
                source.block = new Block((int)jsonObj.getJSONObject(i).get("id"),(String)jsonObj.getJSONObject(i).get("name"),(String)jsonObj.getJSONObject(i).get("description"),(String)jsonObj.getJSONObject(i).get("type"));
                if(!((String)jsonObj.getJSONObject(i).get("type")).equals("SIMPLE")){
                    source.setType(1);
                }
                JSONArray Obj = (JSONArray)jsonObj.getJSONObject(i).get("Arguments");

                if(Obj.length() != 0){
                    for(int j = 0; j < Obj.length(); j++){
                        String temp = (String)Obj.getJSONObject(j).get("keyValue");
                        String key = temp.substring(1, temp.length() - 1);
                        source.block.args.add(new Arguments((int)Obj.getJSONObject(j).get("id"), (String)Obj.getJSONObject(j).get("name"), key, (String)Obj.getJSONObject(j).get("description")));
                    }
                }
                blockLabelInit(source);
                Blockpane.add(source.getBlockLabel(),0,counter);
                counter++;
                array_bd.add(source);
            }
        }catch(Exception e){
            System.out.println(e);
        }

        loadAutoPlugins();
        loadPlugins.addEventHandler(javafx.event.ActionEvent.ACTION, event -> {
            pluginMenu.getItems().remove(1, pluginMenu.getItems().size());
            this.filesjar.clear();
            loadAutoPlugins();
        });

        Label target = targetLabelCreation(0);
        target.setPrefHeight(573);
        target.setPrefWidth(475);
        Architecture.getChildren().addAll(target);

        menu_save.setAccelerator(new KeyCodeCombination(KeyCode.S, KeyCombination.CONTROL_DOWN));
        menu_export_bash.setAccelerator(new KeyCodeCombination(KeyCode.L, KeyCombination.CONTROL_DOWN));
        menu_export_batch.setAccelerator(new KeyCodeCombination(KeyCode.W, KeyCombination.CONTROL_DOWN));
        menu_open.setAccelerator(new KeyCodeCombination(KeyCode.O, KeyCombination.CONTROL_DOWN));
    }

    @MethodInfo(name = "createContentLabel(String name, int type, int counterPadding)", date = "20/07/18", arguments = "1: String name, Name of the content label that will be created, 2: int type, (2) end of a block (3) start of arguments (4) end of arguments", comments = "Initialize a block label that will be used for a condition block", returnValue="BlockDisplay, the display block initialized" ,revision = 1)
    public BlockDisplay createContentLabel(String name, int type, int counterPadding){
        BlockDisplay endNew = new BlockDisplay(new Label(name),type);
        endNew.getBlockLabel().setStyle("-fx-label-padding: 0 0 0 "+(20 * counterPadding)+";-fx-background-insets:0 0 0 "+(20 * counterPadding)+";-fx-pref-width: "+((20 * counterPadding)+150)+";-fx-background-color:  #1A1A1A; -fx-text-fill: white; -fx-background-radius: 50; -fx-text-alignment: center;");
        endNew.getBlockLabel().setAlignment(Pos.CENTER);
        return endNew;
    }

    @MethodInfo(name = "refreshLabel(BlockDisplay selected, String position)", date = "05/07/18", arguments = "1: BlockDisplay selected, the block move on a target in the architecture, 2: String position, the position sent by the target", comments = "used to display the architecture when a block is added or when a script is loaded", returnValue="None" ,revision = 1)
    public void refreshLabel(BlockDisplay selected, String position){
        int counterPadding = 0;
        BlockDisplay endNew = null;

        ArrayList<BlockDisplay> container = new ArrayList<BlockDisplay>();
        BlockDisplay newOne = new BlockDisplay(selected);

        selected.getBlockLabel().setTextFill(Color.BLACK);

        newOne.getBlockLabel().setStyle("-fx-label-padding: 0 0 0 "+(20 * counterPadding)+";-fx-background-insets:0 0 0 "+(20 * counterPadding)+";-fx-pref-width: "+((20 * counterPadding)+150)+";-fx-background-color:  #1A1A1A; -fx-text-fill: white; -fx-background-radius: 50; -fx-text-alignment: center;");
        newOne.getBlockLabel().setAlignment(Pos.CENTER);
        if (newOne.getType() == 1) {
            String[] insideContainer = newOne.block.getType().split("\\|");
            container = new ArrayList<BlockDisplay>();
            for(String inside : insideContainer){
                container.add(createContentLabel(inside, 3, counterPadding));
                container.add(createContentLabel("END"+inside, 4, counterPadding));
            }
            counterPadding--;
            endNew = createContentLabel("END"+selected.block.getName(), 2, counterPadding);
        }

        addAction(newOne);
        SourceList.add(Integer.parseInt(position), newOne);
        if(newOne.getType() == 1){
            int counterContained = 1;
            for(BlockDisplay contained : container){
                SourceList.add(Integer.parseInt(position)+counterContained, contained);
                counterContained++;
            }
            SourceList.add(Integer.parseInt(position)+counterContained, endNew);
        }
        reDisplay();
    }

    @MethodInfo(name = "targetLabelCreation(Integer number)", date = "05/07/18", arguments = "1: Integer number, the position of the target block", comments = "Initialize a Label that will be used as a target for any movement in the architecture", returnValue="Label target, the label initialized with all the handlers" ,revision = 1)
    public Label targetLabelCreation(Integer number){
        Label target = new Label(number.toString());
        target.setPrefWidth(477);
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
                target.setStyle("-fx-background-color: #5e5e5e;");
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
                    if(db.getString().contains("#COPY&PASTE#")){
                        copyPaste(Integer.parseInt(db.getString().split("\\|")[1]),Integer.parseInt(target.getText()));
                    }else{
                        refreshLabel(tempBlock,target.getText());
                    }
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

    @MethodInfo(name = "blockLabelInit(BlockDisplay source)", date = "05/07/18", arguments = "1: BlockDisplay source, the block on the left panel used to create the architecture", comments = "Initialize the BlockDisplay handlers", returnValue="None" ,revision = 1)
    public void blockLabelInit(BlockDisplay source) {
        source.getBlockLabel().setOnDragDetected(new EventHandler<MouseEvent>() {
            public void handle(MouseEvent event) {
                /* drag was detected, start drag-and-drop gesture*/
                //System.out.println("onDragDetected");
                tempBlock = new BlockDisplay(source);
                /* allow any transfer mode */
                Dragboard db = source.getBlockLabel().startDragAndDrop(TransferMode.ANY);

                /* put a string on dragboard */
                ClipboardContent content = new ClipboardContent();
                content.putString(source.getBlockLabel().getText());
                db.setContent(content);

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

    @MethodInfo(name = "addAction(BlockDisplay newOne)", date = "05/07/18", arguments = "1: BlockDisplay newOne, the new block added in the architecture", comments = "Initialize the handlers for the new block in the architecture", returnValue="None" ,revision = 1)
    public void addAction(BlockDisplay newOne){
        newOne.getBlockLabel().setOnDragDetected(new EventHandler<MouseEvent>() {
            public void handle(MouseEvent event) {
                tempBlock = new BlockDisplay(newOne);
                Dragboard db = newOne.getBlockLabel().startDragAndDrop(TransferMode.ANY);

                ClipboardContent content = new ClipboardContent();
                System.out.println(System.identityHashCode(newOne));
                content.putString("#COPY&PASTE#|"+System.identityHashCode(newOne) );
                db.setContent(content);

                event.consume();
            }
        });

        newOne.getBlockLabel().setOnDragDone(new EventHandler<DragEvent>() {
            public void handle(DragEvent event) {
                event.consume();
            }
        });

        newOne.blockLabel.setOnMouseClicked(new EventHandler<MouseEvent>(){
            @Override
            public void handle(MouseEvent t) {
                description.setText(newOne.block.getDescription());
                blockTitle.setText(newOne.block.getName());
                Label tempLabel;
                TextField tempField;
                TextArea tempArea;
                Tooltip tooltip;
                RowConstraints rc;
                argBox.getChildren().clear();
                argBox.getRowConstraints().clear();
                int counter = 0;
                for(Arguments argsObj : newOne.block.args){

                    tempLabel = new Label(argsObj.getName());
                    tooltip = new Tooltip();

                    tooltip.setText(argsObj.getDescription());
                    tempLabel.setTooltip(tooltip);
                    tempLabel.setTextFill(Color.web("#a0a2ab"));
                    tempLabel.setPrefWidth(280);
                    argBox.add(tempLabel, 0,counter);
                    counter++;

                    if(argsObj.getKey().equals("#custom")){
                        tempArea = new TextArea();
                        tempArea.setPrefWidth(280);
                        tempLabel.setPrefHeight(280);
                        tempArea.setText(argsObj.getValue());
                        tempArea.textProperty().addListener((observable, oldValue, newValue) -> {
                            argsObj.setValue(newValue);
                        });
                        argBox.add(tempArea, 0, counter);
                    }else{
                        tempField = new TextField();
                        tempField.setPrefWidth(280);
                        tempField.setText(argsObj.getValue());
                        tempField.textProperty().addListener((observable, oldValue, newValue) -> {
                            argsObj.setValue(newValue);
                        });
                        argBox.add(tempField, 0, counter);
                    }
                    counter++;
                }
            }
        });

        ContextMenu contextMenu = new ContextMenu();

        MenuItem item1 = new MenuItem("Delete");
        item1.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                remove(newOne);
            }
        });

        contextMenu.getItems().add(item1);

        // When user right-click on Circle
        newOne.blockLabel.setOnContextMenuRequested(new EventHandler<ContextMenuEvent>() {

            @Override
            public void handle(ContextMenuEvent event) {

                contextMenu.show(newOne.blockLabel, event.getScreenX(), event.getScreenY());
            }
        });
    }

    private BlockDisplay findBlockById(int id) {
        for(BlockDisplay bd : array_bd) {
            if(bd.block.getId() == id) {
                return bd;
            }
        }

        return null;
    }

    public void setArchitecture (JSONArray json) throws JSONException {
        int pos_object;

        for(int i = 0; i < json.length(); i++) {
            JSONObject jso = json.getJSONObject(i);
            Iterator keys = jso.keys();
            int j = 0;
            int arg = 1;
            int id = 0;
            if(jso.has("id")) {
                id = (int) jso.get("id");
            } else {
                continue;
            }
            BlockDisplay bd = findBlockById(id);

            refreshLabel(bd, ""+cnt);
            if(bd.getType() == 1) cnt+=2;
            else cnt++;

            for(pos_object = SourceList.size() - 1; pos_object >= 0; pos_object--) {
                if(SourceList.get(pos_object).getType() == 1 || SourceList.get(pos_object).getType() == 0) {
                    break;
                }
            }

            while(keys.hasNext()) {
                String key = (String) keys.next();
                if(jso.get(key) instanceof JSONObject) {
                    JSONObject value = (JSONObject) jso.get(key);
                    Iterator testo= value.keys();
                    while(testo.hasNext()) {
                        String k = (String) testo.next();
                        if(value.get(k) instanceof JSONArray) {
                            setArchitecture(value.getJSONArray(k));
                            cnt+=2;
                        } else {

                            for(int y = 0; y < SourceList.get(pos_object).block.args.size(); y++) {
                                if(SourceList.get(pos_object).block.args.get(y).getKey().equals(k)) {
                                    SourceList.get(pos_object).block.args.get(y).setValue(value.get(k).toString());
                                }
                            }
                        }
                    }
                }
            }
        }
    }

    private JSONObject buildJSON(ArrayList<BlockDisplay> bdl, boolean isRoot) throws JSONException {
        System.out.println("BuilJSON");
        JSONObject jo = new JSONObject();
        JSONObject obj = new JSONObject();
        JSONObject args;

        for(int x = 0; x < bdl.size(); x++) {
            obj = new JSONObject();
            args = new JSONObject();

            if(bdl.get(x).getType() == 0 || bdl.get(x).getType() == 1) {
                obj.put("id", bdl.get(x).block.getId());
                for(int y = 0; y < bdl.get(x).block.args.size(); y++) {
                    args.put(bdl.get(x).block.args.get(y).getKey(), bdl.get(x).block.args.get(y).getValue());
                }
            }

            if(bdl.get(x).getType() == 1) {
                int block_counter =  0;
                int cnt = 0;
                ArrayList<BlockDisplay> sl = new ArrayList<BlockDisplay>();
                x++;
                while(bdl.get(x).getType() != 2) {

                    sl.clear();
                    if(bdl.get(x).getType() == 3) {
                        cnt = 1;
                        x++;
                        while(true) {
                            if(bdl.get(x).getType() == 3) cnt++;
                            if(bdl.get(x).getType() == 4) cnt--;
                            if(cnt == 0) {
                                break;
                            }

                            sl.add(bdl.get(x));
                            x++;
                        }

                        for(int a = 0; a < sl.size(); a++) {
                            System.out.println("a = " + sl.get(a).blockLabel);
                        }
                        if(!sl.isEmpty()) args.append("#blocks" + block_counter, buildJSON(sl, false));
                        else args.append("#blocks" + block_counter, new JSONObject().put("null",  ""));
                        block_counter++;
                    }

                    x++;
                }
            }

            obj.put("arguments", args);
            if(isRoot) jo.append("blocks", obj);
        }

        if(isRoot)
            return jo;
        else return obj;
    }

    @FXML
    private void saveFile() {
        JSONObject jo = new JSONObject();

        try {
            jo =  buildJSON(SourceList, true);
            System.out.println(jo);
        } catch(JSONException e) {
            System.out.println("erreur builder " + e);
        }

        FileChooser fileChooser = new FileChooser();
        fileChooser.setInitialDirectory(new File(GUI.conf.getScriptPath()));
        fileChooser.setTitle("Save file");
        FileChooser.ExtensionFilter extFilter = new FileChooser.ExtensionFilter("SM files (*.sm)", "*.sm");
        fileChooser.getExtensionFilters().add(extFilter);
        File file = fileChooser.showSaveDialog(GUI.stage);
        if(file != null) {
            try {
                PrintWriter writer = new PrintWriter(file.toString(), "UTF-8");
                writer.println(jo.toString(4));
                writer.close();
            } catch (FileNotFoundException e) {
                System.out.println(e);
            } catch (UnsupportedEncodingException e) {
                System.out.println(e);
            } catch (JSONException e) {
                System.out.println(e);
            }
        }
    }

    @MethodInfo(name = "remove(BlockDisplay newOne)", date = "05/07/18", arguments = "1: BlockDisplay newOne, the block which the user choose to delete", comments = "Delete the block from SourceList", returnValue="None" ,revision = 1)
    public void remove(BlockDisplay newOne){
        int counter_1 = 0;
        for(int i = 0; i < SourceList.size() ;i++){
            if(newOne == SourceList.get(i)){
                if(SourceList.get(i).getType() == 1){
                    counter_1=1;
                    SourceList.remove(i);
                    while(true){
                        if(SourceList.size() == i){
                            break;
                        }
                        switch(SourceList.get(i).getType()){
                            case 1:
                                SourceList.remove(i);
                                counter_1++;
                                break;
                            case 2:
                                SourceList.remove(i);
                                counter_1--;
                                break;
                            default :
                                SourceList.remove(i);
                                break;
                        }
                        if(counter_1 == 0){
                            break;
                        }
                    }
                }else{
                    SourceList.remove(i);
                }
                break;
            }
        }
        reDisplay();
    }

    @MethodInfo(name = "reDisplay()", date = "05/07/18", arguments = "", comments = "Regenerate the architecture based on the SourceList", returnValue="None" ,revision = 1)
    public void reDisplay(){
        int counterPadding = 0;
        int counter_2 = 0;

        ArrayList<Label> ResultList = new ArrayList<Label>();

        for (BlockDisplay temp : SourceList) {
            if(temp.getType() != 3 && temp.getType() != 2) {
                ResultList.add(targetLabelCreation(counter_2));
            }

            if(temp.getType() == 2 || temp.getType() == 4){
                counterPadding--;
            }
            temp.getBlockLabel().setStyle("-fx-label-padding: 0 0 0 "+(20 * counterPadding)+";-fx-background-insets:0 0 0 "+(20 * counterPadding)+";-fx-pref-width: "+((20 * counterPadding)+150)+";-fx-background-color:  "+GUI.conf.getBlockColor()+"; -fx-text-fill: white; -fx-background-radius: 50; -fx-text-alignment: center;");
            if(temp.getType() == 1 || temp.getType() == 3){
                counterPadding++;
            }
            ResultList.add(temp.getBlockLabel());
            counter_2++;
        }

        Label last = targetLabelCreation(counter_2);
        last.setPrefHeight(535);
        ResultList.add(last);

        Architecture.getChildren().clear();
        Architecture.getChildren().addAll(ResultList);
    }

    @MethodInfo(name = "copyPaste(String newOne, int position)", date = "05/07/18", arguments = "1: String newOne, the block identifier, 2: int position, the position where the block will be moved", comments = "Move the block and is Children in the SourceList", returnValue="None" ,revision = 1)
    public void copyPaste(int newOne, int position){
        int start = 0;
        int end = 0;
        int counter_1;
        for(int i = 0; i < SourceList.size() ;i++){
            if(newOne == System.identityHashCode(SourceList.get(i))){
                start = i;
                System.out.println(SourceList.get(i).getBlockLabel().getText());
                if(SourceList.get(i).getType() == 1){
                    counter_1=1;
                    while(true){
                        System.out.println("-- I ");
                        System.out.println(i);
                        i++;
                        if(SourceList.size() == i){
                            break;
                        }
                        switch(SourceList.get(i).getType()){
                            case 1:
                                counter_1++;
                                break;
                            case 2:
                                counter_1--;
                                break;
                        }
                        if(counter_1 == 0){
                            break;
                        }
                    }
                }
                end = i;
                break;
            }
        }
        counter_1 = 0;
        if(position == SourceList.size()){
            for(int j = start; j <= end ;j++) {
                SourceList.add(SourceList.remove(start));
            }
        }else{
            for(int i = 0; i < SourceList.size() ;i++) {
                if(counter_1 == position) {
                    if(position <= start) {
                        for (int j = start; j <= end; j++) {
                            SourceList.add(i, SourceList.remove(j));
                            i++;
                        }
                        break;
                    }else{
                        for (int j = start; j <= end; j++) {
                            SourceList.add(i-1, SourceList.remove(start));
                        }
                        break;
                    }
                }
                counter_1++;
            }
        }
        reDisplay();
    }

    @FXML
    @MethodInfo(name = "openConfigurationMenu()", date = "20/07/18", arguments = "None", comments = "open the conf window", returnValue="None" ,revision = 1)
    public void openConfigurationMenu() throws IOException{
        GUI.stage = new Stage();
        Parent new_scene = FXMLLoader.load(getClass().getResource("config_window.fxml"));
        GUI.stage.setScene(new Scene(new_scene));
        GUI.stage.setTitle("Configuration");
        GUI.stage.setResizable(false);
        GUI.stage.show();
    }

    @FXML
    private void unixChoice() {
        try {
            GUIHomepageController.export_file = buildJSON(SourceList, true);
            ExportController ec = new ExportController();
            ec.getConvertedFile("unix");
        } catch(JSONException e) {
            System.out.println(e);
        }
    }

    @FXML
    private void windowsChoice() {
        try {
            GUIHomepageController.export_file = buildJSON(SourceList, true);
            ExportController ec = new ExportController();
            ec.getConvertedFile("windows");
        } catch(JSONException e) {
            System.out.println(e);
        }
    }

    @FXML
    private void openFile() {
        GUIHomepageController ghc = new GUIHomepageController();
        ghc.loadProject();
    }

    @MethodInfo(name = "loadAutoPlugins()", date = "13/07/18", arguments = "None", comments = "Load automatically the jar plugins from the toLoad folder", returnValue="None" ,revision = 1)
    public void loadAutoPlugins(){
        try {
            plugins = PluginLoader.initAsPlugin(PluginLoader.loadDirectoryC("toLoad", "config.cfg"));
        } catch (InstantiationException e) {
            e.printStackTrace();
        } catch (IllegalAccessException f) {
            f.printStackTrace();
        } catch (ClassNotFoundException g) {
            g.printStackTrace();
        } catch (IOException h) {
            h.printStackTrace();
        }

        try{
            Stream<Path> paths = Files.walk(Paths.get("toLoad"));
            int[] idx = { 0 };
            //paths.forEach(file -> {

            Iterable<Path> iterable = paths::iterator;
            for(Path file : iterable){
                if (Files.isRegularFile(file)) {
                    Path plugPath = file.toAbsolutePath();
                    this.filesjar.add(plugPath);
                    MenuItem np = new MenuItem();
                    np.setText(plugPath.getFileName().toString().substring(0, plugPath.getFileName().toString().indexOf(".")));
                    np.setId(plugPath.getFileName().toString().substring(0, plugPath.getFileName().toString().indexOf(".")));
                    System.out.println(np.getId());

                    np.setOnAction(event -> {
                        runPlugin(plugins[idx[0]++]);
                    });
                    pluginMenu.getItems().add(np);
                }

            }
        }catch(IOException e){
            e.printStackTrace();
        }


    }

    @MethodInfo(name = "runPlugin()", date = "13/07/18", arguments = "None", comments = "Runs the selected plugin from its run method", returnValue="None" ,revision = 1)
    public void runPlugin(Plugin plugin) {
        plugin.run();
    }
}
