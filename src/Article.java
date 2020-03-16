import ArticleControls.ArticleControl;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class Article {
    
    private String id;
    private String name;

    public StringProperty Content;

    public String getContent() {
        return Content.get();
    }

    public StringProperty contentProperty() {
        return Content;
    }

    public void setContent(String content) {
        this.Content.set(content);
    }

    public List<ArticleControl> controls = new ArrayList<ArticleControl>();

    public String getName() {
        return name;
    }

    public String getId() {
        return id;
    }

    public Article(String nameToSet){
        Content = new SimpleStringProperty("Content");
        name = nameToSet;
        id = UUID.randomUUID().toString();
        DataContainer.articles.add(this);

        init();
    }

    public void init() {
        Content.addListener(new ChangeListener(){

            @Override
            public void changed(ObservableValue observable, Object oldValue, Object newValue) {
                System.out.println(newValue + " lol");
            }
        });
    }

    public void LoadControl(ArticleControl articleControl){

    }
}
