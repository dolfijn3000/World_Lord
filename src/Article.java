import ArticleControls.ArticleControl;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class Article {
    
    private String id;
    private String name;
    public List<ArticleControl> controls = new ArrayList<ArticleControl>();

    public String getName() {
        return name;
    }

    public String getId() {
        return id;
    }

    public Article(String nameToSet){
        name = nameToSet;
        id = UUID.randomUUID().toString();
        Main.articles.add(this);
    }

    public void LoadControl(ArticleControl articleControl){

    }
}
