module com.files {
    requires javafx.controls;
    requires javafx.fxml;
    requires javafx.base;

    opens com.files to javafx.fxml;
    exports com.files;
}
