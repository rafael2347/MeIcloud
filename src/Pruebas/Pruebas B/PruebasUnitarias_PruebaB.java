import static org.junit.Assert.*;
import org.junit.Test;
import java.io.File;
import java.io.IOException;

public class SincronizacionFTPTest {

    @Test
    public void testSubirArchivoFTP() {
        String archivoLocalSubir = "archivo_local.txt";
        String archivoRemotoSubir = "/remote-folder/remote-folder.txt";
        SincronizacionFTP.subirArchivoFTP(archivoLocalSubir, archivoRemotoSubir);

        // Verificar si el archivo se ha subido correctamente
        File archivoSubido = new File(archivoRemotoSubir);
        assertTrue(archivoSubido.exists());
    }

    @Test
    public void testBorrarArchivoFTP() {
        String archivoRemotoBorrar = "/remote-folder/remote-folder.txt";
        // Se crea el archivo a borrar para probar la funcionalidad
        try {
            File archivo = new File(archivoRemotoBorrar);
            archivo.createNewFile();
        } catch (IOException e) {
            e.printStackTrace();
        }

        SincronizacionFTP.borrarArchivoFTP(archivoRemotoBorrar);

        // Verificar si el archivo se ha borrado correctamente
        File archivoBorrado = new File(archivoRemotoBorrar);
        assertFalse(archivoBorrado.exists());
    }

    @Test
    public void testDescargarArchivoFTP() {
        String archivoRemotoDescargar = "/remote-folder/remote-folder.txt";
        String archivoLocalDescargar = "archivo_local_descargado.txt";
        // Se crea el archivo remoto para probar la funcionalidad
        try {
            File archivo = new File(archivoRemotoDescargar);
            archivo.createNewFile();
        } catch (IOException e) {
            e.printStackTrace();
        }

        SincronizacionFTP.descargarArchivoFTP(archivoRemotoDescargar, archivoLocalDescargar);

        // Verificar si el archivo se ha descargado correctamente
        File archivoDescargado = new File(archivoLocalDescargar);
        assertTrue(archivoDescargado.exists());
    }
}
