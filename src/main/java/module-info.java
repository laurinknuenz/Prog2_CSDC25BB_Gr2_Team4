module at.ac.fhcampuswien.fhmdb {
    requires javafx.controls;
    requires javafx.fxml;
    requires okhttp3;
    requires com.google.gson;

    requires com.jfoenix;
    requires annotations;

    // opens model package to gson.. overrides java 17 restrictions
    opens at.ac.fhcampuswien.fhmdb to javafx.fxml;
    exports at.ac.fhcampuswien.fhmdb;
    opens at.ac.fhcampuswien.fhmdb.models;
}