package client.controllers;

import client.connection.Connector;
import db.entities.StudentGrade;
import db.helperClasses.SubjectMeanInfo;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
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
    public TableView<SubjectMeanInfo> tableView;

    @FXML
    public TableColumn<SubjectMeanInfo, String> name;

    @FXML
    public TableColumn<SubjectMeanInfo, Float> grade;

    private List<SubjectMeanInfo> grades;

    @FXML
    private void handleSearchButton(ActionEvent event) {
        Integer studID = Integer.parseInt(findStudent.getText());
        Set<StudentGrade> s = Connector.searchStudentById(studID);

        grades = s.stream().map(d -> new SubjectMeanInfo(d.getSubject().getName(), d.getGrade().getValue())).collect(Collectors.toList());

        if (grades.size() != 0 && tableView != null) {
            ObservableList<SubjectMeanInfo> listaStudentow = FXCollections.observableArrayList(grades);
            name.setCellValueFactory(new PropertyValueFactory<>("subjectName"));
            grade.setCellValueFactory(new PropertyValueFactory<>("mean"));
            tableView.setItems(listaStudentow);
        }
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {}

}

