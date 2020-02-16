package WL_TreeView;

import java.io.Serializable;
import java.util.Objects;
import java.util.UUID;

public class WLTreeNode implements Serializable {
    private String name;
    public String id;
    public String articleId;


    public WLTreeNode(String name, boolean generateId) {
        this.name = name;
        if(generateId) {
            id = UUID.randomUUID().toString();
        }
    }

    public WLTreeNode(String name, boolean generateId, String articleId) {
        this.name = name;
        this.articleId = articleId;
        if(generateId) {
            id = UUID.randomUUID().toString();
        }
    }

    public String getName() {
        return name;
    }
    public String getId() {
        return id;
    }



}
