package client.controllers;

import client.connection.Connector;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.MenuBar;
import javafx.stage.Stage;

import java.io.IOException;

public class MenuBarController implements Controller {
    private Stage stage;
    private Scene scene;
    private Parent root;
    public Button connectButton;
    private final Connector connector = new Connector();
    @FXML
    private MenuBar menuBar;

    public void toSubjectList() throws IOException {
//        Connector.getSubcjexts() // wysle tobie nr 1 i zacznę słuchanie ciebie w watku -> obierasz 1 i pobierasz liste przedmiotow -> wysylacz mi -> ja odbieram i kończe sluchanie
        changeScene("/scenes/main/subject/subjectList.fxml");
    }

    public void toStudentList() throws IOException {
        connector.getStudents();
        changeScene("/scenes/main/student/studentList.fxml");
    }

    public void toStudentAdd() throws IOException {
        changeScene("/scenes/main/student/addStudent.fxml");
    }

    public void toStudentDelete() throws IOException {
        changeScene("/scenes/main/student/deleteStudent.fxml");
    }

    public void toSubjectAdd() throws IOException {
        changeScene("/scenes/main/subject/addSubject.fxml");
    }

    public void toSubjectDelete() throws IOException {
        changeScene("/scenes/main/subject/deleteSubject.fxml");
    }

    public void toStart() throws IOException {
        changeScene("/scenes/main/mainPage.fxml");
    }

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

    public void logOut() {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Wylogowanie");
        alert.setHeaderText("Potwierdzenie");
        alert.setContentText("Czy chcesz na pewno się wylogować?");

        alert.showAndWait().ifPresent(response -> {
            if (response == ButtonType.OK) {
                try {
                    connector.disconnect();
                    changeScene("/scenes/landing/landingPage.fxml");
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
            }
        });

    }

    private void changeScene(String fxmlFilePath) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource(fxmlFilePath));
        root = loader.load();
        Stage stage = (Stage) menuBar.getScene().getWindow();

        stage.setHeight(stage.getHeight());
        stage.setWidth(stage.getWidth());

        scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }
}
