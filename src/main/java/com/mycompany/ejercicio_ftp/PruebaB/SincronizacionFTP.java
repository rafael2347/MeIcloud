package com.mycompany.ejercicio_ftp.PruebaB;

import org.apache.commons.net.ftp.FTP;
import org.apache.commons.net.ftp.FTPClient;

import java.io.*;
import java.util.Scanner;

public class SincronizacionFTP {//Prueba B

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        // Solicitar la ruta de la carpeta local
        System.out.print("Introduce la ruta de la carpeta local a sincronizar: ");
        String carpetaLocal = scanner.nextLine();

        // Menú interactivo
        System.out.println("Seleccione la acción que desea realizar:");
        System.out.println("1. Subir archivo al servidor FTP");
        System.out.println("2. Borrar archivo del servidor FTP");
        System.out.println("3. Descargar archivo del servidor FTP");
        System.out.print("Ingrese el número de la acción: ");
        int opcion = scanner.nextInt();

        // Realizar la acción seleccionada
        switch (opcion) {
            case 1:
                System.out.print("Introduce la ruta del archivo local a subir: ");
                scanner.nextLine(); // Consumir la nueva línea pendiente
                String archivoLocalSubir = scanner.nextLine();
                System.out.print("Introduce la ruta del archivo remoto: ");
                String archivoRemotoSubir = scanner.nextLine();
                subirArchivoFTP(archivoLocalSubir, archivoRemotoSubir);
                break;
            case 2:
                System.out.print("Introduce la ruta del archivo remoto a borrar: ");
                scanner.nextLine(); // Consumir la nueva línea pendiente
                String archivoRemotoBorrar = scanner.nextLine();
                borrarArchivoFTP(archivoRemotoBorrar);
                break;
            case 3:
                System.out.print("Introduce la ruta del archivo remoto a descargar: ");
                scanner.nextLine(); // Consumir la nueva línea pendiente
                String archivoRemotoDescargar = scanner.nextLine();
                System.out.print("Introduce la ruta del archivo local: ");
                String archivoLocalDescargar = scanner.nextLine();
                descargarArchivoFTP(archivoRemotoDescargar, archivoLocalDescargar);
                break;
            default:
                System.out.println("Opción no válida.");
                break;
        }

        // Cerrar el scanner
        scanner.close();
    }

    private static void subirArchivoFTP(String archivoLocal, String archivoRemoto) {
        String server = "127.0.0.1"; // Dirección del servidor FTP
        int port = 21; // Puerto FTP
        String user = "root"; // Nombre de usuario FTP
        String pass = "123456"; // Contraseña FTP

        FTPClient ftpClient = new FTPClient();
        try {
            ftpClient.connect(server, port);
            ftpClient.login(user, pass);
            ftpClient.enterLocalPassiveMode();
            ftpClient.setFileType(FTP.BINARY_FILE_TYPE);

            File localFile = new File(archivoLocal);
            InputStream inputStream = new FileInputStream(localFile);

            // Subir archivo al servidor FTP
            ftpClient.storeFile(archivoRemoto, inputStream);

            inputStream.close();
            System.out.println("Archivo subido al servidor FTP correctamente.");

        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                if (ftpClient.isConnected()) {
                    ftpClient.logout();
                    ftpClient.disconnect();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    private static void borrarArchivoFTP(String archivoRemoto) {
        String server = "127.0.0.1"; // Dirección del servidor FTP
        int port = 21; // Puerto FTP
        String user = "root"; // Nombre de usuario FTP
        String pass = "123456"; // Contraseña FTP

        FTPClient ftpClient = new FTPClient();
        try {
            ftpClient.connect(server, port);
            ftpClient.login(user, pass);
            ftpClient.enterLocalPassiveMode();

            // Borrar archivo del servidor FTP
            ftpClient.deleteFile(archivoRemoto);

            System.out.println("Archivo eliminado del servidor correctamente.");

        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                if (ftpClient.isConnected()) {
                    ftpClient.logout();
                    ftpClient.disconnect();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    private static void descargarArchivoFTP(String archivoRemoto, String archivoLocal) {
        String server = "127.0.0.1"; // Dirección del servidor FTP
        int port = 21; // Puerto FTP
        String user = "root"; // Nombre de usuario FTP
        String pass = "123456"; // Contraseña FTP

        FTPClient ftpClient = new FTPClient();
        try {
            ftpClient.connect(server, port);
            ftpClient.login(user, pass);
            ftpClient.enterLocalPassiveMode();

            // Descargar archivo del servidor FTP
            OutputStream outputStream = new BufferedOutputStream(new FileOutputStream(archivoLocal));
            ftpClient.retrieveFile(archivoRemoto, outputStream);

            outputStream.close();
            System.out.println("Archivo descargado del servidor FTP correctamente.");

        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                if (ftpClient.isConnected()) {
                    ftpClient.logout();
                    ftpClient.disconnect();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}
