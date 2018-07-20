package GUI;

import javafx.fxml.FXML;
import javafx.stage.FileChooser;
import org.json.JSONException;

import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;

public class ExportController {

    @FXML
    private void unixChoice() {
        System.out.println("unix");
        getConvertedFile("unix");
    }

    @FXML
    private void windowsChoice() {
        System.out.println("windows");
        getConvertedFile("windows");
    }

    public void getConvertedFile(String platform) {
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
