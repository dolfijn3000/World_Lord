package WL_TreeView;

import javafx.geometry.Point2D;
import javafx.scene.control.TreeCell;
import javafx.scene.control.TreeItem;
import javafx.scene.control.TreeView;
import javafx.scene.input.*;
import javafx.util.Callback;

import java.util.Objects;

public class CellFactory implements Callback<TreeView<WLTreeNode>, TreeCell<WLTreeNode>> {
    private static final DataFormat JAVA_FORMAT = new DataFormat("application/x-java-serialized-object");
    private static final String DROP_HINT_STYLE = "-fx-border-color: #eea82f; -fx-border-width: 0 0 2 0; -fx-padding: 3 3 1 3";
    private static final String DROP_STYLE = "-fx-border-color: #eea82f; -fx-border-width: 2 2 2 2; -fx-padding: 3 3 1 3";
    private TreeCell<WLTreeNode> dropZone;
    private TreeItem<WLTreeNode> draggedItem;

    private enum WorkDropType { DROP_INTO, REORDER }
    private static WorkDropType workDropType;

    @Override
    public TreeCell<WLTreeNode> call(TreeView<WLTreeNode> treeView) {
        TreeCell<WLTreeNode> cell = new TreeCell<WLTreeNode>() {
            @Override
            protected void updateItem(WLTreeNode item, boolean empty) {
                super.updateItem(item, empty);
                // clear cell
                if (item == null || empty) {
                    setText(null);
                    return;
                }
                if (item == null) return;
                setText(item.getName());
            }

        };

        cell.setOnDragDetected((MouseEvent event) -> dragDetected(event, cell, treeView));
        cell.setOnDragOver((DragEvent event) -> dragOver(event, cell, treeView));
        cell.setOnDragDropped((DragEvent event) -> drop(event, cell, treeView));
        cell.setOnDragDone((DragEvent event) -> clearDropLocation());

        return cell;
    }

    private void dragDetected(MouseEvent event, TreeCell<WLTreeNode> treeCell, TreeView<WLTreeNode> treeView) {
        draggedItem = treeCell.getTreeItem();

        // root can't be dragged
        if (draggedItem.getParent() == null) return;
        Dragboard db = treeCell.startDragAndDrop(TransferMode.MOVE);

        ClipboardContent content = new ClipboardContent();
        content.put(JAVA_FORMAT, draggedItem.getValue());
        db.setContent(content);
        db.setDragView(treeCell.snapshot(null, null));
        event.consume();
    }

    private void dragOver(DragEvent event, TreeCell<WLTreeNode> treeCell, TreeView<WLTreeNode> treeView) {
        if (!event.getDragboard().hasContent(JAVA_FORMAT)) return;

        Point2D sceneCoordinates = treeCell.localToScene(0d, 0d);
        TreeItem<WLTreeNode> thisItem = treeCell.getTreeItem();

        double height = treeCell.getHeight();

        // get the y coordinate within the control
        double y = event.getSceneY() - (sceneCoordinates.getY());



        // can't drop on itself
        if (draggedItem == null || thisItem == null || thisItem == draggedItem) return;
        // ignore if this is the root
        if (draggedItem.getParent() == null) {
            clearDropLocation();
            return;
        }

        if(dropZoneIsChild(draggedItem,thisItem)){
            clearDropLocation();
            return;
        }

        event.acceptTransferModes(TransferMode.MOVE);
        if (!Objects.equals(dropZone, treeCell)) {
            clearDropLocation();
            this.dropZone = treeCell;

        }

        if (y > (height * .75d)) {
            workDropType = WorkDropType.REORDER;
            dropZone.setStyle(DROP_HINT_STYLE);
        }
        else {
            workDropType = WorkDropType.DROP_INTO;
            dropZone.setStyle(DROP_STYLE);
        }
    }
    private void drop(DragEvent event, TreeCell<WLTreeNode> treeCell, TreeView<WLTreeNode> treeView) {
        Dragboard db = event.getDragboard();
        boolean success = false;
        if (!db.hasContent(JAVA_FORMAT)) return;

        TreeItem<WLTreeNode> thisItem = treeCell.getTreeItem();
        TreeItem<WLTreeNode> droppedItemParent = draggedItem.getParent();


        if(workDropType == WorkDropType.DROP_INTO) {
                droppedItemParent.getChildren().remove(draggedItem);
                thisItem.getChildren().add(draggedItem);
        }
        else if(workDropType == WorkDropType.REORDER) {
            // remove from previous location
            droppedItemParent.getChildren().remove(draggedItem);
            // dropping on parent node makes it the first child
            if (Objects.equals(droppedItemParent, thisItem)) {
                thisItem.getChildren().add(0, draggedItem);
                treeView.getSelectionModel().select(draggedItem);
            } else {
                // add to new location
                int indexInParent = thisItem.getParent().getChildren().indexOf(thisItem);
                thisItem.getParent().getChildren().add(indexInParent + 1, draggedItem);
            }
            treeView.getSelectionModel().select(draggedItem);
        }
        event.setDropCompleted(success);

    }

    private void clearDropLocation() {
        if (dropZone != null) dropZone.setStyle("");
    }

    //make sure that when we drop the node it is not a child of the node we are dragging
    private boolean dropZoneIsChild(TreeItem<WLTreeNode>  node, TreeItem<WLTreeNode> destination){
        boolean isChild = false;

        for(TreeItem<WLTreeNode> child : node.getChildren()){


            if(destination.getValue().getId() == child.getValue().getId() ){
                isChild = true;
                System.out.println("found problem");
                System.out.println("dragged node id: " + destination.getValue().getId() + "child id: " + child.getValue().getId()  );
                return isChild;
            }
            if(!child.getChildren().isEmpty()){
                isChild = dropZoneIsChild(child,destination);
            }

        }

        return isChild;
    }

}
