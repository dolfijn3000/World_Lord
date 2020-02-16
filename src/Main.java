import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.util.ArrayList;
import java.util.List;

public class Main extends Application {

    public static List<Article> articles = new ArrayList<Article>();

    @Override
    public void start(Stage primaryStage) throws Exception{
        Parent root = FXMLLoader.load(getClass().getResource("MainScreen.fxml"));
        primaryStage.setTitle("World lord");
        primaryStage.setScene(new Scene(root));
        primaryStage.setMinHeight(300);
        primaryStage.setMinWidth(1510);
        primaryStage.show();
    }


    public static void main(String[] args) {
        launch(args);
    }
}
