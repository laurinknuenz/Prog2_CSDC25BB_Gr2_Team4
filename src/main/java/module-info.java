module at.ac.fhcampuswien.fhmdb {
    requires javafx.controls;
    requires javafx.fxml;
    requires okhttp3;
    requires com.google.gson;
    requires com.h2database;
    requires ormlite.jdbc;

    requires com.jfoenix;
    requires annotations;
    requires java.sql;

    opens at.ac.fhcampuswien.fhmdb.database to ormlite.jdbc;
    // opens model package to gson.. overrides java 17 restrictions
    opens at.ac.fhcampuswien.fhmdb to javafx.fxml;
    exports at.ac.fhcampuswien.fhmdb;
    opens at.ac.fhcampuswien.fhmdb.models;
    exports at.ac.fhcampuswien.fhmdb.interfaces;
    opens at.ac.fhcampuswien.fhmdb.interfaces to javafx.fxml;
}