package es.ieslosmontecillos.appinforme;// VentanaIngresoClienteController.java

// Importa otras clases necesarias...

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.net.URL;
import java.sql.Connection;
import java.util.ResourceBundle;

public class VentanaIdController implements Initializable {

    @FXML
    private TextField clienteIdField;

    // Referencia al controlador principal
    private AppInforme mainController;

    // Referencia a la conexión a la BD
    private Connection conexion;

    // Método para inicializar datos
    public void initData(AppInforme mainController, Connection conexion) {
        this.mainController = mainController;
        this.conexion = conexion;
    }

    @FXML
    private void generarFacturaPorCliente() {
        String clienteId = clienteIdField.getText();
        // Llamar al método en el controlador principal con el ID del cliente ingresado
        mainController.generarFacturaPorClienteConId(clienteId);
        // Cerrar la ventana emergente
        ((Stage) clienteIdField.getScene().getWindow()).close();
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        // Puedes realizar alguna inicialización si es necesario
    }

    public void generarFacturasPorCliente(ActionEvent actionEvent) {
        String clienteId = clienteIdField.getText();
        // Llamar al método en el controlador principal con el ID del cliente ingresado
        mainController.generarFacturaPorClienteConId(clienteId);
        // Cerrar la ventana emergente
        ((Stage) clienteIdField.getScene().getWindow()).close();
    }
}
