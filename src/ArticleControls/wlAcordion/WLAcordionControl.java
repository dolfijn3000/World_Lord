package ArticleControls.wlAcordion;

import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.GridPane;

import java.io.IOException;

public class WLAcordionControl extends AnchorPane {

    public WLAcordionController controller;
    public WLAcordionControl(){

        super();

        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("WlAcordion.fxml"));

            controller = new WLAcordionController();

            loader.setController(controller);

            Node n = loader.load();

            this.getChildren().add(n);
            controller.Main.setMinWidth(30);

        }
        catch (IOException ix){

        }
    }
}
