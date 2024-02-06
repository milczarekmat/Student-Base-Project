package client.connection;

import db.entities.Operations;
import db.entities.Student;
import db.entities.Subject;
import db.helperClasses.ManageInfo;
import db.helperClasses.SubjectMeanInfo;
import javafx.scene.control.Alert;

import java.io.*;
import java.net.*;
import java.util.ArrayList;

public class Connector {

    private static boolean loading;

    private static boolean connected;

    private static Socket socket = null;
    private static ObjectInputStream input = null;
    private static ObjectOutputStream out = null;

    public static boolean isConnected() {
        return connected;
    }

    public static void connect() {
        try {
            loading = true;
            connected = false;
            socket = new Socket("localhost", 8080);
            out =  new ObjectOutputStream(socket.getOutputStream());
            input = new ObjectInputStream(socket.getInputStream());
            System.out.println("Connected");
        }
        catch (UnknownHostException u) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Błąd połączenia");
            alert.setHeaderText("Brak połączenia z serwerem");
            alert.setContentText("Sprawdź połączenie z serwerem.");

            alert.showAndWait();
            loading = false;
            return;
        }
        catch (IOException i) {
//            System.out.println(i);
            loading = false;
            return;
        }
//        catch (ClassNotFoundException e) {
//            throw new RuntimeException(e);
//        }
        loading = false;
        connected = true;
    }

    public void disconnect() {
        try {
            input.close();
            out.close();
            socket.close();
        }
        catch (IOException i) {
            loading = false;
            return;
        }
        loading = false;
        connected = false;
        socket = null;
        System.out.println("Disconnected");
    }

    public ArrayList<Student> getStudents() {
        try {
            out.writeObject(Operations.SHOW_STUDENTS);
        } catch (IOException e) {
            printConnectionLost();
            throw new RuntimeException(e);
        }

        ArrayList<Student> receivedStudenci;

        try {
            receivedStudenci = (ArrayList<Student>) input.readObject();
        } catch (IOException | ClassNotFoundException e) {
            throw new RuntimeException(e);
        }

        return receivedStudenci;
    }

    public ArrayList<Student> getStudentsWithGrades() {
        try {
            out.writeObject(Operations.SHOW_STUDENTS_WITH_GRADES);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        ArrayList<Student> receivedStudenci;

        try {
            receivedStudenci = (ArrayList<Student>) input.readObject();
        } catch (IOException | ClassNotFoundException e) {
            throw new RuntimeException(e);
        }

        return receivedStudenci;
    }

    public static ArrayList<Student> getStudentsWithGradesWithoutServerNotification() {

        ArrayList<Student> receivedStudenci;

        try {
            receivedStudenci = (ArrayList<Student>) input.readObject();
        } catch (IOException | ClassNotFoundException e) {
            throw new RuntimeException(e);
        }

        return receivedStudenci;
    }

    public static ArrayList<Subject> getSubjects() {
        try {
            out.writeObject(Operations.SHOW_SUBJECTS);
        } catch (IOException e) {
            printConnectionLost();
            throw new RuntimeException(e);
        }

        ArrayList<Subject> receivedSubjects;

        try {
            receivedSubjects = (ArrayList<Subject>) input.readObject();
        } catch (IOException | ClassNotFoundException e) {
            throw new RuntimeException(e);
        }

        return receivedSubjects;
    }

    public static void addStudent(Student student) {
        try {
            out.writeObject(Operations.ADD_STUDENT);
        } catch (IOException e) {
            printConnectionLost();
            throw new RuntimeException(e);
        }
        try {
            out.writeObject(student);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        try{
            String message = (String) input.readObject();
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Info");
            alert.setHeaderText("Status operacji dodawnia studenta:");
            alert.setContentText(message);

            alert.showAndWait();
        } catch (ClassNotFoundException | IOException e) {
            throw new RuntimeException(e);
        }
    }

    public static void deleteStudent (int studentID) {
        try {
            out.writeObject(Operations.DELETE_STUDENT);
        } catch (IOException e) {
            printConnectionLost();
            throw new RuntimeException(e);
        }
        try {
            out.writeObject(studentID);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        try{
            String message = (String) input.readObject();
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Info");
            alert.setHeaderText("Status operacji usuwania studenta:");
            alert.setContentText(message);

            alert.showAndWait();
        } catch (ClassNotFoundException | IOException e) {
            throw new RuntimeException(e);
        }
    }

    public static ArrayList<SubjectMeanInfo> getStudentGrades() {
        try {
            out.writeObject(Operations.GET_SUBJECTS_WITH_GRADES);
        } catch (IOException e) {
            printConnectionLost();
            throw new RuntimeException(e);
        }

        ArrayList<SubjectMeanInfo> receivedSubjects;
        try {
            receivedSubjects = (ArrayList<SubjectMeanInfo>) input.readObject();
        } catch (IOException | ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
        return receivedSubjects;
    }

    public static void deleteSubject(String subjectName) {
        try {
            out.writeObject(Operations.DELETE_SUBJECT);
        }
        catch (IOException e) {
            printConnectionLost();
            throw new RuntimeException(e);
        }
        try {
            out.writeObject(subjectName);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        try{
            String message = (String) input.readObject();
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Info");
            alert.setHeaderText("Status operacji dodawania przedmiotu:");
            alert.setContentText(message);

            alert.showAndWait();
        } catch (ClassNotFoundException | IOException e) {
            throw new RuntimeException(e);
        }
    }

    public static void addSubject(Subject subject) {
        try {
            out.writeObject(Operations.ADD_SUBJECT);
        } catch (IOException e) {
            printConnectionLost();
            throw new RuntimeException(e);
        }
        try {
            out.writeObject(subject);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public static void editGradeForStudent(ManageInfo pack) {
        try {
            out.writeObject(Operations.EDIT_STUDENT_GRADE);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        try {
            out.writeObject(pack);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

    }

    public static void removeSubjectForStudent(ManageInfo pack) {
        try {
            out.writeObject(Operations.REMOVE_SUBJECT_FOR_STUDENT);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        try {
            out.writeObject(pack);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

    }

    public static void addSubjectForStudent(ManageInfo pack) {
        try {
            out.writeObject(Operations.ADD_SUBJECT_FOR_STUDENT);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        try {
            out.writeObject(pack);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

    }
    public static void printConnectionLost(){
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle("Błąd połączenia");
        alert.setHeaderText("Brak połączenia z serwerem");
        alert.setContentText("Sprawdź połączenie z serwerem.");

        alert.showAndWait();
    }
}
