package client.controllers;

import db.helperClasses.SubjectMeanInfo;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.Initializable;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;

import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;

public class SubjectMeanController implements Controller, Initializable {
    public TableView<SubjectMeanInfo> tableMeanView;
    public TableColumn<SubjectMeanInfo, String> subjectName;
    public TableColumn<SubjectMeanInfo, Float> mean;

    public static ArrayList<SubjectMeanInfo> subjectsWithMeans = new ArrayList<>();

    public static void setSubjectsWithMeans(ArrayList<SubjectMeanInfo> subjectsWithMeans) {
        SubjectMeanController.subjectsWithMeans = subjectsWithMeans;
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        if (subjectsWithMeans.size() != 0 && tableMeanView != null) {
            ObservableList<SubjectMeanInfo> subjectsList = FXCollections.observableArrayList(subjectsWithMeans);

            subjectName.setCellValueFactory(new PropertyValueFactory<>("subjectName"));
            mean.setCellValueFactory(new PropertyValueFactory<>("mean"));

            tableMeanView.setItems(subjectsList);
        }
    }
}
