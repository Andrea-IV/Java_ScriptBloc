package GUI;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.VBox;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import javax.swing.*;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.time.LocalDate;
import java.util.ArrayList;

public class GUIHomepageController {

    public GUIController test;

    @FXML
    public static Stage stage;

    @FXML
    private FlowPane Blockpane;

    @FXML
    public VBox Architecture;

    private BlockDisplay tempBlock;

    @FXML
    private void loadProject() {
        FileChooser fileChooser = new FileChooser();
        fileChooser.setInitialDirectory(new File("C:\\Users\\ycapel\\Documents\\ESGI_cours\\S2\\projet_annuel\\Java_ScriptBloc\\JAVASCRIPTBLOCKS"));
        fileChooser.setTitle("Open Resource File");
        File file = fileChooser.showOpenDialog(GUIHomepageController.stage);
        if(file != null) {
            try {
                System.out.println(file.toString());
                String fl = new String(Files.readAllBytes(file.toPath()));
                System.out.println(fl);

                JSONObject jsonObject = new JSONObject(fl);
                String[] names = jsonObject.getNames(jsonObject);
                JSONArray ja  = jsonObject.toJSONArray(new JSONArray(names));
//                System.out.println(ja.toString());

                FXMLLoader loader = new FXMLLoader();
                loader.setLocation(getClass().getResource("mainWindow2.fxml"));
                Parent scene = loader.load();
                GUIController gc = loader.getController();
                gc.setArchitecture((JSONArray) ja.get(0));
                Stage stage = new Stage();
                stage.setScene(new Scene(scene));
                stage.setTitle("untitled");
                stage.show();

            } catch(JSONException e) {
                System.out.println(e);
            } catch (IOException e) {
                System.out.println(e);
            }
        }
//        startNewProject();
    }

    @FXML
    private void convertProject() {
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Open Resource File");
        File file = fileChooser.showOpenDialog(GUIHomepageController.stage);
        if(file != null) {
            System.out.println(file.toString());
        }

        ApiCall ac = new ApiCall("http://127.0.0.1/");
    }

    @FXML
    private void startNewProject() throws IOException {
        GUIHomepageController.stage = new Stage();
        Parent new_scene = FXMLLoader.load(getClass().getResource("mainWindow2.fxml"));
        GUIHomepageController.stage.setScene(new Scene(new_scene));
        GUIHomepageController.stage.setTitle("untitled.sm");
        GUIHomepageController.stage.show();
    }
}
