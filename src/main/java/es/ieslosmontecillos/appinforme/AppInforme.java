package es.ieslosmontecillos.appinforme;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;

import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javax.swing.JOptionPane;
import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.JasperReport;
import net.sf.jasperreports.engine.util.JRLoader;
import net.sf.jasperreports.view.JasperViewer;

public class AppInforme extends Application {
    public static Connection conexion = null;

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
            JasperReport jr = (JasperReport)JRLoader.loadObject(getClass().getResource(nombreInforme));
            //Map de parámetros
            Map<String, Object> parametros = new HashMap<>();

            JasperPrint jp = (JasperPrint) JasperFillManager.fillReport(jr, parametros, conexion);
            JasperViewer.viewReport(jp);
        } catch (JRException ex) {
            System.out.println("Error al recuperar el jasper");
            JOptionPane.showMessageDialog(null, ex);
        }
    }


    public void generarListadoFacturas(ActionEvent actionEvent) {
        generaInforme("facturas.jasper");
    }

    public void generarVentasTotales(ActionEvent actionEvent) {
        generaInforme("ventas_totales.jasper");
    }

    public void generarFacturasPorCliente(ActionEvent actionEvent) {
        //generaInforme();
    }

    public void generarSubinformeListadoFacturas(ActionEvent actionEvent) {
        //generaInforme();
    }
}


