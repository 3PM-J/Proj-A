package upeu.edu.pe.registroa.controller;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import upeu.edu.pe.registroa.model.Paciente;
import upeu.edu.pe.registroa.service.PacienteService;
import upeu.edu.pe.registroa.util.ValidationUtils;

import java.net.URL;
import java.time.LocalDate;
import java.util.Optional;
import java.util.ResourceBundle;

@Component
public class PacienteController implements Initializable {

    @FXML private TextField txtNombres;
    @FXML private TextField txtApellidos;
    @FXML private TextField txtDni;
    @FXML private DatePicker dpFechaNacimiento;
    @FXML private TextField txtTelefono;
    @FXML private TextField txtEmail;
    @FXML private TextField txtDireccion;
    @FXML private ComboBox<String> cbGenero;
    @FXML private TextField txtBuscar;
    
    @FXML private TableView<Paciente> tablePacientes;
    @FXML private TableColumn<Paciente, Long> colId;
    @FXML private TableColumn<Paciente, String> colNombres;
    @FXML private TableColumn<Paciente, String> colApellidos;
    @FXML private TableColumn<Paciente, String> colDni;
    @FXML private TableColumn<Paciente, LocalDate> colFechaNacimiento;
    @FXML private TableColumn<Paciente, String> colTelefono;
    @FXML private TableColumn<Paciente, String> colEmail;
    @FXML private TableColumn<Paciente, String> colGenero;
    
    @FXML private Button btnGuardar;
    @FXML private Button btnActualizar;
    @FXML private Button btnEliminar;
    @FXML private Button btnLimpiar;
    @FXML private Button btnBuscar;

    @Autowired
    private PacienteService pacienteService;

    private ObservableList<Paciente> pacientesList = FXCollections.observableArrayList();
    private ObservableList<Paciente> filteredList = FXCollections.observableArrayList();
    private Paciente pacienteSeleccionado;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        initializeTable();
        initializeComboBox();
        loadPacientes();
        setupSearchFunctionality();
        
        // Listener para selección en la tabla
        tablePacientes.getSelectionModel().selectedItemProperty().addListener(
            (obs, oldSelection, newSelection) -> {
                if (newSelection != null) {
                    pacienteSeleccionado = newSelection;
                    fillForm(newSelection);
                    btnActualizar.setDisable(false);
                    btnEliminar.setDisable(false);
                    btnGuardar.setDisable(true);
                }
            }
        );
    }

    private void initializeTable() {
        colId.setCellValueFactory(new PropertyValueFactory<>("id"));
        colNombres.setCellValueFactory(new PropertyValueFactory<>("nombres"));
        colApellidos.setCellValueFactory(new PropertyValueFactory<>("apellidos"));
        colDni.setCellValueFactory(new PropertyValueFactory<>("dni"));
        colFechaNacimiento.setCellValueFactory(new PropertyValueFactory<>("fechaNacimiento"));
        colTelefono.setCellValueFactory(new PropertyValueFactory<>("telefono"));
        colEmail.setCellValueFactory(new PropertyValueFactory<>("email"));
        colGenero.setCellValueFactory(new PropertyValueFactory<>("genero"));
        
        tablePacientes.setItems(filteredList);
        
        // Configurar el comportamiento de selección
        tablePacientes.setRowFactory(tv -> {
            TableRow<Paciente> row = new TableRow<>();
            row.setOnMouseClicked(event -> {
                if (event.getClickCount() == 2 && (!row.isEmpty())) {
                    Paciente rowData = row.getItem();
                    pacienteSeleccionado = rowData;
                    fillForm(rowData);
                }
            });
            return row;
        });
    }

    private void initializeComboBox() {
        cbGenero.setItems(FXCollections.observableArrayList("Masculino", "Femenino", "Otro"));
    }

    private void setupSearchFunctionality() {
        txtBuscar.textProperty().addListener((observable, oldValue, newValue) -> {
            filterPacientes(newValue);
        });
    }

    private void filterPacientes(String searchText) {
        filteredList.clear();
        if (searchText == null || searchText.isEmpty()) {
            filteredList.addAll(pacientesList);
        } else {
            String lowerCaseFilter = searchText.toLowerCase();
            for (Paciente paciente : pacientesList) {
                if (paciente.getNombres().toLowerCase().contains(lowerCaseFilter) ||
                    paciente.getApellidos().toLowerCase().contains(lowerCaseFilter) ||
                    paciente.getDni().contains(searchText) ||
                    (paciente.getEmail() != null && paciente.getEmail().toLowerCase().contains(lowerCaseFilter))) {
                    filteredList.add(paciente);
                }
            }
        }
    }

    private void loadPacientes() {
        pacientesList.clear();
        pacientesList.addAll(pacienteService.findAll());
        filteredList.clear();
        filteredList.addAll(pacientesList);
    }

    private void fillForm(Paciente paciente) {
        txtNombres.setText(paciente.getNombres());
        txtApellidos.setText(paciente.getApellidos());
        txtDni.setText(paciente.getDni());
        dpFechaNacimiento.setValue(paciente.getFechaNacimiento());
        txtTelefono.setText(paciente.getTelefono());
        txtEmail.setText(paciente.getEmail());
        txtDireccion.setText(paciente.getDireccion());
        cbGenero.setValue(paciente.getGenero());
    }

    @FXML
    private void handleGuardar() {
        if (validateForm()) {
            try {
                Paciente paciente = new Paciente();
                paciente.setNombres(txtNombres.getText().trim());
                paciente.setApellidos(txtApellidos.getText().trim());
                paciente.setDni(txtDni.getText().trim());
                paciente.setFechaNacimiento(dpFechaNacimiento.getValue());
                paciente.setTelefono(txtTelefono.getText().trim());
                paciente.setEmail(txtEmail.getText().trim());
                paciente.setDireccion(txtDireccion.getText().trim());
                paciente.setGenero(cbGenero.getValue());

                if (pacienteService.existsByDni(paciente.getDni())) {
                    showAlert("Error", "Ya existe un paciente con este DNI", Alert.AlertType.ERROR);
                    return;
                }

                pacienteService.save(paciente);
                showAlert("Éxito", "Paciente guardado correctamente", Alert.AlertType.INFORMATION);
                loadPacientes();
                clearForm();
            } catch (Exception e) {
                showAlert("Error", "Error al guardar el paciente: " + e.getMessage(), Alert.AlertType.ERROR);
            }
        }
    }

    @FXML
    private void handleActualizar() {
        if (pacienteSeleccionado != null && validateForm()) {
            try {
                pacienteSeleccionado.setNombres(txtNombres.getText().trim());
                pacienteSeleccionado.setApellidos(txtApellidos.getText().trim());
                pacienteSeleccionado.setDni(txtDni.getText().trim());
                pacienteSeleccionado.setFechaNacimiento(dpFechaNacimiento.getValue());
                pacienteSeleccionado.setTelefono(txtTelefono.getText().trim());
                pacienteSeleccionado.setEmail(txtEmail.getText().trim());
                pacienteSeleccionado.setDireccion(txtDireccion.getText().trim());
                pacienteSeleccionado.setGenero(cbGenero.getValue());

                if (pacienteService.existsByDniAndIdNot(pacienteSeleccionado.getDni(), pacienteSeleccionado.getId())) {
                    showAlert("Error", "Ya existe otro paciente con este DNI", Alert.AlertType.ERROR);
                    return;
                }

                pacienteService.save(pacienteSeleccionado);
                showAlert("Éxito", "Paciente actualizado correctamente", Alert.AlertType.INFORMATION);
                loadPacientes();
                clearForm();
            } catch (Exception e) {
                showAlert("Error", "Error al actualizar el paciente: " + e.getMessage(), Alert.AlertType.ERROR);
            }
        }
    }

    @FXML
    private void handleEliminar() {
        if (pacienteSeleccionado != null) {
            Alert confirmAlert = new Alert(Alert.AlertType.CONFIRMATION);
            confirmAlert.setTitle("Confirmar eliminación");
            confirmAlert.setHeaderText("¿Está seguro de eliminar este paciente?");
            confirmAlert.setContentText("Esta acción no se puede deshacer.\n\nPaciente: " + 
                pacienteSeleccionado.getNombreCompleto());

            Optional<ButtonType> result = confirmAlert.showAndWait();
            if (result.isPresent() && result.get() == ButtonType.OK) {
                try {
                    pacienteService.deleteById(pacienteSeleccionado.getId());
                    showAlert("Éxito", "Paciente eliminado correctamente", Alert.AlertType.INFORMATION);
                    loadPacientes();
                    clearForm();
                } catch (Exception e) {
                    showAlert("Error", "Error al eliminar el paciente: " + e.getMessage(), Alert.AlertType.ERROR);
                }
            }
        }
    }

    @FXML
    private void handleLimpiar() {
        clearForm();
    }

    @FXML
    private void handleBuscar() {
        String searchText = txtBuscar.getText();
        if (searchText != null && !searchText.trim().isEmpty()) {
            filterPacientes(searchText);
        } else {
            loadPacientes();
        }
    }

    private void clearForm() {
        txtNombres.clear();
        txtApellidos.clear();
        txtDni.clear();
        dpFechaNacimiento.setValue(null);
        txtTelefono.clear();
        txtEmail.clear();
        txtDireccion.clear();
        cbGenero.setValue(null);
        txtBuscar.clear();
        
        pacienteSeleccionado = null;
        tablePacientes.getSelectionModel().clearSelection();
        
        btnGuardar.setDisable(false);
        btnActualizar.setDisable(true);
        btnEliminar.setDisable(true);
        
        // Restaurar la lista completa
        filteredList.clear();
        filteredList.addAll(pacientesList);
    }

    private boolean validateForm() {
        // Validar nombres
        if (!ValidationUtils.isValidName(txtNombres.getText())) {
            showAlert("Error de validación", "El campo Nombres debe tener al menos 2 caracteres", Alert.AlertType.WARNING);
            txtNombres.requestFocus();
            return false;
        }
        
        // Validar apellidos
        if (!ValidationUtils.isValidName(txtApellidos.getText())) {
            showAlert("Error de validación", "El campo Apellidos debe tener al menos 2 caracteres", Alert.AlertType.WARNING);
            txtApellidos.requestFocus();
            return false;
        }
        
        // Validar DNI
        if (!ValidationUtils.isValidDni(txtDni.getText())) {
            showAlert("Error de validación", "El DNI debe tener exactamente 8 dígitos", Alert.AlertType.WARNING);
            txtDni.requestFocus();
            return false;
        }
        
        // Validar fecha de nacimiento
        if (dpFechaNacimiento.getValue() == null) {
            showAlert("Error de validación", "La fecha de nacimiento es obligatoria", Alert.AlertType.WARNING);
            dpFechaNacimiento.requestFocus();
            return false;
        }
        
        if (dpFechaNacimiento.getValue().isAfter(LocalDate.now())) {
            showAlert("Error de validación", "La fecha de nacimiento no puede ser futura", Alert.AlertType.WARNING);
            dpFechaNacimiento.requestFocus();
            return false;
        }
        
        // Validar teléfono (opcional pero si se ingresa debe ser válido)
        if (!txtTelefono.getText().trim().isEmpty() && !ValidationUtils.isValidPhone(txtTelefono.getText().trim())) {
            showAlert("Error de validación", "El teléfono debe tener 9 dígitos", Alert.AlertType.WARNING);
            txtTelefono.requestFocus();
            return false;
        }
        
        // Validar email (opcional pero si se ingresa debe ser válido)
        if (!txtEmail.getText().trim().isEmpty() && !ValidationUtils.isValidEmail(txtEmail.getText().trim())) {
            showAlert("Error de validación", "El formato del email no es válido", Alert.AlertType.WARNING);
            txtEmail.requestFocus();
            return false;
        }
        
        // Validar género
        if (cbGenero.getValue() == null) {
            showAlert("Error de validación", "Debe seleccionar un género", Alert.AlertType.WARNING);
            cbGenero.requestFocus();
            return false;
        }
        
        return true;
    }

    private void showAlert(String title, String message, Alert.AlertType type) {
        Alert alert = new Alert(type);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }
}