package GUI;

import javafx.application.Application;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.FlowPane;
import javafx.stage.Stage;
import java.io.IOException;
import java.net.URL;

public class GUI extends Application {



    @Override
    public void start(Stage stage) throws IOException {
        URL url = getClass().getResource("mainWindow2.fxml");
        FXMLLoader loader = new FXMLLoader(url);
        BorderPane mainWindow = (BorderPane)loader.load();
        Scene scene = new Scene(mainWindow);
        stage.setScene(scene);
        stage.show();
    }
    public static void main(String[] args){
        launch(args);
    }
}
