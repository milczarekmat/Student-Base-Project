package client.customElements;

import client.connection.Connector;
import client.controllers.ManagingController;
import client.controllers.MenuBarController;
import db.entities.Grade;
import db.entities.Student;
import db.entities.StudentGrade;
import db.entities.Subject;
import db.helperClasses.ManageInfo;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class CustomTreeCell extends TreeCell<StudentGrade> {
    @Override
    protected void updateItem(StudentGrade item, boolean empty) {
        super.updateItem(item, empty);

        if (isEmpty()) {
            setGraphic(null);
            setText(null);
        } else {
            if (this.getTreeItem().isLeaf()) {
                HBox cellBox = new HBox(12);

                if (item.getSubject() == null) {
                    setGraphic(null);
                    setText("Brak ocen i przedmiotów dla studenta");
                    return;
                }

                Label subjectLabel = new Label(item.getSubject().getName());
                Grade grade = item.getGrade();

                Label gradeLabel;
                Label gradeFullNameLabel;
                if (grade == null) {
                    gradeLabel = new Label("Brak oceny");
                    gradeFullNameLabel = new Label(null);
                } else {
                    gradeLabel = new Label(String.valueOf(grade.getValue()));
                    gradeFullNameLabel = new Label(grade.getName());
                }
                Button changeGradeBtn = new Button("Zmień ocenę");
                Button deleteSubjectBtn = new Button("-");

                subjectLabel.prefWidth(200);
                subjectLabel.setMinWidth(200);
                cellBox.getChildren().addAll(deleteSubjectBtn, subjectLabel, gradeFullNameLabel, gradeLabel, changeGradeBtn);

                // Przycisk od zmiany oceny
                changeGradeBtn.setOnAction(event -> {
                    Dialog<String> dialog = new Dialog<>();
                    dialog.setTitle("Zmiana oceny");

                    ButtonType okButtonType = new ButtonType("OK", ButtonBar.ButtonData.OK_DONE);
                    dialog.getDialogPane().getButtonTypes().addAll(okButtonType, ButtonType.CANCEL);

                    ComboBox<String> gradeComboBox = new ComboBox<>();
                    gradeComboBox.getItems().addAll(String.valueOf(2f), String.valueOf(3f), String.valueOf(3.5f),
                            String.valueOf(4f), String.valueOf(4.5f), String.valueOf(5f), "Brak oceny");

                    VBox layout = new VBox(10);
                    layout.getChildren().addAll(new Label("Wybierz nową ocenę:"), gradeComboBox);
                    dialog.getDialogPane().setContent(layout);


                    dialog.setResultConverter(dialogButton -> {
                        if (dialogButton == okButtonType) {
                            return gradeComboBox.getValue();
                        }
                        return null;
                    });

                    dialog.showAndWait().ifPresent(result -> {
                        System.out.println("Wybrano nową ocenę: " + result);

                        float resultFloat;
                        if (result.equals("Brak oceny")) {
                            resultFloat = 0f;
                        } else {
                            resultFloat = Float.parseFloat(result);
                        }

                        ManageInfo pack = new ManageInfo(item.getStudent().getId(), item.getSubject().getId(), resultFloat);
                        Connector.editGradeForStudent(pack);

                        ArrayList<Student> students = Connector.getStudentsWithGradesWithoutServerNotification();
                        ManagingController.setStudents(students);

                        reloadView(event);
                    });
                });

                // Przycisk od usuwania przedmiotu
                deleteSubjectBtn.setOnAction(event -> {
                    Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
                    alert.setTitle("Potwierdzenie usunięcia");
                    alert.setHeaderText("Czy na pewno chcesz usunąć ten przedmiot dla studenta?");
                    alert.setContentText("Usunięcie przedmiotu spowoduje usunięcie związanej z nim oceny.");

                    Optional<ButtonType> result = alert.showAndWait();
                    if (result.isPresent() && result.get() == ButtonType.OK) {
                        ManageInfo pack = new ManageInfo(item.getStudent().getId(), item.getSubject().getId(), 0);

                        Connector.removeSubjectForStudent(pack);

                        ArrayList<Student> students = Connector.getStudentsWithGradesWithoutServerNotification();
                        ManagingController.setStudents(students);

                        reloadView(event);
                    }
                });

                setGraphic(cellBox);
                setText(null);
            } else {
                HBox cellBox = new HBox(20);

                Button addSubjectBtn = new Button("Dodaj przedmiot");

                HBox.setMargin(addSubjectBtn, new javafx.geometry.Insets(0, 25, 0, 0));

                // uzyskiwanie nazw przedmiotow, ktore nie zostaly jeszcze dodane dla studenta
                ArrayList<Subject> subjects = Connector.getSubjects();
                List<String> subjectNames = subjects.stream()
                        .filter(subject -> {
                            for (StudentGrade studentGrade : item.getStudent().getStudentGrades()) {
                                if (subject.getName().equals(studentGrade.getSubject().getName())) {
                                    return false;
                                }
                            }
                            return true;
                        })
                        .map(Subject::getName).toList();

                if (subjectNames.size() > 0) {
                    cellBox.getChildren().addAll(addSubjectBtn);
                }

                // Przycisk od dodawania przedmiotu
                addSubjectBtn.setOnAction(event -> {
                    Dialog<String> dialog = new Dialog<>();
                    dialog.setTitle("Dodawanie przedmiotu dla studenta");

                    ButtonType okButtonType = new ButtonType("OK", ButtonBar.ButtonData.OK_DONE);
                    dialog.getDialogPane().getButtonTypes().addAll(okButtonType, ButtonType.CANCEL);

                    ComboBox<String> subjectComboBox = new ComboBox<>();

                    subjectNames.forEach(name -> subjectComboBox.getItems().add(name));

                    VBox layout = new VBox(14);
                    layout.getChildren().addAll(new Label("Dodaj przedmiot dla: " + " "
                            + item.getStudent().getName() + " " + item.getStudent().getSurname() + " " + item.getStudent().getId()), subjectComboBox);
                    dialog.getDialogPane().setContent(layout);


                    dialog.setResultConverter(dialogButton -> {
                        if (dialogButton == okButtonType) {
                            return subjectComboBox.getValue();
                        }
                        return null;
                    });

                    dialog.showAndWait().ifPresent(result -> {
                        System.out.println("Wybrano nowy przedmiot: " + result);

                        Subject selectedSubject = subjects.stream()
                                .filter(subject -> subject.getName().equals(result))
                                .findFirst()
                                .orElse(null);

                        assert selectedSubject != null;

                        ManageInfo manageInfoPack = new ManageInfo(item.getStudent().getId(), selectedSubject.getId(), 0f);

                        Connector.addSubjectForStudent(manageInfoPack);

                        ArrayList<Student> students = Connector.getStudentsWithGradesWithoutServerNotification();
                        ManagingController.setStudents(students);

                        reloadView(event);
                    });

                });

                setGraphic(cellBox);

                Student student = item.getStudent();
                setText(student.getName() + " " + student.getSurname() + " " + student.getId());
            }
        }
    }

    private void reloadView(ActionEvent event) {
        Node sourceNode = (Node) event.getSource();
        Stage primaryStage = (Stage) sourceNode.getScene().getWindow();
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/scenes/main/menuBar.fxml"));

        Parent root;
        try {
            root = loader.load();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        MenuBarController menuBarController = loader.getController();
        Scene scene = new Scene(root);
        primaryStage.setScene(scene);
        primaryStage.show();

        try {
            menuBarController.toManaging();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
