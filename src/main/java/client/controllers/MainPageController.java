package client.controllers;

import client.connection.Connector;
import db.entities.Student;
import db.entities.StudentGrade;
import db.helperClasses.SubjectMeanInfo;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.ListView;
import javafx.scene.control.MenuBar;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;


import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;
import java.util.Set;
import java.util.stream.Collectors;


public class MainPageController implements Controller, Initializable {
    public MenuBar menu;

    @FXML
    public TextField findStudent;
    @FXML
    private ListView<String> listView;

    private List<String> grades;

    @FXML
    private void handleSearchButton(ActionEvent event) {
        Integer studID = Integer.parseInt(findStudent.getText());
        Set<StudentGrade> s = Connector.searchStudentById(studID);

        grades = s.stream().map(d -> new SubjectMeanInfo(d.getSubject().getName(), d.getGrade().getValue()).toString()).collect(Collectors.toList());
        grades.forEach(System.out::println);
        listView.setItems(FXCollections.observableList(grades));
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        listView = new ListView<>();
    }
}
