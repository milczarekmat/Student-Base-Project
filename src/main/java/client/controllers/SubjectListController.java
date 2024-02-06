package client.controllers;

import client.connection.Connector;
import db.entities.Student;
import db.entities.StudentGrade;
import db.entities.Subject;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.util.Pair;

import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;

public class SubjectListController implements Initializable {
    public MenuBar menu;
    public TableView<Subject> tableView;
    public TableColumn<Subject, Integer> id;
    public TableColumn<Subject, String> name;
    public TableColumn<Subject, String> teacher;
    public static ArrayList<Subject> subjects = new ArrayList<>();

    public static ArrayList<Pair<String, Float>> studentsGrades = new ArrayList<>();

    @FXML
    private TextField subjectName;

    @FXML
    private TextField subjectTeacher;

    @FXML
    private void handleAddButtonAction(ActionEvent event) {
        String name = subjectName.getText();
        String teacher = subjectTeacher.getText();

        Subject subject = new Subject();
        subject.setName(name);
        subject.setSubjectManager(teacher);
        Connector.addSubject(subject);

        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Sukces");
        alert.setHeaderText("Dodano nowy przedmiot: " + name + " ,prowadzony przez " + teacher);
        alert.setContentText("Możesz dodać następny");

        alert.showAndWait();
        subjectTeacher.clear();
        subjectName.clear();
    }

    public static void setSubjects(ArrayList<Subject> s)  {
        subjects = s;
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        if (subjects.size() != 0 && tableView != null) {
            ObservableList<Subject> subjectsList = FXCollections.observableArrayList(subjects);

            id.setCellValueFactory(new PropertyValueFactory<>("id"));
            name.setCellValueFactory(new PropertyValueFactory<>("name"));
            teacher.setCellValueFactory(new PropertyValueFactory<>("subjectManager"));

            tableView.setItems(subjectsList);
        }
    }
}
