package com.mycompany.ejercicio_ftp.PruebaA;

import org.apache.commons.net.ftp.FTP;
import org.apache.commons.net.ftp.FTPClient;

import java.io.*;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Scanner;

public class Ejercicio_FTP {//Prueba A

    public static void main(String[] args) {
        // Solicitar la ruta de la carpeta
        Scanner scanner = new Scanner(System.in);
        System.out.print("Introduce la ruta de la carpeta a comprimir: ");
        String folderName = scanner.nextLine();

        // Comprimir la carpeta
        String zipFileName = compressFolder(folderName);

        if (zipFileName != null) {
            // Sube el archivo comprimido al servidor FTP
            uploadToFTP(zipFileName);
        }

        // Cerrar el scanner
        scanner.close();
    }

    private static String compressFolder(String folderName) {
        try {
            // Genera el nombre del archivo ZIP usando la fecha y hora actual
            SimpleDateFormat dateFormat = new SimpleDateFormat("yyyyMMdd_HHmmss");
            String zipFileName = "backup_" + dateFormat.format(new Date()) + ".zip";

            // Ejecuta el comando zip para comprimir la carpeta
            String[] command = {"zip", "-r", zipFileName, folderName};
            Process process = Runtime.getRuntime().exec(command);

            // Espera a que el proceso termine
            int exitCode = process.waitFor();
            if (exitCode == 0) {
                System.out.println("Carpeta comprimida correctamente.");
                return zipFileName;
            } else {
                System.err.println("Error al comprimir la carpeta.");
                return null;
            }
        } catch (IOException | InterruptedException e) {
            e.printStackTrace();
            return null;
        }
    }

    private static void uploadToFTP(String zipFileName) {
        String server = "127.0.0.1"; // Coloca aquí la dirección del servidor FTP
        int port = 14147; // Puerto FTP
        String user = "root"; //
        String pass = "123456"; // Contraseña FTP

        FTPClient ftpClient = new FTPClient();
        System.out.println(ftpClient);
        try {
            ftpClient.connect(server, port);
            boolean success = ftpClient.login(user, pass);
            if (!success) {
                System.err.println("Inicio de sesión fallido. Verifica el nombre de usuario y la contraseña.");
                return;
            }
            ftpClient.enterLocalPassiveMode();
            ftpClient.setFileType(FTP.BINARY_FILE_TYPE);

            File zipFile = new File(zipFileName);
            InputStream inputStream = new FileInputStream(zipFile);

            String remoteFilePath = "/remote-folder/" + zipFileName; // Ruta en el servidor FTP donde se va a almacenar el archivo

            boolean done = ftpClient.storeFile(remoteFilePath, inputStream);
            inputStream.close();
            if (done) {
                System.out.println("Archivo subido al servidor FTP correctamente.");
            } else {
                System.err.println("Error al subir el archivo al servidor FTP.");
            }
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

