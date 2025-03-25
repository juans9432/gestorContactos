package co.edu.uniquindio.gestorcontactos.controladores;

import co.edu.uniquindio.gestorcontactos.modelo.Contacto;
import co.edu.uniquindio.gestorcontactos.modelo.GestionContactos;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;

import java.net.URL;
import java.time.LocalDate;
import java.util.ResourceBundle;

public class InicioControlador implements Initializable {
    @FXML
    private TextField txtNombre;


    @FXML
    private TextField txtApellido;

    @FXML
    private TextField txtNumero;

    @FXML
    private TextField txtCorreo;

    @FXML
    private DatePicker datePickerFechaNacimiento;

    @FXML
    private TableView<Contacto> tablaContactos;


    @FXML
    private TableColumn<Contacto, String> colNombre;


    @FXML
    private TableColumn<Contacto, String> colApellido;


    @FXML
    private TableColumn<Contacto, String> colNumero;

    @FXML
    private TableColumn<Contacto, String> colCorreo;

    @FXML
    private TableColumn<Contacto, String> colFecha;

    private final GestionContactos gestionContactos;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        colNombre.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getNombre()));
        colApellido.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getApellido()));
        colNumero.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getNumeroDeTelefono()));
        colCorreo.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getCorreoElectronico()));
        colFecha.setCellValueFactory(cellData -> {
            LocalDate fechaNacimiento = cellData.getValue().getFechaDeNacimiento();
            return new SimpleStringProperty(fechaNacimiento != null ? fechaNacimiento.toString() : "");
        });
    }

    public InicioControlador() {
        gestionContactos = new GestionContactos();
    }



    public void crearNota(ActionEvent e){
        LocalDate fechaNacimiento = datePickerFechaNacimiento.getValue();
        gestionContactos.agregarContacto(txtNombre.getText(), txtApellido.getText(), txtNumero.getText(), txtCorreo.getText(), fechaNacimiento);


        txtNombre.clear();
        txtApellido.clear();
        txtNumero.clear();
        txtCorreo.clear();
        datePickerFechaNacimiento.setValue(null);

        tablaContactos.setItems( FXCollections.observableArrayList(gestionContactos.listarContactos()) );


    }

}
