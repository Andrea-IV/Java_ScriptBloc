package GUI;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.*;
import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.lang.annotation.Annotation;
import java.util.ArrayList;
import java.util.List;

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
        try {
            List<Class<?>> classes = ClassFinder.find("GUI");
            PrintWriter writer = new PrintWriter("DocGUI.txt", "UTF-8");
            writer.println("                                   =========================================================");
            writer.println("                                   ||  SCRIPT::BLOCKS GUI PACKAGE FUNCTION DOCUMENTATION  ||");
            writer.println("                                   =========================================================");
            writer.println();
            for(Class name: classes){
                if(!name.getName().contains("$")) {
                    writer.println("||----------CLASS----------");
                    writer.println("||     "+name.getName());
                    writer.println("||-------------------------");
                    writer.println("||");
                    writeConstructorInFile(GUI.class.getClassLoader().loadClass((name.getName())).getConstructors(), writer);
                    writeFieldInFile(GUI.class.getClassLoader().loadClass((name.getName())).getFields(), writer);
                    writeMethodInFile(GUI.class.getClassLoader().loadClass((name.getName())).getMethods(), writer);
                    writer.println("||");
                }
            }
            writer.close();
        }catch (Exception e){
            System.out.println("FILE NOT FOUND, NO DOC !");
            System.out.println(e);
        }
        launch(args);
    }

    @MethodInfo(name = "writeMethodInFile(Method[] methodArray, PrintWriter writer)", date = "05/07/18", arguments = "1: Method[] methodArray, list of method to write the doc, 2: PrintWriter writer, printer to write in the doc field", comments = "Loop on all the methods to print annotation in the doc", returnValue="None" ,revision = 1)
    public static void writeMethodInFile(Method[] methodArray, PrintWriter writer){
        for (Method method: methodArray) {
            // checks if MethodInfo annotation is present for the method
            if (method.isAnnotationPresent(MethodInfo.class)) {
                try {
                    MethodInfo methodAnno = method.getAnnotation(MethodInfo.class);
                    writer.println("||");
                    writer.println("||    ---------METHOD----------");
                    writer.println("||         "+methodAnno.name());
                    writer.println("||    -------------------------");
                    writer.println("||    -- DATE : "+methodAnno.date());
                    writer.println("||    -- REVISION : "+methodAnno.revision());
                    writer.println("||    -- DESCRIPTION : "+methodAnno.comments());
                    writer.println("||    -- ARGUMENTS : "+methodAnno.arguments());
                    writer.println("||    -- RETURN : "+methodAnno.returnValue());
                } catch (Throwable ex) {
                    ex.printStackTrace();
                }
            }
        }
    }

    @MethodInfo(name = "writeConstructorInFile(Constructor[] methodArray, PrintWriter writer)", date = "05/07/18", arguments = "1: Constructor[] methodArray, list of constructor to write the doc, 2: PrintWriter writer, printer to write in the doc field", comments = "Loop on all the constructors to print annotation in the doc", returnValue="None" ,revision = 1)
    public static void writeConstructorInFile(Constructor[] methodArray, PrintWriter writer){
        for (Constructor method: methodArray) {
            // checks if MethodInfo annotation is present for the method
            if (method.isAnnotationPresent(MethodInfo.class)) {
                try {
                    MethodInfo methodAnno = (MethodInfo) method.getAnnotation(MethodInfo.class);
                    writer.println("||");
                    writer.println("||    -------CONSTRUCTOR-------");
                    writer.println("||         "+methodAnno.name());
                    writer.println("||    -------------------------");
                    writer.println("||    -- DATE : "+methodAnno.date());
                    writer.println("||    -- REVISION : "+methodAnno.revision());
                    writer.println("||    -- DESCRIPTION : "+methodAnno.comments());
                    writer.println("||    -- ARGUMENTS : "+methodAnno.arguments());
                    writer.println("||    -- RETURN : "+methodAnno.returnValue());
                } catch (Throwable ex) {
                    ex.printStackTrace();
                }
            }
        }
    }

    @MethodInfo(name = "writeFieldInFile(Field[] methodArray, PrintWriter writer)", date = "05/07/18", arguments = "1: Field[] methodArray, list of field to write the doc, 2: PrintWriter writer, printer to write in the doc field", comments = "Loop on all the Fields to print annotation in the doc", returnValue="None" ,revision = 1)
    public static void writeFieldInFile(Field[] methodArray, PrintWriter writer){
        for (Field method: methodArray) {
            // checks if MethodInfo annotation is present for the method
            if (method.isAnnotationPresent(MethodInfo.class)) {
                try {
                    MethodInfo methodAnno = (MethodInfo) method.getAnnotation(MethodInfo.class);
                    writer.println("||");
                    writer.println("||    ----------FIELD----------");
                    writer.println("||         "+methodAnno.name());
                    writer.println("||    -------------------------");
                    writer.println("||    -- DATE : "+methodAnno.date());
                    writer.println("||    -- REVISION : "+methodAnno.revision());
                    writer.println("||    -- DESCRIPTION : "+methodAnno.comments());
                } catch (Throwable ex) {
                    ex.printStackTrace();
                }
            }
        }
    }

}
