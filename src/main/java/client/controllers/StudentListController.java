package client.controllers;
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

        Student student = new Student(indexNumber, firstName, lastName, faculty);
        System.out.println(student);
        Connector.addStudent(student);
    }

    private void setCheckBoxValues() {
        ObservableList<String> list = FXCollections.observableArrayList();
        list.addAll("WEEiA", "FTIMS","BiNoZ");
        facultyChoiceBox.setItems(list);
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
}
