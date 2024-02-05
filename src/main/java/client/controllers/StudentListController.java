package client.controllers;

import db.entities.Student;
import javafx.scene.control.MenuBar;

import java.util.ArrayList;

public class StudentListController {
    public MenuBar menu;

    public ArrayList<Student> students = new ArrayList<>();

    public void setStudents(ArrayList<Student> s) {
        students = s;
    }
}
