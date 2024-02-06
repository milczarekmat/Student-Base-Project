package client.controllers;

import client.customElements.CustomTreeCell;
import db.entities.Grade;
import db.entities.Student;
import db.entities.StudentGrade;
import db.entities.Subject;
import javafx.fxml.Initializable;
import javafx.scene.control.MenuBar;
import javafx.scene.control.TreeItem;
import javafx.scene.control.TreeView;

import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;
import java.util.Set;

public class ManagingController implements Initializable {
    public MenuBar menu;

    public TreeView<String> treeView;

    public static ArrayList<Student> students = new ArrayList<>();

    public static void setStudents(ArrayList<Student> students) {
        ManagingController.students = students;
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        final TreeItem<String> root = new TreeItem<>("Root node");
        root.setExpanded(true);


        students.forEach(student -> {
            String name = student.getName();
            String surname = student.getSurname();
            TreeItem<String> parentItem = new TreeItem<>(name + " " + surname);
            root.getChildren().add(parentItem);

            Set<StudentGrade> studentGrades = student.getStudentGrades();

            if (studentGrades.size() == 0) {
                parentItem.getChildren().add(new TreeItem<>("no grades"));
            } else {
                studentGrades.forEach(item -> {
                    Grade grade = item.getGrade();
                    Subject subject = item.getSubject();

                    TreeItem<String> childItem = new TreeItem<>(subject.getName() + "    " + grade.getValue());

                    parentItem.getChildren().add(childItem);
                });
            }
        });


        treeView.setShowRoot(false);

        treeView.setRoot(root);

        treeView.setCellFactory(e -> new CustomTreeCell());
    }
}
