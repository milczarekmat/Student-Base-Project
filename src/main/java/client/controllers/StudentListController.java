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
import db.repositories.StudentRepository;
import client.connection.Connector;

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
    @FXML
    private TextField indexNumberField;
    @FXML
    private TextField firstNameField;
    @FXML
    private TextField lastNameField;
    @FXML
    private ChoiceBox<String> facultyChoiceBox;

    @FXML
    private void handleAddButtonAction(ActionEvent event) {
        int indexNumber = Integer.parseInt(indexNumberField.getText());
        String firstName = firstNameField.getText();
        String lastName = lastNameField.getText();
        String faculty = facultyChoiceBox.getValue();

        if(faculty == null) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Brak wyboru wydzialu");
            alert.setHeaderText("Bład");
            alert.setContentText("Wybierz wydzial");

            alert.showAndWait();
            return;
        }

        Student student = new Student(indexNumber, firstName, lastName, faculty);
        System.out.println(student);
        Connector.addStudent(student);

        indexNumberField.clear();
        firstNameField.clear();
        lastNameField.clear();
        facultyChoiceBox.getSelectionModel().clearSelection();
    }

    public void setCheckBoxValues() {
        if(facultyChoiceBox != null) {
            ObservableList<String> list = FXCollections.observableArrayList();
            list.addAll("WEEiA", "FTIMS", "BiNoZ");
            facultyChoiceBox.setItems(list);
        }
    }

    public static void setStudent(ArrayList<Student> s) {
        students = s;
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        setCheckBoxValues();
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
