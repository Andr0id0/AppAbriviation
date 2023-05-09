module com.abriv.abriviator {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.sql;
    requires java.desktop;


    opens com.abriv.abriviator to javafx.fxml;
    exports com.abriv.abriviator;
}