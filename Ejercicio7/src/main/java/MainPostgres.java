import java.sql.Connection;
import java.sql.Date;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.time.LocalDate;

public class MainPostgres {
    public static void main(String[] args) {

        ManejoPostgres mp = new ManejoPostgres();
        String urlConexion = "jdbc:postgresql://ad-ej7.co6njyzhm85i.us-east-1.rds.amazonaws.com:5432/f12006";
        String usuario = "postgres";
        String password = "Hola1234";
        int constructorid;

        try (Connection conexion = DriverManager.getConnection(urlConexion, usuario, password)) {
            try { // Por defecto, el gestor de base de datos ejecuta una operación de confirmación después de la ejecución de cada sentencia de SQL.
                // Para desactivar la confirmación automática e iniciar así una transacción, invocamos el método Connection.setAutoCommit(false)
                conexion.setAutoCommit(false);

                Equipo e = new Equipo("SA", "Seat F3", "Spanish", "www.seat.es");

                constructorid = mp.insercionEquipo(e, conexion);

                Piloto p = new Piloto("SNZ", "Carlos", "Sainz", new Date(1994, 9, 1), "Spanish",constructorid, "https://en.wikipedia.org/wiki/Carlos_Sainz_Jr.");
                Piloto p2 = new Piloto("MLA", "Manuel", "Alomá", new Date(1995, 5, 12), "Spanish",constructorid, "www.hola");

               mp.insercionPiloto(p, conexion);
               mp.insercionPiloto(p2, conexion);


               conexion.commit();

               /* mp.clasificacionMundial(conexion);
                mp.resultadoCarreraPiloto("ALO", conexion);*/

            } catch (SQLException ex1) {
                System.err.println(ex1.getClass().getName() + ": " + ex1.getMessage());
                conexion.rollback();
                System.err.println("ROLLBACK ejecutado");

            }

            mp.mostrarPilotos(conexion);
            mp.mostrarEquipos(conexion);

        } catch (SQLException ex) {
            System.err.println(ex.getClass().getName() + ": " + ex.getMessage());
        }
    }
}
