package cajeroautomatico;

import java.util.ArrayList;
import java.util.Scanner;

public class CajeroAutomatico {

    private double saldo = 10000f;
    private String usuario;
    private final ArrayList<String> historialDeTransacciones;

    public ArrayList<String> getHistorialDeTransacciones() { return this.historialDeTransacciones; }
    public void setHistorialDeTransacciones(String str) { this.historialDeTransacciones.add(str); }

    public String getUsuario() { return this.usuario; }
    public void setUsuario (String usuario) { this.usuario = usuario; }

    public double getSaldo() { return this.saldo; }
    public void setSaldo (double saldo) { this.saldo = saldo;  }

    public boolean checkUsuario(String user) { return !user.matches(".*(\\d|\\s).*"); }

    public boolean checkPassword(String pass) { return !pass.contains(" "); }

    public boolean checkMonto(String monto) { return !monto.matches("^\\d+(\\.\\d+)?$"); }

    public boolean checkOpcion(String opcion) { return !opcion.matches("^\\d$"); }

    public void presentacion() {
        System.out.println("==============================");
        System.out.println("BIENVENIDO AL CAJERO AUTOMÁTICO");
        System.out.println("==============================");
        System.out.println("1. Iniciar Sesión");
        System.out.println("2. Salir");
        System.out.println("==============================");

        Scanner entrada = new Scanner(System.in);
        int opcion;
        System.out.print("Ingrese una opción: ");
        String strOpcion = entrada.nextLine();
        while (!strOpcion.matches("[12]")) {
            System.out.print("¡Error!, escriba una opción válida (1 o 2): ");
            strOpcion = entrada.nextLine();
        }
        opcion = Integer.parseInt(strOpcion);
        switch (opcion) {
            case 1:
                inicioSesion();
                break;
            case 2:
                System.exit(0);
                break;
            default:
                break;
        }
    }

    public void inicioSesion() {
        System.out.println("==============================");
        System.out.println("LOGIN");
        System.out.println("==============================");
        Scanner entrada = new Scanner(System.in);
        System.out.print("Usuario: ");
        String user = entrada.nextLine();
        while (!checkUsuario(user)) {
            System.out.println("Nombre de usuario inválido.");
            System.out.print("Usuario: ");
            user = entrada.nextLine();
        }
        setUsuario(user);
        System.out.print("Contraseña: ");
        String pass = entrada.nextLine();
        while (!checkPassword(pass)) {
            System.out.println("Contraseña inválida.");
            System.out.print("Contraseña: ");
            pass = entrada.nextLine();
        }
    }

    public void consultarSaldo() {
        System.out.println("Saldo disponible: " + getSaldo() + " $");
        ventanaCierre();
    }

    public void ventanaCierre() {
        System.out.println(
                """
                ==============================
                1. Volver al menú principal
                2. Salir
                ==============================
                """
        );
        Scanner ent = new Scanner(System.in);
        int val;
        do {
            String strOpcion = ent.nextLine();
            while (checkOpcion(strOpcion)) {
                System.out.print("¡Error!, escriba una opción válida: ");
                strOpcion = ent.nextLine();
            }
            val = Integer.parseInt(strOpcion);
            if (val == 1) {
                menuPrincipal();
            } else if (val == 2) {
                System.exit(0);
            }
        } while (val != 1);
    }

    public void retirarDinero() {
        System.out.print("Ingrese monto a retirar: ");
        Scanner in = new Scanner(System.in);
        String strMonto = in.nextLine();
        while (checkMonto(strMonto)) {
            System.out.print("¡Error!, ingrese un número valido: " );
            strMonto = in.nextLine();
        }
        double monto = Double.parseDouble(strMonto);
        if (monto > getSaldo()) {
            System.out.println("Saldo insuficiente.");
            retirarDinero();
        } else {
            double newSaldo = (getSaldo() - monto);
            setSaldo(newSaldo);
            setHistorialDeTransacciones("Retiro: " + monto + "$");
            System.out.println("Retiro: " + monto + "$" );
            consultarSaldo();
        }
    }

    public void depositarDinero() {
        System.out.print("Ingrese monto a depositar: ");
        Scanner in = new Scanner(System.in);
        String strMonto = in.nextLine();
        while (checkMonto(strMonto)) {
            System.out.print("¡Error!, ingrese un número valido: " );
            strMonto = in.nextLine();
        }
        double monto = Double.parseDouble(strMonto);
        if (monto <= 0) {
            System.out.println("Error, el monto debe ser mayor a cero.");
            depositarDinero();
        } else {
            double newSaldo = getSaldo() + monto;
            setSaldo(newSaldo);
            String trans = "Depósito: " + monto + "$";
            setHistorialDeTransacciones(trans);
            consultarSaldo();
        }
    }

    public void transferirDinero() {
        Scanner in = new Scanner(System.in);
        System.out.print("Ingrese un monto: ");
        String strMonto = in.nextLine();
        while (checkMonto(strMonto)) {
            System.out.print("¡Error!, ingrese un número valido: " );
            strMonto = in.nextLine();
        }
        double monto = Double.parseDouble(strMonto);
        double newSaldo = (getSaldo() - monto);
        if (newSaldo < 0) {
            System.out.println("Saldo insuficiente.");
            transferirDinero();
        } else {
            System.out.print("Ingrese CBU del destinatario:");
            String cbu = in.nextLine();
            while (!cbu.matches("^\\d{6,}$")) {
                System.out.print("CBU incorrecto. Escriba un CBU válido: ");
                cbu = in.nextLine();
            }
            setSaldo(newSaldo);
            setHistorialDeTransacciones("Transferencia: " + monto + "$, a CBU: " + cbu);
            consultarSaldo();
        }
    }

    public void verHistorial() {
        System.out.print(
                """
                ============================
                HISTORIAL DE TRANSACCIONES
                ============================
                """
        );
        for (String transaccion : getHistorialDeTransacciones()) {
            if (transaccion != null) System.out.println(transaccion);
        }
        ventanaCierre();
    }

    public void cerrarSesion() {
        presentacion();
        menuPrincipal();
    }

    public void menuPrincipal() {
        System.out.println(
                "==============================\n" +
                "MENÚ PRINCIPAL\n" +
                "Bienvenido nuevamente, " + getUsuario() + ".\n" +
                "==============================\n" +
                "1. Consultar saldo\n" +
                "2. Retirar dinero\n" +
                "3. Depositar dinero\n" +
                "4. Transferir dinero\n" +
                "5. Ver historial de transacciones\n" +
                "6. Cerrar sesión\n" +
                "==============================\n" +
                "Seleccione una opción: "
        );
        Scanner ent = new Scanner(System.in);
        String strOpcion = ent.nextLine();
        while (checkOpcion(strOpcion)) {
            System.out.print("¡Error!, escriba una opción válida: ");
            strOpcion = ent.nextLine();
        }
        int caso = Integer.parseInt(strOpcion);
        switch(caso) {
            case 1:
                consultarSaldo();
                break;
            case 2:
                retirarDinero();
                break;
            case 3:
                depositarDinero();
                break;
            case 4:
                transferirDinero();
                break;
            case 5:
                verHistorial();
                break;
            case 6:
                cerrarSesion();
                break;
            default:
                System.out.println("Entrada inválida. Por favor seleccione una entrada válida.");
                menuPrincipal();
                break;
        }
    }

    public CajeroAutomatico() {
        historialDeTransacciones = new ArrayList<>();
        presentacion();
        menuPrincipal();
    }

}
