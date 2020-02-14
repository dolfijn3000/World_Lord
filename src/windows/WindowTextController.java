package windows;


import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.io.IOException;

public class WindowTextController {


    public Button ok;
    public Button cancel;
    @FXML
    public TextField text;
    @FXML
    public Label promptLabel;

    public StringProperty name = new SimpleStringProperty(this,"name","null");


    public StringProperty nameProperty() {
        return name;
    }
    public void setName(String name) {
        this.name.set(name);
    }


    public void SetPrompt(String prompt){
        System.out.println(prompt);
        promptLabel.setText(prompt);
    }

    public void confirm(){
        Stage stage = (Stage) ok.getScene().getWindow();
        // do what you have to do
        stage.close();
        setName(text.getText());
    }
}
