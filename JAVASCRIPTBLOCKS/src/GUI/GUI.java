package GUI;

import AnoParser.MethodInfo;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.*;

public class GUI extends Application {

    public static Stage stage;
    public static Configuration conf;

    @Override
    @MethodInfo(name = "start(Stage stage)", date = "05/07/18", arguments = "1: Stage stage, The stage to be set", comments = "Init the Base Window", returnValue="None" ,revision = 1)
    public void start(Stage stage) throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("openingWindow.fxml"));
        Scene scene = new Scene(root);
        GUI.stage = stage;
        GUI.stage.setTitle("Script::Blocks");
        GUI.stage.setScene(scene);
        GUI.stage.setResizable(false);
        GUI.stage.show();
    }

    @MethodInfo(name = "readConf()", date = "20/07/18", arguments = "None", comments = "Read and initialize the configuration", returnValue="None" ,revision = 1)
    public static void readConf() throws FileNotFoundException, UnsupportedEncodingException {
        File confFile = new File("confFile.txt");
        if(confFile.exists()){
            conf = new Configuration();
            String[] splited;
            boolean problem = false;
            BufferedReader br = null;
            FileReader fr = null;
            try {
                fr = new FileReader("confFile.txt");
                br = new BufferedReader(fr);
                String sCurrentLine;
                for(int i =0; i < 5; i++){
                    if((sCurrentLine = br.readLine()) != null){
                        splited = sCurrentLine.split("\\;");
                        switch (splited[0]){
                            case "ScriptPath":
                                conf.setScriptPath(splited[1]);
                                break;
                            case "PluginPath":
                                conf.setPluginPath(splited[1]);
                                break;
                            case "BackgroundColor":
                                conf.setBackgroundColor(splited[1]);
                                break;
                            case "BlockColor":
                                conf.setBlockColor(splited[1]);
                                break;
                            case "ListColor":
                                conf.setListColor(splited[1]);
                                break;
                            default:
                                problem = true;
                                break;
                        }
                    }else{
                        problem = true;
                    }
                    if(problem){
                        System.out.println(problem);
                        conf = new Configuration();
                        break;
                    }
                }
            } catch (IOException e) {
                e.printStackTrace();
            } finally {
                try {
                    if (br != null)
                        br.close();
                    if (fr != null)
                        fr.close();
                } catch (IOException ex) {
                    ex.printStackTrace();
                }
            }
        }else{
            PrintWriter writer = new PrintWriter("confFile.txt", "UTF-8");
            conf = new Configuration();
            writer.println("ScriptPath;" + System.getProperty("user.dir"));
            writer.println("PluginPath;" + System.getProperty("user.dir")+"\\toLoad");
            writer.println("BackgroundColor;#1A1A1A");
            writer.println("BlockColor;#1A1A1A");
            writer.println("ListColor;#5e5e5e");
            writer.close();
        }
    }

    @MethodInfo(name = "main(String[] args)", date = "05/07/18", arguments = "1: String[] args, The rgs", comments = "The Main, launch start & parse annotations", returnValue="None" ,revision = 1)
    public static void main(String[] args) {
        try {
            readConf();
        }catch(Exception e){
            System.out.println("Could not open file or encoding problem");
        }
        launch(args);
    }

}
