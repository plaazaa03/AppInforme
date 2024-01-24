module es.ieslosmontecillos.appinforme {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.sql;
    requires java.desktop;
    requires jasperreports;


    opens es.ieslosmontecillos.appinforme to javafx.fxml;
    exports es.ieslosmontecillos.appinforme;
}