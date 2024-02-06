package client.controllers;

import javafx.fxml.Initializable;
import javafx.scene.control.MenuBar;
import javafx.scene.control.TreeItem;
import javafx.scene.control.TreeView;

import client.customElements.CustomTreeCell;

import java.net.URL;
import java.util.ResourceBundle;

public class ManagingController implements Initializable {
    public MenuBar menu;

    public TreeView<String> treeView;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        final TreeItem<String> root = new TreeItem<>("Root node");
        root.setExpanded(true);

        final TreeItem<String> parentNode1 = new TreeItem<>("Jan Kowalski 240753");

        final TreeItem<String> childNode1 = new TreeItem<>("Matematyka     dostateczny (3)");
        final TreeItem<String> childNode2 = new TreeItem<>("Przyroda       bardzo dobry (5)");


        root.getChildren().setAll(parentNode1);

        parentNode1.getChildren().addAll(childNode1, childNode2);


        treeView.setShowRoot(false);

        treeView.setRoot(root);

        treeView.setCellFactory(e -> new CustomTreeCell());
    }
}
