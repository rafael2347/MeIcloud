import static org.junit.Assert.*;
import org.junit.Test;
import java.io.File;

public class Ejercicio_FTPTest {

    @Test
    public void testCompressFolder() {
        // Carpeta de prueba
        String folderName = "test-folder";

        // Llama al método para comprimir la carpeta
        String zipFileName = Ejercicio_FTP.compressFolder(folderName);

        // Verifica si se generó el archivo ZIP correctamente
        assertNotNull(zipFileName);
        assertTrue(new File(zipFileName).exists());
    }

    @Test
    public void testUploadToFTP() {
        // Archivo ZIP de prueba
        String zipFileName = "test-folder.zip";

        // Llama al método para subir el archivo al servidor FTP
        Ejercicio_FTP.uploadToFTP(zipFileName);

        // Verifica si el archivo se subió correctamente
        // Aquí puedes agregar más aserciones según tus requisitos
    }
}
