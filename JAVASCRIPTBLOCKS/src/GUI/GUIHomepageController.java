package GUI;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;

public class GUIHomepageController {

    public static JSONObject export_file;
    @FXML
    private AnchorPane anchorPane;

    @FXML
    public void initialize() {
        anchorPane.setStyle("-fx-background-color:"+GUI.conf.getBackgroundColor()+";");
    }


    @FXML
    public void loadProject() {
        FileChooser fileChooser = new FileChooser();
        fileChooser.setInitialDirectory(new File(GUI.conf.getScriptPath()));
        fileChooser.setTitle("Open SM File");
        File file = fileChooser.showOpenDialog(GUI.stage);
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
                GUI.stage = new Stage();
                GUI.stage.setScene(new Scene(scene));
                GUI.stage.setResizable(false);
                GUI.stage.setTitle("Script::Blocks");
                GUI.stage.show();

            } catch(JSONException e) {
                System.out.println("load project " + e);
            } catch (IOException e) {
                System.out.println(e);
            }
        }
//        startNewProject();
    }

    @FXML
    private void convertProject() {
        FileChooser fileChooser = new FileChooser();
        fileChooser.setInitialDirectory(new File(GUI.conf.getScriptPath()));
        fileChooser.setTitle("Open SM File");
        File file = fileChooser.showOpenDialog(GUI.stage);
        if(file != null) {
            try {
                System.out.println(file.toString());
                String fl = new String(Files.readAllBytes(file.toPath()));

                export_file = new JSONObject(fl);

                FXMLLoader loader = new FXMLLoader();
                loader.setLocation(getClass().getResource("export_window.fxml"));
                Parent scene = loader.load();
                GUI.stage = new Stage();
                GUI.stage.setScene(new Scene(scene));
                GUI.stage.setResizable(false);
                GUI.stage.setTitle("Export file");
                GUI.stage.show();
            } catch (IOException e) {
                System.out.println(e);
            } catch(JSONException e) {
                System.out.println(e);
            }
        }
    }

    @FXML
    private void startNewProject() throws IOException {
        GUI.stage = new Stage();
        Parent new_scene = FXMLLoader.load(getClass().getResource("mainWindow2.fxml"));
        GUI.stage.setScene(new Scene(new_scene));
        GUI.stage.setTitle("untitled.sm");
        GUI.stage.setResizable(false);
        GUI.stage.show();
    }

    @FXML
    private void openConfiguration() throws IOException {
        GUI.stage = new Stage();
        Parent new_scene = FXMLLoader.load(getClass().getResource("config_window.fxml"));
        GUI.stage.setScene(new Scene(new_scene));
        GUI.stage.setTitle("Configuration");
        GUI.stage.setResizable(false);
        GUI.stage.show();
    }
}
