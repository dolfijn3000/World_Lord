package WL_TreeView;

import java.io.Serializable;
import java.util.Objects;

public class WLTreeNode implements Serializable {
    private String name;

    public WLTreeNode(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }


}
