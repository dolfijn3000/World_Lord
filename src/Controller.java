import WL_TreeView.CellFactory;
import WL_TreeView.WLTreeNode;
import javafx.beans.Observable;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.ObservableList;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.GridPane;
import javafx.stage.Modality;
import javafx.stage.Stage;
import windows.WindowTextController;

import java.io.IOException;

public class Controller {

    public TreeItem<WLTreeNode> root = new TreeItem<WL_TreeView.WLTreeNode>(new WLTreeNode("root",true));
    public Button add;
    public Button delete;

    @FXML
    private TreeView<WLTreeNode> navigation;
    @FXML
    private TabPane tabPane;
    @FXML
    private GridPane grid;

    String answer = "null";

    public Controller(){

    }

    @FXML
    public void initialize() {
        navigation.setRoot(root);
        navigation.setShowRoot(true);
        navigation.setCellFactory(new CellFactory());

        navigation.getRoot().getChildren().add(new TreeItem<WLTreeNode>(new WLTreeNode("1",true)));
        navigation.getRoot().getChildren().add(new TreeItem<WLTreeNode>(new WLTreeNode("2",true)));
        navigation.getRoot().getChildren().add(new TreeItem<WLTreeNode>(new WLTreeNode("3",true)));
        navigation.getRoot().getChildren().add(new TreeItem<WLTreeNode>(new WLTreeNode("4",true)));
        navigation.getRoot().getChildren().add(new TreeItem<WLTreeNode>(new WLTreeNode("5",true)));

        tabPane.setTabDragPolicy(TabPane.TabDragPolicy.REORDER);

        //open article on doubleclick
        navigation.setOnMouseClicked(event -> {
            TreeItem<WLTreeNode> ti = navigation.getSelectionModel().getSelectedItem();
            System.out.println(navigation.getHeight());
            if (ti == null || event.getClickCount() < 2)
                return;

            Article article = FindArticleById(navigation.getSelectionModel().getSelectedItem().getValue().articleId);
            if(article == null){

            }else{
                tabPane.getTabs().add(new WLTab(ti.getValue().getName(),article));
            }
        });

    }

    //Creates new node for the treeview
    public  void AddNewNode(){
        try {
            //Initialize fxml loader and load our scene
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/windows/Window_text.fxml"));
            Parent scene = (Parent) fxmlLoader.load();

            //Get the controller for the scene
            WindowTextController windowTextController = fxmlLoader.getController();
            windowTextController.SetPrompt("node name");


            //Initialize stage and show
            Stage stage = new Stage();
            stage.initModality(Modality.APPLICATION_MODAL);
            stage.setTitle("new node");
            stage.setScene(new Scene(scene));
            stage.sizeToScene();
            stage.show();
            stage.setMinHeight(stage.getHeight());
            stage.setMinHeight(stage.getHeight());

            //Set an event for when name is submited
            windowTextController.nameProperty().addListener((v,oldValue,newValue) ->{
                answer = newValue;

                //get selected node and if none apply root
                TreeItem<WLTreeNode> currentItem = navigation.getSelectionModel().getSelectedItem();
                if (currentItem == null) currentItem = navigation.getRoot();

                Article article = new Article(answer);
                currentItem.getChildren().add(new TreeItem<WLTreeNode>(new WLTreeNode(answer,true,article.getId())));

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

    public Article FindArticleById(String id){
        for(Article article : Main.articles){
            if(article.getId() == id){
                return article;
            }
        }
        return null;
    }

}
