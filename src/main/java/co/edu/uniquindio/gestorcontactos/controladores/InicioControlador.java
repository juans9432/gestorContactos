package co.edu.uniquindio.gestorcontactos.controladores;

import co.edu.uniquindio.gestorcontactos.modelo.Contacto;
import co.edu.uniquindio.gestorcontactos.modelo.GestionContactos;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.FileChooser;

import java.io.File;
import java.net.URL;
import java.time.LocalDate;
import java.util.Optional;
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
    private TextField txtBuscar;

    @FXML
    private DatePicker txtFechaNacimiento;

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

    @FXML
    private ImageView imgContacto;

    private Contacto contactoSeleccionado;

    private final GestionContactos gestionContactos;

    private ObservableList<Contacto> contactosObservable;

    public InicioControlador() {
        gestionContactos = new GestionContactos();
    }

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
        contactosObservable = FXCollections.observableArrayList();
        cargarContactos();

        tablaContactos.setOnMouseClicked(e -> {
            //Obtener la nota seleccionada
            contactoSeleccionado = tablaContactos.getSelectionModel().getSelectedItem();

            if(contactoSeleccionado != null){
                txtNombre.setText(contactoSeleccionado.getNombre());
                txtApellido.setText(contactoSeleccionado.getApellido());
                txtNumero.setText(contactoSeleccionado.getNumeroDeTelefono());
                txtCorreo.setText(contactoSeleccionado.getCorreoElectronico());
                imgContacto.setImage(contactoSeleccionado.getImgContacto());

                txtFechaNacimiento.setValue(contactoSeleccionado.getFechaDeNacimiento() != null
                        ? contactoSeleccionado.getFechaDeNacimiento()
                        : null);
            }

        });
    }


    public void crearContacto(ActionEvent e){
        try{
            LocalDate fechaNacimiento = txtFechaNacimiento.getValue();
            gestionContactos.agregarContacto(
                    txtNombre.getText(),
                    txtApellido.getText(),
                    txtNumero.getText(),
                    txtCorreo.getText(),
                    txtFechaNacimiento.getValue()
            );
            actualizarContactos();
            limpiarCampos();
            mostrarAlerta("Contacto agergado correctamente", Alert.AlertType.INFORMATION);
        }catch (Exception ex){
            mostrarAlerta(ex.getMessage(), Alert.AlertType.ERROR);

        }
    }

    private void mostrarAlerta(String mensaje, Alert.AlertType tipo){


        Alert alert = new Alert(tipo);
        alert.setTitle("Información");
        alert.setHeaderText(null);
        alert.setContentText(mensaje);
        alert.show();


    }

    private void cargarContactos() {
        contactosObservable.setAll(gestionContactos.listarContactos());
        tablaContactos.setItems(contactosObservable);
    }

    public void actualizarContactos() {
        contactosObservable.setAll(gestionContactos.listarContactos());
    }


    //Modifica solo un contacto y luego actualiza la tabla
    public void actualizarContacto() {
        //Obtener el contacto seleccionado en la tabla
        Contacto contactoSeleccionado = tablaContactos.getSelectionModel().getSelectedItem();
        if (contactoSeleccionado != null) {
            contactoSeleccionado.setNombre(txtNombre.getText());
            contactoSeleccionado.setApellido(txtApellido.getText());
            contactoSeleccionado.setNumeroDeTelefono(txtNumero.getText());
            contactoSeleccionado.setCorreoElectronico(txtCorreo.getText());
            contactoSeleccionado.setFechaDeNacimiento(txtFechaNacimiento.getValue());
            gestionContactos.actualizarContacto(contactoSeleccionado);
            actualizarContactos();
            mostrarAlerta("contacto actualizado correctamente", Alert.AlertType.INFORMATION);
        } else {
            mostrarAlerta("seleccione un contacto para actualizar", Alert.AlertType.WARNING);
        }
    }

    public void eliminarContacto() {
        // Obtener el contacto seleccionado en la tabla
        Contacto contactoSeleccionado = tablaContactos.getSelectionModel().getSelectedItem();

        if (contactoSeleccionado != null) {

            Alert confirmacion = new Alert(Alert.AlertType.CONFIRMATION);
            confirmacion.setTitle("confirmar eliminacion");
            confirmacion.setHeaderText(null);
            confirmacion.setContentText("¿esta seguro de que desea eliminar este contacto?");
            Optional<ButtonType> resultado = confirmacion.showAndWait();

            if (resultado.isPresent() && resultado.get() == ButtonType.OK) {

                gestionContactos.eliminarContacto(contactoSeleccionado);
                actualizarContactos();
                limpiarCampos();
                mostrarAlerta("contacto eliminado correctamente", Alert.AlertType.INFORMATION);
            }
        } else {
            mostrarAlerta("seleccione un contacto para eliminar", Alert.AlertType.WARNING);
        }
    }
    //

    public void buscarContacto() {
        String nombreBuscado = txtBuscar.getText();
        String telefonoBuscado= txtBuscar.getText();

        if (nombreBuscado.isEmpty()) {
            mostrarAlerta("ingrese un nombre para buscar.", Alert.AlertType.WARNING);
            return;
        } else if (telefonoBuscado.isEmpty()) {
            mostrarAlerta("ingrese un telefono para buscar", Alert.AlertType.WARNING);
        }

        Contacto contactoEncontrado = gestionContactos.buscarContactoPorNombre(nombreBuscado);

        if (contactoEncontrado != null) {
            String info = "Nombre: " + contactoEncontrado.getNombre() + "\n" +
                    "Apellido: " + contactoEncontrado.getApellido() + "\n" +
                    "Teléfono: " + contactoEncontrado.getNumeroDeTelefono() + "\n" +
                    "Correo: " + contactoEncontrado.getCorreoElectronico() + "\n" +
                    "Fecha de Nacimiento: " + (contactoEncontrado.getFechaDeNacimiento() != null ? contactoEncontrado.getFechaDeNacimiento().toString() : "No registrada");
            mostrarAlerta(info, Alert.AlertType.INFORMATION);
        } else {
            mostrarAlerta("no se encontró ningún contacto con ese nombre.", Alert.AlertType.ERROR);
        }
    }
    //seleccionar la foto del contacto

    public void seleccionarFoto() {
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Seleccionar Imagen");

        fileChooser.getExtensionFilters().add(
                new FileChooser.ExtensionFilter("Imágenes", "*.png", "*.jpg")
        );

        File file = fileChooser.showOpenDialog(null);

        if (file != null) {
            Image imagen = new Image(file.toURI().toString());
            imgContacto.setImage(imagen); // muestra la imagen en el ImageView
        }
    }

    private void limpiarCampos(){
        txtNombre.clear();
        txtApellido.clear();
        txtNumero.clear();
        txtCorreo.clear();
        txtFechaNacimiento.setValue(null);
        imgContacto.setImage(null);
    }
}

