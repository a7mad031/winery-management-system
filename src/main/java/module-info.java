module bg.tu_varna.sit.winefactory {
    requires javafx.controls;
    requires javafx.fxml;

    requires org.hibernate.orm.core;
    requires jakarta.persistence;
    requires java.sql;
    requires java.naming;
    requires jakarta.validation;

    opens bg.tu_varna.sit.winefactory.data.entities to org.hibernate.orm.core;
    exports bg.tu_varna.sit.winefactory.data.entities;

    opens bg.tu_varna.sit.winefactory.data.access to org.hibernate.orm.core;
    exports bg.tu_varna.sit.winefactory.data.access;

    exports bg.tu_varna.sit.winefactory.application;
    opens bg.tu_varna.sit.winefactory.application to javafx.fxml;

    exports bg.tu_varna.sit.winefactory.presentation.controllers;
    opens bg.tu_varna.sit.winefactory.presentation.controllers to javafx.fxml;

}