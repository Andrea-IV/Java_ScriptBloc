package GUI;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.AnchorPane;
import javafx.stage.FileChooser;
import org.json.JSONException;

import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.concurrent.TimeUnit;

public class ExportController {

    private String platform;

    @FXML
    private Label labelWait;
    @FXML
    private Button unix_button;
    @FXML
    private Button windows_button;
    @FXML
    private AnchorPane anchorBackground;

    @FXML
    private  void initialize(){
        anchorBackground.setStyle("-fx-background-color:"+GUI.conf.getBackgroundColor()+";");
    }

    @FXML
    private void unixChoice() {
        getConvertedFile("unix");
    }

    @FXML
    private void windowsChoice() {
        getConvertedFile("windows");
    }

    @MethodInfo(name = "getConvertedFile()", date = "05/07/18", arguments = "String", comments = "Will call API to convert the file choosen and will save it in a new file", returnValue="None" ,revision = 1)
    public void getConvertedFile(String platform) {
        ApiCall api = new ApiCall("http://127.0.0.1:8080/");
        try {
            String full_file =
                    "{" +
                    "\"type\": \""+ platform +"\","+
                    "\"blocks\": " + GUIHomepageController.export_file.get("blocks").toString() +
                    "}";
            System.out.println(full_file);
            String[][] headers = {{"Content-Type", "application/json"}};
            String res = api.ApiPostResponse("block/finalscript", full_file, headers);
            System.out.println(res);

            FileChooser fileChooser = new FileChooser();
            fileChooser.setInitialDirectory(new File(GUI.conf.getScriptPath()));
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
            GUI.stage.close();
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
