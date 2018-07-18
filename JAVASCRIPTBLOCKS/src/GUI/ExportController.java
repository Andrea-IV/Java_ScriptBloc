package GUI;

import javafx.embed.swing.JFXPanel;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;

public class ExportController {

    private String platform;

    @FXML
    private Button unix_button;
    @FXML
    private Button windows_button;

    @FXML
    private void unixChoice() {
        System.out.println("unix");
        this.platform = "unix";
        getFileConverted("unix");
//        Stage stage = (Stage) unix_button.getScene().getWindow();
//        stage.close();
    }

    @FXML
    private void windowsChoice() {
        System.out.println("windows");
        this.platform = "windows";
        getFileConverted("windows");
//        Stage stage = (Stage) windows_button.getScene().getWindow();
//        stage.close();
    }

    private void getFileConverted(String platform) {
        ApiCall api = new ApiCall("http://127.0.0.1:8080/");
        try {
            String full_file =
                    "{" +
                    "\"type\": \""+ platform +"\","+
                    "\"blocks\": " + GUIHomepageController.export_file.get("blocks").toString() +
                    "}";
            System.out.println(full_file);
            String res = api.ApiPostResponse("block/finalscript", full_file);
            System.out.println(res);

            FileChooser fileChooser = new FileChooser();
            fileChooser.setInitialDirectory(new File("C:\\Users\\ycapel\\Documents\\ESGI_cours\\S2\\projet_annuel\\Java_ScriptBloc\\JAVASCRIPTBLOCKS"));
            fileChooser.setTitle("Save converted file");
            if(platform.equals("unix")) {
                FileChooser.ExtensionFilter extFilter = new FileChooser.ExtensionFilter("BASH files (*.sh)", "*.sh");
                fileChooser.getExtensionFilters().add(extFilter);
            } else if(platform.equals("windows")) {
                FileChooser.ExtensionFilter extFilter = new FileChooser.ExtensionFilter("BATCH files (*.bat)", "*.bat");
                fileChooser.getExtensionFilters().add(extFilter);
                extFilter = new FileChooser.ExtensionFilter("BASH files (*.cmd)", "*.cmd");
                fileChooser.getExtensionFilters().add(extFilter);
            }

            File file = fileChooser.showSaveDialog(GUI.stage);
            if(file != null) {
                PrintWriter writer = new PrintWriter(file.toString(), "UTF-8");
                writer.println(res);
                writer.close();
            }
        } catch (JSONException e) {
            System.out.println(e);
        } catch (IOException e) {
            System.out.println(e);
        }
    }
}
