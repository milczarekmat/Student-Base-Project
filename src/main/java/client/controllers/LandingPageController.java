package client.controllers;

import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.stage.Stage;

import java.io.IOException;

public class LandingPageController implements Controller{
    public Button connectButton;

    public void logIn(ActionEvent event) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/main/mainPage.fxml"));
        Parent root = loader.load();
        Stage stage = (Stage)((Node)event.getSource()).getScene().getWindow();

        //        Controller newSceneController = loader.getController();
        stage.setHeight(stage.getHeight());
        stage.setWidth(stage.getWidth());

        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }
}
