package client.controllers;

import client.connection.Connector;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.stage.Stage;

import java.io.IOException;

public class LandingPageController {
    public Button connectButton;

    public void logIn(ActionEvent event) throws IOException {
        Connector.connect();

        if (!Connector.isConnected()) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Błąd połączenia");
            alert.setHeaderText("Wystąpił problem z połączeniem.");
            alert.setContentText("Sprawdź ustawienia połączenia i spróbuj ponownie.");

            alert.showAndWait();
            return;
        }

        FXMLLoader loader = new FXMLLoader(getClass().getResource("/scenes/main/mainPage.fxml"));
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
