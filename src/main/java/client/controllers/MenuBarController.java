package client.controllers;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.MenuBar;
import javafx.stage.Stage;

import java.io.IOException;

public class MenuBarController implements Controller {
    private Stage stage;
    private Scene scene;
    private Parent root;

    @FXML
    private MenuBar menuBar;

    public void toSubjectList() throws IOException {
        changeScene("/scenes/main/subject/subjectList.fxml");
    }

    public void toStudentList() throws IOException {
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

    public void logOut() {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Wylogowanie");
        alert.setHeaderText("Potwierdzenie");
        alert.setContentText("Czy chcesz na pewno się wylogować?");

        alert.showAndWait().ifPresent(response -> {
            if (response == ButtonType.OK) {
                try {
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
