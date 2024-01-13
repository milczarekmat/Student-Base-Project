package client.controllers;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.MenuBar;
import javafx.stage.Stage;

import java.io.IOException;

public class MenuBarController implements Controller {
    private Stage stage;
    private Scene scene;
    private Parent root;

    @FXML
    private MenuBar menuBar;

    private float currentWidth;
    private float currentHeight;

    public void toStudentList() throws IOException {
        changeScene("/studentList.fxml");
    }

    public void toStart() throws IOException {
        changeScene("/mainPage.fxml");
    }

    private void changeScene(String fxmlFilePath) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource(fxmlFilePath));
        root = loader.load();
        Stage stage = (Stage) menuBar.getScene().getWindow();

        scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }


}
