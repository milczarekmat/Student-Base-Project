module student.base.project {
    requires javafx.graphics;
    requires javafx.controls;
    requires javafx.fxml;

    opens client;
    opens client.controllers;
}