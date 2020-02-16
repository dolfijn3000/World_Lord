package ArticleControls;

import javafx.scene.layout.VBox;

public abstract class ArticleControl<T> extends VBox {

    public String name = null;

    public abstract T GetData();

}
