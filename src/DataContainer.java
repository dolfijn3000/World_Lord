import java.util.ArrayList;
import java.util.List;

//this class is responsible for holding al the data en saving it
public class DataContainer {

    public static List<Article> articles = new ArrayList<Article>();

    public static void getArticleContent(){
        for(Article article: articles){
            System.out.println(article.getContent() + " content from article" + article.getId());
        }
    }
    //list with all articles

    //list of al nodes and order of them.
}
