package client.controllers;

import db.entities.Student;
import db.entities.Subject;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.Initializable;
import javafx.scene.control.MenuBar;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;

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
