package client.customElements;

import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TreeCell;
import javafx.scene.control.TreeItem;
import javafx.scene.layout.HBox;

public class CustomTreeCell extends TreeCell<String> {
    @Override
    protected void updateItem(String item, boolean empty) {
        super.updateItem(item, empty);

        TreeItem<String> currentItem = getTreeItem();

        if (isEmpty()) {
            setGraphic(null);
            setText(null);
        } else {
            if (this.getTreeItem().isLeaf()) {
                HBox cellBox = new HBox(12);

                if (item.equals("no grades")) {
                    setGraphic(null);
                    setText("Brak ocen i przedmiotów dla studenta");
                    return;
                }

                Label label = new Label(item);
                Button changeGradeBtn = new Button("Zmień ocenę");
                Button deleteSubjectBtn = new Button("-");

                label.prefWidth(200);
                label.setMinWidth(200);
                cellBox.getChildren().addAll(deleteSubjectBtn, label, changeGradeBtn);

                changeGradeBtn.setOnAction(event -> {
                    System.out.println("Kliknięto przycisk zmien ocene" + currentItem.getValue());
//                  TODO do wyswietlania modal z formularzem zmiany oceny (wybor z listy dostepnych) i obsluga do serwera
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
                setText(item);
            }
        }
    }
}
