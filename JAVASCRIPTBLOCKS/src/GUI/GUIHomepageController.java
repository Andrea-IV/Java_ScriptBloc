package GUI;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.layout.VBox;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

import javax.swing.*;
import java.io.File;
import java.io.IOException;

public class GUIHomepageController {

    @FXML
    public static Stage stage;

//    @FXML
//    private VBox recent_projects;

    @FXML
    private void loadProject() {
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Open Resource File");
        File file = fileChooser.showOpenDialog(GUIHomepageController.stage);
        if(file != null) {
            System.out.println(file.toString());
        }

//        ApiCall ac = new ApiCall("http://127.0.0.1/");

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
