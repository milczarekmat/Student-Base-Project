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
import java.util.*;
import java.util.stream.Stream;

public class ManagingController implements Initializable {
    public MenuBar menu;

    public TreeView<StudentGrade> treeView;

    public static ArrayList<Student> students = new ArrayList<>();

    public static void setStudents(ArrayList<Student> students) {
        ManagingController.students = students;
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        final TreeItem<StudentGrade> root = new TreeItem<>(new StudentGrade());
        root.setExpanded(true);


        students.forEach(student -> {

            List<StudentGrade> studentGrades = student.getStudentGrades().stream().toList()
                    .stream().filter(sg -> Objects.equals(student.getId(), sg.getStudent().getId())).toList();

            TreeItem<StudentGrade> parentItem;
            if (studentGrades.size() == 0) {
                StudentGrade studentGrade = new StudentGrade();
                studentGrade.setStudent(student);
                parentItem = new TreeItem<>(studentGrade);
            } else {
                StudentGrade singleStudentGrade = studentGrades.get(0);
                parentItem = new TreeItem<>(singleStudentGrade);
            }

            root.getChildren().add(parentItem);


            if (studentGrades.size() == 0) {
                parentItem.getChildren().add(new TreeItem<>(new StudentGrade()));
            } else {
                studentGrades.forEach(item -> {
                    Grade grade = item.getGrade();
                    Subject subject = item.getSubject();

                    TreeItem<StudentGrade> childItem = new TreeItem<>(item);

                    parentItem.getChildren().add(childItem);
                });
            }
        });


        treeView.setShowRoot(false);

        treeView.setRoot(root);

        treeView.setCellFactory(e -> new CustomTreeCell());
    }
}
