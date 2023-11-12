import java.nio.file.Files;
import java.nio.file.Path;
import java.sql.Connection;
import java.sql.DriverManager;
import java.time.LocalDate;
import java.util.List;

public class OperacionesSqlite {
    public static void main(String[] args) {

        Path rutaBaseDatos = Path.of("Ejercicio6", "src", "main", "resources", "db", "f12006sqlite.db");
        OperacionesCRUDPilotos op = new OperacionesCRUDPilotos();
        String info;
        List<Piloto> listaPilotos;
        Piloto p;
        Piloto p1 = new Piloto( "CAR", "Carol", "Maldonado", LocalDate.of(2000, 3, 8), "Spanish", "www.hola.com" );
        Piloto pilotoActualizado = new Piloto(7, "PRU", "Carmen", "Torres", LocalDate.of(1974, 7, 28), "Spanish", "www.loquesea");

        try (Connection conexion = DriverManager.getConnection("jdbc:sqlite:" + rutaBaseDatos.toString())) {

            p = op.LeerPiloto(2, conexion);
            info = p.toString();
            System.out.println(info);

            op.CrearPiloto(p1, conexion);

            listaPilotos = op.LeerPilotos(conexion);
            op.ActualizarPiloto(pilotoActualizado, conexion);
            op.BorrarPiloto(pilotoActualizado, conexion);
            op.MostrarClasificacionPiloto(conexion);
            op.MostrarClasificacionConstructores(conexion);


        } catch (Exception e) {
            System.err.println(e.getClass().getName() + ": " + e.getMessage());

        }
    }
}
