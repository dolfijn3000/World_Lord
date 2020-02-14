import WL_TreeView.CellFactory;
import WL_TreeView.WLTreeNode;
import javafx.beans.Observable;
import javafx.collections.ObservableList;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ContextMenu;
import javafx.scene.control.TreeItem;
import javafx.scene.control.TreeView;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.stage.Modality;
import javafx.stage.Stage;
import windows.WindowTextController;

import java.io.IOException;

public class Controller {

    public TreeItem<WLTreeNode> root = new TreeItem<WL_TreeView.WLTreeNode>(new WLTreeNode("root"));
    public Button add;
    public Button delete;

    @FXML
    private TreeView<WLTreeNode> navigation;

    String answer = "null";

    public Controller(){

    }

    @FXML
    public void initialize() {
        navigation.setRoot(root);
        navigation.setShowRoot(true);
        navigation.setCellFactory(new CellFactory());

        navigation.getRoot().getChildren().add(new TreeItem<WLTreeNode>(new WLTreeNode("1")));
        navigation.getRoot().getChildren().add(new TreeItem<WLTreeNode>(new WLTreeNode("2")));
        navigation.getRoot().getChildren().add(new TreeItem<WLTreeNode>(new WLTreeNode("3")));
        navigation.getRoot().getChildren().add(new TreeItem<WLTreeNode>(new WLTreeNode("4")));
        navigation.getRoot().getChildren().add(new TreeItem<WLTreeNode>(new WLTreeNode("5")));
        navigation.setOnKeyPressed(new EventHandler<KeyEvent>() {

            @Override
            public void handle(KeyEvent event) {
                if (event.getCode() == KeyCode.ENTER) {
                    TreeItem<WLTreeNode> currentItem = navigation.getSelectionModel().getSelectedItem();
                    ObservableList<TreeItem<WLTreeNode>> children = currentItem.getParent().getChildren();

                    int indexOfCurrentItem = children.indexOf(currentItem);
                    TreeItem<WLTreeNode> nodeAbove = currentItem.getParent().getChildren().get(indexOfCurrentItem - 1);

                    nodeAbove.getChildren().add(0,currentItem);



                }
            }
        });
    }

    //Creates new node for the treeview
    public  void AddNewNode(){
        try {
            //Initialize fxml loader and load our scene
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/windows/Window_text.fxml"));
            Parent scene = (Parent) fxmlLoader.load();

            //set shortcut keys
            scene.addEventFilter(KeyEvent.KEY_PRESSED, new EventHandler<KeyEvent>() {
                public void handle(KeyEvent ke) {
                    System.out.println("Key Pressed");
                    if (ke.getCode() == KeyCode.ESCAPE) {
                        System.out.println("Key Pressed: " + ke.getCode());
                        ke.consume(); // <-- stops passing the event to next node
                    }
                }
            });

            //Get the controller for the scene
            WindowTextController windowTextController = fxmlLoader.getController();
            windowTextController.SetPrompt("node name");


            //Initialize stage and show
            Stage stage = new Stage();
            stage.initModality(Modality.APPLICATION_MODAL);
            stage.setTitle("new node");
            stage.setScene(new Scene(scene));
            stage.show();

            //Set an event for when name is submited
            windowTextController.nameProperty().addListener((v,oldValue,newValue) ->{
                answer = newValue;

                //get selected node and if none apply root
                TreeItem<WLTreeNode> currentItem = navigation.getSelectionModel().getSelectedItem();
                if (currentItem == null) currentItem = navigation.getRoot();
                currentItem.getChildren().add(new TreeItem<WLTreeNode>(new WLTreeNode(answer)));

            });

        } catch (IOException e) {
            e.printStackTrace();
        }

    }


    //Create branches
    public TreeItem<String> makeBranch(String title, TreeItem<String> parent) {
        TreeItem<String> item = new TreeItem<>(title);
        item.setExpanded(true);
        parent.getChildren().add(item);
        return item;
    }


}
