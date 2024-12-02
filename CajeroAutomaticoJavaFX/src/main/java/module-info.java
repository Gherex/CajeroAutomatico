module com.gherex.cajeroautomaticojavafx {
    requires javafx.controls;
    requires javafx.fxml;


    opens com.gherex.cajeroautomaticojavafx to javafx.fxml;
    exports com.gherex.cajeroautomaticojavafx;
}