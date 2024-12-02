package com.gherex.cajeroautomaticojavafx;

import java.util.ArrayList;

public class CajeroAutomatico {

    private double saldo = 10000f;
    private String usuario;
    private final ArrayList<String> historialDeTransacciones;

    // Instancia única (singleton)
    private static CajeroAutomatico instance;

    // Constructor privado para evitar instanciación directa
    private CajeroAutomatico() {
        historialDeTransacciones = new ArrayList<>();
    }

    // Método para obtener la instancia única
    public static CajeroAutomatico getInstance() {
        if (instance == null) {
            instance = new CajeroAutomatico();
        }
        return instance;
    }

    // Getters y setters
    public ArrayList<String> getHistorialDeTransacciones() {
        return this.historialDeTransacciones;
    }

    public void setHistorialDeTransacciones(String str) {
        this.historialDeTransacciones.add(str);
    }

    public String getUsuario() {
        return this.usuario;
    }

    public void setUsuario(String usuario) {
        this.usuario = usuario;
    }

    public double getSaldo() {
        return this.saldo;
    }

    public void setSaldo(double saldo) {
        this.saldo = saldo;
    }

    // Métodos de validación
    public boolean checkUsuario(String user) {
        return !user.matches(".*(\\d|\\s).*"); // No debe contener números o espacios
    }

    public boolean checkPassword(String pass) {
        return !pass.contains(" "); // No debe contener espacios
    }

    public boolean checkMonto(String monto) {
        return monto.matches("^\\d+(\\.\\d+)?$"); // Debe ser un número válido
    }

    public boolean checkCBU(String cbu) {
        return cbu.matches("^\\d{6,}$"); // Debe tener al menos 6 dígitos
    }
}
