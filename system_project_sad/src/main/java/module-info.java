module project_system.org {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.sql;
    requires javafx.graphics;
    requires javax.websocket.api;
    requires com.google.gson;
    requires javafx.base;
    requires org.json;
    requires java.desktop;
    requires javafx.web;
  

    opens project_system.org to javafx.fxml;
    exports project_system.org;
}