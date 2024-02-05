package client.controllers;
import db.entities.Student;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.MenuBar;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;

import java.util.ArrayList;

public class StudentListController {
    public MenuBar menu;
    public TableView<Student> tableView;
    public TableColumn<Student, Integer> nrIndeksuColumn;
    public TableColumn<Student, String> imieColumn;
    public TableColumn<Student, String> nazwiskoColumn;
    public TableColumn<Student, String> wydzialColumn;

    public static ArrayList<Student> students = new ArrayList<>();

    public static void setStudent(ArrayList<Student> s) {
        students = s;
    }

    public void initialize() {
        ObservableList<Student> listaStudentow = FXCollections.observableArrayList(students);
        tableView.setItems(listaStudentow);

        nrIndeksuColumn.setCellValueFactory(new PropertyValueFactory<>("id"));
        imieColumn.setCellValueFactory(new PropertyValueFactory<>("name"));
        nazwiskoColumn.setCellValueFactory(new PropertyValueFactory<>("surname"));
        wydzialColumn.setCellValueFactory(new PropertyValueFactory<>("department"));
    }
}
