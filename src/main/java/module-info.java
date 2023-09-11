module com.example.fx_sms {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.sql;
    requires org.postgresql.jdbc;


    opens com.example.fx_sms to javafx.fxml;
    exports com.example.fx_sms;
}