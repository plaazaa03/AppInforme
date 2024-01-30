package es.ieslosmontecillos.appinforme;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;

import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.TextField;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javax.swing.JOptionPane;
import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.JasperReport;
import net.sf.jasperreports.engine.design.JasperDesign;
import net.sf.jasperreports.engine.util.JRLoader;
import net.sf.jasperreports.engine.xml.JRXmlLoader;
import net.sf.jasperreports.view.JasperViewer;

public class AppInforme extends Application {
    @FXML
    public static Connection conexion = null;
    @FXML
    public TextField textNumCliente;

    @Override
    public void start(Stage primaryStage) {
        // establecemos la conexion con la BD
        conectaBD();
        // creamos la escena
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("appinforme.fxml"));
            Parent root = loader.load();


            //obtenemos el controlador y establecemos al conexion

            Scene scene = new Scene(root, 600, 400);

            primaryStage.setTitle("Aplicacion de informes");
            primaryStage.setScene(scene);
            primaryStage.show();

        } catch (IOException e) {
            throw new RuntimeException(e);
        }

    }


    private void conectaBD() {
        //establecemos la conexion con la BD
        String baseDatos = "jdbc:hsqldb:hsql://localhost:9001/test";
        String usuario = "sa";
        String clave = "";

        try {
            //Class.forName("org.hsqldb.jdbcDriver").newInstance();
            Class.forName("org.hsqldb.jdbcDriver");
            conexion = DriverManager.getConnection(baseDatos, usuario, clave);
        } catch (ClassNotFoundException cnfe) {
            System.err.println("Fallo al cargar JDBC");
            cnfe.printStackTrace();
            System.exit(1);
        } catch (SQLException sqle) {
            System.err.println("No se pudo conectar a BD");
            sqle.printStackTrace();
            System.exit(1);
        } catch (Exception ex) {
            System.err.println("Imposible Conectar");
            ex.printStackTrace();
            System.exit(1);
        }
    }

    private void generaInforme(String nombreInforme) {
        try {
            JasperReport jr = (JasperReport) JRLoader.loadObject(getClass().getResource(nombreInforme));
            //Map de parámetros
            Map parametros = new HashMap<>();



            String textField = textNumCliente.getText();
            if (textField == "") {
                System.err.println("No se ha introducido ningun parametro");
            } else {
                int clientes = Integer.valueOf(textField);
                parametros.put("NUM_CLIENTE", clientes);
            }


            //Cargar subInforme
            // JasperReport jsr = (JasperReport) JRLoader.loadObject(new File("C://Users/Usuario/IdeaProjects/AppInforme/src/main/resources/es/ieslosmontecillos/appinforme/Subinforme_Documento.jasper"));
            JasperReport jsr = (JasperReport) JRLoader.loadObject(getClass().getResource("Subinforme_Documento.jasper"));
            parametros.put("subReportParameter",jsr);

            JasperPrint jp = (JasperPrint) JasperFillManager.fillReport(jr, parametros, conexion);
            JasperViewer.viewReport(jp, false);
        } catch (Exception ex) {
            ex.printStackTrace();
            System.out.println("Error general al recuperar el informe");
            JOptionPane.showMessageDialog(null, ex);
        }

    }


    public void generarListadoFacturas(ActionEvent actionEvent) {
        try {
            generaInforme("facturas.jasper");
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public void generarVentasTotales(ActionEvent actionEvent) {
        try {
            generaInforme("Venta_Totales.jasper");
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public void generarFacturasPorCliente(ActionEvent actionEvent) {
        //mostrar la ventana para poder ingresar el dni del cliente

        try {
            generaInforme("facturas_por_cliente.jasper");
        } catch (Exception e) {
            throw new RuntimeException(e);
        }

    }

    public void generarSubinformeListadoFacturas(ActionEvent actionEvent) {
        try {
            generaInforme("SunInforme_Listado_Facturas_2.jasper");
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}


