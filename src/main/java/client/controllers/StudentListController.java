package client.controllers;
import client.connection.Connector;
import db.entities.Student;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;

import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;

public class StudentListController implements Initializable {
    public MenuBar menu;
    public TableView<Student> tableView;
    public TableColumn<Student, Integer> nrIndeksuColumn;
    public TableColumn<Student, String> imieColumn;
    public TableColumn<Student, String> nazwiskoColumn;
    public TableColumn<Student, String> wydzialColumn;

    @FXML
    private TextField deletedIndex;

    public static ArrayList<Student> students = new ArrayList<>();

    public static void setStudent(ArrayList<Student> s) {
        students = s;
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        if (students.size() != 0 && tableView != null) {
            ObservableList<Student> listaStudentow = FXCollections.observableArrayList(students);

            nrIndeksuColumn.setCellValueFactory(new PropertyValueFactory<>("id"));
            imieColumn.setCellValueFactory(new PropertyValueFactory<>("name"));
            nazwiskoColumn.setCellValueFactory(new PropertyValueFactory<>("surname"));
            wydzialColumn.setCellValueFactory(new PropertyValueFactory<>("department"));

            tableView.setItems(listaStudentow);
        }
    }

    @FXML
    private void handleDeleteButtonAction(ActionEvent actionEvent) {
        Integer index = Integer.parseInt(deletedIndex.getText());

        //todo: sprawdzic czy student istnieje

        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Potwierdzenie usuniecia");
        alert.setHeaderText("Potwierdzenie");
        alert.setContentText("Czy chcesz żeby na pewno usunąć studenta o indeksie " + index + "?");

        alert.showAndWait().ifPresent(response -> {
            if (response == ButtonType.OK) {
                Connector.deleteStudent(index);
                deletedIndex.clear();
            }
        });
    }
}
