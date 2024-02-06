package client.customElements;

import client.connection.Connector;
import client.controllers.ManagingController;
import client.controllers.MenuBarController;
import db.entities.Grade;
import db.entities.Student;
import db.entities.StudentGrade;
import db.helperClasses.EditGradeInfo;
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

public class CustomTreeCell extends TreeCell<StudentGrade> {
    @Override
    protected void updateItem(StudentGrade item, boolean empty) {
        super.updateItem(item, empty);

        TreeItem<StudentGrade> currentItem = getTreeItem();

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
                if (grade == null) {
                    gradeLabel = new Label("Brak oceny");
                } else {
                    gradeLabel = new Label(String.valueOf(grade.getValue()));
                }
                Button changeGradeBtn = new Button("Zmień ocenę");
                Button deleteSubjectBtn = new Button("-");

                subjectLabel.prefWidth(200);
                subjectLabel.setMinWidth(200);
                cellBox.getChildren().addAll(deleteSubjectBtn, subjectLabel, gradeLabel, changeGradeBtn);

                // przycisk od zmiany oceny
                changeGradeBtn.setOnAction(event -> {
                    Dialog<String> dialog = new Dialog<>();
                    dialog.setTitle("Zmiana oceny");

                    ButtonType okButtonType = new ButtonType("OK", ButtonBar.ButtonData.OK_DONE);
                    dialog.getDialogPane().getButtonTypes().addAll(okButtonType, ButtonType.CANCEL);

                    ComboBox<String> gradeComboBox = new ComboBox<>();
                    gradeComboBox.getItems().addAll(String.valueOf(3f), String.valueOf(3.5f),
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

                        EditGradeInfo pack = new EditGradeInfo(item.getStudent().getId(), item.getSubject().getId(), resultFloat);
                        Connector.editGradeForStudent(pack);

                        ArrayList<Student> students = Connector.getStudentsWithGradesWithoutNotification();
                        ManagingController.setStudents(students);

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

                    });
                });

                deleteSubjectBtn.setOnAction(event -> {
                    System.out.println("Kliknięto przycisk usun przedmiot" + currentItem.getValue() + currentItem.getParent().getValue());
//                  TODO do zrobienia alert czy na pewno oraz wyslanie zadania usuwania do serwera
                });

                setGraphic(cellBox);
                setText(null);
            } else {
                HBox cellBox = new HBox(20);

                Button addSubjectBtn = new Button("Dodaj przedmiot");

                HBox.setMargin(addSubjectBtn, new javafx.geometry.Insets(0, 25, 0, 0));

                cellBox.getChildren().addAll(addSubjectBtn);

                addSubjectBtn.setOnAction(event -> {
                    System.out.println("Kliknięto przycisk dodaj przedmiot" + currentItem.getValue());
//                  TODO do wyswietlania modal z formularzem dodania przedmiotu (wybor z listy dostepnych) i obsluga do serwera
                });

                setGraphic(cellBox);

                Student student = item.getStudent();
                setText(student.getName() + " " + student.getSurname() + " " + student.getId());
            }
        }
    }
}
