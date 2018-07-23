package GUI;

import com.jfoenix.controls.JFXTextField;
import javafx.fxml.FXML;
import javafx.scene.layout.AnchorPane;
import javafx.stage.DirectoryChooser;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;

public class ConfigurationController {

    private String scriptPath;
    private String pluginPath;

    @FXML
    private JFXTextField blockListColor;
    @FXML
    private JFXTextField blockColor;
    @FXML
    private JFXTextField backgroundColor;
    @FXML
    private AnchorPane anchorBackground;

    @FXML
    public void initialize(){
        anchorBackground.setStyle("-fx-background-color:"+GUI.conf.getBackgroundColor()+";");
        blockListColor.setText(GUI.conf.getListColor());
        blockColor.setText(GUI.conf.getBlockColor());
        backgroundColor.setText(GUI.conf.getBackgroundColor());
        scriptPath = GUI.conf.getScriptPath();
        pluginPath = GUI.conf.getPluginPath();
    }

    @FXML
    public void saveConfiguration() throws FileNotFoundException, UnsupportedEncodingException {
        PrintWriter writer = new PrintWriter("confFile.txt", "UTF-8");
        GUI.conf.setPluginPath(pluginPath);
        GUI.conf.setScriptPath(scriptPath);
        writer.println("ScriptPath;" + scriptPath);
        writer.println("PluginPath;" + pluginPath);
        writer.println("BackgroundColor;"+backgroundColor.getText());
        writer.println("BlockColor;"+blockColor.getText());
        writer.println("ListColor;"+blockListColor.getText());
        writer.close();
        GUI.stage.close();
    }

    @FXML
    public void resetConfiguration() throws FileNotFoundException, UnsupportedEncodingException {
        PrintWriter writer = new PrintWriter("confFile.txt", "UTF-8");
        GUI.conf.setPluginPath(System.getProperty("user.dir"));
        GUI.conf.setScriptPath(System.getProperty("user.dir"));
        writer.println("ScriptPath;" + System.getProperty("user.dir"));
        writer.println("PluginPath;" + System.getProperty("user.dir"));
        writer.println("BackgroundColor;#1A1A1A");
        writer.println("BlockColor;#1A1A1A");
        writer.println("ListColor;#5e5e5e");
        writer.close();
        GUI.stage.close();
    }

    @FXML
    public void setScriptFolder(){
        DirectoryChooser dir = new DirectoryChooser();
        dir.setInitialDirectory(new File(GUI.conf.getScriptPath()));
        dir.setTitle("Choose Base Script Folder");
        File selectedDirectory = dir.showDialog(GUI.stage);

        if(selectedDirectory != null){
            scriptPath = selectedDirectory.getAbsolutePath();
            System.out.println(scriptPath);
        }
    }

    @FXML
    public void setPluginFolder(){
        DirectoryChooser dir = new DirectoryChooser();
        dir.setInitialDirectory(new File(GUI.conf.getPluginPath()));
        dir.setTitle("Choose Base Script Folder");
        File selectedDirectory = dir.showDialog(GUI.stage);

        if(selectedDirectory != null){
            pluginPath = selectedDirectory.getAbsolutePath();
            System.out.println(pluginPath);
        }
    }
}
