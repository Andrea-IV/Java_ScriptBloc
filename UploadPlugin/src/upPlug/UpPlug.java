package upPlug;


import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.stage.FileChooser;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.HttpVersion;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.params.ClientPNames;
import org.apache.http.entity.mime.MultipartEntity;
import org.apache.http.entity.mime.content.FileBody;
import org.apache.http.entity.mime.content.StringBody;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.params.BasicHttpParams;
import org.apache.http.params.CoreProtocolPNames;
import org.apache.http.params.HttpParams;
import plugins.ApiCall;
import plugins.Plugin;
import javafx.stage.Stage;
import javafx.fxml.FXML;
import org.json.JSONArray;
import org.json.JSONException;

import java.io.File;
import java.io.IOException;
import java.io.UnsupportedEncodingException;

public class UpPlug extends Plugin {

    public static Stage stage;

    public Scene scene;

    public TabPane allTab;
    public Tab tabLog;
    public Tab tabUp;

    public TextField logInput;
    public TextField pwInput;

    public TextField userSign;
    public TextField logSign;
    public TextField pwSign;
    public TextField pw2Sign;

    public TextField scriptName;
    public TextArea scriptDesc;

    private boolean isConnected = false;

    private String mail;
    private String userName;
    private String token;
    private int idUser;


    public void start(Stage stage){
        System.out.println(System.getProperty("user.dir"));
        try{
            FXMLLoader fx = new FXMLLoader();

            fx.setClassLoader(getClass().getClassLoader());
            fx.setLocation(getClass().getResource("/upPlug/mainWindow.fxml"));

            scene = new Scene(fx.load());
            this.stage = stage;
            this.stage.setTitle("Upload Script");
            this.stage.setScene(scene);
            this.stage.show();

            allTab = (TabPane) scene.lookup("#allTab");
            logInput = (TextField)scene.lookup("#logInput");
            pwInput = (TextField)scene.lookup("#logInput");
            userSign = (TextField)scene.lookup("#userSign");
            logSign = (TextField)scene.lookup("#logSign");
            pwSign = (TextField)scene.lookup("#pwSign");
            pw2Sign = (TextField)scene.lookup("#pw2Sign");
            scriptName = (TextField)scene.lookup("#scriptName");
            scriptDesc = (TextArea)scene.lookup("#scriptDesc");
            int i = 0;
            for(Tab tab : allTab.getTabs()){
                if(i == 0 && tab instanceof Tab){
                    tabLog = tab;
                }else if(i == 1 && tab instanceof Tab) {
                    tabUp = tab;
                }
                i++;
            }

            allTab.getTabs().remove(tabLog);
            allTab.getTabs().remove(tabUp);

            if(isConnected){
                allTab.getTabs().add(tabUp);
            }else{
                allTab.getTabs().add(tabLog);
            }
        }catch(IOException e){
            e.printStackTrace();
        }

    }

    public void run(){
        start(new Stage());
    }


    public void close() {
        System.out.println("Closing...");
    }

    @FXML
    public void logAction(ActionEvent event) {

        String log = logInput.getText();
        String pw = pwInput.getText();

        ApiCall api = new ApiCall("http://127.0.0.1:8080/");
        try{
            String full_file =
                    "[{" +
                    "\"email\": \""+ log +"\","+
                    "\"password\": \""+ pw +"\""+
                    "}]";
            String[][] headers = {{"Content-Type", "application/json"}};
            try{
                JSONArray jsonObj = new JSONArray(api.ApiPostResponse("user/login", full_file, headers));
                System.out.println(jsonObj);
                mail = log;
                userName = (String)jsonObj.getJSONObject(0).get("name");
                token = (String)jsonObj.getJSONObject(0).get("token");
                idUser = (int)jsonObj.getJSONObject(0).get("id");
                isConnected = true;
            }catch(JSONException e){
                e.printStackTrace();
            }

            allTab.getTabs().remove(tabLog);
            allTab.getTabs().add(tabUp);
        }catch(IOException e){
            e.printStackTrace();
        }

    }

    @FXML
    public void signAction(ActionEvent event) {
        String name = userSign.getText();
        String log = logSign.getText();
        String pw = pwSign.getText();
        String pw2 = pw2Sign.getText();

        ApiCall api = new ApiCall("http://127.0.0.1:8080/");
        try{
            String full_file =
                    "[{" +
                            "\"email\": \""+ log +"\","+
                            "\"password1\": \""+ pw +"\","+
                            "\"password2\": \""+ pw2 +"\","+
                            "\"name\": \""+ name +"\""+
                            "}]";
            String[][] headers = {{"Content-Type", "application/json"}};
            try{
                JSONArray jsonObj = new JSONArray(api.ApiPostResponse("user/register", full_file, headers));
                System.out.println("you can now login");

            }catch(JSONException e){
                e.printStackTrace();
            }
        }catch(IOException e){
            e.printStackTrace();
        }
    }

    @FXML
    void uploadAction(ActionEvent event) {

        String name = scriptName.getText();
        String desc = scriptDesc.getText();

        FileChooser fileChooser = new FileChooser();
        fileChooser.setInitialDirectory(new File("C:\\Users"));
        fileChooser.setTitle("Open SM File");
        File file = fileChooser.showOpenDialog(stage);
        int idScript = 0;

        if(file != null) {
            ApiCall api = new ApiCall("http://127.0.0.1:8080/");
            try{
                String full_file =
                        "[{" +
                                "\"name\": \""+ name +"\","+
                                "\"description\": \""+ desc +"\","+
                                "\"size\": "+ (file.length())/1000 +
                                "}]";
                String[][] headers = {{"Content-Type", "application/json"}, {"Authorization","Bearer "+token}};
                try{
                    JSONArray jsonObj = new JSONArray(api.ApiPostResponse("script/add", full_file, headers));
                    idScript = (int)jsonObj.getJSONObject(0).get("id");
                    System.out.println(idScript);
                }catch(JSONException e){
                    e.printStackTrace();
                }
            }catch(IOException e){
                e.printStackTrace();
            }

            HttpClient client = new DefaultHttpClient();
            client.getParams().setParameter(CoreProtocolPNames.PROTOCOL_VERSION, HttpVersion.HTTP_1_1);
            HttpPost post = new HttpPost("http://127.0.0.1/WEN_ANNUEL/WebScriptBlock/upFromJava.php");

            File file2 = new File(name+"_"+idScript+".sm");
            MultipartEntity entity = new MultipartEntity();
            entity.addPart("userfile", new FileBody(file));
            entity.addPart("userfile2", new FileBody(file2));

            post.setEntity(entity);

            try{
                HttpResponse response = client.execute(post);
                HttpEntity resEntity = response.getEntity();
                System.out.println(response);

            }catch(IOException e){
                e.printStackTrace();
            }
        }
    }

    @FXML
    void dcAction(ActionEvent event) {

        mail = "";
        token = "";
        userName = "";
        isConnected = false;

        allTab.getTabs().remove(tabUp);
        allTab.getTabs().add(tabLog);

    }

    public boolean isConnected() {
        return isConnected;
    }

    public void setConnected(boolean connected) {
        isConnected = connected;
    }

    public String getMail() {
        return mail;
    }

    public void setMail(String mail) {
        this.mail = mail;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }
}
