module student.base.project {
    requires javafx.graphics;
    requires javafx.controls;
    requires javafx.fxml;
    requires jakarta.persistence;
    requires org.hibernate.orm.core;
    requires java.naming;

    opens client;
    opens client.controllers;
    opens server;
    opens db.entities;
}