package com.gherex.cajeroautomaticojavafx;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;

import java.io.IOException;

public class CajeroController {

    private final CajeroAutomatico ca;

    public CajeroController() {
        ca = CajeroAutomatico.getInstance(); // Obtiene la instancia única
    }

    /*----------------- VENTANA INICIO DE SESIÓN + LÓGICA -------------------*/
    @FXML
    protected void onIniciarSesionButtonClick() throws IOException {
        CajeroApplication.changeScene("Login.fxml");
    }
    @FXML
    protected void onSalirButtonClick() {
        System.exit(0); // Cierra la aplicación
    }
    @FXML
    private TextField usernameField; // inputs para la ventana Login
    @FXML
    private TextField passwordField;
    @FXML
    private Label errorLabel;
    @FXML
    private Label menuText;

    @FXML
    protected void onLoginSubmit() throws IOException {
        String username = usernameField.getText();
        String password = passwordField.getText();
        if (ca.checkUsuario(username) && ca.checkPassword(password)) {
            ca.setUsuario(username);
            errorLabel.setText(""); // Limpia el mensaje de error
            System.out.println("Inicio de sesión exitoso.");
            CajeroApplication.changeScene("MenuPrincipal.fxml");
        } else {
            errorLabel.setText("Usuario o contraseña incorrectos.");
        }
    }

    /*----------------- VENTANA RETIRAR DINERO + LÓGICA -------------------*/
    @FXML
    protected void onRetirarDineroButtonClick() throws IOException {
        CajeroApplication.changeScene("RetirarDinero.fxml");
    }

    @FXML
    private TextField montoField;
    @FXML
    private Label errorRDLabel;
    @FXML
    private Label saldoDisponible;

    @FXML
    protected void onRetirarSubmit() {
        String monto = montoField.getText();
        if (ca.checkMonto(monto)) {
            double newMonto = ca.getSaldo() - Double.parseDouble(monto);
            if (newMonto >= 0) {
                String str = "Retiro: " + monto + "$";
                ca.setHistorialDeTransacciones(str);
                saldoDisponible.setText("Saldo disponible: " + newMonto + "$");
                ca.setSaldo(newMonto);
                errorRDLabel.setText(""); // Limpia el mensaje de error
            }
            else {
                errorRDLabel.setText("Saldo insuficiente.");
            }
        } else {
            errorRDLabel.setText("Ingrese un valor numérico.");
        }
    }

    @FXML
    protected void onVolverAlMenu() throws IOException {
        CajeroApplication.changeScene("MenuPrincipal.fxml");
    }

    /*----------------- VENTANA CONSULTAR SALDO + LÓGICA -------------------*/
    @FXML
    private Label consultarSaldo;

    @FXML
    protected void onConsultarSaldoButtonClick() throws IOException {
        CajeroApplication.changeScene("ConsultarSaldo.fxml");
    }

    @FXML
    public void initialize() {
        if (menuText != null) {
            menuText.setText("Bienvenido nuevamente, " + ca.getUsuario() + ".");
        }
        // Actualizar el saldo disponible cuando se carga la ventana
        if (consultarSaldo != null) {
            consultarSaldo.setText("Saldo disponible: " + ca.getSaldo() + "$");
        }
        if (historialTransacciones != null) {
            StringBuilder historial = new StringBuilder();
            for (String transaccion : ca.getHistorialDeTransacciones()) {
                historial.append(transaccion).append("\n"); // Agregar cada transacción con un salto de línea
            }
            historialTransacciones.setText(historial.toString()); // Establecer all text concatenado
        }
    }

    /*----------------- VENTANA DEPOSITAR DINERO + LÓGICA -------------------*/
    @FXML
    protected void onDepositarDineroButtonClick() throws IOException {
        CajeroApplication.changeScene("DepositarDinero.fxml");
    }

    @FXML
    private Label errorDDLabel;

    @FXML
    protected void onDepositarSubmit() {
        String monto = montoField.getText();
        if (ca.checkMonto(monto) && (Double.parseDouble(monto) >= 0)) {
            String str = "Depósito: " + monto + "$";
            ca.setHistorialDeTransacciones(str);
            double newSaldo = ca.getSaldo() + Double.parseDouble(monto);
            ca.setSaldo(newSaldo);
            saldoDisponible.setText("Saldo disponible: " + newSaldo + "$");
            errorDDLabel.setText(""); // Limpia el mensaje de error
        } else {
            errorRDLabel.setText("Ingrese un valor numérico.");
        }
    }

    /*----------------- VENTANA TRANSFERIR DINERO + LÓGICA -------------------*/
    @FXML
    protected void onTransferirDineroButtonClick() throws IOException {
        CajeroApplication.changeScene("TransferirDinero.fxml");
    }
    @FXML
    private Label errorTDLabel;
    @FXML
    private TextField cbuField;

    @FXML
    protected void onTransferirSubmit() {
        String monto = montoField.getText();
        if (ca.checkMonto(monto)) {
            double newMonto = ca.getSaldo() - Double.parseDouble(monto);
            if (newMonto >= 0) {
                if (ca.checkCBU(cbuField.getText())) {
                    String str = "Transferencia: " + monto + "$, a CBU: " + cbuField.getText();
                    ca.setHistorialDeTransacciones(str);
                    saldoDisponible.setText("Saldo disponible: " + newMonto + "$");
                    ca.setSaldo(newMonto);
                    errorTDLabel.setText(""); // Limpia el mensaje de error
                } else {
                    errorTDLabel.setText("CBU inexistente.");
                }
            }
            else {
                errorTDLabel.setText("Saldo insuficiente.");
            }
        } else {
            errorTDLabel.setText("Ingrese un valor numérico.");
        }
    }

    /*----------------- VENTANA MOSTRAR HISTORIAL DE TRANSACCIONES + LÓGICA -------------------*/
    @FXML
    protected void onHistorialButtonClick() throws IOException {
        CajeroApplication.changeScene("HistorialTransacciones.fxml");
    }

    @FXML
    private Label historialTransacciones;

    @FXML
    protected void onCerrarSesionButtonClick() throws IOException {
        CajeroApplication.changeScene("Bienvenida.fxml");
    }

}
