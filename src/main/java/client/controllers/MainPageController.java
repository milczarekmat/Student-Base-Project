package client.controllers;

import client.connection.Connector;
import db.entities.Student;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.ListView;
import javafx.scene.control.MenuBar;
import javafx.scene.control.TextField;
import org.w3c.dom.Text;
import db.repositories.StudentRepository;

public class MainPageController implements Controller {
    public MenuBar menu;

    @FXML
    public TextField findStudent;
    @FXML
    private ListView<String> listView;

    @FXML
    private void handleSearchButton(ActionEvent event) {
        Integer studID = Integer.parseInt(findStudent.getText());


        System.out.println(studID);
    }
}
