package com.yuliamz.so.s2.controlador;

import com.yuliamz.so.s2.Modelo.AdministradorProcesos;
import com.yuliamz.so.s2.Modelo.Proceso;
import com.yuliamz.so.s2.Vista.MaskField;
import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.beans.property.ReadOnlyStringWrapper;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.SortedList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.CheckBox;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.Tooltip;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.Pane;

/**
 * FXML Controller class
 *
 * @author Yuliamz
 */
public class SO2Controller implements Initializable {

    ObservableList<Proceso> listaProcesos;
    AdministradorProcesos ap;

    @FXML
    private Pane panelProcesos, panelReportes;
    @FXML
    private MaskField txtNombreProceso, numTiempo, numPrioridad, numNuevaPrioridad;
    @FXML
    private CheckBox checkBloqueo, checkDestruccion, checkComunicacion;
    @FXML
    private Label infoBloqueo, errorNombre, infoDestruccion, infoComunicacion;
    @FXML
    private TableView<Proceso> tablaProcesos;
    @FXML
    private TableColumn<Proceso, String> columnaNombre, columnaBloqueo, columnaDestruccion, columnaComunicacion;
    @FXML
    private TableColumn<Proceso, Integer> columnaTiempo, columnaPrioridad;
    @FXML
    private ListView<String> viewListos, viewDespachados, viewEjecucion, viewExpTiempo, viewBloqueados, viewDestruidos, viewComunicacion, viewFinalizados;

    @FXML
    void bloquear(ActionEvent event) {
        infoBloqueo.setVisible(checkBloqueo.isSelected());
    }

    @FXML
    void comunicar(ActionEvent event) {
        infoComunicacion.setVisible(checkComunicacion.isSelected());
    }

    @FXML
    void destruir(ActionEvent event) {
        infoDestruccion.setVisible(checkDestruccion.isSelected());
    }

    @FXML
    void limpiar(ActionEvent event) {
        checkBloqueo.setSelected(false);
        checkDestruccion.setSelected(false);
        checkComunicacion.setSelected(false);
        txtNombreProceso.setPlainText("P");
        numTiempo.setPlainText("5");
        numPrioridad.setPlainText("1");
        numNuevaPrioridad.setPlainText("");
        infoBloqueo.setVisible(false);
        infoComunicacion.setVisible(false);
        infoDestruccion.setVisible(false);
        errorNombre.setVisible(false);
    }

    boolean validarCampos() {
        if ((!txtNombreProceso.getText().trim().matches(".*\\d+.*"))) {
            errorNombre.setText("El nombre debe finalizar en un número");
            errorNombre.setVisible(true);
            return false;
        }
        if (listaProcesos.stream().anyMatch(e -> e.getNombre().equals(txtNombreProceso.getText().trim()))) {
            errorNombre.setText("El nombre ya registrado, por favor elija uno nuevo");
            errorNombre.setVisible(true);
            return false;
        }
        if (numTiempo.getText().equals("")) {
            errorNombre.setText("El tiempo del proceso no puede estar vacío");
            errorNombre.setVisible(true);
            return false;
        }
        if (numTiempo.getText().replaceAll("0+", "0").equals("0")) {
            errorNombre.setText("El tiempo del proceso debe ser superior a 0");
            errorNombre.setVisible(true);
            return false;
        }
        if (numPrioridad.getText().isEmpty()) {
            errorNombre.setText("Debe especificar una prioridad al proceso");
            errorNombre.setVisible(true);
            return false;
        }
        if (numPrioridad.getText().replaceAll("0+", "0").equals("0")) {
            errorNombre.setText("La prioridad del proceso debe ser superior a 0");
            errorNombre.setVisible(true);
            return false;
        }
        

        try {
            int prioridad = Integer.parseInt(numPrioridad.getText());
            if (listaProcesos.stream().anyMatch(e -> e.getPrioridad() == prioridad) && numNuevaPrioridad.getText().isEmpty()) {
                errorNombre.setText("La prioridad ya fué asignada");
                errorNombre.setVisible(true);
                return false;
            }
        } catch (NumberFormatException e) {
            errorNombre.setText("La prioridad no es válida");
            errorNombre.setVisible(true);
            return false;
        }
        
        if (!numNuevaPrioridad.getText().isEmpty()) {
            if (numNuevaPrioridad.getText().replaceAll("0+", "0").equals("0")) {
                errorNombre.setText("El cambio de prioridad debe ser superior a 0");
                errorNombre.setVisible(true);
                return false;
            }

            try {
                int nuevaPrioridad = Integer.parseInt(numNuevaPrioridad.getText());
//                int prioridad = Integer.parseInt(numPrioridad.getText());
                if (listaProcesos.stream().anyMatch(e -> e.getPrioridad() == nuevaPrioridad)) {
                    errorNombre.setText("La nueva prioridad ya fué asignada");
                    errorNombre.setVisible(true);
                    return false;
                }
//                if (prioridad==nuevaPrioridad) {
//                    errorNombre.setText("La nueva prioridad es igual a la prioridad asignada");
//                    errorNombre.setVisible(true);
//                    return false;
//                }
            } catch (NumberFormatException e) {
                errorNombre.setText("La nueva prioridad no es válida");
                errorNombre.setVisible(true);
                return false;
            }

        }
        
        

        return true;
    }

    @FXML
    void crearProceso(ActionEvent event) {
        if (validarCampos()) {
            int tiempo = Integer.parseInt(numTiempo.getText());
            int prioridad = Integer.parseInt(numNuevaPrioridad.getText().isEmpty() ? numPrioridad.getText() : numNuevaPrioridad.getText());
            listaProcesos.add(new Proceso(txtNombreProceso.getText().trim(), tiempo, prioridad, checkBloqueo.isSelected(), checkDestruccion.isSelected(), checkComunicacion.isSelected()));
            limpiar(event);
        }
    }

    @FXML
    void reportes(ActionEvent event) {
        if (!panelReportes.isVisible()) {
            mostrarReportes();
        }
    }

    @FXML
    void procesos(ActionEvent event) {
        if (!panelProcesos.isVisible()) {
            mostrarProcesos();
        }
    }

    @FXML
    void iniciarProcesos(ActionEvent event) {
        if (listaProcesos.isEmpty()) {
            Alert alert = new Alert(AlertType.ERROR);
            alert.setTitle("No hay procesos");
            alert.setHeaderText("No existen procesos para la simulación");
            alert.setContentText("Asegurese de ingresar al menos un(1) proceso antes de iniciar la simulación");
            alert.initOwner(panelProcesos.getScene().getWindow());
            alert.showAndWait();
        } else {
            ap = new AdministradorProcesos(new ArrayList<>(listaProcesos));
            try {
                ap.iniciarSecuencia();
                viewListos.setItems(getObservableListFrom(ap.getListos()));
                viewDespachados.setItems(getObservableListFrom(ap.getDespachados()));
                viewEjecucion.setItems(getObservableListFrom(ap.getEjecucion()));
                viewExpTiempo.setItems(getObservableListFrom(ap.getExpiracionTiempo()));
                viewBloqueados.setItems(getObservableListFrom(ap.getBloqueados()));
                viewDestruidos.setItems(getObservableListFrom(ap.getDestruidos()));
                viewComunicacion.setItems(getObservableListFrom(ap.getComunicacion()));
                viewFinalizados.setItems(getObservableListFrom(ap.getFinalizados()));

                mostrarReportes();
            } catch (CloneNotSupportedException ex) {
                Logger.getLogger(SO2Controller.class.getName()).log(Level.SEVERE, null, ex);
                Alert alert = new Alert(AlertType.ERROR);
                alert.setTitle("Error inesperado");
                alert.setHeaderText("Ocurrió un error en la ejecución");
                alert.setContentText("Asegurese que la información insertada sea correcta");
                alert.initOwner(panelProcesos.getScene().getWindow());
                alert.showAndWait();
            }
        }

    }

    void mostrarReportes() {
        panelProcesos.setVisible(false);
        panelReportes.setVisible(true);
    }

    void mostrarProcesos() {
        panelReportes.setVisible(false);
        panelProcesos.setVisible(true);
    }

    ObservableList<String> getObservableListFrom(ArrayList<Proceso> list) {
        ObservableList<String> observableList = FXCollections.observableArrayList();
        list.forEach(e -> observableList.add(e.getNombre() + " - " + e.getTiempo() + "(u)"));
        return observableList;
    }

    @FXML
    void acercaDe(ActionEvent event) {
        Alert alert = new Alert(AlertType.INFORMATION);
        alert.setTitle("Acerca de...");
        alert.setHeaderText("Software 2 de Sistemas Operativos");
        alert.setContentText("Este software tiene como objetivo simular\nla transmicion de estados de los procesos\nmanejados mediante una computadora.\n\nAutores:\n    *Julian David Grijalba Bernal\n    *William Desiderio Gil Farfan");
        alert.initOwner(panelProcesos.getScene().getWindow());
        alert.showAndWait();
    }

    @FXML
    void salir(ActionEvent event) {
        System.exit(0);
    }

    @FXML
    private void eliminarProceso(ActionEvent event) {
        if (tablaProcesos.getSelectionModel().getSelectedIndex() >= 0) {
            tablaProcesos.getItems().remove(tablaProcesos.getSelectionModel().getFocusedIndex());
        } else {
            Alert alert = new Alert(AlertType.ERROR);
            alert.setTitle("No se puede eliminar");
            alert.setHeaderText("Debe seleccionar el proceso que desea eliminar");
            alert.initOwner(panelProcesos.getScene().getWindow());
            alert.showAndWait();
        }
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        listaProcesos = FXCollections.observableArrayList();
        columnaNombre.setCellValueFactory(new PropertyValueFactory<>("nombre"));
        columnaTiempo.setCellValueFactory(new PropertyValueFactory<>("tiempo"));
        columnaPrioridad.setCellValueFactory(new PropertyValueFactory<>("prioridad"));
        columnaBloqueo.setCellValueFactory(new PropertyValueFactory<>("isBloqueado"));
        columnaBloqueo.setCellValueFactory(e -> {
            return new ReadOnlyStringWrapper(e.getValue().isBloqueado() ? "SI" : "NO");
        });
        columnaDestruccion.setCellValueFactory(new PropertyValueFactory<>("isDestruido"));
        columnaDestruccion.setCellValueFactory(e -> {
            return new ReadOnlyStringWrapper(e.getValue().isDestruido() ? "SI" : "NO");
        });
        columnaComunicacion.setCellValueFactory(new PropertyValueFactory<>("isComunicacion"));
        columnaComunicacion.setCellValueFactory(e -> {
            return new ReadOnlyStringWrapper(e.getValue().isComunicacion() ? "SI" : "NO");
        });
        tablaProcesos.setItems(listaProcesos);
        txtNombreProceso.setTooltip(new Tooltip("El nombre del proceso debe iniciar con una \"P\" seguido de un número identificador"));
        numTiempo.setTooltip(new Tooltip("El tiempo del proceso debe ser superior a 0 y no puede estar vacío"));
        numTiempo.setPlainText("5");
        numPrioridad.setPlainText("1");
    }

}
//<?import com.yuliamz.so.s2.Vista.*?>
