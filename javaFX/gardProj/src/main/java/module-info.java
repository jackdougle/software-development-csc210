module workspace.files {
    requires javafx.controls;
    requires javafx.fxml;

    opens workspace.files to javafx.fxml;
    exports workspace.files;
}
