package es.ieslosmontecillos.appinforme;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.layout.VBox;
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
        TextField tituloIntro = new TextField("nº producto");
        Button btn = new Button();
        btn.setText("Informe");

        VBox root = new VBox();
        root.getChildren().addAll(tituloIntro, btn);

        btn.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {

                generaInforme(tituloIntro);
                System.out.println("Generando informe");

            }
        });

        Scene scene = new Scene(root, 300, 250);

        primaryStage.setTitle("Obtener informe");
        primaryStage.setScene(scene);
        primaryStage.show();

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

    private void generaInforme(TextField tintro) {
        try {
            JasperReport jr = (JasperReport)JRLoader.loadObject(getClass().getResource("PedidosProd.jasper"));
            //Map de parámetros
            Map parametros = new HashMap();
            int nproducto = Integer.valueOf(tintro.getText());
            parametros.put("ParamProducto", nproducto);

            JasperPrint jp = (JasperPrint) JasperFillManager.fillReport(jr, parametros, conexion);
            JasperViewer.viewReport(jp);
        } catch (JRException ex) {
            System.out.println("Error al recuperar el jasper");
            JOptionPane.showMessageDialog(null, ex);
        }
    }



}


