import ArticleControls.wlAcordion.WLAcordionControl;
import javafx.scene.control.Button;
import javafx.scene.control.Tab;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;

public class WLTab extends Tab {


    Article article;


    public WLTab(String name,Article article){
        GridPane gridPane = LoadArticle(article);
        this.setText(name);
        this.article = article;
        //load the gridpane into a anchor pane so that it can be anchored to the borders
        AnchorPane anchorPane = new AnchorPane();
        AnchorPane.setRightAnchor(gridPane,0.0);
        AnchorPane.setLeftAnchor(gridPane,0.0);
        anchorPane.getChildren().add(gridPane);

        this.setContent(anchorPane);

    }

    //load the article and add the needed controls
    public GridPane LoadArticle(Article article){
        //every control is stored in a cell in a gridpane
        GridPane gridPane = new GridPane();

        //create a column
        ColumnConstraints col = new ColumnConstraints();
        col.setPercentWidth(100);
        col.setFillWidth(true);

        //some settings for the gridpane
        gridPane.setMinWidth(Double.MIN_VALUE);
        gridPane.getColumnConstraints().add(col);

        //load standard controls

        //load dynamic controls

        WLAcordionControl w = new WLAcordionControl();
        w.controller.Main.setMaxWidth(Double.MAX_VALUE);
        GridPane.setFillWidth(w.controller.Main, true);
        gridPane.add(w.controller.Main,0,1);

        return gridPane;
    }
}
